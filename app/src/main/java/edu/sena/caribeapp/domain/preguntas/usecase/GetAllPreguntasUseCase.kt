package edu.sena.caribeapp.domain.preguntas.usecase

import edu.sena.caribeapp.domain.preguntas.model.Pregunta
import edu.sena.caribeapp.domain.preguntas.repository.PreguntaRepository
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject


class GetAllPreguntasUseCase @Inject constructor(
    private val repository: PreguntaRepository
) {
    suspend operator fun invoke(): Resource<List<Pregunta>> {
        return repository.getAllPreguntas()
    }
}