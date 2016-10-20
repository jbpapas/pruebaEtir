package es.dipucadiz.etir.comun.utilidades;

import java.net.MalformedURLException;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.BDDocumentalBO;
import es.dipucadiz.etir.comun.bo.PortafirmasBO;
import es.dipucadiz.etir.comun.bo.PortafirmasSeguimientoBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.constants.PortafirmasConstants;
import es.dipucadiz.etir.comun.dto.BDDocumentalDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.ExpedienteDTO;
import es.dipucadiz.etir.comun.dto.PortafirmasDTO;
import es.dipucadiz.etir.comun.dto.PortafirmasSeguimientoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.gdtel.clientesws.pfirmav2CXFClient.ClientManager;
import es.gdtel.clientesws.pfirmav2CXFClient.client.modify.service.ModifyService;
import es.gdtel.clientesws.pfirmav2CXFClient.client.query.service.QueryService;
import es.gdtel.clientesws.pfirmav2CXFClient.exception.PfirmaException;
import es.gdtel.clientesws.pfirmav2CXFClient.type.Action;
import es.gdtel.clientesws.pfirmav2CXFClient.type.ActionList;
import es.gdtel.clientesws.pfirmav2CXFClient.type.Document;
import es.gdtel.clientesws.pfirmav2CXFClient.type.DocumentType;
import es.gdtel.clientesws.pfirmav2CXFClient.type.DocumentTypeList;
import es.gdtel.clientesws.pfirmav2CXFClient.type.RemitterList;
import es.gdtel.clientesws.pfirmav2CXFClient.type.Request;
import es.gdtel.clientesws.pfirmav2CXFClient.type.SignLine;
import es.gdtel.clientesws.pfirmav2CXFClient.type.SignLineList;
import es.gdtel.clientesws.pfirmav2CXFClient.type.Signer;
import es.gdtel.clientesws.pfirmav2CXFClient.type.SignerList;
import es.gdtel.clientesws.pfirmav2CXFClient.type.State;
import es.gdtel.clientesws.pfirmav2CXFClient.type.UserList;
import es.gdtel.clientesws.pfirmav2CXFClient.util.Constants;


public class PortafirmasService {
	private static final Log LOG = LogFactory.getLog(PortafirmasService.class);

	private static PortafirmasBO portafirmasBO;
	private static PortafirmasSeguimientoBO portafirmasSeguimientoBO;
	private static BDDocumentalBO bdDocumentalBO;
	private static AcmUsuarioBO acmUsuarioBO;

	private static String urlPortafirmas = GadirConfig.leerParametro("url.ws.portafirmas");
	private static String ipRetorno = GadirConfig.leerParametro("ip.retorno.portafirmas");

	public static void nuevo(Long coBDDocumentalGrupo, String nombrePdf, Long coExpediente) {
		try {
			BDDocumentalDTO bdDocumentalDTO = PortafirmasService.obtenerBDDocumentalDTO(coBDDocumentalGrupo, nombrePdf);
			PortafirmasDTO portafirmasDTO = new PortafirmasDTO();
			portafirmasDTO.setBdDocumentalDTO(bdDocumentalDTO);
			portafirmasDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
			ExpedienteDTO expedienteOrigenDTO = new ExpedienteDTO();
			expedienteOrigenDTO.setCoExpediente(coExpediente);
			portafirmasDTO.setExpedienteOrigenDTO(expedienteOrigenDTO);
			portafirmasDTO.setFhActualizacion(Utilidades.getDateActual());
			portafirmasBO.save(portafirmasDTO);
			
			PortafirmasSeguimientoDTO portafirmasSeguimientoDTO = new PortafirmasSeguimientoDTO();
			portafirmasSeguimientoDTO.setPortafirmasDTO(portafirmasDTO);
			portafirmasSeguimientoDTO.setCoUsuarioActualizacion(portafirmasDTO.getCoUsuarioActualizacion());
			portafirmasSeguimientoDTO.setFhActualizacion(portafirmasDTO.getFhActualizacion());
			portafirmasSeguimientoDTO.setFhEstado(portafirmasDTO.getFhActualizacion());
			portafirmasSeguimientoDTO.setEstado(PortafirmasConstants.ESTADO_GENERADO);
			portafirmasSeguimientoBO.save(portafirmasSeguimientoDTO);
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
		}
	}

