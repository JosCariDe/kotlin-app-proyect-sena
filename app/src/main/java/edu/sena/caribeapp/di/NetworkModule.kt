// app/src/main/java/edu/sena/caribeapp/di/NetworkModule.kt
package edu.sena.caribeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.sena.caribeapp.data.auth.remote.AuthApi // ¡Nueva importación!
import edu.sena.caribeapp.data.estudiantes.remote.EstudiantesApi // ¡Nueva importación!
import edu.sena.caribeapp.data.preguntas.remote.PreguntasApi // ¡Nueva importación!
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

    private const val BASE_URL = "http://10.0.2.2:5100/"

    @Provides // Se coloca con @Provides en vez de @binds, ya que es una libreria,
    @Singleton          //osea, no es de nuestra propiedad
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            // .create() No lo vamos a colocar, ya que en las siguientes 3 funciones
            // se les va agregar en su respectica implementacion
            //.create(AuthApi::class.java)
    }

    // --- Nuevos métodos para proporcionar las interfaces de la API ---

    /**
     * Provee una instancia de AuthApi.
     * @param retrofit La instancia de Retrofit, inyectada por Hilt.
     * @return Una instancia de AuthApi.
     */
    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    /**
     * Provee una instancia de EstudiantesApi.
     * @param retrofit La instancia de Retrofit, inyectada por Hilt.
     * @return Una instancia de EstudiantesApi.
     */
    @Provides
    @Singleton
    fun provideEstudiantesApi(retrofit: Retrofit): EstudiantesApi {
        return retrofit.create(EstudiantesApi::class.java)
    }

    /**
     * Provee una instancia de PreguntasApi.
     * @param retrofit La instancia de Retrofit, inyectada por Hilt.
     * @return Una instancia de PreguntasApi.
     */
    @Provides
    @Singleton
    fun providePreguntasApi(retrofit: Retrofit): PreguntasApi {
        return retrofit.create(PreguntasApi::class.java)
    }
}
