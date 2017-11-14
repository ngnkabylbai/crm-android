package kz.mycrm.android.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import kz.mycrm.android.util.LiveDataCallAdapter
import kz.mycrm.android.util.LiveDataCallAdapterFactory

/**
 * Created by NKabylbay on 11/11/2017.
 */
object RetrofitClient {

    var retrofit: Retrofit? = null
    var httpClient = OkHttpClient.Builder()

    fun getClient(baseUrl: String): Retrofit {
        val logger = HttpLoggingInterceptor()
        httpClient.addInterceptor(logger)

        logger.level = HttpLoggingInterceptor.Level.BODY

        if(retrofit == null)
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()

        return retrofit!!
    }
}

