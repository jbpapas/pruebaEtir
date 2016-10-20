package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.ComponentTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.PaginaActual;

public class PaginaActualTag extends ComponentTag {

	private static final long serialVersionUID = -9166312322277142860L;
	
	protected String uid;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new PaginaActual(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		((PaginaActual) component).setUid(this.uid);
	}

	
	
	
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}


}
