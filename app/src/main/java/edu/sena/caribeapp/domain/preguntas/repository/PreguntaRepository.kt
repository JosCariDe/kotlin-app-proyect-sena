package edu.sena.caribeapp.domain.preguntas.repository

import edu.sena.caribeapp.domain.preguntas.model.Pregunta
import edu.sena.caribeapp.util.Resource

interface PreguntaRepository {

    suspend fun getAllPreguntas(): Resource<List<Pregunta>>

    suspend fun getPreguntaById(id: String): Resource<Pregunta>

    suspend fun createPregunta(pregunta: Pregunta): Resource<Pregunta>

    suspend fun updatePregunta(id: String, pregunta: Pregunta): Resource<Pregunta>

    suspend fun deletePregunta(id: String): Resource<Unit>
}