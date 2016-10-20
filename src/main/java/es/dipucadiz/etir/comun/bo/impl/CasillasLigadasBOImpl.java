package es.dipucadiz.etir.comun.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.CasillasLigadasBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTOId;
import es.dipucadiz.etir.comun.dto.CasillasLigadasDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class CasillasLigadasBOImpl extends
        AbstractGenericBOImpl<CasillasLigadasDTO, Long> implements
        CasillasLigadasBO {

	
	private static final long serialVersionUID = 5906586311724732356L;

	
	private DAOBase<CasillasLigadasDTO, Long> casillasLigadasDao;

	
	public List<CasillasLigadasDTO> findCasillasLigadasACasilla(
	        final CasillaModeloDTOId idCasilla) throws GadirServiceException {
		try {
			List<CasillasLigadasDTO> casillas = new ArrayList<CasillasLigadasDTO>();
			casillas =  this.findFiltered(new String[] {
			        "casillaModeloDTO.id.nuCasilla",
			        "casillaModeloDTO.id.coVersion",
			        "casillaModeloDTO.id.coModelo" }, new Object[] {
			        idCasilla.getNuCasilla(), idCasilla.getCoVersion(),
			        idCasilla.getCoModelo() });
			for(CasillasLigadasDTO dto : casillas){
				if(!Hibernate.isInitialized(dto.getCasillaModeloDTO())){
					Hibernate.initialize(dto.getCasillaModeloDTO());
				}
			}
			
			return casillas;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener las casillas ligadas a la casilla dada.",
			        e);
		}
	}
	
	
	public List<CasillasLigadasDTO> findCasillasLigadasByModeloVersion(
	        final ModeloVersionDTOId idModeloVersion) throws GadirServiceException {
		try {
			return this.findFiltered(new String[] {
			        "casillaModeloDTO.id.coVersion",
			        "casillaModeloDTO.id.coModelo" }, new Object[] {
					idModeloVersion.getCoVersion(),
					idModeloVersion.getCoModelo() });
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener las casillas ligadas al modelo version dados.",
			        e);
		}
	}

	
	public DAOBase<CasillasLigadasDTO, Long> getDao() {
		return this.getCasillasLigadasDao();
	}

	public DAOBase<CasillasLigadasDTO, Long> getCasillasLigadasDao() {
		return casillasLigadasDao;
	}

	public void setCasillasLigadasDao(
	        final DAOBase<CasillasLigadasDTO, Long> casLigadasDao) {
		this.casillasLigadasDao = casLigadasDao;
	}
	
	public void auditorias(CasillasLigadasDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
}
