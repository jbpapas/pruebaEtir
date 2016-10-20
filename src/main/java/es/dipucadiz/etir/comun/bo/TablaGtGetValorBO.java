package es.dipucadiz.etir.comun.bo;

import java.util.Map;



public interface TablaGtGetValorBO {
	
	public Map<String, Object> execute(String tabla, String elemento, String columna);

}
