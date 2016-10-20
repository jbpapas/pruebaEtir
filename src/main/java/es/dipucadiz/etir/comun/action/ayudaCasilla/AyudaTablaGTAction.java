package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.dto.TablaGtColumnaDTO;
import es.dipucadiz.etir.comun.dto.TablaGtElementoDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTOId;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb04.bo.MantenimientoTablaGtBO;

final public class AyudaTablaGTAction extends AyudaCasillaAction {

	private static final long serialVersionUID = -9114081744262030234L;
	
	protected List<TablaGtColumnaDTO> listaTablaGtColumnas;
	protected List<List<TablaGtElementoDTO>> listaTablaGtElementos;
	protected MantenimientoTablaGtBO mantenimientoTablaGtBO;
	
	protected String coElemento;
	protected int fila;
	protected String nomfila;
	
	private static final Log LOG = LogFactory.getLog(AyudaTablaGTAction.class);
	
	public String execute() throws GadirServiceException {

		try{
			super.execute();

			String tablaGT="";

			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				String valor="";
				if (validacionArgumentoDTO.getTipo().equals("S")){
					valor = DatosSesion.getAyudaCasillaVO().getCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()));
				}else if (validacionArgumentoDTO.getTipo().equals("K") || validacionArgumentoDTO.getTipo().equals("T")){
					valor = validacionArgumentoDTO.getValor();
				}

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 1:	tablaGT = valor; break;
				}
			}
			
			if (Utilidades.isEmpty(tablaGT) || !TablaGt.isTabla(tablaGT)){
				addActionError(Mensaje.getTexto(MensajeConstants.V1_INEXISTENTE, "Tabla"));
			}
			
			if (!hasErrors()){
				setListaTablaGtColumnas(mantenimientoTablaGtBO.findColumnas(tablaGT));
				setListaTablaGtElementos(mantenimientoTablaGtBO.findElementos(listaTablaGtColumnas));
			}			

		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de tabla GT", e);
		}

		return INPUT;
	}

	public String ajaxBotonSeleccionar() {
		ajaxData="";
		
		try{
			String tablaGT = "";			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){
				if(validacionArgumentoDTO.getId().getCoArgumentoFuncion() == 1) {
					tablaGT = validacionArgumentoDTO.getValor();
					setListaTablaGtColumnas(mantenimientoTablaGtBO.findColumnas(tablaGT));
					setListaTablaGtElementos(mantenimientoTablaGtBO.findElementos(listaTablaGtColumnas));
				}
				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
					case 2: DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), nomfila);
							break;
					case 3: DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), TablaGt.getValor(tablaGT, nomfila, TablaGt.COLUMNA_DESCRIPCION));
							break;
					case 4: String[] cadenaValores = validacionArgumentoDTO.getValor().split(",");
							for(int i = 1; i <= cadenaValores.length;i++) {
								String dato = cadenaValores[i-1].trim();
								if(Utilidades.isNumeric(dato) && Integer.parseInt(dato) > 0) {
									TablaGtColumnaDTO columna = listaTablaGtColumnas.get(i+1);
									TablaGtElementoDTO elemento = listaTablaGtElementos.get(i+1).get(fila-1);
									ValidacionArgumentoDTO vaCodigoDTO = validacionArgumentoBO.findById(new ValidacionArgumentoDTOId(validacionArgumentoDTO.getId().getCoValidacion(), validacionArgumentoDTO.getId().getCoFuncion(), Integer.parseInt(dato)*2+3));
									if(vaCodigoDTO != null) 
										DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(vaCodigoDTO.getValor()), elemento.getValor());
									ValidacionArgumentoDTO vaDescripcionDTO = validacionArgumentoBO.findById(new ValidacionArgumentoDTOId(validacionArgumentoDTO.getId().getCoValidacion(), validacionArgumentoDTO.getId().getCoFuncion(), Integer.parseInt(dato)*2+4));
									if(vaDescripcionDTO != null) {
										if (columna.getTablaGtDTOByCoTablaValidacion() != null && Utilidades.isNotEmpty(columna.getTablaGtDTOByCoTablaValidacion().getCoTablaGt())) {
											String infoAsociadaDescripcion = TablaGt.getValor(columna.getTablaGtDTOByCoTablaValidacion().getCoTablaGt(), elemento.getValor(), TablaGt.COLUMNA_DESCRIPCION);
											DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(vaDescripcionDTO.getValor()), infoAsociadaDescripcion);
										}
									}
								}
							}
							break;							
				}
			}
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de tabla GT", e);
		}
		
		ajaxData=parseaResultado();
		return AJAX_DATA;
	}

	public List<TablaGtColumnaDTO> getListaTablaGtColumnas() {
		return listaTablaGtColumnas;
	}

	public void setListaTablaGtColumnas(List<TablaGtColumnaDTO> listaTablaGtColumnas) {
		this.listaTablaGtColumnas = listaTablaGtColumnas;
	}

	public List<List<TablaGtElementoDTO>> getListaTablaGtElementos() {
		return listaTablaGtElementos;
	}

	public void setListaTablaGtElementos(
			List<List<TablaGtElementoDTO>> listaTablaGtElementos) {
		this.listaTablaGtElementos = listaTablaGtElementos;
	}

	public MantenimientoTablaGtBO getMantenimientoTablaGtBO() {
		return mantenimientoTablaGtBO;
	}

	public void setMantenimientoTablaGtBO(
			MantenimientoTablaGtBO mantenimientoTablaGtBO) {
		this.mantenimientoTablaGtBO = mantenimientoTablaGtBO;
	}

	public String getCoElemento() {
		return coElemento;
	}

	public void setCoElemento(String coElemento) {
		this.coElemento = coElemento;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public String getNomfila() {
		return nomfila;
	}

	public void setNomfila(String nomfila) {
		this.nomfila = nomfila;
	}

	
}
