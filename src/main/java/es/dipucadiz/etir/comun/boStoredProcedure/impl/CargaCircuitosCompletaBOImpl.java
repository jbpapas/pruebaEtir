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

import es.dipucadiz.etir.comun.boStoredProcedure.CargaCircuitosCompletaBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class CargaCircuitosCompletaBOImpl extends StoredProcedure implements CargaCircuitosCompletaBO {
	private static final Log LOG = LogFactory.getLog(CargaCircuitosCompletaBOImpl.class);

		public CargaCircuitosCompletaBOImpl(final DataSource dataSource) {
			super(dataSource, "PQ_MIGRACION_CIRCUITOS.CARGAR_CIRCUITOS");
			setFunction(true);
			declareParameter(new SqlOutParameter("coError", Types.INTEGER));
			declareParameter(new SqlParameter("nombreFichero", Types.VARCHAR));
			compile();
		}
		
		@SuppressWarnings("unchecked")
		public Map<String, Object> execute(String nombreFichero) throws GadirServiceException {
			LOG.debug("Ejecuto PQ_MIGRACION_CIRCUITOS.CARGAR_CIRCUITOS");
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("nombreFichero", nombreFichero);
			Map<String, Object> resultsList = null;
			try {
				resultsList= new HashMap<String, Object>();
				resultsList = execute(params);
			} catch (Exception e){
				LOG.error("Fallo en PQ_MIGRACION_CIRCUITOS.CARGAR_CIRCUITOS", e);
			}
			return resultsList;
		}

}

