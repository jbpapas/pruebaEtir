package es.dipucadiz.etir.comun.struts.taglib.component;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.GenericUIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "sample", description = "Sample tag", tldTagClass = "es.dipucadiz.etir.arq.web.taglib.struts.tag.SampleTag")
public class Sample extends GenericUIBean {

	/**
	 * Atributo que almacena el LOG de la clase.
	 */
	private static final Log LOG = LogFactory.getLog(Sample.class);
	
	private static final String TEMPLATE = "sample";

	private String nombre;
	
	public Sample(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		super(stack, req, res);
	}

	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		
		String actualValue = null;
		
		if (this.nombre.startsWith("%{") && this.nombre.endsWith("}")) {
			this.nombre = nombre.substring(2, nombre.length() - 1);
		}
		
		actualValue = (String) super.getStack().findValue(this.nombre, String.class);
		try {
            if (actualValue != null) {
                writer.write(actualValue);
            } else {
                writer.write("");
            }
        } catch (IOException e) {
            LOG.info("Could not print out value '" + nombre + "'", e);
        }

        return result;
	}

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
	
	

}
