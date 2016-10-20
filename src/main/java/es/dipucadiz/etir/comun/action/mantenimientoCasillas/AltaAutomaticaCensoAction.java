package es.dipucadiz.etir.comun.action.mantenimientoCasillas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.action.AbstractGadirBaseAction;
import es.dipucadiz.etir.comun.action.NuevoDocumentoAction;
import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.ConceptoModeloBO;
import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.MunicipioConceptoModeloUtil;

public class AltaAutomaticaCensoAction extends AbstractGadirBaseAction {
	private static final long serialVersionUID = -7403991882408181441L;
	private static final Log LOG = LogFactory.getLog(AltaAutomaticaCensoAction.class);
	
	private DocumentoBO documentoBO;
	private ConceptoModeloBO conceptoModeloBO;
	private ClienteBO clienteBO;
	
	private boolean seleccionarDestinos;
	private String coModeloOrigen;
	private String coVersionOrigen;
	private String coDocumentoOrigen;
	private Long coCliente;
	private Long coDomicilio;
	private String coModeloDestino;
	private String coVersionDestino;
	private String coDocumentoDestino;

	private List<KeyValue> listaCensos;
	private String[] checked;
	private String[] censoKey;
	private boolean[] checkedValue;
	
	@SuppressWarnings("unchecked")
	public String execute() {
		String result;
		try {
			Map<String, Object> altaAutomaticaEnCenso = (Map<String, Object>) getObjetoSesion(MantenimientoCasillasAction.ALTA_AUTO_CENSO);
			DocumentoDTOId documentoOrigenDTOId = (DocumentoDTOId) altaAutomaticaEnCenso.get("documentoOrigen");
			List<Map<String, Object>> listaDestinos = (List<Map<String, Object>>) altaAutomaticaEnCenso.get("destinos");

			DocumentoDTO documentoOrigenDTO = documentoBO.findById(documentoOrigenDTOId);
			coModeloOrigen = documentoOrigenDTO.getId().getCoModelo();
			coVersionOrigen = documentoOrigenDTO.getId().getCoVersion();
			coDocumentoOrigen = documentoOrigenDTO.getId().getCoDocumento();
			coCliente = documentoOrigenDTO.getClienteDTO().getCoCliente();
			coDomicilio = documentoOrigenDTO.getDomicilioDTOByCoDomicilio().getCoDomicilio();
			
			if (seleccionarDestinos) {
				listaCensos = new ArrayList<KeyValue>(listaDestinos.size());
				checkedValue = new boolean[listaDestinos.size()];
				int i = 0;
				for (Map<String, Object> destino : listaDestinos) {
					ModeloDTO modeloDTO = MunicipioConceptoModeloUtil.getModeloDTO((String) destino.get("coModelo"));
					String key = destino.get("coProvincia") + "|" + destino.get("coMunicipio") + "|" + destino.get("coConcepto") + "|" + destino.get("coModelo") + "|" + destino.get("coVersion");
					String value = modeloDTO.getCodigoDescripcion();
					if (conceptoModeloBO.isMultiConcepto(modeloDTO.getCoModelo())) {
						ConceptoDTO conceptoDTO = MunicipioConceptoModeloUtil.getConceptoDTO((String) destino.get("coConcepto"));
						value += " del concepto " + conceptoDTO.getCodigoDescripcion();
					}
					listaCensos.add(new KeyValue(key, value));
					if ((Boolean)destino.get("seleccionado")) {
						checkedValue[i] = true;
					} else {
						checkedValue[i] = false;
					}
					i++;
				}
				result = INPUT;
			} else {
				result = tratarSiguienteCenso();
			}
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			addActionError(e.getMensaje());
			result = INPUT;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private String tratarSiguienteCenso() {
		String result = null;
		Map<String, Object> altaAutomaticaEnCenso = (Map<String, Object>) getObjetoSesion(MantenimientoCasillasAction.ALTA_AUTO_CENSO);
		DocumentoDTOId documentoOrigenDTOId = (DocumentoDTOId) altaAutomaticaEnCenso.get("documentoOrigen");
		List<Map<String, Object>> listaDestinos = (List<Map<String, Object>>) altaAutomaticaEnCenso.get("destinos");
		boolean algunoTratado = false;
		String modeloFallo = null;
		for (Map<String, Object> destino : listaDestinos) {
			Boolean seleccionado = (Boolean) destino.get("seleccionado");
			Boolean tratado = (Boolean) destino.get("tratado");
			if (seleccionado && !tratado) {
				// Si quedan conceptos sin tratar, ir directamente al mantenimiento de casillas.
				try {
					algunoTratado = true;
					result = tratarDestino(documentoOrigenDTOId, destino);
					destino.put("tratado", Boolean.TRUE);
					break;
				} catch (GadirServiceException e) {
					if (modeloFallo != null) {
						modeloFallo = (String) destino.get("coModelo");
					}
				}
			}
		}
		if (!algunoTratado) {
			// En caso contrario, eliminar el objeto de la sesión y volver por la pila. No quedan censos por tratar.
			result = botonCancelar();
		} else {
			if (result == null) {
				addActionError("Imposible tratar el siguiente censo de la lista" + (modeloFallo == null ? "." : ", modelo " + modeloFallo + "."));
				result = execute();
			}
		}
		return result;
	}

	private String tratarDestino(DocumentoDTOId documentoOrigenDTOId, Map<String, Object> destino) throws GadirServiceException {
		// Realizar alta manual con documento origen e ir al mantenimiento de casillas.
		String result;
		DocumentoDTO documentoDTO = documentoBO.findById(documentoOrigenDTOId);
		if (documentoDTO.getClienteDTO() == null) {
			addActionError("Documento origen no tiene cliente asignado, no se podrá crear el censo.");
			result = execute();
		} else {
			NuevoDocumentoAction nuevoDocumentoAction = (NuevoDocumentoAction) GadirConfig.getBean("nuevoDocumentoAction");;
			nuevoDocumentoAction.setCoMunicipio((String)destino.get("coProvincia") + (String)destino.get("coMunicipio"));
			nuevoDocumentoAction.setCoConcepto((String) destino.get("coConcepto"));
			nuevoDocumentoAction.setCoModelo((String) destino.get("coModelo"));
			nuevoDocumentoAction.setCoVersion((String) destino.get("coVersion"));
			nuevoDocumentoAction.setDocOrigenModelo(documentoOrigenDTOId.getCoModelo());
			nuevoDocumentoAction.setDocOrigenVersion(documentoOrigenDTOId.getCoVersion());
			nuevoDocumentoAction.setDocOrigenDocumento(documentoOrigenDTOId.getCoDocumento());
			nuevoDocumentoAction.setCoCliente(documentoDTO.getClienteDTO().getCoCliente().toString());
			nuevoDocumentoAction.setNif(clienteBO.findById(documentoDTO.getClienteDTO().getCoCliente()).getIdentificador());
			nuevoDocumentoAction.setRefCat(documentoDTO.getRefCatastral());
			nuevoDocumentoAction.setCoDomicilio(documentoDTO.getDomicilioDTOByCoDomicilio().getCoDomicilio());
			nuevoDocumentoAction.setSession(getSession());
			try {
				result = nuevoDocumentoAction.botonAceptar();
				if (!SUCCESS.equals(result)) {
					setActionErrors(nuevoDocumentoAction.getActionErrors());
					setFieldErrors(nuevoDocumentoAction.getFieldErrors());
					result = execute();
					coModeloDestino = null;
					coVersionDestino = null;
					coDocumentoDestino = null;
				} else {
					coModeloDestino = nuevoDocumentoAction.getCoModelo();
					coVersionDestino = nuevoDocumentoAction.getCoVersion();
					coDocumentoDestino = nuevoDocumentoAction.getCoDocumento();
				}
			} catch (GadirServiceException e) {
				LOG.error(e.getMensaje(), e);
				addActionError(e.getMensaje());
				result = execute();
			}
		}
		return result;
	}

	private Map<String, Object> creaObjetoTest() {
		List<Map<String, Object>> listaDestinos = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> posibleDestino = new HashMap<String, Object>();
		posibleDestino.put("coProvincia", "11");
		posibleDestino.put("coMunicipio", "014");
		posibleDestino.put("coConcepto", "0101");
		posibleDestino.put("coModelo", "101");
		posibleDestino.put("coVersion", "1");
		posibleDestino.put("seleccionado", Boolean.FALSE);
		posibleDestino.put("tratado", Boolean.FALSE);
		listaDestinos.add(posibleDestino);

		posibleDestino = new HashMap<String, Object>();
		posibleDestino.put("coProvincia", "11");
		posibleDestino.put("coMunicipio", "014");
		posibleDestino.put("coConcepto", "0104");
		posibleDestino.put("coModelo", "104");
		posibleDestino.put("coVersion", "1");
		posibleDestino.put("seleccionado", Boolean.FALSE);
		posibleDestino.put("tratado", Boolean.FALSE);
		listaDestinos.add(posibleDestino);
		
		posibleDestino = new HashMap<String, Object>();
		posibleDestino.put("coProvincia", "11");
		posibleDestino.put("coMunicipio", "014");
		posibleDestino.put("coConcepto", "0105");
		posibleDestino.put("coModelo", "105");
		posibleDestino.put("coVersion", "1");
		posibleDestino.put("seleccionado", Boolean.FALSE);
		posibleDestino.put("tratado", Boolean.FALSE);
		listaDestinos.add(posibleDestino);
		
		posibleDestino = new HashMap<String, Object>();
		posibleDestino.put("coProvincia", "11");
		posibleDestino.put("coMunicipio", "014");
		posibleDestino.put("coConcepto", "2133");
		posibleDestino.put("coModelo", "133");
		posibleDestino.put("coVersion", "1");
		posibleDestino.put("seleccionado", Boolean.FALSE);
		posibleDestino.put("tratado", Boolean.FALSE);
		listaDestinos.add(posibleDestino);
		
		posibleDestino = new HashMap<String, Object>();
		posibleDestino.put("coProvincia", "11");
		posibleDestino.put("coMunicipio", "014");
		posibleDestino.put("coConcepto", "3133");
		posibleDestino.put("coModelo", "133");
		posibleDestino.put("coVersion", "1");
		posibleDestino.put("seleccionado", Boolean.FALSE);
		posibleDestino.put("tratado", Boolean.FALSE);
		listaDestinos.add(posibleDestino);
		
		Map<String, Object> altaAutomaticaEnCenso = new HashMap<String, Object>();
		altaAutomaticaEnCenso.put("documentoOrigen", new DocumentoDTOId("401", "1", "000366746"));
		altaAutomaticaEnCenso.put("destinos", listaDestinos);
		return altaAutomaticaEnCenso;
	}

	@SuppressWarnings("unchecked")
	public String botonAceptar() {
		Map<String, Object> altaAutomaticaEnCenso = (Map<String, Object>) getObjetoSesion(MantenimientoCasillasAction.ALTA_AUTO_CENSO);
		List<Map<String, Object>> listaDestinos = (List<Map<String, Object>>) altaAutomaticaEnCenso.get("destinos");
		int i = 0;
		for (Map<String, Object> destino : listaDestinos) {
			Boolean marcado = Boolean.FALSE;
			for (String checkedString : checked) {
				if (String.valueOf(i).equals(checkedString)) {
					marcado = Boolean.TRUE;
					break;
				}
			}
			destino.put("seleccionado", marcado);
			i++;
		}
		return tratarSiguienteCenso();
	}
	
	@SuppressWarnings("unchecked")
	public String botonAnular() {
		Map<String, Object> altaAutomaticaEnCenso = (Map<String, Object>) getObjetoSesion(MantenimientoCasillasAction.ALTA_AUTO_CENSO);
		List<Map<String, Object>> listaDestinos = (List<Map<String, Object>>) altaAutomaticaEnCenso.get("destinos");
		for (Map<String, Object> destino : listaDestinos) {
			destino.put("seleccionado", Boolean.FALSE);
		}
		return execute();
	}
	
	public String botonCancelar() {
		String result;
		try {
			volverPilaRedirect();
			borraObjetoSesion(MantenimientoCasillasAction.ALTA_AUTO_CENSO);
			result = BLANK;
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			addActionError(e.getMensaje());
			result = execute();
		}
		return result;
	}
	
	
	public boolean isSeleccionarDestinos() {
		return seleccionarDestinos;
	}

	public void setSeleccionarDestinos(boolean seleccionarDestinos) {
		this.seleccionarDestinos = seleccionarDestinos;
	}

	public List<KeyValue> getListaCensos() {
		return listaCensos;
	}

	public void setListaCensos(List<KeyValue> listaCensos) {
		this.listaCensos = listaCensos;
	}




	public DocumentoBO getDocumentoBO() {
		return documentoBO;
	}




	public void setDocumentoBO(DocumentoBO documentoBO) {
		this.documentoBO = documentoBO;
	}




	public String getCoModeloOrigen() {
		return coModeloOrigen;
	}




	public void setCoModeloOrigen(String coModeloOrigen) {
		this.coModeloOrigen = coModeloOrigen;
	}




	public String getCoVersionOrigen() {
		return coVersionOrigen;
	}




	public void setCoVersionOrigen(String coVersionOrigen) {
		this.coVersionOrigen = coVersionOrigen;
	}




	public String getCoDocumentoOrigen() {
		return coDocumentoOrigen;
	}




	public void setCoDocumentoOrigen(String coDocumentoOrigen) {
		this.coDocumentoOrigen = coDocumentoOrigen;
	}




	public Long getCoCliente() {
		return coCliente;
	}




	public void setCoCliente(Long coCliente) {
		this.coCliente = coCliente;
	}




	public Long getCoDomicilio() {
		return coDomicilio;
	}




	public void setCoDomicilio(Long coDomicilio) {
		this.coDomicilio = coDomicilio;
	}




	public String[] getChecked() {
		return checked;
	}




	public void setChecked(String[] checked) {
		this.checked = checked;
	}




	public String[] getCensoKey() {
		return censoKey;
	}




	public void setCensoKey(String[] censoKey) {
		this.censoKey = censoKey;
	}

	public boolean[] getCheckedValue() {
		return checkedValue;
	}

	public void setCheckedValue(boolean[] checkedValue) {
		this.checkedValue = checkedValue;
	}

	public ConceptoModeloBO getConceptoModeloBO() {
		return conceptoModeloBO;
	}

	public void setConceptoModeloBO(ConceptoModeloBO conceptoModeloBO) {
		this.conceptoModeloBO = conceptoModeloBO;
	}

	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public String getCoModeloDestino() {
		return coModeloDestino;
	}

	public void setCoModeloDestino(String coModeloDestino) {
		this.coModeloDestino = coModeloDestino;
	}

	public String getCoVersionDestino() {
		return coVersionDestino;
	}

	public void setCoVersionDestino(String coVersionDestino) {
		this.coVersionDestino = coVersionDestino;
	}

	public String getCoDocumentoDestino() {
		return coDocumentoDestino;
	}

	public void setCoDocumentoDestino(String coDocumentoDestino) {
		this.coDocumentoDestino = coDocumentoDestino;
	}

}
