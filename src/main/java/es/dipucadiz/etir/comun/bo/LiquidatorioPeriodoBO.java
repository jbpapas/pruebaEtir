package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.LiquidatorioPeriodoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface LiquidatorioPeriodoBO extends GenericBO<LiquidatorioPeriodoDTO, Long> {


	List<String> findByMunicipioConceptoModeloEjercicio(final String coProvincia, final String coMunicipio, 
			final String coConcepto, final String coModelo, final String ejercicio) throws GadirServiceException;
	
	public List<LiquidatorioPeriodoDTO> findByMunicipioConceptoModeloEjercicioPAG(String coProvincia, String coMunicipio, String coConcepto, String coModelo, Short ejercicio) throws GadirServiceException;
	
	
	List<LiquidatorioPeriodoDTO> findByMunicipioConceptoModeloEjercicioPeriodo(final String coProvincia, final String coMunicipio, 
			final String coConcepto, final String coModelo, final String ejercicio, final String periodo) throws GadirServiceException;
	
	List<LiquidatorioPeriodoDTO> findByMunicipioModeloEjercicioSinConcepto(final String coProvincia, final String coMunicipio, 
			final String coConcepto, final String coModelo, final String ejercicio) throws GadirServiceException;
	
}
