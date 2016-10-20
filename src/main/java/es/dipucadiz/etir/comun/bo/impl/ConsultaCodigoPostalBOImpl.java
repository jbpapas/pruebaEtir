package es.dipucadiz.etir.comun.bo.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.bo.ConsultaCodigoPostalBO;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ConsultaCodigoPostalBOImpl extends StoredProcedure implements ConsultaCodigoPostalBO, Serializable {
	private static final long serialVersionUID = 7333437868989030885L;

	public ConsultaCodigoPostalBOImpl(final DataSource dataSource) {
		super(dataSource, "COMUN_UTIL.consultaCodigoPostal");
		setFunction(true);
		declareParameter(new SqlOutParameter("result", Types.NUMERIC));
		declareParameter(new SqlParameter("e_coCalle", Types.NUMERIC));
		declareParameter(new SqlParameter("e_numero", Types.NUMERIC));
		compile();
	}

	@SuppressWarnings("unchecked")
	public int execute(Long coCalle, Integer numero) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("e_coCalle", Long.toString(coCalle));
		params.put("e_numero", Utilidades.isEmpty(numero) ? null : Integer.toString(numero));
		final Map<String, BigDecimal> results = super.execute(params);
		return results.get("result").intValue();
	}
}
