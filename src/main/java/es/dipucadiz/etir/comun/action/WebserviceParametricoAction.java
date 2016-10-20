package es.dipucadiz.etir.comun.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.BancoBO;
import es.dipucadiz.etir.comun.bo.CalleBO;
import es.dipucadiz.etir.comun.bo.CalleUbicacionBO;
import es.dipucadiz.etir.comun.bo.ClienteBO;
import es.dipucadiz.etir.comun.bo.ConceptoBO;
import es.dipucadiz.etir.comun.bo.ConveniosModeloBO;
import es.dipucadiz.etir.comun.bo.GenericBO;
import es.dipucadiz.etir.comun.bo.MunicipioBO;
import es.dipucadiz.etir.comun.bo.PeriodoVoluntariaBO;
import es.dipucadiz.etir.comun.bo.ProvinciaBO;
import es.dipucadiz.etir.comun.dao.DAOConstant;
import es.dipucadiz.etir.comun.dto.BancoDTO;
import es.dipucadiz.etir.comun.dto.BonificacionDTO;
import es.dipucadiz.etir.comun.dto.BonificacionTipoDTO;
import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.CalleUbicacionDTO;
import es.dipucadiz.etir.comun.dto.CircuitoDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.ConceptoDTO;
import es.dipucadiz.etir.comun.dto.ConveniosConceptoDTOId;
import es.dipucadiz.etir.comun.dto.ConveniosModeloDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTOId;
import es.dipucadiz.etir.comun.dto.PeriodoVoluntariaDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.MunicipioConceptoModeloUtil;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

final public class WebserviceParametricoAction extends AbstractGadirBaseAction {


	private static final long serialVersionUID = 1597531504828280531L;

	private String parametro;	
	private String xmlRespuesta;
	
	//Parametros de las funciones
	private String id;
	private String provincia;
	private String codProvincia;
	private String codMunicipio;
	private String codigoEntidad;
	private String municipio;
	private String conc;
	private String codProv;
	private String codMun;
	private String descripcion;
	private short ejercicio;
	private String tipo;
	private String nif;
	private String contribuyente;
	private String concepto;
	private String codConcepto;
	private String proc;
	
	private ProvinciaBO provinciaBO;
	private MunicipioBO municipioBO;
	private GenericBO<BonificacionDTO, Long> bonificacionBO;
	private ClienteBO clienteBO;
	private ConceptoBO conceptoBO;
	private CalleBO calleBO;
	private BancoBO bancoBO;
	private CalleUbicacionBO calleUbicacionBO;
	private ConveniosModeloBO conveniosModeloBO;
	private PeriodoVoluntariaBO periodoVoluntariaBO;
	private GenericBO<BonificacionTipoDTO, Long> bonificacionTipoBO;
	private GenericBO<CircuitoDTO, String> circuitoBO;
	
