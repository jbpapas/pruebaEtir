package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.JuntaEnvioLiquidacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.JuntaEnvioLiquidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class JuntaEnvioLiquidacionBOImpl extends AbstractGenericBOImpl<JuntaEnvioLiquidacionDTO, Long>
        implements JuntaEnvioLiquidacionBO {
	
	
	private static final long serialVersionUID = 6879318165367306079L;

	
	private DAOBase<JuntaEnvioLiquidacionDTO, Long> dao;
	

	@Override
	public DAOBase<JuntaEnvioLiquidacionDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<JuntaEnvioLiquidacionDTO, Long> dao) {
		this.dao = dao;
	}

	
	public void auditorias(JuntaEnvioLiquidacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
