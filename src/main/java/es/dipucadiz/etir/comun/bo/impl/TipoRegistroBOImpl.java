package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.TipoRegistroBO;
import es.dipucadiz.etir.comun.config.ParametrosConfig.ValoresParametrosConfig;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTOId;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTOId;
import es.dipucadiz.etir.comun.dto.CargaDTO;
import es.dipucadiz.etir.comun.dto.CargaEstructuraDTO;
import es.dipucadiz.etir.comun.dto.CargaEstructuraDTOId;
import es.dipucadiz.etir.comun.dto.CargaTipoRegistroDTO;
import es.dipucadiz.etir.comun.dto.CargaTipoRegistroDTOId;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


@SuppressWarnings("unchecked")
public class TipoRegistroBOImpl extends
        AbstractGenericBOImpl<CargaTipoRegistroDTO, CargaTipoRegistroDTOId>
        implements TipoRegistroBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -2645109021150008074L;

	/**
	 * Atributo que almacena el literal asociado al campo id.cargaDTO.coCarga de
	 * una {@link CargaTipoRegistroDTO}.
	 */
	private static final String CAMPO_CO_EST = "id.coCarga";

	/**
	 * Atributo que almacena el literal asociado al campo id.cargaDTO.coCarga de
	 * una {@link CargaTipoRegistroDTO}.
	 */
	private static final String CAMPO_CO_TIP = "id.coCargaTipoRegistro";

	/**
	 * Atributo que almacena el DAO asociado a {@link CargaTipoRegistroDTO}.
	 */
	private DAOBase<CargaTipoRegistroDTO, CargaTipoRegistroDTOId> cargaTipoRegistroDao;

	/**
	 * Atributo que almacena el DAO asociado a {@link CargaCriterioDTO}.
	 */
	private DAOBase<CargaCriterioDTO, CargaCriterioDTOId> cargaCriterioDao;

	/**
	 * Atributo que almacena el DAO asociado a {@link CargaEstructuraDTO}.
	 */
	private DAOBase<CargaEstructuraDTO, CargaEstructuraDTOId> cargaEstructuraDao;

	/**
	 * Atributo que almacena el DAO asociado a {@link CargaCriterioCondicionDTO}
	 * .
	 */
	private DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> cargaCriterioCondicionDao;
	
	/**
	 * Atributo que almacena el DAO asociado a {@link CargaDTO}
	 * .
	 */
	private DAOBase<CargaDTO, Long> cargaDao;

	/**
	 * {@inheritDoc}
	 */
	public List<CargaTipoRegistroDTO> findTiposRegistrosByEstructura(
	        final Long codEstructura) throws GadirServiceException {
		List<CargaTipoRegistroDTO> resultado;
		try {
			resultado = this.findFiltered(CAMPO_CO_EST, codEstructura,
			        CAMPO_CO_TIP, DAOConstant.ASC_ORDER);
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error obtener las casillas.", e);
		}
		return resultado;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CasillaModeloDTO> findCasillasModelosBoRepeticion(
	        final String codModelo, final String codVersion)
	        throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("codModelo", codModelo);
			params.put("codVersion", codVersion);
			final List<CasillaModeloDTO> result = (List<CasillaModeloDTO>) this
			        .getDao().ejecutaNamedQuerySelect(
			                QueryName.CASILLA_MODELO_BO_REPETICION, params);
			return result;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener las casillas con Bo_Repeticion.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CargaCriterioDTO> findBoCriteriosIncExc(final Long codCarga,
	        final String codTipoRegistro) throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("codTipoRegistro", codTipoRegistro);
			params.put("codCarga", codCarga);
			final List<CargaCriterioDTO> result = (List<CargaCriterioDTO>) this
			        .getDao().ejecutaNamedQuerySelect(QueryName.CRITERIOS_INC_EXC,
			                params);
			return result;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener las casillas con Bo_Repeticion.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CargaTipoRegistroDTO> findBoHoja1(final Long codCarga)
	        throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("codCarga", codCarga);
			params.put("boCreaHoja1", true);
			final List<CargaTipoRegistroDTO> result = (List<CargaTipoRegistroDTO>) this
			        .getDao().ejecutaNamedQuerySelect(QueryName.HOJA1, params);
			return result;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener las casillas con Bo_Repeticion.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CargaTipoRegistroDTO> findBoHojaN(final Long codCarga)
	        throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("codCarga", codCarga);
			params.put("boCreaHojaN", true);
			final List<CargaTipoRegistroDTO> result = (List<CargaTipoRegistroDTO>) this
			        .getDao().ejecutaNamedQuerySelect(QueryName.HOJAN, params);
			return result;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener las casillas con Bo_Repeticion.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteTipoRegistro(final CargaTipoRegistroDTOId idTipoReg)
	        throws GadirServiceException {
		try {
			if (idTipoReg != null) {

				// Obtenemos los criterio-condicion y los borramos.
				final String[] parametros = new String[2];
				parametros[0] = "id.coCarga";
				parametros[1] = "id.coCargaTipoRegistro";
				final Object[] valores = new Object[2];
				valores[0] = idTipoReg.getCoCarga();
				valores[1] = idTipoReg.getCoCargaTipoRegistro();
				final List<CargaCriterioCondicionDTO> listaCriteriosCond = this
				        .getCargaCriterioCondicionDao().findFiltered(
				                parametros, valores);

				this.deleteCriteriosCondTipoRegistro(listaCriteriosCond);

				// Obtenemos los criterios y los borramos.
				final List<CargaCriterioDTO> listaCriterios = this
				        .findBoCriteriosIncExc(idTipoReg.getCoCarga(),
				                idTipoReg.getCoCargaTipoRegistro());
				this.deleteCriteriosTipoRegistro(listaCriterios);

				// Obtenemos las asociaciones y las borramos.
				final List<CargaEstructuraDTO> listaPosiciones = this
				        .findBoPosicionesCasillasReg(idTipoReg.getCoCarga(),
				                idTipoReg.getCoCargaTipoRegistro());
				this.deleteAsociaciones(listaPosiciones);

				// Borramos el tipo de registro tras borrar las asociaciones.
				this.getCargaTipoRegistroDao().delete(idTipoReg);
			}
		} catch (final GadirServiceException gse) {
			throw gse;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al borrar el tipo de registro y sus dependencias.",
			        e);
		}
	}

	private void deleteCriteriosTipoRegistro(final List<CargaCriterioDTO> lista) {
		for (final CargaCriterioDTO criterio : lista) {
			this.getCargaCriterioDao().delete(criterio.getId());
		}
	}

	private void deleteCriteriosCondTipoRegistro(
	        final List<CargaCriterioCondicionDTO> lista) {
		for (final CargaCriterioCondicionDTO cond : lista) {
			this.getCargaCriterioCondicionDao().delete(cond.getId());
		}
	}

	private void deleteAsociaciones(final List<CargaEstructuraDTO> lista) {
		for (final CargaEstructuraDTO asoc : lista) {
			this.getCargaEstructuraDao().delete(asoc.getId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteCriteriosIncExc(
	        final List<CargaCriterioDTO> listaCriterios)
	        throws GadirServiceException {
		for (int i = 0; i < listaCriterios.size(); i++) {
			this.getCargaCriterioDao().delete(listaCriterios.get(i).getId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveCriteriosIncExc(final List<CargaCriterioDTO> listaCriterios)
	        throws GadirServiceException {
		for (int i = 0; i < listaCriterios.size(); i++) {
			listaCriterios.get(i).setFhActualizacion(Utilidades.getDateActual());
			listaCriterios.get(i).setCoUsuarioActualizacion(DatosSesion.getLogin());
			this.getCargaCriterioDao().save(listaCriterios.get(i));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deletePosicionesCasillas(
	        final List<CargaEstructuraDTO> listaCriterios)
	        throws GadirServiceException {
		for (int i = 0; i < listaCriterios.size(); i++) {
			this.getCargaEstructuraDao().delete(listaCriterios.get(i).getId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void savePosicionesCasillas(
	        final List<CargaEstructuraDTO> listaCriterios)
	        throws GadirServiceException {
		for (int i = 0; i < listaCriterios.size(); i++) {
			listaCriterios.get(i).setFhActualizacion(Utilidades.getDateActual());
			listaCriterios.get(i).setCoUsuarioActualizacion(DatosSesion.getLogin());
			this.getCargaEstructuraDao().save(listaCriterios.get(i));
		}
	}

	
	
	/**
	 * {@inheritDoc}
	 */
	public List<CargaEstructuraDTO> findBoPosicionesCasillasReg(
	        final Long codCarga, final String codTipoRegistro)
	        throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("codTipoRegistro", codTipoRegistro);
			params.put("codCarga", codCarga);
			final List<CargaEstructuraDTO> result = (List<CargaEstructuraDTO>) this
			        .getDao().ejecutaNamedQuerySelect(
			                QueryName.POSICIONES_CASILLAS_REG, params);
			return result;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener las casillas con Bo_Repeticion.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CargaCriterioCondicionDTO> obtenerListaCriteriosCond(
	        final CargaTipoRegistroDTOId idTipoReg) {
		// Obtenemos los criterio-condicion y los borramos.
		final String[] parametros = new String[2];
		parametros[0] = "id.coCarga";
		parametros[1] = "id.coCargaTipoRegistro";
		final Object[] valores = new Object[2];
		valores[0] = idTipoReg.getCoCarga();
		valores[1] = idTipoReg.getCoCargaTipoRegistro();
		final List<CargaCriterioCondicionDTO> listaCriteriosCond = this
		        .getCargaCriterioCondicionDao().findFiltered(parametros,
		                valores);
		return listaCriteriosCond;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CargaCriterioDTO> obtenerListaCriterios(
	        final CargaTipoRegistroDTOId idTipoReg)
	        throws GadirServiceException {
		// Obtenemos los criterios y los borramos.
		final List<CargaCriterioDTO> listaCriterios = this
		        .findBoCriteriosIncExc(idTipoReg.getCoCarga(), idTipoReg
		                .getCoCargaTipoRegistro());
		return listaCriterios;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CargaEstructuraDTO> obtenerListaEstructuras(
	        final CargaTipoRegistroDTOId idTipoReg)
	        throws GadirServiceException {
		// Obtenemos los criterios y los borramos.
		final List<CargaEstructuraDTO> listaPosiciones = this
		        .findBoPosicionesCasillasReg(idTipoReg.getCoCarga(), idTipoReg
		                .getCoCargaTipoRegistro());
		return listaPosiciones;
	}

	/**
	 * {@inheritDoc}
	 * @throws GadirServiceException 
	 */
	public void guardarTipoRegistroPorDefecto(final CargaTipoRegistroDTO registroGuardar) throws GadirServiceException{
		try{
			//guardamos el nuevo tipo registro
			this.save(registroGuardar);
			//recuperamos el anterior por defecto
			final CargaTipoRegistroDTO registro00 = this
			        .cargaTipoRegistroDao
			        .findById(
			                new CargaTipoRegistroDTOId(
			                		registroGuardar.getId().getCoCarga(),
			                        ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO));

			final String[] parametros = new String[2];
			parametros[0] = "id.coCarga";
			parametros[1] = "id.coCargaTipoRegistro";
			final Object[] valores = new Object[2];
			valores[0] = registroGuardar.getId().getCoCarga();
			valores[1] = registro00.getId()
			        .getCoCargaTipoRegistro();
			//nos traemos todos los criterios del tipo registro anterior
			final List<CargaCriterioDTO> listaCriterios = this
	        .findBoCriteriosIncExc(
	        		registroGuardar.getId().getCoCarga(),
	                registro00.getId()
	                        .getCoCargaTipoRegistro());
			// iteramos sobre ellos, le asignamos el nuevo tipo de registro dado de alta y lo guardamos
			List<CargaCriterioDTO> listaCriteriosGuardar = new ArrayList<CargaCriterioDTO>();
			for (final CargaCriterioDTO crit : listaCriterios) {
				CargaCriterioDTO auxiliar = new CargaCriterioDTO();
				auxiliar.setId(new CargaCriterioDTOId());
				auxiliar.getId().setCoCarga(
				        registroGuardar.getId().getCoCarga());
				auxiliar.getId().setCoCargaCriterio(crit.getId().getCoCargaCriterio());
				auxiliar.getId().setCoCargaTipoRegistro(
				        registroGuardar.getId()
				                .getCoCargaTipoRegistro());
				auxiliar.setBoInclusion(crit.getBoInclusion());
				listaCriteriosGuardar.add(auxiliar);
			}
		
			this.saveCriteriosIncExc(
					listaCriteriosGuardar);
		// ahora recuperamos la lista de condiciones de los criterios del anterior
			final List<CargaCriterioCondicionDTO> listaCriteriosCond = this
	        .cargaCriterioCondicionDao.findFiltered(
	                parametros, valores);
		// y los borramos todos
			if (listaCriteriosCond != null) {
				for (final CargaCriterioCondicionDTO cCond : listaCriteriosCond) {
					this.cargaCriterioCondicionDao.delete(
					        cCond.getId());
				}
			}
			// y ahora los guardamos con el nuevo tipo de registro
			if (listaCriteriosCond != null) {
				for (int i = 0; i < listaCriteriosCond.size(); i++) {
					CargaCriterioCondicionDTO auxiliar = new CargaCriterioCondicionDTO();
					auxiliar.setId(new CargaCriterioCondicionDTOId());
					auxiliar.getId().setCoCarga(listaCriteriosCond.get(i).getId().getCoCarga());
					auxiliar.getId().setCoCargaCriterio(listaCriteriosCond.get(i).getId().getCoCargaCriterio());
					auxiliar.getId().setCoCargaCriterioCondicion(listaCriteriosCond.get(i).getId().getCoCargaCriterioCondicion());
					auxiliar.getId().setCoCargaTipoRegistro(
					                registroGuardar
					                        .getId()
					                        .getCoCargaTipoRegistro());
					auxiliar.setConector(listaCriteriosCond.get(i).getConector());
					auxiliar.setOperador(listaCriteriosCond.get(i).getOperador());
					auxiliar.setPosFin(listaCriteriosCond.get(i).getPosFin());
					auxiliar.setPosIni(listaCriteriosCond.get(i).getPosIni());
					auxiliar.setValor(listaCriteriosCond.get(i).getValor());
					auxiliar.setFhActualizacion(Utilidades.getDateActual());
					auxiliar.setCoUsuarioActualizacion(DatosSesion.getLogin());
					this.cargaCriterioCondicionDao.save(
					        auxiliar);
				}
			}

			
			// ya podemos borrar todos los criterios anteriores
			this.deleteCriteriosIncExc(
			        listaCriterios);
		
			
			//recuperamos las estructuras del tipo de registro anterior
			
			final List<CargaEstructuraDTO> listaPosiciones = this
			        .findBoPosicionesCasillasReg(
			        		registroGuardar.getId().getCoCarga(),
			                registro00.getId()
			                        .getCoCargaTipoRegistro());
			// y las borramos
			this.deletePosicionesCasillas(
			        listaPosiciones);
			//damos de alta las nuevas pero con el nuevo tipo registro
			List<CargaEstructuraDTO> listaPosicionesGuardar = new ArrayList<CargaEstructuraDTO>();
			for (final CargaEstructuraDTO cEst : listaPosiciones) {
				CargaEstructuraDTO auxiliar = new CargaEstructuraDTO();
				auxiliar.setId(new CargaEstructuraDTOId());
				auxiliar.setPosFin(cEst.getPosFin());
				auxiliar.setPosIni(cEst.getPosIni());
				auxiliar.getId().setNuCasilla(cEst.getId().getNuCasilla());
				auxiliar.getId().setCoCarga(cEst.getId().getCoCarga());
				auxiliar.getId().setCoCargaTipoRegistro(
				        registroGuardar.getId()
				                .getCoCargaTipoRegistro());
				listaPosicionesGuardar.add(auxiliar);
			}

			this.savePosicionesCasillas(
					listaPosicionesGuardar);

			//borramos el tipo de registro anterior
			cargaTipoRegistroDao.delete(registro00.getId());
			
		}
		catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error en el método guardarTipoRegistroPorDefecto",
			        e);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 * @throws GadirServiceException 
	 */
	public void eliminarCreandoRegistroDefecto(final CargaTipoRegistroDTO registro) throws GadirServiceException{
		try{
			
			registro.getCargaDTO().setTipregPosFin(null);
			registro.getCargaDTO().setTipregPosIni(null);
			registro.getCargaDTO().setFhActualizacion(Utilidades.getDateActual());
			registro.getCargaDTO().setCoUsuarioActualizacion(DatosSesion.getLogin());
			this.cargaDao.save(registro.getCargaDTO());
			
			final CargaTipoRegistroDTO origen = registro;
			final CargaTipoRegistroDTO destino = new CargaTipoRegistroDTO();
			destino.setId(new CargaTipoRegistroDTOId(registro.getId().getCoCarga(),
			        ValoresParametrosConfig.VALOR_TIPO_REGISTRO_DEFECTO));
			destino.setBoCreaHoja1(true);
			destino.setBoCreaHojaN(false);
			final List<CargaCriterioCondicionDTO> listaCriteriosCondiciones = this
			        .obtenerListaCriteriosCond(origen.getId());
			final List<CargaCriterioDTO> listaCriterios = this
			        .obtenerListaCriterios(origen.getId());
			final List<CargaEstructuraDTO> listaEstructuras = this
			        .obtenerListaEstructuras(origen.getId());
	
			this.deleteTipoRegistro(origen.getId());
	
			// Almacenamos el TR destino
	
			this.save(destino);
	
			List<CargaCriterioDTO> listaCriteriosGuardar = new ArrayList<CargaCriterioDTO>(); 
			for (int i = 0; i < listaCriterios.size(); i++) {
				CargaCriterioDTO auxiliar = new CargaCriterioDTO();
				auxiliar.setId(new CargaCriterioDTOId());
				auxiliar.getId().setCoCarga(
				        destino.getId().getCoCarga());
				auxiliar.getId().setCoCargaTipoRegistro(
				        destino.getId().getCoCargaTipoRegistro());
				auxiliar.getId().setCoCargaCriterio(listaCriterios.get(i).getId().getCoCargaCriterio());
				auxiliar.setBoInclusion(listaCriterios.get(i).getBoInclusion());
				listaCriteriosGuardar.add(auxiliar);
			}
	
			this.saveCriteriosIncExc(listaCriteriosGuardar);
	
			for (int i = 0; i < listaCriteriosCondiciones.size(); i++) {
				CargaCriterioCondicionDTO auxiliar = new CargaCriterioCondicionDTO();
				auxiliar.setId(new CargaCriterioCondicionDTOId());
				auxiliar.getId().setCoCarga(
				        destino.getId().getCoCarga());
				auxiliar.getId().setCoCargaTipoRegistro(
				        destino.getId().getCoCargaTipoRegistro());
				auxiliar.getId().setCoCargaCriterio(listaCriteriosCondiciones.get(i).getId().getCoCargaCriterio());
				auxiliar.getId().setCoCargaCriterioCondicion(listaCriteriosCondiciones.get(i).getId().getCoCargaCriterioCondicion());
				auxiliar.setConector(listaCriteriosCondiciones.get(i).getConector());
				auxiliar.setOperador(listaCriteriosCondiciones.get(i).getOperador());
				auxiliar.setPosFin(listaCriteriosCondiciones.get(i).getPosFin());
				auxiliar.setPosIni(listaCriteriosCondiciones.get(i).getPosIni());
				auxiliar.setValor(listaCriteriosCondiciones.get(i).getValor());
				auxiliar.setFhActualizacion(Utilidades.getDateActual());
				auxiliar.setCoUsuarioActualizacion(DatosSesion.getLogin());
				this.cargaCriterioCondicionDao
				        .save(auxiliar);
			}
	
			List<CargaEstructuraDTO> listaEstructuraGuardar = new ArrayList<CargaEstructuraDTO>();
			for (int i = 0; i < listaEstructuras.size(); i++) {
				
				CargaEstructuraDTO auxiliar = new CargaEstructuraDTO();
				auxiliar.setId(new CargaEstructuraDTOId());
				auxiliar.setPosFin(listaEstructuras.get(i).getPosFin());
				auxiliar.setPosIni(listaEstructuras.get(i).getPosIni());
				auxiliar.getId().setNuCasilla(listaEstructuras.get(i).getId().getNuCasilla());
				auxiliar.getId().setCoCarga(
				        destino.getId().getCoCarga());
				auxiliar.getId().setCoCargaTipoRegistro(
				        destino.getId().getCoCargaTipoRegistro());
				listaEstructuraGuardar.add(auxiliar);
			}
			this.savePosicionesCasillas(listaEstructuraGuardar);
		}
		catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error en el método eliminarCreandoRegistroDefecto",
			        e);
		}
	}
	
	@Override
	public DAOBase<CargaTipoRegistroDTO, CargaTipoRegistroDTOId> getDao() {
		return this.getCargaTipoRegistroDao();
	}

	/**
	 * @return the cargaTipoRegistroDao
	 */
	public DAOBase<CargaTipoRegistroDTO, CargaTipoRegistroDTOId> getCargaTipoRegistroDao() {
		return cargaTipoRegistroDao;
	}

	/**
	 * @param cargaTipoRegistroDao
	 *            the cargaTipoRegistroDao to set
	 */
	public void setCargaTipoRegistroDao(
	        final DAOBase<CargaTipoRegistroDTO, CargaTipoRegistroDTOId> cargaTipoRegistroDao) {
		this.cargaTipoRegistroDao = cargaTipoRegistroDao;
	}

	/**
	 * @return the cargaCriterioDao
	 */
	public DAOBase<CargaCriterioDTO, CargaCriterioDTOId> getCargaCriterioDao() {
		return cargaCriterioDao;
	}

	/**
	 * @param cargaCriterioDao
	 *            the cargaCriterioDao to set
	 */
	public void setCargaCriterioDao(
	        final DAOBase<CargaCriterioDTO, CargaCriterioDTOId> cargaCriterioDao) {
		this.cargaCriterioDao = cargaCriterioDao;
	}

	/**
	 * @return the cargaEstructuraDao
	 */
	public DAOBase<CargaEstructuraDTO, CargaEstructuraDTOId> getCargaEstructuraDao() {
		return cargaEstructuraDao;
	}

	/**
	 * @param cargaEstructuraDao
	 *            the cargaEstructuraDao to set
	 */
	public void setCargaEstructuraDao(
	        final DAOBase<CargaEstructuraDTO, CargaEstructuraDTOId> cargaEstructuraDao) {
		this.cargaEstructuraDao = cargaEstructuraDao;
	}

	/**
	 * Método que devuelve el atributo cargaCriterioCondicionDao.
	 * 
	 * @return cargaCriterioCondicionDao.
	 */
	public DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> getCargaCriterioCondicionDao() {
		return cargaCriterioCondicionDao;
	}

	/**
	 * Método que establece el atributo cargaCriterioCondicionDao.
	 * 
	 * @param cargaCriterioCondicionDao
	 *            El cargaCriterioCondicionDao.
	 */
	public void setCargaCriterioCondicionDao(
	        final DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> cargaCriterioCondicionDao) {
		this.cargaCriterioCondicionDao = cargaCriterioCondicionDao;
	}

	/**
     * @return the cargaDao
     */
    public DAOBase<CargaDTO, Long> getCargaDao() {
    	return cargaDao;
    }

	/**
     * @param cargaDao the cargaDao to set
     */
    public void setCargaDao(DAOBase<CargaDTO, Long> cargaDao) {
    	this.cargaDao = cargaDao;
    }
    
    public void auditorias(CargaTipoRegistroDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		CargaDTO carga = getCargaDao().findById(transientObject.getId().getCoCarga());
		carga.setFhActualizacion(Utilidades.getDateActual());
		carga.setCoUsuarioActualizacion(DatosSesion.getLogin());
		getCargaDao().save(carga);
		
	}
}
