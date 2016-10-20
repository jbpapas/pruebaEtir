package es.dipucadiz.etir.comun.bo.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.CargaCriterioBO;
import es.dipucadiz.etir.comun.bo.CargaCriterioCondicionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioCondicionDTOId;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTO;
import es.dipucadiz.etir.comun.dto.CargaCriterioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link CargaCriterioCondicionDTO}.
 * 
 * @version 1.0 01/12/2009
 * @author SDS[FJTORRES]
 */
public class CargaCriterioCondicionBOImpl
        extends
        AbstractGenericBOImpl<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId>
        implements CargaCriterioCondicionBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -2229452782628885928L;

	/**
	 * Atributo que almacena el dao asociado a {@link CargaCriterioCondicionDTO}
	 * .
	 */
	private DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> cargaCriterioCondicionDao;

	/**
	 * Atributo que almacena el dao asociado a {@link CargaCriterioDTO}.
	 */
	private DAOBase<CargaCriterioDTO, CargaCriterioDTOId> cargaCriterioDao;
	
	private CargaCriterioBO cargaCriterioBO;

	/**
	 * {@inheritDoc}
	 */
	public void saveWithCargaCriterio(final CargaCriterioCondicionDTO condicion)
	        throws GadirServiceException {
		try {
			if(Utilidades.isNotNull(condicion.getCargaCriterioDTO())) {
				condicion.getCargaCriterioDTO().setFhActualizacion(Utilidades.getDateActual());
				condicion.getCargaCriterioDTO().setCoUsuarioActualizacion(DatosSesion.getLogin());
			}
			this.getCargaCriterioDao()
			        .saveOnly(condicion.getCargaCriterioDTO());
			this.save(condicion);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al grabar la condicion"
			                + "del criterio de carga, junto con el propio criterio de carga.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public byte getNextClave(final CargaCriterioDTOId idCriterio)
	        throws GadirServiceException {
		byte valor = 0;
		// Obtenemos las condiciones.
		final List<CargaCriterioCondicionDTO> lista = this
		        .findListaCondicioneCriterio(idCriterio);

		for (final CargaCriterioCondicionDTO cond : lista) {
			if (cond.getId().getCoCargaCriterioCondicion() > valor) {
				valor = cond.getId().getCoCargaCriterioCondicion();
			}
		}
		valor += 1;
		return valor;
	}

	
	
	/**
	 * {@inheritDoc}
	 * @throws GadirServiceException 
	 */
	@SuppressWarnings("unchecked")
	public List<CargaCriterioCondicionDTO> findCondicionesIniciales(
	        final CargaCriterioDTOId idCriterio, final boolean filtroCriterio) throws GadirServiceException {
	
		if (idCriterio == null /*
								 * || idCriterio.getCargaTipoRegistroDTO() ==
								 * null
								 */) {
			return Collections.EMPTY_LIST;
		}
		try {
		
		// Iniciamos la lista de resultado.
		List<CargaCriterioCondicionDTO> lista = Collections.EMPTY_LIST;
		
		final Map<String, Object> params = new HashMap<String, Object>();
		//params.put("codTipoRegistro", idCriterio.getCoCargaTipoRegistro());
		params.put("codEstructura", idCriterio.getCoCarga());
		// Si tenemos que filtrar por criterio añadimos el trozo de query y
		// generamos la nueva.
		if (filtroCriterio){
			params.put("codCriterio", idCriterio.getCoCargaCriterio());
			lista = this.getCargaCriterioCondicionDao().findByNamedQuery(
			        QueryName.CONDICIONES_SIN_CONECTOR_CON_CRITERIO, params);
		}else{
		
		lista = this.getCargaCriterioCondicionDao().findByNamedQuery(
		        QueryName.CONDICIONES_SIN_CONECTOR, params);
		}


		return lista;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException("Ocurrio un error al obtener las "
			        + "condiciones iniciales del criterio de carga.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CargaCriterioCondicionDTO> findListaCondicioneCriterio(
	        final CargaCriterioDTOId idCriterio) throws GadirServiceException {
		try {
			final String[] propNames = new String[] { "id.coCargaCriterio",
			        "id.coCargaTipoRegistro", "id.coCarga" };
			final Object[] filters = new Object[] {
			        idCriterio.getCoCargaCriterio(),
			        idCriterio.getCoCargaTipoRegistro(),
			        idCriterio.getCoCarga() };
			final String[] orders = new String[] { "id.coCargaCriterioCondicion" };

			final List<CargaCriterioCondicionDTO> lista = this
			        .getCargaCriterioCondicionDao().findFiltered(propNames,
			                filters, orders, null);
			return lista;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException("Ocurrio un error al obtener las "
			        + "condiciones del criterio de carga.", e);
		}
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
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<CargaCriterioCondicionDTO, CargaCriterioCondicionDTOId> getDao() {
		return this.getCargaCriterioCondicionDao();
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
	
	public void auditorias(CargaCriterioCondicionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		transientObject.setCargaCriterioDTO(cargaCriterioBO.findById(new CargaCriterioDTOId(
				transientObject.getId().getCoCarga(),
				transientObject.getId().getCoCargaTipoRegistro(),
				transientObject.getId().getCoCargaCriterio())));
		
		cargaCriterioBO.auditorias(transientObject.getCargaCriterioDTO(), false);
	}

	/**
	 * @return the cargaCriterioBO
	 */
	public CargaCriterioBO getCargaCriterioBO() {
		return cargaCriterioBO;
	}

	/**
	 * @param cargaCriterioBO the cargaCriterioBO to set
	 */
	public void setCargaCriterioBO(CargaCriterioBO cargaCriterioBO) {
		this.cargaCriterioBO = cargaCriterioBO;
	}
	

}
