package es.dipucadiz.etir.comun.action.mantenimientoCasillas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.ValidacionDTO;

public class MCDocumentoVO implements Serializable {
	private static final long serialVersionUID = 2073236890366223255L;

	private DocumentoDTO documentoDTO;
	
	private Boolean repeticionHojas;
	private Short numHojas;
	
	private SortedMap<Short, MCInformacionCasillaVO> informacionCasillas;
	
	private Map<Short, MCHojaVO> hojas;
	
	private boolean permitirBotonCrearHojas;
	
	private String liquidacionIniValor;
	private String liquidacionFinValor;
	private String liquidacionIniFormato;
	private String liquidacionFinFormato;
	private double liquidacionIniLongitud;
	private double liquidacionFinLongitud;
	
	public MCDocumentoVO() {
		super();
		informacionCasillas = new TreeMap<Short,MCInformacionCasillaVO>();		
	}
	
	public MCHojaVO aseguraHoja(short hoja){
		if (hojas==null){
			hojas= new HashMap<Short, MCHojaVO>();
		}
		if (!hojas.containsKey(hoja)){
			hojas.put(hoja, new MCHojaVO(hoja));
		}
		return hojas.get(hoja);
	}
	
	public void guardaCasillaModelo(short nuCasilla, Boolean BoDobleDigitacion, Boolean boRepeticion, String formato, BigDecimal longitud, String nombre){
		//aseguraHoja((short)1);
		
		MCInformacionCasillaVO mcInformacionCasillaVO=null;
		
		if(informacionCasillas.containsKey(nuCasilla)){
			mcInformacionCasillaVO = informacionCasillas.get(nuCasilla);
		}else{
			mcInformacionCasillaVO = new MCInformacionCasillaVO();
		}
		mcInformacionCasillaVO.setNuCasilla(nuCasilla);
		mcInformacionCasillaVO.setRepeticion(boRepeticion);
		mcInformacionCasillaVO.setFormato(formato);
		mcInformacionCasillaVO.setLongitud(longitud);
		mcInformacionCasillaVO.setNombre(nombre);
		informacionCasillas.put(nuCasilla, mcInformacionCasillaVO);
	}

	public void guardaCasillaMunicipio(short nuCasilla, short orden, Boolean mantenible){
		//aseguraHoja((short)1);
		MCInformacionCasillaVO mcInformacionCasillaVO=null;
		if(informacionCasillas.containsKey(nuCasilla)){
			mcInformacionCasillaVO = informacionCasillas.get(nuCasilla);
		}else{
			mcInformacionCasillaVO = new MCInformacionCasillaVO();
		}
		mcInformacionCasillaVO.setOrden(orden);
		mcInformacionCasillaVO.setVisible(mantenible);
		informacionCasillas.put(nuCasilla, mcInformacionCasillaVO);
	}
	
	public void guardaCasillaMunicipioOperacion(short nuCasilla, String operacion, String atributo){
		//aseguraHoja((short)1);
		MCInformacionCasillaVO mcInformacionCasillaVO=null;
		if(informacionCasillas.containsKey(nuCasilla)){
			mcInformacionCasillaVO = informacionCasillas.get(nuCasilla);
		}else{
			mcInformacionCasillaVO = new MCInformacionCasillaVO();
		}
		
		calculaAtributo(mcInformacionCasillaVO, atributo);
		
		
		informacionCasillas.put(nuCasilla, mcInformacionCasillaVO);
	}
	
	
	public void guardaCasillaValor(short hoja, short nuCasilla, String valor, boolean error){
		
		MCHojaVO miHoja=aseguraHoja(hoja);

		MCCasillaVO mcCasillaVO=null;
		if(miHoja.getCasillas().containsKey(nuCasilla)){
			mcCasillaVO = miHoja.getCasillas().get(nuCasilla);
		}else{
			mcCasillaVO = new MCCasillaVO();
		}
		mcCasillaVO.setNuCasilla(nuCasilla);
		mcCasillaVO.setValor(valor);
		mcCasillaVO.setError(error);
		miHoja.getCasillas().put(nuCasilla, mcCasillaVO);

	}
	
	public void guardaValidacionAyuda(short nuCasilla, List<ValidacionDTO> validaciones, ValidacionDTO ayuda){
		//aseguraHoja1();
		
		MCInformacionCasillaVO mcInformacionCasillaVO=null;
		if(informacionCasillas.containsKey(nuCasilla)){
			mcInformacionCasillaVO = informacionCasillas.get(nuCasilla);
		}else{
			mcInformacionCasillaVO = new MCInformacionCasillaVO();
		}
		mcInformacionCasillaVO.setAyuda(ayuda);
		mcInformacionCasillaVO.setValidaciones(validaciones);
		informacionCasillas.put(nuCasilla, mcInformacionCasillaVO);
	}
	
