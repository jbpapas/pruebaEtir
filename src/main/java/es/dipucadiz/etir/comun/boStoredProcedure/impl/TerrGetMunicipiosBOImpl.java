package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.internal.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.TerrGetMunicipiosBO;



public class TerrGetMunicipiosBOImpl extends StoredProcedure implements TerrGetMunicipiosBO {
	
	private static final Log LOG = LogFactory.getLog(TerrGetMunicipiosBOImpl.class);

	public TerrGetMunicipiosBOImpl(final DataSource dataSource) {
		super(dataSource, "CONTROL_TERRITORIAL.getMunicipios");
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
		declareParameter(new SqlParameter("e_codigoTerritorial", Types.VARCHAR));
		compile();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> execute(final String codigoTerritorial) {
		LOG.trace("Ejecuto getMunicipios: " + codigoTerritorial);
		final Map<String, String> params = new HashMap<String, String>();
		params.put("e_codigoTerritorial", codigoTerritorial);
		List<Map<String, Object>> results = null;
		try {
			results = (List<Map<String, Object>>) execute(params).get("result");
		} catch (Exception e) {
			LOG.debug("Fallo en TerrGetConceptosBOImpl.execute()", e);
		}
		return results;
	}
	

}
