package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.AcmUsuarioPreferenciaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.AcmUsuarioPreferenciaDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioPreferenciaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class AcmUsuarioPreferenciaBOImpl extends AbstractGenericBOImpl<AcmUsuarioPreferenciaDTO, AcmUsuarioPreferenciaDTOId>  implements AcmUsuarioPreferenciaBO {

	private static final Log LOG = LogFactory.getLog(AcmUsuarioPreferenciaBOImpl.class);

	private DAOBase<AcmUsuarioPreferenciaDTO, AcmUsuarioPreferenciaDTOId> dao;


	public DAOBase<AcmUsuarioPreferenciaDTO, AcmUsuarioPreferenciaDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<AcmUsuarioPreferenciaDTO, AcmUsuarioPreferenciaDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(AcmUsuarioPreferenciaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
