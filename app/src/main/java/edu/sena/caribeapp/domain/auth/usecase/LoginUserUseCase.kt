package edu.sena.caribeapp.domain.auth.usecase

import edu.sena.caribeapp.domain.auth.repository.AuthRepository
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject

/**
 * Caso de Uso para iniciar sesión de un usuario.
 * Encapsula la lógica de negocio para la operación de login.
 *
 * @param repository La interfaz del repositorio de autenticación, inyectada por Hilt.
 */

class loginUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    /**
     * Operador de invocación para hacer que el Caso de Uso sea invocable como una función.
     * Permite llamar a este Caso de Uso directamente como `loginUserUseCase(email, password)`.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Un Resource que indica el estado de la operación (Éxito, Error, Cargando)
     *         y contiene la entidad Estudiante si es exitoso.
     */
    suspend operator fun invoke(email: String, password: String): Resource<Estudiante> {
        // Aquí podríamos añadir lógica de validación de negocio adicional si fuera necesario,
        // por ejemplo, verificar la fortaleza de la contraseña antes de llamar al repositorio.
        return repository.loginUser(email, password)
    }
}