	public String execute(){

		//Pruebas
		/*List<String[]> listaPruebas = new ArrayList<String[]>();
		
		listaPruebas.add(new String[]{"TIPO01"});
		listaPruebas.add(new String[]{"SIGLAS01", "CL"});
		listaPruebas.add(new String[]{"SIGLAS02"});
		listaPruebas.add(new String[]{"PROVINCIAS01"});
		listaPruebas.add(new String[]{"PERIODOS01"});
		listaPruebas.add(new String[]{"PERIODOS02", "0A"});
		listaPruebas.add(new String[]{"ORGANISMOS01"});
		listaPruebas.add(new String[]{"MUNICIPIOS01", "11"});
		listaPruebas.add(new String[]{"MUNICIPIOS02", "11", "023"});
		listaPruebas.add(new String[]{"MOTIVOS01"});
		listaPruebas.add(new String[]{"ENTIDADES01", "2106"});
		listaPruebas.add(new String[]{"DELEGACIONES01", ""});
		listaPruebas.add(new String[]{"CONCEPTOS01", "01"});
		listaPruebas.add(new String[]{"CONCEPTOS01", "0101"});
		listaPruebas.add(new String[]{"CALLES01", "11", "031", "san fede"});
		listaPruebas.add(new String[]{"CALLES02", "211291"});
		listaPruebas.add(new String[]{"CALENDARIO01", "11014", "0101", "2010"});
		/*listaPruebas.add(new String[]{"CALENDARIO02"});
		listaPruebas.add(new String[]{"BONIFICACIONES01", "001", "11", ""});
		listaPruebas.add(new String[]{"BONIFICACIONES01", "001", "11", "0101"});
		listaPruebas.add(new String[]{"BONIFICACIONES02", "001", "11", "0101", "FN"});
		listaPruebas.add(new String[]{"BONIFICACIONES03", "039", "11", ""});
		listaPruebas.add(new String[]{"BONIFICACIONES04", "44961453H", "SANCHEZ CUENCA SILVIA", "001", "0101", "VP"});
		listaPruebas.add(new String[]{"ACTOS01"});
		
		for(String[] prueba : listaPruebas) {
			parametro = prueba[0];
			
			if(parametro.equals("SIGLAS01") || parametro.equals("PERIODOS02") || parametro.equals("CALLES02")) {
				id = prueba[1];
			}
			else if(parametro.equals("MUNICIPIOS01")) {		
				provincia = prueba[1]; 
			}
			else if(parametro.equals("MUNICIPIOS02")) {		
				codProvincia = prueba[1];
				codMunicipio = prueba[2];
			}
			else if(parametro.equals("ENTIDADES01")) {		
				codigoEntidad = prueba[1]; 
			}
			else if(parametro.equals("DELEGACIONES01")) {		
				municipio = prueba[1]; 
			}
			else if(parametro.equals("CONCEPTOS01")) {		
				conc = prueba[1]; 
			}
			else if(parametro.equals("CALLES01")) {		
				codProv = prueba[1];
				codMun = prueba[2];
				descripcion = prueba[3];
			}		
			else if(parametro.equals("CALENDARIO01")) {		
				municipio = prueba[1];
				concepto = prueba[2];
				ejercicio = Short.parseShort(prueba[3]);
			}
			else if(parametro.equals("BONIFICACIONES01")) {		
				codMunicipio = prueba[1];
				codProvincia = prueba[2];
				codConcepto = prueba[3];
			}
			else if(parametro.equals("BONIFICACIONES02")) {		
				codMunicipio = prueba[1];
				codProvincia = prueba[2];
				codConcepto = prueba[3];
				tipo = prueba[4];
			}
			else if(parametro.equals("BONIFICACIONES03")) {		
				codMunicipio = prueba[1];
				codProvincia = prueba[2];
				codConcepto = prueba[3];
			}
			else if(parametro.equals("BONIFICACIONES04")) {		
				nif = prueba[1];
				contribuyente = prueba[2];
				codMunicipio = prueba[3];
				concepto = prueba[4];
				tipo = prueba[5];
			}*/
		
		xmlRespuesta="";

		boolean reconocido=false;
		
		try{
			if(parametro.equalsIgnoreCase("TIPO01")) {
				obtenerTipos();
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("SIGLAS01")) {
				obtenerSigla(id);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("SIGLAS02")) {
				obtenerTodasSiglas();
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("PROVINCIAS01")) {
				obtenerProvincias();
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("PROVINCIAS02")) {
				obtenerProvincia(codProvincia);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("PERIODOS01")) {
				obtenerTodosPeriodos();
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("PERIODOS02")) {
				obtenerPeriodo(id);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("ORGANISMOS01")) {
				obtenerTodosOrganismos();
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("MUNICIPIOS01")) {
				obtenerMunicipios(provincia);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("MUNICIPIOS02")) {
				obtenerMunicipio(codProvincia, codMunicipio);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("MUNICIPIOS03")) {
				obtenerCodigoMunicipioCadiz(municipio);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("MOTIVOS01")) {
				obtenerMotivosDII();
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("ENTIDADES01")) {
				obtenerDescripcionEntidad(codigoEntidad);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("DELEGACIONES01")) {				
				obtenerDelegacion(municipio);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("CONCEPTOS01")) {
				obtenerConceptos(conc);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("CALLES01")) {
				obtenerCallesByNombre(codProv, codMun, descripcion);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("CALLES02")) {
				obtenerCalle(Long.parseLong(id));
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("CALENDARIO01")) {
				try{MunicipioDTO m = municipioBO.findById(new MunicipioDTOId(municipio.substring(0,2), municipio.substring(2, 5)));
				obtenerCalendario(m, conceptoBO.findById(concepto), ejercicio);}catch(Exception e){}
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("CALENDARIO02")) {
				obtenerListadoEjercicios();
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("BONIFICACIONES01")) {
				obtenerBonificaciones(codMunicipio, codProvincia, codConcepto);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("BONIFICACIONES02")) {
				obtenerBonificacion(codMunicipio, codProvincia, codConcepto, tipo);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("BONIFICACIONES03")) {
				obtenerConceptosBonificaciones(codMunicipio, codProvincia);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("BONIFICACIONES04")) {
				obtenerReferenciasObjetoTributaria(nif, contribuyente, codMunicipio, concepto, tipo);
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("ACTOS01")) {
				obtenerActos();
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("PROCEDIMIENTOS01")) {
				obtenerProcedimientosAcerca();
				reconocido = true;
			}
			else if(parametro.equalsIgnoreCase("PROCEDIMIENTOS02")) {
				obtenerProcedimiento(proc);
				reconocido = true;
			}

		}catch(Exception e){
			log.error("Error en webservice", e);
		}

		//}
		return "webservice";
	}

	private void obtenerTipos() throws GadirServiceException {
		List<KeyValue> listaTipos = TablaGt.getListaCodigoDescripcion(TablaGtConstants.TABLA_TIPOS_RECURSOS); 
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaTipos>";
		
		for(KeyValue tipo : listaTipos) {
			xmlRespuesta+=
				"<tipo>"+
					"<codigo>"+tipo.getKey()+"</codigo>"+
					"<descripcion>"+tipo.getValue()+"</descripcion>"+
				"</tipo>";
		}
		xmlRespuesta+="</listaTipos>";
	}
	
