package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.List;
import java.util.Map;



public interface TerrGetConceptosModeloBO {
	
	public List<Map<String, Object>> execute(String provincia, String municipio, String codigoTerritorial, String codigoModelo);

}
