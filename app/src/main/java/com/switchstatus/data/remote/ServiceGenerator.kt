package com.switchstatus.data.remote

import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.switchstatus.APP_KEY
import com.switchstatus.BASE_URL_THINGWORX
import com.switchstatus.BuildConfig
import com.switchstatus.data.remote.moshiFactories.MyKotlinJsonAdapterFactory
import com.switchstatus.data.remote.moshiFactories.MyStandardJsonAdapters
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by AhmedEltaher
 */

private const val timeoutRead = 30   //In seconds
private const val appKey = "appKey"
private const val accept = "Accept"
private const val contentType = "Content-Type"
private const val contentTypeValue = "application/json"
private const val timeoutConnect = 30   //In seconds

@Singleton
class ServiceGenerator @Inject constructor() {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit

    private var headerInterceptor = Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
                .header(contentType, contentTypeValue)
                .header(accept, contentTypeValue)
                .header(appKey, APP_KEY)
                .method(original.method, original.body)
                .build()

        chain.proceed(request)
    }

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

    init {
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_THINGWORX).client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                        .disableHtmlEscaping().create()))
                .build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

    private fun getMoshi(): Moshi {
        return Moshi.Builder()
                .add(MyKotlinJsonAdapterFactory())
                .add(MyStandardJsonAdapters.FACTORY)
                .build()
    }
}
