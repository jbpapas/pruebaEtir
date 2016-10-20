package es.dipucadiz.etir.comun.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pfirma.client.PfServicioWS;
import pfirma.client.PfServicioWSServiceLocator;
import pfirma.ws.EntregaWS;
import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.BDDocumentalBO;
import es.dipucadiz.etir.comun.bo.PortafirmasBO;
import es.dipucadiz.etir.comun.bo.PortafirmasSeguimientoBO;
import es.dipucadiz.etir.comun.boStoredProcedure.GuardarDocumentacionBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.constants.PortafirmasConstants;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.BDDocumentalDTO;
import es.dipucadiz.etir.comun.dto.ExpedienteSeguimientoDTO;
import es.dipucadiz.etir.comun.dto.PortafirmasDTO;
import es.dipucadiz.etir.comun.dto.PortafirmasSeguimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Batch;
import es.dipucadiz.etir.comun.utilidades.BatchConstants;
import es.dipucadiz.etir.comun.utilidades.Impresion;
import es.dipucadiz.etir.comun.utilidades.Planificador;
import es.dipucadiz.etir.comun.utilidades.PortafirmasService;
import es.dipucadiz.etir.comun.utilidades.PostBatch;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;
import es.dipucadiz.etir.sb06.bo.TramitadorBO;



final public class WebservicePortafirmasAction extends AbstractGadirBaseAction {
	private static final long serialVersionUID = 4151027296489021128L;
	private static final Log LOG = LogFactory.getLog(WebservicePortafirmasAction.class);

	private static String urlPortafirmas = GadirConfig.leerParametro("url.ws.portafirmas");
	private static String rutaAdjuntos = GadirConfig.leerParametro("ruta.informes.merge");
	
	private String xmlRespuesta;
	private PortafirmasBO portafirmasBO;
	private PortafirmasSeguimientoBO portafirmasSeguimientoBO;
	private GuardarDocumentacionBO guardarDocumentacionBO;
	private BDDocumentalBO bdDocumentalBO;
	private AcmUsuarioBO acmUsuarioBO;
	private TramitadorBO tramitadorBO;

