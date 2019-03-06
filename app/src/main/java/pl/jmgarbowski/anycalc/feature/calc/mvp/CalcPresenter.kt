package pl.jmgarbowski.anycalc.feature.calc.mvp

import pl.jmgarbowski.anycalc.feature.calc.evaluation.Calculator
import javax.inject.Inject

class CalcPresenter @Inject constructor(private val calculator: Calculator) : CalcMVP.Presenter {

    private var view: CalcMVP.View? = null

    override fun keyClick(char: Char) {

    }

    override fun equalSignClick() {

    }

    override fun eraseClick() {

    }

    override fun erasePress() {

    }

    //BasePresenter
    override fun bind(view: CalcMVP.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun isViewBound(): Boolean {
        return this.view != null
    }
}