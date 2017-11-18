package kz.mycrm.android.remote

import kz.mycrm.android.util.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by NKabylbay on 11/11/2017.
 */
object RetrofitClient {

    var retrofit: Retrofit? = null
    lateinit var httpClient: OkHttpClient.Builder

    init {

    }

    fun getClient(baseUrl: String): Retrofit {

        httpClient = OkHttpClient.Builder()
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logger)


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

