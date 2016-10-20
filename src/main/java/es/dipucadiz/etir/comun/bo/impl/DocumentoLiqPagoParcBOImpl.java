package es.dipucadiz.etir.comun.bo.impl;


import es.dipucadiz.etir.comun.bo.DocumentoLiqPagoParcBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DocumentoLiqPagoParcDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DocumentoLiqPagoParcBOImpl extends AbstractGenericBOImpl<DocumentoLiqPagoParcDTO, Long>
implements DocumentoLiqPagoParcBO {

	private static final long serialVersionUID = 6879318165367306079L;

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<DocumentoLiqPagoParcDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<DocumentoLiqPagoParcDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<DocumentoLiqPagoParcDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(DocumentoLiqPagoParcDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
	//	transientObject.setFhActualizacion(Utilidades.getDateActual()); no se hace este set para que no interfiera en los envios RGC
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}
}
