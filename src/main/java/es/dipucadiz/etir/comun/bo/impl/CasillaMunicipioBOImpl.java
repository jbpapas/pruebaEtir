package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CasillaModeloBO;
import es.dipucadiz.etir.comun.bo.CasillaMunicipioBO;
import es.dipucadiz.etir.comun.bo.CasillaMunicipioOperacionBO;
import es.dipucadiz.etir.comun.config.ParametrosConfig.ValoresParametrosConfig;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTO;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTOId;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioDTO;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioOperacionDTO;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioOperacionDTOId;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.MunicipioConceptoModeloUtil;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb05.action.G5114CasillasMunicipio.G5114AbstractAction;
import es.dipucadiz.etir.sb05.vo.CasillaMunicipioVO;



public class CasillaMunicipioBOImpl extends AbstractGenericBOImpl<CasillaMunicipioDTO, Long> implements CasillaMunicipioBO {

	private static final long serialVersionUID = 6879318165367306079L;

	
	/**
	 * Atributo que almacena el parametro codigo de modelo.
	 */
	private static final String PARAM_MODELO = "codModelo";

	/**
	 * Atributo que almacena el parametro codigo de version.
	 */
	private static final String PARAM_VERSION = "codVersion";

	/**
	 * Atributo que almacena el parametro numero de casilla.
	 */
	private static final String PARAM_CASILLA = "codCasilla";

	/**
	 * Atributo que almacena el parametro codigo de municipio.
	 */
	private static final String PARAM_MUNICIPIO = "codMunicipio";

	/**
	 * Atributo que almacena el parametro codigo de concepto.
	 */
	private static final String PARAM_CONCEPTO = "codConcepto";

	/**
	 * Atributo que almacena el parametro codigo de provincia.
	 */
	private static final String PARAM_PROVINCIA = "codProvincia";

	/**
	 * Atributo que almacena el DAO asociado a {@link CasillaMunicipioDTO}.
	 */
	private DAOBase<CasillaMunicipioDTO, Long> casillaMunicipioDao;

	/**
	 * Atributo que almacena el DAO asociado a {@link CasillaModeloDTO}.
	 */
	private CasillaModeloBO casillaModeloBO;
	
	/**
	 * Atributo que almacena el DAO asociado a {@link CasillaMunicipioOperacionDTO}.
	 */
	private CasillaMunicipioOperacionBO casillaMunicipioOperacionBO;
	
	/**
	 * Atributo que almacena el DAO asociado a {@link CasillaMunicipioOperacionDTO}.
	 */
	private DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> casillaMunicipioOperacionDao;

	/**
	 * Constante que contiene el proceso genérico
	 */
	public static final String OPERACION_GENERICA = "GENERICO";

