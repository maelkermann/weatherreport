package zyz.trema.weatherreport.base.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

const val SCHEDULER_MAIN_THREAD = "mainThread"
const val SCHEDULER_IO = "io"

/**
 * Created by S.Nur Uysal on 2019-10-23.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
   @Provides
   @Named(SCHEDULER_MAIN_THREAD)
   fun provideAndroidMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

   @Provides
   @Named(SCHEDULER_IO)
   fun provideIoScheduler(): Scheduler = Schedulers.io()
}
