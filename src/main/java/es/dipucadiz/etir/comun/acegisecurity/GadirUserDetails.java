package es.dipucadiz.etir.comun.acegisecurity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;

import es.dipucadiz.etir.comun.dto.AcmBotonDTO;
import es.dipucadiz.etir.comun.dto.AcmMenuDTO;
import es.dipucadiz.etir.comun.dto.AcmPerfilDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioNotificacionDTO;
import es.dipucadiz.etir.comun.dto.CodigoTerritorialDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.utilidades.PilaVolver;
import es.dipucadiz.etir.comun.vo.AyudaCasillaVO;
import es.dipucadiz.etir.comun.vo.OpcionIncidenciaVO;

public class GadirUserDetails extends User implements UserDetails {

	private static final long serialVersionUID = -6244198236190066742L;

	protected String nombre;
	protected CodigoTerritorialDTO codigoTerritorial;
	protected String codigoTerritorialGenerico;
	protected List<String> codigosTerritorialesExtra = new ArrayList<String>();
	protected List<String> codtersCompatsConsulta = new ArrayList<String>();
	protected List<String> codtersCompatsEdicion = new ArrayList<String>();
	protected List<OpcionIncidenciaVO> opcionesImpresion;
	protected String impresora;
	//protected String colaEjecucion;
	protected String cargo;
	protected String carpetaAcceso;
	protected AcmPerfilDTO perfil;
	protected Boolean conAccesibilidad = false;
	protected List<MunicipioDTO> municipiosAccesibles = new ArrayList<MunicipioDTO>();
	protected List<AcmMenuDTO> acmMenusAccesibles = new ArrayList<AcmMenuDTO>();
	protected List<AcmBotonDTO> acmBotonsAccesibles = new ArrayList<AcmBotonDTO>();
	protected String menuHtml;
	protected Map<String, String> preferencias;
	private Map<String, PilaVolver> pilasVolver = new HashMap<String, PilaVolver>();
	private Map<String, Integer> numeracionVentanas = new HashMap<String, Integer>();
	protected Date TS;
	protected Map<String, Map<String, Object>> objetos = new HashMap<String, Map<String, Object>>();
	protected Map<String, Object> objetosUnicos = new HashMap<String, Object>();
	protected Map<String, String> coAcmMenuActuals = new HashMap<String, String>();
	protected Map<String, String> coProcesoActuals = new HashMap<String, String>();
	private String coProcesoActual = "";
	private String coAcmMenuActual = "";
	protected String email;
	protected String escudo;
	protected UnidadAdministrativaDTO unidadAdministrativa;
	protected int numResultadosPaginacion;
	protected AyudaCasillaVO ayudaCasillaVO;
	protected boolean accesoTotalConsulta = false;
	protected boolean accesoTotalEdicion = false;
	List<AcmUsuarioNotificacionDTO> usuarioNotificacionesTipo3T = new ArrayList<AcmUsuarioNotificacionDTO>();
	List<AcmUsuarioNotificacionDTO> usuarioNotificacionesTipo2 = new ArrayList<AcmUsuarioNotificacionDTO>();
	List<AcmUsuarioNotificacionDTO> usuarioNotificacionesTipo1 = new ArrayList<AcmUsuarioNotificacionDTO>();
	protected int UsuarioNumNotificacionesTipo1;

