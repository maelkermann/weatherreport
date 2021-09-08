package zyz.trema.weatherreport.domain.service

import retrofit2.Response
import retrofit2.http.GET
import zyz.trema.weatherreport.base.None

interface OpenweathermapApiService {

    @GET("data/2.5/forecast?appid=325ae576b8c79a44b37346b6da7b5075&q=Paris&units=metric&cnt=5")
    suspend fun get(): Response<None>

}
