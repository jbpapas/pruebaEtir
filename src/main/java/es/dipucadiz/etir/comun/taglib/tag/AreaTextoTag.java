
package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.TextareaTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.AreaTexto;


public class AreaTextoTag extends TextareaTag {
    

	private static final long serialVersionUID = 2831109865215200025L;
	
	protected String conFooter;
	protected String conAyuda;
	protected String nombreCampoAyuda;
	protected String salida;
	
	protected String coProcesoActual;

	protected String classText;
	protected String classLabel;
	protected String styleLabel;
	protected String styleText;
	protected String styleGroup;
	
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		coProcesoActual=stack.findString("coProcesoActual");
        return new AreaTexto(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();
        
        AreaTexto areaTexto = (AreaTexto) component;
        
        areaTexto.setConFooter(conFooter);
        areaTexto.setConAyuda(conAyuda);
        areaTexto.setNombreCampoAyuda(nombreCampoAyuda);
        areaTexto.setSalida(salida);
        
        areaTexto.setCoProcesoActual(coProcesoActual);
        
        areaTexto.setClassLabel(classLabel);
        areaTexto.setClassText(classText);
        areaTexto.setStyleLabel(styleLabel);
        areaTexto.setStyleText(styleText);
        areaTexto.setStyleGroup(styleGroup);
    }

	public String getConFooter() {
		return conFooter;
	}

	public void setConFooter(String conFooter) {
		this.conFooter = conFooter;
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

	
}
