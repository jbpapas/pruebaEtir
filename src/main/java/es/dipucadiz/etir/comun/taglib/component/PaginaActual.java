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

import es.dipucadiz.etir.comun.utilidades.DisplayTagUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

@StrutsTag(name = "paginaActual", description = "Página actual tag", tldTagClass = "es.dipucadiz.etir.comun.taglib.tag.PaginaActualTag")
public class PaginaActual extends GenericUIBean {

	private static final Log LOG = LogFactory.getLog(PaginaActual.class);
	
	protected String uid;
	
	public PaginaActual(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		if (Utilidades.isEmpty(uid)) {
			uid = "row";
		}
		String name = DisplayTagUtil.getVariablePagina(uid, request);
		Integer value = DisplayTagUtil.getValorPagina(uid, request);
		try {
			writer.write("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\" id=\"" + id + "\" />");
		} catch (IOException e) {
			LOG.info("Tag PaginaActualTag could not print.", e);
		}
        return result;
	}
	
	
	
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}



	
	

}
