package es.dipucadiz.etir.comun.bo.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CargaControlRecepcionBO;
import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CargaControlRecepcionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link CargaControlRecepcionDTO}.
 * 
 * @version 1.0 09/11/2009
 * @author SDS[AGONZALEZ]
 */
public class CargaControlRecepcionBOImpl extends
        AbstractGenericBOImpl<CargaControlRecepcionDTO, Long> implements
        CargaControlRecepcionBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6600031313681872877L;

	/**
	 * Atributo que almacena el DAO asociado a {@link CargaControlRecepcionDTO}.
	 */
	private DAOBase<CargaControlRecepcionDTO, Long> cargaControlRecepcionDao;

	@Override
	public DAOBase<CargaControlRecepcionDTO, Long> getDao() {
		return this.getCargaControlRecepcionDao();
	}

	public List<CargaControlRecepcionDTO> getListadoCargaControlRecepcion(
	        String municipio, String concepto, String modelo, String version, String fxDesde, String fxHasta)
	        throws GadirServiceException, ParseException {
		List<CargaControlRecepcionDTO> resultado = null;
		
		if(!Utilidades.isEmpty(fxDesde) && fxDesde.indexOf(":")==-1) fxDesde += " 00:00:00";
		if(!Utilidades.isEmpty(fxHasta) && fxHasta.indexOf(":")==-1) fxHasta += " 23:59:59";
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CargaControlRecepcionDTO.class);
		criteria.createAlias("cargaDTO", "c");
		criteria.createAlias("municipioDTO", "m");
		if (Utilidades.isNotEmpty(municipio)) {
			criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio", municipio));
		}
		if (Utilidades.isNotEmpty(concepto)) {
			criteria.add(Restrictions.eq("c.conceptoDTO.coConcepto", concepto));
		}
		if (Utilidades.isNotEmpty(modelo)) {
			criteria.add(Restrictions.eq("c.modeloVersionDTO.id.coModelo", modelo));
		}
		if (Utilidades.isNotEmpty(version)) {
			criteria.add(Restrictions.eq("c.modeloVersionDTO.id.coVersion", version));
		}
		if (Utilidades.isNotEmpty(fxDesde)) {
			criteria.add(Restrictions.ge("fhRecepcion", formateador.parse(fxDesde)));
		}
		if (Utilidades.isNotEmpty(fxHasta)) {
			criteria.add(Restrictions.le("fhRecepcion", formateador.parse(fxHasta)));
		}
		criteria.addOrder(Order.desc("fhRecepcion"));
		criteria.addOrder(Order.desc("coCargaControlRecepcion"));
		resultado = findByCriteria(criteria);
		
