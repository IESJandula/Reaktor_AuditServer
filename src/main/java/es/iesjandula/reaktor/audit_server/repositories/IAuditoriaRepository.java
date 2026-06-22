package es.iesjandula.reaktor.audit_server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.iesjandula.reaktor.audit_server.models.Auditoria;

/**
 * Interfaz que define el repositorio de auditorías
 */
public interface IAuditoriaRepository extends JpaRepository<Auditoria, Long>
{
	/**
	 * Cuenta las peticiones agrupadas por día de la semana.
	 * Devuelve [diaSemana (1=domingo, 2=lunes... 7=sábado), COUNT(*)]
	 */
	@Query(value = "SELECT DAYOFWEEK(timestamp), COUNT(*) FROM auditoria WHERE cursoAcademico = :cursoAcademico GROUP BY DAYOFWEEK(timestamp)",
		   nativeQuery = true)
	List<Object[]> contarPorDiaSemana(@Param("cursoAcademico") String cursoAcademico) ;

	/**
	 * Cuenta las peticiones agrupadas por hora del día.
	 * Devuelve [hora (0-23), COUNT(*)]
	 */
	@Query(value = "SELECT HOUR(timestamp), COUNT(*) FROM auditoria WHERE cursoAcademico = :cursoAcademico GROUP BY HOUR(timestamp)",
		   nativeQuery = true)
	List<Object[]> contarPorHora(@Param("cursoAcademico") String cursoAcademico) ;

	/**
	 * Cuenta las peticiones agrupadas por el primer segmento del endpoint.
	 * Devuelve [endpoint, COUNT(*)]
	 */
	@Query("SELECT a.endpoint, COUNT(a) FROM Auditoria a WHERE a.cursoAcademico = :cursoAcademico GROUP BY a.endpoint")
	List<Object[]> contarPorEndpoint(@Param("cursoAcademico") String cursoAcademico) ;
}