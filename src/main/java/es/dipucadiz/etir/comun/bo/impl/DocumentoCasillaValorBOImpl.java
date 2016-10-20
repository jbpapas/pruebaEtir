package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.CasillaModeloBO;
import es.dipucadiz.etir.comun.bo.DocumentoCasillaValorBO;
import es.dipucadiz.etir.comun.bo.HDocumentoCasillaValorBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioDTO;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioOperacionDTO;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioOperacionDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class DocumentoCasillaValorBOImpl extends
		AbstractGenericBOImpl<DocumentoCasillaValorDTO, DocumentoCasillaValorDTOId> implements
		DocumentoCasillaValorBO {

	
	private static final long serialVersionUID = 6600031313681872877L;
	
	private static final String DOCUMENTO_CO_DOCUMENTO = "id.coDocumento";

	private static final String DOCUMENTO_CO_VERSION = "id.coVersion";

	private static final String DOCUMENTO_CO_MODELO = "id.coModelo";
	
	
	private static final String CODIGO_PROCESO = "G5335";
	
	
	private HDocumentoCasillaValorBO histDocumentoCasillaValorBO;
	
	
	private DAOBase<DocumentoCasillaValorDTO, DocumentoCasillaValorDTOId> documentoCasillaDao;
	
	private DAOBase<CasillaMunicipioDTO, Long> casillaMunicipioDao;
	
	private DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> casillaMunicipioOperacionDao;

	private CasillaModeloBO casillaModeloBO;
	
	
	private DAOBase<DocumentoDTO, DocumentoDTOId> documentoDao;
	
	@Override
	public DAOBase<DocumentoCasillaValorDTO, DocumentoCasillaValorDTOId> getDao() {
		return documentoCasillaDao;
	}

	public List<DocumentoCasillaValorDTO> findDocumentosCasillaByDocumento(
			String coDocumento, String coModelo, String coVersion)
	{
		String[] propNames = {DOCUMENTO_CO_DOCUMENTO, DOCUMENTO_CO_MODELO,
				DOCUMENTO_CO_VERSION };
		String[] filters = { coDocumento,
				coModelo,
				coVersion };

		List<DocumentoCasillaValorDTO> casillas = this.getDao().findFiltered(propNames, filters,
				DOCUMENTO_CO_DOCUMENTO, DAOConstant.ASC_ORDER);
		
		if(null == casillas || !(casillas.size() > 0))
			casillas = new ArrayList<DocumentoCasillaValorDTO>();
		
		return casillas;
	}
	
	
	
	public List<DocumentoCasillaValorDTO> findDocumentosCasillaByDocumentoAndNuCasilla(
			String coDocumento, String coModelo, String coVersion, String nuCasilla)
			throws GadirServiceException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		try {
			parametros.put("coDocumento", coDocumento);
			parametros.put("coModelo", coModelo);
			parametros.put("coVersion", coVersion);
			parametros.put("nuCasilla", Short.valueOf(nuCasilla));
			return this.getDao().findByNamedQuery("DocumentoCasillaValor.findByDocumentoAndNuCasilla", parametros);
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener documento casilla valor", e);
		}	
		
	}

	public void actualizarCasilla(DocumentoCasillaValorDTO casilla) throws GadirServiceException {
		try {
			this.save(casilla);
			this.getHistDocumentoCasillaValorBO().actualizarHistorico(casilla, CODIGO_PROCESO);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al actualizar la casilla.",
			        e);
		}
		
	}
	

	public DAOBase<DocumentoCasillaValorDTO, DocumentoCasillaValorDTOId> getDocumentoCasillaDao() {
		return documentoCasillaDao;
	}

	public void setDocumentoCasillaDao(
			DAOBase<DocumentoCasillaValorDTO, DocumentoCasillaValorDTOId> documentoCasillaDao) {
		this.documentoCasillaDao = documentoCasillaDao;
	}
	
	/**
     * Método que devuelve el atributo histDocumentoCasillaValorBO.
     * 
     * @return histDocumentoCasillaValorBO.
     */
    public HDocumentoCasillaValorBO getHistDocumentoCasillaValorBO() {
    	return histDocumentoCasillaValorBO;
    }

	/**
     * Método que establece el atributo histDocumentoCasillaValorBO.
     * 
     * @param histDocumentoCasillaValorBO
     *            El histDocumentoCasillaValorBO.
     */
    public void setHistDocumentoCasillaValorBO(
            HDocumentoCasillaValorBO histDocumentoCasillaValorBO) {
    	this.histDocumentoCasillaValorBO = histDocumentoCasillaValorBO;
    }
    
    public void auditorias(DocumentoCasillaValorDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		DocumentoDTO doc = new DocumentoDTO();
		doc = getDocumentoDao().findById(new DocumentoDTOId (transientObject.getId().getCoModelo(),
				transientObject.getId().getCoVersion(),
				transientObject.getId().getCoDocumento()));
		doc.setFhActualizacion(transientObject.getFhActualizacion());
		doc.setCoUsuarioActualizacion(transientObject.getCoUsuarioActualizacion());
		getDocumentoDao().save(doc);
	}

    
	public DAOBase<CasillaMunicipioDTO, Long> getCasillaMunicipioDao() {
		return casillaMunicipioDao;
	}

	/**
	 * @param casillaMunicipioDao the casillaMunicipioDao to set
	 */
	public void setCasillaMunicipioDao(
			DAOBase<CasillaMunicipioDTO, Long> casillaMunicipioDao) {
		this.casillaMunicipioDao = casillaMunicipioDao;
	}

	/**
	 * @return the casillaMunicipioOperacionDao
	 */
	public DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> getCasillaMunicipioOperacionDao() {
		return casillaMunicipioOperacionDao;
	}

	/**
	 * @param casillaMunicipioOperacionDao the casillaMunicipioOperacionDao to set
	 */
	public void setCasillaMunicipioOperacionDao(
			DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> casillaMunicipioOperacionDao) {
		this.casillaMunicipioOperacionDao = casillaMunicipioOperacionDao;
	}

	public DAOBase<DocumentoDTO, DocumentoDTOId> getDocumentoDao() {
		return documentoDao;
	}

	public void setDocumentoDao(DAOBase<DocumentoDTO, DocumentoDTOId> documentoDao) {
		this.documentoDao = documentoDao;
	}

	public CasillaModeloBO getCasillaModeloBO() {
		return casillaModeloBO;
	}

	public void setCasillaModeloBO(CasillaModeloBO casillaModeloBO) {
		this.casillaModeloBO = casillaModeloBO;
	}

	
}
