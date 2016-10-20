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

import es.dipucadiz.etir.comun.boStoredProcedure.DescargaConfiguracionMigracionCompletaBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class DescargaConfiguracionMigracionCompletaBOImpl extends StoredProcedure implements DescargaConfiguracionMigracionCompletaBO{
	private static final Log LOG = LogFactory.getLog(DescargaConfiguracionMigracionCompletaBOImpl.class);

	public DescargaConfiguracionMigracionCompletaBOImpl(final DataSource dataSource) {
		super(dataSource, "PQ_MIGRACION_CASCON.DESCARGAR_TODO");
		setFunction(true);
		declareParameter(new SqlOutParameter("coError", Types.INTEGER));
		declareParameter(new SqlParameter("fechaActual", Types.VARCHAR));
		declareParameter(new SqlOutParameter("msg", Types.VARCHAR));
		compile();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(String fechaActual) throws GadirServiceException {
		LOG.debug("Ejecuto PQ_MIGRACION_CASCON.DESCARGAR_TODO");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("fechaActual", fechaActual);
		Map<String, Object> resultsList = null;
		try {
			resultsList= new HashMap<String, Object>();
			resultsList = execute(params);
		} catch (Exception e){
			LOG.error("Fallo en PQ_MIGRACION_CASCON.DESCARGAR_TODO", e);
		}
		return resultsList;
	}

}
