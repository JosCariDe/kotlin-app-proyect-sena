package edu.sena.caribeapp.presentation.simulacro

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class SimulacroViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(SimulacroUiState())
    val uiState: StateFlow<SimulacroUiState> = _uiState.asStateFlow()

    private val objSimulacro: Simulacro = savedStateHandle.get<Simulacro>("simulacro")
        ?: throw IllegalStateException("Obj Clase No encontrado en los parametros de navegacion")


    // Funcion para cargar la clase en el uiStateClase

    fun loadSimulacroData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                simulacro = objSimulacro,
                errorMessage = null,
                numPreguntas = objSimulacro.listaIdPreguntas.size
            )
        }
    }

    fun resetUiState() {
        _uiState.value = SimulacroUiState()
    }

}