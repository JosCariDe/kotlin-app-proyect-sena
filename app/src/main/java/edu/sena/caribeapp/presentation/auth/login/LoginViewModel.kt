// app/src/main/java/edu/sena/caribeapp/presentation/auth/login/LoginViewModel.kt
package edu.sena.caribeapp.presentation.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.sena.caribeapp.domain.auth.usecase.LoginUserUseCase
import edu.sena.caribeapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de Login.
 * Maneja el estado de la UI, las interacciones del usuario y la lógica de login.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase // Inyecta el Caso de Uso de Login
) : ViewModel() {

    // Estado mutable interno de la UI de Login
    private val _uiState = MutableStateFlow(LoginUiState())
    // Estado inmutable expuesto a la UI
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Actualiza el valor del correo electrónico en el estado de la UI.
     */
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, errorMessage = null)
    }

    /**
     * Actualiza el valor de la contraseña en el estado de la UI.
     */
    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, errorMessage = null)
    }

    /**
     * Intenta iniciar sesión con las credenciales actuales.
     * Lanza una corrutina para ejecutar el Caso de Uso de forma asíncrona.
     */
    var contadorFallasLogin: Int = 0
    fun login() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null) // Estado de carga
            var bloquearBtnLogin = false

            val email = _uiState.value.email
            val password = _uiState.value.password

            // Validaciones básicas antes de llamar al caso de uso
            if (email.isBlank() || password.isBlank()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Por favor, ingresa tu correo y contraseña."
                )
                return@launch
            }

            if (!email.contains(char = '@', ignoreCase = true)) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Por favor, en el correo ingresa un dominio valido"
                )
                return@launch
            }

            when (val result = loginUserUseCase(email, password)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoadingSuccessFul = true,
                        errorMessage = null
                    )
                    // Aquí podrías guardar el token de sesión si lo hubiera
                }
                is Resource.Error -> {
                    contadorFallasLogin++
                    if (contadorFallasLogin >= 3) {
                        bloquearBtnLogin = true
                    }
                    _uiState.value = _uiState.value.copy(
                        bloquearBtnLogin = bloquearBtnLogin,
                        isLoading = false,
                        errorMessage = result.message ?: "Error desconocido al iniciar sesión.",

                    )
                }
                is Resource.Loading -> {
                    // El estado de carga ya se manejó al inicio de la función
                }
            }
        }
    }

    /**
     * Restablece el estado de la UI de login.
     * Útil después de un login exitoso o para limpiar errores.
     */
    fun resetUiState() {
        _uiState.value = LoginUiState()
    }
}
