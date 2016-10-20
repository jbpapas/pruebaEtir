
package es.dipucadiz.etir.comun.bo.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.HCasillaMunicipioOperacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.FuncionDTO;
import es.dipucadiz.etir.comun.dto.HCasillaMunicipioOperaciDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class HCasillaMunicipioOperacionBOImpl extends AbstractGenericBOImpl<HCasillaMunicipioOperaciDTO, Long>
implements HCasillaMunicipioOperacionBO {
	
	
	
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */

	private static final long serialVersionUID = 2316250635387251595L;

	
	/**
	 * Atributo que almacena el DAO asociado a {@link FuncionDTO}.
	 */
	private DAOBase<HCasillaMunicipioOperaciDTO, Long> listaDao;
	
	
	
	public List<HCasillaMunicipioOperaciDTO> obtenerHCasillasMunicipioOpe( Long cocasillaMunicipio,
			Date fecha)
			throws GadirServiceException{
		
		final DetachedCriteria criteria = DetachedCriteria.forClass(HCasillaMunicipioOperaciDTO.class);
		criteria.add(Restrictions.eq("coCasillaMunicipio",cocasillaMunicipio));
		
		criteria.addOrder(Order.desc("fhActualizacion"));
        
		
		if(fecha!= null){
			criteria.add(Restrictions.ge("fhActualizacion",fecha));
		}
		
		List<HCasillaMunicipioOperaciDTO> lista=this.listaDao.findByCriteria(criteria);
		
		
		return lista;
		
		
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<HCasillaMunicipioOperaciDTO, Long> getDao() {
		return this.getListaDao();
	}

	/**
	 * @return the listaDao
	 */
	public DAOBase<HCasillaMunicipioOperaciDTO, Long> getListaDao() {
		return listaDao;
	}

	/**
	 * @param listaDao
	 *            the listaDao to set
	 */
	public void setListaDao(final DAOBase<HCasillaMunicipioOperaciDTO, Long> listaDao) {
		this.listaDao = listaDao;
	}
	
	public void auditorias(HCasillaMunicipioOperaciDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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