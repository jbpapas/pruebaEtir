package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.TramiteBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.CircuitoRutaDTO;
import es.dipucadiz.etir.comun.dto.TramiteDTO;
import es.dipucadiz.etir.comun.dto.TramiteTransicionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb06.vo.TramiteVO;

public class TramiteBOImpl extends AbstractGenericBOImpl<TramiteDTO, Long> implements TramiteBO {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */

	private static final long serialVersionUID = 2316250635387251595L;

	/**
	 * Atributo que almacena el DAO asociado a {@link TramiteDTO}.
	 */
	private DAOBase<TramiteDTO, Long> dao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DAOBase<TramiteDTO, Long> getDao() {
		return dao;
	}

	/**
	 * @param tramiteDao
	 *            the tramiteDao to set
	 */
	public void setDao(final DAOBase<TramiteDTO, Long> dao) {
		this.dao = dao;
	}
	
	public void auditorias(TramiteDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
	public List<TramiteVO> getTramitesByRutaYSinTipo(CircuitoRutaDTO circuitoRutaDTO, String tipo) throws GadirServiceException {
		List<TramiteVO> tramiteVOs = new ArrayList<TramiteVO>();
		DetachedCriteria dc = DetachedCriteria.forClass(TramiteDTO.class);
		
		dc.add(Restrictions.eq("circuitoRutaDTO", circuitoRutaDTO));
		dc.add(Restrictions.or(Restrictions.ne("tipo", tipo), Restrictions.isNull("tipo")));
		dc.addOrder(Order.asc("etiqueta"));
		
		List<TramiteDTO> tramiteDTOs = findByCriteria(dc);
		
		for (TramiteDTO tramiteDTO : tramiteDTOs) {
			TramiteVO tramiteVO = new TramiteVO();
			tramiteVO.setCoTramite(tramiteDTO.getCoTramite());
			tramiteVO.setCodigoDescripcion(tramiteDTO.getCodigoDescripcionEspecial());
			tramiteVO.setTipo(tramiteDTO.getTipoTramite());
			tramiteVO.setTipoDescripcion(TablaGt.getValor("TAB0153", tramiteDTO.getTipoTramite(), TablaGt.COLUMNA_DESCRIPCION));
			if(tramiteDTO.getProcesoDTO() != null) 
				tramiteVO.setProcesoDescripcion(tramiteDTO.getProcesoDTO().getCodigoDescripcion());
			Hibernate.initialize(new String[] {"tramiteTransicionDTOsForCoTramiteDestino"});
			List<TramiteTransicionDTO> lista = new ArrayList<TramiteTransicionDTO>(tramiteDTO.getTramiteTransicionDTOsForCoTramiteOrigen());
			for (TramiteTransicionDTO tramiteTransicionDTO : lista) {
				Hibernate.initialize(tramiteTransicionDTO);
			}
			Collections.sort(lista);
			if (lista.size() > 0 && lista.get(lista.size()-1).getOrden() == -1) {
				TramiteDTO tramiteDTODestino = findById(lista.get(lista.size()-1).getId().getCoTramiteDestino());
				tramiteVO.setTramiteError(tramiteDTODestino.getCodigoDescripcion());
				lista.remove(lista.size()-1); // Quitar el trámite error de la lista porque no saldrá en la descripción del trámite, para que funcione igual que antes.
			}
			if (lista.size() == 2) {
				tramiteVO.setTramiteSiguiente("Sí: " + lista.get(1).getTramiteDTOByCoTramiteDestino().getCodigoDescripcionEspecial() + ", " + "No: " + lista.get(0).getTramiteDTOByCoTramiteDestino().getCodigoDescripcionEspecial());
			} else if(lista.size() == 1) {
				tramiteVO.setTramiteSiguiente(lista.get(0).getTramiteDTOByCoTramiteDestino().getCodigoDescripcionEspecial());
			}
			
			tramiteVOs.add(tramiteVO);
		}
		return tramiteVOs;
	}
	public List<TramiteVO> getTramitesByRuta(CircuitoRutaDTO circuitoRutaDTO) throws GadirServiceException {
		List<TramiteVO> tramiteVOs = new ArrayList<TramiteVO>();
		List<TramiteDTO> tramiteDTOs = findFilteredInitialized("circuitoRutaDTO", circuitoRutaDTO, "etiqueta", DAOConstant.ASC_ORDER, new String[] {"tramiteTransicionDTOsForCoTramiteDestino"});
		for (TramiteDTO tramiteDTO : tramiteDTOs) {
			TramiteVO tramiteVO = new TramiteVO();
			tramiteVO.setCoTramite(tramiteDTO.getCoTramite());
			tramiteVO.setCodigoDescripcion(tramiteDTO.getCodigoDescripcionEspecial());
			tramiteVO.setTipo(tramiteDTO.getTipoTramite());
			tramiteVO.setTipoDescripcion(TablaGt.getValor("TAB0153", tramiteDTO.getTipoTramite(), TablaGt.COLUMNA_DESCRIPCION));
			if(tramiteDTO.getProcesoDTO() != null) 
				tramiteVO.setProcesoDescripcion(tramiteDTO.getProcesoDTO().getCodigoDescripcion());
			List<TramiteTransicionDTO> lista = new ArrayList<TramiteTransicionDTO>(tramiteDTO.getTramiteTransicionDTOsForCoTramiteOrigen());
			for (TramiteTransicionDTO tramiteTransicionDTO : lista) {
				Hibernate.initialize(tramiteTransicionDTO);
			}
			Collections.sort(lista);
			if (lista.size() > 0 && lista.get(lista.size()-1).getOrden() == -1) {
				TramiteDTO tramiteDTODestino = findById(lista.get(lista.size()-1).getId().getCoTramiteDestino());
				tramiteVO.setTramiteError(tramiteDTODestino.getCodigoDescripcion());
				lista.remove(lista.size()-1); // Quitar el trámite error de la lista porque no saldrá en la descripción del trámite, para que funcione igual que antes.
			}
			if (lista.size() == 2) {
				tramiteVO.setTramiteSiguiente("Sí: " + lista.get(1).getTramiteDTOByCoTramiteDestino().getCodigoDescripcionEspecial() + ", " + "No: " + lista.get(0).getTramiteDTOByCoTramiteDestino().getCodigoDescripcionEspecial());
			} else if(lista.size() == 1) {
				tramiteVO.setTramiteSiguiente(lista.get(0).getTramiteDTOByCoTramiteDestino().getCodigoDescripcionEspecial());
			}
			
			tramiteVOs.add(tramiteVO);
		}
		return tramiteVOs;
	}

}