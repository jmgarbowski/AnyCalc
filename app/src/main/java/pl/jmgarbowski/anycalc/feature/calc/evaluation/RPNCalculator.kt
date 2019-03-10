package pl.jmgarbowski.anycalc.feature.calc.evaluation

import java.lang.NumberFormatException
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Reverse Polish Notation calculator
 */
class RPNCalculator @Inject constructor() : Calculator {

    companion object {
        //calc errors
        const val divideZeroError = "(Divide by zero!)"
        const val unsupportedEquation = "(Error)"
    }

    override fun evaluate(input: String): String {

        val stack: Stack<String> = Stack()
        val postfixEquation: Queue<String> = LinkedList<String>()
        var poll: String
        var firstNumber: Double
        var nextNumber: Double
        var result: Double = 0.0

        postfixEquation.addAll(convertToPostfix(input))

        while (!postfixEquation.isEmpty()) {
            poll = postfixEquation.poll()
            try {
                val num = poll.toDouble()
                stack.add(poll)
            } catch (e: NumberFormatException) {
                try {
                    firstNumber = stack.peek().toDouble()
                    stack.pop()
                    nextNumber = stack.peek().toDouble()
                    stack.pop()
                    when (poll) {
                        Operator.plus.toString() -> result = nextNumber + firstNumber
                        Operator.minus.toString() -> result = nextNumber - firstNumber
                        Operator.multiply.toString() -> result = nextNumber * firstNumber
                        Operator.division.toString() -> {
                            if (firstNumber == 0.0) {
                                return divideZeroError
                            } else {
                                result = nextNumber / firstNumber
                            }
                        }
                    }
                    stack.push(result.toString())
                } catch (e: EmptyStackException) {
                    return unsupportedEquation
                }

            }
        }

        return if (!stack.empty()) stack.peek()
        else unsupportedEquation
    }

    private fun convertToPostfix(infixInput: String): ArrayList<String> {
        val stack: Stack<String> = Stack()
        //separate tokens according to operators
        val tokenizer = StringTokenizer(infixInput, Operator.toString(), true)
        var postfix = ""
        try {
            while (tokenizer.hasMoreTokens()) {
                val token = tokenizer.nextToken()
                if( token == "+" || token == "*" || token == "-" || token == "/") {
                    while(!stack.empty() && priority(stack.peek()) >= priority(token))
                        postfix += stack.pop()  + " "
                    stack.push(token)
                }
                else if(token == "(") stack.push(token)
                else if(token == ")") {
                    while(stack.peek() != "(") postfix += stack.pop() + " "
                    stack.pop()
                }
                else postfix += token  + " "
            }
            while(!stack.empty()) postfix += stack.pop()  + " "
        } catch (e: EmptyStackException) {
            postfix = ""
        }

        val num = ArrayList<String>(postfix.split(" "))
        num.removeAll(Collections.singleton(""))

        return num
    }

    private fun priority(operator: String): Int {
        return when(operator) {
            "+", "-" -> 1
            "*", "/" -> 2
            else -> 0
        }
    }

}