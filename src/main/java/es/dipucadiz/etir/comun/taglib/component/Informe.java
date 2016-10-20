package es.dipucadiz.etir.comun.taglib.component;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.GenericUIBean;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.util.UrlHelper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.utilidades.Utilidades;

@StrutsTag(name = "informe", description = "Informe tag", tldTagClass = "es.dipucadiz.etir.comun.taglib.tag.InformeTag")
public class Informe extends GenericUIBean {

	private static final Log LOG = LogFactory.getLog(Informe.class);
	
	protected String metodo;
	
	public Informe(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		if (Utilidades.isEmpty(metodo)) {
			metodo = "botonImprimir";
		}
		
		try {
			writer.write("<form id=\"impresion\" name=\"impresion\" action=\"" + renderFormUrl() + "\" method=\"post\">" + '\n');
			writer.write("<input type=\"hidden\" name=\"method:" + metodo + "\" />" + '\n');
			writer.write("<input type=\"hidden\" name=\"informeActuacion\" value=\"2\" id=\"informeActuacion\" />" + '\n');
			writer.write("<input type=\"hidden\" name=\"informeParametro\" value=\"\" id=\"informeParametro\" />" + '\n');
		} catch (IOException e) {
			LOG.info("Tag InformeTag could not print.", e);
		}
        return result;
	}

	@Override
	public boolean end(Writer writer, String body) {
		boolean result = super.end(writer, body);
		try {
			writer.write("</form>" + '\n');
		} catch (IOException e) {
			LOG.info("Tag InformeTag could not print.", e);
		}
        return result;
	}
	
	@SuppressWarnings("unchecked")
	private String renderFormUrl() {
		ActionInvocation ai = (ActionInvocation) this.getStack().getContext().get(ActionContext.ACTION_INVOCATION);
		String action = ai.getProxy().getActionName();
		String namespace = ai.getProxy().getNamespace();

        ActionMapping nameMapping = actionMapper.getMappingFromActionName(action);
        String actionName = nameMapping.getName();
        String actionMethod = nameMapping.getMethod();

		ActionMapping mapping = new ActionMapping(actionName, namespace, actionMethod, this.parameters);
		return UrlHelper.buildUrl(this.actionMapper.getUriFromActionMapping(mapping), this.request, this.response, null);
	}
	
	
	
	
	
	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}



	
	

}
