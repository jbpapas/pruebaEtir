package es.dipucadiz.etir.comun.utilidades;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.dto.ExpedienteSeguimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class LenguajilloUtil extends LenguajilloUtilBase {
	private static final Log LOG = LogFactory.getLog(LenguajilloUtil.class);

	private static GenericBO<ExpedienteSeguimientoDTO, Long> expedienteSeguimientoBO;

	public static String interpretaLinkExpediente(Long coExpedienteSeguimiento, String texto) {
		String result;
		try {
			ExpedienteSeguimientoDTO expedienteSeguimientoDTO = expedienteSeguimientoBO.findByIdInitialized(coExpedienteSeguimiento, new String[] {"expedienteClienteDocDTOByCoExpedienteClienteDoc"});
			result = convertirEnLink(texto, "G62SeleccionRue.action?coExpediente=" + expedienteSeguimientoDTO.getExpedienteDTO().getCoExpediente());
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			result = texto;
		}
		return result;
	}

	public static GenericBO<ExpedienteSeguimientoDTO, Long> getExpedienteSeguimientoBO() {
		return expedienteSeguimientoBO;
	}

	public void setExpedienteSeguimientoBO(
			GenericBO<ExpedienteSeguimientoDTO, Long> expedienteSeguimientoBO) {
		LenguajilloUtil.expedienteSeguimientoBO = expedienteSeguimientoBO;
	}

	public static Log getLog() {
		return LOG;
	}
	

}