	private void obtenerSigla(String id) throws GadirServiceException {
		String descripcionSigla = "";
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xmlRespuesta+="<sigla>";
		
		if(TablaGt.isElemento(TablaGtConstants.TABLA_TIPO_VIA_PUBLICA, id)) {
			descripcionSigla = TablaGt.getValor(TablaGtConstants.TABLA_TIPO_VIA_PUBLICA, id, TablaGt.COLUMNA_DESCRIPCION);
		
			xmlRespuesta+=
				"<codigo>"+id+"</codigo>"+
				"<descripcion>"+descripcionSigla+"</descripcion>";
		}
		xmlRespuesta+="</sigla>";	
	}
    
	
	private void obtenerTodasSiglas() throws GadirServiceException{
		List<KeyValue> listaSiglas = TablaGt.getListaCodigoDescripcion(TablaGtConstants.TABLA_TIPO_VIA_PUBLICA); 
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaSiglas>";
		
		for(KeyValue sigla : listaSiglas) {
			xmlRespuesta+=
				"<sigla>"+
					"<codigo>"+sigla.getKey()+"</codigo>"+
					"<descripcion>"+sigla.getValue()+"</descripcion>"+
				"</sigla>";
		}
		xmlRespuesta+="</listaSiglas>";
	}

	private void obtenerProvincias() throws GadirServiceException{
		DetachedCriteria criterio = DetachedCriteria.forClass(ProvinciaDTO.class);
		criterio.add(Restrictions.ne("coProvincia", "**"));
		criterio.addOrder(Order.asc("nombre"));
		//List<ProvinciaDTO> listaProvincias = provinciaBO.findAll("nombre", DAOConstant.ASC_ORDER); 
		//if(listaProvincias.get(0).equals("**"));
			//listaProvincias.remove(0);
		List<ProvinciaDTO> listaProvincias = provinciaBO.findByCriteria(criterio);
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaProvincias>";
		
		for(ProvinciaDTO p : listaProvincias) {
			xmlRespuesta+=
				"<provincia>"+
					"<codigo>"+p.getCoProvincia()+"</codigo>"+
					"<descripcion>"+p.getNombre()+"</descripcion>"+
				"</provincia>";
		}
		xmlRespuesta+="</listaProvincias>";
		System.out.println(xmlRespuesta);
	}
	private void obtenerProvincia(String codProvincia) throws GadirServiceException{
		
		if (codProvincia.length()==1){
			codProvincia="0"+codProvincia;
		}
		
		String descripcionProvincia = "";
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<provincia>";
		
		ProvinciaDTO m = provinciaBO.findById(codProvincia); 
		if(m != null) {
			descripcionProvincia = m.getNombre();
		
			xmlRespuesta+=
				"<codigo>"+codProvincia+"</codigo>"+
				"<descripcion>"+descripcionProvincia+"</descripcion>";
		}
		xmlRespuesta+="</provincia>";		
	}

	private void obtenerPeriodo(String id) throws GadirServiceException {
		String descripcionPeriodo = "";
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xmlRespuesta+="<periodo>";
		
		if(TablaGt.isElemento(TablaGtConstants.TABLA_PERIODO, id)) {
			descripcionPeriodo = TablaGt.getValor(TablaGtConstants.TABLA_PERIODO, id, TablaGt.COLUMNA_DESCRIPCION);
		
			xmlRespuesta+=
				"<codigo>"+id+"</codigo>"+
				"<descripcion>"+descripcionPeriodo+"</descripcion>";
		}
		xmlRespuesta+="</periodo>";	
	}
    
	
	private void obtenerTodosPeriodos() throws GadirServiceException{
		List<KeyValue> listaPeriodos = TablaGt.getListaCodigoDescripcion(TablaGtConstants.TABLA_PERIODO); 
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaPeriodos>";
		
		for(KeyValue periodo : listaPeriodos) {
			xmlRespuesta+=
				"<periodo>"+
					"<codigo>"+periodo.getKey()+"</codigo>"+
					"<descripcion>"+periodo.getValue()+"</descripcion>"+
				"</periodo>";
		}
		xmlRespuesta+="</listaPeriodos>";
	}
	
	private void obtenerTodosOrganismos() throws GadirServiceException{
		List<KeyValue> listaOrganismos = TablaGt.getListaCodigoDescripcion(TablaGtConstants.TABLA_ORGANISMOS_ACERCA); 
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaOrganismos>";
		
		for(KeyValue organismo : listaOrganismos) {
			xmlRespuesta+=
				"<organismo>"+
					"<codigo>"+organismo.getKey()+"</codigo>"+
					"<descripcion>"+organismo.getValue()+"</descripcion>"+
				"</organismo>";
		}
		xmlRespuesta+="</listaOrganismos>";
	}
	
	private void obtenerMunicipios(String provincia) throws GadirServiceException{
		
		if (!Utilidades.isEmpty(provincia) && !Utilidades.isNumeric(provincia)) {
			DetachedCriteria dc = DetachedCriteria.forClass(ProvinciaDTO.class);
			dc.add(Restrictions.sqlRestriction("translate(UPPER(this_.nombre), 'ÁÉÍÓÚÖ', 'AEIOUO') = translate('" + provincia.toUpperCase() + "', 'ÁÉÍÓÚÖ', 'AEIOUO')"));
			
			List<ProvinciaDTO> listaProvincias = provinciaBO.findByCriteria(dc);
			if(listaProvincias.size() == 1) {
				provincia = listaProvincias.get(0).getNombre();
			}
		}
		
		if (provincia.length()==1){
			provincia="0"+provincia;
		}
		
		List<MunicipioDTO> listaMunicipios = municipioBO.findFiltered("id.coProvincia", provincia, "nombre", DAOConstant.ASC_ORDER); 
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaMunicipios>";
		
		for(MunicipioDTO m : listaMunicipios) {
			xmlRespuesta+=
				"<municipio>"+
					"<codigo>"+m.getId().getCoMunicipio()+"</codigo>"+
					"<descripcion>"+m.getNombre()+"</descripcion>"+
					"<callejero>0</callejero>"+
					"<visible>"+((m.getBoMunicipioIne()!=null && m.getBoMunicipioIne())?"1":"0")+"</visible>"+
					"<organismo>"+"000"+"</organismo>"+
					"<descripcionAcerca>"+m.getNombre()+"</descripcionAcerca>"+
				"</municipio>";
		}
		xmlRespuesta+="</listaMunicipios>";
	}
	
