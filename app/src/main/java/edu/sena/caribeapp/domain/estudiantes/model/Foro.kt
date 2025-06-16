package edu.sena.caribeapp.domain.estudiantes.model

/**
 * Entidad de dominio que representa un foro.
 * Pura, sin dependencias de la capa de datos o presentación.
 */

data class Foro(
    val id: String,
    val nombre: String,
    val creador: String,
    val fecha: String,
)