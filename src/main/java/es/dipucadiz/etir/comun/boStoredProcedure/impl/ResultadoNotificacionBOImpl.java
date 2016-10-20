package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.ResultadoNotificacionBO;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class ResultadoNotificacionBOImpl extends StoredProcedure implements ResultadoNotificacionBO {

	private static final Log LOG = LogFactory.getLog(ResultadoNotificacionBOImpl.class);

	public ResultadoNotificacionBOImpl(final DataSource dataSource) {
		super(dataSource, "PQ_RESULTADO_NOTIFICACION.resultado_notificacion_v");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("e_co_usuario_actualizacion", Types.VARCHAR));
		declareParameter(new SqlOutParameter("e_mensaje_error", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_modelo", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_version", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_documento", Types.VARCHAR));
		declareParameter(new SqlParameter("e_fecha", Types.VARCHAR));
		declareParameter(new SqlParameter("e_resultado", Types.VARCHAR));
		declareParameter(new SqlParameter("e_codigo_barras", Types.VARCHAR));
		declareParameter(new SqlParameter("e_observaciones", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, Date fecha, String resultado, String coBarras, String observaciones) {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_co_usuario_actualizacion", DatosSesion.getLogin());
		params.put("e_co_modelo", coModelo);
		params.put("e_co_version", coVersion);
		params.put("e_co_documento", coDocumento);
		params.put("e_fecha", Utilidades.dateToDDMMYYYY(fecha));
		params.put("e_resultado", resultado);
		params.put("e_codigo_barras", coBarras);
		params.put("e_observaciones", observaciones);
		Map<String, Object> resultsList= new HashMap<String, Object>();
		try {
			resultsList = execute(params);
		} catch (Exception e) {
			LOG.debug("Fallo en ResultadoNotificacionBOImpl.execute()", e);
		}
		return resultsList;
	}


}
