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

import es.dipucadiz.etir.comun.bo.TablaGtGetListaElementosBO;


public class TablaGtGetListaElementosBOImpl extends StoredProcedure implements TablaGtGetListaElementosBO {

	public TablaGtGetListaElementosBOImpl(final DataSource dataSource) {
		super(dataSource, "COMUN_UTIL.TablaGT_getListaElementos");
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
		declareParameter(new SqlParameter("e_columna", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> execute(final String tabla, final String columna) {
		final Map params = new HashMap();
		params.put("e_tabla", tabla);
		params.put("e_columna", columna);
		final Map results = execute(params);
		return (List) results.get("result");
	}

}
