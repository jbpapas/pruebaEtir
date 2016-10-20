package es.dipucadiz.etir.comun.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.ibm.icu.util.Calendar;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.ImpresoraBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.AcmMenuDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioCodterrDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.CodterrUniadmDTO;
import es.dipucadiz.etir.comun.dto.ImpresoraDTO;
import es.dipucadiz.etir.comun.dto.ProcesoDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.ProcesoUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

final public class InformacionAction extends AbstractGadirBaseAction {

	private static final long serialVersionUID = -785972905522959721L;
	private String coImpresora;
	private String impresora;
	private String codigo;
	private String nombre;
	private String unidadAdministrativa;
	private String codigoTerritorial;
	private String codigoTerritorialGen;
	private String direccionIP;
	private String proceso;
	private String entorno;
	private String baseDatos;
	private String colaEjecucion;
	private String carpetaTrabajo;
	private String puntoMenu;
	private List<ImpresoraDTO> listaImpresora;
	private AcmUsuarioBO acmUsuarioBO;
	private ImpresoraBO impresoraBO;
	private static final Log LOG = LogFactory.getLog(InformacionAction.class);

	public String execute() throws GadirServiceException {

		try{
			codigo = DatosSesion.getLogin();
			nombre = DatosSesion.getNombre();

			codigoTerritorialGen = DatosSesion.getCodigoTerritorialGenerico();
			direccionIP = getRequest().getRemoteHost();

			entorno = GadirConfig.leerParametro("entorno.servidor");
			baseDatos = GadirConfig.leerParametro("entorno.oracle");
			coImpresora = DatosSesion.getImpresora();
			if(Utilidades.isNotEmpty(coImpresora)){
				ImpresoraDTO impresoraDTO = impresoraBO.findById(coImpresora);
				impresora = impresoraDTO.getNombre();
			}
			colaEjecucion = DatosSesion.getColaEjecucion();
			carpetaTrabajo = DatosSesion.getCarpetaAcceso();

		}catch(Exception e){
			LOG.error("Error obteniendo informacion del usuario", e);
		}

		try{
			ProcesoDTO procesoDTO = ProcesoUtil.getProcesoByCoProceso(DatosSesion.getCoProcesoActual(getTabName()));

			if (Utilidades.isNotNull(procesoDTO)) {
				proceso = procesoDTO.getCodigoDescripcion();
			}

			List<AcmMenuDTO> acmMenuDTOs = ProcesoUtil.getAcmMenusByProceso(procesoDTO);

			if (acmMenuDTOs != null && acmMenuDTOs.size()>0) {
				for (AcmMenuDTO acmMenuDTO : acmMenuDTOs) {
					if (DatosSesion.getAcmMenusAccesibles().contains(acmMenuDTO)) {
						puntoMenu = acmMenuDTO.getCodigoDescripcion();
						break;
					}
				}
			} else {
				puntoMenu = "";
			}
		}catch(Exception e){
			LOG.error("Error obteniendo del proceso", e);
		}


		try{
			unidadAdministrativa = DatosSesion.getUnidadAdministrativa().getCoUnidadAdministrativa() + ", " + DatosSesion.getUnidadAdministrativa().getNombre();
		}catch(Exception e){
			LOG.error("Error obteniendo la unidad administrativa del usuario", e);
		}

		try{
			codigoTerritorial = DatosSesion.getCodigoTerritorial().getCoCodigoTerritorial() + ", " + DatosSesion.getCodigoTerritorial().getNombre();
		}catch(Exception e){
			LOG.error("Error obteniendo el codigo territorial del usuario", e);
		}

		try{
			//vamos a cargar la lista de impresoras
			UnidadAdministrativaDTO unidadUsuario = DatosSesion.getUnidadAdministrativa();
			String unidadUsuarioDescr="";
			if (unidadUsuario!=null){
				unidadUsuarioDescr=unidadUsuario.getCoUnidadAdministrativa();
			}

			DetachedCriteria dc = DetachedCriteria.forClass(ImpresoraDTO.class, "impresora");
			
			DetachedCriteria dcCodTersUniAdm = DetachedCriteria.forClass(CodterrUniadmDTO.class, "unidad");
			dcCodTersUniAdm.setProjection(Projections.property("unidad.id.coUnidadAdministrativa"));
			dcCodTersUniAdm.add(Restrictions.eqProperty("impresora.unidadAdministrativaDTO.coUnidadAdministrativa", "unidad.id.coUnidadAdministrativa"));
					
			DetachedCriteria dcCodTersUsuario = DetachedCriteria.forClass(AcmUsuarioCodterrDTO.class, "codTer");
			dcCodTersUsuario.setProjection(Projections.property("codTer.codigoTerritorial"));
			dcCodTersUsuario.add(Restrictions.eqProperty("unidad.id.coCodigoTerritorial", "codTer.codigoTerritorial"));
			dcCodTersUsuario.add(Restrictions.eq("acmUsuarioDTO.coAcmUsuario", DatosSesion.getLogin()));
			dcCodTersUniAdm.add(Subqueries.exists(dcCodTersUsuario));
			
			dc.add(Restrictions.or(
					Restrictions.eq("unidadAdministrativaDTO.coUnidadAdministrativa", DatosSesion.getUnidadAdministrativa().getCoUnidadAdministrativa()), 
					Subqueries.exists(dcCodTersUniAdm)));
			
			listaImpresora = impresoraBO.findByCriteria(dc);

			if(Utilidades.isNotEmpty(coImpresora)){
				ImpresoraDTO impresoraDTO = impresoraBO.findById(coImpresora);
				if(Utilidades.isNull(impresoraDTO.getUnidadAdministrativaDTO()) || !(unidadUsuarioDescr).equals(impresoraDTO.getUnidadAdministrativaDTO().getCoUnidadAdministrativa())){
					listaImpresora.add(impresoraDTO);
				}
			}
			codigoTerritorial = DatosSesion.getCodigoTerritorial().getCoCodigoTerritorial() + ", " + DatosSesion.getCodigoTerritorial().getNombre();
		}catch(Exception e){
			LOG.error("Error obteniendo el codigo territorial del usuario", e);
		}

		return INPUT;
	}


