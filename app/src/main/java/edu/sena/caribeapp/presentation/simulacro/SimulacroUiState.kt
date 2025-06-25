package edu.sena.caribeapp.presentation.simulacro

import edu.sena.caribeapp.domain.estudiantes.model.ClaseICFES
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.model.Foro
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import edu.sena.caribeapp.presentation.home.SimulacroConClase

data class SimulacroUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val simulacro: Simulacro? = null,
    val estudiante: Estudiante? = null,
    val clase: ClaseICFES? = null,
    val numPreguntas: Int? = 0
)