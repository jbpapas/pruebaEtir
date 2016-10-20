package es.dipucadiz.etir.comun.action.ayudaCasilla;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.CalleBO;
import es.dipucadiz.etir.comun.bo.CalleTramoPostalBO;
import es.dipucadiz.etir.comun.bo.CalleUbicacionBO;
import es.dipucadiz.etir.comun.bo.ConceptoBO;
import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.bo.MunicipioDatosBO;
import es.dipucadiz.etir.comun.bo.MunicipioSinonimoBO;
import es.dipucadiz.etir.comun.bo.ProvinciaBO;
import es.dipucadiz.etir.comun.bo.ProvinciaSinonimoBO;
import es.dipucadiz.etir.comun.displaytag.GadirPaginatedList;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.CalleSinonimoDTO;
import es.dipucadiz.etir.comun.dto.CalleSinonimoDTOId;
import es.dipucadiz.etir.comun.dto.CalleTramoCategoriaDTO;
import es.dipucadiz.etir.comun.dto.CalleTramoCategoriaDTOId;
import es.dipucadiz.etir.comun.dto.CalleTramoPostalDTO;
import es.dipucadiz.etir.comun.dto.CalleUbicacionDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDatosDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDatosDTOId;
import es.dipucadiz.etir.comun.dto.MunicipioSinonimoDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaSinonimoDTO;
import es.dipucadiz.etir.comun.dto.ValidacionArgumentoDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.MunicipioConceptoModeloUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.sb04.action.G4215Callejero.G4215CalleVO;

final public class AyudaCalleAction extends AyudaCasillaAction {

	private static final long serialVersionUID = 2030159311207134260L;
	private static final Log LOG = LogFactory.getLog(AyudaCalleAction.class);
	
	protected GadirPaginatedList<G4215CalleVO> listaCalles;
	List<G4215CalleVO> listaCallesVO;
	protected List<CalleDTO> lista;
	protected CalleBO calleBO;
	protected MunicipioBO municipioBO;
	protected CalleTramoPostalBO calleTramoPostalBO;
	protected GenericBO<CalleTramoCategoriaDTO, CalleTramoCategoriaDTOId> calleTramoCategoriaBO;
	protected MunicipioDatosBO municipioDatosBO;
	protected CalleUbicacionBO calleUbicacionBO;
	protected ProvinciaBO provinciaBO;
	protected ConceptoBO conceptoBO;
	protected ProvinciaSinonimoBO provinciaSinonimoBO;
	protected MunicipioSinonimoBO municipioSinonimoBO;
	protected GenericBO<CalleSinonimoDTO, CalleSinonimoDTOId> calleSinonimoBO;
	protected String calle;
	protected String coCalle;
	protected String numDomicilio="";
	protected String cpGeneral="";
	protected boolean callejeroM=false;
	protected boolean conUbicacion=false;
	
	// Variables para el display tag
	private String sort;
	private String dir;
	private int page = 1;
	private int porPagina = 15;
	private String nombrePestana;
	private String coConcepto;

	public String execute() throws GadirServiceException
	{
		try
		{
			String coMunicipio="";
			String descProvincia="";
			String descMunicipio="";
	
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
					case 6: numDomicilio = valor; break;
					case 9: descProvincia = valor; break;
					case 10: descMunicipio = valor; break;
					case 11: coConcepto = valor; break;
					}
				}
				
				if (Utilidades.isEmpty(coMunicipio) || coMunicipio.length()!=5){
					if(Utilidades.isEmpty(descMunicipio))
						addActionError(Mensaje.getTexto(MensajeConstants.CODIGO_DE_MUNICIPIO_INEXISTENTE));
					else {
						coMunicipio = obtenerMunicipio(descProvincia, descMunicipio);
						if(Utilidades.isEmpty(coMunicipio) || coMunicipio.length()!=5)
							addActionError(Mensaje.getTexto(MensajeConstants.CODIGO_DE_MUNICIPIO_INEXISTENTE));
					}
				} else {
					descMunicipio = MunicipioConceptoModeloUtil.getMunicipioDescripcion(coMunicipio);
				}				
				
				nombrePestana = descMunicipio;
				
				if(!Utilidades.isEmpty(numDomicilio) && !Utilidades.isNumeric(numDomicilio))
					addActionError("El número de vía es incorrecto");
				
