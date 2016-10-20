package es.dipucadiz.etir.comun.bo.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.action.mantenimientoCasillas.MCCasillaVO;
import es.dipucadiz.etir.comun.action.mantenimientoCasillas.MCDocumentoVO;
import es.dipucadiz.etir.comun.action.mantenimientoCasillas.MCHojaVO;
import es.dipucadiz.etir.comun.action.mantenimientoCasillas.MCInformacionCasillaVO;
import es.dipucadiz.etir.comun.action.mantenimientoCasillas.MCOrdenCasillaComparator;
import es.dipucadiz.etir.comun.bo.MantenimientoCasillasBO;
import es.dipucadiz.etir.comun.boStoredProcedure.BorradoManualDocumentoBO;
import es.dipucadiz.etir.comun.boStoredProcedure.GuardarCasillasMasivoBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.constants.CasillaConstants;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTO;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTOId;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioDTO;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioOperacionDTO;
import es.dipucadiz.etir.comun.dto.CasillaMunicipioOperacionDTOId;
import es.dipucadiz.etir.comun.dto.CasillasLigadasDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.FuncionDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTOId;
import es.dipucadiz.etir.comun.dto.ValidacionDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.ControlTerritorial;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.FuncionPlsqlUtil;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.SigreUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb05.action.G5114CasillasMunicipio.G5114AbstractAction;



