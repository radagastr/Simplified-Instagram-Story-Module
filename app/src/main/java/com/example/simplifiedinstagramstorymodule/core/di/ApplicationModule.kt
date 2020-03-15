package com.example.simplifiedinstagramstorymodule.core.di

import android.content.Context
import com.example.simplifiedinstagramstorymodule.BuildConfig
import com.example.simplifiedinstagramstorymodule.core.StoryModuleApplication
import com.example.simplifiedinstagramstorymodule.core.repository.CoreRepository
import com.example.simplifiedinstagramstorymodule.core.utilities.Constants
import com.example.simplifiedinstagramstorymodule.core.utilities.DynamicEndPointInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Suppress("TooManyFunctions")
@Module
class ApplicationModule(private val application: StoryModuleApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideInterceptor(): DynamicEndPointInterceptor = DynamicEndPointInterceptor()

    @Provides
    @Singleton
    @Named(Constants.REGULAR_RETROFIT)
    fun provideRetrofit(interceptor: DynamicEndPointInterceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASEURL)
            .client(createClient(interceptor))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(dynamicEndPointInterceptor: DynamicEndPointInterceptor): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        //okHttpClientBuilder.addInterceptor(dynamicEndPointInterceptor)
        /*
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

         */
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideCoreRepository(dataSource: CoreRepository.Network): CoreRepository =
        dataSource

}