package edu.sena.caribeapp.data.auth.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) para la petición de inicio de sesión.
 * Representa la estructura de los datos que se envían a la API para el login.
 */
data class LoginRequest(
    @SerializedName("correo") // Mapea el campo 'correo' del JSON
    val email: String,
    @SerializedName("contrasena") // Mapea el campo 'contrasena' del JSON
    val password: String
)
