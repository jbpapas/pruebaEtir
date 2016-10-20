package es.dipucadiz.etir.comun.bo.impl;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.ConveniosModeloBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ConveniosModeloDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class ConveniosModeloBOImpl extends AbstractGenericBOImpl<ConveniosModeloDTO, Long> implements ConveniosModeloBO {

	private DAOBase<ConveniosModeloDTO, Long> dao;

	public void setDao(DAOBase<ConveniosModeloDTO, Long> dao) {
		this.dao = dao;
	}

	public DAOBase<ConveniosModeloDTO, Long> getDao() {
		return dao;
	}
	
	public ConveniosModeloDTO findByIdLazy(final Long coConveniosModelo) throws GadirServiceException {
		ConveniosModeloDTO conveniosModeloDTO = null;
		if (Utilidades.isNotNull(coConveniosModelo)) {
			try {
				conveniosModeloDTO = this.getDao().findById(coConveniosModelo);
				if (Utilidades.isNotNull(conveniosModeloDTO)) {
					// Inicializamos los objetos asociados.
					Hibernate.initialize(conveniosModeloDTO.getConveniosTipoDTOs());
				}
			} catch (final Exception e) {
					log.error(e.getCause(), e);
					throw new GadirServiceException(
					        "Ocurrio un error al obtener el convenio: ", e);
				}
			}
		return conveniosModeloDTO;
	}

	public void auditorias(ConveniosModeloDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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