/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.IncidenciaSituacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.IncidenciaDTO;
import es.dipucadiz.etir.comun.dto.IncidenciaSituacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class IncidenciaSituacionBOImpl extends AbstractGenericBOImpl<IncidenciaSituacionDTO, Long> implements IncidenciaSituacionBO {

	private static final long serialVersionUID = -2530737654670139535L;

	private static final Log LOG = LogFactory.getLog(IncidenciaSituacionBOImpl.class);
	
	private DAOBase<IncidenciaSituacionDTO, Long> dao;
	
	public DAOBase<IncidenciaSituacionDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<IncidenciaSituacionDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void auditorias(IncidenciaSituacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

	public List<IncidenciaDTO> findByClaves(String coProvincia,
			String coMunicipio, String coModelo, String coVersion)
			throws GadirServiceException {

		Map<String, Object> parametros= new HashMap<String, Object>();
		parametros.put("coProvincia", coProvincia);
		parametros.put("coMunicipio", coMunicipio);
		parametros.put("coModelo", coModelo);
		parametros.put("coVersion", coVersion);
		
		List<IncidenciaSituacionDTO> incidenciaSituacionDTOs = dao.findByNamedQuery("Tramite.findIncidenciasDistintas", parametros);
		
		List<IncidenciaDTO> incidenciaDTOs = new ArrayList<IncidenciaDTO>();
		
		String ante = "vacia";
		
		for(IncidenciaSituacionDTO isDTO : incidenciaSituacionDTOs)
		{
			Hibernate.initialize(isDTO.getIncidenciaDTO());
			String codigoIn = isDTO.getIncidenciaDTO().getCodigoDescripcion();
			if (!ante.equalsIgnoreCase(codigoIn))
				incidenciaDTOs.add(isDTO.getIncidenciaDTO());
			
			ante = codigoIn;
		}
		
		return incidenciaDTOs;
	}

}
