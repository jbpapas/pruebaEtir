package es.dipucadiz.etir.comun.bo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.dto.CasillaMunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb05.vo.CasillaMunicipioVO;


public interface CasillaMunicipioBO extends GenericBO<CasillaMunicipioDTO, Long> {

	public CasillaMunicipioDTO findByIdInitialized(Long id) throws GadirServiceException;
	
	                  
	List<CasillaMunicipioDTO> findCasillasMunicipioByMunicipioConceptoModeloVersion(
	        final String codMunicipio, final String codConcepto,
	        final String codModelo, final String codVersion)
	        throws GadirServiceException;
	
	CasillaMunicipioDTO findCasillaMunicipioByMunicipioProvinciaConceptoModeloVersionNuCasilla(
	        final String codMunicipio, String codProcincia,final String codConcepto,
	        final String codModelo, final String codVersion, final String nuCasilla)
	        throws GadirServiceException;

	boolean existCasillaMunicipioByUniqueConstraint(
	        final CasillaMunicipioDTO casMun) throws GadirServiceException;

	List<CasillaMunicipioDTO> findCasillasByMunicipioModeloVersionGenerica(final String coMunicipio,
	        final String coProvincia, final String coModelo,
	        final String coVersion, String coConcepto) throws GadirServiceException;

	List<CasillaMunicipioDTO> findCasillasMunicipioByMunicipioProvinciaConceptoModeloVersion(
	        final String codMunicipio,final String codProcincia, final String codConcepto,
	        final String codModelo, final String codVersion)
	        throws GadirServiceException;
	
	CasillaMunicipioDTO findByIdFetchCasillaModelo(Long id) throws GadirServiceException;
	
	CasillaMunicipioDTO obtenerCasillaByIdFetchConceptoModelo(Long id) throws GadirServiceException;

	public List<CasillaMunicipioVO> findByCriteriosSeleccionPaginado(DetachedCriteria criteria, int porPagina, int page, String operacion, boolean mantenimientoExperto) throws GadirServiceException;
	public boolean existeOperacion(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, String operacion) throws GadirServiceException;
}
