package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.DocumentoLiquidacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoLiquidacionDTO;
import es.dipucadiz.etir.comun.dto.DocumentoLiquidacionDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.MunicipioConstants;
import es.dipucadiz.etir.comun.utilidades.ProvinciaConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DocumentoLiquidacionBOImpl extends
		AbstractGenericBOImpl<DocumentoLiquidacionDTO, DocumentoLiquidacionDTOId> implements
		DocumentoLiquidacionBO {

	
	private static final long serialVersionUID = 6600031313681872877L;
	
	
	private DAOBase<DocumentoLiquidacionDTO, DocumentoLiquidacionDTOId> documentoLiquidacionDao;

	@Override
	public DAOBase<DocumentoLiquidacionDTO, DocumentoLiquidacionDTOId> getDao() {
		return documentoLiquidacionDao;
	}

	public DAOBase<DocumentoLiquidacionDTO, DocumentoLiquidacionDTOId> getDocumentoCasillaDao() {
		return documentoLiquidacionDao;
	}

	public void setDocumentoLiquidacionDao(
			DAOBase<DocumentoLiquidacionDTO, DocumentoLiquidacionDTOId> documentoLiquidacionDao) {
		this.documentoLiquidacionDao = documentoLiquidacionDao;
	}
	
	public void auditorias(DocumentoLiquidacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Boolean findDocumentoLiquidacion(DocumentoDTOId id)
			throws GadirServiceException {
			String queryString;
			queryString = 	"select n " +
							"from DocumentoLiquidacionDTO n " +
							"where n.documentoDTO.id.coDocumento = '"+ id.getCoDocumento() + "' "+
							"and n.documentoDTO.id.coVersion = '"+ id.getCoVersion() + "' "+
							"and n.documentoDTO.id.coModelo = '"+ id.getCoModelo()+"' ";
			try {
			List<DocumentoLiquidacionDTO> result = (List<DocumentoLiquidacionDTO>) this.getDao()
					.findByQuery(queryString);
			if(result.size() > 0){
				return true;
			}
			else{
				return false;
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el documento catastral.", e);
		}
	}
	
	public Boolean findDocumentoLiquidacionByMunicipioModeloVersionConceptoPeriodoEjercicio(String municipio, String provincia, String modelo, String version, String concepto, String periodo, String ejercicio) throws GadirServiceException{
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoLiquidacionDTO.class);
		criteria.createAlias("documentoDTO", "documentoDTOAlias");
		
		if (!MunicipioConstants.CO_MUNICIPIO_GENERICO.equals(municipio)) {
			criteria.add(Restrictions.eq("documentoDTOAlias.municipioDTO.id.coMunicipio", municipio));
		}
		if (!ProvinciaConstants.CO_PROVINCIA_GENERICO.equals(provincia)) {
			criteria.add(Restrictions.eq("documentoDTOAlias.municipioDTO.id.coProvincia", provincia));
		}
		if(Utilidades.isNotEmpty(concepto)){
			criteria.add(Restrictions.eq("documentoDTOAlias.conceptoDTO.coConcepto", concepto));
		}
		criteria.add(Restrictions.eq("documentoDTOAlias.modeloVersionDTO.id.coModelo", modelo));
		criteria.add(Restrictions.eq("documentoDTOAlias.modeloVersionDTO.id.coVersion", version));
		criteria.add(Restrictions.le("documentoDTOAlias.ejercicio", new Short(ejercicio)));
		criteria.add(Restrictions.ge("documentoDTOAlias.ejercicio", new Short(ejercicio)));
		List<DocumentoLiquidacionDTO> documentos= this.getDao().findByCriteria(criteria);
		
		if(documentos.size() > 0 ){
			return true;
		}else{
			return false;
		}
	}
	
	
}
