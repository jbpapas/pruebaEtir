package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.ComponentTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.Parametro;

public class ParametroTag extends ComponentTag {

	private static final long serialVersionUID = -2995405986537206779L;
	
	protected String nombre;
	protected String valor;
	protected String id;
	protected String formulario;
	protected int elemento;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Parametro(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		((Parametro) component).setNombre(this.nombre);
		((Parametro) component).setValor(this.valor);
		((Parametro) component).setId(this.id);
		((Parametro) component).setFormulario(this.formulario);
		((Parametro) component).setElemento(this.elemento);
	}

	
	
	
	
	
	
	
	
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFormulario() {
		return formulario;
	}

	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}

	public int getElemento() {
		return elemento;
	}

	public void setElemento(int elemento) {
		this.elemento = elemento;
	}

	
	
	
	
	
	
	

}
