package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.FuncionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FuncionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class FuncionBOImpl extends AbstractGenericBOImpl<FuncionDTO, String>
        implements FuncionBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */

	private static final long serialVersionUID = 2316250635387251595L;

	/**
	 * Atributo que almacena el DAO asociado a {@link FuncionDTO}.
	 */
	private DAOBase<FuncionDTO, String> funcionDao;

	
	
	public List<FuncionDTO> getFuncionesPorTipo(String tipo) throws GadirServiceException{
		
		final DetachedCriteria criteria = DetachedCriteria.forClass(FuncionDTO.class);
		criteria.add(Restrictions.eq("tipo",tipo));
		List<FuncionDTO> lista=this.funcionDao.findByCriteria(criteria);
		
		return lista;
		
		
	}

	
	public FuncionDTO getFuncionByNombre(String nombre) throws GadirServiceException{
		
		final DetachedCriteria criteria = DetachedCriteria.forClass(FuncionDTO.class);
		criteria.add(Restrictions.eq("nombre",nombre));
		List<FuncionDTO> lista=this.funcionDao.findByCriteria(criteria);
		if(lista!=null && lista.size()>0){
			return lista.get(0);
		}else{
			return null;
		}
		
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<FuncionDTO, String> getDao() {
		return this.getFuncionDao();
	}

	/**
	 * @return the funcionDao
	 */
	public DAOBase<FuncionDTO, String> getFuncionDao() {
		return funcionDao;
	}

	/**
	 * @param funcionDao
	 *            the funcionDao to set
	 */
	public void setFuncionDao(final DAOBase<FuncionDTO, String> funcionDao) {
		this.funcionDao = funcionDao;
	}
	
	public void auditorias(FuncionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
