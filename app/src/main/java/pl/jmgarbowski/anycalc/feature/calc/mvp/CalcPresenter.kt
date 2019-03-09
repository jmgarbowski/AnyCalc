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
    private var equationSb: StringBuilder = StringBuilder(equationMaxLength)

    /**
     * CalcMVP.Presenter
     */
    override fun keyClick(char: Char) {
        if (equationSb.length == equationMaxLength) return
        when {
            isOperator(char) -> {
                if (equationSb.isEmpty()
                    || isLastItemComma()) return
                if (isLastItemOperator()) removeLastItemOperator()
                equationSb.append(changeOperatorSymbol(char))
            }
            isComma(char) -> {
                if (equationSb.isEmpty()
                    || isLastItemComma()
                    || isLastItemOperator()) return
                    equationSb.append(char)
            }
            else -> {
                equationSb.append(char)
            }
        }

        view?.displayEquation(equationSb.toString())
            .also { Timber.d("Equation: $equationSb.toString()") }
    }

    override fun equalSignClick() {
        view?.displayResult(calculator.evaluate(equationSb.toString()))
    }

    override fun eraseClick() {
        removeLastElement()
        view?.displayEquation(equationSb.toString())
    }

    //Clear all rows
    override fun erasePress() {
        view?.displayEquation(equationSb.clear().toString())
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

    private fun isOperator(char: Char): Boolean {
        return operators.contains(char)
    }

    private fun isComma(char: Char): Boolean {
        return char == '.'
    }

    private fun changeOperatorSymbol(char: Char): Char {
        return when (char) {
            '*' -> '\u00D7'
            '/' -> '\u00F7'
            else -> char
        }
    }

    private fun isLastItemOperator(): Boolean {
        return (isOperator(equationSb.elementAt(equationSb.length - 1)))
    }

    private fun isLastItemComma(): Boolean {
        return (isComma(equationSb.elementAt(equationSb.length - 1)))
    }

    private fun removeLastItemOperator() {
        if (equationSb.isNotEmpty())
            equationSb.deleteCharAt(equationSb.length - 1)
    }

    private fun removeLastElement() {
        if (equationSb.isNotEmpty())
            equationSb.deleteCharAt(equationSb.length - 1)
    }

}