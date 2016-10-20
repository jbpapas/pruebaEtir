package es.dipucadiz.etir.comun.bo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.dto.PlantillaDTO;
import es.dipucadiz.etir.comun.dto.PlantillaOdtDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;
import es.dipucadiz.etir.sb05.vo.PlantillaVO;


public interface PlantillaBO extends GenericBO<PlantillaDTO, Long> {

	PlantillaDTO findByIdLazy(final Long coPlantilla, boolean lazyTotal) throws GadirServiceException;
	
	List<PlantillaDTO> findByAccesoPlantillaVO(Long coProcesoAccion, AccesoPlantillaVO accesoPlantillaVO) throws GadirServiceException;

	public List<PlantillaOdtDTO> buscarPlantillasFiltro(String cadena) throws GadirServiceException;
	
	List<PlantillaVO> findByCriteriosSeleccionPaginado(DetachedCriteria criteria, int porPagina, int page) throws GadirServiceException;
}
