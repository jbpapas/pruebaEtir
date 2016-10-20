package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.ObtenerNumeroDocumentoBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class ObtenerNumeroDocumentoBOImpl extends StoredProcedure implements ObtenerNumeroDocumentoBO {

	private static final Log LOG = LogFactory.getLog(ObtenerNumeroDocumentoBOImpl.class);

	public ObtenerNumeroDocumentoBOImpl(final DataSource dataSource) {
		super(dataSource, "CONTADOR_SECUENCIAL.obtener_numero_documento");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_modelo", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_version", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public String execute(String coModelo, String coVersion) throws GadirServiceException {
		String coDocumento = null;
		LOG.debug("Ejecuto fu_ga_guardar_pdf.");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_co_modelo", coModelo);
		params.put("e_co_version", coVersion);
		Map<String, Object> resultsList = new HashMap<String, Object>();
		try {
			resultsList = execute(params);
			if (resultsList == null) {
				throw new GadirServiceException("CONTADOR_SECUENCIAL.obtener_numero_documento devuelve null.");
			} else {
				if (!resultsList.containsKey("resultado")) {
					throw new GadirServiceException("CONTADOR_SECUENCIAL.obtener_numero_documento no contiene resultado.");
				} else {
					coDocumento = (String) resultsList.get("resultado");
					if (Utilidades.isEmpty(coDocumento)) {
						throw new GadirServiceException("El código de documento devuelto por CONTADOR_SECUENCIAL.obtener_numero_documento no tiene contenido.");
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Fallo en ObtenerNumeroDocumentoBOImpl.execute()", e);
			throw new GadirServiceException("Fallo en ObtenerNumeroDocumentoBOImpl.execute()", e);
		}
		return coDocumento;
	}

}
