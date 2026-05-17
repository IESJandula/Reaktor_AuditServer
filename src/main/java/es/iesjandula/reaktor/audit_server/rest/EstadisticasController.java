package es.iesjandula.reaktor.audit_server.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.audit_server.dto.EstadisticaDiaSemanaDto;
import es.iesjandula.reaktor.audit_server.dto.EstadisticaMicroservicioDto;
import es.iesjandula.reaktor.audit_server.dto.EstadisticaTramoHorarioDto;
import es.iesjandula.reaktor.audit_server.repositories.IAuditoriaRepository;
import es.iesjandula.reaktor.base.utils.BaseConstants;

@RequestMapping("/audit/estadisticas")
@RestController
public class EstadisticasController
{
	private static final Logger log = LoggerFactory.getLogger(EstadisticasController.class) ;

	@Autowired
	private IAuditoriaRepository auditoriaRepository ;

	/**
	 * Devuelve el número de peticiones agrupadas por día de la semana (lunes a viernes).
	 */
	@PreAuthorize("hasAnyRole('" + BaseConstants.ROLE_ADMINISTRADOR + "', '" + BaseConstants.ROLE_DIRECCION + "')")
	@GetMapping("/por-dia-semana")
	public ResponseEntity<?> obtenerPeticionesPorDiaSemana()
	{
		try
		{
			log.info("Petición para obtener estadísticas de auditoría por día de la semana") ;

			// Obtenemos los datos agrupados por día de la semana
			List<Object[]> resultados = this.auditoriaRepository.contarPorDiaSemana() ;

			// Inicializamos el mapa con los 5 días lectivos
			Map<String, Long> mapaDias = new HashMap<>() ;
			mapaDias.put("Lunes", 0L) ;
			mapaDias.put("Martes", 0L) ;
			mapaDias.put("Miércoles", 0L) ;
			mapaDias.put("Jueves", 0L) ;
			mapaDias.put("Viernes", 0L) ;

			// Recorremos los resultados
			for (Object[] fila : resultados)
			{
				int diaSemana = ((Number) fila[0]).intValue() ;
				Long totalPeticiones = ((Number) fila[1]).longValue() ;

				// Convertimos el número de día de base de datos al nombre del día
				// 1=domingo, 2=lunes, 3=martes, 4=miércoles, 5=jueves, 6=viernes, 7=sábado
				String nombreDia = this.convertirDiaSemana(diaSemana) ;

				// Solo añadimos los días lectivos (lunes a viernes)
				if (nombreDia != null)
				{
					mapaDias.put(nombreDia, mapaDias.get(nombreDia) + totalPeticiones) ;
				}
			}

			// Convertimos el mapa a una lista de DTOs en orden lunes-viernes
			List<EstadisticaDiaSemanaDto> listaResultados = new ArrayList<>() ;
			listaResultados.add(new EstadisticaDiaSemanaDto("Lunes",     mapaDias.get("Lunes"))) ;
			listaResultados.add(new EstadisticaDiaSemanaDto("Martes",    mapaDias.get("Martes"))) ;
			listaResultados.add(new EstadisticaDiaSemanaDto("Miércoles", mapaDias.get("Miércoles"))) ;
			listaResultados.add(new EstadisticaDiaSemanaDto("Jueves",    mapaDias.get("Jueves"))) ;
			listaResultados.add(new EstadisticaDiaSemanaDto("Viernes",   mapaDias.get("Viernes"))) ;

			return ResponseEntity.ok(listaResultados) ;
		}
		catch (Exception exception)
		{
			String mensajeError = "Error inesperado al obtener estadísticas de auditoría por día de la semana" ;
			log.error(mensajeError, exception) ;
			return ResponseEntity.status(500).body(mensajeError) ;
		}
	}

