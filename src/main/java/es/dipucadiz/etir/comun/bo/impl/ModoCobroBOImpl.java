package es.dipucadiz.etir.comun.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.ModoCobroBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.ModoCobroDTO;
import es.dipucadiz.etir.comun.dto.SituacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link ModoCobroDTO}.
 * 
 */
public class ModoCobroBOImpl extends
        AbstractGenericBOImpl<ModoCobroDTO, String> implements
        ModoCobroBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6565233459057641800L;

	/**
	 * Atributo que almacena el DAO asociado a {@link ModoCobroDTO}.
	 */
	private DAOBase<ModoCobroDTO, String> modoCobroDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<ModoCobroDTO, String> getDao() {
		return this.getModoCobroDao();
	}

	/**
	 * Método que devuelve el atributo modoCobroDao.
	 * 
	 * @return modoCobroDao.
	 */
	public DAOBase<ModoCobroDTO, String> getModoCobroDao() {
		return modoCobroDao;
	}
	
	/**
	 * Método que establece el atributo modoCobroDao.
	 * 
	 * @param modoCobroDao
	 *            El modoCobroDao.
	 */
	public void setModoCobroDao(final DAOBase<ModoCobroDTO, String> modoCobroDao) {
		this.modoCobroDao = modoCobroDao;
	}
	
	public void auditorias(ModoCobroDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

	public List<ModoCobroDTO> findByEstadoOrigen(String coEstado ) throws GadirServiceException
	{
		final Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("coEstado", coEstado);
		
		return this.getDao().findByNamedQuery(QueryName.MODOSCOBRO_BY_ESTADO_ORIGEN, params);
	}
	
	public List<ModoCobroDTO> findByEstadoDestino(String coEstado ) throws GadirServiceException
	{
		final Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("coEstado", coEstado);
		
		return this.getDao().findByNamedQuery(QueryName.MODOSCOBRO_BY_ESTADO_DESTINO, params);
	}
	
	public List<ModoCobroDTO> findAll() throws GadirServiceException {
		try {
			return getDao().findAll("coModoCobro", DAOConstant.ASC_ORDER);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener el listado.", e);
		}
	}
	
	
}
