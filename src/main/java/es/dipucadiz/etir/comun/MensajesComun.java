package es.dipucadiz.etir.comun;

/**
 * Interface responsable de definir las key de los mensajes genericos de la
 * aplicacion.
 *
 * @version 1.0 30/11/2009
 * @author SDS[FJTORRES]
 */
public interface MensajesComun {

	/**
	 * Código asociado al mensaje de información a mostrar cuando se intenta dar
	 * de alta un registro que ya existe.
	 */
	String INFO_REG_EXISTE = "4";

	/**
	 * Código asociado al mensaje de información a mostrar cuando se da de alta
	 * un registro.
	 */
	String INFO_REG_ALTA = "5";

	/**
	 * Código asociado al mensaje de información a mostrar cuando se da de baja
	 * un registro.
	 */
	String INFO_REG_BAJA = "6";

	/**
	 * Código asociado al mensaje de información a mostrar cuando se modifica un
	 * registro.
	 */
	String INFO_REG_MODIF = "7";

	/**
	 * Código asociado al mensaje de información a mostrar cuando se active un
	 * registro.
	 */
	String INFO_REG_ACTIVADO = "10";

	/**
	 * Código asociado al mensaje de información a mostrar cuando se intenta
	 * borrar un registro que no exista.
	 */
	String INFO_REG_YA_BAJA = "19";

	/**
	 * Código asociado al error a usar cuando no se selecciona un elemento en un
	 * listado.
	 */
	String ERROR_ELEM_NO_SEL = "20";

	/**
	 * Código asociado al mensaje que debe seleccionar un registro.
	 */
	String RADIO = "43";

	/**
	 * Código asociado al error de campo requerido.
	 */
	String ERROR_REQUERIDO = "10001";

	/**
	 * Código asociado al error de campo numerico.
	 */
	String ERROR_NUMERICO = "10003";

	/**
	 * Código asociado al error a mostrar cuando un campo A deba ser mayor que
	 * un campo B.
	 */
	String ERROR_VALORA_MAYOR_VALORB = "10004";

	/**
	 * Código asociado al error a mostrar cuando un campo A deba ser menor que
	 * un campo B.
	 */
	String ERROR_VALORA_MENOR_VALORB = "10005";

	/**
	 * Código asociado al error a mostrar cuando el código de estructura de
	 * entrada y tipo de registro no llegan a alguna pantalla que los requiera.
	 */
	String ERROR_DATOS_ENTRADA = "10039";

	/**
	 * Código asociado al error a mostrar cuando se se produce un solapamiento
	 * de posiciones en una misma línea.
	 */
	String ERROR_SOLAPAMIENTO = "10043";

	/**
	 * Código asociado al error a mostrar cuando se supera la longitud de la
	 * estructura de entrada en alguna de las operaciones.
	 */
	String ERROR_LONGITUD_ESTRUCTURA = "10044";

	/**
	 * Código asociado al mensaje de informacion a mostrar tras un duplicar.
	 */
	String INFO_REG_DUPLICAR = "10049";

	/**
	 * Código asociado al error a mostrar cuando se inserta un valor menor que
	 * otro establecido o permitido.
	 */
	String ERROR_NUMERO_MENORIGUALQUE = "10069";

	/**
	 * Código asociado al error a mostrar cuando se inserta un valor mayor que
	 * otro establecido o permitido.
	 */
	String ERROR_NUMERO_MENORQUE = "10071";

	/**
	 * Código asociado al error a mostrar cuando se inserta un valor menor que
	 * otro establecido o permitido.
	 */
	String ERROR_NUMERO_MAYORQUE = "10072";

	/**
	 * Atributo que almacena el codigo de error a usar cuando se supere la
	 * longitud permitida para el dato seleccionado.
	 */
	String ERROR_SUPERA_LONG = "10107";

	/**
	 * Código asociado al error a mostrar cuando no se encuentra ningún
	 * documento seleccionado con el estado elegido para ese caso.
	 */
	String ESTADO_INCORRECTO_MASIVA = "10130";

	/**
	 * Código asociado al error a mostrar cuando no existe un registro
	 * para los criterios seleccionados.
	 */
	String NO_DOCUMENTOS_FILTRO = "10131";

	/**
	 * Código asociado al mensaje de información asociado al mensaje a usar
	 * cuando se desactiva un registro.
	 */
	String INFO_REG_DESACTIVADO = "10127";

	/**
	 * Código asociado al mensaje de información a mostrar cuando se intenta dar
	 * de alta un registro que ya existe, se le indica por paramero nombre.
	 */
	String INFO_REG_EXISTE_NOMBRE = "10164";

	/**
	 * Código asociado al mensaje de información a mostrar cuando se intenta
	 * usar un registro que no existe, se le indica el nombre por parametro.
	 */
	String INFO_REG_NO_EXISTE_NOMBRE = "10165";

	/**
	 * Código del mensaje a usar cuando el filtro aplicado es incorrecto.
	 */
	String INFO_FILTRO_INCORRECTO = "10175";
}
