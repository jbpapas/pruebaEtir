package es.dipucadiz.etir.comun.bo.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;



public class GenerarSecuenciaContadorBOImpl extends StoredProcedure implements Serializable{
	private static final long serialVersionUID = 2538141247412763869L;

	/**
	 * Constructor de la clase.
	 * 
	 * @param dataSource dataSource.
	 */
	public GenerarSecuenciaContadorBOImpl(final DataSource dataSource) {
		super(dataSource, "LLAMA_OBTENER_SECUENCIA");
		this.setFunction(true);
		declareParameter(new SqlOutParameter("result", Types.NUMERIC));
		declareParameter(new SqlParameter("p_co_usuario", Types.VARCHAR));
		declareParameter(new SqlParameter("p_fecha", Types.DATE));
		declareParameter(new SqlParameter("f_tipo", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public Map execute(final String tipo, final String coUsuario, final Date fecha) {
		final Map params = new HashMap();
		params.put("p_co_usuario",coUsuario);
		params.put("p_fecha",fecha);
		params.put("f_tipo", tipo);
		return execute(params);
	}
}
