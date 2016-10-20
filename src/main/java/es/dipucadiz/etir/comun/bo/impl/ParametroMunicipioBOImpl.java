package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ParametroMunicipioBO;
import es.dipucadiz.etir.comun.bo.ParametroMunicipioValorBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.ParametroMunicipioDTO;
import es.dipucadiz.etir.comun.dto.ParametroMunicipioValorDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ParametroMunicipioBOImpl extends AbstractGenericBOImpl<ParametroMunicipioDTO, Long>  implements ParametroMunicipioBO {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7906613593047467480L;
	
	private DAOBase<ParametroMunicipioDTO, Long> dao;
	
	private ParametroMunicipioValorBO parametroMunicipioValorBO;

	public DAOBase<ParametroMunicipioDTO, Long> getDao() {
		return dao;
	}
	
	public void setDao(final DAOBase<ParametroMunicipioDTO, Long> dao) {
		this.dao = dao;
	}
	
	public void auditorias(ParametroMunicipioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.dipucadiz.etir.comun.bo.ParametroMunicipioBO#findParametroMunicipioById(java.lang.Long)
	 */
	public ParametroMunicipioDTO findParametroMunicipioById(Long codParametro) throws GadirServiceException{
		ParametroMunicipioDTO parametro = dao.findById(codParametro);
		if(!Hibernate.isInitialized(parametro.getMunicipioDTO())){
			Hibernate.initialize(parametro.getMunicipioDTO());
		}
		if(!Hibernate.isInitialized(parametro.getModeloVersionDTO())){
			Hibernate.initialize(parametro.getModeloVersionDTO());
			if(!Hibernate.isInitialized(parametro.getModeloVersionDTO().getModeloDTO())){
				Hibernate.initialize(parametro.getModeloVersionDTO().getModeloDTO());
			}
		}
		if(!Hibernate.isInitialized(parametro.getConceptoDTO())){
			Hibernate.initialize(parametro.getConceptoDTO());
		}
		if(!Hibernate.isInitialized(parametro.getParametroDTOByCoParametroRango())){
			Hibernate.initialize(parametro.getParametroDTOByCoParametroRango());
		}
		
		return parametro;
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.dipucadiz.etir.comun.bo.ParametroMunicipioBO#deleteParametroMunicipioAndValores(java.lang.Long)
	 */
	public void deleteParametroMunicipioAndValores(Long codParametroMunicipio) throws GadirServiceException{
		
		List<ParametroMunicipioValorDTO> valores = this.getParametroMunicipioValorBO().findByParametroMunicipio(codParametroMunicipio);
		for (ParametroMunicipioValorDTO valor : valores) {
			this.getParametroMunicipioValorBO().delete(valor.getId());
		}
		this.delete(codParametroMunicipio);
		
	}

	
	
	public List<Object[]> findByMunicipioConceptoModeloVersion(MunicipioDTOId idMunicipio,
			String codigoConcepto, ModeloVersionDTOId idModeloVersion) throws GadirServiceException{
		
		

		final DetachedCriteria criteria = DetachedCriteria.forClass(ParametroMunicipioDTO.class);
		
		criteria.setFetchMode("municipioDTO", FetchMode.JOIN);
		criteria.setFetchMode("conceptoDTO", FetchMode.JOIN);
		criteria.setFetchMode("modeloVersionDTO", FetchMode.JOIN);
		criteria.setFetchMode("parametroDTOByCoParametro", FetchMode.JOIN);

		criteria.add(Restrictions.eq("municipioDTO.id",idMunicipio));	
		criteria.add(Restrictions.eq("conceptoDTO.id",codigoConcepto));
		criteria.add(Restrictions.eq("modeloVersionDTO.id",idModeloVersion));
		
		criteria.createAlias("parametroDTOByCoParametro", "parametro");

		
		
//		criteria.setProjection(Projections.distinct(Projections.projectionList()));
//		criteria.setProjection(Projections.property("parametroDTOByCoParametro"), "parametro");
		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(
						Projections.property("parametro.coParametro")))
				.add(Projections.property("parametro.nombre")));
		
				 
	
		List lista=this.dao.findByCriteria(criteria);
		
		
		
		return lista;
		
	}
	/**
	 * @return the parametroMunicipioValorBO
	 */
	public ParametroMunicipioValorBO getParametroMunicipioValorBO() {
		return parametroMunicipioValorBO;
	}

	/**
	 * @param parametroMunicipioValorBO the parametroMunicipioValorBO to set
	 */
	public void setParametroMunicipioValorBO(
			ParametroMunicipioValorBO parametroMunicipioValorBO) {
		this.parametroMunicipioValorBO = parametroMunicipioValorBO;
	}
	
}