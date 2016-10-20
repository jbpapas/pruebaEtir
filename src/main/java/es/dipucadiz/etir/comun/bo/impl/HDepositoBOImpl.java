package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.HDepositoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FuncionDTO;
import es.dipucadiz.etir.comun.dto.HDepositoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class HDepositoBOImpl extends AbstractGenericBOImpl<HDepositoDTO, Long> implements HDepositoBO {

	private static final long serialVersionUID = -5271590775753363503L;

	/**
	 * Atributo que almacena el DAO asociado a {@link FuncionDTO}.
	 */
	private DAOBase<HDepositoDTO, Long> listaDao;

	
	public String obtenerTipoAnterior( Long coDeposito)
				throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(HDepositoDTO.class);
		criteria.add(Restrictions.eq("coDeposito",coDeposito));
		criteria.addOrder(Order.desc("fhActualizacion"));
        
		List<HDepositoDTO> lista=findByCriteria(criteria);
		
		if (lista.size()>1){
			for(HDepositoDTO l: lista){
				if(!l.getTipo().equalsIgnoreCase("I") && !l.getTipo().equalsIgnoreCase("B"))
					return l.getTipo();
			}
			return "D";
		}else
			return "D";
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<HDepositoDTO, Long> getDao() {
		return this.getListaDao();
	}

	/**
	 * @return the listaDao
	 */
	public DAOBase<HDepositoDTO, Long> getListaDao() {
		return listaDao;
	}

	/**
	 * @param listaDao
	 *            the listaDao to set
	 */
	public void setListaDao(final DAOBase<HDepositoDTO, Long> listaDao) {
		this.listaDao = listaDao;
	}
	
	
	public void auditorias(HDepositoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
	
	