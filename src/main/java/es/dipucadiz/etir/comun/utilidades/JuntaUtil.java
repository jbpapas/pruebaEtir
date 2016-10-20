package es.dipucadiz.etir.comun.utilidades;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.DocumentoCasillaValorBO;
import es.dipucadiz.etir.comun.constants.CasillaConstants;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class JuntaUtil {
	
	private static DocumentoCasillaValorBO documentoCasillaValorBO;
	private static final Log LOG = LogFactory.getLog(JuntaUtil.class);
	
	public static String getDescripcionConceptoJunta(DocumentoDTO documentoDTO) {
//		String result = null;
//		try {
//			DocumentoCasillaValorDTOId id = new DocumentoCasillaValorDTOId();
//			id.setCoModelo(documentoDTO.getId().getCoModelo());
//			id.setCoVersion(documentoDTO.getId().getCoVersion());
//			id.setCoDocumento(documentoDTO.getId().getCoDocumento());
//			id.setHoja(CasillaConstants.PRIMERA_HOJA);
//			id.setNuCasilla(CasillaConstants.NU_CASILLA_CONCEPTO_JUNTA);
//			DocumentoCasillaValorDTO dto = documentoCasillaValorBO.findById(id);
//			if (dto != null && Utilidades.isNotEmpty(dto.getValor())) {
//				KeyValue keyValue = TablaGt.getCodigoDescripcion(TablaGtConstants.TABLA_CONCEPTO_JUNTA, dto.getValor());
//				if (keyValue != null && Utilidades.isNotEmpty(keyValue.getValue())) {
//					result = keyValue.getCodigoDescripcion();
//				}
//			}
//		} catch (GadirServiceException e) {
//			LOG.error(e.getMensaje(), e);
//		}
//		
//		if (Utilidades.isEmpty(result) && documentoDTO.getConceptoDTO()!=null) {
//			result = MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(documentoDTO.getConceptoDTO().getCoConcepto());
//		}
//		return result;
		return getConceptoJunta(documentoDTO);
	}
	
	public static String getConceptoJunta(DocumentoDTO documentoDTO) {
		String result = null;
		try {
			DocumentoCasillaValorDTOId id = new DocumentoCasillaValorDTOId();
			id.setCoModelo(documentoDTO.getId().getCoModelo());
			id.setCoVersion(documentoDTO.getId().getCoVersion());
			id.setCoDocumento(documentoDTO.getId().getCoDocumento());
			id.setHoja(CasillaConstants.PRIMERA_HOJA);
			id.setNuCasilla(CasillaConstants.NU_CASILLA_CONCEPTO_JUNTA);
			DocumentoCasillaValorDTO dto = documentoCasillaValorBO.findById(id);
			if (dto != null && Utilidades.isNotEmpty(dto.getValor())) {
				result = dto.getValor();
			}
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
		}
		
		if (Utilidades.isEmpty(result) && documentoDTO.getConceptoDTO()!=null) {
			result = MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(documentoDTO.getConceptoDTO().getCoConcepto());
		}
		return result;
	}

	public static DocumentoCasillaValorBO getDocumentoCasillaValorBO() {
		return documentoCasillaValorBO;
	}

	public void setDocumentoCasillaValorBO(
			DocumentoCasillaValorBO documentoCasillaValorBO) {
		JuntaUtil.documentoCasillaValorBO = documentoCasillaValorBO;
	}

	public static Log getLog() {
		return LOG;
	}
	
}
