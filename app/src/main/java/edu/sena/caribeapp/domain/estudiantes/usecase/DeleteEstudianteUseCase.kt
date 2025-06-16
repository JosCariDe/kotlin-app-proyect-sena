package edu.sena.caribeapp.domain.estudiantes.usecase

import edu.sena.caribeapp.domain.estudiantes.repository.EstudianteRepository
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject

/**
 * Caso de Uso para eliminar un estudiante.
 *
 * @param repository La interfaz del repositorio de estudiantes, inyectada por Hilt.
 */
class DeleteEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    /**
     * Operador de invocación para eliminar un estudiante.
     * @param id El ID del estudiante a eliminar.
     * @return Un Resource que indica el estado de la operación (Éxito, Error, Cargando).
     */
    suspend operator fun invoke(id: String): Resource<Unit> {
        return repository.deleteEstudiante(id)
    }
}
