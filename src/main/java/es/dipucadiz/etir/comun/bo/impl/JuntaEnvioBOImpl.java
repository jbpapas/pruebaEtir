package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.JuntaEnvioBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.JuntaEnvioDTO;
import es.dipucadiz.etir.comun.dto.JuntaEnvioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class JuntaEnvioBOImpl extends AbstractGenericBOImpl<JuntaEnvioDTO, JuntaEnvioDTOId>
        implements JuntaEnvioBO {
	
	
	private static final long serialVersionUID = 6879318165367306079L;

	
	private DAOBase<JuntaEnvioDTO, JuntaEnvioDTOId> dao;
	

	@Override
	public DAOBase<JuntaEnvioDTO, JuntaEnvioDTOId> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<JuntaEnvioDTO, JuntaEnvioDTOId> dao) {
		this.dao = dao;
	}

	
	public void auditorias(JuntaEnvioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
