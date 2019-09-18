package utils

import android.util.Log

/**
 * @author  wuLiang
 * @description:
 * @date :2019/8/28 17:00
 */
class LogUtils {

    private var className: String? = null//类名
    private var methodName: String? = null//方法名
    private var lineNumber: Int = 0//行数

    constructor()

    fun isDebuggable(): Boolean {
        return true
    }

    private fun createLog(log: String): String {
        val buffer = StringBuffer()
        buffer.append(methodName)
        buffer.append("(").append(className).append(":").append(lineNumber).append(")")
        buffer.append(log)
        return buffer.toString()
    }

    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }


    fun e(message: String) {
        if (!isDebuggable())
            return

        // Throwable instance must be created before any methods
        getMethodNames(Throwable().stackTrace)
        Log.e(className, createLog(message))
    }


    fun i(message: String) {
        if (!isDebuggable())
            return

        getMethodNames(Throwable().stackTrace)
        Log.i(className, createLog(message))
    }

    fun d(message: String) {
        if (!isDebuggable())
            return

        getMethodNames(Throwable().stackTrace)
        Log.d(className, createLog(message))
    }

    fun v(message: String) {
        if (!isDebuggable())
            return

        getMethodNames(Throwable().stackTrace)
        Log.v(className, createLog(message))
    }

    fun w(message: String) {
        if (!isDebuggable())
            return

        getMethodNames(Throwable().stackTrace)
        Log.w(className, createLog(message))
    }

    fun wtf(message: String) {
        if (!isDebuggable())
            return

        getMethodNames(Throwable().stackTrace)
        Log.wtf(className, createLog(message))
    }

}