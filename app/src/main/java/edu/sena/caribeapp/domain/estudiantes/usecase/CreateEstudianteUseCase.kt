package edu.sena.caribeapp.domain.estudiantes.usecase

import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.repository.EstudianteRepository
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject

/**
 * Caso de Uso para crear un nuevo estudiante.
 *
 * @param repository La interfaz del repositorio de estudiantes, inyectada por Hilt.
 */
class CreateEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    /**
     * Operador de invocación para crear un nuevo estudiante.
     * @param estudiante La entidad Estudiante a crear.
     * @return Un Resource que indica el estado de la operación (Éxito, Error, Cargando)
     *         y contiene la entidad Estudiante creada si es exitoso.
     */
    suspend operator fun invoke(estudiante: Estudiante): Resource<Estudiante> {
        // Aquí podrías añadir validaciones de negocio antes de crear el estudiante
        return repository.createEstudiante(estudiante)
    }
}
