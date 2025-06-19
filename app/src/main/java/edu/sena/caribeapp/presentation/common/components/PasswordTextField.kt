// app/src/main/java/edu/sena/caribeapp/presentation/common/components/PasswordTextField.kt
package edu.sena.caribeapp.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Composable reutilizable para un campo de texto de contraseña con visibilidad.
 *
 * @param value El valor actual del campo de texto.
 * @param onValueChange Callback que se invoca cuando el valor del campo cambia.
 * @param label Texto de la etiqueta del campo.
 * @param imeAction Acción del botón "Enter" del teclado (ej., Next, Done).
 * @param onDone Acción a realizar cuando se presiona el botón "Done" del teclado.
 * @param modifier Modificador para aplicar estilos al campo.
 */
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Contraseña",
    imeAction: ImeAction = ImeAction.Done,
    onDone: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Estado para controlar la visibilidad de la contraseña
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Candado"
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, // Tipo de teclado para contraseña
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        // Transforma visualmente el texto (oculta/muestra)
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        // Icono al final para alternar visibilidad
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
