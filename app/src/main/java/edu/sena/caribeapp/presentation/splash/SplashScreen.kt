// app/src/main/java/edu/sena/caribeapp/presentation/splash/SplashScreen.kt
package edu.sena.caribeapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.sena.caribeapp.R // Necesitarás añadir el logo en res/drawable
import edu.sena.caribeapp.presentation.navigation.AppScreens // Aún no creado, lo haremos después
import edu.sena.caribeapp.ui.theme.CaribeAppTheme // Tu tema de la app
import edu.sena.caribeapp.ui.theme.Primary

/**
 * Composable para la pantalla de Splash.
 * Muestra el logo y el nombre de la aplicación, y navega a la siguiente pantalla
 * cuando el ViewModel indica que está listo.
 *
 * @param navController El controlador de navegación para la navegación entre pantallas.
 * @param viewModel El ViewModel del Splash, inyectado por Hilt.
 */
@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel() // Hilt inyecta el ViewModel
) {
    // Observa el estado 'isReady' del ViewModel.
    // Cuando 'isReady' cambie, este Composable se recompondrá.
    val isReady by viewModel.isReady.collectAsState()

    // LaunchedEffect se ejecuta cuando el Composable entra en la composición
    // y se relanza si sus claves cambian. Aquí, la clave es 'isReady'.
    LaunchedEffect(key1 = isReady) {
        if (isReady) {
            // Cuando el Splash está listo, navega a la pantalla de Login.
            // popUpTo elimina la pantalla de Splash de la pila de navegación
            // para que el usuario no pueda volver a ella con el botón de atrás.
            navController.navigate(AppScreens.LoginScreen.route) {
                popUpTo(AppScreens.SplashScreen.route) {
                    inclusive = true // Incluye el Splash en la eliminación
                }
            }
        }
    }

    // Diseño de la UI del Splash Screen
    SplashContent()
}

/**
 * Composable que define el contenido visual del Splash Screen.
 * Separado para facilitar la vista previa.
 */
@Composable
fun SplashContent() {
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
            .background(Primary), // Color de fondo (asegúrate de que Green80 esté en Color.kt)
        verticalArrangement = Arrangement.Center, // Centra verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente
    ) {
        // Imagen del logo
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Usa tu logo aquí
            contentDescription = "Logo de la aplicación",
            modifier = Modifier.size(128.dp) // Tamaño del logo
        )
        // Texto del nombre de la aplicación
        Text(
            text = "Preparate ICFES Caribe",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.White // Color del texto
            ),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    CaribeAppTheme {
        // Para la vista previa, necesitamos un NavController simulado
        SplashContent()
    }
}
