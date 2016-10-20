package es.dipucadiz.etir.comun.boStoredProcedure.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.boStoredProcedure.TerrGetCodtersCompatsBO;



public class TerrGetCodtersCompatsBOImpl extends StoredProcedure implements TerrGetCodtersCompatsBO {

	private static final Log LOG = LogFactory.getLog(TerrGetCodtersCompatsBOImpl.class);

	public TerrGetCodtersCompatsBOImpl(final DataSource dataSource) {
		super(dataSource, "CONTROL_TERRITORIAL.getCodtersCompats2");
		setFunction(true);
		declareParameter(new SqlOutParameter("result", Types.VARCHAR));
		declareParameter(new SqlParameter("e_codigoTerritorial", Types.VARCHAR));
		declareParameter(new SqlParameter("e_modo", Types.VARCHAR));
		compile();
	}
	
	public List<String> execute(final String codigoTerritorial, char modo) {
		LOG.trace("Ejecuto getCodtersCompats2: " + codigoTerritorial);
		final Map<String, String> params = new HashMap<String, String>();
		params.put("e_codigoTerritorial", codigoTerritorial);
		params.put("e_modo", String.valueOf(modo));
		List<String> resultsList= new ArrayList<String>();
		try {
			String result =  (String)execute(params).get("result");
			resultsList = Arrays.asList(result.split(","));
		} catch (Exception e) {
			LOG.debug("Fallo en TerrGetCodtersCompatsBOImpl.execute()", e);
		}
		
		return resultsList;
	}

}
