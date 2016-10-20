package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.ComprobarCuentaClienteBO;

public class ComprobarCuentaClienteBOImpl extends StoredProcedure implements ComprobarCuentaClienteBO {

	private static final Log LOG = LogFactory.getLog(ComprobarDomicilioBOImpl.class);

	public ComprobarCuentaClienteBOImpl(final DataSource dataSource) {
		super(dataSource, "FU_GA_COMPROBAR_CUENTA_CLIENTE");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("e_co_banco", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_banco_sucursal", Types.VARCHAR));
		declareParameter(new SqlParameter("e_dc", Types.VARCHAR));
		declareParameter(new SqlParameter("e_cuenta", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_cliente", Types.NUMERIC));
		declareParameter(new SqlParameter("e_procedencia", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_modelo", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_version", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_documento", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(String coBanco, String coBancoSucursal, String dc, String cuenta, Long coCliente, String procedencia, String coModelo, String coVersion, String coDocumento) {
		LOG.debug("Ejecuto FU_GA_COMPROBAR_CUENTA_CLIENTE");
		final Map<String, Object> params = new HashMap<String, Object>();		        
		   		    
		params.put("e_co_banco", coBanco);
		params.put("e_co_banco_sucursal", coBancoSucursal);
		params.put("e_dc", dc);
		params.put("e_cuenta", cuenta);
		params.put("e_co_cliente", coCliente);
		params.put("e_procedencia", procedencia);
		params.put("e_co_modelo", coModelo);
		params.put("e_co_version", coVersion);
		params.put("e_co_documento", coDocumento);
		
		Map<String, Object> resultsList= new HashMap<String, Object>();
		try {
			resultsList = execute(params);		
		} catch (Exception e) {
			LOG.debug("Fallo en ComprobarCuentaClienteBOImpl.execute(). ", e);			
		}
		
		return resultsList;
	}
	

}