	public String execute(){
		try {
			PostBatch.setEjecucionBatch(true);
			
			LOG.error("Entra en WebservicePortafirmasAction");
			LOG.error("RequestURI: " + getRequest().getRequestURI());
			LOG.error("RequestURL: " + getRequest().getRequestURL());
			LOG.error("QueryString: " + getRequest().getQueryString());

			String dni = getServletRequest().getParameter("EDNI");
			String estado = getServletRequest().getParameter("ESTADO");
			String hashPet = getServletRequest().getParameter("HASHPET");
			String hashDoc = getServletRequest().getParameter("EHASH");
			String id = getServletRequest().getParameter("id");
			
			LOG.error("EDNI: " + dni);
			LOG.error("ESTADO: " + estado);
			LOG.error("HASHPET: " + hashPet);
			LOG.error("EHASH: " + hashDoc);
			LOG.error("id: " + id);

			URL url = new URL(urlPortafirmas + "services/PfServicioWS?wsdl");
			PfServicioWSServiceLocator locator = new PfServicioWSServiceLocator();
			PfServicioWS pfirma = locator.getPfServicioWS(url);

			if(estado == null || "".equals(estado)) {
				EntregaWS[] entregas = pfirma.consultarEntregasPeticion(hashPet);
				if(entregas != null && entregas.length > 0) {
					EntregaWS entregaWS = entregas[0];
					estado = entregaWS.getCESTADO();
					LOG.error("entregaWS.getCESTADO: " + estado);
				}
			}

			if (hashPet == null || hashPet.equals("") || hashDoc == null
					|| hashDoc.equals("") || dni == null || dni.equals("")
					|| estado == null || estado.equals("")) {
				throw new GadirServiceException("Parámetros recibidos no correctos");
			}

			// Recuperar motivos de rechazo o devolución
			String observaciones = pfirma.consultarObservacionesEntrega(hashDoc, dni);
			LOG.error("observaciones: " + observaciones);
			
			PortafirmasDTO portafirmasDTO = portafirmasBO.findById(Long.valueOf(id));
			final AcmUsuarioDTO acmUsuarioEjecucionDTO = acmUsuarioBO.findById(obtenerUsuarioAperturaExpedienteOrigen(portafirmasDTO.getExpedienteOrigenDTO().getCoExpediente()));
			PortafirmasSeguimientoDTO portafirmasSeguimientoDTO = new PortafirmasSeguimientoDTO();
			portafirmasSeguimientoDTO.setPortafirmasDTO(portafirmasDTO);
			portafirmasSeguimientoDTO.setEstado(estado);
			portafirmasSeguimientoDTO.setFhEstado(Utilidades.getDateActual());
			portafirmasSeguimientoDTO.setDni(dni);
			portafirmasSeguimientoDTO.setObservaciones(observaciones);
			portafirmasSeguimientoDTO.setFhActualizacion(Utilidades.getDateActual());
			portafirmasSeguimientoDTO.setCoUsuarioActualizacion(acmUsuarioEjecucionDTO.getCoAcmUsuario());
			portafirmasSeguimientoBO.save(portafirmasSeguimientoDTO);

			// Si se firma el documento, lo recupero firmado
			if (PortafirmasConstants.ESTADO_FIRMADO.equals(estado)) {
				String path = urlPortafirmas + "informeFirma/?docHASH="+hashDoc+"&petHASH="+hashPet;
				LOG.error("Recuperamos PDF firmado: " + path);
				if (path != null) {
					BDDocumentalDTO bdDocIda = bdDocumentalBO.findById(portafirmasDTO.getBdDocumentalDTO().getCoBDDocumental());

					URL urlDoc = new URL(path);
					InputStream inStream = urlDoc.openStream();
					byte[] pdf = IOUtils.toByteArray(inStream);

					String nombreFicheroFirmado = Impresion.soloCaracteresSeguros("firmado_"+bdDocIda.getNombre(), true);
					File targetFile = new File(rutaAdjuntos + nombreFicheroFirmado);
					OutputStream outStream = new FileOutputStream(targetFile);
					outStream.write(pdf);

					inStream.close();
					outStream.close();

					Long coBDDocumentalGrupoVuelta = guardarDocumentacionBO.execute(bdDocIda.getBdDocumentalGrupoDTO().getCoBDDocumentalGrupo(), portafirmasDTO.getAsunto(), nombreFicheroFirmado, null, acmUsuarioEjecucionDTO.getCoAcmUsuario());
					LOG.error("PDF firmado asociado al grupo de BDDoc: " + coBDDocumentalGrupoVuelta.toString());

					BDDocumentalDTO bdDocumentalDTOFirmado = PortafirmasService.obtenerBDDocumentalDTO(coBDDocumentalGrupoVuelta, nombreFicheroFirmado);
					portafirmasDTO.setBdDocumentalFirmadoDTO(bdDocumentalDTOFirmado);
					portafirmasBO.save(portafirmasDTO);
				}
			}
			
			// Invocamos el tramitador según el estado
			if (Arrays.asList(PortafirmasConstants.ESTADOS_INVOCAR_TRAMITADOR).contains(estado)) {
				List<String> parametros = new ArrayList<String>();
				parametros.add(BatchConstants.CO_PROCESO_FINAL_PORTAFIRMA);
				parametros.add(null);
				parametros.add(null);
				parametros.add(portafirmasDTO.getExpedienteDTO().getCoExpediente().toString());
				parametros.add(null);
				parametros.add(null);
				parametros.add(null);
				parametros.add(null);
				parametros.add(null);
				parametros.add(null);
				parametros.add(null);
				parametros.add("Petición con id " + portafirmasDTO.getCoPortafirmas() + " con estado " + estado + ".");
				parametros.add(null);
				Batch.lanzar(BatchConstants.CO_PROCESO_FINAL_PORTAFIRMA, acmUsuarioEjecucionDTO.getCoAcmUsuario(), parametros, null, new AccesoPlantillaVO());
			}

			getServletResponse().setContentType("text/xml;charset=UTF-8");
			LOG.error("Finaliza WebservicePortafirmasAction");
			escribirCorrecto();
		} catch (NumberFormatException e) {
			LOG.error(e.getMessage(), e);
			escribirError(e.getMessage());
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			escribirError(e.getMensaje());
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e);
			escribirError(e.getMessage());
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			escribirError(e.getMessage());
		} catch (RemoteException e) {
			LOG.error(e.getMessage(), e);
			escribirError(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			escribirError(e.getMessage());
		} catch (InterruptedException e) {
			LOG.error(e.getMessage(), e);
			escribirError(e.getMessage());
		}

		return "webservice";
	}

