package edu.sena.caribeapp.data.auth.mapper

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
 * relacionadas con la autenticación y estudiantes.
 * Se utiliza para transformar EstudianteDto a Estudiante.
 */

@Singleton
class AuthMapper @Inject constructor() {

    // Funciones de extensión para mapear DTOs anidados a entidades
    fun ForoDto.toDomain(): Foro {
        return Foro(
            id = this.id, // El "this" hace referencia a los atributos de ForoDto
            nombre = this.nombre,
            creador = this.creador,
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
            foros = foros.map { it.toDomain() }, // Mapea la lista de foros
            simulacros = simulacros.map { it.toDomain() } // Mapea la lista de simulacros
        )
    }

    /**
     * Convierte un EstudianteDto de la capa de datos a una entidad Estudiante de la capa de dominio.
     */
    fun EstudianteDto.toDomain(): Estudiante {
        return Estudiante(
            id = id,
            nombreCompleto = nombreCompleto,
            correo = correo,
            fechaRegistro = fechaRegistro,
            departamento = departamento,
            municipio = municipio,
            grado = grado,
            clasesICFES = clasesICFES?.map { it.toDomain() } // Mapea la lista de clases, si no es nula
        )
    }
}