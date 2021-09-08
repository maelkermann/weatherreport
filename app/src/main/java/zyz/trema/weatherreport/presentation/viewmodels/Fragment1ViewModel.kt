package zyz.trema.weatherreport.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zyz.trema.weatherreport.base.BaseMVIViewModel
import zyz.trema.weatherreport.base.MVI
import zyz.trema.weatherreport.base.None
import zyz.trema.weatherreport.domain.interactors.FetchWeatherInCityUseCase
import javax.inject.Inject

@HiltViewModel
class Fragment1ViewModel @Inject constructor(
    private val fetchWeatherInCityUseCase : FetchWeatherInCityUseCase
) : BaseMVIViewModel<Fragment1ViewModel.UIState, Fragment1ViewModel.UserAction>(
    UIState.emptyState
){

    data class UIState(
        val isLoading: Boolean,
        val errorEvent: Throwable?,
        val data: None?
    ) : MVI.State {
        companion object {
            val emptyState: UIState = UIState(
                isLoading = false,
                errorEvent = null,
                data = null
            )
        }
    }

    sealed class UserAction : MVI.Intent

    init {
        load()
    }

    override suspend fun processIntent(intent: UserAction) {}

    private fun load() {
        viewModelScope.launch {
            publishState { copy(isLoading = true, errorEvent = null) }
            fetchWeatherInCityUseCase(FetchWeatherInCityUseCase.Param()).also { result ->
                publishState { copy(isLoading = false) }
                if (result.hasFailed()) {
                    publishState { copy(errorEvent = result.failure()) }
                } else {
                    publishState { copy(data = result.get(), errorEvent = null) }
                }
            }
        }
    }

}