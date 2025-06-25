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
    // Estado para la pregunta actual y la opción seleccionada
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val currentQuestionText = "What is the capital of South Korea?"
    val options = listOf("Pyongyang", "Taegu", "Seoul", "Kitakyushu")
    val currentQuestionNumber = 2
    val totalQuestions = 10

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
                onBackClick = { /* TODO: Implement navigation back */ },
                onMenuClick = { /* TODO: Implement menu action */ }
            )
            ProgressBarWithText(
                currentQuestion = currentQuestionNumber,
                totalQuestions = totalQuestions
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuizCategoryCard(
                category = "Geography",
                questionNumbrer = currentQuestionNumber,
                totalQuestions = totalQuestions
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuestionText(question = currentQuestionText)
            Spacer(modifier = Modifier.height(8.dp))

            options.forEach { option ->
                OptionButton(
                    text = option,
                    isSelected = selectedOption == option,
                    onClick = { onOptionSelected(option) }
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Empuja los botones de navegación al final

            QuizNavigationButtons(
                onPreviousClick = { /* TODO: Implement previous question logic */ },
                onNextClick = { /* TODO: Implement next question logic */ }
            )
        }
    }
}

