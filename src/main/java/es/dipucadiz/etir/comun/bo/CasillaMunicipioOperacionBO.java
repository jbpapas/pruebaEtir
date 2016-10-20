package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CasillaMunicipioOperacionDTO;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioOperacionDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb05.vo.CasillaMunicipioVO;


public interface CasillaMunicipioOperacionBO extends
		GenericBO<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> {

	public CasillaMunicipioOperacionDTO findByCoCasillaMunicipioAndCoOperacion(final long coCasillaMunicipio, final String coOperacion) throws GadirServiceException;
	
	
	
	public Long getNumProcesosByCasillaMunicipio(final String coCasillaMunicipio) throws GadirServiceException;
	
	
	public Boolean findByCoParametro(final String coParametro)throws GadirServiceException;

	public List<CasillaMunicipioOperacionDTO> findByCoCasillaMunicipio(final long coCasillaMunicipio) throws GadirServiceException;
	public List<CasillaMunicipioVO> findByCoCasillaMunicipioVO(final long coCasillaMunicipio) throws GadirServiceException;

}
