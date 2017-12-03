package kz.mycrm.android.remote

import kz.mycrm.android.util.LiveDataCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

/**
 * Created by NKabylbay on 11/11/2017.
 */
object RetrofitClient {

    private lateinit var httpClient: OkHttpClient.Builder
    private var listener: OnConnectionTimeoutListener? = null
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {

        httpClient = OkHttpClient.Builder()
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logger)
//        httpClient.interceptors().add(Interceptor { chain -> onOnIntercept(chain) })
//        httpClient.connectTimeout(30, TimeUnit.SECONDS)
//        httpClient.readTimeout(30, TimeUnit.SECONDS)

        if(retrofit == null)
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()

        return retrofit!!
    }

    fun setConnectionTimeoutListener(listener: OnConnectionTimeoutListener) {
        this.listener = listener
    }

//    private fun onOnIntercept(chain: Interceptor.Chain): Response {
//        try {
//            val response = chain.proceed(chain.request())
//            return response.newBuilder()
//                    .body(ResponseBody.create(response.body()?.contentType(), response.message())).build()
//        } catch (exception: SocketTimeoutException) {
//            exception.printStackTrace()
//            listener?.onConnectionTimeout()
//        } catch (exception: SocketException) {
//            exception.printStackTrace()
//            listener?.onConnectionTimeout()
//        }
//        return chain.proceed(chain.request())
//    }
}

