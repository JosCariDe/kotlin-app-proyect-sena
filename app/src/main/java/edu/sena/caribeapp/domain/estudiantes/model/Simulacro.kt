package edu.sena.caribeapp.domain.estudiantes.model

data class Simulacro(
    val id: String,
    val titulo: String,
    val area: String,
    val estado: String,
    val listaIdPreguntas: List<String>,
    val listaOpcionesCorrectas: List<String>,
    val listaOpcionesEscogidas: List<String>,

)