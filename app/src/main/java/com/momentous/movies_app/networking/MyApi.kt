package com.momentous.movies_app.networking

import com.fastorder.customer.networking.TLSSocketFactory
import com.google.gson.JsonObject
import com.momentous.movies_app.BuildConfig
import com.momentous.movies_app.entity.network.MoviesDetailsResponse
import com.momentous.movies_app.entity.network.MoviesResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by asifa on 2/12/2018.
 */

interface MyApi {


    @GET(WebServiceUrl.MOVIES)
    suspend fun getMovies(@QueryMap request: HashMap<String, String>): Response<MoviesResponse>

    @GET(WebServiceUrl.MOVIES+"/{id}")
    suspend fun getMovieDetails(@Path("id") id: String): Response<MoviesDetailsResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val tlsTocketFactory = TLSSocketFactory()

            val builder = OkHttpClient().newBuilder()
                .callTimeout(2, TimeUnit.MINUTES)
            builder.readTimeout(100, TimeUnit.SECONDS)
            builder.connectTimeout(100, TimeUnit.SECONDS)
            builder.sslSocketFactory(tlsTocketFactory, tlsTocketFactory.trustManager)
            builder.hostnameVerifier { hostname, session -> true }

            builder.addInterceptor(networkConnectionInterceptor)
            builder.addInterceptor(interceptor)

            builder.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    val request = chain.request().newBuilder()
                        .addHeader("api-key", BuildConfig.API_KEY)
                        .build()
                    return chain.proceed(request)
                }
            })
            val client = builder.build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build()
                .create(MyApi::class.java)
        }
    }

}
