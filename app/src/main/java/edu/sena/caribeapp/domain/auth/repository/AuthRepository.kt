package edu.sena.caribeapp.domain.auth.repository

import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.util.Resource

/**
 * Interfaz de repositorio para las operaciones de autenticación.
 * Define el contrato que la capa de datos debe implementar para el login y registro.
 */
interface AuthRepository {

    suspend fun registerUser(
        fullName: String,
        email: String,
        password: String,
        department: String,
        municipality: String,
        grade: String
    ): Resource<Estudiante>

    suspend fun loginUser(
        email: String,
        password: String
    ): Resource<Estudiante>
}
