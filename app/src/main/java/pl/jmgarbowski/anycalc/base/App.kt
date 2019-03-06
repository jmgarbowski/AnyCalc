package pl.jmgarbowski.anycalc.base

import android.app.Application
import android.content.Context
import pl.jmgarbowski.anycalc.BuildConfig
import pl.jmgarbowski.anycalc.base.di.component.ApplicationComponent
import pl.jmgarbowski.anycalc.base.di.component.DaggerApplicationComponent
import timber.log.Timber

class App : Application() {

    companion object {
        private val appComponent: ApplicationComponent by lazy {
            DaggerApplicationComponent.builder()
                .build()
        }

        fun getApplicationComponent(applicationContext: Context): ApplicationComponent =
            if (applicationContext is Application) {
                appComponent
            } else {
                throw IllegalArgumentException("Given context is not application context.")
            }
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}