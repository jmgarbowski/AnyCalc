package pl.jmgarbowski.anycalc.base.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.jmgarbowski.anycalc.base.di.qualifier.IOThreadScheduler
import pl.jmgarbowski.anycalc.base.di.qualifier.MainThreadScheduler
import pl.jmgarbowski.anycalc.base.di.scope.Application

@Module
class SchedulerModule {

    @Provides
    @Application
    @MainThreadScheduler
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Application
    @IOThreadScheduler
    fun provideIOThreadScheduler(): Scheduler = Schedulers.io()

}