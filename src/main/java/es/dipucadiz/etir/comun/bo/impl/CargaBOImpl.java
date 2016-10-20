package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.CargaBO;
import es.dipucadiz.etir.comun.config.ParametrosConfig.ValoresParametrosConfig;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName.QueryCarga;
import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTOId;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTOId;
import es.dipucadiz.etir.comun.dto.CargaDTO;
import es.dipucadiz.etir.comun.dto.CargaEstructuraDTO;
import es.dipucadiz.etir.comun.dto.CargaEstructuraDTOId;
import es.dipucadiz.etir.comun.dto.CargaTipoRegistroDTO;
import es.dipucadiz.etir.comun.dto.CargaTipoRegistroDTOId;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb05.comun.utilidades.UtilidadesEstEntrada;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link CargaDTO}.
 * 
 * @version 2.0 18/11/2009
 * @author SDS[RMGARRIDO]
 */
public class CargaBOImpl extends AbstractGenericBOImpl<CargaDTO, Long>
        implements CargaBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6600031313681872877L;

	/**
	 * Atributo que almacena el PROP_COMUNICIPIO de la clase.
	 */
	private static final String PROP_COMUNICIPIO = "municipioDTO.id.coMunicipio";

	/**
	 * Atributo que almacena el PROP_COCONCEPTO de la clase.
	 */
	private static final String PROP_COCONCEPTO = "conceptoDTO.coConcepto";

	/**
	 * Atributo que almacena el PROP_COVERSION de la clase.
	 */
	private static final String PROP_COVERSION = "casillaModeloDTO.id.coVersion";

	/**
	 * Atributo que almacena el PROP_COMODELO de la clase.
	 */
	private static final String PROP_COMODELO = "casillaModeloDTO.id.coModelo";

	/**
	 * Atributo que almacena el PROPFIND_COMODELO de la clase.
	 */
	private static final String PROPFIND_COMODELO = "modeloVersionDTO.id.coModelo";

	/**
	 * Atributo que almacena el PROPFIND_COVERSION de la clase.
	 */
	private static final String PROPFIND_COVERSION = "modeloVersionDTO.id.coVersion";

	/**
	 * Atributo que almacena el PROP_NOMBRE de la clase.
	 */
	private static final String PROP_NOMBRE = "nombre";

	/**
	 * Atributo que almacena el DAO asociado a {@link CargaDTO}.
	 */
	private DAOBase<CargaDTO, Long> cargaDao;

	/**
	 * Atributo que almacena el DAO asociado a {@link CargaTipoRegistroDTO}.
	 */
	private DAOBase<CargaTipoRegistroDTO, CargaTipoRegistroDTOId> cargaTipoRegistroDao;

	/**
	 * Atributo que almacena el dao asociado a {@link CargaEstructuraDTO}.
	 */
	private DAOBase<CargaEstructuraDTO, CargaEstructuraDTOId> cargaEstructuraDao;

	/**
	 * Atributo que almacena el DAO asociado a {@link CasillaMunicipioDTO}.
	 */
	private DAOBase<CasillaMunicipioDTO, String> casillaMunicipioDao;

	/**
	 * Atributo que almacena el cargaCriterioDao de la clase.
	 */
	private DAOBase<CargaCriterioDTO, CargaCriterioDTOId> cargaCriterioDao;

	/**
	 * Atributo que almacena el cargaCondicionDao de la clase.
	 */
	private DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> cargaCondicionDao;

	/**
	 * {@inheritDoc}
	 */
	public CargaDTO findByIdLazy(final Long coCarga)
	        throws GadirServiceException {
		if (Utilidades.isNull(coCarga)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El id recibido es null, no se recupera nada del sistema.");
			}
			return null;
		} else {
			try {
				final Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("coCarga", coCarga);
				CargaDTO result = (CargaDTO) this.cargaDao
				        .findByNamedQueryUniqueResult(QueryCarga.FIND_BY_ID, param);
				if (result != null && result.getModeloVersionDTO() != null) {
					Hibernate.initialize(result.getModeloVersionDTO()
					        .getModeloDTO());
				}
				return result;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener la carga con id: "
				                + coCarga, e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public CargaDTO findNumeroEstructuras(final String coMunicipio)
	        throws GadirServiceException {
		if (Utilidades.isNull(coMunicipio)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El id recibido es null, no se recupera nada del sistema.");
			}
			return null;
		} else {
			try {
				final Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("coMunicipio", coMunicipio);
				if (this.cargaDao.ejecutaNamedQuerySelect(
				        QueryCarga.FIND_NUMERO_ESTRUCTURAS, param).equals(
				        Long.valueOf(1))) {

					CargaDTO filtro = (CargaDTO) this.cargaDao
					        .findByNamedQueryUniqueResult(
					                QueryCarga.FIND_BY_MUNICIPIO_ID, param);
					return filtro;
				} else {
					return null;
				}
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener la carga con id de municipio: "
				                + coMunicipio, e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateEstructura(final CargaDTO carga,
	        final boolean presentaTipos) throws GadirServiceException {
		if (Utilidades.isNull(carga)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto recibido es null, no se persiste nada en el sistema.");
			}
		} else {
			final List<CargaTipoRegistroDTO> tiposRegistro = this
			        .getCargaTipoRegistroDao().findFiltered("id.coCarga",
			                carga.getCoCarga());
			if (presentaTipos) {
				this.save(carga);
			} else {
				// Si tiene una lista de tipos es una compleja que pasa a ser
				// plana
				if (tiposRegistro != null && tiposRegistro.size() > 1) {
					// Se eliminan los registros asociados a la estructura.
					this.eliminaAsociadosCompleja(carga.getCoCarga());
					// Se actualiza la misma.
					this.save(carga);
					// Se crea el registro por defecto para que pase a ser
					// plana.
					crearRegistroDefecto(carga);
				} else if (tiposRegistro != null && tiposRegistro.size() == 0) {
					// Se actualiza la estructura.
					this.save(carga);
					// Se crea el registro por defecto para que pase a ser
					// plana.
					crearRegistroDefecto(carga);
				}
				// Si hay un solo registro, comprobar si es plana o compleja
				else if (tiposRegistro != null && tiposRegistro.size() == 1) {
					// Estructura compleja que pasa a ser plana
					if (!tiposRegistro
					        .get(0)
					        .getId()
					        .getCoCargaTipoRegistro()
					        .equals(
					                ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO)) {
						this.eliminaAsociadosCompleja(carga.getCoCarga());
						this.save(carga);
						crearRegistroDefecto(carga);
						// Estructura plana que se mantiene como tal
					} else if (tiposRegistro
					        .get(0)
					        .getId()
					        .getCoCargaTipoRegistro()
					        .equals(
					                ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO)) {
						this.save(carga);
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteEstructuraCascade(final Long coCarga)
	        throws GadirServiceException {
		if (Utilidades.isNull(coCarga)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El id recibido es null, no se elimina nada en el sistema.");
			}
		} else {
			try {
				// Borramos los criterios asociados
				this.deleteCriterio(coCarga);
				// Borramos las casillas asociadas
				this.deleteCasilla(coCarga);
				// Borramos los tipos de registro asociados
				this.deleteTipoRegistro(coCarga);
				// Por último se borra la estructura
				this.delete(coCarga);
			} catch (final GadirServiceException ge) {
				throw ge;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al borrar las estructuras", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CargaDTO> findByUniqueKey(final CargaDTO filtro)
	        throws GadirServiceException {
		if (Utilidades.isNull(filtro)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto recibido es null, no se persiste nada en el sistema.");
			}
			return Collections.emptyList();
		} else {
			try {
				final String[] propNames = { PROP_COMUNICIPIO,
				        PROPFIND_COMODELO, PROP_COCONCEPTO, PROPFIND_COVERSION,
				        PROP_NOMBRE };
				final Object[] filters = {
				        filtro.getMunicipioDTO().getId().getCoMunicipio(),
				        filtro.getModeloVersionDTO().getId().getCoModelo(),
				        filtro.getConceptoDTO().getCoConcepto(),
				        filtro.getModeloVersionDTO().getId().getCoVersion(),
				        filtro.getNombre() };
				return this.findFiltered(propNames, filters);

			} catch (final GadirServiceException ge) {
				throw ge;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener las estructuras.", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CargaDTO> findEstructuraByParamString(String codModelo,
	        String codVersion, String codMunicipio, String codConcepto)
	        throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("codModelo", codModelo);
			params.put("codVersion", codVersion);
			params.put("coMunicipio", codMunicipio);
			params.put("coConcepto", codConcepto);
			return this.findByNamedQuery(QueryCarga.ESTRUCTURA_FIND_BY_PARAM,
			        params);
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener las estructuras.", e);
		}
	}

	// }

	/**
	 * {@inheritDoc}
	 */
	public List<CargaDTO> findEstructuraByParam(CargaDTO filtro)
	        throws GadirServiceException {
		if (Utilidades.isNull(filtro)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto recibido es null, no se persiste nada en el sistema.");
			}
			return Collections.emptyList();
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>();
				if (filtro.getModeloVersionDTO() != null) {
					params.put("codModelo", filtro.getModeloVersionDTO()
					        .getId().getCoModelo());
				}
				if (filtro.getModeloVersionDTO() != null) {
					params.put("codVersion", filtro.getModeloVersionDTO()
					        .getId().getCoVersion());
				}
				if (filtro.getMunicipioDTO() != null) {
					params.put("coMunicipio", filtro.getMunicipioDTO().getId()
					        .getCoMunicipio());
				}
				if (filtro.getConceptoDTO() != null) {
					params.put("coConcepto", filtro.getConceptoDTO()
					        .getCoConcepto());
				}

				return this.findByNamedQuery(
				        QueryCarga.ESTRUCTURA_FIND_BY_PARAM, params);
			} catch (final GadirServiceException ge) {
				throw ge;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener las estructuras.", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOnly(final CargaDTO carga, final boolean presenta)
	        throws GadirServiceException {
		if (Utilidades.isNull(carga)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto recibido es null, no se persiste nada en el sistema.");
			}
		} else {
			if (!presenta) {
				carga.setFhActualizacion(Utilidades.getDateActual());
				carga.setCoUsuarioActualizacion(DatosSesion.getLogin());
				carga.setCoCarga((Long) this.getCargaDao().saveOnly(carga));
				this.crearRegistroDefecto(carga);
			} else if (presenta) {
				this.save(carga);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void duplicar(final CargaDTO carga, final boolean presenta)
	        throws GadirServiceException {
		if (Utilidades.isNull(carga)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto recibido es null, no se persiste nada en el sistema.");
			}
		} else {
			try {
				final List<CargaTipoRegistroDTO> tiposRegistro = this
				        .getCargaTipoRegistroDao().findFiltered("id.coCarga",
				                carga.getCoCarga());
				carga.setCoCarga(null);
				carga.setFhActualizacion(Utilidades.getDateActual());
				carga.setCoUsuarioActualizacion(DatosSesion.getLogin());
				carga.setCoCarga((Long) this.getCargaDao().saveOnly(carga));

				// Si se presentan tipos de registro, se duplican los asociados
				if (presenta) {
					if (tiposRegistro != null && tiposRegistro.size() > 0) {
						// Si tiene el tReg estándar 00, es una plana que pasa a
						// compleja
						if (tiposRegistro.size() == 1) {
							if (tiposRegistro
							        .get(0)
							        .getId()
							        .getCoCargaTipoRegistro()
							        .equals(
							                ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO)) {
								this.deleteCriterio(tiposRegistro.get(0)
								        .getId().getCoCarga());
								this.deleteCasilla(tiposRegistro.get(0).getId()
								        .getCoCarga());
								this.getCargaTipoRegistroDao().delete(
								        tiposRegistro.get(0).getId());
							} else if (!tiposRegistro
							        .get(0)
							        .getId()
							        .getCoCargaTipoRegistro()
							        .equals(
							                ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO)) {
								// Duplicamos los tipos de registro
								this.duplicaTipoRegistro(tiposRegistro, carga);

								// Duplicamos las casillas que cumplan las
								// condiciones
								// exigidas
								this.duplicaCasilla(tiposRegistro, carga);

								// Duplicamos los criterios que cumplan las
								// condiciones
								// exigidas
								this.duplicaCriterio(tiposRegistro, carga);
							}

							// Si hay más de un tReg, es una compleja,
							// duplicamos
							// normalmente.
						} else if (tiposRegistro.size() > 1) {
							// Duplicamos los tipos de registro
							this.duplicaTipoRegistro(tiposRegistro, carga);

							// Duplicamos las casillas que cumplan las
							// condiciones
							// exigidas
							this.duplicaCasilla(tiposRegistro, carga);

							// Duplicamos los criterios que cumplan las
							// condiciones
							// exigidas
							this.duplicaCriterio(tiposRegistro, carga);
						}
					}
				} else if (!presenta) {
					// Si tenemos un solo tReg, comprobamos si es estructura
					// plana o
					// compleja
					if (tiposRegistro != null && tiposRegistro.size() == 1) {
						if (tiposRegistro
						        .get(0)
						        .getId()
						        .getCoCargaTipoRegistro()
						        .equals(
						                ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO)) {
							// Duplicamos los tipos de registro
							this.duplicaTipoRegistro(tiposRegistro, carga);

							// Duplicamos las casillas que cumplan las
							// condiciones
							// exigidas
							this.duplicaCasilla(tiposRegistro, carga);

							// Duplicamos los criterios que cumplan las
							// condiciones
							// exigidas
							this.duplicaCriterio(tiposRegistro, carga);
						} else if (!tiposRegistro
						        .get(0)
						        .getId()
						        .getCoCargaTipoRegistro()
						        .equals(
						                ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO)) {
							this.deleteCriterio(tiposRegistro.get(0).getId()
							        .getCoCarga());
							this.deleteCasilla(tiposRegistro.get(0).getId()
							        .getCoCarga());
							this.getCargaTipoRegistroDao().delete(
							        tiposRegistro.get(0).getId());

							final CargaTipoRegistroDTO tipoRegistro = new CargaTipoRegistroDTO();
							final CargaTipoRegistroDTOId id = new CargaTipoRegistroDTOId();
							id.setCoCarga(carga.getCoCarga());
							id
							        .setCoCargaTipoRegistro(ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO);
							tipoRegistro.setId(id);
							tipoRegistro.setBoCreaHoja1(true);
							tipoRegistro.setBoCreaHojaN(false);
							tipoRegistro.setFhActualizacion(Utilidades.getDateActual());
							tipoRegistro.setCoUsuarioActualizacion(DatosSesion.getLogin());
							this.cargaTipoRegistroDao.save(tipoRegistro);
						}
						// Si tenemos mas de un tReg, estariamos pasando de
						// compleja
						// a plana.
						// Se eliminan los tReg originales y no habría que
						// duplicar
						// tReg, luego por tanto no se duplican ni casillas ni
						// criterios.
					} else if (tiposRegistro != null
					        && tiposRegistro.size() > 1) {
						for (int i = 0; i < tiposRegistro.size(); i++) {
							this.deleteCriterio(tiposRegistro.get(0).getId()
							        .getCoCarga());
							this.deleteCasilla(tiposRegistro.get(0).getId()
							        .getCoCarga());
							this.getCargaTipoRegistroDao().delete(
							        tiposRegistro.get(i).getId());
						}
						final CargaTipoRegistroDTO tipoRegistro = new CargaTipoRegistroDTO();
						final CargaTipoRegistroDTOId id = new CargaTipoRegistroDTOId();
						id.setCoCarga(carga.getCoCarga());
						id
						        .setCoCargaTipoRegistro(ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO);
						tipoRegistro.setId(id);
						tipoRegistro.setBoCreaHoja1(true);
						tipoRegistro.setBoCreaHojaN(false);
						tipoRegistro.setFhActualizacion(Utilidades.getDateActual());
						tipoRegistro.setCoUsuarioActualizacion(DatosSesion.getLogin());
						this.cargaTipoRegistroDao.save(tipoRegistro);
					}
				}
			} catch (final GadirServiceException ge) {
				throw ge;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				e.printStackTrace();
				throw new GadirServiceException(
				        "Ocurrio un error al duplicar las estructuras", e);
			}
		}
	}

	// Métodos privados

	/**
	 * Método que se encarga de eliminar los registros asociados a una
	 * estructura compleja que pasa a ser plana.
	 * 
	 * @param coCarga
	 *            Identificador de la estructura que pasa a ser plana
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	private void eliminaAsociadosCompleja(final Long coCarga)
	        throws GadirServiceException {
		this.deleteCriterio(coCarga);
		this.deleteCasilla(coCarga);
		this.deleteTipoRegistro(coCarga);

	}

	/**
	 * Método que se encarga de crear un registro por defecto.
	 * 
	 * @param carga
	 *            Carga para la que se va a crear el tipo de registro por
	 *            defecto.
	 */
	private void crearRegistroDefecto(final CargaDTO carga) {
		final CargaTipoRegistroDTO tipoRegistro = new CargaTipoRegistroDTO();
		final CargaTipoRegistroDTOId id = new CargaTipoRegistroDTOId();
		id.setCoCarga(carga.getCoCarga());
		id
		        .setCoCargaTipoRegistro(ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO);
		tipoRegistro.setId(id);
		tipoRegistro.setBoCreaHoja1(true);
		tipoRegistro.setBoCreaHojaN(false);
		tipoRegistro.setFhActualizacion(Utilidades.getDateActual());
		tipoRegistro.setCoUsuarioActualizacion(DatosSesion.getLogin());
		this.getCargaTipoRegistroDao().save(tipoRegistro);
	}

	/**
	 * MÃ©todo que se encarga de duplicar los criterios que cumplan con las
	 * condiciones exigidas.
	 * 
	 * @param tiposRegistro
	 *            Listado de tipos de registro originalmente asociado a los
	 *            criterios.
	 * @param carga
	 *            La nueva estructura resultado de duplicar una existente.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca una excepciÃ³n.
	 */
	private void duplicaCriterio(
	        final List<CargaTipoRegistroDTO> tiposRegistro, final CargaDTO carga)
	        throws GadirServiceException {
		final Iterator<CargaTipoRegistroDTO> itOrigen = tiposRegistro
		        .iterator();
		// Recuperamos los tipos de registro originales para recuperar los
		// criterios
		while (itOrigen.hasNext()) {
			final CargaTipoRegistroDTO tipoOrigen = itOrigen.next();
			final String[] propNames = { "id.coCargaTipoRegistro", "id.coCarga" };
			final Object[] filters = {
			        tipoOrigen.getId().getCoCargaTipoRegistro(),
			        tipoOrigen.getId().getCoCarga() };
			final List<CargaCriterioDTO> criterios = this.cargaCriterioDao
			        .findFiltered(propNames, filters);
			final Iterator<CargaCriterioDTO> itCrite = criterios.iterator();
			// Recuperamos la lista de criterios asociados al tipo original
			while (itCrite.hasNext()) {
				final CargaCriterioDTO criteOrigen = itCrite.next();

				// Recuperamos la lista de condiciones
				String[] props = { "id.coCarga", "id.coCargaTipoRegistro",
				        "id.coCargaCriterio" };
				Object[] values = { criteOrigen.getId().getCoCarga(),
				        criteOrigen.getId().getCoCargaTipoRegistro(),
				        criteOrigen.getId().getCoCargaCriterio() };
				final List<CargaCriterioCondicionDTO> condiciones = this
				        .getCargaCondicionDao().findFiltered(props, values);
				final Iterator<CargaCriterioCondicionDTO> itCondiciones = condiciones
				        .iterator();
				final List<CargaCriterioCondicionDTO> condicionesFinales = new ArrayList<CargaCriterioCondicionDTO>();
				// Validamos que las posiciones de la condición no excedan la
				// longitud de la estructura.
				while (itCondiciones.hasNext()) {
					final CargaCriterioCondicionDTO aux = itCondiciones.next();
					if (aux.getPosFin() - aux.getPosIni() + 1 < carga
					        .getTamRegistro()) {
						condicionesFinales.add(aux);
					}
				}
				// Si hemos obtenido al menos una condición válida, duplicamos
				// la misma junto con su criterio
				if (condicionesFinales.size() > 0) {
					final CargaCriterioDTO nuevoCriterio = new CargaCriterioDTO();

					final CargaTipoRegistroDTO nuevoTipo = new CargaTipoRegistroDTO();
					final CargaTipoRegistroDTOId id = new CargaTipoRegistroDTOId();
					id.setCoCarga(carga.getCoCarga());
					id.setCoCargaTipoRegistro(tipoOrigen.getId()
					        .getCoCargaTipoRegistro());

					nuevoTipo.setId(id);

					final CargaCriterioDTOId idCri = new CargaCriterioDTOId();
					idCri.setCoCarga(nuevoTipo.getId().getCoCarga());
					idCri.setCoCargaTipoRegistro(nuevoTipo.getId()
					        .getCoCargaTipoRegistro());
					idCri.setCoCargaCriterio(criteOrigen.getId()
					        .getCoCargaCriterio());
					nuevoCriterio.setId(idCri);
					nuevoCriterio.setBoInclusion(criteOrigen.getBoInclusion());
					nuevoCriterio.setFhActualizacion(Utilidades.getDateActual());
					nuevoCriterio.setCoUsuarioActualizacion(DatosSesion.getLogin());
					this.getCargaCriterioDao().save(nuevoCriterio);
					final Iterator<CargaCriterioCondicionDTO> itGuardar = condicionesFinales
					        .iterator();
					while (itGuardar.hasNext()) {
						final CargaCriterioCondicionDTO condAux = itGuardar
						        .next();
						final CargaCriterioCondicionDTO condicion = new CargaCriterioCondicionDTO();
						final CargaCriterioCondicionDTOId idCon = new CargaCriterioCondicionDTOId();
						idCon.setCoCarga(idCri.getCoCarga());
						idCon.setCoCargaCriterio(idCri.getCoCargaCriterio());
						idCon.setCoCargaTipoRegistro(idCri
						        .getCoCargaTipoRegistro());
						idCon.setCoCargaCriterioCondicion(condAux.getId()
						        .getCoCargaCriterioCondicion());
						condicion.setId(idCon);
						condicion.setConector(condAux.getConector());
						condicion.setOperador(condAux.getOperador());
						condicion.setPosFin(condAux.getPosFin());
						condicion.setPosIni(condAux.getPosIni());
						condicion.setValor(condAux.getValor());
						condicion.setFhActualizacion(Utilidades.getDateActual());
						condicion.setCoUsuarioActualizacion(DatosSesion.getLogin());
						this.getCargaCondicionDao().save(condicion);
					}
				}
			}
		}
	}

	/**
	 * Método que se encarga de duplicar los tipos de registro asociados a una
	 * estructura que ha sido duplicada.
	 * 
	 * @param tiposRegistro
	 *            Listado de los tipos de registro asociados a la estructura
	 *            original.
	 * @param carga
	 *            La nueva estructura resultado de duplicar una existente.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca una excepción.
	 */
	private void duplicaTipoRegistro(
	        final List<CargaTipoRegistroDTO> tiposRegistro, final CargaDTO carga)
	        throws GadirServiceException {
		final Iterator<CargaTipoRegistroDTO> itTipos = tiposRegistro.iterator();
		try {
			while (itTipos.hasNext()) {
				final CargaTipoRegistroDTO tipo = itTipos.next();
				final CargaTipoRegistroDTO aux = new CargaTipoRegistroDTO();
				final CargaTipoRegistroDTOId idTipo = new CargaTipoRegistroDTOId();
				idTipo.setCoCarga(carga.getCoCarga());
				idTipo.setCoCargaTipoRegistro(tipo.getId()
				        .getCoCargaTipoRegistro());
				aux.setId(idTipo);
				aux.setBoCreaHoja1(tipo.getBoCreaHoja1());
				aux.setBoCreaHojaN(tipo.getBoCreaHojaN());
				aux.setFhActualizacion(Utilidades.getDateActual());
				aux.setCoUsuarioActualizacion(DatosSesion.getLogin());
				this.getCargaTipoRegistroDao().save(aux);
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			e.printStackTrace();
			throw new GadirServiceException(
			        "Ocurrio un error al duplicar los tipos de registro", e);
		}
	}

	/**
	 * Método que se encarga de duplicar las casillas asociadas a una estructura
	 * y tipos de registro.
	 * 
	 * @param tiposRegistro
	 *            Listado de tipos de registro originales, antes de duplicar.
	 * @param carga
	 *            La nueva estructura resultado de duplicar una existente.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca una excepción.
	 */
	private void duplicaCasilla(final List<CargaTipoRegistroDTO> tiposRegistro,
	        final CargaDTO carga) throws GadirServiceException {
		final Iterator<CargaTipoRegistroDTO> itOrigen = tiposRegistro
		        .iterator();
		while (itOrigen.hasNext()) {
			final CargaTipoRegistroDTO aux = itOrigen.next();
			final String[] propNames = { "id.coCarga", "id.coCargaTipoRegistro" };
			final Object[] filters = { aux.getId().getCoCarga(),
			        aux.getId().getCoCargaTipoRegistro() };
			final List<CargaEstructuraDTO> casillas = this
			        .getCargaEstructuraDao().findFiltered(propNames, filters);
			final Iterator<CargaEstructuraDTO> itCasillas = casillas.iterator();
			while (itCasillas.hasNext()) {
				final CargaEstructuraDTO casAux = itCasillas.next();
				if (this.validaCasilla(casAux, carga)) {
					final CargaEstructuraDTO casillaNueva = new CargaEstructuraDTO();
					final CargaEstructuraDTOId idEst = new CargaEstructuraDTOId();
					idEst.setCoCarga(carga.getCoCarga());
					idEst.setCoCargaTipoRegistro(aux.getId()
					        .getCoCargaTipoRegistro());
					idEst.setNuCasilla(casAux.getId().getNuCasilla());
					casillaNueva.setId(idEst);
					casillaNueva.setPosFin(casAux.getPosFin());
					casillaNueva.setPosIni(casAux.getPosIni());
					casillaNueva.setFhActualizacion(Utilidades.getDateActual());
					casillaNueva.setCoUsuarioActualizacion(DatosSesion.getLogin());
					this.getCargaEstructuraDao().save(casillaNueva);
				}
			}
		}
	}

	/**
	 * Método que se encarga de validar que es posible duplicar la casilla que
	 * se está tratando.
	 * 
	 * @param casillaOrigen
	 *            Casilla que se está tratando, que se pretende duplicar.
	 * @param cargaNueva
	 *            Estructura nueva ya duplicada.
	 * @return boolean Indicando si es posible o no realizar el duplicado de la
	 *         casilla.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	private boolean validaCasilla(final CargaEstructuraDTO casillaOrigen,
	        final CargaDTO cargaNueva) throws GadirServiceException {
		final String[] propNames = { PROP_COMUNICIPIO, PROP_COCONCEPTO,
		        PROP_COVERSION, PROP_COMODELO };
		final Object[] filters = {
		        cargaNueva.getMunicipioDTO().getId().getCoMunicipio(),
		        cargaNueva.getConceptoDTO().getCoConcepto(),
		        cargaNueva.getModeloVersionDTO().getId().getCoVersion(),
		        cargaNueva.getModeloVersionDTO().getId().getCoModelo() };
		try {
			final List<CasillaMunicipioDTO> casillasMunicipio = this
			        .getCasillaMunicipioDao().findFiltered(propNames, filters);
			boolean valido = false;

			for (int i = 0; i < casillasMunicipio.size(); i++) {
				final CasillaMunicipioDTO aux = casillasMunicipio.get(i);

			
				if (aux.getCasillaModeloDTO().getId().getNuCasilla() == casillaOrigen
				        .getId().getNuCasilla()) {
					valido = UtilidadesEstEntrada.validarAsocCasilla(
					        cargaNueva,
					        aux.getCasillaModeloDTO().getLongitud(),
					        casillaOrigen, null, null);
				}
			}
			return valido;
		} catch (final GadirServiceException gse) {
			throw gse;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			e.printStackTrace();
			throw new GadirServiceException(
			        "Ocurrio un error al validar las casillas", e);
		}
	}

	/**
	 * Método que se encarga de eliminar los criterios y condiciones asociados a
	 * una estructura.
	 * 
	 * @param coCarga
	 *            Identificador de la estructura que se va a eliminar.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	private void deleteCriterio(final Long coCarga)
	        throws GadirServiceException {
		try {
			final List<CargaCriterioDTO> criterios = this.cargaCriterioDao
			        .findFiltered("id.coCarga", coCarga);
			final List<CargaCriterioCondicionDTO> condiciones = this.cargaCondicionDao
			        .findFiltered("id.coCarga", coCarga);
			final Iterator<CargaCriterioDTO> itCriterios = criterios.iterator();
			final Iterator<CargaCriterioCondicionDTO> itCondiciones = condiciones
			        .iterator();

			// Se borra primero las condiciones ya que dependen de los criterios
			while (itCondiciones.hasNext()) {
				this.getCargaCondicionDao()
				        .delete(itCondiciones.next().getId());
			}
			// Seguidamente se borran los criterios
			while (itCriterios.hasNext()) {
				this.getCargaCriterioDao().delete(itCriterios.next().getId());
			}

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al borrar los criterios", e);
		}
	}

	/**
	 * Método que se encarga de eliminar las casillas asociadas a una
	 * estructura.
	 * 
	 * @param coCarga
	 *            Identificador de la estructura que ser va a eliminar.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	private void deleteCasilla(final Long coCarga) throws GadirServiceException {
		try {
			final List<CargaEstructuraDTO> listado = this
			        .getCargaEstructuraDao()
			        .findFiltered("id.coCarga", coCarga);
			final Iterator<CargaEstructuraDTO> itCasilla = listado.iterator();
			while (itCasilla.hasNext()) {
				this.getCargaEstructuraDao().delete(itCasilla.next().getId());
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al borrar las casillas", e);
		}
	}

	/**
	 * Método que se encarga de eliminar los tipos de registro asociados a una
	 * estructura.
	 * 
	 * @param coCarga
	 *            Identificador de la estructura que se va a eliminar.
	 * @throws GadirServiceException
	 *             En el caso de que se produzca un error.
	 */
	private void deleteTipoRegistro(final Long coCarga)
	        throws GadirServiceException {
		try {
			final List<CargaTipoRegistroDTO> listado = this.cargaTipoRegistroDao
			        .findFiltered("id.coCarga", coCarga);
			final Iterator<CargaTipoRegistroDTO> itTipos = listado.iterator();
			while (itTipos.hasNext()) {
				this.getCargaTipoRegistroDao().delete(itTipos.next().getId());
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al borrar los tipos de registro", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<CargaDTO, Long> getDao() {
		return this.getCargaDao();
	}

	// GETTERS AND SETTERS

	/**
	 * Método que devuelve el atributo cargaDao.
	 * 
	 * @return cargaDao.
	 */
	public DAOBase<CargaDTO, Long> getCargaDao() {
		return cargaDao;
	}

	/**
	 * Método que establece el atributo cargaDao.
	 * 
	 * @param cargaDao
	 *            El cargaDao.
	 */
	public void setCargaDao(final DAOBase<CargaDTO, Long> cargaDao) {
		this.cargaDao = cargaDao;
	}

	/**
	 * Método que devuelve el atributo cargaTipoRegistroDao.
	 * 
	 * @return GenericDAO
	 */
	public DAOBase<CargaTipoRegistroDTO, CargaTipoRegistroDTOId> getCargaTipoRegistroDao() {
		return cargaTipoRegistroDao;
	}

	/**
	 * Método que establece el atributo cargaTipoRegistroDao.
	 * 
	 * @param cargaTipoRegistroDao
	 *            Dao de carga tipo registro.
	 */
	public void setCargaTipoRegistroDao(
	        final DAOBase<CargaTipoRegistroDTO, CargaTipoRegistroDTOId> cargaTipoRegistroDao) {
		this.cargaTipoRegistroDao = cargaTipoRegistroDao;
	}

	/**
	 * Método que devuelve el dao de cargaEstructura.
	 * 
	 * @return GenericDAO para trabajar con cargaEstructura.
	 */
	public DAOBase<CargaEstructuraDTO, CargaEstructuraDTOId> getCargaEstructuraDao() {
		return cargaEstructuraDao;
	}

	/**
	 * Método que establece el dao de cargaEstructura.
	 * 
	 * @param cargaEstructuraDao
	 *            El GenericDAO para trabajar con cargaEstructura.
	 */
	public void setCargaEstructuraDao(
	        final DAOBase<CargaEstructuraDTO, CargaEstructuraDTOId> cargaEstructuraDao) {
		this.cargaEstructuraDao = cargaEstructuraDao;
	}

	/**
	 * Método que devuelve el atributo casillaMunicipioDao.
	 * 
	 * @return casillaMunicipioDao.
	 */
	public DAOBase<CasillaMunicipioDTO, String> getCasillaMunicipioDao() {
		return casillaMunicipioDao;
	}

	/**
	 * Método que establece el atributo casillaMunicipioDao.
	 * 
	 * @param casillaMunicipioDao
	 *            El casillaMunicipioDao.
	 */
	public void setCasillaMunicipioDao(
	        final DAOBase<CasillaMunicipioDTO, String> casillaMunicipioDao) {
		this.casillaMunicipioDao = casillaMunicipioDao;
	}

	/**
	 * Método que devuelve el atributo cargaCriterioDao.
	 * 
	 * @return cargaCriterioDao.
	 */
	public DAOBase<CargaCriterioDTO, CargaCriterioDTOId> getCargaCriterioDao() {
		return cargaCriterioDao;
	}

	/**
	 * Método que establece el atributo cargaCriterioDao.
	 * 
	 * @param cargaCriterioDao
	 *            El cargaCriterioDao.
	 */
	public void setCargaCriterioDao(
	        final DAOBase<CargaCriterioDTO, CargaCriterioDTOId> cargaCriterioDao) {
		this.cargaCriterioDao = cargaCriterioDao;
	}

	/**
	 * Método que devuelve el atributo cargaCondicionDao.
	 * 
	 * @return cargaCondicionDao.
	 */
	public DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> getCargaCondicionDao() {
		return cargaCondicionDao;
	}

	/**
	 * Método que establece el atributo cargaCondicionDao.
	 * 
	 * @param cargaCondicionDao
	 *            El cargaCondicionDao.
	 */
	public void setCargaCondicionDao(
	        final DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> cargaCondicionDao) {
		this.cargaCondicionDao = cargaCondicionDao;
	}
	
	public void auditorias(CargaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
