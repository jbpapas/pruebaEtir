package es.dipucadiz.etir.comun.bo.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.vo.RegCandidatoMapper;
import es.dipucadiz.etir.comun.vo.RegCandidatoVO;

/**
 * Clase responsable de realizar la llamada a la funcion PL
 * FU_GA_BUSQUEDA_CANDIDATOS. La función devolverá una lista de
 * {@link RegCandidatoVO}
 * 
 * @version 1.0 18/01/2009
 * @author SDS[AGONZALEZ]
 */
public class BuscarCandidatosBOImpl extends StoredProcedure implements Serializable {
	private static final long serialVersionUID = -3394980498940833601L;

	/**
	 * Constructor de la clase.
	 * 
	 * @param dataSource dataSource.
	 */
	public BuscarCandidatosBOImpl(final DataSource dataSource) {
		super(dataSource, "FU_GA_BUSQUEDA_CANDIDATOS");
		setFunction(true);
		declareParameter(new SqlOutParameter("result", OracleTypes.CURSOR,
		        new RegCandidatoMapper()));
		declareParameter(new SqlParameter("P_NIF", Types.VARCHAR));
		declareParameter(new SqlParameter("P_RAZON_SOCIAL", Types.VARCHAR));
		declareParameter(new SqlParameter("P_MODELO", Types.VARCHAR));
		declareParameter(new SqlParameter("P_VERSION", Types.VARCHAR));
		declareParameter(new SqlParameter("P_DOCUMENTO", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public Map execute(final String nif, final String razonSocial,
	        final String modelo, final String version,
	        final String codigoDocumento) {
		final Map params = new HashMap();
		params.put("P_NIF", nif);
		params.put("P_RAZON_SOCIAL", razonSocial);
		params.put("P_MODELO", modelo);
		params.put("P_VERSION", version);
		params.put("P_DOCUMENTO", codigoDocumento);
		return execute(params);
	}
}
