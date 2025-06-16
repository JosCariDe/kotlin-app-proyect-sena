package edu.sena.caribeapp.util

/**
 * Clase sellada (sealed class) que representa el estado de una operación asíncrona.
 * Puede ser Loading, Success o Error.
 *
 * @param T El tipo de datos que se espera en caso de éxito.
 * @param data Los datos resultantes de la operación (presente en Success, opcional en Error).
 * @param message Un mensaje asociado al estado (presente en Error, opcional en Success).
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    /**
     * Representa un estado de éxito.
     * @param data Los datos obtenidos exitosamente.
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Representa un estado de error.
     * @param message El mensaje de error.
     * @param data Datos opcionales que podrían estar disponibles incluso en caso de error (ej. datos parciales).
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    /**
     * Representa un estado de carga.
     * No contiene datos ni mensajes, solo indica que la operación está en curso.
     */
    class Loading<T> : Resource<T>()
}
