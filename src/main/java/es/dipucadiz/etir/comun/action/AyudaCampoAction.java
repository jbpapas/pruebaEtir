package es.dipucadiz.etir.comun.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.AyudaCampoBO;
import es.dipucadiz.etir.comun.dto.AyudaCampoDTO;

final public class AyudaCampoAction extends AbstractGadirBaseAction {
	
	private static final long serialVersionUID = -785972905522959721L;

	private AyudaCampoBO ayudaCampoBO;
	
	private String coProceso;
	
	private String campo;
	
	private String textoAyuda;

	private static final Log LOG = LogFactory.getLog(AyudaCampoAction.class);

	private AyudaCampoAction() {}
	
	public String getAyuda(){
		
		textoAyuda="";
		
		try{
			List<AyudaCampoDTO> lista=ayudaCampoBO.findByProcesoAndCampo(coProceso, campo);
			
			if (lista!=null && !lista.isEmpty()){
				AyudaCampoDTO ayudaCampoDTO = lista.get(0);
				textoAyuda=ayudaCampoDTO.getTextoAyuda();
			}
		}catch(Exception e){
			LOG.warn("Error cobteniendo ayuda de campo", e);
		}
		
		if (textoAyuda==null || textoAyuda.equals("")){
			try{
				List<AyudaCampoDTO> lista=ayudaCampoBO.findByProcesoAndCampo("*", campo);
				
				if (lista!=null && !lista.isEmpty()){
					AyudaCampoDTO ayudaCampoDTO = lista.get(0);
					textoAyuda=ayudaCampoDTO.getTextoAyuda();
				}
			}catch(Exception e){
				LOG.warn("Error cobteniendo ayuda de campo", e);
			}
		}
		
		if (textoAyuda==null || textoAyuda.equals("")){
			textoAyuda="No hay ayuda definida para el campo '" + campo + "' y el proceso '" + coProceso + "'.";
		}
		
		return "ayudaCampo";
	}

	public AyudaCampoBO getAyudaCampoBO() {
		return ayudaCampoBO;
	}

	public void setAyudaCampoBO(AyudaCampoBO ayudaCampoBO) {
		this.ayudaCampoBO = ayudaCampoBO;
	}

	public String getCoProceso() {
		return coProceso;
	}

	public void setCoProceso(String coProceso) {
		this.coProceso = coProceso;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getTextoAyuda() {
		return textoAyuda;
	}

	public void setTextoAyuda(String textoAyuda) {
		this.textoAyuda = textoAyuda;
	}

	
	
	

}
