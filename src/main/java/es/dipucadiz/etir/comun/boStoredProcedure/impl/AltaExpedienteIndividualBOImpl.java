package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.AltaExpedienteIndividualBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public class AltaExpedienteIndividualBOImpl extends StoredProcedure implements AltaExpedienteIndividualBO  {

	private static final Log LOG = LogFactory.getLog(AltaExpedienteIndividualBOImpl.class);

	public AltaExpedienteIndividualBOImpl(final DataSource dataSource) {
		super(dataSource, "EJ_COMUN.alta_expediente_individual");
		LOG.info("EJ_COMUN.alta_expediente_individual");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("e_co_cliente", Types.NUMERIC));
		declareParameter(new SqlParameter("e_co_provincia", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_municipio", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_circuito", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_exp_seg", Types.NUMERIC));		
		
	 
		compile();
	}

	@SuppressWarnings("unchecked")
	public BigDecimal execute( String e_co_cliente, String e_co_provincia, String e_co_municipio, String e_co_circuito, String e_co_exp_segl) throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		//params.put("e_co_cliente", Long.getLong(e_co_cliente));
		params.put("e_co_cliente",  e_co_cliente);		
		params.put("e_co_provincia",e_co_provincia);
		params.put("e_co_municipio", e_co_municipio);
		params.put("e_co_circuito", e_co_circuito);
		//params.put("e_co_exp_seg",  Long.getLong(e_co_exp_segl));
		params.put("e_co_exp_seg",   e_co_exp_segl); 
		Map<String, Object> resultsList = new HashMap<String, Object>();
		BigDecimal resultadoFinal = BigDecimal.ZERO; 
		 
		try {
			resultsList = execute(params);
			if (resultsList == null) {
				throw new GadirServiceException("EJ_COMUN.alta_expediente_individual devuelve null.");
			} else {
				if (!resultsList.containsKey("resultado")) {
					throw new GadirServiceException("EJ_COMUN.alta_expediente_individual no contiene resultado.");
				} 
				
				else if(resultsList.get("resultado")==null){
					throw new GadirServiceException("EJ_COMUN.alta_expediente_individual No se ha podido dar de alta el expediente. Contacte con el administrador del sistema.");
				}else {
					  resultadoFinal = ((BigDecimal) resultsList.get("resultado"));
					if (resultadoFinal == null) {
						throw new GadirServiceException("Error al dar alta individual del expediente");
					} else {
						return  resultadoFinal ;
						 
 
					}
				}
				
			}
		} catch (Exception e) {
			throw new GadirServiceException(e);
		}
		 
	}

}
