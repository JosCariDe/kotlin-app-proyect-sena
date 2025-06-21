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
    private val registerUserUseCase: RegisterUserUseCase // Inyecta el Caso de Uso de Registro
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    /**
     * Actualiza el valor del nombre completo en el estado de la UI.
     */
    fun onFullNameChange(fullName: String) {
        _uiState.value = _uiState.value.copy(fullName = fullName, errorMessage = null)
    }

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
     * Actualiza el valor del departamento en el estado de la UI.
     */
    fun onDepartmentChange(department: String) {
        _uiState.value = _uiState.value.copy(department = department, errorMessage = null)
    }

    /**
     * Actualiza el valor del municipio en el estado de la UI.
     */
    fun onMunicipalityChange(municipality: String) {
        _uiState.value = _uiState.value.copy(municipality = municipality, errorMessage = null)
    }

    /**
     * Actualiza el valor del grado en el estado de la UI.
     */
    fun onGradeChange(grade: String) {
        _uiState.value = _uiState.value.copy(grade = grade, errorMessage = null)
    }

    /**
     * Intenta registrar un nuevo usuario con los datos actuales.
     * Lanza una corrutina para ejecutar el Caso de Uso de forma asíncrona.
     */
    fun register() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val currentState = _uiState.value
            // Validaciones básicas antes de llamar al caso de uso
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
                }
                is Resource.Error -> {
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "Error desconocido al registrarse."
                    )
                }
                is Resource.Loading -> {
                    // El estado de carga ya se manejó al inicio de la función
                }
            }
        }
    }

    /**
     * Restablece el estado de la UI de registro.
     * Útil después de un registro exitoso o para limpiar errores.
     */
    fun resetUiState() {
        _uiState.value = RegisterUiState()
    }
}
