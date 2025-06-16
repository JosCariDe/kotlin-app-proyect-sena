package edu.sena.caribeapp.domain.preguntas.usecase

import edu.sena.caribeapp.domain.preguntas.model.Pregunta
import edu.sena.caribeapp.domain.preguntas.repository.PreguntaRepository
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject

class CreatePreguntaUseCase @Inject constructor(
    private val repository: PreguntaRepository
) {
    suspend operator fun invoke(pregunta: Pregunta): Resource<Pregunta> {
        return repository.createPregunta(pregunta)
    }
}