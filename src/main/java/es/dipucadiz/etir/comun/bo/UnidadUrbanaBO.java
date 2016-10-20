package es.dipucadiz.etir.comun.bo;

import java.math.BigDecimal;
import java.util.List;

import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.comun.vo.UnidadUrbanaVO;
import es.dipucadiz.etir.sb07.comun.vo.ViasVO;

public interface UnidadUrbanaBO extends GenericBO<UnidadUrbanaDTO, Long> {

	
	public boolean hayEnCalle(CalleDTO calleDTO) throws GadirServiceException;
	
	/**
	 * Incluye dependencias (initialize)
	 * @param Código de unidad urbana.
	 * @return DTO con dependencias inicializados.
	 * @throws GadirServiceException
	 */
	public UnidadUrbanaDTO findUnidadUrbanaByCodigo(final Long coUnidadUrbana) throws GadirServiceException;
	
	public UnidadUrbanaDTO saveOrFindByDatos (Long coCalle, Integer numero, String letra, String escalera, String planta, String puerta, String bloque, BigDecimal km, Integer cp) throws GadirServiceException;
	public List<UnidadUrbanaVO> buscarUnidadesUrbanasVO(final UnidadUrbanaVO unidad,boolean asociados,Integer pagina, Integer elementos) throws GadirServiceException;
	
	public List<UnidadUrbanaDTO> findUnidadUrbanaCallejero(final UnidadUrbanaDTO filtro) throws GadirServiceException;
	
	public boolean tieneRelaciones(final Long coUnidadUrbana) throws GadirServiceException;
	
	public void eliminarUUrbana(final Long coUnidadUrbana) throws GadirServiceException;
	
	
	public void modificarUUrbana(final Long coUnidadUrbanaOriginal,UnidadUrbanaDTO unidadUrbanaModificada) throws GadirServiceException;
	
	public void modificarUUrbanaExiste(final Long coUnidadUrbanaOriginal,UnidadUrbanaDTO unidadUrbanaModificadaExistente) throws GadirServiceException;
	
	public void modificarUUrbanaExisteConModifDocumentos(final Long coUnidadUrbanaOriginal,UnidadUrbanaDTO unidadUrbanaModificadaExistente) throws GadirServiceException;
	/**
	 * M�todo encargado de buscar las v�as de un municipio, sigla y nombre
	 * 
	 * @param coMunicipio
	 * @param codSigla
	 * @param nombreVia
	 * @param pagina
	 * @param vias
	 * @param limite
	 * @return
	 * @throws GadirServiceException
	 */
	public Integer findByParamsPorMunicipio(String coProvincia, String coMunicipio,String codSigla, String nombreVia, Integer pagina,
	        List<ViasVO> vias, Integer limite)
	        throws GadirServiceException;
	
	public Integer contadorUnidadesUrbanasVO(final UnidadUrbanaVO unidad, boolean asociados) throws GadirServiceException;

	public boolean tieneAsociacion(final Long coUnidadUrbana) throws GadirServiceException;
	
	public UnidadUrbanaDTO buscaUUrbanaIdentica(UnidadUrbanaDTO unidadUrbana) throws GadirServiceException;
	
	public void modificarUUrbanaExisteConModifDocumentosYRefCatastral(final Long coUnidadUrbanaOriginal, final Long coUnidadUrbanaDestino) throws GadirServiceException;
	
	public void asociarUnidadesSinReferencia(Long coOrigen, Long coDestino) throws GadirServiceException;
	
	public void asociarUnidadesUnaReferencia(Long coOrigen, Long coDestino) throws GadirServiceException;
	
}