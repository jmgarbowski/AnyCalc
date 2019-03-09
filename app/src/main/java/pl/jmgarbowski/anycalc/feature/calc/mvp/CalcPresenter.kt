package pl.jmgarbowski.anycalc.feature.calc.mvp

import pl.jmgarbowski.anycalc.feature.calc.evaluation.Calculator
import timber.log.Timber
import java.lang.StringBuilder
import javax.inject.Inject

class CalcPresenter @Inject constructor(private val calculator: Calculator) : CalcMVP.Presenter {

    companion object {
        private const val equationMaxLength: Int  = 16
        //operators and their alternatives
        private val operators: Array<Char> = arrayOf('+', '-', '*', '\u00D7', '/', '\u00F7')
    }

    private var view: CalcMVP.View? = null
    private var calculatorSb: StringBuilder = StringBuilder(equationMaxLength)
    private var displaySb: StringBuilder = StringBuilder(equationMaxLength)

    /**
     * CalcMVP.Presenter
     */
    override fun keyClick(char: Char) {
        val lastElement = displaySb.length - 1
        if (isOperator(char)) {
            if (displaySb.isEmpty()) return
            if (displaySb.isNotEmpty() && isOperator(displaySb.elementAt(lastElement))) {
                    displaySb.deleteCharAt(lastElement)
            }
            when (char) {
                '*' -> displaySb.append('\u00D7')
                '/' -> displaySb.append('\u00F7')
                else -> displaySb.append(char)
            }
            calculatorSb.append(char)
        } else {
            displaySb.append(char)
            calculatorSb.append(char)
        }

        view?.displayEquation(displaySb.toString())
            .also { Timber.d("Equation: $displaySb.toString()") }
    }

    override fun equalSignClick() {
        view?.displayResult(calculator.evaluate(calculatorSb.toString()))
    }

    override fun eraseClick() {
        view?.displayEquation(
            if (displaySb.isNotEmpty())
                displaySb.deleteCharAt(displaySb.length - 1).toString()
            else ""
        )
    }

    //Clear all rows
    override fun erasePress() {
        view?.displayEquation(displaySb.clear().toString())
        view?.displayResult("")
    }

    /**
     * BasePresenter
     */
    override fun bind(view: CalcMVP.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun isViewBound(): Boolean {
        return this.view != null
    }

    private fun isOperator(char: Char) : Boolean {
        return operators.contains(char)
    }
}