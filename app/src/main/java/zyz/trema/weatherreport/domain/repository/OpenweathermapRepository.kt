package zyz.trema.weatherreport.domain.repository

import zyz.trema.weatherreport.base.Completion
import zyz.trema.weatherreport.base.None
import zyz.trema.weatherreport.domain.service.OpenweathermapApiService
import zyz.trema.weatherreport.extensions.toThrowable
import javax.inject.Inject

class OpenweathermapRepository @Inject constructor(
    private val service: OpenweathermapApiService,
) {

    suspend fun get() = service.get().let { response ->
        if (response.isSuccessful) {
            Completion.success(response.body() ?: None())
        } else {
            Completion.fail(response.toThrowable("get failed"))
        }
    }

}