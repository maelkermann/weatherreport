package zyz.trema.weatherreport.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import zyz.trema.weatherreport.base.BaseUseCase
import zyz.trema.weatherreport.base.Completion
import zyz.trema.weatherreport.base.IoDispatcher
import zyz.trema.weatherreport.base.None
import zyz.trema.weatherreport.domain.repository.OpenweathermapRepository
import javax.inject.Inject

class FetchWeatherInCityUseCase @Inject constructor(
    private val repository: OpenweathermapRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : BaseUseCase<FetchWeatherInCityUseCase.Param, None>(dispatcher) {

    override suspend fun execute(param: Param): Completion<None> = repository.get()

    data class Param(
        val city: String = "Paris"
    )
}