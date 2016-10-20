package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.ContadorRefObjDTO;
import es.dipucadiz.etir.comun.dto.HContadorRefObjDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface HContadorRefObjBO extends GenericBO<HContadorRefObjDTO, Long> {
	/**
	 * Grabar en histórico a partir de contadorRefObjDTO.
	 * @param contadorRefObjDTO
	 * @param movimiento "A" - Alta. "M" - Modificación. "B" - Borrado.
	 * @throws GadirServiceException
	 */
	public void guardarHContadorRefObj(ContadorRefObjDTO contadorRefObjDTO, String movimiento) throws GadirServiceException;
}
