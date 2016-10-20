package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import es.dipucadiz.etir.comun.bo.FuncionValidacionBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.Mensaje;


public class FuncionValidacionBOImpl extends StoredProcedure implements FuncionValidacionBO {

	public FuncionValidacionBOImpl(final DataSource dataSource) {
		super(dataSource, "FUNCION_VALIDACION.VALIDACION_SIMPLE");
		setFunction(true);
		declareParameter(new SqlOutParameter("result", Types.NUMERIC));
		declareParameter(new SqlParameter("e_nombre_validacion", Types.VARCHAR));
		declareParameter(new SqlOutParameter("s_resultado", Types.NUMERIC));
		declareParameter(new SqlOutParameter("s_msg", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro1", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro2", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro3", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro4", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro5", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro6", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro7", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro8", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro9", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro10", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro11", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro12", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro13", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro14", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro15", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro16", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro17", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro18", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro19", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro20", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro21", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro22", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro23", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro24", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro25", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro26", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro27", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro28", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro29", Types.VARCHAR));
		declareParameter(new SqlParameter("parametro30", Types.VARCHAR));
		compile();
	}

	@SuppressWarnings("unchecked")
	public boolean execute(final String e_nombre_validacion, final String parametro1, final String parametro2, final String parametro3, final String parametro4, final String parametro5, final String parametro6, final String parametro7, final String parametro8, final String parametro9, final String parametro10, final String parametro11, final String parametro12, final String parametro13, final String parametro14, final String parametro15, final String parametro16, final String parametro17, final String parametro18, final String parametro19, final String parametro20, final String parametro21, final String parametro22, final String parametro23, final String parametro24, final String parametro25, final String parametro26, final String parametro27, final String parametro28, final String parametro29, final String parametro30) throws GadirServiceException {
		final Map params = new HashMap();
		params.put("e_nombre_validacion", e_nombre_validacion);
		params.put("parametro1", parametro1);
		params.put("parametro2", parametro2);
		params.put("parametro3", parametro3);
		params.put("parametro4", parametro4);
		params.put("parametro5", parametro5);
		params.put("parametro6", parametro6);
		params.put("parametro7", parametro7);
		params.put("parametro8", parametro8);
		params.put("parametro9", parametro9);
		params.put("parametro10", parametro10);
		params.put("parametro11", parametro11);
		params.put("parametro12", parametro12);
		params.put("parametro13", parametro13);
		params.put("parametro14", parametro14);
		params.put("parametro15", parametro15);
		params.put("parametro16", parametro16);
		params.put("parametro17", parametro17);
		params.put("parametro18", parametro18);
		params.put("parametro19", parametro19);
		params.put("parametro20", parametro20);
		params.put("parametro21", parametro21);
		params.put("parametro22", parametro22);
		params.put("parametro23", parametro23);
		params.put("parametro24", parametro24);
		params.put("parametro25", parametro25);
		params.put("parametro26", parametro26);
		params.put("parametro27", parametro27);
		params.put("parametro28", parametro28);
		params.put("parametro29", parametro29);
		params.put("parametro30", parametro30);
		
		final Map results = execute(params);
		BigDecimal coError = (BigDecimal) results.get("result");
		String texto = (String) results.get("s_msg");
		if(coError.intValue()!=0){
			throw new GadirServiceException(Mensaje.getTexto(coError.intValue(), texto));
		}
		int valor = ((BigDecimal) results.get("s_resultado")).intValue();
		return valor == 1;
	}

}
