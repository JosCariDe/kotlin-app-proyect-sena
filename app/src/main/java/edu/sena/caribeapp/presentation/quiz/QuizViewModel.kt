package edu.sena.caribeapp.presentation.quiz


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.sena.caribeapp.domain.estudiantes.usecase.GetEstudianteByIdUseCase
import edu.sena.caribeapp.domain.preguntas.usecase.GetPreguntaByIdUseCase
import edu.sena.caribeapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getPreguntaByIdUseCase: GetPreguntaByIdUseCase,
    private val getEstudianteByIdUseCase: GetEstudianteByIdUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val idPregunta: String = savedStateHandle.get<String>("preguntaId")
        ?: throw IllegalStateException("Id Pregunta NO ENCONTRADO en los parametros de navegacion")

    private val preguntaActual: Int = savedStateHandle.get<Int>("indexActual")
        ?: throw IllegalStateException("indice de la pregunta NO ENCONTRADO en los parametros de navegacion")

    private val cantidadPreguntas: Int = savedStateHandle.get<Int>("cantidadPreguntas")
        ?: throw IllegalStateException("Id Pregunta NO ENCONTRADO en los parametros de navegacion")

    private val idEstudiante: String = savedStateHandle.get<String>("estudianteId")
        ?: throw IllegalStateException("Id Estudiante NO ENCONTRADO en los parametros de navegacion")

    private val idClase: String = savedStateHandle.get<String>("claseId")
        ?: throw IllegalStateException("Id Clase NO ENCONTRADO en los parametros de navegacion")

    private val idSimulacro: String = savedStateHandle.get<String>("simulacroId")
        ?: throw IllegalStateException("Id Simulacro NO ENCONTRADO en los parametros de navegacion")

    init {
        // Cuando el ViewModel se crea, inicia la carga de datos usando el ID obtenido
        loadQuizData()
    }

    fun loadQuizData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Cargar el estudiante
            when (val estudianteResult = getEstudianteByIdUseCase(idEstudiante)) {
                is Resource.Success -> {
                    val estudiante = estudianteResult.data
                    if (estudiante != null) {
                        val targetClase = estudiante.clasesICFES
                            ?.find { it.id == idClase }

                        val targetSimulacro = targetClase
                            ?.simulacros
                            ?.find { it.id == idSimulacro }

                        // Cargar la pregunta
                        when (val preguntaResult = getPreguntaByIdUseCase(idPregunta)) {
                            is Resource.Success -> {
                                val pregunta = preguntaResult.data
                                if (pregunta != null) {
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        pregunta = pregunta,
                                        estudiante = estudiante,
                                        clase = targetClase,
                                        simulacro = targetSimulacro,
                                        indexPreguntaActual = preguntaActual,
                                        cantidadPreguntas = cantidadPreguntas,
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
                                    erroMessage = preguntaResult.message ?: "Error al cargar la pregunta."
                                )
                            }
                            is Resource.Loading -> { /* No hacer nada, ya se maneja al inicio */ }
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            erroMessage = "No se encontró el estudiante."
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        erroMessage = estudianteResult.message ?: "Error al cargar los datos del estudiante."
                    )
                }
                is Resource.Loading -> { /* No hacer nada, ya se maneja al inicio */ }
            }
        }
    }

    fun resetUiState() {
        _uiState.value = QuizUiState()
    }

}
