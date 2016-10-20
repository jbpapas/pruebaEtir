package es.dipucadiz.etir.comun.utilidades;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTOId;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.SigreExpedienteDTO;
import es.dipucadiz.etir.comun.dto.SigreExpedienteDTOId;
import es.dipucadiz.etir.comun.dto.SigreSubconceptoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class SigreUtil {
	
	private static GenericBO<SigreSubconceptoDTO, Long> sigreSubconceptoBO;
	private static GenericBO<SigreExpedienteDTO, SigreExpedienteDTOId> sigreExpedienteBO;
	
	private static Map<String, String> descripcionSubconceptoMap = new HashMap<String, String>();
	private static final Log LOG = LogFactory.getLog(SigreUtil.class);
	
	public static String getDescripcionSubconcepto(MunicipioDTOId municipioId, CasillaModeloDTOId casillaId, String coTasa) {
		String key = municipioId.getCoProvincia()+"-"+municipioId.getCoMunicipio()+"-"+casillaId.getCoModelo()+"-"+casillaId.getCoVersion()+"-"+casillaId.getNuCasilla()+"-"+coTasa;
		String value;
		try {
			if (descripcionSubconceptoMap.containsKey(key)) {
				value = descripcionSubconceptoMap.get(key);
			} else {
				String[] propNames = {"municipioDTO.id", "casillaModeloDTO.id", "coTasa"};
				Object[] filters = {municipioId, casillaId, coTasa};
				List<SigreSubconceptoDTO> lista = sigreSubconceptoBO.findFiltered(propNames, filters);
				if (lista.isEmpty()) {
					value = null;
				} else {
					value = lista.get(0).getDescripcion();
				}
				descripcionSubconceptoMap.put(key, value);
			}
		} catch(Exception e){
			LOG.warn("Error recuperando nombre de casilla de la caché.", e);
			value = null;
		}
		return value;
	}
	
	public static void vaciarDescripcionSubconceptoMap() {
		descripcionSubconceptoMap = new HashMap<String, String>();
	}
	
	public static boolean tieneExpedientesSigre(final Long coCliente) {
		DetachedCriteria criterio = DetachedCriteria.forClass(SigreExpedienteDTO.class, "sigexp");
		criterio.add(Restrictions.eq("sigexp.clienteDTO.coCliente", coCliente));
		criterio.add(Restrictions.sqlRestriction("rownum=1"));
		int count;
		try {
			count = sigreExpedienteBO.countByCriteria(criterio);
		} catch (GadirServiceException e) {
			count = -1;
			LOG.error(e.getMensaje(), e);
		}
		return count > 0;
	}

	public static List<SigreExpedienteDTO> getExpedientesSigre(Long coCliente) {
		List<SigreExpedienteDTO> result;
		try {
			result = sigreExpedienteBO.findFiltered(
					new String[] {"clienteDTO.coCliente"}, new Object[] {coCliente}, 
					new String[] {"id.libreria", "id.expediente"}, new int[] {DAOConstant.ASC_ORDER, DAOConstant.DESC_ORDER});
		} catch (GadirServiceException e) {
			result = Collections.emptyList();
			LOG.error(e.getMensaje(), e);
		}
		return result;
	}

	public void setSigreSubconceptoBO(GenericBO<SigreSubconceptoDTO, Long> sigreSubconceptoBO) {
		SigreUtil.sigreSubconceptoBO = sigreSubconceptoBO;
	}

	public GenericBO<SigreSubconceptoDTO, Long> getSigreSubconceptoBO() {
		return sigreSubconceptoBO;
	}

	public static GenericBO<SigreExpedienteDTO, SigreExpedienteDTOId> getSigreExpedienteBO() {
		return sigreExpedienteBO;
	}

	public void setSigreExpedienteBO(GenericBO<SigreExpedienteDTO, SigreExpedienteDTOId> sigreExpedienteBO) {
		SigreUtil.sigreExpedienteBO = sigreExpedienteBO;
	}

	public static Map<String, String> getDescripcionSubconceptoMap() {
		return descripcionSubconceptoMap;
	}

	public static void setDescripcionSubconceptoMap(
			Map<String, String> descripcionSubconceptoMap) {
		SigreUtil.descripcionSubconceptoMap = descripcionSubconceptoMap;
	}

	public static Log getLog() {
		return LOG;
	}

}
