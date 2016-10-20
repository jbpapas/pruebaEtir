package es.dipucadiz.etir.comun.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.Preparable;

import es.dipucadiz.etir.comun.bo.CasillaMunicipioBO;
import es.dipucadiz.etir.comun.bo.CasillasRelacionadasBO;
import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.ConceptoBO;
import es.dipucadiz.etir.comun.bo.ConceptoModeloBO;
import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.bo.DocumentoCensoBO;
import es.dipucadiz.etir.comun.bo.ModeloBO;
import es.dipucadiz.etir.comun.bo.ModeloVersionBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.boStoredProcedure.AltaManualDocumentoBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.constants.SituacionConstants;
import es.dipucadiz.etir.comun.constants.TipoModeloConstants;
import es.dipucadiz.etir.comun.displaytag.GadirPaginatedList;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ConceptoModeloDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCensoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.ControlTerritorial;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.EstadoSituacionConstants;
import es.dipucadiz.etir.comun.utilidades.IncidenciaConstants;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.ModoCobroConstants;
import es.dipucadiz.etir.comun.utilidades.MunicipioConceptoModeloUtil;
import es.dipucadiz.etir.comun.utilidades.MunicipioConstants;
import es.dipucadiz.etir.comun.utilidades.OpcionesIncidenciaSituacionUtil;
import es.dipucadiz.etir.comun.utilidades.ProvinciaConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb06.utilidades.TramitadorConstants;
import es.dipucadiz.etir.sb06.vo.EstadoTramitadorOnlineVO;
import es.dipucadiz.etir.sb07.tributos.action.G743MantenimientoDocumentos.G743DocumentoVO;
import es.dipucadiz.etir.sb07.tributos.action.G743MantenimientoDocumentos.G743TodosDocumentosAction;

public class NuevoDocumentoAction extends AbstractGadirBaseAction implements Preparable, Volvible {

	private static final long serialVersionUID = 4385623403412797087L;
	
	public static final String NUEVO_TRIBUO = "tributos";
	public static final String NUEVA_RESULUCION_CATASTRAL = "rescat";
	public static final String TRAMITADOR_ONLINE = "tramitador";
	public static final String CLIENTES_CENSO = "clicen";
	public static final String CLIENTES_LIQUIDACION = "cliliq";
	
	String origen;
	
	List<MunicipioDTO> listaMunicipios;
    List<ConceptoDTO> listaConceptos;
    List<ModeloDTO> listaModelos;
    List<ModeloVersionDTO> listaVersiones;
    
    boolean primerosDatos = false;
    boolean segundoFormulario = false;
    
    String tipo;
    String subtipo;
    
    String ejercicioIni;
    String ejercicioFin;
    
    String docOrigenModelo;
    String docOrigenVersion;
    String docOrigenDocumento;
    
    String fechaIni;
    String fechaFin;
    
    String porcentajeParticipacion;
    
    String cliente;
    String coCliente;
    String nif;
    
    String refCat;
    
    String coModelo;
    String modelo;
    String coVersion;
    String version;
    String coMunicipio;
    String municipio;
    String coConcepto;
    String concepto;
    private Long coDomicilio;
    private String coDocumento;
    private boolean calcular;
    
    private ModeloBO modeloBO;
    private ModeloVersionBO modeloVersionBO;
    private MunicipioBO municipioBO;
    private CasillaMunicipioBO casillaMunicipioBO;
    private ClienteBO clienteBO;
    private DocumentoBO documentoBO;
    private DocumentoCensoBO documentoCensoBO;
    private CasillasRelacionadasBO casillasRelacionadasBO;
    private AltaManualDocumentoBO altaManualDocumentoBO;
    private ConceptoBO conceptoBO;
    private ConceptoModeloBO conceptoModeloBO;
    private EstadoTramitadorOnlineVO estadoTramitadorOnlineVO;
    
    // Buscador documento origen
    private List<MunicipioDTO> listaMunicipiosDocOri;
    private String municipioDocOri;
    private String coMunicipioDocOri;
    private List<ConceptoDTO> listaConceptosDocOri;
    private String conceptoDocOri;
    private String coConceptoDocOri;
    private String modeloDocOri;
    private String coModeloDocOri;
    private String versionDocOri;
    private String coVersionDocOri;
    private String clienteDocOri;
    private String coClienteDocOri;
    private String nifDocOri;
    private String refObjTrib1DocOri;
    private boolean buscadorDocOriAbierto=false;
    private String sort;
    private String dir;
    private int page = 1;
    private int porPagina = 10;
    private GadirPaginatedList<G743DocumentoVO> listaDocumentosDocOri;
    private boolean pedirImpresion;
    private List<KeyValue> listaImpresiones;
    private String ejecutaIncidencia;
    
    public String execute(){
    	try{
    		if (TRAMITADOR_ONLINE.equals(origen)) {
    			estadoTramitadorOnlineVO = (EstadoTramitadorOnlineVO) getObjetoSesion(TramitadorConstants.OBJETO_SESION);
    			if (estadoTramitadorOnlineVO.getCircuitoRutaDTO().getMunicipioDTO().getId().getCoProvincia().equals(ProvinciaConstants.CO_PROVINCIA_GENERICO) &&
    					estadoTramitadorOnlineVO.getCircuitoRutaDTO().getMunicipioDTO().getId().getCoMunicipio().equals(MunicipioConstants.CO_MUNICIPIO_GENERICO)) {
    				listaMunicipios=ControlTerritorial.getMunicipiosUsuario(false);
    			} else {
    				listaMunicipios = new ArrayList<MunicipioDTO>(1);
    				listaMunicipios.add(municipioBO.findById(estadoTramitadorOnlineVO.getCircuitoRutaDTO().getMunicipioDTO().getId()));
    			}
    		} else {
    			listaMunicipios=ControlTerritorial.getMunicipiosUsuario(false);
    		}
    		if (primerosDatos) {
    			cargarCombosDocOri();
    			buscarTiposModelo();
    			calculaPedirImpresion();
    		}
    		cargarValoresCombos();
    	}catch(Exception e){
    		log.error("Error en execute", e);
    		addActionError(e.getMessage());
    	}

    	return INPUT;
    }
    
