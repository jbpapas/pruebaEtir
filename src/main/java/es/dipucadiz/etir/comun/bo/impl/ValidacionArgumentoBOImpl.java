package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.ValidacionArgumentoBO;
import es.dipucadiz.etir.comun.bo.ValidacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.ValidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


@SuppressWarnings("unchecked")
public class ValidacionArgumentoBOImpl extends
        AbstractGenericBOImpl<ValidacionArgumentoDTO, ValidacionArgumentoDTOId>
        implements ValidacionArgumentoBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -3754864533089540947L;
	
	/**
	 * Atributo que almacena el servicio de {@link ValidacionDTO}.
	 */
	private ValidacionBO validacionBO;

	/**
	 * Atributo que almacena el DAO asociado a {@link ValidacionArgumentoDTO}.
	 */
	private DAOBase<ValidacionArgumentoDTO, ValidacionArgumentoDTOId> dao;

	/**
	 * {@inheritDoc}
	 */
	public List<ValidacionArgumentoDTO> findArgumentos(final Long coValidacion)
	        throws GadirServiceException {
		if (Utilidades.isNull(coValidacion)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El código recibido es null, no se recupera nada del sistema.");
			}
			return Collections.emptyList();
		} else {
			try {

				List<ValidacionArgumentoDTO> listaTmp;
				if (coValidacion == null) {
					listaTmp = Collections.EMPTY_LIST;
				} else {
					listaTmp = this.getDao().findFiltered("id.coValidacion", coValidacion);
				}
				List<ValidacionArgumentoDTO> lista = new ArrayList<ValidacionArgumentoDTO>();
				for (ValidacionArgumentoDTO dto : listaTmp) {
					if (dto.getTipo() != null) {
						Hibernate.initialize(dto.getFuncionArgumentoDTO());
						lista.add(dto);
					}
				}
				return lista;
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener los argumentos de la validacion: "
				                + coValidacion);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ValidacionArgumentoDTO> findArgumentosValidacion(
	        final ValidacionDTO val) throws GadirServiceException {
		if (Utilidades.isNull(val)) {
			if (log.isDebugEnabled()) {
				log.debug("Los datos de filtrado llegaron null, no se recupera nada del sistema");
			}
			return Collections.emptyList();
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>(
				        1);
				params.put("codValidacion", val.getCoValidacion());
				return this.getDao().findByNamedQuery(
				        QueryName.ARGUMENTO_VALIDACION_COVALIDACION, params);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener el listado de argumentos de validaciones.",
				        e);
			}
		}
	}
	

	/**
	 * {@inheritDoc}
	 */
	public ValidacionArgumentoDTO findByIdWithFuncionArgumento(
	        final ValidacionArgumentoDTOId id) throws GadirServiceException {
		if (Utilidades.isNull(id)) {
			if (log.isDebugEnabled()) {
				log.debug("El identificador del objeto a recuperar llegó null");
			}
			return null;
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>(
				        3);
				params.put("codValidacion", id.getCoValidacion());
				params.put("codFuncion", id.getCoFuncion());
				params.put("codArgumento", id.getCoArgumentoFuncion());
				return (ValidacionArgumentoDTO) this.getDao()
				        .findByNamedQueryUniqueResult(
				                QueryName.VALIDACIONARG_WITH_FUNCARG, params);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener el listado de argumentos de validaciones.",
				        e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<ValidacionArgumentoDTO, ValidacionArgumentoDTOId> getDao() {
		return dao;
	}

	/**
	 * Método que establece el atributo dao.
	 * 
	 * @param dao
	 *            El dao.
	 */
	public void setDao(
	        final DAOBase<ValidacionArgumentoDTO, ValidacionArgumentoDTOId> dao) {
		this.dao = dao;
	}
	
	public void auditorias(ValidacionArgumentoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		ValidacionDTO val = getValidacionBO().findById(transientObject.getId().getCoValidacion());
		getValidacionBO().auditorias(val, false);
	}

	/**
	 * @return the validacionBO
	 */
	public ValidacionBO getValidacionBO() {
		return validacionBO;
	}

	/**
	 * @param validacionBO the validacionBO to set
	 */
	public void setValidacionBO(ValidacionBO validacionBO) {
		this.validacionBO = validacionBO;
	}
	

}
