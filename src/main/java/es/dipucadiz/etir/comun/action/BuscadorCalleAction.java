package es.dipucadiz.etir.comun.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.xwork.StringEscapeUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CalleBO;
import es.dipucadiz.etir.comun.bo.CalleUbicacionBO;
import es.dipucadiz.etir.comun.bo.MunicipioDatosBO;
import es.dipucadiz.etir.comun.constants.XMLHeaders;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDatosDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDatosDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.utilidades.RespuestaXML;

public class BuscadorCalleAction extends AbstractGadirBaseAction {
	private static final long serialVersionUID = -9128760150985476178L;
	
	private Boolean iniciando;
	
	private boolean callejeroMunicipal=false;
	
	private Boolean resetear;
	
	private String filtroSigla;
	private String filtroCalle;
	
	String sesionFiltroSigla;
	String sesionFiltroCalle;

	private CalleBO calleBO;
	private CalleUbicacionBO calleUbicacionBO;
	private MunicipioDatosBO municipioDatosBO;
	
	private String coMunicipio;
	private String coProvincia;

	public String execute() throws GadirServiceException, IOException {

		return null;
	}
	
	public void cargarCallesAjax() throws IOException, GadirServiceException
	{
		String xml = "";
		Integer start, limit;
//		Integer pagina = 0;
		
		boolean tengoDatos=false;
		
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession sesion = getRequest().getSession(true);
		
		String filtroSiglaBueno = "";
		String filtroCalleBueno = "";
		String coMunicipioBueno = "";
		String coProvinciaBueno = "";
		
		if(null != this.getIniciando() && this.getIniciando())
		{
			sesion.setAttribute("filtroSigla", filtroSigla);
			sesion.setAttribute("filtroCalle", filtroCalle);
			sesion.setAttribute("coMunicipio", coMunicipio);
			sesion.setAttribute("coProvincia", coProvincia);
		}
		else if(null != this.getResetear() && this.getResetear())
		{
			sesion.setAttribute("filtroSigla", null);
			sesion.setAttribute("filtroCalle", null);
		}
		else
		{
			if (!Utilidades.isEmpty((String)sesion.getAttribute("filtroSigla")) || !Utilidades.isEmpty((String)sesion.getAttribute("filtroCalle"))){
				filtroSiglaBueno = (String)sesion.getAttribute("filtroSigla");
				filtroCalleBueno = (String)sesion.getAttribute("filtroCalle");
				coMunicipioBueno = (String)sesion.getAttribute("coMunicipio");
				coProvinciaBueno = (String)sesion.getAttribute("coProvincia");
				tengoDatos=true;
			}else{
				sesion.setAttribute("filtroSigla", null);
				sesion.setAttribute("filtroCalle", null);
			}
			
		}
		
		if (tengoDatos){
			try{
				start = new Integer(request.getParameter("start"));
				limit = new Integer(request.getParameter("limit"));
			}
			catch(Exception e)
			{
				start = 0;
				limit = 14;
			}
			
//			if(null != start && null != limit)
//			{
//				pagina = new Integer(start) + limit;
//			}
				
			try{
				MunicipioDatosDTO municipioDatosDTO=municipioDatosBO.findById(new MunicipioDatosDTOId(coProvinciaBueno, coMunicipioBueno));
				if (municipioDatosDTO!=null && municipioDatosDTO.getBoCallejeroMunicipal()!=null && municipioDatosDTO.getBoCallejeroMunicipal()){
					callejeroMunicipal=true;
				}
			}catch(Exception e){
				
			}
			
			Integer paginas = calleBO.countByCriteria(getCriterioCalle(coProvinciaBueno, coMunicipioBueno, filtroSiglaBueno, filtroCalleBueno, false));
			List<CalleDTO> listaCalleDTO = calleBO.findByCriteria(getCriterioCalle(coProvinciaBueno, coMunicipioBueno, filtroSiglaBueno, filtroCalleBueno, true), start, limit);

			xml = listaCallesToXml(listaCalleDTO, "", "calles", getRequest(), paginas);
		}
		
		RespuestaXML.generaRespuestaXML(xml, ServletActionContext.getResponse());
	}
	
