package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ExtraccionEstructuraCampoDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionEstructuraCampoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface ExtraccionEstructuraCampoBO extends
        GenericBO<ExtraccionEstructuraCampoDTO, ExtraccionEstructuraCampoDTOId> {
	
	public List<ExtraccionEstructuraCampoDTO> findByEstructura(String coExtraccion, String tipoRegistro, short ordenExtraccion) throws GadirServiceException;
}
