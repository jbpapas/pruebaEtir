package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.bo.BatchSetEstadoBO;


public class BatchSetEstadoBOImpl extends StoredProcedure implements BatchSetEstadoBO {

	public BatchSetEstadoBOImpl(final DataSource dataSource) {
		super(dataSource, "COMUN_UTIL.Batch_setEstado");
		setFunction(true);
		declareParameter(new SqlOutParameter("result", Types.NUMERIC));
		declareParameter(new SqlParameter("e_codigoEjecucion", Types.NUMERIC));
		declareParameter(new SqlParameter("e_estado", Types.VARCHAR));
		declareParameter(new SqlParameter("e_codigoError", Types.NUMERIC));
		declareParameter(new SqlParameter("e_observaciones", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_usuario_actualizacion", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public int execute(final int codigoEjecucion, final char estado, final int codigoTerminacion, final String usuarioAct, final String observaciones) {
		final Map params = new HashMap();
		params.put("e_codigoEjecucion", Integer.toString(codigoEjecucion));
		params.put("e_estado", String.valueOf(estado));
		params.put("e_codigoError", Integer.toString(codigoTerminacion));
		if (observaciones != null && observaciones.length() > 100) {
			params.put("e_observaciones", observaciones.substring(0, 100));
		} else {
			params.put("e_observaciones", observaciones);
		}
		params.put("e_co_usuario_actualizacion", usuarioAct);
		final Map results = execute(params);
		return ((BigDecimal)results.get("result")).intValue();
	}
}
