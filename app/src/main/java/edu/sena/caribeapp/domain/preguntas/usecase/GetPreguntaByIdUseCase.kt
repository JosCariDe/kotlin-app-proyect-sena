package edu.sena.caribeapp.domain.preguntas.usecase

import edu.sena.caribeapp.domain.preguntas.model.Pregunta
import edu.sena.caribeapp.domain.preguntas.repository.PreguntaRepository
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject


class GetPreguntaByIdUseCase @Inject constructor(
    private val repository: PreguntaRepository
) {
    suspend operator fun invoke(id: String): Resource<Pregunta> {
        return repository.getPreguntaById(id)
    }
}