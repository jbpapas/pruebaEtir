package es.dipucadiz.etir.comun.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.CargaEstructuraBO;
import es.dipucadiz.etir.comun.bo.TipoRegistroBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CargaDTO;
import es.dipucadiz.etir.comun.dto.CargaEstructuraDTO;
import es.dipucadiz.etir.comun.dto.CargaEstructuraDTOId;
import es.dipucadiz.etir.comun.dto.CargaTipoRegistroDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link CargaEstructuraDTO}.
 * 
 * @version 1.0 26/11/2009
 * @author SDS[FJTORRES]
 */
public class CargaEstructuraBOImpl extends
        AbstractGenericBOImpl<CargaEstructuraDTO, CargaEstructuraDTOId>
        implements CargaEstructuraBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 3357483241955308188L;

	/**
	 * Atributo que almacena el dao asociado a {@link CargaEstructuraDTO}.
	 */
	private DAOBase<CargaEstructuraDTO, CargaEstructuraDTOId> cargaEstructuraDao;

	/**
	 * Atributo que almacena el dao asociado a {@link CargaDTO}.
	 */
	private DAOBase<CargaDTO, Long> cargaDao;
	private TipoRegistroBO tipoRegistroBO;

	/**
	 * {@inheritDoc}
	 */
	public List<CargaEstructuraDTO> findListadoByTipoRegistro(
	        final CargaTipoRegistroDTOId idTipReg) throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			//params.put("codTipoRegistro", idTipReg.getCoCargaTipoRegistro());
			params.put("codCarga", idTipReg.getCoCarga());
			final List<CargaEstructuraDTO> result = this.getDao().findByNamedQuery(
			        QueryName.POSICIONES_CASILLAS, params);
			return result;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el listado de asociaciones de posiciones a casillas.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveAndDelete(final CargaEstructuraDTO transientObject,
	        final Short nuCasilla) throws GadirServiceException {
		try {
			final CargaEstructuraDTOId idBorrar = new CargaEstructuraDTOId();
			idBorrar.setNuCasilla(nuCasilla);
			idBorrar.setCoCarga(transientObject.getId().getCoCarga());
			idBorrar.setCoCargaTipoRegistro(transientObject.getId()
			        .getCoCargaTipoRegistro());

			this.delete(idBorrar);

			this.save(transientObject);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al grabar y borrar.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(final CargaEstructuraDTO transientObject)
	        throws GadirServiceException {
		try {

			auditorias(transientObject, false);
			final CargaDTO carga = this.cargaDao.findById(transientObject
			        .getId().getCoCarga());
			if (transientObject.getPosFin()!=null && transientObject.getPosFin() > carga.getTamRegistro()) {
				carga.setTamRegistro(transientObject.getPosFin());
				this.cargaDao.save(carga);
			}

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al grabar el objeto " + transientObject,
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<CargaEstructuraDTO, CargaEstructuraDTOId> getDao() {
		return this.getCargaEstructuraDao();
	}

	/**
	 * Método que devuelve el atributo cargaEstructuraDao.
	 * 
	 * @return cargaEstructuraDao.
	 */
	public DAOBase<CargaEstructuraDTO, CargaEstructuraDTOId> getCargaEstructuraDao() {
		return cargaEstructuraDao;
	}

	/**
	 * Método que establece el atributo cargaEstructuraDao.
	 * 
	 * @param cargaEstructuraDao
	 *            El cargaEstructuraDao.
	 */
	public void setCargaEstructuraDao(
	        final DAOBase<CargaEstructuraDTO, CargaEstructuraDTOId> cargaEstructuraDao) {
		this.cargaEstructuraDao = cargaEstructuraDao;
	}

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
	
	public void auditorias(CargaEstructuraDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		transientObject.setCargaTipoRegistroDTO(tipoRegistroBO.findById(new CargaTipoRegistroDTOId(transientObject.getId().getCoCarga(),
				transientObject.getId().getCoCargaTipoRegistro())));
		tipoRegistroBO.auditorias(transientObject.getCargaTipoRegistroDTO(), false);
	}

	/**
	 * @return the tipoRegistroBO
	 */
	public TipoRegistroBO getTipoRegistroBO() {
		return tipoRegistroBO;
	}

	/**
	 * @param tipoRegistroBO the tipoRegistroBO to set
	 */
	public void setTipoRegistroBO(TipoRegistroBO tipoRegistroBO) {
		this.tipoRegistroBO = tipoRegistroBO;
	}
	
}
