package es.dipucadiz.etir.comun.constants;

import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTOId;


final public class ModeloConstants {
	public final static String CO_MODELO_GENERICO = "***";
	public final static ModeloDTO MODELO_GENERICO_DTO = new ModeloDTO("***", "TODOS LOS MODELOS");
	public final static ModeloVersionDTO MODELO_VERSION_GENERICO_DTO = new ModeloVersionDTO(new ModeloVersionDTOId("***","*"), null);

	public final static String CO_VERSION_1 = "1";
	
	public final static String CO_MODELO_PLAZO_PPP = "PPP";
	public final static String CO_MODELO_PLAZO_FRACCIONAMIENTO = "FRA";
	public final static String CO_MODELO_CUADERNO_19_CARGOS = "C19";
	public final static String CO_MODELO_CUADERNO_19_PPP = "P19";
	public final static String CO_MODELO_CUADERNO_19_FRACCIONAMIENTO = "F19";
	public final static String CO_MODELO_CUADERNO_19_DEVOLUCIONES = "D19";
	public final static String CO_MODELO_CUADERNO_19_RESUMEN = "R19";
	public final static String CO_MODELO_BD_DOCUMENTAL = "BDD";
	public final static String CO_MODELO_CUADERNO_60 = "C60";
	public final static String CO_MODELO_CUADERNO_60_RESUMEN = "R60";
	public final static String CO_MODELO_CUADERNO_43 = "C43";
	public final static String CO_MODELO_CUADERNO_43_RESUMEN = "R43";
	public final static String CO_MODELO_BOP = "BOP";
	public final static String CO_MODELO_COSTAS = "COS";
	
	public final static String CO_MODELO_201 = "201";
}
