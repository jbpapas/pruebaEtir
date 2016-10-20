package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.List;



public interface TerrGetCodtersCompatsBO {
	
	public List<String> execute(final String codigoTerritorial, char modo);

}
