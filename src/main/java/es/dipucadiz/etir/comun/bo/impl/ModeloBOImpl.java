package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.ModeloBO;
import es.dipucadiz.etir.comun.constants.TipoModeloConstants;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CircuitoModeloDTO;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ConceptoModeloDTO;
import es.dipucadiz.etir.comun.dto.LiquidatorioEjercicioDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.TramitePlantillaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class ModeloBOImpl extends AbstractGenericBOImpl<ModeloDTO, String>
        implements ModeloBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a modelo.
	 */
	private DAOBase<ModeloDTO, String> dao;
	
	/**
	 * Atributo que almacena el DAO asociado a conceptoModelo.
	 */
	private DAOBase<ConceptoModeloDTO, Long> conceptoModeloDao;

	

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<ModeloDTO> findAllModelos(final String estado)
	        throws GadirServiceException {
		List<ModeloDTO> modelos = Collections.EMPTY_LIST;

		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("estado", estado);
			modelos = this.getDao().findByNamedQuery(
			        QueryName.DOCUMENTOS_OBTENER_TODOS_LOS_MODELOS, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el listado de modelos:", e);
		}
		return modelos;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<ModeloDTO> findModelosByConcepto(final String coConcepto)
	        throws GadirServiceException {
		List<ModeloDTO> lista = null;
		if (Utilidades.isEmpty(coConcepto)) {
			lista = Collections.EMPTY_LIST;
		} else {
			try {
				final Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("coConcepto", coConcepto);
				lista = this.dao.findByNamedQuery(
				        QueryName.MODELOS_BY_CONCEPTO, param);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener los modelos filtrados por el concepto dado. Concepto: "
				                + coConcepto, e);
			}
		}
		return lista;
	}
	
	/**
	 * {@inheritDoc}
	 */


	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<ModeloDTO> findModelosByConceptoTLF_LR(final String coConcepto) throws GadirServiceException {
		List<ModeloDTO> lista = null;
		if (Utilidades.isEmpty(coConcepto)) {
			lista = Collections.EMPTY_LIST;
		} else {
			try {
				final Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("coConcepto", coConcepto);
				lista = this.dao.findByNamedQuery("Modelo.findModelosByConceptoTLF_LR", param);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException("Error al obtener los modelos filtrados por el concepto dado. Concepto: " + coConcepto, e);
			}
		}
		return lista;
	}
	
	/**
	 * {@inheritDoc}
	 */
	

	public List<ModeloDTO> findModelosByConceptos(final List<ConceptoDTO> conceptos)
	        throws GadirServiceException { 
		List<ModeloDTO> lista = null;
		List<String> conc = new ArrayList<String>();
		for(ConceptoDTO c : conceptos){
			conc.add(c.getCoConcepto());
		}
		if(conc.size() > 0){
			DetachedCriteria criteria = DetachedCriteria.forClass(ModeloDTO.class);
			criteria.createAlias("conceptoModeloDTOs", "conceptoModeloDTOsAlias");
			criteria.add(Restrictions.in("conceptoModeloDTOsAlias.conceptoDTO.coConcepto", conc));
			lista = this.getDao().findByCriteria(criteria);
			return lista;
		}else{
			return new ArrayList<ModeloDTO>();
		}
	}
	

	/**
	 * @return the conceptoModeloDao
	 */
	public DAOBase<ConceptoModeloDTO, Long> getConceptoModeloDao() {
		return conceptoModeloDao;
	}

		
	
	/**
	 * {@inheritDoc}
	 */
	public Boolean eliminarModelo(final ModeloDTO modelo) throws GadirServiceException {
		try {
	
				for (ConceptoModeloDTO conceptoModelo : modelo.getConceptoModeloDTOs()) {
					this.getConceptoModeloDao().delete(conceptoModelo.getCoConceptoModelo());
				}
					
				this.delete(modelo.getCoModelo());
				
				return true;
				
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error actualizando un modelo y una versión.", e);
			
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Integer> comprobarEliminarModelo(final ModeloDTO modelo) throws GadirServiceException {
		try {
			
			//Comprobamos que el modelo no tenga relaciones que no debamos eliminar.
			List<Integer> resModelo = tieneRelacionesNoEliminables(modelo.getCoModelo());
			
			return resModelo;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error actualizando un modelo y una versión.", e);
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> tieneRelacionesNoEliminables(final String coModelo)
	        throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("codModelo", coModelo);
		try {
			List<Integer> errores = new ArrayList<Integer>();
			
			List<Object> resultado = (List<Object>) this.getDao().ejecutaNamedQuerySelect(
			        "ModeloDTO.tieneRelacionesNoEliminables", params);
		
			if (!resultado.isEmpty()) {
				for(int i=0; i<resultado.size(); i++){
					errores.add(Integer.parseInt(resultado.get(i).toString()));
				}
			} else {
				errores.add(0);
			}
			
			return errores;
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al comprobar las relaciones del Modelo dado.",
			        e);
		}
	}
	
	public List<ModeloDTO> findModelosEnDocumentos() throws GadirServiceException{
		List<ModeloDTO> lista = null;
		
			try {
				lista = this.dao.findByNamedQuery(QueryName.MODELOS_DE_DOCUMENTO);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException("Error al obtener los modelos");
			
		}
		return lista;
		
	}
	
	public ModeloDTO findInitializedById(String id) throws GadirServiceException {
		ModeloDTO modelo = dao.findById( id );
		
		if( !Hibernate.isInitialized( modelo ) ) {
			Hibernate.initialize( modelo );
		}
		if( !Hibernate.isInitialized( modelo.getModeloDTO() ) ) {
			Hibernate.initialize( modelo.getModeloDTO() );
		}
		if( !Hibernate.isInitialized( modelo.getCircuitoModeloDTOs() ) ) {
			Hibernate.initialize( modelo.getCircuitoModeloDTOs() );
			for( CircuitoModeloDTO circuitoModelo: modelo.getCircuitoModeloDTOs() ) {
				Hibernate.initialize( circuitoModelo );
			}
		}
		if( !Hibernate.isInitialized( modelo.getConceptoModeloDTOs() ) ) {
			Hibernate.initialize( modelo.getConceptoModeloDTOs() );
			for( ConceptoModeloDTO conceptoModelo: modelo.getConceptoModeloDTOs() ) {
				Hibernate.initialize( conceptoModelo );
			}
		}
		if( !Hibernate.isInitialized( modelo.getLiquidatorioEjercicioDTOs() ) ) {
			Hibernate.initialize( modelo.getLiquidatorioEjercicioDTOs() );
			for( LiquidatorioEjercicioDTO liquidacionesEjercicio: modelo.getLiquidatorioEjercicioDTOs() ) {
				Hibernate.initialize( liquidacionesEjercicio );
			}
		}
		if( !Hibernate.isInitialized( modelo.getModeloDTOs() ) ) {
			Hibernate.initialize( modelo.getModeloDTOs() );
			for( ModeloDTO modeloDTO: modelo.getModeloDTOs() ) {
				Hibernate.initialize( modeloDTO );
			}
		}
		if( !Hibernate.isInitialized( modelo.getModeloVersionDTOs() ) ) {
			Hibernate.initialize( modelo.getModeloVersionDTOs() );
			for( ModeloVersionDTO modeloVersion: modelo.getModeloVersionDTOs() ) {
				Hibernate.initialize( modeloVersion );
			}
		}
		
		return modelo;
	}

	// GETTERS AND SETTERS

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<ModeloDTO, String> getDao() {
		return this.dao;
	}

	/**
	 * Método que establece el atributo dao.
	 * 
	 * @param dao
	 *            El dao.
	 */
	public void setDao(final DAOBase<ModeloDTO, String> dao) {
		this.dao = dao;
	}
	
	public void auditorias(ModeloDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

	public void setConceptoModeloDao(
			DAOBase<ConceptoModeloDTO, Long> conceptoModeloDao) {
		this.conceptoModeloDao = conceptoModeloDao;
	}

	public ModeloDTO findModeloReciboByConcepto(String coConcepto) throws GadirServiceException {
		ModeloDTO result = null;
		List<ModeloDTO> listaModeloDTOs = findModelosByConcepto(coConcepto);
		for (ModeloDTO modeloDTO : listaModeloDTOs) {
			if (TipoModeloConstants.TIPO_LIQUIDACION.equals(modeloDTO.getTipo()) && 
					TipoModeloConstants.SUBTIPO_RECIBO.equals(modeloDTO.getSubtipo())) {
				result = modeloDTO;
				break;
			}
		}
		return result;
	}

	public List<ModeloDTO> findModelosTiposRecibo() throws GadirServiceException {
		String[] propNames = {"tipo", "subtipo"};
		Object[] filters = {"L", "R"};
		return findFiltered(propNames, filters, "coModelo", DAOConstant.ASC_ORDER);
	}

	public List<ModeloDTO> findModelosTiposLiquidacion() throws GadirServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ModeloDTO.class);
		criteria.add(Restrictions.eq("tipo", "L"));
		criteria.add(Restrictions.in("subtipo", new String[] {"N", "I"}));
		criteria.addOrder(Order.asc("coModelo"));
		return findByCriteria(criteria);
	}


}
