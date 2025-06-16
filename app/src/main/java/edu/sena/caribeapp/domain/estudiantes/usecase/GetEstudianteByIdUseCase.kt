package edu.sena.caribeapp.domain.estudiantes.usecase

import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.repository.EstudianteRepository
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject

/**
 * Caso de Uso para obtener un estudiante por su ID.
 *
 * @param repository La interfaz del repositorio de estudiantes, inyectada por Hilt.
 */
class GetEstudianteByIdUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    /**
     * Operador de invocación para obtener un estudiante por su ID.
     * @param id El ID del estudiante a buscar.
     * @return Un Resource que indica el estado de la operación (Éxito, Error, Cargando)
     *         y contiene la entidad Estudiante si es exitoso.
     */
    suspend operator fun invoke(id: String): Resource<Estudiante> {
        return repository.getEstudianteById(id)
    }
}
