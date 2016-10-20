package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.PlantillaBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.PlantillaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaCampoDTO;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaCampoDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaFirmaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaFirmaDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaImagenDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondicionalDTO;
import es.dipucadiz.etir.comun.dto.PlantillaImgCondicionalDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaModeloDTO;
import es.dipucadiz.etir.comun.dto.PlantillaModeloDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaOdtDTO;
import es.dipucadiz.etir.comun.dto.PlantillaTablaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaTablaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;
import es.dipucadiz.etir.sb05.vo.PlantillaVO;

public class PlantillaBOImpl extends AbstractGenericBOImpl<PlantillaDTO, Long> implements PlantillaBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -2340497702134956775L;

	/**
	 * Atributo que almacena el literal del código de plantilla en las
	 * relaciones.
	 */
	private static final String CO_PLANTILLA = "plantillaDTO.coPlantilla";
	
	
	/**
	 * Atributo que almacena el literal del código de plantilla en las
	 * relaciones.
	 */
	private static final String CO_PLANTILLA_ID = "id.coPlantilla";

	/**
	 * Atributo que almacena el dao asociadoa {@link PlantillaDTO}.
	 */
	private DAOBase<PlantillaDTO, Long> plantillaDao;

	/**
	 * Atributo que almacena el dam asociadoa {@link PlantillaImagenDTO}.
	 */
	private DAOBase<PlantillaImagenDTO, Long> plantillaImagenDao;

	/**
	 * Atributo que almacena el dao asociadoa {@link PlantillaImgCondicionalDTO}
	 * .
	 */
	private DAOBase<PlantillaImgCondicionalDTO, PlantillaImgCondicionalDTOId> plantillaImgCondicionalDao;

	/**
	 * Atributo que almacena el dao asociadoa {@link PlantillaDTO}.
	 */
	private DAOBase<PlantillaModeloDTO, PlantillaModeloDTOId> plantillaModeloDao;

	/**
	 * Atributo que almacena el dao asociadoa {@link PlantillaTablaDTO}.
	 */
	private DAOBase<PlantillaTablaDTO, PlantillaTablaDTOId> plantillaTablaDao;

	/**
	 * Atributo que almacena el dao asociadoa {@link PlantillaEtiquetaDTO}.
	 */
	private DAOBase<PlantillaEtiquetaDTO, PlantillaEtiquetaDTOId> plantillaEtiquetaDao;

	/**
	 * Atributo que almacena el dao asociadoa {@link PlantillaEtiquetaCampoDTO}.
	 */
	private DAOBase<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId> plantillaEtiquetaCampoDao;

	/**
	 * Atributo que almacena el dao asociadoa {@link PlantillaFirmaDTO}.
	 */
	private DAOBase<PlantillaFirmaDTO, PlantillaFirmaDTOId> plantillaFirmaDao;
	
	/**
	 * Atributo que almacena el dao asociadoa {@link PlantillaFirmaDTO}.
	 */
	private DAOBase<PlantillaOdtDTO, Long> plantillaOdtDao;

	

	/**
	 * {@inheritDoc}
	 */
	public PlantillaDTO findByIdLazy(final Long coPlantilla, final boolean lazyTotal) throws GadirServiceException {
		PlantillaDTO plantilla = null;
		if (Utilidades.isNotNull(coPlantilla)) {
			try {
				plantilla = this.getDao().findById(coPlantilla);
				if (Utilidades.isNotNull(plantilla)) {
					// Inicializamos los objetos asociados.
					Hibernate.initialize(plantilla.getMunicipioDTO());
					Hibernate.initialize(plantilla.getModeloVersionDTO());
					Hibernate.initialize(plantilla.getModeloVersionDTO().getModeloDTO());
					Hibernate.initialize(plantilla.getConceptoDTO());
					Hibernate.initialize(plantilla.getProcesoAccionDTO());
					Hibernate.initialize(plantilla.getPlantillaOdtDTO());

					// Inicializamos el proceso, dentro de la accion.
					if (plantilla.getProcesoAccionDTO() != null) {
						Hibernate.initialize(plantilla.getProcesoAccionDTO().getProcesoDTO());
					}
					
					if (lazyTotal) {
						Hibernate.initialize(plantilla.getPlantillaModeloDTOs());
						Hibernate.initialize(plantilla.getPlantillaTablaDTOs());
						Hibernate.initialize(plantilla.getPlantillaEtiquetaDTOs());
						Hibernate.initialize(plantilla.getPlantillaFirmaDTOs());
						for (PlantillaEtiquetaDTO etiquetaDTO : plantilla.getPlantillaEtiquetaDTOs()) {
							Hibernate.initialize(etiquetaDTO.getPlantillaEtiquetaCampoDTOs());
						}
						Hibernate.initialize(plantilla.getPlantillaImagenDTOs());
						for (PlantillaImagenDTO imagenDTO : plantilla.getPlantillaImagenDTOs()) {
							Hibernate.initialize(imagenDTO.getPlantillaImgCondicionalDTOs());
							for (PlantillaImgCondicionalDTO imgCondicionalDTO : imagenDTO.getPlantillaImgCondicionalDTOs()) {
								Hibernate.initialize(imgCondicionalDTO.getPlantillaImgCondCampoDTOs());
							}
						}
					}
				}
			} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
				        "Ocurrio un error al obtener la plantilla con código:"
				                + CO_PLANTILLA, e);
			}
		}
		return plantilla;
	}


	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<PlantillaDTO, Long> getDao() {
		return this.getPlantillaDao();
	}

	// GETTERS AND SETTERS

	/**
	 * Método que devuelve el atributo plantillaDao.
	 * 
	 * @return plantillaDao.
	 */
	public DAOBase<PlantillaDTO, Long> getPlantillaDao() {
		return plantillaDao;
	}

	/**
	 * Método que establece el atributo plantillaDao.
	 * 
	 * @param plantillaDao
	 *            El plantillaDao.
	 */
	public void setPlantillaDao(final DAOBase<PlantillaDTO, Long> plantillaDao) {
		this.plantillaDao = plantillaDao;
	}

	/**
	 * @return the plantillaImagenDao
	 */
	public DAOBase<PlantillaImagenDTO, Long> getPlantillaImagenDao() {
		return plantillaImagenDao;
	}

	/**
	 * @param plantillaImagenDao
	 *            the plantillaImagenDao to set
	 */
	public void setPlantillaImagenDao(
	        final DAOBase<PlantillaImagenDTO, Long> plantillaImagenDao) {
		this.plantillaImagenDao = plantillaImagenDao;
	}

	/**
	 * @return the plantillaImgCondicionalDao
	 */
	public DAOBase<PlantillaImgCondicionalDTO, PlantillaImgCondicionalDTOId> getPlantillaImgCondicionalDao() {
		return plantillaImgCondicionalDao;
	}

	/**
	 * @param plantillaImgCondicionalDao
	 *            the plantillaImgCondicionalDao to set
	 */
	public void setPlantillaImgCondicionalDao(
	        final DAOBase<PlantillaImgCondicionalDTO, PlantillaImgCondicionalDTOId> plantillaImgCondicionalDao) {
		this.plantillaImgCondicionalDao = plantillaImgCondicionalDao;
	}

	/**
	 * @return the plantillaModeloDao
	 */
	public DAOBase<PlantillaModeloDTO, PlantillaModeloDTOId> getPlantillaModeloDao() {
		return plantillaModeloDao;
	}

	/**
	 * @param plantillaModeloDao
	 *            the plantillaModeloDao to set
	 */
	public void setPlantillaModeloDao(
	        final DAOBase<PlantillaModeloDTO, PlantillaModeloDTOId> plantillaModeloDao) {
		this.plantillaModeloDao = plantillaModeloDao;
	}

	/**
	 * @return the plantillaTablaDao
	 */
	public DAOBase<PlantillaTablaDTO, PlantillaTablaDTOId> getPlantillaTablaDao() {
		return plantillaTablaDao;
	}

	/**
	 * @param plantillaTablaDao
	 *            the plantillaTablaDao to set
	 */
	public void setPlantillaTablaDao(
	        final DAOBase<PlantillaTablaDTO, PlantillaTablaDTOId> plantillaTablaDao) {
		this.plantillaTablaDao = plantillaTablaDao;
	}

	/**
	 * @return the plantillaEtiquetaDao
	 */
	public DAOBase<PlantillaEtiquetaDTO, PlantillaEtiquetaDTOId> getPlantillaEtiquetaDao() {
		return plantillaEtiquetaDao;
	}

	/**
	 * @param plantillaEtiquetaDao
	 *            the plantillaEtiquetaDao to set
	 */
	public void setPlantillaEtiquetaDao(
	        final DAOBase<PlantillaEtiquetaDTO, PlantillaEtiquetaDTOId> plantillaEtiquetaDao) {
		this.plantillaEtiquetaDao = plantillaEtiquetaDao;
	}

	/**
	 * @return the plantillaEtiquetaCampoDao
	 */
	public DAOBase<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId> getPlantillaEtiquetaCampoDao() {
		return plantillaEtiquetaCampoDao;
	}

	/**
	 * @param plantillaEtiquetaCampoDao
	 *            the plantillaEtiquetaCampoDao to set
	 */
	public void setPlantillaEtiquetaCampoDao(
	        final DAOBase<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId> plantillaEtiquetaCampoDao) {
		this.plantillaEtiquetaCampoDao = plantillaEtiquetaCampoDao;
	}

	/**
	 * @return the plantillaFirmaDao
	 */
	public DAOBase<PlantillaFirmaDTO, PlantillaFirmaDTOId> getPlantillaFirmaDao() {
		return plantillaFirmaDao;
	}

	/**
	 * @param plantillaFirmaDao
	 *            the plantillaFirmaDao to set
	 */
	public void setPlantillaFirmaDao(
	        final DAOBase<PlantillaFirmaDTO, PlantillaFirmaDTOId> plantillaFirmaDao) {
		this.plantillaFirmaDao = plantillaFirmaDao;
	}
	
	

	/**
	 * @return the plantillaOdtDao
	 */
	public DAOBase<PlantillaOdtDTO, Long> getPlantillaOdtDao() {
		return plantillaOdtDao;
	}

	/**
	 * @param plantillaOdtDao the plantillaOdtDao to set
	 */
	public void setPlantillaOdtDao(DAOBase<PlantillaOdtDTO, Long> plantillaOdtDao) {
		this.plantillaOdtDao = plantillaOdtDao;
	}

	public List<PlantillaDTO> findByAccesoPlantillaVO(Long coProcesoAccion, AccesoPlantillaVO accesoPlantillaVO) throws GadirServiceException {
		List<PlantillaDTO> plantillaDTOs = new ArrayList<PlantillaDTO>();
		
		String[] coConceptos = accesoPlantillaVO.getCoConcepto().split(",");
		String[] propNames = new String[6];
		propNames[0] = "procesoAccionDTO.coProcesoAccion";
		propNames[1] = "municipioDTO.id.coProvincia";
		propNames[2] = "municipioDTO.id.coMunicipio";
		propNames[3] = "conceptoDTO.coConcepto";
		propNames[4] = "modeloVersionDTO.id.coModelo";
		propNames[5] = "modeloVersionDTO.id.coVersion";
		
		for (int i=0; i<8 && plantillaDTOs.isEmpty(); i++) {
			DetachedCriteria criterio = DetachedCriteria.forClass(PlantillaDTO.class);
			criterio.add(Restrictions.eq(propNames[0], coProcesoAccion));
			if (i < 4) {
				criterio.add(Restrictions.eq(propNames[1], accesoPlantillaVO.getCoProvincia()));
				criterio.add(Restrictions.eq(propNames[2], accesoPlantillaVO.getCoMunicipio()));
			} else {
				criterio.add(Restrictions.eq(propNames[1], "**"));
				criterio.add(Restrictions.eq(propNames[2], "***"));
			}
			if (i < 2 || i == 4 || i == 5) {
				criterio.add(Restrictions.in(propNames[3], coConceptos));
			} else {
				criterio.add(Restrictions.eq(propNames[3], "****"));
			}
			if (i%2 == 0) {
				criterio.add(Restrictions.eq(propNames[4], accesoPlantillaVO.getCoModelo()));
				criterio.add(Restrictions.eq(propNames[5], accesoPlantillaVO.getCoVersion()));
			} else {
				criterio.add(Restrictions.eq(propNames[4], "***"));
				criterio.add(Restrictions.eq(propNames[5], "*"));
			}
			criterio.addOrder(Order.asc("orden"));
			plantillaDTOs = findByCriteria(criterio);
			for (PlantillaDTO plantillaDTO : plantillaDTOs) {
				Hibernate.initialize(plantillaDTO.getPlantillaOdtDTO());
			}
			if (!plantillaDTOs.isEmpty()) {
				break;
			}
		}
		
		return plantillaDTOs;
	}
	
	public void auditorias(PlantillaDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
//		transientObject.setFhActualizacion(Utilidades.getDateActual()); Es tabla padre, se graba lo que le venga.
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}

	public List<PlantillaVO> findByCriteriosSeleccionPaginado(DetachedCriteria criteria, int porPagina, int page) throws GadirServiceException {
		List<PlantillaDTO> plantillaDTOs = findByCriteria(criteria, (page - 1) * porPagina, porPagina);
		List<PlantillaVO> plantillaVOs = new ArrayList<PlantillaVO>();
		for (PlantillaDTO plantillaDTO : plantillaDTOs) {
			PlantillaVO plantillaVO = new PlantillaVO();
			Hibernate.initialize(plantillaDTO.getMunicipioDTO());
			plantillaVO.setMunicipio(plantillaDTO.getMunicipioDTO().getCodigoDescripcion());
			plantillaVO.setCoMunicipio(plantillaDTO.getMunicipioDTO().getId().getCoProvincia() + plantillaDTO.getMunicipioDTO().getId().getCoMunicipio());
			Hibernate.initialize(plantillaDTO.getModeloVersionDTO());
			Hibernate.initialize(plantillaDTO.getModeloVersionDTO().getModeloDTO());
			plantillaVO.setModeloVersion(plantillaDTO.getModeloVersionDTO().getCodigoDescripcion());
			plantillaVO.setCoModeloVersion(plantillaDTO.getModeloVersionDTO().getId().getCoModelo() + " " + plantillaDTO.getModeloVersionDTO().getId().getCoVersion());
			Hibernate.initialize(plantillaDTO.getConceptoDTO());
			plantillaVO.setConcepto(plantillaDTO.getConceptoDTO().getCodigoDescripcion());
			plantillaVO.setCoConcepto(plantillaDTO.getConceptoDTO().getCoConcepto());
			Hibernate.initialize(plantillaDTO.getPlantillaOdtDTO());
			plantillaVO.setNombre(plantillaDTO.getPlantillaOdtDTO().getNombre());
			plantillaVO.setCoPlantilla(plantillaDTO.getCoPlantilla());
			Hibernate.initialize(plantillaDTO.getProcesoAccionDTO());
			Hibernate.initialize(plantillaDTO.getProcesoAccionDTO().getProcesoDTO());
			plantillaVO.setAccion(plantillaDTO.getProcesoAccionDTO().getAccion());
			plantillaVO.setProceso(plantillaDTO.getProcesoAccionDTO().getProcesoDTO().getCodigoDescripcion());
			plantillaVO.setCoProceso(plantillaDTO.getProcesoAccionDTO().getProcesoDTO().getCoProceso());
			
			if(plantillaDTO.getTipo()!= null){
				if(plantillaDTO.getTipo().equals("I"))
					plantillaVO.setTipo("I - Resumen");
				else if(plantillaDTO.getTipo().equals("D"))
					plantillaVO.setTipo("D - Detalle");
				else
					plantillaVO.setTipo("");
			}
			else
				plantillaVO.setTipo("");
			
			plantillaVO.setOrden(plantillaDTO.getOrden());
			
			plantillaVOs.add(plantillaVO);
		}
		return plantillaVOs;
	}

	public List<PlantillaOdtDTO> buscarPlantillasFiltro(String cadena) throws GadirServiceException{
		List<PlantillaOdtDTO> listado;
		final DetachedCriteria criteria = DetachedCriteria.forClass(PlantillaOdtDTO.class);
		criteria.add(Restrictions.like("nombre","%"+cadena+"%").ignoreCase());
		listado= this.plantillaOdtDao.findByCriteria(criteria);
	    return listado; 
		
	}
}
