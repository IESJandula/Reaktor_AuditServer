package es.iesjandula.reaktor.audit.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Francisco Manuel Benítez Chico
 */
@SpringBootApplication
@ComponentScan(basePackages = {"es.iesjandula"})
public class ReaktorAuditServerApplication
{
	/**
	 * Método principal de la aplicación.
	 * @param args Argumentos de la línea de comandos
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(ReaktorAuditServerApplication.class, args);
	}
}
