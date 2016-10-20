package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.DocumentoCensoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.DocumentoCensoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCensoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class DocumentoCensoBOImpl extends
		AbstractGenericBOImpl<DocumentoCensoDTO, DocumentoCensoDTOId> implements
		DocumentoCensoBO {


	private static final long serialVersionUID = 1L;
	
	private DAOBase<DocumentoCensoDTO, DocumentoCensoDTOId> documentoCensoDao;

	public DAOBase<DocumentoCensoDTO, DocumentoCensoDTOId> getDao() {
		return documentoCensoDao;
	}

	public DAOBase<DocumentoCensoDTO, DocumentoCensoDTOId> getDocumentoCensoDao() {
		return documentoCensoDao;
	}

	public void setDocumentoCensoDao(
			DAOBase<DocumentoCensoDTO, DocumentoCensoDTOId> documentoCensoDao) {
		this.documentoCensoDao = documentoCensoDao;
	}
	
	public List<DocumentoCensoDTO> findDocumentosCensoByModeloAndVersion(final String coModelo, final String coVersion)
	        throws GadirServiceException {
		List<DocumentoCensoDTO> lista = null;
		if (Utilidades.isEmpty(coModelo) || Utilidades.isEmpty(coVersion)) {
			lista = Collections.EMPTY_LIST;
		} else {
			try {
				lista = this.getDao().findFiltered(new String[] { "id.coModelo", "id.coVersion" }, 
						new Object[] { coModelo, coVersion },
				        "id.coDocumento", DAOConstant.ASC_ORDER);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener los documentos por modelo y version");
			}
		}
		return lista;
	}
	
	public List<DocumentoCensoDTO> findDocumentosCensoByMunicipioConceptoModeloVersion(final String coMunicipio, final String coConcepto, final String coModelo, final String coVersion)
	        throws GadirServiceException {
		List<DocumentoCensoDTO> lista = null;
		if (Utilidades.isEmpty(coModelo) || Utilidades.isEmpty(coVersion) || 
				Utilidades.isEmpty(coMunicipio) || Utilidades.isEmpty(coConcepto)) {
			lista = Collections.EMPTY_LIST;
		} else {
			HashMap params = new HashMap();
			params.put("coMunicipio", coMunicipio);
			params.put("coConcepto", coConcepto);
			params.put("coModelo", coModelo);
			params.put("coVersion", coVersion);
			try {
				lista = this.getDao().findByNamedQuery("DocumentoCenso.findByMunicipioConceptoModeloVersion", params);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener los documentos por municipio, concepto, modelo y version");
			}
		}
		return lista;
	}
	
	public List<DocumentoCensoDTO> findDocumentosCensoByMunicipioConceptoModeloVersionSinConcepto(final String coMunicipio, final String coConcepto, final String coModelo, final String coVersion)
	        throws GadirServiceException {
		List<DocumentoCensoDTO> documentos = new ArrayList<DocumentoCensoDTO>();
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoCensoDTO.class);

		criteria.createAlias("documentoDTO", "documentoDTOAlias");
		
		criteria.add(Restrictions.eq("documentoDTOAlias.municipioDTO.id.coProvincia", "11"));
		criteria.add(Restrictions.eq("documentoDTOAlias.municipioDTO.id.coMunicipio", coMunicipio));
		criteria.add(Restrictions.eq("documentoDTOAlias.estado", "B"));
		if(Utilidades.isNotEmpty(coConcepto)){
			criteria.add(Restrictions.eq("documentoDTOAlias.conceptoDTO.coConcepto", coConcepto));
		}
		criteria.add(Restrictions.eq("id.coModelo", coModelo));
		criteria.add(Restrictions.eq("id.coVersion", coVersion));
		try {
			documentos= this.getDao().findByCriteria(criteria);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener los documentos por municipio, concepto, modelo y version");
		}
		
		return documentos;
	}
	
	public void auditorias(DocumentoCensoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}
	
	
	public int countByCriterioBusqueda(DetachedCriteria criterio) throws GadirServiceException {
		return getDocumentoCensoDao().countByCriteria(criterio);
	}	
}
