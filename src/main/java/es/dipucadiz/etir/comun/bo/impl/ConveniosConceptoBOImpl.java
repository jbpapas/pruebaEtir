package es.dipucadiz.etir.comun.bo.impl;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.ConveniosConceptoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ConveniosConceptoDTO;
import es.dipucadiz.etir.comun.dto.ConveniosConceptoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ConveniosConceptoBOImpl extends
        AbstractGenericBOImpl<ConveniosConceptoDTO, ConveniosConceptoDTOId> implements
        ConveniosConceptoBO {


	
	private DAOBase<ConveniosConceptoDTO, ConveniosConceptoDTOId> dao;

	public void setDao(DAOBase<ConveniosConceptoDTO, ConveniosConceptoDTOId> dao) {
		this.dao = dao;
	}
	
	public DAOBase<ConveniosConceptoDTO, ConveniosConceptoDTOId> getDao() {
		return dao;
	}
	
	public ConveniosConceptoDTO findByIdLazy(final ConveniosConceptoDTOId id) throws GadirServiceException {
		ConveniosConceptoDTO conveniosConceptoDTO = null;
		if (Utilidades.isNotNull(id)) {
			try {
				conveniosConceptoDTO = this.getDao().findById(id);
				if (Utilidades.isNotNull(conveniosConceptoDTO)) {
					// Inicializamos los objetos asociados.
					Hibernate.initialize(conveniosConceptoDTO.getOtrDTO());
				}
			} catch (final Exception e) {
					log.error(e.getCause(), e);
					throw new GadirServiceException(
					        "Ocurrio un error al obtener el convenio: ", e);
				}
			}
		return conveniosConceptoDTO;
	}
	
	public void auditorias(ConveniosConceptoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

}
