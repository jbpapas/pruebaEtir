package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.List;

import es.dipucadiz.etir.comun.bo.FuncionArgumentoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTO;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link FuncionArgumentoDTO}.
 * 
 * @version 1.0 17/12/2009
 * @author SDS[FJTORRES]
 */
@SuppressWarnings("unchecked")
public class FuncionArgumentoBOImpl extends
        AbstractGenericBOImpl<FuncionArgumentoDTO, FuncionArgumentoDTOId>
        implements FuncionArgumentoBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -3787197847596135773L;

	/**
	 * Atributo que almacena el dao asociado a {@link FuncionArgumentoDTO}.
	 */
	private DAOBase<FuncionArgumentoDTO, FuncionArgumentoDTOId> funcionArgumentoDao;

	/**
	 * {@inheritDoc}
	 */
	public List<FuncionArgumentoDTO> findArgumentosFuncion(
			final String coFuncion, final boolean salida)
	        throws GadirServiceException {
		if (Utilidades.isEmpty(coFuncion)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El código recibido es null, no se recupera nada del sistema.");
			}
			return Collections.EMPTY_LIST;
		}
		try {
			final String[] props = { "id.coFuncion", "boEntrada" };
			final Object[] values = { coFuncion, !salida };
			return this.getFuncionArgumentoDao().findFiltered(props, values, "id.coArgumentoFuncion", DAOConstant.ASC_ORDER);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener los argumentos de la funcion: "
			                + coFuncion, e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<FuncionArgumentoDTO> findArgumentosFuncion(
	        final String coFuncion) throws GadirServiceException {
		if (Utilidades.isEmpty(coFuncion)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El código recibido es null, no se recupera nada del sistema.");
			}
			return Collections.EMPTY_LIST;
		}
		try {
			return this.getFuncionArgumentoDao().findFiltered(
			        "id.coFuncion", coFuncion);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener los argumentos de la funcion: "
			                + coFuncion, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<FuncionArgumentoDTO, FuncionArgumentoDTOId> getDao() {
		return this.getFuncionArgumentoDao();
	}

	/**
	 * Método que devuelve el atributo funcionArgumentoDao.
	 * 
	 * @return funcionArgumentoDao.
	 */
	public DAOBase<FuncionArgumentoDTO, FuncionArgumentoDTOId> getFuncionArgumentoDao() {
		return funcionArgumentoDao;
	}

	/**
	 * Método que establece el atributo funcionArgumentoDao.
	 * 
	 * @param funcionArgumentoDao
	 *            El funcionArgumentoDao.
	 */
	public void setFuncionArgumentoDao(
	        final DAOBase<FuncionArgumentoDTO, FuncionArgumentoDTOId> funcionArgumentoDao) {
		this.funcionArgumentoDao = funcionArgumentoDao;
	}
	
	public void auditorias(FuncionArgumentoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
