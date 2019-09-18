package retroft

import android.util.Log
import com.google.gson.JsonParseException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException


class ExceptionHelper {
    fun handleException(e: Throwable): String {
        e.printStackTrace()
        val error: String?
        if (e is SocketTimeoutException) {//网络超时
            error = "网络连接异常"
        } else if (e is ConnectException) { //均视为网络错误
            error = "网络连接异常"
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {   //均视为解析错误
            error = "数据解析异常"
        } else if (e is ApiException) {//服务器返回的错误信息
            error = e.cause?.message
        } else if (e is UnknownHostException) {
            error = "网络连接异常"
        } else if (e is IllegalArgumentException) {
            error = "下载文件已存在"
        } else {//未知错误
            try {
               Log.e("ExceptionHelper","错误: " + e.message)
            } catch (e1: Exception) {
                Log.e("ExceptionHelper","错误: 未知错误Debug调试" )
            }

            error = "未知错误"
        }
        return error!!
    }

}