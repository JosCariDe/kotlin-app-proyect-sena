package edu.sena.caribeapp.domain.estudiantes.repository
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.util.Resource

interface EstudianteRepository {

    suspend fun getAllEstudiantes(): Resource<List<Estudiante>>

    suspend fun getEstudianteById(id: String): Resource<Estudiante>

    suspend fun createEstudiante(estudiante: Estudiante): Resource<Estudiante>

    suspend fun updateEstudiante(id: String, estudiante: Estudiante): Resource<Estudiante>

    suspend fun deleteEstudiante(id: String): Resource<Unit> // Unit para indicar que no devuelve datos específicos


}