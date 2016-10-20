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

import es.dipucadiz.etir.comun.boStoredProcedure.ResultadoNotificacionCodBarBO;


public class ResultadoNotificacionCodBarBOImpl extends StoredProcedure implements ResultadoNotificacionCodBarBO {

	private static final Log LOG = LogFactory.getLog(ResultadoNotificacionCodBarBOImpl.class);

	public ResultadoNotificacionCodBarBOImpl(final DataSource dataSource) {
		super(dataSource, "PQ_RESULTADO_NOTIFICACION.introduce_resultado_individual");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("e_codigo_barras", Types.VARCHAR));
		declareParameter(new SqlParameter("e_tipo", Types.VARCHAR));
		declareParameter(new SqlParameter("e_fecha", Types.VARCHAR));
		declareParameter(new SqlOutParameter("e_mensaje", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	
	public Map<String, Object> execute(String codigoBarras, String tipo,
			String fecha) {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_codigo_barras", codigoBarras);
		params.put("e_tipo", tipo);
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
