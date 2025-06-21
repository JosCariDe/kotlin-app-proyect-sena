package edu.sena.caribeapp.presentation.clase

import edu.sena.caribeapp.domain.estudiantes.model.ClaseICFES
import edu.sena.caribeapp.domain.estudiantes.model.Foro
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro

data class ClassUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val clase: ClaseICFES? = null,
    val foros: List<Foro> = emptyList(),
    val simulacros: List<Simulacro> = emptyList()
)
