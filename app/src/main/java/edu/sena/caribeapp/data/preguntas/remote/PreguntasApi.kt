package edu.sena.caribeapp.data.preguntas.remote

import edu.sena.caribeapp.data.common.dto.ApiResponse
import edu.sena.caribeapp.data.preguntas.dto.PreguntaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfaz de Retrofit para los endpoints de preguntas.
 * Define los métodos para interactuar con la API de gestión de preguntas.
 */
interface PreguntasApi {

    /**
     * Obtiene la lista de todas las preguntas.
     * @return Una lista de PreguntaDto, envuelta en ApiResponse.
     */
    @GET("preguntas")
    suspend fun getAllPreguntas(): Response<ApiResponse<List<PreguntaDto>>>

    /**
     * Obtiene una pregunta por su ID.
     * @param id El ID de la pregunta.
     * @return Una PreguntaDto, envuelta en ApiResponse.
     */
    @GET("preguntas/{id}")
    suspend fun getPreguntaById(@Path("id") id: String): Response<ApiResponse<PreguntaDto>>

    /**
     * Crea una nueva pregunta.
     * @param preguntaDto El objeto PreguntaDto a crear.
     * @return La PreguntaDto creada con su ID, envuelta en ApiResponse.
     */
    @Headers("Content-Type: application/json")
    @POST("preguntas")
    suspend fun createPregunta(@Body preguntaDto: PreguntaDto): Response<ApiResponse<PreguntaDto>>

    /**
     * Actualiza una pregunta existente.
     * @param id El ID de la pregunta a actualizar.
     * @param preguntaDto El objeto PreguntaDto con los datos actualizados.
     * @return La PreguntaDto actualizada, envuelta en ApiResponse.
     */
    @Headers("Content-Type: application/json")
    @PUT("preguntas/{id}")
    suspend fun updatePregunta(
        @Path("id") id: String,
        @Body preguntaDto: PreguntaDto
    ): Response<ApiResponse<PreguntaDto>>

    /**
     * Elimina una pregunta por su ID.
     * @param id El ID de la pregunta a eliminar.
     * @return Una respuesta genérica (ej. un mensaje de éxito), envuelta en ApiResponse.
     */
    @DELETE("preguntas/{id}")
    suspend fun deletePregunta(@Path("id") id: String): Response<ApiResponse<Any>>
}
