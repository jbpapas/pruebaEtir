package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.DeudaBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.vo.DeudaVO;



public class DeudaBOImpl extends StoredProcedure implements DeudaBO {

	private static final Log LOG = LogFactory.getLog(DeudaBOImpl.class);

	public DeudaBOImpl(final DataSource dataSource) {
		super(dataSource, "EJ_CALCULO.DEUDA");
		LOG.info("EJ_CALCULO.DEUDA");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("e_modelo", Types.VARCHAR));
		declareParameter(new SqlParameter("e_version", Types.VARCHAR));
		declareParameter(new SqlParameter("e_documento", Types.VARCHAR));
		declareParameter(new SqlOutParameter("E_IM_PPAL_PENDIENTE", Types.NUMERIC));
		declareParameter(new SqlOutParameter("E_IM_RECARGO_pte", Types.NUMERIC));
		declareParameter(new SqlOutParameter("E_IM_DEMORA_pte", Types.NUMERIC));
		declareParameter(new SqlOutParameter("E_IM_COSTAS_pte", Types.NUMERIC));
		declareParameter(new SqlOutParameter("E_IM_TOTAL_pte", Types.NUMERIC));
		compile();
	}

	@SuppressWarnings("unchecked")
	public DeudaVO execute(String coModelo, String coVersion, String coDocumento) throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_modelo", coModelo);
		params.put("e_version", coVersion);
		params.put("e_documento", coDocumento);
		Map<String, Object> resultsList = new HashMap<String, Object>();
		DeudaVO deudaVO = new DeudaVO();
		try {
			resultsList = execute(params);
			if (resultsList == null) {
				throw new GadirServiceException("EJ_CALCULO.DEUDA devuelve null.");
			} else {
				if (!resultsList.containsKey("resultado")) {
					throw new GadirServiceException("EJ_CALCULO.DEUDA no contiene resultado.");
				} else {
					int resultado = ((BigDecimal) resultsList.get("resultado")).intValue();
					if (resultado != 0) {
						throw new GadirServiceException(Mensaje.getTexto(resultado));
					} else {
						deudaVO.setImPrincipalPendiente((BigDecimal)resultsList.get("E_IM_PPAL_PENDIENTE"));
						deudaVO.setImRecargoPendiente((BigDecimal)resultsList.get("E_IM_RECARGO_pte"));
						deudaVO.setImDemoraPendiente((BigDecimal)resultsList.get("E_IM_DEMORA_pte"));
						deudaVO.setImCostasPendiente((BigDecimal)resultsList.get("E_IM_COSTAS_pte"));
						deudaVO.setImTotalPendiente((BigDecimal)resultsList.get("E_IM_TOTAL_pte"));
					}
				}
			}
		} catch (Exception e) {
			throw new GadirServiceException(e);
		}
		return deudaVO;
	}

}