	private void obtenerMunicipio(String codProvincia, String codMunicipio) throws GadirServiceException{
		String descripcionMunicipio = "";
		
		if (codProvincia.length()==1){
			codProvincia="0"+codProvincia;
		}
		
		if (codMunicipio.length()!=3){
			switch (codMunicipio.length()){
			case 1:codMunicipio="00"+codMunicipio;break;
			case 2:codMunicipio="0"+codMunicipio;break;
			}
		}
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<municipio>";
		
		MunicipioDTO m = municipioBO.findByIdInitialized(new MunicipioDTOId(codProvincia, codMunicipio), new String[]{"municipioDatosDTO"}); 
		if(m != null) {
			descripcionMunicipio = m.getNombre();
			String callejero="0";
			if (m.getMunicipioDatosDTO()!=null && m.getMunicipioDatosDTO().getBoCallejeroMunicipal()!=null && m.getMunicipioDatosDTO().getBoCallejeroMunicipal()){
				callejero="1";
			}
		
			xmlRespuesta+=
				"<codigo>"+codProvincia+codMunicipio+"</codigo>"+
				"<descripcion>"+descripcionMunicipio+"</descripcion>"+
				"<callejero>" + callejero + "</callejero>"+
				"<visible>"+((m.getBoMunicipioIne()!=null && m.getBoMunicipioIne())?"1":"0")+"</visible>"+
				"<organismo>"+"000"+"</organismo>"+
				"<descripcionAcerca>"+descripcionMunicipio+"</descripcionAcerca>";
		}
		xmlRespuesta+="</municipio>";		
	}
	
	private void obtenerCodigoMunicipioCadiz(String municipio) throws GadirServiceException{
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<municipio>";
		
		DetachedCriteria criterio = DetachedCriteria.forClass(MunicipioDTO.class);
		criterio.add(Restrictions.eq("id.coProvincia", "11"));
		criterio.add(Restrictions.sqlRestriction("translate(UPPER(this_.nombre), 'ÁÉÍÓÚÖ', 'AEIOUO') like translate('" + municipio.toUpperCase() + "%', 'ÁÉÍÓÚÖ', 'AEIOUO')"));
		
		List<MunicipioDTO> municipioDTOs = municipioBO.findByCriteria(criterio, 0, 1);
		if(!municipioDTOs.isEmpty()) {
			xmlRespuesta+="<codigo>"+municipioDTOs.get(0).getId().getCoMunicipio()+"</codigo>";
		}
		xmlRespuesta+="</municipio>";		
	}
	
	private void obtenerMotivosDII() throws GadirServiceException{
		List<KeyValue> listaMotivos = TablaGt.getListaCodigoDescripcion(TablaGtConstants.TABLA_MOTIVOS_DEVOLUCION_II); 
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaMotivos>";
		
		for(KeyValue motivo : listaMotivos) {
			xmlRespuesta+=
				"<motivo>"+
					"<codigo>"+motivo.getKey()+"</codigo>"+
					"<descripcion>"+motivo.getValue()+"</descripcion>"+
				"</motivo>";
		}
		xmlRespuesta+="</listaMotivos>";
	}
	
	private void obtenerDescripcionEntidad(String codigoEntidad) throws GadirServiceException{
		String nombreBanco = "";
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<entidad>";
		
		BancoDTO b = bancoBO.findById(codigoEntidad); 
		if(b != null) {
			nombreBanco = b.getNombre();
		
			xmlRespuesta+=
				"<codigo>"+codigoEntidad+"</codigo>"+
				"<descripcion>"+nombreBanco+"</descripcion>";
		}
		xmlRespuesta+="</entidad>";		
	}
	
	private void obtenerDelegacion(String municipio) throws GadirServiceException {
		//TODO
	}
	
	private void obtenerConceptos(String conc) throws GadirServiceException {
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaConceptos>";
		
		final DetachedCriteria criteria = DetachedCriteria.forClass(ConceptoDTO.class);		
		criteria.add(Restrictions.like("coConcepto", conc+"%"));
		criteria.add(Restrictions.eq("boVisibleAcerca", true));
		
		List<ConceptoDTO> listaConceptos = conceptoBO.findByCriteria(criteria);
		
		for(ConceptoDTO c : listaConceptos) {
			xmlRespuesta+=
			"<concepto>"+
				"<codigo>"+c.getCoConcepto()+"</codigo>"+
				"<descripcion>"+c.getNombre()+"</descripcion>"+
			"</concepto>";
		}
		
		xmlRespuesta+="</listaConceptos>";
	}
	
