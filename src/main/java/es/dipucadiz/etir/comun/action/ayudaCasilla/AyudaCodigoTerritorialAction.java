package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.bo.CodigoTerritorialBO;
import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.dto.CodigoTerritorialDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.FuncionPlsqlUtil;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.ParametrosDatoCorporativoVO;

final public class AyudaCodigoTerritorialAction extends AyudaCasillaAction {

	protected CodigoTerritorialBO codigoTerritorialBO;
	protected DocumentoBO documentoBO;
	
	protected List<CodigoTerritorialDTO> listaCodigoTerritorials;
	protected String coCodigoTerritorial;


	private static final Log LOG = LogFactory.getLog(AyudaCodigoTerritorialAction.class);

	public String execute() throws GadirServiceException {

		try{
			super.execute();

			String coMunicipio="";

			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				String valor="";
				if (validacionArgumentoDTO.getTipo().equals("S")){
					valor = DatosSesion.getAyudaCasillaVO().getCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()));
				}else if (validacionArgumentoDTO.getTipo().equals("K") || validacionArgumentoDTO.getTipo().equals("T")){
					valor = validacionArgumentoDTO.getValor();
				}

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 1:	coMunicipio = valor; break;
				}
			}

			if (Utilidades.isEmpty(coMunicipio) || coMunicipio.length()!=5){
				addActionError(Mensaje.getTexto(MensajeConstants.CODIGO_DE_MUNICIPIO_INEXISTENTE));
			}

			if (!hasErrors()){
				//listaCodigoTerritorials = codigoTerritorialBO.findByMunicipio(coMunicipio.substring(0, 2), coMunicipio.substring(2, 5));

				DocumentoDTO documentoDTO = documentoBO.findById(new DocumentoDTOId(
						DatosSesion.getAyudaCasillaVO().getCoModelo(), 
						DatosSesion.getAyudaCasillaVO().getCoVersion(),
						DatosSesion.getAyudaCasillaVO().getCoDocumento()));
				
//				String[] parametros = new String[21];
//				parametros[0] = DatosSesion.getAyudaCasillaVO().getCoModelo();
//				parametros[1] = DatosSesion.getAyudaCasillaVO().getCoVersion();
//				parametros[2] = DatosSesion.getAyudaCasillaVO().getCoDocumento();
//				parametros[3] = documentoDTO.getMunicipioDTO().getId().getCoProvincia(); //provincia
//				parametros[4] = documentoDTO.getMunicipioDTO().getId().getCoMunicipio();  //municipio
//				parametros[6] = String.valueOf(Utilidades.getAnoActual()); //ejercicio	
//				parametros[7] = documentoDTO.getConceptoDTO().getCoConcepto(); //concepto
				
				ParametrosDatoCorporativoVO paramsVO = new ParametrosDatoCorporativoVO();
				
				paramsVO.setCoModelo(DatosSesion.getAyudaCasillaVO().getCoModelo());
				paramsVO.setCoVersion(DatosSesion.getAyudaCasillaVO().getCoVersion());
				paramsVO.setCoDocumento(DatosSesion.getAyudaCasillaVO().getCoDocumento());
				
				paramsVO.setCoProvincia(documentoDTO.getMunicipioDTO().getId().getCoProvincia());
				paramsVO.setCoMunicipio(documentoDTO.getMunicipioDTO().getId().getCoMunicipio());
//				paramsVO.setEjercicio(String.valueOf(Utilidades.getAnoActual()));
				paramsVO.setEjercicio(String.valueOf(documentoDTO.getEjercicio()));
				paramsVO.setCoConcepto(documentoDTO.getConceptoDTO().getCoConcepto());

				listaCodigoTerritorials = new ArrayList<CodigoTerritorialDTO>();
				
				//String codTer = FuncionPlsqlUtil.datoCorporativo("convenio", 2, parametros);
				
				String codTer = FuncionPlsqlUtil.datoCorporativo("convenio", 2, paramsVO);
				
				if(Utilidades.isEmpty(codTer))
					addActionError("No se ha podido obtener la ayuda de código territorial.");
				else
					listaCodigoTerritorials.add(codigoTerritorialBO.findById(codTer));
			}

		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de codigo territorial", e);
		}

		return INPUT;
	}

	public String ajaxBotonSeleccionar(){
		
		ajaxData="";
		
		try{

			CodigoTerritorialDTO codigoTerritorialDTO = codigoTerritorialBO.findById(coCodigoTerritorial);
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
				case 2:	
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), codigoTerritorialDTO.getCoCodigoTerritorial());
					break;
				case 3:	
					DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), codigoTerritorialDTO.getNombre());
					break;
				}
			}
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de codigo territorial", e);
		}
		
		ajaxData=parseaResultado();
		return AJAX_DATA;
	}


	public CodigoTerritorialBO getCodigoTerritorialBO() {
		return codigoTerritorialBO;
	}

	public void setCodigoTerritorialBO(CodigoTerritorialBO codigoTerritorialBO) {
		this.codigoTerritorialBO = codigoTerritorialBO;
	}

	public List<CodigoTerritorialDTO> getListaCodigoTerritorials() {
		return listaCodigoTerritorials;
	}

	public void setListaCodigoTerritorials(
			List<CodigoTerritorialDTO> listaCodigoTerritorials) {
		this.listaCodigoTerritorials = listaCodigoTerritorials;
	}

	public String getCoCodigoTerritorial() {
		return coCodigoTerritorial;
	}

	public void setCoCodigoTerritorial(String coCodigoTerritorial) {
		this.coCodigoTerritorial = coCodigoTerritorial;
	}

	public DocumentoBO getDocumentoBO() {
		return documentoBO;
	}

	public void setDocumentoBO(DocumentoBO documentoBO) {
		this.documentoBO = documentoBO;
	}

	
}
