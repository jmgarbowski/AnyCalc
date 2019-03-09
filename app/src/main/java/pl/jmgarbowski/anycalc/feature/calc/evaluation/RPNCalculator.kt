package pl.jmgarbowski.anycalc.feature.calc.evaluation

import javax.inject.Inject

/**
 * Reverse Polish Notation calculator
 */
class RPNCalculator @Inject constructor() : Calculator {
    override fun evaluate(equation: String): String {
        //todo implement RPN algorithm
        return "kurłaa +$equation"
    }
}