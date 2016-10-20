package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public interface ModeloBO extends GenericBO<ModeloDTO, String> {

	/**
	 * Método que se encarga de obtener los modelos de GA_DOCUMENTOS.
	 * 
	 * @param estado
	 *            Estado del documento.
	 * @return Listado de objetos poblados.
	 * @throws GadirServiceException
	 *             Si ocurre algún error.
	 */
	List<ModeloDTO> findAllModelos(final String estado)
	        throws GadirServiceException;

	/**
	 * Método que se encarga de obtener la lista de modelos asociados a un
	 * concepto dado, si el concepto que llega es null o vacio se devuelve una
	 * lista vacia.
	 * 
	 * @param coConcepto
	 *            Código del concepto.
	 * @return Lista de modelos.
	 * @throws GadirServiceException
	 *             Si ocurre cualquier error.
	 */
	List<ModeloDTO> findModelosByConcepto(final String coConcepto)
	        throws GadirServiceException;
	        
	List<ModeloDTO> findModelosByConceptoTLF_LR(final String coConcepto)
    throws GadirServiceException;
	
	List<Integer> tieneRelacionesNoEliminables(final String coModelo) throws GadirServiceException;
	
	Boolean eliminarModelo(final ModeloDTO modelo) throws GadirServiceException;
	
	List<ModeloDTO> findModelosEnDocumentos() throws GadirServiceException;
	
	List<ModeloDTO> findModelosByConceptos(final List<ConceptoDTO> conceptos)throws GadirServiceException;
	
	ModeloDTO findInitializedById(String id) throws GadirServiceException;

	List<Integer> comprobarEliminarModelo(ModeloDTO modelo) throws GadirServiceException;
	ModeloDTO findModeloReciboByConcepto(String coConcepto) throws GadirServiceException;

	List<ModeloDTO> findModelosTiposRecibo() throws GadirServiceException;
	List<ModeloDTO> findModelosTiposLiquidacion() throws GadirServiceException;
	
}
