package es.dipucadiz.etir.comun.bo;

import java.util.List;

import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaCampoDTO;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaCampoDTOId;
import es.dipucadiz.etir.comun.dto.PlantillaEtiquetaDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb05.vo.EtiquetaCampoVO;


public interface PlantillaEtiquetaCampoBO extends
        GenericBO<PlantillaEtiquetaCampoDTO, PlantillaEtiquetaCampoDTOId> {

	List<EtiquetaCampoVO> findByEtiqueta(PlantillaEtiquetaDTOId id) throws GadirServiceException;

}
