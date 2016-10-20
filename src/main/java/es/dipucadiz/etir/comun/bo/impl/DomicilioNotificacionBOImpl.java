/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dipucadiz.etir.comun.bo.DomicilioBO;
import es.dipucadiz.etir.comun.bo.DomicilioNotificacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.DomicilioNotificacionDTO;
import es.dipucadiz.etir.comun.dto.HDomicilioNotificacionDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.ControlTerritorial;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DomicilioNotificacionBOImpl extends AbstractGenericBOImpl<DomicilioNotificacionDTO, Long> implements DomicilioNotificacionBO {
	private static final long serialVersionUID = -7163899333352983782L;

	private DAOBase<HDomicilioNotificacionDTO, Long> hDomicilioNotificacionDAO;
	
	private DAOBase<DomicilioNotificacionDTO, Long> dao;
	
	private DomicilioBO domicilioBO;

	public DomicilioNotificacionDTO findByIdFetch(final String coDomicilioNotificacion) throws GadirServiceException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		try {
			parametros.put("id", new Long(coDomicilioNotificacion));
			return findByNamedQuery(QueryName.DOMICILIONOTIFICACION_FINDBYIDFETCH, parametros)
					.get(0);
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener el domicilio notificacion seleccionado", e);
		}	
	}
	
	public List<DomicilioNotificacionDTO> findByClienteAndDomicilio(final String coCliente, final String coDomicilio, final boolean isAyuntamiento) throws GadirServiceException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		try {
			parametros.put("coCliente", new Long(coCliente));
			parametros.put("coDomicilio", new Long(coDomicilio));
			List<DomicilioNotificacionDTO> listaTmp = findByNamedQuery(QueryName.DOMICILIONOTIFICACION_FINDBYCLIENTEANDDOMICILIO, parametros);
			List<DomicilioNotificacionDTO> resultado = new ArrayList<DomicilioNotificacionDTO>();
			List<MunicipioDTO> municipiosUsuario = null;
			if (isAyuntamiento) {
				municipiosUsuario = ControlTerritorial.getMunicipiosUsuario(false);
			}
			for (DomicilioNotificacionDTO domnotDTO : listaTmp) {
				if (!isAyuntamiento || municipiosUsuario.contains(domnotDTO.getMunicipioDTO())) {
					resultado.add(domnotDTO);
				}
			}
			return resultado;
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los conceptos del domicilio notificacion", e);
		}	
	}
	
	/**
	 * Da de alta el domicilio de notificacion.
	 * Los cambios realizados son guardados en el historico.
	 * 
	 * @param domicilioNotificacion
	 * @throws GadirServiceException
	 */
	public void altaDomicilioNotificacion(DomicilioNotificacionDTO domicilioNotificacion) throws GadirServiceException {
		// Actualizamos
		this.saveOnly(domicilioNotificacion);
		
		// Registramos el cambio en el histórico
		HDomicilioNotificacionDTO hDomicilioNotificacion = new HDomicilioNotificacionDTO();
		
		hDomicilioNotificacion.setCoDomicilioNotificacion(domicilioNotificacion.getCoDomicilioNotificacion());
		hDomicilioNotificacion.setCoCliente((null != domicilioNotificacion.getClienteDTO())?domicilioNotificacion.getClienteDTO().getCoCliente():null);
		hDomicilioNotificacion.setCoConcepto((null != domicilioNotificacion.getConceptoDTO())?domicilioNotificacion.getConceptoDTO().getCoConcepto():null);
		hDomicilioNotificacion.setCoModelo((null != domicilioNotificacion.getModeloVersionDTO())?domicilioNotificacion.getModeloVersionDTO().getId().getCoModelo():null);
		hDomicilioNotificacion.setCoVersion((null != domicilioNotificacion.getModeloVersionDTO())?domicilioNotificacion.getModeloVersionDTO().getId().getCoVersion():null);
		hDomicilioNotificacion.setCoDocumento((null != domicilioNotificacion.getCoDocumentoCenso())?domicilioNotificacion.getCoDocumentoCenso():null);
		hDomicilioNotificacion.setCoDomicilio((null != domicilioNotificacion.getDomicilioDTO())?domicilioNotificacion.getDomicilioDTO().getCoDomicilio():null);
		hDomicilioNotificacion.setHTipoMovimiento("A");
		hDomicilioNotificacion.setFxVigenciaDesde(domicilioNotificacion.getFxVigenciaDesde());
		hDomicilioNotificacion.setFxVigenciaHasta(domicilioNotificacion.getFxVigenciaHasta());
		hDomicilioNotificacion.setFhActualizacion(new Date());
		
		hDomicilioNotificacionDAO.saveOnly(hDomicilioNotificacion);
	}
	
	/**
	 * Actualiza el domicilio de notificacion.
	 * Los cambios realizados son guardados en el historico.
	 * 
	 * @param domicilioNotificacion
	 * @throws GadirServiceException
	 */
	public void actualizarDomicilioNotificacion(DomicilioNotificacionDTO domicilioNotificacion) throws GadirServiceException {
		// Actualizamos
		this.save(domicilioNotificacion);
		
		// Registramos el cambio en el histórico
		HDomicilioNotificacionDTO hDomicilioNotificacion = new HDomicilioNotificacionDTO();
		
		hDomicilioNotificacion.setCoDomicilioNotificacion(domicilioNotificacion.getCoDomicilioNotificacion());
		hDomicilioNotificacion.setCoCliente((null != domicilioNotificacion.getClienteDTO())?domicilioNotificacion.getClienteDTO().getCoCliente():null);
		hDomicilioNotificacion.setCoConcepto((null != domicilioNotificacion.getConceptoDTO())?domicilioNotificacion.getConceptoDTO().getCoConcepto():null);
		hDomicilioNotificacion.setCoModelo((null != domicilioNotificacion.getModeloVersionDTO())?domicilioNotificacion.getModeloVersionDTO().getId().getCoModelo():null);
		hDomicilioNotificacion.setCoVersion((null != domicilioNotificacion.getModeloVersionDTO())?domicilioNotificacion.getModeloVersionDTO().getId().getCoVersion():null);
		hDomicilioNotificacion.setCoDocumento((null != domicilioNotificacion.getCoDocumentoCenso())?domicilioNotificacion.getCoDocumentoCenso():null);
		hDomicilioNotificacion.setCoDomicilio((null != domicilioNotificacion.getDomicilioDTO())?domicilioNotificacion.getDomicilioDTO().getCoDomicilio():null);
		hDomicilioNotificacion.setHTipoMovimiento("M");
		hDomicilioNotificacion.setFxVigenciaDesde(domicilioNotificacion.getFxVigenciaDesde());
		hDomicilioNotificacion.setFxVigenciaHasta(domicilioNotificacion.getFxVigenciaHasta());
		hDomicilioNotificacion.setFhActualizacion(new Date());
		
		hDomicilioNotificacionDAO.saveOnly(hDomicilioNotificacion);
	}
	
	/**
	 * Método encargado de eliminar un concepto asociado a un domicilio de notificación.
	 * En el caso de no tener más conceptos asociados, el domicilio deja de ser un domicilio
	 * de notificación.
	 * 
	 * @param domicilioNotificacion
	 * @throws GadirServiceException
	 */
	public void eliminarDomicilioNotificacion(DomicilioNotificacionDTO domicilioNotificacion) throws GadirServiceException {
		// Actualizamos
		this.getDao().delete(domicilioNotificacion.getCoDomicilioNotificacion());
		
		//Comprobamos si hay mas domicilios Notificacion asociados al domicilio.
		if (this.getDao().findFiltered("domicilioDTO", 
				domicilioNotificacion.getDomicilioDTO()).size() == 0) {
			//Si no hay, el domicilio dejará de ser un domicilio de notificacion.
			domicilioNotificacion.getDomicilioDTO().setBoNotificacion(false);
			this.getDomicilioBO().actualizarDomicilio(domicilioNotificacion.getDomicilioDTO(), "M");
		}
		
		// Registramos el cambio en el histórico
		HDomicilioNotificacionDTO hDomicilioNotificacion = new HDomicilioNotificacionDTO();
		
		hDomicilioNotificacion.setCoDomicilioNotificacion(domicilioNotificacion.getCoDomicilioNotificacion());
		hDomicilioNotificacion.setCoCliente((null != domicilioNotificacion.getClienteDTO())?domicilioNotificacion.getClienteDTO().getCoCliente():null);
		hDomicilioNotificacion.setCoConcepto((null != domicilioNotificacion.getConceptoDTO())?domicilioNotificacion.getConceptoDTO().getCoConcepto():null);
		hDomicilioNotificacion.setCoModelo((null != domicilioNotificacion.getModeloVersionDTO())?domicilioNotificacion.getModeloVersionDTO().getId().getCoModelo():null);
		hDomicilioNotificacion.setCoVersion((null != domicilioNotificacion.getModeloVersionDTO())?domicilioNotificacion.getModeloVersionDTO().getId().getCoVersion():null);
		hDomicilioNotificacion.setCoDocumento((null != domicilioNotificacion.getCoDocumentoCenso())?domicilioNotificacion.getCoDocumentoCenso():null);
		hDomicilioNotificacion.setCoDomicilio((null != domicilioNotificacion.getDomicilioDTO())?domicilioNotificacion.getDomicilioDTO().getCoDomicilio():null);
		hDomicilioNotificacion.setHTipoMovimiento("B");
		hDomicilioNotificacion.setFxVigenciaDesde(domicilioNotificacion.getFxVigenciaDesde());
		hDomicilioNotificacion.setFxVigenciaHasta(domicilioNotificacion.getFxVigenciaHasta());
		hDomicilioNotificacion.setFhActualizacion(new Date());
		
		hDomicilioNotificacionDAO.saveOnly(hDomicilioNotificacion);
	}
	
	/**
	 * Método encargado de eliminar un domicilio de notificación junto a todos
	 * sus conceptos asociados.
	 * 
	 * @param domicilio
	 * @throws GadirServiceException
	 */
	public void eliminarDomicilioNotificacionConceptos(DomicilioDTO domicilioNotificacion, boolean isAyuntamiento)
		throws GadirServiceException {
		// Cargamos el listado de conceptos del domicilio de notificacion asociados al cliente
		List<DomicilioNotificacionDTO> domiciliosNotificacion = this.findByClienteAndDomicilio(domicilioNotificacion.getClienteDTO().getCoCliente().toString(), 
				domicilioNotificacion.getCoDomicilio().toString(), isAyuntamiento);
		
		// Borramos todos los conceptos asociados al domicilio
		for (int i = 0; i < domiciliosNotificacion.size();i++) {
			this.eliminarDomicilioNotificacion(domiciliosNotificacion.get(i));
		}
		
		// Actualizamos
		//this.getDomicilioBO().eliminarDomicilio(domicilioNotificacion); NO DEBE ELMINAR EL DOMICILIO, PUEDE SER FISCAL O TRIBUTARIO   JON 22/10/2012
		DomicilioDTO domicilioDTO = domicilioBO.findById(domicilioNotificacion.getCoDomicilio());
		boolean sigueSiendoNotif = findFiltered("domicilioDTO", domicilioDTO, 0, 1).size() > 0;
		domicilioDTO.setBoNotificacion(sigueSiendoNotif);
		domicilioDTO.setFhActualizacion(Utilidades.getDateActual());
		domicilioDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
		domicilioBO.save(domicilioDTO);
	}
	
	public DAOBase<HDomicilioNotificacionDTO, Long> getHDomicilioNotificacionDAO() {
		return hDomicilioNotificacionDAO;
	}

	public void setHDomicilioNotificacionDAO(DAOBase<HDomicilioNotificacionDTO, Long> domicilioNotificacionDAO) {
		hDomicilioNotificacionDAO = domicilioNotificacionDAO;
	}
	
	public DAOBase<DomicilioNotificacionDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<DomicilioNotificacionDTO, Long> dao) {
		this.dao = dao;
	}

	/**
	 * @return the domicilioBO
	 */
	public DomicilioBO getDomicilioBO() {
		return domicilioBO;
	}

	/**
	 * @param domicilioBO the domicilioBO to set
	 */
	public void setDomicilioBO(DomicilioBO domicilioBO) {
		this.domicilioBO = domicilioBO;
	}
	public void auditorias(DomicilioNotificacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
}
