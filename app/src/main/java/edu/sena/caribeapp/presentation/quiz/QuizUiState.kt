package edu.sena.caribeapp.presentation.quiz

import edu.sena.caribeapp.domain.estudiantes.model.ClaseICFES
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.model.Foro
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import edu.sena.caribeapp.domain.preguntas.model.Pregunta
import edu.sena.caribeapp.presentation.home.HomeTab
import edu.sena.caribeapp.presentation.home.SimulacroConClase

data class QuizUiState(
    val isLoading: Boolean = false,
    val erroMessage: String? = null,
    val pregunta: Pregunta? = null,
    val estudiante: Estudiante? = null,
    val clase: ClaseICFES? = null,
    val simulacro: Simulacro? = null,
    //val listPreguntas: List<String>? = emptyList(),
    val indexPreguntaActual: Int? = 0,
    val cantidadPreguntas: Int?  = 0,
    val primerLoading: Boolean = false

)