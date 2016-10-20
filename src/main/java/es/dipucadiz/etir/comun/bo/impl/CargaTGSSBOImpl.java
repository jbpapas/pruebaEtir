package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.CargaTGSSBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CargaTGSSDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public class CargaTGSSBOImpl extends AbstractGenericBOImpl<CargaTGSSDTO, Long>
        implements CargaTGSSBO {
	
	
	private static final long serialVersionUID = 6879318165367306079L;

	
	private DAOBase<CargaTGSSDTO, Long> dao;
	

	@Override
	public DAOBase<CargaTGSSDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<CargaTGSSDTO, Long> dao) {
		this.dao = dao;
	}

	
	public void auditorias(CargaTGSSDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
