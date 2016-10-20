package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.InformeBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.InformeDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



public class InformeBOImpl extends AbstractGenericBOImpl<InformeDTO, Long>  implements InformeBO {

	private DAOBase<InformeDTO, Long> dao;

	public DAOBase<InformeDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<InformeDTO, Long> dao) {
		this.dao = dao;
	}
	
	public void auditorias(InformeDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		if (Utilidades.isNotNull(DatosSesion.getLogin())) {
			transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		}
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}

}
