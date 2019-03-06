package pl.jmgarbowski.anycalc.feature.calc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.jmgarbowski.anycalc.R
import pl.jmgarbowski.anycalc.feature.calc.evaluation.Calculator
import pl.jmgarbowski.anycalc.feature.calc.mvp.CalcMVP
import pl.jmgarbowski.anycalc.main.di.MainActivityInjector
import javax.inject.Inject

class CalcFragment : Fragment(), CalcMVP.View {

    @Inject
    lateinit var presenter: CalcMVP.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bind(this)
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.unbind()
        super.onSaveInstanceState(outState)
    }

    override fun displayEquation(equation: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayResult(result: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

fun CalcFragment.inject() {
    MainActivityInjector.get(requireContext()).mainActivityComponent
        .calcFragmentComponent()
        .inject(this)
}