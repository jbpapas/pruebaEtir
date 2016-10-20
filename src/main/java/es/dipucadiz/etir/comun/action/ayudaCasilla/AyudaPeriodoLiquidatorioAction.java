package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.bo.LiquidatorioPeriodoBO;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.CalleSinonimoDTO;
import es.dipucadiz.etir.comun.dto.CalleTramoCategoriaDTO;
import es.dipucadiz.etir.comun.dto.CalleTramoCategoriaDTOId;
import es.dipucadiz.etir.comun.dto.LiquidatorioEjercicioDTO;
import es.dipucadiz.etir.comun.dto.LiquidatorioPeriodoDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

final public class AyudaPeriodoLiquidatorioAction extends AyudaCasillaAction {

	private static final long serialVersionUID = -785972905522959721L;

	private LiquidatorioPeriodoBO liquidatorioPeriodoBO;
	protected GenericBO<LiquidatorioEjercicioDTO, Long> liquidatorioEjercicioBO;

	protected List<LiquidatorioPeriodoDTO> listaPeriodos;
	protected List<LiquidatorioEjercicioDTO> listaEjercicios;
	protected String coLiquidatorioPeriodo;

	protected String ejercicio;

	private static final Log LOG = LogFactory.getLog(AyudaPeriodoLiquidatorioAction.class);

	public String execute() throws GadirServiceException {

		try{
			super.execute();

			String coDocumento="";
			String coMunicipio="";
			String coConcepto="";
			String ejercicio="";
			String periodo="";
			String fechaInicio="";
			String fechaFin="";
			String prioridad="";

			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos)
			{

				String valor="";
				
				if (validacionArgumentoDTO.getTipo().equals("S")){
					valor = DatosSesion.getAyudaCasillaVO().getCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()));
				}else if (validacionArgumentoDTO.getTipo().equals("K") || validacionArgumentoDTO.getTipo().equals("T")){
					valor = validacionArgumentoDTO.getValor();
				}

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion())
				{
					case 1:	coDocumento = valor; break;
					case 2:	coMunicipio = valor; break;
					case 3:	coConcepto = valor; break;
					case 4:	ejercicio = valor; break;
					case 5:	periodo = valor; break;
					case 6:	fechaInicio = valor; break;
					case 7:	fechaFin = valor; break;
					case 8:	prioridad = valor; break;
				}
			}
			
			if (Utilidades.isEmpty(coMunicipio) || coMunicipio.length()!=5){
				addActionError(Mensaje.getTexto(MensajeConstants.CODIGO_DE_MUNICIPIO_INEXISTENTE));
			}
			if (Utilidades.isEmpty(coConcepto)){
				addActionError(Mensaje.getTexto(MensajeConstants.CODIGO_DE_CONCEPTO_INEXISTENTE));
			}
			if (Utilidades.isEmpty(DatosSesion.getAyudaCasillaVO().getCoModelo())){
				addActionError(Mensaje.getTexto(MensajeConstants.CODIGO_DE_MODELO_INEXISTENTE));
			}
			if (Utilidades.isEmpty(ejercicio) || !Utilidades.isNumeric(ejercicio)){
				addActionError(Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "Ejercicio"));
			}
			
			if (!hasErrors()){
				this.ejercicio=ejercicio;
					
				listaEjercicios = liquidatorioEjercicioBO.findByCriteria(getCriteria(coMunicipio.substring(0,2), coMunicipio.substring(2,5), coConcepto, DatosSesion.getAyudaCasillaVO().getCoModelo(), Short.parseShort(ejercicio)));
				
				if(listaEjercicios == null || listaEjercicios.isEmpty())
					listaEjercicios = liquidatorioEjercicioBO.findByCriteria(getCriteria("**", "***", coConcepto, DatosSesion.getAyudaCasillaVO().getCoModelo(), Short.parseShort(ejercicio)));
				
				if(listaEjercicios == null || listaEjercicios.isEmpty())
					listaEjercicios = liquidatorioEjercicioBO.findByCriteria(getCriteria("**", "***", "****", DatosSesion.getAyudaCasillaVO().getCoModelo(), Short.parseShort(ejercicio)));
				
				if(listaEjercicios == null || listaEjercicios.isEmpty())
					listaEjercicios = liquidatorioEjercicioBO.findByCriteria(getCriteria("**", "***", "****", "***", Short.parseShort(ejercicio)));
				
				Long coLiqEjercicio = listaEjercicios.get(0).getCoLiquidatorioEjercicio();
				
				listaPeriodos = liquidatorioPeriodoBO.findFiltered("liquidatorioEjercicioDTO.coLiquidatorioEjercicio", coLiqEjercicio);
			}

		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de periodo liquidatorio", e);
		}

		return INPUT;
	}

	public String ajaxBotonSeleccionar() {
		ajaxData="";
		
		try{

			LiquidatorioPeriodoDTO liquidatorioPeriodoDTO = liquidatorioPeriodoBO.findById(Long.valueOf(coLiquidatorioPeriodo));

			String periodo = liquidatorioPeriodoDTO.getPeriodoLiquidatorio();
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 5:	
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), periodo);
					break;
