package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.bo.CargoSubcargoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CargoSubcargoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoLiquidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.vo.SubCargoVO;

public class CargoSubcargoBOImpl extends AbstractGenericBOImpl<CargoSubcargoDTO, Long> implements CargoSubcargoBO {

	/**
	 * Atributo que almacena el serialVersionUID
	 */
	private static final long serialVersionUID = 8458398059250010847L;

	private static final Log LOG = LogFactory.getLog(CargoBOImpl.class);
	
	private DAOBase<CargoSubcargoDTO, Long> dao;

	public DAOBase<CargoSubcargoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<CargoSubcargoDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

		
	
	/**
	 * {@inheritDoc}
	 */
	public List<CargoSubcargoDTO> findCargosByMunicipioModeloEjercicioPeriodo(final String coMunicipio, final String coProvincia, final String coModelo, final String ejercicio, final String periodo)
		throws GadirServiceException{
		String queryString;
		queryString = 	"select c " +
						"from CargoSubcargoDTO c " +
						"left join fetch c.cargoDTO " +
						"left join fetch c.cargoDTO.modeloDTO " +
						"left join fetch c.conceptoDTO " +
						"left join fetch c.cargoDTO.unidadAdministrativaDTO " +
						"where c.cargoDTO.municipioDTO.id.coMunicipio = '"+ coMunicipio+"' "+
						"and c.cargoDTO.municipioDTO.id.coProvincia = '"+ coProvincia+"' "+
//						"and c.cargoDTO.anoCargo = '9999' "+
						"and c.situacion is null ";
		try {
			if (Utilidades.isNotEmpty(coModelo)) {
				queryString = queryString + "and c.cargoDTO.modeloDTO.coModelo = '"+coModelo+"' ";
			}
			if (Utilidades.isNotEmpty(ejercicio)) {
				queryString = queryString + "and c.cargoDTO.ejercicio = '"+ejercicio+"' ";
			}
			if (Utilidades.isNotEmpty(periodo)) {
				queryString = queryString + "and c.cargoDTO.periodo = '"+periodo+"' ";
			}
			List<CargoSubcargoDTO> lista = (List<CargoSubcargoDTO>) this.getDao().findByQuery(queryString);
			return lista;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el listado de cargos.", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<SubCargoVO> findSubCargosVOByCargo(final Long id) throws GadirServiceException{
		try {
			List<SubCargoVO> lista = new ArrayList<SubCargoVO>();
			List<CargoSubcargoDTO> listaResultado = this.findFiltered("cargoDTO.coCargo", id);
			if(listaResultado!= null && listaResultado.size()>0){
				for(int i=0;i<listaResultado.size();i++){
					CargoSubcargoDTO resultado=listaResultado.get(i);
					if (resultado != null){
						Hibernate.initialize(resultado.getConceptoDTO());
						Hibernate.initialize(resultado.getCargoDTO());
						Hibernate.initialize(resultado.getDocumentoLiquidacionDTOs());
						if (resultado.getCargoDTO() != null){
							Hibernate.initialize(resultado.getCargoDTO().getMunicipioDTO());
							Hibernate.initialize(resultado.getCargoDTO().getUnidadAdministrativaDTO());
						}
						
					}
					SubCargoVO subcargoVO = new SubCargoVO(resultado);
					lista.add(subcargoVO);
				}
			}
			return lista;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el listado de cargos.", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<CargoSubcargoDTO> findSubCargosByCargo(final Long id) throws GadirServiceException{
		try {
			List<CargoSubcargoDTO> listaResultado = this.findFiltered("cargoDTO.coCargo", id);
			if(listaResultado!= null && listaResultado.size()>0){
				for(int i=0;i<listaResultado.size();i++){
					CargoSubcargoDTO resultado=listaResultado.get(i);
					if (resultado != null){
						Hibernate.initialize(resultado.getConceptoDTO());
						Hibernate.initialize(resultado.getCargoDTO());
						Hibernate.initialize(resultado.getDocumentoLiquidacionDTOs());
						if (resultado.getCargoDTO() != null){
							Hibernate.initialize(resultado.getCargoDTO().getMunicipioDTO());
							Hibernate.initialize(resultado.getCargoDTO().getUnidadAdministrativaDTO());
						}
					}
				}
			}
			return listaResultado;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el listado de cargos.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Integer findNumeroSubCargos(final Long coCargo) throws GadirServiceException{
		List<BigDecimal> listaResultado = null;
		Integer resultado = null;
		try{
			final Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("coCargo", coCargo);
			listaResultado = (List<BigDecimal>)this.dao.ejecutaNamedQuerySelect(
			        QueryName.FIND_NUMERO_SUBCARGOS, param);
			for (BigDecimal objeto : listaResultado) {
				resultado = new Integer(objeto.toString());
			}
			resultado = resultado+1;
		}catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el numero de subcargos.", e);
		}
	return resultado;
	}	

	
	/**
	 * {@inheritDoc}
	 */
	public CargoSubcargoDTO findByCodigo(final Long id) throws GadirServiceException{
		CargoSubcargoDTO subcargo = new CargoSubcargoDTO();
		try {
			subcargo = this.getDao().findById(id);
			Hibernate.initialize(subcargo.getCargoDTO());
			Object[] array = subcargo.getCargoDTO().getCargoSubcargoDTOs().toArray();
			for(int i=0;i<array.length;i++){
				Hibernate.initialize(array[i]);
			}
			array = subcargo.getDocumentoLiquidacionDTOs().toArray();
			for(int i=0;i<array.length;i++){
				Hibernate.initialize(array[i]);
				Hibernate.initialize(((DocumentoLiquidacionDTO)array[i]).getDocumentoDTO());
				Hibernate.initialize(((DocumentoLiquidacionDTO)array[i]).getDocumentoDTO().getSituacionDTO());
			}
			Hibernate.initialize(subcargo.getCargoDTO().getModeloDTO());
			Hibernate.initialize(subcargo.getConceptoDTO());
		}catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el Subcargo.", e);
		}
		return subcargo;
	}	
	

	/**
	 * {@inheritDoc}
	 */
	public void auditorias(CargoSubcargoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}	
	
	public List<CargoSubcargoDTO> findByCriterioBusqueda(DetachedCriteria criterio) throws GadirServiceException {
		return getDao().findByCriteria(criterio);
	}

	public CargoSubcargoDTO findByIdInitialized(Long coCargoSubcargo) throws GadirServiceException {
		CargoSubcargoDTO result = findById(coCargoSubcargo);
		Hibernate.initialize(result.getCargoDTO());
		Hibernate.initialize(result.getConceptoDTO());
		Hibernate.initialize(result.getCargoDTO().getMunicipioDTO());
		Hibernate.initialize(result.getCargoDTO().getModeloDTO());
		return result;
	}
}
	
	