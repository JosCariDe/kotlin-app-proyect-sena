package edu.sena.caribeapp.domain.preguntas.model

data class Pregunta(
    val id: String,
    val area: String,
    val tema: String,
    val enunciado: String,
    val opciones: List<Opcion>,
    val explicacionCorrecta: String,
    val urlImagen: String?, // Puede ser nulo, no siempre traen imagenes las preguntas
)