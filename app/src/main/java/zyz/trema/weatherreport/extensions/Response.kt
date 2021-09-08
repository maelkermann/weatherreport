package zyz.trema.weatherreport.extensions

import retrofit2.Response
import zyz.trema.weatherreport.base.ResponseThrowable

fun <T> Response<T>.toThrowable(defaultMessage: String) = errorBody()?.string()?.let {
    ResponseThrowable(
        code = code(),
        message = defaultMessage
    )
} ?: ResponseThrowable(code(), defaultMessage)

