package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import es.dipucadiz.etir.comun.bo.ParametroBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ParametroDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ParametroBOImpl extends AbstractGenericBOImpl<ParametroDTO, String>  implements ParametroBO {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7906613593047467480L;
	
	/**
	 * {@inheritDoc}
	 */
	public Boolean findParametro(String codParametro)
			throws GadirServiceException {
			String queryString;
			queryString = 	"select p " +
							"from ParametroDTO p " +
							"where p.coParametro = '"+ codParametro +"' ";
			try {
			List<ParametroDTO> result = (List<ParametroDTO>) this.getDao()
					.findByQuery(queryString);
			if(result.size() > 0){
				return true;
			}
			else{
				return false;
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el parametro.", e);
		}
	}
	
	public List<ParametroDTO> listarTodos()throws GadirServiceException {
	
	try {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParametroDTO.class);
		criteria.addOrder(Order.asc("coParametro"));
		return findByCriteria(criteria);

} catch (final Exception e) {
	log.error(e.getCause(), e);
	throw new GadirServiceException(
			"Error al obtener el listado.", e);
}
}

	
	private DAOBase<ParametroDTO, String> dao;

	public DAOBase<ParametroDTO, String> getDao() {
		return dao;
	}
	
	public void setDao(final DAOBase<ParametroDTO, String> dao) {
		this.dao = dao;
	}
	
	public void auditorias(ParametroDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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