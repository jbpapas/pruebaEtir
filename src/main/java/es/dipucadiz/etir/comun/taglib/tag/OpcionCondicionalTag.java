
package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.SubmitTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.OpcionCondicional;


public class OpcionCondicionalTag extends SubmitTag {
    

	private static final long serialVersionUID = 2831109865215200025L;
	
	protected String nuDocumento;
    protected String accion;
    
    protected String coProcesoActual;
    
    protected String mostrar;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		coProcesoActual=stack.findString("coProcesoActual");
        return new OpcionCondicional(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();
        
        OpcionCondicional opcionCondicional = (OpcionCondicional) component;
        
        opcionCondicional.setNuDocumento(nuDocumento);
        opcionCondicional.setAccion(accion);
        opcionCondicional.setCoProcesoActual(coProcesoActual);
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

	

	public String getMostrar() {
		return mostrar;
	}

	public void setMostrar(String mostrar) {
		this.mostrar = mostrar;
	}

	public String getCoProcesoActual() {
		return coProcesoActual;
	}

	public void setCoProcesoActual(String coProcesoActual) {
		this.coProcesoActual = coProcesoActual;
	}

	
	
}
