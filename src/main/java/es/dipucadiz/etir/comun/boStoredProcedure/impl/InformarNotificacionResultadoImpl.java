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

import es.dipucadiz.etir.comun.boStoredProcedure.InformarNotificacionResultadoBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.vo.CalculoInteresesVO;



public class InformarNotificacionResultadoImpl extends StoredProcedure implements InformarNotificacionResultadoBO  {

	private static final Log LOG = LogFactory.getLog(InformarNotificacionResultadoImpl.class);
	private String mensaje;
	
	
	public InformarNotificacionResultadoImpl(final DataSource dataSource) {
		super(dataSource, "CORRESP_NORE.peticion"); //pl al que llama
		LOG.info("CORRESP_NORE.peticion");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("e_nu_peticion", Types.NUMERIC));  
		declareParameter(new SqlOutParameter("e_msg", Types.VARCHAR)); 
 
		compile();
	}

	@SuppressWarnings("unchecked")
	public void execute(Long nuPeticion) throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("e_nu_peticion", nuPeticion);
		params.put("e_msg", mensaje);
		
		Map<String, Object> resultsList = new HashMap<String, Object>();
		 
		try {
			resultsList = execute(params);
			if (resultsList == null) {
				throw new GadirServiceException("CORRESP_NORE.peticion devuelve null.");
			} else {
				if (!resultsList.containsKey("resultado")) {
					throw new GadirServiceException("CORRESP_NORE.PETICION no contiene resultado.");
				} else {
					int resultado = ((BigDecimal) resultsList.get("resultado")).intValue();
					if (resultado != 0) {
						throw new GadirServiceException(Mensaje.getTexto(resultado));
					}  
				}
			}
		} catch (Exception e) {
			throw new GadirServiceException(e);
		}
		 
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	

}
