package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.CorrespondenciaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CorrespondenciaArgumentoDTO;
import es.dipucadiz.etir.comun.dto.CorrespondenciaArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.CorrespondenciaDTO;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTO;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link CorrespondenciaDTO}.
 * 
 * @version 1.0 09/11/2009
 * @author SDS[FJTORRES]
 */
public class CorrespondenciaBOImpl extends
        AbstractGenericBOImpl<CorrespondenciaDTO, Long> implements
        CorrespondenciaBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -5442785481519973934L;

	/**
	 * Atributo que almacena el dao asociado a {@link CorrespondenciaDTO}.
	 */
	private DAOBase<CorrespondenciaDTO, Long> correspondenciaDao;

	/**
	 * Atributo que almacena el dao asociadoa {@link ValidacionArgumentoDTO}.
	 */
	private DAOBase<CorrespondenciaArgumentoDTO, CorrespondenciaArgumentoDTOId> correspondenciaArgumentoDao;

	/**
	 * Atributo que almacena el funcionArgumentoDao de la clase.
	 */
	private DAOBase<FuncionArgumentoDTO, FuncionArgumentoDTOId> funcionArgumentoDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<CorrespondenciaDTO, Long> getDao() {
		return this.getCorrespondenciaDao();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CorrespondenciaDTO> findCorrespondenciaByParam(
	        final CorrespondenciaDTO filtro) throws GadirServiceException {
		if (Utilidades.isNull(filtro)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El filtro de la consulta llegó null, no se recupera ningún resultado");
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
				        QueryName.CORRESPONDENCIA_FIND_BY_PARAM, params);
			} catch (final GadirServiceException ge) {
				throw ge;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener las correspondencias.", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CorrespondenciaDTO> findBoActivaCoDocumento(
	        final String coDocumento) throws GadirServiceException {
		if (Utilidades.isEmpty(coDocumento)) {
			if (log.isDebugEnabled()) {
				log.debug("El código de filtrado llegó null");
			}
			return Collections.emptyList();
		} else {
			try {
				final HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("coDocumento", coDocumento);

				return this
				        .findByNamedQuery(
				                QueryName.CORRESPONDENCIAS_ACTIVAS_CO_DOCUMENTO,
				                params);
			} catch (final GadirServiceException ge) {
				throw ge;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener las correspondencias.", e);
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
				        .debug("El parametro con la id de la correspondencia llego null.");
			}
		} else {
			try {
				final List<CorrespondenciaArgumentoDTO> argumentos = this.correspondenciaArgumentoDao
				        .findFiltered("id.coCorrespondencia", id);
				for (final CorrespondenciaArgumentoDTO arg : argumentos) {
					this.correspondenciaArgumentoDao.delete(arg.getId());
				}
				this.correspondenciaDao.delete(id);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al eliminar la correspondencia y sus argumentos. Correspondencia: "
				                + id, e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveWithArgumentos(final CorrespondenciaDTO corr)
	        throws GadirServiceException {
		if (Utilidades.isNull(corr)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto a persistir llegó null, no se persistirá nada.");
			}
		} else {
			try {
				save(corr);
				//corr.setCoCorrespondencia((Long) this.correspondenciaDao.saveOnly(corr));
				if (corr.getCoCorrespondencia() != null) {

					final List<FuncionArgumentoDTO> listado = this
					        .getFuncionArgumentoDao().findFiltered(
					                "id.coFuncion",
					                corr.getFuncionDTO().getCoFuncion());
					for (final FuncionArgumentoDTO arg : listado) {
						final CorrespondenciaArgumentoDTO param = new CorrespondenciaArgumentoDTO();
						final CorrespondenciaArgumentoDTOId identificador = new CorrespondenciaArgumentoDTOId();
						identificador.setCoArgumentoFuncion(arg.getId()
						        .getCoArgumentoFuncion());
						identificador.setCoFuncion(arg.getId().getCoFuncion());
						identificador.setCoCorrespondencia(corr
						        .getCoCorrespondencia());
						param.setId(identificador);
						param.setFhActualizacion(Utilidades.getDateActual());
						param.setCoUsuarioActualizacion(DatosSesion.getLogin());
						this.getCorrespondenciaArgumentoDao().save(param);
					}
				}
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al guardar la correspondencia y sus argumentos.",
				        e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateWithArgumentos(final CorrespondenciaDTO corr)
	        throws GadirServiceException {
		if (Utilidades.isNull(corr)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El objeto a persistir llegó null, no se persistirá nada.");
			}
		} else {
			try {
				final List<CorrespondenciaArgumentoDTO> listado = this
				        .getCorrespondenciaArgumentoDao().findFiltered(
				                "id.coCorrespondencia",
				                corr.getCoCorrespondencia());
				for (final CorrespondenciaArgumentoDTO corrArg : listado) {
					this.getCorrespondenciaArgumentoDao().delete(
					        corrArg.getId());
				}

				this.save(corr);

				final List<FuncionArgumentoDTO> listadoFunc = this
				        .getFuncionArgumentoDao().findFiltered("id.coFuncion",
				                corr.getFuncionDTO().getCoFuncion());
				for (final FuncionArgumentoDTO arg : listadoFunc) {
					final CorrespondenciaArgumentoDTO param = new CorrespondenciaArgumentoDTO();
					final CorrespondenciaArgumentoDTOId identificador = new CorrespondenciaArgumentoDTOId();
					identificador.setCoArgumentoFuncion(arg.getId()
					        .getCoArgumentoFuncion());
					identificador.setCoFuncion(arg.getId().getCoFuncion());
					identificador.setCoCorrespondencia(corr
					        .getCoCorrespondencia());
					param.setId(identificador);
					param.setFhActualizacion(Utilidades.getDateActual());
					param.setCoUsuarioActualizacion(DatosSesion.getLogin());
					this.getCorrespondenciaArgumentoDao().save(param);
				}
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al guardar la correspondencia y sus argumentos.",
				        e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public CorrespondenciaDTO findByIdWithFuncion(final Long codigo)
	        throws GadirServiceException {
		if (Utilidades.isNull(codigo)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El dato de filtrado llegó null, no se recupera nada.");
			}
			return null;
		} else {
			try {
				final Map<String, Object> param = new HashMap<String, Object>(1);
				param.put("coCorrespondencia", codigo);
				final CorrespondenciaDTO correspondencia = (CorrespondenciaDTO) this.correspondenciaDao
				        .findByNamedQueryUniqueResult(
				                QueryName.CORRESPONDENCIA_FIND_FUNCION, param);
				if(correspondencia != null){
					Hibernate.initialize(correspondencia.getMunicipioDTO());
				}
				return correspondencia;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener la correspondencia con la funcion rellena.",
				        e);
			}
		}
	}

	// GETTERS AND SETTERS

	/**
	 * Método que devuelve el atributo correspondenciaDao.
	 * 
	 * @return correspondenciaDao.
	 */
	public DAOBase<CorrespondenciaDTO, Long> getCorrespondenciaDao() {
		return correspondenciaDao;
	}

	/**
	 * Método que establece el atributo correspondenciaDao.
	 * 
	 * @param correspondenciaDao
	 *            El correspondenciaDao.
	 */
	public void setCorrespondenciaDao(
	        final DAOBase<CorrespondenciaDTO, Long> correspondenciaDao) {
		this.correspondenciaDao = correspondenciaDao;
	}

	/**
	 * @return the correspondenciaArgumentoDao
	 */
	public DAOBase<CorrespondenciaArgumentoDTO, CorrespondenciaArgumentoDTOId> getCorrespondenciaArgumentoDao() {
		return correspondenciaArgumentoDao;
	}

	/**
	 * @param correspondenciaArgumentoDao
	 *            the correspondenciaArgumentoDao to set
	 */
	public void setCorrespondenciaArgumentoDao(
	        final DAOBase<CorrespondenciaArgumentoDTO, CorrespondenciaArgumentoDTOId> correspondenciaArgumentoDao) {
		this.correspondenciaArgumentoDao = correspondenciaArgumentoDao;
	}

	/**
	 * @return the funcionArgumentoDao
	 */
	public DAOBase<FuncionArgumentoDTO, FuncionArgumentoDTOId> getFuncionArgumentoDao() {
		return funcionArgumentoDao;
	}

	/**
	 * @param funcionArgumentoDao
	 *            the funcionArgumentoDao to set
	 */
	public void setFuncionArgumentoDao(
	        final DAOBase<FuncionArgumentoDTO, FuncionArgumentoDTOId> funcionArgumentoDao) {
		this.funcionArgumentoDao = funcionArgumentoDao;
	}
	
	public void auditorias(CorrespondenciaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
