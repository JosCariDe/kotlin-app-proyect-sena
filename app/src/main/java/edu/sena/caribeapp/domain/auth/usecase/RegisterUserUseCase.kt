package edu.sena.caribeapp.domain.auth.usecase

import edu.sena.caribeapp.domain.auth.repository.AuthRepository
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject

/**
 * Caso de Uso para registrar un nuevo usuario.
 * Encapsula la lógica de negocio para la operación de registro.
 *
 * @param repository La interfaz del repositorio de autenticación, inyectada por Hilt.
 */
class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    /**
     * Operador de invocación para hacer que el Caso de Uso sea invocable como una función.
     * Permite llamar a este Caso de Uso directamente como `registerUserUseCase(...)`.
     *
     * @param fullName Nombre completo del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param department Departamento del usuario.
     * @param municipality Municipio del usuario.
     * @param grade Grado del usuario.
     * @return Un Resource que indica el estado de la operación (Éxito, Error, Cargando)
     *         y contiene la entidad Estudiante si es exitoso.
     */
    suspend operator fun invoke(
        fullName: String,
        email: String,
        password: String,
        department: String,
        municipality: String,
        grade: String
    ): Resource<Estudiante> {
        // Aquí también podríamos añadir lógica de validación de negocio específica para el registro.
        return repository.registerUser(fullName, email, password, department, municipality, grade)
    }
}
