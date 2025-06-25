package edu.sena.caribeapp.presentation.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.sena.caribeapp.domain.preguntas.usecase.GetPreguntaByIdUseCase
import edu.sena.caribeapp.presentation.simulacro.SimulacroUiState
import edu.sena.caribeapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getPreguntaByIdUseCase: GetPreguntaByIdUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val idPregunta: String = savedStateHandle.get<String>("preguntaId")
        ?: throw IllegalStateException("Id Pregunta NO ENCONTRADO en los parametros de navegacion")

    private val preguntaActual: String = savedStateHandle.get<String>("indexActual")
        ?: throw IllegalStateException("indice de la pregunta NO ENCONTRADO en los parametros de navegacion")

    private val cantidadPreguntas: String = savedStateHandle.get<String>("cantidadPreguntas")
        ?: throw IllegalStateException("Id Pregunta NO ENCONTRADO en los parametros de navegacion")

    fun loadQuizData() {
        viewModelScope.launch {

            when(val result = getPreguntaByIdUseCase(idPregunta)) {

                is Resource.Success -> {
                    val pregunta = result.data
                    if (pregunta != null) {

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            pregunta = pregunta,
                            erroMessage = null
                        )

                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            erroMessage = "No se encontró la pregunta."
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        erroMessage = result.message ?: "Error al cargar los datos de la pregunta."
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