package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.boStoredProcedure.CalculoManualDocumentoBO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Batch;
import es.dipucadiz.etir.comun.utilidades.BatchConstants;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;



public class CalculoManualDocumentoBOImpl implements CalculoManualDocumentoBO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4014515149201474353L;
	private static final Log LOG = LogFactory.getLog(CalculoManualDocumentoBOImpl.class);

	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, String tipo, String coProcesoActual) throws GadirServiceException {
		if (Utilidades.isEmpty(tipo)) {
			throw new GadirServiceException("El tipo de llamada al calculo es incorrecto.");
		}
		LOG.debug("Ejecuto FR_DOCUMENTO.calculo_manual_documento.");
		List<String> params = new ArrayList<String>();
		params.add(coModelo);
		params.add(coVersion);
		params.add(coDocumento);
		params.add("1");
		params.add(coProcesoActual);
		params.add(tipo);
		Map<String, Object> resultsList= new HashMap<String, Object>();
		EjecucionDTO ejecucionDTO = Batch.ejecutarDevolverDTO(BatchConstants.CALCULO_MANUAL_DOCUMENTO, params, new AccesoPlantillaVO());
		resultsList.put("resultado", ejecucionDTO.getCoTerminacion());
		resultsList.put("coMensajeError", ejecucionDTO.getCoTerminacion());
		resultsList.put("variable", ejecucionDTO.getObservaciones());
		return resultsList;
	}

}
