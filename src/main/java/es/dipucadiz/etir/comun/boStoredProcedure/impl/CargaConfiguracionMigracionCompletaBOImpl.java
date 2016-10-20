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

import es.dipucadiz.etir.comun.boStoredProcedure.CargaConfiguracionMigracionCompletaBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class CargaConfiguracionMigracionCompletaBOImpl extends StoredProcedure implements CargaConfiguracionMigracionCompletaBO {
	private static final Log LOG = LogFactory.getLog(CargaConfiguracionMigracionCompletaBOImpl.class);

		public CargaConfiguracionMigracionCompletaBOImpl(final DataSource dataSource) {
			super(dataSource, "PQ_MIGRACION_CASCON.CARGAR_CASCON");
			setFunction(true);
			declareParameter(new SqlOutParameter("coError", Types.INTEGER));
			declareParameter(new SqlParameter("fecha", Types.VARCHAR));
			declareParameter(new SqlOutParameter("msg", Types.VARCHAR));
			compile();
		}
		
		@SuppressWarnings("unchecked")
		public Map<String, Object> execute(String fecha) throws GadirServiceException {
			LOG.debug("Ejecuto PQ_MIGRACION_CASCON.CARGAR_CASCON");
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("fecha", fecha);
			Map<String, Object> resultsList = null;
			try {
				resultsList= new HashMap<String, Object>();
				resultsList = execute(params);
			} catch (Exception e){
				LOG.error("Fallo en PQ_MIGRACION_CASCON.CARGAR_CASCON", e);
			}
			return resultsList;
		}

}

