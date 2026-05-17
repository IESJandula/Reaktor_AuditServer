package es.iesjandula.reaktor.audit_server.utils;

/**
 * Clase que contiene constantes utilizadas en el microservicio de auditoría.
 * <p>
 * Esta clase define constantes relacionadas con los códigos de error, los nombres
 * legibles de los microservicios contemplados en las estadísticas y los porcentajes
 * de umbral aplicados al cálculo de tramos horarios.
 * </p>
 * 
 */
public final class Constants
{
	/*********************************************************/
	/*********************** Errores *************************/
	/*********************************************************/

	/** Error genérico - Código */
	public static final int ERR_GENERICO_CODE = 1 ;

	/** Error genérico - Mensaje */
	public static final String ERR_GENERICO_MESSAGE = "Error genérico" ;

	/** Error - Estadísticas por día de la semana - Código */
	public static final int ERR_ESTADISTICAS_DIA_SEMANA_CODE = 100 ;

	/** Error - Estadísticas por día de la semana - Mensaje */
	public static final String ERR_ESTADISTICAS_DIA_SEMANA_MESSAGE = "Error al obtener estadísticas de auditoría por día de la semana" ;

	/** Error - Estadísticas por tramo horario - Código */
	public static final int ERR_ESTADISTICAS_TRAMO_HORARIO_CODE = 101 ;

	/** Error - Estadísticas por tramo horario - Mensaje */
	public static final String ERR_ESTADISTICAS_TRAMO_HORARIO_MESSAGE = "Error al obtener estadísticas de auditoría por tramo horario" ;

	/** Error - Estadísticas por microservicio - Código */
	public static final int ERR_ESTADISTICAS_MICROSERVICIO_CODE = 102 ;

	/** Error - Estadísticas por microservicio - Mensaje */
	public static final String ERR_ESTADISTICAS_MICROSERVICIO_MESSAGE = "Error al obtener estadísticas de auditoría por microservicio" ;

	/*********************************************************/
	/******************* Días de la semana *******************/
	/*********************************************************/

	/** Día de la semana - Lunes */
	public static final String DIA_LUNES = "Lunes" ;

	/** Día de la semana - Martes */
	public static final String DIA_MARTES = "Martes" ;

	/** Día de la semana - Miércoles */
	public static final String DIA_MIERCOLES = "Miércoles" ;

	/** Día de la semana - Jueves */
	public static final String DIA_JUEVES = "Jueves" ;

	/** Día de la semana - Viernes */
	public static final String DIA_VIERNES = "Viernes" ;

	/*********************************************************/
	/******************** Tramos horarios ********************/
	/*********************************************************/

	/** Tramo horario - Otros (agrupa los tramos con porcentaje igual o inferior al umbral) */
	public static final String TRAMO_HORARIO_OTROS = "Otros" ;

	/** Porcentaje umbral para agrupar tramos horarios en "Otros" */
	public static final double UMBRAL_PORCENTAJE_TRAMO_OTROS = 5.0 ;

	/*********************************************************/
	/********************* Microservicios ********************/
	/*********************************************************/

	/** Prefijo path - Impresión remota */
	public static final String PATH_PRINTERS = "/printers" ;

	/** Prefijo path - Reservas */
	public static final String PATH_BOOKINGS = "/bookings" ;

	/** Prefijo path - Incidencias */
	public static final String PATH_ISSUES = "/issues" ;

	/** Prefijo path - Seguridad */
	public static final String PATH_FIREBASE = "/firebase" ;

	/** Prefijo path - Notificaciones */
	public static final String PATH_NOTIFICATIONS = "/notifications" ;

	/** Prefijo path - Eventos */
	public static final String PATH_EVENTS = "/events" ;

	/** Prefijo path - Huelgas */
	public static final String PATH_STRIKES = "/strikes" ;

	/** Prefijo path - Domótica */
	public static final String PATH_AUTOMATIONS = "/automations" ;

	/** Prefijo path - Herramientas directivas */
	public static final String PATH_SCHOOL_MANAGER = "/school_manager" ;

	/** Nombre legible - Impresión remota */
	public static final String MICROSERVICIO_PRINTERS = "Impresión remota" ;

	/** Nombre legible - Reservas */
	public static final String MICROSERVICIO_BOOKINGS = "Reservas" ;

	/** Nombre legible - Incidencias */
	public static final String MICROSERVICIO_ISSUES = "Incidencias" ;

	/** Nombre legible - Seguridad */
	public static final String MICROSERVICIO_FIREBASE = "Seguridad" ;

	/** Nombre legible - Notificaciones */
	public static final String MICROSERVICIO_NOTIFICATIONS = "Notificaciones" ;

	/** Nombre legible - Eventos */
	public static final String MICROSERVICIO_EVENTS = "Eventos" ;

	/** Nombre legible - Huelgas */
	public static final String MICROSERVICIO_STRIKES = "Huelgas" ;

	/** Nombre legible - Domótica */
	public static final String MICROSERVICIO_AUTOMATIONS = "Domótica" ;

	/** Nombre legible - Herramientas directivas */
	public static final String MICROSERVICIO_SCHOOL_MANAGER = "Herramientas directivas" ;
}