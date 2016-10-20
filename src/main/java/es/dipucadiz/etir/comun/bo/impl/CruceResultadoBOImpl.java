package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CruceResultadosBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CruceDTO;
import es.dipucadiz.etir.comun.dto.CruceResultadoDTO;
import es.dipucadiz.etir.comun.dto.CruceResultadoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class CruceResultadoBOImpl extends AbstractGenericBOImpl<CruceResultadoDTO, CruceResultadoDTOId> implements CruceResultadosBO {
	
	private DAOBase<CruceResultadoDTO, CruceResultadoDTOId> dao;
	
	
	public List<CruceResultadoDTO> obtenerListado(CruceDTO cruce)throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceResultadoDTO.class);
		
		criteria.add(Restrictions.eq("id.coCruceGrupo",cruce.getId().getCoCruceGrupo()));
		criteria.add(Restrictions.eq("id.coArgumento",cruce.getId().getCoArgumento()));
		criteria.addOrder( Order.asc("id.coResultado"));

		List<CruceResultadoDTO> lista=this.dao.findByCriteria(criteria);
		
		return lista;
	}

	
	
	public Byte obtenerUltimoID(CruceDTO argumento) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceResultadoDTO.class);
		criteria.setProjection(Projections.max("id.coResultado"));
		
		criteria.setFetchMode("id",  FetchMode.JOIN);
		criteria.add(Restrictions.eq("cruceDTO.id.coCruceGrupo",argumento.getId().getCoCruceGrupo()));
		criteria.add(Restrictions.eq("cruceDTO.id.coArgumento",argumento.getId().getCoArgumento()));
//		criteria.setResultTransformer(Transformers.aliasToBean(Short.class));
		
		List lista=this.dao.findByCriteria(criteria);
//		List<Short> campaigns = this.dao.findByCriteria(criteria);

		if(lista!=null &&  lista.size()>0 && lista.get(0)!= null){
			
			return Byte.parseByte(lista.get(0).toString());
		}
		  
		return 0;
	            
	}
	

	
	public void auditorias(CruceResultadoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public DAOBase<CruceResultadoDTO, CruceResultadoDTOId> getDao() {
		return dao;
	}

	public void setDao(DAOBase<CruceResultadoDTO, CruceResultadoDTOId> dao) {
		this.dao = dao;
	}
	
}