	public static boolean envio(final Long coPortafirmas) {
		boolean result;
		try {
			// Recuperar los datos de envío a Portafirmas
			PortafirmasDTO portafirmasDTO = portafirmasBO.findByIdInitialized(coPortafirmas, new String[] {"clienteSolicitanteDTO", "bdDocumentalDTO", "expedienteDTO", "expedienteOrigenDTO"});
			ClienteDTO solicitanteDTO = portafirmasDTO.getClienteSolicitanteDTO();
			BDDocumentalDTO bdDocumentalDTO = portafirmasDTO.getBdDocumentalDTO();
			ExpedienteDTO expedienteDTO = portafirmasDTO.getExpedienteDTO();
			ExpedienteDTO expedienteOrigenDTO = portafirmasDTO.getExpedienteOrigenDTO();
			
			// Obtención de clientes
			ClientManager cm = new ClientManager();
			QueryService queryServiceClient = cm.getQueryServiceClient(urlPortafirmas + "servicesv2/QueryService?wsdl");
			ModifyService modifyServiceClient = cm.getModifyServiceClient(urlPortafirmas + "servicesv2/ModifyService?wsdl");
			
			// Definir remitente
			UserList userRemitentes = null;
			try {
				userRemitentes = queryServiceClient.queryUsers(solicitanteDTO.getIdentificador());
			} catch (SOAPFaultException sfe) {
				LOG.warn(sfe.getMessage(), sfe);
			}
			if (!userValido(userRemitentes)) {
				registrarSeguimientoError(portafirmasDTO, solicitanteDTO.getIdentificador(), PortafirmasConstants.ESTADO_ERROR, "Error registrando usuario " + solicitanteDTO.getIdentificador() + " como remitente en Portafirmas.");
				throw new GadirServiceException("Error al definir " + solicitanteDTO.getIdentificador() + " como remitente.");
			}
			RemitterList remitterList = new RemitterList();
			remitterList.getUser().add(userRemitentes.getUser().get(0));
			
			// Definir firmantes y crear lineas de firma y de visto bueno
			SignLine signLineFi = new SignLine();
			SignLine signLineVb = new SignLine();
			SignerList signerListFi = new SignerList();
			SignerList signerListVb = new SignerList();
			if (portafirmasDTO.getFirmanteFijo() != null) {
				signerListFi.getSigner().add(obtenerFirmante(portafirmasDTO, portafirmasDTO.getFirmanteFijo(), "firmante fijo", queryServiceClient));
			}
			if (portafirmasDTO.getFirmanteJefe() != null) {
				signerListFi.getSigner().add(obtenerFirmante(portafirmasDTO, portafirmasDTO.getFirmanteJefe(), "firmante jefe", queryServiceClient));
			}
			if (portafirmasDTO.getFirmanteDirector() != null) {
				signerListFi.getSigner().add(obtenerFirmante(portafirmasDTO, portafirmasDTO.getFirmanteDirector(), "firmante director", queryServiceClient));
			}
			if (portafirmasDTO.getVistoBueno() != null) {
				signerListVb.getSigner().add(obtenerFirmante(portafirmasDTO, portafirmasDTO.getVistoBueno(), "visto bueno", queryServiceClient));
			}
			signLineFi.setSignerList(signerListFi);
			signLineVb.setSignerList(signerListVb);
			signLineFi.setType(Constants.SIGN_TYPE);
			signLineVb.setType(Constants.PASS_TYPE);
			SignLineList signLineList = new SignLineList();
			signLineList.getSignLine().add(signLineVb);
			signLineList.getSignLine().add(signLineFi);
			
			// Obtener el tipo de documento
			DocumentTypeList docTypeList = queryServiceClient.queryDocumentTypes("GENERICO");
			DocumentType docType = docTypeList.getDocumentType().get(0);
			
			// Definir acciones a ejecutar sobre cambios de estados
			String accionWebPf = "http://" + ipRetorno + "/etir/webservicePortafirmas.action?" +
				"id=" + portafirmasDTO.getCoPortafirmas() + "&" +
				"rue=" + expedienteDTO.getCodigoDescripcion().replace(' ', '-') + "&" +
				"rueOrigen=" + expedienteOrigenDTO.getCodigoDescripcion().replace(' ', '-') + "&" +
				"HASHPET&EHASH&EDNI&ESTADO";
			Action actionFirmado = new Action();
			actionFirmado.setType("WEB");
			actionFirmado.setAction(accionWebPf);
			State stateFirmado = new State();
			stateFirmado.setIdentifier(PortafirmasConstants.ESTADO_FIRMADO);
			actionFirmado.setState(stateFirmado);

			Action actionVb = new Action();
			actionVb.setType("WEB");
			actionVb.setAction(accionWebPf);
			State stateVb = new State();
			stateVb.setIdentifier(PortafirmasConstants.ESTADO_VISTO_BUENO);
			actionVb.setState(stateVb);

			Action actionDevuelto = new Action();
			actionDevuelto.setType("WEB");
			actionDevuelto.setAction(accionWebPf);
			State stateDevuelto = new State();
			stateDevuelto.setIdentifier(PortafirmasConstants.ESTADO_DEVUELTO);
			actionDevuelto.setState(stateDevuelto);

			Action actionRechazado = new Action();
			actionRechazado.setType("WEB");
			actionRechazado.setAction(accionWebPf);
			State stateRechazado = new State();
			stateRechazado.setIdentifier(PortafirmasConstants.ESTADO_RECHAZADO);
			actionRechazado.setState(stateRechazado);

			// Crear la lista de acciones
			ActionList actionList = new ActionList();
			actionList.getAction().add(actionFirmado);
			actionList.getAction().add(actionVb);
			actionList.getAction().add(actionDevuelto);
			actionList.getAction().add(actionRechazado);
			
			// Crear el objeto de petición
			Request req = new Request();
			req.setApplication("PFIRMA");
//			req.setDocumentList(docList);
			req.setReference(expedienteDTO.getCodigoDescripcion().replace(" ", ""));
			req.setRemitterList(remitterList);
			req.setSignLineList(signLineList);
			req.setSignType(Constants.SIGN_TYPE_CASCADE);
			req.setSubject(portafirmasDTO.getAsunto());
			if (expedienteOrigenDTO != null) {
				req.setText("Expediente origen en eTir: " + expedienteOrigenDTO.getCodigoDescripcion());
			}
			req.setActionList(actionList);
			
			// Crear la petición
			String requestHash = modifyServiceClient.createRequest(req);

			// Crear el documento para firmar
			Document doc = new Document();
			doc.setSign(true);
			doc.setDocumentType(docType);
			doc.setMime("application/pdf");
			doc.setName(bdDocumentalDTO.getNombre());
			DataSource ds = new BDDocumentalDataSource(bdDocumentalDTO);
			DataHandler dh = new DataHandler(ds);
			doc.setContent(dh);
			String docHash = modifyServiceClient.insertDocument(requestHash, doc);

			// Grabar petición en Oracle
			portafirmasDTO.setHashpet(requestHash); // De la petición
			portafirmasDTO.setEhash(docHash); // Del documento
			portafirmasBO.save(portafirmasDTO);
			PortafirmasSeguimientoDTO portafirmasSeguimientoDTO = new PortafirmasSeguimientoDTO();
			portafirmasSeguimientoDTO.setPortafirmasDTO(portafirmasDTO);
			portafirmasSeguimientoDTO.setEstado(PortafirmasConstants.ESTADO_ENVIADO);
			portafirmasSeguimientoDTO.setFhEstado(Utilidades.getDateActual());
			portafirmasSeguimientoDTO.setObservaciones(null);
			portafirmasSeguimientoDTO.setFhActualizacion(Utilidades.getDateActual());
			portafirmasSeguimientoDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
			portafirmasSeguimientoBO.save(portafirmasSeguimientoDTO);

			// Enviar la petición
			modifyServiceClient.sendRequest(requestHash);
			result = true;
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			result = false;
		} catch (MalformedURLException e) {
			LOG.error("Error en URL de WS de portafirmas", e);
			result = false;
		} catch (PfirmaException e) {
			LOG.error(e.getMessage(), e);
			result = false;
		}
		return result;
	}

