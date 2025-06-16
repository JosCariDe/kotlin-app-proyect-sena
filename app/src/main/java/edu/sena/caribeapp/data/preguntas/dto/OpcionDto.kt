package edu.sena.caribeapp.data.preguntas.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para representar una opción de respuesta para una pregunta.
 */
data class OpcionDto(
    @SerializedName("letra")
    val letra: String,
    @SerializedName("texto")
    val texto: String,
    @SerializedName("_id")
    val id: String
)
