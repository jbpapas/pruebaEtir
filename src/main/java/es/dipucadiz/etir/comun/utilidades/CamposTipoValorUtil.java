package es.dipucadiz.etir.comun.utilidades;

import java.util.ArrayList;
import java.util.List;

import es.dipucadiz.etir.comun.bo.CasillaModeloBO;
import es.dipucadiz.etir.comun.bo.CasillaMunicipioBO;
import es.dipucadiz.etir.comun.bo.FuncionArgumentoBO;
import es.dipucadiz.etir.comun.bo.FuncionBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTO;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTOId;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioDTO;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTO;
import es.dipucadiz.etir.comun.dto.FuncionArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.FuncionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.CampoTipoValorVO;


public class CamposTipoValorUtil {

	private static CasillaModeloBO casillaModeloBO; 
	private static CasillaMunicipioBO casillaMunicipioBO;
	private static FuncionBO funcionBO;
	private static FuncionArgumentoBO funcionArgumentoBO;
	
	
	
	
	
//	private static ExtraccionEstructuraBO extraccionEstructuraBO;
//	private static ExtraccionEstructuraCampoBO extraccionEstructuraCampoBO;
	
	/*public static List<FuncionDTO> getFunciones(){
		
		List<FuncionDTO> lista = new ArrayList<FuncionDTO>();
		
		try{
			lista=funcionBO.findAll();
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}*/
	
