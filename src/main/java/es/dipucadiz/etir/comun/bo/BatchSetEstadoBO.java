package es.dipucadiz.etir.comun.bo;




public interface BatchSetEstadoBO {
	
	public int execute(int codigoEjecucion, char estado, int codigoTerminacion, String usuarioAct, String observaciones);

}
