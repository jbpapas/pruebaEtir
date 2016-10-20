package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.boStoredProcedure.AltaManualDocumentoBO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Batch;
import es.dipucadiz.etir.comun.utilidades.BatchConstants;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;



public class AltaManualDocumentoBOImpl implements AltaManualDocumentoBO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6921520370206202488L;
	private static final Log LOG = LogFactory.getLog(AltaManualDocumentoBOImpl.class);

	public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, String coModeloOrigen, String coVersionOrigen, String coDocumentoOrigen, Long coCliente, String refCatastral, Long coDomicilio, String fxInicio, String fxFin, String coProcesoActual) {
		return execute(coProvincia, coMunicipio, coConcepto, coModelo, coVersion, coModeloOrigen, coVersionOrigen, coDocumentoOrigen, coCliente, refCatastral, coDomicilio, fxInicio, fxFin, false, coProcesoActual);
	}
	public Map<String, Object> execute(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, String coModeloOrigen, String coVersionOrigen, String coDocumentoOrigen, Long coCliente, String refCatastral, Long coDomicilio, String fxInicio, String fxFin, boolean todasCasillas, String coProcesoActual) {
		LOG.debug("Ejecuto FR_DOCUMENTO.alta_manual_documento.");
		List<String> params = new ArrayList<String>();
		params.add(coProvincia);
		params.add(coMunicipio);
		params.add(coConcepto);
		params.add(coModelo);
		params.add(coVersion);
		params.add(coModeloOrigen);
		params.add(coVersionOrigen);
		params.add(coDocumentoOrigen);
		params.add(coCliente == null ? "" : Long.toString(coCliente));
		params.add(refCatastral);
		params.add(coDomicilio == null ? "" : Long.toString(coDomicilio));
		params.add(fxInicio);
		params.add(fxFin);
		params.add(todasCasillas ? "1" : "0");
		params.add(coProcesoActual);
		Map<String, Object> resultsList= new HashMap<String, Object>();
		try {
			EjecucionDTO ejecucionDTO = Batch.ejecutarDevolverDTO(BatchConstants.ALTA_MANUAL_DOCUMENTO, params, new AccesoPlantillaVO());
			if (ejecucionDTO.getCoTerminacion() == 0) {
				resultsList.put("resultado", BigDecimal.ZERO);
				resultsList.put("coMensajeError", BigDecimal.ZERO);
				resultsList.put("coDocumento", ejecucionDTO.getObservaciones());
			} else {
				resultsList.put("resultado", new BigDecimal(ejecucionDTO.getCoTerminacion()));
				resultsList.put("coMensajeError", new BigDecimal(ejecucionDTO.getCoTerminacion()));
				resultsList.put("variable", ejecucionDTO.getObservaciones());
			}
		} catch (GadirServiceException e) {
			resultsList.put("resultado", new BigDecimal(4658));
			resultsList.put("coMensajeError", new BigDecimal(4658));
			resultsList.put("variable", e.getMensaje());
		}
		
		return resultsList;
	}

}
