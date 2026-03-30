package es.iesjandula.reaktor.audit.server.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import es.iesjandula.reaktor.base.utils.BaseConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase que representa una auditoría.
 * @author Francisco Manuel Benítez Chico
 */
@Entity
@Table(name = "auditoria")
public class Auditoria implements Serializable
{
    /** Long - ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Nombre del servicio que genera el evento */
    @Column(name = "service_name", nullable = false)
	private String serviceName;

	/** Tipo de principal que genera el evento */
    @Column(name = "tipo_evento_usuario_aplicacion", nullable = false)
	private String tipoEventoUsuarioAplicacion ;

	/** Roles del principal que genera el evento */
    @Column(name = "roles", nullable = false)
	private String roles;

	/** Método HTTP del evento */
    @Column(name = "metodo", nullable = false)
	private String metodo;

	/** Endpoint del evento */
    @Column(name = "endpoint", nullable = false)
	private String endpoint;

	/** User agent del evento */
    @Column(name = "user_agent", nullable = false)
	private String userAgent;

	/** Timestamp del evento */
    @Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;

	/** Estado HTTP del evento */
    @Column(name = "status", nullable = false)
	private int status;

	/** Duración del evento en milisegundos */
    @Column(name = "duration_ms", nullable = false)
	private Long durationMs;

	/**
	 * Constructor con parámetros
	 * @param serviceName de la auditoría
	 * @param tipoEventoUsuarioAplicacion de la auditoría
	 * @param roles de la auditoría
	 * @param metodo de la auditoría
	 * @param endpoint de la auditoría
	 * @param userAgent de la auditoría
	 * @param timestamp de la auditoría en formato LocalDateTime
	 * @param status de la auditoría en formato int
	 * @param durationMs de la auditoría en formato Long
	 */
	public Auditoria(String serviceName, String tipoEventoUsuarioAplicacion, List<String> roles, String metodo, String endpoint, String userAgent, LocalDateTime timestamp, int status, Long durationMs)
	{
        // Seteamos los atributos de la auditoría
		this.serviceName                 = serviceName ;
		this.tipoEventoUsuarioAplicacion = tipoEventoUsuarioAplicacion ;
		this.metodo 			         = metodo ;
		this.endpoint 			         = endpoint ;
		this.userAgent 			         = userAgent ;
		this.timestamp 			         = timestamp ;
		this.status 			         = status ;
		this.durationMs 			     = durationMs ;

        // Seteamos los roles
		this.setRolesList(roles) ;
	}

	/**
	 * Obtiene el nombre del servicio que genera el evento
	 * @return Nombre del servicio que genera el evento
	 */
	public String getServiceName()
	{
		return this.serviceName;
	}

	/**
	 * Establece el nombre del servicio que genera el evento
	 * @param serviceName Nombre del servicio que genera el evento
	 */
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	/**
	 * Obtiene el tipo de evento (USUARIO o APLICACION) que genera el evento
	 * @return Tipo de evento (USUARIO o APLICACION) que genera el evento
	 */
	public String getTipoEventoUsuarioAplicacion()
	{
		return this.tipoEventoUsuarioAplicacion;
	}

	/**
	 * Establece el tipo de evento (USUARIO o APLICACION) que genera el evento
	 * @param tipoEventoUsuarioAplicacion Tipo de evento (USUARIO o APLICACION) que genera el evento
	 */
	public void setTipoEventoUsuarioAplicacion(String tipoEventoUsuarioAplicacion)
	{
		this.tipoEventoUsuarioAplicacion = tipoEventoUsuarioAplicacion ;
	}

	/**
	 * Obtiene los roles del principal que genera el evento
	 * @return Roles del principal que genera el evento
	 */
	public String getRoles()
	{
		return this.roles;
	}

	/**
	 * Establece los roles del principal que genera el evento
	 * @param roles Roles del principal que genera el evento
	 */
	public void setRoles(String roles)
	{
		this.roles = roles;
	}

	/**
	 * Obtiene el método HTTP del evento
	 * @return Método HTTP del evento
	 */
	public String getMetodo()
	{
		return this.metodo;
	}

	/**
	 * Establece el método HTTP del evento
	 * @param metodo Método HTTP del evento
	 */
	public void setMetodo(String metodo)
	{
		this.metodo = metodo;
	}

	/**
	 * Obtiene el endpoint del evento
	 * @return Endpoint del evento
	 */
	public String getEndpoint()
	{
		return this.endpoint;
	}

	/**
	 * Establece el endpoint del evento
	 * @param endpoint Endpoint del evento
	 */
	public void setEndpoint(String endpoint)
	{
		this.endpoint = endpoint;
	}

	/**
	 * Obtiene el user agent del evento
	 * @return User agent del evento
	 */
	public String getUserAgent()
	{
		return this.userAgent;
	}

	/**
	 * Establece el user agent del evento
	 * @param userAgent User agent del evento
	 */
	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

	/**
	 * Obtiene el timestamp del evento
	 * @return Timestamp del evento
	 */
	public LocalDateTime getTimestamp()
	{
		return this.timestamp;
	}

	/**
	 * Establece el timestamp del evento
	 * @param timestamp Timestamp del evento
	 */
	public void setTimestamp(LocalDateTime timestamp)
	{
		this.timestamp = timestamp;
	}

	/**
	 * Obtiene el estado HTTP del evento
	 * @return Estado HTTP del evento
	 */
	public int getStatus()
	{
		return this.status;
	}

	/**
	 * Establece el estado HTTP del evento
	 * @param status Estado HTTP del evento
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

	/**
	 * Obtiene la duración del evento en milisegundos
	 * @return Duración del evento en milisegundos
	 */
	public long getDurationMs()
	{
		return this.durationMs;
	}

	/**
	 * Establece la duración del evento en milisegundos
	 * @param durationMs Duración del evento en milisegundos
	 */
	public void setDurationMs(long durationMs)
	{
		this.durationMs = durationMs;
	}

    /**
     * @return lista de roles deserializada
     */
    public List<String> getRolesList()
    {
        return Arrays.asList(this.roles.split(BaseConstants.STRING_COMA)) ;
    }

    /**
     * Setter para establecer los roles desde una lista
     * 
     * @param rolesList lista de roles
     */
    public void setRolesList(List<String> rolesList)
    {
    	this.roles = null ;
    	
        if (rolesList != null && !rolesList.isEmpty())
        {

	        StringBuilder rolesStringBuilder = new StringBuilder();
	
	        for (int i = 0 ; i < rolesList.size() ; i++)
	        {
	        	// Añadimos el role 
	            rolesStringBuilder.append(rolesList.get(i)) ;
	            
	            // Si no es el último, añadimos una coma
	            if (i < rolesList.size() - 1)
	            {
	                rolesStringBuilder.append(BaseConstants.STRING_COMA) ;
	            }
	        }
	
	        // Convierte el StringBuilder a cadena
	        this.roles = rolesStringBuilder.toString() ;
        }
    }
}
