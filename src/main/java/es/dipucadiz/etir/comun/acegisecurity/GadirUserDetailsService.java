package es.dipucadiz.etir.comun.acegisecurity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.jdbc.JdbcDaoImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.ibm.icu.util.Calendar;

import es.dipucadiz.etir.comun.bo.AcmBotonBO;
import es.dipucadiz.etir.comun.bo.AcmMenuBO;
import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.AcmUsuarioCodterrBO;
import es.dipucadiz.etir.comun.bo.AcmUsuarioPreferenciaBO;
import es.dipucadiz.etir.comun.bo.CodigoTerritorialBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.AcmBotonDTO;
import es.dipucadiz.etir.comun.dto.AcmMenuDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioCodterrDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioPreferenciaDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioPreferenciaDTOId;
import es.dipucadiz.etir.comun.dto.CodigoTerritorialDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.ControlTerritorial;
import es.dipucadiz.etir.comun.utilidades.MenuUtil;
import es.dipucadiz.etir.comun.utilidades.OpcionesIncidenciaSituacionUtil;
import es.dipucadiz.etir.comun.utilidades.UsuarioNotificacionUtil;

public class GadirUserDetailsService extends JdbcDaoImpl{

	private AcmUsuarioBO acmUsuarioBO;

	private static final Log LOG = LogFactory.getLog(GadirUserDetailsService.class);

