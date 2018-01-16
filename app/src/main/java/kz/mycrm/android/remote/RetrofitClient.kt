package kz.mycrm.android.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kz.mycrm.android.BuildConfig
import kz.mycrm.android.util.Constants
import kz.mycrm.android.util.LiveDataCallAdapterFactory
import kz.mycrm.android.util.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by NKabylbay on 11/11/2017.
 */
object RetrofitClient {

    private val baseUrl: String = BuildConfig.DAR_API
    private var listener: OnConnectionTimeoutListener? = null
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        return retrofit ?: createClient()
    }

    fun setConnectionTimeoutListener(listener: OnConnectionTimeoutListener) {
        this.listener = listener
    }

    private fun createClient() : Retrofit {
        val httpClient = OkHttpClient.Builder()
        if(BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Logger.api(message) })
//            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logger)
        }
        httpClient.addInterceptor(TokenInterceptor())
        //        httpClient.interceptors().add(Interceptor { chain -> onOnIntercept(chain) })
        //        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        //        httpClient.readTimeout(30, TimeUnit.SECONDS)

        val gson = GsonBuilder()
                .setDateFormat(Constants.orderDateTimeFormat.toPattern())
                .create()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(httpClient.build())
                .build()
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

