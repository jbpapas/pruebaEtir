package es.dipucadiz.etir.comun.taglib.component;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.GenericUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class BotonObservaciones extends GenericUIBean {

	private static final Log LOG = LogFactory.getLog(BotonObservaciones.class);

	protected String tabla;
	protected String campos;
	protected String id;
	protected Long coObservacionesGrupo;
	protected boolean isIcono;
	
	public BotonObservaciones(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		super(stack, req, res);
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		
		String idUnico = "observacionesId" + Base64.encodeBase64URLSafeString(id.getBytes());
		
		try {
			if (isIcono) {
//				writer.write("<input type=\"hidden\" name=\"" + idUnico + "tabla\" value=\"" + tabla + "\" />\n");
//				writer.write("<input type=\"hidden\" name=\"" + idUnico + "campos\" value=\"" + campos + "\" />\n");
//				writer.write("<input type=\"hidden\" name=\"" + idUnico + "id\" value=\"" + id + "\" />\n");
				writer.write("<a href=\"#\" onclick=\"return observaciones('"+tabla+"', '"+campos+"', '"+id+"', '"+coObservacionesGrupo+"', '', '"+idUnico+"')\"><img src=\"image/iconos/16x16/observaciones_" + (coObservacionesGrupo == null || coObservacionesGrupo == 0? "sin" : "con") + ".png\" class=\"observaciones\" id=\"" + idUnico + "\" style=\"display:inline-block\" /></a>\n");
			} else {
//				writer.write("<a href=\"#\" ><img src=\"image/iconos/16x16/observaciones_" + (coObservacionesGrupo == null || coObservacionesGrupo == 0? "sin" : "con") + ".png\" class=\"observaciones\" /></a>\n");
				writer.write("<button onclick=\"return observaciones('"+tabla+"', '"+campos+"', '"+id+"', '"+coObservacionesGrupo+"', '', '"+idUnico+"')\" style=\"width:150px\"><img src=\"image/iconos/16x16/observaciones_" + (coObservacionesGrupo == null || coObservacionesGrupo == 0? "sin" : "con") + ".png\" alt=\"Observaciones\" id=\"" + idUnico + "\" style=\"width:16px;height:16px;vertical-align:middle\" /> Observaciones</button>");
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}

		return result;
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
