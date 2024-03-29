package utils

import android.app.Activity
import android.content.Context
import android.util.Log
import java.util.*


/**
 * @author  wuLiang
 * @description:
 * @date :2019/8/28 16:56
 */
class ActivityControlManager {

    private var activityStack: Stack<Activity>? = null
    private var instance: ActivityControlManager? = null

    constructor()


    /**
     * 单一实例
     */
    @Synchronized
    fun getAppInstance(): ActivityControlManager {
        if (instance == null) {
            instance = ActivityControlManager()
        }
        return instance as ActivityControlManager
    }

    /**
     * 添加Activity到堆栈
     */
    @Synchronized
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
        Log.i("TAG", "ActivityManager添加了：" + activity.javaClass.name)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity {
        return activityStack!!.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        finishActivity(activityStack!!.lastElement())
    }

    /**
     * 移除最后一个Activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            activityStack!!.remove(activity)
            Log.i("TAG", "ActivityManager移除了：" + activity.javaClass.name)
        }
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            activityStack!!.remove(activity)
            activity.finish()
            Log.i("TAG", "ActivityManager关闭了：" + activity.javaClass.name)
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (i in activityStack!!.indices) {
            if (activityStack!!.get(i).javaClass == cls) {
                finishActivity(activityStack!!.get(i))
                removeActivity(activityStack!!.get(i))
                return
            }
        }
    }


    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        for (activity in this!!.activityStack!!) {
            if (activity != null) {
                activity!!.finish()
            }
        }
        activityStack!!.clear()
    }


    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {
        try {
            finishAllActivity()
            val activityMgr = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            activityMgr.restartPackage(context.getPackageName())
            System.exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}