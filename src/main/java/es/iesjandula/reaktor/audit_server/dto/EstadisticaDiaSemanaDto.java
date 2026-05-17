package es.iesjandula.reaktor.audit_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadisticaDiaSemanaDto
{
	/** Día de la semana */
	private String diaSemana ;

	/** Total de peticiones */
	private Long totalPeticiones ;
}