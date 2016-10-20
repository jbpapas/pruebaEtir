package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.bo.TablaGtIsElementoBO;


public class TablaGtIsElementoBOImpl extends StoredProcedure implements TablaGtIsElementoBO {

	public TablaGtIsElementoBOImpl(final DataSource dataSource) {
		super(dataSource, "COMUN_UTIL.TablaGT_isElemento");
		setFunction(true);
		declareParameter(new SqlOutParameter("result", Types.NUMERIC));
		declareParameter(new SqlParameter("e_tabla", Types.VARCHAR));
		declareParameter(new SqlParameter("e_elemento", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public boolean execute(final String tabla, final String elemento) {
		final Map params = new HashMap();
		params.put("e_tabla", tabla);
		params.put("e_elemento", elemento);
		final Map results = execute(params);
		return ((BigDecimal)results.get("result")).intValue() == 1;
	}

}
