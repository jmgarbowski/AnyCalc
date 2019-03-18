package pl.jmgarbowski.anycalc.feature.calc.evaluation

interface Calculator {
    fun evaluate(input: String) : Result
}