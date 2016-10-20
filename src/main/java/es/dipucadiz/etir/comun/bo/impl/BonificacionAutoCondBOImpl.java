package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.BonificacionAutoCondBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.BonificacionAutoCondDTO;
import es.dipucadiz.etir.comun.dto.BonificacionAutoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

/**
 * Clase responsable de implementar la lógica de negocio necesaria para el
 * mantenimiento de la clase del banco {@link BonificacionAutoCondDTO}.
 * 
 */
public class BonificacionAutoCondBOImpl extends AbstractGenericBOImpl<BonificacionAutoCondDTO, Long> implements BonificacionAutoCondBO {
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 6879318165367306079L;
	protected static final Log LOG = LogFactory.getLog(BonificacionAutoCondBOImpl.class);

	/**
	 * Atributo que almacena el DAO asociado a banco.
	 */
	private DAOBase<BonificacionAutoCondDTO, Long> dao;
	
	// GETTERS AND SETTERS
	@Override
	public DAOBase<BonificacionAutoCondDTO, Long> getDao() {
		return this.dao;
	}

	public void setDao(final DAOBase<BonificacionAutoCondDTO, Long> dao) {
		this.dao = dao;
	}

	// MÉTODOS //
	
	public void auditorias(BonificacionAutoCondDTO transientObject, Boolean saveOnly) throws GadirServiceException {
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

	public void deleteByBonifiAutoId(Long coBonificacionAuto) throws GadirServiceException {
		List<BonificacionAutoCondDTO> lista = findFiltered("bonificacionAutoDTO.coBonificacionAuto", coBonificacionAuto);
		for (BonificacionAutoCondDTO condDTO : lista) {
			delete(condDTO.getCoBonificacionAutoCond());
		}
	}

	public Byte findOrdenSiguiente(BonificacionAutoDTO bonificacionAutoDTO) {
		Byte result = 1;
		try {
			List<BonificacionAutoCondDTO> lista = findFiltered("bonificacionAutoDTO", bonificacionAutoDTO, "orden", DAOConstant.DESC_ORDER, 0, 1);
			if (!lista.isEmpty()) {
				result = Byte.valueOf((byte) (lista.get(0).getOrden().byteValue() + 1));
			}
		} catch (GadirServiceException e) {
			LOG.error(e);
		}
		return result;
	}


}
