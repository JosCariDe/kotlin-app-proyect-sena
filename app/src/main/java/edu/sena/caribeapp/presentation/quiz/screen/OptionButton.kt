package edu.sena.caribeapp.presentation.quiz.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.sena.caribeapp.ui.theme.CaribeAppTheme

import edu.sena.caribeapp.ui.theme.Primary
import edu.sena.caribeapp.ui.theme.Secondary

@Composable
fun OptionButton (
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    val backgroundColor = if (isSelected) Primary else Color.White
    val textColor = if (isSelected) Color.White else Color.Black
    val borderColor = if (isSelected) Primary else Color.Gray.copy(alpha = 0.5f)

    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        color = textColor,
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
        fontSize = 18.sp
    )


}

@Preview(showBackground = true)
@Composable
fun PreviewOptionButtonSelected() {
    CaribeAppTheme {
        OptionButton(text = "Seoul", isSelected = true, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOptionButtonUnselected() {
    CaribeAppTheme {
        OptionButton(text = "Pyongyang", isSelected = false, onClick = {})
    }
}