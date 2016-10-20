package es.dipucadiz.etir.comun.utilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.ConceptoBO;
import es.dipucadiz.etir.comun.bo.ModeloBO;
import es.dipucadiz.etir.comun.bo.ModeloVersionBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.bo.ProvinciaBO;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class MunicipioConceptoModeloUtil {
	
	private static MunicipioBO municipioBO;
	private static ProvinciaBO provinciaBO;
	private static ConceptoBO conceptoBO;
	private static ModeloBO modeloBO;
	private static ModeloVersionBO modeloVersionBO;
	
	private static Map<String, MunicipioDTO> municipiosMap = new HashMap<String, MunicipioDTO>();
	private static Map<String, ConceptoDTO> conceptosMap = new HashMap<String, ConceptoDTO>();
	private static Map<String, ModeloDTO> modelosMap = new HashMap<String, ModeloDTO>();
	
	private static final Log LOG = LogFactory.getLog(MunicipioConceptoModeloUtil.class);
	
	public static MunicipioDTO getMunicipioDTO(String coProvincia, String coMunicipio){
		return getMunicipioDTO(coProvincia + coMunicipio);
	}
	
	public static MunicipioDTO getMunicipioDTO(String coMunicipioCompleto){
		MunicipioDTO result=null;
		try{
			result=municipiosMap.get(coMunicipioCompleto);
			if (result==null){
				MunicipioDTOId id = new MunicipioDTOId(coMunicipioCompleto.substring(0, 2), coMunicipioCompleto.substring(2, 5));
				result=municipioBO.findById(id);
				if (result!=null){
					municipiosMap.put(coMunicipioCompleto, result);
				}
			}
		}catch(Exception e){
			LOG.warn("no se encuentra municipio", e);
		}
		return result;
	}

	public static MunicipioDTO getMunicipioDTOByRowid(String rowid){
		MunicipioDTO result;
		try {
			result = municipioBO.findByRowid(rowid);
		} catch (GadirServiceException e) {
			result = null;
			LOG.error(e.getMensaje(), e);
		}
		return result;
	}
	
	public static Object getMunicipioDTOId(String coMunicipioCompleto) {
		return new MunicipioDTOId(coMunicipioCompleto.substring(0, 2), coMunicipioCompleto.substring(2, 5));
	}
	
	public static String getMunicipioDescripcion(String coMunicipioCompleto){
		String result=coMunicipioCompleto;
		try{
			result = getMunicipioDTO(coMunicipioCompleto).getNombre();
		}catch(Exception e){
			LOG.warn("no se encuentra municipio", e);
		}
		return result;
	}
	
	public static String getMunicipioDescripcion(String coProvincia, String coMunicipio){
		return getMunicipioDescripcion(coProvincia + coMunicipio);
	}
	
	public static String getMunicipioCodigoDescripcion(String coMunicipioCompleto){
		String result=coMunicipioCompleto;
		try{
			result = getMunicipioDTO(coMunicipioCompleto).getCodigoDescripcion();
		}catch(Exception e){
			LOG.warn("no se encuentra municipio", e);
		}
		return result;
	}

	public static String getMunicipioCodigoDescripcion(String coProvincia, String coMunicipio){
		return getMunicipioCodigoDescripcion(coProvincia + coMunicipio);
	}
	
	public static ConceptoDTO getConceptoDTO(String coConcepto){
		ConceptoDTO result=null;
		try{
			result=conceptosMap.get(coConcepto);
			if (result==null){
				
				result=conceptoBO.findById(coConcepto);
				if (result!=null){
					conceptosMap.put(coConcepto, result);
				}
			}
		}catch(Exception e){
			LOG.warn("no se encuentra concepto", e);
		}
		return result;
	}
	
	public static ModeloDTO getModeloDTO(String coModelo){
		ModeloDTO result=null;
		try{
			result=modelosMap.get(coModelo);
			if (result==null){
				
				result=modeloBO.findById(coModelo);
				if (result!=null){
					modelosMap.put(coModelo, result);
				}
			}
		}catch(Exception e){
			LOG.warn("no se encuentra modelo", e);
		}
		return result;
	}
	
	public static String getConceptoDescripcion(String coConcepto){
		String result=coConcepto;
		try{
			result = getConceptoDTO(coConcepto).getNombre();
		}catch(Exception e){
			LOG.warn("no se encuentra concepto", e);
		}
		return result;
	}
	
	public static String getConceptoCodigoDescripcion(String coConcepto){
		String result=coConcepto;
		try{
			result = getConceptoDTO(coConcepto).getCodigoDescripcion();
		}catch(Exception e){
			LOG.warn("no se encuentra concepto", e);
		}
		return result;
	}

	public static String getModeloCodigoDescripcion(String coModelo) {
		String result=coModelo;
		try{
			result = getModeloDTO(coModelo).getCodigoDescripcion();
		}catch(Exception e){
			LOG.warn("no se encuentra concepto", e);
		}
		return result;
	}
	
	public static List<String> getListaCoModelo(List<ModeloDTO> modeloDTOs) {
		List<String> coModelos = new ArrayList<String>();
		for (ModeloDTO modeloDTO : modeloDTOs) {
			coModelos.add(modeloDTO.getCoModelo());
		}
		return coModelos;
	}
	
	
	public static void vaciarMunicipiosMap() {
		municipiosMap = new HashMap<String, MunicipioDTO>();
	}
	
	public static void vaciarConceptosMap() {
		conceptosMap = new HashMap<String, ConceptoDTO>();
	}
	
	public static void vaciarModelosMap() {
		modelosMap = new HashMap<String, ModeloDTO>();
	}
	
	public MunicipioBO getMunicipioBO() {
		return municipioBO;
	}

	public void setMunicipioBO(MunicipioBO municipioBO) {
		MunicipioConceptoModeloUtil.municipioBO = municipioBO;
	}

	public ProvinciaBO getProvinciaBO() {
		return provinciaBO;
	}

	public void setProvinciaBO(ProvinciaBO provinciaBO) {
		MunicipioConceptoModeloUtil.provinciaBO = provinciaBO;
	}

	public ConceptoBO getConceptoBO() {
		return conceptoBO;
	}

	public void setConceptoBO(ConceptoBO conceptoBO) {
		MunicipioConceptoModeloUtil.conceptoBO = conceptoBO;
	}

	public ModeloBO getModeloBO() {
		return modeloBO;
	}

	public void setModeloBO(ModeloBO modeloBO) {
		MunicipioConceptoModeloUtil.modeloBO = modeloBO;
	}

	public ModeloVersionBO getModeloVersionBO() {
		return modeloVersionBO;
	}

	public void setModeloVersionBO(ModeloVersionBO modeloVersionBO) {
		MunicipioConceptoModeloUtil.modeloVersionBO = modeloVersionBO;
	}

}
