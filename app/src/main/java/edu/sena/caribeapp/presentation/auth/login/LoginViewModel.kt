// app/src/main/java/edu/sena/caribeapp/presentation/auth/login/LoginViewModel.kt
package edu.sena.caribeapp.presentation.auth.login

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
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Nuevo StateFlow para la navegación, para que la UI pueda reaccionar
    private val _navigateToHome = MutableStateFlow<String?>(null)
    val navigateToHome: StateFlow<String?> = _navigateToHome.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, errorMessage = null)
    }

    fun login() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val email = _uiState.value.email
            val password = _uiState.value.password

            if (email.isBlank() || password.isBlank()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Por favor, ingresa tu correo y contraseña."
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
                    // ¡Pasa el ID del estudiante al navegar!
                    result.data?.id?.let { estudianteId ->
                        _navigateToHome.value = estudianteId
                    }
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "Error desconocido al iniciar sesión."
                    )
                }
                is Resource.Loading -> {
                    // El estado de carga ya se manejó al inicio de la función
                }
            }
        }
    }

    /**
     * Función para indicar que la navegación a Home ha sido manejada.
     */
    fun onNavigationHandled() {
        _navigateToHome.value = null
    }

    fun resetUiState() {
        _uiState.value = LoginUiState()
    }
}
