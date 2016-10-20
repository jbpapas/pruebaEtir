package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ExtraccionOrdenDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionOrdenDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface ExtraccionOrdenBO extends
        GenericBO<ExtraccionOrdenDTO, ExtraccionOrdenDTOId> {
	
	public List<ExtraccionOrdenDTO> findByTipoRegistro(String coExtraccion, String tipoRegistro) throws GadirServiceException;

}