	private void obtenerCallesByNombre(String codProv, String codMun, String descripcion) throws GadirServiceException {
		
		if (codProv.length()==1){
			codProv="0"+codProv;
		}
		
		if (codMun.length()!=3){
			switch (codMun.length()){
			case 1:codMun="00"+codMun;break;
			case 2:codMun="0"+codMun;break;
			}
		}
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaCalles>";
		
		final DetachedCriteria criteria = DetachedCriteria.forClass(CalleDTO.class);
		String crit = "%"+descripcion+"%";
		
		criteria.add(Restrictions.eq("municipioDTO.id.coProvincia", codProv));
		criteria.add(Restrictions.eq("municipioDTO.id.coMunicipio", codMun));
		if(Utilidades.isNotEmpty(descripcion))
			criteria.add(Restrictions.like("nombreCalle", crit).ignoreCase());
        
		List<CalleDTO> listaCalles = calleBO.findByCriteria(criteria, 0, 100);
		
		for(CalleDTO c : listaCalles) {
			String ubicacion = "";
			String tipo = "G";
			
			if(c.getCoMunicipal() != null && c.getCoMunicipal() > 0)
				tipo = "M";
			else if (c.getCoDgc() != null && c.getCoDgc() > 0)
				tipo = "C";
			
			if(c.getCalleUbicacionDTO() != null) {
				CalleUbicacionDTO cu = calleUbicacionBO.findById(c.getCalleUbicacionDTO().getCoCalleUbicacion());
				ubicacion = cu.getUbicacion();
			}
			
			xmlRespuesta+=
			"<calle>"+
				"<codigo>"+c.getCoCalle()+"</codigo>"+
				"<provincia>"+c.getMunicipioDTO().getId().getCoProvincia()+"</provincia>"+
				"<municipio>"+c.getMunicipioDTO().getId().getCoMunicipio()+"</municipio>"+
				"<sigla>"+c.getSigla()+"</sigla>"+
				"<nombre>"+c.getNombreCalle()+"</nombre>"+
				"<tipo>"+tipo+"</tipo>"+
				"<procedencia>"+(Utilidades.isEmpty(c.getProcedencia())?"":c.getProcedencia())+"</procedencia>"+
				"<ubicacion>"+ubicacion+"</ubicacion>"+
			"</calle>";
		}
		
		xmlRespuesta+="</listaCalles>";
	}

	private void obtenerCalle(Long id) throws GadirServiceException {
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<calle>";
		
		if(calleBO.findById(id) != null) {
			CalleDTO c = calleBO.findById(id);
			String ubicacion = "";
			String tipo = "G";
			
			if(c.getCoMunicipal() != null && c.getCoMunicipal() > 0)
				tipo = "M";
			else if (c.getCoDgc() != null && c.getCoDgc() > 0)
				tipo = "C";
			
			if(c.getCalleUbicacionDTO() != null) {
				CalleUbicacionDTO cu = calleUbicacionBO.findById(c.getCalleUbicacionDTO().getCoCalleUbicacion());
				ubicacion = cu.getUbicacion();
			}
			
			xmlRespuesta+=
			"<codigo>"+c.getCoCalle()+"</codigo>"+
			"<provincia>"+c.getMunicipioDTO().getId().getCoProvincia()+"</provincia>"+
			"<municipio>"+c.getMunicipioDTO().getId().getCoMunicipio()+"</municipio>"+
			"<sigla>"+c.getSigla()+"</sigla>"+
			"<nombre>"+c.getNombreCalle()+"</nombre>"+
			"<tipo>"+tipo+"</tipo>"+
			"<procedencia>"+(Utilidades.isEmpty(c.getProcedencia())?"":c.getProcedencia())+"</procedencia>"+
			"<ubicacion>"+ubicacion+"</ubicacion>";
		}
		
		xmlRespuesta+="</calle>";
	}

	private void obtenerCalendario(MunicipioDTO municipio, ConceptoDTO concepto, short ejercicio) throws GadirServiceException {
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaCalendario>";
		
		String coConcepto = concepto.getCoConcepto();
		String modeloGenerico = "***";
		String modelo = "2" + coConcepto.substring(2);
		
		try {
			ConveniosModeloDTO conveniosModeloDTO = new ConveniosModeloDTO();
			ConveniosConceptoDTOId id = new ConveniosConceptoDTOId(municipio.getId().getCoProvincia(), municipio.getId().getCoMunicipio(), ejercicio, coConcepto);
			List<ConveniosModeloDTO> listaConveniosModelo = conveniosModeloBO.findFiltered(
					new String[]{"conveniosConceptoDTO.id", "modeloDTO.coModelo"}, 
					new Object[]{id, modeloGenerico});
			
			if(listaConveniosModelo.isEmpty()) {
				listaConveniosModelo = conveniosModeloBO.findFiltered(
						new String[]{"conveniosConceptoDTO.id", "modeloDTO.coModelo"}, 
						new Object[]{id, modelo});
			}
			
			if(!listaConveniosModelo.isEmpty()) {
				conveniosModeloDTO = listaConveniosModelo.get(0);
				List<PeriodoVoluntariaDTO> listaPeriodosVoluntaria = periodoVoluntariaBO.findFiltered(
						"conveniosModeloDTO.coConveniosModelo", conveniosModeloDTO.getCoConveniosModelo());
				
				for(PeriodoVoluntariaDTO periodo : listaPeriodosVoluntaria) {
					xmlRespuesta+=
						"<periodo>"+
							"<provincia>"+municipio.getId().getCoProvincia()+"</provincia>"+
							"<municipio>"+municipio.getId().getCoMunicipio()+"</municipio>"+
							"<concepto>"+coConcepto+"</concepto>"+
							"<ejercicio>"+ejercicio+"</ejercicio>"+
							"<fxInicio>"+periodo.getFxIniVoluntaria()+"</fxInicio>"+
							"<fxFin>"+periodo.getFxFinVoluntaria()+"</fxFin>"+
						"</periodo>";
				}
			}
		} catch (Exception e) {}
		xmlRespuesta+="</listaCalendario>";
	}
	
