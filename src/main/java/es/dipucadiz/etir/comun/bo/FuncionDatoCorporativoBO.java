package es.dipucadiz.etir.comun.bo;

import es.dipucadiz.etir.comun.exception.GadirServiceException;





public interface FuncionDatoCorporativoBO {
	
	public String execute(final String datoCorporativo, final int argumento, String co_modelo, String co_version, String co_documento,
			String co_provincia, String co_municipio, String co_periodo, String ejercicio, String co_concepto, String co_cliente, 
			String co_domicilio, String co_unidad_urbana, String co_territorial, String co_unidad_administrativa, String nu_hoja, 
			String co_proceso_accion, String co_rue, String co_rue_ori, String co_modelo_ori, 
			String co_version_ori, String co_documento_ori, String co_auxiliar) throws GadirServiceException;
	
}
