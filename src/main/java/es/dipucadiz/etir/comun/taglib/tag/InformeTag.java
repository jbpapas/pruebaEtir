package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.ComponentTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.Informe;

public class InformeTag extends ComponentTag {

	private static final long serialVersionUID = -2995405986537206779L;
	
	protected String metodo;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Informe(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		((Informe) component).setMetodo(this.metodo);
	}

	
	
	
	
	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}


}
