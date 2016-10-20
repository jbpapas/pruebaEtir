package es.dipucadiz.etir.comun.bo.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ArgumentoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CruceDTO;
import es.dipucadiz.etir.comun.dto.CruceDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.ArgumentoVO;


public class ArgumentoBOImpl extends AbstractGenericBOImpl<CruceDTO, CruceDTOId> implements ArgumentoBO {

	private DAOBase<CruceDTO, CruceDTOId> dao;
	
	
	public CruceDTO findByRowidConCondicionAndResultado(String rowid) throws GadirServiceException{
		
			final DetachedCriteria criteria = DetachedCriteria.forClass(CruceDTO.class);
		
		criteria.add(Restrictions.eq("rowid",Utilidades.decodificarRowidFormatoSeguro(rowid)));
		criteria.setFetchMode("cruceGrupoDTO", FetchMode.JOIN);
		criteria.setFetchMode("cruceGrupoDTO.municipioDTO", FetchMode.JOIN);
		criteria.setFetchMode("conceptoDTOByCoConceptoOrigen", FetchMode.JOIN);
		
		criteria.setFetchMode("conceptoDTOByCoConceptoCruce", FetchMode.JOIN);
		
		
		criteria.setFetchMode("modeloVersionDTOByVermodArgcruCruce", FetchMode.JOIN);
		criteria.setFetchMode("modeloVersionDTOByVermodArgcruCruce.modeloDTO", FetchMode.JOIN);
		
		criteria.setFetchMode("modeloVersionDTOByVermodArgcru", FetchMode.JOIN);
		criteria.setFetchMode("modeloVersionDTOByVermodArgcru.modeloDTO", FetchMode.JOIN);
		
		
		List<CruceDTO> lista=this.dao.findByCriteria(criteria);
		if (lista!= null && lista.size()>0){
			return lista.get(0);
		}else{
			return null;
		}
		
		
		
		
	}
	
	public List<ArgumentoVO> findByCruceConCondicionAndResultado(Long idCruce) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceDTO.class);
		
		criteria.add(Restrictions.eq("cruceGrupoDTO.coCruceGrupo",idCruce));
		criteria.setFetchMode("cruceGrupoDTO", FetchMode.JOIN);
		criteria.setFetchMode("cruceGrupoDTO.municipioDTO", FetchMode.JOIN);
		criteria.setFetchMode("conceptoDTOByCoConceptoOrigen", FetchMode.JOIN);
		
		criteria.setFetchMode("conceptoDTOByCoConceptoCruce", FetchMode.JOIN);
		
		
		criteria.setFetchMode("modeloVersionDTOByVermodArgcruCruce", FetchMode.JOIN);
		criteria.setFetchMode("modeloVersionDTOByVermodArgcruCruce.modeloDTO", FetchMode.JOIN);
		
		criteria.setFetchMode("modeloVersionDTOByVermodArgcru", FetchMode.JOIN);
		criteria.setFetchMode("modeloVersionDTOByVermodArgcru.modeloDTO", FetchMode.JOIN);
		criteria.add(Restrictions.isNotEmpty("cruceResultadoDTOs"));
		criteria.add(Restrictions.isNotEmpty("cruceCondicionDTOs"));
		
		
		List<CruceDTO> lista=this.dao.findByCriteria(criteria);
		List<ArgumentoVO> listaArgumentos=null;
		if(lista!=null && lista.size()>0){
			listaArgumentos=new ArrayList<ArgumentoVO>();
			CruceDTO cruceTemp;
			
			for (int i=0; i<lista.size();i++){
				cruceTemp = lista.get(i);
				ArgumentoVO arg= new ArgumentoVO();
				
				try {
					PropertyUtils.copyProperties(arg, cruceTemp);
				} catch (IllegalAccessException e) {
					log.error(e);
				} catch (InvocationTargetException e) {
					log.error(e);
				} catch (NoSuchMethodException e) {
					log.error(e);
				}
				listaArgumentos.add(arg);
				
				
			}
		}
		
		
		return listaArgumentos;
	}
	
	
	
	public List<ArgumentoVO> findByCruce(Long idCruce) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CruceDTO.class);
		
		criteria.add(Restrictions.eq("cruceGrupoDTO.coCruceGrupo",idCruce));
		
		criteria.setFetchMode("conceptoDTOByCoConceptoOrigen", FetchMode.JOIN);
