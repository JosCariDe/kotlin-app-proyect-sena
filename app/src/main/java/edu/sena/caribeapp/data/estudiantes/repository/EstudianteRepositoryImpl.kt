// app/src/main/java/edu/sena/caribeapp/data/estudiantes/repository/EstudianteRepositoryImpl.kt
package edu.sena.caribeapp.data.estudiantes.repository

import edu.sena.caribeapp.data.estudiantes.dto.EstudianteDto
import edu.sena.caribeapp.data.estudiantes.mapper.EstudianteMapper
import edu.sena.caribeapp.data.estudiantes.remote.EstudianteRemoteDataSource
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.repository.EstudianteRepository
import edu.sena.caribeapp.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación concreta de EstudianteRepository.
 * Coordina entre la fuente de datos remota y realiza el mapeo de DTOs a entidades.
 * Maneja los errores de red y los transforma en un Resource.Error.
 */
@Singleton // Se inyectará como un singleton
class EstudianteRepositoryImpl @Inject constructor(
    private val remoteDataSource: EstudianteRemoteDataSource,
    private val mapper: EstudianteMapper // Inyecta el mapeador de estudiantes
) : EstudianteRepository { // Implementa la interfaz de dominio

    override suspend fun getAllEstudiantes(): Resource<List<Estudiante>> {
        return try {
            val response = remoteDataSource.getAllEstudiantes()
            if (response.isSuccessful && response.body()?.data != null) {

                // Convierte la lista de DTOs a lista de entidades de dominio
                val estudiantesDto: List<EstudianteDto>? = response.body()!!.data

                val estudiantes = mapper.run {
                    estudiantesDto!!.map { it.toDomain() }
                }

                Resource.Success(estudiantes)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido al obtener estudiantes"
                Resource.Error(errorMessage)
            }
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Error HTTP inesperado")
        } catch (e: IOException) {
            Resource.Error("No se pudo conectar al servidor. Revisa tu conexión a internet.")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Ocurrió un error inesperado")
        }
    }

    override suspend fun getEstudianteById(id: String): Resource<Estudiante> {
        return try {
            val response = remoteDataSource.getEstudianteById(id)
            if (response.isSuccessful && response.body()?.data != null) {

                // Convierte DTO a entidad de dominio
                val estudianteDto: EstudianteDto? = response.body()!!.data
                Resource.Success(
                    mapper.run {
                        estudianteDto!!.toDomain()
                    }
                )
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Estudiante no encontrado"
                Resource.Error(errorMessage)
            }
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Error HTTP inesperado")
        } catch (e: IOException) {
            Resource.Error("No se pudo conectar al servidor. Revisa tu conexión a internet.")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Ocurrió un error inesperado")
        }
    }


    override suspend fun createEstudiante(estudiante: Estudiante): Resource<Estudiante> {
        return try {
            // Mapea entidad de dominio a DTO usando función de extensión
            val estudianteDto = mapper.run { estudiante.toDto() }

            val response = remoteDataSource.createEstudiante(estudianteDto)
            if (response.isSuccessful && response.body()?.data != null) {
                // Mapea DTO de respuesta a entidad de dominio
                val estudianteCreado = mapper.run {
                    response.body()!!.data!!.toDomain()
                }
                Resource.Success(estudianteCreado)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error al crear estudiante"
                Resource.Error(errorMessage)
            }
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Error HTTP inesperado")
        } catch (e: IOException) {
            Resource.Error("No se pudo conectar al servidor. Revisa tu conexión a internet.")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Ocurrió un error inesperado")
        }
    }

    override suspend fun updateEstudiante(id: String, estudiante: Estudiante): Resource<Estudiante> {
        return try {
            val estudianteDto = mapper.run { estudiante.toDto() }// Mapea entidad a DTO
            val response = remoteDataSource.updateEstudiante(id, estudianteDto)
            if (response.isSuccessful && response.body()?.data != null) {
                // Mapea DTO de respuesta a entidad de dominio
                val estudianteCreado = mapper.run {
                    response.body()!!.data!!.toDomain()
                }
                Resource.Success(estudianteCreado)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error al actualizar estudiante"
                Resource.Error(errorMessage)
            }
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Error HTTP inesperado")
        } catch (e: IOException) {
            Resource.Error("No se pudo conectar al servidor. Revisa tu conexión a internet.")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Ocurrió un error inesperado")
        }
    }

    override suspend fun deleteEstudiante(id: String): Resource<Unit> {
        return try {
            val response = remoteDataSource.deleteEstudiante(id)
            if (response.isSuccessful) {
                Resource.Success(Unit) // Éxito sin datos específicos
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error al eliminar estudiante"
                Resource.Error(errorMessage)
            }
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Error HTTP inesperado")
        } catch (e: IOException) {
            Resource.Error("No se pudo conectar al servidor. Revisa tu conexión a internet.")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Ocurrió un error inesperado")
        }
    }
}
