package es.dipucadiz.etir.comun.utilidades;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.FuncionDatoCorporativoBO;
import es.dipucadiz.etir.comun.bo.FuncionValidacionBO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.vo.ParametrosDatoCorporativoVO;

public class FuncionPlsqlUtil {
	
	private static FuncionDatoCorporativoBO funcionDatoCorporativoBO;
	private static FuncionValidacionBO funcionValidacionBO;
	
	private static final Log LOG = LogFactory.getLog(FuncionPlsqlUtil.class);
	
	public static boolean validacion(String nombreValidacion, String[] parametros) throws GadirServiceException {

		boolean resultado=funcionValidacionBO.execute( //TODO
				nombreValidacion, 
				(parametros.length>0)?parametros[0]:"",
				(parametros.length>1)?parametros[1]:"",
				(parametros.length>2)?parametros[2]:"",
				(parametros.length>3)?parametros[3]:"",
				(parametros.length>4)?parametros[4]:"",	
				(parametros.length>5)?parametros[5]:"",		
				(parametros.length>6)?parametros[6]:"",		
				(parametros.length>7)?parametros[7]:"",
				(parametros.length>8)?parametros[8]:"",	
				(parametros.length>9)?parametros[9]:"",	
				(parametros.length>10)?parametros[10]:"",	
				(parametros.length>11)?parametros[11]:"",
				(parametros.length>12)?parametros[12]:"",
				(parametros.length>13)?parametros[13]:"",
				(parametros.length>14)?parametros[14]:"",
				(parametros.length>15)?parametros[15]:"",
				(parametros.length>16)?parametros[16]:"",
				(parametros.length>17)?parametros[17]:"",
				(parametros.length>18)?parametros[18]:"",
				(parametros.length>19)?parametros[19]:"",
				(parametros.length>20)?parametros[20]:"",
				(parametros.length>21)?parametros[21]:"",
				(parametros.length>22)?parametros[22]:"",
				(parametros.length>23)?parametros[23]:"",
				(parametros.length>24)?parametros[24]:"",
				(parametros.length>25)?parametros[25]:"",
				(parametros.length>26)?parametros[26]:"",
				(parametros.length>27)?parametros[27]:"",
				(parametros.length>28)?parametros[28]:"",
				(parametros.length>29)?parametros[29]:""
		);
		
		return resultado;
	}
	
	public static String datoCorporativo(String datoCorporativo, int argumento, String[] parametros) throws GadirServiceException {
		
		String resultado=funcionDatoCorporativoBO.execute(
				datoCorporativo, 
				argumento, 
				(!Utilidades.isEmpty(parametros[0]))?parametros[0]:"",
				(!Utilidades.isEmpty(parametros[1]))?parametros[1]:"",
				(!Utilidades.isEmpty(parametros[2]))?parametros[2]:"",
				(!Utilidades.isEmpty(parametros[3]))?parametros[3]:"",
				(!Utilidades.isEmpty(parametros[4]))?parametros[4]:"",	
				(!Utilidades.isEmpty(parametros[5]))?parametros[5]:"",		
				(!Utilidades.isEmpty(parametros[6]))?parametros[6]:"",		
				(!Utilidades.isEmpty(parametros[7]))?parametros[7]:"",
				(!Utilidades.isEmpty(parametros[8]))?parametros[8]:"",	
				(!Utilidades.isEmpty(parametros[9]))?parametros[9]:"",	
				(!Utilidades.isEmpty(parametros[10]))?parametros[10]:"",	
				(!Utilidades.isEmpty(parametros[11]))?parametros[11]:"",
				(!Utilidades.isEmpty(parametros[12]))?parametros[12]:"",
				(!Utilidades.isEmpty(parametros[13]))?parametros[13]:"",
				(!Utilidades.isEmpty(parametros[14]))?parametros[14]:"",
				(!Utilidades.isEmpty(parametros[15]))?parametros[15]:"",
				(!Utilidades.isEmpty(parametros[16]))?parametros[16]:"",
				(!Utilidades.isEmpty(parametros[17]))?parametros[17]:"",
				(!Utilidades.isEmpty(parametros[18]))?parametros[18]:"",
				(!Utilidades.isEmpty(parametros[19]))?parametros[19]:"",
				(!Utilidades.isEmpty(parametros[20]))?parametros[20]:"");
		
		return resultado;
	}
	
	public static String datoCorporativo(String datoCorporativo, int argumento, ParametrosDatoCorporativoVO paramsVO) throws GadirServiceException {
		
		String resultado=funcionDatoCorporativoBO.execute(
				datoCorporativo, 
				argumento, 
				paramsVO.getCoModelo(),
				paramsVO.getCoVersion(),
				paramsVO.getCoDocumento(),
				paramsVO.getCoProvincia(),
				paramsVO.getCoMunicipio(),	
				paramsVO.getPeriodo(),		
				paramsVO.getEjercicio(),		
				paramsVO.getCoConcepto(),
				paramsVO.getCoCliente(),	
				paramsVO.getCoDomicilio(),
				paramsVO.getCoUnidadUrbana(),	
				paramsVO.getCoTerritorial(),	
				paramsVO.getCoUnidadAdministrativa(),
				paramsVO.getNuHoja(),
				paramsVO.getCoProcesoAccion(),
				paramsVO.getCoRue(),
				paramsVO.getCoRueOri(),
				paramsVO.getCoModeloOri(),
				paramsVO.getCoVersionOri(),
				paramsVO.getCoDocumentoOri(),
				paramsVO.getCoAuxiliar());

		return resultado;
	}

	public FuncionDatoCorporativoBO getFuncionDatoCorporativoBO() {
		return funcionDatoCorporativoBO;
	}

	public void setFuncionDatoCorporativoBO(
			FuncionDatoCorporativoBO funcionDatoCorporativoBO) {
		FuncionPlsqlUtil.funcionDatoCorporativoBO = funcionDatoCorporativoBO;
	}

	public FuncionValidacionBO getFuncionValidacionBO() {
		return funcionValidacionBO;
	}

	public void setFuncionValidacionBO(
			FuncionValidacionBO funcionValidacionBO) {
		FuncionPlsqlUtil.funcionValidacionBO = funcionValidacionBO;
	}

	
}
