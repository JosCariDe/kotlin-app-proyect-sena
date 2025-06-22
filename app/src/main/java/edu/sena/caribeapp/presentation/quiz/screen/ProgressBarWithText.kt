package edu.sena.caribeapp.presentation.quiz.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import edu.sena.caribeapp.ui.theme.CaribeAppTheme

import edu.sena.caribeapp.ui.theme.Primary
import edu.sena.caribeapp.ui.theme.Secondary

@Composable
fun ProgressBarWithText(
    currentQuestion: Int,
    totalQuestions: Int,
    modifier: Modifier = Modifier
){

    val progress = currentQuestion.toFloat() / totalQuestions.toFloat()

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Secondary)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "$currentQuestion/$totalQuestions",
                color = Color.White,
                fontSize = 12.sp
            )
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .padding(start = 8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Secondary, // Color del progreso completado
            trackColor = Primary.copy(alpha = 0.3f), // Color de la barra de progreso sin completar
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewProgressBarWithText() {
    CaribeAppTheme {
        ProgressBarWithText(currentQuestion = 2, totalQuestions = 10)
    }
}