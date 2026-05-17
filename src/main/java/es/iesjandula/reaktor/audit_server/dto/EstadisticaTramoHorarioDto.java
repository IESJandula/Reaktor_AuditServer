package es.iesjandula.reaktor.audit_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadisticaTramoHorarioDto
{
	/** Tramo horario */
	private String tramoHorario ;

	/** Total de peticiones */
	private Long totalPeticiones ;
}