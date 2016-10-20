package es.dipucadiz.etir.comun.acegisecurity;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.intercept.web.FilterInvocation;
import org.acegisecurity.vote.RoleVoter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.action.AbstractGadirBaseAction;
import es.dipucadiz.etir.comun.dto.AcmBotonDTO;
import es.dipucadiz.etir.comun.dto.AcmMenuDTO;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.listener.SessionCounterListener;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.ProcesoUtil;

public class GadirVoter extends RoleVoter
{
	private static final Log LOG = LogFactory.getLog(GadirVoter.class);

	public int vote(Authentication authentication, Object object, ConfigAttributeDefinition config) {
		boolean activeSessionTouched=false;

		int resultado= ACCESS_DENIED;
		if (authentication!=null){
			resultado=super.vote(authentication, object, config);
		}

		if (resultado!=ACCESS_DENIED){
			

			try{
				//GadirUserDetails userDetails = (GadirUserDetails)authentication.getPrincipal();

				FilterInvocation fi = (FilterInvocation)object;
				
				String url = fi.getRequestUrl();
				String tabName= getTabName(fi.getHttpRequest());
				
				if((url.startsWith("/G") || url.startsWith("/sb")) && !url.toLowerCase().contains("ajax")){

					if("false".equals(fi.getHttpRequest().getParameter("consulta"))){
						resultado=ACCESS_DENIED;
						throw new Exception("El parametro consulta es de uso interno");
					}


					String action=fi.getHttpRequest().getServletPath();

					if (action.startsWith("/")) action=action.substring(1);

					ProcesoDTO procesoDTO = ProcesoUtil.getProcesoByAction(action);



					if (procesoDTO!=null){
						String paramVentanaBotonLateral="";
						try{
							paramVentanaBotonLateral=fi.getHttpRequest().getParameter("ventanaBotonLateral");
							if (paramVentanaBotonLateral==null || paramVentanaBotonLateral.equals("")){
								paramVentanaBotonLateral=(String)fi.getHttpRequest().getAttribute("ventanaBotonLateral");
							}
						}catch(Exception e){
						}
						
						boolean ventanaBotonLateral = (paramVentanaBotonLateral!=null && !paramVentanaBotonLateral.equals(""));
						if (!ventanaBotonLateral){
							DatosSesion.setCoProcesoActual(tabName, procesoDTO.getCoProceso());
							SessionCounterListener.modifySessionCoProceso(fi.getHttpRequest().getRequestedSessionId(), procesoDTO.getCoProceso());
							activeSessionTouched=true;
						}

						List<AcmMenuDTO> acmMenuDTOs = ProcesoUtil.getAcmMenusByProceso(procesoDTO);

						if (acmMenuDTOs!=null && acmMenuDTOs.size()>0){
							boolean puedo = false;
							for (AcmMenuDTO acmMenuDTO : acmMenuDTOs) {
								// ¿Está en mi perfil?
								puedo=DatosSesion.getAcmMenusAccesibles().contains(acmMenuDTO);
								if (puedo) {
									if (!ventanaBotonLateral){
										DatosSesion.setCoAcmMenuActual(tabName, acmMenuDTO.getCoAcmMenu()); // Marcar en el menú
									}
									break;
								}
							}

							if (!puedo) {
								// ¿Está en mi botonera?
								List<AcmBotonDTO> acmBotonDTOs = DatosSesion.getAcmBotonsAccesibles();
								for (AcmBotonDTO acmBotonDTO : acmBotonDTOs) {
									if (procesoDTO.getCoProceso().equals(acmBotonDTO.getProcesoDTO().getCoProceso())) {
										puedo = true;
										break;
									}
								}
							}
							
							
							if (!puedo){
								resultado=ACCESS_DENIED;
								LOG.info("#######################################################");
								LOG.info(DatosSesion.getLogin() +  " ha intentado colarse en " + fi.getRequestUrl() + ".");
								LOG.info("Se prohibe porque no tiene acceso al punto de menu " + DatosSesion.getCoAcmMenuActual(tabName) + ".");
								LOG.info("#######################################################");						
							}
						}

					}else{
						//DatosSesion.setCoProcesoActual("");
						LOG.info("#######################################################");
						LOG.info("No encuentro nada en GA_PROCESO a partir del action " + action);
						LOG.info("#######################################################");	
					}


					//				if (fi.getRequestUrl().startsWith("/M")){
					//					
					//					if (fi.getRequestUrl().startsWith("/M52730P0")){
					//						System.out.println("#######################################################");
					//						System.out.println("Soy " + userDetails.getNombre() + ", quiero acceder a " + fi.getRequestUrl() + " y deniego");
					//						System.out.println("#######################################################");
					//						resultado=ACCESS_DENIED;
					//					}else{
					//						System.out.println("#######################################################");
					//						System.out.println("Soy " + userDetails.getNombre() + ", quiero acceder a " + fi.getRequestUrl() + " y me abstengo");
					//						System.out.println("#######################################################");
					//						//return ACCESS_GRANTED;
					//					}
					//				}else{
					//					//System.out.println("#######################################################");
					//					//System.out.println("Soy " + userDetails.getNombre() + ", quiero acceder a " + fi.getRequestUrl() + " y me abstengo");
					//					//System.out.println("#######################################################");
					//					//return AccessDecisionVoter.ACCESS_ABSTAIN;
					//				}

					if (!activeSessionTouched) {
						SessionCounterListener.touchSession(fi.getHttpRequest().getRequestedSessionId());
					}
					
				}

				DatosSesion.setCoAcmMenuActualSoloDesdeJsp(DatosSesion.getCoAcmMenuActual(tabName));
				DatosSesion.setCoProcesoActualSoloDesdeJsp(DatosSesion.getCoProcesoActual(tabName));

			}catch(Exception e){
				LOG.error(e);
			}

		}

		return resultado;
	}


	protected String getTabName(HttpServletRequest request){
		String tabName="";
		Cookie[] cookies = request.getCookies();
		if (cookies!=null && cookies.length>0){
			for (int i=0;i<cookies.length;i++){
				if (AbstractGadirBaseAction.COOKIE_TAB_NAME.equals(cookies[i].getName())){
					tabName=cookies[i].getValue();
				}
			}
		}
		return tabName;
	}

}
