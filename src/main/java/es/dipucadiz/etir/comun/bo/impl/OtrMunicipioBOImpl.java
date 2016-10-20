/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.OtrMunicipioBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.OtrMunicipioDTO;
import es.dipucadiz.etir.comun.dto.OtrMunicipioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class OtrMunicipioBOImpl extends AbstractGenericBOImpl<OtrMunicipioDTO, OtrMunicipioDTOId> implements OtrMunicipioBO {

	private static final Log LOG = LogFactory.getLog(OtrMunicipioBOImpl.class);
	
	private DAOBase<OtrMunicipioDTO, OtrMunicipioDTOId> dao;
	private DAOBase<MunicipioDTO, MunicipioDTOId> municipioDao;

	public DAOBase<OtrMunicipioDTO, OtrMunicipioDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<OtrMunicipioDTO, OtrMunicipioDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void setMunicipioDao(DAOBase<MunicipioDTO, MunicipioDTOId> municipioDao) {
		this.municipioDao = municipioDao;
	}

	public DAOBase<MunicipioDTO, MunicipioDTOId> getMunicipioDao() {
		return municipioDao;
	}


	public void auditorias(OtrMunicipioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
