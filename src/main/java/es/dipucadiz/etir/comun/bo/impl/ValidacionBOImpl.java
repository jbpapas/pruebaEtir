package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.ValidacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTO;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.ValidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class ValidacionBOImpl extends
        AbstractGenericBOImpl<ValidacionDTO, Long> implements ValidacionBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -2340497702134956775L;

	/**
	 * Atributo que almacena el dao asociadoa {@link ValidacionDTO}.
	 */
	private DAOBase<ValidacionDTO, Long> validacionDao;

	/**
	 * Atributo que almacena el dao asociadoa {@link ValidacionArgumentoDTO}.
	 */
	private DAOBase<ValidacionArgumentoDTO, ValidacionArgumentoDTOId> validacionArgumentoDao;

	/**
	 * Atributo que almacena el funcionArgumentoDao de la clase.
	 */
	private DAOBase<FuncionArgumentoDTO, FuncionArgumentoDTOId> funcionArgumentoDao;

	
	/**
	 * {@inheritDoc}
	 */
	public List<ValidacionDTO> findValidaciones(final ValidacionDTO filtro)
	        throws GadirServiceException {
		if (Utilidades.isNull(filtro)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El filtro definido es null, se devuelve una lista vacia.");
			}
			return Collections.emptyList();
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>(
				        4);
				params.put("codMunicipio", filtro.getMunicipioDTO().getId()
				        .getCoMunicipio());
				params.put("codConcepto", filtro.getConceptoDTO()
				        .getCoConcepto());
				params.put("codModelo", filtro.getCasillaModeloDTO().getId()
				        .getCoModelo());
				params.put("codVersion", filtro.getCasillaModeloDTO().getId()
				        .getCoVersion());
				return this.getDao().findByNamedQuery(
				        QueryName.VALIDACION_OBTENER_LISTADO, params);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener el listado de validaciones.", e);
			}
		}
	}
	
	public List<ValidacionDTO> findValidacionesTipo(final ValidacionDTO filtro) throws GadirServiceException {
		if (Utilidades.isNull(filtro)) {
			if (log.isDebugEnabled()) {
				log.debug("El filtro definido es null, se devuelve una lista vacia.");
			}
			return Collections.emptyList();
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>(
						4);
				params.put("codMunicipio", filtro.getMunicipioDTO().getId()
						.getCoMunicipio());
				params.put("codConcepto", filtro.getConceptoDTO()
						.getCoConcepto());
				params.put("codModelo", filtro.getCasillaModeloDTO().getId()
						.getCoModelo());
				params.put("codVersion", filtro.getCasillaModeloDTO().getId()
						.getCoVersion());
				params.put("tipo", filtro.getTipo());
				return this.getDao().findByNamedQuery(
						QueryName.VALIDACION_OBTENER_LISTADO_CON_TIPO, params);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
		        "Error al obtener el listado de validaciones.", e);
			}
		}
}

	/**
	 * {@inheritDoc}
	 */
	public List<ValidacionDTO> findValidacionesFunciones(
	        final ValidacionDTO filtro) throws GadirServiceException {
		if (Utilidades.isNull(filtro)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El filtro definido es null, se devuelve una lista vacia.");
			}
			return Collections.emptyList();
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>(
				        6);
				params.put("codMunicipio", filtro.getMunicipioDTO().getId()
				        .getCoMunicipio());
				params.put("codConcepto", filtro.getConceptoDTO()
				        .getCoConcepto());
				params.put("codModelo", filtro.getCasillaModeloDTO().getId()
				        .getCoModelo());
				params.put("codVersion", filtro.getCasillaModeloDTO().getId()
				        .getCoVersion());
				params.put("codProvincia", filtro.getMunicipioDTO().getId()
				        .getCoProvincia());
				params.put("tipo", filtro.getTipo());
				return this.getDao().findByNamedQuery(
				        QueryName.VALIDACION_FUNCION_OBTENER_LISTADO, params);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener el listado de validaciones.", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ValidacionDTO findByIdWithCasillas(final Long coValidacion)
	        throws GadirServiceException {
		if (Utilidades.isNull(coValidacion)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El identificador recibido es null, no se recupera ningún objeto.");
			}
			return new ValidacionDTO();
		} else {
			try {
				final Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("codValidacion", coValidacion);
				return (ValidacionDTO) this.getDao().findByNamedQueryUniqueResult(
				        QueryName.VALIDACION_WITH_CASILLAS, param);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener la validación.", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteWithArgumentos(final Long id)
	        throws GadirServiceException {
		if (Utilidades.isNull(id)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El la id de la validacion llegó null, no se elimina nada.");
			}
		} else {
			try {
				final List<ValidacionArgumentoDTO> argumentos = this.validacionArgumentoDao
				        .findFiltered("id.coValidacion", id);
				for (final ValidacionArgumentoDTO arg : argumentos) {
					this.validacionArgumentoDao.delete(arg.getId());
				}
				this.validacionDao.delete(id);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al eliminar la validacion y sus argumentos. Validacion: "
				                + id, e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveWithArgumentos(final ValidacionDTO val)
	        throws GadirServiceException {
		if (Utilidades.isNull(val)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto a persistir llegó null, no se persistirá nada.");
			}
		} else {
			try {
				val.setFhActualizacion(Utilidades.getDateActual());
				val.setCoUsuarioActualizacion(DatosSesion.getLogin());
				val.setCoValidacion((Long) this.validacionDao.saveOnly(val));
				if (val.getCoValidacion() != null) {

					final List<FuncionArgumentoDTO> listado = this
					        .getFuncionArgumentoDao().findFiltered(
					                "id.coFuncion",
					                val.getFuncionDTO().getCoFuncion());
					for (final FuncionArgumentoDTO arg : listado) {
						ValidacionArgumentoDTO param = new ValidacionArgumentoDTO();
						ValidacionArgumentoDTOId identificador = new ValidacionArgumentoDTOId();
						identificador.setCoArgumentoFuncion(arg.getId()
						        .getCoArgumentoFuncion());
						identificador.setCoValidacion(val.getCoValidacion());
						identificador.setCoFuncion(val.getFuncionDTO()
						        .getCoFuncion());
						param.setId(identificador);
						param.setFhActualizacion(Utilidades.getDateActual());
						param.setCoUsuarioActualizacion(DatosSesion.getLogin());
						this.getValidacionArgumentoDao().save(param);
					}
				}
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al guardar la validacion y sus argumentos.",
				        e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateWithArgumentos(final ValidacionDTO val)
	        throws GadirServiceException {
		if (Utilidades.isNull(val)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto a persistir llegó null, no se persistirá nada.");
			}
		} else {
			try {
				boolean funcionEditada = false;
				
				final List<ValidacionArgumentoDTO> listado = this
				        .getValidacionArgumentoDao().findFiltered(
				                "id.coValidacion", val.getCoValidacion());
				if (listado.size() >0 && 
						!(listado.get(0).getId().getCoFuncion()).equals(val.getFuncionDTO().getCoFuncion())){
					//se ha editado la funcion
					funcionEditada = true;
				}
				if (funcionEditada){
					for (final ValidacionArgumentoDTO valArg : listado) {
						this.getValidacionArgumentoDao().delete(valArg.getId());
					}
				}
				

				this.save(val);
				if (funcionEditada){
					final List<FuncionArgumentoDTO> listadoFunc = this
					        .getFuncionArgumentoDao().findFiltered("id.coFuncion",
					                val.getFuncionDTO().getCoFuncion());
					for (final FuncionArgumentoDTO arg : listadoFunc) {
						ValidacionArgumentoDTO param = new ValidacionArgumentoDTO();
						ValidacionArgumentoDTOId identificador = new ValidacionArgumentoDTOId();
						identificador.setCoArgumentoFuncion(arg.getId()
						        .getCoArgumentoFuncion());
						identificador.setCoValidacion(val.getCoValidacion());
						identificador.setCoFuncion(val.getFuncionDTO()
						        .getCoFuncion());
						param.setId(identificador);
						param.setFhActualizacion(Utilidades.getDateActual());
						param.setCoUsuarioActualizacion(DatosSesion.getLogin());
						this.getValidacionArgumentoDao().save(param);
					}
				}
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al guardar la validacion y sus argumentos.",
				        e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Short getNextOrden(final ValidacionDTO validacion)
	        throws GadirServiceException {
		Short orden = 1;
		if (Utilidades.isNull(validacion)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto con los datos llegó null, no se puede calcular el orden");
			}
			orden = 0;
		} else {
			try {
				final String[] props = { "municipioDTO", "conceptoDTO",
				        "casillaModeloDTO" };
				final Object[] values = { validacion.getMunicipioDTO(),
				        validacion.getConceptoDTO(),
				        validacion.getCasillaModeloDTO() };

				// Obtenemos el numero de validaciones
				final List<ValidacionDTO> lista = this.validacionDao
				        .findFiltered(props, values);

				// Obtenemos el máximo orden de la lista
				if (null == lista) {
					orden = 1;
				} else {
					Short aux = 1;
					for (int i = 0; i < lista.size(); i++) {
						if (lista.get(i).getOrden() > aux) {
							aux = lista.get(i).getOrden();
						}
					}
					orden = aux;
				}
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener el orden de la validación.",
				        e);
			}
		}
		return orden;
	}
	
	
	@Override
	public DAOBase<ValidacionDTO, Long> getDao() {
		return this.getValidacionDao();
	}

	// GETTERS AND SETTERS

	/**
	 * Método que devuelve el atributo validacionDao.
	 * 
	 * @return validacionDao.
	 */
	public DAOBase<ValidacionDTO, Long> getValidacionDao() {
		return validacionDao;
	}

	/**
	 * Método que establece el atributo validacionDao.
	 * 
	 * @param validacionDao
	 *            El validacionDao.
	 */
	public void setValidacionDao(
	        final DAOBase<ValidacionDTO, Long> validacionDao) {
		this.validacionDao = validacionDao;
	}

	/**
	 * Método que devuelve el atributo validacionArgumentoDao.
	 * 
	 * @return validacionArgumentoDao.
	 */
	public DAOBase<ValidacionArgumentoDTO, ValidacionArgumentoDTOId> getValidacionArgumentoDao() {
		return validacionArgumentoDao;
	}

	/**
	 * Método que establece el atributo validacionArgumentoDao.
	 * 
	 * @param validacionArgumentoDao
	 *            El validacionArgumentoDao.
	 */
	public void setValidacionArgumentoDao(
	        final DAOBase<ValidacionArgumentoDTO, ValidacionArgumentoDTOId> validacionArgumentoDao) {
		this.validacionArgumentoDao = validacionArgumentoDao;
	}

	/**
	 * Método que devuelve el atributo funcionArgumentoDao.
	 * 
	 * @return funcionArgumentoDao El funcionDao.
	 */
	public DAOBase<FuncionArgumentoDTO, FuncionArgumentoDTOId> getFuncionArgumentoDao() {
		return funcionArgumentoDao;
	}

	/**
	 * Método que establece el atributo funcionDao.
	 * 
	 * @param funcionArgumentoDao
	 *            El funcionDao.
	 */
	public void setFuncionArgumentoDao(
	        DAOBase<FuncionArgumentoDTO, FuncionArgumentoDTOId> funcionArgumentoDao) {
		this.funcionArgumentoDao = funcionArgumentoDao;
	}
	
	public void auditorias(ValidacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
