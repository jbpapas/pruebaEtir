/**
 * 
 */
package es.dipucadiz.etir.comun.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import es.dipucadiz.etir.comun.bo.CalleBO;
import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.bo.HCalleBO;
import es.dipucadiz.etir.comun.dao.DAOBase;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dao.QueryName;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.CalleSinonimoDTO;
import es.dipucadiz.etir.comun.dto.CalleSinonimoDTOId;
import es.dipucadiz.etir.comun.dto.CalleUbicacionDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb04.action.G4215Callejero.G4215CalleVO;
import es.dipucadiz.etir.sb07.comun.vo.ViasVO;

public class CalleBOImpl extends AbstractGenericBOImpl<CalleDTO, Long> implements CalleBO {

	private static final long serialVersionUID = -6392092953835819518L;

	private static final Log LOG = LogFactory.getLog(CalleBOImpl.class);
	
	private DAOBase<CalleDTO, Long> dao;
	
	private HCalleBO hcalleBO;

	public DAOBase<CalleDTO, Long> getDao() {
		return dao;
		
	
	}

	public void setDao(final DAOBase<CalleDTO, Long> dao) {
		LOG.trace("setDao: " + dao.getClass().getName());
		this.dao = dao;
	}

	public List<CalleDTO> findByCriteria(String provincia, String municipio, String calle, int firstResult, int maxResults) throws GadirServiceException{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CalleDTO.class);
		String crit = "%"+calle+"%";
		
		criteria.add(Restrictions.eq("municipioDTO.id.coProvincia",provincia));
		criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio",municipio));
		if(Utilidades.isNotEmpty(calle))
			criteria.add(Restrictions.ilike("nombreCalle", crit));
        
		List<CalleDTO> lista=this.dao.findByCriteria(criteria,firstResult,maxResults);
		
