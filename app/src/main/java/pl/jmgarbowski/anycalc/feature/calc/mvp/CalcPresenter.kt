package pl.jmgarbowski.anycalc.feature.calc.mvp

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Calculator
import timber.log.Timber
import java.lang.StringBuilder
import javax.inject.Inject

class CalcPresenter @Inject constructor(private val calculator: Calculator) : CalcMVP.Presenter {

    companion object {
        private const val equationMaxLength: Int  = 32
        //operators and their alternatives
        private val operators: Array<Char> = arrayOf('+', '-', '*', '\u00D7', '/', '\u00F7')
    }

    private val calculationRelay = PublishRelay.create<String>()
    private var view: CalcMVP.View? = null
    private var equationSb: StringBuilder = StringBuilder(equationMaxLength)
    private var commaLocked: Boolean = false //helper flag to decide comma char should be append to builder

    init {
        observeAnswer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                view?.displayResult(it)
            }
    }

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
                commaLocked = false
            }
            isComma(char) -> {
                if (equationSb.isEmpty()
                    || isLastItemComma()
                    || isLastItemOperator()
                    || commaLocked) return
                    equationSb.append(char)
                commaLocked = true
            }
            else -> {
                equationSb.append(char)
            }
        }

        view?.displayEquation(equationSb.toString())
            .also { Timber.d("Equation: $equationSb.toString()") }
    }

    override fun equalSignClick() {
        calculationRelay.accept(equationSb.toString())
    }

    override fun eraseClick() {
        removeLastElement()
        view?.displayEquation(equationSb.toString())
    }

    //Clear all rows
    override fun erasePress() {
        view?.displayEquation(equationSb.clear().toString())
        view?.displayResult("")
        commaLocked = false
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

    private fun observeAnswer(): Observable<String>
            = calculationRelay.map { calculator.evaluate(it) }

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
        if (equationSb.isNotEmpty()) {
            if (isLastItemComma()) commaLocked = false
            equationSb.deleteCharAt(equationSb.length - 1)
        }
    }

}