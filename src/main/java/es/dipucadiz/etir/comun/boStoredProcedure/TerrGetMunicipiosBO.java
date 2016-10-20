package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.List;
import java.util.Map;



public interface TerrGetMunicipiosBO {
	
	public List<Map<String, Object>> execute(String codigoTerritorial);

}