	public CasillaMunicipioDTO findByIdInitialized(Long id) throws GadirServiceException {
		CasillaMunicipioDTO result=null;
		
		result = this.findById(id);
		
		if (result!=null){
			if(result.getCasillaModeloDTO()!=null){
				Hibernate.initialize(result.getCasillaModeloDTO());
				if(result.getCasillaModeloDTO().getModeloVersionDTO() != null){
					Hibernate.initialize(result.getCasillaModeloDTO().getModeloVersionDTO());
				}
			}
			if(result.getMunicipioDTO() != null){
				Hibernate.initialize(result.getMunicipioDTO());
				if(result.getMunicipioDTO().getProvinciaDTO() != null){
					Hibernate.initialize(result.getMunicipioDTO().getProvinciaDTO());
				}
			}
		}
		
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */   
	//pepe                           
	public List<CasillaMunicipioDTO> findCasillasMunicipioByMunicipioConceptoModeloVersion(
	        final String codMunicipio, final String codConcepto,
	        final String codModelo, final String codVersion)
	        throws GadirServiceException {	
		try {
			// Creamos el mapa de parametros.
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("codModelo", codModelo);
			params.put("codVersion", codVersion);
			// Iniciamos la lista de resultado.
			List<CasillaMunicipioDTO> lista = Collections.emptyList();
			// Si tenemos que filtrar por codMunicipio añadimos el trozo de query y
			
			if (Utilidades.isNotEmpty(codMunicipio)) {
				params.put("codMunicipio", codMunicipio);
				if (Utilidades.isNotEmpty(codConcepto)){
					params.put("codConcepto", codConcepto);
					lista = this.getCasillaMunicipioDao().findByNamedQuery(
					        QueryName.CASILLA_MUNICIPIO_BY_MOVERCONMUN, params);
				}else{
					lista = this.getCasillaMunicipioDao().findByNamedQuery(
					        QueryName.CASILLA_MUNICIPIO_BY_MOVERMUN, params);
				}
			}else if (Utilidades.isNotEmpty(codConcepto)){
				params.put("codConcepto", codConcepto);
				lista = this.getCasillaMunicipioDao().findByNamedQuery(
				        QueryName.CASILLA_MUNICIPIO_BY_MOVERCON, params);
			}else{
				lista = this.getCasillaMunicipioDao().findByNamedQuery(
				        QueryName.CASILLA_MUNICIPIO_BY_MOVER, params);
			}
				for(CasillaMunicipioDTO dto : lista){
					if(!Hibernate.isInitialized(dto.getMunicipioDTO())){
						Hibernate.initialize(dto.getMunicipioDTO());
					}
				}
				
			return lista;
			
		
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error obtener las casillas.", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */                             
	public List<CasillaMunicipioDTO> findCasillasMunicipioByMunicipioProvinciaConceptoModeloVersion(
	        final String codMunicipio, String codProcincia,final String codConcepto,
	        final String codModelo, final String codVersion)
	        throws GadirServiceException {	
		try {
			// Creamos el mapa de parametros.
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("codModelo", codModelo);
			params.put("codVersion", codVersion);
			// Iniciamos la lista de resultado.
			List<CasillaMunicipioDTO> lista = Collections.emptyList();
			// Si tenemos que filtrar por codMunicipio añadimos el trozo de query y
			
			if (Utilidades.isNotEmpty(codMunicipio)) {
				params.put("codMunicipio", codMunicipio);
				params.put("codProvincia", codProcincia);
				if (Utilidades.isNotEmpty(codConcepto)){
					params.put("codConcepto", codConcepto);
					
					lista = this.getCasillaMunicipioDao().findByNamedQuery(
					        QueryName.FIND_CASILLAS_BY_MUN_CON_MOD_VER, params);
				}else{
					lista = this.getCasillaMunicipioDao().findByNamedQuery(
					        QueryName.CASILLA_MUNICIPIO_BY_MOVERMUN, params);
				}
			}else if (Utilidades.isNotEmpty(codConcepto)){
				params.put("codConcepto", codConcepto);
				lista = this.getCasillaMunicipioDao().findByNamedQuery(
				        QueryName.CASILLA_MUNICIPIO_BY_MOVERCON, params);
			}else{
				lista = this.getCasillaMunicipioDao().findByNamedQuery(
				        QueryName.CASILLA_MUNICIPIO_BY_MOVER, params);
			}
				
				
			return lista;
			
		
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error obtener las casillas.", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public CasillaMunicipioDTO findCasillaMunicipioByMunicipioProvinciaConceptoModeloVersionNuCasilla(
	        final String codMunicipio, String codProvincia,final String codConcepto,
	        final String codModelo, final String codVersion, final String nuCasilla)
	        throws GadirServiceException {
		CasillaMunicipioDTO result = null;
		String[] propNames = {"municipioDTO.id.coMunicipio", "municipioDTO.id.coProvincia", "conceptoDTO.coConcepto", "casillaModeloDTO.id.coModelo", "casillaModeloDTO.id.coVersion", "casillaModeloDTO.id.nuCasilla"};
		Object[] filters = {codMunicipio, codProvincia, codConcepto, codModelo, codVersion, Short.valueOf(nuCasilla)};
		List<CasillaMunicipioDTO> casillaMunicipioDTOs = findFiltered(propNames, filters);
		if (!casillaMunicipioDTOs.isEmpty()) {
			result = casillaMunicipioDTOs.get(0);
		}
		return result;
	}
	
	

	

	

	

	/**
	 * {@inheritDoc}
	 */
	public boolean existCasillaMunicipioByUniqueConstraint(
	        final CasillaMunicipioDTO casMun) throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put(PARAM_PROVINCIA, casMun.getMunicipioDTO()
			        .getProvinciaDTO().getCoProvincia());
			params.put(PARAM_MUNICIPIO, casMun.getMunicipioDTO().getId()
			        .getCoMunicipio());	
			params.put(PARAM_CONCEPTO, casMun.getConceptoDTO().getCoConcepto());
			params.put(PARAM_MODELO, casMun.getCasillaModeloDTO().getId()
			        .getCoModelo());
			params.put(PARAM_VERSION, casMun.getCasillaModeloDTO().getId()
			        .getCoVersion());
			params.put(PARAM_CASILLA, casMun.getCasillaModeloDTO().getId()
			        .getNuCasilla());
			Object result = this.getDao().findByNamedQueryUniqueResult(
			        QueryName.CASILLA_MUNICIPIO_BY_UNIQUE_KEY, params);
			
			if(result == null){
				params.put(PARAM_PROVINCIA, ValoresParametrosConfig.VALOR_PROVINCIA_GENERICA);
				params.put(PARAM_MUNICIPIO, ValoresParametrosConfig.VALOR_MUNICIPIO_GENERICO);
				result = this.getDao().findByNamedQueryUniqueResult(
				        QueryName.CASILLA_MUNICIPIO_BY_UNIQUE_KEY, params);
			}
			return result != null;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al filtrar por: Provincia, Municipio, Concepto, Modelo, Version y Casilla.",
			        e);
		}
	}

	
	/**
	 * {@inheritDoc}
	 */
	public List<CasillaMunicipioDTO> findCasillasByMunicipioModeloVersionGenerica(
	        final String coMunicipio, final String coProvincia,
	        final String coModelo, final String coVersion,final String coConcepto)
	        throws GadirServiceException {
		if (Utilidades.isEmpty(coMunicipio) || Utilidades.isEmpty(coProvincia)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("Los datos por los que filtrar llegaron null, no se  recupera nada del sistema");
			}
			return Collections.emptyList();
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>();
				params.put(PARAM_PROVINCIA, coProvincia);
				params.put(PARAM_MUNICIPIO, coMunicipio);	
				params.put(PARAM_CONCEPTO, coConcepto);
				params.put(PARAM_MODELO, coModelo);
				params.put(PARAM_VERSION, coVersion);
				
				@SuppressWarnings("unchecked")
				List<Object[]> listaAuxiliar = (List<Object[]>) this.getDao().ejecutaNamedQuerySelect(QueryName.CASILLA_MUNICIPIO_BY_MOVERGEN, params);
				List<CasillaMunicipioDTO> listaCasillas = new ArrayList<CasillaMunicipioDTO>();
				for(Object[] casilla : listaAuxiliar){
					CasillaMunicipioDTO auxiliar = new CasillaMunicipioDTO();
					
					if(casilla[4] != null && casilla[5] != null && casilla[6] != null){
						CasillaModeloDTO casMod = (CasillaModeloDTO) casillaModeloBO.findById(new CasillaModeloDTOId((String)casilla[4],(String)casilla[5],((BigDecimal)casilla[6]).shortValue()));
						auxiliar.setCasillaModeloDTO(casMod);
					}
					
					if(casilla[3] != null){
						auxiliar.setConceptoDTO(new ConceptoDTO());
						auxiliar.getConceptoDTO().setCoConcepto((String)casilla[3]);
					}
					
					if(casilla[0] != null){
						auxiliar.setCoCasillaMunicipio(((BigDecimal)casilla[0]).longValue());
					}
					
					if(casilla[7] != null){
						auxiliar.setOrden(((BigDecimal)casilla[7]).shortValue());
					}
					
					listaCasillas.add(auxiliar);
				}
				return listaCasillas;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrior un error al obtener las casillas filtradas por municipio",
				        e);
			}
		}

	}

	
	public CasillaMunicipioDTO findByIdFetchCasillaModelo(Long id) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CasillaMunicipioDTO.class);
		
		criteria.add(Restrictions.eq("coCasillaMunicipio",id));
		criteria.setFetchMode("casillaModeloDTO", FetchMode.JOIN);
		List<CasillaMunicipioDTO> lista=this.casillaMunicipioDao.findByCriteria(criteria);
		if(lista!=null && lista.size()>0){
			return lista.get(0);
		}else{
			return null;
		}
	}

	/**
	 * Método que devuelve el atributo casillaMunicipioDao.
	 * 
	 * @return casillaMunicipioDao.
	 */
	public DAOBase<CasillaMunicipioDTO, Long> getCasillaMunicipioDao() {
		return casillaMunicipioDao;
	}

	/**
	 * Método que establece el atributo casillaMunicipioDao.
	 * 
	 * @param casillaMunicipioDao
	 *            El casillaMunicipioDao.
	 */
	public void setCasillaMunicipioDao(
	        final DAOBase<CasillaMunicipioDTO, Long> casillaMunicipioDao) {
		this.casillaMunicipioDao = casillaMunicipioDao;
	}

	/**
	 * Método que se encarga de devolver el dao de la clase.
	 * 
	 * @return DAOBase El dao de la clase.
	 */
	@Override
	public DAOBase<CasillaMunicipioDTO, Long> getDao() {
		return this.getCasillaMunicipioDao();
	}

	/**
	 * Método que devuelve el atributo casillaModeloBO.
	 * 
	 * @return casillaModeloBO.
	 */
	public CasillaModeloBO getCasillaModeloBO() {
		return casillaModeloBO;
	}

	/**
	 * Método que establece el atributo casillaModeloBO.
	 * 
	 * @param casillaModeloBO
	 *            El casillaModeloBO.
	 */
	public void setCasillaModeloBO(final CasillaModeloBO casillaModeloBO) {
		this.casillaModeloBO = casillaModeloBO;
	}
	
	public void auditorias(CasillaMunicipioDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
//		transientObject.setFhActualizacion(Utilidades.getDateActual()); // Es tabla padre, se pone la fecha manualmente.
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}
	
	
	
	public CasillaMunicipioDTO obtenerCasillaByIdFetchConceptoModelo(Long id) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CasillaMunicipioDTO.class);
		criteria.add(Restrictions.eq("coCasillaMunicipio",id));
		criteria.setFetchMode("municipioDTO", FetchMode.JOIN);
		criteria.setFetchMode("conceptoDTO", FetchMode.JOIN);
		criteria.setFetchMode("casillaModeloDTO", FetchMode.JOIN);
		criteria.setFetchMode("casillaModeloDTO.modeloVersionDTO", FetchMode.JOIN);
		criteria.setFetchMode("casillaModeloDTO.modeloVersionDTO.modeloDTO", FetchMode.JOIN);
		
		List<CasillaMunicipioDTO> lista=this.casillaMunicipioDao.findByCriteria(criteria);
		if(lista!= null && lista.size()>0){
			return lista.get(0);
		}else{
			return null;
		}
	}


	/**
	 * @return the casillaMunicipioOperacionBO
	 */
	public CasillaMunicipioOperacionBO getCasillaMunicipioOperacionBO() {
		return casillaMunicipioOperacionBO;
	}


	/**
	 * @param casillaMunicipioOperacionBO the casillaMunicipioOperacionBO to set
	 */
	public void setCasillaMunicipioOperacionBO(
			CasillaMunicipioOperacionBO casillaMunicipioOperacionBO) {
		this.casillaMunicipioOperacionBO = casillaMunicipioOperacionBO;
	}

	/**
	 * @return the casillaMunicipioOperacionDao
	 */
	public DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> getCasillaMunicipioOperacionDao() {
		return casillaMunicipioOperacionDao;
	}

	/**
	 * @param casillaMunicipioOperacionDao the casillaMunicipioOperacionDao to set
	 */
	public void setCasillaMunicipioOperacionDao(
			DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> casillaMunicipioOperacionDao) {
		this.casillaMunicipioOperacionDao = casillaMunicipioOperacionDao;
	}

	
	public List<CasillaMunicipioVO> findByCriteriosSeleccionPaginado(DetachedCriteria criteria, int porPagina, int page, String operacion, boolean mantenimientoExperto) throws GadirServiceException {
		List<CasillaMunicipioDTO> casillaMunicipioDTOs = findByCriteria(criteria, (page - 1) * porPagina, porPagina);
		List<CasillaMunicipioVO> casillaMunicipioVOs = new ArrayList<CasillaMunicipioVO>();
		Map<String, String> atributo = new HashMap<String, String>();
		
		
		for (CasillaMunicipioDTO casillaMunicipioDTO : casillaMunicipioDTOs) {
			CasillaMunicipioVO casillaMunicipioVO = new CasillaMunicipioVO();
			// Datos de GA_CASILLA_MUNICIPIO
			casillaMunicipioVO.setCoCasillaMunicipio(casillaMunicipioDTO.getCoCasillaMunicipio());
			casillaMunicipioVO.setOrden(Short.toString(casillaMunicipioDTO.getOrden()));
			Hibernate.initialize(casillaMunicipioDTO.getCasillaModeloDTO());
			casillaMunicipioVO.setCasilla(casillaMunicipioDTO.getCasillaModeloDTO().getCodigoDescripcion());
			if (casillaMunicipioDTO.getBoMantenible()) {
				casillaMunicipioVO.setMantenible("Sí");
			} else {
				casillaMunicipioVO.setMantenible("No");
			}
			
			//Inicializo m con el código del modelo que quiero ver si tiene o no recargoProvincial
			ModeloDTO m = MunicipioConceptoModeloUtil.getModeloDTO(casillaMunicipioDTO.getCasillaModeloDTO().getId().getCoModelo());
			//Si está a true, entonces cargo la columna, sino no
			if (m.getBoRecargoProvincial() != null && m.getBoRecargoProvincial()) {
				casillaMunicipioVO.setBoRecargoProvincialModelo(true);
			} else {
				casillaMunicipioVO.setBoRecargoProvincialModelo(false);
			}
			if (m.getBoNoApremiable() != null && m.getBoNoApremiable()) {
				casillaMunicipioVO.setBoNoApremiableModelo(true);
			} else {
				casillaMunicipioVO.setBoNoApremiableModelo(false);
			}
			//Si en la casilla seleccionada tiene recargoProvincial a true, escribe "Si" en la columna, sino escribe "No"
			if (casillaMunicipioDTO.getBoRecargoProvincial()!=null && casillaMunicipioDTO.getBoRecargoProvincial()) {
				casillaMunicipioVO.setRecargoProvincial("Sí");
			} else {
				casillaMunicipioVO.setRecargoProvincial("No");
			}
			if (casillaMunicipioDTO.getBoNoApremiable()!=null && casillaMunicipioDTO.getBoNoApremiable()) {
				casillaMunicipioVO.setNoApremiable("Sí");
			} else {
				casillaMunicipioVO.setNoApremiable("No");
			}
			
			casillaMunicipioVO.setBorrable(G5114AbstractAction.OPERACION_GENERICA.equals(operacion));
			// Datos de GA_CASILLA_MODELO
			

			CasillaModeloDTO casillaModeloDTO = casillaModeloBO.findById(casillaMunicipioDTO.getCasillaModeloDTO().getId());
			casillaMunicipioVO.setFormato(casillaModeloDTO.getFormato().toString());
			casillaMunicipioVO.setLongitud(Utilidades.bigDecimalToString(casillaModeloDTO.getLongitud()));
			if (casillaModeloDTO.getBoRepeticion()) {
				casillaMunicipioVO.setRepeticionHojas("S");
			} else {
				casillaMunicipioVO.setRepeticionHojas("N");
			}
					
			//casillaMunicipioVO.setLongitud(casillaModeloDTO.getLongitud().toString()); 
			
			// Datos de GA_CASILLA_MUNICIPIO_OPERACION
			Hibernate.initialize(casillaMunicipioDTO.getCasillaMunicipioOperacionDTOs());
			if (casillaMunicipioDTO.getCasillaMunicipioOperacionDTOs().size() > 1) {
				casillaMunicipioVO.setMasProcesos("Sí");
			} else {
				casillaMunicipioVO.setMasProcesos("No");
			}
			CasillaMunicipioOperacionDTO casillaMunicipioOperacionDTO = null;
			for (CasillaMunicipioOperacionDTO operacionDTO : casillaMunicipioDTO.getCasillaMunicipioOperacionDTOs()) {
				if (operacion.equals(operacionDTO.getId().getCoOperacion())) {
					casillaMunicipioOperacionDTO = operacionDTO;
					casillaMunicipioVO.setBorrable(true);
				}
				if (casillaMunicipioOperacionDTO == null && G5114AbstractAction.OPERACION_GENERICA.equals(operacionDTO.getId().getCoOperacion())) {
					casillaMunicipioOperacionDTO = operacionDTO;
				}
			}
			if (casillaMunicipioOperacionDTO != null) {
				Hibernate.initialize(casillaMunicipioOperacionDTO.getFuncionArgumentoDTO());
				if (casillaMunicipioOperacionDTO.getFuncionArgumentoDTO() != null) {
					casillaMunicipioVO.setFuncionArgumento(casillaMunicipioOperacionDTO.getFuncionArgumentoDTO().getCodigoDescripcion());
					casillaMunicipioVO.setFuncion(casillaMunicipioOperacionDTO.getFuncionArgumentoDTO().getFuncionDTO().getCoFuncion());
					casillaMunicipioVO.setArgumento(casillaMunicipioOperacionDTO.getFuncionArgumentoDTO().getId().getCoArgumentoFuncion()+" - "+casillaMunicipioOperacionDTO.getFuncionArgumentoDTO().getNombre());
				}
				if (casillaMunicipioOperacionDTO.getParametro() != null) {
					casillaMunicipioVO.setParametro(casillaMunicipioOperacionDTO.getParametro());
				}
				casillaMunicipioVO.setOperacion(casillaMunicipioOperacionDTO.getId().getCoOperacion());
				
				String key = casillaMunicipioOperacionDTO.getAtributo();
				String value;
				if (atributo.containsKey(key)) {
					value = atributo.get(key);
				} else {
					value = TablaGt.getCodigoDescripcion(TablaGtConstants.TABLA_ATRIBUTOS_CASILLAS, key).getCodigoDescripcion();
					if (value.contains("null")) {
						value = Utilidades.sustituir(value, "null", "?");
					}
					atributo.put(key, value);
				}
				
				casillaMunicipioVO.setAtributo(value);
			}
			casillaMunicipioVOs.add(casillaMunicipioVO);
		}
		return casillaMunicipioVOs;
	}

	public boolean existeOperacion(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, String operacion) throws GadirServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(CasillaMunicipioDTO.class);
		if (Utilidades.isNotEmpty(coConcepto)) {
			criteria.add(Restrictions.or(Restrictions.eq("conceptoDTO.coConcepto", coConcepto), Restrictions.eq("conceptoDTO.coConcepto", ValoresParametrosConfig.VALOR_CONCEPTO_GENERICO)));
		}
		criteria.add(Restrictions.or(Restrictions.eq("casillaModeloDTO.id.coModelo", coModelo), Restrictions.eq("casillaModeloDTO.id.coModelo", ValoresParametrosConfig.VALOR_MODELO_GENERICO)));
		if (coVersion != null) {
			criteria.add(Restrictions.or(Restrictions.eq("casillaModeloDTO.id.coVersion", coVersion), Restrictions.eq("casillaModeloDTO.id.coVersion", ValoresParametrosConfig.VALOR_VERSION_GENERICA)));
		}
		criteria.createAlias("casillaMunicipioOperacionDTOs", "os");

		if (operacion == OPERACION_GENERICA) {
			criteria.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia), Restrictions.eq("municipioDTO.id.coProvincia", ValoresParametrosConfig.VALOR_PROVINCIA_GENERICA)));
			criteria.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipio), Restrictions.eq("municipioDTO.id.coMunicipio", ValoresParametrosConfig.VALOR_MUNICIPIO_GENERICO)));
			criteria.add(Restrictions.eq("os.id.coOperacion", operacion));
			List<CasillaMunicipioDTO> casillaMunicipioDTOs = findByCriteria(criteria, 0, 1);
			return casillaMunicipioDTOs.size() > 0;
		} else {
			criteria.add(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia));
			criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipio));
			criteria.add(Restrictions.eq("os.id.coOperacion", OPERACION_GENERICA));			
			if (countByCriteria(criteria) == 0) {
				criteria = DetachedCriteria.forClass(CasillaMunicipioDTO.class);
				criteria.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia), Restrictions.eq("municipioDTO.id.coProvincia", ValoresParametrosConfig.VALOR_PROVINCIA_GENERICA)));
				criteria.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipio), Restrictions.eq("municipioDTO.id.coMunicipio", ValoresParametrosConfig.VALOR_MUNICIPIO_GENERICO)));
				if (Utilidades.isNotEmpty(coConcepto)) {
					criteria.add(Restrictions.or(Restrictions.eq("conceptoDTO.coConcepto", coConcepto), Restrictions.eq("conceptoDTO.coConcepto", ValoresParametrosConfig.VALOR_CONCEPTO_GENERICO)));
				}
				criteria.add(Restrictions.or(Restrictions.eq("casillaModeloDTO.id.coModelo", coModelo), Restrictions.eq("casillaModeloDTO.id.coModelo", ValoresParametrosConfig.VALOR_MODELO_GENERICO)));
				if (coVersion != null) {
					criteria.add(Restrictions.or(Restrictions.eq("casillaModeloDTO.id.coVersion", coVersion), Restrictions.eq("casillaModeloDTO.id.coVersion", ValoresParametrosConfig.VALOR_VERSION_GENERICA)));
				}
				criteria.createAlias("casillaMunicipioOperacionDTOs", "os");
				criteria.add(Restrictions.eq("os.id.coOperacion", operacion));
				List<CasillaMunicipioDTO> casillaMunicipioDTOs = findByCriteria(criteria, 0, 1);
				return casillaMunicipioDTOs.size() > 0;
			} else {
				criteria = DetachedCriteria.forClass(CasillaMunicipioDTO.class);
				criteria.add(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia));
				criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipio));
				if (Utilidades.isNotEmpty(coConcepto)) {
					criteria.add(Restrictions.or(Restrictions.eq("conceptoDTO.coConcepto", coConcepto), Restrictions.eq("conceptoDTO.coConcepto", ValoresParametrosConfig.VALOR_CONCEPTO_GENERICO)));
				}
				criteria.add(Restrictions.or(Restrictions.eq("casillaModeloDTO.id.coModelo", coModelo), Restrictions.eq("casillaModeloDTO.id.coModelo", ValoresParametrosConfig.VALOR_MODELO_GENERICO)));
				if (coVersion != null) {
					criteria.add(Restrictions.or(Restrictions.eq("casillaModeloDTO.id.coVersion", coVersion), Restrictions.eq("casillaModeloDTO.id.coVersion", ValoresParametrosConfig.VALOR_VERSION_GENERICA)));
				}
				if(!Utilidades.isEmpty(operacion)) {
					criteria.createAlias("casillaMunicipioOperacionDTOs", "os");				
					criteria.add(Restrictions.eq("os.id.coOperacion", operacion));
				}
				List<CasillaMunicipioDTO> casillaMunicipioDTOs = findByCriteria(criteria, 0, 1);
				return casillaMunicipioDTOs.size() > 0;
				
			}
		}
	}

}
