package es.dipucadiz.etir.comun.utilidades;

import java.util.List;

import es.dipucadiz.etir.comun.dto.AcmMenuDTO;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;

public class MenuUtil {

	public static String getMenuHtml(List<AcmMenuDTO> listaAcmMenus){
		return getMenuHtml(listaAcmMenus, "jstree");
	}
	
	public static String getMenuHtml(List<AcmMenuDTO> listaAcmMenus, String prefijo){
		
		String menuHtml="";
		
		for (AcmMenuDTO acmMenuDTO : listaAcmMenus) {
			
			if (acmMenuDTO.getAcmMenuDTO() == null) {
				menuHtml += cargaItemMenu(acmMenuDTO, acmMenuDTO.getProcesoDTO(), listaAcmMenus, true, prefijo);
			}
		}
		
		return menuHtml;
	}

	public static String getMenuHtmlCheckbox(List<AcmMenuDTO> listaAcmMenus, List<AcmMenuDTO> listaAcmMenuseleccionados){
		return getMenuHtmlCheckbox(listaAcmMenus, listaAcmMenuseleccionados, "jstree");
	}
	
	public static String getMenuHtmlCheckbox(List<AcmMenuDTO> listaAcmMenus, List<AcmMenuDTO> listaAcmMenuseleccionados, String prefijo){
		
		String menuHtml="";
		
		if (listaAcmMenus!=null){
		for (AcmMenuDTO acmMenuDTO : listaAcmMenus) {
			
			if (acmMenuDTO.getAcmMenuDTO() == null) {
				menuHtml += cargaItemMenuCheckbox(acmMenuDTO, acmMenuDTO.getProcesoDTO(), listaAcmMenus, listaAcmMenuseleccionados, true, prefijo);
			}
		}
		}
		
		return menuHtml;
	}
	
	public static String getMenuHtmlCheckboxSubsistema(List<AcmMenuDTO> listaAcmMenus, List<AcmMenuDTO> listaAcmMenuseleccionados, int subsistema){
		return getMenuHtmlCheckboxSubsistema(listaAcmMenus, listaAcmMenuseleccionados, subsistema, "jstree");
	}
	
	public static String getMenuHtmlCheckboxSubsistema(List<AcmMenuDTO> listaAcmMenus, List<AcmMenuDTO> listaAcmMenuseleccionados, int subsistema, String prefijo){
		
		String menuHtml="";
		
		if (listaAcmMenus!=null){
		for (AcmMenuDTO acmMenuDTO : listaAcmMenus) {
			
			if (acmMenuDTO.getAcmMenuDTO() == null && acmMenuDTO.getOrden() == subsistema) {
				menuHtml += cargaItemMenuCheckbox(acmMenuDTO, acmMenuDTO.getProcesoDTO(), listaAcmMenus, listaAcmMenuseleccionados, true, prefijo);
			}
		}
		}
		
		return menuHtml;
	}
	