//		if(Utilidades.isEmpty(municipio)) {
//		
//			if(!Utilidades.isEmpty(concepto)){
//				resultado = this.findByNamedQuery(
//				        QueryName.CARGA_CONTROLES_CONCEPTO_TODOS_MUNICIPIOS, new String[] {
//				        		 "concepto" },
//				        new Object[] {concepto});
//				
//			}
//			else {
//				if(Utilidades.isEmpty(fxHasta)){
//					resultado = this.findByNamedQuery(
//				        QueryName.CARGA_CONTROLES_DESDE, new String[] {
//				        		"fxDesde" },
//				        new Object[] {formateador.parse(fxDesde)});
//				}
//				else{
//					resultado = this.findByNamedQuery(
//					        QueryName.CARGA_CONTROLES_DESDE_HASTA, new String[] {
//					        		"fxDesde","fxHasta" },
//					        new Object[] {formateador.parse(fxDesde), formateador.parse(fxHasta)});
//				}
//			}
//
//		}
//		else {
//			if(Utilidades.isEmpty(concepto)) {
//				if(Utilidades.isEmpty(fxDesde)){
//					resultado = this.findByNamedQuery(
//				        QueryName.CARGA_CONTROLES_MUNICIPIO, new String[] {
//				        		"municipio" },
//				        new Object[] {municipio});
//				}
//				else{
//					if(Utilidades.isEmpty(fxHasta)){
//						resultado = this.findByNamedQuery(
//						        QueryName.CARGA_CONTROLES_MUNICIPIO_DESDE, new String[] {
//						        		"municipio", "fxDesde" },
//						        new Object[] {municipio, formateador.parse(fxDesde)});
//					}
//					else{
//						resultado = this.findByNamedQuery(
//					        QueryName.CARGA_CONTROLES_MUNICIPIO_DESDE_HASTA, new String[] {
//					        		"municipio", "fxDesde","fxHasta" },
//					        new Object[] {municipio, formateador.parse(fxDesde), formateador.parse(fxHasta)});
//					}
//				}
//			}
//			else{
//				if(Utilidades.isEmpty(modelo)){
//					if(Utilidades.isEmpty(fxDesde)){
//						resultado = this.findByNamedQuery(
//					        QueryName.CARGA_CONTROLES_CONCEPTO, new String[] {
//					        		"municipio", "concepto" },
//					        new Object[] {municipio,concepto});
//					}
//					else{
//						if(Utilidades.isEmpty(fxHasta)){
//							resultado = this.findByNamedQuery(
//							        QueryName.CARGA_CONTROLES_CONCEPTO_DESDE, new String[] {
//							        		"municipio", "concepto", "fxDesde" },
//							        new Object[] {municipio, concepto, formateador.parse(fxDesde)});
//						}
//						else{
//							resultado = this.findByNamedQuery(
//						        QueryName.CARGA_CONTROLES_CONCEPTO_DESDE_HASTA, new String[] {
//						        		"municipio","concepto", "fxDesde","fxHasta" },
//						        new Object[] {municipio, concepto, formateador.parse(fxDesde), formateador.parse(fxHasta)});
//						}
//					}
//				}
//				else{
//					if(Utilidades.isEmpty(version)){
//						if(Utilidades.isEmpty(fxDesde)){
//							resultado = this.findByNamedQuery(
//						        QueryName.CARGA_CONTROLES_MODELO, new String[] {
//						        		"municipio", "concepto", "modelo" },
//						        new Object[] {municipio,concepto,modelo});
//						}
//						else{
//							if(Utilidades.isEmpty(fxHasta)){
//								resultado = this.findByNamedQuery(
//								        QueryName.CARGA_CONTROLES_MODELO_DESDE, new String[] {
//								        		"municipio", "concepto", "modelo", "fxDesde" },
//								        new Object[] {municipio, concepto, modelo, formateador.parse(fxDesde)});
//							}
//							else{
//								resultado = this.findByNamedQuery(
//							        QueryName.CARGA_CONTROLES_MODELO_DESDE_HASTA, new String[] {
//							        		"municipio","concepto", "modelo", "fxDesde","fxHasta" },
//							        new Object[] {municipio, concepto, modelo, formateador.parse(fxDesde), formateador.parse(fxHasta)});
//							}
//						}
//					}
//					else{
//						if(Utilidades.isEmpty(fxDesde)){
//							resultado = this.findByNamedQuery(
//						        QueryName.CARGA_CONTROLES_RECEPCION_SIN_FECHA, new String[] {
//						        		"municipio", "concepto", "modelo", "version" },
//						        new Object[] {municipio,concepto,modelo,version});
//						}
//						else{
//							if(Utilidades.isEmpty(fxHasta)){
//								resultado = this.findByNamedQuery(
//								        QueryName.CARGA_CONTROLES_RECEPCION_DESDE, new String[] {
//								        		"municipio", "concepto", "modelo", "version", "fxDesde" },
//								        new Object[] {municipio, concepto, modelo, version, formateador.parse(fxDesde)});
//							}
//							else{
//								resultado = this.findByNamedQuery(
//							        QueryName.CARGA_CONTROLES_RECEPCION, new String[] {
//							        		"municipio","concepto", "modelo", "version", "fxDesde","fxHasta" },
//							        new Object[] {municipio, concepto, modelo, version, formateador.parse(fxDesde), formateador.parse(fxHasta)});
//							}
//						}
//					}
//				}
//			}
//		}
		
		List<CargaControlRecepcionDTO> listaRecepciones = new ArrayList<CargaControlRecepcionDTO>();
		for (CargaControlRecepcionDTO cargaCriterioCondicionDTO : resultado) {
			try {
				if(cargaCriterioCondicionDTO.getEjecucionDTO() != null) {
					Hibernate.initialize(cargaCriterioCondicionDTO.getEjecucionDTO());
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.warn("Ejecución de carga inexistente", e);
			}				
			listaRecepciones.add(cargaCriterioCondicionDTO);
		}
		
		return listaRecepciones;
	}
	
	
	public List<CargaControlRecepcionDTO> getListadoCargaControlRecepcionAceptadas(
	        String municipio, String concepto, String modelo, String version, String fxDesde, String fxHasta)
	        throws GadirServiceException, ParseException {
		List<CargaControlRecepcionDTO> resultado = null;
		
		if(!Utilidades.isEmpty(fxDesde) && fxDesde.indexOf(":")==-1) fxDesde += " 00:00:00";
		if(!Utilidades.isEmpty(fxHasta) && fxHasta.indexOf(":")==-1) fxHasta += " 23:59:59";
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
		
		
		
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CargaControlRecepcionDTO.class);
		criteria.createAlias("cargaDTO", "c");
		criteria.createAlias("municipioDTO", "m");
		
		criteria.add(Restrictions.ne("estado", "R"));
		
		if (Utilidades.isNotEmpty(municipio)) {
			criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio", municipio));
		}
		if (Utilidades.isNotEmpty(concepto)) {
			criteria.add(Restrictions.eq("c.conceptoDTO.coConcepto", concepto));
		}
		if (Utilidades.isNotEmpty(modelo)) {
			criteria.add(Restrictions.eq("c.modeloVersionDTO.id.coModelo", modelo));
		}
		if (Utilidades.isNotEmpty(version)) {
			criteria.add(Restrictions.eq("c.modeloVersionDTO.id.coVersion", version));
		}
		if (Utilidades.isNotEmpty(fxDesde)) {
			criteria.add(Restrictions.ge("fhRecepcion", formateador.parse(fxDesde)));
		}
		if (Utilidades.isNotEmpty(fxHasta)) {
			criteria.add(Restrictions.le("fhRecepcion", formateador.parse(fxHasta)));
		}
		criteria.addOrder(Order.desc("fhRecepcion"));
		criteria.addOrder(Order.desc("coCargaControlRecepcion"));
		resultado = findByCriteria(criteria);
		
