package edu.sena.caribeapp.presentation.home

import edu.sena.caribeapp.domain.estudiantes.model.ClaseICFES
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.model.Foro
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro

data class HomeUiState(
    val isLoading: Boolean = false,
    val estudiante: Estudiante? = null,
    val clasesICFES: List<ClaseICFES> = emptyList(),
    val foros: List<Foro> = emptyList(),
    val simulacros: List<SimulacroConClase> = emptyList(), // Cambiado a SimulacroConClase
    val selectedTab: HomeTab = HomeTab.FOROS,
    val searchQuery: String = "",
    val errorMessage: String? = null
)

enum class HomeTab {
    FOROS, SIMULACROS
}

// Nueva clase de datos para asociar Simulacro con su ClaseICFES
data class SimulacroConClase(
    val simulacro: Simulacro,
    val claseId: String
)
