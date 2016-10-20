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

import es.dipucadiz.etir.comun.boStoredProcedure.ResultadoNotificacionDocumBO;


public class ResultadoNotificacionDocumBOImpl extends StoredProcedure implements ResultadoNotificacionDocumBO {

	private static final Log LOG = LogFactory.getLog(ResultadoNotificacionDocumBOImpl.class);

	public ResultadoNotificacionDocumBOImpl(final DataSource dataSource) {
		super(dataSource, "PQ_RESULTADO_NOTIFICACION.introduce_resultado_individual");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("e_documento", Types.VARCHAR));
		declareParameter(new SqlParameter("e_tipo", Types.VARCHAR));
		declareParameter(new SqlParameter("e_resultado", Types.VARCHAR));
		declareParameter(new SqlParameter("e_fecha", Types.VARCHAR));
		declareParameter(new SqlOutParameter("e_mensaje", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	
	public Map<String, Object> execute(String documento, String tipo, String resultado,String fecha) {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_documento", documento);
		params.put("e_tipo", tipo);
		params.put("e_resultado", resultado);
		params.put("e_fecha", fecha);
		Map<String, Object> resultsList= new HashMap<String, Object>();
		try {
			resultsList = execute(params);
		} catch (Exception e) {
			LOG.debug("Fallo en ResultadoNotificacionCodBarBOImpl.execute()", e);
		}
		return resultsList;
	}


}
