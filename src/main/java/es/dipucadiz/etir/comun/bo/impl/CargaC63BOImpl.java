package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.CargaC63BO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CargaC63DTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public class CargaC63BOImpl extends AbstractGenericBOImpl< CargaC63DTO, Long>
        implements CargaC63BO {
	
	
	private static final long serialVersionUID = 6879318165367306079L;

	
	private DAOBase< CargaC63DTO, Long> dao;
	

	@Override
	public DAOBase< CargaC63DTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase< CargaC63DTO, Long> dao) {
		this.dao = dao;
	}

	
	public void auditorias( CargaC63DTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}


}
