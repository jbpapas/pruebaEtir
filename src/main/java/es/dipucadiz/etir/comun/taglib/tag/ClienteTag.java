package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.ComponentTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.Cliente;

public class ClienteTag extends ComponentTag {

	private static final long serialVersionUID = 8793850390059309517L;
	
	protected String coCliente;
	protected String coDomicilio;
	protected String abierto;
	protected String conCaja;
	protected String titulo;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Cliente(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		((Cliente) component).setCoCliente(coCliente);
		((Cliente) component).setCoDomicilio(coDomicilio);
		((Cliente) component).setAbierto(abierto);
		((Cliente) component).setConCaja(conCaja);
		((Cliente) component).setTitulo(titulo);
	}

	public String getCoCliente() {
		return coCliente;
	}

	public void setCoCliente(String coCliente) {
		this.coCliente = coCliente;
	}

	public String getCoDomicilio() {
		return coDomicilio;
	}

	public void setCoDomicilio(String coDomicilio) {
		this.coDomicilio = coDomicilio;
	}

	public String getAbierto() {
		return abierto;
	}

	public void setAbierto(String abierto) {
		this.abierto = abierto;
	}

	public String getConCaja() {
		return conCaja;
	}

	public void setConCaja(String conCaja) {
		this.conCaja = conCaja;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	

}
