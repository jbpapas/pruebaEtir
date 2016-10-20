/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.DocumentoLiqPagoParcBO;
import es.dipucadiz.etir.comun.boStoredProcedure.DeudaBO;
import es.dipucadiz.etir.comun.boStoredProcedure.DeudaFechaCobrosBO;
import es.dipucadiz.etir.comun.constants.ModeloConstants;
import es.dipucadiz.etir.comun.dto.DocumentoLiqPagoParcDTO;
import es.dipucadiz.etir.comun.dto.DocumentoLiquidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.DeudaVO;


final public class CajetinUtils {
	private static final Log LOG = LogFactory.getLog(CajetinUtils.class);

	private static DeudaBO deudaBO;
	private static DeudaFechaCobrosBO deudaFechaCobrosBO;
	private static DocumentoLiqPagoParcBO documentoLiqPagoParcBO;

	public static String fechaAplicacion(Date fxAplicacion) {
		String result = "";
		Calendar ahora = Calendar.getInstance();

		ahora.set(Calendar.HOUR, 23);
		ahora.set(Calendar.MINUTE, 59);
		ahora.set(Calendar.SECOND, 59);
		ahora.set(Calendar.MILLISECOND, 0);

		if(ahora.getTime().before(fxAplicacion)) {
			result = "!La fecha introducida no puede ser posterior al día de hoy";
		}

		return result;
	}

	public static String fechaEjecutiva(DocumentoLiquidacionDTO docLiqDTO, Date fxAplicacion) {
		String result = "";
		if (docLiqDTO.getFxPasoEjecutiva() != null &&
				EstadoSituacionConstants.EJECUTIVA.equals(docLiqDTO.getEstadoSituacionDTO().getCoEstadoSituacion()) &&
				fxAplicacion.before(docLiqDTO.getFxPasoEjecutiva())) {
			result = "!Fecha de paso a ejecutiva es posterior a la fecha de aplicación (" + Utilidades.dateToDDMMYYYY(docLiqDTO.getFxPasoEjecutiva()) + ")";
		}
		return result;
	}

	public static String circuitoIncompatible(DocumentoLiquidacionDTO docLiqDTO) throws GadirServiceException {
		String result = "";
		
		String resultado = (String)((ArrayList<?>) documentoLiqPagoParcBO.ejecutaQuerySelect("SELECT FU_GA_DOC_CIRCUITOS_INC('" +docLiqDTO.getId().getCoModelo()+"' , '" +docLiqDTO.getId().getCoVersion() + "', '"+ docLiqDTO.getId().getCoDocumento() + "'  ) FROM DUAL")).get(0);

		if (resultado!= null && resultado.equals("SI")){
			result = "!Este documento se encuentra en un circuito que no permite esta operación.";
		}		
		
		return result;
	}

	public static String fechaCobroPermitida(DocumentoLiquidacionDTO docLiqDTO, Date fxAplicacion) throws GadirServiceException {
		String result = "";
		if ((docLiqDTO.getImCobradoCostas() == null || !docLiqDTO.getImCobradoCostas().equals(BigDecimal.ZERO)) &&  
				(docLiqDTO.getImCobradoIntereses() == null || !docLiqDTO.getImCobradoIntereses().equals(BigDecimal.ZERO)) &&   
				(docLiqDTO.getImCobradoPrincipal() == null || !docLiqDTO.getImCobradoPrincipal().equals(BigDecimal.ZERO))  &&  
				(docLiqDTO.getImCobradoRecApremio() == null || !docLiqDTO.getImCobradoRecApremio().equals(BigDecimal.ZERO))) { 
			// Si no hay importe cobrado, se permite el cobro aunque existan cobros y anulaciones
		}else{
			DetachedCriteria dcPagoParc = DetachedCriteria.forClass(DocumentoLiqPagoParcDTO.class);
			dcPagoParc.add(Restrictions.eq("documentoLiquidacionDTO.id.coModelo", docLiqDTO.getId().getCoModelo()));
			dcPagoParc.add(Restrictions.eq("documentoLiquidacionDTO.id.coVersion", docLiqDTO.getId().getCoVersion()));
			dcPagoParc.add(Restrictions.eq("documentoLiquidacionDTO.id.coDocumento", docLiqDTO.getId().getCoDocumento()));		
			dcPagoParc.add(Restrictions.gt("fxCobro", fxAplicacion));	
			dcPagoParc.addOrder(Order.desc("fxCobro"));
			List<DocumentoLiqPagoParcDTO> pagosParciales = documentoLiqPagoParcBO.findByCriteria(dcPagoParc);
			BigDecimal suma = BigDecimal.ZERO;
			for (DocumentoLiqPagoParcDTO pagoDTO : pagosParciales) {
				suma = suma.add(pagoDTO.getImCobradoTotal());
				if (suma.compareTo(BigDecimal.ZERO) > 0) {
					result = "!El documento tiene un cobro posterior a la fecha de aplicación (" + Utilidades.dateToDDMMYYYY(pagoDTO.getFxCobro()) + ")";
					break;
				}
			}
		}
		return result;
	}

