package es.dipucadiz.etir.comun.action.mantenimientoCasillas;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Preparable;

import es.dipucadiz.etir.comun.action.AbstractGadirBaseAction;
import es.dipucadiz.etir.comun.action.NuevoDocumentoAction;
import es.dipucadiz.etir.comun.action.Volvible;
import es.dipucadiz.etir.comun.bo.CalleBO;
import es.dipucadiz.etir.comun.bo.CasillaMunicipioBO;
import es.dipucadiz.etir.comun.bo.CasillasRelacionadasBO;
import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.ConceptoBO;
import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.bo.DomicilioBO;
import es.dipucadiz.etir.comun.bo.MantenimientoCasillasBO;
import es.dipucadiz.etir.comun.bo.ModeloBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.bo.ProvinciaBO;
import es.dipucadiz.etir.comun.bo.SituacionBO;
import es.dipucadiz.etir.comun.bo.UnidadUrbanaBO;
import es.dipucadiz.etir.comun.boStoredProcedure.ActualizarConDocOrigenBO;
import es.dipucadiz.etir.comun.boStoredProcedure.CalculoManualDocumentoBO;
import es.dipucadiz.etir.comun.boStoredProcedure.ControlIncidenciaBO;
import es.dipucadiz.etir.comun.boStoredProcedure.CrearHojasBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.constants.CasillaConstants;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.CasillasRelacionadasDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.dto.SituacionDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.dto.ValidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Batch;
import es.dipucadiz.etir.comun.utilidades.BloqueoUtil;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Formato;
import es.dipucadiz.etir.comun.utilidades.Impresion;
import es.dipucadiz.etir.comun.utilidades.IncidenciaConstants;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.MunicipioConstants;
import es.dipucadiz.etir.comun.utilidades.OpcionesIncidenciaSituacionUtil;
import es.dipucadiz.etir.comun.utilidades.ProvinciaConstants;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.AccesoPlantillaVO;
import es.dipucadiz.etir.comun.vo.AyudaCasillaVO;
import es.dipucadiz.etir.sb05.action.G5114CasillasMunicipio.G5114AbstractAction;

public class MantenimientoCasillasAction extends AbstractGadirBaseAction implements Preparable,Volvible {


	private static final long serialVersionUID = 5337872374644181435L;
	private static final Log LOG = LogFactory.getLog(MantenimientoCasillasAction.class);
	String pestLlamada;
	String coModelo;
	String coVersion;
	String coDocumento;

	String coModeloSel;
	String coVersionSel;
	String coDocumentoSel;

	String operacion;
	
	//acciones permitidas, ante la duda... no permito nada
	Boolean edicion = false;
	Boolean nuevoDocumento = false;
	Boolean borrarDocumento = false;
	Boolean crearHoja = false;
	Boolean eliminarHoja = false;
	Boolean guardar = false;
	Boolean calcular = false;
	Boolean mostrarColumnaError = false;
	private Boolean pedirDocOrigen = false;
	private Boolean mostrarMensajes = true;
	
	MantenimientoCasillasBO mantenimientoCasillasBO;
	ClienteBO clienteBO;
	DomicilioBO	domicilioBO;
	UnidadUrbanaBO unidadUrbanaBO;
	CalleBO calleBO;
	CasillaMunicipioBO casillaMunicipioBO;
	MunicipioBO municipioBO;
	ProvinciaBO provinciaBO;
	SituacionBO situacionBO;
	ConceptoBO conceptoBO;
	private ModeloBO modeloBO;
	private CalculoManualDocumentoBO calculoManualDocumentoBO;
	private CrearHojasBO crearHojasBO;
	private ControlIncidenciaBO controlIncidenciaBO;
	private CasillasRelacionadasBO casillasRelacionadasBO;

	MCDocumentoVO mcDocumentoVO;

	int hoja=1;
	private String hojaIr;
	boolean incluirCalculo = false;
	
	SortedMap<Short, String> listaCasillasError= new TreeMap<Short, String>();
	
	boolean errorTotal=false;
	
	private String coModeloPedirDocOrigen;
	private String coVersionPedirDocOrigen;
	private String coDocumentoPedirDocOrigen;
	private String tipoModelo;
	private String subtipoModelo;
	private String cssBotonCrearHojas = "display:none";
	private String cssBotonGuardar = "display:auto";
	private String actionMessage;
	private short hojaPrimerError = -1;
	
	public final String OPERACION_GENERICO ="GENERICO";
	public final String OPERACION_MODIFICA ="MODIFICA";
	public final String OPERACION_CONSULTA ="CONSULTA";
	public final String OPERACION_ALTA ="ALTA";
	public final String OPERACION_CONTROL ="CONTROL";
	
	public final String DOCUMENTO_PDTE_CORRES = "C";
	public final String DOCUMENTO_EN_BASE_DE_DATOS = "B";
	public final String DOCUMENTO_FALLO_EN_CORRES = "F";
	public final String DOCUMENTO_INDEFINIDO = "I";
	public final String DOCUMENTO_RECHAZADO = "R";
	
	public final static String ALTA_AUTO_CENSO = "altaAutoCenso";
	
	private boolean isGeneradoParcial = false;
	private String mensajeGeneradoParcial;
	private boolean seleccionarDestinos = false;
	private String ejecutaIncidencia;
	private String origen;
	
	private void cambiarAtributo(MCInformacionCasillaVO casilla, boolean mirandoLigadas){
		if (!Utilidades.isEmpty(casilla.getAtributoError())){
			MCDocumentoVO.calculaAtributo(casilla, casilla.getAtributoError());
			mcDocumentoVO.getInformacionCasillas().put(casilla.getNuCasilla(), casilla);
			if (mirandoLigadas)activarCasillasLigadas(casilla.getNuCasilla());
		}
	}
	
	private void activarCasillasLigadas(short nuCasilla){
		
		try{
			List<Short> listaEnlazadas = mantenimientoCasillasBO.findCasillasLigadas(coModelo, coVersion, nuCasilla);
			for (Short nuCasillaEnlazada:listaEnlazadas){
				cambiarAtributo(mcDocumentoVO.getInformacionCasillas().get(nuCasillaEnlazada), false);
			}
		}catch(GadirServiceException e){
			LOG.error("error en activarCasillasLigadas", e);
			addActionError("Error activando casillas ligadas");
		}
	}
	
	private boolean casillasEnlazadasRellenas(short hoja, short nuCasilla){
		
		boolean algunaRellena=false;
		try{
			List<Short> listaEnlazadas = mantenimientoCasillasBO.findCasillasLigadas(coModelo, coVersion, nuCasilla);

			for (Short nuCasillaEnlazada:listaEnlazadas){
				
				MCHojaVO mcHojaVO = mcDocumentoVO.getHojas().get(hoja);
				
				if (mcHojaVO != null && !mcHojaVO.getCasillas().isEmpty()){
					
					MCCasillaVO mcCasillaVO = mcHojaVO.getCasillas().get(nuCasillaEnlazada);
					
					if(mcCasillaVO != null && !Utilidades.isEmpty(mcCasillaVO.getValor()))
						algunaRellena=true;
				}
			}
		}catch(GadirServiceException e){
			LOG.error("error en casillasEnlazadasRellenas", e);
			addActionError("Error determinando contenido de casillas enlazadas");
		}
		return algunaRellena;
	}
	
	public String execute(){
		
		// Establece las propiedades que se usan para llamar al action G743DocumentoDetalle.
		coModeloSel = coModelo;
		coVersionSel = coVersion;
		coDocumentoSel = coDocumento;
		
		
		String result = INPUT;
		if (hoja==0)hoja=1;
		if (operacion==null || operacion.isEmpty())operacion=OPERACION_GENERICO;
		
		if (Utilidades.isEmpty(coModelo) || Utilidades.isEmpty(coVersion) || Utilidades.isEmpty(coDocumento)){
			addActionError("Debe indicarse un documento (modelo - versión - número de documento)");
		}

		if (Utilidades.isEmpty(operacion)){
			addActionError("Debe indicarse la operación");
		}
		if (!hasErrors()){
			if (!OPERACION_CONSULTA.equals(operacion)) {
				Map<String, Object> bloqueoMap = BloqueoUtil.isGeneradoParcialCenso(coModelo, coVersion, coDocumento);
				isGeneradoParcial = (Boolean) bloqueoMap.get(BloqueoUtil.KEY_EXISTE_PARCIAL);
				if (isGeneradoParcial) mensajeGeneradoParcial = (String) bloqueoMap.get(BloqueoUtil.KEY_MENSAJE);
			}
			result = primeraCarga();
			if (!hasErrors()){
				pasaValidaciones((short)hoja);
			}
		}
		
		if (guardarAutomatico()) {
			result = botonGuardar();
		}
 
		return result;
	}
	
