package edu.sena.caribeapp.data.estudiantes.remote

import edu.sena.caribeapp.data.estudiantes.dto.EstudianteDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfaz de Retrofit para los endpoints de estudiantes.
 * Define los métodos para interactuar con la API de gestión de estudiantes.
 */
interface EstudiantesApi {

    /**
     * Obtiene la lista de todos los estudiantes.
     * @return Una lista de EstudianteDto.
     */
    @GET("estudiantes")
    suspend fun getAllEstudiantes(): Response<List<EstudianteDto>>

    /**
     * Obtiene un estudiante por su ID.
     * @param id El ID del estudiante.
     * @return Un EstudianteDto.
     */
    @GET("estudiantes/{id}")
    suspend fun getEstudianteById(@Path("id") id: String): Response<EstudianteDto>

    /**
     * Crea un nuevo estudiante.
     * @param estudianteDto El objeto EstudianteDto a crear.
     * @return El EstudianteDto creado con su ID.
     */
    @Headers("Content-Type: application/json")
    @POST("estudiantes")
    suspend fun createEstudiante(@Body estudianteDto: EstudianteDto): Response<EstudianteDto>

    /**
     * Actualiza un estudiante existente.
     * @param id El ID del estudiante a actualizar.
     * @param estudianteDto El objeto EstudianteDto con los datos actualizados.
     * @return El EstudianteDto actualizado.
     */
    @Headers("Content-Type: application/json")
    @PUT("estudiantes/{id}")
    suspend fun updateEstudiante(
        @Path("id") id: String,
        @Body estudianteDto: EstudianteDto
    ): Response<EstudianteDto>

    /**
     * Elimina un estudiante por su ID.
     * @param id El ID del estudiante a eliminar.
     * @return Una respuesta genérica (ej. un mensaje de éxito).
     */
    @DELETE("estudiantes/{id}")
    suspend fun deleteEstudiante(@Path("id") id: String): Response<Any> // O un DTO de mensaje de éxito
}