	public static String periodoVoluntario(DocumentoLiquidacionDTO docLiqDTO, Date fxAplicacion) {
		String result = "";
		if(docLiqDTO.getEstadoSituacionDTO().getCoEstadoSituacion().equals('V')){
			if( fxAplicacion.before(docLiqDTO.getFxIniVoluntario())){
				result = "!El documento tiene una fecha de inicio de período voluntario posterior a la fecha indicada.";
			}
		}
		return result;
	}

	public static DeudaVO calcularDeuda(DocumentoLiquidacionDTO docLiqDTO, Date fxAplicacion) {
		DeudaVO deudaVO = new DeudaVO();
		String error = "";
		//Cambiamos de formato la fecha - 23 horas 59 min 59 seg
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss+01:00", new Locale("es", "ES"));
		dateFormat.setLenient(false);
		Calendar fechaActualUltimaHora = Calendar.getInstance();
		fechaActualUltimaHora.setTime(fxAplicacion);
		
	    fechaActualUltimaHora.set(Calendar.HOUR_OF_DAY, 23);
	    fechaActualUltimaHora.set(Calendar.MINUTE, 59);
	    fechaActualUltimaHora.set(Calendar.SECOND, 59);
	    try {
			fxAplicacion = dateFormat.parse(dateFormat.format(fechaActualUltimaHora.getTime()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (Utilidades.isEmpty(error)) {
				error = fechaAplicacion(fxAplicacion);
			}
			if (Utilidades.isEmpty(error)) {
				error = fechaEjecutiva(docLiqDTO, fxAplicacion);
			}
			if (Utilidades.isEmpty(error)) {
				error = circuitoIncompatible(docLiqDTO);
			}
			if (Utilidades.isEmpty(error)) {
				error = fechaCobroPermitida(docLiqDTO, fxAplicacion);
			}
			if (Utilidades.isEmpty(error)) {
				error = periodoVoluntario(docLiqDTO, fxAplicacion);
			}
			if (Utilidades.isEmpty(error)) {
				if (ModeloConstants.CO_MODELO_COSTAS.equals(docLiqDTO.getId().getCoModelo())) {
					deudaVO = deudaBO.execute(docLiqDTO.getId().getCoModelo(), docLiqDTO.getId().getCoVersion(), docLiqDTO.getId().getCoDocumento());	    				
				} else {
					deudaVO = deudaFechaCobrosBO.execute(docLiqDTO.getId().getCoModelo(), docLiqDTO.getId().getCoVersion(), docLiqDTO.getId().getCoDocumento(), fxAplicacion);
				}
				if(deudaVO.getImTotalPendiente().compareTo(BigDecimal.ZERO) <= 0) {
					error = "!El documento tiene un importe pendiente igual a 0,00 € para la fecha indicada .";
				}
			}
		} catch (GadirServiceException e) {
			LOG.error(e.getMensaje(), e);
			error = "!" + e.getMensaje();
		}
		deudaVO.setObservaciones(error);
		return deudaVO;
	}




	public DeudaBO getDeudaBO() {
		return deudaBO;
	}

	public void setDeudaBO(DeudaBO deudaBO) {
		CajetinUtils.deudaBO = deudaBO;
	}

	public DeudaFechaCobrosBO getDeudaFechaCobrosBO() {
		return deudaFechaCobrosBO;
	}

	public void setDeudaFechaCobrosBO(DeudaFechaCobrosBO deudaFechaCobrosBO) {
		CajetinUtils.deudaFechaCobrosBO = deudaFechaCobrosBO;
	}

	public DocumentoLiqPagoParcBO getDocumentoLiqPagoParcBO() {
		return documentoLiqPagoParcBO;
	}

	public void setDocumentoLiqPagoParcBO(DocumentoLiqPagoParcBO documentoLiqPagoParcBO) {
		CajetinUtils.documentoLiqPagoParcBO = documentoLiqPagoParcBO;
	}

}