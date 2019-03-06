package pl.jmgarbowski.anycalc.main.di.component

import dagger.Subcomponent
import pl.jmgarbowski.anycalc.main.di.module.MainActivityModule
import pl.jmgarbowski.anycalc.base.di.scope.Activity
import pl.jmgarbowski.anycalc.feature.calc.di.component.CalcFragmentComponent
import pl.jmgarbowski.anycalc.main.MainActivity

@Subcomponent(
    modules = [
        MainActivityModule::class
    ]
)
@Activity
interface MainActivityComponent {
    fun calcFragmentComponent(): CalcFragmentComponent
    fun inject(mainActivity: MainActivity)
}