package es.dipucadiz.etir.comun.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.dipucadiz.etir.comun.bo.AcmUsuarioBO;
import es.dipucadiz.etir.comun.bo.CasillaModeloBO;
import es.dipucadiz.etir.comun.bo.DocumentoCasillaValorBO;
import es.dipucadiz.etir.comun.bo.HDocumentoCasillaValorBO;
import es.dipucadiz.etir.comun.bo.impl.HistoricoGenericoBOImpl;
import es.dipucadiz.etir.comun.displaytag.GadirPaginatedList;
import es.dipucadiz.etir.comun.dto.AcmUsuarioDTO;
import es.dipucadiz.etir.comun.dto.CasillaModeloDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoCasillaValorDTOId;
import es.dipucadiz.etir.comun.dto.HDocumentoCasillaValorDTO;
import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.ControlTerritorial;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.Impresion;
import es.dipucadiz.etir.comun.utilidades.KeyValue;
import es.dipucadiz.etir.comun.utilidades.Mensaje;
import es.dipucadiz.etir.comun.utilidades.MensajeConstants;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.TablaGtConstants;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.HistoricoGenericoVO;

public class HistoricoGenericoAction extends AbstractGadirBaseAction {

	private static final long serialVersionUID = 7334345552434388963L;
	private static final Log LOG = LogFactory.getLog(HistoricoGenericoAction.class);

	protected String historicoNombre;
	private String historicoCriterios;
	protected String historicoBusqueda;
	private List<KeyValue> listaCriterios;
	protected int columnas;
	protected GadirPaginatedList listadoHistorico;
	protected List<HistoricoGenericoVO> historicoTabla;
	protected HistoricoGenericoBOImpl historicoGenericoBO;
	private CasillaModeloBO casillaModeloBO;
	protected List<String> titulos;
	private boolean experto;
	protected String usuario;
	protected Date fechaDesde;
	protected Date fechaHasta;
	private String fechaD;
	private String fechaH;
	protected List<AcmUsuarioDTO> listaUsuarios;
	protected AcmUsuarioBO acmUsuarioBO;
	protected String usuarioRowid;
	private List<KeyValue> listaMovimientos = new ArrayList<KeyValue>();
	protected String movimiento;
	protected String movimientoKey;
	
	protected String sort;
	protected String dir;
	protected int page = 1;
	protected int porPagina = 10;
	
	protected boolean informe = false;
	
	private HDocumentoCasillaValorBO hDocumentoCasillaValorBO;
	private DocumentoCasillaValorBO documentoCasillaValorBO;
	
	protected boolean mostrarMovimiento = true;
	
	protected boolean alinearDerecha = false;
	
