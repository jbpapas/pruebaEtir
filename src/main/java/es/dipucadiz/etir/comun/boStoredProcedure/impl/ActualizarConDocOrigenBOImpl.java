package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.boStoredProcedure.ActualizarConDocOrigenBO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Batch;
import es.dipucadiz.etir.comun.utilidades.BatchConstants;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;



public class ActualizarConDocOrigenBOImpl implements ActualizarConDocOrigenBO {

	private static final Log LOG = LogFactory.getLog(ActualizarConDocOrigenBOImpl.class);

	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, String coModeloOrigen, String coVersionOrigen, String coDocumentoOrigen, String coProcesoActual) {
		LOG.debug("Ejecuto FR_DOCUMENTO.ACTUALIZAR_CON_DOC_ORIGEN");
		List<String> params = new ArrayList<String>();
		params.add(coModelo);
		params.add(coVersion);
		params.add(coDocumento);
		params.add(coModeloOrigen);
		params.add(coVersionOrigen);
		params.add(coDocumentoOrigen);
		params.add("0");
		params.add(coProcesoActual);
		Map<String, Object> resultsList= new HashMap<String, Object>();
		try {
			EjecucionDTO ejecucionDTO = Batch.ejecutarDevolverDTO(BatchConstants.ACTUALIZAR_CON_DOCUMENTO_ORIGEN, params, new AccesoPlantillaVO());
			resultsList.put("resultado", ejecucionDTO.getCoTerminacion());
			resultsList.put("coMensajeError", ejecucionDTO.getCoTerminacion());
			resultsList.put("variable", ejecucionDTO.getObservaciones());
		} catch (GadirServiceException e) {
			resultsList.put("resultado", MensajeConstants.V1);
			resultsList.put("coMensajeError", MensajeConstants.V1);
			resultsList.put("variable", e.getMensaje());
		}
		return resultsList;
	}

}
