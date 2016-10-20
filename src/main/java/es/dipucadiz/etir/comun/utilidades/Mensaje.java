/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.util.ArrayList;
import java.util.List;

import es.dipucadiz.etir.comun.bo.MensajeGetTextoBO;

/**
 * Recuperación de mensajes de error
 * @author jronnols
 *
 */
final public class Mensaje {

	private static MensajeGetTextoBO mensajeGetTextoBO;

	private Mensaje() {}
	/**
	 * Devuelve el texto del error asociado al código enviado por parámetro&#46;<br>En PL/SQL: comun_util.Mensaje_getTexto.
	 * @param codigo
	 * @return El mensaje que corresponde con el código.
	 */
	public static String getTexto(final int codigo) {
		return getTexto(codigo, new ArrayList<String>());
	}
	/**
	 * Sustituye variables en el mensaje (:1:, :2:, ...) por valores&#46;<br>En PL/SQL: comun_util.Mensaje_getTexto.
	 * @param codigo
	 * @param variables
	 * @return El mensaje que corresponde con el código.
	 */
	public static String getTexto(final int codigo, final List<String> variables) {
		String texto = mensajeGetTextoBO.execute(codigo, variables);
		if ("?".equals(texto)) {
			texto = "ERROR " + codigo + " NO ENCONTRADO.";
		}
		return texto;
	}
	
	/**
	 * Sustituye el variable en el mensaje (:1:) por el valor indicado.
	 * @param codigo
	 * @param variable
	 * @return
	 */
	public static String getTexto(final int codigo, final String variable) {
		final List<String> lista = new ArrayList<String>(1);
		lista.add(variable);
		return getTexto(codigo, lista);
	}
	public static MensajeGetTextoBO getMensajeGetTextoBO() {
		return mensajeGetTextoBO;
	}
	public void setMensajeGetTextoBO(final MensajeGetTextoBO mensajeGetTextoBO) {
		Mensaje.mensajeGetTextoBO = mensajeGetTextoBO;
	}
	
	
}
