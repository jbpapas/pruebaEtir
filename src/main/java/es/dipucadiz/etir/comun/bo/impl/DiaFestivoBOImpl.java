/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.DiaFestivoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DiaFestivoDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DiaFestivoBOImpl extends AbstractGenericBOImpl<DiaFestivoDTO, Long> implements DiaFestivoBO {

	private static final Log LOG = LogFactory.getLog(DiaFestivoBOImpl.class);
	
	private DAOBase<DiaFestivoDTO, Long> dao;

	public DAOBase<DiaFestivoDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<DiaFestivoDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}
	
	
	public List<DiaFestivoDTO> findByMunicipioAno(MunicipioDTO municipioDTO, Short ano) throws GadirServiceException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("coMunicipio", municipioDTO.getId().getCoMunicipio());
		params.put("coProvincia", municipioDTO.getId().getCoProvincia());
		GregorianCalendar gcInicio= new GregorianCalendar(ano.intValue(), GregorianCalendar.JANUARY, 1, 0, 0, 0);
		Date fechaInicio = new Date(gcInicio.getTimeInMillis());
		params.put("fechaInicio", fechaInicio);
		GregorianCalendar gcFin= new GregorianCalendar(ano.intValue(), GregorianCalendar.DECEMBER, 31, 23, 59, 59);
		Date fechaFin = new Date(gcFin.getTimeInMillis());
		params.put("fechaFin", fechaFin);
		return dao.findByNamedQuery("DiaFestivo.findByMunicipioAno", params);
	}
	
	public List<DiaFestivoDTO> findByMunicipioAnoMes(MunicipioDTO municipioDTO, int ano, int mes) throws GadirServiceException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("coMunicipio", municipioDTO.getId().getCoMunicipio());
		params.put("coProvincia", municipioDTO.getId().getCoProvincia());
		GregorianCalendar gcInicio= new GregorianCalendar(ano, mes, 1, 0, 0, 0);
		Date fechaInicio = new Date(gcInicio.getTimeInMillis());
		params.put("fechaInicio", fechaInicio);
		GregorianCalendar gcFin= new GregorianCalendar(ano, mes, 31, 23, 59, 59);
		Date fechaFin = new Date(gcFin.getTimeInMillis());
		params.put("fechaFin", fechaFin);
		return dao.findByNamedQuery("DiaFestivo.findByMunicipioAno", params);
	}
	
	public List<DiaFestivoDTO> findByMunicipioDiaTipo(MunicipioDTO municipioDTO, Date dia, String tipoDiaFestivo) throws GadirServiceException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("coMunicipio", municipioDTO.getId().getCoMunicipio());
		params.put("coProvincia", municipioDTO.getId().getCoProvincia());
		
		params.put("tipoDiaFestivo", tipoDiaFestivo);
		
		GregorianCalendar gcInicio= new GregorianCalendar();
		gcInicio.setTime(dia);
		gcInicio.set(GregorianCalendar.HOUR_OF_DAY, 0);
		gcInicio.set(GregorianCalendar.MINUTE, 0);
		gcInicio.set(GregorianCalendar.SECOND, 0);
		Date fechaInicio = new Date(gcInicio.getTimeInMillis());
		params.put("fechaInicio", fechaInicio);
		GregorianCalendar gcFin= new GregorianCalendar();
		gcFin.setTime(dia);
		gcFin.set(GregorianCalendar.HOUR_OF_DAY, 23);
		gcFin.set(GregorianCalendar.MINUTE, 59);
		gcFin.set(GregorianCalendar.SECOND, 59);
		Date fechaFin = new Date(gcFin.getTimeInMillis());
		params.put("fechaFin", fechaFin);
		return dao.findByNamedQuery("DiaFestivo.findByMunicipioDiaTipo", params);
	}
	
	public void duplicarDiaFestivos(MunicipioDTO municipioDTO, Short ano, Short anoDestino) throws GadirServiceException{
		
		List<DiaFestivoDTO> listaFestivosOrigen=findByMunicipioAno(municipioDTO, ano);
		
		Iterator<DiaFestivoDTO> it=listaFestivosOrigen.iterator();
		
		while (it.hasNext()){
			DiaFestivoDTO diaFestivoDTO = it.next();
			Date fechaOrigen = diaFestivoDTO.getDia();
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(fechaOrigen);
			gc.set(GregorianCalendar.YEAR, anoDestino);
			Date fechaDestino = gc.getTime();
			
			List<DiaFestivoDTO> listaFestivosDestino=dao.findFiltered(new String[]{"municipioDTO", "dia", "tipo"}, new Object[]{municipioDTO, fechaDestino,""+diaFestivoDTO.getTipo()});
			if (listaFestivosDestino==null || listaFestivosDestino.isEmpty()){
				DiaFestivoDTO newDiaFestivoDTO = new DiaFestivoDTO();
				newDiaFestivoDTO.setMunicipioDTO(diaFestivoDTO.getMunicipioDTO());
				newDiaFestivoDTO.setTipo(diaFestivoDTO.getTipo());
				newDiaFestivoDTO.setNombre(diaFestivoDTO.getNombre());
				newDiaFestivoDTO.setCoDiaFestivo(null);
				newDiaFestivoDTO.setDia(fechaDestino);
				newDiaFestivoDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
				newDiaFestivoDTO.setFhActualizacion(new Date());
				
				this.save(newDiaFestivoDTO);
			}
		}
		
	}
	
	public void auditorias(DiaFestivoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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
