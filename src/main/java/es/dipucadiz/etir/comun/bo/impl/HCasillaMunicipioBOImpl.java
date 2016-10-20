package es.dipucadiz.etir.comun.bo.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.HCasillaMunicipioBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FuncionDTO;
import es.dipucadiz.etir.comun.dto.HCasillaMunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class HCasillaMunicipioBOImpl extends AbstractGenericBOImpl<HCasillaMunicipioDTO, Long>
implements HCasillaMunicipioBO {
	
	
	
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */

	private static final long serialVersionUID = 2316250635387251595L;

	
	/**
	 * Atributo que almacena el DAO asociado a {@link FuncionDTO}.
	 */
	private DAOBase<HCasillaMunicipioDTO, Long> listaDao;
	
	
	
	public List<HCasillaMunicipioDTO> obtenerHCasillasMunicipio( String coMunicipio,
			String coConcepto,
			String coModelo,
			String coVersion,Date fecha)
				throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(HCasillaMunicipioDTO.class);
		criteria.add(Restrictions.eq("coMunicipio",coMunicipio));
		criteria.add(Restrictions.eq("coConcepto",coConcepto));
		criteria.add(Restrictions.eq("coModelo",coModelo));
		criteria.add(Restrictions.eq("coVersion",coVersion));
		criteria.addOrder(Order.desc("fhActualizacion"));
        
		
		if(fecha!= null){
			criteria.add(Restrictions.ge("fhActualizacion",fecha));
		}
		
		List<HCasillaMunicipioDTO> lista=this.listaDao.findByCriteria(criteria);
		
		
		return lista;
		
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<HCasillaMunicipioDTO, Long> getDao() {
		return this.getListaDao();
	}

	/**
	 * @return the listaDao
	 */
	public DAOBase<HCasillaMunicipioDTO, Long> getListaDao() {
		return listaDao;
	}

	/**
	 * @param listaDao
	 *            the listaDao to set
	 */
	public void setListaDao(final DAOBase<HCasillaMunicipioDTO, Long> listaDao) {
		this.listaDao = listaDao;
	}
	
	public void auditorias(HCasillaMunicipioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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