package es.dipucadiz.etir.comun.bo.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.HDocumentoBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.HDocumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class HDocumentoBOImpl extends
		AbstractGenericBOImpl<HDocumentoDTO, Long> implements HDocumentoBO {

	private static final Log LOG = LogFactory.getLog(HDocumentoBOImpl.class);

	private DAOBase<HDocumentoDTO, Long> dao;

	public DAOBase<HDocumentoDTO, Long> getDao() {
		return dao;
	}
	public void setDao(final DAOBase<HDocumentoDTO, Long> dao) {
		this.dao = dao;
	}


	public void auditorias(HDocumentoDTO transientObject, Boolean saveOnly)
			throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());

		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	public void guardarHDocumento(DocumentoDTO documento, String movimiento)
			throws GadirServiceException {

		HDocumentoDTO hdoc = new HDocumentoDTO();

		hdoc.setHTipoMovimiento(movimiento);

		hdoc.setCoDocumento(documento.getId().getCoDocumento());
		hdoc.setCoModelo(documento.getId().getCoModelo());
		hdoc.setCoVersion(documento.getId().getCoVersion());

		hdoc.setEjercicio(documento.getEjercicio());
		hdoc.setEstado(documento.getEstado());
		hdoc.setFxDevengo(documento.getFxDevengo());
		//hdoc.setFxDomiciliacion(documento.getFxDomiciliacion());
		hdoc.setFxPresentacion(documento.getFxPresentacion());
		hdoc.setPeriodo(documento.getPeriodo());
		hdoc.setProcedencia(documento.getProcedencia());
		hdoc.setRefCatastral(documento.getRefCatastral());
		hdoc.setRefDomiciliacion(documento.getRefDomiciliacion());
		hdoc.setRefObjTributario1(documento.getRefObjTributario1());
		hdoc.setRefObjTributario2(documento.getRefObjTributario2());
		hdoc.setRefObjTributario3(documento.getRefObjTributario3());
		hdoc.setBoIndividual(documento.getBoIndividual());

		if (documento.getCargaControlRecepcionDTO() != null) {
			hdoc
					.setCoCargaControlRecepcion(documento
							.getCargaControlRecepcionDTO()
							.getCoCargaControlRecepcion());
		}
		if (documento.getClienteDTO() != null) {
			hdoc.setCoCliente(documento.getClienteDTO().getCoCliente());
		}
		if (documento.getCodigoTerritorialDTO() != null) {
			hdoc.setCoCodigoTerritorial(documento.getCodigoTerritorialDTO()
					.getCoCodigoTerritorial());
		}if (documento.getConceptoDTO() != null) {
			hdoc.setCoConcepto(documento.getConceptoDTO().getCoConcepto());
		}
		if (documento.getDocumentoDTOByOrigenDocuni() != null) {
			hdoc.setCoDocumentoOrigen(documento.getDocumentoDTOByOrigenDocuni()
					.getId().getCoDocumento());
			hdoc.setCoModeloOrigen(documento.getDocumentoDTOByOrigenDocuni()
					.getId().getCoModelo());
			hdoc.setCoVersionOrigen(documento.getDocumentoDTOByOrigenDocuni()
					.getId().getCoVersion());
		}
		if (documento.getDocumentoDTOByResumenDocumento() != null) {
			hdoc.setCoDocumentoResumen(documento
					.getDocumentoDTOByResumenDocumento().getId()
					.getCoDocumento());
			hdoc.setCoModeloResumen(documento
					.getDocumentoDTOByResumenDocumento().getId().getCoModelo());
			hdoc
					.setCoVersionResumen(documento
							.getDocumentoDTOByResumenDocumento().getId()
							.getCoVersion());
		}
		if (documento.getDomiciliacionDTO() != null) {
			hdoc.setCoDomiciliacion(documento.getDomiciliacionDTO()
					.getCoDomiciliacion());
		}
		if (documento.getDomicilioDTOByCoDomicilio() != null) {
			hdoc.setCoDomicilio(documento.getDomicilioDTOByCoDomicilio()
					.getCoDomicilio());
		}
		if (documento.getDomicilioDTOByCoDomicilioFiscal() != null) {
			hdoc.setCoDomicilioFiscal(documento
					.getDomicilioDTOByCoDomicilioFiscal().getCoDomicilio());
		}
		if (documento.getMunicipioDTO() != null) {
			hdoc.setCoMunicipio(documento.getMunicipioDTO().getId()
					.getCoMunicipio());
			hdoc.setCoProvincia(documento.getMunicipioDTO().getId()
					.getCoProvincia());
		}
		if (documento.getSituacionDTO() != null) {
			hdoc.setCoSituacion(documento.getSituacionDTO().getCoSituacion());
		}
		if (documento.getUnidadAdministrativaDTO() != null) {
			hdoc.setCoUnidadAdministrativa(documento
					.getUnidadAdministrativaDTO().getCoUnidadAdministrativa());
		}

		try {
			this.saveOnly(hdoc);

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException("Error al registrar el historico:",
					e);
		}

	}
}
