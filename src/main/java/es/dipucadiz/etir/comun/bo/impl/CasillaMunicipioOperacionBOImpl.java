package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.CasillaMunicipioOperacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioDTO;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioOperacionDTO;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioOperacionDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.CasillaMunicipioOperacionVO;
import es.dipucadiz.etir.sb05.vo.CasillaMunicipioVO;


public class CasillaMunicipioOperacionBOImpl extends
		AbstractGenericBOImpl<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> implements
		CasillaMunicipioOperacionBO {
	
	private static final long serialVersionUID = 6879318165367306079L;

	
	private DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> casillaMunicipioOperacionDao;

	
	private DAOBase<CasillaMunicipioDTO, Long> casillaMunicipioDao;
	
	public CasillaMunicipioOperacionDTO findByCoCasillaMunicipioAndCoOperacion(final long coCasillaMunicipio, final String coOperacion) throws GadirServiceException {
		CasillaMunicipioOperacionDTO result = null;
		String[] propNames = {"id.coCasillaMunicipio", "id.coOperacion"};
		Object[] filters = {coCasillaMunicipio, coOperacion};
		List<CasillaMunicipioOperacionDTO> operacionDTOs = findFiltered(propNames, filters);
		if (!operacionDTOs.isEmpty()) {
			result = operacionDTOs.get(0);
		}
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Long getNumProcesosByCasillaMunicipio(final String coCasillaMunicipio) throws GadirServiceException {
		Long resultado;
		try {
				final Map<String, Object> params = new HashMap<String, Object>(
						1);
				params.put("coCasillaMunicipio", Long.valueOf(coCasillaMunicipio));

				resultado = (Long)this.getDao().ejecutaNamedQuerySelect("CasillaMunicipioOperacion.numProcesosByCasillaMunicipio", 
						params);

		} catch (final Exception e) {
				log.error(e.getCause(), e);
				throw new GadirServiceException(
						"Error al obtener el listado de unidades urbanas es:", e);
		}
		return resultado;
	}
	
	
	
	public class Comparador implements Comparator{
		public int compare(Object o1, Object o2) {
			CasillaMunicipioOperacionVO u1 = (CasillaMunicipioOperacionVO) o1;
			CasillaMunicipioOperacionVO u2 = (CasillaMunicipioOperacionVO) o2;
			
		    return new Integer (u1.getOrden().toString()) - new Integer(u2.getOrden().toString());
		  }

	}
	
	public DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> getCasillaMunicipioOperacionDao() {
		return casillaMunicipioOperacionDao;
	}

	public void setCasillaMunicipioOperacionDao(
			DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> casillaMunicipioOperacionDao) {
		this.casillaMunicipioOperacionDao = casillaMunicipioOperacionDao;
	}

	public DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> getDao() {
		return this.getCasillaMunicipioOperacionDao();
	}
	
	public void auditorias(CasillaMunicipioOperacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
		
		CasillaMunicipioDTO casillaMunicipio = new CasillaMunicipioDTO();
		casillaMunicipio = casillaMunicipioDao.findById(transientObject.getId().getCoCasillaMunicipio());
		casillaMunicipio.setFhActualizacion(Utilidades.getDateActual());
		casillaMunicipio.setCoUsuarioActualizacion(DatosSesion.getLogin());
		getCasillaMunicipioDao().save(casillaMunicipio);
	}

	/**
	 * @return the casillaMunicipioDao
	 */
	public DAOBase<CasillaMunicipioDTO, Long> getCasillaMunicipioDao() {
		return casillaMunicipioDao;
	}

	/**
	 * @param casillaMunicipioDao the casillaMunicipioDao to set
	 */
	public void setCasillaMunicipioDao(
			DAOBase<CasillaMunicipioDTO, Long> casillaMunicipioDao) {
		this.casillaMunicipioDao = casillaMunicipioDao;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public Boolean findByCoParametro(final String coParametro)
			throws GadirServiceException {
			String queryString;
			queryString = 	"select pm " +
							"from CasillaMunicipioOperacionDTO pm " +
							"where pm.parametro = '"+ coParametro + "' ";

			try {
			List<CasillaMunicipioOperacionDTO> result = (List<CasillaMunicipioOperacionDTO>) this.getDao()
					.findByQuery(queryString);
			if(result.size() > 0){
				return true;
			}
			else{
				return false;
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener la casilla municipio operación.", e);
		}
	}


	public List<CasillaMunicipioOperacionDTO> findByCoCasillaMunicipio(long coCasillaMunicipio) throws GadirServiceException {
		return findFiltered("id.coCasillaMunicipio", coCasillaMunicipio);
	}

	public List<CasillaMunicipioVO> findByCoCasillaMunicipioVO(long coCasillaMunicipio) throws GadirServiceException {
		List<CasillaMunicipioOperacionDTO> operacionDTOs = findFiltered("id.coCasillaMunicipio", coCasillaMunicipio);
		List<CasillaMunicipioVO> resultado = new ArrayList<CasillaMunicipioVO>();
		for (CasillaMunicipioOperacionDTO operacionDTO : operacionDTOs) {
			CasillaMunicipioVO casillaMunicipioVO = new CasillaMunicipioVO();
			casillaMunicipioVO.setOperacion(operacionDTO.getId().getCoOperacion());
			casillaMunicipioVO.setOperacionDescripcion(TablaGt.getCodigoDescripcion(TablaGtConstants.TABLA_PROCESOS_FUNCIONES, operacionDTO.getId().getCoOperacion()).getCodigoDescripcion());
			casillaMunicipioVO.setAtributo(TablaGt.getCodigoDescripcion(TablaGtConstants.TABLA_ATRIBUTOS_CASILLAS, operacionDTO.getAtributo()).getCodigoDescripcion());
			if (operacionDTO.getFuncionArgumentoDTO() != null) {
				Hibernate.initialize(operacionDTO.getFuncionArgumentoDTO());
				casillaMunicipioVO.setFuncionArgumento(operacionDTO.getFuncionArgumentoDTO().getCodigoDescripcion());
			}
			if (operacionDTO.getParametro() != null) {
				casillaMunicipioVO.setParametro(operacionDTO.getParametro());
			}
			resultado.add(casillaMunicipioVO);
		}
		return resultado;
	}

}
