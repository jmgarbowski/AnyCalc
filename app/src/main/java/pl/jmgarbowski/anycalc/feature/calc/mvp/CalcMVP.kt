package pl.jmgarbowski.anycalc.feature.calc.mvp

interface CalcMVP {
    interface View {
        fun displayEquation(equation: String)
        fun displayResult(result: String)
    }
    interface Presenter {
        fun keyClick(char: Char)
        fun equalSignClick()
        fun eraseClick() //remove last char
        fun erasePress() //clear both rows
    }
}