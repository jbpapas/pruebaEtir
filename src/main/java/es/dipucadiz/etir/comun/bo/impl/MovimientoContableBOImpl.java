package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.MovimientoContableBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.MovimientoContableDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class MovimientoContableBOImpl extends AbstractGenericBOImpl<MovimientoContableDTO, Long> implements MovimientoContableBO {
	private static final long serialVersionUID = -3983962186669128961L;
	private static final Log LOG = LogFactory.getLog(MovimientoContableBOImpl.class);

	private DAOBase<MovimientoContableDTO, Long> dao;

	public DAOBase<MovimientoContableDTO, Long> getDao() {
		return dao;
	}

	public void setDao(final DAOBase<MovimientoContableDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public void auditorias(MovimientoContableDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());

		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}

	
	public BigDecimal findImporteRestante(MovimientoContableDTO movimientoContableDTO) throws GadirServiceException {
		BigDecimal importeRestante;
		if (!movimientoContableDTO.isBoHaber()) {
			importeRestante = null;
		} else {
			importeRestante = movimientoContableDTO.getImporte();
			// Que sea movimiento debe y que el movimiento haber origen sea el que nos llega por parámetro.
			String [] propNames = {"boHaber", "movimientoContableDTO.coMovimientoContable"};
			Object [] filters = {false, movimientoContableDTO.getCoMovimientoContable()};
			List<MovimientoContableDTO> movimientoContableDTOs = findFiltered(propNames, filters);
			for (MovimientoContableDTO movimientoDebeDTO: movimientoContableDTOs) {
				importeRestante = importeRestante.subtract(movimientoDebeDTO.getImporte());
			}
		}
		return importeRestante;
	}
	
	/*
	@Override
	public BigDecimal findImporteRestante(MovimientoContableDTO movimientoContableDTO) {
		BigDecimal importeRestante;
		if (!movimientoContableDTO.isBoHaber()) {
			importeRestante = null;
		} else {
			importeRestante = new BigDecimal("0");
			if(movimientoContableDTO.getImporte() != null) {
				importeRestante = movimientoContableDTO.getImporte();
				
				DetachedCriteria criterio = DetachedCriteria.forClass(MovimientoContableDTO.class);
				criterio.setProjection(Projections.projectionList() 
				    	.add(Projections.sum("importe"))
				    	.add(Projections.groupProperty("movimientoContableDTO.coMovimientoContable")));
				
				criterio.add(Restrictions.eq("movimientoContableDTO.coMovimientoContable", movimientoContableDTO.getCoMovimientoContable()));
				criterio.add(Restrictions.eq("boHaber", false));
				
				List<Object[]> sumaImporteHaber = getDao().findByCriteriaGenerico(criterio);
				if(!sumaImporteHaber.isEmpty()) {
					Object[] objeto = sumaImporteHaber.get(0);
					importeRestante = importeRestante.subtract((BigDecimal)objeto[0]);	
				}
			}			
		}
		return importeRestante;
	}*/

}
