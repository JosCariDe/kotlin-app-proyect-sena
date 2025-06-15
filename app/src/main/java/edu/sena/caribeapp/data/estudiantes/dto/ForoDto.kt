package edu.sena.caribeapp.data.estudiantes.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para representar un foro dentro de una clase ICFES.
 */
data class ForoDto(
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("creador")
    val creador: String,
    @SerializedName("fecha")
    val fecha: String, // Considerar usar un tipo de fecha/hora más robusto como Instant o LocalDateTime
    @SerializedName("_id")
    val id: String
)