				if (!hasErrors())
				{
					MunicipioDatosDTO md = municipioDatosBO.findById(new MunicipioDatosDTOId(coMunicipio.substring(0, 2), coMunicipio.substring(2, 5)));
					
					//Si el municipio tiene callejero municipal
					if (md.getBoCallejeroMunicipal()!= null)
							if (md.getBoCallejeroMunicipal()== true)
								callejeroM = true;
					
					if(md.getCpGenerico()!=null)
						cpGeneral = md.getCpGenerico().toString();
					
					//Vemos si hay alguna ubicacxion en el municipio
					int ubicaciones = calleUbicacionBO.countByCriteria(getCriteriaUbicacion(coMunicipio.substring(0, 2), coMunicipio.substring(2, 5)));
					
					if(ubicaciones >0)
						conUbicacion = true;
						
					lista = calleBO.findByCriteria(getCriteria(true, callejeroM,coMunicipio.substring(0, 2), coMunicipio.substring(2, 5)), (page - 1) * porPagina, porPagina);
					int total = calleBO.countByCriteria(getCriteria(false, callejeroM, coMunicipio.substring(0, 2), coMunicipio.substring(2, 5)));
					
					obtenerListaCalles();
					listaCalles = new GadirPaginatedList<G4215CalleVO>(listaCallesVO, total, porPagina, page, "calles", sort, dir);
					
				}
			}catch(Exception e)
			{
				addActionError(e.getMessage());
				LOG.error("Error obteniendo la ayuda de calles", e);
		}
			
		return INPUT;
	}

	private void obtenerListaCalles() throws GadirServiceException{
		listaCallesVO = calleBO.getCalleVOs(lista, calle == null ? calle : calle.toUpperCase(), calleSinonimoBO);
    }
	
	private DetachedCriteria getCriteria(boolean ordenar,boolean callejero, String codProvincia, String codMunicipio) throws GadirServiceException{
		// Filtro por municipio
		final DetachedCriteria criteria = DetachedCriteria.forClass(CalleDTO.class, "cal");
		
		criteria.add(Restrictions.eq("municipioDTO.id.coProvincia",codProvincia));
		criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio",codMunicipio));
		
		//Filtro por nombre de calle
		if(Utilidades.isNotEmpty(calle))
		{
			criteria.add(calleBO.getRestrictionNombreCalle(calle.toUpperCase(), CalleBO.CONTIENE, "cal.", true));
		}
        		
		//Si tiene callejero municipal, mostramos las calles que tienen co_municipal
		if(callejero)
		{
			criteria.add(Expression.isNotNull("coMunicipal"));
			criteria.add(Restrictions.ne("coMunicipal", new Integer(0)));
		}
		
		// Ordenacion
		if (ordenar) {
			if (Utilidades.isEmpty(sort)) {
				setSort("nombreCalle");
				setDir(GadirPaginatedList.ASCENDING);
			}
			if ("sigla".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("sigla"));
				} else {
					criteria.addOrder(Order.asc("sigla"));
				}
			} else if ("nombreCalle".equals(sort)) {
				if (GadirPaginatedList.DESCENDING.equals(dir)) {
					criteria.addOrder(Order.desc("nombreCalle"));
				} else {
					criteria.addOrder(Order.asc("nombreCalle"));
				}
			}
		}
		return criteria;
	}
	
	private DetachedCriteria getCriteriaUbicacion(String codProvincia, String codMunicipio) throws GadirServiceException{
		// Filtro por municipio
		final DetachedCriteria criteria = DetachedCriteria.forClass(CalleUbicacionDTO.class);
		
		criteria.add(Restrictions.eq("municipioDTO.id.coProvincia",codProvincia));
		criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio",codMunicipio));
		
		return criteria;
	}
	
	public String botonSeleccionar() throws GadirServiceException {
		if(!Utilidades.isEmpty(calle))
		{
			String calleaux = getCalle();
			calleaux = calleaux.replace ('à','a');
			calleaux = calleaux.replace ('é','e');
			calleaux = calleaux.replace ('í','i');
			calleaux = calleaux.replace ('ó','o');
			calleaux = calleaux.replace ('ú','u'); 
			
			setCalle(calleaux.toLowerCase());
		}
		
		return execute();
	}
	
	public String ajaxBotonSeleccionar() {
		ajaxData="";
		
		try{
			CalleDTO calleDTO = calleBO.findByIdInitialized(Long.parseLong(coCalle), new String[] {"calleUbicacionDTO"});
			String categoria = "";
			String cp = "";
			
			List<ValidacionArgumentoDTO> argumentos=validacionArgumentoBO.findArgumentos(Long.valueOf(DatosSesion.getAyudaCasillaVO().getCoValidacion()));

			for (ValidacionArgumentoDTO validacionArgumentoDTO : argumentos){

				switch (validacionArgumentoDTO.getId().getCoArgumentoFuncion()){
					case 3:	
						DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), calleDTO.getSigla());
						break;
					case 4:	
						DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), calleDTO.getNombreCalle());
						break;
					case 5:	
						String valor;
						if (Utilidades.isEmpty(calleDTO.getCoMunicipal())) {
							valor = "";
						} else {
							valor = calleDTO.getCoMunicipal().toString();
						}
						DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), valor);
						break;	
					
					case 7:
							if(!Utilidades.isEmpty(coConcepto))
							{
								List<CalleTramoCategoriaDTO> tramos;
								if(calleTramoCategoriaBO.findByCriteria(getCriterioTramoConceptoCategoria(calleDTO.getMunicipioDTO(), coConcepto)).isEmpty()){
									tramos = calleTramoCategoriaBO.findByCriteria(getCriterioTramoCategoria("****",calleDTO,numDomicilio));
								}
								else{
									tramos = calleTramoCategoriaBO.findByCriteria(getCriterioTramoCategoria(coConcepto,calleDTO,numDomicilio));
								}
								
								if(tramos != null && tramos.size() > 0)
								{
									if(tramos.size() == 1) //Sólo hay uno, se lo asignamos
										categoria = tramos.get(0).getCategoria();
									else //hay más de uno, no asignamos categoria
										categoria = "";
								}
								else
									categoria="0"; //No existen tramos
								}
							else
								categoria="";
							DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), categoria);
							
						break;
					case 8:
							List<CalleTramoPostalDTO> tramosCp = calleTramoPostalBO.findByCriteria(getCriterioTramoPostal(calleDTO,numDomicilio));
							
							if(tramosCp != null && tramosCp.size() > 0)
							{
								if(!numDomicilio.isEmpty())
								{
									if(tramosCp.size() == 1) //Sólo hay uno, se lo asignamos
											cp = tramosCp.get(0).getCp().toString();
									else
									{
										for(CalleTramoPostalDTO tramo:tramosCp)
										{
											if(Integer.parseInt(numDomicilio) >= tramo.getId().getDesde() && Integer.parseInt(numDomicilio) <= tramo.getHasta())
												cp = tramo.getCp().toString();
										}
									}
								}
								else
								{
									if(tramosCp.size() == 1) //Sólo hay uno, se lo asignamos
										cp = tramosCp.get(0).getCp().toString();
								}
							}
							
							//Si no hay CP para el domicilio, le asignamos el CP generico del municipio
							if(cp.isEmpty())
								cp = cpGeneral;
							
							DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), cp);
						
						break;
					case 12:
						DatosSesion.getAyudaCasillaVO().setCasilla(Integer.valueOf(validacionArgumentoDTO.getValor()), (calleDTO.getCalleUbicacionDTO() == null ? "" : calleDTO.getCalleUbicacionDTO().getUbicacion()));
						break;
				}
			}
		}catch(Exception e){
			addActionError(e.getMessage());
			LOG.error("Error obteniendo la ayuda de calles", e);
		}
		
		ajaxData=parseaResultado();
		
		return AJAX_DATA;
	}
	
	private DetachedCriteria getCriterioTramoConceptoCategoria(MunicipioDTO municipio, String concepto)
	{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CalleTramoCategoriaDTO.class);
		criteria.createAlias("calleDTO", "call");
		criteria.add(Restrictions.eq("conceptoDTO.coConcepto", concepto));
		criteria.add(Restrictions.eq("call.municipioDTO", municipio));
		return criteria;
	}
	private DetachedCriteria getCriterioTramoCategoria(String concepto, CalleDTO calle, String numVia)
	{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CalleTramoCategoriaDTO.class);	
		Criterion ambos = Restrictions.eq("tipo","A");
       
		criteria.add(Restrictions.eq("conceptoDTO.coConcepto", concepto));
		criteria.add(Restrictions.eq("id.coCalle",calle.getCoCalle()));
		
		if(!numVia.isEmpty())
		{
			criteria.add(Restrictions.le("id.desde",Integer.parseInt(numVia)));
			criteria.add(Restrictions.ge("hasta",Integer.parseInt(numVia)));
		
			//Vemos si el numero introducido es par o impar (si ha especificado numero de via)
			if(Integer.parseInt(numVia)%2 == 0)
			{
				Criterion par = Restrictions.eq("tipo","P");
				LogicalExpression orExp = Restrictions.or(ambos,par);
				criteria.add(orExp);
			}
			else
			{
				Criterion impar = Restrictions.eq("tipo","I");
				LogicalExpression orExp = Restrictions.or(ambos,impar);
				criteria.add(orExp);
			}
		}
		
		return criteria;
	}
	
	private DetachedCriteria getCriterioTramoPostal(CalleDTO calle, String numVia)
	{
		final DetachedCriteria criteria = DetachedCriteria.forClass(CalleTramoPostalDTO.class);	
		Criterion ambos = Restrictions.eq("tipo","A");
       
			
		criteria.add(Restrictions.eq("id.coCalle",calle.getCoCalle()));
		
		if(!numVia.isEmpty())
		{
			criteria.add(Restrictions.le("id.desde",Integer.parseInt(numVia)));
			criteria.add(Restrictions.ge("hasta",Integer.parseInt(numVia)));
		
			//Vemos si el numero introducido es par o impar (si ha especificado numero de via)
			if(Integer.parseInt(numVia)%2 == 0)
			{
				Criterion par = Restrictions.eq("tipo","P");
				LogicalExpression orExp = Restrictions.or(ambos,par);
				criteria.add(orExp);
			}
			else
			{
				Criterion impar = Restrictions.eq("tipo","I");
				LogicalExpression orExp = Restrictions.or(ambos,impar);
				criteria.add(orExp);
			}
		}
		
		return criteria;
	}
	
	private String obtenerMunicipio(String descripcionProvincia, String descripcionMunicipio) {
		String codigoMunicipio = "";
		String coProvincia="";
		String coMunicipio="";
		try {
			final DetachedCriteria criterioProvincia = DetachedCriteria.forClass(ProvinciaDTO.class);
			criterioProvincia.add(Restrictions.eq("nombre", descripcionProvincia).ignoreCase());
			
			List<ProvinciaDTO> provinciaDTOs = provinciaBO.findByCriteria(criterioProvincia);
			if(!provinciaDTOs.isEmpty())
				coProvincia = provinciaDTOs.get(0).getCoProvincia();
			else {
				//Buscamos en los sinonimos
				final DetachedCriteria criterioProvinciaSinonimo = DetachedCriteria.forClass(ProvinciaSinonimoDTO.class);
				criterioProvinciaSinonimo.add(Restrictions.eq("id.sinonimo", descripcionProvincia).ignoreCase());
				List<ProvinciaSinonimoDTO> listaSinonimos = provinciaSinonimoBO.findByCriteria(criterioProvinciaSinonimo);
				if(!listaSinonimos.isEmpty())
					coProvincia = listaSinonimos.get(0).getId().getCoProvincia();				
			}
			
			final DetachedCriteria criterioMunicipio = DetachedCriteria.forClass(MunicipioDTO.class);
			criterioMunicipio.add(Restrictions.eq("nombre", descripcionMunicipio).ignoreCase());
			if(!Utilidades.isEmpty(coProvincia))
				criterioMunicipio.add(Restrictions.eq("id.coProvincia", coProvincia));
			
			List<MunicipioDTO> municipioTOs = municipioBO.findByCriteria(criterioMunicipio);
			if(!municipioTOs.isEmpty()) {
				coMunicipio = municipioTOs.get(0).getId().getCoMunicipio();
				if(Utilidades.isEmpty(coProvincia))
					coProvincia = municipioTOs.get(0).getId().getCoProvincia();
			}
			else {
				//Buscamos en los sinonimos
				final DetachedCriteria criterioMunicipioSinonimo = DetachedCriteria.forClass(MunicipioSinonimoDTO.class);
				
				criterioMunicipioSinonimo.add(Restrictions.eq("id.sinonimo", descripcionMunicipio).ignoreCase());
				if(!Utilidades.isEmpty(coProvincia))
					criterioMunicipio.add(Restrictions.eq("id.coProvincia", coProvincia));
				
				List<MunicipioSinonimoDTO> listaSinonimos = municipioSinonimoBO.findByCriteria(criterioMunicipioSinonimo);
				if(!listaSinonimos.isEmpty()) {
					coMunicipio = listaSinonimos.get(0).getId().getCoMunicipio();
					if(Utilidades.isEmpty(coProvincia))
						coProvincia = listaSinonimos.get(0).getId().getCoProvincia();
				}
			}			
			
		} catch(Exception e) {log.error("error obteniendo municipio");}
		
		codigoMunicipio = coProvincia + coMunicipio;
		return codigoMunicipio;
	}
	
	public List<CalleDTO> getLista() {
		return lista;
	}

	public void setLista(List<CalleDTO> lista) {
		this.lista = lista;
	}

	public CalleBO getCalleBO() {
		return calleBO;
	}

	public void setCalleBO(CalleBO calleBO) {
		this.calleBO = calleBO;
	}

	public MunicipioBO getMunicipioBO() {
		return municipioBO;
	}

	public void setMunicipioBO(MunicipioBO municipioBO) {
		this.municipioBO = municipioBO;
	}

	public GadirPaginatedList<G4215CalleVO> getListaCalles() {
		return listaCalles;
	}

	public void setListaCalles(GadirPaginatedList<G4215CalleVO> listaCalles) {
		this.listaCalles = listaCalles;
	}

	public List<G4215CalleVO> getListaCallesVO() {
		return listaCallesVO;
	}

	public void setListaCallesVO(List<G4215CalleVO> listaCallesVO) {
		this.listaCallesVO = listaCallesVO;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPorPagina() {
		return porPagina;
	}

	public void setPorPagina(int porPagina) {
		this.porPagina = porPagina;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCoCalle() {
		return coCalle;
	}

	public void setCoCalle(String coCalle) {
		this.coCalle = coCalle;
	}

	public String getNumDomicilio() {
		return numDomicilio;
	}

	public void setNumDomicilio(String numDomicilio) {
		this.numDomicilio = numDomicilio;
	}

	public MunicipioDatosBO getMunicipioDatosBO() {
		return municipioDatosBO;
	}

	public void setMunicipioDatosBO(MunicipioDatosBO municipioDatosBO) {
		this.municipioDatosBO = municipioDatosBO;
	}

	public CalleTramoPostalBO getCalleTramoPostalBO() {
		return calleTramoPostalBO;
	}

	public void setCalleTramoPostalBO(CalleTramoPostalBO calleTramoPostalBO) {
		this.calleTramoPostalBO = calleTramoPostalBO;
	}

	public GenericBO<CalleTramoCategoriaDTO, CalleTramoCategoriaDTOId> getCalleTramoCategoriaBO() {
		return calleTramoCategoriaBO;
	}

	public void setCalleTramoCategoriaBO(
			GenericBO<CalleTramoCategoriaDTO, CalleTramoCategoriaDTOId> calleTramoCategoriaBO) {
		this.calleTramoCategoriaBO = calleTramoCategoriaBO;
	}

	public boolean isCallejeroM() {
		return callejeroM;
	}

	public void setCallejeroM(boolean callejeroM) {
		this.callejeroM = callejeroM;
	}

	public boolean isConUbicacion() {
		return conUbicacion;
	}

	public void setConUbicacion(boolean conUbicacion) {
		this.conUbicacion = conUbicacion;
	}

	public CalleUbicacionBO getCalleUbicacionBO() {
		return calleUbicacionBO;
	}

	public void setCalleUbicacionBO(CalleUbicacionBO calleUbicacionBO) {
		this.calleUbicacionBO = calleUbicacionBO;
	}

	public String getCpGeneral() {
		return cpGeneral;
	}

	public void setCpGeneral(String cpGeneral) {
		this.cpGeneral = cpGeneral;
	}

	public ProvinciaBO getProvinciaBO() {
		return provinciaBO;
	}

	public void setProvinciaBO(ProvinciaBO provinciaBO) {
		this.provinciaBO = provinciaBO;
	}

	public ProvinciaSinonimoBO getProvinciaSinonimoBO() {
		return provinciaSinonimoBO;
	}

	public void setProvinciaSinonimoBO(ProvinciaSinonimoBO provinciaSinonimoBO) {
		this.provinciaSinonimoBO = provinciaSinonimoBO;
	}

	public MunicipioSinonimoBO getMunicipioSinonimoBO() {
		return municipioSinonimoBO;
	}

	public void setMunicipioSinonimoBO(MunicipioSinonimoBO municipioSinonimoBO) {
		this.municipioSinonimoBO = municipioSinonimoBO;
	}

	public String getNombrePestana() {
		return nombrePestana;
	}

	public void setNombrePestana(String nombrePestana) {
		this.nombrePestana = nombrePestana;
	}

	public GenericBO<CalleSinonimoDTO, CalleSinonimoDTOId> getCalleSinonimoBO() {
		return calleSinonimoBO;
	}

	public void setCalleSinonimoBO(
			GenericBO<CalleSinonimoDTO, CalleSinonimoDTOId> calleSinonimoBO) {
		this.calleSinonimoBO = calleSinonimoBO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Log getLog() {
		return LOG;
	}

	public ConceptoBO getConceptoBO() {
		return conceptoBO;
	}

	public void setConceptoBO(ConceptoBO conceptoBO) {
		this.conceptoBO = conceptoBO;
	}

	public String getCoConcepto() {
		return coConcepto;
	}

	public void setCoConcepto(String coConcepto) {
		this.coConcepto = coConcepto;
	}	
}