	public UserDetails loadUserByUsername(String username){

		GadirUserDetails gadirUserDetails = null;

		AcmUsuarioDTO acmUsuarioDTO = null;
		try {
			acmUsuarioDTO=acmUsuarioBO.findByIdInitialized(username);
		} catch (GadirServiceException e1) {
		
			e1.printStackTrace();
		}

		
		if (acmUsuarioDTO !=null){
			GrantedAuthority[] roles = {new GrantedAuthorityImpl("ROLE_USER")};
			// Caducidad contraseña se implementa aquí, en la llamada a User.
			Date fxCaducidad=  acmUsuarioDTO.getFxCaducidadPw();
			boolean noEstaCaducada=true;
			if( fxCaducidad!=null ){
				  Calendar calendarCaducidad = Calendar.getInstance();
				calendarCaducidad.setTime(fxCaducidad);
				Calendar ahora = Calendar.getInstance();
				if(calendarCaducidad.before(ahora)|| calendarCaducidad.equals(ahora)){
					noEstaCaducada=false;
				}
			}
				
				
				
			UserDetails userDetails=new User(acmUsuarioDTO.getCoAcmUsuario(), acmUsuarioDTO.getContrasena(), "A".equals(acmUsuarioDTO.getEstado()), true, noEstaCaducada, true, roles);
			gadirUserDetails = new GadirUserDetails(userDetails);
			
			try{
				gadirUserDetails = loadSession(gadirUserDetails);	
			} catch (GadirServiceException e) {
				e.printStackTrace();
			}
			
			
			LOG.info("Usuario " + acmUsuarioDTO.getCoAcmUsuario() + " se ha logado.");
		} else {
		 
		 	 
			LOG.info("Usuario " + username + " no se ha podido logar correctamente.");
		}
		
		return gadirUserDetails;
	}
	
	
	public static GadirUserDetails loadSession (GadirUserDetails gadirUserDetails) throws GadirServiceException {
		AcmUsuarioDTO acmUsuarioDTO = ((AcmUsuarioBO) GadirConfig.getBean("acmUsuarioBO")).findByIdInitialized(gadirUserDetails.getUsername());		
		gadirUserDetails.setCodigoTerritorial(acmUsuarioDTO.getCodigoTerritorialDTO());
		gadirUserDetails.setUnidadAdministrativa(acmUsuarioDTO.getUnidadAdministrativaDTO());
		gadirUserDetails.setCodigoTerritorialGenerico(acmUsuarioDTO.getCodigoTerritorialGenerico());
		gadirUserDetails.setConAccesibilidad(acmUsuarioDTO.getBoAccesibilidad());
		gadirUserDetails.setImpresora(acmUsuarioDTO.getImpresora());
		gadirUserDetails.setCargo(acmUsuarioDTO.getCargo());
		gadirUserDetails.setCarpetaAcceso(acmUsuarioDTO.getCarpetaAcceso());
		gadirUserDetails.setNombre(acmUsuarioDTO.getClienteDTO().getRazonSocial());
		//gadirUserDetails.setColaEjecucion("gadir");
		gadirUserDetails.setPerfil(acmUsuarioDTO.getAcmPerfilDTO());
		gadirUserDetails.setEmail(acmUsuarioDTO.getEmail());
		gadirUserDetails.setEscudo(buscarImagen(acmUsuarioDTO.getCodigoTerritorialDTO().getCoCodigoTerritorial()));
		
		//codigos territoriales extra
		AcmUsuarioCodterrBO acmUsuarioCodterrBO = ((AcmUsuarioCodterrBO) GadirConfig.getBean("acmUsuarioCodterrBO"));
		List<AcmUsuarioCodterrDTO> lista = acmUsuarioCodterrBO.findFiltered("acmUsuarioDTO.coAcmUsuario", gadirUserDetails.getUsername());
		List<String> codTerrExtra=new ArrayList<String>();
		for (AcmUsuarioCodterrDTO acmUsuarioCodterrDTO : lista){
			codTerrExtra.add(acmUsuarioCodterrDTO.getCodigoTerritorial());
		}
		gadirUserDetails.setCodigosTerritorialesExtra(codTerrExtra);
		
		CodigoTerritorialBO codigoTerritorialBO = ((CodigoTerritorialBO) GadirConfig.getBean("codigoTerritorialBO"));
		DetachedCriteria dc=DetachedCriteria.forClass(CodigoTerritorialDTO.class);
		dc.add(Restrictions.not(Restrictions.like("coCodigoTerritorial", "%*%")));
		List<CodigoTerritorialDTO> listaCodters = codigoTerritorialBO.findByCriteria(dc);
		
		
		//obtener codigos territoriales compatibles para consultas con PL
		List<String> codtersCompatibles = ControlTerritorial.getCodTersCompatibles(acmUsuarioDTO.getCodigoTerritorialGenerico(), codTerrExtra, ControlTerritorial.CONSULTA);
		gadirUserDetails.setCodtersCompatsConsulta(codtersCompatibles);
		if (codtersCompatibles.size()==listaCodters.size()){
			gadirUserDetails.setAccesoTotalConsulta(true);
		}else{
			gadirUserDetails.setAccesoTotalConsulta(false);
		}
		
		//obtener codigos territoriales compatibles para edicion con PL
		List<String> codtersCompatiblesEdicion = ControlTerritorial.getCodTersCompatibles(acmUsuarioDTO.getCodigoTerritorialGenerico(), codTerrExtra, ControlTerritorial.EDICION);
		gadirUserDetails.setCodtersCompatsEdicion(codtersCompatiblesEdicion);
		if (codtersCompatiblesEdicion.size()==listaCodters.size()){
			gadirUserDetails.setAccesoTotalEdicion(true);
		}else{
			gadirUserDetails.setAccesoTotalEdicion(false);
		}
		
		// numero de resultados por pagina en paginacion
		try {
			AcmUsuarioPreferenciaDTO acmUsuarioPreferenciaDTO = ((AcmUsuarioPreferenciaBO) GadirConfig.getBean("acmUsuarioPreferenciaBO")).findById(new AcmUsuarioPreferenciaDTOId(acmUsuarioDTO.getCoAcmUsuario(), GadirConfig.leerParametro("preferencia.numero.resultados.paginacion")));
			if(acmUsuarioPreferenciaDTO!=null){
				gadirUserDetails.setNumResultadosPaginacion(Integer.valueOf(acmUsuarioPreferenciaDTO.getValor()));
			}
		} catch (GadirServiceException e1) {
		}

		//Obtener municipos accesibles con PL
		List<MunicipioDTO> municipiosAccesibles = ControlTerritorial.getMunicipiosCodter(acmUsuarioDTO.getCodigoTerritorialGenerico());
		gadirUserDetails.setMunicipiosAccesibles(municipiosAccesibles);
		
		//Obtener botones accesibles
		List<AcmBotonDTO> acmBotonsAccesibles = new ArrayList<AcmBotonDTO>();
		try{
			acmBotonsAccesibles = ((AcmBotonBO) GadirConfig.getBean("acmBotonBO")).findByAcmPerfil(acmUsuarioDTO.getAcmPerfilDTO().getCoAcmPerfil());
		} catch (Exception e){
			e.printStackTrace();
		}
		try{
			acmBotonsAccesibles.addAll(((AcmBotonBO) GadirConfig.getBean("acmBotonBO")).findByAcmUsuario(acmUsuarioDTO.getCoAcmUsuario()));
		} catch (Exception e){
			e.printStackTrace();
		}
		gadirUserDetails.setAcmBotonsAccesibles(acmBotonsAccesibles);
		
		//Obtener items de menú accesibles
		List<AcmMenuDTO> acmMenusAccesibles = new ArrayList<AcmMenuDTO>();
		try{				
			//acmMenusAccesibles = acmMenuBO.findByAcmPerfilInitialized(acmUsuarioDTO.getAcmPerfilDTO().getCoAcmPerfil());
			
			
			if(acmUsuarioDTO.getBoAccesibilidad()){
				acmMenusAccesibles = ((AcmMenuBO) GadirConfig.getBean("acmMenuBO")).findByAcmPerfilSinOrden(acmUsuarioDTO.getAcmPerfilDTO().getCoAcmPerfil());
			}else{
				acmMenusAccesibles = ((AcmMenuBO) GadirConfig.getBean("acmMenuBO")).findByAcmPerfil(acmUsuarioDTO.getAcmPerfilDTO().getCoAcmPerfil());
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		gadirUserDetails.setAcmMenusAccesibles(acmMenusAccesibles);
		
		//Obtener codigo HTML del menú
		String menuHtml = MenuUtil.getMenuHtml(acmMenusAccesibles);
		gadirUserDetails.setMenuHtml(menuHtml);
		
//		//TODO: Obtener las incidencias de impresión accesibles para el usuario
		gadirUserDetails.setOpcionesImpresion(OpcionesIncidenciaSituacionUtil.getOpcionesImpresion(gadirUserDetails.getMunicipiosAccesibles(), gadirUserDetails.getCodigoTerritorialGenerico()));
		
		try{
			gadirUserDetails.setUsuarioNotificacionesTipo1(UsuarioNotificacionUtil.getUsuarioNotificacionesTipo1(acmUsuarioDTO.getCoAcmUsuario()));
			gadirUserDetails.setUsuarioNotificacionesTipo2(UsuarioNotificacionUtil.getUsuarioNotificacionesTipo2(acmUsuarioDTO.getCoAcmUsuario()));
			gadirUserDetails.setUsuarioNotificacionesTipo3T(UsuarioNotificacionUtil.getUsuarioNotificacionesTipo3T(acmUsuarioDTO.getCoAcmUsuario()));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return gadirUserDetails;
	}


	private static String buscarImagen(String coCodigoTerritorialDTO) {
		final String rutaTomcat = GadirConfig.leerParametro("ruta.tomcat");
		final String tipoSistema = GadirConfig.leerParametro("entorno.tipo.sistema");
		final String barra = "linux".equals(tipoSistema) ? "/" : "\\";
		String carpetaEscudos = rutaTomcat + "webapps" + barra + "etir" + barra + "image" + barra + "escudos" + barra;
		File folder = new File(carpetaEscudos);
		if (!folder.exists()) {
			carpetaEscudos = rutaTomcat + "wtpwebapps" + barra + "etir" + barra + "image" + barra + "escudos" + barra;
		}
		File file = new File(carpetaEscudos + coCodigoTerritorialDTO + ".png");
		if (!file.exists()) {
			file = new File(carpetaEscudos + coCodigoTerritorialDTO.substring(0, 2) + ".png");
			if (!file.exists()) {
				file = new File(carpetaEscudos + "default.png");
			}
		}
		return "/image/escudos/" + file.getName();
	}

	public AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}


	public void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		this.acmUsuarioBO = acmUsuarioBO;
	}

	
}
