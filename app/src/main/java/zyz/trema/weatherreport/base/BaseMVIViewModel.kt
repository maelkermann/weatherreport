package zyz.trema.weatherreport.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

public abstract class BaseMVIViewModel<S : MVI.State, I : MVI.Intent>(
        firstModel: S,
        savedStateHandle: SavedStateHandle? = null
) : androidx.lifecycle.ViewModel(), MVI.ViewModel<S, I> {

    private val savedState = savedStateHandle

    private var state: S = firstModel
    protected val currentState: S get() = state

    // TODO: We might want to only store the most recent change (replay = 1). This would require
    // careful redesign of tests and revalidation of app behavior.
    private val stateChanges = MutableSharedFlow<S.() -> S>(
            64, // Even when there's no subscriber, store changes
            0,
            BufferOverflow.DROP_OLDEST
    )

    public val uiState: SharedFlow<S> = flow {
        stateChanges.collect { changes ->
            state = state.changes()
            emit(state)
        }
    }.shareIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            replay = 1
    )

    init {
        viewModelScope.launch { publishState { firstModel } }
    }

    /**
     * Publish a state change based on the current state.
     * usage ex: publishState { copy(isUserConnected = true) }
     */
    protected suspend fun publishState(change: S.() -> S) {
        stateChanges.emit(change)
    }

    protected suspend fun publishState(state: S) {
        stateChanges.emit { state }
    }

    public suspend fun <P : MVI.State> reduceAndPublishState(reducer: MVI.Reducer<S, P>, partial: P) {
        val change: S.() -> S = {
            reducer.reduce(this, partial)
        }

        stateChanges.emit(change)
    }

    protected abstract suspend fun processIntent(intent: I)

    final override fun sendIntent(intent: I) {
        viewModelScope.launch { processIntent(intent) }
    }

    protected inline fun <reified T : Enum<T>, E> MutableStateFlow<E>.updateWithSavedState(enum: T, value: E): Unit =
            updateWithSavedState(enum.name, value)

    protected fun <E> MutableStateFlow<E>.updateWithSavedState(key: String, value: E) {
        savedState?.set(key, value)
        this.value = value
    }

    protected fun <E> Flow<E>.asViewModelScopeLiveData(): LiveData<E> = this.asLiveData(viewModelScope.coroutineContext)

    override fun stop() {
        viewModelScope.cancel()
    }
}

public object MVI {

    public interface State {
        public fun reset(): State = this
    }

    public interface Intent

    public interface ViewModel<S : State, I : Intent> {

        public fun sendIntent(intent: I)

        public fun stop()
    }

    public fun interface Reducer<S : State, P : State> {
        public fun reduce(previousState: S, partialState: P): S
    }
}
