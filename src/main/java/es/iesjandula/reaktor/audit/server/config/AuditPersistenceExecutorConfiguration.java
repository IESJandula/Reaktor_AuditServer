package es.iesjandula.reaktor.audit.server.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Ejecutor acotado para persistencia asíncrona: el {@code JmsListener} delega rápido y el trabajo pesado
 * (BD, etc.) no bloquea el hilo del consumidor JMS. Si la cola se llena, {@link ThreadPoolExecutor.CallerRunsPolicy}
 * aplica contrapresión.
 */
@Configuration
public class AuditPersistenceExecutorConfiguration
{
	/** Logger de la clase */
	private static final Logger log = LoggerFactory.getLogger(AuditPersistenceExecutorConfiguration.class);

	/** String - Nombre del bean */
	public static final String BEAN_NAME = "auditPersistenceExecutor";

	/**
	 * Configura el ThreadPoolTaskExecutor para la persistencia de la auditoría.
	 * @return ThreadPoolTaskExecutor para la persistencia de la auditoría
	 */
	@Bean(name = BEAN_NAME)
	public ThreadPoolTaskExecutor auditPersistenceExecutor()
	{
		// Logueamos la configuración
		log.info("Configurando ThreadPoolTaskExecutor para la persistencia de la auditoría");

		// Creamos el ThreadPoolTaskExecutor
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

		// Le indicamos el tamaño del core pool
		threadPoolTaskExecutor.setCorePoolSize(2);

		// Le indicamos el tamaño del max pool
		threadPoolTaskExecutor.setMaxPoolSize(8);

		// Le indicamos la capacidad de la cola
		threadPoolTaskExecutor.setQueueCapacity(2000);

		// Le indicamos el prefijo del nombre del hilo
		threadPoolTaskExecutor.setThreadNamePrefix("audit-persist-");

		// Le indicamos el handler de rejected execution
		threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

		// Inicializamos el ThreadPoolTaskExecutor
		threadPoolTaskExecutor.initialize();

		// Devolvemos el ThreadPoolTaskExecutor configurado
		return threadPoolTaskExecutor;
	}
}
