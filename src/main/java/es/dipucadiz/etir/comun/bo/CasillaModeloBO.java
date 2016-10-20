package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CasillaModeloDTO;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface CasillaModeloBO extends
        GenericBO<CasillaModeloDTO, CasillaModeloDTOId> {

	
	List<CasillaModeloDTO> findCasillasModeloByVersionModelo(String codVersion,
	        String codModelo) throws GadirServiceException;
	
	List<CasillaModeloDTO> findCasillasALigar(CasillaModeloDTOId idCasilla)
	        throws GadirServiceException;
	
	public List<Integer> tieneRelacionesNoEliminables(final CasillaModeloDTOId id) throws GadirServiceException;
}
