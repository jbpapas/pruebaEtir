package es.dipucadiz.etir.comun.bo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import es.dipucadiz.etir.comun.bo.DocumentoCasillaValorBO;
import es.dipucadiz.etir.comun.bo.DocumentoNotDgcBO;
import es.dipucadiz.etir.comun.bo.HNotificacionDGCBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.dto.DocumentoNotDgcDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb07.comun.vo.DocumentoNotDgcVO;
import es.dipucadiz.etir.sb07.comun.vo.ResolucionCatastralSeleccionadaVO;
import es.dipucadiz.etir.sb07.comun.vo.ResolucionCatastralVO;


public class DocumentoNotDgcBOImpl extends AbstractGenericBOImpl<DocumentoNotDgcDTO, Long> implements DocumentoNotDgcBO{

	
	private static final long serialVersionUID = -5783761310957232932L;
	
	private DAOBase<DocumentoNotDgcDTO, Long> dao;
	
	private DocumentoCasillaValorBO documentoCasillaValorBO;
	
	private HNotificacionDGCBO hnotificacionDgcBO;
	
	public DAOBase<DocumentoNotDgcDTO, Long> getDao() {
		return dao;
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean findDocumentoCatastral(String tDocumento, String coProvincia, String coMunicipio, 
			String coModelo, String coConcepto, String coVersion, Short ejercicio, String coPeriodo)
			throws GadirServiceException {
			String queryString;
			queryString = 	"select n " +
							"from DocumentoNotDgcDTO n " +
							"left join fetch n.documentoDTO " +
							"where n.documentoDTO.id.coDocumento in( "+
								"select d.id.coDocumento "+
								"from DocumentoDTO d "+
								"where d.municipioDTO.id.coMunicipio = '"+ coMunicipio+"' "+
								"and d.municipioDTO.id.coProvincia = '"+ coProvincia+"' "+
								"and d.modeloVersionDTO.id.coModelo = '"+ coModelo+"' "+
								"and d.modeloVersionDTO.id.coVersion = '"+ coVersion+"' "+	
								"and d.conceptoDTO.coConcepto = '"+ coConcepto+"' ";
			try {
				if ((Utilidades.isNotNull(ejercicio)))
					queryString = queryString + "and d.ejercicio = '"+ ejercicio+"' ";
					
				if ((Utilidades.isNotEmpty(coPeriodo)) && (Utilidades.isNotEmpty(tDocumento))) {
					queryString = queryString + "and d.periodo = '"+coPeriodo+"') ";
					queryString = queryString + "and n.tipo = '"+tDocumento+"' ";
				}
				if ((Utilidades.isNotEmpty(coPeriodo)) && (Utilidades.isEmpty(tDocumento))) {
					queryString = queryString + "and d.periodo = '"+coPeriodo+"') ";
				}
				if ((Utilidades.isEmpty(coPeriodo)) && (Utilidades.isNotEmpty(tDocumento))) {
					queryString = queryString + ")and n.tipo = '"+tDocumento+"' ";
				}
				if ((Utilidades.isEmpty(coPeriodo)) && (Utilidades.isEmpty(tDocumento))) {
					queryString = queryString + ") ";
				}
			List<DocumentoNotDgcDTO> result = (List<DocumentoNotDgcDTO>) this.getDao()
					.findByQuery(queryString);
			if(result.size() > 0){
				return true;
			}
			else{
				return false;
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el documento catastral.", e);
		}
	}
	
	

	
	
	/**
	 * {@inheritDoc}
	 */
	public List<DocumentoNotDgcVO> findEnviosBop(String publicados, String noPublicados) throws GadirServiceException{
		
		String queryString;
		queryString = 	"select n.grupoBop, n.fxEnvioBop, n.fxPublicacionBop, n.fxNotificacion " +
						"from DocumentoNotDgcDTO n ";
		try {
			
			queryString = queryString + "where boNotificacionBop = 1 ";
			
			if (("true".equals(publicados)) && (Utilidades.isEmpty(noPublicados))) {
				queryString = queryString + "and fxPublicacionBop is not null ";
			}
			if ((Utilidades.isEmpty(publicados)) && ("true".equals(noPublicados))){
				queryString = queryString + "and fxPublicacionBop is null ";	
			}
			
			queryString = queryString + "group by n.fxEnvioBop, n.fxPublicacionBop, n.fxNotificacion, n.grupoBop";
			
			List<DocumentoNotDgcDTO> listaResultado = (List<DocumentoNotDgcDTO>) this.getDao().findByQuery(queryString);
			
			List<DocumentoNotDgcVO> listaResultadoVO = new ArrayList<DocumentoNotDgcVO>();

			for(Object res: listaResultado){
				DocumentoNotDgcVO resultadoVO = new DocumentoNotDgcVO();
				
				Object[] aux = (Object[])res;
						
				resultadoVO.setGrupoBop((Long)aux[0]);
				resultadoVO.setFxEnvioBop((Date)aux[1]);
				resultadoVO.setFxPublicacionBop((Date)aux[2]);
				resultadoVO.setFxNotificacion((Date)aux[3]);
							
				listaResultadoVO.add(resultadoVO);
			}
			
			return listaResultadoVO;
		}
		catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener la lista de relaciones de envíos a BOP.", e);
			
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public List<ResolucionCatastralVO> findEnviosGrupoBop(Long grupoBop) throws GadirServiceException{
		List<ResolucionCatastralVO> result = new ArrayList<ResolucionCatastralVO>();
		String queryString;
		queryString = 	"from DocumentoNotDgcDTO n " +
						"left join fetch n.documentoDTO " +
						"where n.grupoBop = "+ grupoBop +" ";
						
		try {
			List<DocumentoNotDgcDTO> lista = new ArrayList<DocumentoNotDgcDTO>();
			lista = this.getDao().findByQuery(queryString);
			
			Iterator<DocumentoNotDgcDTO> it = lista.iterator();

			while (it.hasNext()) {
				DocumentoNotDgcDTO notificacion = it.next();
				result.add(this.obtenerResolucionCatastralVO(notificacion));
			}
			return result;
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el documento catastral.", e);
		}
		
	}
	
	
	public List<ResolucionCatastralVO> findDocumentosEnvio(Long grupoBop, String fechaEnvio, String fechaPublicacion, String fechaNotificacion) throws GadirServiceException{
		List<ResolucionCatastralVO> result = new ArrayList<ResolucionCatastralVO>();
		String queryString;
		queryString = 	"from DocumentoNotDgcDTO n " +
						"left join fetch n.documentoDTO " +
						"where n.grupoBop = '"+ grupoBop  + "' ";
		
		if(!Utilidades.isEmpty(fechaEnvio)){
			queryString = queryString + "and n.fxEnvioBop = '" + fechaEnvio  + "' ";
		}
		else
			queryString = queryString + "and n.fxEnvioBop is null ";
		
		if(!Utilidades.isEmpty(fechaPublicacion)){
			queryString = queryString + "and n.fxPublicacionBop = '" + fechaPublicacion  + "' ";
		}
		else
			queryString = queryString + "and n.fxPublicacionBop is null ";
		
		if(!Utilidades.isEmpty(fechaNotificacion)){
			queryString = queryString + "and n.fxNotificacion = '" + fechaNotificacion + "' ";
		}
		else
			queryString = queryString + "and n.fxNotificacion is null ";
						
		try {
			List<DocumentoNotDgcDTO> lista = new ArrayList<DocumentoNotDgcDTO>();
			lista = this.getDao().findByQuery(queryString);
			
			Iterator<DocumentoNotDgcDTO> it = lista.iterator();

			while (it.hasNext()) {
				DocumentoNotDgcDTO notificacion = it.next();
				result.add(this.obtenerResolucionCatastralVO(notificacion));
			}
			return result;
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el documento catastral.", e);
		}
		
	}
	
	
	
	/**
	 * {@inheritDoc}
	 */
	public List<ResolucionCatastralVO> findResolucionCatastralVO(String anoExpediente, String numExpediente, String docExpediente, 
			Boolean liquidados, Boolean noLiquidados, Boolean liquidables, Boolean noLiquidables) throws GadirServiceException {
		List<ResolucionCatastralVO> result = new ArrayList<ResolucionCatastralVO>();
		String queryString;
		queryString = 	"select n " +
						"from DocumentoNotDgcDTO n " +
						"left join fetch n.documentoDTO " +
						"left join fetch n.documentoDTO.conceptoDTO " +
						"where n.anoExpediente = "+ anoExpediente +" " +
						"and n.expedienteDgc = '"+ numExpediente +"' " +
						"and n.documentoDTO.modeloVersionDTO.modeloDTO.tipo = 'T' " + 
						"and n.documentoDTO.modeloVersionDTO.modeloDTO.subtipo = 'N'";
							
		try {
			if (Utilidades.isNotEmpty(docExpediente)) {
				queryString = queryString + " and n.documentoExpediente = "+docExpediente;
			}
			queryString = queryString + booleanosToWhere(noLiquidables, liquidables, noLiquidados, liquidados);
			
			Iterator<DocumentoNotDgcDTO> it = this.getDao().findByQuery(queryString).iterator();

			while (it.hasNext()) {
				DocumentoNotDgcDTO notificacion = it.next();
				result.add(this.obtenerResolucionCatastralVO(notificacion));
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el documento catastral.", e);
		}
		return result;
	}
	
	private String booleanosToWhere(boolean noLiquidables, boolean liquidables, boolean noLiquidados, boolean liquidados) {
		String whereString = "";
		if (liquidables && !noLiquidables) {
			whereString = whereString + " and n.boLiquidable = 1";
		}
		if (noLiquidables && !liquidables) {
			whereString = whereString + " and n.boLiquidable = 0";
		}
		if (liquidados && !noLiquidados) {
			whereString = whereString + " and n.boLiquidado = 1";
		}
		if (noLiquidados && !liquidados) {
			whereString = whereString + " and n.boLiquidado = 0";
		}
		return whereString;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ResolucionCatastralVO> findResolucionCatastralVOByDocumento(String tDocumento, String coProvincia, String coMunicipio, 
			String coModelo, String coConcepto, String coVersion, Short ejercicio, String coPeriodo, Boolean liquidados, 
			Boolean noLiquidados, Boolean liquidables, Boolean noLiquidables) throws GadirServiceException {
		List<ResolucionCatastralVO> result = new ArrayList<ResolucionCatastralVO>();
		String queryString;
		queryString = 	"select n " +
						"from DocumentoNotDgcDTO n " +
						"left join fetch n.documentoDTO " +
						"left join fetch n.documentoDTO.conceptoDTO " +
						"where n.documentoDTO.municipioDTO.id.coMunicipio = '"+ coMunicipio +"' " +
						"and n.documentoDTO.ejercicio = " + ejercicio.toString() + " " +									
						"and n.documentoDTO.municipioDTO.id.coProvincia = '"+ coProvincia +"' " +
						"and n.documentoDTO.modeloVersionDTO.modeloDTO.tipo = 'T' " + 
						"and n.documentoDTO.modeloVersionDTO.modeloDTO.subtipo = 'N' ";
		try {
			if (Utilidades.isNotEmpty(coConcepto)) {
				queryString = queryString + "and n.documentoDTO.conceptoDTO.coConcepto = '"+ coConcepto +"' ";
			}
			if (Utilidades.isNotEmpty(coModelo)) {
				queryString = queryString + "and n.documentoDTO.id.coModelo = '"+ coModelo +"' ";
			}
			if (Utilidades.isNotEmpty(coVersion)) {
				queryString = queryString + "and n.documentoDTO.id.coVersion = '"+ coVersion +"' ";
			}
			if (Utilidades.isNotEmpty(coPeriodo)) {
				queryString = queryString + "and n.documentoDTO.periodo = '" + coPeriodo + "' ";
			}
			if (Utilidades.isNotEmpty(tDocumento)) {
				queryString = queryString + "and n.tipo = '"+ tDocumento +"' ";
			}
			queryString = queryString + booleanosToWhere(noLiquidables, liquidables, noLiquidados, liquidados);
			
			Iterator<DocumentoNotDgcDTO> it = this.getDao().findByQuery(queryString).iterator();

			while (it.hasNext()) {
				DocumentoNotDgcDTO notificacion = it.next();
				result.add(this.obtenerResolucionCatastralVO(notificacion));
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el documento catastral.", e);
		}
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<ResolucionCatastralVO> findResolucionCatastralVOByRefCatastral(String refCatastral, Boolean liquidados, 
			Boolean noLiquidados, Boolean liquidables, Boolean noLiquidables) throws GadirServiceException {
		List<ResolucionCatastralVO> result = new ArrayList<ResolucionCatastralVO>();
		String queryString;
		queryString = 	"from DocumentoNotDgcDTO n " +
						"left join fetch n.documentoDTO " +
						"where n.documentoDTO.refCatastral = '"+ refCatastral +"' " +
						"and n.documentoDTO.modeloVersionDTO.modeloDTO.tipo = 'T' " + 
						"and n.documentoDTO.modeloVersionDTO.modeloDTO.subtipo = 'N' ";
		try {
			queryString = queryString + booleanosToWhere(noLiquidables, liquidables, noLiquidados, liquidados);
			
			Iterator<DocumentoNotDgcDTO> it = this.getDao().findByQuery(queryString).iterator();

			while (it.hasNext()) {
				DocumentoNotDgcDTO notificacion = it.next();
				result.add(this.obtenerResolucionCatastralVO(notificacion));
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el documento catastral.", e);
		}
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<ResolucionCatastralVO> findResolucionCatastralVOByNumDocumento(String codModelo, String
			codVersion, String codDocumento, Boolean liquidados, Boolean noLiquidados, Boolean liquidables, 
			Boolean noLiquidables) throws GadirServiceException {
		List<ResolucionCatastralVO> result = new ArrayList<ResolucionCatastralVO>();
		String queryString;
		queryString = 	"from DocumentoNotDgcDTO n " +
						"left join fetch n.documentoDTO " +
						"where n.documentoDTO.id.coModelo = '"+ codModelo +"' " +
						"and n.documentoDTO.id.coVersion = '"+ codVersion +"' " +
						"and n.documentoDTO.id.coDocumento = '"+ codDocumento +"' " +
						"and n.documentoDTO.modeloVersionDTO.modeloDTO.tipo = 'T' " + 
						"and n.documentoDTO.modeloVersionDTO.modeloDTO.subtipo = 'N' ";
		try {
			queryString = queryString + booleanosToWhere(noLiquidables, liquidables, noLiquidados, liquidados);

			Iterator<DocumentoNotDgcDTO> it = this.getDao().findByQuery(queryString).iterator();

			while (it.hasNext()) {
				DocumentoNotDgcDTO notificacion = it.next();
				result.add(this.obtenerResolucionCatastralVO(notificacion));
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el documento catastral.", e);
		}
		return result;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public DocumentoNotDgcDTO findDocNotDgcByModeloVersionDocumento(String codModelo, String
			codVersion, String codDocumento) throws GadirServiceException {
		DocumentoNotDgcDTO result = new DocumentoNotDgcDTO();
		String queryString;
		queryString = 	"from DocumentoNotDgcDTO n " +
						"left join fetch n.documentoDTO " +
						"where n.documentoDTO.id.coModelo = '"+ codModelo +"' " +
						"and n.documentoDTO.id.coVersion = '"+ codVersion +"' " +
						"and n.documentoDTO.id.coDocumento = '"+ codDocumento +"' )";
		try {
			List<DocumentoNotDgcDTO> lista = new ArrayList<DocumentoNotDgcDTO>();
			lista = this.getDao().findByQuery(queryString);
			if(lista.size() > 0){
				result = lista.get(0);
				Hibernate.initialize(result.getDocumentoDTO());
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el documento catastral.", e);
		}
		return result;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public DocumentoNotDgcDTO findByIdFetch(final Long coDocumentoNotDgc)
	        throws GadirServiceException {
		try {
			final Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("coNotificacionDgc", coDocumentoNotDgc);
			return findByNamedQuery(QueryName.FIND_NOTIFIACION_BY_FECH, param)
			.get(0);	
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener la notifiacion Dgc con id: "
			                + coDocumentoNotDgc, e);
		}
	}
	
	public void modificarNotificacion(ResolucionCatastralSeleccionadaVO notificacion) throws GadirServiceException{
		
		try {
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
			DocumentoNotDgcDTO obj = findById(notificacion.getCoDocumentoNotDgc());
			boolean modificado = false;
			
			if ((Utilidades.isEmpty(notificacion.getResultNotificacion()) && !Utilidades.isEmpty(obj.getResultadoNotificacion()))
					|| (!Utilidades.isEmpty(notificacion.getResultNotificacion()) && Utilidades.isEmpty(obj.getResultadoNotificacion()))
					||(!Utilidades.isEmpty(notificacion.getResultNotificacion()) && !Utilidades.isEmpty(obj.getResultadoNotificacion())
					&& !notificacion.getResultNotificacion().equals(obj.getResultadoNotificacion()))){
				
				obj.setResultadoNotificacion(notificacion.getResultNotificacion());
				modificado = true;
				
			}		

			if ((notificacion.getFechaNotificacion() == null && obj.getFxNotificacion() != null)
					|| (Utilidades.isNotEmpty(notificacion.getFechaNotificacion()) && obj.getFxNotificacion() == null)
					|| (Utilidades.isNotEmpty(notificacion.getFechaNotificacion()) && obj.getFxNotificacion() != null
							&&!notificacion.getFechaNotificacion().equals(formateador.format(obj.getFxNotificacion())))){
				if (notificacion.getFechaNotificacion() != null) {
					obj.setFxNotificacion(formateador.parse(notificacion.getFechaNotificacion()));
				} else {
					obj.setFxNotificacion(null);
				}
				modificado = true;
			}
			
			
			
			if (Utilidades.isNotEmpty(notificacion.getLiquidable())){
				if (notificacion.getLiquidable().charAt(0)== 'S' && 
//						(obj.getBoLiquidable() == null || !obj.getBoLiquidable())){
						(!obj.getBoLiquidable())){
					obj.setBoLiquidable(true);
					modificado = true;
				}
				if (notificacion.getLiquidable().charAt(0)== 'N' && 
//						(obj.getBoLiquidable() == null || obj.getBoLiquidable())){
						(obj.getBoLiquidable())){
					obj.setBoLiquidable(false);
					modificado = true;
				}		
			}
			if (modificado){
				this.save(obj);
			}
			
			
		}
		catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al modificar la notificacion", e);
			
		}
	}
	
	public void eliminarNotificacion(Long idNotificacion) throws GadirServiceException{
		try {
			DocumentoNotDgcDTO noti = findById(idNotificacion);
			// ya lo hace el triguer
		//	hnotificacionDgcBO.guardarHNotificacionDGC(noti, "B");
			this.delete(idNotificacion);
		}
		catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al eliminar la notificacion", e);
			
		}
	}
	
	
	public void auditorias(DocumentoNotDgcDTO transientObject, Boolean saveOnly)
			throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	private ResolucionCatastralVO obtenerResolucionCatastralVO(DocumentoNotDgcDTO notificacion) 
		throws GadirServiceException {
		ResolucionCatastralVO resolucion = new ResolucionCatastralVO();
		resolucion.setMunicipio(
				notificacion.getDocumentoDTO().getMunicipioDTO().getId().getCoProvincia() + 
				notificacion.getDocumentoDTO().getMunicipioDTO().getId().getCoMunicipio());
		
		if(notificacion.getBoLiquidado())
			resolucion.setBoLiquidado("Sí");
		else
			resolucion.setBoLiquidado("No");
		if(notificacion.getBoLiquidable())
			resolucion.setBoLiquidable("Sí");
		else
			resolucion.setBoLiquidable("No");
		resolucion.setCoDocumento(notificacion.getDocumentoDTO().getId().getCoDocumento());
		if (Utilidades.isNotNull(notificacion.getDocumentoDTO().getConceptoDTO())) {
//			resolucion.setConcepto(notificacion.getDocumentoDTO().getConceptoDTO().getNombre());
			resolucion.setConcepto(notificacion.getDocumentoDTO().getConceptoDTO().getCoConcepto());
		}
		resolucion.setCoDocumentoNotDgc(notificacion.getCoDocumentoNotDgc());
		resolucion.setEjercicio(notificacion.getDocumentoDTO().getEjercicio());
		
		String anoExpediente;
		if (notificacion.getAnoExpediente() == null) {
			anoExpediente = "";
		} else {
			anoExpediente = String.valueOf(notificacion.getAnoExpediente());
		}
		if(notificacion.getDocumentoExpediente()!=null){
			resolucion.setExpediente(anoExpediente + " " +
					notificacion.getExpedienteDgc() + " " + 
					notificacion.getDocumentoExpediente().toString());
		}
		else
		{
			resolucion.setExpediente(anoExpediente + " " +
					notificacion.getExpedienteDgc());
		}
//		resolucion.setExpediente(String.valueOf(notificacion.getAnoExpediente()) + " " +
//				notificacion.getExpedienteDgc() + " " + 
//				((notificacion.getDocumentoExpediente()!=null)?notificacion.getDocumentoExpediente().toString():""));
		resolucion.setNumDocumento(notificacion.getDocumentoDTO().getId().getCoModelo() + " " +
				notificacion.getDocumentoDTO().getId().getCoVersion() + " " +
				notificacion.getDocumentoDTO().getId().getCoDocumento());
//		resolucion.setPeriodo(TablaGt.getCodigoDescripcion(TablaGt.TABLA_PERIODO, notificacion.getDocumentoDTO().getPeriodo()).getValue());
		resolucion.setPeriodo(notificacion.getDocumentoDTO().getPeriodo());
		
		resolucion.setRefCatastral(notificacion.getDocumentoDTO().getRefCatastral());
		resolucion.setTipoDocumento(notificacion.getTipo());
		
		resolucion.setTitular(rellenaRazonSocialTitular(notificacion));
		resolucion.setDni(rellenaIdentificadorTitular(notificacion));
		return resolucion;
	}
	
	private String rellenaRazonSocialTitular (DocumentoNotDgcDTO notificacion) throws GadirServiceException{
		String titular = "";
		List<DocumentoCasillaValorDTO> casillas;
		if ("551".equals(notificacion.getDocumentoDTO().getId().getCoModelo()) &&
				"1".equals(notificacion.getDocumentoDTO().getId().getCoVersion())) {
			casillas = this.getDocumentoCasillaValorBO().findDocumentosCasillaByDocumentoAndNuCasilla(notificacion.getDocumentoDTO().getId().getCoDocumento(), 
					notificacion.getDocumentoDTO().getId().getCoModelo(), 
					notificacion.getDocumentoDTO().getId().getCoVersion(), "064");
			if (casillas.size() > 0) {
				titular = casillas.get(0).getValor();
			}
		}
		else if ("552".equals(notificacion.getDocumentoDTO().getId().getCoModelo()) &&
				"1".equals(notificacion.getDocumentoDTO().getId().getCoVersion())) {
			casillas = this.getDocumentoCasillaValorBO().findDocumentosCasillaByDocumentoAndNuCasilla(notificacion.getDocumentoDTO().getId().getCoDocumento(), 
					notificacion.getDocumentoDTO().getId().getCoModelo(), 
					notificacion.getDocumentoDTO().getId().getCoVersion(), "071");
			if (casillas.size() > 0) {
				titular = casillas.get(0).getValor();
			}
		}
		else {
			casillas = this.getDocumentoCasillaValorBO().findDocumentosCasillaByDocumentoAndNuCasilla(notificacion.getDocumentoDTO().getId().getCoDocumento(), 
					notificacion.getDocumentoDTO().getId().getCoModelo(), 
					notificacion.getDocumentoDTO().getId().getCoVersion(), "011");
			if (casillas.size() > 0) {
				titular = casillas.get(0).getValor();
			}
		}
		return titular;
	}
	private String rellenaIdentificadorTitular (DocumentoNotDgcDTO notificacion) throws GadirServiceException{
		String identificador = "";
		List<DocumentoCasillaValorDTO> casillas;
		if ("551".equals(notificacion.getDocumentoDTO().getId().getCoModelo()) &&
				"1".equals(notificacion.getDocumentoDTO().getId().getCoVersion())) {
			casillas = this.getDocumentoCasillaValorBO().findDocumentosCasillaByDocumentoAndNuCasilla(notificacion.getDocumentoDTO().getId().getCoDocumento(), 
					notificacion.getDocumentoDTO().getId().getCoModelo(), 
					notificacion.getDocumentoDTO().getId().getCoVersion(), "062");
			if (casillas.size() > 0) {
				identificador =  casillas.get(0).getValor();
			}
		}
		else if ("552".equals(notificacion.getDocumentoDTO().getId().getCoModelo()) &&
				"1".equals(notificacion.getDocumentoDTO().getId().getCoVersion())) {
			casillas = this.getDocumentoCasillaValorBO().findDocumentosCasillaByDocumentoAndNuCasilla(notificacion.getDocumentoDTO().getId().getCoDocumento(), 
					notificacion.getDocumentoDTO().getId().getCoModelo(), 
					notificacion.getDocumentoDTO().getId().getCoVersion(), "069");
			if (casillas.size() > 0) {
				identificador = casillas.get(0).getValor();
			}
		}
		else {
			casillas = this.getDocumentoCasillaValorBO().findDocumentosCasillaByDocumentoAndNuCasilla(notificacion.getDocumentoDTO().getId().getCoDocumento(), 
					notificacion.getDocumentoDTO().getId().getCoModelo(), 
					notificacion.getDocumentoDTO().getId().getCoVersion(), "010");
			if (casillas.size() > 0) {
				identificador = casillas.get(0).getValor();
			}
		}
		return identificador;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public ResolucionCatastralSeleccionadaVO buscaResolucionCatastralSeleccionadaVO(final Long coDocumentoNotDgc)
	        throws GadirServiceException {
		try {
			final Map<String, Object> param = new HashMap<String, Object>(1);
			param.put("coNotificacionDgc", coDocumentoNotDgc);
			DocumentoNotDgcDTO noti =  findByNamedQuery(QueryName.FIND_NOTIFIACION_BY_FECH, param).get(0);
			Hibernate.initialize(noti.getDocumentoDTO());
			Hibernate.initialize(noti.getDocumentoDTO().getMunicipioDTO());
			ResolucionCatastralSeleccionadaVO result = new ResolucionCatastralSeleccionadaVO(noti);
			result.setNombreMunicipio(noti.getDocumentoDTO().getMunicipioDTO().getCodigoDescripcion());
//			if ("551".equals(noti.getDocumentoDTO().getId().getCoModelo()) && "1".equals(noti.getDocumentoDTO().getId().getCoVersion())) {
//				result.setRazonSocialDestinatario("");
//				result.setNifDestinatario("");
//			}
			if (noti.getDocumentoDTO().getId().getCoModelo().startsWith("55")) {
				result.setRazonSocialDestinatario(noti.getNombreDestinatario());
				result.setNifDestinatario(noti.getNifDestinatario());
				result.setRazonSocialTitular(noti.getNombreTitular());
				result.setNifTitular(noti.getNifTitular());
			} else {
				result.setRazonSocialTitular(rellenaRazonSocialTitular(noti));
				result.setNifTitular(rellenaIdentificadorTitular(noti));
			}
			
			return result;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Ocurrio un error al obtener la notifiacion Dgc con id: "
			                + coDocumentoNotDgc, e);
		}
	}
	

	/**
	 * @param dao the dao to set
	 */
	public void setDao(DAOBase<DocumentoNotDgcDTO, Long> dao) {
		this.dao = dao;
	}

	/**
	 * @return the documentoCasillaValorBO
	 */
	public DocumentoCasillaValorBO getDocumentoCasillaValorBO() {
		return documentoCasillaValorBO;
	}

	/**
	 * @param documentoCasillaValorBO the documentoCasillaValorBO to set
	 */
	public void setDocumentoCasillaValorBO(
			DocumentoCasillaValorBO documentoCasillaValorBO) {
		this.documentoCasillaValorBO = documentoCasillaValorBO;
	}

	/**
	 * @return the hnotificacionDgcBO
	 */
	public HNotificacionDGCBO getHnotificacionDgcBO() {
		return hnotificacionDgcBO;
	}

	/**
	 * @param hnotificacionDgcBO the hnotificacionDgcBO to set
	 */
	public void setHnotificacionDgcBO(HNotificacionDGCBO hnotificacionDgcBO) {
		this.hnotificacionDgcBO = hnotificacionDgcBO;
	}

	public List<ResolucionCatastralVO> findResolucionCatastralVOByCoCliente(String coCliente, boolean liquidados, boolean noLiquidados, boolean liquidables, boolean noLiquidables) throws GadirServiceException {
		List<ResolucionCatastralVO> result = new ArrayList<ResolucionCatastralVO>();
		String queryString;
		queryString = 	"from DocumentoNotDgcDTO n " +
						"left join fetch n.documentoDTO " +
						"where n.documentoDTO.clienteDTO.coCliente = '"+ coCliente +"' " +
						"and n.documentoDTO.modeloVersionDTO.modeloDTO.tipo = 'T' " + 
						"and n.documentoDTO.modeloVersionDTO.modeloDTO.subtipo = 'N' ";
		try {
			queryString = queryString + booleanosToWhere(noLiquidables, liquidables, noLiquidados, liquidados);
			
			Iterator<DocumentoNotDgcDTO> it = this.getDao().findByQuery(queryString).iterator();

			while (it.hasNext()) {
				DocumentoNotDgcDTO notificacion = it.next();
				result.add(this.obtenerResolucionCatastralVO(notificacion));
			}
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el documento catastral.", e);
		}
		return result;
	}


	
	
}