	private String obtenerUsuarioAperturaExpedienteOrigen(Long coExpedienteOrigen) {
		String usuario;
		ExpedienteSeguimientoDTO segDTO = null;
		try {
			segDTO = tramitadorBO.getExpedienteSeguimientoAperturaDTO(coExpedienteOrigen);
		} catch (GadirServiceException e) {
			LOG.error("Error al obtener seguimiento de apertura del expediente origen.", e);
		}
		if (segDTO == null || Utilidades.isEmpty(segDTO.getCoUsuarioActualizacion())) {
			usuario = Planificador.CO_USUARIO_PLANIFICADOR;
		} else {
			usuario = segDTO.getCoUsuarioActualizacion();
		}
		return usuario;
	}

	private void escribirCorrecto() {
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xmlRespuesta+="<ok/>";
	}

	private void escribirError(String error) {
		getServletResponse().setStatus(500);
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xmlRespuesta+="<error>" + error + "</error>";
	}
	
	
	public String getXmlRespuesta() {
		return xmlRespuesta;
	}

	public void setXmlRespuesta(String xmlRespuesta) {
		this.xmlRespuesta = xmlRespuesta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PortafirmasBO getPortafirmasBO() {
		return portafirmasBO;
	}

	public void setPortafirmasSeguimientoBO(PortafirmasSeguimientoBO portafirmasSeguimientoBO) {
		this.portafirmasSeguimientoBO = portafirmasSeguimientoBO;
	}

	public PortafirmasSeguimientoBO getPortafirmasSeguimientoBO() {
		return portafirmasSeguimientoBO;
	}

	public void setPortafirmasBO(PortafirmasBO portafirmasBO) {
		this.portafirmasBO = portafirmasBO;
	}

	public static Log getLog() {
		return LOG;
	}

	public static String getUrlPortafirmas() {
		return urlPortafirmas;
	}

	public static void setUrlPortafirmas(String urlPortafirmas) {
		WebservicePortafirmasAction.urlPortafirmas = urlPortafirmas;
	}

	public static String getRutaAdjuntos() {
		return rutaAdjuntos;
	}

	public static void setRutaAdjuntos(String rutaAdjuntos) {
		WebservicePortafirmasAction.rutaAdjuntos = rutaAdjuntos;
	}

	public GuardarDocumentacionBO getGuardarDocumentacionBO() {
		return guardarDocumentacionBO;
	}

	public void setGuardarDocumentacionBO(
			GuardarDocumentacionBO guardarDocumentacionBO) {
		this.guardarDocumentacionBO = guardarDocumentacionBO;
	}

	public BDDocumentalBO getBdDocumentalBO() {
		return bdDocumentalBO;
	}

	public void setBdDocumentalBO(BDDocumentalBO bdDocumentalBO) {
		this.bdDocumentalBO = bdDocumentalBO;
	}

	public AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}

	public void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		this.acmUsuarioBO = acmUsuarioBO;
	}

	public TramitadorBO getTramitadorBO() {
		return tramitadorBO;
	}

	public void setTramitadorBO(TramitadorBO tramitadorBO) {
		this.tramitadorBO = tramitadorBO;
	}

}
