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

import es.dipucadiz.etir.comun.boStoredProcedure.GuardarDocumentacionBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;



public class GuardarDocumentacionBOImpl extends StoredProcedure implements GuardarDocumentacionBO {

	private static final Log LOG = LogFactory.getLog(GuardarDocumentacionBOImpl.class);

	public GuardarDocumentacionBOImpl(final DataSource dataSource) {
		super(dataSource, "fu_ga_guardar_documentacion");
		setFunction(true);
		declareParameter(new SqlOutParameter("grupo", Types.NUMERIC));
		declareParameter(new SqlParameter("e_mod", Types.VARCHAR));
		declareParameter(new SqlParameter("e_ver", Types.VARCHAR));
		declareParameter(new SqlParameter("e_doc", Types.VARCHAR));
		declareParameter(new SqlParameter("e_gru", Types.NUMERIC));
		declareParameter(new SqlParameter("e_obs", Types.VARCHAR));
		declareParameter(new SqlParameter("e_pdf", Types.VARCHAR));
		declareParameter(new SqlParameter("e_eje", Types.NUMERIC));
		declareParameter(new SqlParameter("e_usu", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public Long execute(String coModelo, String coVersion, String coDocumento, Long coBDDocumentalGrupo, String observaciones, String nombrePdfGadirNFS, Long coEjecucion, String coAcmUsuario) throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_mod", coModelo);
		params.put("e_ver", coVersion);
		params.put("e_doc", coDocumento);
		params.put("e_gru", coBDDocumentalGrupo);
		params.put("e_obs", observaciones);
		params.put("e_pdf", nombrePdfGadirNFS);
		params.put("e_eje", coEjecucion);
		params.put("e_usu", coAcmUsuario);
		Map<String, Object> resultsList= new HashMap<String, Object>();
		Long result;
		try {
			resultsList = execute(params);
			if (resultsList.containsKey("grupo")) {
				BigDecimal resultTmp = (BigDecimal) resultsList.get("grupo");
				if (resultTmp != null) {
					result = resultTmp.longValue();
					if (result < 0) {
						throw new GadirServiceException("Función FU_GA_GUARDAR_DOCUMENTACION devuelve error " + result + ", consulte LOG.");
					}
				} else {
					result = null;
				}
			} else {
				LOG.error("Función FU_GA_GUARDAR_DOCUMENTACION no devuelve grupo.");
				throw new GadirServiceException("Función FU_GA_GUARDAR_DOCUMENTACION no devuelve grupo.");
			}
		} catch (GadirServiceException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new GadirServiceException("Error al guardar documentación: " + e.getMessage(), e);
		}
		return result;
	}

	public Long execute(Long coBDDocumentalGrupo, String observaciones, String nombrePdfGadirNFS, Long coEjecucion, String coAcmUsuario) throws GadirServiceException {
		return execute(null, null, null, coBDDocumentalGrupo, observaciones, nombrePdfGadirNFS, coEjecucion, coAcmUsuario);
	}

}