    private void cargarValoresCombos() {
    	if (Utilidades.isNotEmpty(coMunicipio) && Utilidades.isEmpty(municipio)) {
    		municipio = MunicipioConceptoModeloUtil.getMunicipioCodigoDescripcion(coMunicipio);
    	}
		if (Utilidades.isNotEmpty(coConcepto) && Utilidades.isEmpty(concepto)) {
			concepto = MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(coConcepto);
		}
		if (Utilidades.isNotEmpty(coModelo) && Utilidades.isEmpty(modelo)) {
			modelo = MunicipioConceptoModeloUtil.getModeloCodigoDescripcion(coModelo);
		}
		if (Utilidades.isNotEmpty(coVersion) && Utilidades.isEmpty(version)) {
			version = coVersion;
		}
	}

	private void calculaPedirImpresion() {
		// Es una liquidación incidenciable con impresión de notificación en gestión?
		pedirImpresion = false;
		
		if (TipoModeloConstants.TIPO_LIQUIDACION.equals(tipo)) {
			String convenioActual = ControlTerritorial.getTipoConvenio(coMunicipio.substring(0, 2), coMunicipio.substring(2), coConcepto, coModelo);
			if ("A".equals(convenioActual)) {
	        	String coIncidenciaImpresion = IncidenciaConstants.CO_INCIDENCIA_IMPRIMIR_DIPTICO_DIRECTO;
	    		Long key = OpcionesIncidenciaSituacionUtil.getIncidenciaSituacionDTO(
	    				coMunicipio.substring(0, 2), 
	    				coMunicipio.substring(2), 
	    				coModelo, 
	    				coVersion, 
	    				SituacionConstants.CO_SITUACION_PENDIENTE, 
	    				EstadoSituacionConstants.VOLUNTARIA, 
	    				ModoCobroConstants.PENDIENTE, 
	    				coIncidenciaImpresion).getCoIncidenciaSituacion();
	    		if (key != null) {
		    		pedirImpresion = true;
		    		listaImpresiones = new ArrayList<KeyValue>(2);
		    		String value = "Sí"; //OpcionesIncidenciaSituacionUtil.getDescripcionIncidenciaDocumento(coIncidenciaImpresion);
		    		listaImpresiones.add(new KeyValue(key.toString(), value));
		    		listaImpresiones.add(new KeyValue("-", "No"));
	    		}
			}
    	}
	}

	public String botonPrimerosDatos(){
    	
    	validaPrimerosDatos();
    	
    	if (!hasErrors()){
    		primerosDatos=true;
    		try{
    			buscarTiposModelo();
        		if (CLIENTES_CENSO.equals(origen) || CLIENTES_LIQUIDACION.equals(origen)) {
        			ClienteDTO clienteDTO = clienteBO.findById(Long.valueOf(coCliente));
        			nif = clienteDTO.getIdentificador();
        			cliente = clienteDTO.getRazonSocial();
        		}
    		}catch(Exception e){
    			LOG.error("Error en botonPrimerosDatos", e);
    			addActionError("Error validando la selección");
    		}
    	}else{
    		return execute();
    	}

    	return DETALLE;
    }
    
    private void buscarTiposModelo() throws GadirServiceException {
		ModeloDTO modeloDTO = modeloBO.findById(coModelo);
		tipo=modeloDTO.getTipo();
		subtipo=modeloDTO.getSubtipo();
	}

	public String botonVolver() {
    	primerosDatos = false;
    	return DETALLE;
    }
    
