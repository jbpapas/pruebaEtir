package es.dipucadiz.etir.comun.bo.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.HCalleBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.HCalleDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class HCalleBOImpl extends AbstractGenericBOImpl<HCalleDTO, Long> implements HCalleBO {

	private static final Log LOG = LogFactory.getLog(HCalleBOImpl.class);
	
	private DAOBase<HCalleDTO, Long> dao;

	public DAOBase<HCalleDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<HCalleDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	public void guardarHCalle(CalleDTO calle) throws GadirServiceException{
		HCalleDTO hcalle = new HCalleDTO();
		hcalle.setCoCalle(calle.getCoCalle());
		hcalle.setCoDgc(calle.getCoDgc());
		hcalle.setCoMunicipal(calle.getCoMunicipal());
		hcalle.setCoMunicipio(calle.getMunicipioDTO().getId().getCoMunicipio());
		hcalle.setCoProvincia(calle.getMunicipioDTO().getId().getCoProvincia());
		hcalle.setFhActualizacion(new Date());
		hcalle.setHTipoMovimiento("A");
		hcalle.setNombreCalle(calle.getNombreCalle());
		hcalle.setProcedencia(calle.getProcedencia());
		hcalle.setSigla(calle.getSigla());
		if(calle.getCalleUbicacionDTO()!=null){
			hcalle.setCoCalleUbicacion(calle.getCalleUbicacionDTO().getCoCalleUbicacion());
		}
		
		try {
			this.saveOnly(hcalle);
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al registrar el historico:", e);
		}
	}
	
	public void guardarHCalle(CalleDTO calle, String movimiento) throws GadirServiceException{
		HCalleDTO hcalle = new HCalleDTO();
		hcalle.setCoCalle(calle.getCoCalle());
		if (calle.getCalleUbicacionDTO() != null){
			hcalle.setCoCalleUbicacion(calle.getCalleUbicacionDTO().getCoCalleUbicacion());
		}
		hcalle.setCoDgc(calle.getCoDgc());
		hcalle.setCoMunicipal(calle.getCoMunicipal());
		hcalle.setCoMunicipio(calle.getMunicipioDTO().getId().getCoMunicipio());
		hcalle.setCoProvincia(calle.getMunicipioDTO().getId().getCoProvincia());
		hcalle.setHTipoMovimiento(movimiento);
		hcalle.setNombreCalle(calle.getNombreCalle());
		hcalle.setProcedencia(calle.getProcedencia());
		hcalle.setSigla(calle.getSigla());
		
		try {
			this.saveOnly(hcalle);
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al registrar el historico:", e);
		}
	}
	
	public void auditorias(HCalleDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
	
	