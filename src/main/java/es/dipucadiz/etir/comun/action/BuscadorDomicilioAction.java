package es.dipucadiz.etir.comun.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.DomicilioBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.bo.ProvinciaBO;
import es.dipucadiz.etir.comun.bo.UnidadUrbanaBO;
import es.dipucadiz.etir.comun.constants.XMLHeaders;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.utilidades.RespuestaXML;

public class BuscadorDomicilioAction extends AbstractGadirBaseAction {
	private static final long serialVersionUID = -9128760150985476178L;
	
	private Boolean iniciando;
	
	private Boolean resetear;
	
	//busqueda 1
	private String coProvinciaSel;
	private String coMunicipioSel;
	private String coSiglaSel;
	private String calleSel;
	
	//busqueda 2
	private String numeroSel;
	private String letraSel;
	private String bloqueSel;
	private String escaleraSel;
	private String plantaSel;
	private String puertaSel;
	private String kmSel;
	private String coCalleSel;
	
	//busqueda 3
	private String coUniUrbSel;

	private ProvinciaBO provinciaBO;
	private MunicipioBO municipioBO;
	private CalleBO calleBO;
	private UnidadUrbanaBO unidadUrbanaBO;
	private DomicilioBO domicilioBO;
	private ClienteBO clienteBO;

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
		
		String coProvinciaSelBueno = "";
		String coMunicipioSelBueno = "";
		String coSiglaSelBueno = "";
		String calleSelBueno = "";
		
		if(null != this.getIniciando() && this.getIniciando())
		{
			sesion.setAttribute("coProvinciaSel", coProvinciaSel);
			sesion.setAttribute("coMunicipioSel", coMunicipioSel);
			sesion.setAttribute("coSiglaSel", coSiglaSel);
			sesion.setAttribute("calleSel", calleSel);
		}
		else if(null != this.getResetear() && this.getResetear())
		{
			sesion.setAttribute("coProvinciaSel", null);
			sesion.setAttribute("coMunicipioSel", null);
			sesion.setAttribute("coSiglaSel", null);
			sesion.setAttribute("calleSel", null);
		}
		else
		{
			if (!Utilidades.isEmpty((String)sesion.getAttribute("coProvinciaSel")) ||
					!Utilidades.isEmpty((String)sesion.getAttribute("coMunicipioSel")) ||
					!Utilidades.isEmpty((String)sesion.getAttribute("coSiglaSel")) ||
					!Utilidades.isEmpty((String)sesion.getAttribute("calleSel"))){
				coProvinciaSelBueno = (String)sesion.getAttribute("coProvinciaSel");
				coMunicipioSelBueno = (String)sesion.getAttribute("coMunicipioSel");
				coSiglaSelBueno = (String)sesion.getAttribute("coSiglaSel");
				calleSelBueno = (String)sesion.getAttribute("calleSel");
				tengoDatos=true;
			}else{
				sesion.setAttribute("coProvinciaSel", null);
				sesion.setAttribute("coMunicipioSel", null);
				sesion.setAttribute("coSiglaSel", null);
				sesion.setAttribute("calleSel", null);
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
				limit = 15;
			}
			
//			if(null != start && null != limit)
//			{
//				pagina = new Integer(start) + limit;
//			}
				
			
			Integer paginas = calleBO.countByCriteria(getCriterioCalle(coProvinciaSelBueno, coMunicipioSelBueno, coSiglaSelBueno, calleSelBueno, false));
			List<CalleDTO> listaCalleDTO = calleBO.findByCriteria(getCriterioCalle(coProvinciaSelBueno, coMunicipioSelBueno, coSiglaSelBueno, calleSelBueno, true), start, limit);

			xml = listaCallesToXml(listaCalleDTO, "", "calles", getRequest(), paginas);
		}
		
