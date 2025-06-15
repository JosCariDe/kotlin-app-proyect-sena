package edu.sena.caribeapp.data.auth.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) para la respuesta de inicio de sesión.
 * Representa la estructura de los datos que se reciben de la API después de un login.
 * Asume que la API devuelve un "message" y un campo "data" genérico.
 */
data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Any? // Usamos Any? por ahora, si hay un token o datos de usuario, lo especificaremos
)
