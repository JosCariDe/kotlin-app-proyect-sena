package edu.sena.caribeapp.presentation.home

import edu.sena.caribeapp.domain.estudiantes.model.ClaseICFES
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.model.Foro
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro

/**
 * Enum que representa las pestañas disponibles en la sección "Mi avance" del HomeScreen.
 */
enum class HomeTab {
    FOROS, SIMULACROS
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val estudiante: Estudiante? = null,
    val clasesICFES: List<ClaseICFES> = emptyList(),
    val foros: List<Foro> = emptyList(),
    val simulacros: List<Simulacro> = emptyList(),
    val selectedTab: HomeTab = HomeTab.FOROS, // Pestaña por defecto
    val mensajeBienvenidaPredeterminado: String = "Bienvenido a PREPARATE ICFES CARIBE",
    // Nuevo atributo: función anónima opcional para un saludo personalizado
    val accionBienvenidaPersonalizada: ((String) -> Unit)? = null,
    val searchQuery: String = ""
)
