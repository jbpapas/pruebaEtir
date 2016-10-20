package es.dipucadiz.etir.comun.taglib.component;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.GenericUIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.utilidades.Utilidades;

@StrutsTag(name = "informe", description = "Informe tag", tldTagClass = "es.dipucadiz.etir.comun.taglib.tag.InformeTag")
public class Parametro extends GenericUIBean {

	/**
	 * Atributo que almacena el LOG de la clase.
	 */
	private static final Log LOG = LogFactory.getLog(Parametro.class);

	protected String nombre;
	protected String valor;
	protected String id;
	protected String formulario;
	protected int elemento;
	
	public Parametro(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		super(stack, req, res);
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);

		if (Utilidades.isEmpty(id)) {
			id = "impresion_" + nombre;
		}
		
		try {
			writer.write("<input type=\"hidden\" name=\"" + nombre + "\" value=\"" + valor + "\" id=\"" + id + "\" />" + '\n');
			if (!Utilidades.isEmpty(formulario)) {
				writer.write("<script type=\"text/javascript\">" + '\n');
				writer.write("$(document).ready(function() {" + '\n');
				writer.write("	$('#impresion').submit(function() {" + '\n');
				writer.write("		document.getElementById('" + id + "').value=document.forms['" + formulario + "'].elements[" + elemento + "].value;" + '\n');
				writer.write("		return true;" + '\n');
				writer.write("	});" + '\n');
				writer.write("});" + '\n');
				writer.write("</script>" + '\n');
			}
		} catch (IOException e) {
			LOG.info("Tag InformeTag could not print.", e);
		}
		
        return result;
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
