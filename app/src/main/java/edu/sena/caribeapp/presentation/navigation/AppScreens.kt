// app/src/main/java/edu/sena/caribeapp/presentation/navigation/AppScreens.kt
package edu.sena.caribeapp.presentation.navigation

/**
 * Clase sellada (sealed class) que define todas las rutas de navegación de la aplicación.
 * Esto proporciona seguridad de tipos y centraliza las rutas.
 */
sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object LoginScreen : AppScreens("login_screen")
    object RegisterScreen : AppScreens("register_screen") // ¡Nueva ruta!
    object HomeScreen : AppScreens("home_screen/{estudianteId}") {
        // Función auxiliar para construir la ruta con un ID real
        fun createRoute(estudianteId: String) = "home_screen/$estudianteId"
    }
    // Añadir más pantallas aquí a medida que se creen
}
