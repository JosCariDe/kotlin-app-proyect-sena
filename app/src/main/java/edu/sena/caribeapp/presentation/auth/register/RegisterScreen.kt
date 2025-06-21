// app/src/main/java/edu/sena/caribeapp/presentation/auth/register/RegisterScreen.kt
package edu.sena.caribeapp.presentation.auth.register

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.sena.caribeapp.R
import edu.sena.caribeapp.presentation.common.components.CustomTextField
import edu.sena.caribeapp.presentation.common.components.PasswordTextField
import edu.sena.caribeapp.presentation.navigation.AppScreens
import edu.sena.caribeapp.ui.theme.CaribeAppTheme


/**
 * Composable para la pantalla de Registro.
 * Muestra la UI de registro y maneja las interacciones con el ViewModel.
 *
 * @param navController El controlador de navegación.
 * @param viewModel El ViewModel de Registro, inyectado por Hilt.
 */
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val navigateToHome by viewModel.navigateToHome.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.errorMessage, key2 = navigateToHome) {
        uiState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.resetUiState()
        }

        navigateToHome?.let { estudianteId -> // ¡Cambiado!
            Toast.makeText(context, "Registro exitoso. ¡Bienvenido!", Toast.LENGTH_LONG).show()
            navController.navigate(AppScreens.HomeScreen.createRoute(estudianteId)) { // ¡Cambiado!
                popUpTo(AppScreens.LoginScreen.route) {
                    inclusive = true
                }
            }
            viewModel.onNavigationHandled() // Notifica al ViewModel que la navegación fue manejada
            viewModel.resetUiState()
        }

    }

    RegisterContent(
        uiState = uiState,
        onFullNameChange = viewModel::onFullNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onDepartmentChange = viewModel::onDepartmentChange,
        onMunicipalityChange = viewModel::onMunicipalityChange,
        onGradeChange = viewModel::onGradeChange,
        onRegisterClick = viewModel::register,
        onLoginClick = { navController.popBackStack() } // Vuelve a la pantalla anterior (Login)
    )
}

/**
 * Composable que define el contenido visual de la pantalla de Registro.
 * Es una función pura (sin ViewModel) para facilitar la vista previa.
 */
@Composable
fun RegisterContent(
    uiState: RegisterUiState,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onDepartmentChange: (String) -> Unit,
    onMunicipalityChange: (String) -> Unit,
    onGradeChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7bb369))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crea tu cuenta",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
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
                CustomTextField(
                    value = uiState.fullName,
                    onValueChange = onFullNameChange,
                    label = "Nombre Completo",
                    imeAction = ImeAction.Next
                )
                CustomTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = "Email",
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
                PasswordTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    label = "Contraseña",
                    imeAction = ImeAction.Next
                )
                CustomTextField(
                    value = uiState.department,
                    onValueChange = onDepartmentChange,
                    label = "Departamento",
                    imeAction = ImeAction.Next
                )
                CustomTextField(
                    value = uiState.municipality,
                    onValueChange = onMunicipalityChange,
                    label = "Municipio",
                    imeAction = ImeAction.Next
                )
                CustomTextField(
                    value = uiState.grade,
                    onValueChange = onGradeChange,
                    label = "Grado",
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                    onDone = onRegisterClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7bb369))
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Registrarse", fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onLoginClick) {
                    Text("¿Ya tienes cuenta? Inicia sesión", color = Color(0xFF7bb369), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun RegisterScreenPreview() {
    CaribeAppTheme {
        RegisterContent(
            uiState = RegisterUiState(),
            onFullNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onDepartmentChange = {},
            onMunicipalityChange = {},
            onGradeChange = {},
            onRegisterClick = {},
            onLoginClick = {}
        )
    }
}
