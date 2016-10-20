package es.dipucadiz.etir.comun.taglib.tag;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.CampoTextoSelec;


public class CampoTextoSelecTag  extends CampoTextoTag {
	private static final long serialVersionUID = 1L;

	protected String isCliente;
	protected String isDomicilio;
	protected String nifValue;
	protected String codigoValue;
	protected String sufijo;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new CampoTextoSelec(stack, req, res);
    }


	protected void populateParams() {
		super.populateParams();
    
		CampoTextoSelec campoTexto = (CampoTextoSelec) component;
  
		if (!"true".equals(isCliente)) isCliente = "false";
		if (!"true".equals(isDomicilio)) isDomicilio = "false";
		campoTexto.setIsCliente(isCliente);
		campoTexto.setIsDomicilio(isDomicilio);
		campoTexto.setNifValue(nifValue);
		campoTexto.setCodigoValue(codigoValue);
		if (sufijo==null)sufijo="";
		campoTexto.setSufijo(sufijo);
		
//    Boolean accesible=DatosSesion.isConAccesibilidad();
//  
//    if (accesible !=null && accesible){
//    	 campoTexto.setTheme("gadirAccesible");  
//    }else{
//    	campoTexto.setTheme("gadirInaccesible");
//    }
		if (campoTexto.getTheme() == null) {
			campoTexto.setTheme("css_xhtml");
		}
	}


	public String getIsCliente() {
		return isCliente;
	}


	public void setIsCliente(String isCliente) {
		this.isCliente = isCliente;
	}

	public String getIsDomicilio() {
		return isCliente;
	}


	public void setIsDomicilio(String isDomicilio) {
		this.isDomicilio = isDomicilio;
	}


	public String getNifValue() {
		return nifValue;
	}


	public void setNifValue(String nifValue) {
		this.nifValue = nifValue;
	}


	public String getCodigoValue() {
		return codigoValue;
	}


	public void setCodigoValue(String codigoValue) {
		this.codigoValue = codigoValue;
	}


	public String getSufijo() {
		return sufijo;
	}


	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}

}