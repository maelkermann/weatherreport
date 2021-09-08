package zyz.trema.weatherreport.base

// Default completion
public class CompletionThrowable(message: String) : Throwable(message)
open class ResponseThrowable(val code: Int, message: String) : Throwable(message)