	/**
	 * Devuelve el número de peticiones agrupadas por tramo horario.
	 * Los tramos con menos del 5% de peticiones se agrupan en "Otros".
	 */
	@PreAuthorize("hasAnyRole('" + BaseConstants.ROLE_ADMINISTRADOR + "', '" + BaseConstants.ROLE_DIRECCION + "')")
	@GetMapping("/por-tramo-horario")
	public ResponseEntity<?> obtenerPeticionesPorTramoHorario()
	{
		try
		{
			log.info("Petición para obtener estadísticas de auditoría por tramo horario") ;

			// Obtenemos los datos agrupados por hora
			List<Object[]> resultados = this.auditoriaRepository.contarPorHora() ;

			// Acumulamos los datos en un mapa por tramo horario
			Map<String, Long> mapaTramos = new HashMap<>() ;
			long totalPeticiones = 0L ;

			for (Object[] fila : resultados)
			{
				int hora = ((Number) fila[0]).intValue() ;
				Long total = ((Number) fila[1]).longValue() ;

				// Construimos el nombre del tramo horario
				String tramo = String.format("%02d:00 - %02d:00", hora, (hora + 1) % 24) ;

				mapaTramos.put(tramo, total) ;
				totalPeticiones += total ;
			}

			// Devolvemos lista vacía si no hay peticiones
			if (totalPeticiones == 0)
			{
				return ResponseEntity.ok(new ArrayList<EstadisticaTramoHorarioDto>()) ;
			}

			// Separamos los tramos con más del 5% del resto
			List<EstadisticaTramoHorarioDto> listaResultados = new ArrayList<>() ;
			long totalOtros = 0L ;

			for (String tramo : mapaTramos.keySet())
			{
				Long total = mapaTramos.get(tramo) ;
				double porcentaje = (total * 100.0) / totalPeticiones ;

				if (porcentaje > 5.0)
				{
					listaResultados.add(new EstadisticaTramoHorarioDto(tramo, total)) ;
				}
				else
				{
					totalOtros += total ;
				}
			}

			// Ordenamos la lista de mayor a menor usando el algoritmo burbuja
			this.ordenarResultadosTramo(listaResultados) ;

			// Añadimos al final la categoría "Otros" si tiene peticiones
			if (totalOtros > 0)
			{
				listaResultados.add(new EstadisticaTramoHorarioDto("Otros", totalOtros)) ;
			}

			return ResponseEntity.ok(listaResultados) ;
		}
		catch (Exception exception)
		{
			String mensajeError = "Error inesperado al obtener estadísticas de auditoría por tramo horario" ;
			log.error(mensajeError, exception) ;
			return ResponseEntity.status(500).body(mensajeError) ;
		}
	}

	/**
	 * Convierte el número de día de la semana de base de datos al nombre del día.
	 * Solo devuelve los días lectivos. Devuelve null para sábado y domingo.
	 */
	private String convertirDiaSemana(int diaSemana)
	{
		switch (diaSemana)
		{
			case 2:  return "Lunes" ;
			case 3:  return "Martes" ;
			case 4:  return "Miércoles" ;
			case 5:  return "Jueves" ;
			case 6:  return "Viernes" ;
			default: return null ;
		}
	}

