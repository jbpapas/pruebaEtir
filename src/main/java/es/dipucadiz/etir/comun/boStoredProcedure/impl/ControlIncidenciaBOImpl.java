package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.ControlIncidenciaBO;



public class ControlIncidenciaBOImpl extends StoredProcedure implements ControlIncidenciaBO, Serializable {
	private static final long serialVersionUID = 4890112484132527992L;

	private static final Log LOG = LogFactory.getLog(ControlIncidenciaBOImpl.class);

	public ControlIncidenciaBOImpl(final DataSource dataSource) {
		super(dataSource, "FR_DOCUMENTO.control_incidencia");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("E_MOD", Types.VARCHAR));
		declareParameter(new SqlParameter("E_VER", Types.VARCHAR));
		declareParameter(new SqlParameter("E_DOC", Types.VARCHAR));
		declareParameter(new SqlParameter("E_INCIDENCIA", Types.VARCHAR));
		declareParameter(new SqlOutParameter("coMensajeError", Types.NUMERIC));
		declareParameter(new SqlOutParameter("variable", Types.VARCHAR));
		declareParameter(new SqlParameter("E_OBSERVACIONES", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, String coIncidencia, String observaciones) {
		LOG.debug("Ejecuto FR_DOCUMENTO.control_incidencia.");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("E_MOD", coModelo);
		params.put("E_VER", coVersion);
		params.put("E_DOC", coDocumento);
		params.put("E_INCIDENCIA", coIncidencia);
		params.put("E_OBSERVACIONES", observaciones);
		Map<String, Object> resultsList= new HashMap<String, Object>();
		try {
			resultsList = execute(params);
		} catch (Exception e) {
			LOG.error("Fallo en ControlIncidenciaBOImpl.execute()", e);
		}
		
		return resultsList;
	}

}
