package retroft

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

interface BaseApi {
    fun getRetrofit(): Retrofit

    fun setInterceptor(interceptor: Interceptor): OkHttpClient.Builder

    fun setConverterFactory(factory: Converter.Factory): Retrofit.Builder
}