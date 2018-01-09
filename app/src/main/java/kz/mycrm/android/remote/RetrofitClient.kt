package kz.mycrm.android.remote

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

    private var listener: OnConnectionTimeoutListener? = null
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {

        if(retrofit == null) {
            val httpClient = OkHttpClient.Builder()
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Logger.api(message) })
//            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(TokenInterceptor())
            httpClient.addInterceptor(logger)
            //        httpClient.interceptors().add(Interceptor { chain -> onOnIntercept(chain) })
            //        httpClient.connectTimeout(30, TimeUnit.SECONDS)
            //        httpClient.readTimeout(30, TimeUnit.SECONDS)

            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()
        }

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

