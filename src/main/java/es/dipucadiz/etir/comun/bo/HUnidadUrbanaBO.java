package es.dipucadiz.etir.comun.bo;

import java.util.Date;
import java.util.List;

import es.dipucadiz.etir.comun.dto.HUnidadUrbanaDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.comun.vo.UnidadUrbanaVO;

public interface HUnidadUrbanaBO extends GenericBO<HUnidadUrbanaDTO, Long> {
	
	public List<HUnidadUrbanaDTO> findHUnidadUrbanaByCodigo(final Long coUnidadUrbana) throws GadirServiceException;
	public List<HUnidadUrbanaDTO> findHUnidadUrbanaByCodigoYFecha(final Long coUnidadUrbana, Date filtroFecha) throws GadirServiceException;
	public void guardarHUrbana(UnidadUrbanaDTO unidad, String tipoMovimiento) throws GadirServiceException;
	public List<HUnidadUrbanaDTO> buscarHUnidadesUrbanasVO(final UnidadUrbanaVO unidad, Date fecha, String nombre, int inicio, int fin) throws GadirServiceException;
	public Integer buscarHUnidadesUrbanasVOCount(final UnidadUrbanaVO unidad, Date fecha, String nombre) throws GadirServiceException;

}
