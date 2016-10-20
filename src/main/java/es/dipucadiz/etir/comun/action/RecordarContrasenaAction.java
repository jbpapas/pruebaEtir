package es.dipucadiz.etir.comun.action;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

final public class RecordarContrasenaAction extends AbstractGadirBaseAction {

	private static final Log LOG = LogFactory.getLog(RecordarContrasenaAction.class);

	private String j_username;
	
	private AcmUsuarioBO acmUsuarioBO;
	
	public String execute() throws GadirServiceException {
		
		if (j_username==null || j_username.equals("")){
			addActionError("Introduzca un nombre de usuario");
		}
		
		
		if (!hasErrors()){
			try{
				AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findFiltered("coAcmUsuario", j_username).get(0);
				
				String EMAIL_FROM="gadir@dipucadiz.es";
				String EMAIL_PROTOCOLO = "smtp";
				String EMAIL_HOST = "neron5.dipucadiz.dom";
				String EMAIL_USER = "gadir@dipucadiz.dom";
				String EMAIL_PASSWORD = "gad09ir";
				String para=acmUsuarioDTO.getEmail();
				String asunto="eTIR: recordatorio de contraseña";
				
				String texto="Su contraseña es: " + acmUsuarioDTO.getContrasena();
				
				final Properties props = System.getProperties();
				final Session session = Session.getInstance(props, null);
				try {
					final MimeMessage msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(EMAIL_FROM));
					final InternetAddress[] address = { new InternetAddress(para) };
					msg.setRecipients(Message.RecipientType.TO, address);
					msg.setSubject(asunto);

					final MimeBodyPart bp1 = new MimeBodyPart();
					bp1.setText(texto);
		 
					

					final Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(bp1);

					msg.setContent(multipart);
		 			msg.setSentDate(new Date());

					final Transport transport = session.getTransport(EMAIL_PROTOCOLO);
					transport.connect(EMAIL_HOST, EMAIL_USER, EMAIL_PASSWORD);
					transport.sendMessage(msg, msg.getAllRecipients());
					transport.close();
					
					addActionMessage("Se le ha enviado un mail con su contraseña a la dirección " + para);
					
		 		} catch (MessagingException mex) {
					throw new GadirServiceException(mex);
				}
				
			}catch(Exception e){
				LOG.error("Error enviando mail", e);
				addActionError("Id de usuario inexistente: " + j_username);
			}
			
			
		}
		
		return INPUT;
	}

	public String getJ_username() {
		return j_username;
	}

	public void setJ_username(String jUsername) {
		j_username = jUsername;
	}

	public AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}

	public void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		this.acmUsuarioBO = acmUsuarioBO;
	}

	

}
