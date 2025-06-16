package edu.sena.caribeapp.domain.preguntas.usecase

import edu.sena.caribeapp.domain.preguntas.model.Pregunta
import edu.sena.caribeapp.domain.preguntas.repository.PreguntaRepository
import edu.sena.caribeapp.util.Resource
import javax.inject.Inject

class UpdatePreguntaUseCase @Inject constructor(
    private val repositoy: PreguntaRepository
) {
    suspend operator fun invoke(id: String, pregunta: Pregunta): Resource<Pregunta> {
        return repositoy.updatePregunta(id, pregunta)
    }
}