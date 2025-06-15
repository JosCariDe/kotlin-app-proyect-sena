// app/src/main/java/edu/sena/caribeapp/data/estudiantes/dto/SimulacroDto.kt
package edu.sena.caribeapp.data.estudiantes.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para representar un simulacro dentro de una clase ICFES.
 */
data class SimulacroDto(
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("area")
    val area: String,
    @SerializedName("estado")
    val estado: String,
    @SerializedName("lista_id_preguntas")
    val listaIdPreguntas: List<String>, // Asumiendo que son IDs de preguntas
    @SerializedName("lista_opciones_correctas")
    val listaOpcionesCorrectas: List<String>, // Asumiendo que son opciones como String
    @SerializedName("lista_opciones_escogidas")
    val listaOpcionesEscogidas: List<String>, // Asumiendo que son opciones como String
    @SerializedName("_id")
    val id: String
)
