package zyz.trema.weatherreport.base

public class Completion<out T> private constructor(
    private val result: T?,
    private val error: Throwable?
) {

    public companion object {

        public fun fail(error: Throwable): Completion<Nothing> = Completion(null, error)
        public fun fail(message: String): Completion<Nothing> = Completion(null, Throwable(message))
        public fun <T> success(data: T): Completion<T> = Completion(data, null)
    }

    public fun hasFailed(): Boolean = error != null

    public fun get(): T = result ?: throw CompletionThrowable("No result, error is $error")
    public fun failure(): Throwable = error ?: throw CompletionThrowable("No error, result is $result")
}