	private boolean guardarAutomatico() {
		boolean result = false;
		if (NuevoDocumentoAction.TRAMITADOR_ONLINE.equals(origen) && OPERACION_ALTA.equals(operacion)) {
			result = true;
			for (Entry<Short, MCInformacionCasillaVO> entry : mcDocumentoVO.getInformacionCasillas().entrySet()) {
				MCInformacionCasillaVO casillaVO = entry.getValue();
				if (casillaVO!=null && casillaVO.getDesprotegido()!=null && casillaVO.getConDatosValidos()!=null && (casillaVO.getDesprotegido() || casillaVO.getConDatosValidos())) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	private String primeraCarga(){
		String result = INPUT;
		try {
//			Date t1=new Date();
			mcDocumentoVO = mantenimientoCasillasBO.getDocumento(coModelo, coVersion, coDocumento, operacion);
			cargaTipoSubtipo();
			ponerInicioFinLiquidacion();
			calcularCssBotonesGuardar();
//			Date t2 = new Date();
			//System.out.println("****************************************************************");
			//System.out.println("T1: " + t1);
			//System.out.println("T2: " + t2);
			//System.out.println("T2 - T1: " + (t2.getTime()-t1.getTime()) + " milisegundos");
			//System.out.println("****************************************************************");
//			grabarEstadisticasCarga(coModelo, coVersion, coDocumento, operacion, t1, t2);
			setObjetoSesion("mcDocumentoVO", mcDocumentoVO); 
			comprobarSesionAltaAutoCenso();
			//martecher
			
		//	String caracterModelo = coModelo.substring(0, 1);  
			 if("L".equalsIgnoreCase(tipoModelo)){
				 pestLlamada="2";
			 }else{
				 pestLlamada="1";
			 }
		} catch (GadirServiceException e) {
			
			if(e.getMessage().equals("No se encuentran casillas para el modelo")) {
				addActionError("No existen casillas para el documento seleccionado");
			} else {
				addActionError("Error obteniendo documento.");
				addActionError(e.getMensaje());
				try {
					result = botonVolver();
				} catch (GadirServiceException e2) {
					addActionError(e2.getMensaje());
				}
			}
			
			mcDocumentoVO=null;
			LOG.error("Error obteniendo documento", e);
			e.printStackTrace();
		}
		return result;
	}
	
//	private void grabarEstadisticasCarga(String coModelo, String coVersion, String coDocumento, String operacion, Date t1, Date t2) {
//		try {
//			String mensaje = "Carga de documento " + coModelo + " " + coVersion + " " + coDocumento + " (" + operacion + "): " + (t2.getTime()-t1.getTime()) + " milisegundos";
//			clienteBO.ejecutaQueryUpdate("insert into TR_ERROR_PL(MODULO, ERROR, CO_USUARIO, FH_ACTUALIZACION, BO_PL) " +
//					"values ('" + getActionNameTriki() + "', '" + mensaje + "', '" + DatosSesion.getLogin() + "',systimestamp, 0)");
//
//		} catch (Exception e) {
//			LOG.error(e.getMessage(), e);
//		}
//	}

	@SuppressWarnings("unchecked")
	private void comprobarSesionAltaAutoCenso() {
		if (existeObjetoSesion(ALTA_AUTO_CENSO)) {
			if (!OPERACION_ALTA.equals(operacion)) {
				borraObjetoSesion(ALTA_AUTO_CENSO);
			} else if (mcDocumentoVO.getDocumentoDTO().getDocumentoDTOByOrigenDocuni() == null) {
				borraObjetoSesion(ALTA_AUTO_CENSO);
			} else {
				Map<String, Object> altaAutomaticaEnCenso = (HashMap<String, Object>) getObjetoSesion(ALTA_AUTO_CENSO);
				DocumentoDTOId aaDocOri = (DocumentoDTOId) altaAutomaticaEnCenso.get("documentoOrigen");
				DocumentoDTOId mcDocOri = mcDocumentoVO.getDocumentoDTO().getDocumentoDTOByOrigenDocuni().getId();
				if (!aaDocOri.getCoModelo().equals(mcDocOri.getCoModelo()) || !aaDocOri.getCoVersion().equals(mcDocOri.getCoVersion()) || !aaDocOri.getCoDocumento().equals(mcDocOri.getCoDocumento())) {
					borraObjetoSesion(ALTA_AUTO_CENSO);
				}
			}
		}
	}
	
	public boolean isLiquidacionLL() {
		boolean result;
		try {
			if (Utilidades.isEmpty(tipoModelo) || Utilidades.isEmpty(subtipoModelo)) {
				ModeloDTO modeloDTO;
				modeloDTO = modeloBO.findById(mcDocumentoVO.getDocumentoDTO().getId().getCoModelo());
				tipoModelo = modeloDTO.getTipo();
				subtipoModelo = modeloDTO.getSubtipo();
			}
			result = "L".equals(tipoModelo) && ("N".equals(subtipoModelo) || "I".equals(subtipoModelo));
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			result = false;
		}
		return result;
	}

	private void calcularCssBotonesGuardar() {
		if (isLiquidacionLL()) {
			if (Utilidades.isNotNull(mcDocumentoVO.getHojas()) &&
					Utilidades.isNotNull(mcDocumentoVO.getHojas().get(CasillaConstants.PRIMERA_HOJA).getCasillas().get(CasillaConstants.NU_CASILLA_INICIO_LIQUIDACION)) &&
					Utilidades.isNotNull(mcDocumentoVO.getHojas().get(CasillaConstants.PRIMERA_HOJA).getCasillas().get(CasillaConstants.NU_CASILLA_FIN_LIQUIDACION))) {
				String valorIniActual = mcDocumentoVO.getHojas().get(CasillaConstants.PRIMERA_HOJA).getCasillas().get(CasillaConstants.NU_CASILLA_INICIO_LIQUIDACION).getValor();
				String valorFinActual = mcDocumentoVO.getHojas().get(CasillaConstants.PRIMERA_HOJA).getCasillas().get(CasillaConstants.NU_CASILLA_FIN_LIQUIDACION).getValor();
				String valorIniActualFormateado = "";
				String valorFinActualFormateado = "";
				if (Utilidades.isNotEmpty(valorIniActual)) {
					try {
						valorIniActualFormateado = Formato.formatearDato(valorIniActual, mcDocumentoVO.getLiquidacionIniFormato(), mcDocumentoVO.getLiquidacionIniLongitud(), " ");
					} catch (Exception e) {
						LOG.debug("Error en Formato.formatearDato.", e);
					}
				}
				if (Utilidades.isNotEmpty(valorFinActual)) {
					try {
						valorFinActualFormateado = Formato.formatearDato(valorFinActual, mcDocumentoVO.getLiquidacionFinFormato(), mcDocumentoVO.getLiquidacionFinLongitud(), " ");
					} catch (Exception e) {
						LOG.debug("Error en Formato.formatearDato.", e);
					}
				}
				
				if (!mcDocumentoVO.getLiquidacionIniValor().equals(valorIniActualFormateado) ||
				    !mcDocumentoVO.getLiquidacionFinValor().equals(valorFinActualFormateado)) {
					cssBotonCrearHojas = "display:auto";
					cssBotonGuardar = "display:none";
				}
			}
		}
	}

	private void cargaTipoSubtipo() {
		try {
			ModeloDTO modeloDTO = modeloBO.findById(mcDocumentoVO.getDocumentoDTO().getId().getCoModelo());
			tipoModelo = modeloDTO.getTipo();
			subtipoModelo = modeloDTO.getSubtipo();
			if ("L".equals(tipoModelo)) {
				pedirDocOrigen = false;
			}
		} catch (GadirServiceException e) {
			e.printStackTrace();
			addActionError("No puede cargar el modelo " + mcDocumentoVO.getDocumentoDTO().getId().getCoModelo());
		}
	}

	private void ponerInicioFinLiquidacion() {
		if (isLiquidacionLL()) {
			mcDocumentoVO.setPermitirBotonCrearHojas(true);
			try {
				String valor = mcDocumentoVO.getHojas().get(CasillaConstants.PRIMERA_HOJA).getCasillas().get(CasillaConstants.NU_CASILLA_INICIO_LIQUIDACION).getValor();
				String formato = mcDocumentoVO.getInformacionCasillas().get(CasillaConstants.NU_CASILLA_INICIO_LIQUIDACION).getFormato();
				double longitud = Double.valueOf(mcDocumentoVO.getInformacionCasillas().get(CasillaConstants.NU_CASILLA_INICIO_LIQUIDACION).getLongitud().toString());
				mcDocumentoVO.setLiquidacionIniFormato(formato);
				mcDocumentoVO.setLiquidacionIniLongitud(longitud);
				mcDocumentoVO.setLiquidacionIniValor(Formato.formatearDato(valor, formato, longitud, " "));
			} catch (NullPointerException e) { // No existe la casilla
				mcDocumentoVO.setLiquidacionIniValor("");
			} catch (GadirServiceException e) {
				mcDocumentoVO.setLiquidacionIniValor("");
				LOG.debug("Error en Formato.formatearDato.", e);
			}
			try {
				String valor = mcDocumentoVO.getHojas().get(CasillaConstants.PRIMERA_HOJA).getCasillas().get(CasillaConstants.NU_CASILLA_FIN_LIQUIDACION).getValor();
				String formato = mcDocumentoVO.getInformacionCasillas().get(CasillaConstants.NU_CASILLA_FIN_LIQUIDACION).getFormato();
				double longitud = Double.valueOf(mcDocumentoVO.getInformacionCasillas().get(CasillaConstants.NU_CASILLA_FIN_LIQUIDACION).getLongitud().toString());
				mcDocumentoVO.setLiquidacionFinFormato(formato);
				mcDocumentoVO.setLiquidacionFinLongitud(longitud);
				mcDocumentoVO.setLiquidacionFinValor(Formato.formatearDato(valor, formato, longitud, " "));
			} catch (NullPointerException e) { // No existe la casilla
				mcDocumentoVO.setLiquidacionFinValor("");
			} catch (GadirServiceException e) {
				mcDocumentoVO.setLiquidacionFinValor("");
				LOG.debug("Error en Formato.formatearDato.", e);
			}
		} else {
			mcDocumentoVO.setPermitirBotonCrearHojas(false);
		}
	}

	private void pasaValidaciones(short nuHoja){
		pasaValidaciones(nuHoja, false);
	}
	private void pasaValidaciones(short nuHoja, boolean obligarValidacion){
		if (OPERACION_CONSULTA.equals(operacion)) return;

		if (mcDocumentoVO.getHojas()==null || mcDocumentoVO.getHojas().get(nuHoja) == null){
			addActionError("No existe la hoja seleccionada");
		}
		
		if (!hasErrors() || obligarValidacion){
			for (Short nuCasilla : mcDocumentoVO.getInformacionCasillas().keySet()){
				MCInformacionCasillaVO informacionCasilla = mcDocumentoVO.getInformacionCasillas().get(nuCasilla);
//				if (nuHoja==1 || (informacionCasilla.getRepeticion()!=null && informacionCasilla.getRepeticion())){
				if ((nuHoja==1 && !informacionCasilla.getRepeticion()) || (nuHoja>1 && informacionCasilla.getRepeticion())) {
					boolean procederConCasilla = true;
					MCCasillaVO mcCasilla = (mcDocumentoVO.getHojas().get(nuHoja)).getCasillas().get(nuCasilla);
					if (mcCasilla==null || Utilidades.isEmpty(mcCasilla.getValor())){
						if (informacionCasilla.getContenidoObligatorio()!=null && informacionCasilla.getContenidoObligatorio()){
							//addActionError(Mensaje.getTexto(MensajeConstants.CASILLA_OBLIGATORIA));
							listaCasillasError.put(informacionCasilla.getNuCasilla(), Mensaje.getTexto(MensajeConstants.CASILLA_OBLIGATORIA));
							cambiarAtributo(informacionCasilla, true);
							procederConCasilla = false;
						}else if (informacionCasilla.getDesprotegido()!=null  && informacionCasilla.getDesprotegido() &&
								informacionCasilla.getVisible()!=null  && !informacionCasilla.getVisible()){
							boolean algunaRellena=casillasEnlazadasRellenas(nuHoja, informacionCasilla.getNuCasilla());

							if (algunaRellena){
								//addActionError(Mensaje.getTexto(MensajeConstants.CASILLA_OBLIGATORIA));
								listaCasillasError.put(informacionCasilla.getNuCasilla(), Mensaje.getTexto(MensajeConstants.CASILLA_OBLIGATORIA));
								cambiarAtributo(informacionCasilla, true);
								procederConCasilla = false;
							}

						}else if (informacionCasilla.getContenidoObligatorio()!=null && !informacionCasilla.getContenidoObligatorio()){
							//OK
						}
					}
					if (procederConCasilla) {
						try{
							String valor;
							if (mcCasilla == null || mcCasilla.getValor() == null) {
								valor = "";
							} else if ("N".equals(informacionCasilla.getFormato())) {
								valor = mcCasilla.getValor().replace('.', ',');
							} else {
								valor = mcCasilla.getValor();
							}
							valor = Formato.formatearDato(valor, informacionCasilla.getFormato(), informacionCasilla.getLongitud().doubleValue(), " ");
							if (mcCasilla != null) {
								mcCasilla.setValor(valor);
							}
						}catch(Exception e){
							//addActionError(Mensaje.getTexto(Integer.valueOf(e.getMessage())));
							if (Utilidades.isNumeric(e.getMessage())) {
								listaCasillasError.put(informacionCasilla.getNuCasilla(), Mensaje.getTexto(Integer.valueOf(e.getMessage())));
							} else {
								listaCasillasError.put(informacionCasilla.getNuCasilla(), "Error: " + e.getMessage());
							}
							cambiarAtributo(informacionCasilla, true);
						}

						if (informacionCasilla.getConDatosValidos()!=null && informacionCasilla.getConDatosValidos()){
							if (informacionCasilla.getAyuda()!=null){
								boolean correcto = false;
								try{
									List<ValidacionDTO> validacionDTOs = new ArrayList<ValidacionDTO>(1);
									validacionDTOs.add(informacionCasilla.getAyuda());
									correcto = mantenimientoCasillasBO.ejecutaValidacion(mcDocumentoVO, validacionDTOs, nuHoja);
									if (!correcto){
										//addActionError("No pasa la validacion ");
										listaCasillasError.put(informacionCasilla.getNuCasilla(), "Casilla no válida");
										cambiarAtributo(informacionCasilla, true);
									}
								}catch(GadirServiceException e){
									listaCasillasError.put(informacionCasilla.getNuCasilla(), e.getMessage());
									cambiarAtributo(informacionCasilla, true);
								}catch(Exception e){
									LOG.error(e, e);
									listaCasillasError.put(informacionCasilla.getNuCasilla(), "Casilla no válida");
									cambiarAtributo(informacionCasilla, true);
								}
							}
							if (informacionCasilla.getValidaciones()!=null){
//								if(informacionCasilla.getAyuda()==null || !informacionCasilla.getAyuda().getFuncionDTO().getCoPlsql().equals(informacionCasilla.getValidacion().getFuncionDTO().getCoPlsql())){
								boolean correcto = false;
								try{
									correcto = mantenimientoCasillasBO.ejecutaValidacion(mcDocumentoVO, informacionCasilla.getValidaciones(), nuHoja);
									if (!correcto){
										//addActionError("No pasa la validacion ");
										listaCasillasError.put(informacionCasilla.getNuCasilla(), "Casilla no válida");
										cambiarAtributo(informacionCasilla, true);
									}
								}catch(GadirServiceException e){
									listaCasillasError.put(informacionCasilla.getNuCasilla(), e.getMessage());
									cambiarAtributo(informacionCasilla, true);
								}catch(Exception e){
									LOG.error(e, e);
									listaCasillasError.put(informacionCasilla.getNuCasilla(), "Casilla no válida");
									cambiarAtributo(informacionCasilla, true);
								}
//								}
							}
						}
					}
				}
			}	
		}	
	}

	public String ajaxHayCambiosHoja(){
		
		if (mcDocumentoVO==null)getDocumentoSesion();
		
		ajaxData="NO";
		
		if (!hasErrors()){
			for(Short nuCasilla:mcDocumentoVO.getInformacionCasillas().keySet()){
				MCInformacionCasillaVO informacionCasilla=mcDocumentoVO.getInformacionCasillas().get(nuCasilla);
//				if (hoja==1 || (hoja>1 && informacionCasilla.getRepeticion()!=null && informacionCasilla.getRepeticion())){
				if ((hoja==1 && !informacionCasilla.getRepeticion()) || (hoja>1 && informacionCasilla.getRepeticion())) {
					String valorNuevo=(String)getRequest().getParameter("casilla"+nuCasilla);
					if (valorNuevo!=null){
						MCCasillaVO mcCasilla = (mcDocumentoVO.getHojas().get((short)hoja)).getCasillas().get(nuCasilla);
						String valorAnterior = mcCasilla.getValor();
						if ((valorAnterior==null && !valorNuevo.isEmpty()) || 
								(valorAnterior!=null && !valorNuevo.equals(valorAnterior))){
							ajaxData="SI";
							break;
						}
					}
				}
			}
		}
		return AJAX_DATA;
	}
	
	public String ajaxGetAyudaTipoTexto(){

		if (mcDocumentoVO==null)getDocumentoSesion();

		ajaxData="";

		try{

			short casilla=Short.valueOf(getServletRequest().getParameter("method:ajaxGetAyudaTipoTexto"));
			Long coValidacion=mcDocumentoVO.getInformacionCasillas().get(casilla).getAyuda().getCoValidacion();
			String metodo=mcDocumentoVO.getInformacionCasillas().get(casilla).getAyuda().getFuncionDTO().getMetodo();

			if (!hasErrors()){

				AyudaCasillaVO ayudaCasillaVO = new AyudaCasillaVO(coModelo, coVersion, coDocumento, coValidacion);

				for(Short nuCasilla:mcDocumentoVO.getInformacionCasillas().keySet()){
					MCInformacionCasillaVO informacionCasilla=mcDocumentoVO.getInformacionCasillas().get(nuCasilla);
//					if (hoja==1 || (hoja>1 && informacionCasilla.getRepeticion()!=null && informacionCasilla.getRepeticion())){
					if ((hoja==1 && !informacionCasilla.getRepeticion()) || (hoja>1 && informacionCasilla.getRepeticion())) {
						String valorNuevo=(String)getRequest().getParameter("casilla"+nuCasilla);
						if (valorNuevo!=null){
							ayudaCasillaVO.setCasilla(nuCasilla, valorNuevo);
						}
					}
				}

				DatosSesion.setAyudaCasillaVO(ayudaCasillaVO);

				Class<?> c = Class.forName("es.dipucadiz.etir.comun.utilidades.AyudaCasillaTexto");
//				Method m = c.getMethod(metodo, null);
				Method m = c.getMethod(metodo);

//				String texto = (String)m.invoke(null, null);
				String texto = (String)m.invoke(null);
				ajaxData=texto;

			}
		}catch(Exception e){
			LOG.error("Error cargando ayuda de casilla tipo texto", e);
		}
		return AJAX_DATA;
	}
	
	public String ajaxGetAyudaTipoPantalla(){

		if (mcDocumentoVO==null)getDocumentoSesion();

		ajaxData="";

		try{
			short casilla=Short.valueOf(getServletRequest().getParameter("method:ajaxGetAyudaTipoPantalla"));
			Long coValidacion=mcDocumentoVO.getInformacionCasillas().get(casilla).getAyuda().getCoValidacion();

			if (!hasErrors()){

				AyudaCasillaVO ayudaCasillaVO = new AyudaCasillaVO(coModelo, coVersion, coDocumento, coValidacion);

				for(Short nuCasilla:mcDocumentoVO.getInformacionCasillas().keySet()){
					MCInformacionCasillaVO informacionCasilla=mcDocumentoVO.getInformacionCasillas().get(nuCasilla);
					
					short hojaDeCasilla;
					if (mcDocumentoVO.getInformacionCasillas().get(nuCasilla).getRepeticion()) {
						hojaDeCasilla = (short)hoja;
					} else {
						hojaDeCasilla = 1;
					}
					
					if ((hojaDeCasilla==1 && !informacionCasilla.getRepeticion()) || (hojaDeCasilla>1 && informacionCasilla.getRepeticion())) {
						String valorNuevo=(String)getRequest().getParameter("casilla"+nuCasilla);
						if (valorNuevo == null) {
							try
							{
								valorNuevo = mcDocumentoVO.getHojas().get((short)hojaDeCasilla).getCasillas().get(nuCasilla).getValor();
							} catch (Exception e) {}
						}
						if (valorNuevo!=null){
							ayudaCasillaVO.setCasilla(nuCasilla, valorNuevo);
						}
					}
				}
				DatosSesion.setAyudaCasillaVO(ayudaCasillaVO);
				ajaxData=mcDocumentoVO.getInformacionCasillas().get(casilla).getAyuda().getFuncionDTO().getClase();
			}
		}catch(Exception e){
			LOG.error("Error cargando ayuda de casilla tipo pantalla", e);
		}
		return AJAX_DATA;
	}
	
	public String botonHojaMenos(){
		
		getDocumentoSesion();
		
		if (hoja>1){
			hoja = hoja-1;
		}else{
			addActionError("Error bajando de hoja");
		}
		
		
		pasaValidaciones((short)hoja);
		return INPUT;
	}
	
	public String botonHojaMas(){
		
		getDocumentoSesion();

		if (!hasErrors()){
			if (hoja<mcDocumentoVO.getNumHojas()){
				hoja = hoja+1;
			}else{
				addActionError("Error subiendo de hoja");
			}
		}

		pasaValidaciones((short)hoja);
		return INPUT;
	}
	
	public String botonHojaIr() {
		getDocumentoSesion();

		if (Utilidades.isEmpty(hojaIr)) {
			addActionError("Seleccione hoja.");
		} else if (!Formato.validar(hojaIr, "N", 3)) {
			addActionError("Lo hoja introducida no es numerica.");
		}
		
		if (!hasErrors()){
			int hojaNuevaInt = Integer.valueOf(hojaIr);
			if (hojaNuevaInt >= 1 && hojaNuevaInt<=mcDocumentoVO.getNumHojas()){
				hoja = hojaNuevaInt;
			}else{
				addActionError("Hoja debe ser mayor o igual que 1 y menor o igual que " + mcDocumentoVO.getNumHojas());
			}
		}

		pasaValidaciones((short)hoja);
		return INPUT;
	}
	
	public String botonIrDocumentoOrigen() throws IOException{
		
		getDocumentoSesion();

		if (!hasErrors()){
			DocumentoDTO documentoOrigen=mcDocumentoVO.getDocumentoDTO().getDocumentoDTOByOrigenDocuni();
			if (documentoOrigen!=null){
				apilar();
				
				coModelo=documentoOrigen.getId().getCoModelo();
				coVersion=documentoOrigen.getId().getCoVersion();
				coDocumento=documentoOrigen.getId().getCoDocumento();
				hoja=1;
				operacion="CONSULTA";
				edicion=false;
				nuevoDocumento=false;
				borrarDocumento=false;
				crearHoja=false;
				eliminarHoja=false;
				guardar=false;
				calcular=false;
//				verTodoElDocumento=true;
				mostrarColumnaError=false;
				
				return execute();
			}else{
				addActionError("Error obteniendo el documento origen");
			}
			pasaValidaciones((short)hoja);
		}
		
		return INPUT;
	}

	public String botonIrDocumentoResumen() throws IOException{
		
		getDocumentoSesion();

		if (!hasErrors()){
			DocumentoDTO documentoResumen=mcDocumentoVO.getDocumentoDTO().getDocumentoDTOByResumenDocumento();
			if (documentoResumen!=null){
				apilar();
				
				coModelo=documentoResumen.getId().getCoModelo();
				coVersion=documentoResumen.getId().getCoVersion();
				coDocumento=documentoResumen.getId().getCoDocumento();
				hoja=1;
				operacion="CONSULTA";
				edicion=false;
				nuevoDocumento=false;
				borrarDocumento=false;
				crearHoja=false;
				eliminarHoja=false;
				guardar=false;
				calcular=false;
//				verTodoElDocumento=true;
				mostrarColumnaError=false;
				
				return execute();
			}else{
				addActionError("Error obteniendo el documento resumen");
			}
			pasaValidaciones((short)hoja);
		}
		
		return INPUT;
	}
	
	
	public String botonAnadirHoja(){
		
		getDocumentoSesion();

		if (!hasErrors()){
			if (mcDocumentoVO.getRepeticionHojas()!=null && mcDocumentoVO.getRepeticionHojas()){
				short nuevaHoja=mcDocumentoVO.getNumHojas();
				nuevaHoja++;
				mcDocumentoVO.getHojas().put(nuevaHoja, new MCHojaVO(nuevaHoja));
				mcDocumentoVO.setNumHojas(nuevaHoja);
				hoja = nuevaHoja;
			}else{
				addActionError("No se pueden añadir hojas al documento actual");
			}
			pasaValidaciones((short)hoja);
		}
		
		return INPUT;
	}
	
	public String botonEliminarHoja(){
		getDocumentoSesion();

		if (!hasErrors()){
			if (hoja>1 && hoja==mcDocumentoVO.getNumHojas()){
				mcDocumentoVO.getHojas().remove((short)hoja);
				mcDocumentoVO.setNumHojas((short) (mcDocumentoVO.getNumHojas() - 1));
				hoja--;
			}else{
				addActionError("No se puede eliminar la hoja seleccionada");
			}
			pasaValidaciones((short)hoja);
		}
		
		return INPUT;
	}
	
	public String botonVerTodoElDocumento(){
		
		getDocumentoSesion();
		pasaValidaciones((short)hoja);
		return INPUT;
	}
	public String botonCasillasVisibles(){
		
		getDocumentoSesion();
		pasaValidaciones((short)hoja);
		return INPUT;
	}
	public String botonCalcular(){
		incluirCalculo = true;
		return botonGuardar();
	}
	public String botonEliminar(){
		String result = INPUT;
		getDocumentoSesion();
		
		try{
			DocumentoDTO documentoDTO = mantenimientoCasillasBO.findDocumento(coModelo, coVersion, coDocumento);
			String documentoCompleto = documentoDTO.getNumeroDocumentoCompleto();
			if (!documentoDTO.getFhActualizacion().equals(mcDocumentoVO.getDocumentoDTO().getFhActualizacion())){
				addActionError(Mensaje.getTexto(MensajeConstants.EL_REGISTRO_HA_SIDO_MODIFICADO_POR_OTRO_USUARIO));
			}
			if (!hasErrors()){

				int resultadoBorrado = mantenimientoCasillasBO.borraDocumentoCompleto(mcDocumentoVO, getCoProcesoActual());
				if (resultadoBorrado == 0) {
					addActionMessage("El documento " + documentoCompleto + " se ha eliminado correctamente");
				}else{
					if(resultadoBorrado == 1) 
						addActionError("Error al borrar el documento " + documentoCompleto + ", ya que posee documento origen.");
					else 
						addActionError("Error al borrar el documento " + documentoCompleto + " (" + resultadoBorrado + ")");		
				}
				
				
				mcDocumentoVO=null;
				result = botonVolver();
			}
		}catch(Exception e){
			LOG.error("Error guardando el documento", e);
			addActionError("Error guardando el documento");
		}
		
		return result;
	}
	
	public String botonVolver() throws GadirServiceException {
		String result;
		if (existeObjetoSesion(ALTA_AUTO_CENSO)) {
			result = ALTA_AUTO_CENSO;
		} else {
			super.volverPilaRedirect();
			result = BLANK;
		}
		return result;
	}
	
	public String botonGuardarHoja(){
		getDocumentoSesion();
		if (!hasErrors()){
			guardaHojaEnSesion();
			pasaValidaciones((short)hoja);
		}
		return INPUT;
	}
	
	private void guardaHojaEnSesion(){
		for(Short nuCasilla:mcDocumentoVO.getInformacionCasillas().keySet()){
			try{
				MCInformacionCasillaVO informacionCasilla=mcDocumentoVO.getInformacionCasillas().get(nuCasilla);
				if ((hoja == 1 && !informacionCasilla.getRepeticion()) || (hoja > 1 && informacionCasilla.getRepeticion())) {
					String valorNuevo=(String)getRequest().getParameter("casilla"+nuCasilla);
					if (valorNuevo!=null){
						MCCasillaVO mcCasilla = (mcDocumentoVO.getHojas().get((short)hoja)).getCasillas().get(nuCasilla);
						String valorAnterior = mcCasilla.getValor();
						if (!valorNuevo.equals(valorAnterior)){
							mcDocumentoVO.guardaCasillaValor((short)hoja, nuCasilla, valorNuevo, mcCasilla.error);
						}
					}
				}
			}catch(Exception e){
				addActionError("Error en la actualizacion de la casilla " + nuCasilla);
				LOG.error("Error en la actualizacion de la casilla " + nuCasilla, e);
			}
		}
	}
	
	public String botonGuardar(){
		String result = INPUT;
		actionMessage = null;
		getDocumentoSesion();
		guardaHojaEnSesion();
		
		if (!hasErrors()){
			
			for (short i=1; i<=mcDocumentoVO.getNumHojas(); i++){
				pasaValidaciones(i);
				if (hojaPrimerError < 0 && !listaCasillasError.isEmpty()) {
					hojaPrimerError = i;
				}
			}

			if (listaCasillasError.isEmpty() || OPERACION_CONTROL.equals(operacion)){
				try{
					DocumentoDTO documentoDTO = mantenimientoCasillasBO.findDocumento(coModelo, coVersion, coDocumento);
					String documentoCompleto = documentoDTO.getNumeroDocumentoCompleto();
					if (!documentoDTO.getFhActualizacion().equals(mcDocumentoVO.getDocumentoDTO().getFhActualizacion())){
						addActionError(Mensaje.getTexto(MensajeConstants.EL_REGISTRO_HA_SIDO_MODIFICADO_POR_OTRO_USUARIO));
					}
					if (!hasErrors()){

						mantenimientoCasillasBO.saveDocumentoCompleto(mcDocumentoVO, getCoProcesoActual());
						actionMessage = "El documento " + documentoCompleto + " se ha guardado correctamente";
						boolean volverPila = false;
						boolean incluirCorrespondencia = !OPERACION_CONTROL.equals(operacion);
						
						try {
							String estado = pasarValidacionesCorrespondencias(documentoCompleto, incluirCorrespondencia);
 							boolean valido = DOCUMENTO_EN_BASE_DE_DATOS.equals(estado);
//							boolean valido = DOCUMENTO_EN_BASE_DE_DATOS.equals(estado) || "windows".equals(GadirConfig.leerParametro("entorno.tipo.sistema"));
							if (valido || (DOCUMENTO_PDTE_CORRES.equals(estado) && !incluirCorrespondencia)) {
								volverPila = true;
							}
							boolean calculoCorrecto = false;
							String errorEnCalculo = null;
							if (valido && incluirCalculo) {
								String tipo = "R";
//								if (IncidenciaConstants.CO_INCIDENCIA_IMPRIMIR_DIPTICO_DIRECTO.equals(ejecutaIncidencia)) {
								if (Utilidades.isNotEmpty(ejecutaIncidencia)) {
									tipo = "P"; // Para preparar el cargo para la posterior impresión.
								}
								Map<String, Object> resultCalculo = calculoManualDocumentoBO.execute(coModelo, coVersion, coDocumento, tipo, getCoProcesoActual());
								if (resultCalculo == null) {
				        			addActionError("Error no controlado en el calculo.");
								} else {
									Integer resultado = (Integer) resultCalculo.get("resultado");
					        		if (resultado == null || resultado.intValue() != 0) {
					        			Integer coMensajeError = (Integer) resultCalculo.get("coMensajeError");
						        		if (coMensajeError != null) {
							        		String variable = (String) resultCalculo.get("variable");
						        			addActionError(Mensaje.getTexto(coMensajeError.intValue(), variable));
						        		} else {
						        			if (resultado != null && resultado.intValue() > 100000) {
						        				errorEnCalculo = "Error en cálculo: " + super.getText("error.calculo." + resultado.intValue());
						        				addActionError(errorEnCalculo);
						        			} else {
						        				addActionError(Mensaje.getTexto(MensajeConstants.V1, "Error desconocido en el calculo del documento."));
						        			}
						        		}
					        			primeraCarga();
					        		} else {
					        			if (resultado != null) {
					        				Integer coMensajeError = (Integer) resultCalculo.get("coMensajeError");
							        		if (coMensajeError != null && coMensajeError != 0) {
								        		String variable = (String) resultCalculo.get("variable");
							        			addActionError(Mensaje.getTexto(coMensajeError.intValue(), variable));
							        		}
					        			}
					        			if (!hasErrors()) {
					        				actionMessage = "El documento " + documentoCompleto + " ha pasado el cálculo correctamente.";
					        				// Ejecutar incidencia, si hubiera alguna seleccionada
					        				if (Utilidades.isNotEmpty(ejecutaIncidencia) && ejecutaIncidencia.length() > 1) {
					        					KeyValue resultadoIncidencia = OpcionesIncidenciaSituacionUtil.ejecutarIncidencia(coModelo, coVersion, coDocumento, ejecutaIncidencia, null, null, null);
					        					if (IncidenciaConstants.RESULTADO_ERROR.equals(resultadoIncidencia.getKey())) {
					        						addActionError(resultadoIncidencia.getValue());
					        					}
					        				}
					        			}
					        			calculoCorrecto = true;
					        		}
								}
							}
							if (incluirCalculo) {
								if (!DOCUMENTO_EN_BASE_DE_DATOS.equals(estado) || !calculoCorrecto) {
									String coIncidencia = null;
									String observaciones = null;
									if (OPERACION_ALTA.equals(operacion)) coIncidencia = IncidenciaConstants.CO_INCIDENCIA_DOC_GENERAR_LIQUIDACION;
									if (OPERACION_MODIFICA.equals(operacion)) coIncidencia = IncidenciaConstants.CO_INCIDENCIA_DOC_RECALCULO_LIQUIDACION;
									if (DOCUMENTO_FALLO_EN_CORRES.equals(estado)) {
										observaciones = "Error en correspondencia.";
									} else if (incluirCalculo && !calculoCorrecto) {
										if (!Utilidades.isEmpty(errorEnCalculo)) {
											observaciones = errorEnCalculo;
										} else {
											observaciones = "Error en cálculo.";
										}
									}
									
									Map<String, Object> resultIncidencia = controlIncidenciaBO.execute(coModelo, coVersion, coDocumento, coIncidencia, observaciones);
									if (resultIncidencia == null) {
					        			addActionError("Error no controlado en control de incidencia.");
									} else {
										BigDecimal resultado = (BigDecimal) resultIncidencia.get("resultado");
						        		if (resultado == null || resultado.intValue() != 0) {
							        		BigDecimal coMensajeError = (BigDecimal) resultIncidencia.get("coMensajeError");
							        		if (coMensajeError != null) {
								        		String variable = (String) resultIncidencia.get("variable");
							        			addActionError(Mensaje.getTexto(coMensajeError.intValue(), variable));
							        		} else {
							        			addActionError(Mensaje.getTexto(MensajeConstants.V1, "Error desconocido en control de incidencia."));
							        		}
						        		}
									}
									volverPila = false;
								} else {
									if (OPERACION_ALTA.equals(operacion)) {
//										// Para cualquier documento
//										if (OPERACION_ALTA.equals(operacion) && "L".equals(tipoModelo) && ("N".equals(subtipoModelo) || "I".equals(subtipoModelo))) {
										// Sí, es una liquidación y estamos en el alta.
										List<CasillasRelacionadasDTO> destinos = casillasRelacionadasBO.findRelacionesDestino(
												documentoDTO.getMunicipioDTO().getId().getCoProvincia(),
												documentoDTO.getMunicipioDTO().getId().getCoMunicipio(),
												documentoDTO.getConceptoDTO().getCoConcepto(),
												documentoDTO.getId().getCoModelo(),
												documentoDTO.getId().getCoVersion(),
												documentoDTO.getMunicipioDTO().getId().getCoProvincia(),
												documentoDTO.getMunicipioDTO().getId().getCoMunicipio()
										);

										if (!destinos.isEmpty()) {
											// Sí, existen relaciones
											List<Map<String, Object>> listaDestinos = new ArrayList<Map<String, Object>>();
											Map<String, String> tiposDeModelo = new HashMap<String, String>();
											for (CasillasRelacionadasDTO destino : destinos) {
												// Modelo es de tipo censo?
												String coModeloDestino = destino.getCoModeloRelacionada();
												String tipoSubtipo;
												if (tiposDeModelo.containsKey(coModeloDestino)) {
													tipoSubtipo = tiposDeModelo.get(coModeloDestino);
												} else {
													ModeloDTO modeloDestinoDTO = modeloBO.findById(coModeloDestino);
													tipoSubtipo = modeloDestinoDTO.getTipo() + "|" + modeloDestinoDTO.getSubtipo();
													tiposDeModelo.put(coModeloDestino, tipoSubtipo);
												}
												if ("T|L".equals(tipoSubtipo) || "T|F".equals(tipoSubtipo)) {
													// Sí, el modelo es censo
													String coProvinciaDestino = destino.getCoProvinciaRelacionada();
													String coMunicipioDestino = destino.getCoMunicipioRelacionada();
													if (ProvinciaConstants.CO_PROVINCIA_GENERICO.equals(coProvinciaDestino)) {
														coProvinciaDestino = documentoDTO.getMunicipioDTO().getId().getCoProvincia();
													}
													if (MunicipioConstants.CO_MUNICIPIO_GENERICO.equals(coMunicipioDestino)) {
														coMunicipioDestino = documentoDTO.getMunicipioDTO().getId().getCoMunicipio();
													}
													
													boolean existeCasillasMunicipios = casillaMunicipioBO.existeOperacion(
															coProvinciaDestino, 
															coMunicipioDestino, 
															destino.getCoConceptoRelacionada(), 
															coModeloDestino, 
															destino.getCoVersionRelacionada(), 
															OPERACION_ALTA);
													
													if (existeCasillasMunicipios) {
														Map<String, Object> posibleDestino = new HashMap<String, Object>();
														posibleDestino.put("coProvincia", coProvinciaDestino);
														posibleDestino.put("coMunicipio", coMunicipioDestino);
														posibleDestino.put("coConcepto", destino.getCoConceptoRelacionada());
														posibleDestino.put("coModelo", coModeloDestino);
														posibleDestino.put("coVersion", destino.getCoVersionRelacionada());
														posibleDestino.put("seleccionado", Boolean.FALSE);
														posibleDestino.put("tratado", Boolean.FALSE);
														if (!listaDestinos.contains(posibleDestino)) {
															listaDestinos.add(posibleDestino);
														}
													}
												}
											}
											if (!listaDestinos.isEmpty()) {
												// Sí, algunas relaciones eran con destino censo y origen liquidación
												Map<String, Object> altaAutomaticaEnCenso = new HashMap<String, Object>();
												altaAutomaticaEnCenso.put("documentoOrigen", documentoDTO.getId());
												altaAutomaticaEnCenso.put("destinos", listaDestinos);
												setObjetoSesion(ALTA_AUTO_CENSO, altaAutomaticaEnCenso);
												seleccionarDestinos = true;
											}
										}
									}
								}
							}
						} catch (Exception e) {
							primeraCarga(); // Hay errores posteriores al guardado del documento, hay que volver a recuperar el documento de base de datos (fhActualizacion etc.)
							throw e;
						}
						if (volverPila) {
							result = botonVolver();
						} else {
							primeraCarga();
							if (!listaCasillasError.isEmpty()) {
								listaCasillasError.clear();
								pasaValidaciones((short)hoja, true);
							}
						}
					}
				}catch(GadirServiceException e){
					LOG.error("Error guardando el documento", e);
					addActionError("Error guardando el documento: " + e.getMensaje());
				}catch(Exception e){
					LOG.error("Error guardando el documento", e);
					addActionError("Error guardando el documento.");
				}

			}else{
				if (hoja != hojaPrimerError) {
					hojaIr = Short.toString(hojaPrimerError);
					botonHojaIr();
				}
				addActionError("No se puede guardar el documento porque hay casillas con datos incorrectos" + (hojaPrimerError > 1 ? " en la hoja " + hojaPrimerError : "") + ".");
			}
			
		}
		
		if (mostrarMensajes && !hasErrors() && Utilidades.isNotEmpty(actionMessage)) {
			addActionMessage(actionMessage); // Sólo se mostrará el último mensaje grabado.
		}
		
		return result;
	}
	
	private String pasarValidacionesCorrespondencias(String documentoCompleto, boolean incluirCorrespondencia) {
		String estado;
		if (G5114AbstractAction.OPERACION_GRABAR_CASILLAS.equals(operacion)) {
			estado = estadoDocumento(coModelo, coVersion, coDocumento);
		} else {
			AccesoPlantillaVO accesoPlantillaVO = new AccesoPlantillaVO();
			accesoPlantillaVO.setCoProvincia(mcDocumentoVO.getDocumentoDTO().getMunicipioDTO().getId().getCoProvincia());
			accesoPlantillaVO.setCoMunicipio(mcDocumentoVO.getDocumentoDTO().getMunicipioDTO().getId().getCoMunicipio());
			accesoPlantillaVO.setCoConcepto(mcDocumentoVO.getDocumentoDTO().getConceptoDTO().getCoConcepto());
			accesoPlantillaVO.setCoModelo(mcDocumentoVO.getDocumentoDTO().getId().getCoModelo());
			accesoPlantillaVO.setCoVersion(mcDocumentoVO.getDocumentoDTO().getId().getCoVersion());
			List<String> parametros = new ArrayList<String>();
			parametros.add("");
			parametros.add("");
			parametros.add("");
			parametros.add(coModelo);
			parametros.add(coVersion);
			parametros.add(coDocumento);
			int codretValid;
			try {
				codretValid = Batch.ejecutar(Batch.CO_PROCESO_VALIDACION, parametros, accesoPlantillaVO);
			} catch (GadirServiceException e) {
				LOG.error(e.getMensaje(), e);
				if (e.getMensaje() != null) {
					addActionError(e.getMensaje());
				} else {
					addActionError(e.getMessage());
				}
				codretValid = -1;
			}
			estado = estadoDocumento(coModelo, coVersion, coDocumento);
			if (codretValid == 0 && DOCUMENTO_PDTE_CORRES.equals(estado)) {
				actionMessage = "El documento " + documentoCompleto + " ha pasado las validaciones correctamente.";
				if (incluirCorrespondencia) {
					int codretCorres;
					try {
						codretCorres = Batch.ejecutar(Batch.CO_PROCESO_CORRESPONDENCIAS, parametros, accesoPlantillaVO);
					} catch (GadirServiceException e) {
						LOG.error(e.getMensaje(), e);
						addActionError(e.getMensaje());
						codretCorres = -1;
					}
					estado = estadoDocumento(coModelo, coVersion, coDocumento);
					if (codretCorres == 0 && DOCUMENTO_EN_BASE_DE_DATOS.equals(estado)) {
						actionMessage = "El documento " + documentoCompleto + " ha ejecutado correspondencias correctamente.";
					} else {
						if (codretValid > 0) addActionError(Mensaje.getTexto(codretCorres));
						addActionError("El documento " + documentoCompleto + " no ha ejecutado correspondencias correctamente, consulte la cola de procesos.");
					}
				}
			} else {
				if (codretValid > 0) addActionError(Mensaje.getTexto(codretValid));
				addActionError("El documento " + documentoCompleto + " no ha pasado las validaciones correctamente, consulte la cola de procesos.");
			}
		}
		return estado;
	}
	
	private String estadoDocumento(String coModelo, String coVersion, String coDocumento) {
		String resultado = null;
		try {
			resultado = mantenimientoCasillasBO.findDocumento(coModelo, coVersion, coDocumento).getEstado();
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
		}
		return resultado;
	}

	public String botonPedirDocOrigen() throws GadirServiceException {
		List<String> errores = new ArrayList<String>(); // Para que execute() no se de cuenta de los errors y así cargar el documento bien.
		getDocumentoSesion();
		if ((Utilidades.isNotEmpty(coModeloPedirDocOrigen) || Utilidades.isNotEmpty(coVersionPedirDocOrigen) || Utilidades.isNotEmpty(coDocumentoPedirDocOrigen)) &&
				(Utilidades.isEmpty(coModeloPedirDocOrigen) || Utilidades.isEmpty(coVersionPedirDocOrigen) || Utilidades.isEmpty(coDocumentoPedirDocOrigen))) {
			errores.add(Mensaje.getTexto(MensajeConstants.VALOR_CAMPO_INCORRECTO));
		} else if(coModelo.equals(coModeloPedirDocOrigen) && coVersion.equals(coVersionPedirDocOrigen) && coDocumento.equals(coDocumentoPedirDocOrigen)){
			errores.add("No se puede poner como documento origen el documento seleccionado");
		}else{
			if (Utilidades.isNotEmpty(coDocumentoPedirDocOrigen)) {	
				coModeloPedirDocOrigen = Utilidades.normalizarCoModelo(coModeloPedirDocOrigen);
				coVersionPedirDocOrigen = Utilidades.normalizarCoVersion(coVersionPedirDocOrigen);
				coDocumentoPedirDocOrigen = Utilidades.normalizarCoDocumento(coDocumentoPedirDocOrigen);
				//Validar el documento origen
				CasillasRelacionadasBO casillasRelacionadasBO = (CasillasRelacionadasBO) GadirConfig.getBean("casillasRelacionadasBO");
				DocumentoBO documentoBO = (DocumentoBO) GadirConfig.getBean("documentoBO");
				String coMuni = mcDocumentoVO.getDocumentoDTO().getMunicipioDTO().getId().getCoMunicipio();
				String coProv = mcDocumentoVO.getDocumentoDTO().getMunicipioDTO().getId().getCoProvincia();
				String coConcepto = mcDocumentoVO.getDocumentoDTO().getConceptoDTO().getCoConcepto();
				String coModelo = mcDocumentoVO.getDocumentoDTO().getId().getCoModelo();
				String coVersion = mcDocumentoVO.getDocumentoDTO().getId().getCoVersion();
				boolean modelosCompatibles = true;
				if (!coModeloPedirDocOrigen.equals(coModelo) || !coVersionPedirDocOrigen.equals(coVersion)) {
					if (!casillasRelacionadasBO.isRelacionados(coMuni, coProv, coConcepto, coModelo, coVersion, coModeloPedirDocOrigen, coVersionPedirDocOrigen)) {
						errores.add(Mensaje.getTexto(MensajeConstants.V1, "El modelo origen no está relacionado con el modelo a dar de alta."));
						modelosCompatibles = false;
					}
				}
				if(modelosCompatibles) {
					DocumentoDTO documentoOrigenDTO = documentoBO.findById(new DocumentoDTOId(coModeloPedirDocOrigen, coVersionPedirDocOrigen, coDocumentoPedirDocOrigen));
					if (documentoOrigenDTO == null) {
						errores.add(Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "El documento origen"));
					} else if (documentoOrigenDTO.getMunicipioDTO() == null ||
	    					!coProv.equals(documentoOrigenDTO.getMunicipioDTO().getId().getCoProvincia()) || 
	    					!coMuni.equals(documentoOrigenDTO.getMunicipioDTO().getId().getCoMunicipio())) {
						errores.add(Mensaje.getTexto(MensajeConstants.V1, "El documento origen pertenece a otro municipio."));
	    			}
				}
			}
		}
		
		if (errores.isEmpty() && Utilidades.isNotEmpty(coDocumentoPedirDocOrigen)) {
			//Llamar al PLcito de actualización con documento origen.
			ActualizarConDocOrigenBO actualizarConDocOrigenBO = (ActualizarConDocOrigenBO) GadirConfig.getBean("actualizarConDocOrigenBO");
			Map<String, Object> result = actualizarConDocOrigenBO.execute(mcDocumentoVO.getDocumentoDTO().getId().getCoModelo(), mcDocumentoVO.getDocumentoDTO().getId().getCoVersion(), mcDocumentoVO.getDocumentoDTO().getId().getCoDocumento(), coModeloPedirDocOrigen, coVersionPedirDocOrigen, coDocumentoPedirDocOrigen, getCoProcesoActual());
			if (result == null) {
				errores.add("Error desconocido al actualizar el documento con documento origen.");
			} else {
				int resultado = ((Integer) result.get("resultado")).intValue();
				if (resultado != 0) {
					LOG.error("actualizarConDocOrigenBO ha fallado con resultado: " + resultado);
					int coMensajeError = ((Integer) result.get("coMensajeError")).intValue();
					String variable = (String) result.get("variable");
					errores.add(Mensaje.getTexto(coMensajeError, variable));
				} else {
					addActionMessage("El documento ha sido actualizado correctamente con el documento origen.");
				}
			}
		}
		
		String result = null;
		if (errores.isEmpty()) {
			try {
				String donde = "mantenimientoCasillas.action";
				donde += "?coModelo=" + mcDocumentoVO.getDocumentoDTO().getId().getCoModelo();
				donde += "&coVersion=" + mcDocumentoVO.getDocumentoDTO().getId().getCoVersion();
				donde += "&coDocumento=" + mcDocumentoVO.getDocumentoDTO().getId().getCoDocumento();
				donde += "&operacion=" + operacion;
				donde += "&edicion=" + edicion;
				donde += "&nuevoDocumento=" + nuevoDocumento;
				donde += "&borrarDocumento=" + borrarDocumento;
				donde += "&crearHoja=" + crearHoja;
				donde += "&eliminarHoja=" + eliminarHoja;
				donde += "&guardar=" + guardar;
				donde += "&calcular=" + calcular;
//				donde += "&verTodoElDocumento=" + verTodoElDocumento;
				donde += "&mostrarColumnaError=" + mostrarColumnaError;
				donde += "&pedirDocOrigen=false";
				getServletResponse().sendRedirect(donde);
				result = BLANK;
			} catch (IOException e) {
				e.printStackTrace();
				errores.add("Error yendo al mantenimiento de casillas. (" + e.getMessage() + ")");
			}
		}
		if (!errores.isEmpty()) {
			result = execute();
		}
		
		for (Iterator<String> iterator = errores.iterator(); iterator.hasNext();) {
			addFieldError("coModeloPedirDocOrigen", iterator.next());
		}
		
		return result;
	}

	public String botonAnularPedirDocOrigen() {
		coModeloPedirDocOrigen = "";
		coVersionPedirDocOrigen = "";
		coDocumentoPedirDocOrigen = "";
		return execute();
	}
	
	public String botonCrearHojas() {
		String result = botonGuardarHoja();
		if (!hasErrors()) {
			try {
				Map<String, Object> ejecucion = crearHojasBO.execute(
						mcDocumentoVO.getDocumentoDTO().getId().getCoModelo(), 
						mcDocumentoVO.getDocumentoDTO().getId().getCoVersion(), 
						mcDocumentoVO.getDocumentoDTO().getId().getCoDocumento(), 
						mcDocumentoVO.getHojas().get(CasillaConstants.PRIMERA_HOJA).getCasillas().get(CasillaConstants.NU_CASILLA_INICIO_LIQUIDACION).getValor(), 
						mcDocumentoVO.getHojas().get(CasillaConstants.PRIMERA_HOJA).getCasillas().get(CasillaConstants.NU_CASILLA_FIN_LIQUIDACION).getValor(),
						getCoProcesoActual());
				int resultado = ((Integer)ejecucion.get("resultado")).intValue();
				if (resultado != 0) {
					int coMensajeError = ((Integer)ejecucion.get("coMensajeError")).intValue();
					String variable = (String)ejecucion.get("variable");
					addActionError(Mensaje.getTexto(coMensajeError, variable));
				} else {
					mantenimientoCasillasBO.cargarHojasRepeticion(mcDocumentoVO);
					ponerInicioFinLiquidacion();
					calcularCssBotonesGuardar();
				}
			} catch (Exception e) {
				e.printStackTrace();
				addActionError("Error al crear hojas. (" + e.getMessage() + ")");
			}
		}
		if (!hasErrors()) {
			result = botonCalcular();
		}
		return result;
	}

	
 

	public CasillaMunicipioBO getCasillaMunicipioBO() {
		return casillaMunicipioBO;
	}

	public void setCasillaMunicipioBO(CasillaMunicipioBO casillaMunicipioBO) {
		this.casillaMunicipioBO = casillaMunicipioBO;
	}

	public List<Short> getListaHojas() {
		List<Short> result = new ArrayList<Short>(mcDocumentoVO.getNumHojas());
		for (short i=1; i<=mcDocumentoVO.getNumHojas(); i++) {
			if (hoja != i) {
				result.add(i);
			}
		}
		return result;
	}
	
	public boolean getDocumentoSesion(){
		mcDocumentoVO=(MCDocumentoVO)getObjetoSesion("mcDocumentoVO");

		if (mcDocumentoVO!=null){
			if (!mcDocumentoVO.getDocumentoDTO().getId().getCoModelo().equals(coModelo) ||
					!mcDocumentoVO.getDocumentoDTO().getId().getCoVersion().equals(coVersion) || 
					!mcDocumentoVO.getDocumentoDTO().getId().getCoDocumento().equals(coDocumento)){
				mcDocumentoVO=null;
			}
		}

		if (mcDocumentoVO==null){
			//errorTotal=true;
			return false;
		} else {
			calcularCssBotonesGuardar();
		}
		return true;
	}

	
	private String getMostrarValue(String objeto) {
		String result = (String) getObjetoSesion(objeto);
		if (!"false".equals(result)) result = "true";
		return result;
	}
	
	public String getMostrarCliente() {
		return getMostrarValue("mostrarCliente");
	}

	public void setMostrarCliente(String mostrarCliente) {
		setObjetoSesion("mostrarCliente", mostrarCliente);
	}

	public String isMostrarDomicilio() {
		return getMostrarValue("mostrarDomicilio");
	}

	public void setMostrarDomicilio(String mostrarDomicilio) {
		setObjetoSesion("mostrarDomicilio", mostrarDomicilio);
	}

	public String isMostrarDatos() {
		return getMostrarValue("mostrarDatos");
	}

	public void setMostrarDatos(String mostrarDatos) {
		setObjetoSesion("mostrarDatos", mostrarDatos);
	}
	

	@SuppressWarnings({ "deprecation" })
	public String botonImprimirTablas() throws GadirServiceException{
		
		try{
			
			getDocumentoSesion();
			
			DomicilioDTO domicilioDTO;
			ClienteDTO clienteDTO;
			UnidadUrbanaDTO unidadUrbanaDTO;
			CalleDTO calleDTO;
			MunicipioDTO municipioDTO;
			ProvinciaDTO provinciaDTO;
			SituacionDTO situacionDTO;
			ConceptoDTO conceptoDTO;
			
			List<KeyValue> listaEtiquetas = new ArrayList<KeyValue>();
			listaEtiquetas.add(new KeyValue("CABEZA"));
			listaEtiquetas.add(new KeyValue("TITUL", "MANTENIMIENTO DE CASILLAS"));
			listaEtiquetas.add(new KeyValue("CUERPO"));
			
			//DATOS CLIENTE
			if(mcDocumentoVO.getDocumentoDTO().getClienteDTO() != null){
				clienteDTO = clienteBO.findById(mcDocumentoVO.getDocumentoDTO().getClienteDTO().getCoCliente());
			
			
				listaEtiquetas.add(new KeyValue("NOMBR", clienteDTO.getRazonSocial()));
				listaEtiquetas.add(new KeyValue("IDENT", clienteDTO.getIdentificador()));
				listaEtiquetas.add(new KeyValue("CODIG", Long.toString(clienteDTO.getCoCliente())));
			
				domicilioDTO = domicilioBO.findById(Long.valueOf(mcDocumentoVO.getDocumentoDTO().getDomicilioDTOByCoDomicilioFiscal().getCoDomicilio()));
				
				unidadUrbanaDTO = unidadUrbanaBO.findById(domicilioDTO.getUnidadUrbanaDTO().getCoUnidadUrbana());
				calleDTO = calleBO.findById(unidadUrbanaDTO.getCalleDTO().getCoCalle());
				municipioDTO = municipioBO.findById(calleDTO.getMunicipioDTO().getId());
				provinciaDTO = provinciaBO.findById(municipioDTO.getId().getCoProvincia());
				
				listaEtiquetas.add(new KeyValue("VIA__", calleDTO.getSigla() + " " + calleDTO.getNombreCalle()));
				
				if (unidadUrbanaDTO.getNumero()!=null)
					listaEtiquetas.add(new KeyValue("NUMER", Integer.toString(unidadUrbanaDTO.getNumero())));
				else
					listaEtiquetas.add(new KeyValue("NUMER", ""));
				
				if (unidadUrbanaDTO.getLetra()!=null)
					listaEtiquetas.add(new KeyValue("LETRA", unidadUrbanaDTO.getLetra()));
				else
					listaEtiquetas.add(new KeyValue("LETRA", ""));
				
				if (unidadUrbanaDTO.getPlanta()!=null)
					listaEtiquetas.add(new KeyValue("PLANT", unidadUrbanaDTO.getPlanta()));
				else
					listaEtiquetas.add(new KeyValue("PLANT", ""));
				
				if (unidadUrbanaDTO.getBloque()!=null)
					listaEtiquetas.add(new KeyValue("BLOQ_", unidadUrbanaDTO.getBloque()));
				else
					listaEtiquetas.add(new KeyValue("BLOQ_", ""));
				
				if (unidadUrbanaDTO.getKm()!=null)
					listaEtiquetas.add(new KeyValue("KILOM", unidadUrbanaDTO.getKm().toString()));
				else
					listaEtiquetas.add(new KeyValue("KILOM", ""));
				
				if (unidadUrbanaDTO.getEscalera()!=null)
					listaEtiquetas.add(new KeyValue("ESCAL", unidadUrbanaDTO.getEscalera()));
				else
					listaEtiquetas.add(new KeyValue("ESCAL", ""));
				
				if (unidadUrbanaDTO.getPuerta()!=null)
					listaEtiquetas.add(new KeyValue("PUERT", unidadUrbanaDTO.getPuerta()));
				else
					listaEtiquetas.add(new KeyValue("PUERT", ""));
				
				if (unidadUrbanaDTO.getCp()!=null && unidadUrbanaDTO.getCp().intValue()!=0)
					listaEtiquetas.add(new KeyValue("CODPO", Integer.toString(unidadUrbanaDTO.getCp())));
				else
					listaEtiquetas.add(new KeyValue("CODPO", ""));	
				
				listaEtiquetas.add(new KeyValue("MUNIC", municipioDTO.getCodigoDescripcion()));
				listaEtiquetas.add(new KeyValue("PROVI", provinciaDTO.getNombre()));
			}
			else{
				listaEtiquetas.add(new KeyValue("NOMBR", ""));
				listaEtiquetas.add(new KeyValue("IDENT", ""));
				listaEtiquetas.add(new KeyValue("CODIG", ""));
				listaEtiquetas.add(new KeyValue("VIA__", ""));
				listaEtiquetas.add(new KeyValue("NUMER", ""));
				listaEtiquetas.add(new KeyValue("LETRA", ""));
				listaEtiquetas.add(new KeyValue("PLANT", ""));
				listaEtiquetas.add(new KeyValue("BLOQ_", ""));
				listaEtiquetas.add(new KeyValue("KILOM", ""));
				listaEtiquetas.add(new KeyValue("ESCAL", ""));
				listaEtiquetas.add(new KeyValue("PUERT", ""));
				listaEtiquetas.add(new KeyValue("CODPO", ""));
				listaEtiquetas.add(new KeyValue("PROVI", ""));
				listaEtiquetas.add(new KeyValue("MUNIC", ""));
			}
			
			
			//DOMICILIO TRIBUTARIO
			
			if(mcDocumentoVO.getDocumentoDTO().getDomicilioDTOByCoDomicilio()!=null){
				domicilioDTO = null;
				unidadUrbanaDTO = null;
				calleDTO = null;
				municipioDTO = null;
				provinciaDTO = null;
				
				domicilioDTO = domicilioBO.findById(Long.valueOf(mcDocumentoVO.getDocumentoDTO().getDomicilioDTOByCoDomicilio().getCoDomicilio()));
				unidadUrbanaDTO = unidadUrbanaBO.findById(domicilioDTO.getUnidadUrbanaDTO().getCoUnidadUrbana());
				calleDTO = calleBO.findById(unidadUrbanaDTO.getCalleDTO().getCoCalle());
				municipioDTO = municipioBO.findById(calleDTO.getMunicipioDTO().getId());
				provinciaDTO = provinciaBO.findById(municipioDTO.getId().getCoProvincia());
				
				listaEtiquetas.add(new KeyValue("VIA_T", calleDTO.getSigla() + " " + calleDTO.getNombreCalle()));
				
				if (unidadUrbanaDTO.getNumero()!=null)
					listaEtiquetas.add(new KeyValue("NUM_T", Integer.toString(unidadUrbanaDTO.getNumero())));
				else
					listaEtiquetas.add(new KeyValue("NUM_T", ""));
				
				if (unidadUrbanaDTO.getLetra()!=null)
					listaEtiquetas.add(new KeyValue("LET_T", unidadUrbanaDTO.getLetra()));
				else
					listaEtiquetas.add(new KeyValue("LET_T", ""));
				
				if (unidadUrbanaDTO.getPlanta()!=null)
					listaEtiquetas.add(new KeyValue("PLA_T", unidadUrbanaDTO.getPlanta()));
				else
					listaEtiquetas.add(new KeyValue("PLA_T", ""));
				
				if (unidadUrbanaDTO.getBloque()!=null)
					listaEtiquetas.add(new KeyValue("BLO_T", unidadUrbanaDTO.getBloque()));
				else
					listaEtiquetas.add(new KeyValue("BLO_T", ""));
				
				if (unidadUrbanaDTO.getKm()!=null)
					listaEtiquetas.add(new KeyValue("KIL_T", unidadUrbanaDTO.getKm().toString()));
				else
					listaEtiquetas.add(new KeyValue("KIL_T", ""));
				
				if (unidadUrbanaDTO.getEscalera()!=null)
					listaEtiquetas.add(new KeyValue("ESC_T", unidadUrbanaDTO.getEscalera()));
				else
					listaEtiquetas.add(new KeyValue("ESC_T", ""));
				
				if (unidadUrbanaDTO.getPuerta()!=null)
					listaEtiquetas.add(new KeyValue("PUE_T", unidadUrbanaDTO.getPuerta()));
				else
					listaEtiquetas.add(new KeyValue("PUE_T", ""));
				
				if (unidadUrbanaDTO.getCp()!=null && unidadUrbanaDTO.getCp().intValue()!=0)
					listaEtiquetas.add(new KeyValue("COD_T", Integer.toString(unidadUrbanaDTO.getCp())));
				else
					listaEtiquetas.add(new KeyValue("COD_T", ""));	
				
				listaEtiquetas.add(new KeyValue("MUN_T", municipioDTO.getCodigoDescripcion()));
				listaEtiquetas.add(new KeyValue("PRO_T", provinciaDTO.getNombre()));
				
				if(mcDocumentoVO.getDocumentoDTO().getRefCatastral()!=null)
					listaEtiquetas.add(new KeyValue("REFCA", mcDocumentoVO.getDocumentoDTO().getRefCatastral()));
				else
					listaEtiquetas.add(new KeyValue("REFCA",""));
			}
			else{
				listaEtiquetas.add(new KeyValue("VIA_T", ""));
				listaEtiquetas.add(new KeyValue("NUM_T", ""));
				listaEtiquetas.add(new KeyValue("LET_T", ""));
				listaEtiquetas.add(new KeyValue("PLA_T", ""));
				listaEtiquetas.add(new KeyValue("BLO_T", ""));
				listaEtiquetas.add(new KeyValue("KIL_T", ""));
				listaEtiquetas.add(new KeyValue("ESC_T", ""));
				listaEtiquetas.add(new KeyValue("PUE_T", ""));
				listaEtiquetas.add(new KeyValue("COD_T", ""));
				listaEtiquetas.add(new KeyValue("PRO_T", ""));
				listaEtiquetas.add(new KeyValue("MUN_T", ""));
				listaEtiquetas.add(new KeyValue("REFCA", ""));
			}
			
			//DATOS TRIBUTARIOS
			
			if(mcDocumentoVO.getDocumentoDTO().getRefObjTributario1()!=null)
				listaEtiquetas.add(new KeyValue("REFTR", mcDocumentoVO.getDocumentoDTO().getRefObjTributario1()));
			else
				listaEtiquetas.add(new KeyValue("REFTR", ""));
			
			if(mcDocumentoVO.getDocumentoDTO().getPeriodo()!=null)
				listaEtiquetas.add(new KeyValue("PERIO", TablaGt.getCodigoDescripcion(TablaGt.TABLA_PERIODOS_GADIR, mcDocumentoVO.getDocumentoDTO().getPeriodo()).getCodigoDescripcion()));
			else
				listaEtiquetas.add(new KeyValue("PERIO", ""));
			
			if(mcDocumentoVO.getDocumentoDTO().getEjercicio()!=null)
				listaEtiquetas.add(new KeyValue("EJERC", Short.toString(mcDocumentoVO.getDocumentoDTO().getEjercicio())));
			else
				listaEtiquetas.add(new KeyValue("EJERC", ""));
			
			if(mcDocumentoVO.getDocumentoDTO().getSituacionDTO() != null){
				situacionDTO = situacionBO.findById(mcDocumentoVO.getDocumentoDTO().getSituacionDTO().getCoSituacion());
				listaEtiquetas.add(new KeyValue("SITUA", situacionDTO.getCodigoDescripcion()));
			}
			else
				listaEtiquetas.add(new KeyValue("SITUA", ""));
			
			if(mcDocumentoVO.getDocumentoDTO().getConceptoDTO() != null){
				conceptoDTO = conceptoBO.findById(mcDocumentoVO.getDocumentoDTO().getConceptoDTO().getCoConcepto());
				listaEtiquetas.add(new KeyValue("CONCP", conceptoDTO.getCodigoDescripcion()));
			}
			else
				listaEtiquetas.add(new KeyValue("CONCP", ""));
			
			if(mcDocumentoVO.getDocumentoDTO().getMunicipioDTO() != null){
				municipioDTO = municipioBO.findById(mcDocumentoVO.getDocumentoDTO().getMunicipioDTO().getId());
				listaEtiquetas.add(new KeyValue("MUNDT", municipioDTO.getCodigoDescripcion()));
			}
			else
				listaEtiquetas.add(new KeyValue("MUNDT", ""));
				
			
			listaEtiquetas.add(new KeyValue("DOCUM", mcDocumentoVO.getDocumentoDTO().getId().getCoModelo() + " " +
												mcDocumentoVO.getDocumentoDTO().getId().getCoVersion() + " " +
												mcDocumentoVO.getDocumentoDTO().getId().getCoDocumento()));
			
			if(mcDocumentoVO.getDocumentoDTO().getDocumentoDTOByOrigenDocuni() != null)
				listaEtiquetas.add(new KeyValue("DOC_O", mcDocumentoVO.getDocumentoDTO().getDocumentoDTOByOrigenDocuni().getId().getCoModelo() + " " +
														mcDocumentoVO.getDocumentoDTO().getDocumentoDTOByOrigenDocuni().getId().getCoVersion() + " " +
														mcDocumentoVO.getDocumentoDTO().getDocumentoDTOByOrigenDocuni().getId().getCoDocumento()));
			else
				listaEtiquetas.add(new KeyValue("DOC_O", ""));
			
			
			boolean sw = false;
			
			for(Short nuHoja : mcDocumentoVO.getHojas().keySet()){
						
				pasaValidaciones((short)nuHoja);
				
				for (Short nuCasilla : mcDocumentoVO.getInformacionCasillas().keySet()){
					
					MCInformacionCasillaVO informacionCasilla = mcDocumentoVO.getInformacionCasillas().get(nuCasilla);
					
					if(informacionCasilla.getApariencia(mcDocumentoVO, nuHoja, edicion, getPestana().equals("1")) > 0){
						
						sw = true;
						
						listaEtiquetas.add(new KeyValue("LINEAcasillas"));
						listaEtiquetas.add(new KeyValue("N_HOJ", Short.toString(nuHoja)));
						listaEtiquetas.add(new KeyValue("CASIL", nuCasilla.toString() + " - " + informacionCasilla.getNombre()));
						
						if(listaCasillasError.get(nuCasilla) != null)
							listaEtiquetas.add(new KeyValue("ERROR", "Sí"));
						else
							listaEtiquetas.add(new KeyValue("ERROR", "No"));
						
						MCCasillaVO cc = mcDocumentoVO.getHojas().get(nuHoja).getCasillas().get(nuCasilla);
						
						if(!Utilidades.isEmpty(cc.getValor()))
							listaEtiquetas.add(new KeyValue("VALOR", cc.getValor().trim()));
						else
							listaEtiquetas.add(new KeyValue("VALOR", ""));
					}
						
				}	
			}
			
			if(!sw){
				listaEtiquetas.add(new KeyValue("LINEAcasillas"));
				listaEtiquetas.add(new KeyValue("N_HOJ", ""));
				listaEtiquetas.add(new KeyValue("CASIL", ""));
				listaEtiquetas.add(new KeyValue("VALOR", ""));
				listaEtiquetas.add(new KeyValue("ERROR", ""));
			}
			
			Impresion.merge("mantenimientoCasillas.odt", listaEtiquetas, DatosSesion.getLogin(), getInformeActuacion(), getServletResponse(), getInformeParametro());
	
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		
		if (getServletResponse().isCommitted()) return null;
		
		return INPUT;
	}
	
	
	public String getCoModelo() {
		return coModelo;
	}

	public void setCoModelo(String coModelo) {
		this.coModelo = coModelo;
	}

	public String getCoVersion() {
		return coVersion;
	}

	public void setCoVersion(String coVersion) {
		this.coVersion = coVersion;
	}

	public String getCoDocumento() {
		return coDocumento;
	}

	public void setCoDocumento(String coDocumento) {
		this.coDocumento = coDocumento;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public Boolean getEdicion() {
		return edicion;
	}

	public String getCoModeloSel() {
		return coModeloSel;
	}

	public void setCoModeloSel(String coModeloSel) {
		this.coModeloSel = coModeloSel;
	}

	public String getCoVersionSel() {
		return coVersionSel;
	}

	public void setCoVersionSel(String coVersionSel) {
		this.coVersionSel = coVersionSel;
	}

	public String getCoDocumentoSel() {
		return coDocumentoSel;
	}

	public void setCoDocumentoSel(String coDocumentoSel) {
		this.coDocumentoSel = coDocumentoSel;
	}

	public void setEdicion(Boolean edicion) {
		this.edicion = edicion;
	}

	public Boolean getNuevoDocumento() {
		return nuevoDocumento;
	}

	public void setNuevoDocumento(Boolean nuevoDocumento) {
		this.nuevoDocumento = nuevoDocumento;
	}

	public Boolean getBorrarDocumento() {
		return borrarDocumento;
	}

	public void setBorrarDocumento(Boolean borrarDocumento) {
		this.borrarDocumento = borrarDocumento;
	}

	public Boolean getCrearHoja() {
		return crearHoja;
	}

	public void setCrearHoja(Boolean crearHoja) {
		this.crearHoja = crearHoja;
	}

	public Boolean getEliminarHoja() {
		return eliminarHoja;
	}

	public void setEliminarHoja(Boolean eliminarHoja) {
		this.eliminarHoja = eliminarHoja;
	}

	public Boolean getGuardar() {
		return guardar;
	}

	public void setGuardar(Boolean guardar) {
		this.guardar = guardar;
	}

	public Boolean getCalcular() {
		return calcular;
	}

	public void setCalcular(Boolean calcular) {
		this.calcular = calcular;
	}

	public MantenimientoCasillasBO getMantenimientoCasillasBO() {
		return mantenimientoCasillasBO;
	}

	public void setMantenimientoCasillasBO(
			MantenimientoCasillasBO mantenimientoCasillasBO) {
		this.mantenimientoCasillasBO = mantenimientoCasillasBO;
	}

	public MCDocumentoVO getMcDocumentoVO() {
		return mcDocumentoVO;
	}

	public void setMcDocumentoVO(MCDocumentoVO mcDocumentoVO) {
		this.mcDocumentoVO = mcDocumentoVO;
	}

	public Boolean getMostrarColumnaError() {
		return mostrarColumnaError;
	}

	public void setMostrarColumnaError(Boolean mostrarColumnaError) {
		this.mostrarColumnaError = mostrarColumnaError;
	}

	public int getHoja() {
		return hoja;
	}

	public String getPestLlamada() {
		return pestLlamada;
	}

	public void setPestLlamada(String pestLlamada) {
		this.pestLlamada = pestLlamada;
	}

	public void setHoja(int hoja) {
		this.hoja = hoja;
	}

	public SortedMap<Short, String> getListaCasillasError() {
		return listaCasillasError;
	}

	public void setListaCasillasError(SortedMap<Short, String> listaCasillasError) {
		this.listaCasillasError = listaCasillasError;
	}

	public boolean isErrorTotal() {
		return errorTotal;
	}

	public void setErrorTotal(boolean errorTotal) {
		this.errorTotal = errorTotal;
	}
	
	public DomicilioBO getDomicilioBO() {
		return domicilioBO;
	}

	public void setDomicilioBO(DomicilioBO domicilioBO) {
		this.domicilioBO = domicilioBO;
	}

	public UnidadUrbanaBO getUnidadUrbanaBO() {
		return unidadUrbanaBO;
	}

	public void setUnidadUrbanaBO(UnidadUrbanaBO unidadUrbanaBO) {
		this.unidadUrbanaBO = unidadUrbanaBO;
	}

	public CalleBO getCalleBO() {
		return calleBO;
	}

	public void setCalleBO(CalleBO calleBO) {
		this.calleBO = calleBO;
	}

	public MunicipioBO getMunicipioBO() {
		return municipioBO;
	}

	public void setMunicipioBO(MunicipioBO municipioBO) {
		this.municipioBO = municipioBO;
	}

	public ProvinciaBO getProvinciaBO() {
		return provinciaBO;
	}

	public void setProvinciaBO(ProvinciaBO provinciaBO) {
		this.provinciaBO = provinciaBO;
	}

	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public SituacionBO getSituacionBO() {
		return situacionBO;
	}

	public void setSituacionBO(SituacionBO situacionBO) {
		this.situacionBO = situacionBO;
	}

	public ConceptoBO getConceptoBO() {
		return conceptoBO;
	}

	public void setConceptoBO(ConceptoBO conceptoBO) {
		this.conceptoBO = conceptoBO;
	}

	public void setCalculoManualDocumentoBO(CalculoManualDocumentoBO calculoManualDocumentoBO) {
		this.calculoManualDocumentoBO = calculoManualDocumentoBO;
	}

	public CalculoManualDocumentoBO getCalculoManualDocumentoBO() {
		return calculoManualDocumentoBO;
	}

	public boolean isIncluirCalculo() {
		return incluirCalculo;
	}

	public void setIncluirCalculo(boolean incluirCalculo) {
		this.incluirCalculo = incluirCalculo;
	}

	public String getOPERACION_GENERICO() {
		return OPERACION_GENERICO;
	}

	public String getOPERACION_MODIFICA() {
		return OPERACION_MODIFICA;
	}

	public String getOPERACION_CONSULTA() {
		return OPERACION_CONSULTA;
	}

	public String getOPERACION_ALTA() {
		return OPERACION_ALTA;
	}

	public String getOPERACION_CONTROL() {
		return OPERACION_CONTROL;
	}

	public void setHojaIr(String hojaIr) {
		this.hojaIr = hojaIr;
	}

	public String getHojaIr() {
		return hojaIr;
	}

	public void setPedirDocOrigen(Boolean pedirDocOrigen) {
		this.pedirDocOrigen = pedirDocOrigen;
	}

	public Boolean getPedirDocOrigen() {
		return pedirDocOrigen;
	}

	public void setCoModeloPedirDocOrigen(String coModeloPedirDocOrigen) {
		this.coModeloPedirDocOrigen = coModeloPedirDocOrigen;
	}

	public String getCoModeloPedirDocOrigen() {
		return coModeloPedirDocOrigen;
	}

	public void setCoVersionPedirDocOrigen(String coVersionPedirDocOrigen) {
		this.coVersionPedirDocOrigen = coVersionPedirDocOrigen;
	}

	public String getCoVersionPedirDocOrigen() {
		return coVersionPedirDocOrigen;
	}

	public void setCoDocumentoPedirDocOrigen(String coDocumentoPedirDocOrigen) {
		this.coDocumentoPedirDocOrigen = coDocumentoPedirDocOrigen;
	}

	public String getCoDocumentoPedirDocOrigen() {
		return coDocumentoPedirDocOrigen;
	}

	public void setTipoModelo(String tipoModelo) {
		this.tipoModelo = tipoModelo;
	}

	public String getTipoModelo() {
		return tipoModelo;
	}

	public void setSubtipoModelo(String subtipoModelo) {
		this.subtipoModelo = subtipoModelo;
	}

	public String getSubtipoModelo() {
		return subtipoModelo;
	}

	public void setModeloBO(ModeloBO modeloBO) {
		this.modeloBO = modeloBO;
	}

	public ModeloBO getModeloBO() {
		return modeloBO;
	}

	public short getPRIMERA_HOJA() {
		return CasillaConstants.PRIMERA_HOJA;
	}

	public short getNU_CASILLA_INICIO_LIQUIDACION() {
		return CasillaConstants.NU_CASILLA_INICIO_LIQUIDACION;
	}

	public short getNU_CASILLA_FIN_LIQUIDACION() {
		return CasillaConstants.NU_CASILLA_FIN_LIQUIDACION;
	}

	public void setCrearHojasBO(CrearHojasBO crearHojasBO) {
		this.crearHojasBO = crearHojasBO;
	}

	public CrearHojasBO getCrearHojasBO() {
		return crearHojasBO;
	}

	public void setCssBotonCrearHojas(String cssBotonCrearHojas) {
		this.cssBotonCrearHojas = cssBotonCrearHojas;
	}

	public String getCssBotonCrearHojas() {
		return cssBotonCrearHojas;
	}

	public void setCssBotonGuardar(String cssBotonGuardar) {
		this.cssBotonGuardar = cssBotonGuardar;
	}

	public String getCssBotonGuardar() {
		return cssBotonGuardar;
	}

	public ControlIncidenciaBO getControlIncidenciaBO() {
		return controlIncidenciaBO;
	}

	public void setControlIncidenciaBO(ControlIncidenciaBO controlIncidenciaBO) {
		this.controlIncidenciaBO = controlIncidenciaBO;
	}

	public boolean isGeneradoParcial() {
		return isGeneradoParcial;
	}

	public void setGeneradoParcial(boolean isGeneradoParcial) {
		this.isGeneradoParcial = isGeneradoParcial;
	}

	public String getMensajeGeneradoParcial() {
		return mensajeGeneradoParcial;
	}

	public void setMensajeGeneradoParcial(String mensajeGeneradoParcial) {
		this.mensajeGeneradoParcial = mensajeGeneradoParcial;
	}

	public CasillasRelacionadasBO getCasillasRelacionadasBO() {
		return casillasRelacionadasBO;
	}

	public void setCasillasRelacionadasBO(
			CasillasRelacionadasBO casillasRelacionadasBO) {
		this.casillasRelacionadasBO = casillasRelacionadasBO;
	}

	public boolean isSeleccionarDestinos() {
		return seleccionarDestinos;
	}

	public void setSeleccionarDestinos(boolean seleccionarDestinos) {
		this.seleccionarDestinos = seleccionarDestinos;
	}

	public String getEjecutaIncidencia() {
		return ejecutaIncidencia;
	}

	public void setEjecutaIncidencia(String ejecutaIncidencia) {
		this.ejecutaIncidencia = ejecutaIncidencia;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public Boolean getMostrarMensajes() {
		return mostrarMensajes;
	}

	public void setMostrarMensajes(Boolean mostrarMensajes) {
		this.mostrarMensajes = mostrarMensajes;
	}

}
