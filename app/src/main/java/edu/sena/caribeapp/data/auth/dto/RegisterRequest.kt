package edu.sena.caribeapp.data.auth.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) para la petición de registro de usuario.
 * Representa la estructura de los datos que se envían a la API para el registro.
 */
data class RegisterRequest(
    @SerializedName("nombre_completo")
    val fullName: String,
    @SerializedName("correo")
    val email: String,
    @SerializedName("contrasena")
    val password: String,
    @SerializedName("departamento")
    val department: String,
    @SerializedName("municipio")
    val municipality: String,
    @SerializedName("grado")
    val grade: String
)
