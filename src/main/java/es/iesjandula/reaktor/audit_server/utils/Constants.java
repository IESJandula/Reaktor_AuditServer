package es.iesjandula.reaktor.audit_server.utils;

import java.util.HashMap;
import java.util.Map;

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

    /** Mapa clave valor con los prefijos y nombres legibles de los microservicios */
    public static final Map<String, String> MICROSERVICIOS_MAP = new HashMap<String, String>() {{
        put("/printers", "Impresión remota");
        put("/bookings", "Reservas");
        put("/issues", "Incidencias");
        put("/firebase", "Seguridad");
        put("/notifications", "Notificaciones");
        put("/events", "Eventos");
        put("/strikes", "Huelgas");
        put("/automations", "Domótica");
        put("/school_manager", "Herramientas directivas");
    }};
}