package edu.sena.caribeapp.data.estudiantes.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para representar la información completa de un estudiante.
 * Utilizado en las respuestas de login y registro, y para obtener datos de estudiantes.
 */
data class EstudianteDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("nombre_completo")
    val nombreCompleto: String,
    @SerializedName("correo")
    val correo: String,
    @SerializedName("fecha_registro")
    val fechaRegistro: String, // Considerar usar un tipo de fecha/hora más robusto
    @SerializedName("departamento")
    val departamento: String,
    @SerializedName("municipio")
    val municipio: String,
    @SerializedName("grado")
    val grado: String,
    @SerializedName("clasesICFES")
    val clasesICFES: List<ClaseICFESDto>?, // Puede ser nulo en el registro inicial
    @SerializedName("__v")
    val v: Int? // Campo de versión de MongoDB, puede ser nulo
)
