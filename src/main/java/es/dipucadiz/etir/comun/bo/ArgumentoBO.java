package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CruceDTO;
import es.dipucadiz.etir.comun.dto.CruceDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.ArgumentoVO;

public interface ArgumentoBO extends GenericBO<CruceDTO, CruceDTOId> {
	
	List<ArgumentoVO> findByCruce(Long idCruce) throws GadirServiceException;
	
	
	/**
	 * Método encargado de buscar los argumentos de un cruce que tengan condiciones y resultados
	 * 
	 * @param cruce
	 * @throws GadirServiceException
	 */
	List<ArgumentoVO> findByCruceConCondicionAndResultado(Long idCruce) throws GadirServiceException;
	
	
	
	
	/**
	 * Método encargado de eliminar un argumento y sus datos relacionados.
	 * 
	 * @param argumento
	 * @throws GadirServiceException
	 */
	void eliminarArgumento(CruceDTO argumento)throws GadirServiceException;
	
	/**
	 * Método encargado de devolver el máximo id de coArgumento en cruces
	 * 
	 * @return
	 */
	Integer crearId(String coCruce);
	
	
	/**
	 * Método encargado de devolver el argumento por su rowid con fetchConcepto y m
	 * 
	 * @return
	 */
	CruceDTO findByRowidConCondicionAndResultado(String rowid) throws GadirServiceException;
}