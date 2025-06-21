package edu.sena.caribeapp.presentation.clase



import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.sena.caribeapp.domain.estudiantes.model.ClaseICFES
import edu.sena.caribeapp.domain.estudiantes.usecase.GetEstudianteByIdUseCase // Necesitamos esto para obtener la clase
import edu.sena.caribeapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de Clase.
 * Maneja el estado de la UI y la obtención de datos de la clase, foros y simulacros.
 */
@HiltViewModel
class ClassViewModel @Inject constructor(
    private val getEstudianteByIdUseCase: GetEstudianteByIdUseCase, // Necesitamos esto para obtener la clase
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassUiState())
    val uiState: StateFlow<ClassUiState> = _uiState.asStateFlow()

    // Obtiene el ID de la clase de los argumentos de navegación
    private val claseId: String = savedStateHandle.get<String>("claseId")
        ?: throw IllegalStateException("Clase ID no encontrado en los argumentos de navegación.")

    // ID del estudiante logueado (simulado por ahora, en una app real se obtendría de la sesión)
    private val loggedInEstudianteId: String = "6845d826441f914e0a7475d2" // ID de ejemplo de tu JSON

    init {
        loadClassData()
    }

    /**
     * Carga la información de la clase, los foros y los simulacros.
     */
    fun loadClassData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            when (val result = getEstudianteByIdUseCase(loggedInEstudianteId)) {
                is Resource.Success -> {
                    val estudiante = result.data
                    if (estudiante != null) {
                        // Encuentra la clase con el ID correcto
                        val clase = estudiante.clasesICFES?.find { it.id == claseId }

                        if (clase != null) {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                clase = clase,
                                foros = clase.foros,
                                simulacros = clase.simulacros,
                                errorMessage = null
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = "No se encontró la clase con ID $claseId"
                            )
                        }
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
                        errorMessage = result.message ?: "Error al cargar los datos."
                    )
                }
                is Resource.Loading -> {
                    // Estado de carga ya manejado
                }
            }
        }
    }

    /**
     * Restablece el estado de la UI de la pantalla de Clase.
     */
    fun resetUiState() {
        _uiState.value = ClassUiState()
    }
}
