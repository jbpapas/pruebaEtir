package es.dipucadiz.etir.comun.utilidades;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CalleUbicacionBO;
import es.dipucadiz.etir.comun.bo.DomicilioBO;
import es.dipucadiz.etir.comun.bo.UnidadUrbanaBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.CalleUbicacionDTO;
import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class DomicilioUtil {
	
	private static DomicilioBO domicilioBO;
	private static UnidadUrbanaBO unidadUrbanaBO;
	
	private static final Log LOG = LogFactory.getLog(DomicilioUtil.class);
	
	public static String getDescripcionDomicilio(long coDomicilio, boolean conMunicipio){

		String result="";

		try{
			DetachedCriteria dc = DetachedCriteria.forClass(DomicilioDTO.class);
			
			dc.createAlias("unidadUrbanaDTO", "uu");
			dc.createAlias("uu.calleDTO", "c");
			if (conMunicipio){
				dc.createAlias("c.municipioDTO", "m");
				dc.createAlias("m.provinciaDTO", "p");
			}
			
			dc.add(Restrictions.eq("coDomicilio",coDomicilio));
			
			DomicilioDTO domicilioDTO = domicilioBO.findByCriteria(dc, 0, 1).get(0);
			result = getDescripcionDomicilio(domicilioDTO, conMunicipio);
			
		}catch(Exception e){
			LOG.warn("no se encuentra domicilio con coDomicilio " + coDomicilio);
		}

		return result;
	}
	public static String getDescripcionDomicilioJSP(long coDomicilio, boolean conMunicipio){
		return StringEscapeUtils.escapeHtml(getDescripcionDomicilio(coDomicilio,conMunicipio));
	}
	
	public static DomicilioDTO getDomicilioFiscal(Long coCliente, boolean conMunicipio) throws GadirServiceException {
		DomicilioDTO res = null;
		
		DetachedCriteria dc = DetachedCriteria.forClass(DomicilioDTO.class);
		
		dc.createAlias("unidadUrbanaDTO", "uu");
		dc.createAlias("uu.calleDTO", "c");
		if (conMunicipio){
			dc.createAlias("c.municipioDTO", "m");
			dc.createAlias("m.provinciaDTO", "p");
		}
		
		dc.add(Restrictions.eq("boFiscalMunicipal",new Boolean(true)));
		dc.add(Restrictions.eq("clienteDTO.coCliente",coCliente));

		try{
			List<DomicilioDTO> domicilios =domicilioBO.findByCriteria(dc, 0, 1); 
			res=null;
			if(!domicilios.isEmpty())
				res = domicilios.get(0);
		}catch(Exception e){
			LOG.error("El cliente no tiene domicilio fiscal: " + coCliente, e);
		}
		return res;
	}
	
	public static String getDescripcionDomicilioFiscal(long coCliente, boolean conMunicipio){
		String result="";

		try{
			DomicilioDTO domicilioDTO = getDomicilioFiscal(coCliente, conMunicipio);
			result = getDescripcionDomicilio(domicilioDTO, conMunicipio);
			
		}catch(Exception e){
			LOG.warn("no se encuentra domicilio fiscal para el coCliente " + coCliente);
		}

		return result;
	}
	
	public static String getDescripcionDomicilioByUnidadUrbana(Long coUnidadUrbana, boolean conMunicipio) {	
		StringBuffer result = new StringBuffer();
		
		try{
			DetachedCriteria dc = DetachedCriteria.forClass(UnidadUrbanaDTO.class, "uu");
			
			dc.createAlias("uu.calleDTO", "c");
			if (conMunicipio){
				dc.createAlias("c.municipioDTO", "m");
				dc.createAlias("m.provinciaDTO", "p");
			}
			
			dc.add(Restrictions.eq("coUnidadUrbana", coUnidadUrbana));
			
			UnidadUrbanaDTO unidadUrbanaDTO = unidadUrbanaBO.findByCriteria(dc, 0, 1).get(0);
				
			if (Utilidades.isNotEmpty(unidadUrbanaDTO.getCalleDTO().getSigla())){
				result.append(unidadUrbanaDTO.getCalleDTO().getSigla());
			}
			if (Utilidades.isNotEmpty(unidadUrbanaDTO.getCalleDTO().getNombreCalle())){
				result.append(" ").append(unidadUrbanaDTO.getCalleDTO().getNombreCalle());
			}
			
			result.append(" ").append(unidadUrbanaDTO.getCadenaCompleta());
			/*if (domicilioDTO.getUnidadUrbanaDTO().getNumero() != null){
				result.append(" ").append(domicilioDTO.getUnidadUrbanaDTO().getNumero());
			}
			if (Utilidades.isNotEmpty(domicilioDTO.getUnidadUrbanaDTO().getLetra())){
				result.append(" ").append(domicilioDTO.getUnidadUrbanaDTO().getLetra());
			}
			if (Utilidades.isNotEmpty(domicilioDTO.getUnidadUrbanaDTO().getBloque())){
				result.append(" ").append(domicilioDTO.getUnidadUrbanaDTO().getBloque());
			}
			if (Utilidades.isNotEmpty(domicilioDTO.getUnidadUrbanaDTO().getEscalera())){
				result.append(" ").append(domicilioDTO.getUnidadUrbanaDTO().getEscalera());
			}
			if (Utilidades.isNotEmpty(domicilioDTO.getUnidadUrbanaDTO().getPlanta())){
				result.append(" ").append(domicilioDTO.getUnidadUrbanaDTO().getPlanta());
			}
			if (Utilidades.isNotEmpty(domicilioDTO.getUnidadUrbanaDTO().getPuerta())){
				result.append(" ").append(domicilioDTO.getUnidadUrbanaDTO().getPuerta());
			}
			if (domicilioDTO.getUnidadUrbanaDTO().getKm() != null && !domicilioDTO.getUnidadUrbanaDTO().getKm().equals(0)){
				result.append(domicilioDTO.getUnidadUrbanaDTO().getKm()).append(" ");
			}*/
			
			if (unidadUrbanaDTO.getCp() != null && !unidadUrbanaDTO.getCp().equals(0)) {
				result.append(", ").append(unidadUrbanaDTO.getCp());
			}
			if (conMunicipio){
				if (unidadUrbanaDTO.getCalleDTO().getMunicipioDTO() != null){
					result.append(", ").append(unidadUrbanaDTO.getCalleDTO().getMunicipioDTO().getNombre());
					if(unidadUrbanaDTO.getCalleDTO().getCalleUbicacionDTO() != null) {
						String ubicacion = "";
						try {
							CalleUbicacionDTO calleUbicacionDTO = ((CalleUbicacionBO) GadirConfig.getBean("calleUbicacionBO")).findById(unidadUrbanaDTO.getCalleDTO().getCalleUbicacionDTO().getCoCalleUbicacion());
							ubicacion = calleUbicacionDTO.getUbicacion();
						} catch(Exception e) {
							ubicacion = "";
						}
						result.append(" - ").append(ubicacion);
					}
					result.append(" (").append(unidadUrbanaDTO.getCalleDTO().getMunicipioDTO().getProvinciaDTO().getNombre()).append(")");
				}
			}
		}catch(Exception e){
			LOG.warn("no se encuentra domicilio con coDomicilio " + coUnidadUrbana);
		}
		
		String resultString;
		if (result.length() == 0){
			resultString = "";
		} else {
			resultString = result.toString();
		}
		
		return resultString;
	}
	
	private static String getDescripcionDomicilio(DomicilioDTO domicilioDTO, boolean conMunicipio) {
		return getDescripcionDomicilioByUnidadUrbana(domicilioDTO.getUnidadUrbanaDTO().getCoUnidadUrbana(), conMunicipio);
	}
	
	public static DomicilioDTO getDomicilioFiscalInicializado(Long coCliente, boolean conClienteDTO) throws GadirServiceException {
		return domicilioBO.findDomicilioFiscalByCliente(coCliente, conClienteDTO);
	}

	public static DomicilioDTO getDomicilioInicializado(Long coDomicilio, boolean conClienteDTO) throws GadirServiceException {
		return domicilioBO.findDomicilioByIdInicializado(coDomicilio, conClienteDTO);
	}

	public DomicilioBO getDomicilioBO() {
		return domicilioBO;
	}

	public void setDomicilioBO(DomicilioBO domicilioBO) {
		DomicilioUtil.domicilioBO = domicilioBO;
	}
	
	public UnidadUrbanaBO getUnidadUrbanaBO() {
		return unidadUrbanaBO;
	}
	
	public void setUnidadUrbanaBO(UnidadUrbanaBO unidadUrbanaBO) {
		DomicilioUtil.unidadUrbanaBO = unidadUrbanaBO;
	}

}
