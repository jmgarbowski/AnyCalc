package pl.jmgarbowski.anycalc

import org.junit.Test
import org.junit.Assert.*
import pl.jmgarbowski.anycalc.feature.calc.evaluation.RPNCalculator


/**
 * Tests for RPN Calculator
 */
class CalculatorTest {

    private val calculator = RPNCalculator()

    @Test
    fun testSimpleAddition() {
        assertEquals("4.0", calculator.evaluate("2+2"))
    }

    @Test
    fun testWhiteSpace() {
        assertEquals("4.0", calculator.evaluate("2 +2"))
    }

    @Test
    fun testSimpleDivision() {
        assertEquals("2.0", calculator.evaluate("4/2"))
    }

    @Test
    fun testDivisionZero() {
        assertEquals(RPNCalculator.divideZeroError, calculator.evaluate("1000/0"))
    }

    @Test
    fun testSimpleMultiply() {
        assertEquals("16.0", calculator.evaluate("4*4"))
    }

    @Test
    fun testSimpleFloat() {
        assertEquals("1.5", calculator.evaluate("3/2"))
    }

    @Test
    fun testSimpleParenthesis() {
        assertEquals("10.0", calculator.evaluate("(2+3)*2"))
    }

    @Test
    fun testErrorMessage() {
        assertEquals(RPNCalculator.unsupportedEquation, calculator.evaluate(")+5*22+1"))
    }

    @Test
    fun testErrorMessage2() {
        assertEquals(RPNCalculator.unsupportedEquation, calculator.evaluate("(a+b)*2/1200"))
    }

    @Test
    fun testErrorMessage3() {
        assertEquals(RPNCalculator.unsupportedEquation, calculator.evaluate("2,5+1"))
    }

    @Test
    fun testComplex() {
        assertEquals("17.704800000000002", calculator.evaluate("(2.66*3.14)*(4/2)+1"))
    }

    @Test
    fun testComplex2() {
        assertEquals("-6.84", calculator.evaluate("(2.750+1.33)*(4/2)-15"))
    }

    @Test
    fun testComplex3() {
        assertEquals("-9.59", calculator.evaluate("2.750+1.33*(4/2)-15"))
    }

    @Test
    fun testComplex4() {
        assertEquals("160.46", calculator.evaluate("2.750+1.33*((4/2)-15)+200-10*2.5"))
    }

    @Test
    fun testOverflow() {
        assertEquals("1.8E7", calculator.evaluate("9000000+9000000"))
    }

    @Test
    fun testComplex5() {
        assertEquals("-1951.7875", calculator.evaluate("3.14*((2/8)-15)*2.5-12*(150+21/7)"))
    }

    @Test
    fun testComplex6() {
        assertEquals("48005.0", calculator.evaluate("(100/2)/5/2+1000*(12*4)"))
    }

    @Test
    fun testComplex7() {
        assertEquals("-8632.648648648648", calculator.evaluate("(120/2)/5/2+10-30*20*(12*4)/3.33"))
    }

    @Test
    fun testComplex8() {
        assertEquals("1646.6955266955263", calculator.evaluate("2*45*(233/(33*2)-2+34.55*(12+12+12+14))/189*2"))
    }

    @Test
    fun testErrorMessage4() {
        assertEquals(RPNCalculator.unsupportedEquation, calculator.evaluate(""))
    }

}