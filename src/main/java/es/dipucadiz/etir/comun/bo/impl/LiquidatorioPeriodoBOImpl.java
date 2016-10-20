/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.LiquidatorioPeriodoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.LiquidatorioPeriodoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class LiquidatorioPeriodoBOImpl extends AbstractGenericBOImpl<LiquidatorioPeriodoDTO, Long> implements LiquidatorioPeriodoBO {

	private static final Log LOG = LogFactory.getLog(LiquidatorioPeriodoBOImpl.class);
	
	private DAOBase<LiquidatorioPeriodoDTO, Long> dao;
	public DAOBase<LiquidatorioPeriodoDTO, Long> getDao() {
		return dao;
	}
	public void setDao(final DAOBase<LiquidatorioPeriodoDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(LiquidatorioPeriodoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
	
	@SuppressWarnings("unchecked")
	public List<String> findByMunicipioConceptoModeloEjercicio(final String coProvincia, final String coMunicipio, 
			final String coConcepto, final String coModelo, final String ejercicio) throws GadirServiceException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		try {
			parametros.put("coMunicipio", coMunicipio);
			parametros.put("coProvincia", coProvincia);
			parametros.put("coConcepto", coConcepto);
			parametros.put("coModelo", coModelo);
			parametros.put("ejercicio", new Short(ejercicio));
			return (List<String>)this.getDao().ejecutaNamedQuerySelect("LiquidatorioPeriodo.findByMunicipioConceptoModeloEjercicio", parametros);
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los periodos", e);
		}	
	}
	
	
	public List<LiquidatorioPeriodoDTO> findByMunicipioConceptoModeloEjercicioPAG(String coProvincia, String coMunicipio, String coConcepto, String coModelo, Short ejercicio) throws GadirServiceException{
		
		List<LiquidatorioPeriodoDTO> lista;
		
		lista = findByNamedQuery("LiquidatorioPeriodo.findByMunicipioAndConceptoAndModeloAndEjercicio", 
				new String[]{"coProvincia", "coMunicipio", "coConcepto", "coModelo", "ejercicio"}, 
				new Object[]{coProvincia, coMunicipio, coConcepto, coModelo, ejercicio});
		
		if (lista==null || lista.isEmpty()){
			lista = findByNamedQuery("LiquidatorioPeriodo.findByMunicipioAndConceptoAndModeloAndEjercicio", 
					new String[]{"coProvincia", "coMunicipio", "coConcepto", "coModelo", "ejercicio"}, 
					new Object[]{coProvincia, coMunicipio, coConcepto, "***", ejercicio});
		}
		
		if (lista==null || lista.isEmpty()){
			lista = findByNamedQuery("LiquidatorioPeriodo.findByMunicipioAndConceptoAndModeloAndEjercicio", 
					new String[]{"coProvincia", "coMunicipio", "coConcepto", "coModelo", "ejercicio"}, 
					new Object[]{coProvincia, coMunicipio, "****", coModelo, ejercicio});
		}
		
		if (lista==null || lista.isEmpty()){
			lista = findByNamedQuery("LiquidatorioPeriodo.findByMunicipioAndConceptoAndModeloAndEjercicio", 
					new String[]{"coProvincia", "coMunicipio", "coConcepto", "coModelo", "ejercicio"}, 
					new Object[]{coProvincia, coMunicipio, "****", "***", ejercicio});
		}
		
		if (lista==null || lista.isEmpty()){
			lista = findByNamedQuery("LiquidatorioPeriodo.findByMunicipioAndConceptoAndModeloAndEjercicio", 
					new String[]{"coProvincia", "coMunicipio", "coConcepto", "coModelo", "ejercicio"}, 
					new Object[]{"**", "***", coConcepto, coModelo, ejercicio});
		}
		
		if (lista==null || lista.isEmpty()){
			lista = findByNamedQuery("LiquidatorioPeriodo.findByMunicipioAndConceptoAndModeloAndEjercicio", 
					new String[]{"coProvincia", "coMunicipio", "coConcepto", "coModelo", "ejercicio"}, 
					new Object[]{"**", "***", coConcepto, "***", ejercicio});
		}
		
		if (lista==null || lista.isEmpty()){
			lista = findByNamedQuery("LiquidatorioPeriodo.findByMunicipioAndConceptoAndModeloAndEjercicio", 
					new String[]{"coProvincia", "coMunicipio", "coConcepto", "coModelo", "ejercicio"}, 
					new Object[]{"**", "***", "****", coModelo, ejercicio});
		}
		
		if (lista==null || lista.isEmpty()){
			lista = findByNamedQuery("LiquidatorioPeriodo.findByMunicipioAndConceptoAndModeloAndEjercicio", 
					new String[]{"coProvincia", "coMunicipio", "coConcepto", "coModelo", "ejercicio"}, 
					new Object[]{"**", "***", "****", "***", ejercicio});
		}
		
		
		return lista;
	}
	
	
	/**
	 * Método que devuelve una lista de periodos segun los Municipio, Concepto, 
	 * Modelo, ejercicio y periodo dados.
	 * 
	 * @param coProvincia
	 * @param coMunicipio
	 * @param coConcepto
	 * @param coModelo
	 * @param ejercicio
	 * @param periodo
	 * 
	 * @return List<LiquidatorioPeriodoDTO>
	 * @throws GadirServiceException
	 */
	public List<LiquidatorioPeriodoDTO> findByMunicipioConceptoModeloEjercicioPeriodo(final String coProvincia, final String coMunicipio, 
			final String coConcepto, final String coModelo, final String ejercicio, final String periodo) throws GadirServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(LiquidatorioPeriodoDTO.class);
		
		criteria.createAlias("liquidatorioEjercicioDTO", "liquidatorioEjercicioDTOAlias");
		
		criteria.add(Restrictions.eq("liquidatorioEjercicioDTOAlias.municipioDTO.id.coMunicipio", coMunicipio));
		criteria.add(Restrictions.eq("liquidatorioEjercicioDTOAlias.municipioDTO.id.coProvincia", coProvincia));
		if(Utilidades.isNotEmpty(coConcepto)){
			criteria.add(Restrictions.eq("liquidatorioEjercicioDTOAlias.conceptoDTO.coConcepto", coConcepto));
		}
		criteria.add(Restrictions.eq("liquidatorioEjercicioDTOAlias.modeloDTO.coModelo", coModelo));
		criteria.add(Restrictions.le("liquidatorioEjercicioDTOAlias.ejercicioDesde", new Short(ejercicio)));
		criteria.add(Restrictions.ge("liquidatorioEjercicioDTOAlias.ejercicioHasta", new Short(ejercicio)));
		criteria.add(Restrictions.eq("periodoLiquidatorio", periodo));
		List<LiquidatorioPeriodoDTO> periodos= this.getDao().findByCriteria(criteria);
		
		return periodos;
	}
	
	/**
	 * Método que devuelve una lista de periodos segun los Municipio, Concepto, 
	 * Modelo y ejercicio dados.
	 * 
	 * @param coProvincia
	 * @param coMunicipio
	 * @param coConcepto
	 * @param coModelo
	 * @param ejercicio
	 * 
	 * @return List<LiquidatorioPeriodoDTO>
	 * @throws GadirServiceException
	 */
	public List<LiquidatorioPeriodoDTO> findByMunicipioModeloEjercicioSinConcepto(final String coProvincia, final String coMunicipio, 
			final String coConcepto, final String coModelo, final String ejercicio) throws GadirServiceException {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(LiquidatorioPeriodoDTO.class);
		criteria.createAlias("liquidatorioEjercicioDTO", "liquidatorioEjercicioDTOAlias");
		
		criteria.add(Restrictions.eq("liquidatorioEjercicioDTOAlias.municipioDTO.id.coMunicipio", coMunicipio));
		criteria.add(Restrictions.eq("liquidatorioEjercicioDTOAlias.municipioDTO.id.coProvincia", coProvincia));
		if(Utilidades.isNotEmpty(coConcepto)){
			criteria.add(Restrictions.eq("liquidatorioEjercicioDTOAlias.conceptoDTO.coConcepto", coConcepto));
		}
		criteria.add(Restrictions.eq("liquidatorioEjercicioDTOAlias.modeloDTO.coModelo", coModelo));
		criteria.add(Restrictions.le("liquidatorioEjercicioDTOAlias.ejercicioDesde", new Short(ejercicio)));
		criteria.add(Restrictions.ge("liquidatorioEjercicioDTOAlias.ejercicioHasta", new Short(ejercicio)));
		List<LiquidatorioPeriodoDTO> periodos= this.getDao().findByCriteria(criteria);
		
		return periodos;
	}
	
		
}
