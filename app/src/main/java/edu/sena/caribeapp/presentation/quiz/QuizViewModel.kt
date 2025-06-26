package edu.sena.caribeapp.presentation.quiz


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.sena.caribeapp.domain.estudiantes.usecase.GetEstudianteByIdUseCase
import edu.sena.caribeapp.domain.preguntas.usecase.GetPreguntaByIdUseCase
import edu.sena.caribeapp.util.Resource
import android.util.Log // Importar Log
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

    private val idEstudiante: String = savedStateHandle.get<String>("estudianteId")
        ?: run { Log.e("QuizViewModel", "Error: estudianteId NO ENCONTRADO en los parametros de navegacion"); throw IllegalStateException("Id Estudiante NO ENCONTRADO en los parametros de navegacion") }

    private val idClase: String = savedStateHandle.get<String>("claseId")
        ?: run { Log.e("QuizViewModel", "Error: claseId NO ENCONTRADO en los parametros de navegacion"); throw IllegalStateException("Id Clase NO ENCONTRADO en los parametros de navegacion") }

    private val idSimulacro: String = savedStateHandle.get<String>("simulacroId")
        ?: run { Log.e("QuizViewModel", "Error: simulacroId NO ENCONTRADO en los parametros de navegacion"); throw IllegalStateException("Id Simulacro NO ENCONTRADO en los parametros de navegacion") }

    private val idPregunta: String = savedStateHandle.get<String>("preguntaId")
        ?: run { Log.e("QuizViewModel", "Error: preguntaId NO ENCONTRADO en los parametros de navegacion"); throw IllegalStateException("Id Pregunta NO ENCONTRADO en los parametros de navegacion") }

    private val preguntaActual: Int = savedStateHandle.get<String>("indexActual")?.toIntOrNull()
        ?: run { Log.e("QuizViewModel", "Error: indexActual NO ENCONTRADO o formato inválido en los parámetros de navegación"); throw IllegalStateException("Índice de la pregunta NO ENCONTRADO o formato inválido en los parámetros de navegación") }

    private val cantidadPreguntas: Int = savedStateHandle.get<String>("cantidadPreguntas")?.toIntOrNull()
        ?: run { Log.e("QuizViewModel", "Error: cantidadPreguntas NO ENCONTRADA o formato inválido en los parámetros de navegación"); throw IllegalStateException("Cantidad de preguntas NO ENCONTRADA o formato inválido en los parámetros de navegación") }




    init {
        // Cuando el ViewModel se crea, inicia la carga de datos usando el ID obtenido
        loadQuizData()
    }

    fun loadQuizData() {
        viewModelScope.launch {
            Log.d("QuizViewModel", "Iniciando loadQuizData()")
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Cargar el estudiante
            when (val estudianteResult = getEstudianteByIdUseCase(idEstudiante)) {
                is Resource.Success -> {
                    val estudiante = estudianteResult.data
                    if (estudiante != null) {
                        val targetClase = estudiante.clasesICFES
                            ?.find {
                                Log.d("QuizViewModel", "Comparando clase: ${it.id} con $idClase")
                                it.id == idClase
                            }

                        if (targetClase == null) {
                            Log.e("QuizViewModel", "Error: Clase con ID '$idClase' no encontrada para el estudiante '$idEstudiante'. Clases disponibles: ${estudiante.clasesICFES?.map { it.id }}")
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                erroMessage = "Clase no encontrada."
                            )
                            return@launch // Salir de la corrutina si la clase no se encuentra
                        }
                        Log.d("QuizViewModel", "Clase encontrada: ${targetClase.id}")

                        val targetSimulacro = targetClase
                            .simulacros
                            .find {
                                Log.d("QuizViewModel", "Comparando simulacro: ${it.id} con $idSimulacro")
                                it.id == idSimulacro
                            }

                        if (targetSimulacro == null) {
                            Log.e("QuizViewModel", "Error: Simulacro con ID '$idSimulacro' no encontrado en la clase '${targetClase.id}'. Simulacros disponibles: ${targetClase.simulacros?.map { it.id }}")
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                erroMessage = "Simulacro no encontrado."
                            )
                            return@launch // Salir de la corrutina si el simulacro no se encuentra
                        }
                        Log.d("QuizViewModel", "Simulacro encontrado: ${targetSimulacro.id}")

                        Log.d("QuizViewModel", "Intentando cargar pregunta con ID: $idPregunta")
                        // Cargar la pregunta
                        when (val preguntaResult = getPreguntaByIdUseCase(idPregunta)) {
                            is Resource.Success -> {
                                val pregunta = preguntaResult.data
                                if (pregunta != null) {
                                    Log.d("QuizViewModel", "Pregunta cargada exitosamente: ${pregunta.enunciado}")
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
                                    Log.e("QuizViewModel", "Error: Pregunta nula después de Resource.Success.")
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        erroMessage = "No se encontró la pregunta."
                                    )
                                }
                            }
                            is Resource.Error -> {
                                Log.e("QuizViewModel", "Error al cargar la pregunta: ${preguntaResult.message}")
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    erroMessage = preguntaResult.message ?: "Error al cargar la pregunta."
                                )
                            }
                            is Resource.Loading -> {
                                Log.d("QuizViewModel", "Cargando pregunta...")
                                /* No hacer nada, ya se maneja al inicio */
                            }
                        }
                    } else {
                        Log.e("QuizViewModel", "Error: Estudiante nulo después de Resource.Success.")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            erroMessage = "No se encontró el estudiante."
                        )
                    }
                }
                is Resource.Error -> {
                    Log.e("QuizViewModel", "Error al cargar los datos del estudiante: ${estudianteResult.message}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        erroMessage = estudianteResult.message ?: "Error al cargar los datos del estudiante."
                    )
                }
                is Resource.Loading -> {
                    Log.d("QuizViewModel", "Cargando datos del estudiante...")
                    /* No hacer nada, ya se maneja al inicio */
                }
            }
        }
    }

    fun resetUiState() {
        _uiState.value = QuizUiState()
    }

}
