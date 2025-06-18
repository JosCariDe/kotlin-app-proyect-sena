// app/src/main/java/edu/sena/caribeapp/MainActivity.kt
package edu.sena.caribeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint // ¡Nueva importación!
import edu.sena.caribeapp.presentation.navigation.AppScreens // ¡Nueva importación!
import edu.sena.caribeapp.presentation.splash.SplashScreen // ¡Nueva importación!
import edu.sena.caribeapp.ui.theme.CaribeAppTheme

/**
 * Actividad principal de la aplicación.
 * Anotada con @AndroidEntryPoint para habilitar la inyección de dependencias con Hilt.
 * Contiene el NavHost para la navegación de la aplicación.
 */
@AndroidEntryPoint // Anotación de Hilt para Activities
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaribeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Configuración del NavHost para la navegación
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AppScreens.SplashScreen.route // La pantalla de inicio es el Splash
                    ) {
                        // Define las rutas de navegación
                        composable(route = AppScreens.SplashScreen.route) {
                            SplashScreen(navController = navController)
                        }
                        // Aquí se añadirán otras pantallas como LoginScreen, HomeScreen, etc.
                        // composable(route = AppScreens.LoginScreen.route) { LoginScreen(...) }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CaribeAppTheme {
        // Puedes poner un Composable de prueba aquí si lo deseas
    }
}
