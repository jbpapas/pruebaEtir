/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.acegisecurity.GadirUserDetails;
import es.dipucadiz.etir.comun.dto.AcmBotonDTO;
import es.dipucadiz.etir.comun.dto.AcmMenuDTO;
import es.dipucadiz.etir.comun.dto.AcmUsuarioNotificacionDTO;
import es.dipucadiz.etir.comun.dto.CodigoTerritorialDTO;
import es.dipucadiz.etir.comun.dto.UnidadAdministrativaDTO;
import es.dipucadiz.etir.comun.vo.AyudaCasillaVO;
import es.dipucadiz.etir.comun.vo.OpcionIncidenciaVO;

/**
 * Acceso a datos de la sesión
 * @author Epicsa
 */
final public class DatosSesion {

	private static final Log LOG = LogFactory.getLog(DatosSesion.class);

	private DatosSesion() {}

	/**
	 * 
	 * @return El login del usuario
	 */
	public static String getLogin() {

		String username;

		if (Utilidades.isNotNull(SecurityContextHolder.getContext().getAuthentication())) {
			final Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (obj instanceof UserDetails) {
				username = ((UserDetails) obj).getUsername();
			} else {
				//username = obj.toString();
				username = "anonymous";
			}
		} else {
			username = null;
		}

		return username;
	}

	private static GadirUserDetails getGadirUserDetails() {

		GadirUserDetails gadirUserDetails = null;
		try {
			gadirUserDetails = (GadirUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			//no hay autenticación 
		}
		return gadirUserDetails;
	}

	/**
	 * 
	 * @return El código territorial del usuario
	 */
	public static CodigoTerritorialDTO getCodigoTerritorial() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		CodigoTerritorialDTO resultado;
		if (gadirUserDetails == null) {
			resultado = null;
		} else {
			resultado = gadirUserDetails.getCodigoTerritorial();
		}
		return resultado;
	}

