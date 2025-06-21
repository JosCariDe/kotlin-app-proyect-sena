package edu.sena.caribeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.usecase.GetEstudianteByIdUseCase // Caso de Uso para obtener estudiante
import edu.sena.caribeapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla principal (HomeScreen).
 * Maneja el estado de la UI, la obtención de datos del estudiante, clases, foros y simulacros.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEstudianteByIdUseCase: GetEstudianteByIdUseCase // Inyecta el Caso de Uso
    // Aquí se inyectarían otros Casos de Uso si hubiera endpoints separados para foros/simulacros
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // ID del estudiante logueado (simulado por ahora, en una app real se obtendría de la sesión)
    private val loggedInEstudianteId: String = "6845d826441f914e0a7475d2" // ID de ejemplo de tu JSON

    init {
        // Cuando el ViewModel se crea, inicia la carga de datos
        loadHomeScreenData()
    }

    /**
     * Carga todos los datos necesarios para el HomeScreen:
     * perfil del estudiante, sus clases, foros y simulacros.
     */
    fun loadHomeScreenData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            when (val result = getEstudianteByIdUseCase(loggedInEstudianteId)) {
                is Resource.Success -> {
                    val estudiante = result.data
                    if (estudiante != null) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            estudiante = estudiante,
                            clasesICFES = estudiante.clasesICFES ?: emptyList(), // Si es nulo, lista vacía
                            foros = estudiante.clasesICFES?.flatMap { it.foros } ?: emptyList(), // Extrae todos los foros de todas las clases
                            simulacros = estudiante.clasesICFES?.flatMap { it.simulacros } ?: emptyList(), // Extrae todos los simulacros
                            errorMessage = null
                        )
                        // Si el usuario añadió una acción de bienvenida personalizada, la invocamos aquí
                        _uiState.value.accionBienvenidaPersonalizada?.invoke(estudiante.nombreCompleto)
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "No se encontró el perfil del estudiante."
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "Error al cargar los datos del perfil."
                    )
                }
                is Resource.Loading -> {
                    // Estado de carga ya manejado
                }
            }
        }
    }

    /**
     * Cambia la pestaña seleccionada en la sección "Mi avance".
     */
    fun onTabSelected(tab: HomeTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    /**
     * Restablece el estado de la UI del HomeScreen.
     */
    fun resetUiState() {
        _uiState.value = HomeUiState()
    }

    // Barra de busqueda
    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
}
