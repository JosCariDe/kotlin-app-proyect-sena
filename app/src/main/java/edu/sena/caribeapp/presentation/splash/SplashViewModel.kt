// app/src/main/java/edu/sena/caribeapp/presentation/splash/SplashViewModel.kt
package edu.sena.caribeapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de Splash.
 * Maneja la lógica de inicialización y la navegación a la siguiente pantalla.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    // Aquí se inyectarían Casos de Uso si el Splash necesitara lógica de negocio,
    // por ejemplo, un caso de uso para verificar si el usuario ya está logueado.
    // private val checkUserSessionUseCase: CheckUserSessionUseCase
) : ViewModel() {

    // Estado que indica si la inicialización del Splash ha terminado.
    // La UI observará este estado para saber cuándo navegar.
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()
    // val isReady: StateFlow<Boolean> = _isReady

    init {
        // Inicia la lógica del Splash cuando el ViewModel se crea.
        // Usamos viewModelScope para lanzar una corrutina que se cancelará automáticamente.
        viewModelScope.launch {
            delay(2000L) // Simula una carga de 2 segundos
            // Aquí iría la lógica real, ej:
            // val isLoggedIn = checkUserSessionUseCase()
            // _isReady.value = isLoggedIn // O true si siempre navega a login/home
            _isReady.value = true // Por ahora, siempre listo después del delay
        }
    }
}
