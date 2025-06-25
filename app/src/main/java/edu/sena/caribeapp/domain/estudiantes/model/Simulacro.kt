package edu.sena.caribeapp.domain.estudiantes.model

data class Simulacro(
    val id: String,
    val titulo: String,
    val area: String,
    val estado: String,
    val listaIdPreguntas: List<String>,
    val listaOpcionesCorrectas: List<String>,
    val listaOpcionesEscogidas: List<String>,
    val fechaInicio: String? = null, // Añadido para la fecha de inicio
    val fechaFin: String? = null, // Añadido para la fecha de fin
    val totalPreguntas: Int = 0, // Añadido para el total de preguntas
    val preguntasRespondidas: Int = 0 // Añadido para las preguntas respondidas
)