public class MantenimientoCasillasBOImpl implements MantenimientoCasillasBO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5507065547254842751L;
//	private static final Log LOG = LogFactory.getLog(MantenimientoCasillasBOImpl.class);
	
	private DAOBase<DocumentoDTO, DocumentoDTOId> documentoDAO;
	private DAOBase<DocumentoCasillaValorDTO, DocumentoCasillaValorDTOId> documentoCasillaValorDAO;
	private DAOBase<CasillaMunicipioDTO, Long> casillaMunicipioDAO;
	private DAOBase<CasillaModeloDTO, CasillaModeloDTOId> casillaModeloDAO;
	private DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> casillaMunicipioOperacionDAO;
	private DAOBase<ValidacionDTO, String> validacionDAO;
	private DAOBase<CasillasLigadasDTO, Long> casillasLigadasDAO;
	private DAOBase<FuncionDTO, String> funcionDAO;
	private DAOBase<ValidacionArgumentoDTO, ValidacionArgumentoDTOId> validacionArgumentoDAO;
	private DAOBase<ModeloDTO, String> modeloDAO;
	
	public final String OPERACION_GENERICO ="GENERICO";
	public final String OPERACION_MODIFICA ="MODIFICA";
	public final String OPERACION_CONSULTA ="CONSULTA";
	public final String OPERACION_ALTA ="ALTA";
	public final String OPERACION_CONTROL ="CONTROL";
	
	public MCDocumentoVO getDocumento(String coModelo, String coVersion, String coDocumento, String operacion) throws GadirServiceException{
		MCDocumentoVO mcDocumentoVO = new MCDocumentoVO();
		
		DocumentoDTO documentoDTO = documentoDAO.findById(new DocumentoDTOId(coModelo, coVersion, coDocumento));
		if (documentoDTO == null) {
			throw new GadirServiceException ("No se encuentra el documento");
		}
		if (ControlTerritorial.isUsuarioOT()) {
			Hibernate.initialize(documentoDTO.getDocumentoLiquidacionDTO());
			if (!DatosSesion.getUnidadAdministrativa().getCoUnidadAdministrativa().endsWith(documentoDTO.getDocumentoLiquidacionDTO().getOtrDTO().getCoOtr())) {
				throw new GadirServiceException ("No tiene acceso de " + operacion + " sobre el documento con OTR " + documentoDTO.getDocumentoLiquidacionDTO().getOtrDTO().getCoOtr() + ".");
			}
		} else if (documentoDTO.getCodigoTerritorialDTO() != null && !ControlTerritorial.isCodterCompatibleUsuario(
				documentoDTO.getCodigoTerritorialDTO().getCoCodigoTerritorial(), 
				OPERACION_CONSULTA.equals(operacion) ? ControlTerritorial.CONSULTA : ControlTerritorial.EDICION)) {
			//throw new GadirServiceException ("El código territorial del usuario (" + DatosSesion.getCodigoTerritorialGenerico() + ") no es compatible con el del documento (" + documentoDTO.getCodigoTerritorialDTO().getCoCodigoTerritorial() + ") para la operación " + operacion + ".");
			throw new GadirServiceException ("No tiene acceso de " + operacion + " sobre el documento.");
		}
		
		/* GA_DOCUMENTO_CASILLA_VALOR (obtención de valores) */
		//TODO: "Sin comillas"
//		List<DocumentoCasillaValorDTO> listaDocumentoCasillaValorDTO = documentoCasillaValorDAO.findFiltered(
//				new String[]{"id.coModelo", "id.coVersion", "id.coDocumento"},
//				new Object[]{coModelo, coVersion, coDocumento},
//				new String[]{"id.hoja", "id.nuCasilla"},
//				new int[]{DAOConstant.ASC_ORDER, DAOConstant.ASC_ORDER}
//				);
		//TODO: "Con comillas"
		DetachedCriteria criteriaCasillas = DetachedCriteria.forClass(DocumentoCasillaValorDTO.class);
		criteriaCasillas.add(Restrictions.sqlRestriction("co_modelo='" + coModelo + "' AND co_version='" + coVersion + "' AND co_documento='" + coDocumento + "'"));
		criteriaCasillas.addOrder(Order.asc("id.hoja"));
		criteriaCasillas.addOrder(Order.asc("id.nuCasilla"));
		List<DocumentoCasillaValorDTO> listaDocumentoCasillaValorDTO = documentoCasillaValorDAO.findByCriteria(criteriaCasillas);
		/* FIN: GA_DOCUMENTO_CASILLA_VALOR (obtención de valores) */

		ModeloDTO modeloDTO=modeloDAO.findById(documentoDTO.getModeloVersionDTO().getId().getCoModelo());
		boolean conAyudasDeModeloGenerico=(modeloDTO.getBoAyudaCasilla()!=null && modeloDTO.getBoAyudaCasilla());
		//Hibernate.initialize(documentoDTO.getConceptoDTO());
		
		mcDocumentoVO.setDocumentoDTO(documentoDTO);
		
		String coMunicipio= documentoDTO.getMunicipioDTO().getId().getCoMunicipio();
		String coProvincia= documentoDTO.getMunicipioDTO().getId().getCoProvincia();
		String coConcepto = "";
		if(Utilidades.isNotNull(documentoDTO.getConceptoDTO()))
			coConcepto = documentoDTO.getConceptoDTO().getCoConcepto();
		
		/*  GA_CASILLA_MODELO  */
		List<CasillaModeloDTO> listaCasillaModeloDTO = casillaModeloDAO.findFiltered(
				new String[]{"id.coModelo", "id.coVersion"},
				new Object[]{coModelo, coVersion});
		if (listaCasillaModeloDTO==null || listaCasillaModeloDTO.isEmpty())throw new GadirServiceException ("No se encuentran casillas para el modelo");
		
		boolean repeticionHojas=false;
		
		/* GA_VALIDACIONES: ¿EXISTEN PARTICULARES? */
		String coProvinciaValidacion = null;
		String coMunicipioValidacion = null;
		if (!OPERACION_CONSULTA.equals(operacion)) {
			if (!existeValidacionParticular(coProvincia, coMunicipio, coConcepto, coModelo, coVersion)) {
				coProvinciaValidacion = "**";
				coMunicipioValidacion = "***";
			} else {
				coProvinciaValidacion = coProvincia;
				coMunicipioValidacion = coMunicipio;
			}
		}
		
		for (CasillaModeloDTO casillaModeloDTO : listaCasillaModeloDTO ){
			// Si cumple con condiciones varias, recuperar el nombre de la casilla de de SigreSubconcepto.
			String nombreCasilla = null;
			String coTasa = obtenerValorCasilla(listaDocumentoCasillaValorDTO, CasillaConstants.NU_CASILLA_CO_TASA_SIGRE);
			if (Utilidades.isNotEmpty(coTasa)) {
				if (casillaModeloDTO.getId().getCoModelo().startsWith("2") || casillaModeloDTO.getId().getCoModelo().startsWith("4") || casillaModeloDTO.getId().getNuCasilla() == 29) {
					if ((casillaModeloDTO.getId().getNuCasilla() > 45 && casillaModeloDTO.getId().getNuCasilla() < 68) ||
							casillaModeloDTO.getId().getNuCasilla() > 100 ||
							casillaModeloDTO.getId().getNuCasilla() == 29) {
						nombreCasilla = SigreUtil.getDescripcionSubconcepto(documentoDTO.getMunicipioDTO().getId(), casillaModeloDTO.getId(), coTasa);
					}
				}
			}
			if (Utilidades.isEmpty(nombreCasilla)) {
				nombreCasilla = casillaModeloDTO.getNombre();
			}
			
			mcDocumentoVO.guardaCasillaModelo(
					casillaModeloDTO.getId().getNuCasilla(),
					casillaModeloDTO.getBoDobleDigitacion(),
					casillaModeloDTO.getBoRepeticion(),
					casillaModeloDTO.getFormato(),
					casillaModeloDTO.getLongitud(),
					nombreCasilla
			);
			if(casillaModeloDTO.getBoRepeticion()){
				repeticionHojas=true;
			}	
			
			if (!OPERACION_CONSULTA.equals(operacion)) {
				/*  GA_VALIDACIONES  */
				List<ValidacionDTO> validaciones = getValidaciones(coProvinciaValidacion, coMunicipioValidacion, coConcepto, coModelo, coVersion, casillaModeloDTO.getId().getNuCasilla());
				if (validaciones!=null){
					for (ValidacionDTO validacion : validaciones) {
						Hibernate.initialize(validacion.getFuncionDTO());
					}
				}
				
				ValidacionDTO ayuda = getAyuda(coProvincia, coMunicipio, coConcepto, coModelo, coVersion, casillaModeloDTO.getId().getNuCasilla(), conAyudasDeModeloGenerico);
				if (ayuda!=null)Hibernate.initialize(ayuda.getFuncionDTO());
				
				mcDocumentoVO.guardaValidacionAyuda(casillaModeloDTO.getId().getNuCasilla(), validaciones, ayuda);
				/*  FIN: GA_VALIDACIONES  */
			}
			
		}
		mcDocumentoVO.setRepeticionHojas(repeticionHojas);
		/*  FIN: GA_CASILLA_MODELO  */
		
		
		/*  GA_CASILLA_MUNICIPIO Y GA_CASILLA_MUNICIPIO_OPERACION  */
		
		List<CasillaMunicipioDTO> listaCasillaMunicipioDTO = casillaMunicipioDAO.findFiltered(
				new String[]{"municipioDTO.id.coProvincia", "municipioDTO.id.coMunicipio", "conceptoDTO.coConcepto", "casillaModeloDTO.id.coModelo", "casillaModeloDTO.id.coVersion"},
				new Object[]{coProvincia, coMunicipio, !Utilidades.isEmpty(coConcepto)?coConcepto:"****", coModelo, coVersion});
		
		if (listaCasillaMunicipioDTO==null || listaCasillaMunicipioDTO.isEmpty()){
			coMunicipio="***";
			coProvincia="**";
			listaCasillaMunicipioDTO = casillaMunicipioDAO.findFiltered(
					new String[]{"municipioDTO.id.coProvincia", "municipioDTO.id.coMunicipio", "conceptoDTO.coConcepto", "casillaModeloDTO.id.coModelo", "casillaModeloDTO.id.coVersion"},
					new Object[]{coProvincia, coMunicipio, !Utilidades.isEmpty(coConcepto)?coConcepto:"****", coModelo, coVersion});
		}
		
		// Si operación GRABACAS, filtrar sólo los que tengan esta operación.
		if (G5114AbstractAction.OPERACION_GRABAR_CASILLAS.equals(operacion)) {
			for (int i=listaCasillaMunicipioDTO.size()-1; i>=0; i--) {
				CasillaMunicipioOperacionDTOId id = new CasillaMunicipioOperacionDTOId(listaCasillaMunicipioDTO.get(i).getCoCasillaMunicipio(), G5114AbstractAction.OPERACION_GRABAR_CASILLAS);
				if (casillaMunicipioOperacionDAO.findById(id) == null) {
					listaCasillaMunicipioDTO.remove(i);
				}
			}
		}
		
		if (listaCasillaMunicipioDTO==null || listaCasillaMunicipioDTO.isEmpty())throw new GadirServiceException ("No se encuentran casillas para el municipio y concepto");
		
		List<Long> coCasillaMunicipios = new ArrayList<Long>(listaCasillaMunicipioDTO.size());
		for (CasillaMunicipioDTO casillaMunicipioDTO : listaCasillaMunicipioDTO ){
			mcDocumentoVO.guardaCasillaMunicipio(
					casillaMunicipioDTO.getCasillaModeloDTO().getId().getNuCasilla(),
					casillaMunicipioDTO.getOrden(),
					casillaMunicipioDTO.getBoMantenible()
			);
			coCasillaMunicipios.add(casillaMunicipioDTO.getCoCasillaMunicipio());
		}
		for (int i=1; i<=2; i++) {
			if(i==1 || coCasillaMunicipios.size()>0){
			DetachedCriteria criteria = DetachedCriteria.forClass(CasillaMunicipioOperacionDTO.class, "op");
			if (i == 1) {
				criteria.add(Restrictions.eq("id.coOperacion", operacion)); // En la primera vuelta, la operación particular.
			} else {
				criteria.add(Restrictions.eq("id.coOperacion", OPERACION_GENERICO)); // En la segunda vuelta, la operación genérica.
			}
			criteria.add(Restrictions.in("id.coCasillaMunicipio", coCasillaMunicipios.toArray()));
			List<CasillaMunicipioOperacionDTO> casillaMunicipioOperacionDTOs = casillaMunicipioOperacionDAO.findByCriteria(criteria);
			for (CasillaMunicipioOperacionDTO casillaMunicipioOperacionDTO : casillaMunicipioOperacionDTOs) {
				long coCasillaMunicipio = casillaMunicipioOperacionDTO.getId().getCoCasillaMunicipio();
				mcDocumentoVO.guardaCasillaMunicipioOperacion(
						getNuCasillaSegunCoCasillaMunicipio(listaCasillaMunicipioDTO, coCasillaMunicipio),
						casillaMunicipioOperacionDTO.getId().getCoOperacion(),
						casillaMunicipioOperacionDTO.getAtributo()
				);
				coCasillaMunicipios.remove(coCasillaMunicipio); // Quitamos las casillas ya tratadas, para que en la siguiente vuelta no las vuela a recuperar.
			}
			}
		}
		/*  FIN: GA_CASILLA_MUNICIPIO Y GA_CASILLA_MUNICIPIO_OPERACION  */
		
		/*  GA_CASILLA_VALOR (tratamiento de valores) */
		short ultimaHoja=0;
		for (DocumentoCasillaValorDTO documentoCasillaValorDTO : listaDocumentoCasillaValorDTO ){
			mcDocumentoVO.guardaCasillaValor(
					documentoCasillaValorDTO.getId().getHoja(),
					documentoCasillaValorDTO.getId().getNuCasilla(),
					documentoCasillaValorDTO.getValor(),
					documentoCasillaValorDTO.getBoError() == null ? false : documentoCasillaValorDTO.getBoError()
			);
			ultimaHoja = documentoCasillaValorDTO.getId().getHoja();	
			
		}
		mcDocumentoVO.setNumHojas(Short.valueOf(ultimaHoja));
		
		if (mcDocumentoVO.getHojas()==null || mcDocumentoVO.getHojas().get(1)==null){
			mcDocumentoVO.aseguraHoja((short)1);
		}
		
		/*  FIN: GA_CASILLA_VALOR  */
		
		
		/* ORDEN */
		@SuppressWarnings("unchecked")
		SortedMap<Short,MCInformacionCasillaVO> sortedData = new TreeMap<Short,MCInformacionCasillaVO>(new MCOrdenCasillaComparator(mcDocumentoVO.getInformacionCasillas()));
		sortedData.putAll(mcDocumentoVO.getInformacionCasillas());
		mcDocumentoVO.setInformacionCasillas(sortedData);
		/* FIN: ORDEN */
		
		
		return mcDocumentoVO;
	}
	
	private String obtenerValorCasilla(List<DocumentoCasillaValorDTO> listaDocumentoCasillaValorDTO, short nuCasillaCoTasaSigre) {
		String valor = null;
		for (DocumentoCasillaValorDTO casillaDTO : listaDocumentoCasillaValorDTO) {
			if (casillaDTO.getId().getNuCasilla() == nuCasillaCoTasaSigre) {
				valor = casillaDTO.getValor();
				break;
			}
		}
		return valor;
	}

	private short getNuCasillaSegunCoCasillaMunicipio(List<CasillaMunicipioDTO> listaCasillaMunicipioDTO, long coCasillaMunicipio) throws GadirServiceException {
		short result = -1;
		for (CasillaMunicipioDTO casillaMunicipioDTO : listaCasillaMunicipioDTO) {
			if (casillaMunicipioDTO.getCoCasillaMunicipio() == coCasillaMunicipio) {
				result = casillaMunicipioDTO.getCasillaModeloDTO().getId().getNuCasilla();
				break;
			}
		}
		if (result == -1) {
			throw new GadirServiceException("No encuentro casilla a partir del coCasillaMunicipio=" + coCasillaMunicipio);
		}
		return result;
	}

	public void cargarHojasRepeticion(MCDocumentoVO mcDocumentoVO) throws GadirServiceException {
		for (Short i=2; i<=mcDocumentoVO.getNumHojas(); i++) {
			mcDocumentoVO.getHojas().remove(i);
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoCasillaValorDTO.class);
		criteria.add(Restrictions.eq("id.coModelo", mcDocumentoVO.getDocumentoDTO().getId().getCoModelo()));
		criteria.add(Restrictions.eq("id.coVersion", mcDocumentoVO.getDocumentoDTO().getId().getCoVersion()));
		criteria.add(Restrictions.eq("id.coDocumento", mcDocumentoVO.getDocumentoDTO().getId().getCoDocumento()));
		criteria.add(Restrictions.gt("id.hoja", CasillaConstants.PRIMERA_HOJA));
		List<DocumentoCasillaValorDTO> listaDocumentoCasillaValorDTO = documentoCasillaValorDAO.findByCriteria(criteria);
		
		short ultimaHoja=0;
		for (DocumentoCasillaValorDTO documentoCasillaValorDTO : listaDocumentoCasillaValorDTO ){
			mcDocumentoVO.guardaCasillaValor(
					documentoCasillaValorDTO.getId().getHoja(),
					documentoCasillaValorDTO.getId().getNuCasilla(),
					documentoCasillaValorDTO.getValor(),
					documentoCasillaValorDTO.getBoError() == null ? false : documentoCasillaValorDTO.getBoError()
			);
			if (documentoCasillaValorDTO.getId().getHoja() > ultimaHoja) {
				ultimaHoja = documentoCasillaValorDTO.getId().getHoja();
			}
		}
		mcDocumentoVO.setNumHojas(Short.valueOf(ultimaHoja));
		
		if (mcDocumentoVO.getHojas()==null || mcDocumentoVO.getHojas().get(1)==null){
			mcDocumentoVO.aseguraHoja((short)1);
		}
	}

	private DetachedCriteria creaConsultaValidacion(String provincia, String municipio,String concepto,String modelo,String version,short nuCasilla, String tipo){
		DetachedCriteria criteria = DetachedCriteria.forClass(ValidacionDTO.class);
		criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio", municipio));
		criteria.add(Restrictions.eq("municipioDTO.id.coProvincia", provincia));
		if(!Utilidades.isEmpty(concepto))
			criteria.add(Restrictions.eq("conceptoDTO.coConcepto", concepto));
		else
			criteria.add(Restrictions.isNull("conceptoDTO.coConcepto"));
		criteria.add(Restrictions.eq("casillaModeloDTO.id.coModelo", modelo));
		criteria.add(Restrictions.eq("casillaModeloDTO.id.coVersion", version));
		criteria.add(Restrictions.eq("casillaModeloDTO.id.nuCasilla", nuCasilla));
		criteria.add(Restrictions.eq("tipo", tipo));
		criteria.add(Restrictions.eq("boActiva", true));
		criteria.addOrder(Order.desc("orden"));
		return criteria;
	}
	
	private ValidacionDTO getAyuda(String coProvincia, String coMunicipio,String  coConcepto, String coModelo, String coVersion, short nuCasilla, boolean conAyudasDeModeloGenerico) throws GadirServiceException{
		//CONSULTA 1.
		DetachedCriteria consulta = creaConsultaValidacion(coProvincia, coMunicipio, coConcepto, coModelo, coVersion, nuCasilla, "H");
		List<ValidacionDTO> listaVal = validacionDAO.findByCriteria(consulta);
		if(listaVal != null && !listaVal.isEmpty()){
			return listaVal.get(0);
		}else{
			//CONSULTA 2
			if (conAyudasDeModeloGenerico){
				consulta = creaConsultaValidacion(coProvincia, coMunicipio, coConcepto, "***", "*", nuCasilla, "H");
				listaVal = validacionDAO.findByCriteria(consulta);
			}
			if(listaVal != null && !listaVal.isEmpty()){
				return listaVal.get(0);
			}else{
				//CONSULTA 3
				consulta = creaConsultaValidacion(coProvincia, coMunicipio, "****", coModelo, coVersion, nuCasilla, "H");
				listaVal = validacionDAO.findByCriteria(consulta);
				if(listaVal != null && !listaVal.isEmpty()){
					return listaVal.get(0);
				}else{
					//CONSULTA 4
					if (conAyudasDeModeloGenerico){
						consulta = creaConsultaValidacion(coProvincia, coMunicipio, "****", "***", "*", nuCasilla, "H");
						listaVal = validacionDAO.findByCriteria(consulta);
					}
					if(listaVal != null && !listaVal.isEmpty()){
						return listaVal.get(0);
					}else{
						//CONSULTA 5
						consulta = creaConsultaValidacion("**", "***", coConcepto, coModelo, coVersion, nuCasilla, "H");
						listaVal = validacionDAO.findByCriteria(consulta);
						if(listaVal != null && !listaVal.isEmpty()){
							return listaVal.get(0);
						}else{
							//CONSULTA 6
							if (conAyudasDeModeloGenerico){
								consulta = creaConsultaValidacion("**", "***", coConcepto, "***", "*", nuCasilla, "H");
								listaVal = validacionDAO.findByCriteria(consulta);
							}
							if(listaVal != null && !listaVal.isEmpty()){
								return listaVal.get(0);
							}else{
								//CONSULTA 7
								consulta = creaConsultaValidacion("**", "***", "****", coModelo, coVersion, nuCasilla, "H");
								listaVal = validacionDAO.findByCriteria(consulta);
								if(listaVal != null && !listaVal.isEmpty()){
									return listaVal.get(0);
								}else{
									//CONSULTA 8
									if (conAyudasDeModeloGenerico){
										consulta = creaConsultaValidacion("**", "***", "****", "***", "*", nuCasilla, "H");
										listaVal = validacionDAO.findByCriteria(consulta);
									}
									if(listaVal != null && !listaVal.isEmpty()){
										return listaVal.get(0);
									}else{
										return null;
									}
								}

							}
						}
					}
				}
			}
		}
	}
	
	//TODO: Si es modo consulta, devolver falso directamente.
	private boolean existeValidacionParticular(String coProvincia, String coMunicipio, String coConcepto, String coModelo, String coVersion) throws GadirServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ValidacionDTO.class);
		criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipio));
		criteria.add(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia));
		
		if(!Utilidades.isEmpty(coConcepto))
			criteria.add(Restrictions.eq("conceptoDTO.coConcepto", coConcepto));
		else
			criteria.add(Restrictions.isNull("conceptoDTO.coConcepto"));
		criteria.add(Restrictions.eq("casillaModeloDTO.id.coModelo", coModelo));
		criteria.add(Restrictions.eq("casillaModeloDTO.id.coVersion", coVersion));
		criteria.add(Restrictions.eq("tipo", "V"));
		criteria.add(Restrictions.eq("boActiva", true));
		int count = validacionDAO.countByCriteria(criteria);
		return count > 0;
	}
	
	private List<ValidacionDTO> getValidaciones(String coProvincia, String coMunicipio,String  coConcepto, String coModelo, String coVersion, short nuCasilla) throws GadirServiceException{
		DetachedCriteria consulta = creaConsultaValidacion(coProvincia, coMunicipio, coConcepto, coModelo, coVersion, nuCasilla, "V");
		List<ValidacionDTO> listaVal = validacionDAO.findByCriteria(consulta);
		if(listaVal != null && !listaVal.isEmpty()){
			return listaVal;//.get(0);
		}else{
//			consulta = creaConsultaValidacion("**", "***", coConcepto, coModelo, coVersion, nuCasilla, "V");
//			listaVal = validacionDAO.findByCriteria(consulta);
//			if(listaVal != null && !listaVal.isEmpty()){
//				return listaVal.get(0);
//			}else{
			return null;
//			}
		}
	}

	public List<Short> findCasillasLigadas(String coModelo, String coVersion, short nuCasilla) throws GadirServiceException {

		List<Short> casillas = new ArrayList<Short>();
		List<CasillasLigadasDTO> casillasLigadas =  casillasLigadasDAO.findFiltered(
				new String[] {"casillaModeloDTO.id.coModelo", "casillaModeloDTO.id.coVersion", "casillaModeloDTO.id.nuCasilla" }, 
				new Object[] {coModelo, coVersion, nuCasilla});
		for(CasillasLigadasDTO dto : casillasLigadas){
			casillas.add(dto.getNuCasillaLigada());
		}
		return casillas;
	}
	
	public boolean ejecutaValidacion(MCDocumentoVO mcDocumentoVO, List<ValidacionDTO> validacionDTOs, short hoja) throws GadirServiceException {
		boolean correcto=false;
		
		for (ValidacionDTO validacionDTO : validacionDTOs) {
			String[] parametros = new String[30];
			
			//FuncionDTO funcion = funcionDAO.findById(validacionDTO.getFuncionDTO().getCoFuncion());
			FuncionDTO funcion = validacionDTO.getFuncionDTO();
			String coPlsql = funcion.getCoPlsql();
			
			List<ValidacionArgumentoDTO> listaArgumentos = validacionArgumentoDAO.findFiltered(new String[] {"id.coValidacion", "id.coFuncion"}, new Object[] {validacionDTO.getCoValidacion(), validacionDTO.getFuncionDTO().getCoFuncion()});
			for(ValidacionArgumentoDTO validacionArgumentoDTO : listaArgumentos){
				if (validacionArgumentoDTO.getTipo() != null) {
					if (validacionArgumentoDTO.getTipo().equals("K") || validacionArgumentoDTO.getTipo().equals("T")){
						parametros[validacionArgumentoDTO.getId().getCoArgumentoFuncion()-1]=validacionArgumentoDTO.getValor();
					}else if(validacionArgumentoDTO.getTipo().equals("S")){
						String valor = null;
						try{
							short hojaDeCasilla;
							if (mcDocumentoVO.getInformacionCasillas().get(Short.valueOf(validacionArgumentoDTO.getValor())).getRepeticion()) {
								hojaDeCasilla = hoja;
							} else {
								hojaDeCasilla = 1;
							}
							valor=((mcDocumentoVO.getHojas().get(hojaDeCasilla)).getCasillas().get(Short.valueOf(validacionArgumentoDTO.getValor()))).getValor();
						}catch(Exception e){
							try{
							valor=((mcDocumentoVO.getHojas().get((short)1)).getCasillas().get(Short.valueOf(validacionArgumentoDTO.getValor()))).getValor();
							}catch(Exception e2){}
						}
						parametros[validacionArgumentoDTO.getId().getCoArgumentoFuncion()-1]=valor;
					}
				}
			}
			
			correcto=FuncionPlsqlUtil.validacion(coPlsql, parametros);
			if (!correcto) {
				break;
			}
		}
		
		return correcto;
	}
	
	public DocumentoDTO findDocumento(String coModelo, String coVersion, String coDocumento) throws GadirServiceException{
		
		DocumentoDTOId documentoDTOId = new DocumentoDTOId(coModelo, coVersion, coDocumento);
		
		return documentoDAO.findById(documentoDTOId);
	}
	
	public void saveDocumentoCompleto(MCDocumentoVO mcDocumentoVO, String coProcesoActual) throws GadirServiceException{
		char separador = '\n';
		Date fhActualizacion= new Date();
		StringBuffer hojas = new StringBuffer();
		StringBuffer casillas = new StringBuffer();
		StringBuffer valores = new StringBuffer();
		StringBuffer errores = new StringBuffer();
		
		for (Short nuHoja : mcDocumentoVO.getHojas().keySet()) {
			MCHojaVO hoja = mcDocumentoVO.getHojas().get(nuHoja);
			for (Short nuCasilla : hoja.getCasillas().keySet()) {
				MCInformacionCasillaVO informacionCasilla=mcDocumentoVO.getInformacionCasillas().get(nuCasilla);
				if ((nuHoja == 1 && !informacionCasilla.getRepeticion()) || (nuHoja > 1 && informacionCasilla.getRepeticion())) {
					MCCasillaVO casilla = hoja.getCasillas().get(nuCasilla);
					hojas.append(nuHoja).append(separador);
					casillas.append(nuCasilla).append(separador);
					if (casilla.getValor() == null) {
						valores.append(separador);
					} else {
						valores.append(casilla.getValor()).append(separador);
					}
//					errores.append(casilla.getError() ? "1" : "0").append(separador);
					errores.append("0").append(separador);
				}
			}
		}
		
		GuardarCasillasMasivoBO guardarCasillasMasivoBO = (GuardarCasillasMasivoBO) GadirConfig.getBean("guardarCasillasMasivoBO");
		Map<String, Object> result = guardarCasillasMasivoBO.execute(
				mcDocumentoVO.getDocumentoDTO().getId().getCoModelo(), 
				mcDocumentoVO.getDocumentoDTO().getId().getCoVersion(), 
				mcDocumentoVO.getDocumentoDTO().getId().getCoDocumento(), 
				hojas.toString(), 
				casillas.toString(), 
				valores.toString(), 
				errores.toString(), 
				fhActualizacion,
				coProcesoActual);
		int coMensajeError = ((BigDecimal) result.get("coMensajeError")).intValue();
		if (coMensajeError == 0) {
			DocumentoDTO documentoDTO = mcDocumentoVO.getDocumentoDTO();
			documentoDTO.setFhActualizacion(fhActualizacion);
			documentoDTO.setCoUsuarioActualizacion(DatosSesion.getLogin());
			getDocumentoDAO().save(documentoDTO);
		} else {
			String variable = (String) result.get("variable");
			throw new GadirServiceException(Mensaje.getTexto(coMensajeError, variable));
		}
	}
	
	public int borraDocumentoCompleto(MCDocumentoVO mcDocumentoVO, String coProcesoActual) throws GadirServiceException{
		BorradoManualDocumentoBO borradoManualDocumentoBO = (BorradoManualDocumentoBO) GadirConfig.getBean("borradoManualDocumentoBO");
		int result = borradoManualDocumentoBO.execute(
				mcDocumentoVO.getDocumentoDTO().getId().getCoModelo(), 
				mcDocumentoVO.getDocumentoDTO().getId().getCoVersion(), 
				mcDocumentoVO.getDocumentoDTO().getId().getCoDocumento(), 
				"GRABA_HISTORICO",
				coProcesoActual);
		return result;
	}


	public DAOBase<DocumentoDTO, DocumentoDTOId> getDocumentoDAO() {
		return documentoDAO;
	}

	public void setDocumentoDAO(DAOBase<DocumentoDTO, DocumentoDTOId> documentoDAO) {
		this.documentoDAO = documentoDAO;
	}

	public DAOBase<DocumentoCasillaValorDTO, DocumentoCasillaValorDTOId> getDocumentoCasillaValorDAO() {
		return documentoCasillaValorDAO;
	}

	public void setDocumentoCasillaValorDAO(
			DAOBase<DocumentoCasillaValorDTO, DocumentoCasillaValorDTOId> documentoCasillaValorDAO) {
		this.documentoCasillaValorDAO = documentoCasillaValorDAO;
	}

	public DAOBase<CasillaMunicipioDTO, Long> getCasillaMunicipioDAO() {
		return casillaMunicipioDAO;
	}

	public void setCasillaMunicipioDAO(
			DAOBase<CasillaMunicipioDTO, Long> casillaMunicipioDAO) {
		this.casillaMunicipioDAO = casillaMunicipioDAO;
	}

	public DAOBase<CasillaModeloDTO, CasillaModeloDTOId> getCasillaModeloDAO() {
		return casillaModeloDAO;
	}

	public void setCasillaModeloDAO(
			DAOBase<CasillaModeloDTO, CasillaModeloDTOId> casillaModeloDAO) {
		this.casillaModeloDAO = casillaModeloDAO;
	}

	public DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> getCasillaMunicipioOperacionDAO() {
		return casillaMunicipioOperacionDAO;
	}

	public void setCasillaMunicipioOperacionDAO(
			DAOBase<CasillaMunicipioOperacionDTO, CasillaMunicipioOperacionDTOId> casillaMunicipioOperacionDAO) {
		this.casillaMunicipioOperacionDAO = casillaMunicipioOperacionDAO;
	}

	public DAOBase<ValidacionDTO, String> getValidacionDAO() {
		return validacionDAO;
	}

	public void setValidacionDAO(DAOBase<ValidacionDTO, String> validacionDAO) {
		this.validacionDAO = validacionDAO;
	}

	public DAOBase<CasillasLigadasDTO, Long> getCasillasLigadasDAO() {
		return casillasLigadasDAO;
	}

	public void setCasillasLigadasDAO(
			DAOBase<CasillasLigadasDTO, Long> casillasLigadasDAO) {
		this.casillasLigadasDAO = casillasLigadasDAO;
	}

	public DAOBase<FuncionDTO, String> getFuncionDAO() {
		return funcionDAO;
	}

	public void setFuncionDAO(DAOBase<FuncionDTO, String> funcionDAO) {
		this.funcionDAO = funcionDAO;
	}

	public DAOBase<ValidacionArgumentoDTO, ValidacionArgumentoDTOId> getValidacionArgumentoDAO() {
		return validacionArgumentoDAO;
	}

	public void setValidacionArgumentoDAO(
			DAOBase<ValidacionArgumentoDTO, ValidacionArgumentoDTOId> validacionArgumentoDAO) {
		this.validacionArgumentoDAO = validacionArgumentoDAO;
	}

	public DAOBase<ModeloDTO, String> getModeloDAO() {
		return modeloDAO;
	}

	public void setModeloDAO(DAOBase<ModeloDTO, String> modeloDAO) {
		this.modeloDAO = modeloDAO;
	}

	
	
	
}
