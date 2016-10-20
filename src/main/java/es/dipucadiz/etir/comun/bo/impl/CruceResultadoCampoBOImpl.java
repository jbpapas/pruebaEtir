package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CruceResultadosCampoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CruceResultadoCampoDTO;
import es.dipucadiz.etir.comun.dto.CruceResultadoCampoDTOId;
import es.dipucadiz.etir.comun.dto.CruceResultadoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CruceResultadoCampoBOImpl extends AbstractGenericBOImpl<CruceResultadoCampoDTO, CruceResultadoCampoDTOId> implements CruceResultadosCampoBO {
	
	private DAOBase<CruceResultadoCampoDTO, CruceResultadoCampoDTOId> dao;
	

	
	public List<CruceResultadoCampoDTO> obtenerListado(CruceResultadoDTO resultado)throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceResultadoCampoDTO.class);
		
		criteria.add(Restrictions.eq("id.coCruceGrupo",resultado.getId().getCoCruceGrupo()));
		criteria.add(Restrictions.eq("id.coArgumento",resultado.getId().getCoArgumento()));
		criteria.add(Restrictions.eq("id.coResultado",resultado.getId().getCoResultado()));
		criteria.addOrder( Order.asc("id.coCampo"));
		

		List<CruceResultadoCampoDTO> lista=this.dao.findByCriteria(criteria);
		
		return lista;
	}

	public Short obtenerUltimoID(CruceResultadoDTO cruceResultado) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceResultadoCampoDTO.class);
		criteria.setProjection(Projections.max("id.coCampo"));
		
		criteria.setFetchMode("id",  FetchMode.JOIN);
		criteria.add(Restrictions.eq("cruceResultadoDTO.id.coCruceGrupo",cruceResultado.getId().getCoCruceGrupo()));
		criteria.add(Restrictions.eq("cruceResultadoDTO.id.coArgumento",cruceResultado.getId().getCoArgumento()));
		criteria.add(Restrictions.eq("cruceResultadoDTO.id.coResultado",cruceResultado.getId().getCoResultado()));
		
		List lista=this.dao.findByCriteria(criteria);

		if(lista!=null &&  lista.size()>0 && lista.get(0)!= null){
			
			return Short.parseShort(lista.get(0).toString());
		}
		  
		return 0;
	            
	}
	
	
	public void auditorias(CruceResultadoCampoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	public DAOBase<CruceResultadoCampoDTO, CruceResultadoCampoDTOId> getDao() {
		return dao;
	}

	public void setDao(DAOBase<CruceResultadoCampoDTO, CruceResultadoCampoDTOId> dao) {
		this.dao = dao;
	}
	
}