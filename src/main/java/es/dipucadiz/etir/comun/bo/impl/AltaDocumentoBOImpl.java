package es.dipucadiz.etir.comun.bo.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;



public class AltaDocumentoBOImpl extends StoredProcedure implements Serializable{
	private static final long serialVersionUID = -367814501470156617L;

	/**
	 * Constructor de la clase.
	 * 
	 * @param dataSource dataSource.
	 */
	public AltaDocumentoBOImpl(final DataSource dataSource) {
		//TODO FALTA EL NOMBRE DE LA PL Y COMPROBAR LOS PARAMETROS
		super(dataSource, "");
		this.setFunction(true);
		declareParameter(new SqlOutParameter("CO_PROVINCIA", Types.VARCHAR));
		declareParameter(new SqlParameter("CO_MUNICIPIO", Types.VARCHAR));
		declareParameter(new SqlParameter("CO_CONCEPTO", Types.VARCHAR));
		declareParameter(new SqlParameter("CO_MODELO", Types.VARCHAR));
		declareParameter(new SqlOutParameter("CO_VERSION", Types.VARCHAR));
		declareParameter(new SqlParameter("CO_MODELO_ORIGEN", Types.VARCHAR));
		declareParameter(new SqlParameter("CO_VERSION_ORIGEN", Types.VARCHAR));
		declareParameter(new SqlParameter("CO_DOCUMENTO_ORIGEN", Types.VARCHAR));
		declareParameter(new SqlOutParameter("REFERENCIA_CATASTRAL", Types.VARCHAR));

		compile();
	}

	@SuppressWarnings("unchecked")

	public Map execute(final String CoProvincia, final String CoMunicpio,final String CoConcepto,
			final String CoModelo,final String CoVersion,final String CoModeloOrigen,
			final String CoVersionOrigen,final String CoDocumentoOrigen,String RefCatastral) {
	
		final Map params = new HashMap();
		params.put("CO_PROVINCIA",CoProvincia);
		params.put("CO_MUNICIPIO",CoMunicpio);
		params.put("CO_CONCEPTO",CoConcepto);
		params.put("CO_MODELO",CoModelo);
		params.put("CO_VERSION",CoVersion);
		params.put("CO_MODELO_ORIGEN",CoModeloOrigen);
		params.put("CO_VERSION_ORIGEN",CoVersionOrigen);
		params.put("CO_DOCUMENTO_ORIGEN",CoDocumentoOrigen);
		params.put("REFERENCIA_CATASTRAL",RefCatastral);	
		return execute(params);
	}
}
