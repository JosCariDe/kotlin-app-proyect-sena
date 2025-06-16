package edu.sena.caribeapp.data.auth.remote

import edu.sena.caribeapp.data.auth.dto.LoginRequest
import edu.sena.caribeapp.data.auth.dto.LoginResponse
import edu.sena.caribeapp.data.auth.dto.RegisterRequest
import edu.sena.caribeapp.data.auth.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Interfaz de Retrofit para los endpoints de autenticación.
 * Define los métodos para interactuar con la API de autenticación.
 */
interface AuthApi {
//asdasd
    /**
     * Realiza una petición POST para registrar un nuevo usuario.
     * @param registerRequest El objeto que contiene los datos de registro del usuario.
     * @return Un objeto Response que contiene la respuesta del registro.
     */
    @Headers("Content-Type: application/json") // Indica que el cuerpo de la petición es JSON
    @POST("auth/register") // Define el metodo HTTP (POST) y la ruta del endpoint
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    /**
     * Realiza una petición POST para iniciar sesión.
     * @param loginRequest El objeto que contiene las credenciales de login (correo y contraseña).
     * @return Un objeto Response que contiene la respuesta del login.
     */
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
