/**
 * 
 */
package es.dipucadiz.etir.comun.utilidades;

import java.util.List;

import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.exception.GadirServiceException;


final public class CodigoBarrasUtil {
	
	public static String getCodBarras(String coBarras, String tipoNotif) throws GadirServiceException {
		String sql = "Select PQ_CODIGO_BARRAS_AUX.DEVUELVE_CODBAR_23('"+coBarras+"'," + "'"+tipoNotif+"') from dual";
		String res = null;
		MunicipioBO municipioBO = (MunicipioBO) GadirConfig.getBean("municipioBO");
		@SuppressWarnings("unchecked")
		List<Object> lista = (List<Object>)municipioBO.ejecutaQuerySelect(sql);
		res = (String)lista.get(0);
		return res;
	}


	public static String getCodBarras(String coBarras) throws GadirServiceException {
		String sql = "Select PQ_CODIGO_BARRAS_AUX.DEVUELVE_CODIGO_BARRAS('"+coBarras+"') from dual";
		String res = null;
		MunicipioBO municipioBO = (MunicipioBO) GadirConfig.getBean("municipioBO");
		@SuppressWarnings("unchecked")
		List<Object> lista = (List<Object>)municipioBO.ejecutaQuerySelect(sql);
		res = (String)lista.get(0);
		return res;
	}

}


