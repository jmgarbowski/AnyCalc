package pl.jmgarbowski.anycalc.main.di

import android.content.Context
import pl.jmgarbowski.anycalc.main.di.component.MainActivityComponent
import pl.jmgarbowski.anycalc.main.MainActivity

interface MainActivityInjector {
    companion object {
        fun get(context: Context) = if (context is MainActivity) {
            context
        } else {
            throw IllegalArgumentException("Activity injector is not ${MainActivity::class.java.simpleName} class.")
        }
    }

    val mainActivityComponent: MainActivityComponent
}