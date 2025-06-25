package edu.sena.caribeapp.presentation.simulacro

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import edu.sena.caribeapp.domain.estudiantes.usecase.GetEstudianteByIdUseCase
import edu.sena.caribeapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class SimulacroViewModel @Inject constructor(
    private val getEstudianteByIdUseCase: GetEstudianteByIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(SimulacroUiState())
    val uiState: StateFlow<SimulacroUiState> = _uiState.asStateFlow()

    private val idSimulacro: String = savedStateHandle.get<String>("simulacroId")
        ?: throw IllegalStateException("Obj Clase No encontrado en los parametros de navegacion")

    private val estudianteId: String = savedStateHandle.get<String>("estudianteId")
        ?: throw IllegalStateException("Estudiante ID no encontrado en los argumentos de navegación.")

    private val claseId: String = savedStateHandle.get<String>("claseId")
        ?: throw IllegalStateException("Clase con ID no encontrado en los argumentos de navegación.")

    private lateinit var objSimulacro : Simulacro
    // Funcion para cargar la clase en el uiStateClase

    init {
        // Cuando el ViewModel se crea, inicia la carga de datos usando el ID obtenido
        loadSimulacroData()
    }

    fun loadSimulacroData() {
        viewModelScope.launch {

            when (val result = getEstudianteByIdUseCase(estudianteId)) { // ¡Usa el ID obtenido!
                is Resource.Success -> {
                    val estudiante = result.data
                    if (estudiante != null) {
                        val targetClase = estudiante.clasesICFES
                            ?.find { it.id == claseId }
                        val targetSimulacro = targetClase
                            ?.simulacros
                            ?.find { it.id == idSimulacro }

                        if (targetSimulacro != null) {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                estudiante = estudiante,
                                clase = targetClase,
                                simulacro = targetSimulacro,
                                numPreguntas = targetSimulacro.listaIdPreguntas.size,
                                errorMessage = null
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = "Simulacro no encontrado para la clase especificada."
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
                        errorMessage = result.message ?: "Error al cargar los datos del perfil."
                    )
                }

                is Resource.Loading<*> -> TODO()
            }

        }
    }

    fun resetUiState() {
        _uiState.value = SimulacroUiState()
    }

}