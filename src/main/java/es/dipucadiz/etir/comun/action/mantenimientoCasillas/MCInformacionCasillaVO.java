package es.dipucadiz.etir.comun.action.mantenimientoCasillas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import es.dipucadiz.etir.comun.dto.ValidacionDTO;
import es.dipucadiz.etir.comun.utilidades.Utilidades;


public class MCInformacionCasillaVO implements Serializable {
	private static final long serialVersionUID = -7124016319958837041L;
//	private static final Log LOG = LogFactory.getLog(MCInformacionCasillaVO.class);
	
	private short nuCasilla;
	private short orden;
	private String nombre;
	private String formato;
	private BigDecimal longitud;
	private boolean repeticion;
	private Boolean visible;
	private Boolean desprotegido;
	private Boolean contenidoObligatorio;
	private Boolean conDatosValidos;
	//protected String apariencia; que se calcule en directo
	private String atributo;
	private String atributoError;
//	private ValidacionDTO validacion=null; //la guardo con el funcionDTO initialized
	private List<ValidacionDTO> validaciones=null;
	private ValidacionDTO ayuda=null; //la guardo con el funcionDTO initialized
	//protected String java;
	//protected String plsql1;
	//protected String plsql2;
	
	
	private int APARIENCIA_NO_VISIBLE=0;
	private int APARIENCIA_VISIBLE=1;
	private int APARIENCIA_EDITABLE=2;
	
	public int getSizeCampoTexto(){
		int size=50;
		
		try{
			
			if (formato.equals("A") || formato.equals("E")){
				return longitud.intValue();
			}
			if (formato.equals("D")){
				return 10;
			}			
			if (formato.equals("N")){ //TODO: que paranoya
				size=longitud.intValue();
				BigDecimal bd=longitud.subtract(new BigDecimal(size));
				int i=(bd.movePointRight(1)).intValue();
				if (i!=0){
					size+= i;
					size += 1; //la coma
				}				
			}			
		}catch(Exception e){
			
		}
		
		return size;
	}
	
	public int getSizeTotalCampoTexto(){
		int size = getSizeCampoTexto();
		try{
			
			if (formato.equals("A") && size >= 50){
				size += 40;
			}
				
		}catch(Exception e){
			
		}
		
		return size;
	}
	
	public int getApariencia(MCDocumentoVO mcDocumentoVO, short hoja, boolean modoEdicion, boolean todoElDocumento){
		
		// Excluir casillas no incluidas en CASCON.
		if (atributo == null) {
			return APARIENCIA_NO_VISIBLE;
		}
		
		int apariencia=APARIENCIA_NO_VISIBLE;

		try{
			if (getDesprotegido()){
				apariencia=APARIENCIA_EDITABLE;
			}else if(getVisible()){
				apariencia=APARIENCIA_VISIBLE;
			}
		}catch(Exception e){

		}

		if (hoja==1){
			if (repeticion){
				apariencia=APARIENCIA_NO_VISIBLE;
			}
		}else if (hoja>1){
			if (!repeticion){
				apariencia=APARIENCIA_NO_VISIBLE;
			}
		}
		
		if (!modoEdicion){
			try {
				if(apariencia!=APARIENCIA_NO_VISIBLE && (!Utilidades.isEmpty(((mcDocumentoVO.getHojas().get(hoja)).getCasillas().get(nuCasilla)).getValor()) || (getContenidoObligatorio()!=null && getContenidoObligatorio()) )){
					apariencia = APARIENCIA_VISIBLE;
				}else{
					apariencia = APARIENCIA_NO_VISIBLE;
				}
			} catch (Exception e) {
				apariencia = APARIENCIA_NO_VISIBLE;
//				LOG.error("Casilla " + nuCasilla + ", hoja " + hoja, e);
			}
		}
		
		if (todoElDocumento){
			if (hoja==1){
				if (!repeticion){
					apariencia=APARIENCIA_VISIBLE;
				}
			}else if (hoja>1){
				if (repeticion){
					apariencia=APARIENCIA_VISIBLE;
				}
			}
		}
		return apariencia;
	}
	
	
	public MCInformacionCasillaVO(){
		super();
	}
	
	public MCInformacionCasillaVO(short nuCasilla, short orden, String nombre,
			String formato, BigDecimal longitud, boolean repeticion, Boolean visible,
			Boolean desprotegido, Boolean contenidoObligatorio,
			Boolean conDatosValidos, String atributoError,
			List<ValidacionDTO> validaciones, ValidacionDTO ayuda) {
		super();
		this.nuCasilla = nuCasilla;
		this.orden = orden;
		this.nombre = nombre;
		this.formato = formato;
		this.longitud = longitud;
		this.repeticion = repeticion;
		this.visible = visible;
		this.desprotegido = desprotegido;
		this.contenidoObligatorio = contenidoObligatorio;
		this.conDatosValidos = conDatosValidos;
		this.atributoError = atributoError;
		this.validaciones = validaciones;
		this.ayuda = ayuda;
	}
	
	
	public short getOrden() {
		return orden;
	}
	public void setOrden(short orden) {
		this.orden = orden;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public BigDecimal getLongitud() {
		return longitud;
	}
	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}
	public boolean getRepeticion() {
		return repeticion;
	}
	public void setRepeticion(boolean repeticion) {
		this.repeticion = repeticion;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public Boolean getDesprotegido() {
		return desprotegido;
	}
	public void setDesprotegido(Boolean desprotegido) {
		this.desprotegido = desprotegido;
	}
	public Boolean getContenidoObligatorio() {
		return contenidoObligatorio;
	}
	public void setContenidoObligatorio(Boolean contenidoObligatorio) {
		this.contenidoObligatorio = contenidoObligatorio;
	}
	public Boolean getConDatosValidos() {
		return conDatosValidos;
	}
	public void setConDatosValidos(Boolean conDatosValidos) {
		this.conDatosValidos = conDatosValidos;
	}
	public String getAtributoError() {
		return atributoError;
	}
	public void setAtributoError(String atributoError) {
		this.atributoError = atributoError;
	}
	public ValidacionDTO getAyuda() {
		return ayuda;
	}
	public void setAyuda(ValidacionDTO ayuda) {
		this.ayuda = ayuda;
	}

	public short getNuCasilla() {
		return nuCasilla;
	}

	public void setNuCasilla(short nuCasilla) {
		this.nuCasilla = nuCasilla;
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public List<ValidacionDTO> getValidaciones() {
		return validaciones;
	}

	public void setValidaciones(List<ValidacionDTO> validaciones) {
		this.validaciones = validaciones;
	}
	
	
}
