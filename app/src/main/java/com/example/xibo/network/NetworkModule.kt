package com.example.xibo.network

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.xibo.BuildConfig
import com.example.xibo.HeaderConstants
import com.example.xibo.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //for retrofit
    @Singleton
    @Provides
    fun provideOkHttp() : OkHttpClient{
        return OkHttpClient.Builder()
            .build()
    }


    @AppContext
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideSharedPref(@AppContext context: Context): SharedPref {
        return SharedPref(context)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        //prod:  http://nej-app1.corporate.nejinc.com/WMS_Mobile_WebAPI/mobile/
        //UAT:  http://nej-dev.corporate.nejinc.com/wms_mobile_webapi_dev/mobile/
        return Retrofit.Builder()
            .baseUrl(HeaderConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getUnsafeOkHttpClient()!!)
            .build()
    }

    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiService {
        return retrofit
            .create(ApiService::class.java)
    }


    private fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                @SuppressLint("CustomX509TrustManager")
                object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.writeTimeout(2, TimeUnit.MINUTES)
            builder.connectTimeout(2, TimeUnit.MINUTES)
            builder.readTimeout(2, TimeUnit.MINUTES)
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
            val okHttpClient: OkHttpClient
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
                okHttpClient = builder.build()
            } else {
                okHttpClient = builder
//                    .addInterceptor(provideAuthenticationInterceptor())
                    .build()
//                val interceptor = HttpLoggingInterceptor()
//                interceptor.level = HttpLoggingInterceptor.Level.BODY
//                builder.addInterceptor(interceptor)
//                okHttpClient = builder.addInterceptor(interceptor).build()
            }
            okHttpClient
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}