	private static String cargaItemMenu(AcmMenuDTO acmMenuDTO, ProcesoDTO procesoDTO, List<AcmMenuDTO> listaAcmMenus, boolean header, String prefijo) {
		String cadHtml="";
		try {
		
		if (acmMenuDTO.getProcesoDTO() == null || acmMenuDTO.getProcesoDTO().getCoProceso()==null) {
			
			boolean isCarpetaSinHijos = true;
			if (header && acmMenuDTO.getProcesoDTO() == null) {
				for (AcmMenuDTO menuHijo : listaAcmMenus) {
					if (menuHijo.getAcmMenuDTO()!=null && menuHijo.getAcmMenuDTO().equals(acmMenuDTO)) {
						isCarpetaSinHijos = false;
						break;
					}
				}
			} else {
				isCarpetaSinHijos = false;
			}
			
			if (!isCarpetaSinHijos) {
				if (header){
					cadHtml += "<div id=\"" + prefijo + "_header_" + acmMenuDTO.getCoAcmMenu().trim() + "\" class=\"menu-header\">" + acmMenuDTO.getNombre() + "</div>";
					cadHtml += "<div class=\"menu-body\" style=\"display:none\" id=\"" + prefijo + "_" + acmMenuDTO.getCoAcmMenu().trim() + "\">";				
				}else{
					cadHtml += "<li style=\"cursor:default\" id=\"" + prefijo + "_"+ acmMenuDTO.getCoAcmMenu().trim() +"\"><ins>&nbsp;</ins>" + acmMenuDTO.getNombre() + "";
				}
				
				cadHtml += "<ul>";
			
				for (AcmMenuDTO menuHijo : listaAcmMenus){				
					
					ProcesoDTO procesoHijo = menuHijo.getProcesoDTO();
					
					if (menuHijo.getAcmMenuDTO()!=null && menuHijo.getAcmMenuDTO().equals(acmMenuDTO)){
						
						cadHtml += cargaItemMenu(menuHijo, procesoHijo, listaAcmMenus, false, prefijo);
					}
				}
				
	
				cadHtml += "</ul>";
				if (header){
					cadHtml += "</div>";
				}else{
					cadHtml += "</li>";	
				}
			}

		} else {
			cadHtml += "<li id=\"" + prefijo + "_"+ acmMenuDTO.getCoAcmMenu().trim() +"\" rel=\"hoja\" ><a href=\"/etir/" + procesoDTO.getUrl() + "\" title=\"" + acmMenuDTO.getDescripcion() + "\"> <ins style=\"background-position:-16px 0;\"> </ins>" + acmMenuDTO.getNombre() + "</a></li>";
			//style=\"white-space:normal;\" 
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cadHtml;
	}
	
	private static String cargaItemMenuCheckbox(AcmMenuDTO acmMenuDTO, ProcesoDTO procesoDTO, List<AcmMenuDTO> listaAcmMenus, List<AcmMenuDTO> listaAcmMenuseleccionados, boolean header, String prefijo) {
		String cadHtml="";
		
		boolean seleccionado=false;
		
		if (listaAcmMenuseleccionados.contains(acmMenuDTO)){
			seleccionado=true;
		}
		
		try {
		
		if (acmMenuDTO.getProcesoDTO() == null || acmMenuDTO.getProcesoDTO().getCoProceso()==null) {
			
			if (header){
				cadHtml += "<div class=\"menu-header\">" + acmMenuDTO.getNombre() + "</div>";
				cadHtml += "<div class=\"menu-body\" id=\"" + prefijo + "_O" + acmMenuDTO.getOrden() + "\">";				
			}else{
				//cadHtml += "<li class=\"open\" id=\"" + prefijo + "_"+ acmMenuDTO.getCoAcmMenu() +"\"><a href=\"#\"><ins>&nbsp;</ins>" + acmMenuDTO.getNombre() + "</a>";
				cadHtml += "<li id=\"" + prefijo + "_"+ acmMenuDTO.getCoAcmMenu().trim() +"\"><a id=\"" + acmMenuDTO.getCoAcmMenu().trim() + "\" " + (seleccionado?"class=\"checked\"":"") + " href=\"#\"><ins>&nbsp;</ins>" + acmMenuDTO.getNombre() + "</a>";
			}
			
			cadHtml += "<ul>";
		
			for (AcmMenuDTO menuHijo : listaAcmMenus){				
				
				ProcesoDTO procesoHijo = menuHijo.getProcesoDTO();
				
				if (menuHijo.getAcmMenuDTO()!=null && menuHijo.getAcmMenuDTO().equals(acmMenuDTO)){
					
					cadHtml += cargaItemMenuCheckbox(menuHijo, procesoHijo, listaAcmMenus, listaAcmMenuseleccionados, false, prefijo);
				}
			}
			

			cadHtml += "</ul>";
			if (header){
				cadHtml += "</div>";
			}else{
				cadHtml += "</li>";	
			}

		} else {
			cadHtml += "<li id=\"" + prefijo + "_"+ acmMenuDTO.getCoAcmMenu().trim() +"\" ><a id=\"" + acmMenuDTO.getCoAcmMenu().trim() + "\" " + (seleccionado?"class=\"checked\"":"") + " href=\"#\"><ins>&nbsp;</ins>" + acmMenuDTO.getNombre() + "</a></li>";
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cadHtml;
	}
	
	public static String ponePrefijosMenuAccesible(String coAcmMenu, String nombre, ProcesoDTO procesoDTO, AcmMenuDTO acmMenuPadreDTO){
		String resultado="";
		
			if (procesoDTO==null){
				if(acmMenuPadreDTO==null){
					resultado+="SUB";
				}else{
					resultado+="(";
				}
			}
			resultado += coAcmMenu.substring(1) + ", " + nombre;
			if (procesoDTO==null){
				if(acmMenuPadreDTO==null){
					
				}else{
					resultado+=")";
				}
			}
		
		return resultado;
	}
	
}
