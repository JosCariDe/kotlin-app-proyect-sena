package edu.sena.caribeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase Application principal de la aplicación.
 * Anotada con @HiltAndroidApp para habilitar la inyección de dependencias con Hilt.
 * Hilt generará un componente de aplicación que puede proporcionar dependencias a otras clases.
 */
@HiltAndroidApp
class CaribeApp : Application() {
    // No necesitamos lógica adicional aquí por ahora,
    // Hilt se encarga de la inicialización necesaria.
}