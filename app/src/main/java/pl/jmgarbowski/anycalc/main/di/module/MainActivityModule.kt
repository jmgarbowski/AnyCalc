package pl.jmgarbowski.anycalc.main.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.jmgarbowski.anycalc.base.di.scope.Activity

@Module
class MainActivityModule(private val context: Context) {
    @Provides
    @Activity
    fun provideContext(): Context = context
}