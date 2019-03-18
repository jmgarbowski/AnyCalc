package pl.jmgarbowski.anycalc.base.mvp

interface BasePresenter<T : BaseView> {
    fun bind(view: T)
    fun unbind()
}