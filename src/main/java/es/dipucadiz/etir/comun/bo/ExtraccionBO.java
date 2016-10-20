package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ExtraccionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface ExtraccionBO extends GenericBO<ExtraccionDTO, String> {

	public List<ExtraccionDTO> findEdtructuraParametrosYGenerico(final String codMunicipio, final String codModelo) throws GadirServiceException;
	
	public List<Object[]> findCompatibles(final String coCodigoTerritorialGenerico) throws GadirServiceException;
	
	public List<Object[]> findCompatiblesNoExpertos(final String coCodigoTerritorialGenerico) throws GadirServiceException;
}
