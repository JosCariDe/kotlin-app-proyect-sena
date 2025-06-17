// app/src/main/java/edu/sena/caribeapp/data/estudiantes/mapper/EstudianteMapper.kt
package edu.sena.caribeapp.data.estudiantes.mapper

import edu.sena.caribeapp.data.estudiantes.dto.ClaseICFESDto
import edu.sena.caribeapp.data.estudiantes.dto.EstudianteDto
import edu.sena.caribeapp.data.estudiantes.dto.ForoDto
import edu.sena.caribeapp.data.estudiantes.dto.SimulacroDto
import edu.sena.caribeapp.domain.estudiantes.model.ClaseICFES
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.model.Foro
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapeador para convertir DTOs de la API a entidades de dominio
 * relacionadas con estudiantes.
 * Se utiliza para transformar EstudianteDto a Estudiante y viceversa.
 */
@Singleton // Se inyectará como un singleton
class EstudianteMapper @Inject constructor() {

    // --- Mapeo de DTO a Dominio (API -> App) ---

    fun ForoDto.toDomain(): Foro {
        return Foro(
            id = id,
            nombre = nombre,
            creador = creador,
            fecha = fecha
        )
    }

    fun SimulacroDto.toDomain(): Simulacro {
        return Simulacro(
            id = id,
            titulo = titulo,
            area = area,
            estado = estado,
            listaIdPreguntas = listaIdPreguntas,
            listaOpcionesCorrectas = listaOpcionesCorrectas,
            listaOpcionesEscogidas = listaOpcionesEscogidas
        )
    }

    fun ClaseICFESDto.toDomain(): ClaseICFES {
        return ClaseICFES(
            id = id,
            nombreClase = nombreClase,
            profesor = profesor,
            foros = foros.map { it.toDomain() },
            simulacros = simulacros.map { it.toDomain() }
        )
    }

    fun EstudianteDto.toDomain(): Estudiante {
        return Estudiante(
            id = id,
            nombreCompleto = nombreCompleto,
            correo = correo,
            fechaRegistro = fechaRegistro,
            departamento = departamento,
            municipio = municipio,
            grado = grado,
            clasesICFES = clasesICFES?.map { it.toDomain() }
        )
    }

    // --- Mapeo de Dominio a DTO (App -> API) ---
    // Necesario para operaciones POST/PUT donde enviamos una entidad al backend

    fun Foro.toDto(): ForoDto {
        return ForoDto(
            id = id,
            nombre = nombre,
            creador = creador,
            fecha = fecha
        )
    }

    fun Simulacro.toDto(): SimulacroDto {
        return SimulacroDto(
            id = id,
            titulo = titulo,
            area = area,
            estado = estado,
            listaIdPreguntas = listaIdPreguntas,
            listaOpcionesCorrectas = listaOpcionesCorrectas,
            listaOpcionesEscogidas = listaOpcionesEscogidas
        )
    }

    fun ClaseICFES.toDto(): ClaseICFESDto {
        return ClaseICFESDto(
            id = id,
            nombreClase = nombreClase,
            profesor = profesor,
            foros = foros.map { it.toDto() },
            simulacros = simulacros.map { it.toDto() }
        )
    }

    fun Estudiante.toDto(): EstudianteDto {
        return EstudianteDto(
            id = id, // El ID puede ser vacío si es una creación nueva
            nombreCompleto = nombreCompleto,
            correo = correo,
            contrasenaHash = "", // No se envía la contraseña hash desde la app
            fechaRegistro = fechaRegistro,
            departamento = departamento,
            municipio = municipio,
            grado = grado,
            clasesICFES = clasesICFES?.map { it.toDto() },
            v = 0 // Omitir o manejar según la API
        )
    }
}
