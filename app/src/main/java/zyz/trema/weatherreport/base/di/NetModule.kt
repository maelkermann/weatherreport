package zyz.trema.weatherreport.base.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import zyz.trema.weatherreport.BuildConfig
import zyz.trema.weatherreport.domain.service.OpenweathermapApiService

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    fun providesApi(retrofit: Retrofit) = retrofit.create(OpenweathermapApiService::class.java)

    @Provides
    fun providesRetrofit(@ApplicationContext context: Context, okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }

    @Provides
    fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if ( BuildConfig.DEBUG ) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

}