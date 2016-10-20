package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.CasillasRelacionadasDTO;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface CasillasRelacionadasBO extends
		GenericBO<CasillasRelacionadasDTO, Long> {
	
	
	List<CasillasRelacionadasDTO> findCasillaRelacionadaFiltered(CasillasRelacionadasDTO casillaRelacionada)
			throws GadirServiceException;
	
	public List<CasillasRelacionadasDTO> findCasillasRelacionadasFiltro(
			final String codMunicipio, final String codProvincia, final String codConcepto,
	        final String codModelo, final String codVersion, final String codMunicipioSalida,
	        final String codProvinciaSalida, final String codConceptoSalida,
	        final String codModeloSalida, final String codVersionSalida)
	        throws GadirServiceException;
	
	boolean isRelacionados(String coMunicipioDestino, String coProvinciaDestino, String coConceptoDestino, String coModeloDestino, String coVersionDestino, String coModeloDocOrigen, String coVersionDocOrigen) throws GadirServiceException;

	public List<ConceptoDTO> findConceptosOrigen(String coProvinciaDestino, String coMunicipioDestino, String coConceptoDestino, String coModeloDestino, String coVersionDestino, String coProvinciaOrigen, String coMunicipioOrigen) throws GadirServiceException;
	public List<ModeloDTO> findModelosOrigen(String coProvinciaDestino, String coMunicipioDestino, String coConceptoDestino, String coModeloDestino, String coVersionDestino, String coProvinciaOrigen, String coMunicipioOrigen, String coConceptoOrigen) throws GadirServiceException;
	public List<ModeloVersionDTO> findVersionesOrigen(String coProvinciaDestino, String coMunicipioDestino, String coConceptoDestino, String coModeloDestino, String coVersionDestino, String coProvinciaOrigen, String coMunicipioOrigen, String coConceptoOrigen, String coModeloOrigen) throws GadirServiceException;
	public List<CasillasRelacionadasDTO> findRelacionesDestino(String coProvinciaOrigen, String coMunicipioOrigen, String coConceptoOrigen, String coModeloOrigen, String coVersionOrigen, String coProvinciaDestino, String coMunicipioDestino) throws GadirServiceException;

}
