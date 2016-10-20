package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.boStoredProcedure.CrearHojasBO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Batch;
import es.dipucadiz.etir.comun.utilidades.BatchConstants;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;



public class CrearHojasBOImpl implements CrearHojasBO, Serializable {
	private static final long serialVersionUID = -5534374970983166848L;
	private static final Log LOG = LogFactory.getLog(CrearHojasBOImpl.class);

	public Map<String, Object> execute(String coModelo, String coVersion, String coDocumento, String inicio, String fin, String coProcesoActual) {
		LOG.debug("Ejecuto FR_DOCUMENTO.crear_hojas.");
		List<String> params = new ArrayList<String>();
		params.add(coModelo);
		params.add(coVersion);
		params.add(coDocumento);
		params.add(inicio);
		params.add(fin);
		params.add(coProcesoActual);
		Map<String, Object> resultsList= new HashMap<String, Object>();
		try {
			EjecucionDTO ejecucionDTO = Batch.ejecutarDevolverDTO(BatchConstants.CREAR_HOJAS_DOCUMENTO, params, new AccesoPlantillaVO());
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
