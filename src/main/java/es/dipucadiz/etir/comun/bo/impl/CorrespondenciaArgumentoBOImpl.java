package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.CorrespondenciaArgumentoBO;
import es.dipucadiz.etir.comun.bo.CorrespondenciaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CorrespondenciaArgumentoDTO;
import es.dipucadiz.etir.comun.dto.CorrespondenciaArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.CorrespondenciaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class CorrespondenciaArgumentoBOImpl
        extends
        AbstractGenericBOImpl<CorrespondenciaArgumentoDTO, CorrespondenciaArgumentoDTOId>
        implements CorrespondenciaArgumentoBO {

	private static final long serialVersionUID = -3754864533089540947L;

	private DAOBase<CorrespondenciaArgumentoDTO, CorrespondenciaArgumentoDTOId> correspondenciaArgumentoDao;
	
	private CorrespondenciaBO correspondenciaBO;

	/**
	 * {@inheritDoc}
	 */
	public List<CorrespondenciaArgumentoDTO> findArgumentosCorrespondencia(
	        final CorrespondenciaDTO corr) throws GadirServiceException {
		if (Utilidades.isNull(corr)) {
			if (log.isDebugEnabled()) {
				log.debug("Los datos de filtrado llegaron null");
			}
			return Collections.emptyList();
		} else {
			try {
				final Map<String, Object> params = new HashMap<String, Object>(
				        1);
				params.put("codCorrespondencia", corr.getCoCorrespondencia());
				return this.getDao().findByNamedQuery(
				        QueryName.ARGUMENTO_COCORRESPONDENCIA, params);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al obtener el listado de argumentos de correspondencia.",
				        e);
			}
		}
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
	        DAOBase<CorrespondenciaArgumentoDTO, CorrespondenciaArgumentoDTOId> correspondenciaArgumentoDao) {
		this.correspondenciaArgumentoDao = correspondenciaArgumentoDao;
	}

	@Override
	public DAOBase<CorrespondenciaArgumentoDTO, CorrespondenciaArgumentoDTOId> getDao() {
		return correspondenciaArgumentoDao;
	}
	
	public void auditorias(CorrespondenciaArgumentoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		
		CorrespondenciaDTO correspondencia = getCorrespondenciaBO().findById(transientObject.getId().getCoCorrespondencia());
		getCorrespondenciaBO().auditorias(correspondencia, false);
	}

	/**
	 * @return the correspondenciaBO
	 */
	public CorrespondenciaBO getCorrespondenciaBO() {
		return correspondenciaBO;
	}

	/**
	 * @param correspondenciaBO the correspondenciaBO to set
	 */
	public void setCorrespondenciaBO(CorrespondenciaBO correspondenciaBO) {
		this.correspondenciaBO = correspondenciaBO;
	}
	

}