	private void obtenerListadoEjercicios() throws GadirServiceException {
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaEjercicios>";
		
		DetachedCriteria criterio = DetachedCriteria.forClass(PeriodoVoluntariaDTO.class);		
		criterio.setProjection(Projections.projectionList() 
		    	.add(Projections.distinct(Projections.property("conveniosConceptoDTO.id.ejercicio"))));		
//		criterio.createAlias("conveniosModeloDTO", "c");		
		List<Object> listaPeriodosVoluntaria = periodoVoluntariaBO.findByCriteriaGenerico(criterio);
		
		for(Object periodo : listaPeriodosVoluntaria) {
			xmlRespuesta+="<ejercicio>"+periodo+"</ejercicio>";									
		}
		
		xmlRespuesta+="</listaEjercicios>";
	}
	
	private void obtenerBonificaciones(String codMunicipio, String codProvincia, String codConcepto) throws GadirServiceException {
		boolean error = false;
		DetachedCriteria criterio = DetachedCriteria.forClass(BonificacionTipoDTO.class);
		
		if (codProvincia.length()==1){
			codProvincia="0"+codProvincia;
		}
		
		if (codMunicipio.length()!=3){
			switch (codMunicipio.length()){
			case 1:codMunicipio="00"+codMunicipio;break;
			case 2:codMunicipio="0"+codMunicipio;break;
			}
		}
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaBonificaciones>";
		
		if(Utilidades.isEmpty(codProvincia) || Utilidades.isEmpty(codMunicipio) || municipioBO.findById(new MunicipioDTOId(codProvincia, codMunicipio)) == null)
			error = true;
		else {
			criterio.add(Restrictions.eq("municipioDTO.id.coProvincia", codProvincia));
			criterio.add(Restrictions.eq("municipioDTO.id.coMunicipio", codMunicipio));
		}
			
		if(!Utilidades.isEmpty(codConcepto)) {
			if(conceptoBO.findById(codConcepto) == null)
				error = true;
			else
				criterio.add(Restrictions.eq("conceptoDTO.coConcepto", codConcepto));
		}
		
		criterio.setProjection(Projections.groupProperty("tipoBonificacion"));
		
		if(!error) {
			Object o = bonificacionTipoBO.findByCriteria(criterio);
			
			List<String> listaTipos = (List<String>)o;
		
			for(String tipo : listaTipos) {
				xmlRespuesta+=
					"<bonificacion>"+
						"<codigo>"+""+"</codigo>"+
						"<provincia>"+codProvincia+"</provincia>"+
						"<municipio>"+codMunicipio+"</municipio>"+
						"<concepto>"+""+"</concepto>"+
						"<tipo>"+tipo+"</tipo>"+
					"</bonificacion>";
			}
		}
		
		xmlRespuesta+="</listaBonificaciones>";
	}
	
	public void obtenerBonificacion(String codMunicipio, String codProvincia, String codConcepto, String tipo) throws GadirServiceException {
		
		if (codProvincia.length()==1){
			codProvincia="0"+codProvincia;
		}
		
		if (codMunicipio.length()!=3){
			switch (codMunicipio.length()){
			case 1:codMunicipio="00"+codMunicipio;break;
			case 2:codMunicipio="0"+codMunicipio;break;
			}
		}
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
		xmlRespuesta+="<bonificacion>";
		
		if(!Utilidades.isEmpty(codProvincia) && !Utilidades.isEmpty(codMunicipio)  
				&& municipioBO.findById(new MunicipioDTOId(codProvincia, codMunicipio)) != null
				&& !Utilidades.isEmpty(codConcepto) && conceptoBO.findById(codConcepto) != null
				&& !Utilidades.isEmpty(tipo)) {
			DetachedCriteria criterio = DetachedCriteria.forClass(BonificacionTipoDTO.class);
			criterio.add(Restrictions.eq("municipioDTO.id.coProvincia", codProvincia));
			criterio.add(Restrictions.eq("municipioDTO.id.coMunicipio", codMunicipio));
			criterio.add(Restrictions.eq("conceptoDTO.coConcepto", codConcepto));
			criterio.add(Restrictions.eq("tipoBonificacion", tipo));
			
			List<BonificacionTipoDTO> listaBonificaciones = bonificacionTipoBO.findByCriteria(criterio, 0, 1);
			
			if(!listaBonificaciones.isEmpty()) {
				BonificacionTipoDTO b = listaBonificaciones.get(0);
				
				xmlRespuesta+=
				"<codigo>"+b.getCoBonificacionTipo()+"</codigo>"+
				"<provincia>"+codProvincia+"</provincia>"+
				"<municipio>"+codMunicipio+"</municipio>"+
				"<concepto>"+codConcepto+"</concepto>"+
				"<tipo>"+tipo+"</tipo>";
			}
		}
		
		xmlRespuesta+="</bonificacion>";
	}
	
