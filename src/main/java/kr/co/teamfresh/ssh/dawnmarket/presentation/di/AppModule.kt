package kr.co.teamfresh.ssh.dawnmarket.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.teamfresh.ssh.dawnmarket.BuildConfig
import kr.co.teamfresh.ssh.dawnmarket.data.network.ApiService
import kr.co.teamfresh.ssh.dawnmarket.data.repositoryimpl.CategoryRepositoryImpl
import kr.co.teamfresh.ssh.dawnmarket.domain.repository.CategoryRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Singleton
    @Binds
    abstract fun bindCategoryListRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ):CategoryRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun proviceOkHttpClient(): OkHttpClient{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }

}