// app/src/main/java/edu/sena/caribeapp/data/estudiantes/remote/EstudianteRemoteDataSource.kt
package edu.sena.caribeapp.data.estudiantes.remote

import edu.sena.caribeapp.data.estudiantes.dto.EstudianteDto
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fuente de datos remota para las operaciones de estudiantes.
 * Interactúa directamente con la EstudiantesApi de Retrofit.
 */
@Singleton // Se inyectará como un singleton
class EstudianteRemoteDataSource @Inject constructor(
    private val estudiantesApi: EstudiantesApi // Inyecta la interfaz de Retrofit
) {
    suspend fun getAllEstudiantes() = estudiantesApi.getAllEstudiantes()
    suspend fun getEstudianteById(id: String) = estudiantesApi.getEstudianteById(id)
    suspend fun createEstudiante(estudianteDto: EstudianteDto) = estudiantesApi.createEstudiante(estudianteDto)
    suspend fun updateEstudiante(id: String, estudianteDto: EstudianteDto) = estudiantesApi.updateEstudiante(id, estudianteDto)
    suspend fun deleteEstudiante(id: String) = estudiantesApi.deleteEstudiante(id)
}
