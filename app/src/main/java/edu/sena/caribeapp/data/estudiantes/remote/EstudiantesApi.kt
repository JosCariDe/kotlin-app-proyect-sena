package edu.sena.caribeapp.data.estudiantes.remote

import edu.sena.caribeapp.data.common.dto.ApiResponse
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
     * @return Una lista de EstudianteDto, envuelta en ApiResponse.
     */
    @GET("estudiantes")
    suspend fun getAllEstudiantes(): Response<ApiResponse<List<EstudianteDto>>>

    /**
     * Obtiene un estudiante por su ID.
     * @param id El ID del estudiante.
     * @return Un EstudianteDto, envuelto en ApiResponse.
     */
    @GET("estudiantes/{id}")
    suspend fun getEstudianteById(@Path("id") id: String): Response<ApiResponse<EstudianteDto>>

    /**
     * Crea un nuevo estudiante.
     * @param estudianteDto El objeto EstudianteDto a crear.
     * @return El EstudianteDto creado con su ID, envuelto en ApiResponse.
     */
    @Headers("Content-Type: application/json")
    @POST("estudiantes")
    suspend fun createEstudiante(@Body estudianteDto: EstudianteDto): Response<ApiResponse<EstudianteDto>>

    /**
     * Actualiza un estudiante existente.
     * @param id El ID del estudiante a actualizar.
     * @param estudianteDto El objeto EstudianteDto con los datos actualizados.
     * @return El EstudianteDto actualizado, envuelto en ApiResponse.
     */
    @Headers("Content-Type: application/json")
    @PUT("estudiantes/{id}")
    suspend fun updateEstudiante(
        @Path("id") id: String,
        @Body estudianteDto: EstudianteDto
    ): Response<ApiResponse<EstudianteDto>>

    /**
     * Elimina un estudiante por su ID.
     * @param id El ID del estudiante a eliminar.
     * @return Una respuesta genérica (ej. un mensaje de éxito), envuelta en ApiResponse.
     */
    @DELETE("estudiantes/{id}")
    suspend fun deleteEstudiante(@Path("id") id: String): Response<ApiResponse<Any>>
}
