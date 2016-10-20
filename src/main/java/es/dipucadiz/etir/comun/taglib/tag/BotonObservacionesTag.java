package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.ComponentTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.BotonObservaciones;

public class BotonObservacionesTag extends ComponentTag {
	private static final long serialVersionUID = 6270559193603553114L;

	protected String tabla;
	protected String campos;
	protected String id;
	protected Long coObservacionesGrupo;
	protected boolean isIcono;
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new BotonObservaciones(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		((BotonObservaciones) component).setTabla(tabla);
		((BotonObservaciones) component).setCampos(campos);
		((BotonObservaciones) component).setId(id);
		((BotonObservaciones) component).setCoObservacionesGrupo(coObservacionesGrupo);
		((BotonObservaciones) component).setIcono(isIcono);
	}


	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getCampos() {
		return campos;
	}

	public void setCampos(String campos) {
		this.campos = campos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCoObservacionesGrupo() {
		return coObservacionesGrupo;
	}

	public void setCoObservacionesGrupo(Long coObservacionesGrupo) {
		this.coObservacionesGrupo = coObservacionesGrupo;
	}

	public boolean isIcono() {
		return isIcono;
	}

	public void setIcono(boolean isIcono) {
		this.isIcono = isIcono;
	}

	
}
