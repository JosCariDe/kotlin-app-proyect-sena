// app/src/main/java/edu/sena/caribeapp/presentation/auth/login/LoginScreen.kt
package edu.sena.caribeapp.presentation.auth.login

import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LightingColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.sena.caribeapp.R
import edu.sena.caribeapp.presentation.common.components.CustomTextField
import edu.sena.caribeapp.presentation.common.components.PasswordTextField
import edu.sena.caribeapp.presentation.navigation.AppScreens
import edu.sena.caribeapp.ui.theme.CaribeAppTheme

/**
 * Composable para la pantalla de Login.
 * Muestra la UI de login y maneja las interacciones con el ViewModel.
 *
 * @param navController El controlador de navegación.
 * @param viewModel El ViewModel de Login, inyectado por Hilt.
 */
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    // Observa el estado de la UI del ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val navigateToHome by viewModel.navigateToHome.collectAsState() //Observacion para navegacion
    val context = LocalContext.current // Obtiene el contexto de Android para mostrar Toasts

    // LaunchedEffect para manejar efectos secundarios (navegación, mostrar Toast)
    LaunchedEffect(key1 = uiState.errorMessage, key2 = navigateToHome) {
        // Muestra Toast si hay un mensaje de error
        uiState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.resetUiState() // Limpia el estado del error después de mostrarlo
        }

        // Navega a HomeScreen si el login fue exitoso y tenemos un ID de estudiante
        navigateToHome?.let { estudianteId -> // ¡Cambiado!
            navController.navigate(AppScreens.HomeScreen.createRoute(estudianteId)) { // ¡Cambiado!
                popUpTo(AppScreens.LoginScreen.route) {
                    inclusive = true
                }
            }
            viewModel.onNavigationHandled() // Notifica al ViewModel que la navegación fue manejada
            viewModel.resetUiState() // Restablece el estado después de la navegación
        }
    }

    // Contenido principal de la pantalla de Login
    LoginContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange, // Es equivalente a escribir una lambda así: onEmailChange = { newEmail -> viewModel.onEmailChange(newEmail) }.
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::login,
        onRegisterClick = { navController.navigate(AppScreens.RegisterScreen.route) } // Navega a registro
    )
}

/**
 * Composable que define el contenido visual de la pantalla de Login.
 * Es una función pura (sin ViewModel) para facilitar la vista previa.
 */
@Composable
fun LoginContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7bb369)), // Color de fondo
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo de la aplicación
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo de la aplicación",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Preparate ICFES Caribe",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Tarjeta de Login
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Inicia sesión para mayor contenido.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Campo de Email
                CustomTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = "Email",
                    keyboardType = KeyboardType.Email,
                    imeAction = androidx.compose.ui.text.input.ImeAction.Next // Pasa al siguiente campo
                )

                // Campo de Contraseña
                PasswordTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    label = "Contraseña",
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done, // Finaliza el teclado
                    onDone = onLoginClick // Llama a login al presionar Done
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de Iniciar Sesión
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading, // Deshabilita el botón durante la carga
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7bb369)) // Color del botón
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Iniciar Sesión", fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text("O inicia sesión con:", modifier = Modifier.padding(vertical = 8.dp))

                // Botones de Google y Apple (simulados por ahora)
                Button(
                    onClick = { /* TODO: Implementar login con Google */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("Continue with Google", color = Color.Black)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* TODO: Implementar login con Apple */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("Continue with Apple", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de "Sign Up here"
                TextButton(onClick = onRegisterClick) {
                    Text("Sign Up here", color = Color(0xFF7bb369), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun LoginScreenPreview() {
    CaribeAppTheme {
        LoginContent(
            uiState = LoginUiState(),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}
