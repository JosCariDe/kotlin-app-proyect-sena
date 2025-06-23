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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import edu.sena.caribeapp.presentation.auth.login.LoginScreen // ¡Nueva importación!
import edu.sena.caribeapp.presentation.auth.register.RegisterScreen
import edu.sena.caribeapp.presentation.clase.ClassScreen
import edu.sena.caribeapp.presentation.home.HomeScreen
import edu.sena.caribeapp.presentation.navigation.AppScreens
import edu.sena.caribeapp.presentation.simulacro.SimulacroScreen
import edu.sena.caribeapp.presentation.splash.SplashScreen
import edu.sena.caribeapp.ui.theme.CaribeAppTheme

/**
 * Actividad principal de la aplicación.
 * Anotada con @AndroidEntryPoint para habilitar la inyección de dependencias con Hilt.
 * Contiene el NavHost para la navegación de la aplicación.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaribeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AppScreens.SplashScreen.route
                    ) {
                        composable(route = AppScreens.SplashScreen.route) {
                            SplashScreen(navController = navController)
                        }
                        // ¡Añade esta ruta para la pantalla de Login!
                        composable(route = AppScreens.LoginScreen.route) {
                            LoginScreen(navController = navController)
                        }

                        // ¡Añade esta ruta para la pantalla de Register!
                        composable(route = AppScreens.RegisterScreen.route) {
                            RegisterScreen(navController = navController)
                        }
                        // Ruta de Home con el Id de estudiante
                        composable(
                            route = AppScreens.HomeScreen.route,
                            arguments = listOf(navArgument("estudianteId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            // Extrae el argumento estudianteId de backStackEntry
                            val estudianteId = backStackEntry.arguments?.getString("estudianteId")
                            // Asegúrate de que el ID no sea nulo antes de pasar a HomeScreen
                            if (estudianteId != null) {
                                HomeScreen(navController = navController) // HomeScreen obtendrá el ID del ViewModel
                            } else {
                                // Manejar el caso de ID nulo, quizás navegar de vuelta a Login
                                navController.navigate(AppScreens.LoginScreen.route) {
                                    popUpTo(AppScreens.SplashScreen.route) { inclusive = true }
                                }
                            }
                        }
                        //RUta para clase
                        composable(
                            route = AppScreens.ClassScreen.route,
                            arguments = listOf(navArgument("claseId") { type = NavType.StringType },navArgument("estudianteId") { type = NavType.StringType } )
                        ) { backStackEntry ->
                            val claseId = backStackEntry.arguments?.getString("claseId")
                            val estudianteId = backStackEntry.arguments?.getString("estudianteId")
                            if (claseId != null && estudianteId != null) {
                                ClassScreen(navController = navController)
                            } else {
                                // Manejar el caso de ID nulo, quizás navegar de vuelta a HomeScreen
                                // o mostrar un error
                            }
                        }
                        //Ruta para simulacro
                        composable (
                            route = AppScreens.SimulacroScreen.route,
                            arguments = listOf(navArgument("estudianteId") {type = NavType.StringType}, navArgument("claseId") {type = NavType.StringType}, navArgument("simulacroId") {type = NavType.StringType} )
                        ) { backStackEntry ->
                            val estudianteId = backStackEntry.arguments?.getString("estudianteId")
                            val claseId = backStackEntry.arguments?.getString("claseId")
                            val simulacroId = backStackEntry.arguments?.getString("simulacroId")
                            if (estudianteId != null && claseId != null  && simulacroId != null ) {
                                SimulacroScreen(navController = navController)
                            } else {
                                // Manejar el caso de ID nulo, quizás navegar de vuelta a HomeScreen
                                // o mostrar un error
                            }
                        }
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
