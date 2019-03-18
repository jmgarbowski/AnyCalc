package pl.jmgarbowski.anycalc.base.di.component

import dagger.Component
import pl.jmgarbowski.anycalc.main.di.module.MainActivityModule
import pl.jmgarbowski.anycalc.base.di.module.ApplicationModule
import pl.jmgarbowski.anycalc.base.di.module.SchedulerModule
import pl.jmgarbowski.anycalc.base.di.scope.Application
import pl.jmgarbowski.anycalc.main.di.component.MainActivityComponent

@Component(
    modules = [
        ApplicationModule::class,
        SchedulerModule::class
    ]
)
@Application
interface ApplicationComponent {
    fun mainActivityComponent(moduleMain: MainActivityModule): MainActivityComponent
}