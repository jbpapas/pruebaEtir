package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.CalleUbicacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CalleUbicacionBO extends GenericBO<CalleUbicacionDTO, Long>{
	CalleUbicacionDTO obtenerUbicacionByCalle(Long codCalle) throws GadirServiceException;
}
