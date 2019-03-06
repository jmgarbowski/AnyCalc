package pl.jmgarbowski.anycalc.feature.calc.mvp

import pl.jmgarbowski.anycalc.base.mvp.BasePresenter
import pl.jmgarbowski.anycalc.base.mvp.BaseView

interface CalcMVP {
    interface View : BaseView {
        fun displayEquation(equation: String)
        fun displayResult(result: String)
    }
    interface Presenter : BasePresenter<View> {
        fun keyClick(char: Char)
        fun equalSignClick()
        fun eraseClick() //remove last char
        fun erasePress() //clear both rows
    }
}