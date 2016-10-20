package es.dipucadiz.etir.comun.action;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.displaytag.tags.TableTagParameters;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.inject.Inject;

import es.dipucadiz.etir.comun.annotation.MetodoVolvible;
import es.dipucadiz.etir.comun.bo.OtrBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.taglib.component.BotonVolverPila;
import es.dipucadiz.etir.comun.utilidades.ControlTerritorial;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.DisplayTagUtil;
import es.dipucadiz.etir.comun.utilidades.Impresion;
import es.dipucadiz.etir.comun.utilidades.ItemPilaVolver;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb06.utilidades.TramitadorConstants;

@SuppressWarnings("unchecked")
public abstract class AbstractGadirBaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware, ServletContextAware, Preparable {

	protected final Log log = LogFactory.getLog(getClass());

	public static final String ALTA = "alta";
	public static final String DETALLE = "detalle";
	public static final String SELECCION = "seleccion";
	public static final String IMPRESION = "impresion";
	public static final String VOLVER = "volver";
	public static final String PUNTOS_MENU = "puntosMenu";
	public static final String BLANK = "blank";
	public static final String CONSULTA = "consultaUsuarioPerfil";
	public static final String MODIFICAR = "modificar";
	public static final String DOCUMENTOS = "documentos";
	public static final String COOKIE_TAB_NAME = "tabName";

	// para devolver las opciondes de un autocompleter en las llamadas ajax
	public static final String AUTOCOMPLETER_OPTIONS = "autocompleterOptions";
	public static final String AJAX_DATA = "ajaxData";
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 4741468638637467189L;

	/**
	 * Sufijo por defecto de los action.
	 */
	@Inject(value = StrutsConstants.STRUTS_ACTION_EXTENSION, required = false)
	private String defaultExtension;

	/**
	 * Atributo para almacena la sesion.
	 */
	private Map<WEBKEYS, Collection<String>> session;

	/**
	 * Atributo para controlar los tabIndex de componentes.
	 */
	private Integer tabIndex;

	/**
	 * Atributo para almacenar la request.
	 */
	private HttpServletRequest servletRequest;

	/**
	 * Atributo para almacenar la response.
	 */
	private HttpServletResponse servletResponse;

	/**
	 * Atributo para almacenar el context.
	 */
	private ServletContext servletContext;

	private ItemPilaVolver itemPilaVolver;

	private Boolean consulta = false;

	//private String coAcmMenu;

	private int informeActuacion = Impresion.ACT_ABRIR;
	private String informeParametro;

	protected List<KeyValue> autocompleterOptions = new ArrayList<KeyValue>();
	protected String ajaxData = "";
	protected boolean autocompleterConCodigo = true;

	//private String coProcesoActual;

	private String pestanaDocumentos;
	private String pestana;
	private String subpestana;
	private String subsubpestana;
	private boolean clearStatus = false;

	private boolean botonVolverPila = false;
	private boolean preapilar = true;
	private boolean vaciarPila = false;
	private boolean isAbrirPdf = false;

	private boolean ventanaBotonLateral = false;

	protected String ejecutarIncidenciaObservaciones;
	protected Date ejecutarIncidenciaFechaBaja;
	protected String ejecutarIncidenciaEconomica;

	protected Date ejecutarIncidenciaCargoFechaInicioVoluntaria;
	protected Date ejecutarIncidenciaCargoFechaFinVoluntaria;
	protected String ejecutarIncidenciaCargoOrden;
	/*private String ejecutarIncidenciaCoModeloSel; 
	private String ejecutarIncidenciaCoVersionSel; 
	private String ejecutarIncidenciaCoDocumentoSel; 
	private String ejecutarIncidenciaCoIncidenciaSituacion;*/
	protected OtrBO otrBO;

	public String volverPila() {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			setItemPilaVolver(DatosSesion.popPilaVolver(tabName));
		}