	private static void registrarSeguimientoError(PortafirmasDTO portafirmasDTO, String dni, String estado, String observaciones) throws GadirServiceException {
		PortafirmasSeguimientoDTO portafirmasSeguimientoDTO = new PortafirmasSeguimientoDTO();
		portafirmasSeguimientoDTO.setPortafirmasDTO(portafirmasDTO);
		portafirmasSeguimientoDTO.setEstado(estado);
		portafirmasSeguimientoDTO.setFhEstado(Utilidades.getDateActual());
		portafirmasSeguimientoDTO.setObservaciones(observaciones);
		portafirmasSeguimientoDTO.setFhActualizacion(Utilidades.getDateActual());
		portafirmasSeguimientoDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
		portafirmasSeguimientoDTO.setDni(dni);
		portafirmasSeguimientoBO.save(portafirmasSeguimientoDTO);
	}

	private static Signer obtenerFirmante(PortafirmasDTO portafirmasDTO, String firmante, String descripcion, QueryService queryServiceClient) throws GadirServiceException, PfirmaException {
		UserList userList = null;
		try {
			userList = queryServiceClient.queryUsers(firmante);
		} catch (SOAPFaultException sfe) {
			LOG.warn(sfe.getMessage() + " (" + firmante + ")", sfe);
		}
		if (!userValido(userList)) {
			registrarSeguimientoError(portafirmasDTO, firmante, PortafirmasConstants.ESTADO_ERROR, "Error registrando usuario " + firmante + " como " + descripcion + " en Portafirmas.");
			throw new GadirServiceException("Error al definir " + firmante + " como " + descripcion);
		}
		Signer signer = new Signer();
		signer.setUserJob(userList.getUser().get(0));
		return signer;
	}

