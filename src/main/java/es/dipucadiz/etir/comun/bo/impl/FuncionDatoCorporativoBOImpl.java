package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.bo.FuncionDatoCorporativoBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


public class FuncionDatoCorporativoBOImpl extends StoredProcedure implements FuncionDatoCorporativoBO {

	public FuncionDatoCorporativoBOImpl(final DataSource dataSource) {
		super(dataSource, "FUNCION_DATO_CORPORATIVO.DATO_CORPORATIVO_SIMPLE");
		setFunction(true);
		declareParameter(new SqlOutParameter("result", Types.NUMERIC));
		declareParameter(new SqlParameter("e_nombre_dato_corporativo", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_modelo", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_version", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_documento", Types.VARCHAR));
		
		declareParameter(new SqlParameter("e_co_provincia", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_municipio", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_periodo", Types.VARCHAR));
		declareParameter(new SqlParameter("e_ejercicio", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_concepto", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_cliente", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_domicilio", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_unidad_urbana", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_territorial", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_unidad_administrativa", Types.VARCHAR));
		declareParameter(new SqlParameter("e_nu_hoja", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_proceso_accion", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_rue", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_rue_ori", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_modelo_ori", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_version_ori", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_documento_ori", Types.VARCHAR));
		declareParameter(new SqlParameter("e_co_auxiliar", Types.VARCHAR));
		
		declareParameter(new SqlParameter("e_nu_argumento", Types.NUMERIC));
		declareParameter(new SqlOutParameter("s_valor", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public String execute(final String datoCorporativo, final int argumento, String co_modelo, String co_version, String co_documento,
			String co_provincia, String co_municipio, String co_periodo, String ejercicio, String co_concepto, String co_cliente, 
			String co_domicilio, String co_unidad_urbana, String co_territorial, String co_unidad_administrativa, String nu_hoja, 
			String co_proceso_accion, String co_rue, String co_rue_ori, String co_modelo_ori, 
			String co_version_ori, String co_documento_ori, String co_auxiliar) throws GadirServiceException {
		
		final Map params = new HashMap();
		params.put("e_nombre_dato_corporativo", datoCorporativo);
		params.put("e_co_modelo", co_modelo);
		params.put("e_co_version", co_version);
		params.put("e_co_documento", co_documento);
		params.put("e_co_provincia", co_provincia);
		params.put("e_co_municipio", co_municipio);
		params.put("e_co_periodo", co_periodo);
		params.put("e_ejercicio", ejercicio);
		params.put("e_co_concepto", co_concepto);
		params.put("e_co_cliente", co_cliente);
		params.put("e_co_domicilio", co_domicilio);
		params.put("e_co_unidad_urbana", co_unidad_urbana);
		params.put("e_co_territorial", co_territorial);
		params.put("e_co_unidad_administrativa", co_unidad_administrativa);
		params.put("e_nu_hoja", nu_hoja);
		params.put("e_co_proceso_accion", co_proceso_accion);
		params.put("e_co_rue", co_rue);
		params.put("e_co_rue_ori", co_rue_ori);
		params.put("e_co_modelo_ori", co_modelo_ori);
		params.put("e_co_version_ori", co_version_ori);
		params.put("e_co_documento_ori", co_documento_ori);
		params.put("e_co_auxiliar", co_auxiliar);
		
		params.put("e_nu_argumento", argumento);
		final Map results = execute(params);
		BigDecimal coError = (BigDecimal) results.get("result");
		
		if (coError.intValue()!=0){
			//throw new GadirServiceException(coError.toPlainString());
			return null;
		}
		String valor = (String) results.get("s_valor");
		return valor;
	}
	
}
