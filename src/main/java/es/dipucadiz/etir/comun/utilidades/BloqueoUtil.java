package es.dipucadiz.etir.comun.utilidades;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.bo.ModeloBO;
import es.dipucadiz.etir.comun.bo.ModeloVersionBO;
import es.dipucadiz.etir.comun.constants.ConceptoConstants;
import es.dipucadiz.etir.comun.constants.ModeloConstants;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class BloqueoUtil {

	private static Logger LOG = LoggerFactory.getLogger(BloqueoUtil.class);
	private static DocumentoBO documentoBO;
	private static ModeloVersionBO modeloVersionBO;
	private static ModeloBO modeloBO;
	public static final short TODOS_EJERCICIOS = 0;
	public static final String TODOS_PERIODOS = "**";

	public static final String KEY_IS_BLOQUEADO = "isBloqueado";
	public static final String KEY_MENSAJE = "mensaje";
	public static final String KEY_ERROR = "error";
	public static final String KEY_EXISTE_PARCIAL = "parcial";
	public static final String KEY_PERIODOS_GENERADOS = "con";
	public static final String KEY_PERIODOS_NO_GENERADOS = "sin";

	private static Map<String, Object> isBloqueado(MunicipioDTO municipioDTO, ConceptoDTO conceptoDTO, String coModelo) {
		return isBloqueado(municipioDTO.getId().getCoProvincia(), municipioDTO.getId().getCoMunicipio(), conceptoDTO.getCoConcepto(), coModelo, ModeloConstants.MODELO_VERSION_GENERICO_DTO.getId().getCoVersion(), TODOS_EJERCICIOS, TODOS_PERIODOS);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> isBloqueado(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, short ejercicio, String periodo) {
		Map<String, Object> resultado = new HashMap<String, Object>();
		String procesos = BatchConstants.CO_PROCESO_GENLIQ + "|" + BatchConstants.CO_PROCESO_GENLIQ_IBI + "|" + BatchConstants.CO_PROCESO_GENREC + "|" + BatchConstants.CO_PROCESO_RECALCULO;
		try {
			if (Utilidades.isEmpty(coProvincia)) coProvincia = MunicipioConstants.MUNICIPIO_GENERICO_DTO.getId().getCoProvincia();
			if (Utilidades.isEmpty(coMunicipio)) coMunicipio = MunicipioConstants.CO_MUNICIPIO_GENERICO;
			if (Utilidades.isEmpty(coConcepto)) coConcepto = ConceptoConstants.CO_CONCEPTO_GENERICO;
			if (Utilidades.isEmpty(coModelo)) coModelo = ModeloConstants.MODELO_GENERICO_DTO.getCoModelo();
			if (Utilidades.isEmpty(coVersion)) coVersion = ModeloConstants.MODELO_VERSION_GENERICO_DTO.getId().getCoVersion();
			if (Utilidades.isEmpty(ejercicio)) ejercicio = TODOS_EJERCICIOS;
			if (Utilidades.isEmpty(periodo)) periodo = TODOS_PERIODOS;
			List<Object> lista = (List<Object>) documentoBO.ejecutaQuerySelect(
					"SELECT GF_BLOQUEO.PROCESO_BLOQUEADO('" + 
						coProvincia + "','" + 
						coMunicipio + "','" + 
						coConcepto + "','" + 
						coModelo + "','" + 
						coVersion + "','" + 
						ejercicio + "','" + 
						periodo + "','" +
						procesos + "') FROM DUAL");
			BigDecimal res = (BigDecimal)lista.get(0);
			boolean isBloqueado = res.intValue() == 1;
			resultado.put(KEY_IS_BLOQUEADO, Boolean.valueOf(isBloqueado));
			if (isBloqueado) {
//				String mensaje = "Existe un bloqueo por proceso en ejecución para " + coProvincia + coMunicipio + " / " + coConcepto;
//				if (!ModeloConstants.MODELO_GENERICO_DTO.getCoModelo().equals(coModelo)) mensaje += " / " + coModelo;
//				if (!ModeloConstants.MODELO_VERSION_GENERICO_DTO.getId().getCoVersion().equals(coVersion)) mensaje += " " + coVersion;
//				if (ejercicio != TODOS_EJERCICIOS) mensaje += " / " + ejercicio;
//				if (!TODOS_PERIODOS.equals(periodo)) mensaje += " / " + periodo;
//				mensaje += ". Por favor espere unos instantes mientras finalice.";
				String mensaje = "Actualmente se está ejecutando un proceso que impide la modificación de los datos a los que se intenta acceder.";
				resultado.put(KEY_MENSAJE, mensaje);
			}
		} catch (GadirServiceException e) {
			LOG.error("Error en llamada a GF_BLOQUEO.PROCESO_BLOQUEADO", e);
		}
		return resultado;
	}

	public static Map<String, Object> isBloqueadoCenso(String coModelo, String coVersion, String coDocumento) {
		Map<String, Object> resultado = null;
		try {
			DocumentoDTO documentoDTO = documentoBO.findById(new DocumentoDTOId(coModelo, coVersion, coDocumento));
			// Utilizar el modelo complementario.
			ModeloDTO modeloDTO = MunicipioConceptoModeloUtil.getModeloDTO(coModelo);
			if ("T".equals(modeloDTO.getTipo()) && ("L".equals(modeloDTO.getSubtipo()) || "F".equals(modeloDTO.getSubtipo()))) {
				if (modeloDTO.getModeloDTO() == null) { // El modelo complementario.
					throw new GadirServiceException("No existe modelo complementario para " + coModelo);
				}
				resultado = isBloqueado(documentoDTO.getMunicipioDTO(), documentoDTO.getConceptoDTO(), modeloDTO.getModeloDTO().getCoModelo());
			} else {
				resultado = new HashMap<String, Object>();
				resultado.put(BloqueoUtil.KEY_IS_BLOQUEADO, Boolean.valueOf(false));
			}
		} catch (GadirServiceException e) {
			LOG.error("Error consultando GA_DOCUMENTO (isBloqueadoCenso)", e);
		}
		return resultado;
	}

	public static Map<String, Object> isGeneradoParcialCenso(String coModelo, String coVersion, String coDocumento) {
		Map<String, Object> resultado = null;
		try {
			DocumentoDTO documentoDTO = documentoBO.findById(new DocumentoDTOId(coModelo, coVersion, coDocumento));
			ModeloDTO modeloDTO = MunicipioConceptoModeloUtil.getModeloDTO(coModelo);
			if ("T".equals(modeloDTO.getTipo()) && ("L".equals(modeloDTO.getSubtipo()) || "F".equals(modeloDTO.getSubtipo()))) {
				if (modeloDTO.getModeloDTO() == null) { // El modelo complementario.
					throw new GadirServiceException("No existe modelo complementario para " + coModelo);
				}
				resultado = isGeneradoParcial(documentoDTO.getMunicipioDTO(), documentoDTO.getConceptoDTO(), modeloDTO.getModeloDTO().getCoModelo());
			} else {
				resultado = new HashMap<String, Object>();
				resultado.put(BloqueoUtil.KEY_EXISTE_PARCIAL, Boolean.valueOf(false));
			}
		} catch (GadirServiceException e) {
			LOG.error("Error consultando GA_DOCUMENTO (isBloqueadoCenso)", e);
		}
		return resultado;
	}
	
	
	private static Map<String, Object> isGeneradoParcial(MunicipioDTO municipioDTO, ConceptoDTO conceptoDTO, String coModelo) {
		return isGeneradoParcial(municipioDTO.getId().getCoProvincia(), municipioDTO.getId().getCoMunicipio(), conceptoDTO.getCoConcepto(), coModelo, ModeloConstants.MODELO_VERSION_GENERICO_DTO.getId().getCoVersion(), TODOS_EJERCICIOS);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> isGeneradoParcial(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion, short ejercicio) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (ModeloConstants.MODELO_GENERICO_DTO.getCoModelo().equals(coModelo)) {
				coModelo = modeloBO.findModeloReciboByConcepto(coConcepto).getCoModelo();
			}
			if (ModeloConstants.MODELO_VERSION_GENERICO_DTO.getId().getCoVersion().equals(coVersion)) {
				coVersion = modeloVersionBO.findVersionVigente(coModelo).getId().getCoVersion();
			}
			List<Object> lista = (List<Object>) documentoBO.ejecutaQuerySelect(
					"SELECT GF_BLOQUEO.RECIBOS_GENERADOS('" + 
						coProvincia + "','" + 
						coMunicipio + "','" + 
						coConcepto + "','" + 
						coModelo + "','" + 
						coVersion + "','" + 
						ejercicio + "') FROM DUAL");
			String res = (String)lista.get(0);
			StringTokenizer stringTokenizer = new StringTokenizer(res,",");
			int i=0;
			boolean existeParcial = false;
			String periodosSin = null;
			String periodosCon = null;
			while (stringTokenizer.hasMoreTokens()) {
				switch (i) {
				case 0:
					existeParcial = "1".equals(stringTokenizer.nextToken());
					result.put(KEY_EXISTE_PARCIAL, Boolean.valueOf(existeParcial));
					break;
				case 1:
					periodosSin = stringTokenizer.nextToken();
					result.put(KEY_PERIODOS_NO_GENERADOS, periodosSin);
					break;
				case 2:
					periodosCon = stringTokenizer.nextToken();
					result.put(KEY_PERIODOS_GENERADOS, periodosCon);
					break;
				}
				i++;
			}
			if (existeParcial) {
				stringTokenizer = new StringTokenizer(periodosSin,"|");
				boolean primero = true;
				periodosSin = "";
				while (stringTokenizer.hasMoreTokens()) {
					if (primero) {
						primero = false;
					} else {
						periodosSin += ", ";
					}
					periodosSin += TablaGt.getValor(TablaGtConstants.TABLA_PERIODO, stringTokenizer.nextToken(), TablaGt.COLUMNA_DESCRIPCION);
				}
				stringTokenizer = new StringTokenizer(periodosCon,"|");
				primero = true;
				periodosCon = "";
				while (stringTokenizer.hasMoreTokens()) {
					if (primero) {
						primero = false;
					} else {
						periodosCon += ", ";
					}
					periodosCon += TablaGt.getValor(TablaGtConstants.TABLA_PERIODO, stringTokenizer.nextToken(), TablaGt.COLUMNA_DESCRIPCION);
				}
				result.put(KEY_MENSAJE, "Los recibos correspondientes al " + periodosSin + " del ejercicio, aún no se han generado. Si se realiza la modificación, deberá recalcular el " + periodosCon + " para que la información de ambos periodos sea la misma.");
			}
		} catch (NullPointerException e) {
			LOG.error(e.getMessage(), e);
			result.put(KEY_ERROR, e.getMessage());
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			result.put(KEY_ERROR, e.getMensaje());
		}
		return result;
	}

	public static DocumentoBO getDocumentoBO() {
		return documentoBO;
	}

	public void setDocumentoBO(DocumentoBO documentoBO) {
		BloqueoUtil.documentoBO = documentoBO;
	}

	public static ModeloVersionBO getModeloVersionBO() {
		return modeloVersionBO;
	}

	public void setModeloVersionBO(ModeloVersionBO modeloVersionBO) {
		BloqueoUtil.modeloVersionBO = modeloVersionBO;
	}

	public static ModeloBO getModeloBO() {
		return modeloBO;
	}

	public void setModeloBO(ModeloBO modeloBO) {
		BloqueoUtil.modeloBO = modeloBO;
	}

}
