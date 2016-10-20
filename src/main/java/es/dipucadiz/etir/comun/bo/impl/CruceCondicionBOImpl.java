package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CruceCondicionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CruceCondicionDTO;
import es.dipucadiz.etir.comun.dto.CruceCondicionDTOId;
import es.dipucadiz.etir.comun.dto.CruceDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class CruceCondicionBOImpl extends AbstractGenericBOImpl<CruceCondicionDTO, CruceCondicionDTOId> implements CruceCondicionBO {

	private DAOBase<CruceCondicionDTO, CruceCondicionDTOId> dao;
	
	public void auditorias(CruceCondicionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	public DAOBase<CruceCondicionDTO, CruceCondicionDTOId> getDao() {
		return dao;
	}

	public void setDao(DAOBase<CruceCondicionDTO, CruceCondicionDTOId> dao) {
		this.dao = dao;
	}
	
	public List<CruceCondicionDTO> obtenerListado(CruceDTO cruce)throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceCondicionDTO.class);
		
		criteria.add(Restrictions.eq("cruceDTO.id.coCruceGrupo",cruce.getId().getCoCruceGrupo()));
		criteria.add(Restrictions.eq("cruceDTO.id.coArgumento",cruce.getId().getCoArgumento()));
		criteria.addOrder( Order.asc("id.coCondicion"));

		List<CruceCondicionDTO> lista=this.dao.findByCriteria(criteria);
		
		return lista;
	}

	public Short obtenerUltimoID(CruceDTO cruce)throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceCondicionDTO.class);
		criteria.setProjection(Projections.max("id.coCondicion"));
		
		criteria.setFetchMode("id",  FetchMode.JOIN);
		criteria.add(Restrictions.eq("cruceDTO.id.coCruceGrupo",cruce.getId().getCoCruceGrupo()));
		criteria.add(Restrictions.eq("cruceDTO.id.coArgumento",cruce.getId().getCoArgumento()));
//		criteria.setResultTransformer(Transformers.aliasToBean(Short.class));
		
		List lista=this.dao.findByCriteria(criteria);
//		List<Short> campaigns = this.dao.findByCriteria(criteria);

		if(lista!=null &&  lista.size()>0 && lista.get(0)!= null){
			
			return Short.parseShort(lista.get(0).toString());
		}
		  
		return 0;
	            
	}
	
	
	
	public Short obtenerPrimerID(CruceDTO cruce)throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceCondicionDTO.class);
		criteria.setProjection(Projections.min("id.coCondicion"));
		
		criteria.setFetchMode("id",  FetchMode.JOIN);
		criteria.add(Restrictions.eq("cruceDTO.id.coCruceGrupo",cruce.getId().getCoCruceGrupo()));
		criteria.add(Restrictions.eq("cruceDTO.id.coArgumento",cruce.getId().getCoArgumento()));
//		criteria.setResultTransformer(Transformers.aliasToBean(Short.class));
		
		List lista=this.dao.findByCriteria(criteria);
//		List<Short> campaigns = this.dao.findByCriteria(criteria);

		if(lista!=null &&  lista.size()>0 && lista.get(0)!= null){
			
			return Short.parseShort(lista.get(0).toString());
		}
		  
		return 0;
	            
	}
	
	
	
	public CruceDTO obtenerArgumento(String rowidCondicion)throws GadirServiceException{
		
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceCondicionDTO.class);
		criteria.setFetchMode("cruceDTO", FetchMode.JOIN);
		criteria.add(Restrictions.eq("rowid",Utilidades.decodificarRowidFormatoSeguro(rowidCondicion)));
		List<CruceCondicionDTO> lista=this.dao.findByCriteria(criteria);
		if(lista!=null && lista.size()>0){
			return lista.get(0).getCruceDTO();
		}
		
		
		
		return null;
		
	}
}