	public GadirUserDetails(final UserDetails userDetails) throws IllegalArgumentException {

		super(userDetails.getUsername(), userDetails.getPassword(), userDetails.isEnabled(), userDetails.isAccountNonExpired(), userDetails.isCredentialsNonExpired(), userDetails.isAccountNonLocked(),
				userDetails.getAuthorities());
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CodigoTerritorialDTO getCodigoTerritorial() {
		return codigoTerritorial;
	}

	public void setCodigoTerritorial(CodigoTerritorialDTO codigoTerritorial) {
		this.codigoTerritorial = codigoTerritorial;
	}

	public String getCodigoTerritorialGenerico() {
		return codigoTerritorialGenerico;
	}

	public void setCodigoTerritorialGenerico(String codigoTerritorialGenerico) {
		this.codigoTerritorialGenerico = codigoTerritorialGenerico;
	}

	public List<MunicipioDTO> getMunicipiosAccesibles() {
		return municipiosAccesibles;
	}

	public void setMunicipiosAccesibles(List<MunicipioDTO> municipiosAccesibles) {
		this.municipiosAccesibles = municipiosAccesibles;
	}

	public String getImpresora() {
		return impresora;
	}

	public void setImpresora(String impresora) {
		this.impresora = impresora;
	}

	/*public String getColaEjecucion() {
		return colaEjecucion;
	}
	
	public void setColaEjecucion(String colaEjecucion) {
		this.colaEjecucion = colaEjecucion;
	}*/

	public Boolean isConAccesibilidad() {
		return conAccesibilidad;
	}

	public void setConAccesibilidad(Boolean conAccesibilidad) {
		this.conAccesibilidad = conAccesibilidad;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCarpetaAcceso() {
		return carpetaAcceso;
	}

	public void setCarpetaAcceso(String carpetaAcceso) {
		this.carpetaAcceso = carpetaAcceso;
	}

	public List<AcmMenuDTO> getAcmMenusAccesibles() {
		return acmMenusAccesibles;
	}

	public void setAcmMenusAccesibles(List<AcmMenuDTO> acmMenusAccesibles) {
		this.acmMenusAccesibles = acmMenusAccesibles;
	}

	public List<AcmBotonDTO> getAcmBotonsAccesibles() {
		return acmBotonsAccesibles;
	}

	public void setAcmBotonsAccesibles(List<AcmBotonDTO> acmBotonsAccesibles) {
		this.acmBotonsAccesibles = acmBotonsAccesibles;
	}

	public String getMenuHtml() {
		return menuHtml;
	}

	public void setMenuHtml(String menuHtml) {
		this.menuHtml = menuHtml;
	}

	public Map<String, String> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(Map<String, String> preferencias) {
		this.preferencias = preferencias;
	}

	public AcmPerfilDTO getPerfil() {
		return perfil;
	}

	public void setPerfil(AcmPerfilDTO perfil) {
		this.perfil = perfil;
	}

	public Date getTS() {
		return TS;
	}

	public void setTS(Date tS) {
		TS = tS;
	}

	public Map<String, Map<String, Object>> getObjetos() {
		return objetos;
	}

	public void setObjetos(Map<String, Map<String, Object>> objetos) {
		this.objetos = objetos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEscudo() {
		return escudo;
	}

	public void setEscudo(String escudo) {
		this.escudo = escudo;
	}

	public UnidadAdministrativaDTO getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(UnidadAdministrativaDTO unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	public int getNumResultadosPaginacion() {
		return numResultadosPaginacion;
	}

	public void setNumResultadosPaginacion(int numResultadosPaginacion) {
		this.numResultadosPaginacion = numResultadosPaginacion;
	}

	public AyudaCasillaVO getAyudaCasillaVO() {
		return ayudaCasillaVO;
	}

	public void setAyudaCasillaVO(AyudaCasillaVO ayudaCasillaVO) {
		this.ayudaCasillaVO = ayudaCasillaVO;
	}

	public List<String> getCodigosTerritorialesExtra() {
		return codigosTerritorialesExtra;
	}

	public void setCodigosTerritorialesExtra(List<String> codigosTerritorialesExtra) {
		this.codigosTerritorialesExtra = codigosTerritorialesExtra;
	}

	public List<OpcionIncidenciaVO> getOpcionesImpresion() {
		return opcionesImpresion;
	}

	public void setOpcionesImpresion(List<OpcionIncidenciaVO> opcionesImpresion) {
		this.opcionesImpresion = opcionesImpresion;
	}

	public List<String> getCodtersCompatsConsulta() {
		return codtersCompatsConsulta;
	}

	public void setCodtersCompatsConsulta(List<String> codtersCompatsConsulta) {
		this.codtersCompatsConsulta = codtersCompatsConsulta;
	}

	public List<String> getCodtersCompatsEdicion() {
		return codtersCompatsEdicion;
	}

	public void setCodtersCompatsEdicion(List<String> codtersCompatsEdicion) {
		this.codtersCompatsEdicion = codtersCompatsEdicion;
	}

	public boolean isAccesoTotalConsulta() {
		return accesoTotalConsulta;
	}

	public void setAccesoTotalConsulta(boolean accesoTotalConsulta) {
		this.accesoTotalConsulta = accesoTotalConsulta;
	}

	public boolean isAccesoTotalEdicion() {
		return accesoTotalEdicion;
	}

	public void setAccesoTotalEdicion(boolean accesoTotalEdicion) {
		this.accesoTotalEdicion = accesoTotalEdicion;
	}

	public List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo3T() {
		return usuarioNotificacionesTipo3T;
	}

	public void setUsuarioNotificacionesTipo3T(List<AcmUsuarioNotificacionDTO> usuarioNotificacionesTipo3T) {
		this.usuarioNotificacionesTipo3T = usuarioNotificacionesTipo3T;
	}

	public List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo2() {
		return usuarioNotificacionesTipo2;
	}

	public void setUsuarioNotificacionesTipo2(List<AcmUsuarioNotificacionDTO> usuarioNotificacionesTipo2) {
		this.usuarioNotificacionesTipo2 = usuarioNotificacionesTipo2;
	}

	public List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo1() {
		return usuarioNotificacionesTipo1;
	}

	public void setUsuarioNotificacionesTipo1(List<AcmUsuarioNotificacionDTO> usuarioNotificacionesTipo1) {
		this.usuarioNotificacionesTipo1 = usuarioNotificacionesTipo1;
	}

	public int getUsuarioNumNotificacionesTipo1() {
		return UsuarioNumNotificacionesTipo1;
	}

	public void setUsuarioNumNotificacionesTipo1(int usuarioNumNotificacionesTipo1) {
		UsuarioNumNotificacionesTipo1 = usuarioNumNotificacionesTipo1;
	}

	public Map<String, PilaVolver> getPilasVolver() {
		return pilasVolver;
	}

	public void setPilasVolver(Map<String, PilaVolver> pilasVolver) {
		this.pilasVolver = pilasVolver;
	}

	public Map<String, Integer> getNumeracionVentanas() {
		return numeracionVentanas;
	}

	public void setNumeracionVentanas(Map<String, Integer> numeracionVentanas) {
		this.numeracionVentanas = numeracionVentanas;
	}

	public Map<String, String> getCoAcmMenuActuals() {
		return coAcmMenuActuals;
	}

	public void setCoAcmMenuActuals(Map<String, String> coAcmMenuActuals) {
		this.coAcmMenuActuals = coAcmMenuActuals;
	}

	public Map<String, String> getCoProcesoActuals() {
		return coProcesoActuals;
	}

	public void setCoProcesoActuals(Map<String, String> coProcesoActuals) {
		this.coProcesoActuals = coProcesoActuals;
	}

	public String getCoProcesoActual() {
		return coProcesoActual;
	}

	public void setCoProcesoActual(String coProcesoActual) {
		this.coProcesoActual = coProcesoActual;
	}

	public String getCoAcmMenuActual() {
		return coAcmMenuActual;
	}

	public void setCoAcmMenuActual(String coAcmMenuActual) {
		this.coAcmMenuActual = coAcmMenuActual;
	}

	public Map<String, Object> getObjetosUnicos() {
		return objetosUnicos;
	}

	public void setObjetosUnicos(Map<String, Object> objetosUnicos) {
		this.objetosUnicos = objetosUnicos;
	}

}
