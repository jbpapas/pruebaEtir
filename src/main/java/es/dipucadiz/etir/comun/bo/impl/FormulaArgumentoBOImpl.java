package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.FormulaArgumentoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FormulaArgumentoDTO;
import es.dipucadiz.etir.comun.dto.FormulaArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class FormulaArgumentoBOImpl extends
		AbstractGenericBOImpl<FormulaArgumentoDTO, FormulaArgumentoDTOId>
		implements FormulaArgumentoBO {
	
	/**
	 * Atributo que almacena el dao asociado a {@link FuncionArgumentoDTO}.
	 */
	private DAOBase<FormulaArgumentoDTO, FormulaArgumentoDTOId> formulaArgumentoDao;
	
	
	public List<FormulaArgumentoDTO> obtenerArgumentosFromPaso(String codFormula, Byte codPaso) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(FormulaArgumentoDTO.class);
		
		criteria.add(Restrictions.eq("id.coFormula",codFormula));
		criteria.add(Restrictions.eq("id.coPaso",codPaso));
		List<FormulaArgumentoDTO> lista=this.formulaArgumentoDao.findByCriteria(criteria);
		
		
		return lista;
	}
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<FormulaArgumentoDTO, FormulaArgumentoDTOId> getDao() {
		return this.getFormulaArgumentoDao();
	}
	
	
	
	/**
	 * Método que devuelve el atributo funcionArgumentoDao.
	 * 
	 * @return funcionArgumentoDao.
	 */
	public DAOBase<FormulaArgumentoDTO, FormulaArgumentoDTOId> getFormulaArgumentoDao() {
		return formulaArgumentoDao;
	}

	/**
	 * Método que establece el atributo funcionArgumentoDao.
	 * 
	 * @param funcionArgumentoDao
	 *            El funcionArgumentoDao.
	 */
	public void setFormulaArgumentoDao(DAOBase<FormulaArgumentoDTO, FormulaArgumentoDTOId> formulaArgumentoDao) {
		this.formulaArgumentoDao = formulaArgumentoDao;
	}

	public void auditorias(FormulaArgumentoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public List<FormulaArgumentoDTO> getFormulaArgumentoByFormula(String coFormula, Byte coPaso){
		DetachedCriteria criteria = DetachedCriteria.forClass(FormulaArgumentoDTO.class);
		criteria.add(Restrictions.eq("id.coFormula", coFormula));
		criteria.add(Restrictions.eq("id.coPaso", coPaso));
		criteria.addOrder(Order.asc("id.coArgumento"));
		List<FormulaArgumentoDTO> formulas= this.getDao().findByCriteria(criteria);
		return formulas;
	}
	
	
}