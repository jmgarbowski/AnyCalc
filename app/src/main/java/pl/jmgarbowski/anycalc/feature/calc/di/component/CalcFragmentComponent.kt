package pl.jmgarbowski.anycalc.feature.calc.di.component

import dagger.Subcomponent
import pl.jmgarbowski.anycalc.base.di.scope.Fragment
import pl.jmgarbowski.anycalc.feature.calc.di.module.CalcFragmentModule
import pl.jmgarbowski.anycalc.feature.calc.ui.CalcFragment

@Subcomponent(
    modules = [
        CalcFragmentModule::class
    ]
)
@Fragment
interface CalcFragmentComponent {
    fun inject(calcFragment: CalcFragment)
}