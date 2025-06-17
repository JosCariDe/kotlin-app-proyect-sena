package edu.sena.caribeapp.data.auth.remote

import edu.sena.caribeapp.data.auth.dto.LoginRequest
import edu.sena.caribeapp.data.auth.dto.RegisterRequest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fuente de datos remota para las operaciones de autenticación.
 * Interactúa directamente con la AuthApi de Retrofit.
 */

@Singleton
class AuthRemoteDataSource @Inject constructor(
    private val authApi: AuthApi // Inyecta la interfaz de Retrofit
){

    /**
     * Realiza la llamada a la API para registrar un usuario.
     * @param registerRequest Los datos de registro.
     * @return La respuesta de la API (ApiResponse<RegisterResponse>).
     */
    suspend fun registerUser(registerRequest: RegisterRequest) = authApi.registerUser(registerRequest)

    /**
     * Realiza la llamada a la API para iniciar sesión.
     * @param loginRequest Las credenciales de login.
     * @return La respuesta de la API (ApiResponse<LoginResponse>).
     */
    suspend fun loginUser(loginRequest: LoginRequest) = authApi.loginUser(loginRequest)
}