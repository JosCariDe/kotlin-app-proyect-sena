package edu.sena.caribeapp.domain.auth.repository

import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.util.Resource

/**
 * Interfaz de repositorio para las operaciones de autenticación.
 * Define el contrato que la capa de datos debe implementar para el login y registro.
 */
interface AuthRepository {

    /**
     * Registra un nuevo usuario.
     * @param fullName Nombre completo del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param department Departamento del usuario.
     * @param municipality Municipio del usuario.
     * @param grade Grado del usuario.
     * @return Un Resource que indica el estado de la operación (Éxito, Error, Cargando)
     *         y contiene la entidad Estudiante si es exitoso.
     */
    suspend fun registerUser(
        fullName: String,
        email: String,
        password: String,
        department: String,
        municipality: String,
        grade: String
    ): Resource<Estudiante>

    /**
     * Inicia sesión de un usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Un Resource que indica el estado de la operación (Éxito, Error, Cargando)
     *         y contiene la entidad Estudiante si es exitoso.
     */
    suspend fun loginUser(
        email: String,
        password: String
    ): Resource<Estudiante>
}
