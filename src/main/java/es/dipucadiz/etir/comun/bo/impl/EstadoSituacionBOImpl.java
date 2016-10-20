package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import es.dipucadiz.etir.comun.bo.EstadoSituacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.EstadoSituacionDTO;
import es.dipucadiz.etir.comun.dto.ModoCobroDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link EstadoSituacionDTO}.
 * 
 */
public class EstadoSituacionBOImpl extends
        AbstractGenericBOImpl<EstadoSituacionDTO, String> implements
        EstadoSituacionBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6565233459057641800L;

	/**
	 * Atributo que almacena el DAO asociado a {@link EstadoSituacionDTO}.
	 */
	private DAOBase<EstadoSituacionDTO, String> estadoSituacionDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<EstadoSituacionDTO, String> getDao() {
		return this.getEstadoSituacionDao();
	}

	/**
	 * Método que devuelve el atributo estadoSituacionDao.
	 * 
	 * @return estadoSituacionDao.
	 */
	public DAOBase<EstadoSituacionDTO, String> getEstadoSituacionDao() {
		return estadoSituacionDao;
	}
	
	/**
	 * Método que establece el atributo estadoSituacionDao.
	 * 
	 * @param estadoSituacionDao
	 *            El estadoSituacionDao.
	 */
	public void setEstadoSituacionDao(final DAOBase<EstadoSituacionDTO, String> estadoSituacionDao) {
		this.estadoSituacionDao = estadoSituacionDao;
	}
	
	public void auditorias(EstadoSituacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

	public List<EstadoSituacionDTO> findAll() throws GadirServiceException {
		try {
			return getDao().findAll("coEstadoSituacion", DAOConstant.ASC_ORDER);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado.", e);
		}
	}

}