	public static void calculaAtributo(MCInformacionCasillaVO mcInformacionCasillaVO, String atributo){
		//TODO mirar de tablaGT
		mcInformacionCasillaVO.setAtributo(atributo);
		if (atributo!=null){
			if (atributo.equals("E")){
				mcInformacionCasillaVO.setVisible(false);
				mcInformacionCasillaVO.setContenidoObligatorio(false);
				mcInformacionCasillaVO.setConDatosValidos(true);
				mcInformacionCasillaVO.setDesprotegido(true);
				mcInformacionCasillaVO.setAtributoError("E");
			}else if(atributo.equals("M")){
				mcInformacionCasillaVO.setVisible(true);
				mcInformacionCasillaVO.setContenidoObligatorio(false);
				mcInformacionCasillaVO.setConDatosValidos(true);
				mcInformacionCasillaVO.setDesprotegido(true);
				mcInformacionCasillaVO.setAtributoError(null);
			}else if(atributo.equals("N")){
				mcInformacionCasillaVO.setVisible(false);
				mcInformacionCasillaVO.setContenidoObligatorio(false);
				mcInformacionCasillaVO.setConDatosValidos(false);
				mcInformacionCasillaVO.setDesprotegido(false);
				mcInformacionCasillaVO.setAtributoError("X");
			}else if(atributo.equals("O")){
				mcInformacionCasillaVO.setVisible(true);
				mcInformacionCasillaVO.setContenidoObligatorio(true);
				mcInformacionCasillaVO.setConDatosValidos(true);
				mcInformacionCasillaVO.setDesprotegido(true);
				mcInformacionCasillaVO.setAtributoError(null);
			}else if(atributo.equals("P")){
				mcInformacionCasillaVO.setVisible(true);
				mcInformacionCasillaVO.setContenidoObligatorio(true);
				mcInformacionCasillaVO.setConDatosValidos(true);
				mcInformacionCasillaVO.setDesprotegido(false);
				mcInformacionCasillaVO.setAtributoError("O");
			}else if(atributo.equals("Q")){
				mcInformacionCasillaVO.setVisible(true);
				mcInformacionCasillaVO.setContenidoObligatorio(false);
				mcInformacionCasillaVO.setConDatosValidos(true);
				mcInformacionCasillaVO.setDesprotegido(false);
				mcInformacionCasillaVO.setAtributoError("M");
			}else if(atributo.equals("S")){
				mcInformacionCasillaVO.setVisible(false);
				mcInformacionCasillaVO.setContenidoObligatorio(true);
				mcInformacionCasillaVO.setConDatosValidos(true);
				mcInformacionCasillaVO.setDesprotegido(false);
				mcInformacionCasillaVO.setAtributoError("O");
			}else if(atributo.equals("V")){
				mcInformacionCasillaVO.setVisible(true);
				mcInformacionCasillaVO.setContenidoObligatorio(false);
				mcInformacionCasillaVO.setConDatosValidos(false);
				mcInformacionCasillaVO.setDesprotegido(false);
				mcInformacionCasillaVO.setAtributoError("X");
			}else if(atributo.equals("X")){
				mcInformacionCasillaVO.setVisible(true);
				mcInformacionCasillaVO.setContenidoObligatorio(false);
				mcInformacionCasillaVO.setConDatosValidos(false);
				mcInformacionCasillaVO.setDesprotegido(true);
				mcInformacionCasillaVO.setAtributoError(null);
			}else if(atributo.equals("Z")){
				mcInformacionCasillaVO.setVisible(false);
				mcInformacionCasillaVO.setContenidoObligatorio(false);
				mcInformacionCasillaVO.setConDatosValidos(true);
				mcInformacionCasillaVO.setDesprotegido(false);
				mcInformacionCasillaVO.setAtributoError("M");
			}
			
		}
	}

	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}

	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
	}

	public Boolean getRepeticionHojas() {
		return repeticionHojas;
	}

	public void setRepeticionHojas(Boolean repeticionHojas) {
		this.repeticionHojas = repeticionHojas;
	}

	public Short getNumHojas() {
		return numHojas;
	}

	public void setNumHojas(Short numHojas) {
		this.numHojas = numHojas;
	}

	public SortedMap<Short, MCInformacionCasillaVO> getInformacionCasillas() {
		return informacionCasillas;
	}

	public void setInformacionCasillas(
			SortedMap<Short, MCInformacionCasillaVO> informacionCasillas) {
		this.informacionCasillas = informacionCasillas;
	}

	public Map<Short, MCHojaVO> getHojas() {
		return hojas;
	}

	public void setHojas(Map<Short, MCHojaVO> hojas) {
		this.hojas = hojas;
	}

	public void setPermitirBotonCrearHojas(boolean permitirBotonCrearHojas) {
		this.permitirBotonCrearHojas = permitirBotonCrearHojas;
	}

	public boolean isPermitirBotonCrearHojas() {
		return permitirBotonCrearHojas;
	}

	public String getLiquidacionIniValor() {
		return liquidacionIniValor;
	}

	public void setLiquidacionIniValor(String liquidacionIniValor) {
		this.liquidacionIniValor = liquidacionIniValor;
	}

	public String getLiquidacionFinValor() {
		return liquidacionFinValor;
	}

	public void setLiquidacionFinValor(String liquidacionFinValor) {
		this.liquidacionFinValor = liquidacionFinValor;
	}

	public String getLiquidacionIniFormato() {
		return liquidacionIniFormato;
	}

	public void setLiquidacionIniFormato(String liquidacionIniFormato) {
		this.liquidacionIniFormato = liquidacionIniFormato;
	}

	public String getLiquidacionFinFormato() {
		return liquidacionFinFormato;
	}

	public void setLiquidacionFinFormato(String liquidacionFinFormato) {
		this.liquidacionFinFormato = liquidacionFinFormato;
	}

	public double getLiquidacionIniLongitud() {
		return liquidacionIniLongitud;
	}

	public void setLiquidacionIniLongitud(double liquidacionIniLongitud) {
		this.liquidacionIniLongitud = liquidacionIniLongitud;
	}

	public double getLiquidacionFinLongitud() {
		return liquidacionFinLongitud;
	}

	public void setLiquidacionFinLongitud(double liquidacionFinLongitud) {
		this.liquidacionFinLongitud = liquidacionFinLongitud;
	}

	
}
