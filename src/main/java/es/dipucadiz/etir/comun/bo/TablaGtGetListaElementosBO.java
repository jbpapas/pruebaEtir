package es.dipucadiz.etir.comun.bo;

import java.util.List;
import java.util.Map;



public interface TablaGtGetListaElementosBO {
	
	public List<Map<String, Object>> execute(String tabla, String columna);

}
