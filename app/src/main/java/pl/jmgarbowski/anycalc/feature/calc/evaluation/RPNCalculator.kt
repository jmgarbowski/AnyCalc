package pl.jmgarbowski.anycalc.feature.calc.evaluation

import android.content.Context
import pl.jmgarbowski.anycalc.R
import java.lang.NumberFormatException
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Reverse Polish Notation calculator
 */
class RPNCalculator @Inject constructor(private val context: Context) : Calculator {

    override fun evaluate(input: String): Result {

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
                                return Error(context.getString(R.string.rpn_divide_by_zero))
                            } else {
                                result = nextNumber / firstNumber
                            }
                        }
                    }
                    stack.push(result.toString())
                } catch (e: EmptyStackException) {
                    return Error(context.getString(R.string.rpn_unsupported_error))
                }

            }
        }

        return if (!stack.empty()) Success(stack.peek())
        else Error(context.getString(R.string.rpn_unsupported_error))
    }

    private fun convertToPostfix(infixInput: String): ArrayList<String> {
        val stack: Stack<String> = Stack()
        //separate tokens according to operators
        val tokenizer = StringTokenizer(infixInput, Operator.toString(), true)
        var postfix = ""
        try {
            while (tokenizer.hasMoreTokens()) {
                val token = tokenizer.nextToken()
                when (token) {
                    "+", "*", "-", "/" -> {
                        while(!stack.empty() && priority(stack.peek()) >= priority(token))
                            postfix += stack.pop()  + " "
                        stack.push(token)
                    }
                    "(" -> stack.push(token)
                    ")" -> {
                        while(stack.peek() != "(") postfix += stack.pop() + " "
                        stack.pop()
                    }
                    else -> postfix += token  + " "
                }
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