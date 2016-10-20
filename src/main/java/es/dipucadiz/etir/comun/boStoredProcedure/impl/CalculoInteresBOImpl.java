package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.CalculoInteresBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.CalculoInteresesVO;
import es.dipucadiz.etir.comun.vo.DeudaVO;



public class CalculoInteresBOImpl extends StoredProcedure implements CalculoInteresBO  {

	private static final Log LOG = LogFactory.getLog(CalculoInteresBOImpl.class);

	public CalculoInteresBOImpl(final DataSource dataSource) {
		super(dataSource, "EJ_CALCULO.INTERESES");
		LOG.info("EJ_CALCULO.INTERESES");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("e_fx_desde", Types.DATE));
		declareParameter(new SqlParameter("e_fx_hasta", Types.DATE));
		declareParameter(new SqlParameter("e_im_pendiente", Types.NUMERIC));
		declareParameter(new SqlOutParameter("e_im_intereses", Types.NUMERIC));
		declareParameter(new SqlOutParameter("e_dias", Types.NUMERIC));
		declareParameter(new SqlParameter("e_legal", Types.NUMERIC));
 
		compile();
	}

	@SuppressWarnings("unchecked")
	public CalculoInteresesVO execute(Date e_fx_desde, Date e_fx_hasta, Float e_im_pendiente, Float e_im_intereses, Integer e_dias, Integer e_legal) throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_fx_desde", new java.sql.Date(e_fx_desde.getTime()));
		params.put("e_fx_hasta", new java.sql.Date(e_fx_hasta.getTime()));
		params.put("e_im_pendiente", e_im_pendiente);
		params.put("e_im_intereses", e_im_intereses);
		params.put("e_dias", e_dias);
		params.put("e_legal", e_legal);
		Map<String, Object> resultsList = new HashMap<String, Object>();
		CalculoInteresesVO calculoInteresesVO = new CalculoInteresesVO();
		try {
			resultsList = execute(params);
			if (resultsList == null) {
				throw new GadirServiceException("EJ_CALCULO.INTERESES devuelve null.");
			} else {
				if (!resultsList.containsKey("resultado")) {
					throw new GadirServiceException("EJ_CALCULO.INTERESES no contiene resultado.");
				} else {
					int resultado = ((BigDecimal) resultsList.get("resultado")).intValue();
					if (resultado != 0) {
						throw new GadirServiceException(Mensaje.getTexto(resultado));
					} else {
						calculoInteresesVO.setImIntereses((BigDecimal)resultsList.get("e_im_intereses"));
						calculoInteresesVO.setDias((BigDecimal)resultsList.get("e_dias"));
 
					}
				}
			}
		} catch (Exception e) {
			throw new GadirServiceException(e);
		}
		return calculoInteresesVO;
	}

}
