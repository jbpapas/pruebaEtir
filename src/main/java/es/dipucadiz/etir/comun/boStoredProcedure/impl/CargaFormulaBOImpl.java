package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.CargaFormulaBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class CargaFormulaBOImpl extends StoredProcedure implements CargaFormulaBO {
	private static final Log LOG = LogFactory.getLog(CargaFormulaBOImpl.class);

		public CargaFormulaBOImpl(final DataSource dataSource) {
			super(dataSource, "PQ_UTIL_FORMULA.CARGAR");
			setFunction(true);
			declareParameter(new SqlOutParameter("coError", Types.INTEGER));
			declareParameter(new SqlParameter("nombreZIP", Types.VARCHAR));
			declareParameter(new SqlInOutParameter("msg", Types.VARCHAR));
			compile();
		}
		
		@SuppressWarnings("unchecked")
		public Map<String, Object> execute(String nombreZIP, String msg) throws GadirServiceException {
			LOG.debug("Ejecuto PQ_UTIL_FORMULA.CARGAR");
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("nombreZIP", nombreZIP);
			params.put("msg", msg);
			Map<String, Object> resultsList = null;
			try {
				resultsList= new HashMap<String, Object>();
				resultsList = execute(params);
			} catch (Exception e){
				LOG.error("Fallo en PQ_UTIL_FORMULA.CARGAR", e);
			}
			
			if (msg != "0")
				LOG.error(msg);
			
			return resultsList;
		}

}

