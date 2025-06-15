package edu.sena.caribeapp.data.estudiantes.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para representar una clase ICFES a la que un estudiante está inscrito.
 */
data class ClaseICFESDto(
    @SerializedName("nombre_clase")
    val nombreClase: String,
    @SerializedName("profesor")
    val profesor: String,
    @SerializedName("foros")
    val foros: List<ForoDto>, // Lista de DTOs de Foro
    @SerializedName("simulacros")
    val simulacros: List<SimulacroDto>, // Lista de DTOs de Simulacro
    @SerializedName("_id")
    val id: String
)
