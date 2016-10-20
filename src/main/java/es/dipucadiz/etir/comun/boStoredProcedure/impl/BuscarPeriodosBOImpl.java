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

import es.dipucadiz.etir.comun.boStoredProcedure.BuscarPeriodosBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public class BuscarPeriodosBOImpl extends StoredProcedure implements BuscarPeriodosBO, Serializable {
	private static final long serialVersionUID = 2245323397949406304L;
	private static final Log LOG = LogFactory.getLog(BuscarPeriodosBOImpl.class);

	public BuscarPeriodosBOImpl(final DataSource dataSource) {
		super(dataSource, "FR_DOCUMENTO.BUSCAR_PERIODOS");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.DATE));
		declareParameter(new SqlParameter("coProvincia", Types.VARCHAR));
		declareParameter(new SqlParameter("coMunicipio", Types.VARCHAR));
		declareParameter(new SqlParameter("coConcepto", Types.VARCHAR));
		declareParameter(new SqlParameter("coModelo", Types.VARCHAR));
		declareParameter(new SqlOutParameter("ejercicio", Types.NUMERIC));
		declareParameter(new SqlOutParameter("coPeriodo", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto, String coModelo, short ejercicio, String coPeriodo) throws GadirServiceException {
		LOG.debug("Ejecuto FR_DOCUMENTO.BUSCAR_PERIODOS");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("coProvincia", coProvincia);
		params.put("coMunicipio", coMunicipio);
		params.put("coConcepto", coConcepto);
		params.put("coModelo", coModelo);
		params.put("ejercicio", ejercicio);
		params.put("coPeriodo", coPeriodo);
		Map<String, Object> resultsList = null;
		try {
			resultsList= new HashMap<String, Object>();
			resultsList = execute(params);
		} catch (Exception e) {
			LOG.error("Fallo en FR_DOCUMENTO.BUSCAR_PERIODOS", e);
		}
		return resultsList;
	}

}
