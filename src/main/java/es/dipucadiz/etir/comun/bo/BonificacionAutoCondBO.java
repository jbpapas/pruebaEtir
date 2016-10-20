package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.dto.BonificacionAutoCondDTO;
import es.dipucadiz.etir.comun.dto.BonificacionAutoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase del
 * banco {@link BonificacionAutoCondDTO}.
 * 
 */
public interface BonificacionAutoCondBO extends GenericBO<BonificacionAutoCondDTO, Long> {

	void deleteByBonifiAutoId(Long coBonificacionAutoSel) throws GadirServiceException;
	Byte findOrdenSiguiente(BonificacionAutoDTO bonificacionAutoDTO);
	
}
