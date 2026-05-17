package es.iesjandula.reaktor.audit_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadisticaMicroservicioDto
{
	/** Nombre del microservicio (primer segmento del path) */
	private String microservicio ;

	/** Total de peticiones */
	private Long totalPeticiones ;
}