package com.rongcom.newsapp.di.module

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.rongcom.newsapp.data.api.ApiKeyInterceptor
import com.rongcom.newsapp.data.api.NetworkService
import com.rongcom.newsapp.data.local.AppDatabaseService
import com.rongcom.newsapp.data.local.DatabaseService
import com.rongcom.newsapp.data.local.NewsAppDatabase
import com.rongcom.newsapp.di.BaseUrl
import com.rongcom.newsapp.di.DatabaseName
import com.rongcom.newsapp.di.NetworkAPIKey
import com.rongcom.newsapp.ui.utils.AppConstant
import com.rongcom.newsapp.ui.utils.DefaultDispatcherProvider
import com.rongcom.newsapp.ui.utils.DispatcherProvider
import com.rongcom.newsapp.ui.utils.NetworkHelper
import com.rongcom.newsapp.ui.utils.NetworkHelperImpl
import com.rongcom.newsapp.ui.utils.logger.AppLogger
import com.rongcom.newsapp.ui.utils.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
      @BaseUrl baseUrl: String,
      okHttpClient: OkHttpClient,
      gsonConverterFactory: GsonConverterFactory
    ): NetworkService{
       return Retrofit.Builder()
           .baseUrl(baseUrl)
           .client(okHttpClient)
           .addConverterFactory(gsonConverterFactory)
           .build()
           .create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor) : OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context) : NetworkHelper {
        return NetworkHelperImpl(context)
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@NetworkAPIKey apiKey: String) :  ApiKeyInterceptor {
        return ApiKeyInterceptor(apiKey)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideLogger() : Logger = AppLogger()

    @BaseUrl
    @Provides
    fun provideBaseurl() : String = AppConstant.BASE_URL

    @NetworkAPIKey
    @Provides
    fun provideApiKey() : String = AppConstant.API_KEY

    @Provides
    @Singleton
    fun provideDatabaseService(appDatabase: NewsAppDatabase) : DatabaseService {
        return AppDatabaseService(appDatabase)
    }

    @DatabaseName
    @Provides
    fun provideDatabaseName() : String = AppConstant.DATABASE_NAME

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ) : NewsAppDatabase{
        return Room.databaseBuilder(
            context,
            NewsAppDatabase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotificationWorker(
        @ApplicationContext context: Context
    ) : WorkManager{
       return WorkManager.getInstance(context)
    }
}