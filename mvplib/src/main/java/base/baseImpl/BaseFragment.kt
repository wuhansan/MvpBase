package base.baseImpl

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import base.BasePresenter
import base.BaseView


abstract class BaseFragment<P : BasePresenter> : Fragment(), BaseView {

    private var presenter: P? = null
    private var isViewCreate = false//view是否创建
    private var isViewVisible = false//view是否可见
    var mContext: Context? = null
    private var isFirst = true//是否第一次加载
    private var mRootView: View? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = initPresenter()
    }


    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewCreate = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewVisible = isVisibleToUser
        if (isVisibleToUser && isViewCreate) {
            visibleToUser()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isViewVisible) {
            visibleToUser()
        }
    }

    /**
     * 懒加载
     * 让用户可见
     * 第一次加载
     */
    protected fun firstLoad() {

    }

    /**
     * 懒加载
     * 让用户可见
     */
    protected fun visibleToUser() {
        if (isFirst) {
            firstLoad()
            isFirst = false
        }
    }


    override fun onDestroyView() {
        if (presenter != null) {
            presenter!!.detach()
        }
        isViewCreate = false
        super.onDestroyView()
    }

    abstract fun initPresenter(): P


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(setLayout(), container, false)//和 BaseActivity 一样，layout() 由子类实现，提供布局。
        }


        mRootView?.let { init(it, savedInstanceState) }

        return mRootView
    }

    protected abstract fun init(mRootView: View, savedInstanceState: Bundle?)

    abstract fun setLayout(): Int   //提供给子类实现的抽象类


}
