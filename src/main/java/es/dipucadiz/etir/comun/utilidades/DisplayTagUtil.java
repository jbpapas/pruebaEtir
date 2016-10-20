package es.dipucadiz.etir.comun.utilidades;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.displaytag.model.HeaderCell;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

/**
 * Clase responsable de controlar las acciones mas comunes o utiles asociadas al
 * funcionamiento de display-tag.
 * 
 * @version 1.0 02/12/2009
 * @author SDS[FJTORRES]
 */
public final class DisplayTagUtil {

	/**
	 * Constructor de la clase.
	 */
	private DisplayTagUtil() {

	}

	/**
	 * Método que se encarga de hacer el encode del parametro dado.
	 * 
	 * @param uid
	 *            UID del display-table.
	 * @param param
	 *            Parametro al que queremos hacer encode.
	 */
	public static void encodeParam(final String uid, final String param) {
		new ParamEncoder(uid).encodeParameterName(param);
	}

	/**
	 * Método que se encarga de devolver el nombre de la variable que almacena
	 * la pagina para un display-table.
	 * 
	 * @param uid
	 *            UID del display-table.
	 * @param request
	 *            Petición al servidor para poder obtener el parametro.
	 * @return String con el nombre de la variable.
	 */
	public static String getVariablePagina(final String uid,
	        final HttpServletRequest request) {
		return new ParamEncoder(uid)
		        .encodeParameterName(TableTagParameters.PARAMETER_PAGE);
	}

	/**
	 * Método que se encarga de devolver la pagina actual de un display-table.
	 * 
	 * @param uid
	 *            UID del display-table.
	 * @param request
	 *            Petición al servidor para poder obtener el parametro.
	 * @return Numero entero con la pagina actual.
	 */
	public static Integer getValorPagina(final String uid,
	        final HttpServletRequest request) {
		final String strPagina = request.getParameter(getVariablePagina(uid,
		        request));
		if (Utilidades.isEmpty(strPagina)) {
			return 1;
		} else {
			return Integer.parseInt(strPagina);
		}
	}

	/**
	 * Método para eliminar HTML del contenido de una columna.
	 * @param contenidoCelda
	 * @return
	 */
	public static String formatearCeldaParaExport(String contenidoCelda, String mediaType) {
		String result = StringEscapeUtils.unescapeHtml(contenidoCelda.replace("\n", "").replace("\r", "").replace("\t", "")).replaceAll("\\<.*?\\>", "");
		if (result != null) {
			result = result.replace("€", ""); // El simbolo se sustituye por ? en la Excel exportada.
			result = result.replace("&nbsp;", " ");
			result = result.trim();
			if (MediaTypeEnum.XML.getName().equals(mediaType)) {
				result = StringEscapeUtils.escapeXml(result);
			}
		}
		return result;
	}

	/**
	 * Decide si la columna debe pintarse o no. Especialmente, la columna de iconos no debe pintarse si se trata de una exportación.
	 * @param column
	 * @param currentMediaType
	 * @return
	 */
	public static boolean incluirColumna(HeaderCell column, MediaTypeEnum mediaType) {
		return mediaType == MediaTypeEnum.HTML || 
				!column.getHtmlAttributes().containsKey("class") ||
				!"celdaIconos".equals(column.getHtmlAttributes().get("class").toString());
	}

}
