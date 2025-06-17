package edu.sena.caribeapp.data.preguntas.remote

import edu.sena.caribeapp.data.preguntas.dto.PreguntaDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreguntaRemoteDataSource @Inject constructor(
    private val preguntasApi: PreguntasApi //Inyecta con Retrofit
){
    suspend fun getAllPreguntas() = preguntasApi.getAllPreguntas()
    suspend fun getPreguntaById(id: String) = preguntasApi.getPreguntaById(id)
    suspend fun createPregunta(pregunta: PreguntaDto) = preguntasApi.createPregunta(pregunta)
    suspend fun updatedPregunta(id: String, pregunta: PreguntaDto) = preguntasApi.updatePregunta(id, pregunta)
    suspend fun deletePregunta(id: String) = preguntasApi.deletePregunta(id)
}