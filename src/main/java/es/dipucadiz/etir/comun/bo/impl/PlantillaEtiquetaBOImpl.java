package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.PlantillaBO;
import es.dipucadiz.etir.comun.bo.PlantillaEtiquetaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaCampoDTO;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaCampoDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.CamposTipoValorUtil;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.CampoTipoValorVO;
import es.dipucadiz.etir.sb05.vo.PlantillaEtiquetaVO;


public class PlantillaEtiquetaBOImpl extends
        AbstractGenericBOImpl<PlantillaEtiquetaDTO, PlantillaEtiquetaDTOId>
        implements PlantillaEtiquetaBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a modelo.
	 */
	private DAOBase<PlantillaEtiquetaDTO, PlantillaEtiquetaDTOId> plantillaEtiquetaDao;

	/**
	 * Atributo que almacena el plantillaEtiquetaCampoDao de la clase.
	 */
	private DAOBase<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId> plantillaEtiquetaCampoDao;

	private PlantillaBO plantillaBO;
	/**
	 * {@inheritDoc}
	 */
	public void saveEtiquetaWithCampo(final PlantillaEtiquetaCampoDTO campo)
	        throws GadirServiceException {
		if (Utilidades.isNull(campo)) {
			if (log.isDebugEnabled()) {
				log.debug("El objeto recibido es null, no se guardaran datos");
			}
		} else {
			try {
				this.save(campo.getPlantillaEtiquetaDTO());
				campo.setFhActualizacion(Utilidades.getDateActual());
				campo.setCoUsuarioActualizacion(DatosSesion.getLogin());
				this.plantillaEtiquetaCampoDao.save(campo);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al guardar la etiqueta con los campos asociados.",
				        e);
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public void deleteEtiquetaWithCampos(final PlantillaEtiquetaDTOId id)
	        throws GadirServiceException {
		if (Utilidades.isNull(id)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("El identificador recibido es null, no se puede recuperar el elemento a borrar");
			}
		} else {
			try {
				final List<PlantillaEtiquetaCampoDTO> listadoCampos = this
				        .getPlantillaEtiquetaCampoDao()
				        .findFiltered(
				                new String[] {
				                        "id.coPlantilla",
				                        "id.coEtiqueta" },
				                new Object[] { id.getCoPlantilla(),
				                        id.getCoEtiqueta() });
				if (listadoCampos != null && !listadoCampos.isEmpty()) {
					for (int i = 0; i < listadoCampos.size(); i++) {
						this.getPlantillaEtiquetaCampoDao().delete(
						        listadoCampos.get(i).getId());
					}
				}
				this.getPlantillaEtiquetaDao().delete(id);
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Error al eliminar la etiqueta con los campos asociados.",
				        e);
			}
		}
	}

	

	/**
	 * {@inheritDoc}
	 */
	public List<PlantillaEtiquetaDTO> getEtiquetasByTabla(
	        final Long coPlantilla, final Short coTabla)
	        throws GadirServiceException {
		if (Utilidades.isNull(coPlantilla) || Utilidades.isNull(coTabla)) {
			if (log.isDebugEnabled()) {
				log
				        .debug("Los datos recibidos son nulos, no se pueden obtener las etiquetas");
			}
			return Collections.emptyList();
		} else {
			final Map<String, Object> params = new HashMap<String, Object>(2);
			params.put("coPlantilla", coPlantilla);
			params.put("coTabla", coTabla);

			return this.getDao().findByNamedQuery(
			        QueryName.PLANTILLA_ETIQUETAS_TABLAS, params);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	//pp
	public List<PlantillaEtiquetaVO> findEtiquetasByPlantilla(final Long coPlantilla) throws GadirServiceException {
		List<PlantillaEtiquetaVO> lista = null;
		if (Utilidades.isNull(coPlantilla)) {
			lista = Collections.emptyList();
		} else {
			try {
				
				
				List<PlantillaEtiquetaDTO> plantillaEtiquetaDTOs = findFiltered("id.coPlantilla", coPlantilla, "id.coPlantilla", DAOConstant.ASC_ORDER);
				lista = new ArrayList<PlantillaEtiquetaVO>();
				for (PlantillaEtiquetaDTO plantillaEtiquetaDTO : plantillaEtiquetaDTOs) {
					PlantillaEtiquetaVO plantillaEtiquetaVO = new PlantillaEtiquetaVO();
					plantillaEtiquetaVO.setEtiqueta(plantillaEtiquetaDTO.getId().getCoEtiqueta());
					plantillaEtiquetaVO.setRowid(plantillaEtiquetaDTO.getRowid());
					plantillaEtiquetaVO.setEtiquetaDeConfiguracion(TablaGt.isElemento("TETICON", plantillaEtiquetaDTO.getId().getCoEtiqueta()));
					
					if (Utilidades.isNotNull(plantillaEtiquetaDTO.getCoPlantillaTabla())) {
						plantillaEtiquetaVO.setTabla(plantillaEtiquetaDTO.getCoPlantillaTabla().toString());
					}
					Hibernate.initialize(plantillaEtiquetaDTO.getPlantillaEtiquetaCampoDTOs());
					List<PlantillaEtiquetaCampoDTO> listaCampos = new ArrayList<PlantillaEtiquetaCampoDTO>(plantillaEtiquetaDTO.getPlantillaEtiquetaCampoDTOs());
					Collections.sort(listaCampos);
					
					if (listaCampos != null) {
						if (listaCampos.size() == 1) {
							PlantillaEtiquetaCampoDTO campo = listaCampos.get(0);
							plantillaEtiquetaVO.setTipo(campo.getTipo());
							plantillaEtiquetaVO.setPosicionIni(campo.getPosInicio());
							plantillaEtiquetaVO.setPosicionFin(campo.getPosFin());
							plantillaEtiquetaVO.setModeloAdicional(campo.getModeloAdicional());
							plantillaEtiquetaVO.setVersionAdicional(campo.getVersionAdicional());
							if (campo.getTipo().equals("S")) //Si es tipo casilla imprimimos la hoja
								plantillaEtiquetaVO.setValor(CamposTipoValorUtil.getTextoValor(
										campo.getModeloAdicional(), 
										campo.getVersionAdicional(), 
										new CampoTipoValorVO(campo.getTipo(), campo.getValor(), campo.getValorAdicional()), true, true));
							else
								plantillaEtiquetaVO.setValor(CamposTipoValorUtil.getTextoValor(
										campo.getModeloAdicional(), 
										campo.getVersionAdicional(), 
										new CampoTipoValorVO(campo.getTipo(), campo.getValor(), campo.getValorAdicional()), true, false));
							
						} else if (listaCampos.size() > 1) {
							plantillaEtiquetaVO.setTipo("C"); // Concatenación
							List<CampoTipoValorVO> listaCampoTipoValor = new ArrayList<CampoTipoValorVO>();
							for (PlantillaEtiquetaCampoDTO campo : listaCampos) {
								listaCampoTipoValor.add(new CampoTipoValorVO(campo.getTipo(), campo.getValor(), campo.getValorAdicional(), campo.getModeloAdicional(), campo.getVersionAdicional()));
							}
							plantillaEtiquetaVO.setValor(CamposTipoValorUtil.getTextoConcatenacion(listaCampoTipoValor, true, true));
						}
					}
					lista.add(plantillaEtiquetaVO);
				}
				Collections.sort(lista);
				
				
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException("Error al obtener las etiquetas de la plantilla: " + coPlantilla, e);
			}
		}
		return lista;
	}

	// GETTERS AND SETTERS
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<PlantillaEtiquetaDTO, PlantillaEtiquetaDTOId> getDao() {
		return this.getPlantillaEtiquetaDao();

	}

	/**
	 * Método que devuelve el atributo plantillaEtiquetaDao.
	 *
	 * @return plantillaEtiquetaDao.
	 */
	public DAOBase<PlantillaEtiquetaDTO, PlantillaEtiquetaDTOId> getPlantillaEtiquetaDao() {
		return plantillaEtiquetaDao;
	}

	/**
	 * Método que establece el atributo plantillaEtiquetaDao.
	 *
	 * @param plantillaEtiquetaDao
	 *            El plantillaEtiquetaDao.
	 */
	public void setPlantillaEtiquetaDao(
	        final DAOBase<PlantillaEtiquetaDTO, PlantillaEtiquetaDTOId> plantillaEtiquetaDao) {
		this.plantillaEtiquetaDao = plantillaEtiquetaDao;
	}

	/**
	 * Método que devuelve el atributo plantillaEtiquetaCampoDao.
	 *
	 * @return plantillaEtiquetaCampoDao.
	 */
	public DAOBase<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId> getPlantillaEtiquetaCampoDao() {
		return plantillaEtiquetaCampoDao;
	}

	/**
	 * Método que establece el atributo plantillaEtiquetaCampoDao.
	 *
	 * @param plantillaEtiquetaCampoDao
	 *            El plantillaEtiquetaCampoDao.
	 */
	public void setPlantillaEtiquetaCampoDao(
	        final DAOBase<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId> plantillaEtiquetaCampoDao) {
		this.plantillaEtiquetaCampoDao = plantillaEtiquetaCampoDao;
	}
	
	public void auditorias(PlantillaEtiquetaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		transientObject.setPlantillaDTO(plantillaBO.findById(transientObject.getId().getCoPlantilla()));
		plantillaBO.auditorias(transientObject.getPlantillaDTO(), false);
		
	}

	/**
	 * @return the plantillaBO
	 */
	public PlantillaBO getPlantillaBO() {
		return plantillaBO;
	}

	/**
	 * @param plantillaBO the plantillaBO to set
	 */
	public void setPlantillaBO(PlantillaBO plantillaBO) {
		this.plantillaBO = plantillaBO;
	}
	

}