		RespuestaXML.generaRespuestaXML(xml, ServletActionContext.getResponse());
	}
	
	public void cargarUniUrbAjax() throws IOException, GadirServiceException
	{
		String xml = "";
		Integer start, limit;
//		Integer pagina = 0;
		
		boolean tengoDatos=false;
		
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession sesion = getRequest().getSession(true);
		String numeroSelBueno = "";
		String letraSelBueno = "";
		String bloqueSelBueno = "";
		String escaleraSelBueno = "";
		String plantaSelBueno = "";
		String puertaSelBueno = "";
		String kmSelBueno = "";
		String coCalleSelBueno = "";
		
		if(null != this.getIniciando() && this.getIniciando())
		{
			sesion.setAttribute("numeroSel", numeroSel);
			sesion.setAttribute("letraSel", letraSel);
			sesion.setAttribute("bloqueSel", bloqueSel);
			sesion.setAttribute("escaleraSel", escaleraSel);
			sesion.setAttribute("plantaSel", plantaSel);
			sesion.setAttribute("puertaSel", puertaSel);
			sesion.setAttribute("kmSel", kmSel);
			sesion.setAttribute("coCalleSel", coCalleSel);
		}
		else if(null != this.getResetear() && this.getResetear())
		{
			sesion.setAttribute("numeroSel", null);
			sesion.setAttribute("letraSel", null);
			sesion.setAttribute("bloqueSel", null);
			sesion.setAttribute("escaleraSel", null);
			sesion.setAttribute("plantaSel", null);
			sesion.setAttribute("puertaSel", null);
			sesion.setAttribute("kmSel", null);
			sesion.setAttribute("coCalleSel", null);
		}
		else
		{
			if (!Utilidades.isEmpty((String)sesion.getAttribute("numeroSel")) ||
					!Utilidades.isEmpty((String)sesion.getAttribute("letraSel")) ||
					!Utilidades.isEmpty((String)sesion.getAttribute("bloqueSel")) ||
					!Utilidades.isEmpty((String)sesion.getAttribute("escaleraSel")) ||
					!Utilidades.isEmpty((String)sesion.getAttribute("plantaSel")) ||
					!Utilidades.isEmpty((String)sesion.getAttribute("puertaSel")) ||
					!Utilidades.isEmpty((String)sesion.getAttribute("kmSel")) ||
					!Utilidades.isEmpty((String)sesion.getAttribute("coCalleSel"))){
				
				numeroSelBueno = (String)sesion.getAttribute("numeroSel");
				letraSelBueno = (String)sesion.getAttribute("letraSel");
				bloqueSelBueno = (String)sesion.getAttribute("bloqueSel");
				escaleraSelBueno = (String)sesion.getAttribute("escaleraSel");
				plantaSelBueno = (String)sesion.getAttribute("plantaSel");
				puertaSelBueno = (String)sesion.getAttribute("puertaSel");
				kmSelBueno = (String)sesion.getAttribute("kmSel");
				coCalleSelBueno = (String)sesion.getAttribute("coCalleSel");
				tengoDatos=true;
			}else{
				sesion.setAttribute("numeroSel", null);
				sesion.setAttribute("letraSel", null);
				sesion.setAttribute("bloqueSel", null);
				sesion.setAttribute("escaleraSel", null);
				sesion.setAttribute("plantaSel", null);
				sesion.setAttribute("puertaSel", null);
				sesion.setAttribute("kmSel", null);
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
				
			
			
			Integer paginas = unidadUrbanaBO.countByCriteria(getCriterioUniUrb(coCalleSelBueno, numeroSelBueno, letraSelBueno, bloqueSelBueno, escaleraSelBueno, plantaSelBueno, puertaSelBueno, kmSelBueno));
			List<UnidadUrbanaDTO> listaUniUrbDTO = unidadUrbanaBO.findByCriteria(getCriterioUniUrb(coCalleSelBueno, numeroSelBueno, letraSelBueno, bloqueSelBueno, escaleraSelBueno, plantaSelBueno, puertaSelBueno, kmSelBueno), start, limit);

			xml = listaUniUrbToXml(listaUniUrbDTO, "", "uniUrb", getRequest(), paginas);
		}
		
		RespuestaXML.generaRespuestaXML(xml, ServletActionContext.getResponse());
	}
	
	public void cargarClientesAjax() throws IOException, GadirServiceException
	{
		String xml = "";
		Integer start, limit;
//		Integer pagina = 0;
		
		boolean tengoDatos=false;
		
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession sesion = getRequest().getSession(true);
		String coUniUrbSelBueno = "";
		
		if(null != this.getIniciando() && this.getIniciando())
		{
			sesion.setAttribute("coUniUrbSel", coUniUrbSel);
		}
		else if(null != this.getResetear() && this.getResetear())
		{
			sesion.setAttribute("coUniUrbSel", null);
		}
		else
		{
			if (!Utilidades.isEmpty((String)sesion.getAttribute("coUniUrbSel"))){
				
				coUniUrbSelBueno = (String)sesion.getAttribute("coUniUrbSel");
				tengoDatos=true;
			}else{
				sesion.setAttribute("coUniUrbSel", null);
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
				limit = 12;
			}
			
//			if(null != start && null != limit)
//			{
//				pagina = new Integer(start) + limit;
//			}
				
			
			
			Integer paginas = domicilioBO.countByCriteria(getCriterioCliente(coUniUrbSelBueno));
			List<DomicilioDTO> listaDomiciliosDTO = domicilioBO.findByCriteria(getCriterioCliente(coUniUrbSelBueno), start, limit);

			xml = listaDomiciliosToXml(listaDomiciliosDTO, "", "cliente", getRequest(), paginas);
		}
		
		RespuestaXML.generaRespuestaXML(xml, ServletActionContext.getResponse());
	}
	
	public void cargarMunicipiosAjax() throws IOException, GadirServiceException
	{	
		String idProvincia = ServletActionContext.getRequest().getParameter("idProvincia");
		
		List<MunicipioDTO> listaMunicipios = new ArrayList<MunicipioDTO>();
		
		if(Utilidades.isEmpty(idProvincia)){
			listaMunicipios = new ArrayList<MunicipioDTO>();//ControlTerritorial.getMunicipiosUsuario();
		}
		else{
			listaMunicipios = this.getMunicipioBO().findMunicipiosByProvincia(idProvincia);
		}

		
		String xml = toXmlMunicipio(listaMunicipios, "", "municipio", getRequest(), listaMunicipios.size());
		
		RespuestaXML.generaRespuestaXML(xml, ServletActionContext.getResponse());
	}
	
	public void cargarSiglasAjax() throws IOException
	{
		List<KeyValue> listaSiglas = TablaGt.getListaCodigoDescripcion(TablaGt.TABLA_TIPO_VIA_PUBLICA);
		String result = toXmlSiglas(listaSiglas, "", "sigla", getRequest(), listaSiglas.size());

		RespuestaXML.generaRespuestaXML(result, ServletActionContext.getResponse());
	}
	
	public void cargarProvinciasAjax() throws IOException, GadirServiceException
	{
		
		// se cargarán todas las provincias menos las que tengan co_provincia igual a AY y **
		 	DetachedCriteria dcPronvicias=DetachedCriteria.forClass(ProvinciaDTO.class);
 	 dcPronvicias.add(Restrictions.not(Restrictions.like("coProvincia", "**")));
 	dcPronvicias.add(Restrictions.not(Restrictions.like("coProvincia", "AY")));
//		 dcPronvicias.add(Restrictions.eq("nombre", ""));		
		 List<ProvinciaDTO> listaProvincias = this.getProvinciaBO().findByCriteria(dcPronvicias);
		
		// List<ProvinciaDTO> listaProvincias = this.getProvinciaBO().findAll();
		String xml = toXmlProvincias(listaProvincias, "", "provincia", getRequest(), listaProvincias.size());
		RespuestaXML.generaRespuestaXML(xml, ServletActionContext.getResponse());
	}
	

	private DetachedCriteria getCriterioCalle(String coProvinciaSelSesion, String coMunicipioSelSesion, String coSiglaSelSesion, String calleSelSesion, boolean orden){
		DetachedCriteria criterio = DetachedCriteria.forClass(CalleDTO.class);

		if (!Utilidades.isEmpty(coProvinciaSelSesion)){
			criterio.add(Restrictions.eq("municipioDTO.id.coProvincia", coProvinciaSelSesion));
		}
		if (!Utilidades.isEmpty(coMunicipioSelSesion)){
			criterio.add(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipioSelSesion));
		}
		if (!Utilidades.isEmpty(coSiglaSelSesion)){
			criterio.add(Restrictions.eq("sigla", coSiglaSelSesion));
		}
		if (!Utilidades.isEmpty(calleSelSesion)){
			criterio.add(Restrictions.like("nombreCalle", calleSelSesion, MatchMode.ANYWHERE).ignoreCase());
		}

		criterio.createAlias("calleUbicacionDTO", "u", DetachedCriteria.LEFT_JOIN);

		if(orden){
			criterio.addOrder(Order.asc("nombreCalle"));
		}

		return criterio;
	}
	
	private DetachedCriteria getCriterioUniUrb(String coCalle, String numero, String letra, String bloque, String escalera, String planta, String puerta, String km){
		DetachedCriteria criterio = DetachedCriteria.forClass(UnidadUrbanaDTO.class);

		if (!Utilidades.isEmpty(coCalle)){
			criterio.add(Restrictions.eq("calleDTO.coCalle", Long.valueOf(coCalle)));
		}
		if (!Utilidades.isEmpty(numero)){
			criterio.add(Restrictions.eq("numero", new Integer(numero)));
		}
		if (!Utilidades.isEmpty(letra)){
			criterio.add(Restrictions.eq("letra", letra).ignoreCase());
		}
		if (!Utilidades.isEmpty(bloque)){
			criterio.add(Restrictions.eq("bloque", bloque).ignoreCase());
		}
		if (!Utilidades.isEmpty(escalera)){
			criterio.add(Restrictions.eq("escalera", escalera).ignoreCase());
		}
		if (!Utilidades.isEmpty(planta)){
			criterio.add(Restrictions.eq("planta", planta).ignoreCase());
		}
		if (!Utilidades.isEmpty(puerta)){
			criterio.add(Restrictions.eq("puerta", puerta).ignoreCase());
		}
		if (!Utilidades.isEmpty(km)){
			criterio.add(Restrictions.eq("km", new BigDecimal(km)));
		}
		
		
		return criterio;
	}
	
	private DetachedCriteria getCriterioCliente(String coUniUrb){
		DetachedCriteria criterio = DetachedCriteria.forClass(DomicilioDTO.class);

		criterio.createAlias("unidadUrbanaDTO", "u");
		criterio.createAlias("clienteDTO", "c");
		
		criterio.add(Restrictions.eq("u.coUnidadUrbana", Long.valueOf(coUniUrb)));
		
		return criterio;
	}
	
	protected String toXmlMunicipio(List<MunicipioDTO> listaToXML,
			String tagNombreTabla, String tagItem, HttpServletRequest request,
			Integer totalResultados)
	{
		StringBuilder sb = new StringBuilder();

		if (null == totalResultados)
			totalResultados = listaToXML.size();

		sb.append(XMLHeaders.XML_UTF8);
		if (null == tagNombreTabla || tagNombreTabla.length() <= 0)
			tagNombreTabla = "datos";

		sb.append("<" + tagNombreTabla + ">");
		sb.append("<total>" + totalResultados + "</total>");

		for (MunicipioDTO municipio : listaToXML) {
			sb.append("<" + tagItem + ">");
			sb.append("<codigo>").append((null != municipio.getId().getCoMunicipio()) ? municipio.getId().getCoMunicipio() : "").append("</codigo>");
			sb.append("<nombre>").append("<![CDATA[").append(
					(null != municipio.getNombre()) ? municipio.getNombre()
							: "").append("]]>").append("</nombre>");
			sb.append("</" + tagItem + ">");
		}

		sb.append("</" + tagNombreTabla + ">");

		return sb.toString();
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

	protected String toXmlProvincias(List<ProvinciaDTO> listaToXML,
			String tagNombreTabla, String tagItem, HttpServletRequest request,
			Integer totalResultados)
	{
		StringBuilder sb = new StringBuilder();

		if (null == totalResultados)
			totalResultados = listaToXML.size();

		sb.append(XMLHeaders.XML_UTF8);
		if (null == tagNombreTabla || tagNombreTabla.length() <= 0)
			tagNombreTabla = "datos";

		sb.append("<" + tagNombreTabla + ">");
		sb.append("<total>" + totalResultados + "</total>");

		for (ProvinciaDTO valor : listaToXML) {
			sb.append("<" + tagItem + ">");
			sb.append("<codigo>").append((null != valor.getCoProvincia()) ? valor.getCoProvincia() : "").append("</codigo>");
			sb.append("<nombre>").append("<![CDATA[").append(
					(null != valor.getCoProvincia()) ? valor.getNombre()
							: "").append("]]>").append("</nombre>");
			sb.append("</" + tagItem + ">");
		}

		sb.append("</" + tagNombreTabla + ">");

		return sb.toString();
	}

	protected String listaDomiciliosToXml(List<DomicilioDTO> listaDomiciliosDTO, String tagNombreTabla, String tagItem, HttpServletRequest request, Integer totalResultados) {
		StringBuilder sb;
		InputStream stream;

		sb = new StringBuilder();
		stream = null;

		if (null == totalResultados)
			totalResultados = listaDomiciliosDTO.size();

		sb.append(XMLHeaders.XML_UTF8);
		if (null == tagNombreTabla || tagNombreTabla.length() <= 0)
			tagNombreTabla = "datos";

		sb.append("<" + tagNombreTabla + ">");
		sb.append("<total>" + totalResultados + "</total>");

		for (DomicilioDTO domicilioDTO : listaDomiciliosDTO) {
			sb.append("<" + tagItem + ">");
			sb.append("<coDomicilio>").append((null != domicilioDTO.getCoDomicilio()) ? domicilioDTO.getCoDomicilio() : "").append("</coDomicilio>");
			sb.append("<coCliente>").append((null != domicilioDTO.getClienteDTO()) ? domicilioDTO.getClienteDTO().getCoCliente() : "").append("</coCliente>");
			sb.append("<nif>").append("<![CDATA[").append((null != domicilioDTO.getClienteDTO()) ? StringEscapeUtils.escapeXml(domicilioDTO.getClienteDTO().getIdentificador()) : "").append("]]>").append("</nif>");
			sb.append("<nombre>").append("<![CDATA[").append((null != domicilioDTO.getClienteDTO()) ? StringEscapeUtils.escapeXml(domicilioDTO.getClienteDTO().getRazonSocial()) : "").append("]]>").append("</nombre>");
			sb.append("</" + tagItem + ">");
		}

		sb.append("</" + tagNombreTabla + ">");

		stream = new ByteArrayInputStream(sb.toString().getBytes());

		return InputStreamToString(stream);
	}
	
	protected String listaUniUrbToXml(List<UnidadUrbanaDTO> listaUniUrbDTO, String tagNombreTabla, String tagItem, HttpServletRequest request, Integer totalResultados) {
		StringBuilder sb;
		InputStream stream;

		sb = new StringBuilder();
		stream = null;

		if (null == totalResultados)
			totalResultados = listaUniUrbDTO.size();

		sb.append(XMLHeaders.XML_UTF8);
		if (null == tagNombreTabla || tagNombreTabla.length() <= 0)
			tagNombreTabla = "datos";

		sb.append("<" + tagNombreTabla + ">");
		sb.append("<total>" + totalResultados + "</total>");

		for (UnidadUrbanaDTO unidadUrbanaDTO : listaUniUrbDTO) {
			sb.append("<" + tagItem + ">");
			sb.append("<coUnidadUrbana>").append((null != unidadUrbanaDTO.getCoUnidadUrbana()) ? unidadUrbanaDTO.getCoUnidadUrbana() : "").append("</coUnidadUrbana>");
			sb.append("<numero>").append("<![CDATA[").append((null != unidadUrbanaDTO.getNumero()) ? unidadUrbanaDTO.getNumero() : "").append("]]>").append("</numero>");
			sb.append("<letra>").append("<![CDATA[").append((null != unidadUrbanaDTO.getLetra()) ? StringEscapeUtils.escapeXml(unidadUrbanaDTO.getLetra()) : "").append("]]>").append("</letra>");
			sb.append("<bloque>").append("<![CDATA[").append((null != unidadUrbanaDTO.getBloque()) ? StringEscapeUtils.escapeXml(unidadUrbanaDTO.getBloque()) : "").append("]]>").append("</bloque>");
			sb.append("<escalera>").append("<![CDATA[").append((null != unidadUrbanaDTO.getEscalera()) ? StringEscapeUtils.escapeXml(unidadUrbanaDTO.getEscalera()) : "").append("]]>").append("</escalera>");
			sb.append("<planta>").append("<![CDATA[").append((null != unidadUrbanaDTO.getPlanta()) ? StringEscapeUtils.escapeXml(unidadUrbanaDTO.getPlanta()) : "").append("]]>").append("</planta>");
			sb.append("<puerta>").append("<![CDATA[").append((null != unidadUrbanaDTO.getPuerta()) ? StringEscapeUtils.escapeXml(unidadUrbanaDTO.getPuerta()) : "").append("]]>").append("</puerta>");
			sb.append("<km>").append("<![CDATA[").append((null != unidadUrbanaDTO.getKm()) ? unidadUrbanaDTO.getKm() : "").append("]]>").append("</km>");
			
			sb.append("</" + tagItem + ">");
		}

		sb.append("</" + tagNombreTabla + ">");

		stream = new ByteArrayInputStream(sb.toString().getBytes());

		return InputStreamToString(stream);
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
			sb.append("<sigla>").append("<![CDATA[").append((null != calleDTO.getSigla()) ? StringEscapeUtils.escapeXml(calleDTO.getSigla()) : "").append("]]>").append("</sigla>");
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

	public String getCoProvinciaSel() {
		return coProvinciaSel;
	}

	public void setCoProvinciaSel(String coProvinciaSel) {
		this.coProvinciaSel = coProvinciaSel;
	}

	public String getCoMunicipioSel() {
		return coMunicipioSel;
	}

	public void setCoMunicipioSel(String coMunicipioSel) {
		this.coMunicipioSel = coMunicipioSel;
	}

	public String getCoSiglaSel() {
		return coSiglaSel;
	}

	public void setCoSiglaSel(String coSiglaSel) {
		this.coSiglaSel = coSiglaSel;
	}

	public String getCalleSel() {
		return calleSel;
	}

	public void setCalleSel(String calleSel) {
		this.calleSel = calleSel;
	}

	public ProvinciaBO getProvinciaBO() {
		return provinciaBO;
	}

	public void setProvinciaBO(ProvinciaBO provinciaBO) {
		this.provinciaBO = provinciaBO;
	}

	public MunicipioBO getMunicipioBO() {
		return municipioBO;
	}

	public void setMunicipioBO(MunicipioBO municipioBO) {
		this.municipioBO = municipioBO;
	}

	public CalleBO getCalleBO() {
		return calleBO;
	}

	public void setCalleBO(CalleBO calleBO) {
		this.calleBO = calleBO;
	}

	public UnidadUrbanaBO getUnidadUrbanaBO() {
		return unidadUrbanaBO;
	}

	public void setUnidadUrbanaBO(UnidadUrbanaBO unidadUrbanaBO) {
		this.unidadUrbanaBO = unidadUrbanaBO;
	}

	public DomicilioBO getDomicilioBO() {
		return domicilioBO;
	}

	public void setDomicilioBO(DomicilioBO domicilioBO) {
		this.domicilioBO = domicilioBO;
	}

	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public String getNumeroSel() {
		return numeroSel;
	}

	public void setNumeroSel(String numeroSel) {
		this.numeroSel = numeroSel;
	}

	public String getLetraSel() {
		return letraSel;
	}

	public void setLetraSel(String letraSel) {
		this.letraSel = letraSel;
	}

	public String getBloqueSel() {
		return bloqueSel;
	}

	public void setBloqueSel(String bloqueSel) {
		this.bloqueSel = bloqueSel;
	}

	public String getEscaleraSel() {
		return escaleraSel;
	}

	public void setEscaleraSel(String escaleraSel) {
		this.escaleraSel = escaleraSel;
	}

	public String getPlantaSel() {
		return plantaSel;
	}

	public void setPlantaSel(String plantaSel) {
		this.plantaSel = plantaSel;
	}

	public String getPuertaSel() {
		return puertaSel;
	}

	public void setPuertaSel(String puertaSel) {
		this.puertaSel = puertaSel;
	}

	public String getKmSel() {
		return kmSel;
	}

	public void setKmSel(String kmSel) {
		this.kmSel = kmSel;
	}

	public String getCoCalleSel() {
		return coCalleSel;
	}

	public void setCoCalleSel(String coCalleSel) {
		this.coCalleSel = coCalleSel;
	}

	public String getCoUniUrbSel() {
		return coUniUrbSel;
	}

	public void setCoUniUrbSel(String coUniUrbSel) {
		this.coUniUrbSel = coUniUrbSel;
	}

	
}
