package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.CargoSubcargoSeguimientoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CargoSubcargoSeguimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CargoSubcargoSeguimientoBOImpl extends AbstractGenericBOImpl<CargoSubcargoSeguimientoDTO, Long> implements CargoSubcargoSeguimientoBO {

	private static final long serialVersionUID = 8458398059250010847L;

	private static final Log LOG = LogFactory.getLog(CargoSubcargoSeguimientoBOImpl.class);
	
	private DAOBase<CargoSubcargoSeguimientoDTO, Long> dao;

	public DAOBase<CargoSubcargoSeguimientoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<CargoSubcargoSeguimientoDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void auditorias(CargoSubcargoSeguimientoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}	
	
}
	
	