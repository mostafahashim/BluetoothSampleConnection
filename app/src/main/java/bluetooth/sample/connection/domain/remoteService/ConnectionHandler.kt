package bluetooth.sample.connection.domain.remoteService

import bluetooth.sample.connection.BuildConfig
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ConnectionHandler {
    companion object {
        fun getInstance(): ConnectionHandler = ConnectionHandler()
    }

    private var retrofit: Retrofit? = null

    fun getClient(isUseBaseUrlPref: Boolean): Retrofit? {
        var baseUrl = BuildConfig.BASE_URL
        if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl.plus("/")
        }
        retrofit = Retrofit.Builder().baseUrl(baseUrl).client(
            getUnsafeOkHttpClient()
                .connectionSpecs(
                    listOf(
                        ConnectionSpec.COMPATIBLE_TLS,
                        ConnectionSpec.CLEARTEXT,
                        ConnectionSpec.MODERN_TLS
                    )
                )
                .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
                .protocols(listOf(Protocol.HTTP_1_1))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.MINUTES)
                .retryOnConnectionFailure(false)
                .build()
        )
            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit
    }

    internal fun getClientWithCustomUrl(baseUrl: String): Retrofit? {
        retrofit = Retrofit.Builder().baseUrl(baseUrl).client(
            getUnsafeOkHttpClient()
                .connectionSpecs(
                    listOf(
                        ConnectionSpec.COMPATIBLE_TLS,
                        ConnectionSpec.CLEARTEXT,
                        ConnectionSpec.MODERN_TLS
                    )
                )
                .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
                .protocols(listOf(Protocol.HTTP_1_1))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.MINUTES)
                .retryOnConnectionFailure(false)
                .build()
        )
            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit
    }

    private fun getClientWithoutBaseUrl(): Retrofit? {
        retrofit = Retrofit.Builder().client(
            getUnsafeOkHttpClient()
                .connectionSpecs(
                    listOf(
                        ConnectionSpec.COMPATIBLE_TLS,
                        ConnectionSpec.CLEARTEXT,
                        ConnectionSpec.MODERN_TLS
                    )
                )
                .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
                .protocols(listOf(Protocol.HTTP_1_1))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.MINUTES)
                .retryOnConnectionFailure(false)
                .build()
        )
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { hostname, session -> true }
            builder.addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Connection", "close").build()
                chain.proceed(request)
            }
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}