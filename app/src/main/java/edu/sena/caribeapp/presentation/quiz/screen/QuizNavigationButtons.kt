package edu.sena.caribeapp.presentation.quiz.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.sena.caribeapp.ui.theme.CaribeAppTheme
import edu.sena.caribeapp.ui.theme.Primary

@Composable
fun QuizNavigationButtons(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Button (
            onClick = onPreviousClick,
            modifier = Modifier
                .size(56.dp) // Tamaño cuadrado para el botón de retroceso
                .clip(RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Color.White
            )
        ) {
            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Anterior",
                modifier = Modifier.size(48.dp), // The Image will scale the vector to fit this size
                colorFilter = ColorFilter.tint(Color.White),
                contentScale = ContentScale.Fit // Ensures the icon fits and maintains aspect ratio
            )
        }

        Spacer(modifier = Modifier.width(16.dp)) // Espacio entre los botones

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .weight(1f) // El botón "Next" ocupa el espacio restante
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "next",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuizNavigationButtons() {
    CaribeAppTheme {
        QuizNavigationButtons(onPreviousClick = {}, onNextClick = {})
    }
}