	/**
	 * 
	 * @return El código territorial genérico del usuario
	 */
	public static String getCodigoTerritorialGenerico() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado;
		if (gadirUserDetails == null) {
			resultado = null;
		} else {
			resultado = gadirUserDetails.getCodigoTerritorialGenerico();
		}
		return resultado;
	}

	public static List<String> getCodigosTerritorialesExtra() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		List<String> resultado = null;
		if (gadirUserDetails == null) {
			resultado = null;
		} else {
			resultado = gadirUserDetails.getCodigosTerritorialesExtra();
		}
		return resultado;
	}

	public static List<OpcionIncidenciaVO> getOpcionesImpresion() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		List<OpcionIncidenciaVO> resultado = null;
		if (gadirUserDetails == null) {
			resultado = null;
		} else {
			resultado = gadirUserDetails.getOpcionesImpresion();
		}
		return resultado;
	}

	/**
	 * 
	 * @return El código territorial del usuario
	 */
	public static UnidadAdministrativaDTO getUnidadAdministrativa() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		UnidadAdministrativaDTO resultado;
		if (gadirUserDetails == null) {
			resultado = null;
		} else {
			resultado = gadirUserDetails.getUnidadAdministrativa();
		}
		return resultado;
	}

	/**
	 * 
	 * @return La impresora por defecto del usuario
	 */
	public static String getImpresora() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado;
		if (gadirUserDetails == null) {
			resultado = "";
		} else {
			resultado = gadirUserDetails.getImpresora();
		}
		return resultado;
	}

	public static void setImpresora(String impresora) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado;
		if (gadirUserDetails == null) {
			resultado = "";
		} else {
			gadirUserDetails.setImpresora(impresora);
		}

	}

	/**
	 * 
	 * @return La cola de ejecución para procesos batch
	 */
	public static String getColaEjecucion() {

		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado;
		if (gadirUserDetails == null) {
			resultado = "";
		} else {
			//resultado=gadirUserDetails.getColaEjecucion(); 
			resultado = "g" + gadirUserDetails.getUnidadAdministrativa().getCoUnidadAdministrativa();

		}
		return resultado;
	}

	/*
	 * @return Siempre falso (usar isUsuarioAccesible())
	 * 
	 * @Deprecated
	 * @see isUsuarioAccesible()
	 */
	@Deprecated
	public static boolean isConAccesibilidad() {
		return false;
	}

	/**
	 * 
	 * @return Si el usuario es accesible
	 */
	public static boolean isUsuarioAccesible() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		boolean resultado;
		if (gadirUserDetails == null) {
			resultado = false;
		} else {
			resultado = gadirUserDetails.isConAccesibilidad();
		}
		return resultado;
	}

	public static void setUsuarioAccesible(boolean conAccesibilidad) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) {
			gadirUserDetails.setConAccesibilidad(conAccesibilidad);
		}
	}

	/**
	 * 
	 * @return El nombre del usuario.
	 */
	public static String getNombre() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado;
		if (gadirUserDetails == null) {
			resultado = "";
		} else {
			resultado = gadirUserDetails.getNombre();
		}
		return resultado;
	}

	public static List<AcmBotonDTO> getAcmBotonsAccesibles() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		List<AcmBotonDTO> resultado;
		if (gadirUserDetails == null) {
			resultado = null;
		} else {
			resultado = gadirUserDetails.getAcmBotonsAccesibles();
		}
		return resultado;
	}

	public static List<AcmMenuDTO> getAcmMenusAccesibles() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		List<AcmMenuDTO> resultado;
		if (gadirUserDetails == null) {
			resultado = null;
		} else {
			resultado = gadirUserDetails.getAcmMenusAccesibles();
		}
		return resultado;
	}

	public static String getCargo() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado;
		if (gadirUserDetails == null) {
			resultado = "";
		} else {
			resultado = gadirUserDetails.getCargo();
		}
		return resultado;
	}

	public static String getCarpetaAcceso() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado;
		if (gadirUserDetails == null) {
			resultado = "";
		} else {
			resultado = gadirUserDetails.getCarpetaAcceso();
		}
		return resultado;
	}

	public static String getMenuHtml() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado;
		if (gadirUserDetails == null) {
			resultado = "";
		} else {
			resultado = gadirUserDetails.getMenuHtml();
		}
		return resultado;
	}

	public static Integer actualizaNumeracionVentanas(String tabName) {
		Integer res = null;
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		Map<String, Integer> numeracionVentanas = gadirUserDetails.getNumeracionVentanas();
		if (numeracionVentanas == null) {
			numeracionVentanas = new HashMap<String, Integer>();
		}
		res = numeracionVentanas.get(tabName);
		if (res == null) {
			int numMax = -1;
			for (Integer numPrevio : numeracionVentanas.values()) {
				if (numPrevio != null && numPrevio.intValue() >= numMax) {
					numMax = numPrevio.intValue();
				}
			}
			res = numMax + 1;
			numeracionVentanas.put(tabName, res);
		}
		return res;
	}

	public static Integer getNumeracionVentana(String tabName) {
		Integer res = null;
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		Map<String, Integer> numeracionVentanas = gadirUserDetails.getNumeracionVentanas();
		if (numeracionVentanas != null) {
			res = numeracionVentanas.get(tabName);
		}
		return res;
	}

	private static PilaVolver getPilaVolver(String tabName) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		PilaVolver pilaVolver = gadirUserDetails.getPilasVolver().get(tabName);
		if (pilaVolver == null) {
			pilaVolver = new PilaVolver();
			gadirUserDetails.getPilasVolver().put(tabName, pilaVolver);
		}
		return pilaVolver;
	}

	public static void pushPilaVolver(String tabName, ItemPilaVolver itemPilaVolver) {
		try {
			getPilaVolver(tabName).pushPilaVolver(itemPilaVolver);

			LOG.debug("*****************************************************************************");
			LOG.debug("Apilo: ");
			LOG.debug("    volvible: " + itemPilaVolver.getActionVolvible());
			//LOG.debug("    volvedor: " + itemPilaVolver.getActionVolvedor());
			LOG.debug("    paramets: " + itemPilaVolver.getParametros().toString());
			LOG.debug("*****************************************************************************");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ItemPilaVolver popPilaVolver(String tabName) {
		ItemPilaVolver itemPilaVolver = null;
		try {
			itemPilaVolver = getPilaVolver(tabName).popPilaVolver();
			if (itemPilaVolver != null) {

				LOG.debug("*****************************************************************************");
				LOG.debug("Desapilo: ");
				LOG.debug("    volvible: " + itemPilaVolver.getActionVolvible());
				//LOG.debug("    volvedor: " + itemPilaVolver.getActionVolvedor());
				LOG.debug("    paramets: " + itemPilaVolver.getParametros().toString());
				LOG.debug("*****************************************************************************");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return itemPilaVolver;
	}

	public static ItemPilaVolver topPilaVolver(String tabName) {
		ItemPilaVolver itemPilaVolver = null;
		try {
			itemPilaVolver = getPilaVolver(tabName).topPilaVolver();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return itemPilaVolver;
	}

	public static ItemPilaVolver topPilaVolver(String tabName, int distanceFromTop) {
		ItemPilaVolver itemPilaVolver = null;
		try {
			itemPilaVolver = getPilaVolver(tabName).topPilaVolver(distanceFromTop);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return itemPilaVolver;
	}

	/*public static ItemPilaVolver segundoElementoPilaVolver(String tabName) {
		ItemPilaVolver itemPilaVolver = null;
		try {
			itemPilaVolver = getPilaVolver(tabName).topPilaVolver(1);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return itemPilaVolver;
	}
	
	public static ItemPilaVolver terceroElementoPilaVolver(String tabName) {
		ItemPilaVolver itemPilaVolver = null;
		try {
			itemPilaVolver = getPilaVolver(tabName).topPilaVolver(2);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return itemPilaVolver;
	}*/

	public static void vaciaPilaVolver(String tabName) {
		try {
			getPilaVolver(tabName).vaciaPilaVolver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void preapila(String tabName, ItemPilaVolver itemPilaVolver) {
		preapila(tabName, itemPilaVolver.getParametros(), itemPilaVolver.getActionVolvible());
	}

	public static void preapila(String tabName, Map<String, Object> parametros, String actionVolvible) {
		try {
			getPilaVolver(tabName).pushPrepilaVolverParametros(parametros);
			getPilaVolver(tabName).pushPrepilaVolverAction(actionVolvible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void borraPreapilado(String tabName) {
		try {
			getPilaVolver(tabName).vaciaPrepilaVolverParametros(tabName);
			getPilaVolver(tabName).vaciaPrepilaVolverAction(tabName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ItemPilaVolver getAnteriorPreapilado(String tabName) {
		Map<String, Object> parametrosRequest = new HashMap<String, Object>();
		String actionVolvible = "";
		try {
			//List<Map<String, Object>> listaParametrosRequest = gadirUserDetails.getPrepilaVolverParametros();

			Boolean encontrado = false;
			int i = 1;
			while (!encontrado) {
				parametrosRequest = getPilaVolver(tabName).topPrepilaVolverParametros(i);
				if (parametrosRequest == null) {
					break;
				}
				if (!parametrosRequest.containsKey("ventanaBotonLateral") || "false".equals(parametrosRequest.get("ventanaBotonLateral"))) {
					actionVolvible = getPilaVolver(tabName).topPrepilaVolverAction(i);
					encontrado = true;
				}
				i++;
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		if (actionVolvible.endsWith("Ajax")) actionVolvible = actionVolvible.substring(0, actionVolvible.length() - 4);
		return new ItemPilaVolver(actionVolvible, parametrosRequest);
	}

	public static ItemPilaVolver getPreapilado(String tabName) {
		Map<String, Object> parametrosRequest = new HashMap<String, Object>();
		String actionVolvible = "";
		try {
			parametrosRequest = getPilaVolver(tabName).topPrepilaVolverParametros();
			actionVolvible = getPilaVolver(tabName).topPrepilaVolverAction();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		if (actionVolvible.endsWith("Ajax")) actionVolvible = actionVolvible.substring(0, actionVolvible.length() - 4);
		return new ItemPilaVolver(actionVolvible, parametrosRequest);
	}

	public static void salvaTS(Date ts) {
		try {
			final GadirUserDetails gadirUserDetails = getGadirUserDetails();
			gadirUserDetails.setTS(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void borraTS() {
		try {
			final GadirUserDetails gadirUserDetails = getGadirUserDetails();
			gadirUserDetails.setTS(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Date getTS() {
		Date ts = null;
		try {
			final GadirUserDetails gadirUserDetails = getGadirUserDetails();
			ts = gadirUserDetails.getTS();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}

	private static Map<String, Object> getObjetos(String tabName) {
		Map<String, Object> objetos = null;
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		Map<String, Map<String, Object>> mapObjetos = gadirUserDetails.getObjetos();
		if (mapObjetos == null) {
			mapObjetos = new HashMap<String, Map<String, Object>>();
		}
		objetos = mapObjetos.get(tabName);
		if (objetos == null) {
			objetos = new HashMap<String, Object>();
			mapObjetos.put(tabName, objetos);
		}
		return objetos;
	}

	public static void setObjeto(String tabName, String nombre, Object objeto) {
		try {
			Map<String, Object> objetos = getObjetos(tabName);
			objetos.put(nombre, objeto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getObjeto(String tabName, String nombre) {
		Object resultado = null;
		try {
			Map<String, Object> objetos = getObjetos(tabName);
			resultado = objetos.get(nombre);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static void borraObjeto(String tabName, String nombre) {
		try {
			Map<String, Object> objetos = getObjetos(tabName);
			objetos.remove(nombre);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean existeObjeto(String tabName, String nombre) {
		boolean resultado = false;
		try {
			final GadirUserDetails gadirUserDetails = getGadirUserDetails();
			if (gadirUserDetails == null) {
				resultado = false;
			} else {
				Map<String, Object> objetos = getObjetos(tabName);
				resultado = objetos.containsKey(nombre);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static void setObjetoUnico(String nombre, Object objeto) {
		try {
			final GadirUserDetails gadirUserDetails = getGadirUserDetails();
			Map<String, Object> objetos = gadirUserDetails.getObjetosUnicos();
			objetos.put(nombre, objeto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getObjetoUnico(String nombre) {
		Object resultado = null;
		try {
			final GadirUserDetails gadirUserDetails = getGadirUserDetails();
			Map<String, Object> objetos = gadirUserDetails.getObjetosUnicos();
			resultado = objetos.get(nombre);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static void borraObjetoUnico(String nombre) {
		try {
			final GadirUserDetails gadirUserDetails = getGadirUserDetails();
			Map<String, Object> objetos = gadirUserDetails.getObjetosUnicos();
			objetos.remove(nombre);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean existeObjetoUnico(String nombre) {
		boolean resultado = false;
		try {
			final GadirUserDetails gadirUserDetails = getGadirUserDetails();
			if (gadirUserDetails == null) {
				resultado = false;
			} else {
				Map<String, Object> objetos = gadirUserDetails.getObjetosUnicos();
				resultado = objetos.containsKey(nombre);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static String getCoAcmMenuActual(String tabName) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado = "";
		if (gadirUserDetails != null && StringUtils.isNotEmpty(tabName)) {
			resultado = gadirUserDetails.getCoAcmMenuActuals().get(tabName);
		}
		return resultado;
	}

	public static void setCoAcmMenuActual(String tabName, String coAcmMenuActual) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null && StringUtils.isNotEmpty(tabName) && StringUtils.isNotEmpty(coAcmMenuActual)) {
			gadirUserDetails.getCoAcmMenuActuals().put(tabName, coAcmMenuActual);
		}
	}

	public static String getCoAcmMenuActualSoloDesdeJsp() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado = "";
		if (gadirUserDetails != null) {
			resultado = gadirUserDetails.getCoAcmMenuActual();
		}
		return resultado == null ? "" : resultado;
	}

	public static void setCoAcmMenuActualSoloDesdeJsp(String coAcmMenuActual) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) {
			gadirUserDetails.setCoAcmMenuActual(coAcmMenuActual);
		}
	}

	public static String getCoProcesoActual(String tabName) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado = "";
		if (gadirUserDetails != null && StringUtils.isNotEmpty(tabName)) {
			resultado = gadirUserDetails.getCoProcesoActuals().get(tabName);
		}
		return resultado == null ? "" : resultado;
	}

	public static void setCoProcesoActual(String tabName, String coProcesoActual) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null && StringUtils.isNotEmpty(tabName) && StringUtils.isNotEmpty(coProcesoActual)) {
			gadirUserDetails.getCoProcesoActuals().put(tabName, coProcesoActual);
		}
	}

	public static String getCoProcesoActualSoloDesdeJsp() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado = "";
		if (gadirUserDetails != null) {
			resultado = gadirUserDetails.getCoProcesoActual();
		}
		return resultado;
	}

	public static void setCoProcesoActualSoloDesdeJsp(String coProcesoActual) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null && StringUtils.isNotEmpty(coProcesoActual)) {
			gadirUserDetails.setCoProcesoActual(coProcesoActual);
		}
	}

	public static String getEmail() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado;
		if (gadirUserDetails == null || gadirUserDetails.getEmail() == null) {
			resultado = "";
		} else {
			resultado = gadirUserDetails.getEmail();
		}
		return resultado;
	}

	public static String getEscudo() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		String resultado;
		if (gadirUserDetails == null || gadirUserDetails.getEscudo() == null) {
			resultado = "default.png";
		} else {
			resultado = gadirUserDetails.getEscudo();
		}
		return resultado;
	}

	public static int getNumResultadosPaginacion() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		int resultado;
		if (gadirUserDetails == null) {
			resultado = 0;
		} else {
			resultado = gadirUserDetails.getNumResultadosPaginacion();
		}
		return resultado;
	}

	public static void setNumResultadosPaginacion(int numResultadosPaginacion) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) {
			gadirUserDetails.setNumResultadosPaginacion(numResultadosPaginacion);
		}
	}

	public static AyudaCasillaVO getAyudaCasillaVO() {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		AyudaCasillaVO resultado;
		if (gadirUserDetails == null) {
			resultado = null;
		} else {
			resultado = gadirUserDetails.getAyudaCasillaVO();
		}
		return resultado;
	}

	public static void setAyudaCasillaVO(AyudaCasillaVO ayudaCasillaVO) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) {
			gadirUserDetails.setAyudaCasillaVO(ayudaCasillaVO);
		}
	}

	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo1() {
		List<AcmUsuarioNotificacionDTO> usuarioNotificaciones = new ArrayList<AcmUsuarioNotificacionDTO>();
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) {
			usuarioNotificaciones = gadirUserDetails.getUsuarioNotificacionesTipo1();
			for (AcmUsuarioNotificacionDTO notificacionDTO : usuarioNotificaciones) {
				notificacionDTO.setMensaje(UsuarioNotificacionUtil.parsearMensajeNotificacion(notificacionDTO.getMensaje()));
			}
		}
		return usuarioNotificaciones;
	}

	public static void setUsuarioNotificacionesTipo1(List<AcmUsuarioNotificacionDTO> usuarioNotificaciones) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) {
			gadirUserDetails.setUsuarioNotificacionesTipo1(usuarioNotificaciones);
		}
	}

	public static void setUsuarioNumNotificacionesTipo1(int numeroNotificaciones) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) gadirUserDetails.setUsuarioNumNotificacionesTipo1(numeroNotificaciones);
	}

	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo2() {
		List<AcmUsuarioNotificacionDTO> usuarioNotificaciones = new ArrayList<AcmUsuarioNotificacionDTO>();
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) {
			usuarioNotificaciones = gadirUserDetails.getUsuarioNotificacionesTipo2();
			for (AcmUsuarioNotificacionDTO notificacionDTO : usuarioNotificaciones) {
				notificacionDTO.setMensaje(UsuarioNotificacionUtil.parsearMensajeNotificacion(notificacionDTO.getMensaje()));
			}
		}
		return usuarioNotificaciones;
	}

	public static void setUsuarioNotificacionesTipo2(List<AcmUsuarioNotificacionDTO> usuarioNotificaciones) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) {
			gadirUserDetails.setUsuarioNotificacionesTipo2(usuarioNotificaciones);
		}
	}

	public static List<AcmUsuarioNotificacionDTO> getUsuarioNotificacionesTipo3T() {
		List<AcmUsuarioNotificacionDTO> usuarioNotificaciones = new ArrayList<AcmUsuarioNotificacionDTO>();
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) {
			usuarioNotificaciones = gadirUserDetails.getUsuarioNotificacionesTipo3T();
		}
		return usuarioNotificaciones;
	}

	public static void setUsuarioNotificacionesTipo3T(List<AcmUsuarioNotificacionDTO> usuarioNotificaciones) {
		final GadirUserDetails gadirUserDetails = getGadirUserDetails();
		if (gadirUserDetails != null) {
			gadirUserDetails.setUsuarioNotificacionesTipo3T(usuarioNotificaciones);
		}
	}

}
