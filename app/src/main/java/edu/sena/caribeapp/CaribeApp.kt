package edu.sena.caribeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase Application principal de la aplicación.
 * Anotada con @HiltAndroidApp para habilitar la inyección de dependencias con Hilt.
 */
@HiltAndroidApp
class CaribeApp : Application() {}