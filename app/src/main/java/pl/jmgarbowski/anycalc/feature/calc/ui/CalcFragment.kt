package pl.jmgarbowski.anycalc.feature.calc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.*
import pl.jmgarbowski.anycalc.R
import pl.jmgarbowski.anycalc.feature.calc.mvp.CalcMVP
import pl.jmgarbowski.anycalc.main.di.MainActivityInjector
import timber.log.Timber
import javax.inject.Inject

class CalcFragment : Fragment(), CalcMVP.View {

    @Inject
    lateinit var presenter: CalcMVP.Presenter

    @BindView(R.id.calc_display_equation)
    lateinit var equationText: TextView

    @BindView(R.id.calc_display_result)
    lateinit var resultText: TextView

    private lateinit var unbinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calc, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unbinder = ButterKnife.bind(this, view)
        presenter.bind(this)
        Timber.d("View created")
    }

    override fun onStart() {
        super.onStart()
        if (!presenter.isViewBound()) {
            presenter.bind(this)
        }
    }

    override fun onDestroyView() {
        if (presenter.isViewBound()) {
            presenter.unbind()
        }
        super.onDestroyView()
        unbinder.unbind()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.unbind()
        super.onSaveInstanceState(outState)
    }

    override fun displayEquation(equation: String) {
        equationText.text = equation
    }

    override fun displayResult(result: String) {
        resultText.text = result
    }

    override fun displayError(error: String) {
        resultText.text = error
    }

    @OnClick(
        R.id.keypad_0, R.id.keypad_1, R.id.keypad_2, R.id.keypad_3,
        R.id.keypad_4, R.id.keypad_5, R.id.keypad_6, R.id.keypad_7,
        R.id.keypad_8, R.id.keypad_9, R.id.keypad_comma, R.id.keypad_plus,
        R.id.keypad_minus, R.id.keypad_multiply, R.id.keypad_division,
        R.id.keypad_left_p, R.id.keypad_right_p)
    fun onKeypadClick(view: View) {
        Timber.d("On keypad clicked")
        when(view.id) {
            R.id.keypad_0 -> presenter.keyClick('0')
            R.id.keypad_1 -> presenter.keyClick('1')
            R.id.keypad_2 -> presenter.keyClick('2')
            R.id.keypad_3 -> presenter.keyClick('3')
            R.id.keypad_4 -> presenter.keyClick('4')
            R.id.keypad_5 -> presenter.keyClick('5')
            R.id.keypad_6 -> presenter.keyClick('6')
            R.id.keypad_7 -> presenter.keyClick('7')
            R.id.keypad_8 -> presenter.keyClick('8')
            R.id.keypad_9 -> presenter.keyClick('9')
            R.id.keypad_comma -> presenter.keyClick('.')
            R.id.keypad_plus -> presenter.keyClick('+')
            R.id.keypad_minus -> presenter.keyClick('-')
            R.id.keypad_multiply -> presenter.keyClick('*')
            R.id.keypad_division -> presenter.keyClick('/')
            R.id.keypad_left_p -> presenter.keyClick('(')
            R.id.keypad_right_p -> presenter.keyClick(')')
        }
    }

    @OnClick(R.id.keypad_equal)
    fun onEqualSignClick() {
        presenter.equalSignClick()
    }

    @OnClick(R.id.keypad_e)
    fun onKeypadEraseClick() {
        presenter.eraseClick()
    }

    @OnLongClick(R.id.keypad_e)
    fun onKeypadErasePress(): Boolean {
        presenter.erasePress()
        return true
    }

}

fun CalcFragment.inject() {
    MainActivityInjector.get(requireContext()).mainActivityComponent
        .calcFragmentComponent()
        .inject(this)
}