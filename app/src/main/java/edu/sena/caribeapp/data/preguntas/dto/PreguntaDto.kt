package edu.sena.caribeapp.data.preguntas.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para representar la información completa de una pregunta.
 * Utilizado para obtener, crear, actualizar y eliminar preguntas.
 */
data class PreguntaDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("tema")
    val tema: String,
    @SerializedName("enunciado")
    val enunciado: String,
    @SerializedName("opciones")
    val opciones: List<OpcionDto>, // Lista de DTOs de Opcion
    @SerializedName("explicacion_correcta")
    val explicacionCorrecta: String,
    @SerializedName("url_imagen")
    val urlImagen: String?, // Campo opcional, puede ser nulo
    @SerializedName("__v")
    val v: Int? // Campo de versión de MongoDB, puede ser nulo
)
