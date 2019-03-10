package pl.jmgarbowski.anycalc

import org.junit.Test
import org.junit.Assert.*
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Operator

class OperatorTest {

    @Test
    fun returnPriorityTestChar() {
        assertEquals(1, Operator.getPriority('+'))
    }

    @Test
    fun returnPriorityTestString() {
        assertEquals(2, Operator.getPriority("*"))
    }

    @Test
    fun returnUnexpectedTest() {
        assertEquals(null, Operator.getPriority('$'))
    }

    @Test
    fun returnIsOperatorTest() {
        assertEquals(true, Operator.isOperator('/'))
    }

}