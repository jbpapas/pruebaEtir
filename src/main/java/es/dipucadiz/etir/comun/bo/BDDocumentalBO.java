package es.dipucadiz.etir.comun.bo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.dto.BDDocumentalDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb06.vo.AdjuntoVO;

public interface BDDocumentalBO extends GenericBO<BDDocumentalDTO, Long> {
	public DetachedCriteria getCriterioAdjuntosByExpediente(Long coExpediente, Long coExpedienteSeguimientoActual, boolean filtrarSeguimiento, boolean ordenar, boolean limitarRownum);
	public List<AdjuntoVO> findAdjuntosByExpediente(Long coExpediente, Long coExpedienteSeguimientoActual, boolean filtrarSeguimiento) throws GadirServiceException;
	public String getIdAlfrescoFirmado(Long coBdDocumentalGrupo) throws GadirServiceException;
	
}
