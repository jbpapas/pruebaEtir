package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ExtraccionOrdenCampoDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionOrdenCampoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface ExtraccionOrdenCampoBO extends
        GenericBO<ExtraccionOrdenCampoDTO, ExtraccionOrdenCampoDTOId> {

	public List<ExtraccionOrdenCampoDTO> findByOrden(String coExtraccion, String tipoRegistro, short orden) throws GadirServiceException;
	
}
