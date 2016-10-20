package es.dipucadiz.etir.comun.struts.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de realizar la conversión de {@link java.lang.String} al
 * tipo {@link java.util.Date} y viceversa. <br/>
 * Para ello hace uso de las constantes FORMATO y EXP_REGULAR de esta clase. Con
 * la primera damos el formato y con la segunda verificamos que el valor cumpla
 * el formato.
 * 
 * @version 1.0 29/01/2010
 * @author SDS[FJTORRES]
 */
@SuppressWarnings("unchecked")
public class GadirDateTimeConverter extends StrutsTypeConverter {

	/**
	 * Atributo que almacena el LOG de la clase.
	 */
	private static final Logger LOG = Logger
	        .getLogger(GadirDateTimeConverter.class);

	/**
	 * Atributo que almacena la constante que se encarga de definir el formato
	 * de fecha correcto.
	 */
	private static final String FORMATO = "dd/MM/yyyy hh:mm:ss";

	private static final String EXP_REGULAR = "[\\d]{2}/[\\d]{2}/[\\d]{4} [\\d]{2}:[\\d]{2}:[\\d]{2}";

	@Override
	public Object convertFromString(final Map context, final String[] values,
	        final Class toClass) {
		Object fecha = null;

		if (Utilidades.isNotNull(values)) {

			if (values.length == 1) {
				final String valor = values[0];

				if (Utilidades.isValidForExpression(valor, EXP_REGULAR)) {
					final SimpleDateFormat sdf = new SimpleDateFormat(FORMATO);

					try {

						if (LOG.isDebugEnabled()) {
							LOG.debug("Realizando la conversión de " + valor
							        + " a java.util.Date con el formato "
							        + FORMATO);
						}
						fecha = sdf.parse(valor);

						if (LOG.isDebugEnabled()) {
							LOG.debug("Conversión realizada correctamente.");
						}
					} catch (final ParseException e) {
						throw new TypeConversionException(
						        "No se puede convertir el valor " + valor
						                + " a una fecha.", e);
					}
				} else {
					throw new TypeConversionException(
					        "No se puede convertir el valor " + valor
					                + " a una fecha.");
				}
			} else {
				fecha = super.convertValue(context, values, toClass);
			}
		}

		return fecha;
	}

	@Override
	public String convertToString(final Map context, final Object obj) {
		final SimpleDateFormat sdf = new SimpleDateFormat(FORMATO);
		return sdf.format((Date) obj);
	}

}
