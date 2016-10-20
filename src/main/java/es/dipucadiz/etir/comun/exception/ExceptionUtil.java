package es.dipucadiz.etir.comun.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;

/**
 * Clase que contiene métodos de ayuda para controlar las excepciones.
 * 
 * @version 1.0 27/11/2009
 * @author SDS[FJTORRES]
 */
public final class ExceptionUtil {

	/**
	 * Atributo que almacena la String asociada a un error de oracle.
	 */
	private static final String ORA_ERROR = "ORA-";

	/**
	 * Atributo que almacena el String asociado a una clave primaria.
	 */
	private static final String PK_CONSTRAINT = "PK";

	/**
	 * Atributo que almacena el log de la clase.
	 */
	private static Logger log = Logger.getLogger(ExceptionUtil.class);

	/**
	 * Constructor de la clase.
	 */
	private ExceptionUtil() {

	}

	/**
	 * Método que se encarga de procesar una excepcion producida para controlar el tipo de error ocurrido.
	 * 
	 * @param exc
	 *            Excepcion producida.
	 */
	public static String procesarExcepcion(final Throwable exc) {

		if (exc != null) {
			log.error(exc, exc);

			try {
				GenericBO genericBO = (GenericBO) GadirConfig.getBean("procesoBO");

				final Writer result = new StringWriter();
				final PrintWriter printWriter = new PrintWriter(result);
				exc.printStackTrace(printWriter);

				String traza = result.toString();
				if (traza.length() > 4000) traza = traza.substring(0, 4000);
				traza = traza.replaceAll("'", "\"");
				genericBO.ejecutaQueryUpdate("insert into TR_ERROR_PL(MODULO, ERROR, CO_USUARIO, FH_ACTUALIZACION, BO_PL) "+ "values ('" + DatosSesion.getCoProcesoActualSoloDesdeJsp() + "', '"
												+ traza + "', '" + DatosSesion.getLogin() + "',systimestamp, 0)");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String returnMessage = "Se produjo un error no controlado, por favor contacte con el administrador del sistema.";

		final Throwable root = ExceptionUtils.getRootCause(exc);
		if (root instanceof SQLException) {
			returnMessage = procesarSQLException((SQLException) root);
		} else if (root != null && root.getMessage().contains(ORA_ERROR)) {
			returnMessage = "Se ha producido un error con la base de datos, por favor contacte con el administrador del sistema.";
		}

		return returnMessage;
	}

	/**
	 * Método que se encarga de procesar una {@link SQLException} para generar un mensaje mas comprensible por el usuario.
	 * 
	 * @param sqlExc
	 *            Excepcion producida.
	 * @return Mensaje mas comprensible.
	 */
	private static String procesarSQLException(final SQLException sqlExc) {
		String returnMessage = null;
		final String mensaje = sqlExc.getMessage();
		final int errorCode = sqlExc.getErrorCode();
		switch (errorCode) {
			case 1:
				final String aux = mensaje.substring(mensaje.indexOf("(") - 1, mensaje.lastIndexOf(")"));
				if (aux.contains(PK_CONSTRAINT)) {
					returnMessage = "Se ha violado la clave primaria " + aux;
				} else {
					returnMessage = "Se ha violado la clave unica " + aux;
				}
				break;
			default:
				returnMessage = "Se ha producido un error no controlado de base de datos. Error: " + errorCode + " - " + mensaje;
				break;
		}

		return returnMessage;
	}

	/**
	 * Método que se encarga de comprobar si la excepcion que salta es de integridad referencial.
	 * 
	 * @param e
	 *            Excepcion producida.
	 * @return True si es de integridad referencial y false si no lo es.
	 */
	public static boolean isRestriccionIntegridad(final Throwable e) {
		boolean resultado = false;
		if (e instanceof DataIntegrityViolationException) {
			resultado = true;
		}
		return resultado;
	}
}
