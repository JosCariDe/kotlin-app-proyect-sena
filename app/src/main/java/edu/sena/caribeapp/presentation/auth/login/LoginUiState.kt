package edu.sena.caribeapp.presentation.auth.login

/**
 * Data class que representa el estado de la UI de la pantalla de Login.
 * Contiene todos los datos y estados relevantes para la UI.
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoadingSuccessFul: Boolean = false,
    val bloquearBtnLogin: Boolean = false,
)
