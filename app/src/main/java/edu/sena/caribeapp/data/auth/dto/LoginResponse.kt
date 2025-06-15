package edu.sena.caribeapp.data.auth.dto

import com.google.gson.annotations.SerializedName
import edu.sena.caribeapp.data.estudiantes.dto.EstudianteDto

/**
 * Data Transfer Object (DTO) para la respuesta de inicio de sesión.
 * Representa la estructura de los datos que se reciben de la API después de un login.
 * Ahora el campo 'data' es de tipo EstudianteDto.
 */
data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: EstudianteDto
)
