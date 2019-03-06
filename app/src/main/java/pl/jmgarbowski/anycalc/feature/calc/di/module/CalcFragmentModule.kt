package pl.jmgarbowski.anycalc.feature.calc.di.module

import dagger.Binds
import dagger.Module
import pl.jmgarbowski.anycalc.base.di.scope.Fragment
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Calculator
import pl.jmgarbowski.anycalc.feature.calc.evaluation.RPNCalculator

@Module
abstract class CalcFragmentModule {

    @Binds
    @Fragment
    abstract fun provideCalculator(calculator: RPNCalculator): Calculator

}