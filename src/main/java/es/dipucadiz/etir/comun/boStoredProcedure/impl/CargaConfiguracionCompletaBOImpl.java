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

import es.dipucadiz.etir.comun.boStoredProcedure.CargaConfiguracionCompletaBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class CargaConfiguracionCompletaBOImpl extends StoredProcedure implements CargaConfiguracionCompletaBO {
	private static final Log LOG = LogFactory.getLog(CargaConfiguracionCompletaBOImpl.class);

		public CargaConfiguracionCompletaBOImpl(final DataSource dataSource) {
			super(dataSource, "PQ_UTIL_CASCON.CARGAR_TODO");
			setFunction(true);
			declareParameter(new SqlOutParameter("coError", Types.INTEGER));
			declareParameter(new SqlParameter("coProvincia", Types.VARCHAR));
			declareParameter(new SqlParameter("coMunicipio", Types.VARCHAR));
			declareParameter(new SqlParameter("coConcepto", Types.VARCHAR));
			declareParameter(new SqlParameter("fecha", Types.VARCHAR));
			declareParameter(new SqlOutParameter("msg", Types.VARCHAR));
			compile();
		}
		
		@SuppressWarnings("unchecked")
		public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto, String fecha) throws GadirServiceException {
			LOG.debug("Ejecuto PQ_UTIL_CASCON.CARGAR_TODO");
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("coProvincia", coProvincia);
			params.put("coMunicipio", coMunicipio);
			params.put("coConcepto", coConcepto);
			params.put("fecha", fecha);
			Map<String, Object> resultsList = null;
			try {
				resultsList= new HashMap<String, Object>();
				resultsList = execute(params);
			} catch (Exception e){
				LOG.error("Fallo en PQ_UTIL_CASCON.CARGAR_TODO", e);
			}
			return resultsList;
		}

}

