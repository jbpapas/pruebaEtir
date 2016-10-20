package es.dipucadiz.etir.comun.struts.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.ComponentTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.struts.taglib.component.Sample;

public class SampleTag extends ComponentTag {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -4962228590582610201L;

	private String nombre;

	/**
	 * Método que devuelve el atributo nombre.
	 * 
	 * @return nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que establece el atributo nombre.
	 * 
	 * @param nombre
	 *            El nombre.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new Sample(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		((Sample) component).setNombre(this.nombre);
	}
}
