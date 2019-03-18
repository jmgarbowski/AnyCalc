package pl.jmgarbowski.anycalc.feature.calc.evaluation

class Operator {
    companion object {
        const val leftParenthesis: Char = '('
        const val rightParenthesis: Char = ')'
        const val comma: Char = '.'
        const val plus: Char = '+'
        const val minus: Char = '-'
        const val multiply: Char = '*'
        const val division: Char = '/'

        //operators and their unicode
        val unicode: HashMap<Char, Char>
                = hashMapOf(
                    plus to '\u002B',
                    minus to '\u2212',
                    multiply to '\u00D7',
                    division to '\u00F7'
                )

        private val priority: HashMap<Char, Int>
                = hashMapOf(
                    plus to 1,
                    minus to 1,
                    multiply to 2,
                    division to 2
                )

        /**
         * Return priority of operator, value range 0..2
         */
        fun getPriority(operator: Char): Int = priority[operator] ?: 0

        /**
         * Return priority of operator, value range 0..2
         * Throws exception when string is longer than 1 or is empty
         */
        fun getPriority(operator: String): Int = priority[operator.single()] ?: 0

        /**
         * Return true if char is operator
         */
        fun isOperator(char: Char): Boolean = unicode.contains(char)

        /**
         * Return true if string is operator.
         * Throws exception when string is longer than 1 or is empty
         */
        fun isOperator(string: String): Boolean = unicode.contains(string.single())

        override fun toString(): String {
            return "+-*/()"
        }
    }
}