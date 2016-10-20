package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.MensajeErrorBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.MensajeErrorDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link MensajeErrorDTO}.
 * 
 * @version 1.0 03/12/2009
 * @author SDS[FJTORRES]
 */
public class MensajeErrorBOImpl extends
        AbstractGenericBOImpl<MensajeErrorDTO, Integer> implements
        MensajeErrorBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6565233459057641800L;

	/**
	 * Atributo que almacena el DAO asociado a {@link MensajeErrorDTO}.
	 */
	private DAOBase<MensajeErrorDTO, Integer> mensajeDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<MensajeErrorDTO, Integer> getDao() {
		return this.getMensajeDao();
	}

	/**
	 * Método que devuelve el atributo mensajeDao.
	 * 
	 * @return mensajeDao.
	 */
	public DAOBase<MensajeErrorDTO, Integer> getMensajeDao() {
		return mensajeDao;
	}

	public List<MensajeErrorDTO> buscarTextoError(String cadena) throws GadirServiceException{
		List<MensajeErrorDTO> listado;
		final DetachedCriteria criteria = DetachedCriteria.forClass(MensajeErrorDTO.class);
		criteria.add(Restrictions.like("texto","%"+cadena+"%").ignoreCase());
		listado= this.mensajeDao.findByCriteria(criteria);
	    return listado; 
		
	}
	
	
	
	/**
	 * Método que establece el atributo mensajeDao.
	 * 
	 * @param mensajeDao
	 *            El mensajeDao.
	 */
	public void setMensajeDao(final DAOBase<MensajeErrorDTO, Integer> mensajeDao) {
		this.mensajeDao = mensajeDao;
	}
	
	public void auditorias(MensajeErrorDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