		return lista;
		
	}

	public CalleDTO findByIdFetch(final String coCalle) throws GadirServiceException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		try {
			parametros.put("id", new Long(coCalle));
			return findByNamedQuery(QueryName.CALLE_FINDBYIDFETCH, parametros)
					.get(0);
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener la calle seleccionada", e);
		}	
	}
	public CalleDTO findCalleById(final Long coCalle) throws GadirServiceException {
		try {
			CalleDTO result = findById(coCalle);
			if (result != null && result.getMunicipioDTO() != null){
				Hibernate.initialize(result.getMunicipioDTO());
				if (result.getMunicipioDTO().getProvinciaDTO() != null){
					Hibernate.initialize(result.getMunicipioDTO().getProvinciaDTO());
				}
				
			}
			return result;
					
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al obtener la calle seleccionada", e);
		}	
	}
	
	public List<CalleDTO> findByMunicipioAndLikeNombre(MunicipioDTO municipioDTO, String nombreCalle) throws GadirServiceException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("coMunicipio", municipioDTO.getId().getCoMunicipio());
		params.put("coProvincia", municipioDTO.getId().getCoProvincia());
		params.put("nombreCalle", nombreCalle);
		return dao.findByNamedQuery("Calle.findCallesByMunicipioAndLikeNombreCalle", params);
	}
	
	public List<CalleDTO> findByMunicipioAndLikeCalle(MunicipioDTO municipioDTO, String nombreCalle) throws GadirServiceException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("coMunicipio", municipioDTO.getId().getCoMunicipio());
		params.put("coProvincia", municipioDTO.getId().getCoProvincia());
		params.put("nombreCalle", nombreCalle);
		return dao.findByNamedQuery("Calle.findCallesByMunicipioAndLikeCalle", params);
	}
	
	public List<CalleDTO> findByMunicipioSiglaAndLikeNombre(MunicipioDTO municipioDTO,String sigla, String nombreCalle) throws GadirServiceException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("coMunicipio", municipioDTO.getId().getCoMunicipio());
		params.put("coProvincia", municipioDTO.getId().getCoProvincia());
		params.put("nombreCalle", nombreCalle);
		params.put("sigla", sigla);
		return dao.findByNamedQuery("Calle.findCallesByMunicipioSiglaAndLikeNombreCalle", params);
	}
	
	/*public long countByMunicipioAndLikeNombre(MunicipioDTO municipioDTO, String nombreCalle) throws GadirServiceException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("coMunicipio", municipioDTO.getId().getCoMunicipio());
		params.put("coProvincia", municipioDTO.getId().getCoProvincia());
		params.put("nombreCalle", nombreCalle);
		Long resultado= (Long)dao.findByNamedQueryUniqueResult("Calle.countCallesByMunicipioAndLikeNombreCalle", params);
		return resultado.longValue();
	}*/
	
	public CalleDTO findByMunicipioSiglaNombre(MunicipioDTO municipioDTO, String sigla, String nombreCalle) throws GadirServiceException{
		CalleDTO calleDTO=null;
		List<CalleDTO> lista = dao.findFiltered(new String[]{"municipioDTO","sigla","nombreCalle"}, new Object[]{municipioDTO, sigla, nombreCalle}, "nombreCalle", DAOConstant.ASC_ORDER);
		if (lista!=null && !lista.isEmpty()){
			calleDTO=lista.iterator().next();
		}
		return calleDTO;
	}
	
	public CalleDTO findByMunicipioSiglaNombreUbicacion(MunicipioDTO municipioDTO, String sigla, String nombreCalle, CalleUbicacionDTO calleUbicacionDTO) throws GadirServiceException{
		CalleDTO calleDTO=null;
		
		final DetachedCriteria criteria = DetachedCriteria.forClass(CalleDTO.class);		
		criteria.add(Restrictions.eq("municipioDTO.id.coProvincia",municipioDTO.getId().getCoProvincia()));
		criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio",municipioDTO.getId().getCoMunicipio()));
		criteria.add(Restrictions.eq("sigla",sigla));
		criteria.add(Restrictions.eq("nombreCalle",nombreCalle));
		if (calleUbicacionDTO == null) {
			criteria.add(Restrictions.isNull("calleUbicacionDTO"));
		} else {
			criteria.add(Restrictions.eq("calleUbicacionDTO",calleUbicacionDTO));
		}
		
		List<CalleDTO> lista=this.dao.findByCriteria(criteria);
		
		if (lista!=null && !lista.isEmpty()){
			calleDTO=lista.iterator().next();
		}
		return calleDTO;
	}
	
	
	public CalleDTO findByMunicipioCoMunicipal(MunicipioDTO municipioDTO, Integer coMunicipal) throws GadirServiceException{
		CalleDTO calleDTO=null;
		List<CalleDTO> lista = dao.findFiltered(new String[]{"municipioDTO","coMunicipal"}, new Object[]{municipioDTO, coMunicipal}, "nombreCalle", DAOConstant.ASC_ORDER);
		if (lista!=null && !lista.isEmpty()){
			calleDTO=lista.iterator().next();
		}
		return calleDTO;
	}
	
	public List<CalleDTO> findByMunicipio(MunicipioDTO municipioDTO) throws GadirServiceException{
		List<CalleDTO> lista = dao.findFiltered(new String[]{"municipioDTO"}, new Object[]{municipioDTO}, "nombreCalle", DAOConstant.ASC_ORDER);
		return lista;
	}
	
	public List<CalleDTO> findByMunicipioSigla(MunicipioDTO municipioDTO, String sigla) throws GadirServiceException{
		List<CalleDTO> lista = dao.findFiltered(new String[]{"municipioDTO","sigla"}, new Object[]{municipioDTO,sigla});
		return lista;
	}
	
	public void auditorias(CalleDTO transientObject, Boolean saveOnly) throws GadirServiceException {
		// Se modifica la auditoría de la entidad pasada de esta tabla
		transientObject.setFhActualizacion(Utilidades.getDateActual());
		transientObject.setCoUsuarioActualizacion(DatosSesion.getLogin());
		
		if (saveOnly) {
			getDao().saveOnly(transientObject);
		} else {
			getDao().save(transientObject);
		}
	}
	
	
	public CalleDTO existeCalle(CalleDTO calle) throws GadirServiceException {
		CalleDTO result = null;
		List<CalleDTO> lista = dao.findFiltered(new String[]{"municipioDTO","sigla","nombreCalle"}, 
				new Object[]{calle.getMunicipioDTO(),calle.getSigla(),calle.getNombreCalle()});	
		if (lista.size()>0){
			result = lista.get(0);
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public Integer findByParamsPorMunicipio(String coProvincia,String coMunicipio,String codSigla, String nombreVia, Integer pagina,
	        List<ViasVO> vias, Integer limite)
	        throws GadirServiceException {

		Integer totalPaginas = 0;
		String sqlQuery = "";
		
		if(Utilidades.isEmpty(codSigla)){
			sqlQuery = "select * from (select rownum as r, x.* from (select cl.co_calle, cl.nombre_calle, ub.ubicacion, cl.sigla, md.cp_generico "
		        + "from ga_calle cl left outer join ga_calle_ubicacion ub on ub.co_calle_ubicacion = cl.co_calle_ubicacion "
		        + "left outer join ga_municipio_datos md on md.co_provincia = cl.co_provincia and md.co_municipio = cl.co_municipio "
		        + "where cl.co_Municipio = '"
		        + coMunicipio + "'" 
		        + " and cl.co_provincia = '"
		        + coProvincia + "'";
		}
		else{
			sqlQuery = "select * from (select rownum as r, x.* from (select cl.co_calle, cl.nombre_calle, ub.ubicacion, cl.sigla, md.cp_generico "
		        + "from ga_calle cl left outer join ga_calle_ubicacion ub on ub.co_calle_ubicacion = cl.co_calle_ubicacion "	
		        + "left outer join ga_municipio_datos md on md.co_provincia = cl.co_provincia and md.co_municipio = cl.co_municipio "
		        + "where cl.co_Municipio = '"
		        + coMunicipio + "' and cl.sigla = '" + codSigla+"'"
		        + " and cl.co_provincia = '"
		        + coProvincia + "'";
		}
		if(Utilidades.isNotEmpty(nombreVia))
		{
			sqlQuery += " and cl.nombre_calle like UPPER('" + nombreVia + "%')";
		}
		
		try {
			Integer inicio = pagina - (limite - 1);
			sqlQuery = sqlQuery
			        + " order by cl.nombre_calle)x) where r between "
			        + inicio + " and " + pagina;
			List<Object[]> result = (List<Object[]>) this.getDao()
			        .ejecutaSQLQuerySelect(sqlQuery);
			for (Object[] objeto : result) {
				ViasVO cli = new ViasVO();
				if (objeto[1] != null) {
					cli.setCoCalle(new Long(objeto[1].toString()));
				}
				cli.setNombreVia((((String) objeto[2] != null) ? (String) objeto[2]: ""));
				cli.setUbicacion((((String) objeto[3] != null) ? (String) objeto[3]: ""));
				cli.setSigla((((String) objeto[4] != null) ? (String) objeto[4]: ""));
				cli.setCp((((BigDecimal) objeto[5] != null) ? new Integer(((BigDecimal) objeto[5]).toString()): null));
				vias.add(cli);
			}
			
			if(Utilidades.isEmpty(codSigla)){
				sqlQuery = "select count(*) " + "from ga_calle cl"
			        + " left outer join ga_calle_ubicacion ub on ub.co_calle_ubicacion = cl.co_calle_ubicacion " 
		        + "where cl.co_Municipio = '"
		        + coMunicipio + "'";
			}else{
				sqlQuery = "select count(*) " + "from ga_calle cl"
		        + " left outer join ga_calle_ubicacion ub on ub.co_calle_ubicacion = cl.co_calle_ubicacion " 
		        + "where cl.co_Municipio = '"
	        	+ coMunicipio + "' and cl.sigla = '" + codSigla+"'";
			}
			if(Utilidades.isNotEmpty(nombreVia))
			{
				sqlQuery += " and cl.nombre_calle like UPPER('" + nombreVia + "%')";
			}
			
			List<BigDecimal> resultado = (List<BigDecimal>) this.getDao()
			        .ejecutaSQLQuerySelect(sqlQuery);
			for (BigDecimal objeto : resultado) {
				totalPaginas = new Integer(objeto.toString());
			}

		} catch (final Exception e) {
			log.error(e.getCause(), e);
			throw new GadirServiceException(
			        "Error al obtener el listado de calles.", e);
		}

		return totalPaginas;
	}
	
	public Criterion getRestrictionNombreCalle(String nombreCalle, int modo, String prefijoGaCalle, boolean buscarEnSinonimos) {
		Criterion result;
		// Que exista alguna calle...
		String filtro = getSqlRestrictionCalle("nombre_calle", nombreCalle, modo);
		Criterion lhs = Restrictions.sqlRestriction(filtro);
		// ...o que exista algún sinonimo.
		if (buscarEnSinonimos) {
			DetachedCriteria otro = DetachedCriteria.forClass(CalleSinonimoDTO.class, "sin");
			otro.add(Restrictions.eqProperty(prefijoGaCalle + "coCalle", "sin.id.coCalle"));
			String filtroSinonimo = filtro.replace("nombre_calle", "sinonimo");
			otro.add(Restrictions.sqlRestriction(filtroSinonimo));
			otro.setProjection(Projections.property("id.sigla"));
			otro.setProjection(Projections.property("id.sinonimo"));
			Criterion rhs = Subqueries.exists(otro);
			result = Restrictions.or(lhs, rhs);
		} else {
			result = lhs;
		}
		
		return result;
	}

	private String getSqlRestrictionCalle(String campo, String valor, int modo) {
		String pre = (modo == CONTIENE ? "%" : "");
		String post = (modo == EXACTO ? "" : "%");
		return "translate(UPPER("+campo+"),'ÁÉÍÓÚÖ', 'AEIOUO') like " + "'" + pre + valor + post + "'";
	}
	
	public List<G4215CalleVO> getCalleVOs(List<CalleDTO> calleDTOs, String nombreCalle, GenericBO<CalleSinonimoDTO, CalleSinonimoDTOId> calleSinonimoBO) throws GadirServiceException{
		List<G4215CalleVO> g4215CalleVOs = new ArrayList<G4215CalleVO>();
    	
    	for(CalleDTO calleDTO : calleDTOs){

    		G4215CalleVO calleVO = new G4215CalleVO();
    		calleDTO = findByIdFetch(calleDTO.getCoCalle().toString());

			if (calleDTO.getSigla()!=null){
				calleVO.setSigla(TablaGt.getCodigoDescripcion(TablaGtConstants.TABLA_TIPO_VIA_PUBLICA, calleDTO.getSigla()).getCodigoDescripcion());
			}
			
			if (calleDTO.getNombreCalle()!=null){
				calleVO.setNombreCalle(calleDTO.getNombreCalle());
			}	

			if (calleDTO.getCoCalle()!=null){
				calleVO.setCoCalle(calleDTO.getCoCalle());
			}
			
			if (calleDTO.getCalleUbicacionDTO()!=null){
				calleVO.setUbicacion(calleDTO.getCalleUbicacionDTO().getUbicacion());
			}
			else
				calleVO.setUbicacion("");
			
			if (calleDTO.getCoMunicipal()!=null){
				calleVO.setCoMunicipal(""+calleDTO.getCoMunicipal());
			} else {
				calleVO.setCoMunicipal("");
			}
			
			if (Utilidades.isNotEmpty(nombreCalle) && !calleDTO.getNombreCalle().contains(nombreCalle)) {
				DetachedCriteria criteria = DetachedCriteria.forClass(CalleSinonimoDTO.class);
				criteria.add(Restrictions.eq("id.coCalle", calleDTO.getCoCalle()));
				criteria.add(Restrictions.sqlRestriction(getSqlRestrictionCalle("sinonimo", nombreCalle, CONTIENE)));
				criteria.add(Restrictions.sqlRestriction("ROWNUM=1"));
				List<CalleSinonimoDTO> calleSinonimoDTOs = calleSinonimoBO.findByCriteria(criteria, 0, 1);
				if (!calleSinonimoDTOs.isEmpty()) {
					CalleSinonimoDTO calleSinonimoDTO = calleSinonimoDTOs.get(0);
					calleVO.setNombreCalle(calleVO.getNombreCalle() + " (sinónimo " + calleSinonimoDTO.getId().getSigla() + " " + calleSinonimoDTO.getId().getSinonimo() + ")");
				}
			}
			
			g4215CalleVOs.add(calleVO);
		}
    	
    	return g4215CalleVOs;
	}
	
	
	public CalleDTO nuevaCalle(CalleDTO calle) throws GadirServiceException{
		try {
			this.saveOnly(calle);
			hcalleBO.guardarHCalle(calle, "A");
			return calle;
		} catch (final Exception e) {
			throw new GadirServiceException(
					"Ocurrio un error al registrar la nueva calle", e);
		}	
	}

	/**
	 * @return the hcalleBO
	 */
	public HCalleBO getHcalleBO() {
		return hcalleBO;
	}

	/**
	 * @param hcalleBO the hcalleBO to set
	 */
	public void setHcalleBO(HCalleBO hcalleBO) {
		this.hcalleBO = hcalleBO;
	}	
	
	
}
