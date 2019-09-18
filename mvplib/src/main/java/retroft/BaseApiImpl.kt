package retroft

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class BaseApiImpl : BaseApi {

    @Volatile
    private var retrofit: Retrofit? = null

    private var retrofitBuilder = Retrofit.Builder()
    private var httpBuilder = OkHttpClient.Builder()


    fun BaseApiImpl(baseUrl: String) {
        retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(
                httpBuilder.addInterceptor(getLoggerInterceptor())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
//                        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    .addNetworkInterceptor(NetInterceptor())
                    .build()
            )
            .baseUrl(baseUrl)
//            .client(httpBuilder.addInterceptor())


    }

    private fun getLoggerInterceptor(): HttpLoggingInterceptor {
        var level = HttpLoggingInterceptor.Level.HEADERS
        var loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger() {
            @Override
            fun log(message: String) {
                Log.e("ApiUrl", "---->" + message)
            }
        });

        loggingInterceptor.setLevel(level)
        return loggingInterceptor;
    }

    class NetInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request().newBuilder()
                .addHeader("Connection", "close").build();
            return chain.proceed(request);
        }

    }


    override fun getRetrofit(): Retrofit {
        if (null == retrofit) {
            synchronized(lock = BaseApiImpl()) {
                if (null == retrofit) {
                    retrofit = retrofitBuilder.build()
                }
            }
        }
        return this!!.retrofit!!;
    }

    override fun setInterceptor(interceptor: Interceptor): OkHttpClient.Builder {
        return httpBuilder.addInterceptor(interceptor)
    }

    override fun setConverterFactory(factory: Converter.Factory): Retrofit.Builder {
        return retrofitBuilder.addConverterFactory(factory)
    }

}


