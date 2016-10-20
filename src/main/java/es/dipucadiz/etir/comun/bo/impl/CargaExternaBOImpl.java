package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.CargaExternaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CargaExternaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link CargaExternaDTO}.
 * 
 * @version 1.0 09/11/2009
 * @author SDS[AGONZALEZ]
 */
public class CargaExternaBOImpl extends AbstractGenericBOImpl<CargaExternaDTO, Long> 
		implements CargaExternaBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6600031313681872877L;

	/**
	 * Atributo que almacena el DAO asociado a {@link CargaExternaDTO}.
	 */
	private DAOBase<CargaExternaDTO, Long> cargaExternaDao;	

	@Override
	public DAOBase<CargaExternaDTO, Long> getDao() {
		return this.getCargaExternaDao();
	}

	// GETTERS AND SETTERS

	/**
	 * Método que devuelve el atributo cargaExternaDAO.
	 * 
	 * @return cargaControlRecepcionDao.
	 */
	public DAOBase<CargaExternaDTO, Long> getCargaExternaDao() {
		return cargaExternaDao;
	}

	/**
	 * Método que establece el atributo cargaExternaDAO.
	 * 
	 * @param cargaControlRecepcionDao
	 *            El cargaControlRecepcionDao.
	 */
	public void setCargaExternaDao(DAOBase<CargaExternaDTO, Long> cargaExternaDao) {
		this.cargaExternaDao = cargaExternaDao;
	}
	
	public void auditorias(CargaExternaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
