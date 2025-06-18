// app/src/main/java/edu/sena/caribeapp/di/RepositoryModule.kt
package edu.sena.caribeapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.sena.caribeapp.data.auth.repository.AuthRepositoryImpl
import edu.sena.caribeapp.data.estudiantes.repository.EstudianteRepositoryImpl
import edu.sena.caribeapp.data.preguntas.repository.PreguntaRepositoryImpl
import edu.sena.caribeapp.domain.auth.repository.AuthRepository
import edu.sena.caribeapp.domain.estudiantes.repository.EstudianteRepository
import edu.sena.caribeapp.domain.preguntas.repository.PreguntaRepository
import javax.inject.Singleton

/**
 * Módulo de Hilt para enlazar interfaces de repositorio con sus implementaciones concretas.
 * Se instala en SingletonComponent, lo que significa que los repositorios
 * serán singletons a nivel de aplicación.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule { // Debe ser una clase abstracta para usar @Binds

    /**
     * Enlaza AuthRepository con AuthRepositoryImpl.
     * Cuando se solicite AuthRepository, Hilt proporcionará una instancia de AuthRepositoryImpl.
     */
    @Binds
    @Singleton // Asegura que la implementación sea un singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    /**
     * Enlaza EstudianteRepository con EstudianteRepositoryImpl.
     */
    @Binds
    @Singleton
    abstract fun bindEstudianteRepository(
        estudianteRepositoryImpl: EstudianteRepositoryImpl
    ): EstudianteRepository

    /**
     * Enlaza PreguntaRepository con PreguntaRepositoryImpl.
     */
    @Binds
    @Singleton
    abstract fun bindPreguntaRepository(
        preguntaRepositoryImpl: PreguntaRepositoryImpl
    ): PreguntaRepository
}
