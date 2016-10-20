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

import es.dipucadiz.etir.comun.boStoredProcedure.DeudaFechaInteresesBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.DeudaVO;



public class DeudaFechaInteresesBOImpl extends StoredProcedure implements DeudaFechaInteresesBO {

	private static final Log LOG = LogFactory.getLog(DeudaFechaInteresesBOImpl.class);

	public DeudaFechaInteresesBOImpl(final DataSource dataSource) {
		super(dataSource, "EJ_CALCULO.DEUDA_FECHA_INTERESES");
		LOG.info("EJ_CALCULO.DEUDA_FECHA_INTERESES");
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.NUMERIC));
		declareParameter(new SqlParameter("E_MODELO", Types.VARCHAR));
		declareParameter(new SqlParameter("E_VERSION", Types.VARCHAR));
		declareParameter(new SqlParameter("E_DOCUMENTO", Types.VARCHAR));
		declareParameter(new SqlOutParameter("E_IM_PPAL_PENDIENTE", Types.NUMERIC));
		declareParameter(new SqlOutParameter("E_IM_RECARGO_pte", Types.NUMERIC));
		declareParameter(new SqlOutParameter("E_IM_DEMORA_pte", Types.NUMERIC));
		declareParameter(new SqlOutParameter("E_IM_COSTAS_pte", Types.NUMERIC));
		declareParameter(new SqlOutParameter("E_IM_TOTAL_pte", Types.NUMERIC));
		declareParameter(new SqlParameter("e_date", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public DeudaVO execute(String coModelo, String coVersion, String coDocumento, Date fecha) throws GadirServiceException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("E_MODELO", coModelo);
		params.put("E_VERSION", coVersion);
		params.put("E_DOCUMENTO", coDocumento);
		params.put("e_date", Utilidades.dateToDDMMYYYY(fecha));
		Map<String, Object> resultsList = new HashMap<String, Object>();
		DeudaVO deudaVO = new DeudaVO();
		try {
			resultsList = execute(params);
			if (resultsList == null) {
				throw new GadirServiceException("EJ_CALCULO.DEUDA_FECHA_INTERESES devuelve null.");
			} else {
				if (!resultsList.containsKey("resultado")) {
					throw new GadirServiceException("EJ_CALCULO.DEUDA_FECHA_INTERESES no contiene resultado.");
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
