package edu.sena.caribeapp.data.preguntas.mapper

import edu.sena.caribeapp.data.preguntas.dto.OpcionDto
import edu.sena.caribeapp.data.preguntas.dto.PreguntaDto
import edu.sena.caribeapp.domain.preguntas.model.Opcion
import edu.sena.caribeapp.domain.preguntas.model.Pregunta
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreguntaMapper @Inject constructor() {

    fun OpcionDto.toDomain(): Opcion {
        return Opcion(
            id = this.id,
            letra = this.letra,
            texto = this.texto
        )
    }


    fun PreguntaDto.toDomain(): Pregunta {
        return Pregunta(
            id = this.id,
            area = this.area,
            tema = this.area,
            enunciado = this.enunciado,
            opciones = this.opciones.map { it.toDomain() },
            urlImagen = this.urlImagen,
            explicacionCorrecta = this.explicacionCorrecta
        )
    }

    // --- Mapeo de Dominio a DTO (App -> API) ---
    // Necesario para operaciones POST/PUT donde enviamos una entidad al backend

    fun Opcion.toDto(): OpcionDto {
        return OpcionDto(
            id = id,
            letra = letra,
            texto = texto
        )
    }

    fun Pregunta.toDto(): PreguntaDto {
        return PreguntaDto(
            id = id,
            area = area,
            tema = tema,
            enunciado = enunciado,
            opciones = opciones.map { it.toDto() },
            explicacionCorrecta = explicacionCorrecta,
            urlImagen = urlImagen,
            v = 0 // Omitir o manejar según la API
        )
    }

}