/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

//import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.MunicipioDatosBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.MunicipioDatosDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDatosDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class MunicipioDatosBOImpl extends AbstractGenericBOImpl<MunicipioDatosDTO, MunicipioDatosDTOId> implements MunicipioDatosBO {

	private static final Log LOG = LogFactory.getLog(MunicipioDatosBOImpl.class);
	
	private DAOBase<MunicipioDatosDTO, MunicipioDatosDTOId> dao;

	public DAOBase<MunicipioDatosDTO, MunicipioDatosDTOId> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<MunicipioDatosDTO, MunicipioDatosDTOId> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

//	public List<MunicipioDatosDTO> findByProvincia(String provincia) {
//		return dao.findFiltered("provinciaDTO", new ProvinciaDTO(provincia, null));
//	}
	
	public void auditorias(MunicipioDatosDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
