// app/src/main/java/edu/sena/caribeapp/presentation/navigation/AppScreens.kt
package edu.sena.caribeapp.presentation.navigation

import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import edu.sena.caribeapp.domain.preguntas.model.Pregunta

/**
 * Clase sellada (sealed class) que define todas las rutas de navegación de la aplicación.
 * Esto proporciona seguridad de tipos y centraliza las rutas.
 */
sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object LoginScreen : AppScreens("login_screen")
    object RegisterScreen : AppScreens("register_screen")
    object HomeScreen : AppScreens("home_screen/{estudianteId}") {
        // Función auxiliar para construir la ruta con un ID real
        fun createRoute(estudianteId: String) = "home_screen/$estudianteId"
    }
    object ClassScreen : AppScreens("class_screen/{claseId}/{estudianteId}") {
        fun createRoute(claseId: String, estudianteId: String) = "class_screen/$claseId/$estudianteId"
    }
    object SimulacroScreen: AppScreens("simulacro_screen/{estudianteId}/{claseId}/{simulacroId}") {
        fun createRoute(estudianteId: String, claseId: String, simulacroId: String) = "simulacro_screen/$estudianteId/$claseId/$simulacroId"
    }
    // La conversion de String a Int
    object QuizScreen: AppScreens("quiz_screen/{preguntaId}/{indexActual}/{cantidadPreguntas}") {
        fun createRoute(preguntaId: String, indexActual: String, cantidadPreguntas: String): String {
            return "quiz_screen/$preguntaId/$indexActual/$cantidadPreguntas"
        }
    }
    // Añadir más pantallas aquí a medida que se creen
}
