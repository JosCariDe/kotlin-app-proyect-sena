package edu.sena.caribeapp.domain.estudiantes.usecase

import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.repository.EstudianteRepository
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject

/**
 * Caso de Uso para actualizar un estudiante existente.
 *
 * @param repository La interfaz del repositorio de estudiantes, inyectada por Hilt.
 */
class UpdateEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    /**
     * Operador de invocación para actualizar un estudiante.
     * @param id El ID del estudiante a actualizar.
     * @param estudiante La entidad Estudiante con los datos actualizados.
     * @return Un Resource que indica el estado de la operación (Éxito, Error, Cargando)
     *         y contiene la entidad Estudiante actualizada si es exitoso.
     */
    suspend operator fun invoke(id: String, estudiante: Estudiante): Resource<Estudiante> {
        // Aquí podrías añadir lógica de negocio para la actualización
        return repository.updateEstudiante(id, estudiante)
    }
}