//		if(Utilidades.isEmpty(municipio)) {
//		
//			if(!Utilidades.isEmpty(concepto)){
//				resultado = this.findByNamedQuery(
//				        QueryName.CARGA_CONTROLES_CONCEPTO_TODOS_MUNICIPIOS, new String[] {
//				        		 "concepto" },
//				        new Object[] {concepto});
//				
//			}
//			else {
//				if(Utilidades.isEmpty(fxHasta)){
//					resultado = this.findByNamedQuery(
//				        QueryName.CARGA_CONTROLES_DESDE, new String[] {
//				        		"fxDesde" },
//				        new Object[] {formateador.parse(fxDesde)});
//				}
//				else{
//					resultado = this.findByNamedQuery(
//					        QueryName.CARGA_CONTROLES_DESDE_HASTA, new String[] {
//					        		"fxDesde","fxHasta" },
//					        new Object[] {formateador.parse(fxDesde), formateador.parse(fxHasta)});
//				}
//			}
//
//		}
//		else {
//			if(Utilidades.isEmpty(concepto)) {
//				if(Utilidades.isEmpty(fxDesde)){
//					resultado = this.findByNamedQuery(
//				        QueryName.CARGA_CONTROLES_MUNICIPIO, new String[] {
//				        		"municipio" },
//				        new Object[] {municipio});
//				}
//				else{
//					if(Utilidades.isEmpty(fxHasta)){
//						resultado = this.findByNamedQuery(
//						        QueryName.CARGA_CONTROLES_MUNICIPIO_DESDE, new String[] {
//						        		"municipio", "fxDesde" },
//						        new Object[] {municipio, formateador.parse(fxDesde)});
//					}
//					else{
//						resultado = this.findByNamedQuery(
//					        QueryName.CARGA_CONTROLES_MUNICIPIO_DESDE_HASTA, new String[] {
//					        		"municipio", "fxDesde","fxHasta" },
//					        new Object[] {municipio, formateador.parse(fxDesde), formateador.parse(fxHasta)});
//					}
//				}
//			}
//			else{
//				if(Utilidades.isEmpty(modelo)){
//					if(Utilidades.isEmpty(fxDesde)){
//						resultado = this.findByNamedQuery(
//					        QueryName.CARGA_CONTROLES_CONCEPTO, new String[] {
//					        		"municipio", "concepto" },
//					        new Object[] {municipio,concepto});
//					}
//					else{
//						if(Utilidades.isEmpty(fxHasta)){
//							resultado = this.findByNamedQuery(
//							        QueryName.CARGA_CONTROLES_CONCEPTO_DESDE, new String[] {
//							        		"municipio", "concepto", "fxDesde" },
//							        new Object[] {municipio, concepto, formateador.parse(fxDesde)});
//						}
//						else{
//							resultado = this.findByNamedQuery(
//						        QueryName.CARGA_CONTROLES_CONCEPTO_DESDE_HASTA, new String[] {
//						        		"municipio","concepto", "fxDesde","fxHasta" },
//						        new Object[] {municipio, concepto, formateador.parse(fxDesde), formateador.parse(fxHasta)});
//						}
//					}
//				}
//				else{
//					if(Utilidades.isEmpty(version)){
//						if(Utilidades.isEmpty(fxDesde)){
//							resultado = this.findByNamedQuery(
//						        QueryName.CARGA_CONTROLES_MODELO, new String[] {
//						        		"municipio", "concepto", "modelo" },
//						        new Object[] {municipio,concepto,modelo});
//						}
//						else{
//							if(Utilidades.isEmpty(fxHasta)){
//								resultado = this.findByNamedQuery(
//								        QueryName.CARGA_CONTROLES_MODELO_DESDE, new String[] {
//								        		"municipio", "concepto", "modelo", "fxDesde" },
//								        new Object[] {municipio, concepto, modelo, formateador.parse(fxDesde)});
//							}
//							else{
//								resultado = this.findByNamedQuery(
//							        QueryName.CARGA_CONTROLES_MODELO_DESDE_HASTA, new String[] {
//							        		"municipio","concepto", "modelo", "fxDesde","fxHasta" },
//							        new Object[] {municipio, concepto, modelo, formateador.parse(fxDesde), formateador.parse(fxHasta)});
//							}
//						}
//					}
//					else{
//						if(Utilidades.isEmpty(fxDesde)){
//							resultado = this.findByNamedQuery(
//						        QueryName.CARGA_CONTROLES_RECEPCION_SIN_FECHA, new String[] {
//						        		"municipio", "concepto", "modelo", "version" },
//						        new Object[] {municipio,concepto,modelo,version});
//						}
//						else{
//							if(Utilidades.isEmpty(fxHasta)){
//								resultado = this.findByNamedQuery(
//								        QueryName.CARGA_CONTROLES_RECEPCION_DESDE, new String[] {
//								        		"municipio", "concepto", "modelo", "version", "fxDesde" },
//								        new Object[] {municipio, concepto, modelo, version, formateador.parse(fxDesde)});
//							}
//							else{
//								resultado = this.findByNamedQuery(
//							        QueryName.CARGA_CONTROLES_RECEPCION, new String[] {
//							        		"municipio","concepto", "modelo", "version", "fxDesde","fxHasta" },
//							        new Object[] {municipio, concepto, modelo, version, formateador.parse(fxDesde), formateador.parse(fxHasta)});
//							}
//						}
//					}
//				}
//			}
//		}
		
		List<CargaControlRecepcionDTO> listaRecepciones = new ArrayList<CargaControlRecepcionDTO>();
		for (CargaControlRecepcionDTO cargaCriterioCondicionDTO : resultado) {
			try {
				if(cargaCriterioCondicionDTO.getEjecucionDTO() != null) {
					Hibernate.initialize(cargaCriterioCondicionDTO.getEjecucionDTO());
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.warn("Ejecución de carga inexistente", e);
			}				
			listaRecepciones.add(cargaCriterioCondicionDTO);
		}
		
		return listaRecepciones;
	}

	// GETTERS AND SETTERS

	/**
	 * Método que devuelve el atributo cargaControlRecepcionDao.
	 * 
	 * @return cargaControlRecepcionDao.
	 */
	public DAOBase<CargaControlRecepcionDTO, Long> getCargaControlRecepcionDao() {
		return cargaControlRecepcionDao;
	}

	/**
	 * Método que establece el atributo cargaControlRecepcionDao.
	 * 
	 * @param cargaControlRecepcionDao
	 *            El cargaControlRecepcionDao.
	 */
	public void setCargaControlRecepcionDao(
	        final DAOBase<CargaControlRecepcionDTO, Long> cargaControlRecepcionDao) {
		this.cargaControlRecepcionDao = cargaControlRecepcionDao;
	}
	
	public void auditorias(CargaControlRecepcionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
