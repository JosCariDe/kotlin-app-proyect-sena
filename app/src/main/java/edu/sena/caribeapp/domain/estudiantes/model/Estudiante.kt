package edu.sena.caribeapp.domain.estudiantes.model

data class Estudiante(
    val id: String,
    val nombreCompleto: String,
    val correo: String,
    val fechaRegisto: String,
    val departamento: String,
    val municipio: String,
    val grado: String,
    val clasesICFES: List<ClaseICFES>?
)