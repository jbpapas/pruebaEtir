package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import es.dipucadiz.etir.comun.bo.ArgumentoBO;
import es.dipucadiz.etir.comun.bo.CruceBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CruceDTO;
import es.dipucadiz.etir.comun.dto.CruceGrupoDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.ArgumentoVO;


public class CruceBOImpl extends AbstractGenericBOImpl<CruceGrupoDTO, Long> implements CruceBO {
	
	
	private DAOBase<CruceGrupoDTO, Long> dao;
	private ArgumentoBO argumentoBO;
	
	private TransactionTemplate transactionTemplate; 
	private HibernateTemplate hibernateTemplate;  


	
	public List<CruceGrupoDTO> findByMunicipio(MunicipioDTO municipio) throws GadirServiceException{
		
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceGrupoDTO.class);
			criteria.setFetchMode("municipioDTO", FetchMode.JOIN);	
			criteria.setFetchMode("codigoTerritorialDTO", FetchMode.JOIN);	
			if(municipio!= null){
				criteria.add(Restrictions.eq("municipioDTO",municipio));	
			}
			
			
			List<CruceGrupoDTO> lista=this.dao.findByCriteria(criteria);
			if(lista!= null && lista.size()==0){
				lista=null;
			}
			return lista;
		
	}

	public List<CruceGrupoDTO> findByMunicipioYnombre(MunicipioDTO municipio,String nombre) throws GadirServiceException{
		if (municipio!=null){
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceGrupoDTO.class);
		
			criteria.setFetchMode("municipioDTO", FetchMode.JOIN);
			criteria.add(Restrictions.like("nombre", nombre));
			criteria.add(Restrictions.eq("municipioDTO",municipio));
			List<CruceGrupoDTO> lista=this.dao.findByCriteria(criteria);
			if(lista!= null && lista.size()==0){
				lista=null;
			}
			return lista;
		}else{
			return null;
		}
	}

	
	
	
	public DAOBase<CruceGrupoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(DAOBase<CruceGrupoDTO, Long> dao) {
		this.dao = dao;
	}

	
	
	public List<CruceGrupoDTO> findByRowidFetchMunicipio(String rowid) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceGrupoDTO.class);
		criteria.setFetchMode("unidadAdministrativaDTO", FetchMode.JOIN);
		criteria.setFetchMode("codigoTerritorialDTO", FetchMode.JOIN);
		criteria.setFetchMode("municipioDTO", FetchMode.JOIN);
		criteria.add(Restrictions.eq("rowid",Utilidades.decodificarRowidFormatoSeguro(rowid)));
		List<CruceGrupoDTO> lista=this.dao.findByCriteria(criteria);
		return lista;
	
	}
	
	public List<CruceGrupoDTO> findByIdFetchMunicipio(String id) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceGrupoDTO.class);
		criteria.setFetchMode("municipioDTO", FetchMode.JOIN);
		criteria.add(Restrictions.eq("coCruceGrupo", new Long(id)));
		List<CruceGrupoDTO> lista=this.dao.findByCriteria(criteria);
		return lista;
	
	}
	
	
	
	public void auditorias(CruceGrupoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	
//	public void eliminaCruce (String rowid) throws GadirServiceException{
//		final String rwid=rowid;
//		getTransactionTemplate().execute(new TransactionCallback() {  
//
//	            public Object doInTransaction(TransactionStatus status) {  
//	            List<CruceGrupoDTO> listCruce=null;
//				try {
//					listCruce = findByRowidFetchMunicipio(rwid);
//					
//					CruceGrupoDTO cruce;
//		       		 List <ArgumentoVO> listArg;
//		       		 ArgumentoVO arg;
//		            	if(listCruce!=null){
//		        			cruce=listCruce.get(0);
//		        			listArg=argumentoBO.findByCruce(cruce.getCoCruceGrupo());
//		        			if(listArg!=null){
//		        				for (int i=0; i<listArg.size();i++){
//		        					arg=listArg.get(i);
//		        					CruceDTO argumento=argumentoBO.findById(arg.getId());		        					
//		        					argumentoBO.eliminarArgumento(argumento);
//		        				}
//		        			}
//		        		dao.delete(cruce.getCoCruceGrupo());
//		        		}
//					
//					
//				} catch (GadirServiceException e) {
//					log.error(e);	
//				}
//	       		 
//	                return getHibernateTemplate().loadAll(CruceGrupoDTO.class);  
//
//	            }  
//
//	        });  
	
	public void eliminaCruce (String rowid) throws GadirServiceException{
		final String rwid=rowid;
	
	            List<CruceGrupoDTO> listCruce=null;
				
					listCruce = findByRowidFetchMunicipio(rwid);
					
					CruceGrupoDTO cruce;
		       		 List <ArgumentoVO> listArg;
		       		 ArgumentoVO arg;
		            	if(listCruce!=null){
		        			cruce=listCruce.get(0);
		        			listArg=argumentoBO.findByCruce(cruce.getCoCruceGrupo());
		        			if(listArg!=null){
		        				for (int i=0; i<listArg.size();i++){
		        					arg=listArg.get(i);
		        					CruceDTO argumento=argumentoBO.findById(arg.getId());		        					
		        					argumentoBO.eliminarArgumento(argumento);
		        				}
		        			}
		        		dao.delete(cruce.getCoCruceGrupo());
		        		}
				
	}	
					
				
	       		 
	               
	
	       


	



	public ArgumentoBO getArgumentoBO() {
		return argumentoBO;
	}

	public void setArgumentoBO(ArgumentoBO argumentoBO) {
		this.argumentoBO = argumentoBO;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
}