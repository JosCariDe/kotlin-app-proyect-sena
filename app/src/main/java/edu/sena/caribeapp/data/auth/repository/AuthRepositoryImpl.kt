package edu.sena.caribeapp.data.auth.repository

import edu.sena.caribeapp.data.auth.dto.LoginRequest
import edu.sena.caribeapp.data.auth.dto.RegisterRequest
import edu.sena.caribeapp.data.auth.mapper.AuthMapper
import edu.sena.caribeapp.data.auth.remote.AuthRemoteDataSource
import edu.sena.caribeapp.data.estudiantes.dto.EstudianteDto
import edu.sena.caribeapp.domain.auth.repository.AuthRepository
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación concreta de AuthRepository.
 * Coordina entre la fuente de datos remota y realiza el mapeo de DTOs a entidades.
 * Maneja los errores de red y los transforma en un Resource.Error.
 */
@Singleton // Se inyectará como un singleton
class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource, // Inyecta la fuente de datos remota
    private val authMapper: AuthMapper // Inyecta el mapeador
) : AuthRepository { // Implementa la interfaz de dominio


    override suspend fun registerUser(
        fullName: String,
        email: String,
        password: String,
        department: String,
        municipality: String,
        grade: String
    ): Resource<Estudiante> {
        return try {

            val request = RegisterRequest(fullName, email, password, department, municipality, grade)
            val response = remoteDataSource.registerUser(request)

            if (response.isSuccessful && response.body()?.data != null) {

                // Convierte DTO a entidad de dominio
                val estudianteDto: EstudianteDto = response.body()!!.data
                Resource.Success(
                    authMapper.run {
                        estudianteDto.toDomain()
                    }
                )

            } else {
                // Manejo de errores de la API (ej. 400 Bad Request, 401 Unauthorized)
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido en el registro"
                Resource.Error(errorMessage)
            }
        } catch (e: HttpException) {
            // Errores HTTP (ej. 404 Not Found, 500 Internal Server Error)
            Resource.Error(e.localizedMessage ?: "Error HTTP inesperado")
        } catch (e: IOException) {
            // Errores de red (ej. sin conexión a internet)
            Resource.Error("No se pudo conectar al servidor. Revisa tu conexión a internet.")
        } catch (e: Exception) {
            // Otros errores inesperados
            Resource.Error(e.localizedMessage ?: "Ocurrió un error inesperado")
        }
    }

    /**
     * Implementación del método de inicio de sesión.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Un Flow de Resource que indica el estado de la operación.
     */
    override suspend fun loginUser(email: String, password: String): Resource<Estudiante> {
        return try {
            val request = LoginRequest(email, password)
            val response = remoteDataSource.loginUser(request)

            if (response.isSuccessful && response.body()?.data != null) {

                // Convierte DTO a entidad de dominio
                val estudianteDto: EstudianteDto = response.body()!!.data
                Resource.Success(
                    authMapper.run {
                        estudianteDto.toDomain()
                    }
                )

            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido en el login"
                Resource.Error(errorMessage)
            }
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Error HTTP inesperado")
        } catch (e: IOException) {
            Resource.Error("No se pudo conectar al servidor. Revisa tu conexión a internet.")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Ocurrió un error inesperado")
        }
    }
}
