package edu.sena.caribeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Módulo de Hilt para proporcionar dependencias relacionadas con la red.
 * Se instala en SingletonComponent, lo que significa que las dependencias
 * proporcionadas aquí serán singletons a nivel de aplicación.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // URL base de tu API. Para el emulador de Android, localhost es 10.0.2.2
    private const val BASE_URL = "http://10.0.2.2:5100/"

    /**
     * Provee una instancia de HttpLoggingInterceptor para logging de peticiones HTTP.
     * Útil para depuración.
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logea el cuerpo de la petición y respuesta
        }
    }

    /**
     * Provee una instancia de OkHttpClient.
     * Configura el cliente HTTP con el interceptor de logging.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Añade el interceptor de logging
            // Puedes añadir otros interceptores aquí, como para autenticación
            .build()
    }

    /**
     * Provee una instancia de GsonConverterFactory.
     * Se usa para convertir objetos Kotlin a JSON y viceversa.
     */
    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    /**
     * Provee una instancia de Retrofit.
     * Configura Retrofit con la URL base, el cliente OkHttp y el convertidor Gson.
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // Establece la URL base de la API
            .client(okHttpClient) // Asigna el cliente OkHttp configurado
            .addConverterFactory(gsonConverterFactory) // Añade el convertidor JSON
            .build()
    }
}
