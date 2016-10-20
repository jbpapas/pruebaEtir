package es.dipucadiz.etir.comun.bo.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.HDocumentoLiquidacionBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.DocumentoSeguimientoDTO;
import es.dipucadiz.etir.comun.dto.HDocumentoLiquidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;

public class HDocumentoLiquidacionBOImpl extends AbstractGenericBOImpl<HDocumentoLiquidacionDTO, Long> implements HDocumentoLiquidacionBO {
	private static final long serialVersionUID = -5271590775753363503L;
	private static final Log LOG = LogFactory.getLog(HDocumentoLiquidacionBOImpl.class);

	private DAOBase<HDocumentoLiquidacionDTO, Long> dao;

	public DAOBase<HDocumentoLiquidacionDTO, Long> getDao() {
		return dao;
	}
	public void setDao(final DAOBase<HDocumentoLiquidacionDTO, Long> dao) {
		this.dao = dao;
	}
	
	public void auditorias(HDocumentoLiquidacionDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		throw new GadirServiceException("No permitido realizar inserts en online.");
	}

	public HDocumentoLiquidacionDTO findBySeguimiento(DocumentoSeguimientoDTO documentoSeguimientoDTO) throws GadirServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(HDocumentoLiquidacionDTO.class);
		criteria.add(Restrictions.eq("coModelo", documentoSeguimientoDTO.getDocumentoDTO().getId().getCoModelo()));
		criteria.add(Restrictions.eq("coVersion", documentoSeguimientoDTO.getDocumentoDTO().getId().getCoVersion()));
		criteria.add(Restrictions.eq("coDocumento", documentoSeguimientoDTO.getDocumentoDTO().getId().getCoDocumento()));
		criteria.add(Restrictions.le("fhActualizacion", documentoSeguimientoDTO.getFhActualizacion()));
		criteria.addOrder(Order.desc("fhActualizacion"));
		List<HDocumentoLiquidacionDTO> hDocumentoLiquidacionDTOs = findByCriteria(criteria, 0, 1);
		HDocumentoLiquidacionDTO result;
		if (hDocumentoLiquidacionDTOs.size() > 0) {
			result = hDocumentoLiquidacionDTOs.get(0);
		} else {
			result = new HDocumentoLiquidacionDTO();
			LOG.info("No se encuentran movimientos en el histórico: " + criteria.toString());
		}
		return result;
	}


}
