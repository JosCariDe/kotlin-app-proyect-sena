package edu.sena.caribeapp.presentation.quiz.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.sena.caribeapp.ui.theme.CaribeAppTheme
import edu.sena.caribeapp.ui.theme.Primary
import edu.sena.caribeapp.ui.theme.Secondary


@Composable
fun QuizCategoryCard(
    category: String,
    questionNumbrer: Int,
    totalQuestions: Int,
    modifier: Modifier = Modifier
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.School, // Icono de grado
                    contentDescription = "Categoría",
                    tint = Primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = category,
                    style = MaterialTheme.typography.titleLarge,
                    color = Primary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "PREGUNTA $questionNumbrer DE $totalQuestions",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewQuizCategoryCard() {
    CaribeAppTheme {
        QuizCategoryCard(
            category = "Geography",
            questionNumbrer = 2,
            totalQuestions = 10
        )
    }
}