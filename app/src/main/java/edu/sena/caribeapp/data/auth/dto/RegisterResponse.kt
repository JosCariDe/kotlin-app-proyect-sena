package edu.sena.caribeapp.data.auth.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) para la respuesta de registro de usuario.
 * Representa la estructura de los datos que se reciben de la API después de un registro.
 * Asume que la API devuelve un "message" y un campo "data" genérico.
 */
data class RegisterResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Any? // Usamos Any? por ahora, si hay datos de usuario, lo especificaremos
)
