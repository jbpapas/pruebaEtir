package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.GuardarCasillasMasivoBO;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;



public class GuardarCasillasMasivoBOImpl extends StoredProcedure implements GuardarCasillasMasivoBO {

	private static final Log LOG = LogFactory.getLog(GuardarCasillasMasivoBOImpl.class);

	public GuardarCasillasMasivoBOImpl(final DataSource dataSource) {
		super(dataSource, "FR_DOCUMENTO.grabar_casillas");
		setFunction(false);
//		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("e_mod", Types.VARCHAR));
		declareParameter(new SqlParameter("e_ver", Types.VARCHAR));
		declareParameter(new SqlParameter("e_doc", Types.VARCHAR));
		declareParameter(new SqlParameter("e_hojas", Types.VARCHAR));
		declareParameter(new SqlParameter("e_casillas", Types.VARCHAR));
		declareParameter(new SqlParameter("e_valores", Types.VARCHAR));
		declareParameter(new SqlParameter("e_errores", Types.VARCHAR));
//		declareParameter(new SqlParameter("e_fecha", Types.TIMESTAMP));
//		declareParameter(new SqlParameter("e_usuario", Types.VARCHAR));
		declareParameter(new SqlOutParameter("coMensajeError", Types.NUMERIC));
		declareParameter(new SqlOutParameter("variable", Types.VARCHAR));
		declareParameter(new SqlParameter("e_proceso", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, String hojas, String casillas, String valores, String errores, Date fhActualizacion, String coProcesoActual) {
		LOG.debug("Ejecuto FR_DOCUMENTO.grabar_casillas.");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_mod", coModelo);
		params.put("e_ver", coVersion);
		params.put("e_doc", coDocumento);
		params.put("e_hojas", hojas);
		params.put("e_casillas", casillas);
		params.put("e_valores", valores);
		params.put("e_errores", errores);
//		params.put("e_fecha", fhActualizacion);
//		params.put("e_usuario", DatosSesion.getLogin());
		params.put("e_proceso", coProcesoActual);
		Map<String, Object> resultsList= new HashMap<String, Object>();
		try {
			resultsList = execute(params);
		} catch (Exception e) {
			LOG.error("Fallo en GuardarCasillasMasivoBOImpl.execute()", e);
		}
		
		return resultsList;
	}

}