	public void obtenerConceptosBonificaciones (String codMunicipio, String codProvincia) throws GadirServiceException {
		//obtenerBonificaciones(codMunicipio, codProvincia, "", true);
		boolean error = false;
		DetachedCriteria criterio = DetachedCriteria.forClass(BonificacionTipoDTO.class);
		
		if (codProvincia.length()==1){
			codProvincia="0"+codProvincia;
		}
		
		if (codMunicipio.length()!=3){
			switch (codMunicipio.length()){
			case 1:codMunicipio="00"+codMunicipio;break;
			case 2:codMunicipio="0"+codMunicipio;break;
			}
		}
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaBonificaciones>";
		
		if(Utilidades.isEmpty(codProvincia) || Utilidades.isEmpty(codMunicipio) || municipioBO.findById(new MunicipioDTOId(codProvincia, codMunicipio)) == null)
			error = true;
		else {
			criterio.add(Restrictions.eq("municipioDTO.id.coProvincia", codProvincia));
			criterio.add(Restrictions.eq("municipioDTO.id.coMunicipio", codMunicipio));
			criterio.createAlias("conceptoDTO", "c");
			criterio.add(Restrictions.eq("c.boVisibleAcerca", true));
		}
		
		criterio.setProjection(Projections.groupProperty("conceptoDTO.coConcepto"));
		
		if(!error) {
			 Object o = bonificacionTipoBO.findByCriteria(criterio);
			 
			 List<String> listaConceptos = (List<String>)o;
		
			for(String concepto : listaConceptos) {
				String desc = MunicipioConceptoModeloUtil.getConceptoDescripcion(concepto);
				xmlRespuesta+=
					"<bonificacion>"+
						"<codigo>"+""+"</codigo>"+
						"<provincia>"+codProvincia+"</provincia>"+
						"<municipio>"+codMunicipio+"</municipio>"+
						"<codConcepto>"+concepto+"</codConcepto>"+
						"<concepto>"+desc+"</concepto>"+
						"<tipo>"+""+"</tipo>"+
					"</bonificacion>";
			}
		}
		
		xmlRespuesta+="</listaBonificaciones>";
	}
	
	public void obtenerReferenciasObjetoTributaria(String nif, String contribuyente, String codMunicipio, String concepto, String tipo) throws GadirServiceException {
		String coProvincia = "";
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaReferenciasObjTributario>";
		
		if (codMunicipio.length()==5) {
			coProvincia = codMunicipio.substring(0,2);
			codMunicipio = codMunicipio.substring(2);
		}
		
		Long coCliente = null;
		
		List<ClienteDTO> clientes = clienteBO.findFiltered(new String[]{"identificador", "razonSocial"}, new Object[]{nif, contribuyente}, 0, 1);
		if(!clientes.isEmpty())
			coCliente = clientes.get(0).getCoCliente();
		
		DetachedCriteria criterio = DetachedCriteria.forClass(BonificacionDTO.class);
		
		criterio.setProjection(Projections.projectionList() 
		    	.add(Projections.distinct(Projections.property("refObjTributario"))));
		
		if(!Utilidades.isEmpty(coProvincia))
			criterio.add(Restrictions.eq("municipioDTO.id.coProvincia", coProvincia));
		criterio.add(Restrictions.eq("municipioDTO.id.coMunicipio", codMunicipio));
		if(!Utilidades.isEmpty(coCliente))
			criterio.add(Restrictions.eq("clienteDTO.coCliente", coCliente));
		criterio.add(Restrictions.eq("conceptoDTO.coConcepto", concepto));
		criterio.add(Restrictions.eq("tipoBonificacion", tipo));
		
		List<Object> listaBonificaciones = bonificacionBO.findByCriteriaGenerico(criterio);
		
		for(Object refObjTributario : listaBonificaciones) {
			xmlRespuesta+=
				"<refObjTributario>"+(String) refObjTributario +"</refObjTributario>";				
		}
		
		xmlRespuesta+="</listaReferenciasObjTributario>";
		
	}
	
	private void obtenerActos() throws GadirServiceException{
		List<KeyValue> listaActos = TablaGt.getListaCodigoDescripcion(TablaGtConstants.TABLA_ACTOS_ACERCA); 
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaActos>";
		
		for(KeyValue acto : listaActos) {
			xmlRespuesta+=
				"<acto>"+
					"<codigo>"+acto.getKey()+"</codigo>"+
					"<descripcion>"+acto.getValue()+"</descripcion>"+
				"</acto>";
		}
		xmlRespuesta+="</listaActos>";
	}
	
