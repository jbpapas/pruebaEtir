package es.dipucadiz.etir.comun.bo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.ResumenDocumentoVO;
import es.dipucadiz.etir.sb05.vo.DocumentosCandidatosVO;
import es.dipucadiz.etir.sb05.vo.DocumentosEntradaVO;
import es.dipucadiz.etir.sb05.vo.DocumentosEstadoVO;


public interface DocumentoBO extends GenericBO<DocumentoDTO, DocumentoDTOId> {


	
	List<DocumentoDTO> findDocumentosByMunicipioConcepto(String coMunicipio,
	        String coConcepto) throws GadirServiceException;

	List<DocumentoDTO> findDocumentosByModeloVersionNumDocumento(
	        String coModelo, String coVersion, String numDocumento)
	        throws GadirServiceException;

	
	List<ResumenDocumentoVO> obtenerResumen(String coMunicipio,
	        String coProvincia) throws GadirServiceException;

	List<ResumenDocumentoVO> obtenerResumenEstadoDocumentos(String coMunicipio, String coProvincia, String coConcepto,
			String coModelo, String coVersion, String ejercicio, String coPeriodo) throws GadirServiceException;
	
	List<DocumentoDTO> documentosEspecificosUnidadUrbana(Long coUnidadUrbana) throws GadirServiceException;

	List<DocumentoDTO> obtenerListadoListasCobratorias(String coProvincia, String coMunicipio, 
		Short ejercicio, String coModelo, String situacion) 
		throws GadirServiceException;

	List<DocumentoDTO> findByCriterioBusqueda(DetachedCriteria criterio, int pagina, int max) throws GadirServiceException;
	List<DocumentoDTO> findByCriterioBusqueda(DetachedCriteria criterio) throws GadirServiceException;
	public int countByCriterioBusqueda(DetachedCriteria criterio) throws GadirServiceException;
	
	List<DocumentoDTO> buscaDocumentoByModeloVersion(String coModelo, String coVersion) throws GadirServiceException;

	List<DocumentoDTO> buscaDocumentoByRefCatastral(String refCatastral) throws GadirServiceException;

	DocumentoDTO findDocumentoByRowid(String rowid) throws GadirServiceException;

	DocumentoDTO findByIdDocumento(DocumentoDTOId idDocumento) throws GadirServiceException;
	
	List<DocumentoDTO> findDocumentosByMunicipioConceptoModeloVersion(String coMunicipio, 
			String coConcepto, String coModelo, String coVersion) throws GadirServiceException;	
	
	Integer cuantosDocumentosByMunicipioConceptoMVEP(MunicipioDTO municipio, 
			ConceptoDTO concepto, 
			ModeloVersionDTO modeloVersion, 
			Short ejercicio, 
			String periodo) throws GadirServiceException;
	 
	List<DocumentosEstadoVO> findDocumentosByCargaControlRecepcionandEstado(
			Long coCargaControlRecepcion) throws GadirServiceException;

	List<DocumentosEntradaVO> findByCriteriosSeleccionPaginado(DetachedCriteria criteria, int porPagina, int page) throws GadirServiceException;
	
	List<DocumentosCandidatosVO> findByCriteriosSeleccionPaginadoCandidatos(DetachedCriteria criteria, int porPagina, int page) throws GadirServiceException;

	List<DocumentoDTO>findCensoPorTramoCategoria(String coMunicipio, String co_concepto, String co_modelo, String co_version, String cat, boolean ordenar, int posDesde, int posHasta) throws GadirServiceException;
	
}
