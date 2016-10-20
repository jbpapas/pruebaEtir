package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.CasillaModeloBO;
import es.dipucadiz.etir.comun.bo.CasillasLigadasBO;
import es.dipucadiz.etir.comun.bo.ModeloBO;
import es.dipucadiz.etir.comun.bo.ModeloVersionBO;
import es.dipucadiz.etir.comun.config.ParametrosConfig.ValoresParametrosConfig;
import es.dipucadiz.etir.comun.constants.TipoModeloConstants;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTO;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTOId;
import es.dipucadiz.etir.comun.dto.CasillasLigadasDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


@SuppressWarnings("unchecked")
public class ModeloVersionBOImpl extends
        AbstractGenericBOImpl<ModeloVersionDTO, ModeloVersionDTOId> implements
        ModeloVersionBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 4302866746873369372L;

	/**
	 * Atributo que almacena el DAO asociado a {@link VersionModeloDTO}.
	 */
	private DAOBase<ModeloVersionDTO, ModeloVersionDTOId> dao;

	/**
	 * Atributo que almacena el BO asociado a {@link VersionModeloDTO}.
	 */
	private ModeloBO modeloBO;

	/**
	 * Atributo que almacena el BO asociado a {@link CasillaModeloDTO}.
	 */
	private CasillaModeloBO casillaModeloBO;
	
	/**
	 * Atributo que almacena el BO asociado a {@link CasillasLigadasDTO}.
	 */
	private CasillasLigadasBO casillasLigadasBO;

	/**
	 * {@inheritDoc}
	 */
	public List<ModeloVersionDTO> findVersionesByModelo(final String coModelo)
	        throws GadirServiceException {
		List<ModeloVersionDTO> lista = null;
		if (Utilidades.isEmpty(coModelo)) {
			lista = Collections.EMPTY_LIST;
		} else {
			try {
				lista = this.dao.findFiltered("id.coModelo", coModelo,
				        "id.coVersion", DAOConstant.DESC_ORDER);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener las versiones del modelo. Modelo: "
				                + coModelo, e);
			}
		}
		return lista;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ModeloVersionDTO> findVersiones(final String codModelo,
	        final String codVersion) throws GadirServiceException {
		List<ModeloVersionDTO> resultado;
		try {
			// Comprobamos que filtros vienen informados.
			if (Utilidades.isNotEmpty(codModelo)
			        && Utilidades.isNotEmpty(codVersion)) {
				resultado = this.findByNamedQuery(
				        "Version.findVersionesPorModeloYCodigoIguales",
				        new String[] { "codModelo", "codVersion" },
				        new Object[] { codModelo, codVersion });
			} else if (Utilidades.isNotEmpty(codModelo)) {
				resultado = this.findByNamedQuery(
				        QueryName.VERSIONES_BY_MODELO,
				        new String[] { "codModelo" },
				        new Object[] { codModelo });
			} else if (Utilidades.isNotEmpty(codVersion)) {
				resultado = this.findByNamedQuery(
				        QueryName.VERSIONES_BY_CODIGO_DESDE,
				        new String[] { "codVersion" },
				        new Object[] { codVersion });
			} else {
				resultado = Collections.EMPTY_LIST;
			}
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener las versiones.", e);
		}
		return resultado;
	}

	

	/**
	 * Devuelve las versiones existentes en GA_DOCUMENTOS para el modelo
	 * seleccionado.
	 */
	public List<ModeloVersionDTO> findAllVersionByModelo(final String coModelo,
	        final String estado) throws GadirServiceException {
		List<ModeloVersionDTO> versiones = Collections.EMPTY_LIST;

		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("coModelo", coModelo);
			params.put("estado", estado);
			versiones = this.getDao().findByNamedQuery(
			        QueryName.DOCUMENTOS_OBTENER_VERSIONES_DEL_MODELO, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el listado de versiones del modelo seleccionado:",
			        e);
		}
		return versiones;
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveConNuevoModelo(final ModeloVersionDTO modeloVersion)
	        throws GadirServiceException {
		try {
			this.getModeloBO().saveOnly(modeloVersion.getModeloDTO());
			this.save(modeloVersion);

			this.saveCasillasIniciales(modeloVersion);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error dando de alta un modelo y una nueva versión.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveVersionAndModelo(final ModeloVersionDTO modeloVersion)
	        throws GadirServiceException {
		try {
			this.getModeloBO().save(modeloVersion.getModeloDTO());
			this.save(modeloVersion);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error actualizando un modelo y una versión.", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Boolean eliminarModeloVersion(final ModeloVersionDTO modeloVersion)
	        throws GadirServiceException {
		
			try{
				
				List<CasillasLigadasDTO> casillasLigadas = this.getCasillasLigadasBO().findCasillasLigadasByModeloVersion(modeloVersion.getId());
				for (CasillasLigadasDTO casillaLigada : casillasLigadas) {
					this.getCasillasLigadasBO().delete(casillaLigada.getCoCasillasLigadas());
				}
				for (CasillaModeloDTO casillaModelo : modeloVersion.getCasillaModeloDTOs()) {
					this.getCasillaModeloBO().delete(casillaModelo.getId());
				}
				this.delete(modeloVersion.getId());
				
				return true;
		
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
			        "Ocurrio un error actualizando un modelo y una versión.", e);
		}
	}
	
	public List<Integer> comprobarEliminarModeloVersion(final ModeloVersionDTO modeloVersion) throws GadirServiceException {
		
		boolean tieneRelaciones = false;
		
		try {
			
			List<Integer> res = new ArrayList<Integer>();
			//Comprobamos que el modelo/version no tenga relaciones que no debamos eliminar.
			
			List<Integer> resModeloVersion = this.tieneRelacionesNoEliminables(modeloVersion.getId());
			
			if (resModeloVersion.get(0) == 0) { //NO TIENE RELACIONES DE VERSION 
				
				//Para cada casilla modelo asociada a el modelo/version a eliminar,
				//comprobamos que no tenga relaciones que no debamos eliminar.
				
				for (CasillaModeloDTO casillaModelo : modeloVersion.getCasillaModeloDTOs()) {
					res = this.getCasillaModeloBO().tieneRelacionesNoEliminables(casillaModelo.getId());
					if(res.get(0) != 0) {
						tieneRelaciones = true;
					}
				}
				//Si no tiene relaciones no eliminables, procedemos a eliminar la casilla modelo.
				if(!tieneRelaciones) { //NO TIENE RELACIONES DE CASILLA
					res.add(0);
					return res;
				} else { //TIENE RELACIONES DE CASILLA
					res.add(-1);
					return res;
				}
			} else {
				return resModeloVersion; //DEVOLVEMOS LAS RELACIONES DE VERSIÓN
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error actualizando un modelo y una versión.", e);
		}
	}	
	
	
	/**
	 * {@inheritDoc}
	 */
	public List<Integer> tieneRelacionesNoEliminables(final ModeloVersionDTOId id)
	        throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("codVersion", id.getCoVersion());
		params.put("codModelo", id.getCoModelo());
		try {
			
			List<Integer> errores = new ArrayList<Integer>();
			
			List<Object> resultado = (List<Object>) this.getDao().ejecutaNamedQuerySelect(
			        "ModeloVersionDTO.tieneRelacionesNoEliminables", params);
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
			        "Ocurrio un error al comprobar las relaciones del ModeloVersion dado.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOnlyConCasillas(final ModeloVersionDTO transientObject)
	        throws GadirServiceException {
		try {
			this.saveOnly(transientObject);
			this.saveCasillasIniciales(transientObject);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al crear la nueva version con las casillas iniciales.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveDuplicarVersion(final ModeloVersionDTOId idOrigen,
	        final ModeloVersionDTOId idDestino) throws GadirServiceException {
		try {

			// Duplicamos el modelo
			ModeloDTO modNuevo = this.modeloBO.findById(idDestino
			        .getCoModelo());
			if (Utilidades.isNull(modNuevo)) {
				final ModeloDTO modOrigen = this.modeloBO.findById(idOrigen
				        .getCoModelo());
				modNuevo = new ModeloDTO();
				modNuevo.setCoModelo(idDestino.getCoModelo());
				modNuevo.setBoAyudaCasilla(modOrigen.getBoAyudaCasilla());
				modNuevo.setBoValidarConvenio(modOrigen.getBoValidarConvenio());
				modNuevo.setDescripcion(modOrigen.getDescripcion());
				modNuevo.setModeloDTO(modOrigen.getModeloDTO());
				modNuevo.setNombre(modOrigen.getNombre());
				modNuevo.setSubtipo(modOrigen.getSubtipo());
				modNuevo.setTipo(modOrigen.getTipo());
				modNuevo.setBoNoApremiable(modOrigen.getBoNoApremiable());
				this.modeloBO.saveOnly(modNuevo);
			}

			// Duplicamos version
			final ModeloVersionDTO verOrigen = this.dao.findById(idOrigen);
			final ModeloVersionDTO verNueva = new ModeloVersionDTO();
			verNueva.setFxAlta(verOrigen.getFxAlta());
			verNueva.setId(idDestino);
			this.saveOnly(verNueva);

			final List<CasillaModeloDTO> casillasOrigen = this.casillaModeloBO
			        .findFiltered("modeloVersionDTO.id", idOrigen);

			if (casillasOrigen != null && !casillasOrigen.isEmpty()) {

				CasillaModeloDTO nueva = null;
				CasillaModeloDTOId idNueva = null;
				for (final CasillaModeloDTO casilla : casillasOrigen) {
					idNueva = new CasillaModeloDTOId();
					idNueva.setNuCasilla(casilla.getId().getNuCasilla());
					idNueva.setCoModelo(idDestino.getCoModelo());
					idNueva.setCoVersion(idDestino.getCoVersion());

					nueva = new CasillaModeloDTO();
					nueva.setId(idNueva);
					nueva.setBoDobleDigitacion(casilla.getBoDobleDigitacion());
					nueva.setBoRepeticion(casilla.getBoRepeticion());
					nueva.setDescripcion(casilla.getDescripcion());
					nueva.setFormato(casilla.getFormato());
					nueva.setLongitud(casilla.getLongitud());
					nueva.setNombre(casilla.getNombre());

					this.casillaModeloBO.saveOnly(nueva);
				}
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al duplicar el modelo-version.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ModeloVersionDTO findByIdLazy(final ModeloVersionDTOId id)
	        throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("codVersion", id.getCoVersion());
		params.put("codModelo", id.getCoModelo());
		try {
			return (ModeloVersionDTO) this.getDao().findByNamedQueryUniqueResult(
			        QueryName.VERSION_BY_MODELO_Y_VERSION, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al cargar el modelo-version segun su id.",
			        e);
		}
	}

	/**
	 * Método que se encarga de crear las casillas definidas por defecto para un
	 * modelo-version.
	 * 
	 * @param version
	 *            Version a la que se asociaran las casillas.
	 */
	private void saveCasillasIniciales(final ModeloVersionDTO version) 
		throws GadirServiceException {

		CasillaModeloDTO casilla = null;
		CasillaModeloDTOId idCasilla = null;

		// Obtenemos el bundle con los datos de casillas iniciales.
		final ResourceBundle bundleCasillas = ResourceBundle
		        .getBundle("comun/properties/casillasIniciales");

		// Propiedades definidas de cada casilla.
		final String propNumero = "casilla.inicial.numero.";
		final String propNombre = "casilla.inicial.nombre.";
		final String propDesc = "casilla.inicial.descripcion.";
		final String propFormato = "casilla.inicial.formato.";
		final String propLongitud = "casilla.inicial.longitud.";
		final String propDobleDig = "casilla.inicial.dobleDig.";
		final String propRepHojas = "casilla.inicial.repHojas.";

		// Crear las 45 casillas iniciales
		try {
			for (int i = 1; i <= ValoresParametrosConfig.VALOR_NUMERO_CASILLAS_INICIALES; i++) {

				casilla = new CasillaModeloDTO();
				idCasilla = new CasillaModeloDTOId(version.getId().getCoModelo(),
				        version.getId().getCoVersion(), Short
				                .valueOf(bundleCasillas.getString(propNumero + i)));

				casilla.setId(idCasilla);

				casilla.setBoDobleDigitacion("1".equals(bundleCasillas
				        .getString(propDobleDig + i)));

				casilla.setBoRepeticion("1".equals(bundleCasillas
				        .getString(propRepHojas + i)));

				casilla.setDescripcion(bundleCasillas.getString(propDesc + i));

				casilla.setNombre(bundleCasillas.getString(propNombre + i));
				casilla.setFormato(bundleCasillas.getString(propFormato + i));

				final String strLong = bundleCasillas.getString(propLongitud + i);
				if (Utilidades.isEmpty(strLong)) {
					casilla.setLongitud(null);
				} else {
					
					casilla.setLongitud(new BigDecimal(strLong));
				}

				this.getCasillaModeloBO().save(casilla);
			}
			
			// Casillas automaticas recibos/liquidaciones
			String tipo;
			String subtipo;
			if (Utilidades.isEmpty(version.getModeloDTO().getTipo()) || Utilidades.isEmpty(version.getModeloDTO().getSubtipo())) {
				ModeloDTO modeloDTO = modeloBO.findById(version.getId().getCoModelo());
				tipo = modeloDTO.getTipo();
				subtipo = modeloDTO.getSubtipo();
			} else {
				tipo = version.getModeloDTO().getTipo();
				subtipo = version.getModeloDTO().getSubtipo();
			}
			if (TipoModeloConstants.TIPO_LIQUIDACION.equals(tipo)) {
				short[] casillasArray;
				String coModeloOrigen;
				String coVersionOrigen;
				if (TipoModeloConstants.SUBTIPO_RECIBO.equals(subtipo)) {
					casillasArray = new short[]{79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106};
					coModeloOrigen = "201";
					coVersionOrigen = "1";
				} else  {
					casillasArray = new short[]{48,49,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100};
					coModeloOrigen = "403";
					coVersionOrigen = "1";
				}
				for (short casillaDefecto : casillasArray) {
					CasillaModeloDTO casillaModeloDTO = casillaModeloBO.findById(new CasillaModeloDTOId(coModeloOrigen, coVersionOrigen, casillaDefecto));
					if (casillaModeloDTO != null) {
						CasillaModeloDTO nuevaCasillaDTO = new CasillaModeloDTO();
						nuevaCasillaDTO.setBoDobleDigitacion(casillaModeloDTO.getBoDobleDigitacion());
						nuevaCasillaDTO.setBoRepeticion(casillaModeloDTO.getBoRepeticion());
						nuevaCasillaDTO.setCoUsuarioActualizacion(version.getCoUsuarioActualizacion());
						nuevaCasillaDTO.setDescripcion(casillaModeloDTO.getDescripcion());
						nuevaCasillaDTO.setFhActualizacion(version.getFhActualizacion());
						nuevaCasillaDTO.setFormato(casillaModeloDTO.getFormato());
						nuevaCasillaDTO.setLongitud(casillaModeloDTO.getLongitud());
						nuevaCasillaDTO.setNombre(casillaModeloDTO.getNombre());
						CasillaModeloDTOId id = new CasillaModeloDTOId();
						id.setCoModelo(version.getId().getCoModelo());
						id.setCoVersion(version.getId().getCoVersion());
						id.setNuCasilla(casillaDefecto);
						nuevaCasillaDTO.setId(id);
						casillaModeloBO.save(nuevaCasillaDTO);
					}
				}
			}
		}catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al crear las casillas iniciales.",
			        e);
		}
		
	}

	// GETTERS AND SETTERS

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<ModeloVersionDTO, ModeloVersionDTOId> getDao() {
		return this.dao;
	}

	/**
	 * Método que establece el atributo dao.
	 * 
	 * @param versionDao
	 *            El dao.
	 */
	public void setVersionDao(
	        final DAOBase<ModeloVersionDTO, ModeloVersionDTOId> dao) {
		this.dao = dao;
	}

	/**
	 * Método que establece el atributo dao.
	 * 
	 * @param dao
	 *            El dao.
	 */
	public void setDao(final DAOBase<ModeloVersionDTO, ModeloVersionDTOId> dao) {
		this.dao = dao;
	}

	public ModeloVersionDTO findInitializedById(ModeloVersionDTOId id) throws GadirServiceException {
		ModeloVersionDTO modeloVersionDTO = dao.findById(id);
		Hibernate.initialize(modeloVersionDTO.getModeloDTO());
		Hibernate.initialize(modeloVersionDTO.getCasillaModeloDTOs());
		for (CasillaModeloDTO casillaModeloDTO : modeloVersionDTO.getCasillaModeloDTOs()) {
			Hibernate.initialize(casillaModeloDTO.getCasillasLigadasDTOsForCasmodCasillasLigadas());
			Hibernate.initialize(casillaModeloDTO.getCasillasLigadasDTOsForCasmodCasligCasillasLigadas());
		}
		return modeloVersionDTO;
	}

	/**
	 * @return the modeloBO
	 */
	public ModeloBO getModeloBO() {
		return modeloBO;
	}

	/**
	 * @param modeloBO the modeloBO to set
	 */
	public void setModeloBO(ModeloBO modeloBO) {
		this.modeloBO = modeloBO;
	}

	/**
	 * @return the casillaModeloBO
	 */
	public CasillaModeloBO getCasillaModeloBO() {
		return casillaModeloBO;
	}

	/**
	 * @param casillaModeloBO the casillaModeloBO to set
	 */
	public void setCasillaModeloBO(CasillaModeloBO casillaModeloBO) {
		this.casillaModeloBO = casillaModeloBO;
	}
	
	public void auditorias(ModeloVersionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		//Se debe guardar el modelo a parte ya que esto provoca entradas dobles en el histórico al hacer un alta.
		//ModeloDTO modelo = modeloBO.findById(transientObject.getId().getCoModelo());
		//modeloBO.auditorias(modelo, false);
	}

	public ModeloVersionDTO findVersionVigente(String coModelo) throws GadirServiceException {
		List<ModeloVersionDTO> modeloVersionDTOs = findVersionesByModelo(coModelo);
		ModeloVersionDTO versionVigenteDTO = modeloVersionDTOs.get(0);
		for (ModeloVersionDTO modeloVersionDTO : modeloVersionDTOs) {
			if (modeloVersionDTO.getFxAlta().compareTo(Utilidades.getDateActual()) <= 0) {
				versionVigenteDTO = modeloVersionDTO;
				break;
			}
		}
		return versionVigenteDTO;
	}
	
	
	public List<ModeloVersionDTO> buscaModelosVersionesDeDocumentos(final String coModelo)
		throws GadirServiceException {
			List<ModeloVersionDTO> lista = null;
			if (Utilidades.isEmpty(coModelo)) {
				lista = Collections.EMPTY_LIST;
			} else {
				final Map<String, Object> params = new HashMap<String, Object>();
				params.put("coModelo", coModelo);
				try {
					lista =  (List<ModeloVersionDTO>)this.getDao().findByNamedQuery(
						        QueryName.VERSIONES_DE_DOCUMENTO_BY_MODELO, params);
				} catch (final Exception e) {
					log.error(e.getCause(), e);
					throw new GadirServiceException(
					        "Error al obtener las versiones del modelo. Modelo: "
					                + coModelo, e);
				}
			
			}
			return lista;
	}
	
	

	/**
	 * @return the casillasLigadasBO
	 */
	public CasillasLigadasBO getCasillasLigadasBO() {
		return casillasLigadasBO;
	}

	/**
	 * @param casillasLigadasBO the casillasLigadasBO to set
	 */
	public void setCasillasLigadasBO(CasillasLigadasBO casillasLigadasBO) {
		this.casillasLigadasBO = casillasLigadasBO;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public ModeloVersionDTO findVersionVigenteByModelo(final String coModelo)
	        throws GadirServiceException {
		List<ModeloVersionDTO> lista = null;
		if (Utilidades.isEmpty(coModelo)) {
			lista = Collections.EMPTY_LIST;
		} else {
			try {
				lista = this.dao.findFiltered("id.coModelo", coModelo,
				        "fxAlta", DAOConstant.DESC_ORDER);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener las versiones del modelo. Modelo: "
				                + coModelo, e);
			}
		}
		if (lista.size()>0) {
			return lista.get(0);
		}
		else {
			return null;
		}
	}

	
	
}