//		criteria.setFetchMode("modelconceptoDTOByCoConceptoCruce", FetchMode.JOIN);
		
		criteria.setFetchMode("conceptoDTOByCoConceptoCruce", FetchMode.JOIN);
//		criteria.setFetchMode("modelconceptoDTOByCoConceptoCruce", FetchMode.JOIN);
		
		
		criteria.setFetchMode("modeloVersionDTOByVermodArgcruCruce", FetchMode.JOIN);
		criteria.setFetchMode("modeloVersionDTOByVermodArgcruCruce.modeloDTO", FetchMode.JOIN);
		
		criteria.setFetchMode("modeloVersionDTOByVermodArgcru", FetchMode.JOIN);
		criteria.setFetchMode("modeloVersionDTOByVermodArgcru.modeloDTO", FetchMode.JOIN);
		
		
		List<CruceDTO> lista=this.dao.findByCriteria(criteria);
		List<ArgumentoVO> listaArgumentos=null;
		if(lista!=null && lista.size()>0){
			listaArgumentos=new ArrayList<ArgumentoVO>();
			CruceDTO cruceTemp;
			
			for (int i=0; i<lista.size();i++){
				cruceTemp = lista.get(i);
				ArgumentoVO arg= new ArgumentoVO();
				
				try {
					PropertyUtils.copyProperties(arg, cruceTemp);
				} catch (IllegalAccessException e) {
					log.error(e);
				} catch (InvocationTargetException e) {
					log.error(e);
				} catch (NoSuchMethodException e) {
					log.error(e);
				}
				listaArgumentos.add(arg);
				
				
			}
		}
		
		
		return listaArgumentos;
	}
	
	
	
	
	public void auditorias(CruceDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	public DAOBase<CruceDTO, CruceDTOId> getDao() {
		return dao;
	}

	public void setDao(DAOBase<CruceDTO, CruceDTOId> dao) {
		this.dao = dao;
	}




	public void eliminarArgumento(CruceDTO argumento)
			throws GadirServiceException {
		
		// Eliminamos las condiciones del argumento
		String sqlQuery = "delete from ga_cruce_condicion where co_cruce_grupo = " + 
			argumento.getId().getCoCruceGrupo() + " and co_argumento = " + argumento.getId().getCoArgumento();
		this.getDao().ejecutaSQLQuerySelect(sqlQuery);
		
		// Eliminamos los campos resultados del argumento
		sqlQuery = "delete from ga_cruce_resultado_campo where co_cruce_grupo = " + 
			argumento.getId().getCoCruceGrupo() + " and co_argumento = " + argumento.getId().getCoArgumento();
		this.getDao().ejecutaSQLQuerySelect(sqlQuery);
		
		// Eliminamos los resultados del argumento
		sqlQuery = "delete from ga_cruce_resultado where co_cruce_grupo = " + 
			argumento.getId().getCoCruceGrupo() + " and co_argumento = " + argumento.getId().getCoArgumento();
		this.getDao().ejecutaSQLQuerySelect(sqlQuery);
		
		// Eliminamos el argumento propiamente dicho
		this.delete(argumento.getId());
	}

	@SuppressWarnings("unchecked")
	public Integer crearId(String coCruce) {
		String sqlQuery = "select max(co_argumento) from ga_cruce where co_cruce_grupo = " + coCruce;
		Integer max = 0;
		
		List<BigDecimal> resultado = (List<BigDecimal>) this.getDao()
				.ejecutaSQLQuerySelect(sqlQuery);
		for (BigDecimal objeto : resultado) {
			if(objeto != null){
				max = new Integer(objeto.toString());
			}
		}

		return max + 1;
	}
	
	
	
}