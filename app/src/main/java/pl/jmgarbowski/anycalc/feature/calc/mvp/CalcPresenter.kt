package pl.jmgarbowski.anycalc.feature.calc.mvp

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Calculator
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Error
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Operator
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Operator.Companion.division
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Operator.Companion.isOperator
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Operator.Companion.minus
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Operator.Companion.multiply
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Operator.Companion.unicode
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Operator.Companion.plus
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Result
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Success
import java.lang.StringBuilder
import javax.inject.Inject

class CalcPresenter @Inject constructor(private val calculator: Calculator) : CalcMVP.Presenter {

    companion object {
        private const val equationMaxLength: Int = 32
    }

    private val calculationRelay = PublishRelay.create<String>()
    private val compositeDisposable = CompositeDisposable()
    private var view: CalcMVP.View? = null
    private var equationSb: StringBuilder = StringBuilder(equationMaxLength)
    private var commaLocked: Boolean = false //helper flag to decide comma char should be append to builder
    private var lastResult: Result? = null

    /**
     * CalcMVP.Presenter
     */
    override fun keyClick(char: Char) {
        if (equationSb.length == equationMaxLength) return
        when {
            isOperator(char) -> {
                appendLastResult()
                if (equationSb.isEmpty() || isLastItemComma() || isLastItemLeftParenthesis()) return
                if (isLastItemOperator()) removeLastItemOperator()
                equationSb.append(char)
                commaLocked = false
            }
            isComma(char) -> {
                if (equationSb.isEmpty() || isLastItemComma() || isLastItemOperator() || isLastItemParenthesis() || commaLocked) {
                    return
                }
                equationSb.append(char)
                commaLocked = true
            }
            else -> {
                removeLastResult()
                equationSb.append(char)
            }
        }

        view?.displayEquation(parseEquation(equationSb.toString()))
    }

    override fun equalSignClick() {
        if (equationSb.isNotEmpty()) calculationRelay.accept(equationSb.toString())
    }

    override fun eraseClick() {
        removeLastElement()
        view?.displayEquation(parseEquation(equationSb.toString()))
    }

    //Clear all rows
    override fun erasePress() = clearRows()

    /**
     * BasePresenter
     */
    override fun bind(view: CalcMVP.View) {
        this.view = view
        compositeDisposable += observeAnswer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                lastResult = it
                when (it) {
                    is Success -> {
                        view.displayResult(it.resultMessage)
                    }
                    is Error -> {
                        view.displayError(it.errorMessage)
                    }
                }
            }
    }

    override fun unbind() {
        this.view = null
        compositeDisposable.clear()
    }

    private fun observeAnswer(): Observable<Result> = calculationRelay.map { calculator.evaluate(it) }

    private fun isComma(char: Char): Boolean = char == Operator.comma

    private fun isParenthesis(char: Char): Boolean =
        char == Operator.leftParenthesis || char == Operator.rightParenthesis

    private fun isLeftParenthesis(char: Char): Boolean = char == Operator.leftParenthesis

    private fun isRightParenthesis(char: Char): Boolean = char == Operator.rightParenthesis

    private fun isLastItemOperator(): Boolean = (isOperator(equationSb.elementAt(equationSb.length - 1)))

    private fun isLastItemComma(): Boolean = (isComma(equationSb.elementAt(equationSb.length - 1)))

    private fun isLastItemParenthesis(): Boolean = (isParenthesis(equationSb.elementAt(equationSb.length - 1)))

    private fun isLastItemLeftParenthesis(): Boolean = (isLeftParenthesis(equationSb.elementAt(equationSb.length - 1)))

    private fun isLastItemRightParenthesis(): Boolean = isRightParenthesis(equationSb.elementAt(equationSb.length - 1))

    private fun removeLastItemOperator() {
        if (equationSb.isNotEmpty()) equationSb.deleteCharAt(equationSb.length - 1)
    }

    private fun removeLastElement() {
        if (equationSb.isNotEmpty()) {
            if (isLastItemComma()) commaLocked = false
            equationSb.deleteCharAt(equationSb.length - 1)
        }
        lastResult = null
    }

    private fun clearRows() {
        view?.displayEquation(equationSb.clear().toString())
        view?.displayResult("")
        commaLocked = false
    }

    private fun appendLastResult() {
        lastResult?.apply {
            clearRows()
            when (this) {
                is Success -> equationSb.append(resultMessage)
            }
        }
        lastResult = null
    }

    private fun removeLastResult() {
        lastResult?.apply { clearRows() }
        lastResult = null
    }

    /**
     * This function should be called every time when result row is updated
     */
    private fun parseEquation(equation: String): String {
        return equation
            .replace(plus, requireNotNull(unicode[plus]))
            .replace(minus, requireNotNull(unicode[minus]))
            .replace(multiply, requireNotNull(unicode[multiply]))
            .replace(division, requireNotNull(unicode[division]))
    }

}