	public static List<FuncionDTO> getFuncionesDatoCorporativo(){
		
		List<FuncionDTO> lista = new ArrayList<FuncionDTO>();
		
		try{
			lista=funcionBO.findFiltered("tipo", "D");
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}
	
	public static List<FuncionArgumentoDTO> getArgumentosFuncion(String coFuncion){
		
		List<FuncionArgumentoDTO> lista = new ArrayList<FuncionArgumentoDTO>();
		
		try{
			if(coFuncion!=null && !coFuncion.equals("")){
				lista=funcionArgumentoBO.findFiltered("id.coFuncion", coFuncion, "id.coArgumentoFuncion", DAOConstant.ASC_ORDER);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}
	
	public static List<CasillaModeloDTO> getCasillas(String coModelo, String coVersion){
		
		//TODO ojo estoy cogiendo las casillas del modelo, en lugar de cascon
		
		List<CasillaModeloDTO> lista = new ArrayList<CasillaModeloDTO>();
		
		try{
			lista= casillaModeloBO.findFiltered(new String[]{"id.coModelo", "id.coVersion"}, new String[]{coModelo, coVersion});
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}
	
	public static String traduceTipos(String tipo){
		
		//consultarlo de tablagt
		if (tipo!=null){
			if (tipo.equalsIgnoreCase("S")) return "S - Casilla";
			if (tipo.equalsIgnoreCase("D")) return "D - Dato Corporativo";
			if (tipo.equalsIgnoreCase("K")) return "K - Constante";
			if (tipo.equalsIgnoreCase("C")) return "C - Concatenacion";
			if (tipo.equalsIgnoreCase("E")) return "E - Orden en la estructura";
			if (tipo.equalsIgnoreCase("T")) return "T - Totalizar";
			if (tipo.equalsIgnoreCase("O")) return "O - Contador";
			if (tipo.equalsIgnoreCase("L")) return "L - Totalizador Casilla";
			if (tipo.equalsIgnoreCase("A")) return "A - Totalizador dato corporativo";
			if (tipo.equalsIgnoreCase("Q")) return "Q - Constante final de documento";
			if (tipo.equalsIgnoreCase("Z")) return "Z - Casilla final de documento";
			if (tipo.equalsIgnoreCase("X")) return "X - Dato Corporativo final de documento";
			
			if (tipo.equalsIgnoreCase("Casilla")) return "S";
			if (tipo.equalsIgnoreCase("Dato Corporativo")) return "D";
			if (tipo.equalsIgnoreCase("Constante")) return "K";
			if (tipo.equalsIgnoreCase("Concatenacion")) return "C";
			if (tipo.equalsIgnoreCase("Orden en la estructura")) return "E";
			if (tipo.equalsIgnoreCase("Totalizar")) return "T";
			if (tipo.equalsIgnoreCase("Contador")) return "O";
			if (tipo.equalsIgnoreCase("Totalizador Casilla")) return "L";
			if (tipo.equalsIgnoreCase("Totalizador dato corporativo")) return "A";
			if (tipo.equalsIgnoreCase("Constante final de documento")) return "Q";
			if (tipo.equalsIgnoreCase("Casilla final de documento")) return "Z";
			if (tipo.equalsIgnoreCase("Dato Corporativo final de documento")) return "X";
			
			if (tipo.equalsIgnoreCase("S - Casilla")) return "S";
			if (tipo.equalsIgnoreCase("D - Dato Corporativo")) return "D";
			if (tipo.equalsIgnoreCase("K - Constante")) return "K";
			if (tipo.equalsIgnoreCase("C - Concatenacion")) return "C";
			if (tipo.equalsIgnoreCase("E - Orden en la estructura")) return "E";
			if (tipo.equalsIgnoreCase("T - Totalizar")) return "T";
			if (tipo.equalsIgnoreCase("O - Contador")) return "O";
			if (tipo.equalsIgnoreCase("L - Totalizador Casilla")) return "L";
			if (tipo.equalsIgnoreCase("A - Totalizador dato corporativo")) return "A";
			if (tipo.equalsIgnoreCase("Q - Constante final de documento")) return "Q";
			if (tipo.equalsIgnoreCase("Z - Casilla final de documento")) return "Z";
			if (tipo.equalsIgnoreCase("X - Dato Corporativo final de documento")) return "X";
		}
		
		return tipo;
	}
	
	public static String traduceValores(String tipo, String valor){
		return traduceValores(tipo, valor, null, null);
	}
	
	public static String traduceValores(String tipo, String valor, String coModelo, String coVersion){

		try{
			if(tipo.equals("S") || tipo.equals("T") || tipo.equals("L") || tipo.equals("Z")){
				if (valor!=null){
					if (valor.length()<=3 && Utilidades.isNumeric(valor)){
						CasillaModeloDTOId casillaModeloDTOId = new CasillaModeloDTOId(coModelo, coVersion, Short.valueOf(valor));
						CasillaModeloDTO casillaModeloDTO =casillaModeloBO.findById(casillaModeloDTOId);

						return (valor + " - " + casillaModeloDTO.getNombre());
					}

					if (valor.length()>3 && valor.indexOf(' ')>0){
						return valor.substring(0, valor.indexOf(' ')-1);
					}
				}
			}
			else if(tipo.equals("D") || tipo.equals("A") || tipo.equals("X")){
				
				FuncionDTO funcionDTO = funcionBO.findById(valor);
				
				return funcionDTO.getCodigoDescripcion();
			}
			
 		}catch(Exception e){
			e.printStackTrace();
		}

		return valor;
	}
	
	
	
	public static String traduceValoresAdicionales(String tipo, String valor, String valorAdicional){

		try{
			if(tipo.equals("D") || tipo.equals("A")){
				if (valorAdicional!=null){
					if (Utilidades.isNumeric(valorAdicional)){
						FuncionArgumentoDTOId funcionArgumentoDTOId = new FuncionArgumentoDTOId(valor, Integer.valueOf(valorAdicional));
						FuncionArgumentoDTO funcionArgumentoDTO =funcionArgumentoBO.findById(funcionArgumentoDTOId);

						return funcionArgumentoDTO.getCodigoDescripcion();
					}

					if (valorAdicional.length()>3 && valorAdicional.indexOf(' ')>0){
						return valorAdicional.substring(0, valorAdicional.indexOf(' ')-1);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return valorAdicional;
	}
	
	public static String getTextoValor(String coModelo, String coVersion, CampoTipoValorVO campoTipoValor, boolean incluirModeloVersion, boolean incluirHoja){
		String valor="";
		
		try{
			if (campoTipoValor.getTipo().equalsIgnoreCase("K") || campoTipoValor.getTipo().equalsIgnoreCase("Q")){
				valor = "'" + campoTipoValor.getValor() + "'";
			}
			
			if(campoTipoValor.getTipo().equalsIgnoreCase("O")){
				valor="Contador";
			}
			
			
			if (campoTipoValor.getTipo().equalsIgnoreCase("S") || campoTipoValor.getTipo().equalsIgnoreCase("T") || campoTipoValor.getTipo().equalsIgnoreCase("L") || campoTipoValor.getTipo().equalsIgnoreCase("Z")){
				//if (incluirModeloVersion) {
				//	valor += coModelo + " " + coVersion + " ";
				//}
				for (int i=0; i<3-campoTipoValor.getValor().length();i++){
					valor+="0";
				}
				valor += campoTipoValor.getValor();
				valor += " - ";
				CasillaModeloDTO casillaModeloDTO = casillaModeloBO.findById(new CasillaModeloDTOId(coModelo, coVersion, Short.valueOf(campoTipoValor.getValor())));
				valor += casillaModeloDTO.getNombre();
				
				//TODO decidir si sacamos la hoja
				if (incluirHoja && campoTipoValor.getValorAdicional()!=null && !campoTipoValor.getValorAdicional().equals("")){
					int valorAdicional = Integer.parseInt(campoTipoValor.getValorAdicional());
					if (valorAdicional != 1)
						valor += " (hoja "+ valorAdicional + ")";
				}
			}
			
			if (campoTipoValor.getTipo().equalsIgnoreCase("D") || campoTipoValor.getTipo().equalsIgnoreCase("A") || campoTipoValor.getTipo().equalsIgnoreCase("X")){
				valor += campoTipoValor.getValor();
				valor += "(";
				
				if (Utilidades.isNotEmpty(campoTipoValor.getValorAdicional())) {
					FuncionArgumentoDTO funcionArgumentoDTO = funcionArgumentoBO.findById(new FuncionArgumentoDTOId(campoTipoValor.getValor(), Integer.valueOf(campoTipoValor.getValorAdicional())));
					valor += funcionArgumentoDTO.getId().getCoArgumentoFuncion();
					valor += " - ";
					valor += funcionArgumentoDTO.getNombre();
					valor += ")";
				
					//
					/*
					valor += " -- ";
					valor += coModelo;
					valor += " -- ";
					valor += coVersion;
					//pepe				
					*/
				} else {
					valor += "?)";
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return valor;
	}
	
	public static String getTextoConcatenacion(List<CampoTipoValorVO> listaCampoTipoValor, boolean incluirModeloVersion, boolean incluirHoja){
		return getTextoConcatenacion(null, null, listaCampoTipoValor, incluirModeloVersion, incluirHoja);
	}
	public static String getTextoConcatenacion(String coModelo, String coVersion, List<CampoTipoValorVO> listaCampoTipoValor, boolean incluirModeloVersion, boolean incluirHoja){
		String valor="";
		boolean primera=true;
		try{
			
			for (CampoTipoValorVO campoTipoValorVO : listaCampoTipoValor){
				if (!primera){
					valor+=" & ";
				}else{
					primera=false;
				}
				if (Utilidades.isNotNull(campoTipoValorVO.getCoModelo()) && Utilidades.isNotNull(campoTipoValorVO.getCoVersion())) {
					valor+=getTextoValor(campoTipoValorVO.getCoModelo(), campoTipoValorVO.getCoVersion(), campoTipoValorVO, incluirModeloVersion, incluirHoja);
				} else {
					valor+=getTextoValor(coModelo, coVersion, campoTipoValorVO, incluirModeloVersion, incluirHoja);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return valor;
	}
	
	
	
	
	public static CasillaModeloBO getCasillaModeloBO() {
		return casillaModeloBO;
	}

	public void setCasillaModeloBO(CasillaModeloBO casillaModeloBO) {
		CamposTipoValorUtil.casillaModeloBO = casillaModeloBO;
	}

	public static CasillaMunicipioBO getCasillaMunicipioBO() {
		return casillaMunicipioBO;
	}

	public void setCasillaMunicipioBO(CasillaMunicipioBO casillaMunicipioBO) {
		CamposTipoValorUtil.casillaMunicipioBO = casillaMunicipioBO;
	}

	public static FuncionBO getFuncionBO() {
		return funcionBO;
	}

	public void setFuncionBO(FuncionBO funcionBO) {
		CamposTipoValorUtil.funcionBO = funcionBO;
	}

	public static FuncionArgumentoBO getFuncionArgumentoBO() {
		return funcionArgumentoBO;
	}

	public void setFuncionArgumentoBO(FuncionArgumentoBO funcionArgumentoBO) {
		CamposTipoValorUtil.funcionArgumentoBO = funcionArgumentoBO;
	}

	

	
	
	
	
}
