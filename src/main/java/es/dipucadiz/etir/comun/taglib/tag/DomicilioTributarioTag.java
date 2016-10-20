package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.ComponentTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.DomicilioTributario;

public class DomicilioTributarioTag extends ComponentTag {

	private static final long serialVersionUID = 247621426261202671L;
	protected String coDomicilio;
	protected String refCat;
	protected boolean muestraCliente;
	protected String abierto;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new DomicilioTributario(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		((DomicilioTributario) component).setCoDomicilio(coDomicilio);
		((DomicilioTributario) component).setRefCat(refCat);
		((DomicilioTributario) component).setMuestraCliente(muestraCliente);
		((DomicilioTributario) component).setAbierto(abierto);
	}


	public String getCoDomicilio() {
		return coDomicilio;
	}

	public void setCoDomicilio(String coDomicilio) {
		this.coDomicilio = coDomicilio;
	}

	public String getRefCat() {
		return refCat;
	}

	public void setRefCat(String refCat) {
		this.refCat = refCat;
	}

	public boolean isMuestraCliente() {
		return muestraCliente;
	}

	public void setMuestraCliente(boolean muestraCliente) {
		this.muestraCliente = muestraCliente;
	}

	public String getAbierto() {
		return abierto;
	}

	public void setAbierto(String abierto) {
		this.abierto = abierto;
	}

}
