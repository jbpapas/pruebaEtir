package es.dipucadiz.etir.comun.bo.impl;

import es.dipucadiz.etir.comun.bo.HDocumentoCasillaValorBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.dto.HDocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;



/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del modelo {@link HDocumentoCasillaValorDTO}.
 * 
 * @version 1.0 25/01/2010
 * @author SDS[agonzalez]
 */
public class HDocumentoCasillaValorBOImpl extends
		AbstractGenericBOImpl<HDocumentoCasillaValorDTO, Long> implements
		HDocumentoCasillaValorBO {
	
	/**
     * Atributo que almacena el serialVersionUID de la clase.
     */
    private static final long serialVersionUID = -2027297003123719675L;
    
	/**
	 * Atributo que almacena el DAO asociado a {@link HDocumentoCasillaValorDTO}.
	 */
    private DAOBase<HDocumentoCasillaValorDTO, Long> hiDocumentoCasillaDao;
	
    /**
	 * Método encargado de actualizar el histórico correspondiente a 
	 * documento casilla valor.
	 * 
	 * @param casilla
	 *            - Objeto de tipo DocumentoCasillaValorDTO
	 * @param proceso
	 *            - Codigo del proceso que lo genera.
	 * 
	 * @throws GadirServiceException
	 *             - Si ocurre cualquier error.
	 */
	public void actualizarHistorico(
			DocumentoCasillaValorDTO casilla, String proceso) throws GadirServiceException {
		try {
			if (proceso != null && proceso.length()>20) {
				proceso = proceso.substring(0, 20);
			}
			HDocumentoCasillaValorDTO historico = new HDocumentoCasillaValorDTO();
			historico.setCoModelo(casilla.getId().getCoModelo());
			historico.setCoVersion(casilla.getId().getCoVersion());
			historico.setNuCasilla(Short.valueOf(casilla.getId().getNuCasilla()));
			historico.setCoDocumento(casilla.getId().getCoDocumento());
			historico.setValor(casilla.getValor());
			historico.setHoja(Short.valueOf(casilla.getId().getHoja()));
			historico.setCoProceso(proceso);
			historico.setFhActualizacion(casilla.getFhActualizacion());
//			historico.getId().setCoEjecucion(coEjecucion);
//			historico.getId().setHTipoMovimiento(HTipoMovimiento);
			this.save(historico);
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error al actualizar el histórico",
					e);
		}
	}
    
    @Override
	public DAOBase<HDocumentoCasillaValorDTO, Long> getDao() {
		return this.getHiDocumentoCasillaDao();
	}

	// GETTERS AND SETTERS

	/**
	 * Método que devuelve el atributo hDocumentoCasillaValorDao.
	 * 
	 * @return hDocumentoCasillaValorDao.
	 */
	public DAOBase<HDocumentoCasillaValorDTO, Long> getHiDocumentoCasillaDao() {
		return hiDocumentoCasillaDao;
	}

	/**
	 * Método que establece el atributo hDocumentoCasillaValorDao.
	 * 
	 * @param hDocumentoCasillaValorDao
	 *            El hDocumentoCasillaValorDao.
	 */
	public void setHiDocumentoCasillaDao(
			DAOBase<HDocumentoCasillaValorDTO, Long> hiDocumentoCasillaDao) {
		this.hiDocumentoCasillaDao = hiDocumentoCasillaDao;
	}
	
	public void auditorias(HDocumentoCasillaValorDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}
}
