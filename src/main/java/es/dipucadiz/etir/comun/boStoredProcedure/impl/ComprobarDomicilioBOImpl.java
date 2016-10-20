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

import es.dipucadiz.etir.comun.boStoredProcedure.ComprobarDomicilioBO;

public class ComprobarDomicilioBOImpl extends StoredProcedure implements ComprobarDomicilioBO {

	private static final Log LOG = LogFactory.getLog(ComprobarDomicilioBOImpl.class);

	public ComprobarDomicilioBOImpl(final DataSource dataSource) {
		super(dataSource, "FU_GA_COMPROBAR_DOMICILIO");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("pco_ejecucion", Types.NUMERIC));
		declareParameter(new SqlParameter("p_co_cliente", Types.NUMERIC));
		declareParameter(new SqlParameter("p_ref_cat_ent", Types.VARCHAR));
		declareParameter(new SqlParameter("p_concepto", Types.VARCHAR));
		declareParameter(new SqlParameter("p_prov", Types.VARCHAR));
		declareParameter(new SqlParameter("p_munic", Types.VARCHAR));
		declareParameter(new SqlParameter("p_sigla", Types.VARCHAR));
		declareParameter(new SqlParameter("e_nom_cl", Types.VARCHAR));
		declareParameter(new SqlParameter("p_numero", Types.NUMERIC));
		declareParameter(new SqlParameter("p_letra", Types.VARCHAR));
		declareParameter(new SqlParameter("p_escalera", Types.VARCHAR));
		declareParameter(new SqlParameter("p_planta", Types.VARCHAR));
		declareParameter(new SqlParameter("p_km", Types.NUMERIC));
		declareParameter(new SqlParameter("p_puerta", Types.VARCHAR));
		declareParameter(new SqlParameter("p_bloque", Types.VARCHAR));
		declareParameter(new SqlParameter("p_cp_ent", Types.VARCHAR));
		declareParameter(new SqlParameter("p_procedencia", Types.VARCHAR));
		declareParameter(new SqlOutParameter("p_cp_sal", Types.NUMERIC));
		declareParameter(new SqlOutParameter("p_ref_cat_sal", Types.VARCHAR));
		declareParameter(new SqlOutParameter("p_mensaje", Types.NUMERIC));
		declareParameter(new SqlParameter("e_bo_tributario", Types.NUMERIC));
		compile();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(Long coCliente, String refCatastral, String coConcepto, String nombreProvincia, String nombreMunicipio, String sigla, String nombreCalle, Integer numero, String letra, String escalera, String planta, BigDecimal km, String puerta, String bloque, Integer cp, String procedencia, Integer boTributario) {
		LOG.debug("Ejecuto FU_GA_COMPROBAR_DOMICILIO");
		final Map<String, Object> params = new HashMap<String, Object>();		        
		   		          
		params.put("pco_ejecucion", null);
		params.put("p_co_cliente", coCliente);
		params.put("p_ref_cat_ent", refCatastral);
		params.put("p_concepto", coConcepto);
		params.put("p_prov", nombreProvincia);
		params.put("p_munic", nombreMunicipio);
		params.put("p_sigla", sigla);
		params.put("e_nom_cl", nombreCalle);
		params.put("p_numero", numero);
		params.put("p_letra", letra);
		params.put("p_escalera", escalera);
		params.put("p_planta", planta);
		params.put("p_km", km);
		params.put("p_puerta", puerta);
		params.put("p_bloque", bloque);
		params.put("p_cp_ent", cp);
		params.put("p_procedencia", procedencia);
		params.put("e_bo_tributario", boTributario);
		
		Map<String, Object> resultsList= new HashMap<String, Object>();
		try {
			resultsList = execute(params);		
		} catch (Exception e) {
			LOG.debug("Fallo en ComprobarDomicilioBOImpl.execute(). ", e);			
		}
		
		return resultsList;
	}
	

}
