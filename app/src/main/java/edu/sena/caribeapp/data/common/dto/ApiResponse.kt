package edu.sena.caribeapp.data.common.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO genérico para envolver las respuestas comunes de la API.
 * Tu API devuelve respuestas con un "message" y un campo "data" que contiene los datos reales.
 *
 * @param T El tipo de datos que se espera en el campo 'data'.
 */
data class ApiResponse<T>(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T? // El campo de datos real, puede ser nulo
)
