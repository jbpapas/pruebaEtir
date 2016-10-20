package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.CasillaModeloBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTO;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTOId;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class CasillaModeloBOImpl extends
        AbstractGenericBOImpl<CasillaModeloDTO, CasillaModeloDTOId> implements
        CasillaModeloBO {

	private static final long serialVersionUID = -7782858574282967022L;

	private static final String CAMPO_CO_VER = "id.coVersion";

	private static final String CAMPO_CO_MOD = "id.coModelo";

	private DAOBase<CasillaModeloDTO, CasillaModeloDTOId> casillaModeloDao;
	
	private DAOBase<ModeloVersionDTO, ModeloVersionDTOId> modeloVersionDao;
	
	private DAOBase<ModeloDTO, String> modeloDao;
	


	/**
	 * {@inheritDoc}
	 */
	public List<CasillaModeloDTO> findCasillasModeloByVersionModelo(
	        final String codVersion, final String codModelo)
	        throws GadirServiceException {
		List<CasillaModeloDTO> resultado;
		try {
			if (Utilidades.isNotEmpty(codModelo)
			        && Utilidades.isNotEmpty(codVersion)) {
				resultado = this.findFiltered(new String[] { CAMPO_CO_MOD,
				        CAMPO_CO_VER }, new Object[] { codModelo, codVersion }, "id.nuCasilla", DAOConstant.ASC_ORDER);
			} else if (Utilidades.isNotEmpty(codModelo)) {
				resultado = this.findFiltered(CAMPO_CO_MOD, codModelo);
			} else if (Utilidades.isNotEmpty(codVersion)) {
				resultado = this.findFiltered(CAMPO_CO_VER, codVersion);
			} else {
				resultado = this.findAll();
			}
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error obtener las casillas.", e);
		}
		return resultado;
	}


	public List<CasillaModeloDTO> findCasillasALigar(
	        final CasillaModeloDTOId idCasilla) throws GadirServiceException {
		try {
			final Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("codModelo", idCasilla.getCoModelo());
			params.put("codVersion", idCasilla.getCoVersion());
			params.put("nuCasilla", idCasilla.getNuCasilla());

			return this.getCasillaModeloDao().findByNamedQuery(
			        QueryName.CASILLA_MODELO_CASILLAS_A_LIGAR, params);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener las casillas que se pueden ligar a una dada.",
			        e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> tieneRelacionesNoEliminables(final CasillaModeloDTOId id)
	        throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("codVersion", id.getCoVersion());
		params.put("codModelo", id.getCoModelo());
		params.put("codCasilla", id.getNuCasilla());
		try {
			
			List<Integer> errores = new ArrayList<Integer>();
			
			List<Object> resultado = (List<Object>) this.getDao().ejecutaNamedQuerySelect(
			        "CasillaModeloDTO.tieneRelacionesNoEliminables", params);
			if (!resultado.isEmpty()) {
				for(int i=0; i<resultado.size(); i++){
					errores.add(Integer.parseInt(resultado.get(i).toString()));
				}
			} else {
				errores.add(0);
			}
			
			return errores;
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al comprobar las relaciones del ModeloVersion dado.",
			        e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<CasillaModeloDTO, CasillaModeloDTOId> getDao() {
		return this.getCasillaModeloDao();
	}

	/**
	 * Método que devuelve el atributo casillaModeloDao.
	 * 
	 * @return casillaModeloDao.
	 */
	public DAOBase<CasillaModeloDTO, CasillaModeloDTOId> getCasillaModeloDao() {
		return casillaModeloDao;
	}

	/**
	 * Método que establece el atributo casillaModeloDao.
	 * 
	 * @param casillaModeloDao
	 *            El casillaModeloDao.
	 */
	public void setCasillaModeloDao(
	        final DAOBase<CasillaModeloDTO, CasillaModeloDTOId> casillaModeloDao) {
		this.casillaModeloDao = casillaModeloDao;
	}
	
	public void auditorias(CasillaModeloDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		Date fechaActual = Utilidades.getDateActual();
		String usuario = DatosSesion.getLogin();
		transientObject.setFhActualizacion(fechaActual);
		transientObject.setCoUsuarioActualizacion(usuario);
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		//actualizamos el modelo version
		ModeloVersionDTO modeVer = getModeloVersionDao().findById(new ModeloVersionDTOId(transientObject.getId().getCoModelo(),
				transientObject.getId().getCoVersion()));
		modeVer.setFhActualizacion(fechaActual);
		modeVer.setCoUsuarioActualizacion(usuario);
		getModeloVersionDao().save(modeVer);
		//actualizamos el modelo
		ModeloDTO mode = getModeloDao().findById(modeVer.getId().getCoModelo());
		mode.setFhActualizacion(fechaActual);
		mode.setCoUsuarioActualizacion(usuario);
		getModeloDao().save(mode);
	
	}

	/**
	 * @return the modeloVersionDao
	 */
	public DAOBase<ModeloVersionDTO, ModeloVersionDTOId> getModeloVersionDao() {
		return modeloVersionDao;
	}

	/**
	 * @param modeloVersionDao the modeloVersionDao to set
	 */
	public void setModeloVersionDao(
			DAOBase<ModeloVersionDTO, ModeloVersionDTOId> modeloVersionDao) {
		this.modeloVersionDao = modeloVersionDao;
	}

	/**
	 * @return the modeloDao
	 */
	public DAOBase<ModeloDTO, String> getModeloDao() {
		return modeloDao;
	}

	/**
	 * @param modeloDao the modeloDao to set
	 */
	public void setModeloDao(DAOBase<ModeloDTO, String> modeloDao) {
		this.modeloDao = modeloDao;
	}


	


	
}
