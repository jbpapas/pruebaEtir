package es.dipucadiz.etir.comun.boStoredProcedure;

import java.util.List;

import es.dipucadiz.etir.comun.vo.CandidatoVO;



public interface CandidatosObtenerBO {
	
	public List<CandidatoVO> execute(String identificador, String nombre, String sigla, String nombreCalle, String coProvincia, String coMunicipio);

}
