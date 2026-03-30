package es.iesjandula.reaktor.audit.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.reaktor.audit.server.models.Auditoria;

/**
 * Interfaz que define el repositorio de auditorías
 * @author Francisco Manuel Benítez Chico
 */
public interface IAuditoriaRepository extends JpaRepository<Auditoria, Long> 
{

}
