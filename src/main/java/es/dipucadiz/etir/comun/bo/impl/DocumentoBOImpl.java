package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.bo.DocumentoCasillaValorBO;
import es.dipucadiz.etir.comun.constants.EstadoConstants;
import es.dipucadiz.etir.comun.constants.SituacionConstants;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.dto.ModeloVersionDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.ControlTerritorial;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Formato;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.ResumenDocumentoVO;
import es.dipucadiz.etir.sb05.vo.DocumentosCandidatosVO;
import es.dipucadiz.etir.sb05.vo.DocumentosEntradaVO;
import es.dipucadiz.etir.sb05.vo.DocumentosEstadoVO;


public class DocumentoBOImpl extends
AbstractGenericBOImpl<DocumentoDTO, DocumentoDTOId> implements
DocumentoBO {


	private static final long serialVersionUID = 6600031313681872877L;


	private DAOBase<DocumentoDTO, DocumentoDTOId> documentoDao;

	private DocumentoCasillaValorBO servicioDocumentoCasilla;
	private DAOBase<ModeloDTO, String> modeloDao;

	public Integer cuantosDocumentosByMunicipioConceptoMVEP(MunicipioDTO municipio, 
			ConceptoDTO concepto, 
			ModeloVersionDTO modeloVersion, 
			Short ejercicio, 
			String periodo) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoDTO.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("municipioDTO",municipio));
		criteria.add(Restrictions.eq("conceptoDTO",concepto));
		criteria.add(Restrictions.eq("modeloVersionDTO",modeloVersion));
		criteria.add(Restrictions.eq("ejercicio",ejercicio));
		criteria.add(Restrictions.eq("periodo",periodo));

		List lista=this.documentoDao.findByCriteria(criteria);
		if(lista!=null &&  lista.size()>0 && lista.get(0)!= null){

			return Integer.parseInt(lista.get(0).toString());
		}

		return 0;




	}

	@SuppressWarnings("unchecked")
	public List<DocumentoDTO> findDocumentosByMunicipioConcepto(
			final String coMunicipio, final String coConcepto)
			throws GadirServiceException {
		List<DocumentoDTO> resultado;
		try {
			final HashMap map = new HashMap();
			map.put("coMunicipio", coMunicipio);
			map.put("coConcepto", coConcepto);
			if (Utilidades.isNotEmpty(coMunicipio)
					&& Utilidades.isNotEmpty(coConcepto)) {
				resultado = this.findByNamedQuery(
						QueryName.FIND_DOCUMENTOS_MUNICIPIO_CONCEPTO, map);
			} else {
				resultado = Collections.EMPTY_LIST;
			}
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error al obtener los documentos filtrados por municipio y concepto",
					e);
		}
		return resultado;
	}


	public DocumentoDTO findDocumentoByRowid(String rowid)
	throws GadirServiceException {
		DocumentoDTO documento = null;
		try {
			final Map<String, Object> params = new HashMap<String, Object>(
					1);
			params.put("rowid", Utilidades.decodificarRowidFormatoSeguro(rowid));
			List<DocumentoDTO> lista = (List<DocumentoDTO>) this.getDao().findByNamedQuery(
					"Documento.findByRowid",
					params);
			if (lista.size() > 0){
				documento = lista.get(0);
				Hibernate.initialize(documento.getModeloVersionDTO());
				if (Utilidades.isNotNull(documento.getModeloVersionDTO())){
					Hibernate.initialize(documento.getModeloVersionDTO().getModeloDTO());
				}
				Hibernate.initialize(documento.getSituacionDTO());
			}
			return documento;
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Error al obtener el listado de documentos.", e);
		}
	}



	@SuppressWarnings("unchecked")
	public List<DocumentoDTO> findDocumentosByModeloVersionNumDocumento(
			final String coModelo, final String coVersion,
			final String numDocumento) throws GadirServiceException {
		List<DocumentoDTO> resultado;
		try {
			final HashMap map = new HashMap();
			map.put("coModelo", coModelo);
			map.put("coVersion", coVersion);
			map.put("numDocumento", numDocumento);
			if (Utilidades.isNotEmpty(coModelo)
					&& Utilidades.isNotEmpty(coVersion)
					&& Utilidades.isNotEmpty(numDocumento)) {
				resultado = this.findByNamedQuery(
						QueryName.FIND_DOCUMENTOS_MODELO_VERSION_NUMDOCUMENTO,
						map);
			} else {
				resultado = Collections.EMPTY_LIST;
			}
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error al obtener los documentos filtrados por municipio y concepto",
					e);
		}
		return resultado;
	}


	private List<String> municipioDTOsToLista(List<MunicipioDTO> municipiosUsuario) {
		List<String> municipios = new ArrayList<String>();
		for (MunicipioDTO municipioDTO : municipiosUsuario) {
			municipios.add(municipioDTO.getId().getCoMunicipio());
		}
		return municipios;
	}

	private String municipioDTOsToString(List<MunicipioDTO> municipiosUsuario) {
		StringBuffer municipios = new StringBuffer();
		for (MunicipioDTO municipioDTO : municipiosUsuario) {
			if (municipios.length() > 0) {
				municipios.append(", ");
			}
			municipios.append("('" + municipioDTO.getId().getCoProvincia() + "','" + municipioDTO.getId().getCoMunicipio() + "')");
		}
		return municipios.toString();
	}

	private String codtersToString(List<String> codtersCompatsUsuario) {
		StringBuffer codters = new StringBuffer();
		for (String codter : codtersCompatsUsuario) {
			if (codters.length() > 0) {
				codters.append(", ");
			}
			codters.append("'" + codter + "'");
		}
		return codters.toString();
	}

	@SuppressWarnings("unchecked")
	public List<ResumenDocumentoVO> obtenerResumen(final String coMunicipioParam, final String coProvinciaParam) throws GadirServiceException {
		final List<ResumenDocumentoVO> resultados = new ArrayList<ResumenDocumentoVO>();
		String sqlQuery;
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		if (Utilidades.isEmpty(coMunicipioParam)) {
			sqlQuery = 
				"select count(*), estado, co_provincia, co_municipio, co_concepto, co_modelo, co_version, ejercicio, periodo " +
				"from ga_documento " +
				"where estado != 'B' " + 
				(ControlTerritorial.isUsuarioExperto() ? "" :
					"and ((CO_PROVINCIA is null and CO_MUNICIPIO is null) or ((CO_PROVINCIA, CO_MUNICIPIO) in (" + municipioDTOsToString(ControlTerritorial.getMunicipiosUsuario(false)) + "))) " + 
					"and (CO_CODIGO_TERRITORIAL is null or CO_CODIGO_TERRITORIAL in (" + codtersToString(ControlTerritorial.getCodtersCompatsUsuario(ControlTerritorial.CONSULTA)) + ")) ") + 
					"group by estado, co_provincia, co_municipio, co_concepto, co_modelo, co_version, ejercicio, periodo " +
					"order by co_provincia, co_municipio, co_concepto, co_modelo, co_version, ejercicio, periodo";
		} else {
			sqlQuery = 
				"select count(*), estado, co_provincia, co_municipio, co_concepto, co_modelo, co_version, ejercicio, periodo " +
				"from ga_documento " +
				"where co_provincia = :coProvincia and co_municipio = :coMunicipio and estado != 'B' " +
				(ControlTerritorial.isUsuarioExperto() ? "" :
					"and ((CO_PROVINCIA is null and CO_MUNICIPIO is null) or ((CO_PROVINCIA, CO_MUNICIPIO) in (" + municipioDTOsToString(ControlTerritorial.getMunicipiosUsuario(false)) + "))) " + 
					"and (CO_CODIGO_TERRITORIAL is null or CO_CODIGO_TERRITORIAL in (" + codtersToString(ControlTerritorial.getCodtersCompatsUsuario(ControlTerritorial.CONSULTA)) + ")) ") + 				
					"group by estado, co_provincia, co_municipio, co_concepto, co_modelo, co_version, ejercicio, periodo " +
					"order by co_provincia, co_municipio, co_concepto, co_modelo, co_version, ejercicio, periodo";

			sqlParams.put("coProvincia", coProvinciaParam);
			sqlParams.put("coMunicipio", coMunicipioParam);
		}
		List<Object> sqlResult = (List<Object>) this.getDao().ejecutaSQLQuerySelect(sqlQuery, sqlParams);

		ResumenDocumentoVO resumenDocumentoVO = new ResumenDocumentoVO("", "", "", "", "", "", "");
		for (int i=0; i<sqlResult.size(); i++) {
			Object[] result = (Object[]) sqlResult.get(i);
			// Asignación de valores
			BigDecimal cantidad = (BigDecimal) result[0];
			String estado = (String) result[1];
			String coProvincia = (String) result[2];
			String coMunicipio = (String) result[3];
			String coConcepto = (String) result[4];
			String coModelo = (String) result[5];
			String coVersion = (String) result[6];
			String coPeriodo = (String) result[8];
			// Control de nulos
			if (cantidad == null) cantidad = new BigDecimal(0);
			if (estado == null) estado = "";
			if (coProvincia == null) coProvincia = "";
			if (coMunicipio == null) coMunicipio = "";
			if (coConcepto == null) coConcepto = "";
			if (coModelo == null) coModelo = "";
			if (coVersion == null) coVersion = "";
			if (coProvincia == null) coProvincia = "";
			if (coPeriodo == null) coPeriodo = "";
			// Ejercicio, caso aparte
			String ejercicio = "";
			if (result[7] != null) {
				ejercicio = ((BigDecimal) result[7]).toString();
			}
			// Control si nueva fila
			if (!resumenDocumentoVO.isIgual(coProvincia, coMunicipio, coConcepto, coModelo, coVersion, ejercicio, coPeriodo)) {
				if (resumenDocumentoVO.isNotEmpty()) {
					resultados.add(resumenDocumentoVO);
				}
				resumenDocumentoVO = new ResumenDocumentoVO(coProvincia, coMunicipio, coConcepto, coModelo, coVersion, ejercicio, coPeriodo);
			}
			// Sumamos contadores
			if (Utilidades.isNotEmpty(estado)) {
				switch(estado.charAt(0)) {
				case 'N': resumenDocumentoVO.setCantidadN(Formato.formatearDato(cantidad.toString(),"I",6,"V")); break;
				case 'R': resumenDocumentoVO.setCantidadR(Formato.formatearDato(cantidad.toString(),"I",6,"V")); break;
				case 'I': resumenDocumentoVO.setCantidadI(Formato.formatearDato(cantidad.toString(),"I",6,"V")); break;
				case 'F': resumenDocumentoVO.setCantidadF(Formato.formatearDato(cantidad.toString(),"I",6,"V")); break;
				case 'C': resumenDocumentoVO.setCantidadC(Formato.formatearDato(cantidad.toString(),"I",6,"V")); break;
				case 'L': resumenDocumentoVO.setCantidadL(Formato.formatearDato(cantidad.toString(),"I",6,"V")); break;
				case 'B': resumenDocumentoVO.setCantidadB(Formato.formatearDato(cantidad.toString(),"I",6,"V")); break;
				}
			}
		}
		if (resumenDocumentoVO.isNotEmpty()) {
			resultados.add(resumenDocumentoVO);
		}

		//		List<Object> temporal;
		//		final List<ResumenDocumentoVO> resultados = new ArrayList<ResumenDocumentoVO>();
		//		try {
		//			final HashMap map = new HashMap();
		//			if (coMunicipio != null && !"".equals(coMunicipio)) {
		//				map.put("coMunicipio", coMunicipio);
		//				if(!ValoresParametrosConfig.VALOR_MUNICIPIO_GENERICO.equals(coMunicipio)){
		//					map.put("coProvincia", coProvincia);
		//				}
		//				else{
		//					map.put("coProvincia", ValoresParametrosConfig.VALOR_PROVINCIA_GENERICA);
		//				}
		//				temporal = (List<Object>) this.getDao().findByQueryGenerica(
		//						QueryName.DOCUMENTOS_OBTENER_ESTADISTICAS, map);
		//			} else {
		//				temporal = (List<Object>) this.getDao().findByQueryGenerica(
		//						QueryName.DOCUMENTOS_OBTENER_TODAS_ESTADISTICAS, map);
		//			}
		//			if (temporal != null && !temporal.isEmpty()) {
		//				int i = 0;
		//				int posicion = 0;
		//				while (i < temporal.size()) {
		//					final Object[] atributos = (Object[]) temporal.get(i);
		//					int j = i + 1;
		//					boolean igual = true;
		//					generaListaResumen(resultados, atributos, false, i);
		//					if (j >= temporal.size() - 1) {
		//						i++;
		//					}
		//					while (j < temporal.size() - 1 && igual) {
		//						final Object[] atributosSig = (Object[]) temporal
		//								.get(j);
		//						if (atributosSig[8].equals(atributos[8])
		//								&& atributosSig[1].equals(atributos[1])
		//								&& (atributosSig[2] == null
		//										&& atributos[2] == null || atributosSig[2] != null
		//										&& atributosSig[2].equals(atributos[2]))
		//								&& (atributosSig[3] == null
		//										&& atributos[3] == null || atributosSig[3] != null
		//										&& atributosSig[3].equals(atributos[3]))
		//								&& (atributosSig[4] == null
		//										&& atributos[4] == null || atributosSig[4] != null
		//										&& atributosSig[4].equals(atributos[4]))
		//								&& (atributosSig[7] == null
		//										&& atributos[7] == null || atributosSig[7] != null
		//										&& atributosSig[7].equals(atributos[7]))) {
		//							generaListaResumen(resultados, atributosSig, true,
		//									posicion);
		//							j++;
		//							i = j;
		//						} else {
		//							igual = false;
		//							i++;
		//							posicion++;
		//						}
		//					}
		//				}
		//			}
		//		} catch (final Exception e) {
		//			log.error(e.getCause(), e);
		//			throw new GadirServiceException(
		//					"Ocurrio un error al obtener las estadísticas de los documentos filtrados por municipio",
		//					e);
		//		}
		return resultados;
	}



	@SuppressWarnings("unchecked")
	public List<ResumenDocumentoVO> obtenerResumenEstadoDocumentos(
			String coMunicipio, String coProvincia, String coConcepto,
			String coModelo, String coVersion, String ejercicio,
			String coPeriodo) throws GadirServiceException {

		final List<ResumenDocumentoVO> resultados = new ArrayList<ResumenDocumentoVO>();

		List<MunicipioDTO> listaMunicipiosUsuario = ControlTerritorial.getMunicipiosUsuario(false);
		List<String> listaCodigosTerritoriales = ControlTerritorial.getCodtersCompatsUsuario(ControlTerritorial.CONSULTA);
		List<String> coProvincias = new ArrayList<String>();
		List<String> coMunicipios = new ArrayList<String>();

		for(MunicipioDTO m : listaMunicipiosUsuario) {
			coProvincias.add(m.getId().getCoProvincia());
			coMunicipios.add(m.getId().getCoMunicipio());
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentoDTO.class);

		// Filtro por municipio
		if (!Utilidades.isEmpty(coMunicipio)) {
			criteria.add(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia));
			criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio", coMunicipio));
		}
		else{
			if(!ControlTerritorial.isUsuarioExperto()){
				criteria.add(Restrictions.or(
						Restrictions.in("municipioDTO.id.coProvincia", coProvincias),
						Restrictions.isNull("municipioDTO.id.coProvincia")));
				criteria.add(Restrictions.or(
						Restrictions.in("municipioDTO.id.coMunicipio", coMunicipios),
						Restrictions.isNull("municipioDTO.id.coMunicipio")));
			}
		}

		if(!ControlTerritorial.isUsuarioExperto()){
			criteria.add(Restrictions.or(
					Restrictions.in("codigoTerritorialDTO.coCodigoTerritorial", listaCodigosTerritoriales),
					Restrictions.isNull("codigoTerritorialDTO.coCodigoTerritorial")));
		}

		// Filtro por concepto
		if (!Utilidades.isEmpty(coConcepto)) {
			criteria.add(Restrictions.eq("conceptoDTO.coConcepto", coConcepto));
		}
		// Filtro por modelo
		if (!Utilidades.isEmpty(coModelo)) {
			criteria.add(Restrictions.eq("modeloVersionDTO.id.coModelo", coModelo));
			// Filtro por versión
			if (!Utilidades.isEmpty(coVersion)) {
				criteria.add(Restrictions.eq("modeloVersionDTO.id.coVersion", coVersion));
			}
		}
		//Filtro por ejercicio
		if (!Utilidades.isEmpty(ejercicio)) {
			criteria.add(Restrictions.eq("ejercicio", Short.valueOf(ejercicio)));
		}
		//Filtro por periodo
		if (!Utilidades.isEmpty(coPeriodo)) {
			criteria.add(Restrictions.eq("periodo", coPeriodo));
		}
		//Filtro estado distinto de B
		criteria.add(Restrictions.ne("estado", "B"));

		//Hay que agrupar por varios campos y saber el count

		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("municipioDTO.id.coProvincia"))
				.add(Projections.groupProperty("municipioDTO.id.coMunicipio"))
				.add(Projections.groupProperty("conceptoDTO.coConcepto"))
				.add(Projections.groupProperty("modeloVersionDTO.id.coModelo"))
				.add(Projections.groupProperty("modeloVersionDTO.id.coVersion"))
				.add(Projections.groupProperty("ejercicio"))
				.add(Projections.groupProperty("periodo"))
				.add(Projections.groupProperty("estado"))
				.add(Projections.rowCount()));

		//Hay que ordenar por varios campos
		criteria.addOrder(Order.asc("municipioDTO.id.coProvincia"));
		criteria.addOrder(Order.asc("municipioDTO.id.coMunicipio"));
		criteria.addOrder(Order.asc("conceptoDTO.coConcepto"));
		criteria.addOrder(Order.asc("modeloVersionDTO.id.coModelo"));
		criteria.addOrder(Order.asc("modeloVersionDTO.id.coVersion"));
		criteria.addOrder(Order.asc("ejercicio"));
		criteria.addOrder(Order.asc("periodo"));

		List<Object[]> documentos = findByCriteriaGenerico(criteria);

		ResumenDocumentoVO resumenDocumentoVO =  new ResumenDocumentoVO("", "", "", "", "", "", "");

		String prov = "";
		String munic = "";
		String conc = "";
		String model = "";
		String vers = "";
		String perio = "";
		String ejerc = "";

		for (int i=0; i<documentos.size(); i++){

			Object[] objeto = documentos.get(i);

			//Municipio
			if(objeto[0] != null){
				munic = (String) objeto[1];
				prov = (String) objeto[0];

			}
			else{
				prov = ""; munic = "";
			}

			//Concepto
			conc = (String) objeto[2];
			if(Utilidades.isEmpty(conc))
				conc = "";

			//Modelo/versión
			if(objeto[3] != null){
				model = (String) objeto[3];
				vers = (String) objeto[4];
			}
			else{
				model = ""; vers = "";
			}

			//Ejercicio
			if(objeto[5]!= null){
				ejerc = ((Short) objeto[5]).toString();
			}
			else
				ejerc = "";

			//Periodo
			perio = (String) objeto[6];
			if(Utilidades.isEmpty(perio))
				perio = "";

			// Control si nueva fila
			if (!resumenDocumentoVO.isIgual(prov, munic, conc, model, vers, ejerc, perio)) {
				if (resumenDocumentoVO.isNotEmpty()) {
					resultados.add(resumenDocumentoVO);
				}
				resumenDocumentoVO = new ResumenDocumentoVO(prov, munic, conc, model, vers, ejerc, perio);
			}
			// Sumamos contadores
			if (Utilidades.isNotEmpty(objeto[7].toString())) {
				switch(objeto[7].toString().charAt(0)) {
				case 'N': resumenDocumentoVO.setCantidadN(Formato.formatearDato(objeto[8].toString(),"I",6,"V")); break;
				case 'R': resumenDocumentoVO.setCantidadR(Formato.formatearDato(objeto[8].toString(),"I",6,"V")); break;
				case 'I': resumenDocumentoVO.setCantidadI(Formato.formatearDato(objeto[8].toString(),"I",6,"V")); break;
				case 'F': resumenDocumentoVO.setCantidadF(Formato.formatearDato(objeto[8].toString(),"I",6,"V")); break;
				case 'C': resumenDocumentoVO.setCantidadC(Formato.formatearDato(objeto[8].toString(),"I",6,"V")); break;
				case 'L': resumenDocumentoVO.setCantidadL(Formato.formatearDato(objeto[8].toString(),"I",6,"V")); break;
				case 'B': resumenDocumentoVO.setCantidadB(Formato.formatearDato(objeto[8].toString(),"I",6,"V")); break;
				}
			}
		}
		if (resumenDocumentoVO.isNotEmpty()) {
			resultados.add(resumenDocumentoVO);
		}

		return resultados;
	}




	@Override
	public DAOBase<DocumentoDTO, DocumentoDTOId> getDao() {
		return this.getDocumentoDao();
	}

	// GETTERS AND SETTERS

	/**
	 * Método que devuelve el atributo documentoDao.
	 * 
	 * @return documentoDao.
	 */
	public DAOBase<DocumentoDTO, DocumentoDTOId> getDocumentoDao() {
		return documentoDao;
	}

	/**
	 * Método que establece el atributo documentoDao.
	 * 
	 * @param documentoDao
	 *            El documentoDao.
	 */
	public void setDocumentoDao(
			final DAOBase<DocumentoDTO, DocumentoDTOId> documentoDao) {
		this.documentoDao = documentoDao;
	}


	public DocumentoCasillaValorBO getServicioDocumentoCasilla() {
		return servicioDocumentoCasilla;
	}

	public void setServicioDocumentoCasilla(
			final DocumentoCasillaValorBO servicioDocumentoCasilla) {
		this.servicioDocumentoCasilla = servicioDocumentoCasilla;
	}



	public void auditorias(DocumentoDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		}
		else {
			getDao().save(transientObject);
		}
	}



	public List<DocumentoDTO> documentosEspecificosUnidadUrbana(Long coUnidadUrbana) throws GadirServiceException{
		try{
			final Map<String, Object> params = new HashMap<String, Object>(
					1);
			params.put("coUnidadUrbana", coUnidadUrbana);

			List<DocumentoDTO> lista = (List<DocumentoDTO>)this.getDao().findByNamedQuery(
					QueryName.DOCUMENTOS_ESPECIFICOS_UNIDAD_URBANA, params);
			return lista;
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener los documentos", e);
		}

	}

	public DocumentoDTO findByIdDocumento(DocumentoDTOId idDocumento) throws GadirServiceException{
		DocumentoDTO documento =  findById(idDocumento);

		if(!Hibernate.isInitialized(documento.getMunicipioDTO())){
			Hibernate.initialize(documento.getMunicipioDTO());
		}
		if(!Hibernate.isInitialized(documento.getModeloVersionDTO())){
			Hibernate.initialize(documento.getModeloVersionDTO());
			if(!Hibernate.isInitialized(documento.getModeloVersionDTO().getModeloDTO())){
				Hibernate.initialize(documento.getModeloVersionDTO().getModeloDTO());
			}
		}
		if(!Hibernate.isInitialized(documento.getConceptoDTO())){
			Hibernate.initialize(documento.getConceptoDTO());
		}

		return documento;

	}


	public List<DocumentoDTO> obtenerListadoListasCobratorias(String coProvincia, String coMunicipio, 
			Short ejercicio, String coModelo, String situacion) 
			throws GadirServiceException{
		List<DocumentoDTO> resultado;
		try {
			final HashMap map = new HashMap();
			map.put("coProvincia", coProvincia);
			map.put("coMunicipio", coMunicipio);
			map.put("ejercicio", ejercicio);
			map.put("coModelo", coModelo);
			map.put("coSituacion", situacion);

			resultado = this.findByNamedQuery(
					QueryName.FIND_DOCUMENTOS_LISTAS_COBRATORIAS,
					map);

		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error al obtener los documentos filtrados para las listas Cobratorias",
					e);
		}
		return resultado;
	}

	public List<DocumentoDTO> findByCriterioBusqueda(DetachedCriteria criterio, int pagina, int max) throws GadirServiceException {
		List<DocumentoDTO> lista =  getDocumentoDao().findByCriteria(criterio, pagina, max);
		return lista;
	}

	public int countByCriterioBusqueda(DetachedCriteria criterio) throws GadirServiceException {
		return getDocumentoDao().countByCriteria(criterio);
	}	

	public List<DocumentoDTO> findByCriterioBusqueda(DetachedCriteria criterio) throws GadirServiceException {
		List<DocumentoDTO> lista =  getDocumentoDao().findByCriteria(criterio);
		for(DocumentoDTO dto : lista){
			if(!Hibernate.isInitialized(dto.getDocumentoCensoDTO())){
				Hibernate.initialize(dto.getDocumentoCensoDTO());
			}
			if(!Hibernate.isInitialized(dto.getDocumentoLiquidacionDTO())){
				Hibernate.initialize(dto.getDocumentoLiquidacionDTO());
			}
			if(!Hibernate.isInitialized(dto.getModeloVersionDTO())){
				Hibernate.initialize(dto.getModeloVersionDTO());
			}
			if(!Hibernate.isInitialized(dto.getModeloVersionDTO().getModeloDTO())){
				Hibernate.initialize(dto.getModeloVersionDTO().getModeloDTO());
			}
			if(!Hibernate.isInitialized(dto.getClienteDTO())){
				Hibernate.initialize(dto.getClienteDTO());
			}
			if(!Hibernate.isInitialized(dto.getMunicipioDTO())){
				Hibernate.initialize(dto.getMunicipioDTO());
			}
			if(!Hibernate.isInitialized(dto.getConceptoDTO())){
				Hibernate.initialize(dto.getConceptoDTO());
			}
			if(!Hibernate.isInitialized(dto.getSituacionDTO())){
				Hibernate.initialize(dto.getSituacionDTO());
			}
		}
		return lista;
	}


	@SuppressWarnings("unchecked")
	public List<DocumentoDTO> buscaDocumentoByModeloVersion(String coModelo, String coVersion) throws GadirServiceException{
		List<DocumentoDTO> resultado;
		try {
			final HashMap map = new HashMap();

			map.put("coModelo", coModelo);
			map.put("coVersion", coVersion);

			resultado = this.findByNamedQuery(
					QueryName.DOCUMENTO_BY_MODELO_VERSION,map);

		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error al obtener los documentos filtrados para documentos",
					e);
		}
		return resultado;
	}


	@SuppressWarnings("unchecked")
	public List<DocumentoDTO> buscaDocumentoByRefCatastral(String refCatastral) throws GadirServiceException{
		List<DocumentoDTO> resultado;
		try {
			final HashMap map = new HashMap();

			map.put("refCatastral", refCatastral);

			resultado = this.findByNamedQuery(
					QueryName.DOCUMENTO_BY_REF_CATASTRAL,map);

			return resultado;

		}  catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error al obtener los documentos filtrados para documentos",
					e);
		}
	}


	@SuppressWarnings("unchecked")
	public List<DocumentoDTO> findDocumentosByMunicipioConceptoModeloVersion(
			String coMunicipio, String coConcepto, String coModelo,
			String coVersion) throws GadirServiceException {
		List<DocumentoDTO> resultado;
		try {
			final HashMap map = new HashMap();
			map.put("coMunicipio", coMunicipio);
			map.put("coConcepto", coConcepto);
			map.put("coModelo", coModelo);
			map.put("coVersion", coVersion);

			if (Utilidades.isNotEmpty(coModelo)
					&& Utilidades.isNotEmpty(coVersion)
					&& Utilidades.isNotEmpty(coMunicipio)
					&& Utilidades.isNotEmpty(coConcepto)) {
				resultado = this.findByNamedQuery(
						QueryName.FIND_DOCUMENTOS_MUNICIPIO_CONCEPTO_MODELO_VERSION,
						map);
			} else {
				resultado = Collections.EMPTY_LIST;
			}
		} catch (final GadirServiceException ge) {
			throw ge;
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Ocurrio un error al obtener los documentos filtrados por municipio, concepto, modelo y versión",
					e);
		}
		return resultado;
	}


	public DocumentoDTO findByRowid(String rowid) {

		DocumentoDTO documento = super.findByRowid(rowid);
		if (documento != null) {
			if(!Hibernate.isInitialized(documento.getMunicipioDTO())){
				Hibernate.initialize(documento.getMunicipioDTO());
			}
			if(!Hibernate.isInitialized(documento.getClienteDTO())){
				Hibernate.initialize(documento.getClienteDTO());
			}
		}
		return documento;
	}


	@SuppressWarnings("unchecked")
	public List<DocumentosEstadoVO> findDocumentosByCargaControlRecepcionandEstado(
			Long coCargaControlRecepcion) throws GadirServiceException {

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("coCargaControlRecepcion", coCargaControlRecepcion);

		List<Object[]> result = (List<Object[]>) this
		.getDao()
		.ejecutaNamedQuerySelect(
				"Documento.findDocumentosByCargaControlRecepcionandEstado", params);

		final List<DocumentosEstadoVO> listaResultado = new ArrayList<DocumentosEstadoVO>();


		for (Object[] objeto : result) {
			DocumentosEstadoVO de = new DocumentosEstadoVO();

			de.setEstado((String) objeto[1]);

			de.setEstadoDescripcion(TablaGt.
					getValor(TablaGtConstants.TABLA_ESTADO_DOCUMENTOS, 
							(String) objeto[1], 
							TablaGt.COLUMNA_DESCRIPCION)
			);

			de.setTotalDocumentos(Formato.formatearDato(objeto[0].toString(),"I",6,"V"));
			de.setCoModelo((String) objeto[2]);
			de.setModeloDescripcion(this.modeloDao.findById((String) objeto[2]).getNombre());
			listaResultado.add(de);
		}

		return listaResultado;
	}


	public List<DocumentosEntradaVO> findByCriteriosSeleccionPaginado(
			DetachedCriteria criteria, int porPagina, int page)
			throws GadirServiceException {

		List<DocumentoDTO> documentosEntradaDTOs = findByCriteria(criteria, (page - 1) * porPagina, porPagina);
		List<DocumentosEntradaVO> documentosEntradaVOs = new ArrayList<DocumentosEntradaVO>();
		for (DocumentoDTO documentoDTO : documentosEntradaDTOs) {
			DocumentosEntradaVO documentosEntradaVO = new DocumentosEntradaVO();

			Hibernate.initialize(documentoDTO.getId());
			Hibernate.initialize(documentoDTO.getModeloVersionDTO());
			Hibernate.initialize(documentoDTO.getModeloVersionDTO().getModeloDTO());
			Hibernate.initialize(documentoDTO.getMunicipioDTO());

			documentosEntradaVO.setNumDocumento(
					documentoDTO.getModeloVersionDTO().getId().getCoModelo() + " " + 
					documentoDTO.getModeloVersionDTO().getId().getCoVersion() + " " +
					documentoDTO.getId().getCoDocumento());
			documentosEntradaVO.setEstadoDoc(documentoDTO.getEstado() + "-"
					+ TablaGt.getValor(TablaGtConstants.TABLA_ESTADO_DOCUMENTOS, 
							documentoDTO.getEstado(), 
							TablaGt.COLUMNA_DESCRIPCION));

			documentosEntradaVO.setEstadoDocLetra(documentoDTO.getEstado());

			documentosEntradaVO.setRef_objeto(documentoDTO.getRefObjTributario1());
			documentosEntradaVO.setMunicipio(documentoDTO.getMunicipioDTO().getCodigoDescripcion());

			DocumentoCasillaValorDTO documentoCasillaValorDTO = 
				servicioDocumentoCasilla.findById(
						new DocumentoCasillaValorDTOId(
								documentoDTO.getModeloVersionDTO().getId().getCoModelo(),
								documentoDTO.getModeloVersionDTO().getId().getCoVersion(),
								documentoDTO.getId().getCoDocumento(), 
								(short)10, (short)1));

			if(Utilidades.isNotNull(documentoCasillaValorDTO))
				documentosEntradaVO.setNif_cliente(documentoCasillaValorDTO.getValor());
			else
				documentosEntradaVO.setNif_cliente("");

			documentoCasillaValorDTO = 
				servicioDocumentoCasilla.findById(
						new DocumentoCasillaValorDTOId(
								documentoDTO.getModeloVersionDTO().getId().getCoModelo(),
								documentoDTO.getModeloVersionDTO().getId().getCoVersion(),
								documentoDTO.getId().getCoDocumento(), 
								(short)11, (short)1));

			if(Utilidades.isNotNull(documentoCasillaValorDTO))
				documentosEntradaVO.setNombre_cliente(documentoCasillaValorDTO.getValor());
			else
				documentosEntradaVO.setNombre_cliente("");

			documentosEntradaVOs.add(documentosEntradaVO);
		}
		return documentosEntradaVOs;
	}

	public List<DocumentosCandidatosVO> findByCriteriosSeleccionPaginadoCandidatos(
			DetachedCriteria criteria, int porPagina, int page)
			throws GadirServiceException {

		List<DocumentoDTO> documentosCandidatosDTOs = findByCriteria(criteria, (page - 1) * porPagina, porPagina);
		List<DocumentosCandidatosVO> documentosEntradaVOs = new ArrayList<DocumentosCandidatosVO>();
		for (DocumentoDTO documentoDTO : documentosCandidatosDTOs) {
			DocumentosCandidatosVO documentosEntradaVO = new DocumentosCandidatosVO();

			Hibernate.initialize(documentoDTO.getId());
			Hibernate.initialize(documentoDTO.getModeloVersionDTO());
			Hibernate.initialize(documentoDTO.getMunicipioDTO());

			documentosEntradaVO.setNumDocumento(
					documentoDTO.getModeloVersionDTO().getId().getCoModelo() + " " + 
					documentoDTO.getModeloVersionDTO().getId().getCoVersion() + " " +
					documentoDTO.getId().getCoDocumento());

			DocumentoCasillaValorDTO documentoCasillaValorDTO = 
				servicioDocumentoCasilla.findById(
						new DocumentoCasillaValorDTOId(
								documentoDTO.getModeloVersionDTO().getId().getCoModelo(),
								documentoDTO.getModeloVersionDTO().getId().getCoVersion(),
								documentoDTO.getId().getCoDocumento(), 
								(short)10, (short)1));

			if(Utilidades.isNotNull(documentoCasillaValorDTO))
				documentosEntradaVO.setNif_cliente(documentoCasillaValorDTO.getValor());
			else
				documentosEntradaVO.setNif_cliente("");

			documentoCasillaValorDTO = 
				servicioDocumentoCasilla.findById(
						new DocumentoCasillaValorDTOId(
								documentoDTO.getModeloVersionDTO().getId().getCoModelo(),
								documentoDTO.getModeloVersionDTO().getId().getCoVersion(),
								documentoDTO.getId().getCoDocumento(), 
								(short)11, (short)1));

			if(Utilidades.isNotNull(documentoCasillaValorDTO))
				documentosEntradaVO.setNombre_cliente(documentoCasillaValorDTO.getValor());
			else
				documentosEntradaVO.setNombre_cliente("");

			if (documentoDTO.getMunicipioDTO() != null) {
				documentosEntradaVO.setMunicipio(documentoDTO.getMunicipioDTO().getCodigoDescripcion());
			}

			documentosEntradaVOs.add(documentosEntradaVO);
		}
		return documentosEntradaVOs;
	}

	@SuppressWarnings("unchecked")
	public List<DocumentoDTO> findCensoPorTramoCategoria(String coMunicipio, String co_concepto, String co_modelo, String co_version, String cat, boolean ordenar, int posDesde, int posHasta )throws GadirServiceException {
		try{
			String sql = 
			" select * from ( " +
				"select co_modelo, co_version, co_documento, rownum r2 from (" +
					"select min(rownum) r, co_modelo, co_version, co_documento, co_municipio, nombre_calle from (" +
						"select desde, hasta, nu_orden, calleTramo5_.tipo, numero, sentido, this_.co_modelo, this_.co_version, this_.co_documento, this_.co_municipio, cal4_.nombre_calle from GA_DOCUMENTO this_ " + 
							"inner join GA_CLIENTE cliente6_ on this_.CO_CLIENTE=cliente6_.CO_CLIENTE " +
							"inner join GA_DOMICILIO dom2_ on this_.CO_DOMICILIO=dom2_.CO_DOMICILIO " +
							"inner join GA_UNIDAD_URBANA urb3_ on dom2_.CO_UNIDAD_URBANA=urb3_.CO_UNIDAD_URBANA " +
							"inner join GA_CALLE cal4_ on urb3_.CO_CALLE=cal4_.CO_CALLE " +
							"inner join GA_CALLE_TRAMO_CATEGORIA calletramo5_ on urb3_.CO_CALLE=calletramo5_.CO_CALLE " +
							"inner join GA_DOCUMENTO_CENSO censo1_ on " +
								"this_.CO_MODELO=censo1_.CO_MODELO " +
								"and this_.CO_VERSION=censo1_.CO_VERSION " +
								"and this_.CO_DOCUMENTO=censo1_.CO_DOCUMENTO " +
							"where urb3_.co_calle=calletramo5_.co_calle and this_.CO_CODIGO_TERRITORIAL " +
								"= 'AY" +coMunicipio.substring(3,5)+coMunicipio.substring(3,5)+"' " +
								"and this_.CO_PROVINCIA= '" +coMunicipio.substring(0, 2)+"' " +
								"and this_.CO_MUNICIPIO= '" +coMunicipio.substring(2)+"' " +
								"and this_.CO_CONCEPTO='" +co_concepto+"' " +
								"and this_.CO_MODELO='" +co_modelo+"' " +
								"and this_.CO_VERSION='" +co_version+"' " +
								"and this_.ESTADO='" +EstadoConstants.EN_BASE_DE_DATOS+"' " +
								"and this_.CO_SITUACION='" +SituacionConstants.CO_SITUACION_ALTA_EN_CENSO+"' " +
								"and censo1_.BO_ACTIVO=1 " +
								"and calleTramo5_.CATEGORIA='" +cat+"' " +
								"HAVING numero between desde and hasta " +
												"and urb3_.NUMERO<=calleTramo5_.HASTA " +
												"and urb3_.NUMERO>=calleTramo5_.DESDE " +
												"and (calleTramo5_.TIPO= 'A' or ((MOD(urb3_.numero,2)<>0 " +
												"and calleTramo5_.tipo='I') OR (MOD(urb3_.numero,2)=0 " +
												"and calleTramo5_.tipo='P')))" +
												"GROUP BY desde, hasta, nu_orden, calleTramo5_.tipo, numero, sentido, this_.co_modelo, this_.co_version, this_.co_documento, this_.co_municipio, cal4_.nombre_calle " +
												"ORDER BY calleTramo5_.nu_orden ASC, CASE WHEN calleTramo5_.sentido = 'A' THEN urb3_.numero END ASC, CASE WHEN calleTramo5_.sentido = 'D' THEN urb3_.numero END DESC, this_.co_modelo, this_.co_version, this_.co_documento) " +
												"GROUP BY co_modelo, co_version, co_documento, co_municipio, nombre_calle " +
												"ORDER BY r ASC, co_modelo, co_version, co_documento)) where r2 between '" +posDesde+ "' and '" +posHasta+ "' ";
			
			
			List<Object[]> result = (List<Object[]>) this.getDao().ejecutaSQLQuerySelect(sql);
			List<DocumentoDTO> documentos = new ArrayList<DocumentoDTO>();

			for (Object[] objeto : result){	
				//las posiciones 1, 2 y 3 que corresponden al modelo, versión y documento respectivamente la 0 corresponde al rownum
				//DocumentoDTO doc = documentoDao.findById(new DocumentoDTOId((objeto[1] != null) ? objeto[1].toString():"", (objeto[2] != null) ? objeto[2].toString():"", (objeto[3] != null) ? objeto[3].toString():""));
				DocumentoDTO doc = documentoDao.findById(new DocumentoDTOId((objeto[0] != null) ? objeto[0].toString():"", (objeto[1] != null) ? objeto[1].toString():"", (objeto[2] != null) ? objeto[2].toString():""));

				documentos.add(doc);
			}
			return documentos;
			
		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
					"Error al obtener el censo.", e);
		}
	}

	public DAOBase<ModeloDTO, String> getModeloDao() {
		return modeloDao;
	}

	public void setModeloDao(DAOBase<ModeloDTO, String> modeloDao) {
		this.modeloDao = modeloDao;
	}


}
