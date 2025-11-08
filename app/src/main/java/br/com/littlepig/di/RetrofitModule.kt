package br.com.littlepig.di

import br.com.littlepig.data.UserService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val baseUrl = "http://localhost:3333/"
private const val DURATION = 15L

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    fun providesClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().readTimeout(DURATION, TimeUnit.SECONDS).addInterceptor(
            httpLoggingInterceptor
        ).build()

    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().setLenient().setDateFormat("dd/MM/yyyy").create()
    }

    @Provides
    fun providesGsonConverter(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun initRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): UserService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(UserService::class.java)
    }
}
