package edu.sena.caribeapp.domain.estudiantes.usecase

import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.repository.EstudianteRepository
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject

/**
 * Caso de Uso para obtener la lista de todos los estudiantes.
 *
 * @param repository La interfaz del repositorio de estudiantes, inyectada por Hilt.
 */
class GetAllEstudiantesUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    /**
     * Operador de invocación para obtener todos los estudiantes.
     * @return Un Resource que indica el estado de la operación (Éxito, Error, Cargando)
     *         y contiene una lista de entidades Estudiante si es exitoso.
     */
    suspend operator fun invoke(): Resource<List<Estudiante>> {
        return repository.getAllEstudiantes()
    }
}
