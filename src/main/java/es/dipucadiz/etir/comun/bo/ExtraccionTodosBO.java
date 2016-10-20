package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface ExtraccionTodosBO {

	public void borraExtraccionCompleta (String coExtraccion) throws GadirServiceException;
	
}