    private void validaPrimerosDatos(){
    	if (coMunicipio == null || coMunicipio.length()!=5){
    		addFieldError("municipio", Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
    	}
    	if (coConcepto == null || coConcepto.length()!=4){
    		addFieldError("concepto", Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
    	}
    	if (coModelo == null || coModelo.length()!=3 || coVersion == null || coVersion.length()!=1){
    		addFieldError("modelo", Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
    	}
    }
    
    public String botonAceptar() throws GadirServiceException{
		primerosDatos=true;
		buscarTiposModelo();
		calculaPedirImpresion();
    	try {
			validaDatos();
		} catch (ParseException e) {
			e.printStackTrace();
			addActionError("Error en fechas.");
		}
    	if (!hasErrors()){
    		String coProv = coMunicipio.substring(0,2);
    		String coMuni = coMunicipio.substring(2,5);
    		Date fxInicioDate = Utilidades.strutsFormatToDate(fechaIni);
    		Date fxFinDate = Utilidades.strutsFormatToDate(fechaFin);
    		String fxInicio = null;
    		String fxFin = null;
    		if ("L".equals(tipo) && "N".equals(subtipo)) {
    			fxInicio = "0101" + ejercicioIni;
    			fxFin    = "0101" + ejercicioFin;
    		} else if ("L".equals(tipo) && "I".equals(subtipo)) {
    			fxInicio = Utilidades.dateToDDMMYYYYSinBarras(fxInicioDate);
    			fxFin    = Utilidades.dateToDDMMYYYYSinBarras(fxFinDate);
    		}
    		try {
    			Map<String, Object> resultAlta = altaManualDocumentoBO.execute(coProv, coMuni, coConcepto, coModelo, coVersion, docOrigenModelo, docOrigenVersion, docOrigenDocumento, Long.valueOf(coCliente), refCat, coDomicilio == null ? null : Long.valueOf(coDomicilio), fxInicio, fxFin, getCoProcesoActual());
    			BigDecimal resultado = (BigDecimal) resultAlta.get("resultado");
        		coDocumento = (String) resultAlta.get("coDocumento");
        		BigDecimal coMensajeError = (BigDecimal) resultAlta.get("coMensajeError");
        		String variable = (String) resultAlta.get("variable");
        		if (resultado.intValue() != 0) {
        			addActionError(Mensaje.getTexto(coMensajeError.intValue(), variable));
        		} else if (Utilidades.isEmpty(coDocumento)) {
        			addActionError("Alta manual no devuelve número de documento para modelo " + coModelo + " " + coVersion + ".");
        		} else {
//        			addActionMessage(Mensaje.getTexto(MensajeConstants.V1_HA_SIDO_DADO_DE_ALTA, "el documento " + coModelo + " " + coVersion + " " + coDocumento));
        		}
    		} catch (Exception e) {
    			LOG.error("Error en alta de documento.", e);
    			String cause = "";
    			try {
	    			if (Utilidades.isNotEmpty(e.getMessage())) cause = e.getMessage();
	    			else cause = e.getCause().getMessage();
    			}  catch (Exception e2) {}
    			addActionError("Error en llamada al PLSQL de alta de documento." + (Utilidades.isNotEmpty(cause) ? " ("+cause+")" : ""));
    		}
    	}
    	
    	String result;
    	if (hasErrors()) {
    		result = execute();
    	} else {
    		if ("L".equals(tipo)) {
    			calcular = true;
    		} else {
    			calcular = false;
    		}
    		if (TRAMITADOR_ONLINE.equals(origen)) {
    			estadoTramitadorOnlineVO = (EstadoTramitadorOnlineVO) getObjetoSesion(TramitadorConstants.OBJETO_SESION);
    			estadoTramitadorOnlineVO.setNuevoDocumentoId(new DocumentoDTOId(coModelo, coVersion, coDocumento));
    		}
    		if (getServletRequest() != null) {
    			apilar(); // Para que vuelva aquí tras terminar con el mantenimiento de casillas.
    		}
    		result = SUCCESS;
    	}
    	return result;
    }
    
	// Carga combos buscador documento origen
    private void cargarCombosDocOri() {
		try {
			listaMunicipiosDocOri = new ArrayList<MunicipioDTO>(1);
			listaMunicipiosDocOri.add(municipioBO.findById(new MunicipioDTOId(coMunicipio.substring(0, 2), coMunicipio.substring(2))));
			listaConceptosDocOri = aseguraConcepto(casillasRelacionadasBO.findConceptosOrigen(coMunicipio.substring(0, 2), coMunicipio.substring(2), coConcepto, coModelo, coVersion, coMunicipio.substring(0, 2), coMunicipio.substring(2)), coConcepto);
		} catch (GadirServiceException e) {
			log.error(e.getMensaje(), e);
			addActionError(e.getMensaje());
		}
    }

	private List<ConceptoDTO> aseguraConcepto(List<ConceptoDTO> conceptoDTOs, String coConcepto) throws GadirServiceException {
		boolean yaTiene = false;
		for (ConceptoDTO conceptoDTO : conceptoDTOs) {
			if (conceptoDTO.getCoConcepto().equals(coConcepto)) {
				yaTiene = true;
				break;
			}
		}
		if (!yaTiene) {
			conceptoDTOs.add(conceptoBO.findById(coConcepto));
		}
		return conceptoDTOs;
	}

	private List<ModeloDTO> aseguraModelo(List<ModeloDTO> modeloDTOs, String coModelo) throws GadirServiceException {
		boolean yaTiene = false;
		for (ModeloDTO modeloDTO : modeloDTOs) {
			if (modeloDTO.getCoModelo().equals(coModelo)) {
				yaTiene = true;
				break;
			}
		}
		if (!yaTiene) {
			modeloDTOs.add(modeloBO.findById(coModelo));
		}
		Collections.sort(modeloDTOs);
		return modeloDTOs;
	}

	private List<ModeloVersionDTO> aseguraVersion(List<ModeloVersionDTO> modeloVersionDTOs, String coVersion) throws GadirServiceException {
		boolean yaTiene = false;
		for (ModeloVersionDTO modeloVersionDTO : modeloVersionDTOs) {
			if (modeloVersionDTO.getId().getCoVersion().equals(coVersion)) {
				yaTiene = true;
				break;
			}
		}
		if (!yaTiene) {
			ModeloVersionDTO mvDTO = new ModeloVersionDTO();
			mvDTO.setId(new ModeloVersionDTOId("", "1"));
			modeloVersionDTOs.add(mvDTO);
		}
		return modeloVersionDTOs;
	}

	private void validaDatos() throws ParseException {
    	// Validaciones lógicas
		if (Utilidades.isEmpty(coCliente) || Utilidades.isEmpty(nif)) {
			addFieldError("cliente", Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
		}
		if (Utilidades.isNotEmpty(docOrigenModelo) || Utilidades.isNotEmpty(docOrigenVersion) || Utilidades.isNotEmpty(docOrigenDocumento)) {
			if (Utilidades.isEmpty(docOrigenModelo) || Utilidades.isEmpty(docOrigenVersion) || Utilidades.isEmpty(docOrigenDocumento)) {
				addFieldError("docOrigenModelo", Mensaje.getTexto(MensajeConstants.VALOR_CAMPO_INCORRECTO));
			}
		}		
		if (Utilidades.isNotEmpty(docOrigenModelo)) {
			docOrigenModelo = Utilidades.normalizarCoModelo(docOrigenModelo);
		}
		if (Utilidades.isNotEmpty(docOrigenVersion)) {
			docOrigenVersion = Utilidades.normalizarCoVersion(docOrigenVersion);
		}
		if (Utilidades.isNotEmpty(docOrigenDocumento)) {
			docOrigenDocumento = Utilidades.normalizarCoDocumento(docOrigenDocumento);
		}
		if ("L".equals(tipo) && "N".equals(subtipo)) {
			int ejerMin = Utilidades.getAnoActual() - 10;
			int ejerMax = Utilidades.getAnoActual() + 2;
			if (Utilidades.isEmpty(ejercicioIni)) {
				addFieldError("ejercicioIni", Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
			} else if (!Utilidades.isNumeric(ejercicioIni)) {
				addFieldError("ejercicioIni", Mensaje.getTexto(MensajeConstants.FORMATO_INCORRECTO));
			} else if (Integer.valueOf(ejercicioIni) < ejerMin) {
				addFieldError("ejercicioIni", Mensaje.getTexto(MensajeConstants.ERROR_VALOR_DESDE_HASTA));
			}
			if (Utilidades.isEmpty(ejercicioFin)) {
				addFieldError("ejercicioFin", Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
			} else if (!Utilidades.isNumeric(ejercicioFin)) {
				addFieldError("ejercicioFin", Mensaje.getTexto(MensajeConstants.FORMATO_INCORRECTO));
			} else if (Integer.valueOf(ejercicioFin) > ejerMax) {
				addFieldError("ejercicioFin", Mensaje.getTexto(MensajeConstants.ERROR_VALOR_DESDE_HASTA));
			}
			if (!hasErrors()) {
				if (Integer.valueOf(ejercicioIni) > Integer.valueOf(ejercicioFin)) {
					addFieldError("ejercicioFin", Mensaje.getTexto(MensajeConstants.ERROR_VALOR_DESDE_HASTA));
				}
			}
		} else if ("L".equals(tipo) && "I".equals(subtipo)) {
			if (Utilidades.isNotEmpty(fechaIni) || Utilidades.isNotEmpty(fechaFin)) {
				Date fxMin = Utilidades.YYYYMMDDToDate((Utilidades.getAnoActual() - 10) + "-01-01");
				Date fxMax = Utilidades.YYYYMMDDToDate((Utilidades.getAnoActual() + 2) + "-12-31");
				
				// Primero, convertir a formato date
				Date fxInicioDate = null;
				Date fxFinDate = null;
				if (Utilidades.isNotEmpty(fechaIni)) {
					fxInicioDate = Utilidades.strutsFormatToDate(fechaIni);
				}
				if (Utilidades.isNotEmpty(fechaFin)) {
					fxFinDate = Utilidades.strutsFormatToDate(fechaFin);
				}
				
				if (Utilidades.isEmpty(fechaIni)) {
					addFieldError("fechaIni", Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
				} else if (fxInicioDate == null) {
					addFieldError("fechaIni", Mensaje.getTexto(MensajeConstants.FORMATO_INCORRECTO));
				} else if (fxInicioDate.before(fxMin)) {
					addFieldError("fechaIni", Mensaje.getTexto(MensajeConstants.V1, "Fecha debe ser mayor o igual que " + Utilidades.dateToDDMMYYYY(fxMin)));
				}
				if (Utilidades.isEmpty(fechaFin)) {
					addFieldError("fechaFin", Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
				} else if (fxFinDate == null) {
					addFieldError("fechaFin", Mensaje.getTexto(MensajeConstants.FORMATO_INCORRECTO));
				} else if (fxFinDate.after(fxMax)) {
					addFieldError("fechaFin", Mensaje.getTexto(MensajeConstants.V1, "Fecha debe ser menor o igual que " + Utilidades.dateToDDMMYYYY(fxMax)));
				}
				if (!hasErrors()) {
					if (fxInicioDate.after(fxFinDate)) {
						addFieldError("fechaFin", Mensaje.getTexto(MensajeConstants.ERROR_FECHA_DESDE_HASTA));
					}
				}
			}
		}
		if (pedirImpresion && Utilidades.isEmpty(ejecutaIncidencia)) {
			addFieldError("ejecutaIncidencia", Mensaje.getTexto(MensajeConstants.CAMPO_OBLIGATORIO));
		}

		
		// Validaciones contra base de datos
		if (!hasErrors()) {
			String coProv = coMunicipio.substring(0,2);
			String coMuni = coMunicipio.substring(2,5);

			try{
	    		// Cliente
	    		ClienteDTO clienteDTO = clienteBO.findById(Long.valueOf(coCliente));
	    		if (Utilidades.isNull(clienteDTO)) {
	    			addFieldError("cliente", Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "El cliente"));
	    		}
	    		
	    		// Documento origen
	    		if (Utilidades.isNotEmpty(docOrigenModelo)) {
	    			boolean modelosCompatibles = true;
	    			if (!docOrigenModelo.equals(coModelo) || !docOrigenVersion.equals(coVersion)) {
	    				if (!casillasRelacionadasBO.isRelacionados(coMuni, coProv, coConcepto, coModelo, coVersion, docOrigenModelo, docOrigenVersion)) {
	    					addFieldError("docOrigenModelo", Mensaje.getTexto(MensajeConstants.V1, "El modelo origen no está relacionado con el modelo a dar de alta."));
	    					modelosCompatibles = false;
	    				}
	    			}
	    			if(modelosCompatibles) {
	    				DocumentoDTO documentoOrigenDTO = documentoBO.findById(new DocumentoDTOId(docOrigenModelo, docOrigenVersion, docOrigenDocumento));
	    				if (documentoOrigenDTO == null) {
		    				addFieldError("docOrigenModelo", Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "El documento origen"));
	    				} else if (documentoOrigenDTO.getMunicipioDTO() == null ||
		    					!coProv.equals(documentoOrigenDTO.getMunicipioDTO().getId().getCoProvincia()) || 
		    					!coMuni.equals(documentoOrigenDTO.getMunicipioDTO().getId().getCoMunicipio())) {
		    				addFieldError("docOrigenModelo", Mensaje.getTexto(MensajeConstants.V1, "El documento origen pertenece a otro municipio."));
		    			}
	    			}
	    		}
	    		
	    		// Referencia catastral
    			coDomicilio = null;
	    		if (Utilidades.isNotEmpty(refCat)) {
	    			refCat = refCat.toUpperCase();
		    		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoCensoDTO.class);
		    		criteria.createAlias("documentoDTO", "doc");
		    		//criteria.add(Restrictions.eq("doc.refCatastral", refCat));
		    		criteria.add(Restrictions.eq("doc.refObjTributario1", refCat));
		    		criteria.add(Restrictions.eq("doc.municipioDTO.id.coProvincia", coProv));
		    		criteria.add(Restrictions.eq("doc.municipioDTO.id.coMunicipio", coMuni));
		    		criteria.add(Restrictions.eq("doc.conceptoDTO.coConcepto", coConcepto));
		    		criteria.addOrder(Order.desc("boActivo"));
		    		criteria.addOrder(Order.asc("id.coModelo"));
		    		criteria.addOrder(Order.desc("id.coDocumento"));
		    		List<DocumentoCensoDTO> documentoCensoReferenciadoDTOs = documentoCensoBO.findByCriteria(criteria, 0, 1);
		    		if (documentoCensoReferenciadoDTOs.size() > 0) {
		    			coDomicilio = documentoCensoReferenciadoDTOs.get(0).getDocumentoDTO().getDomicilioDTOByCoDomicilio().getCoDomicilio();
		    		} else {
		    			coDomicilio = null;
		    			//addFieldError("refCat", Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "Referencia catastral"));
		    		}
	    		}
	    		
	    	}catch(Exception e){
	    		log.error("Error en validaDatos", e);
	    		addActionError("Datos incorrectos");
	    	}
		}
    }   	

    
    public String cargaConceptosAjax() throws GadirServiceException{
    	if (listaConceptos ==null || listaConceptos.isEmpty()){
			if (!Utilidades.isEmpty(coMunicipio) && coMunicipio.length()==5){
				try{
					if (NUEVA_RESULUCION_CATASTRAL.equals(origen)) {
						listaConceptos = new ArrayList<ConceptoDTO>();
						listaConceptos.add(conceptoBO.findById("0101"));
						listaConceptos.add(conceptoBO.findById("0102"));
					} else {
						listaConceptos = ControlTerritorial.getConceptos(coMunicipio, null, (short)Utilidades.getAnoActual(), ControlTerritorial.EDICION, "G");
						
						if (TRAMITADOR_ONLINE.equals(origen)) {
							estadoTramitadorOnlineVO = (EstadoTramitadorOnlineVO)getObjetoSesion(TramitadorConstants.OBJETO_SESION);
							List<ConceptoModeloDTO> conceptoModeloDTOs = conceptoModeloBO.findConceptoModelos(listaConceptos, estadoTramitadorOnlineVO.getCoModeloApertura());
							listaConceptos = new ArrayList<ConceptoDTO>(conceptoModeloDTOs.size());
							for (ConceptoModeloDTO conceptoModeloDTO : conceptoModeloDTOs) {
								listaConceptos.add(conceptoModeloDTO.getConceptoDTO());
							}
						} else if (CLIENTES_CENSO.equals(origen) || CLIENTES_LIQUIDACION.equals(origen)) {
							// Filtrar para que salgan conceptos que tengan al menos un medelo de tipo censo / liquidación.
							List<ConceptoDTO> listaConceptosTmp = new ArrayList<ConceptoDTO>();
							for (ConceptoDTO conceptoDTO : listaConceptos) {
								List<ModeloDTO> modeloDTOs = modeloBO.findModelosByConcepto(conceptoDTO.getCoConcepto());
								boolean cumple = false;
								for (ModeloDTO modeloDTO : modeloDTOs) { 
									if (cumpleTipo(modeloDTO, origen)) {
										cumple = true;
										break;
									}
								}
								if (cumple) {
									listaConceptosTmp.add(conceptoDTO);
								}
							}
							listaConceptos = listaConceptosTmp;
						}
					}
				}catch(Exception e){
					LOG.error("Error en cargaConceptosAjax", e);
				}
			}
		}
		
		if(listaConceptos!=null && !listaConceptos.isEmpty()){
			for(ConceptoDTO conceptoDTO: listaConceptos){
				autocompleterOptions.add(new KeyValue(conceptoDTO.getCoConcepto(),conceptoDTO.getNombre()));
			}
		}else{
			if (Utilidades.isEmpty(coMunicipio))
				autocompleterOptions.add(new KeyValue("", "Seleccione municipio"));
			else{
				autocompleterOptions.add(new KeyValue("", "No existen conceptos"));
			}
		}
		return AUTOCOMPLETER_OPTIONS;
	}
    
    
    public String cargaModelosAjax() throws GadirServiceException{
    	if (listaModelos ==null || listaModelos.isEmpty()){
			if (!Utilidades.isEmpty(coConcepto) && coConcepto.length()==4){
				listaModelos = ControlTerritorial.getModelos(coMunicipio, coConcepto, null, (short)Utilidades.getAnoActual(), ControlTerritorial.EDICION, "G", null);
			}
		}
		
    	if(listaModelos!=null && !listaModelos.isEmpty()){
    		for(ModeloDTO modeloDTO: listaModelos){
    			modeloDTO = modeloBO.findById(modeloDTO.getCoModelo());
    			String tipoConvenio = ControlTerritorial.getTipoConvenio(coMunicipio.substring(0, 2), coMunicipio.substring(2, 5), coConcepto, modeloDTO.getCoModelo());
    			
    			if (cumpleTipo(modeloDTO, origen) && 
    					casillaMunicipioBO.existeOperacion(coMunicipio.substring(0, 2), coMunicipio.substring(2, 5), coConcepto, modeloDTO.getCoModelo(), null, "ALTA") &&
    					("A".equals(tipoConvenio) || "G".equals(tipoConvenio))
    			) {
    				autocompleterOptions.add(new KeyValue(modeloDTO.getCoModelo(),modeloDTO.getNombre()));
    			}
    		}
    		if (autocompleterOptions.isEmpty()) {
    			autocompleterOptions.add(new KeyValue("", "No existen modelos para el concepto."));
    		}
    	}else{
			autocompleterOptions.add(new KeyValue("", "Seleccione concepto."));
		}
		return AUTOCOMPLETER_OPTIONS;
	}
    
    public String cargaModelosDocOriAjax() throws GadirServiceException{
    	if (Utilidades.isNotEmpty(coConceptoDocOri)) {
	    	List<ModeloDTO> listaModelosDocOri = aseguraModelo(casillasRelacionadasBO.findModelosOrigen(coMunicipio.substring(0, 2), coMunicipio.substring(2), coConcepto, coModelo, coVersion, coMunicipio.substring(0, 2), coMunicipio.substring(2), coConceptoDocOri), coModelo);
	    	for (ModeloDTO modeloDTO : listaModelosDocOri) {
	    		autocompleterOptions.add(new KeyValue(modeloDTO.getCoModelo(), modeloDTO.getNombre()));
	    	}
    		if (autocompleterOptions.isEmpty()) {
    			autocompleterOptions.add(new KeyValue("", "No existen modelos para el concepto."));
    		}
    	} else {
    		autocompleterOptions.add(new KeyValue("", "Seleccione concepto."));
    	}
		return AUTOCOMPLETER_OPTIONS;
	}
    
    private boolean cumpleTipo(ModeloDTO modeloDTO, String tipoAlta) {
    	boolean result = false;
		if (NUEVO_TRIBUO.equals(tipoAlta)) {
			result = ("T".equals(modeloDTO.getTipo()) && ("L".equals(modeloDTO.getSubtipo()) || 
					"F".equals((modeloDTO.getSubtipo())))) || ("L".equals(modeloDTO.getTipo()));
		} else if (NUEVA_RESULUCION_CATASTRAL.equals(tipoAlta)) {
			result = ("T".equals(modeloDTO.getTipo()) && "N".equals(modeloDTO.getSubtipo()));
		} else if (TRAMITADOR_ONLINE.equals(origen)) {
			estadoTramitadorOnlineVO = (EstadoTramitadorOnlineVO)getObjetoSesion(TramitadorConstants.OBJETO_SESION);
			result = modeloDTO.getCoModelo().equals(estadoTramitadorOnlineVO.getCoModeloApertura());
		} else if (CLIENTES_CENSO.equals(tipoAlta)) {
			result = "T".equals(modeloDTO.getTipo()) && ("L".equals(modeloDTO.getSubtipo()) || "F".equals(modeloDTO.getSubtipo()));
		} else if (CLIENTES_LIQUIDACION.equals(tipoAlta)) {
			result = "L".equals(modeloDTO.getTipo()) && (modeloDTO.getCoModelo().startsWith("4") || modeloDTO.getCoModelo().startsWith("3"));
		}
    	return result;
	}

	public String cargaVersionesAjax() throws GadirServiceException{
		autocompleterConCodigo=false;
    	if (listaVersiones ==null || listaVersiones.isEmpty()){
			if (!Utilidades.isEmpty(coModelo) && coModelo.length()==3){
				try{
					listaVersiones = modeloVersionBO.findVersionesByModelo(coModelo);
				}
				catch(Exception e){
					LOG.error("Error en cargaVersionesAjax", e);
				}
			}
		}
		
		if(listaVersiones!=null && !listaVersiones.isEmpty()){
			for(ModeloVersionDTO modeloVersionDTO: listaVersiones){
    			if (casillaMunicipioBO.existeOperacion(coMunicipio.substring(0, 2), coMunicipio.substring(2, 5), coConcepto, modeloVersionDTO.getId().getCoModelo(), modeloVersionDTO.getId().getCoVersion(), "ALTA")) {
    				autocompleterOptions.add(new KeyValue(modeloVersionDTO.getId().getCoVersion(), modeloVersionDTO.getId().getCoVersion()));
    			}
			}
		}else{
			autocompleterOptions.add(new KeyValue("", ""));
		}
		return AUTOCOMPLETER_OPTIONS;
	}
	public String cargaVersionesDocOriAjax() throws GadirServiceException{
		autocompleterConCodigo=false;
    	if (Utilidades.isNotEmpty(coModeloDocOri)) {
	    	List<ModeloVersionDTO> listaVersionesDocOri = aseguraVersion(casillasRelacionadasBO.findVersionesOrigen(coMunicipio.substring(0, 2), coMunicipio.substring(2), coConcepto, coModelo, coVersion, coMunicipio.substring(0, 2), coMunicipio.substring(2), coConceptoDocOri, coModeloDocOri), coVersion);
	    	for (ModeloVersionDTO modeloVersionDTO : listaVersionesDocOri) {
	    		autocompleterOptions.add(new KeyValue(modeloVersionDTO.getId().getCoVersion(), modeloVersionDTO.getId().getCoVersion()));
	    	}
    		if (autocompleterOptions.isEmpty()) {
    			autocompleterOptions.add(new KeyValue("", "No existen versiones para el modelo."));
    		}
    	} else {
    		autocompleterOptions.add(new KeyValue("", "Seleccione modelo."));
    	}
		return AUTOCOMPLETER_OPTIONS;
	}
	
	public String botonAnular() throws GadirServiceException {
		docOrigenModelo = ""; docOrigenVersion = ""; docOrigenDocumento = "";
		refCat = "";
		if (!CLIENTES_CENSO.equals(origen) && !CLIENTES_LIQUIDACION.equals(origen)) {
			cliente = ""; coCliente = ""; nif = "";
		}
		primerosDatos = true;
		cargarCombosDocOri();
		buscarTiposModelo();
		calculaPedirImpresion();
		return INPUT;
	}
	
	public String botonAceptarDocOri() throws GadirServiceException {
		primerosDatos = true;
		buscadorDocOriAbierto = true;
		cargarCombosDocOri();
    	getDocumentos();
    	buscarTiposModelo();
    	calculaPedirImpresion();
		return INPUT;
	}

	public String botonAnularDocOri() throws GadirServiceException {
		municipioDocOri="";
		coMunicipioDocOri="";
		conceptoDocOri="";
		coConceptoDocOri="";
		modeloDocOri="";
		coModeloDocOri="";
		versionDocOri="";
		coVersionDocOri="";
		refObjTrib1DocOri="";
		clienteDocOri="";
		coClienteDocOri="";
		nifDocOri="";
		primerosDatos = true;
		//buscadorDocOriAbierto = true;
		cargarCombosDocOri();
		buscarTiposModelo();
		calculaPedirImpresion();
		return INPUT;
	}

	private void getDocumentos() {
		//Buscar documentos
		try {
			List<ModeloDTO> listaModelosDocOri;
			if (Utilidades.isNotEmpty(coConceptoDocOri)) {
				listaModelosDocOri = aseguraModelo(casillasRelacionadasBO.findModelosOrigen(coMunicipio.substring(0, 2), coMunicipio.substring(2), coConcepto, coModelo, coVersion, coMunicipio.substring(0, 2), coMunicipio.substring(2), coConceptoDocOri), coModelo);
			} else {
				listaModelosDocOri = new ArrayList<ModeloDTO>();
				List<ConceptoDTO> listlistaConceptosDocOri = aseguraConcepto(casillasRelacionadasBO.findConceptosOrigen(coMunicipio.substring(0, 2), coMunicipio.substring(2), coConcepto, coModelo, coVersion, coMunicipio.substring(0, 2), coMunicipio.substring(2)), coConcepto);
				for (ConceptoDTO conceptoDTO : listlistaConceptosDocOri) {
					listaModelosDocOri.addAll(aseguraModelo(casillasRelacionadasBO.findModelosOrigen(coMunicipio.substring(0, 2), coMunicipio.substring(2), coConcepto, coModelo, coVersion, coMunicipio.substring(0, 2), coMunicipio.substring(2), conceptoDTO.getCoConcepto()), coModelo));
				}
			}
			List<String> coModelos = new ArrayList<String>(listaModelosDocOri.size());
			for (ModeloDTO modeloDTO : listaModelosDocOri) {
				coModelos.add(modeloDTO.getCoModelo());
			}
			G743TodosDocumentosAction actionDoc = (G743TodosDocumentosAction) GadirConfig.getBean("G743TodosDocumentos");
			actionDoc.setFiltroActivo(1);
			actionDoc.setCoCliente(coClienteDocOri);
			actionDoc.setCoMunicipio(coMunicipioDocOri);
			actionDoc.setCoConcepto(coConceptoDocOri);
			actionDoc.setCoModelo(coModeloDocOri);
			actionDoc.setCoVersion(coVersionDocOri);
			actionDoc.setRefObjTrib1(refObjTrib1DocOri);
			actionDoc.setPage(page);
			actionDoc.setSort(sort);
			actionDoc.setDir(dir);
			actionDoc.setPorPagina(porPagina);
			DetachedCriteria criteriaTotal = actionDoc.getCriteria(false, false);
			criteriaTotal.add(Restrictions.in("doc.id.coModelo", coModelos));
			int total = documentoBO.countByCriteria(criteriaTotal);
			DetachedCriteria criteriaDocs = actionDoc.getCriteria(true, false);
			criteriaDocs.add(Restrictions.in("doc.id.coModelo", coModelos));
			List<DocumentoDTO> listaDocs = documentoBO.findByCriteria(criteriaDocs, (page - 1) * porPagina, porPagina);
			actionDoc.setListaDocs(listaDocs);
			actionDoc.obtenerListaDocumentos();
			listaDocumentosDocOri = new GadirPaginatedList<G743DocumentoVO>(actionDoc.getListaDocsVO(), total, porPagina, page, "documentos", sort, dir);
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			addActionError("Error recuperando documentos para seleccionar origen: " + e.getMensaje());
		}
	}

	public String cargaDocumentosAjax() {
    	getDocumentos();
		return SUCCESS;
	}
	
	public List<MunicipioDTO> getListaMunicipios() {
		return listaMunicipios;
	}

	public void setListaMunicipios(List<MunicipioDTO> listaMunicipios) {
		this.listaMunicipios = listaMunicipios;
	}

	public List<ConceptoDTO> getListaConceptos() {
		return listaConceptos;
	}

	public void setListaConceptos(List<ConceptoDTO> listaConceptos) {
		this.listaConceptos = listaConceptos;
	}

	public List<ModeloDTO> getListaModelos() {
		return listaModelos;
	}

	public void setListaModelos(List<ModeloDTO> listaModelos) {
		this.listaModelos = listaModelos;
	}

	public List<ModeloVersionDTO> getListaVersiones() {
		return listaVersiones;
	}

	public void setListaVersiones(List<ModeloVersionDTO> listaVersiones) {
		this.listaVersiones = listaVersiones;
	}

	public boolean isPrimerosDatos() {
		return primerosDatos;
	}

	public void setPrimerosDatos(boolean primerosDatos) {
		this.primerosDatos = primerosDatos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}

	public String getEjercicioIni() {
		return ejercicioIni;
	}

	public void setEjercicioIni(String ejercicioIni) {
		this.ejercicioIni = ejercicioIni;
	}

	public String getEjercicioFin() {
		return ejercicioFin;
	}

	public void setEjercicioFin(String ejercicioFin) {
		this.ejercicioFin = ejercicioFin;
	}

	public String getDocOrigenModelo() {
		return docOrigenModelo;
	}

	public void setDocOrigenModelo(String docOrigenModelo) {
		this.docOrigenModelo = docOrigenModelo;
	}

	public String getDocOrigenVersion() {
		return docOrigenVersion;
	}

	public void setDocOrigenVersion(String docOrigenVersion) {
		this.docOrigenVersion = docOrigenVersion;
	}

	public String getDocOrigenDocumento() {
		return docOrigenDocumento;
	}

	public void setDocOrigenDocumento(String docOrigenDocumento) {
		this.docOrigenDocumento = docOrigenDocumento;
	}

	public String getPorcentajeParticipacion() {
		return porcentajeParticipacion;
	}

	public void setPorcentajeParticipacion(String porcentajeParticipacion) {
		this.porcentajeParticipacion = porcentajeParticipacion;
	}

	public String getCoModelo() {
		return coModelo;
	}

	public void setCoModelo(String coModelo) {
		this.coModelo = coModelo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCoVersion() {
		return coVersion;
	}

	public void setCoVersion(String coVersion) {
		this.coVersion = coVersion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCoMunicipio() {
		return coMunicipio;
	}

	public void setCoMunicipio(String coMunicipio) {
		this.coMunicipio = coMunicipio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCoConcepto() {
		return coConcepto;
	}

	public void setCoConcepto(String coConcepto) {
		this.coConcepto = coConcepto;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public ModeloBO getModeloBO() {
		return modeloBO;
	}

	public void setModeloBO(ModeloBO modeloBO) {
		this.modeloBO = modeloBO;
	}

	public ModeloVersionBO getModeloVersionBO() {
		return modeloVersionBO;
	}

	public void setModeloVersionBO(ModeloVersionBO modeloVersionBO) {
		this.modeloVersionBO = modeloVersionBO;
	}

	public MunicipioBO getMunicipioBO() {
		return municipioBO;
	}

	public void setMunicipioBO(MunicipioBO municipioBO) {
		this.municipioBO = municipioBO;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCoCliente() {
		return coCliente;
	}

	public void setCoCliente(String coCliente) {
		this.coCliente = coCliente;
	}

	public String getRefCat() {
		return refCat;
	}

	public void setRefCat(String refCat) {
		this.refCat = refCat;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public CasillaMunicipioBO getCasillaMunicipioBO() {
		return casillaMunicipioBO;
	}

	public void setCasillaMunicipioBO(CasillaMunicipioBO casillaMunicipioBO) {
		this.casillaMunicipioBO = casillaMunicipioBO;
	}

	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public DocumentoBO getDocumentoBO() {
		return documentoBO;
	}

	public void setDocumentoBO(DocumentoBO documentoBO) {
		this.documentoBO = documentoBO;
	}

	public DocumentoCensoBO getDocumentoCensoBO() {
		return documentoCensoBO;
	}

	public void setDocumentoCensoBO(DocumentoCensoBO documentoCensoBO) {
		this.documentoCensoBO = documentoCensoBO;
	}

	public void setCasillasRelacionadasBO(CasillasRelacionadasBO casillasRelacionadasBO) {
		this.casillasRelacionadasBO = casillasRelacionadasBO;
	}

	public CasillasRelacionadasBO getCasillasRelacionadasBO() {
		return casillasRelacionadasBO;
	}

	public void setAltaManualDocumentoBO(AltaManualDocumentoBO altaManualDocumentoBO) {
		this.altaManualDocumentoBO = altaManualDocumentoBO;
	}

	public AltaManualDocumentoBO getAltaManualDocumentoBO() {
		return altaManualDocumentoBO;
	}

	public Long getCoDomicilio() {
		return coDomicilio;
	}

	public void setCoDomicilio(Long coDomicilio) {
		this.coDomicilio = coDomicilio;
	}

	public String getCoDocumento() {
		return coDocumento;
	}

	public void setCoDocumento(String coDocumento) {
		this.coDocumento = coDocumento;
	}

	public void setCalcular(boolean calcular) {
		this.calcular = calcular;
	}

	public boolean isCalcular() {
		return calcular;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public ConceptoBO getConceptoBO() {
		return conceptoBO;
	}

	public void setConceptoBO(ConceptoBO conceptoBO) {
		this.conceptoBO = conceptoBO;
	}

	public ConceptoModeloBO getConceptoModeloBO() {
		return conceptoModeloBO;
	}

	public void setConceptoModeloBO(ConceptoModeloBO conceptoModeloBO) {
		this.conceptoModeloBO = conceptoModeloBO;
	}

	public EstadoTramitadorOnlineVO getEstadoTramitadorOnlineVO() {
		return estadoTramitadorOnlineVO;
	}

	public void setEstadoTramitadorOnlineVO(
			EstadoTramitadorOnlineVO estadoTramitadorOnlineVO) {
		this.estadoTramitadorOnlineVO = estadoTramitadorOnlineVO;
	}

	public List<MunicipioDTO> getListaMunicipiosDocOri() {
		return listaMunicipiosDocOri;
	}

	public void setListaMunicipiosDocOri(List<MunicipioDTO> listaMunicipiosDocOri) {
		this.listaMunicipiosDocOri = listaMunicipiosDocOri;
	}

	public String getMunicipioDocOri() {
		return municipioDocOri;
	}

	public void setMunicipioDocOri(String municipioDocOri) {
		this.municipioDocOri = municipioDocOri;
	}

	public String getCoMunicipioDocOri() {
		return coMunicipioDocOri;
	}

	public void setCoMunicipioDocOri(String coMunicipioDocOri) {
		this.coMunicipioDocOri = coMunicipioDocOri;
	}

	public List<ConceptoDTO> getListaConceptosDocOri() {
		return listaConceptosDocOri;
	}

	public void setListaConceptosDocOri(List<ConceptoDTO> listaConceptosDocOri) {
		this.listaConceptosDocOri = listaConceptosDocOri;
	}

	public String getConceptoDocOri() {
		return conceptoDocOri;
	}

	public void setConceptoDocOri(String conceptoDocOri) {
		this.conceptoDocOri = conceptoDocOri;
	}

	public String getCoConceptoDocOri() {
		return coConceptoDocOri;
	}

	public void setCoConceptoDocOri(String coConceptoDocOri) {
		this.coConceptoDocOri = coConceptoDocOri;
	}

	public String getModeloDocOri() {
		return modeloDocOri;
	}

	public void setModeloDocOri(String modeloDocOri) {
		this.modeloDocOri = modeloDocOri;
	}

	public String getCoModeloDocOri() {
		return coModeloDocOri;
	}

	public void setCoModeloDocOri(String coModeloDocOri) {
		this.coModeloDocOri = coModeloDocOri;
	}

	public String getVersionDocOri() {
		return versionDocOri;
	}

	public void setVersionDocOri(String versionDocOri) {
		this.versionDocOri = versionDocOri;
	}

	public String getCoVersionDocOri() {
		return coVersionDocOri;
	}

	public void setCoVersionDocOri(String coVersionDocOri) {
		this.coVersionDocOri = coVersionDocOri;
	}

	public boolean isSegundoFormulario() {
		return segundoFormulario;
	}

	public void setSegundoFormulario(boolean segundoFormulario) {
		this.segundoFormulario = segundoFormulario;
	}

	public String getClienteDocOri() {
		return clienteDocOri;
	}

	public void setClienteDocOri(String clienteDocOri) {
		this.clienteDocOri = clienteDocOri;
	}

	public String getCoClienteDocOri() {
		return coClienteDocOri;
	}

	public void setCoClienteDocOri(String coClienteDocOri) {
		this.coClienteDocOri = coClienteDocOri;
	}

	public String getNifDocOri() {
		return nifDocOri;
	}

	public void setNifDocOri(String nifDocOri) {
		this.nifDocOri = nifDocOri;
	}

	public boolean isBuscadorDocOriAbierto() {
		return buscadorDocOriAbierto;
	}

	public void setBuscadorDocOriAbierto(boolean buscadorDocOriAbierto) {
		this.buscadorDocOriAbierto = buscadorDocOriAbierto;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getPorPagina() {
		return porPagina;
	}

	public void setPorPagina(int porPagina) {
		this.porPagina = porPagina;
	}

	public GadirPaginatedList<G743DocumentoVO> getListaDocumentosDocOri() {
		return listaDocumentosDocOri;
	}

	public void setListaDocumentosDocOri(
			GadirPaginatedList<G743DocumentoVO> listaDocumentosDocOri) {
		this.listaDocumentosDocOri = listaDocumentosDocOri;
	}

	public String getRefObjTrib1DocOri() {
		return refObjTrib1DocOri;
	}

	public void setRefObjTrib1DocOri(String refObjTrib1DocOri) {
		this.refObjTrib1DocOri = refObjTrib1DocOri;
	}

	public boolean isPedirImpresion() {
		return pedirImpresion;
	}

	public void setPedirImpresion(boolean pedirImpresion) {
		this.pedirImpresion = pedirImpresion;
	}

	public List<KeyValue> getListaImpresiones() {
		return listaImpresiones;
	}

	public void setListaImpresiones(List<KeyValue> listaImpresiones) {
		this.listaImpresiones = listaImpresiones;
	}

	public String getEjecutaIncidencia() {
		return ejecutaIncidencia;
	}

	public void setEjecutaIncidencia(String ejecutaIncidencia) {
		this.ejecutaIncidencia = ejecutaIncidencia;
	}

	
}
