package es.dipucadiz.etir.comun.utilidades;

import java.util.List;

import es.dipucadiz.etir.comun.bo.ValidacionArgumentoBO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;

public class AyudaCasillaTexto {

	//private static LiquidatorioPeriodoBO liquidatorioPeriodoBO;
	
	//private static final Log LOG = LogFactory.getLog(ProcesoUtil.class);
	protected static ValidacionArgumentoBO validacionArgumentoBO;
	
	public static String textoFormato(){
		String texto="";
		
		try{
			String tipo="";
			int longitud = 0;
			String valorMinimo="";
			String valorMaximo="";
			String valorCasilla="";
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));
			
			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){
				
				String valor="";
				if (validacionArgumentoDTO.getTipo().equals("S")){
					valor = DatosSesion.getAyudaCasillaVO().getCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()));
				}else if (validacionArgumentoDTO.getTipo().equals("K") || validacionArgumentoDTO.getTipo().equals("T")){
					valor = validacionArgumentoDTO.getValor();
				}

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 1:	tipo = valor; break;
				case 2:	try{ longitud = Integer.parseInt(valor);}catch(Exception e){}; break;
				case 3:	valorMinimo = valor; break;
				case 4:	valorMaximo = valor; break;
				case 5:	valorCasilla = valor; break;
				}
			}

			if (tipo.equalsIgnoreCase("T")){
				texto = "ESTA CASILLA DEBE CONTENER UN DATO CON EL FORMATO HHMMSS, \n";
				texto += "HHMMSSd, HH:MM:SS O HH:MM:SS.d";
			}else if (tipo.equalsIgnoreCase("D")){

				//TODO llamar a conseguir fechas para obtener valorMaximo y valorMinimo
				if (longitud==0){
					texto = "ESTA CASILLA DEBE CONTENER UN DATO CON EL FORMATO AAAAMMDD, \n";
					texto += "DDMMAAAA, DD/MM/AAAA O AAAA/MM/DD \n";
					texto += "EL VALOR DEBE ESTAR COMPRENDIDO ENTRE " + "TODO" + " Y " + "TODO"; //TODO
				}else if (longitud==8){
					texto = "ESTA CASILLA DEBE CONTENER UN DATO CON EL FORMATO AAAAMMDD \n";
					texto += "O DDMMAAAA \n";
					texto += "EL VALOR DEBE ESTAR COMPRENDIDO ENTRE " + "TODO" + " Y " + "TODO"; //TODO
				}

			}else if (tipo.equalsIgnoreCase("N")){
				texto = "ESTA CASILLA DEBE CONTENER UN DATO NUMERICO \n";
				texto += "SU LONGITUD MAXIMA DEBE SER " + longitud + " \n";
				texto += "EL VALOR DEBE ESTAR COMPRENDIDO ENTRE " + valorMinimo + " Y " + valorMaximo;
			}else if (tipo.equalsIgnoreCase("A")){
				texto = "ESTA CASILLA DEBE CONTENER UN DATO ALFANUMERICO \n";
				texto += "SU LONGITUD MAXIMA DEBE SER " + longitud + " \n";
				texto += "EL NUMERO DE CARACTERES DEBE ESTAR COMPRENDIDO ENTRE " + valorMinimo + " Y " + valorMaximo;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return texto;
	}
	
	public static String textoEjercicio(){
		String texto="";
		
		try{
			String ejercicio="";
			String ejercicioInferior = "";
			String ejercicioSuperior = "";
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));
			
			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){
				
				String valor="";
				if (validacionArgumentoDTO.getTipo().equals("S")){
					valor = DatosSesion.getAyudaCasillaVO().getCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()));
				}else if (validacionArgumentoDTO.getTipo().equals("K")){
					valor = validacionArgumentoDTO.getValor();
				}

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 1:	ejercicio = valor; break;
				case 2:	ejercicioInferior = valor; break;
				case 3:	ejercicioSuperior = valor; break;
				}
			}
			
			if (ejercicioInferior == null || ejercicioInferior.equals("") || ejercicioInferior.equals("9999")){
				ejercicioInferior = ""+Utilidades.getAnoActual();
			}
			if (ejercicioSuperior == null || ejercicioSuperior.equals("") || ejercicioSuperior.equals("9999")){
				ejercicioSuperior = ""+Utilidades.getAnoActual();
			}
			
			texto = "INTRODUZCA UN EJERCICIO MAYOR O IGUAL QUE " + ejercicioInferior + " Y MENOR O IGUAL QUE " + ejercicioSuperior;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return texto;
	}

	public ValidacionArgumentoBO getValidacionArgumentoBO() {
		return validacionArgumentoBO;
	}

	public void setValidacionArgumentoBO(
			ValidacionArgumentoBO validacionArgumentoBO) {
		AyudaCasillaTexto.validacionArgumentoBO = validacionArgumentoBO;
	}
	
	
	
}
