package edu.sena.caribeapp.presentation.quiz.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.sena.caribeapp.ui.theme.CaribeAppTheme

@Composable
fun QuestionText (
    question: String,
    modifier: Modifier = Modifier
) {

    Text(
        text = question,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        ),
        textAlign = TextAlign.Start, // Alineación a la i   zquierda como en la imagen
        color = Color.Black,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )

}

@Preview(showBackground = true)
@Composable
fun PreviewQuestionText() {
    CaribeAppTheme {
        QuestionText(question = "Cual es la principal diferencia entre Monarquia y Democracia")
    }
}