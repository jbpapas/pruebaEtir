package es.dipucadiz.etir.comun.bo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.dto.CompensacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.sb07.G7E6Compensacion.action.G7E6ListadoCompensacionVO;

/**
 * Interface responsable de definir la lógica de negocio asociada a la clase de
 * compensacion {@link CompensacionDTO}.
 * 
 */
public interface CompensacionBO extends GenericBO<CompensacionDTO, Long> {

	public List<G7E6ListadoCompensacionVO> findByCriteriosGenericoSeleccionPaginadoMunicipio(DetachedCriteria criteria, int porPagina, int page) throws GadirServiceException;
	public List<G7E6ListadoCompensacionVO> findByCriteriosGenericoSeleccionPaginadoEjercicio(DetachedCriteria criteria, int porPagina, int page) throws GadirServiceException;
}