//				case 6:	
//					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), fechaInicio); 
//					break;
//				case 7:	
//					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), fechaFin); 
//					break;
				}
			}
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de periodo liquidatorio", e);
		}
		
		ajaxData=parseaResultado();
		return AJAX_DATA;
	}

	public String fechaInicio(String coPeriodo)
	{
		String fechaInicio = "";
		
		try
		{
			LiquidatorioPeriodoDTO liquidatorioPeriodoDTO = liquidatorioPeriodoBO.findById(Long.valueOf(coPeriodo));
				
			String fechaInicioTemp = ""+liquidatorioPeriodoDTO.getPeriodoIni();
			
			if(fechaInicioTemp.length()==1){ //Solo es el mes (1 digito)
				fechaInicio = "00"+"/"+"0"+fechaInicioTemp + "/" + ejercicio;
			}
			else if(fechaInicioTemp.length()==2){ //Solo es el mes (2 digitos)
				fechaInicio = "00"+"/"+fechaInicioTemp + "/" + ejercicio;
			}
			else if (fechaInicioTemp.length()==3){ //Dia-mes (xyy)
				fechaInicio = fechaInicioTemp.substring(1,3) + "/" +"0"+fechaInicioTemp.substring(0,1) + "/" + ejercicio;
			}else{ //Dia-mes (xxyy)
				fechaInicio = fechaInicioTemp.substring(2,4) + "/" + fechaInicioTemp.substring(0,2) + "/" + ejercicio;
			}
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de periodo liquidatorio", e);
		}
		
		return fechaInicio;
	}
	
	public String fechaFin(String coPeriodo)
	{
		String fechaFin = "";
		
		try
		{
			LiquidatorioPeriodoDTO liquidatorioPeriodoDTO = liquidatorioPeriodoBO.findById(Long.valueOf(coPeriodo));
							
			String fechaFinTemp = ""+liquidatorioPeriodoDTO.getPeriodoFin();
			boolean bisiesto = true;
			int anio = Integer.parseInt(ejercicio);
			
			if(fechaFinTemp.length()==1){ //Solo es el mes (1 digito)
				fechaFin = "00"+"/"+"0"+fechaFinTemp + "/" + ejercicio;
			}
			else if(fechaFinTemp.length()==2){ //Solo es el mes (2 digitos)
				fechaFin = "00"+"/"+fechaFinTemp + "/" + ejercicio;
			}
			else if (fechaFinTemp.length()==3){
				fechaFin = fechaFinTemp.substring(1,3) + "/" +"0"+fechaFinTemp.substring(0,1) + "/" +  ejercicio;
			}else{
				fechaFin = fechaFinTemp.substring(2,4) + "/" + fechaFinTemp.substring(0,2) + "/" + ejercicio;
			}
			
			//Si es el 29 de Febrero
			if(fechaFin.substring(0,2).equals("29") && fechaFin.substring(3,5).equals("02"))
			{
				GregorianCalendar calendar = new GregorianCalendar();

				if (!calendar.isLeapYear(anio))
					bisiesto = false;
				
				if(!bisiesto)
					fechaFin="28/02/"+ejercicio;
			}
			
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de periodo liquidatorio", e);
		}
		
		return fechaFin;
	}
	
	private DetachedCriteria getCriteria(String coProvincia, String coMunicipio, String coConcepto,String  coModelo, Short ejercicio)
	{
		final DetachedCriteria criteria = DetachedCriteria.forClass(LiquidatorioEjercicioDTO.class);
				
		criteria.add(Restrictions.eq("municipioDTO.id.coProvincia",coProvincia));
		criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio",coMunicipio));
		criteria.add(Restrictions.eq("conceptoDTO.coConcepto",coConcepto));
		criteria.add(Restrictions.eq("modeloDTO.coModelo",coModelo));
		criteria.add(Restrictions.le("ejercicioDesde",ejercicio));
		criteria.add(Restrictions.ge("ejercicioHasta",ejercicio));
		
		return criteria;
	}

	public List<LiquidatorioPeriodoDTO> getListaPeriodos() {
		return listaPeriodos;
	}

	public void setListaPeriodos(List<LiquidatorioPeriodoDTO> listaPeriodos) {
		this.listaPeriodos = listaPeriodos;
	}

	public String getCoLiquidatorioPeriodo() {
		return coLiquidatorioPeriodo;
	}

	public void setCoLiquidatorioPeriodo(String coLiquidatorioPeriodo) {
		this.coLiquidatorioPeriodo = coLiquidatorioPeriodo;
	}

	public LiquidatorioPeriodoBO getLiquidatorioPeriodoBO() {
		return liquidatorioPeriodoBO;
	}

	public void setLiquidatorioPeriodoBO(
			LiquidatorioPeriodoBO liquidatorioPeriodoBO) {
		this.liquidatorioPeriodoBO = liquidatorioPeriodoBO;
	}

	public String getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}

	public GenericBO<LiquidatorioEjercicioDTO, Long> getLiquidatorioEjercicioBO() {
		return liquidatorioEjercicioBO;
	}

	public void setLiquidatorioEjercicioBO(
			GenericBO<LiquidatorioEjercicioDTO, Long> liquidatorioEjercicioBO) {
		this.liquidatorioEjercicioBO = liquidatorioEjercicioBO;
	}

	public List<LiquidatorioEjercicioDTO> getListaEjercicios() {
		return listaEjercicios;
	}

	public void setListaEjercicios(List<LiquidatorioEjercicioDTO> listaEjercicios) {
		this.listaEjercicios = listaEjercicios;
	}
}
