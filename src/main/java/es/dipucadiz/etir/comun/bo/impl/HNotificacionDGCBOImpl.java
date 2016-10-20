/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.HNotificacionDGCBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.HNotificacionDGCDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class HNotificacionDGCBOImpl extends AbstractGenericBOImpl<HNotificacionDGCDTO, Long>
		implements HNotificacionDGCBO {

	private DAOBase<HNotificacionDGCDTO, Long> dao;

	public DAOBase<HNotificacionDGCDTO,Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<HNotificacionDGCDTO, Long> dao) {
		this.dao = dao;
	}

	


	/**
	 * 
	 */
//	public void guardarHNotificacionDGC(DocumentoNotDgcDTO notificacion, String tipoMovimiento) throws GadirServiceException{
//		HNotificacionDGCDTO hNotificacion = new HNotificacionDGCDTO();
//		try {
//			hNotificacion.setAnoExpediente(notificacion.getAnoExpediente());
//			hNotificacion.setBoEnviadoSigre(notificacion.getBoEnviadoSigre());
//			hNotificacion.setBoLiquidable(notificacion.getBoLiquidable());
//			hNotificacion.setBoLiquidado(notificacion.getBoLiquidado());
//			hNotificacion.setBoNotificacionBop(notificacion.getBoNotificacionBop());
//			hNotificacion.setBoResolucionPapel(notificacion.getBoResolucionPapel());
//			hNotificacion.setCoDgc(notificacion.getCoDgc());
//			if (notificacion.getDocumentoDTO() != null){
//				hNotificacion.setCoDocumento(notificacion.getDocumentoDTO().getId().getCoDocumento());
//				hNotificacion.setCoModelo(notificacion.getDocumentoDTO().getId().getCoModelo());
//				hNotificacion.setCoVersion(notificacion.getDocumentoDTO().getId().getCoVersion());
//			}
//			
//			hNotificacion.setCoNotificacionDgc(notificacion.getCoDocumentoNotDgc());
//			hNotificacion.setDocumentoExpediente(notificacion.getDocumentoExpediente());
//			hNotificacion.setEjerEfectoIbi(notificacion.getEjerEfectoIbi());
//			hNotificacion.setEjerEntradaPadron(notificacion.getEjerEntradaPadron());
//			hNotificacion.setEstado(notificacion.getEstado());
//			hNotificacion.setExpedienteDgc(notificacion.getExpedienteDgc());
//			hNotificacion.setFhAnulacion(notificacion.getFhAnulacion());
//			hNotificacion.setFhEnvioSigre(notificacion.getFhEnvioSigre());
//			hNotificacion.setFhImpresion(notificacion.getFhImpresion());
//			hNotificacion.setFxDevolucionDgc(notificacion.getFxDevolucionDgc());
//			hNotificacion.setFxEnvioBop(notificacion.getFxEnvioBop());
//			hNotificacion.setFxEnvioNotificado(notificacion.getFxEnvioNotificado());
//			hNotificacion.setFxNotificacion(notificacion.getFxNotificacion());
//			hNotificacion.setFxPublicacionBop(notificacion.getFxPublicacionBop());
//			hNotificacion.setGrupoAnulacion(notificacion.getGrupoAnulacion());
//			hNotificacion.setGrupoBop(notificacion.getGrupoBop());
//			hNotificacion.setHTipoMovimiento(tipoMovimiento);
//			hNotificacion.setResultadoNotificacion(notificacion.getResultadoNotificacion());
//			hNotificacion.setTipo(notificacion.getTipo());
//			hNotificacion.setTipoAlteracion(notificacion.getTipoAlteracion());
//			hNotificacion.setTipoProcedimiento(notificacion.getTipoProcedimiento());
//			this.saveOnly(hNotificacion);
//			
//			
//		} catch (final Exception e) {
//			log.error(e.getCause(), e);
//			throw new GadirServiceException(
//					"Error al registrar el historico:", e);
//		}
//	}
	public void auditorias(HNotificacionDGCDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
}















