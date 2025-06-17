package edu.sena.caribeapp.data.preguntas.repository

import coil3.network.HttpException
import edu.sena.caribeapp.data.preguntas.mapper.PreguntaMapper
import edu.sena.caribeapp.data.preguntas.remote.PreguntaRemoteDataSource
import edu.sena.caribeapp.domain.preguntas.model.Pregunta
import edu.sena.caribeapp.domain.preguntas.repository.PreguntaRepository
import edu.sena.caribeapp.util.Resource
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreguntaRepositoryImpl @Inject constructor(

    private val preguntaRemoteDataSource: PreguntaRemoteDataSource,
    private val mapper: PreguntaMapper

) : PreguntaRepository { // Implementando contrato

    override suspend fun getAllPreguntas(): Resource<List<Pregunta>> {
        return try {

            val response = preguntaRemoteDataSource.getAllPreguntas()
            if (response.isSuccessful && response.body()?.data != null) {
                val preguntaDto = response.body()!!.data
                val preguntas = mapper.run {
                    preguntaDto!!.map { it.toDomain() }
                }
                Resource.Success(preguntas)
            }else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido al obtener preguntas"
                Resource.Error(errorMessage)
            }
        }  catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Error HTTP inesperado")
        } catch (e: IOException) {
            Resource.Error("No se pudo conectar al servidor. Revisa tu conexión a internet.")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Ocurrió un error inesperado")
        }
    }

    override suspend fun getPreguntaById(id: String): Resource<Pregunta> {
        return try {
            val response = preguntaRemoteDataSource.getPreguntaById(id)
            if (response.isSuccessful && response.body()?.data != null) {
                val preguntaDto = response.body()!!.data
                val pregunta = mapper.run {
                    preguntaDto!!.toDomain()
                }
                Resource.Success(pregunta)
            }else {
                val errorMessage = response.errorBody()?.string() ?: "Pregunta no encontrada"
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

    override suspend fun createPregunta(pregunta: Pregunta): Resource<Pregunta> {
        return try {
            val preguntaDto = mapper.run { pregunta.toDto() }
            val response = preguntaRemoteDataSource.createPregunta(preguntaDto)
            if (response.isSuccessful && response.body()?.data != null) {
                val preguntaCreada = mapper.run {
                    response.body()!!.data!!.toDomain()
                }
                Resource.Success(preguntaCreada)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error al crear pregunta"
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

    override suspend fun updatePregunta(id: String, pregunta: Pregunta): Resource<Pregunta> {
        return try {
            val preguntaDto = mapper.run { pregunta.toDto() }
            val response = preguntaRemoteDataSource.updatedPregunta(id, preguntaDto)
            if (response.isSuccessful && response.body()?.data != null) {
                val preguntaActualizada = mapper.run {
                    response.body()!!.data!!.toDomain()
                }
                Resource.Success(preguntaActualizada)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error al actualizar pregunta"
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

    override suspend fun deletePregunta(id: String): Resource<Unit> {
        return try {
            val response = preguntaRemoteDataSource.deletePregunta(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error al eliminar pregunta"
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