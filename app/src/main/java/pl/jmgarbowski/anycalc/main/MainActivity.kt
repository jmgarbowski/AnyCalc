package pl.jmgarbowski.anycalc.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.jmgarbowski.anycalc.R
import pl.jmgarbowski.anycalc.base.App
import pl.jmgarbowski.anycalc.feature.calc.ui.CalcFragment
import pl.jmgarbowski.anycalc.main.di.component.MainActivityComponent
import pl.jmgarbowski.anycalc.main.di.module.MainActivityModule
import pl.jmgarbowski.anycalc.main.di.MainActivityInjector

class MainActivity : AppCompatActivity(), MainActivityInjector {

    override val mainActivityComponent: MainActivityComponent by lazy {
        App.getApplicationComponent(applicationContext)
            .mainActivityComponent(MainActivityModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityComponent.inject(this)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content, CalcFragment()).commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("fragment", true)
        super.onSaveInstanceState(outState)
    }

}
