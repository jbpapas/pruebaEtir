package es.dipucadiz.etir.comun.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.xwork.StringEscapeUtils;
import org.apache.struts2.ServletActionContext;

import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DomicilioUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class BuscadorClientesAction extends AbstractGadirBaseAction {
	private static final long serialVersionUID = -9128760150985476178L;
	
	private Boolean iniciando;
	private String filtroNombre;
	private String filtroNIF;
	//private String filtroAsociado;
	private String filtroTipoBusqueda;
	private String filtroProvincia;
	private String filtroMunicipio;
	private String filtroCalle;
	private Boolean resetear;

	String sesionFiltroNombre;
	String sesionFiltroNIF;
	String sesionFiltroTipoBusqueda;
	String sesionFiltroProvincia;
	String sesionFiltroMunicipio;
	String sesionFiltroCalle;
	
	private ClienteBO clienteBO;

	public String execute() throws GadirServiceException, IOException {
		InputStream is;
		String result;
//		List<ClienteVO> listaToXml;
		Integer start, limit;
//		Integer pagina = 0;

		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();

		is = null;
		result = null;
//		listaToXml = new ArrayList<ClienteVO>();

		HttpSession sesion = getRequest().getSession(true);

		sesionFiltroNombre = (String) sesion.getAttribute("filtroNombre");
		sesionFiltroNIF = (String) sesion.getAttribute("filtroNIF");
		sesionFiltroTipoBusqueda = (String) sesion.getAttribute("filtroTipoBusqueda");
		sesionFiltroProvincia = (String) sesion.getAttribute("filtroProvincia");
		sesionFiltroMunicipio = (String) sesion.getAttribute("filtroMunicipio");
		sesionFiltroCalle = (String) sesion.getAttribute("filtroCalle");
		//String sesionFiltroAsociado = (String) sesion.getAttribute("filtroAsociado");

		if (null != this.getIniciando() && this.getIniciando()) {
			sesion.setAttribute("filtroNombre", filtroNombre);
			sesion.setAttribute("filtroNIF", filtroNIF);
			sesion.setAttribute("filtroTipoBusqueda", filtroTipoBusqueda);
			sesion.setAttribute("filtroProvincia", filtroProvincia);
			sesion.setAttribute("filtroMunicipio", filtroMunicipio);
			sesion.setAttribute("filtroCalle", filtroCalle);
			//sesion.setAttribute("filtroAsociado", filtroAsociado);
		} else if (null != this.getResetear() && this.getResetear()) {
			sesion.setAttribute("filtroNombre", null);
			sesion.setAttribute("filtroNIF", null);
			sesion.setAttribute("filtroTipoBusqueda", null);
			sesion.setAttribute("filtroProvincia", null);
			sesion.setAttribute("filtroMunicipio", null);
			sesion.setAttribute("filtroCalle", null);
			//sesion.setAttribute("filtroAsociado", null);
		} else if ((Utilidades.isNotEmpty(sesionFiltroNombre) || Utilidades.isNotEmpty(sesionFiltroNIF)) && !(null != this.getIniciando() && this.getIniciando())) {
			try {
				start = new Integer(request.getParameter("start"));
				limit = new Integer(request.getParameter("limit"));
			} catch (Exception e) {
				start = 0;
				limit = 13;
			}

//			if (null != start && null != limit) {
//				pagina = new Integer(start) + limit;
//			}

			// Miramos si hay que mostrar los clientes asociados o no
			//String clientesAsociados;

			/*if (Utilidades.isEmpty(sesionFiltroAsociado))
				clientesAsociados = "true";
			else
				clientesAsociados = sesionFiltroAsociado;*/

			boolean contiene = !Utilidades.isEmpty(sesionFiltroTipoBusqueda) && sesionFiltroTipoBusqueda.equalsIgnoreCase("C");
//			Integer paginas = clienteBO.countByCriteria(getCriterio(false));
			Integer paginas = clienteBO.countByCriteria(sesionFiltroNombre, sesionFiltroNIF, sesionFiltroProvincia, sesionFiltroMunicipio, null, sesionFiltroCalle, null, null, null, null, null, null, null, contiene, false);
//			List<ClienteDTO> listaClientes = clienteBO.findByCriteria(getCriterio(true), start, limit);
			List<ClienteDTO> listaClientes = clienteBO.findByCriteria(sesionFiltroNombre, sesionFiltroNIF, sesionFiltroProvincia, sesionFiltroMunicipio, null, sesionFiltroCalle, null, null, null, null, null, null, null, contiene, false, start, limit);
			is = toXml(listaClientes, "", "clientes", getRequest(), paginas);
			/*Integer paginas = this.getClienteBO().findByParamsPorNifYNombre(sesionFiltroNombre.toUpperCase(), sesionFiltroNIF.toUpperCase(), clientesAsociados, pagina, listaToXml, limit, "contiene");

			is = toXml(listaToXml, "", "clientes", getRequest(), paginas);*/

			result = InputStreamToString(is);

		} else {
			sesion.setAttribute("filtroNombre", null);
			sesion.setAttribute("filtroNIF", null);
			sesion.setAttribute("filtroTipoBusqueda", null);
			sesion.setAttribute("filtroProvincia", null);
			sesion.setAttribute("filtroMunicipio", null);
			sesion.setAttribute("filtroCalle", null);
			//sesion.setAttribute("filtroAsociado", null);
		}

		// SI SE PUDO OBTENER UN XML LO ESCRIBIMOS EN EL RESPONSE
		if (null != result && result.length() > 0) {
			response.setContentType("text/xml");
			response.setCharacterEncoding("ISO-8859-1");
			Writer writer = response.getWriter();

			if (null != result)
				xmlToResponse(writer, result);
		} else {
			response.setContentType("text/xml");
			response.setCharacterEncoding("ISO-8859-1");
			Writer writer = response.getWriter();

			result = "<datos></datos>";

			if (null != result)
				xmlToResponse(writer, result);
		}

		return null;
	}
	
//	private DetachedCriteria getCriterio(boolean ordenar){
//		DetachedCriteria dc = DetachedCriteria.forClass(ClienteDTO.class, "clienteAlias");
//		
//		boolean contiene=false;
//		if (!Utilidades.isEmpty(sesionFiltroTipoBusqueda) && sesionFiltroTipoBusqueda.equalsIgnoreCase("C")){
//			contiene=true;
//		}
//		
//		dc.add(Restrictions.isNull("clienteDTOByCoClienteAsociado"));
//		
//		if (!Utilidades.isEmpty(sesionFiltroNombre)){
//			if (contiene){
////				dc.add(Restrictions.like("razonSocial", "%"+sesionFiltroNombre+"%").ignoreCase());
//				dc.add(Restrictions.sqlRestriction("contains(this_.razon_social, '%" + sesionFiltroNombre + "%')>0"));
//			}else{
//				dc.add(Restrictions.like("razonSocial", sesionFiltroNombre+"%").ignoreCase());
//			}
//		}
//		if (!Utilidades.isEmpty(sesionFiltroNIF)){
//			dc.add(Restrictions.eq("identificador", sesionFiltroNIF).ignoreCase());
//		}
//		if (!Utilidades.isEmpty(sesionFiltroProvincia) || !Utilidades.isEmpty(sesionFiltroMunicipio) || !Utilidades.isEmpty(sesionFiltroMunicipio)){
//		
//			DetachedCriteria otro = DetachedCriteria.forClass(DomicilioDTO.class, "otroAlias");
//			
//			otro.setProjection(Projections.property("coDomicilio"));
//			
//			otro.createAlias("otroAlias.unidadUrbanaDTO", "uu");
//			otro.createAlias("uu.calleDTO", "c");
//			
//			otro.add(Restrictions.eqProperty("otroAlias.clienteDTO.coCliente", "clienteAlias.coCliente"));
//			
//			otro.add(Restrictions.eq("boFiscalMunicipal",new Boolean(true)));
//			
//			if (!Utilidades.isEmpty(sesionFiltroProvincia)){
//				otro.add(Restrictions.eq("c.municipioDTO.id.coProvincia",sesionFiltroProvincia));
//			}
//			if (!Utilidades.isEmpty(sesionFiltroMunicipio)){
//				otro.add(Restrictions.eq("c.municipioDTO.id.coMunicipio",sesionFiltroMunicipio));
//			}
//			if (!Utilidades.isEmpty(sesionFiltroCalle)){
//				otro.add(Restrictions.like("c.nombreCalle", "%"+sesionFiltroCalle+"%").ignoreCase());
//			}
//			
//			dc.add(Subqueries.exists(otro));
//			
//		}
//		
//		if (ordenar) {
//			//TODO Ordenar por razon social.
//			dc.addOrder(Order.asc("razonSocial"));
//		}
//		return dc;
//	}
	
	public String buscarClientePorNif() {
		log.debug("Estoy dentro..");
		List<ClienteDTO> clienteDTOs = null;
		if (!filtroNIF.isEmpty()) {
			try {
				clienteDTOs = clienteBO.findFiltered("identificador", filtroNIF);
			} catch (Exception e) {
				log.error(e);
			}
		}
		if (clienteDTOs == null) {
			ajaxData = "|";
		} else if (clienteDTOs.size() < 1) {
			ajaxData = "|No existen clientes con el NIF seleccionado.";
		} else if (clienteDTOs.size() > 1) {
			ajaxData = "|Existen varios clientes con el NIF seleccionado.";
		} else {
			ClienteDTO clienteDTO = clienteDTOs.get(0);
			ajaxData = clienteDTO.getCoCliente().toString() + "|" + clienteDTO.getRazonSocial();
		}
		return AJAX_DATA;
	}

	/**
	 * Convierte la lista de beans pasada como parámetros en items de un XML.
	 * 
	 * @param listaToXML
	 *            lista de beans
	 * @param tagNombreTabla
	 *            nombre de la tabla
	 * @param tagItem
	 * @return El input stream con el XML creado.
	 */
	protected InputStream toXml(List<ClienteDTO> listaToXML, String tagNombreTabla, String tagItem, HttpServletRequest request, Integer totalResultados) {
		StringBuilder sb;
		InputStream stream;

		sb = new StringBuilder();
		stream = null;

		if (null == totalResultados)
			totalResultados = listaToXML.size();

		sb.append("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (null == tagNombreTabla || tagNombreTabla.length() <= 0)
			tagNombreTabla = "datos";

		sb.append("<" + tagNombreTabla + ">");
		sb.append("<total>" + totalResultados + "</total>");

		for (ClienteDTO cliente : listaToXML) {
			sb.append("<" + tagItem + ">");
			sb.append("<codCliente>").append((null != cliente.getCoCliente()) ? cliente.getCoCliente() : "").append("</codCliente>");
			sb.append("<nifCif>").append("<![CDATA[").append((null != cliente.getIdentificador()) ? StringEscapeUtils.escapeXml(cliente.getIdentificador()) : "").append("]]>").append("</nifCif>");
			sb.append("<nombreRazon>").append("<![CDATA[").append((null != cliente.getRazonSocial()) ? StringEscapeUtils.escapeXml(cliente.getRazonSocial()) : "").append("]]>").append("</nombreRazon>");
			sb.append("<domicilio>").append("<![CDATA[").append(StringEscapeUtils.escapeXml(DomicilioUtil.getDescripcionDomicilioFiscal(cliente.getCoCliente(), true))).append("]]>").append("</domicilio>");
			sb.append("</" + tagItem + ">");
		}

		sb.append("</" + tagNombreTabla + ">");

		stream = new ByteArrayInputStream(sb.toString().getBytes());

		return stream;
	}

	/**
	 * Transforma un InputStream (resultado del toXml) en un String
	 * 
	 * @param is
	 * @return
	 */
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

	/**
	 * Añade al response el xml que contiene los datos que se van a pintar en el
	 * formulario
	 * 
	 * @param writer
	 * @param result
	 */
	private void xmlToResponse(Writer writer, String result) {
		try {
			writer.write(result);
			writer.flush();
			writer.close();
		} catch (IOException e) {
		}
	}
	
	
	
	
	
	
	
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

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public String getFiltroNombre() {
		return filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre) {
		this.filtroNombre = filtroNombre;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFiltroNIF() {
		return filtroNIF;
	}

	public void setFiltroNIF(String filtroNIF) {
		this.filtroNIF = filtroNIF;
	}

	public String getFiltroTipoBusqueda() {
		return filtroTipoBusqueda;
	}

	public void setFiltroTipoBusqueda(String filtroTipoBusqueda) {
		this.filtroTipoBusqueda = filtroTipoBusqueda;
	}

	public String getFiltroProvincia() {
		return filtroProvincia;
	}

	public void setFiltroProvincia(String filtroProvincia) {
		this.filtroProvincia = filtroProvincia;
	}

	public String getFiltroMunicipio() {
		return filtroMunicipio;
	}

	public void setFiltroMunicipio(String filtroMunicipio) {
		this.filtroMunicipio = filtroMunicipio;
	}

	public String getFiltroCalle() {
		return filtroCalle;
	}

	public void setFiltroCalle(String filtroCalle) {
		this.filtroCalle = filtroCalle;
	}

	public String getSesionFiltroNombre() {
		return sesionFiltroNombre;
	}

	public void setSesionFiltroNombre(String sesionFiltroNombre) {
		this.sesionFiltroNombre = sesionFiltroNombre;
	}

	public String getSesionFiltroNIF() {
		return sesionFiltroNIF;
	}

	public void setSesionFiltroNIF(String sesionFiltroNIF) {
		this.sesionFiltroNIF = sesionFiltroNIF;
	}

	public String getSesionFiltroTipoBusqueda() {
		return sesionFiltroTipoBusqueda;
	}

	public void setSesionFiltroTipoBusqueda(String sesionFiltroTipoBusqueda) {
		this.sesionFiltroTipoBusqueda = sesionFiltroTipoBusqueda;
	}

	public String getSesionFiltroProvincia() {
		return sesionFiltroProvincia;
	}

	public void setSesionFiltroProvincia(String sesionFiltroProvincia) {
		this.sesionFiltroProvincia = sesionFiltroProvincia;
	}

	public String getSesionFiltroMunicipio() {
		return sesionFiltroMunicipio;
	}

	public void setSesionFiltroMunicipio(String sesionFiltroMunicipio) {
		this.sesionFiltroMunicipio = sesionFiltroMunicipio;
	}

	public String getSesionFiltroCalle() {
		return sesionFiltroCalle;
	}

	public void setSesionFiltroCalle(String sesionFiltroCalle) {
		this.sesionFiltroCalle = sesionFiltroCalle;
	}


}
