package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.CandidatosObtenerBO;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.CandidatoVO;



public class CandidatosObtenerBOImpl extends StoredProcedure implements CandidatosObtenerBO {

	private static final Log LOG = LogFactory.getLog(CandidatosObtenerBOImpl.class);

	public CandidatosObtenerBOImpl(final DataSource dataSource) {
		super(dataSource, "G533_OBTENCION_DE_CANDIDATOS.FU_GA_OBTENER_CANDIDATOS_JAVA"); //FIXME
		setFunction(true);
		declareParameter(new SqlOutParameter("resultado", Types.VARCHAR));
//		declareParameter(new SqlOutParameter("resultado", OracleTypes.CURSOR,
//			new RowMapper() {
//				public Map<String, Object> mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
//					final Map<String, Object> result = new HashMap<String, Object>();
//					final ResultSetMetaData metaData = resultSet.getMetaData();
//					for (int i=1; i<=metaData.getColumnCount(); i++) {
//						result.put(metaData.getColumnName(i), resultSet.getString(i));
//					}
//					return result;
//				}
//			}));
		declareParameter(new SqlParameter("e_identificador", Types.VARCHAR));
		declareParameter(new SqlParameter("e_razon_social", Types.VARCHAR));
		declareParameter(new SqlParameter("e_sigla", Types.VARCHAR));
		declareParameter(new SqlParameter("e_nombre_calle", Types.VARCHAR));
		declareParameter(new SqlParameter("e_provincia", Types.VARCHAR));
		declareParameter(new SqlParameter("e_municipio", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public List<CandidatoVO> execute(String identificador, String nombre, String sigla, String nombreCalle, String coProvincia, String coMunicipio) {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("e_identificador", identificador);
		params.put("e_razon_social", nombre);
		params.put("e_sigla", sigla);
		params.put("e_nombre_calle", nombreCalle);
		params.put("e_provincia", coProvincia);
		params.put("e_municipio", coMunicipio);
		Map<String, Object> resultsList= new HashMap<String, Object>();
		try {
			resultsList = execute(params);
		} catch (Exception e) {
			LOG.debug("Fallo en CandidatosObtenerBOImpl.execute()", e);
		}
		return tratarString((String) resultsList.get("resultado"));
	}

	private List<CandidatoVO> tratarString(String resultado) {
		List<CandidatoVO> candidatoVOs = new ArrayList<CandidatoVO>();
		if (Utilidades.isNotEmpty(resultado)) {
			String[] filas = resultado.split("\\|");
			for (int i = 0; i<filas.length; i++) {
				CandidatoVO candidatoVO = new CandidatoVO();
				String[] datos = filas[i].split("@");
				candidatoVO.setCoCliente(Long.valueOf(datos[0]));
				candidatoVO.setCoUnidadUrbana(Long.valueOf(datos[1]));
				candidatoVO.setCoMensaje(Integer.valueOf(datos[2]));
				candidatoVOs.add(candidatoVO);
			}
		}
		return candidatoVOs;
	}


}
