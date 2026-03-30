package es.iesjandula.reaktor.audit.server.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import es.iesjandula.reaktor.audit.server.config.AuditPersistenceExecutorConfiguration;
import es.iesjandula.reaktor.audit.server.models.Auditoria;
import es.iesjandula.reaktor.audit.server.repositories.IAuditoriaRepository;
import es.iesjandula.reaktor.base.security.models.DtoAuditoria;

/**
 * Consumidor RabbitMQ: entrega rápida al executor de persistencia.
 */
@Component
public class AuditRabbitListener
{
	/** Logger de la clase */
	private static final Logger log = LoggerFactory.getLogger(AuditRabbitListener.class);

	/** ThreadPoolTaskExecutor para la persistencia de la auditoría */
	@Autowired
	@Qualifier(AuditPersistenceExecutorConfiguration.BEAN_NAME)
	private ThreadPoolTaskExecutor auditPersistenceExecutor;

	/** Repositorio de auditorías */
	@Autowired
	private IAuditoriaRepository auditoriaRepository;

	/**
	 * Método que se ejecuta cuando se recibe un evento de auditoría.
	 * @param dtoAuditoria DtoAuditoria con el evento de auditoría
	 */
	@RabbitListener(queues = "${reaktor.audit.queue}")
	public void onAuditEvent(DtoAuditoria dtoAuditoria)
	{
		// Ejecutamos la persistencia de la auditoría
		this.auditPersistenceExecutor.execute(() -> this.almacenarAuditoria(dtoAuditoria));
	}

	/**
	 * Almacena la auditoría en la base de datos.
	 * @param dtoAuditoria DtoAuditoria con el evento de auditoría
	 */
	private void almacenarAuditoria(DtoAuditoria dtoAuditoria)
	{
		try
		{
			// Creo la auditoría
			Auditoria auditoria = new Auditoria(
				dtoAuditoria.getServiceName(),
				dtoAuditoria.getTipoEventoUsuarioAplicacion(),
				dtoAuditoria.getRoles(),
				dtoAuditoria.getMetodo(),
				dtoAuditoria.getEndpoint(),
				dtoAuditoria.getUserAgent(),
				dtoAuditoria.getTimestamp(), 
				dtoAuditoria.getStatus(), 
				dtoAuditoria.getDurationMs());
		
			// Guardo la auditoría en la base de datos
			this.auditoriaRepository.saveAndFlush(auditoria);
				
			// Logueamos la auditoría
			log.info(
				"Evento recibido y almacenado:" +
					" service="    + dtoAuditoria.getServiceName() + 
					" metodo="     + dtoAuditoria.getMetodo() + 
					" endpoint="   + dtoAuditoria.getEndpoint() + 
					" status="     + dtoAuditoria.getStatus() + 
					" durationMs=" + dtoAuditoria.getDurationMs() + 
					" timestamp="  + dtoAuditoria.getTimestamp());
		}
		catch (Exception exception)
		{
			// Logueamos el error
			log.warn("Error al recibir y/o almacenar el evento de auditoría:" +
						" service="    + dtoAuditoria.getServiceName() + 
						" metodo="     + dtoAuditoria.getMetodo() + 
						" endpoint="   + dtoAuditoria.getEndpoint() + 
						" status="     + dtoAuditoria.getStatus() + 
						" durationMs=" + dtoAuditoria.getDurationMs() + 
						" timestamp="  + dtoAuditoria.getTimestamp(), 
					exception);
		}
	}
}
