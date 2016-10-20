package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import es.dipucadiz.etir.comun.bo.CompensacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.CompensacionDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.MunicipioConceptoModeloUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.G7E6Compensacion.action.G7E6ListadoCompensacionVO;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase de compensacion {@link CompensacionDTO}.
 * 
 */
public class CompensacionBOImpl extends AbstractGenericBOImpl<CompensacionDTO, Long>
        implements CompensacionBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;
	
	/**
	 * Atributo que almacena el DAO asociado a compensacion.
	 */
	private DAOBase<CompensacionDTO, Long> dao;


	// MÉTODOS //
	
	public void auditorias(CompensacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}



	public DAOBase<CompensacionDTO, Long> getDao() {
		return dao;
	}



	public void setDao(DAOBase<CompensacionDTO, Long> dao) {
		this.dao = dao;
	}

	

	
	public List<G7E6ListadoCompensacionVO> findByCriteriosGenericoSeleccionPaginadoMunicipio(DetachedCriteria criteria, int porPagina, int page) throws GadirServiceException{ 
		List<Object[]> listaAux = findByCriteriaGenerico(criteria);
		
		List<G7E6ListadoCompensacionVO> listaCompensaciones = new ArrayList<G7E6ListadoCompensacionVO>();
		for (Object[] objeto: listaAux){ 

			G7E6ListadoCompensacionVO compensacionVO = new G7E6ListadoCompensacionVO();

			compensacionVO.setEjercicio((Short) objeto[0]);
			compensacionVO.setNumCompensaciones((Integer)objeto[1]);
			compensacionVO.setImporteTotal((BigDecimal)objeto[2]);
			compensacionVO.setImporteTotalString(compensacionVO.getImporteTotal().toString());
			
			listaCompensaciones.add(compensacionVO);
		}
		return listaCompensaciones;		
	}

	public List<G7E6ListadoCompensacionVO> findByCriteriosGenericoSeleccionPaginadoEjercicio(DetachedCriteria criteria, int porPagina, int page) throws GadirServiceException{ 
		
		List<Object[]> listaAux = findByCriteriaGenerico(criteria);
		List<G7E6ListadoCompensacionVO> listaCompensaciones = new ArrayList<G7E6ListadoCompensacionVO>();
		
		for (Object[] objeto: listaAux){			
			List<CompensacionDTO> listaCompAux = findFiltered("municipioDTO.id.coMunicipio",(String) objeto[0]);
			MunicipioDTO municipioCompDTO = MunicipioConceptoModeloUtil.getMunicipioDTO(listaCompAux.get(0).getMunicipioDTO().getId().getCoProvincia() + listaCompAux.get(0).getMunicipioDTO().getId().getCoMunicipio());
			
			G7E6ListadoCompensacionVO compensacionVO = new G7E6ListadoCompensacionVO();
			compensacionVO.setCodigoDescripcion(municipioCompDTO.getCodigoDescripcion());
	
			compensacionVO.setNumCompensaciones((Integer)objeto[2]);
			compensacionVO.setImporteTotal((BigDecimal)objeto[3]);
			compensacionVO.setImporteTotalString(compensacionVO.getImporteTotal().toString());
			compensacionVO.setMunicipioDTO(municipioCompDTO);
			
			listaCompensaciones.add(compensacionVO);
		}
		return listaCompensaciones;		
	}


	
}
