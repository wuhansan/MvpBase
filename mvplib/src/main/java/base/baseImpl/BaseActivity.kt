package base.baseImpl

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import base.BasePresenter
import base.BaseView
import utils.ActivityControlManager


abstract class BaseActivity<P : BasePresenter> : AppCompatActivity(), BaseView {


    protected var presenter: P? = null
    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        ActivityControlManager().getAppInstance().addActivity(this)//将当前activity添加进入管理栈
        presenter = initPresenter()

    }

    override fun onDestroy() {
        ActivityControlManager().getAppInstance().removeActivity(this)//将当前activity移除管理栈
        if (presenter != null) {
            presenter!!.detach()//在presenter中解绑释放view
            presenter = null
        }
        super.onDestroy()
    }


    /**
     * 在子类中初始化对应的presenter
     *
     * @return 相应的presenter
     */
    abstract fun initPresenter(): P


    override fun dismissLoadingDialog() {

    }

    override fun showLoadingDialog(msg: String) {

    }


}