	private static boolean userValido(UserList userRemitentes) {
		return userRemitentes != null && userRemitentes.getUser() != null && userRemitentes.getUser().size() > 0;
	}

	public static BDDocumentalDTO obtenerBDDocumentalDTO(Long coBDDocumentalGrupo, String nombreFichero) throws GadirServiceException {
		// Obtener el códgigo de BDDoc
		DetachedCriteria criteria = DetachedCriteria.forClass(BDDocumentalDTO.class);
		criteria.add(Restrictions.eq("bdDocumentalGrupoDTO.coBDDocumentalGrupo", coBDDocumentalGrupo));
		criteria.add(Restrictions.eq("nombre", nombreFichero));
		criteria.addOrder(Order.desc("coBDDocumental")); // El que acabamos de insertar tendrá el valor mas alto del grupo.
		List<BDDocumentalDTO> bdDocumentalDTOs = bdDocumentalBO.findByCriteria(criteria);
		BDDocumentalDTO bdDocumentalDTOFirmado = bdDocumentalDTOs.get(0);
		return bdDocumentalDTOFirmado;
	}

	public static PortafirmasBO getPortafirmasBO() {
		return portafirmasBO;
	}

	public void setPortafirmasBO(PortafirmasBO portafirmasBO) {
		PortafirmasService.portafirmasBO = portafirmasBO;
	}

	public static PortafirmasSeguimientoBO getPortafirmasSeguimientoBO() {
		return portafirmasSeguimientoBO;
	}

	public void setPortafirmasSeguimientoBO(PortafirmasSeguimientoBO portafirmasSeguimientoBO) {
		PortafirmasService.portafirmasSeguimientoBO = portafirmasSeguimientoBO;
	}

	public static String getIpRetorno() {
		return ipRetorno;
	}

	public static void setIpRetorno(String ipRetorno) {
		PortafirmasService.ipRetorno = ipRetorno;
	}

	public static Log getLog() {
		return LOG;
	}

	public static String getUrlPortafirmas() {
		return urlPortafirmas;
	}

	public static void setUrlPortafirmas(String urlPortafirmas) {
		PortafirmasService.urlPortafirmas = urlPortafirmas;
	}

	public static BDDocumentalBO getBdDocumentalBO() {
		return bdDocumentalBO;
	}

	public void setBdDocumentalBO(BDDocumentalBO bdDocumentalBO) {
		PortafirmasService.bdDocumentalBO = bdDocumentalBO;
	}

	public static AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}

	public void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		PortafirmasService.acmUsuarioBO = acmUsuarioBO;
	}


}
