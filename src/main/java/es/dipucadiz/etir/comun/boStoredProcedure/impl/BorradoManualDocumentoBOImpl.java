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

import es.dipucadiz.etir.comun.boStoredProcedure.BorradoManualDocumentoBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;



public class BorradoManualDocumentoBOImpl extends StoredProcedure implements BorradoManualDocumentoBO {

	private static final Log LOG = LogFactory.getLog(BorradoManualDocumentoBOImpl.class);

	public BorradoManualDocumentoBOImpl(final DataSource dataSource) {
		super(dataSource, "G7_BORRAR_DOCUMENTO");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("e_co_usuario_actualizacion", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_proceso", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_ejecucion", Types.NUMERIC));
		declareParameter(new SqlParameter("e_co_modelo", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_version", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_documento", Types.VARCHAR));
		declareParameter(new SqlParameter("e_tipo_borrado", Types.VARCHAR));
		declareParameter(new SqlParameter("e_commit", Types.NUMERIC));
		compile();
	}

	@SuppressWarnings("unchecked")
	public int execute(String coModelo, String coVersion, String coDocumento, String tipoBorrado, String coProcesoActual) throws GadirServiceException {
		LOG.debug("Ejecuto G7_BORRAR_DOCUMENTO");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_co_usuario_actualizacion", DatosSesion.getLogin());
		params.put("e_co_proceso", coProcesoActual);
		params.put("e_co_ejecucion", null);
		params.put("e_co_modelo", coModelo);
		params.put("e_co_version", coVersion);
		params.put("e_co_documento", coDocumento);
		params.put("e_tipo_borrado", tipoBorrado);
		params.put("e_commit", 1);
		int result = 47;
		try {
			Map<String, Object> resultsList= new HashMap<String, Object>();
			resultsList = execute(params);
			result = ((BigDecimal) resultsList.get("resultado")).intValue();
		} catch (Exception e) {
			LOG.debug("Fallo en BorradoManualDocumentoBOImpl.execute(). " + coModelo + coVersion + coDocumento + tipoBorrado, e);
			throw new GadirServiceException(e.getMessage(), e);
		}
		
		return result;
	}

}
