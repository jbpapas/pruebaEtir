package es.dipucadiz.etir.comun.taglib.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Submit;

import com.opensymphony.xwork2.util.ValueStack;

public class OpcionCondicional extends Submit {
    final private static String COMPONENT_NAME = OpcionCondicional.class.getName();
    
    protected String nuDocumento;
    protected String accion;
    
    protected String coProcesoActual;
    
    protected String mostrar = null;
    
        
    public OpcionCondicional(ValueStack stack, HttpServletRequest request,
            HttpServletResponse response) {
        super(stack, request, response);
        coProcesoActual=stack.findString("coProcesoActual");
    }

    public String getComponentName() {
        return COMPONENT_NAME;
    }

    public String getDefaultOpenTemplate() {
    	if (mostrar!=null && mostrar.equals("true")){
    		return "submit";
    	}
    	else{
    		return "vacio";
    	}
    }

    protected String getDefaultTemplate() {
    	if (mostrar!=null && mostrar.equals("true")){
    		return "submit-close";
    	}
    	else{
    		return "vacio";
    	}
    }


    public void evaluateExtraParams() {
        super.evaluateExtraParams();
        
        if (coProcesoActual != null)
            addParameter("coProcesoActual", findValue(coProcesoActual, String.class));
        
        if (accion != null)
            addParameter("accion", findValue(accion, String.class));
        
        if (nuDocumento != null)
            addParameter("nuDocumento", findValue(nuDocumento, String.class));
        
//        if (mostrar == null)
//        	mostrar=OpcionCondicionalUtil.esMostrar(DatosSesion.getCoProcesoActual(), accion, nuDocumento)?"true":"false";
    }

	public String getNuDocumento() {
		return nuDocumento;
	}

	public void setNuDocumento(String nuDocumento) {
		this.nuDocumento = nuDocumento;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getCoProcesoActual() {
		return coProcesoActual;
	}

	public void setCoProcesoActual(String coProcesoActual) {
		this.coProcesoActual = coProcesoActual;
	}

	public String getMostrar() {
		return mostrar;
	}

	public void setMostrar(String mostrar) {
		this.mostrar = mostrar;
	}

	
    
}
