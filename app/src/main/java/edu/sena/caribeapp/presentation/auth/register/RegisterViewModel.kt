// app/src/main/java/edu/sena/caribeapp/presentation/auth/register/RegisterViewModel.kt
package edu.sena.caribeapp.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.sena.caribeapp.domain.auth.usecase.RegisterUserUseCase
import edu.sena.caribeapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de Registro.
 * Maneja el estado de la UI, las interacciones del usuario y la lógica de registro.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    // Nuevo StateFlow para la navegación, para que la UI pueda reaccionar
    private val _navigateToHome = MutableStateFlow<String?>(null)
    val navigateToHome: StateFlow<String?> = _navigateToHome.asStateFlow()

    fun onFullNameChange(fullName: String) {
        _uiState.value = _uiState.value.copy(fullName = fullName, errorMessage = null)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, errorMessage = null)
    }

    fun onDepartmentChange(department: String) {
        _uiState.value = _uiState.value.copy(department = department, errorMessage = null)
    }

    fun onMunicipalityChange(municipality: String) {
        _uiState.value = _uiState.value.copy(municipality = municipality, errorMessage = null)
    }

    fun onGradeChange(grade: String) {
        _uiState.value = _uiState.value.copy(grade = grade, errorMessage = null)
    }

    fun register() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val currentState = _uiState.value
            if (currentState.fullName.isBlank() || currentState.email.isBlank() ||
                currentState.password.isBlank() || currentState.department.isBlank() ||
                currentState.municipality.isBlank() || currentState.grade.isBlank()) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Por favor, completa todos los campos."
                )
                return@launch
            }

            when (val result = registerUserUseCase(
                currentState.fullName,
                currentState.email,
                currentState.password,
                currentState.department,
                currentState.municipality,
                currentState.grade
            )) {
                is Resource.Success -> {
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        isRegisterSuccessful = true,
                        errorMessage = null
                    )
                    // ¡Pasa el ID del estudiante al navegar!
                    result.data?.id?.let { estudianteId ->
                        _navigateToHome.value = estudianteId
                    }
                }
                is Resource.Error -> {
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "Error desconocido al registrarse."
                    )
                }
                is Resource.Loading -> {
                    // Estado de carga ya manejado
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
        _uiState.value = RegisterUiState()
    }
}
