package base

import io.reactivex.disposables.Disposable

 interface BasePresenter {
    //默认初始化
    fun start()

    //Activity关闭把view对象置空
    fun detach()

    //将网络请求的每一个disposable添加进入CompositeDisposable 退出时一并注销
    fun addDisposable(subscription: Disposable)

    //注销所有的请求
    fun unDisposable()


}