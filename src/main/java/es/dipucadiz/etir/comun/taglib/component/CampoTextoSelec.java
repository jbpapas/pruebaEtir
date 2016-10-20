package es.dipucadiz.etir.comun.taglib.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class CampoTextoSelec extends CampoTexto {
	
	/**
     * Atributo que almacena el template al que ligamos este componente.
     */
	public static final String TEMPLATE = "textfieldSelect";
	
	
	/**
     * Atributo que almacena el nombre del componente.
     */
    final private static String COMPONENT_NAME = CampoTextoSelec.class.getName();
    
	protected String isCliente;
	protected String isDomicilio;
	protected String nifValue;
	protected String codigoValue;
	protected String sufijo;
	
	public CampoTextoSelec(ValueStack stack, HttpServletRequest request,
            HttpServletResponse response) {
        super(stack, request, response);
        
    }
	
	
	 public String getComponentName() {
	        return COMPONENT_NAME;
	    }
	 public void evaluateExtraParams() {
	        if (isCliente != null)
	            addParameter("isCliente", findValue(isCliente, Boolean.class));
	        if (isDomicilio != null)
	            addParameter("isDomicilio", findValue(isDomicilio, Boolean.class));
	        if (nifValue != null)
	            addParameter("nifValue", findValue(nifValue, String.class));
	        if (codigoValue != null)
	            addParameter("codigoValue", findValue(codigoValue, String.class));
	        if (sufijo != null)
	        	addParameter("sufijo", findValue(sufijo, String.class));
	        addParameter("cacheId", Utilidades.getDateActual().getTime());
	        
	        super.evaluateExtraParams();
	 }
	 
	 
	 
	 
	 //GETTER´s & SETTER´s
	 
	 protected String getDefaultTemplate() {
	        return TEMPLATE;
	    }


	public static String getTEMPLATE() {
		return TEMPLATE;
	}


	public static String getCOMPONENT_NAME() {
		return COMPONENT_NAME;
	}


	public void setIsCliente(String isCliente) {
		this.isCliente = isCliente;
	}


	public String getIsCliente() {
		return isCliente;
	}

	public void setIsDomicilio(String isDomicilio) {
		this.isDomicilio = isDomicilio;
	}


	public String getIsDomicilio() {
		return isDomicilio;
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
