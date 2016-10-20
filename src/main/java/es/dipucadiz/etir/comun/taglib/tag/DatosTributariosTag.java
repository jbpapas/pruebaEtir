package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.ComponentTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.DatosTributarios;

public class DatosTributariosTag extends ComponentTag {

	private static final long serialVersionUID = 247621426261202671L;
	protected String coModelo;
	protected String coVersion;
	protected String coDocumento;
	protected boolean muestraCodigoDocumento;
	protected String abierto;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new DatosTributarios(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		((DatosTributarios) component).setCoModelo(coModelo);
		((DatosTributarios) component).setCoVersion(coVersion);
		((DatosTributarios) component).setCoDocumento(coDocumento);
		((DatosTributarios) component).setMuestraCodigoDocumento(muestraCodigoDocumento);
		((DatosTributarios) component).setAbierto(abierto);
	}

	public String getCoModelo() {
		return coModelo;
	}

	public void setCoModelo(String coModelo) {
		this.coModelo = coModelo;
	}

	public String getCoVersion() {
		return coVersion;
	}

	public void setCoVersion(String coVersion) {
		this.coVersion = coVersion;
	}

	public String getCoDocumento() {
		return coDocumento;
	}

	public void setCoDocumento(String coDocumento) {
		this.coDocumento = coDocumento;
	}

	public boolean isMuestraCodigoDocumento() {
		return muestraCodigoDocumento;
	}

	public void setMuestraCodigoDocumento(boolean muestraCodigoDocumento) {
		this.muestraCodigoDocumento = muestraCodigoDocumento;
	}

	public String getAbierto() {
		return abierto;
	}

	public void setAbierto(String abierto) {
		this.abierto = abierto;
	}
	
}
