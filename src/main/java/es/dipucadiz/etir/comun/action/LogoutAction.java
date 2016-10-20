package es.dipucadiz.etir.comun.action;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.AcmUsuarioLogBO;
import es.dipucadiz.etir.comun.bo.impl.AcmUsuarioLogBOImpl;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.AcmUsuarioLogDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.SessionUtil;
import es.dipucadiz.etir.comun.vo.SessionUtilVO;

final public class LogoutAction extends AbstractGadirBaseAction {

	private static final long serialVersionUID = 4744825327359523705L;
	private static final Log LOG = LogFactory.getLog(LoginAction.class);
	
	public void saveLog() throws GadirServiceException {		
		
		AcmUsuarioLogDTO acmUsuarioLogDTO = new AcmUsuarioLogDTO();
		acmUsuarioLogDTO.setAccion(String.valueOf(AcmUsuarioLogBOImpl.ACCION_LOGOUT));
		acmUsuarioLogDTO.setCoAcmUsuario(DatosSesion.getLogin());
		acmUsuarioLogDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
		acmUsuarioLogDTO.setUsuarioAjeno(DatosSesion.getLogin());
		acmUsuarioLogDTO.setFhAccion(new Date());
		acmUsuarioLogDTO.setFhActualizacion(new Date());
		SessionUtilVO ipHost = SessionUtil.getIpHost(getServletRequest());
		acmUsuarioLogDTO.setIp(ipHost.getIp());
		acmUsuarioLogDTO.setTerminal(ipHost.getHost());
		acmUsuarioLogDTO.setMac(ipHost.getMac());
		AcmUsuarioLogBO acmUsuarioLogBO = ((AcmUsuarioLogBO) GadirConfig.getBean("acmUsuarioLogBO"));
			
		try {
			acmUsuarioLogBO.save(acmUsuarioLogDTO);
			getServletResponse().sendRedirect("j_acegi_logout");
		} catch (IOException e) {
			LOG.error(e);
		}
		
	}
}