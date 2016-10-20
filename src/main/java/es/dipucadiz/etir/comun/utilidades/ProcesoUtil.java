package es.dipucadiz.etir.comun.utilidades;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.AcmMenuBO;
import es.dipucadiz.etir.comun.bo.CruceBO;
import es.dipucadiz.etir.comun.bo.CruceResultadosBO;
import es.dipucadiz.etir.comun.bo.EjecucionBO;
import es.dipucadiz.etir.comun.bo.ExtraccionInformeBO;
import es.dipucadiz.etir.comun.bo.InformeBO;
import es.dipucadiz.etir.comun.bo.ProcesoBO;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.AcmMenuDTO;
import es.dipucadiz.etir.comun.dto.CruceGrupoDTO;
import es.dipucadiz.etir.comun.dto.CruceResultadoDTO;
import es.dipucadiz.etir.comun.dto.EjecucionDTO;
import es.dipucadiz.etir.comun.dto.EjecucionParametroDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionInformeDTO;
import es.dipucadiz.etir.comun.dto.ExtraccionInformeDTOId;
import es.dipucadiz.etir.comun.dto.InformeDTO;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class ProcesoUtil {
	
	private static ProcesoBO procesoBO;
	private static AcmMenuBO acmMenuBO;
	private static EjecucionBO ejecucionBO;
	private static InformeBO informeBO;
	private static CruceBO cruceBO;
	private static CruceResultadosBO cruceResultadosBO;
	private static ExtraccionInformeBO extraccionInformeBO;
	
	private static final Log LOG = LogFactory.getLog(ProcesoUtil.class);
	
	public static ProcesoDTO getProcesoByAction(String action) {
		ProcesoDTO procesoDTO=null;
		
		try{
			procesoDTO=procesoBO.findByUrlAndTipoMP(action);
		}catch(Exception e){
			LOG.warn("Error obteniendo coProceso a partir del nombre de action", e);
		}
		
		/*if (procesoDTO==null){
			try{
				String trozo="";
				action= action.substring(action.lastIndexOf('/')+1);
				char[] charArray = action.toCharArray();
				int i=1;
				while (i<action.length() && Utilidades.isNumeric(""+charArray[i])){
					i++;
				}
				trozo=action.substring(0, i);
				procesoDTO=procesoBO.findById(trozo);
			}catch(Exception e){
				LOG.warn("Error obteniendo coProceso a partir del nombre de action", e);
			}
		}*/
		
		return procesoDTO;
	}
	
	public static ProcesoDTO getProcesoByCoProceso(String coProceso) {
		ProcesoDTO procesoDTO=null;
		
		try{
			procesoDTO=procesoBO.findById(coProceso);
		}catch(Exception e){
			LOG.warn("Error obteniendo procesoDTO a partir de coProceso", e);
		}
		
		return procesoDTO;
	}
	
	public static List<AcmMenuDTO> getAcmMenusByProceso(ProcesoDTO procesoDTO){
		List<AcmMenuDTO> acmMenuDTOs=null;
		
		try{
			acmMenuDTOs =(acmMenuBO.findFiltered("procesoDTO", procesoDTO));
		}catch(Exception e){
			LOG.info("No hay un AcmMenuDTO asociado al ProcesoDTO", e);
		}
		
		return acmMenuDTOs;
	}

	public static ProcesoBO getProcesoBO() {
		return procesoBO;
	}

	public static boolean pdfComienzaConCoEjecucion(InformeDTO informeDTO) {
		boolean resultado;
		int index = informeDTO.getRutaPdf().lastIndexOf('/');
		if (index == -1) index = informeDTO.getRutaPdf().lastIndexOf('\\');
		String nombreInforme = informeDTO.getRutaPdf().substring(index + 1);
		if (informeDTO.getEjecucionDTO() == null) {
			resultado = nombreInforme.startsWith("0_");
		} else {
			resultado = nombreInforme.startsWith(informeDTO.getEjecucionDTO().getCoEjecucion().toString() + "_");
		}
		return resultado;
	}
	public static String getNombreInformeByProceso(InformeDTO informeDTO) {
		try {
			String[] propertiesToInitialize = {"ejecucionParametroDTOs"};
			EjecucionDTO ejecucionDTO = ejecucionBO.findByIdInitialized(informeDTO.getEjecucionDTO().getCoEjecucion(), propertiesToInitialize);
			if (BatchConstants.CO_PROCESO_CRUCES.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				// Recuperar nombre a partir del cruce.
				Long coCruce = null;
				boolean isConSumatorio = false;
				String argumentos[] = null;
				// Obtengo los parámetros de ejecución
				for (EjecucionParametroDTO paramDTO : ejecucionDTO.getEjecucionParametroDTOs()) {
					switch (paramDTO.getId().getNuParametro()) {
					case 1:
						coCruce = Long.parseLong(paramDTO.getValor());
						break;
					case 3:
						isConSumatorio = "S".equals(paramDTO.getValor());
						break;
					case 4:
						argumentos = paramDTO.getValor().split(",");
						break;
					}
				}
				// Ubico el orden del informe actual
				int[] ordenes = extraerOrdenesRutaPdf(informeDTO.getRutaPdf());
				int ordenInforme = ordenes[0];
				int numTrozo = ordenes[1];
				// Obtengo los nombres de los resultados tipo I del cruce en cuestión.
				CruceGrupoDTO cruceGrupoDTO = cruceBO.findById(coCruce);
				int contadorOrden=0;
				if (isConSumatorio) {
					contadorOrden++;
					if (contadorOrden == ordenInforme) {
						return cruceGrupoDTO.getMunicipioDTO().getId().getCoProvincia() + 
								cruceGrupoDTO.getMunicipioDTO().getId().getCoMunicipio() + 
								" - " + "Sumatorio";
					}
				}
				for (String argumento : argumentos) {
					List<CruceResultadoDTO> resultadoDTOs = cruceResultadosBO.findFiltered(new String[]{"id.coCruceGrupo", "id.coArgumento", "tipo"}, new Object[]{coCruce, Short.parseShort(argumento), "I"}, "id.coResultado", DAOConstant.ASC_ORDER);
					for (CruceResultadoDTO resultadoDTO : resultadoDTOs) {
						contadorOrden++;
						if (contadorOrden == ordenInforme) {
							return cruceGrupoDTO.getMunicipioDTO().getId().getCoProvincia() + 
									cruceGrupoDTO.getMunicipioDTO().getId().getCoMunicipio() + " - " +
									resultadoDTO.getNombre() + (numTrozo>1 ? " (trozo " + numTrozo + ")" : "");
						}
					}
					
				}
				return informeDTO.getRutaPdf().substring(informeDTO.getRutaPdf().lastIndexOf('/'));
				
			} else if (BatchConstants.CO_PROCESO_LANZAR_PETICION.equals(ejecucionDTO.getProcesoDTO().getCoProceso())) {
				// Recuperar nombre a partir de la extracción.
				String coExtraccion = null;
				short coExtraccionInforme = -1;
				// Obtengo los parámetros de ejecución
				for (EjecucionParametroDTO paramDTO : ejecucionDTO.getEjecucionParametroDTOs()) {
					switch (paramDTO.getId().getNuParametro()) {
					case 1:
						coExtraccion = paramDTO.getValor();
						break;
					case 5:
						coExtraccionInforme = Short.parseShort((paramDTO.getValor()));
						break;
					}
				}
				// Ubico el orden del informe actual
				int[] ordenes = extraerOrdenesRutaPdf(informeDTO.getRutaPdf());
				int numTrozo = ordenes[1];
				// Obtengo el nombre del informe a partir de la extracción y el orden del informe
				ExtraccionInformeDTO extraccionInformeDTO = extraccionInformeBO.findById(new ExtraccionInformeDTOId(coExtraccion, coExtraccionInforme));
				String result;
				if (extraccionInformeDTO == null) {
					result = coExtraccion;
				} else {
					result = extraccionInformeDTO.getNombre();
				}
				if (numTrozo > 1) {
					result += " (trozo " + numTrozo + ")";
				}
				return result;
			}
			
		} catch (GadirServiceException e) {
			LOG.error(e);
			return informeDTO.getCoInforme() + ": " + e.getMensaje();
		} catch (Exception e) {
			LOG.error(e);
			return informeDTO.getCoInforme() + ": " + e.getLocalizedMessage();
		}
		return "Sin nombre (" + informeDTO.getCoInforme() + ")";
	}
	public static String getNombreInformeByRutaPdf(final String rutaPdf) {
		String nombreInforme;
		if (rutaPdf.contains("-")) {
			nombreInforme = rutaPdf.substring(0, rutaPdf.lastIndexOf('-'));
		} else if (rutaPdf.contains(".")) {
			nombreInforme = rutaPdf.substring(0, rutaPdf.lastIndexOf('.'));
		} else {
			nombreInforme = rutaPdf;
		}
		if (nombreInforme.contains("/")) {
			nombreInforme = nombreInforme.substring(nombreInforme.lastIndexOf('/') + 1);
		} else if (nombreInforme.contains("\\")) {
			nombreInforme = nombreInforme.substring(nombreInforme.lastIndexOf('\\') + 1);
		}
		return Utilidades.sustituir(nombreInforme, "_", " ");
	}
	
	private static int[] extraerOrdenesRutaPdf(String rutaPdf) {
		String nombre = rutaPdf.substring(rutaPdf.lastIndexOf('/')+1);
		nombre = nombre.substring(nombre.indexOf('_')+1);
		int ordenInforme = Integer.parseInt(nombre.substring(0,nombre.indexOf('_')));
		nombre = nombre.substring(nombre.indexOf('_')+1);
		int numTrozo = Integer.parseInt(nombre.substring(0, nombre.indexOf('_')));
		int[] result = new int[2];
		result[0] = ordenInforme;
		result[1] = numTrozo;
		return result;
	}

	public void setProcesoBO(ProcesoBO procesoBO) {
		ProcesoUtil.procesoBO = procesoBO;
	}

	public static AcmMenuBO getAcmMenuBO() {
		return acmMenuBO;
	}

	public void setAcmMenuBO(AcmMenuBO acmMenuBO) {
		ProcesoUtil.acmMenuBO = acmMenuBO;
	}

	public static EjecucionBO getEjecucionBO() {
		return ejecucionBO;
	}

	public void setEjecucionBO(EjecucionBO ejecucionBO) {
		ProcesoUtil.ejecucionBO = ejecucionBO;
	}

	public static InformeBO getInformeBO() {
		return informeBO;
	}

	public void setInformeBO(InformeBO informeBO) {
		ProcesoUtil.informeBO = informeBO;
	}

	public static CruceBO getCruceBO() {
		return cruceBO;
	}

	public void setCruceBO(CruceBO cruceBO) {
		ProcesoUtil.cruceBO = cruceBO;
	}

	public static CruceResultadosBO getCruceResultadosBO() {
		return cruceResultadosBO;
	}

	public void setCruceResultadosBO(CruceResultadosBO cruceResultadosBO) {
		ProcesoUtil.cruceResultadosBO = cruceResultadosBO;
	}

	public static ExtraccionInformeBO getExtraccionInformeBO() {
		return extraccionInformeBO;
	}

	public void setExtraccionInformeBO(ExtraccionInformeBO extraccionInformeBO) {
		ProcesoUtil.extraccionInformeBO = extraccionInformeBO;
	}

	
	
}
