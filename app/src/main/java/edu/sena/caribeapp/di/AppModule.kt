package edu.sena.caribeapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de Hilt para proporcionar dependencias a nivel de aplicación.
 * Se instala en SingletonComponent, lo que significa que las dependencias
 * proporcionadas aquí serán singletons a nivel de aplicación.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provee el Contexto de la aplicación.
     * @param context El contexto de la aplicación, inyectado automáticamente por Hilt.
     * @return Una instancia del Contexto de la aplicación.
     *
     * ¿Por qué es importante?
     * El Contexto de Android es fundamental para muchas operaciones (acceder a recursos,
     * lanzar actividades, etc.). Al proveerlo a través de Hilt, evitamos pasar el Contexto
     * directamente a clases que no lo necesitan, lo que puede llevar a memory leaks.
     * Hilt sabe cómo proporcionar el Contexto de la aplicación gracias a la anotación
     * @ApplicationContext.
     */
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    // Aquí podrías añadir otras dependencias a nivel de aplicación, como:
    // - SharedPreferences
    // - Bases de datos (Room)
    // - Gestores de recursos, etc.
}
