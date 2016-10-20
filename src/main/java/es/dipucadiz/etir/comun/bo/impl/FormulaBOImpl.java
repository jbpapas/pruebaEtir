package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.FormulaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FormulaDTO;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class FormulaBOImpl extends
AbstractGenericBOImpl<FormulaDTO, String>
implements FormulaBO {
	
	
	/**
	 * Atributo que almacena el dao asociado a {@link FuncionArgumentoDTO}.
	 */
	private DAOBase<FormulaDTO, String> formulaDao;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<FormulaDTO, String> getDao() {
		return this.getFormulaDao();
	}

	public DAOBase<FormulaDTO, String> getFormulaDao() {
		return formulaDao;
	}

	public void setFormulaDao(DAOBase<FormulaDTO, String> formulaDao) {
		this.formulaDao = formulaDao;
	}
	
	public void auditorias(FormulaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public FormulaDTO findByIdFormula(final String coFormula){
		return getDao().findById(coFormula);
		
	}
	
	public FormulaDTO findbyNombre(final String nombre){
		List<FormulaDTO> formulas = new ArrayList<FormulaDTO>();
		DetachedCriteria criteria = DetachedCriteria.forClass(FormulaDTO.class);
		criteria.add(Restrictions.eq("nombre", nombre));
		formulas= this.getDao().findByCriteria(criteria);
		if(formulas.size() < 1){
			return null;
		}else{
			return formulas.get(0);
		}
	}

	
}