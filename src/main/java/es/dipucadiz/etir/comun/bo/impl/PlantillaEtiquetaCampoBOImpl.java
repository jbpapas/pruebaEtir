package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.List;

import es.dipucadiz.etir.comun.bo.PlantillaEtiquetaBO;
import es.dipucadiz.etir.comun.bo.PlantillaEtiquetaCampoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaCampoDTO;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaCampoDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.CamposTipoValorUtil;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.CampoTipoValorVO;
import es.dipucadiz.etir.sb05.vo.EtiquetaCampoVO;


public class PlantillaEtiquetaCampoBOImpl
        extends
        AbstractGenericBOImpl<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId>
        implements PlantillaEtiquetaCampoBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a modelo.
	 */
	private DAOBase<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId> plantillaEtiquetaCampoDao;
	
	private PlantillaEtiquetaBO plantillaEtiquetaBO;

	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId> getDao() {
		return this.getPlantillaEtiquetaCampoDao();
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
	        DAOBase<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId> plantillaEtiquetaCampoDao) {
		this.plantillaEtiquetaCampoDao = plantillaEtiquetaCampoDao;
	}
	
	public void auditorias(PlantillaEtiquetaCampoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		transientObject.setPlantillaEtiquetaDTO(plantillaEtiquetaBO.findById(
				new PlantillaEtiquetaDTOId(transientObject.getId().getCoPlantilla(),
						transientObject.getId().getCoEtiqueta())));
		
		plantillaEtiquetaBO.auditorias(transientObject.getPlantillaEtiquetaDTO(), false);
	}

	/**
	 * @return the plantillaEtiquetaBO
	 */
	public PlantillaEtiquetaBO getPlantillaEtiquetaBO() {
		return plantillaEtiquetaBO;
	}

	/**
	 * @param plantillaEtiquetaBO the plantillaEtiquetaBO to set
	 */
	public void setPlantillaEtiquetaBO(PlantillaEtiquetaBO plantillaEtiquetaBO) {
		this.plantillaEtiquetaBO = plantillaEtiquetaBO;
	}

	public List<EtiquetaCampoVO> findByEtiqueta(PlantillaEtiquetaDTOId id) throws GadirServiceException {
		String[] propNames = {"id.coPlantilla", "id.coEtiqueta"};
		Object[] filters = {id.getCoPlantilla(), id.getCoEtiqueta()};
		List<PlantillaEtiquetaCampoDTO> campoDTOs = findFiltered(propNames, filters, "id.orden", DAOConstant.ASC_ORDER);
		List<EtiquetaCampoVO> etiquetaCampoVOs = new ArrayList<EtiquetaCampoVO>();
		for (PlantillaEtiquetaCampoDTO campoDTO : campoDTOs) {
			EtiquetaCampoVO etiquetaCampoVO = new EtiquetaCampoVO();
			etiquetaCampoVO.setOrden(campoDTO.getId().getOrden());
			etiquetaCampoVO.setPosicionFin(campoDTO.getPosFin());
			etiquetaCampoVO.setPosicionIni(campoDTO.getPosInicio());
			etiquetaCampoVO.setTipo(campoDTO.getTipo());
			etiquetaCampoVO.setValor(CamposTipoValorUtil.getTextoValor(
					campoDTO.getModeloAdicional(), 
					campoDTO.getVersionAdicional(), 
					new CampoTipoValorVO(campoDTO.getTipo(), campoDTO.getValor(), campoDTO.getValorAdicional()), true, false));
			etiquetaCampoVO.setRowid(campoDTO.getRowid());
			etiquetaCampoVOs.add(etiquetaCampoVO);
		}
		return etiquetaCampoVOs;
	}
	
}
