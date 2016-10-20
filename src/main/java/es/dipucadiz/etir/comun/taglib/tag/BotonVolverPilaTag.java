package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.ComponentTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.BotonVolverPila;

public class BotonVolverPilaTag extends ComponentTag {

	private static final long serialVersionUID = -5333499487882595094L;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new BotonVolverPila(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
	}

	
	

}
