package es.iesjandula.reaktor.audit.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Cola enlazada al exchange directo y factoría de listeners con deserialización JSON al DTO común.
 */
@Configuration
@EnableRabbit
public class AuditRabbitInfrastructureConfiguration
{
	/** Logger de la clase */
	private static final Logger log = LoggerFactory.getLogger(AuditRabbitInfrastructureConfiguration.class);

	/** String - Exchange de auditoría */
	@Value("${reaktor.audit.exchange}")
	private String exchange;

	/** String - Routing key de auditoría */
	@Value("${reaktor.audit.routing-key}")
	private String routingKey;

	/** String - Queue de auditoría */
	@Value("${reaktor.audit.queue}")
	private String queue;

	/**
	 * Configura el DirectExchange para la auditoría.
	 * @return DirectExchange para la auditoría
	 */
	@Bean
	public DirectExchange auditExchange()
	{
		// Logueamos la configuración
		log.info("Configurando DirectExchange para la auditoría: exchange={}", this.exchange);

		// Le indicamos que es un exchange directo, durable y no auto-deleteable
		return new DirectExchange(this.exchange, true, false);
	}

	/**
	 * Configura la Queue para la auditoría.
	 * @return Queue para la auditoría
	 */
	@Bean
	public Queue auditQueue()
	{
		// Logueamos la configuración
		log.info("Configurando Queue para la auditoría: queue={}", this.queue);

		// Le indicamos que es una queue durable
		return new Queue(this.queue, true);
	}

	/**
	 * Configura el Binding para la auditoría.
	 * @param auditQueue Queue para la auditoría
	 * @param auditExchange DirectExchange para la auditoría
	 * @return Binding para la auditoría
	 */
	@Bean
	public Binding auditBinding(Queue auditQueue, DirectExchange auditExchange)
	{
		// Logueamos la configuración
		log.info("Configurando Binding para la auditoría: routingKey={}", this.routingKey);

		// Le indicamos que se enlaza a la queue con la routing key
		return BindingBuilder.bind(auditQueue).to(auditExchange).with(this.routingKey);
	}

	@Bean
	@SuppressWarnings("null")
	public Jackson2JsonMessageConverter auditJacksonMessageConverter(ObjectMapper objectMapper)
	{
		// Logueamos la configuración
		log.info("Configurando Jackson2JsonMessageConverter para serializar DTOs a JSON (Jackson)");

		// Creamos el Jackson2JsonMessageConverter
		return new Jackson2JsonMessageConverter(objectMapper);
	}

	/**
	 * Configura el SimpleRabbitListenerContainerFactory para la auditoría.
	 * @param connectionFactory ConnectionFactory para la conexión a RabbitMQ
	 * @param auditJacksonMessageConverter Jackson2JsonMessageConverter para serializar DTOs a JSON (Jackson)
	 * @return SimpleRabbitListenerContainerFactory para la auditoría
	 */
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
			                                                                   Jackson2JsonMessageConverter auditJacksonMessageConverter)
	{
		// Logueamos la configuración
		log.info("Configurando SimpleRabbitListenerContainerFactory para la auditoría");

		// Creamos el SimpleRabbitListenerContainerFactory
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		
		// Le indicamos la connection factory
		factory.setConnectionFactory(connectionFactory);

		// Le indicamos el message converter
		factory.setMessageConverter(auditJacksonMessageConverter);

		// Devolvemos el SimpleRabbitListenerContainerFactory configurado
		return factory;
	}
}
