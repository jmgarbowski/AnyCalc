package pl.jmgarbowski.anycalc.feature.calc.di.module

import dagger.Binds
import dagger.Module
import pl.jmgarbowski.anycalc.base.di.scope.Fragment
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Calculator
import pl.jmgarbowski.anycalc.feature.calc.evaluation.RPNCalculator
import pl.jmgarbowski.anycalc.feature.calc.mvp.CalcMVP
import pl.jmgarbowski.anycalc.feature.calc.mvp.CalcPresenter

@Module
abstract class CalcFragmentModule {

    @Binds
    @Fragment
    abstract fun provideCalcPresenter(presenter: CalcPresenter): CalcMVP.Presenter

    @Binds
    @Fragment
    abstract fun provideCalculator(calculator: RPNCalculator): Calculator

}