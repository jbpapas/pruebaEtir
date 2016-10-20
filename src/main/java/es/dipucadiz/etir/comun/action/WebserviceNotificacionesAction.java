package es.dipucadiz.etir.comun.action;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import es.dipucadiz.etir.comun.bo.NotificacionPeticionBO;
import es.dipucadiz.etir.comun.boStoredProcedure.InformarNotificacionResultadoBO;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.NotificacionPeticionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.InsertarIDECadiz;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.utilidades.RespuestaXML;

final public class WebserviceNotificacionesAction extends AbstractGadirBaseAction {

	private static final long serialVersionUID = 3043585538642096444L;
	private static final Log LOG = LogFactory.getLog(WebserviceNotificacionesAction.class);

	private String xmlPeticion;
	private String xmlRespuesta;
	private NotificacionPeticionBO notificacionPeticionBO;
	private InformarNotificacionResultadoBO informarNotificacionResultadoBO;

	public static final String CO_USUARIO_PLANIFICADOR = "planifica";
	public static final String CO_USUARIO_PLANIFICADOR_PWD = "1234512345";

	public String execute() throws IOException {
		try {

			//			final String[] paths = {"classpath*:/*/spring*/applicationContext*.xml"}; // Arranca el contexto necesario en modo batch
			//			final ApplicationContext context = new ClassPathXmlApplicationContext(paths);
			//
			//			DaoAuthenticationProvider daoAuthenticationProvider = (DaoAuthenticationProvider) context.getBean("daoAuthenticationProvider");
			//			Authentication auth = new UsernamePasswordAuthenticationToken(CO_USUARIO_PLANIFICADOR, CO_USUARIO_PLANIFICADOR_PWD);
			//			Authentication result = daoAuthenticationProvider.authenticate(auth);
			//			SecurityContext ctx = new SecurityContextImpl();
			//			ctx.setAuthentication(result);
			//			SecurityContextHolder.setContext(ctx);
			//
			//			AcmUsuarioBO acmUsuarioBO = (AcmUsuarioBO) context.getBean("acmUsuarioBO");
			//			final AcmUsuarioDTO acmUsuarioPlanificadorDTO = acmUsuarioBO.findById(CO_USUARIO_PLANIFICADOR);

			// el domicilio lo recibiremos de forma estructurada separados por el caracter · (mayusculas 3)
			//			xmlPeticion = 
			//					"<notificaciones>"+
			//							"<notificacion>"+
			//									"<codigoBarras>R-2015001033</codigoBarras>"+
			//									"<estado>03</estado>"+
			//									"<intentos>02</intentos>"+
			//									"<fecha>190115</fecha>"+
			//									"<hora>122125</hora>"+
			//									"<anio>2015</anio>"+
			//									"<latitud>-777.323</latitud>"+
			//									"<longitud>56.456</longitud>"+
			//									"<altitud></altitud>"+
			//									"<domicilio>municipio·provincia·11100·c/·via·L·B·E·PL·PU·ubicacion</domicilio>"+
			//							"</notificacion>"+
			//							"<notificacion>"+
			//									"<codigoBarras>NA30002518320281797830E</codigoBarras>"+
			//									"<estado>03</estado>"+
			//									"<intentos>02</intentos>"+
			//									"<fecha>190115</fecha>"+
			//									"<hora>122125</hora>"+
			//									"<anio>2015</anio>"+
			//									"<latitud>-777.323</latitud>"+
			//									"<longitud>56.456</longitud>"+
			//									"<altitud></altitud>"+
			//									"<domicilio></domicilio>"+
			//							"</notificacion>"+
			//							"<notificacion>"+
			//									"<codigoBarras>905211142006001738174411043145040000459080</codigoBarras>"+
			//									"<estado>03</estado>"+
			//									"<intentos>02</intentos>"+
			//									"<fecha>190115</fecha>"+
			//									"<hora>122125</hora>"+
			//									"<anio>2015</anio>"+
			//									"<latitud>-777.323</latitud>"+
			//									"<longitud>56.456</longitud>"+
			//									"<altitud></altitud>"+
			//									"<domicilio>municipio·provincia·11100·c/·via··B·E···</domicilio>"+
			//							"</notificacion>"+
			//				"</notificaciones>";

			LOG.error("Entra en WebservicePortafirmasAction");
			LOG.error("RequestURI: " + getRequest().getRequestURI());
			LOG.error("RequestURL: " + getRequest().getRequestURL());
			LOG.error("QueryString: " + getRequest().getQueryString());
			LOG.error("xmlPeticion: " + xmlPeticion);

			// 1: Registrar datos de la petición
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true); // never forget this!
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlPeticion)));

			Object o = notificacionPeticionBO.ejecutaQuerySelect("SELECT SEQ_NOTIFICACION_GRUPO.NEXTVAL FROM DUAL");
			long nuPeticion = ((BigDecimal) ((List<?>) o).get(0)).longValue();

			NodeList notificacionesNodeList = doc.getElementsByTagName("notificaciones").item(0).getChildNodes();
			xmlRespuesta = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
			for (int i = 0; i < notificacionesNodeList.getLength(); i++) {
				NotificacionPeticionDTO notificacionPeticionDTO = new NotificacionPeticionDTO();
				try {
					notificacionPeticionDTO.setNuPeticion(nuPeticion);
					notificacionPeticionDTO.setFhActualizacion(Utilidades.getDateActual());
					notificacionPeticionDTO.setCoUsuarioActualizacion("webService");

					Node notificacionNode = notificacionesNodeList.item(i);
					NodeList notificacionDatos = notificacionNode.getChildNodes();
					if (notificacionDatos.getLength() == 0) {
						continue;
					}
					for (int j = 0; j < notificacionDatos.getLength(); j++) {
						if (Utilidades.isNotEmpty(notificacionDatos.item(j).getTextContent().trim())) {
							if (notificacionDatos.item(j).getNodeName().equals("codigoBarras")) {
								notificacionPeticionDTO.setCodigoBarras(notificacionDatos.item(j).getTextContent().trim());
							} else if (notificacionDatos.item(j).getNodeName().equals("estado")) {
								notificacionPeticionDTO.setEstado(notificacionDatos.item(j).getTextContent().trim());
							} else if (notificacionDatos.item(j).getNodeName().equals("intentos")) {
								try {
									notificacionPeticionDTO.setIntentos(Integer.parseInt(notificacionDatos.item(j).getTextContent().trim()));
								} catch (NumberFormatException e) {
									notificacionPeticionDTO.setIntentos(Integer.parseInt("99")); // se utilizará 99 como error en el número de intentos
								}
							} else if (notificacionDatos.item(j).getNodeName().equals("fecha")) {
								notificacionPeticionDTO.setFecha(notificacionDatos.item(j).getTextContent().trim());
							} else if (notificacionDatos.item(j).getNodeName().equals("hora")) {
								notificacionPeticionDTO.setHora(notificacionDatos.item(j).getTextContent().trim());
							} else if (notificacionDatos.item(j).getNodeName().equals("anio")) {
								notificacionPeticionDTO.setAnio(notificacionDatos.item(j).getTextContent().trim());
							} else if (notificacionDatos.item(j).getNodeName().equals("latitud")) {
								notificacionPeticionDTO.setLatitud(notificacionDatos.item(j).getTextContent().trim());
							} else if (notificacionDatos.item(j).getNodeName().equals("longitud")) {
								notificacionPeticionDTO.setLongitud(notificacionDatos.item(j).getTextContent().trim());
							} else if (notificacionDatos.item(j).getNodeName().equals("altitud")) {
								notificacionPeticionDTO.setAltura(notificacionDatos.item(j).getTextContent().trim());
							} else if (notificacionDatos.item(j).getNodeName().equals("domicilio")) {
								notificacionPeticionDTO.setDomicilio(notificacionDatos.item(j).getTextContent().trim());
							}
							//TODO: Añadir todas las propiedades
							else {
								throw new GadirServiceException("Propiedad desconocida: " + notificacionDatos.item(j).getNodeName());
							}
						}
					}
				} catch (GadirServiceException e) {
					notificacionPeticionDTO.setCoError((short) MensajeConstants.V1);
					notificacionPeticionDTO.setMensajeError(e.getMensaje());
				}

				if (Utilidades.isEmpty(notificacionPeticionDTO.getCodigoBarras())) {
					throw new GadirServiceException("Registro tratado sin código de barras.");
				} else {
					notificacionPeticionDTO.setBoEnviadoIDE(false);
					notificacionPeticionBO.save(notificacionPeticionDTO);
				}
			}

			//TODO: 2: Llamada modulo registrar resultados de notificación
			try {

				informarNotificacionResultadoBO.execute(nuPeticion);

				// 3: Bucle para ver si hay errores
				List<NotificacionPeticionDTO> notificacionPeticionDTOs = notificacionPeticionBO.findFiltered("nuPeticion", nuPeticion, "coNotificacionPeticion", DAOConstant.ASC_ORDER);
				xmlRespuesta += "<notificaciones>";
				for (NotificacionPeticionDTO notificacionPeticionDTO : notificacionPeticionDTOs) {
					if (notificacionPeticionDTO.getCoError() == null) {
						escribirIncorrecto(notificacionPeticionDTO.getCodigoBarras(), "No procesado");
					} else if (notificacionPeticionDTO.getCoError() == 0) {

						String resultado = InsertarIDECadiz.envio(String.valueOf(notificacionPeticionDTO.getCoNotificacionPeticion()), "R");
						if (resultado.equals("0")) {

							escribirCorrecto_IDE(notificacionPeticionDTO.getCodigoBarras());
							notificacionPeticionDTO.setBoEnviadoIDE(true);
							notificacionPeticionBO.save(notificacionPeticionDTO);
						} else {

							escribirCorrecto(notificacionPeticionDTO.getCodigoBarras());
						}
					} else {
						escribirIncorrecto(notificacionPeticionDTO.getCodigoBarras(), Mensaje.getTexto(notificacionPeticionDTO.getCoError(), notificacionPeticionDTO.getMensajeError()));
					}
				}
				xmlRespuesta += "</notificaciones>";

			} catch (GadirServiceException e) {

				xmlRespuesta += "<notificaciones>";

				escribirErrorGeneral("Error general al procesar las notificaciones recibidas. " + e.getMensaje());

				xmlRespuesta += "</notificaciones>";
			}

		} catch (ParserConfigurationException e) {
			LOG.error(e.getMessage(), e);
			escribirErrorGrave(e.getMessage());
		} catch (SAXException e) {
			LOG.error(e.getMessage(), e);
			escribirErrorGrave(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			escribirErrorGrave(e.getMessage());
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			escribirErrorGrave(e.getMensaje());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			escribirErrorGrave(e.getMessage());
		}

		// 4: Escribir respuesta al cliente
		RespuestaXML.generaRespuestaXML(xmlRespuesta, ServletActionContext.getResponse());

		return null;
	}

	private void escribirErrorGeneral(String error) {
		xmlRespuesta += "<notificacion>\n";
		xmlRespuesta += "<errorGeneral>" + error + "</errorGeneral>\n";
		xmlRespuesta += "</notificacion>\n";
	}

	private void escribirCorrecto(String codigoBarras) {
		xmlRespuesta += "<notificacion>\n";
		xmlRespuesta += "<codigoBarras>" + codigoBarras + "</codigoBarras>\n";
		xmlRespuesta += "<error>0</error>\n";
		xmlRespuesta += "</notificacion>\n";
	}

	private void escribirCorrecto_IDE(String codigoBarras) {
		xmlRespuesta += "<notificacion>\n";
		xmlRespuesta += "<codigoBarras>" + codigoBarras + " correcto y almacenado en IDECadiz</codigoBarras>\n";
		xmlRespuesta += "<error>0</error>\n";
		xmlRespuesta += "</notificacion>\n";
	}

	private void escribirIncorrecto(String codigoBarras, String error) {
		xmlRespuesta += "<notificacion>\n";
		xmlRespuesta += "<codigoBarras>" + codigoBarras + "</codigoBarras>\n";
		xmlRespuesta += "<error>" + StringEscapeUtils.escapeXml(error) + "</error>\n";
		xmlRespuesta += "</notificacion>\n";
	}

	private void escribirErrorGrave(String error) {
		getServletResponse().setStatus(500);
		xmlRespuesta = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		xmlRespuesta += "<error>" + StringEscapeUtils.escapeXml(error) + "</error>\n";
	}

	public String getXmlRespuesta() {
		return xmlRespuesta;
	}

	public void setXmlRespuesta(String xmlRespuesta) {
		this.xmlRespuesta = xmlRespuesta;
	}

	public String getXmlPeticion() {
		return xmlPeticion;
	}

	public void setXmlPeticion(String xmlPeticion) {
		this.xmlPeticion = xmlPeticion;
	}

	public NotificacionPeticionBO getNotificacionPeticionBO() {
		return notificacionPeticionBO;
	}

	public void setNotificacionPeticionBO(NotificacionPeticionBO notificacionPeticionBO) {
		this.notificacionPeticionBO = notificacionPeticionBO;
	}

	public InformarNotificacionResultadoBO getInformarNotificacionResultadoBO() {
		return informarNotificacionResultadoBO;
	}

	public void setInformarNotificacionResultadoBO(InformarNotificacionResultadoBO informarNotificacionResultadoBO) {
		this.informarNotificacionResultadoBO = informarNotificacionResultadoBO;
	}

}
