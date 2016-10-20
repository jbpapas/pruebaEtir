package es.dipucadiz.etir.comun.bo.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.internal.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.bo.TablaGtGetValorBO;


public class TablaGtGetValorBOImpl extends StoredProcedure implements TablaGtGetValorBO {

	public TablaGtGetValorBOImpl(final DataSource dataSource) {
		super(dataSource, "COMUN_UTIL.TablaGT_getValor");
		setFunction(true);
		declareParameter(new SqlOutParameter("result", OracleTypes.CURSOR,
				new RowMapper() {
					public Map<String, Object> mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
						final Map<String, Object> result = new HashMap<String, Object>();
						final ResultSetMetaData metaData = resultSet.getMetaData();
						for (int i=1; i<=metaData.getColumnCount(); i++) {
							if ("FH_ACTUALIZACION".equals(metaData.getColumnName(i))) {
								result.put(metaData.getColumnName(i), resultSet.getTimestamp(i));
							} else {
								result.put(metaData.getColumnName(i), resultSet.getString(i));
							}
						}
						return result;
					}
				}));
		declareParameter(new SqlParameter("e_tabla", Types.VARCHAR));
		declareParameter(new SqlParameter("e_elemento", Types.VARCHAR));
		declareParameter(new SqlParameter("e_columna", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(final String tabla, final String elemento, final String columna) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("e_tabla", tabla);
		params.put("e_elemento", elemento);
		params.put("e_columna", columna);
		final Map<?, ?> results = execute(params);
		final List<Map<String, Object>> resultsList = (List<Map<String, Object>>) results.get("result");
		Map<String, Object> resultsElemento;
		if (resultsList.isEmpty()) {
			resultsElemento = new HashMap<String, Object>();
		} else {
			resultsElemento = resultsList.get(0);
		}
		return resultsElemento;
	}

}
