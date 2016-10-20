package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CasillasRelacionadasBO;
import es.dipucadiz.etir.comun.config.ParametrosConfig.ValoresParametrosConfig;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CasillasRelacionadasDTO;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.MunicipioConstants;
import es.dipucadiz.etir.comun.utilidades.ProvinciaConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


@SuppressWarnings("unchecked")
public class CasillasRelacionadasBOImpl extends
		AbstractGenericBOImpl<CasillasRelacionadasDTO, Long> implements
		CasillasRelacionadasBO {

	
	private static final long serialVersionUID = -156372801353480532L;
	
	
	private static final String CAMPO_CO_VER = "casillaModeloDTO.id.coVersion";

	
	private static final String CAMPO_CO_MOD_REL = "coModeloRelacionada";
	
	
	private static final String CAMPO_CO_VER_REL = "coVersionRelacionada";

	
	private static final String CAMPO_CO_MOD = "casillaModeloDTO.id.coModelo";
	
	
	private static final String CAMPO_NU_CAS = "casillaModeloDTO.id.nuCasilla";
	
	
	private static final String CAMPO_CO_MUN = "municipioDTO.id.coMunicipio";
	
	
	private static final String CAMPO_CO_PRO = "municipioDTO.id.coProvincia";

	
	private static final String CAMPO_CO_CON = "conceptoDTO.coConcepto";
	
	
	private static final String CAMPO_CO_CON_REL = "coConceptoRelacionada";
	
	
	private static final String CAMPO_CO_MUN_REL = "coMunicipioRelacionada";
	
	
	private static final String CAMPO_CO_PRO_REL = "coProvinciaRelacionada";
	
	
	private static final String CAMPO_NU_CAS_REL = "nuCasillaRelacionada";

	
	private DAOBase<CasillasRelacionadasDTO, Long> casillasRelDao;
	
	
	public List<CasillasRelacionadasDTO> findCasillaRelacionadaFiltered(CasillasRelacionadasDTO casillaRelacionada)
			throws GadirServiceException {
		List<CasillasRelacionadasDTO> resultado;
		try {
			String codMunicipio = casillaRelacionada.getMunicipioDTO().getId().getCoMunicipio();
			String codProvincia = casillaRelacionada.getMunicipioDTO().getId().getCoProvincia();
			String codConcepto = casillaRelacionada.getConceptoDTO().getCoConcepto();
			String codVersion = casillaRelacionada.getCasillaModeloDTO().getModeloVersionDTO().getId().getCoVersion();
			String codModelo = casillaRelacionada.getCasillaModeloDTO().getModeloVersionDTO().getId().getCoModelo();
			Short nuCasilla = casillaRelacionada.getCasillaModeloDTO().getId().getNuCasilla();
			String codMunicipioRelacionada = casillaRelacionada.getCoMunicipioRelacionada();
			String codProvinciaRelacionada = casillaRelacionada.getCoProvinciaRelacionada();
			String codModeloRelacionada = casillaRelacionada.getCoModeloRelacionada();
			String codVersionRelacionada = casillaRelacionada.getCoVersionRelacionada();
			String codConceptoRelacionada = casillaRelacionada.getCoConceptoRelacionada();
			Short nuCasillaRelacionada = casillaRelacionada.getNuCasillaRelacionada();
			resultado = this.findFiltered(new String[] { CAMPO_CO_MOD,
				CAMPO_CO_VER, CAMPO_CO_MUN, CAMPO_CO_PRO, CAMPO_CO_CON, CAMPO_NU_CAS, CAMPO_CO_MOD_REL, CAMPO_CO_VER_REL, CAMPO_CO_CON_REL, CAMPO_CO_MUN_REL, 
				CAMPO_CO_PRO_REL, CAMPO_NU_CAS_REL}, 
				new Object[] { codModelo, codVersion, codMunicipio, codProvincia, codConcepto, nuCasilla, codModeloRelacionada, codVersionRelacionada, codConceptoRelacionada,
					codMunicipioRelacionada, codProvinciaRelacionada, nuCasillaRelacionada});
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error obtener las casillas relacionadas dada una casilla de salida.", e);
		}
		return resultado;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<CasillasRelacionadasDTO> findCasillasRelacionadasFiltro(
			final String codMunicipio, final String codProvincia, final String codConcepto,
	        final String codModelo, final String codVersion, final String codMunicipioSalida,
	        final String codProvinciaSalida, final String codConceptoSalida,
	        final String codModeloSalida, final String codVersionSalida)
	        throws GadirServiceException {	
		try {
			// Creamos el mapa de parametros.
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("codMunicipio", codMunicipio);
			params.put("codProvincia", codProvincia);
			params.put("codConcepto", codConcepto);
			params.put("codModelo", codModelo);
			params.put("codVersion", codVersion);
			params.put("codMunicipioSalida", codMunicipioSalida);
			params.put("codProvinciaSalida", codProvinciaSalida);
			params.put("codConceptoSalida", codConceptoSalida);
			params.put("codModeloSalida", codModeloSalida);
			params.put("codVersionSalida", codVersionSalida);
			// Iniciamos la lista de resultado.
			List<CasillasRelacionadasDTO> lista = Collections.EMPTY_LIST;
			
			lista = this.findByNamedQuery(
			        QueryName.CASILLAS_RELACIONADAS_FILTRO, params);
			for(CasillasRelacionadasDTO dto : lista){
				if(!Hibernate.isInitialized(dto.getCasillaModeloDTO())){
					Hibernate.initialize(dto.getCasillaModeloDTO());
					if(!Hibernate.isInitialized(dto.getCasillaModeloDTO().getModeloVersionDTO())){
						Hibernate.initialize(dto.getCasillaModeloDTO().getModeloVersionDTO());
						if(!Hibernate.isInitialized(dto.getCasillaModeloDTO().getModeloVersionDTO().getModeloDTO())){	
							Hibernate.initialize(dto.getCasillaModeloDTO().getModeloVersionDTO().getModeloDTO());
						}
					}
				}
			}
				
			return lista;
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error obtener las casillas relacionadas.", e);
		}
	}
	
	
	
	public boolean isRelacionados(String coMunicipioAlta,String coProvinciaAlta,
			String coConceptoAlta,String coModeloAlta,String coVersionAlta,String coModeloDocOrigen,String coVersionDocOrigen) throws GadirServiceException{

	try {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("coMunicipioAlta", coMunicipioAlta);
		params.put("coProvinciaAlta", coProvinciaAlta);
		params.put("coConceptoAlta", coConceptoAlta);
		params.put("coModeloAlta", coModeloAlta);
		params.put("coVersionAlta", coVersionAlta);
		params.put("coModeloDocOrigen", coModeloDocOrigen);
		params.put("coVersionDocOrigen", coVersionDocOrigen);

		List<CasillasRelacionadasDTO> result = (List<CasillasRelacionadasDTO>) this.getDao()
				.findByNamedQuery(QueryName.EXISTE_RELACION, params);

		if (result.size() > 0) {
			return true;
		} else {
			final Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("coMunicipioAlta", ValoresParametrosConfig.VALOR_MUNICIPIO_GENERICO);
			params2.put("coProvinciaAlta", ValoresParametrosConfig.VALOR_PROVINCIA_GENERICA);
			params2.put("coConceptoAlta", coConceptoAlta);
			params2.put("coModeloAlta", coModeloAlta);
			params2.put("coVersionAlta", coVersionAlta);
			params2.put("coModeloDocOrigen", coModeloDocOrigen);
			params2.put("coVersionDocOrigen", coVersionDocOrigen);
			List<CasillasRelacionadasDTO> result2 = (List<CasillasRelacionadasDTO>) this.getDao()
			.findByNamedQuery(QueryName.EXISTE_RELACION, params2);
			if (result2.size() > 0) {
				return true;
			}else {
				return false;
			}
		}

	} catch (final Exception e) {
		log.error(e.getCause(), e);
		throw new GadirServiceException(
				"Error al obtener el listado de casillas relacionadas.", e);
	}
}

	public CasillasRelacionadasDTO findById(Long id)
			throws GadirServiceException {
		CasillasRelacionadasDTO sol = super.findById(id);
		if(sol != null){
			Hibernate.initialize(sol.getCasillaModeloDTO());
			Hibernate.initialize(sol.getMunicipioDTO());
		}
		return sol;
	}


	public CasillasRelacionadasDTO findByRowid(String rowid) {
		CasillasRelacionadasDTO sol = super.findByRowid(rowid);
		if(sol != null){
			if(!Hibernate.isInitialized(sol.getCasillaModeloDTO())){
				Hibernate.initialize(sol.getCasillaModeloDTO());
				if(!Hibernate.isInitialized(sol.getCasillaModeloDTO().getModeloVersionDTO())){
					Hibernate.initialize(sol.getCasillaModeloDTO().getModeloVersionDTO());
					if(!Hibernate.isInitialized(sol.getCasillaModeloDTO().getModeloVersionDTO().getModeloDTO())){	
						Hibernate.initialize(sol.getCasillaModeloDTO().getModeloVersionDTO().getModeloDTO());
					}
				}
			}
			if(!Hibernate.isInitialized(sol.getConceptoDTO())){
				Hibernate.initialize(sol.getConceptoDTO());
			}
		}
		return sol;
	}

	public DAOBase<CasillasRelacionadasDTO, Long> getDao() {
		return this.getCasillasRelDao();
	}

	
	public DAOBase<CasillasRelacionadasDTO, Long> getCasillasRelDao() {
		return casillasRelDao;
	}

	
	public void setCasillasRelDao(
			DAOBase<CasillasRelacionadasDTO, Long> casillasRelDao) {
		this.casillasRelDao = casillasRelDao;
	}
	
	public void auditorias(CasillasRelacionadasDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

	public List<ConceptoDTO> findConceptosOrigen(
			String coProvinciaDestino, String coMunicipioDestino,
			String coConceptoDestino, String coModeloDestino,
			String coVersionDestino, String coProvinciaOrigen,
			String coMunicipioOrigen) throws GadirServiceException {
		DetachedCriteria criterio = DetachedCriteria.forClass(CasillasRelacionadasDTO.class);
		criterio.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coProvincia", coProvinciaOrigen), Restrictions.eq("municipioDTO.id.coProvincia", ProvinciaConstants.CO_PROVINCIA_GENERICO)));
		criterio.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipioOrigen), Restrictions.eq("municipioDTO.id.coMunicipio", MunicipioConstants.CO_MUNICIPIO_GENERICO)));
		criterio.add(Restrictions.or(Restrictions.eq("coProvinciaRelacionada", coProvinciaDestino), Restrictions.eq("coProvinciaRelacionada", ProvinciaConstants.CO_PROVINCIA_GENERICO)));
		criterio.add(Restrictions.or(Restrictions.eq("coMunicipioRelacionada", coMunicipioDestino), Restrictions.eq("coMunicipioRelacionada", MunicipioConstants.CO_MUNICIPIO_GENERICO)));
		criterio.add(Restrictions.eq("coConceptoRelacionada", coConceptoDestino));
		criterio.add(Restrictions.eq("coModeloRelacionada", coModeloDestino));
		criterio.add(Restrictions.eq("coVersionRelacionada", coVersionDestino));
		criterio.addOrder(Order.asc("conceptoDTO.coConcepto"));
		List<CasillasRelacionadasDTO> casillasRelacionadasDTOs = findByCriteria(criterio);
		
		List<ConceptoDTO> conceptoDTOs = new ArrayList<ConceptoDTO>();
		for (CasillasRelacionadasDTO casillasRelacionadasDTO : casillasRelacionadasDTOs) {
			Hibernate.initialize(casillasRelacionadasDTO.getConceptoDTO());
			conceptoDTOs.add(casillasRelacionadasDTO.getConceptoDTO());
		}
		HashSet<ConceptoDTO> hashSet = new HashSet<ConceptoDTO>(conceptoDTOs);
		
		return new ArrayList<ConceptoDTO>(hashSet);
	}

	public List<ModeloDTO> findModelosOrigen(
			String coProvinciaDestino, String coMunicipioDestino,
			String coConceptoDestino, String coModeloDestino,
			String coVersionDestino, String coProvinciaOrigen,
			String coMunicipioOrigen, String coConceptoOrigen) throws GadirServiceException {
		DetachedCriteria criterio = DetachedCriteria.forClass(CasillasRelacionadasDTO.class);
		criterio.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coProvincia", coProvinciaOrigen), Restrictions.eq("municipioDTO.id.coProvincia", ProvinciaConstants.CO_PROVINCIA_GENERICO)));
		criterio.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipioOrigen), Restrictions.eq("municipioDTO.id.coMunicipio", MunicipioConstants.CO_MUNICIPIO_GENERICO)));
		criterio.add(Restrictions.eq("conceptoDTO.coConcepto", coConceptoOrigen));
		criterio.add(Restrictions.or(Restrictions.eq("coProvinciaRelacionada", coProvinciaDestino), Restrictions.eq("coProvinciaRelacionada", ProvinciaConstants.CO_PROVINCIA_GENERICO)));
		criterio.add(Restrictions.or(Restrictions.eq("coMunicipioRelacionada", coMunicipioDestino), Restrictions.eq("coMunicipioRelacionada", MunicipioConstants.CO_MUNICIPIO_GENERICO)));
		criterio.add(Restrictions.eq("coConceptoRelacionada", coConceptoDestino));
		criterio.add(Restrictions.eq("coModeloRelacionada", coModeloDestino));
		criterio.add(Restrictions.eq("coVersionRelacionada", coVersionDestino));
		criterio.addOrder(Order.asc("casillaModeloDTO.id.coModelo"));
		List<CasillasRelacionadasDTO> casillasRelacionadasDTOs = findByCriteria(criterio);
		
		List<ModeloDTO> modeloDTOs = new ArrayList<ModeloDTO>();
		for (CasillasRelacionadasDTO casillasRelacionadasDTO : casillasRelacionadasDTOs) {
			Hibernate.initialize(casillasRelacionadasDTO.getCasillaModeloDTO());
			Hibernate.initialize(casillasRelacionadasDTO.getCasillaModeloDTO().getModeloVersionDTO());
			Hibernate.initialize(casillasRelacionadasDTO.getCasillaModeloDTO().getModeloVersionDTO().getModeloDTO());
			modeloDTOs.add(casillasRelacionadasDTO.getCasillaModeloDTO().getModeloVersionDTO().getModeloDTO());
		}
		HashSet<ModeloDTO> hashSet = new HashSet<ModeloDTO>(modeloDTOs);
		
		return new ArrayList<ModeloDTO>(hashSet);
	}

	public List<ModeloVersionDTO> findVersionesOrigen(
			String coProvinciaDestino, String coMunicipioDestino,
			String coConceptoDestino, String coModeloDestino,
			String coVersionDestino, String coProvinciaOrigen,
			String coMunicipioOrigen, String coConceptoOrigen, String coModeloOrigen) throws GadirServiceException {
		DetachedCriteria criterio = DetachedCriteria.forClass(CasillasRelacionadasDTO.class);
		criterio.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coProvincia", coProvinciaOrigen), Restrictions.eq("municipioDTO.id.coProvincia", ProvinciaConstants.CO_PROVINCIA_GENERICO)));
		criterio.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipioOrigen), Restrictions.eq("municipioDTO.id.coMunicipio", MunicipioConstants.CO_MUNICIPIO_GENERICO)));
		criterio.add(Restrictions.eq("conceptoDTO.coConcepto", coConceptoOrigen));
		criterio.add(Restrictions.eq("casillaModeloDTO.id.coModelo", coModeloOrigen));
		criterio.add(Restrictions.or(Restrictions.eq("coProvinciaRelacionada", coProvinciaDestino), Restrictions.eq("coProvinciaRelacionada", ProvinciaConstants.CO_PROVINCIA_GENERICO)));
		criterio.add(Restrictions.or(Restrictions.eq("coMunicipioRelacionada", coMunicipioDestino), Restrictions.eq("coMunicipioRelacionada", MunicipioConstants.CO_MUNICIPIO_GENERICO)));
		criterio.add(Restrictions.eq("coConceptoRelacionada", coConceptoDestino));
		criterio.add(Restrictions.eq("coModeloRelacionada", coModeloDestino));
		criterio.add(Restrictions.eq("coVersionRelacionada", coVersionDestino));
		criterio.addOrder(Order.asc("casillaModeloDTO.id.coVersion"));
		List<CasillasRelacionadasDTO> casillasRelacionadasDTOs = findByCriteria(criterio);
		
		List<ModeloVersionDTO> modeloVersionDTOs = new ArrayList<ModeloVersionDTO>();
		for (CasillasRelacionadasDTO casillasRelacionadasDTO : casillasRelacionadasDTOs) {
			Hibernate.initialize(casillasRelacionadasDTO.getCasillaModeloDTO());
			Hibernate.initialize(casillasRelacionadasDTO.getCasillaModeloDTO().getModeloVersionDTO());
			modeloVersionDTOs.add(casillasRelacionadasDTO.getCasillaModeloDTO().getModeloVersionDTO());
		}
		HashSet<ModeloVersionDTO> hashSet = new HashSet<ModeloVersionDTO>(modeloVersionDTOs);
		
		return new ArrayList<ModeloVersionDTO>(hashSet);
	}

	public List<CasillasRelacionadasDTO> findRelacionesDestino(String coProvinciaOrigen, String coMunicipioOrigen, String coConceptoOrigen, String coModeloOrigen, String coVersionOrigen, String coProvinciaDestino, String coMunicipioDestino) throws GadirServiceException {
		DetachedCriteria criterio = DetachedCriteria.forClass(CasillasRelacionadasDTO.class);
		criterio.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coProvincia", coProvinciaOrigen), Restrictions.eq("municipioDTO.id.coProvincia", ProvinciaConstants.CO_PROVINCIA_GENERICO)));
		criterio.add(Restrictions.or(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipioOrigen), Restrictions.eq("municipioDTO.id.coMunicipio", MunicipioConstants.CO_MUNICIPIO_GENERICO)));
		criterio.add(Restrictions.eq("conceptoDTO.coConcepto", coConceptoOrigen));
		criterio.add(Restrictions.eq("casillaModeloDTO.id.coModelo", coModeloOrigen));
		criterio.add(Restrictions.eq("casillaModeloDTO.id.coVersion", coVersionOrigen));
		criterio.add(Restrictions.or(Restrictions.eq("coProvinciaRelacionada", coProvinciaDestino), Restrictions.eq("coProvinciaRelacionada", ProvinciaConstants.CO_PROVINCIA_GENERICO)));
		criterio.add(Restrictions.or(Restrictions.eq("coMunicipioRelacionada", coMunicipioDestino), Restrictions.eq("coMunicipioRelacionada", MunicipioConstants.CO_MUNICIPIO_GENERICO)));
		criterio.addOrder(Order.asc("casillaModeloDTO.id.coVersion"));
		return findByCriteria(criterio);
	}

}