	public String cambiarImpresora() throws GadirServiceException {

		try{
			codigo = DatosSesion.getLogin();

			AcmUsuarioDTO usuario=acmUsuarioBO.findById(codigo);
			usuario.setImpresora(coImpresora);
			usuario.setCoUsuarioActualizacion(DatosSesion.getLogin());
			Calendar ahora = Calendar.getInstance();
			usuario.setFhActualizacion(ahora.getTime());
			acmUsuarioBO.save(usuario);
			DatosSesion.setImpresora(coImpresora);

			if(!hasErrors()){
				addActionMessage("Se ha cambiado de impresora a la " + coImpresora);
			}
		}catch(Exception e){
			LOG.error("Error asignando nueva impresora a usuario", e);
			addActionError("Error asignando nueva impresora a usuario");
		}


		return execute();
	}
	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}


	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}


	public String getCodigoTerritorial() {
		return codigoTerritorial;
	}


	public void setCodigoTerritorial(String codigoTerritorial) {
		this.codigoTerritorial = codigoTerritorial;
	}


	public String getCodigoTerritorialGen() {
		return codigoTerritorialGen;
	}


	public void setCodigoTerritorialGen(String codigoTerritorialGen) {
		this.codigoTerritorialGen = codigoTerritorialGen;
	}


	public String getDireccionIP() {
		return direccionIP;
	}


	public void setDireccionIP(String direccionIP) {
		this.direccionIP = direccionIP;
	}

	public String getEntorno() {
		return entorno;
	}


	public void setEntorno(String entorno) {
		this.entorno = entorno;
	}


	public String getBaseDatos() {
		return baseDatos;
	}


	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}

	public String getColaEjecucion() {
		return colaEjecucion;
	}


	public void setColaEjecucion(String colaEjecucion) {
		this.colaEjecucion = colaEjecucion;
	}


	public String getCarpetaTrabajo() {
		return carpetaTrabajo;
	}


	public void setCarpetaTrabajo(String carpetaTrabajo) {
		this.carpetaTrabajo = carpetaTrabajo;
	}


	public String getProceso() {
		return proceso;
	}


	public void setProceso(String proceso) {
		this.proceso = proceso;
	}


	public String getPuntoMenu() {
		return puntoMenu;
	}


	public void setPuntoMenu(String puntoMenu) {
		this.puntoMenu = puntoMenu;
	}

	public AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}


	public void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		this.acmUsuarioBO = acmUsuarioBO;
	}


	public List<ImpresoraDTO> getListaImpresora() {
		return listaImpresora;
	}


	public void setListaImpresora(List<ImpresoraDTO> listaImpresora) {
		this.listaImpresora = listaImpresora;
	}


	public ImpresoraBO getImpresoraBO() {
		return impresoraBO;
	}


	public void setImpresoraBO(ImpresoraBO impresoraBO) {
		this.impresoraBO = impresoraBO;
	}


	public String getCoImpresora() {
		return coImpresora;
	}


	public void setCoImpresora(String coImpresora) {
		this.coImpresora = coImpresora;
	}


	public String getImpresora() {
		return impresora;
	}


	public void setImpresora(String impresora) {
		this.impresora = impresora;
	}






}
