package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ExtraccionInformeEstructDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public interface ExtraccionInformeEstructBO extends
		GenericBO<ExtraccionInformeEstructDTO, Long> {

	
	public List<ExtraccionInformeEstructDTO> findByInforme(String coExtraccion, short coExtraccionInforme) throws GadirServiceException;
	public List<ExtraccionInformeEstructDTO> findByInformeLineaPosicion(String coExtraccion, short coExtraccionInforme, short linea, short posicion) throws GadirServiceException;
	public List<ExtraccionInformeEstructDTO> findByInformeAndLinea(String coExtraccion, short coExtraccionInforme, short linea) throws GadirServiceException;
}
