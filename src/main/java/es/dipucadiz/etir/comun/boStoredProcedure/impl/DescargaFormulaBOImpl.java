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

import es.dipucadiz.etir.comun.boStoredProcedure.DescargaFormulaBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class DescargaFormulaBOImpl extends StoredProcedure implements DescargaFormulaBO{
	private static final Log LOG = LogFactory.getLog(DescargaFormulaBOImpl.class);

	public DescargaFormulaBOImpl(final DataSource dataSource) {
		super(dataSource, "PQ_UTIL_FORMULA.DESCARGAR");
		setFunction(true);
		declareParameter(new SqlOutParameter("coError", Types.INTEGER));
		declareParameter(new SqlParameter("coProvincia", Types.VARCHAR));
		declareParameter(new SqlParameter("coMunicipio", Types.VARCHAR));
		declareParameter(new SqlParameter("coConcepto", Types.VARCHAR));
		declareParameter(new SqlParameter("coModelo", Types.VARCHAR));
		declareParameter(new SqlParameter("coVersion", Types.VARCHAR));
		declareParameter(new SqlParameter("coFecha", Types.VARCHAR));
		declareParameter(new SqlInOutParameter("msg", Types.VARCHAR));
		compile();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, String coFecha, String msg) throws GadirServiceException {
		LOG.debug("Ejecuto PQ_UTIL_FORMULA.DESCARGAR");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("coProvincia", coProvincia);
		params.put("coMunicipio", coMunicipio);
		params.put("coConcepto", coConcepto);
		params.put("coModelo", coModelo);
		params.put("coVersion", coVersion);
		params.put("coFecha", coFecha);
		params.put("msg", msg);
		Map<String, Object> resultsList = null;
		try {
			resultsList= new HashMap<String, Object>();
			resultsList = execute(params);
		} catch (Exception e){
			LOG.error("Fallo en PQ_UTIL_FORMULA.DESCARGAR", e);
		}
		
		if (msg != "0")
			LOG.error(msg);
		
		return resultsList;
	}

}