	@SuppressWarnings("unchecked")
	public String obtenerValorColumna(int r, int p) throws GadirServiceException {
		
		List<HistoricoGenericoVO> listade = (List<HistoricoGenericoVO>)listadoHistorico.getList();
		HistoricoGenericoVO de = listade.get(r);
		List<String> cols = de.getColumnas();
		
		
		if(historicoNombre.equals("casilla.hoja"))
		{
			if (p == 0)
			{
				String[] criteriosDatos = historicoBusqueda.split("\\|");
				String mod = criteriosDatos[0];
				String vers = criteriosDatos[1];
				String casillaDesc = casillaModeloBO.findById(new CasillaModeloDTOId(mod,vers,Short.parseShort(de.getColumnas().get(p+1)))).getCodigoDescripcion();
				
				return casillaDesc;
			}
			else if(p == 2)
			{
				Date fecha = null;
				String fecha_s = new String();
				try {
					fecha_s = Utilidades.DDMMYYYYtoYYYYMMDD(de.getFecha()) + " " + de.getHora()+".000999";
					fecha = Utilidades.YYYYMMDDHHMMSSToDate(fecha_s);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String valor = new String();
				String[] criteriosDatos = historicoBusqueda.split("\\|");
				String mod = criteriosDatos[0];
				String vers = criteriosDatos[1];
				String coDoc = criteriosDatos[2];
				short hojaCas = Short.parseShort(criteriosDatos[3]);
				short nuCasilla = Short.parseShort(de.getColumnas().get(1));
				
				List<HDocumentoCasillaValorDTO> valoresH = hDocumentoCasillaValorBO.findByCriteria(getCriterio(fecha,mod,vers,coDoc,hojaCas,nuCasilla));
				
				if(valoresH.size() >0) //Hay una actualizacion más reciente
					return valoresH.get(0).getValor();
				else //Cogemos la de documentocasillavalor
				{
					valor = documentoCasillaValorBO.findById(new DocumentoCasillaValorDTOId(mod,vers,coDoc,nuCasilla,hojaCas)).getValor();
					
					return valor;
				}		
			}
			else
				return cols.get(p+1);
		}	
		else if(historicoNombre.equals("casilla"))
		{
			if (p == 1)
			{
				String[] criteriosDatos = historicoBusqueda.split("\\|");
				String mod = criteriosDatos[0];
				String vers = criteriosDatos[1];
				String casillaDesc = casillaModeloBO.findById(new CasillaModeloDTOId(mod,vers,Short.parseShort(de.getColumnas().get(p+1)))).getCodigoDescripcion();
				
				return casillaDesc;
			}
			else if(p == 3)
			{
				Date fecha = null;
				String fecha_s = new String();
				try {
					fecha_s = Utilidades.DDMMYYYYtoYYYYMMDD(de.getFecha()) + " " + de.getHora()+".000999";
					fecha = Utilidades.YYYYMMDDHHMMSSToDate(fecha_s);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String valor = new String();
				String[] criteriosDatos = historicoBusqueda.split("\\|");
				String mod = criteriosDatos[0];
				String vers = criteriosDatos[1];
				String coDoc = criteriosDatos[2];
				short hojaCas = Short.parseShort(criteriosDatos[3]);
				short nuCasilla = Short.parseShort(criteriosDatos[4]);
				
				List<HDocumentoCasillaValorDTO> valoresH = hDocumentoCasillaValorBO.findByCriteria(getCriterio(fecha,mod,vers,coDoc,hojaCas,nuCasilla));
				
				if(valoresH.size() >0) //Hay una actualizacion más reciente
					return valoresH.get(0).getValor();
				else //Cogemos la de documentocasillavalor
				{
					valor = documentoCasillaValorBO.findById(new DocumentoCasillaValorDTOId(mod,vers,coDoc,nuCasilla,hojaCas)).getValor();
					
					return valor;
				}		
			}
			else
				return cols.get(p+1);
		}
		else{
			
			if(de.getColumnasAlinearDerecha() != null && de.getColumnasAlinearDerecha().contains(p+1))
				alinearDerecha = true;
			else
				alinearDerecha = false;
			
			return cols.get(p+1);
		}
	}
	
	private DetachedCriteria getCriterio(Date fecha,String mod,String vers,String coDoc,short hojaCas,short nuCasilla)
	{
		DetachedCriteria criterio = DetachedCriteria.forClass(HDocumentoCasillaValorDTO.class);
		criterio.add(Restrictions.gt("fhActualizacion", fecha));
		criterio.add(Restrictions.eq("coModelo", mod));
		criterio.add(Restrictions.eq("coVersion", vers));
		criterio.add(Restrictions.eq("coDocumento", coDoc));
		criterio.add(Restrictions.eq("hoja", hojaCas));
		criterio.add(Restrictions.eq("nuCasilla", nuCasilla));
		
		criterio.addOrder(Order.asc("fhActualizacion"));
		
		return criterio;
		
	}
	
	public String execute() throws GadirServiceException {
 		LOG.debug(DatosSesion.getLogin() + " está en el execute de HistoricoGenericoAction.");
		LOG.debug("historicoNombre: " + historicoNombre);
		LOG.debug("historicoCriterios: " + historicoCriterios);
		LOG.debug("historicoBusqueda: " + historicoBusqueda);

		if(fechaHasta != null)
		{
			fechaHasta.setHours(23);
			fechaHasta.setMinutes(59);
		}
		
		listaMovimientos = TablaGt.getListaCodigoDescripcion(TablaGtConstants.TABLA_MOVIMIENTOS_CENSO);
		
		if(listaMovimientos.isEmpty())
			setMovimiento("No existen estados disponibles");
		
		//Ver si es usuario experto
		if(ControlTerritorial.isUsuarioExperto())
			experto = true;
		else
			experto = false;
				
		cargarCriteriosSeleccion();
		this.cargarTablaHistorico();
		
		
		//Para que en el caso de error no salga la ventana lateral
		getServletRequest().setAttribute("ventanaBotonLateral", "true");
		
		return INPUT;
	}
	
	private void cargarCriteriosSeleccion() throws GadirServiceException{
		// Criterios de selección
		String criteriosProperty = getText(historicoNombre + ".criterios");
			
		String[] criteriosArray = criteriosProperty.split("\\|");
		String[] criteriosDatos = historicoCriterios.split("\\|");
		listaCriterios = new ArrayList<KeyValue>();
		for (int i=0; i<criteriosArray.length; i++) {
			KeyValue keyValue = new KeyValue();
			if (criteriosArray.length > i && criteriosArray[i] != null) {
				keyValue.setKey(criteriosArray[i]);
			}
			if (criteriosDatos.length > i && criteriosDatos[i] != null) {
				keyValue.setValue(criteriosDatos[i]);
			}
			listaCriterios.add(keyValue);
		}
	}
	
	protected void cargarTablaHistorico() throws GadirServiceException{
		
		try
		{
		
		// Titulos
		String titulosProperty = getText(historicoNombre + ".titulos");
		String[] titulosArray = titulosProperty.split("\\|");
		titulos = new ArrayList<String>();
		for (int i=0; i<titulosArray.length; i++) {
			if (Utilidades.isNotEmpty(titulosArray[i])) {
				titulos.add(titulosArray[i]);
			}
		}
		
		columnas = titulos.size();
		
		AcmUsuarioDTO todos_usuarios = new AcmUsuarioDTO();
		todos_usuarios.setCoAcmUsuario("Todos los usuarios");
		todos_usuarios.setRowid("Todos los usuarios");
		
		listaUsuarios = new ArrayList<AcmUsuarioDTO>();
		
		listaUsuarios.add(todos_usuarios);
		
		listaUsuarios.addAll(acmUsuarioBO.findAll("coAcmUsuario", 1));
		
		if (Utilidades.isEmpty(usuarioRowid) || usuario.equals(listaUsuarios.get(0).getCoAcmUsuario())){
			usuario = null;
			usuarioRowid = null;
		}
		else
		{
			AcmUsuarioDTO acmUsuarioDTO = acmUsuarioBO.findByRowid(usuarioRowid);
			usuario = acmUsuarioDTO.getCoAcmUsuario();
		}
		
	
		
			// Tabla de resultados
			String busquedaColumnasProperty = getText(historicoNombre + ".busqueda");
			String[] busquedaColumnasArray = busquedaColumnasProperty.split("\\|");
			String[] busquedaDatosArray = historicoBusqueda.split("\\|");
			
			String tabla = getText(historicoNombre + ".tabla");
			String select = getText(historicoNombre + ".columnas");
			String where = "";
			if (historicoNombre.equals("festivoCompleto")) {
				String ano = busquedaDatosArray[0];
				String coMunicipioCompleto = busquedaDatosArray[1];
				String fDesde = ano+"0101";
				String fHasta = (Integer.parseInt(ano)+1)+"0101";
				where += "DIA >=to_date('"+fDesde+"','YYYYMMDD')";
				where += " AND DIA <to_date('"+fHasta+"','YYYYMMDD')";
				where += " AND CO_PROVINCIA='" + coMunicipioCompleto.substring(0, 2) + "'";
				where += " AND CO_MUNICIPIO='" + coMunicipioCompleto.substring(2, 5) + "'";
			} else {
				for (int i=0; i<busquedaColumnasArray.length; i++) {
					if (i > 0) {
						where += " AND ";
					}
					where += busquedaColumnasArray[i] + "='" + busquedaDatosArray[i] + "'";
				}
			}
			
			int filasTotal = historicoGenericoBO.execute(tabla, "", where, fechaDesde, fechaHasta, usuario, movimientoKey);	
			
			if(historicoNombre.equals("casilla.hoja") || historicoNombre.equals("casilla"))
			{
				if(!informe){
					historicoTabla = historicoGenericoBO.executeCasillas(tabla, select, where, fechaDesde, fechaHasta, usuario, movimientoKey, porPagina, page);
				}
				else{
					if(filasTotal<1000){
						historicoTabla = historicoGenericoBO.executeCasillas(tabla, select, where, fechaDesde, fechaHasta, usuario, movimientoKey, filasTotal, 1);
					}
				}
//			} else if (historicoNombre.equals("festivoCompleto")) {
//				if(!informe){
//					historicoTabla = historicoGenericoBO.executeFestivoCompleto(tabla, select, where, fechaDesde, fechaHasta, usuario, movimientoKey, porPagina, page, busquedaDatosArray[0], busquedaDatosArray[1]);
//				}
//				else{
//					if(filasTotal<1000){
//						historicoTabla = historicoGenericoBO.executeFestivoCompleto(tabla, select, where, fechaDesde, fechaHasta, usuario, movimientoKey, filasTotal, 1, busquedaDatosArray[0], busquedaDatosArray[1]);
//					}
//				}
			}
			else
			{
				if(!informe){
					historicoTabla = historicoGenericoBO.execute(tabla, select, where, fechaDesde, fechaHasta, usuario, movimientoKey, porPagina, page);
				}
				else{
					if(filasTotal<1000){
						historicoTabla = historicoGenericoBO.execute(tabla, select, where, fechaDesde, fechaHasta, usuario, movimientoKey, filasTotal, 1);
					}
				}
			}
		    
			for(int i=0; i<historicoTabla.size();i++)
			{
				String mov = historicoTabla.get(i).getMovimiento();
				historicoTabla.get(i).setMovimiento(TablaGt.getValor(TablaGtConstants.TABLA_MOVIMIENTOS_CENSO, mov, TablaGt.COLUMNA_DESCRIPCION));
			}
			
			if(!informe)
				listadoHistorico = new GadirPaginatedList(historicoTabla, filasTotal, porPagina, page, "historicos", sort, dir);
			else
				if(filasTotal<1000 ){
					listadoHistorico = new GadirPaginatedList(historicoTabla, filasTotal, filasTotal, 1, "historicos", sort, dir);
				}
			
			if(usuario == null){
				usuario = listaUsuarios.get(0).getCoAcmUsuario();
				usuarioRowid = listaUsuarios.get(0).getCoAcmUsuario();
			}
			
			if(historicoNombre.equals("cliente.domicilio.general") || historicoNombre.equals("casilla") || historicoNombre.equals("casilla.hoja")){
				mostrarMovimiento = false;
			}

		}
		catch(Exception e)
		{
			addActionError("Ha ocurrido un error al realizar el histórico");
			LOG.error(e.getMessage(), e);
		}
	}
	
	
	public String botonFiltrar() throws GadirServiceException {
		return execute();
	}
	
	public String botonAnular() throws GadirServiceException {
		
		setUsuario(""); setUsuarioRowid("");
		setFechaDesde(""); setFechaHasta("");
		setMovimiento(""); setMovimientoKey("");
		
		return execute();
	}

	@SuppressWarnings("unchecked")
	public String botonImprimirHistorico() throws GadirServiceException{
		
		try {
			if(!Utilidades.isEmpty(fechaD))
				fechaDesde = Utilidades.YYYYMMDDToDate(Utilidades.DDMMYYYYtoYYYYMMDD(fechaD));
			if(!Utilidades.isEmpty(fechaH))
				fechaHasta = Utilidades.YYYYMMDDToDate(Utilidades.DDMMYYYYtoYYYYMMDD(fechaH));
			setInforme(true);
			this.cargarTablaHistorico();
			setInforme(false);
			cargarCriteriosSeleccion();

			if(!Utilidades.isNull(listadoHistorico) && listadoHistorico.getFullListSize()<1000)
			{
				List<KeyValue> listaEtiquetas = new ArrayList<KeyValue>();
				
				listaEtiquetas.add(new KeyValue("CABEZA"));
				listaEtiquetas.add(new KeyValue("TITUL", getText(historicoNombre + ".titulo")));
				listaEtiquetas.add(new KeyValue("CUERPO"));
				
				
				//CRITERIOS SELECCIÓN PROPIOS
				if(historicoNombre.equals("usuario")){
					listaEtiquetas.add(new KeyValue("CO_US", listaCriterios.get(0).getValue()));
				}
				else if(historicoNombre.equals("codter")){
					listaEtiquetas.add(new KeyValue("CO_TE", listaCriterios.get(0).getValue()));
				}
				else{
					for(KeyValue k: listaCriterios){
						listaEtiquetas.add(new KeyValue("LINEAcriterios"));
						listaEtiquetas.add(new KeyValue("TI_GE", k.getKey()));
						listaEtiquetas.add(new KeyValue("CO_GE", k.getValue()));
					}
				}
				
				//CRITERIOS SELECCIÓN COMUNES
				
				listaEtiquetas.add(new KeyValue("LINEAcomunes"));
				
				if(!Utilidades.isEmpty(usuario))
					listaEtiquetas.add(new KeyValue("USUAR", usuario));
				else
					listaEtiquetas.add(new KeyValue("USUAR", "Todos los usuarios"));
				
				if(!Utilidades.isEmpty(fechaDesde))
					listaEtiquetas.add(new KeyValue("FE_DE", Utilidades.dateToDDMMYYYY(fechaDesde)));
				else
					listaEtiquetas.add(new KeyValue("FE_DE", "-"));
				
				if(!Utilidades.isEmpty(fechaHasta))
					listaEtiquetas.add(new KeyValue("FE_HA", Utilidades.dateToDDMMYYYY(fechaHasta)));
				else
					listaEtiquetas.add(new KeyValue("FE_HA", "-"));
				
				if(!Utilidades.isEmpty(movimiento))
					listaEtiquetas.add(new KeyValue("MOVIM", movimiento));
				else
					listaEtiquetas.add(new KeyValue("MOVIM", "Todos"));
				
				
				HistoricoGenericoVO obj;
				if(listadoHistorico.getFullListSize()>0 ){
					
					List<HistoricoGenericoVO> de = (List<HistoricoGenericoVO>)listadoHistorico.getList();
					List<String> cols;
					
					for(int i= 0; i<de.size(); i++)
					{
						obj = de.get(i);
						
						//LINEA DE LA TABLA
						if(historicoNombre.equals("usuario")){
							listaEtiquetas.add(new KeyValue("LINEAusuarios"));
						}
						else if(historicoNombre.equals("codter")){
							listaEtiquetas.add(new KeyValue("LINEAcodigosTerritoriales"));
						}
						else{
							listaEtiquetas.add(new KeyValue("LINEAgenerico"));
						}
							
						//DATOS TABLA COMUNES
						
						if(historicoNombre.equals("usuario") || historicoNombre.equals("codter")){
							if(!Utilidades.isEmpty(obj.getFecha()))
								listaEtiquetas.add(new KeyValue("FECTA", obj.getFecha()));
							else
								listaEtiquetas.add(new KeyValue("FECTA", ""));
							
							if(!Utilidades.isEmpty(obj.getHora()))
								listaEtiquetas.add(new KeyValue("HORTA", obj.getHora()));
							else
								listaEtiquetas.add(new KeyValue("HORTA", ""));
							
							if(!Utilidades.isEmpty(obj.getUsuario()))
								listaEtiquetas.add(new KeyValue("USUTA", obj.getUsuario()));
							else
								listaEtiquetas.add(new KeyValue("USUTA", ""));
							
							if(!Utilidades.isEmpty(obj.getMovimiento()))
								listaEtiquetas.add(new KeyValue("MOVTA", obj.getMovimiento()));
							else
								listaEtiquetas.add(new KeyValue("MOVTA", ""));	
						}
						else{
							if(!Utilidades.isEmpty(obj.getFecha()))
								if(!Utilidades.isEmpty(obj.getHora()))
									listaEtiquetas.add(new KeyValue("FEYHO", obj.getFecha() + " - " + obj.getHora()));
								else
									listaEtiquetas.add(new KeyValue("FEYHO", obj.getFecha()));
							else
								if(!Utilidades.isEmpty(obj.getHora()))
									listaEtiquetas.add(new KeyValue("FEYHO", obj.getHora()));
								else
									listaEtiquetas.add(new KeyValue("FEYHO", ""));

							if(!Utilidades.isEmpty(obj.getUsuario()))
								if(!Utilidades.isEmpty(obj.getMovimiento()) && mostrarMovimiento)
									listaEtiquetas.add(new KeyValue("USUMO", obj.getUsuario() + " - " + obj.getMovimiento()));
								else
									listaEtiquetas.add(new KeyValue("USUMO", obj.getUsuario()));
							else
								if(!Utilidades.isEmpty(obj.getMovimiento()) && mostrarMovimiento)
									listaEtiquetas.add(new KeyValue("USUMO", obj.getMovimiento()));
								else
									listaEtiquetas.add(new KeyValue("USUMO", ""));
						}
						
						cols = new ArrayList<String>();
						cols = obj.getColumnas();
						
						//COLUMNAS PROPIAS
						if(historicoNombre.equals("usuario")){
							
							if(!Utilidades.isEmpty(cols.get(1)))
								listaEtiquetas.add(new KeyValue("CO_TG", cols.get(1)));
							else
								listaEtiquetas.add(new KeyValue("CO_TG", ""));
							
							if(!Utilidades.isEmpty(cols.get(2)))
								listaEtiquetas.add(new KeyValue("CO_TE", cols.get(2)));
							else
								listaEtiquetas.add(new KeyValue("CO_TE", ""));
							
							if(!Utilidades.isEmpty(cols.get(3)))
								listaEtiquetas.add(new KeyValue("CO_UA", cols.get(3)));
							else
								listaEtiquetas.add(new KeyValue("CO_UA", ""));
							
							if(!Utilidades.isEmpty(cols.get(4)))
								listaEtiquetas.add(new KeyValue("PERFL", cols.get(4)));
							else
								listaEtiquetas.add(new KeyValue("PERFL", ""));
							
							if(!Utilidades.isEmpty(cols.get(5)))
								listaEtiquetas.add(new KeyValue("CARGO", cols.get(5)));
							else
								listaEtiquetas.add(new KeyValue("CARGO", ""));
							
							if(!Utilidades.isEmpty(cols.get(6)))
								listaEtiquetas.add(new KeyValue("IMPRE", cols.get(6)));
							else
								listaEtiquetas.add(new KeyValue("IMPRE", ""));
							
							if(!Utilidades.isEmpty(cols.get(7)))
								listaEtiquetas.add(new KeyValue("ESTAD", cols.get(7)));
							else
								listaEtiquetas.add(new KeyValue("ESTAD", ""));			

						}
						else if(historicoNombre.equals("codter")){
							
							if(!Utilidades.isEmpty(cols.get(1)))
								listaEtiquetas.add(new KeyValue("NOMTA", cols.get(1)));
							else
								listaEtiquetas.add(new KeyValue("NOMTA", ""));
						}
						
						//COLUMNAS COMUNES
						else{
							
							for(int j=1; j<=columnas ; j++)
							{
								listaEtiquetas.add(new KeyValue("LINEAgenerico"));
								listaEtiquetas.add(new KeyValue("FEYHO", "           " + titulos.get(j-1)));
								if(!Utilidades.isEmpty(cols.get(j-1)))
									listaEtiquetas.add(new KeyValue("USUMO", "           " + cols.get(j-1)));
								else
									listaEtiquetas.add(new KeyValue("USUMO", ""));
							}
						}
					}
				}
				else
				{
					listaEtiquetas.add(new KeyValue("USUAR", "Todos los usuarios"));
					listaEtiquetas.add(new KeyValue("FE_DE", "-"));
					listaEtiquetas.add(new KeyValue("FE_HA", "-"));
					listaEtiquetas.add(new KeyValue("MOVIM", "Todos"));
					
					if(historicoNombre.equals("usuario")){
						listaEtiquetas.add(new KeyValue("CO_US", ""));
						listaEtiquetas.add(new KeyValue("LINEAusuarios"));
						listaEtiquetas.add(new KeyValue("CO_TG", ""));
						listaEtiquetas.add(new KeyValue("CO_TE", ""));
						listaEtiquetas.add(new KeyValue("CO_UAD", ""));
						listaEtiquetas.add(new KeyValue("PERFL", ""));
						listaEtiquetas.add(new KeyValue("CARGO", ""));
						listaEtiquetas.add(new KeyValue("IMPRE", ""));
						listaEtiquetas.add(new KeyValue("ESTAD", ""));
					}
					else if(historicoNombre.equals("codter")){
						listaEtiquetas.add(new KeyValue("CO_TE", ""));
						listaEtiquetas.add(new KeyValue("LINEAcodigosTerritoriales"));
						listaEtiquetas.add(new KeyValue("NOMTA", ""));
					}
					else{
						listaEtiquetas.add(new KeyValue("CO_GE", ""));
						listaEtiquetas.add(new KeyValue("TI_GE", ""));
						listaEtiquetas.add(new KeyValue("LINEAgenerico"));
						listaEtiquetas.add(new KeyValue("FEYHO", ""));
						listaEtiquetas.add(new KeyValue("USUMO", ""));
					}
				}
				
				if(historicoNombre.equals("usuario"))
					Impresion.merge("historicoUsuario.odt", listaEtiquetas, DatosSesion.getLogin(), getInformeActuacion(), getServletResponse(), getInformeParametro());
				else if(historicoNombre.equals("codter"))
					Impresion.merge("historicoCodigoTerritorial.odt", listaEtiquetas, DatosSesion.getLogin(), getInformeActuacion(), getServletResponse(), getInformeParametro());
				else
					Impresion.merge("historicoGenerico.odt", listaEtiquetas, DatosSesion.getLogin(), getInformeActuacion(), getServletResponse(), getInformeParametro());
		
			}
			else{
				addActionError(Mensaje.getTexto(MensajeConstants.V1,"El listado es demasiado extenso para ser generado. (Máximo: 1000 registros)"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		
		if (getServletResponse().isCommitted()) return null;
		
		return execute();
	}
	

	public String getHistoricoNombre() {
		return historicoNombre;
	}

	public void setHistoricoNombre(String historicoNombre) {
		this.historicoNombre = historicoNombre;
	}

	public String getHistoricoCriterios() {
		return historicoCriterios;
	}

	public void setHistoricoCriterios(String historicoCriterios) {
		this.historicoCriterios = historicoCriterios;
	}

	public List<KeyValue> getListaCriterios() {
		return listaCriterios;
	}

	public void setListaCriterios(List<KeyValue> listaCriterios) {
		this.listaCriterios = listaCriterios;
	}

	public void setHistoricoBusqueda(String historicoBusqueda) {
		this.historicoBusqueda = historicoBusqueda;
	}

	public String getHistoricoBusqueda() {
		return historicoBusqueda;
	}

	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public void setHistoricoGenericoBO(HistoricoGenericoBOImpl historicoGenericoBO) {
		this.historicoGenericoBO = historicoGenericoBO;
	}

	public HistoricoGenericoBOImpl getHistoricoGenericoBO() {
		return historicoGenericoBO;
	}

	public void setHistoricoTabla(List<HistoricoGenericoVO> historicoTabla) {
		this.historicoTabla = historicoTabla;
	}

	public List<HistoricoGenericoVO> getHistoricoTabla() {
		return historicoTabla;
	}

	public void setTitulos(List<String> titulos) {
		this.titulos = titulos;
	}

	public List<String> getTitulos() {
		return titulos;
	}

	public boolean isExperto() {
		return experto;
	}

	public void setExperto(boolean esExperto) {
		this.experto = esExperto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}
	
	public String getFechaDesdeString() {
		return Utilidades.dateToDDMMYYYY(fechaDesde);
	}
	
	public String getFechaHastaString() {
		return Utilidades.dateToDDMMYYYY(fechaHasta);
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = Utilidades.strutsFormatToDate(fechaDesde);
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = Utilidades.strutsFormatToDate(fechaHasta);
	}

	public List<AcmUsuarioDTO> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<AcmUsuarioDTO> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public String getUsuarioRowid() {
		return usuarioRowid;
	}

	public void setUsuarioRowid(String usuarioRowid) {
		this.usuarioRowid = usuarioRowid;
	}

	public AcmUsuarioBO getAcmUsuarioBO() {
		return acmUsuarioBO;
	}

	public void setAcmUsuarioBO(AcmUsuarioBO acmUsuarioBO) {
		this.acmUsuarioBO = acmUsuarioBO;
	}

	public List<KeyValue> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(List<KeyValue> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	public String getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	public String getMovimientoKey() {
		return movimientoKey;
	}

	public void setMovimientoKey(String movimientoKey) {
		this.movimientoKey = movimientoKey;
	}


	public GadirPaginatedList getListadoHistorico() {
		return listadoHistorico;
	}


	public void setListadoHistorico(GadirPaginatedList listadoHistorico) {
		this.listadoHistorico = listadoHistorico;
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
	
	public boolean isInforme() {
		return informe;
	}
	
	public void setInforme(boolean informe) {
		this.informe = informe;
	}
	public void setFechaD(String fechaD) {
		this.fechaD = fechaD;
	}
	public String getFechaD() {
		return fechaD;
	}
	public void setFechaH(String fechaH) {
		this.fechaH = fechaH;
	}
	public String getFechaH() {
		return fechaH;
	}
	public CasillaModeloBO getCasillaModeloBO() {
		return casillaModeloBO;
	}
	public void setCasillaModeloBO(CasillaModeloBO casillaModeloBO) {
		this.casillaModeloBO = casillaModeloBO;
	}

	public HDocumentoCasillaValorBO gethDocumentoCasillaValorBO() {
		return hDocumentoCasillaValorBO;
	}

	public void sethDocumentoCasillaValorBO(
			HDocumentoCasillaValorBO hDocumentoCasillaValorBO) {
		this.hDocumentoCasillaValorBO = hDocumentoCasillaValorBO;
	}

	public DocumentoCasillaValorBO getDocumentoCasillaValorBO() {
		return documentoCasillaValorBO;
	}

	public void setDocumentoCasillaValorBO(
			DocumentoCasillaValorBO documentoCasillaValorBO) {
		this.documentoCasillaValorBO = documentoCasillaValorBO;
	}

	public boolean isMostrarMovimiento() {
		return mostrarMovimiento;
	}

	public void setMostrarMovimiento(boolean mostrarMovimiento) {
		this.mostrarMovimiento = mostrarMovimiento;
	}

	public boolean isAlinearDerecha() {
		return alinearDerecha;
	}

	public void setAlinearDerecha(boolean alinearDerecha) {
		this.alinearDerecha = alinearDerecha;
	}
	
	
	
}