		return "volverPila";
	}

	public void volverPilaRedirect() throws GadirServiceException {

		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			String donde = null;
			try {

				if (DatosSesion.topPilaVolver(tabName) != null) {
					donde = BotonVolverPila.getURL(DatosSesion.topPilaVolver(tabName));

					getServletResponse().sendRedirect(donde);

				} else {
					if (DatosSesion.getPreapilado(tabName) != null) {
						addActionError(Mensaje.getTexto(MensajeConstants.V1, "Error intentando volver con la pila vacía"));
						donde = BotonVolverPila.getURL(DatosSesion.getPreapilado(tabName));
						getServletResponse().sendRedirect(donde);
					} else {
						addActionError(Mensaje.getTexto(MensajeConstants.V1, "Error intentando volver con la pila vacía"));
						getServletResponse().sendRedirect("/etir/bienvenido.action");
					}

				}
			} catch (IOException e) {
				throw new GadirServiceException("No puede redireccionar: " + donde, e);
			}
		}
	}

	public void prepare() throws Exception {
		if (this instanceof Volvible && !isExport() && !isAbrirPdf()) {
			if (comprobacionesPreapilar()) {
				preapilar();
			}
		} else {
			//limpiaPreapilacion();
		}

		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			DatosSesion.actualizaNumeracionVentanas(tabName);
			if (vaciarPila) {
				DatosSesion.vaciaPilaVolver(tabName);
			}

			if (botonVolverPila) {
				if (!isPilaVacia()) DatosSesion.popPilaVolver(tabName);
			}

		}

	}

	private boolean comprobacionesPreapilar() {

		boolean resultado = true;

		if (preapilar) {
			// mejorar la forma de averiguar si hay method
			Enumeration<String> en = getServletRequest().getParameterNames();

			if (getServletRequest().getServletPath().contains("!") && !getServletRequest().getServletPath().contains("executePestana")) {
				resultado = false;
			}

			while (resultado && en.hasMoreElements()) {
				String paramName = en.nextElement();
				if (paramName != null && paramName.startsWith("method:")) {
					//hay method, busco si esta anotado
					String methodName = paramName.substring("method:".length());
					Class actionClass = ActionContext.getContext().getActionInvocation().getAction().getClass();
					for (Method method : actionClass.getDeclaredMethods()) {
						if (method.getName().equals(methodName)) {

							if (method.isAnnotationPresent(MetodoVolvible.class)) {

								Annotation annotation = method.getAnnotation(MetodoVolvible.class);
								if (annotation != null) {
									MetodoVolvible metodoVolvible = (MetodoVolvible) annotation;
									resultado = true;
								}

							} else {
								resultado = false;
							}
							break;
						}

					}

					/*String paramValue = getServletRequest().getParameter(paramName);
					if (paramValue != null) {
						if ((getActionName().equals("G5332Listado") && paramValue.equals("Seleccionar"))|| (getActionName().equals("G761Argumentos") && paramValue.equals("actualizar"))
							|| (getActionName().equals("G7H2Detalle") && paramValue.equals("Buscar")) || (getActionName().equals("G7H2Detalle") && paramValue.equals("Anular"))
							|| ((getActionName().equals("G743TodosDocumentos")|| getActionName().equals("G743CensoDocumentos") || getActionName().equals("G743LiquidacionDocumentos")
									|| getActionName().equals("G743ReciboDocumentos") || getActionName().equals("G743OtrosDocumentos") || getActionName().equals("G772Documentos")
									|| getActionName().equals("G728Detalle") || getActionName().equals("G7431LiquidacionDocumentos") || getActionName().equals("G63AsociarDocumentos")
									|| getActionName().equals("G7G4DocumentosPendiente") || getActionName().equals("G7431ReciboDocumentos"))
								&& paramValue.equals("Aceptar"))
							|| (getActionName().equals("G7E3SinAsignar") && paramValue.equals("Aceptar")) || (getActionName().equals("G743PendienteCalculo") && paramValue.equals("Aceptar"))
							|| (getActionName().equals("G728Detalle") && paramValue.equals("Documentación Adjunta"))
					
						) {
							resultado = true;
						} else {
							resultado = false;
						}
					} else {
						resultado = false;
					}*/
				}
			}
			//		ActionContext.getContext().getName();
			//		ActionConfig.
			//		InputConfig annotation = (InputConfig)action.getClass().getMethod(invocation.getProxy().getMethod(), EMPTY_CLASS_ARRAY).getAnnotation(InputConfig.class);

		} else {
			resultado = false;
		}

		return resultado;
	}

	private void preapilar() {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			DatosSesion.preapila(tabName, getServletRequest().getParameterMap(), getActionName());
		}
	}

	//	private void limpiaPreapilacion(){
	//		DatosSesion.borraPreapilado();
	//	}

	public void apilar() {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			if (comprobacionesPreapilar()) {
				//tengo que recuperar el preapilado penultimo, porque en este prepare he vuelto a preapilar
				DatosSesion.pushPilaVolver(tabName, DatosSesion.getAnteriorPreapilado(tabName));
			} else {
				DatosSesion.pushPilaVolver(tabName, DatosSesion.getPreapilado(tabName));
			}

		}
	}

	public void apilarPreapilado() {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			DatosSesion.preapila(tabName, getServletRequest().getParameterMap(), getActionName());
			DatosSesion.pushPilaVolver(tabName, DatosSesion.getPreapilado(tabName));
		}
	}

	protected ItemPilaVolver getAnteriorPreapilado() {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			return DatosSesion.getAnteriorPreapilado(tabName);
		}
		return null;
	}

	public ItemPilaVolver topPilaVolver() {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			return DatosSesion.topPilaVolver(tabName);
		}
		return null;
	}

	public ItemPilaVolver segundoElementoPilaVolver() {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			return DatosSesion.topPilaVolver(tabName, 1);
		}
		return null;
	}

	public ItemPilaVolver terceroElementoPilaVolver() {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			return DatosSesion.topPilaVolver(tabName, 2);
		}
		return null;
	}

	protected ItemPilaVolver popPilaVolver() {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			return DatosSesion.popPilaVolver(tabName);
		}
		return null;
	}

	protected void pushPilaVolver(ItemPilaVolver itemPilaVolver) {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			DatosSesion.pushPilaVolver(tabName, itemPilaVolver);
		}
	}

	protected String getTabName() {
		String tabName = "";
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (COOKIE_TAB_NAME.equals(cookies[i].getName())) {
					tabName = cookies[i].getValue();
				}
			}
		}
		return tabName;
	}

	public String getEntorno() {
		String entorno = "";
		entorno = GadirConfig.leerParametro("entorno.servidor");

		return entorno;
	}

	/*public String botonOpcion(){
		String resultado=OpcionesIncidenciaSituacionUtil.ejecutarIncidencia(ejecutarIncidenciaCoModeloSel, ejecutarIncidenciaCoVersionSel, ejecutarIncidenciaCoDocumentoSel, ejecutarIncidenciaCoIncidenciaSituacion);
		if (Utilidades.isEmpty(resultado)){
			addActionMessage("Incidencia ejecutada correctamente");
		}else{
			addActionError(resultado);
		}
		return execute();
	}
	
	public String ajaxBotonCalculaOpciones(){
		ajaxData=ejecutarIncidenciaCoModeloSel + "##" + ejecutarIncidenciaCoVersionSel + "##" + ejecutarIncidenciaCoDocumentoSel;
		if (!Utilidades.isEmpty(ejecutarIncidenciaCoModeloSel) && !Utilidades.isEmpty(ejecutarIncidenciaCoVersionSel) && !Utilidades.isEmpty(ejecutarIncidenciaCoDocumentoSel)){
			try{
				DocumentoDTO documentoDTO = documentoBO.findById(new DocumentoDTOId(ejecutarIncidenciaCoModeloSel, ejecutarIncidenciaCoVersionSel, ejecutarIncidenciaCoDocumentoSel));
				if (documentoDTO.getMunicipioDTO()!=null && documentoDTO.getModeloVersionDTO()!=null && documentoDTO.getSituacionDTO()!=null){
					List<IncidenciaSituacionDTO> listaOpciones=OpcionesIncidenciaSituacionUtil.getOpcionesIncidencia(
							documentoDTO.getMunicipioDTO().getId().getCoProvincia(), 
							documentoDTO.getMunicipioDTO().getId().getCoMunicipio(), 
							documentoDTO.getModeloVersionDTO().getId().getCoModelo(), 
							documentoDTO.getModeloVersionDTO().getId().getCoVersion(), 
							documentoDTO.getSituacionDTO().getCoSituacion(),
							null,
							null);
					for (IncidenciaSituacionDTO incidenciaSituacionDTO : listaOpciones){
						ajaxData += "@@" + incidenciaSituacionDTO.getIncidenciaDTO().getNombre() + "##" + incidenciaSituacionDTO.getCoIncidenciaSituacion() + "##" +  incidenciaSituacionDTO.getIncidenciaDTO().getCoIncidencia();
					}
				}
			}catch(Exception e){
				LOG.error("error en ajaxBotonCalculaOpciones", e);
			}
		}
		return AJAX_DATA;
	}*/

	public void setProperty(String name, Object value) {
		try {
			BeanUtils.setProperty(this, name, value);
		} catch (Exception e) {
			log.warn("AbstractGadirBaseAction.setProperty", e);
		}
	}

	public Object getProperty(String name) {
		try {
			return BeanUtils.getProperty(this, name);
		} catch (Exception e) {
			log.warn("AbstractGadirBaseAction.setProperty", e);
		}
		return null;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(final HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(final HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(final ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ItemPilaVolver getItemPilaVolver() {
		return itemPilaVolver;
	}

	public void setItemPilaVolver(final ItemPilaVolver itemPilaVolver) {
		this.itemPilaVolver = itemPilaVolver;
	}

	public boolean isPilaVacia() {
		String tabName = getTabName();
		if (StringUtils.isNotEmpty(tabName)) {
			return DatosSesion.topPilaVolver(tabName) == null;
		}
		return true;
	}

	public void setConsulta(final Boolean consulta) {
		this.consulta = consulta;

		this.getRequest().getSession().setAttribute("consulta", consulta);
	}

	public Boolean isConsulta() {
		if (consulta == null) {
			if (this.getRequest().getSession().getAttribute("consulta") == null) {
				return true;
			} else {
				return (Boolean) this.getRequest().getSession().getAttribute("consulta");
			}
		} else {
			return consulta;
		}
	}

	public boolean isUsuarioAyuntamiento() {
		return ControlTerritorial.isUsuarioAyuntamiento();
	}

	/**
	 * Método que se encarga de devolver la petición realizada al servidor
	 * (Request).
	 * 
	 * @return Petición realizada.
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Método que se encarga de devolver el parametro de configuración asociado
	 * a la key dada.
	 * 
	 * @param key
	 *            - Key del parametro.
	 * @return String con el valor del parametro o null (si no lo encuentra o
	 *         ocurre un error).
	 */
	public String getParametro(final String key) {
		return GadirConfig.leerParametro(key);
	}

	/**
	 * Método que se encarga de devolver el nombre de la variable que almacena
	 * el numero de pagina de un display-table.
	 * 
	 * @param uid
	 *            UID del display-table.
	 * @return String con el nombre de la pagina.
	 */
	public String getNombrePaginaDisplay(final String uid) {
		return DisplayTagUtil.getVariablePagina(uid, this.getRequest());
	}

	/**
	 * Método que se encarga de devolver la pagina actual de un display-table.
	 * 
	 * @param uid
	 *            UID del display-table.
	 * @return Pagina actual.
	 */
	public Integer getValorPaginaDisplay(final String uid) {
		return DisplayTagUtil.getValorPagina(uid, this.getRequest());
	}

	/**
	 * Método que se encarga de devolver el result SUCCESS, util para
	 * redirecciones entre pantallas que no requieran realizar operaciones
	 * intermedias.
	 * 
	 * @return Retorno a configuracion de Struts.
	 */
	public String redireccionar() {
		return SUCCESS;
	}

	/**
	 * Método que se encarga de obtener un mensaje aplicando los parametros
	 * dados.
	 * 
	 * @param key
	 *            Key/Id del mensaje.
	 * @param params
	 *            Parametros dados para completar el mensaje.
	 * @return Mensaje formado.
	 */
	public String getMensaje(final String key, final List<String> params) {
		String texto = null;
		try {
			final Integer idMensaje = Integer.valueOf(key);
			texto = Mensaje.getTexto(idMensaje, params);
		} catch (final Exception se) {
			LOG.error("Se produjo un error al obtener el mensaje: " + key + "\n" + "Aplicando los parametros: " + params.toString());
		}
		return texto;
	}

	/**
	 * Método que se encarga de obtener un mensaje.
	 * 
	 * @param key
	 *            Key/Id del mensaje.
	 * @return Mensaje formado.
	 */
	public String getMensaje(final String key) {
		String texto = null;
		try {
			// Si la key es numerico y el servicio esta instanciado obtenemos el
			// mensaje de BD, si no se obtiene de los ficheros de propiedades de
			// struts 2.
			if (StringUtils.isNumeric(key)) {
				final Integer idMensaje = Integer.valueOf(key);
				texto = Mensaje.getTexto(idMensaje);
			} else {
				texto = super.getText(key);
			}
		} catch (final Exception se) {
			LOG.error("Se produjo un error al obtener el mensaje: " + se.getMessage(), se);
		}
		return texto;
	}

	/**
	 * Método para generar el informe. Debe existir un registro en GA_PLANTILLA asociado al proceso actual y la acción "I".
	 * @param listaEtiquetas Puede ser una lista de KeyValue o una lista de String.
	 * @throws GadirServiceException
	 */
	public void generarInforme(List<?> listaEtiquetas) throws GadirServiceException {
		Impresion.merge(listaEtiquetas, DatosSesion.getCoProcesoActual(getTabName()), DatosSesion.getLogin(), getInformeActuacion(), getInformeParametro(), getServletResponse());
	}

	/**
	 * Método que se encarga de devolver el result SUCCESS, util para
	 * redireccionar entre pantallas que no requieran operaciones. Se excluye
	 * del interceptor prepare.
	 * 
	 * @return Retorno a configuracion de Struts.
	 */
	public String volver() throws GadirServiceException {
		return SUCCESS;
	}

	//jbenitac: lo que sigue es para conseguir que los actionMessage y los ActionError se intercambien entre actions
	private enum WEBKEYS {
		USER_VIEW, ACTION_MESSAGE, ACTION_ERROR;
	}

	@Override
	public void addActionError(String anErrorMessage) {
		LOG.error(anErrorMessage);
		if (Utilidades.isNotEmpty(anErrorMessage) && anErrorMessage.startsWith(TramitadorConstants.LITERAL_AVISO)) {
			// Si el mensaje de error comienza por el indicador de Aviso, se pondra en verde y no en rojo.
			addActionMessage(anErrorMessage.substring(TramitadorConstants.LITERAL_AVISO.length()));
		} else {
			super.addActionError(anErrorMessage);
			this.session.put(WEBKEYS.ACTION_ERROR, super.getActionErrors());
		}
	}

	@Override
	public void addActionMessage(String aMessage) {
		super.addActionMessage(aMessage);
		this.session.put(WEBKEYS.ACTION_MESSAGE, super.getActionMessages());
	}

	private void clearErrorsSession() {
		this.session.remove(WEBKEYS.ACTION_ERROR);
	}

	private void clearMessagesSession() {
		this.session.remove(WEBKEYS.ACTION_MESSAGE);
	}

	@Override
	public void clearErrorsAndMessages() {
		clearErrorsSession();
		clearMessagesSession();
		super.clearErrorsAndMessages();
	}

	public boolean hasActionErrorsSession() {
		Collection<String> errors = (Collection<String>) this.session.get(WEBKEYS.ACTION_ERROR);
		if (errors != null && !errors.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasActionMessagesSession() {
		Collection<String> messages = (Collection<String>) this.session.get(WEBKEYS.ACTION_MESSAGE);
		if (messages != null && !messages.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public Collection<String> getActionErrorsSession() {
		Collection<String> errors = (Collection<String>) this.session.get(WEBKEYS.ACTION_ERROR);
		clearErrorsSession();
		clearMessagesSession();
		return errors;
	}

	public Collection<String> getActionMessagesSession() {
		Collection<String> messages = (Collection<String>) this.session.get(WEBKEYS.ACTION_MESSAGE);
		clearMessagesSession();
		return messages;
	}
	//jbenitac: hasta aqui

	public void setObjetoSesion(String nombre, Object objeto) {
		DatosSesion.setObjeto(getTabName(), nombre, objeto);
	}

	public Object getObjetoSesion(String nombre) {
		return DatosSesion.getObjeto(getTabName(), nombre);
	}

	public void borraObjetoSesion(String nombre) {
		DatosSesion.borraObjeto(getTabName(), nombre);
	}

	public boolean existeObjetoSesion(String nombre) {
		return DatosSesion.existeObjeto(getTabName(), nombre);
	}

	public String getPaginaTitulo() {
		return getText("pagina.titulo");
	}

	/**
	 * Método que se encarga de devolver el nombre de la accion actual.
	 * 
	 * @return String con el nombre de la accion.
	 */
	public String getActionName() {
		return ActionContext.getContext().getName();
	}

	/**
	 * Método que devuelve el atributo tabIndex.
	 * 
	 * @return tabIndex.
	 */
	public Integer getTabIndex() {
		if (this.tabIndex == null) {
			this.tabIndex = 0;
		}
		this.tabIndex += 1;
		return tabIndex;
	}

	/**
	 * Método que establece el atributo tabIndex.
	 * 
	 * @param tabIndex
	 *            El tabIndex.
	 */
	public void setTabIndex(final Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	/**
	 * Método que devuelve el atributo session.
	 * 
	 * @return session.
	 */
	public Map<WEBKEYS, Collection<String>> getSession() {
		return session;
	}

	/**
	 * Método que establece el atributo session.
	 * 
	 * @param session
	 *            El session.
	 */
	@SuppressWarnings("rawtypes")
	public void setSession(final Map session) {
		this.session = session;
	}

	/**
	 * Método que devuelve el atributo defaultExtension.
	 * 
	 * @return defaultExtension.
	 */
	public String getDefaultExtension() {
		return defaultExtension;
	}

	/**
	 * Método que establece el atributo defaultExtension.
	 * 
	 * @param defaultExtension
	 *            El defaultExtension.
	 */
	public void setDefaultExtension(final String defaultExtension) {
		this.defaultExtension = defaultExtension;
	}

	public List<KeyValue> getAutocompleterOptions() {
		return autocompleterOptions;
	}

	public void setAutocompleterOptions(final List<KeyValue> autocompleterOptions) {
		this.autocompleterOptions = autocompleterOptions;
	}

	public int getInformeActuacion() {
		return informeActuacion;
	}

	public void setInformeActuacion(int informeActuacion) {
		this.informeActuacion = informeActuacion;
	}

	public String getInformeParametro() {
		return informeParametro;
	}

	public void setInformeParametro(String informeParametro) {
		this.informeParametro = informeParametro;
	}

	public String getPestanaDocumentos() {
		return pestanaDocumentos;
	}

	public void setPestanaDocumentos(String pestanaDocumentos) {
		this.pestanaDocumentos = pestanaDocumentos;
	}

	public String getSubpestana() {
		return subpestana;
	}

	public void setSubpestana(String subpestana) {
		this.subpestana = subpestana;
	}

	public boolean isAutocompleterConCodigo() {
		return autocompleterConCodigo;
	}

	public void setAutocompleterConCodigo(boolean autocompleterConCodigo) {
		this.autocompleterConCodigo = autocompleterConCodigo;
	}

	public boolean isBotonVolverPila() {
		return botonVolverPila;
	}

	public void setBotonVolverPila(boolean botonVolverPila) {
		this.botonVolverPila = botonVolverPila;
	}

	public boolean isPreapilar() {
		return preapilar;
	}

	public void setPreapilar(boolean preapilar) {
		this.preapilar = preapilar;
	}

	public boolean isVaciarPila() {
		return vaciarPila;
	}

	/**
	 * @return the clearStatus
	 */
	public boolean isClearStatus() {
		return clearStatus;
	}

	/**
	 * @param clearStatus the clearStatus to set
	 */
	public void setClearStatus(boolean clearStatus) {
		this.clearStatus = clearStatus;
	}

	public void setVaciarPila(boolean vaciarPila) {
		this.vaciarPila = vaciarPila;
	}

	public boolean isVentanaBotonLateral() {
		return ventanaBotonLateral;
	}

	public void setVentanaBotonLateral(boolean ventanaBotonLateral) {
		this.ventanaBotonLateral = ventanaBotonLateral;
	}

	public String getCoProcesoActual() {
		//este metodo lo usan los gadirTags
		return DatosSesion.getCoProcesoActual(getTabName());
	}

	public String getPestana() {
		return pestana;
	}

	public void setPestana(String pestana) {
		this.pestana = pestana;
	}

	public String getSubsubpestana() {
		return subsubpestana;
	}

	public void setSubsubpestana(String subsubpestana) {
		this.subsubpestana = subsubpestana;
	}

	public String getAjaxData() {
		return ajaxData;
	}

	public void setAjaxData(String ajaxData) {
		this.ajaxData = ajaxData;
	}

	public String getEjecutarIncidenciaObservaciones() {
		return ejecutarIncidenciaObservaciones;
	}

	public void setEjecutarIncidenciaObservaciones(String ejecutarIncidenciaObservaciones) {
		this.ejecutarIncidenciaObservaciones = ejecutarIncidenciaObservaciones;
	}

	public String getEjecutarIncidenciaEconomica() {
		return ejecutarIncidenciaEconomica;
	}

	public void setEjecutarIncidenciaEconomica(String ejecutarIncidenciaEconomica) {
		this.ejecutarIncidenciaEconomica = ejecutarIncidenciaEconomica;
	}

	public String getEjecutarIncidenciaFechaBaja() {
		String res = "";
		try {
			res = Utilidades.dateToStrutsFormat(ejecutarIncidenciaFechaBaja);
		} catch (Exception e) {}
		return res;
	}

	public void setEjecutarIncidenciaFechaBaja(String ejecutarIncidenciaFechaBaja) {
		this.ejecutarIncidenciaFechaBaja = null;
		try {
			this.ejecutarIncidenciaFechaBaja = Utilidades.strutsFormatToDate(ejecutarIncidenciaFechaBaja);
		} catch (Exception e) {}
	}

	public String getEjecutarIncidenciaCargoFechaInicioVoluntaria() {
		String res = "";
		try {
			res = Utilidades.dateToStrutsFormat(ejecutarIncidenciaCargoFechaInicioVoluntaria);
		} catch (Exception e) {}
		return res;
	}

	public void setEjecutarIncidenciaCargoFechaInicioVoluntaria(String ejecutarIncidenciaCargoFechaInicioVoluntaria) {
		this.ejecutarIncidenciaCargoFechaInicioVoluntaria = null;
		try {
			this.ejecutarIncidenciaCargoFechaInicioVoluntaria = Utilidades.strutsFormatToDate(ejecutarIncidenciaCargoFechaInicioVoluntaria);
		} catch (Exception e) {}
	}

	public String getEjecutarIncidenciaCargoFechaFinVoluntaria() {
		String res = "";
		try {
			res = Utilidades.dateToStrutsFormat(ejecutarIncidenciaCargoFechaFinVoluntaria);
		} catch (Exception e) {}
		return res;
	}

	public void setEjecutarIncidenciaCargoFechaFinVoluntaria(String ejecutarIncidenciaCargoFechaFinVoluntaria) {
		this.ejecutarIncidenciaCargoFechaFinVoluntaria = null;
		try {
			this.ejecutarIncidenciaCargoFechaFinVoluntaria = Utilidades.strutsFormatToDate(ejecutarIncidenciaCargoFechaFinVoluntaria);
		} catch (Exception e) {}
	}

	public String getEjecutarIncidenciaCargoOrden() {
		return ejecutarIncidenciaCargoOrden;
	}

	public void setEjecutarIncidenciaCargoOrden(String ejecutarIncidenciaCargoOrden) {
		this.ejecutarIncidenciaCargoOrden = ejecutarIncidenciaCargoOrden;
	}

	public String getActionNameTriki() {
		String result = getActionName();

		if (result.endsWith("Ajax")) result = result.substring(0, result.length() - 4);

		return result;
	}

	public boolean actionHasMethod(String methodName) {
		try {
			Method methodToFind = this.getClass().getMethod(methodName, (Class<?>[]) null);
			if (methodToFind != null) {
				return true;
			}
		} catch (Exception e) {
			//no logeo nada
		}
		return false;
	}

	public boolean isExport() {
		return getRequest().getParameterMap().containsKey(TableTagParameters.PARAMETER_EXPORTING);
	}

	public boolean isAbrirPdf() {
		return isAbrirPdf;
	}

	public void setAbrirPdf(boolean isAbrirPdf) {
		this.isAbrirPdf = isAbrirPdf;
	}

	public OtrBO getOtrBO() {
		return otrBO;
	}

	public void setOtrBO(OtrBO otrBO) {
		this.otrBO = otrBO;
	}

}
