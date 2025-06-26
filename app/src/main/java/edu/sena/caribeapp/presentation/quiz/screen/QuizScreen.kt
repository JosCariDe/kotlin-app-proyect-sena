package edu.sena.caribeapp.presentation.quiz.screen


import android.widget.Toast
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
import android.util.Log // Importar Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.erroMessage) {
        uiState.erroMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.resetUiState()
        }
    }

    if (uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Text("Cargando datos...", modifier = Modifier.padding(top = 8.dp))
        }
    }else {
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
                    onBackClick = {
                        navController.navigate(
                            AppScreens.SimulacroScreen.createRoute(
                                estudianteId = uiState.estudiante!!.id,
                                claseId = uiState.clase!!.id,
                                simulacroId = uiState.simulacro!!.id
                            )
                        )
                                  },
                    onMenuClick = { /* TODO: Implement menu action */ }
                )
                ProgressBarWithText(
                    currentQuestion = currentQuestionNumber,
                    totalQuestions = totalQuestions
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (currentQuestion == null) {
                    Log.e("QuizScreen", "Error: currentQuestion es nulo al intentar renderizar la pantalla.")
                    // Puedes mostrar un indicador de carga o un mensaje de error aquí
                    // CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    // Text("Cargando pregunta...", modifier = Modifier.padding(top = 8.dp))
                } else {
                    QuizCategoryCard(
                        category = currentQuestion.area ?: "General", // Usa la categoría de la pregunta
                        questionNumbrer = currentQuestionNumber,
                        totalQuestions = totalQuestions
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    QuestionText(question = currentQuestion.enunciado ?: "Cargando pregunta...") // Usa el enunciado de la pregunta
                    Spacer(modifier = Modifier.height(8.dp))

                    currentQuestion.opciones?.forEach { option ->
                        OptionButton(
                            text = option.texto,
                            isSelected = selectedOption == option.texto,
                            onClick = { onOptionSelected(option.texto) }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f)) // Empuja los botones de navegación al final

                QuizNavigationButtons(
                    onPreviousClick = {
                        val currentQuestionIndex = uiState.indexPreguntaActual ?: 0
                        val estudianteId = uiState.estudiante?.id ?: ""
                        val claseId = uiState.clase?.id ?: ""
                        val simulacroId = uiState.simulacro?.id ?: ""
                        val listaIdPreguntas = uiState.simulacro?.listaIdPreguntas ?: emptyList()

                        if (currentQuestionIndex > 0 && estudianteId.isNotEmpty() && claseId.isNotEmpty() && simulacroId.isNotEmpty()) {
                            val previousQuestionId = listaIdPreguntas[currentQuestionIndex - 1]
                            navController.navigate(
                                AppScreens.QuizScreen.createRoute(
                                    estudianteId = estudianteId,
                                    claseId = claseId,
                                    simulacroId = simulacroId,
                                    preguntaId = previousQuestionId,
                                    indexActual = (currentQuestionIndex - 1).toString(),
                                    cantidadPreguntas = listaIdPreguntas.size.toString()
                                )
                            )
                        } else {
                            Toast.makeText(context, "No se puede ir a la pregunta anterior. Datos incompletos.", Toast.LENGTH_SHORT).show()
                            Log.e("QuizScreen", "Datos incompletos para navegación anterior: estudianteId=$estudianteId, claseId=$claseId, simulacroId=$simulacroId")
                        }
                    },
                    onNextClick = {
                        val currentQuestionIndex = uiState.indexPreguntaActual ?: 0
                        val totalQuestions = uiState.cantidadPreguntas ?: 0
                        val estudianteId = uiState.estudiante?.id ?: ""
                        val claseId = uiState.clase?.id ?: ""
                        val simulacroId = uiState.simulacro?.id ?: ""
                        val listaIdPreguntas = uiState.simulacro?.listaIdPreguntas ?: emptyList()

                        if (currentQuestionIndex < totalQuestions - 1 && estudianteId.isNotEmpty() && claseId.isNotEmpty() && simulacroId.isNotEmpty()) {
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
                            Toast.makeText(context, "No se puede ir a la siguiente pregunta. Datos incompletos o fin del simulacro.", Toast.LENGTH_SHORT).show()
                            Log.e("QuizScreen", "Datos incompletos para navegación siguiente: estudianteId=$estudianteId, claseId=$claseId, simulacroId=$simulacroId")
                        }
                    }
                )
            }
        }
    }

}
