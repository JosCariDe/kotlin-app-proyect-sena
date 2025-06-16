package edu.sena.caribeapp.domain.estudiantes.model

data class ClaseICFES(
    val id: String,
    val nombreClase: String,
    val profesor: String,
    val foros: List<Foro>,
    val simulacros: List<Simulacro>,
)