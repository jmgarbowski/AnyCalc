package pl.jmgarbowski.anycalc

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Error
import pl.jmgarbowski.anycalc.feature.calc.evaluation.RPNCalculator
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Success

/**
 * Tests for RPN Calculator
 */

@RunWith(RobolectricTestRunner::class)
class CalculatorTest {

    val context = ApplicationProvider.getApplicationContext<Context>()

    private val calculator = RPNCalculator(context)

    @Test
    fun testSimpleAddition() {
        val result = calculator.evaluate("2+2") as Success
        assertEquals("4", result.resultMessage)
    }

    @Test
    fun testWhiteSpace() {
        val result = calculator.evaluate("2 +2") as Success
        assertEquals("4", result.resultMessage)
    }

    @Test
    fun testSimpleDivision() {
        val result = calculator.evaluate("4/2") as Success
        assertEquals("2", result.resultMessage)
    }

    @Test
    fun testDivisionZero() {
        val error = calculator.evaluate("1000/0") as Error
        assertEquals(context.getString(R.string.rpn_divide_by_zero), error.errorMessage)
    }

    @Test
    fun testSimpleMultiply() {
        val result = calculator.evaluate("4*4") as Success
        assertEquals("16", result.resultMessage)
    }

    @Test
    fun testSimpleFloat() {
        val result = calculator.evaluate("3/2") as Success
        assertEquals("1.5", result.resultMessage)
    }

    @Test
    fun testSimpleParenthesis() {
        val result = calculator.evaluate("(2+3)*2") as Success
        assertEquals("10", result.resultMessage)
    }

    @Test
    fun testErrorMessage() {
        val error = calculator.evaluate(")+5*22+1") as Error
        assertEquals(context.getString(R.string.rpn_unsupported_error), error.errorMessage)
    }

    @Test
    fun testErrorMessage2() {
        val error = calculator.evaluate("(a+b)*2/1200") as Error
        assertEquals(context.getString(R.string.rpn_unsupported_error), error.errorMessage)
    }

    @Test
    fun testErrorMessage3() {
        val error = calculator.evaluate("2,5+1") as Error
        assertEquals(context.getString(R.string.rpn_unsupported_error), error.errorMessage)
    }

    @Test
    fun testComplex() {
        val result = calculator.evaluate("(2.66*3.14)*(4/2)+1") as Success
        assertEquals("17.704800000000002", result.resultMessage)
    }

    @Test
    fun testComplex2() {
        val result = calculator.evaluate("(2.750+1.33)*(4/2)-15") as Success
        assertEquals("-6.84", result.resultMessage)
    }

    @Test
    fun testComplex3() {
        val result = calculator.evaluate("2.750+1.33*(4/2)-15") as Success
        assertEquals("-9.59", result.resultMessage)
    }

    @Test
    fun testComplex4() {
        val result = calculator.evaluate("2.750+1.33*((4/2)-15)+200-10*2.5") as Success
        assertEquals("160.46", result.resultMessage)
    }

    @Test
    fun testOverflow() {
        val result = calculator.evaluate("9000000+9000000") as Success
        assertEquals("18000000", result.resultMessage)
    }

    @Test
    fun testComplex5() {
        val result = calculator.evaluate("3.14*((2/8)-15)*2.5-12*(150+21/7)") as Success
        assertEquals("-1951.7875", result.resultMessage)
    }

    @Test
    fun testComplex6() {
        val result = calculator.evaluate("(100/2)/5/2+1000*(12*4)") as Success
        assertEquals("48005", result.resultMessage)
    }

    @Test
    fun testComplex7() {
        val result = calculator.evaluate("(120/2)/5/2+10-30*20*(12*4)/3.33") as Success
        assertEquals("-8632.648648648648", result.resultMessage)
    }

    @Test
    fun testComplex8() {
        val result = calculator.evaluate("2*45*(233/(33*2)-2+34.55*(12+12+12+14))/189*2") as Success
        assertEquals("1646.6955266955263", result.resultMessage)
    }

    @Test
    fun testErrorMessage4() {
        val error = calculator.evaluate("") as Error
        assertEquals(context.getString(R.string.rpn_unsupported_error), error.errorMessage)
    }

}