	/**
	 * Devuelve el número de peticiones agrupadas por microservicio
	 * (primer segmento del path del endpoint).
	 * Las peticiones al microservicio Audit se excluyen.
	 */
	@PreAuthorize("hasAnyRole('" + BaseConstants.ROLE_ADMINISTRADOR + "', '" + BaseConstants.ROLE_DIRECCION + "')")
	@GetMapping("/por-microservicio")
	public ResponseEntity<?> obtenerPeticionesPorMicroservicio()
	{
		try
		{
			log.info("Petición para obtener estadísticas de auditoría por microservicio") ;

			// Obtenemos los datos agrupados por endpoint
			List<Object[]> resultados = this.auditoriaRepository.contarPorEndpoint() ;

			// Acumulamos los datos en un mapa por microservicio
			Map<String, Long> mapaMicroservicios = new HashMap<>() ;

			for (Object[] fila : resultados)
			{
				String endpoint = (String) fila[0] ;
				Long total = ((Number) fila[1]).longValue() ;

				// Traducimos el primer segmento del path al nombre legible
				String microservicio = this.traducirMicroservicio(endpoint) ;

				// Si es null, significa que es Audit y lo excluimos
				if (microservicio != null)
				{
					Long totalActual = mapaMicroservicios.get(microservicio) ;
					if (totalActual == null)
					{
						mapaMicroservicios.put(microservicio, total) ;
					}
					else
					{
						mapaMicroservicios.put(microservicio, totalActual + total) ;
					}
				}
			}

			// Convertimos el mapa a una lista de DTOs
			List<EstadisticaMicroservicioDto> listaResultados = new ArrayList<>() ;
			for (String microservicio : mapaMicroservicios.keySet())
			{
				Long total = mapaMicroservicios.get(microservicio) ;
				listaResultados.add(new EstadisticaMicroservicioDto(microservicio, total)) ;
			}

			// Ordenamos la lista de mayor a menor usando el algoritmo burbuja
			this.ordenarResultadosMicroservicio(listaResultados) ;

			return ResponseEntity.ok(listaResultados) ;
		}
		catch (Exception exception)
		{
			String mensajeError = "Error inesperado al obtener estadísticas de auditoría por microservicio" ;
			log.error(mensajeError, exception) ;
			return ResponseEntity.status(500).body(mensajeError) ;
		}
	}

	/**
	 * Traduce el primer segmento del path al nombre legible del microservicio.
	 * Devuelve null si la petición es del microservicio Audit. 
	 */
	private String traducirMicroservicio(String endpoint)
	{
		String resultado = null ;

		if (endpoint != null && endpoint.startsWith("/"))
		{
			// Extraemos el primer segmento del path
			int posicionSegundaBarra = endpoint.indexOf("/", 1) ;
			String primerSegmento ;

			if (posicionSegundaBarra > 0)
			{
				primerSegmento = endpoint.substring(0, posicionSegundaBarra) ;
			}
			else
			{
				primerSegmento = endpoint ;
			}

			// Traducimos el primer segmento al nombre legible
			if ("/printers".equals(primerSegmento))
			{
				resultado = "Impresión remota" ;
			}
			else if ("/bookings".equals(primerSegmento))
			{
				resultado = "Reservas" ;
			}
			else if ("/issues".equals(primerSegmento))
			{
				resultado = "Incidencias" ;
			}
			else if ("/firebase".equals(primerSegmento))
			{
				resultado = "Seguridad" ;
			}
			// Si es /audit, dejamos resultado a null para excluirlo
		}

		return resultado ;
	}

	/**
	 * Ordena la lista de tramos horarios de mayor a menor usando el algoritmo burbuja.
	 */
	private void ordenarResultadosTramo(List<EstadisticaTramoHorarioDto> lista)
	{
		int tamano = lista.size() ;
		for (int i = 0 ; i < tamano - 1 ; i++)
		{
			for (int j = 0 ; j < tamano - i - 1 ; j++)
			{
				EstadisticaTramoHorarioDto actual = lista.get(j) ;
				EstadisticaTramoHorarioDto siguiente = lista.get(j + 1) ;
				if (actual.getTotalPeticiones() < siguiente.getTotalPeticiones())
				{
					lista.set(j, siguiente) ;
					lista.set(j + 1, actual) ;
				}
			}
		}
	}

	/**
	 * Ordena la lista de microservicios de mayor a menor usando el algoritmo burbuja.
	 */
	private void ordenarResultadosMicroservicio(List<EstadisticaMicroservicioDto> lista)
	{
		int tamano = lista.size() ;
		for (int i = 0 ; i < tamano - 1 ; i++)
		{
			for (int j = 0 ; j < tamano - i - 1 ; j++)
			{
				EstadisticaMicroservicioDto actual = lista.get(j) ;
				EstadisticaMicroservicioDto siguiente = lista.get(j + 1) ;
				if (actual.getTotalPeticiones() < siguiente.getTotalPeticiones())
				{
					lista.set(j, siguiente) ;
					lista.set(j + 1, actual) ;
				}
			}
		}
	}
}