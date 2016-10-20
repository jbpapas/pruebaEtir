package es.dipucadiz.etir.comun.bo.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.bo.MensajeGetTextoBO;


public class MensajeGetTextoBOImpl extends StoredProcedure implements MensajeGetTextoBO {

	public MensajeGetTextoBOImpl(final DataSource dataSource) {
		super(dataSource, "COMUN_UTIL.Mensaje_getTexto");
		setFunction(true);
		declareParameter(new SqlOutParameter("result", Types.VARCHAR));
		declareParameter(new SqlParameter("e_codigo", Types.NUMERIC));
		declareParameter(new SqlParameter("e_variable1", Types.VARCHAR));
		declareParameter(new SqlParameter("e_variable2", Types.VARCHAR));
		declareParameter(new SqlParameter("e_variable3", Types.VARCHAR));
		declareParameter(new SqlParameter("e_variable4", Types.VARCHAR));
		declareParameter(new SqlParameter("e_variable5", Types.VARCHAR));
//		declareParameter(new SqlParameter("e_variables", Types.ARRAY, "CUST_DYNAMIC_TABLE_OF_100"));
		compile();
	}

	@SuppressWarnings("unchecked")
	public String execute(final int codigoError, final List<String> variables) {
		final Map params = new HashMap();
		params.put("e_codigo", codigoError);
		params.put("e_variable1", variables.size() > 0 ? variables.get(0) : null);
		params.put("e_variable2", variables.size() > 1 ? variables.get(1) : null);
		params.put("e_variable3", variables.size() > 2 ? variables.get(2) : null);
		params.put("e_variable4", variables.size() > 3 ? variables.get(3) : null);
		params.put("e_variable5", variables.size() > 4 ? variables.get(4) : null);
//		params.put("e_variables", 
//				new AbstractSqlTypeValue() 
//				{
//					public Object createTypeValue(final Connection connection, final int type, final String typeName) throws SQLException {
//						final PoolableConnection pConnection = (PoolableConnection) connection;
//						final OracleConnection oConnection = (OracleConnection) pConnection.getDelegate();
//						final ArrayDescriptor desc = new ArrayDescriptor(typeName, oConnection);
//						return new oracle.sql.ARRAY(desc, oConnection, variables.toArray());
//					}
//				});
		final Map results = execute(params);
		return ((String)results.get("result"));
	}

}
