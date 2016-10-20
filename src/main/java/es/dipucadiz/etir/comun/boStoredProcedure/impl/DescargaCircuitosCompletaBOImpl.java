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

import es.dipucadiz.etir.comun.boStoredProcedure.DescargaCircuitosCompletaBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class DescargaCircuitosCompletaBOImpl extends StoredProcedure implements DescargaCircuitosCompletaBO{
	private static final Log LOG = LogFactory.getLog(DescargaCircuitosCompletaBOImpl.class);

	public DescargaCircuitosCompletaBOImpl(final DataSource dataSource) {
		super(dataSource, "PQ_MIGRACION_CIRCUITOS.DESCARGAR_CIRCUITOS");
		setFunction(true);
		declareParameter(new SqlOutParameter("coError", Types.INTEGER));
		declareParameter(new SqlParameter("fechaActual", Types.VARCHAR));
		declareParameter(new SqlParameter("coCircuitos", Types.VARCHAR));
		compile();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(String fechaActual, String coCircuitos) throws GadirServiceException {
		LOG.debug("Ejecuto PQ_MIGRACION_CIRCUITOS.DESCARGAR_CIRCUITOS");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("fechaActual", fechaActual);
		params.put("coCircuitos", coCircuitos);
		Map<String, Object> resultsList = null;
		try {
			resultsList= new HashMap<String, Object>();
			resultsList = execute(params);
		} catch (Exception e){
			LOG.error("Fallo en PQ_MIGRACION_CIRCUITOS.DESCARGAR_CIRCUITOS", e);
		}
		return resultsList;
	}
}
