package es.dipucadiz.etir.comun.taglib.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.TextField;

import com.opensymphony.xwork2.util.ValueStack;

public class CampoTexto extends TextField {
    final private static String COMPONENT_NAME = CampoTexto.class.getName();
    
    public static final String TEMPLATE = "gadirCampoTexto";
    
    protected String conFooter;
    protected String conAyuda;
    protected String nombreCampoAyuda;
    protected String salida;
    protected String sinCajita;
    
    protected String coProcesoActual;
    
    protected String classText;
	protected String classLabel;
	protected String styleLabel;
	protected String styleText;
	protected String styleGroup;
        
    public CampoTexto(ValueStack stack, HttpServletRequest request,
            HttpServletResponse response) {
        super(stack, request, response);
        coProcesoActual=stack.findString("coProcesoActual");
    }

    public String getComponentName() {
        return COMPONENT_NAME;
    }


    public void evaluateExtraParams() {
        super.evaluateExtraParams();
        
        if (conFooter != null)
            addParameter("conFooter", findValue(conFooter, Boolean.class));
        
        if (conAyuda != null && conAyuda.equalsIgnoreCase("false")){
        	addParameter("conAyuda", Boolean.FALSE);
        }else{
        	addParameter("conAyuda", Boolean.TRUE);
        }
            
        
        if (labelPosition == null)
            addParameter("labelPosition", "left");
        
        if (coProcesoActual != null)
            addParameter("coProcesoActual", findValue(coProcesoActual, String.class));
        
        if (nombreCampoAyuda != null)
            addParameter("nombreCampoAyuda", findValue(nombreCampoAyuda, String.class));
        
        if (salida != null)
            addParameter("salida", findValue(salida, Boolean.class));

        if (classText != null)
        	addParameter("classText", findValue(classText, String.class));
        if (classLabel != null)
        	addParameter("classLabel", findValue(classLabel, String.class));
        if (styleLabel != null)
        	addParameter("styleLabel", findValue(styleLabel, String.class));
        if (styleText != null)
        	addParameter("styleText", findValue(styleText, String.class));
        if (styleGroup != null)
        	addParameter("styleGroup", findValue(styleGroup, String.class));
        
        if (sinCajita != null)
        	addParameter("sinCajita", findValue(sinCajita, Boolean.class));
    }

    public String getConFooter() {
    	return conFooter;
	}

	public void setConFooter(String conFooter) {
		this.conFooter = conFooter;
	}
	
	protected String getDefaultTemplate() {
        return TEMPLATE;
    }

	public String getConAyuda() {
		return conAyuda;
	}

	public void setConAyuda(String conAyuda) {
		this.conAyuda = conAyuda;
	}

	public String getCoProcesoActual() {
		return coProcesoActual;
	}

	public void setCoProcesoActual(String coProcesoActual) {
		this.coProcesoActual = coProcesoActual;
	}

	public String getNombreCampoAyuda() {
		return nombreCampoAyuda;
	}

	public void setNombreCampoAyuda(String nombreCampoAyuda) {
		this.nombreCampoAyuda = nombreCampoAyuda;
	}

	public String getSalida() {
		return salida;
	}

	public void setSalida(String salida) {
		this.salida = salida;
	}

	public String getClassText() {
		return classText;
	}

	public void setClassText(String classText) {
		this.classText = classText;
	}

	public String getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}

	public String getStyleLabel() {
		return styleLabel;
	}

	public void setStyleLabel(String styleLabel) {
		this.styleLabel = styleLabel;
	}

	public String getStyleText() {
		return styleText;
	}

	public void setStyleText(String styleText) {
		this.styleText = styleText;
	}

	public String getStyleGroup() {
		return styleGroup;
	}

	public void setStyleGroup(String styleGroup) {
		this.styleGroup = styleGroup;
	}

	public String getSinCajita() {
		return sinCajita;
	}

	public void setSinCajita(String sinCajita) {
		this.sinCajita = sinCajita;
	}

    
}