	private void obtenerProcedimientosAcerca() throws GadirServiceException{
		List<CircuitoDTO> listaProcs = circuitoBO.findFiltered("tipo", "R", "coCircuito", DAOConstant.ASC_ORDER);
		//List<KeyValue> listaProcs = TablaGt.getListaCodigoDescripcion(TablaGtConstants.TABLA_PROCEDIMIENTOS_ACERCA); 
		
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<listaProcedimientos>";
		
		for(CircuitoDTO proc : listaProcs) {
			xmlRespuesta+=
				"<procedimiento>"+
					"<codigo>"+proc.getCoCircuito()+"</codigo>"+
					"<descripcion>"+proc.getNombre()+"</descripcion>"+
				"</procedimiento>";
		}
		xmlRespuesta+="</listaProcedimientos>";
	}

	private void obtenerProcedimiento(String proc) throws GadirServiceException{
		xmlRespuesta="";
		xmlRespuesta+="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		xmlRespuesta+="<procedimiento>";
	
		try {
			CircuitoDTO circuitoDTO = circuitoBO.findById(proc.toUpperCase());
			//List<KeyValue> listaProcs = TablaGt.getListaCodigoDescripcion(TablaGtConstants.TABLA_PROCEDIMIENTOS_ACERCA); 
		
			xmlRespuesta+=
				"<codigo>"+circuitoDTO.getCoCircuito()+"</codigo>"+
				"<descripcion>"+circuitoDTO.getNombre()+"</descripcion>";			
		} catch (Exception e) {
			xmlRespuesta+=
				"<codigo/>"+
				"<descripcion/>";	
		}
		
		xmlRespuesta+="</procedimiento>";
	}
	
	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public String getXmlRespuesta() {
		return xmlRespuesta;
	}

	public void setXmlRespuesta(String xmlRespuesta) {
		this.xmlRespuesta = xmlRespuesta;
	}

	public ProvinciaBO getProvinciaBO() {
		return provinciaBO;
	}

	public void setProvinciaBO(ProvinciaBO provinciaBO) {
		this.provinciaBO = provinciaBO;
	}

	public MunicipioBO getMunicipioBO() {
		return municipioBO;
	}

	public void setMunicipioBO(MunicipioBO municipioBO) {
		this.municipioBO = municipioBO;
	}

	public GenericBO<BonificacionDTO, Long> getBonificacionBO() {
		return bonificacionBO;
	}

	public void setBonificacionBO(GenericBO<BonificacionDTO, Long> bonificacionBO) {
		this.bonificacionBO = bonificacionBO;
	}

	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public ConceptoBO getConceptoBO() {
		return conceptoBO;
	}

	public void setConceptoBO(ConceptoBO conceptoBO) {
		this.conceptoBO = conceptoBO;
	}

	public CalleBO getCalleBO() {
		return calleBO;
	}

	public void setCalleBO(CalleBO calleBO) {
		this.calleBO = calleBO;
	}

	public BancoBO getBancoBO() {
		return bancoBO;
	}

	public void setBancoBO(BancoBO bancoBO) {
		this.bancoBO = bancoBO;
	}

	public CalleUbicacionBO getCalleUbicacionBO() {
		return calleUbicacionBO;
	}

	public void setCalleUbicacionBO(CalleUbicacionBO calleUbicacionBO) {
		this.calleUbicacionBO = calleUbicacionBO;
	}

	public ConveniosModeloBO getConveniosModeloBO() {
		return conveniosModeloBO;
	}

	public void setConveniosModeloBO(ConveniosModeloBO conveniosModeloBO) {
		this.conveniosModeloBO = conveniosModeloBO;
	}

	public PeriodoVoluntariaBO getPeriodoVoluntariaBO() {
		return periodoVoluntariaBO;
	}

	public void setPeriodoVoluntariaBO(PeriodoVoluntariaBO periodoVoluntariaBO) {
		this.periodoVoluntariaBO = periodoVoluntariaBO;
	}

	public GenericBO<BonificacionTipoDTO, Long> getBonificacionTipoBO() {
		return bonificacionTipoBO;
	}

	public void setBonificacionTipoBO(
			GenericBO<BonificacionTipoDTO, Long> bonificacionTipoBO) {
		this.bonificacionTipoBO = bonificacionTipoBO;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCodProvincia() {
		return codProvincia;
	}

	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public String getCodigoEntidad() {
		return codigoEntidad;
	}

	public void setCodigoEntidad(String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getConc() {
		return conc;
	}

	public void setConc(String conc) {
		this.conc = conc;
	}

	public String getCodProv() {
		return codProv;
	}

	public void setCodProv(String codProv) {
		this.codProv = codProv;
	}

	public String getCodMun() {
		return codMun;
	}

	public void setCodMun(String codMun) {
		this.codMun = codMun;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public short getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(short ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getContribuyente() {
		return contribuyente;
	}

	public void setContribuyente(String contribuyente) {
		this.contribuyente = contribuyente;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getCodConcepto() {
		return codConcepto;
	}

	public void setCodConcepto(String codConcepto) {
		this.codConcepto = codConcepto;
	}

	public GenericBO<CircuitoDTO, String> getCircuitoBO() {
		return circuitoBO;
	}

	public void setCircuitoBO(GenericBO<CircuitoDTO, String> circuitoBO) {
		this.circuitoBO = circuitoBO;
	}

	public void setProc(String proc) {
		this.proc = proc;
	}

	public String getProc() {
		return proc;
	}
	
}