	public void cargarSiglasAjax() throws IOException
	{
		List<KeyValue> listaSiglas = TablaGt.getListaCodigoDescripcion(TablaGt.TABLA_TIPO_VIA_PUBLICA);
		String result = toXmlSiglas(listaSiglas, "", "sigla", getRequest(), listaSiglas.size());

		RespuestaXML.generaRespuestaXML(result, ServletActionContext.getResponse());
	}
	

	private DetachedCriteria getCriterioCalle(String coProvincia, String coMunicipio, String coSigla, String calle, boolean orden){
		DetachedCriteria criterio = DetachedCriteria.forClass(CalleDTO.class);

		if (!Utilidades.isEmpty(coProvincia)){
			criterio.add(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia));
		}
		if (!Utilidades.isEmpty(coMunicipio)){
			criterio.add(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipio));
		}
		if (!Utilidades.isEmpty(coSigla)){
			criterio.add(Restrictions.eq("sigla", coSigla));
		}
		if (!Utilidades.isEmpty(calle)){
			criterio.add(Restrictions.like("nombreCalle", calle, MatchMode.ANYWHERE).ignoreCase());
		}
		
		if (callejeroMunicipal){
			criterio.add(Restrictions.ne("coMunicipal", new Integer(0)));
			criterio.add(Restrictions.isNotNull("coMunicipal"));
		}

		criterio.createAlias("calleUbicacionDTO", "u", DetachedCriteria.LEFT_JOIN);

		if(orden){
			criterio.addOrder(Order.asc("nombreCalle"));
		}

		return criterio;
	}

	protected String toXmlSiglas(List<KeyValue> listaToXML,
			String tagNombreTabla, String tagItem, HttpServletRequest request,
			Integer totalResultados) throws UnsupportedEncodingException
	{
		StringBuilder sb = new StringBuilder();

		if (null == totalResultados)
			totalResultados = listaToXML.size();

		sb.append(XMLHeaders.XML_UTF8);
		if (null == tagNombreTabla || tagNombreTabla.length() <= 0)
			tagNombreTabla = "datos";

		sb.append("<" + tagNombreTabla + ">");
		sb.append("<total>" + totalResultados + "</total>");

		for (KeyValue valor : listaToXML) {
			sb.append("<" + tagItem + ">");
			sb.append("<codigo>").append((null != valor.getKey()) ? valor.getKey() : "").append("</codigo>");
			sb.append("<nombre>").append("<![CDATA[").append(
					(null != valor.getValue()) ? valor.getValue()
							: "").append("]]>").append("</nombre>");
			sb.append("</" + tagItem + ">");
		}

		sb.append("</" + tagNombreTabla + ">");

		return sb.toString();
	}

	protected String listaCallesToXml(List<CalleDTO> listaCalleDTO, String tagNombreTabla, String tagItem, HttpServletRequest request, Integer totalResultados) {
		StringBuilder sb;
		InputStream stream;

		sb = new StringBuilder();
		stream = null;

		if (null == totalResultados)
			totalResultados = listaCalleDTO.size();

		sb.append(XMLHeaders.XML_UTF8);
		if (null == tagNombreTabla || tagNombreTabla.length() <= 0)
			tagNombreTabla = "datos";

		sb.append("<" + tagNombreTabla + ">");
		sb.append("<total>" + totalResultados + "</total>");

		for (CalleDTO calleDTO : listaCalleDTO) {
			sb.append("<" + tagItem + ">");
			sb.append("<coCalle>").append((null != calleDTO.getCoCalle()) ? calleDTO.getCoCalle() : "").append("</coCalle>");
			sb.append("<coSigla>").append((null != calleDTO.getSigla()) ? calleDTO.getSigla() : "").append("</coSigla>");
			sb.append("<coUbicacion>").append((null != calleDTO.getCalleUbicacionDTO()) ? calleDTO.getCalleUbicacionDTO().getCoCalleUbicacion() : "").append("</coUbicacion>");
			sb.append("<sigla>").append("<![CDATA[").append((null != calleDTO.getSigla()) ? StringEscapeUtils.escapeXml(TablaGt.getCodigoDescripcion(TablaGt.TABLA_TIPO_VIA_PUBLICA, calleDTO.getSigla()).getCodigoDescripcion()) : "").append("]]>").append("</sigla>");
			sb.append("<calle>").append("<![CDATA[").append((null != calleDTO.getNombreCalle()) ? StringEscapeUtils.escapeXml(calleDTO.getNombreCalle()) : "").append("]]>").append("</calle>");
			sb.append("<ubicacion>").append("<![CDATA[").append((null != calleDTO.getCalleUbicacionDTO()) ? StringEscapeUtils.escapeXml(calleDTO.getCalleUbicacionDTO().getUbicacion()) : "").append("]]>").append("</ubicacion>");
			sb.append("</" + tagItem + ">");
		}

		sb.append("</" + tagNombreTabla + ">");

		stream = new ByteArrayInputStream(sb.toString().getBytes());

		return InputStreamToString(stream);
	}

	private String InputStreamToString(InputStream is) {
		String result = "";

		if (null != is) {
			try {
				char[] buffer = new char[is.available()];
				InputStreamReader isReader = new InputStreamReader(is);
				isReader.read(buffer);
				result = new String(buffer);
				is.close();
			} catch (IOException e) {
			}
		}
		return result;
	}


