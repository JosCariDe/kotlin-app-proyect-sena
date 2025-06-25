package edu.sena.caribeapp.presentation.quiz.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.sena.caribeapp.presentation.clase.ClassViewModel
import edu.sena.caribeapp.presentation.navigation.AppScreens
import edu.sena.caribeapp.presentation.quiz.QuizViewModel
import edu.sena.caribeapp.ui.theme.CaribeAppTheme

import edu.sena.caribeapp.ui.theme.Primary
import edu.sena.caribeapp.ui.theme.PrimaryBackGround
import edu.sena.caribeapp.ui.theme.Secondary

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: QuizViewModel = hiltViewModel()
    ) {
    val uiState by viewModel.uiState.collectAsState()

    // Estado para la opción seleccionada
    var selectedOption by remember { mutableStateOf<String?>(null) }

    val currentQuestion = uiState.pregunta
    val currentQuestionNumber = uiState.indexPreguntaActual ?: 0
    val totalQuestions = uiState.cantidadPreguntas ?: 0

    // Para simular la selección de una opción
    val onOptionSelected: (String) -> Unit = { option ->
        selectedOption = option
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryBackGround) // Fondo general de la pantalla
            .statusBarsPadding(), // Para manejar el padding de la barra de estado
        color = PrimaryBackGround // Asegúrate de que el Surface tenga el color de fondo
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            QuizTopBar(
                title = "Quiz Mode",
                onBackClick = { navController.popBackStack() }, // Implementa la navegación de regreso
                onMenuClick = { /* TODO: Implement menu action */ }
            )
            ProgressBarWithText(
                currentQuestion = currentQuestionNumber,
                totalQuestions = totalQuestions
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuizCategoryCard(
                category = currentQuestion?.area ?: "General", // Usa la categoría de la pregunta
                questionNumbrer = currentQuestionNumber,
                totalQuestions = totalQuestions
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuestionText(question = currentQuestion?.enunciado ?: "Cargando pregunta...") // Usa el enunciado de la pregunta
            Spacer(modifier = Modifier.height(8.dp))

            currentQuestion?.opciones?.forEach { option ->
                OptionButton(
                    text = option.texto,
                    isSelected = selectedOption == option.texto,
                    onClick = { onOptionSelected(option.texto) }
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Empuja los botones de navegación al final

            QuizNavigationButtons(
                onPreviousClick = {
                    val currentQuestionIndex = uiState.indexPreguntaActual ?: 0
                    val estudianteId = uiState.estudiante?.id.toString()
                    val claseId = uiState.clase?.id
                    val simulacroId = uiState.simulacro?.id
                    val listaIdPreguntas = uiState.simulacro?.listaIdPreguntas ?: emptyList()

                    if (currentQuestionIndex > 0) {
                        val previousQuestionId = listaIdPreguntas[currentQuestionIndex - 1]
                        navController.navigate(
                            AppScreens.QuizScreen.createRoute(
                                estudianteId = estudianteId,
                                claseId = uiState.clase!!.id,
                                simulacroId = uiState.simulacro!!.id,
                                preguntaId = previousQuestionId,
                                indexActual = (currentQuestionIndex - 1).toString(),
                                cantidadPreguntas = listaIdPreguntas.size.toString()
                            )
                        )
                    } else {
                        // Opcional: Mostrar un Toast o deshabilitar el botón
                    }
                },
                onNextClick = {
                    val currentQuestionIndex = uiState.indexPreguntaActual ?: 0
                    val totalQuestions = uiState.cantidadPreguntas ?: 0
                    val estudianteId = uiState.estudiante?.id.toString()
                    val claseId = uiState.clase?.id.toString()
                    val simulacroId = uiState.simulacro?.id.toString()
                    val listaIdPreguntas = uiState.simulacro?.listaIdPreguntas ?: emptyList()

                    if (currentQuestionIndex < totalQuestions - 1) {
                        val nextQuestionId = listaIdPreguntas[currentQuestionIndex + 1]
                        navController.navigate(
                            AppScreens.QuizScreen.createRoute(
                                estudianteId = estudianteId,
                                claseId = claseId,
                                simulacroId = simulacroId,
                                preguntaId = nextQuestionId,
                                indexActual = (currentQuestionIndex + 1).toString(),
                                cantidadPreguntas = listaIdPreguntas.size.toString()
                            )
                        )
                    } else {
                        // Opcional: Mostrar un Toast o navegar a una pantalla de resultados
                    }
                }
            )
        }
    }
}