//	private void xmlToResponse(Writer writer, String result) {
//		try {
//			writer.write(result);
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//		}
//	}

	public Boolean getIniciando() {
		return iniciando;
	}

	public void setIniciando(Boolean iniciando) {
		this.iniciando = iniciando;
	}

	public Boolean getResetear() {
		return resetear;
	}

	public void setResetear(Boolean resetear) {
		this.resetear = resetear;
	}

	public String getFiltroSigla() {
		return filtroSigla;
	}

	public void setFiltroSigla(String filtroSigla) {
		this.filtroSigla = filtroSigla;
	}

	public String getFiltroCalle() {
		return filtroCalle;
	}

	public void setFiltroCalle(String filtroCalle) {
		this.filtroCalle = filtroCalle;
	}

	public String getSesionFiltroSigla() {
		return sesionFiltroSigla;
	}

	public void setSesionFiltroSigla(String sesionFiltroSigla) {
		this.sesionFiltroSigla = sesionFiltroSigla;
	}

	public String getSesionFiltroCalle() {
		return sesionFiltroCalle;
	}

	public void setSesionFiltroCalle(String sesionFiltroCalle) {
		this.sesionFiltroCalle = sesionFiltroCalle;
	}

	public CalleBO getCalleBO() {
		return calleBO;
	}

	public void setCalleBO(CalleBO calleBO) {
		this.calleBO = calleBO;
	}

	public CalleUbicacionBO getCalleUbicacionBO() {
		return calleUbicacionBO;
	}

	public void setCalleUbicacionBO(CalleUbicacionBO calleUbicacionBO) {
		this.calleUbicacionBO = calleUbicacionBO;
	}

	public String getCoMunicipio() {
		return coMunicipio;
	}

	public void setCoMunicipio(String coMunicipio) {
		this.coMunicipio = coMunicipio;
	}

	public String getCoProvincia() {
		return coProvincia;
	}

	public void setCoProvincia(String coProvincia) {
		this.coProvincia = coProvincia;
	}

	public boolean isCallejeroMunicipal() {
		return callejeroMunicipal;
	}

	public void setCallejeroMunicipal(boolean callejeroMunicipal) {
		this.callejeroMunicipal = callejeroMunicipal;
	}

	public MunicipioDatosBO getMunicipioDatosBO() {
		return municipioDatosBO;
	}

	public void setMunicipioDatosBO(MunicipioDatosBO municipioDatosBO) {
		this.municipioDatosBO = municipioDatosBO;
	}
	
	

	
}
