package es.dipucadiz.etir.comun.taglib.component;

import java.io.Writer;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.GenericUIBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.bo.DocumentoBO;
import es.dipucadiz.etir.comun.bo.DocumentoCensoSeguimientoBO;
import es.dipucadiz.etir.comun.bo.DocumentoLiquidacionBO;
import es.dipucadiz.etir.comun.bo.DocumentoSeguimientoBO;
import es.dipucadiz.etir.comun.boStoredProcedure.DeudaBO;
import es.dipucadiz.etir.comun.config.GadirConfig;
import es.dipucadiz.etir.comun.constants.SituacionConstants;
import es.dipucadiz.etir.comun.dto.DocumentoCensoSeguimientoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTO;
import es.dipucadiz.etir.comun.dto.DocumentoDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoLiquidacionDTO;
import es.dipucadiz.etir.comun.dto.DocumentoLiquidacionDTOId;
import es.dipucadiz.etir.comun.dto.DocumentoSeguimientoDTO;
import es.dipucadiz.etir.comun.dto.ModeloDTO;
import es.dipucadiz.etir.comun.utilidades.DomiciliacionUtil;
import es.dipucadiz.etir.comun.utilidades.EstadoSituacionConstants;
import es.dipucadiz.etir.comun.utilidades.JuntaUtil;
import es.dipucadiz.etir.comun.utilidades.MunicipioConceptoModeloUtil;
import es.dipucadiz.etir.comun.utilidades.OpcionesIncidenciaSituacionUtil;
import es.dipucadiz.etir.comun.utilidades.TablaGt;
import es.dipucadiz.etir.comun.utilidades.Utilidades;
import es.dipucadiz.etir.comun.vo.DeudaVO;

public class DatosTributarios extends GenericUIBean {

	private static final Log LOG = LogFactory.getLog(DatosTributarios.class);

	protected String coModelo;
	protected String coVersion;
	protected String coDocumento;
	protected boolean muestraCodigoDocumento;
	protected String abierto;
	
	public DatosTributarios(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		super(stack, req, res);
	}

	@Override
	public boolean start(Writer writer) {
		Boolean abiertoBoolean;
		if (abierto != null) {
			abiertoBoolean = (Boolean) findValue(abierto, Boolean.class);
		} else {
			abiertoBoolean = true;
		}
		
		boolean result = super.start(writer);
		
		
		if (!Utilidades.isEmpty(coModelo) && !Utilidades.isEmpty(coVersion) && !Utilidades.isEmpty(coDocumento) ) {
			
			try {
				DocumentoBO documentoBO = (DocumentoBO)GadirConfig.getBean("documentoBO");
				DocumentoDTO documentoDTO = documentoBO.findById(new DocumentoDTOId(coModelo, coVersion, coDocumento));
				
				String municipio="";
				if (documentoDTO.getMunicipioDTO()!=null){
					municipio = MunicipioConceptoModeloUtil.getMunicipioCodigoDescripcion(documentoDTO.getMunicipioDTO().getId().getCoProvincia(), documentoDTO.getMunicipioDTO().getId().getCoMunicipio());
				}
				String concepto="";
				if (documentoDTO.getConceptoDTO()!=null){
					if("4JA".equals(documentoDTO.getId().getCoModelo()) || "4JN".equals(documentoDTO.getId().getCoModelo())) {
						concepto = JuntaUtil.getDescripcionConceptoJunta(documentoDTO);
					}
					else
						concepto = MunicipioConceptoModeloUtil.getConceptoCodigoDescripcion(documentoDTO.getConceptoDTO().getCoConcepto());
				}
				String situacion="";
				if (documentoDTO.getSituacionDTO()!=null){
					final String separador = " - ";
					situacion = OpcionesIncidenciaSituacionUtil.getCodigoDescripcionSituacionDocumento(documentoDTO.getSituacionDTO().getCoSituacion());
					if (situacion.contains(separador)) {
						situacion = situacion.split(separador)[1];
					}
				}
				
				String estado="";
				String modo="";
				DocumentoLiquidacionDTO documentoLiquidacionDTO = null;
				try{
					DocumentoLiquidacionBO documentoLiquidacionBO = (DocumentoLiquidacionBO)GadirConfig.getBean("documentoLiquidacionBO");
					DetachedCriteria criteriaLiq = DetachedCriteria.forClass(DocumentoLiquidacionDTO.class, "liq");
					criteriaLiq.add(Restrictions.eq("liq.id", new DocumentoLiquidacionDTOId(coModelo, coVersion, coDocumento)));
					criteriaLiq.createAlias("estadoSituacionDTO", "est");
					criteriaLiq.createAlias("modoCobroDTO", "mod");
					criteriaLiq.createAlias("cargoSubcargoDTO", "sc");
					criteriaLiq.createAlias("sc.cargoDTO", "c");
					criteriaLiq.createAlias("otrDTO", "o");
					
					List<DocumentoLiquidacionDTO> dtos = documentoLiquidacionBO.findByCriteria(criteriaLiq);
					
					if (!dtos.isEmpty()) {
						documentoLiquidacionDTO = dtos.get(0);
						if (documentoLiquidacionDTO!=null){
							if (documentoLiquidacionDTO.getEstadoSituacionDTO()!=null){
								estado= documentoLiquidacionDTO.getEstadoSituacionDTO().getNombre();
							}
							if (documentoLiquidacionDTO.getModoCobroDTO()!=null){
								modo= documentoLiquidacionDTO.getModoCobroDTO().getNombre();
							}
						}
					}
					
				}catch(Exception e){
					
				}
				
				String refObj1="";
				if (documentoDTO.getRefObjTributario1()!=null){
					refObj1=documentoDTO.getRefObjTributario1();
				}
				String refObj2="";
				if (documentoDTO.getRefObjTributario2()!=null){
					refObj2=""+documentoDTO.getRefObjTributario2();
				}
				
				String refObj3="";
				if (documentoDTO.getRefObjTributario3()!=null){
					refObj3=documentoDTO.getRefObjTributario3();
				}
				
				String ultimaInc="";
				String fxUltimaInc="";
				if (documentoLiquidacionDTO != null) {
					DocumentoSeguimientoBO documentoSeguimientoBO = (DocumentoSeguimientoBO)GadirConfig.getBean("documentoSeguimientoBO");
					DocumentoSeguimientoDTO documentoSeguimientoDTO = documentoSeguimientoBO.getUltimaIncidencia(coModelo, coVersion, coDocumento);
					if (documentoSeguimientoDTO != null && documentoSeguimientoDTO.getIncidenciaDTO() != null) {
						 
						
						ultimaInc = OpcionesIncidenciaSituacionUtil.getDescripcionIncidenciaDocumento(documentoSeguimientoDTO.getIncidenciaDTO().getCoIncidencia());
 
						
						fxUltimaInc = Utilidades.dateToDDMMYYYY(documentoSeguimientoDTO.getFhActualizacion());
					}
				} else {
					// Hay seguimiento de censo?
					DocumentoCensoSeguimientoBO documentoCensoSeguimientoBO = (DocumentoCensoSeguimientoBO)GadirConfig.getBean("documentoCensoSeguimientoBO");
					DocumentoCensoSeguimientoDTO documentoCensoSeguimientoDTO = documentoCensoSeguimientoBO.getUltimaIncidencia(coModelo, coVersion, coDocumento);
					if (documentoCensoSeguimientoDTO != null && documentoCensoSeguimientoDTO.getIncidenciaDTO() != null) {
						ultimaInc = OpcionesIncidenciaSituacionUtil.getCodigoDescripcionIncidenciaDocumento(documentoCensoSeguimientoDTO.getIncidenciaDTO().getCoIncidencia());
						fxUltimaInc = Utilidades.dateToDDMMYYYY(documentoCensoSeguimientoDTO.getFhActualizacion());
					}
				}
				
				String cargo="";
				String coOtr="";
				String nombreOTR="";
				if (documentoLiquidacionDTO != null) {
					if (documentoLiquidacionDTO.getCargoSubcargoDTO() != null) {
						String anoCargo = documentoLiquidacionDTO.getCargoSubcargoDTO().getCargoDTO().getAnoCargo().toString();
						String nuCargo = documentoLiquidacionDTO.getCargoSubcargoDTO().getCargoDTO().getNuCargo().toString();
						cargo = anoCargo + " / " + nuCargo;
					}
				
				

	
					if (documentoLiquidacionDTO.getOtrDTO()!=null){
						coOtr=documentoLiquidacionDTO.getOtrDTO().getCoOtr();
						nombreOTR=documentoLiquidacionDTO.getOtrDTO().getNombre();
					}
				
				}
				String ejercicio="";
				if (documentoDTO.getEjercicio()!=null){
					ejercicio=String.valueOf(documentoDTO.getEjercicio());
					 
				}
				
				String periodo="";
				if (documentoDTO.getPeriodo()!=null){
					periodo=String.valueOf(documentoDTO.getPeriodo()+"-"+TablaGt.getValor("TPERIOD", documentoDTO.getPeriodo(), "DESCRIPCION")); 
					 
				}				
	
				
				
				writer.write("<div class=\"fila\">" + '\n');
				writer.write("<script type=\"text/javaScript\">" + '\n');

				writer.write("$(document).ready(function(){" + '\n');	
				writer.write("$('#fieldset-datosTributarios').click(function(e) {" + '\n');
				writer.write("if ($('#interior-datosTributarios').css(\"display\") == 'none'){" + '\n');
				writer.write("$('#interior-datosTributarios').toggle(true);" + '\n');
				writer.write("}else{" + '\n');
				writer.write("$('#interior-datosTributarios').toggle(false);" + '\n');
				writer.write("}" + '\n');
				writer.write("alturaExtendibles();" + '\n');
				writer.write("});" + '\n');
				writer.write("});" + '\n');
				writer.write("</script>" + '\n');

				writer.write("<fieldset id=\"fieldset-datosTributarios\" style=\"cursor:pointer; padding: 0pt 0pt 0pt 1.5%\">" + '\n');
				writer.write("<legend id=\"legend-datosTributarios\">Datos tributarios</legend>" + '\n');

				writer.write("<div id=\"interior-datosTributarios\"");
				if (abiertoBoolean != null && !abiertoBoolean) writer.write(" style=\"display: none\"");
				writer.write(">" + '\n');
				
				writer.write("<div class=\"fila\">" + '\n');
				writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:25%\"><label>Municipio:</label>" + municipio + "</div>" + '\n');
				writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:35%\"><label>Concepto:</label>" + concepto + "</div>" + '\n');
				ModeloDTO modeloDTO = MunicipioConceptoModeloUtil.getModeloDTO(coModelo);
				writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:20%\"><label>Modelo:</label>" + modeloDTO.getCodigoDescripcion() + "</div>" + '\n');
				if(!"".equals(cargo))
					writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:18%\"><label>Cargo:</label>" + cargo + "</div>" + '\n');
				writer.write("</div>"  + '\n'); 
				
				
				//aqui ira la información del otr, período y ejercicio. Esta línea nueva solo debe aparecer si el modelo del documento comienza por 2, 3, 4 o 7
				if(coModelo.startsWith("2") || coModelo.startsWith("3") ||  coModelo.startsWith("4") ||  coModelo.startsWith("7")){
					writer.write("<div class=\"fila\">" + '\n');
					writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:38%\"><label>Otr:</label>" + coOtr+"-"+nombreOTR + "</div>" + '\n');
					writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:30%\"><label>Ejercicio:</label>" + ejercicio + "</div>" + '\n');
					writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:30%\"><label>Periodo:</label>" + periodo + "</div>" + '\n');
					writer.write("</div>" + '\n'); 				
				}
				writer.write("<div class=\"fila\">" + '\n');
				
				if (refObj1!=null && !refObj1.equals("")){
					writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:38%\"><label>Ref. Trib. 1:</label>" + refObj1 + "</div>" + '\n');
				}
				
				writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:30%\"><label>Ref. Trib. 2:</label>");
				if (refObj2!=null && !refObj2.equals("")){
					writer.write( refObj2 + "</div>" + '\n');
				}else{
					writer.write("</div>" + '\n');			    	   
				}
				
				if (refObj3!=null && !refObj3.equals("")){
				writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:30%\"><label>Ref. Trib. 3:</label>" + refObj3 + "</div>" + '\n');
				}

				writer.write("</div>" + '\n');

				if (Utilidades.isNotEmpty(situacion) || Utilidades.isNotEmpty(ultimaInc) || Utilidades.isNotEmpty(fxUltimaInc)){
					writer.write("<div class=\"fila\">" + '\n');
					if (Utilidades.isNotEmpty(situacion)){
						writer.write("<div style=\"float:left;width:38%\"><label>Situación/Estado/Cobro:</label>" + situacion + (estado.isEmpty()?"":("/ " + estado)) + (modo.isEmpty()?"":("/ " + modo)) + "</div>" + '\n');
					}
					if (Utilidades.isNotEmpty(ultimaInc)) {
						writer.write("<div style=\"float:left;width:30%\"><label>Última incidencia:</label>" + ultimaInc + " "+fxUltimaInc+"</div>" + '\n');
					}
					if(documentoLiquidacionDTO != null && documentoLiquidacionDTO.getFxIniVoluntario()!=null){
						writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:30%\"><label>Período Voluntaria:</label>" + Utilidades.dateToDDMMYYYY(documentoLiquidacionDTO.getFxIniVoluntario()) +" a ");
						if(documentoLiquidacionDTO.getFxFinVoluntario()!=null){
							writer.write(Utilidades.dateToDDMMYYYY(documentoLiquidacionDTO.getFxFinVoluntario())+"</div>" + '\n');
						}else{
							writer.write("</div>" + '\n');
						}
					}
					writer.write("</div>" + '\n');
				}
				
				if (documentoDTO.getDomiciliacionDTO() !=null){
					writer.write("<div class=\"fila\">" + '\n');
					writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:38%\"><label>Domiciliación:</label>" +
							DomiciliacionUtil.getCuentaDomiciliacion(documentoDTO.getDomiciliacionDTO().getCoDomiciliacion()) +
							"</div>" + '\n');
					writer.write("</div>" + '\n');
				}
				//falta la parte de los importes
				if (documentoLiquidacionDTO!=null){
					DeudaBO deudaBO = (DeudaBO)GadirConfig.getBean("deudaBO");
					DeudaVO deudaVO= deudaBO.execute(documentoLiquidacionDTO.getId().getCoModelo(), 
							documentoLiquidacionDTO.getId().getCoVersion(), 
							documentoLiquidacionDTO.getId().getCoDocumento());		

					if(documentoLiquidacionDTO!=null && deudaVO !=null){
					
//					if(SituacionConstants.CO_SITUACION_PENDIENTE.equalsIgnoreCase(documentoDTO.getSituacionDTO().getCoSituacion())){
//						
//						 
//						
//					}else if (SituacionConstants.CO_SITUACION_PAGADO.equalsIgnoreCase(documentoDTO.getSituacionDTO().getCoSituacion())){
						//no aparecerá estimado, fecha igual a fx_demora_hasta y los importes estan calculados no hay que llamr a fecha
						//cuando esta cobrado lo que esta en tabla
						writer.write("<div class=\"fila\">" + '\n');
						BigDecimal resultado = BigDecimal.ZERO;

						if(documentoLiquidacionDTO.getImPrincipal()!=null){
							writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:15%;font-size=1.8em\"><label>Principal:</label><p  style=\"font-size=1.15em !important; width:60%; display: inline;\">"  + Utilidades.bigDecimalToImporteGadir(documentoLiquidacionDTO.getImPrincipal()) +"€</p></div>" + '\n');	
							resultado =resultado.add(documentoLiquidacionDTO.getImPrincipal());
						}
						BigDecimal recargo = BigDecimal.ZERO;
						if(documentoLiquidacionDTO.getImRecargoApremio()!=null){
							recargo= documentoLiquidacionDTO.getImRecargoApremio();
						}
							writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:15%;font-size=1.8em\"><label>Recargo:</label><p  style=\"font-size=1.15em !important; width:60%; display: inline;\">"  + Utilidades.bigDecimalToImporteGadir(recargo) +"€</p></div>" + '\n');	
							resultado =resultado.add(recargo);
							
						
						//si es en ejecutiva y pagado tiene que salir la fecha
						//para la fecha si es en ejecutiva y pendiente tiene q salir Estimado
						// y si es en voluntaria no tiene q salir nada
						if(SituacionConstants.CO_SITUACION_PAGADO.equalsIgnoreCase(documentoDTO.getSituacionDTO().getCoSituacion()) && EstadoSituacionConstants.EJECUTIVA .equals(documentoLiquidacionDTO.getEstadoSituacionDTO().getCoEstadoSituacion())){	
							//suma de intereses		cobrados y pendinetes	
							//este con fecha de con fecha de la tabla
							if(documentoLiquidacionDTO.getImCobradoIntereses()==null){
								documentoLiquidacionDTO.setImCobradoIntereses(BigDecimal.ZERO);
							}							 
							BigDecimal suma = documentoLiquidacionDTO.getImCobradoIntereses().add(deudaVO.getImDemoraPendiente());
							writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:20%;font-size=1.8em\"><label>Interés:</label><p  style=\"font-size=1.15em !important; width:60%; display: inline;\">"  + Utilidades.bigDecimalToImporteGadir(suma) /* +" al "+Utilidades.dateToDDMMYYYY(documentoLiquidacionDTO.getFxDemoraHasta())*/+"</p></div>" + '\n');	
							resultado =resultado.add(suma);
						}  else if ((SituacionConstants.CO_SITUACION_PENDIENTE.equalsIgnoreCase(documentoDTO.getSituacionDTO().getCoSituacion()) || SituacionConstants.CO_SITUACION_SUSPENDIDO.equalsIgnoreCase(documentoDTO.getSituacionDTO().getCoSituacion()) ) && EstadoSituacionConstants.EJECUTIVA .equals(documentoLiquidacionDTO.getEstadoSituacionDTO().getCoEstadoSituacion())){
							//este estimado
							if(documentoLiquidacionDTO.getImCobradoIntereses()==null){
										documentoLiquidacionDTO.setImCobradoIntereses(BigDecimal.ZERO);
							}	
							BigDecimal suma = documentoLiquidacionDTO.getImCobradoIntereses().add(deudaVO.getImDemoraPendiente()); 
							String estimado = "";
							if(BigDecimal.ZERO.compareTo(deudaVO.getImPrincipalPendiente()) != 0)
								estimado = " *Estimado al "+Utilidades.dateToDDMMYYYY(new Date());
							writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:20%;font-size=1.8em\"><label>Interés:</label><p  style=\"font-size=1.15em !important; width:60%; display: inline;\">"  + Utilidades.bigDecimalToImporteGadir(suma) + estimado +"</p></div>" + '\n');	
							resultado =resultado.add(suma);
						
						}else if (EstadoSituacionConstants.VOLUNTARIA .equals(documentoLiquidacionDTO.getEstadoSituacionDTO().getCoEstadoSituacion())){
							//este sin fecha
							
							if(documentoLiquidacionDTO.getImCobradoIntereses()==null){
										documentoLiquidacionDTO.setImCobradoIntereses(BigDecimal.ZERO);
							}
							writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:20%;font-size=1.8em\"><label>Interés:</label><p  style=\"font-size=1.15em !important; width:60%; display: inline;\">"  + Utilidades.bigDecimalToImporteGadir(documentoLiquidacionDTO.getImCobradoIntereses())+"</p></div>" + '\n');	
							resultado =resultado.add(documentoLiquidacionDTO.getImCobradoIntereses());
							
						}
						if(documentoLiquidacionDTO.getImCostas() !=null ||deudaVO.getImCostasPendiente()!=null ){	
							
							if(documentoLiquidacionDTO.getImCostas()==null){
								documentoLiquidacionDTO.setImCostas(BigDecimal.ZERO);
							}	
							writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:15%;font-size=1.8em\"><label>Costas:</label><p  style=\"font-size=1.15em !important; width:60%; display: inline;\">"  + Utilidades.bigDecimalToImporteGadir(documentoLiquidacionDTO.getImCostas()) +"</p></div>" + '\n');	
							resultado =resultado.add(documentoLiquidacionDTO.getImCostas());
						}	
					 	  					 
						writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:15%\"><label style=\"font-size=1.8em\">Total:</label><p  style=\"font-size=1.2em !important; width:60%; display: inline;\">" + Utilidades.bigDecimalToImporteGadir(resultado) +"</p></div>" + '\n');	
							 
					 
//						if(deudaVO.getImTotalPendiente() !=null){						 
//							writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:15%\"><label style=\"font-size=1.8em\">Total:</label><p  style=\"font-size=1.2em !important; width:60%; display: inline;\">" + Utilidades.bigDecimalToImporteGadir(resultado) +"</p></div>" + '\n');	
//							 
//						}
						writer.write("<div style=\"float:left;white-space:nowrap;overflow:hidden;width:15%;font-size=1.8em\"><label>Total Pendiente:</label><p  style=\"font-size=1.15em !important; width:60%; display: inline;\">"  + Utilidades.bigDecimalToImporteGadir(deudaVO.getImTotalPendiente()) +"</p></div>" + '\n');	
						 						
						writer.write("</div>" + '\n');

					 
				}
					
				}	
				
				writer.write("</div>" + '\n');
				writer.write("</fieldset>" + '\n');
				writer.write("</div>" + '\n');
				
			} catch (Exception e) {
				LOG.error("Tag DatosTributariosTag could not print.", e);
			}

		}
			
        return result;
	}

	public String getCoModelo() {
		return coModelo;
	}

	public void setCoModelo(String coModelo) {
		this.coModelo = coModelo;
	}

	public String getCoVersion() {
		return coVersion;
	}

	public void setCoVersion(String coVersion) {
		this.coVersion = coVersion;
	}

	public String getCoDocumento() {
		return coDocumento;
	}

	public void setCoDocumento(String coDocumento) {
		this.coDocumento = coDocumento;
	}

	public boolean isMuestraCodigoDocumento() {
		return muestraCodigoDocumento;
	}

	public void setMuestraCodigoDocumento(boolean muestraCodigoDocumento) {
		this.muestraCodigoDocumento = muestraCodigoDocumento;
	}

	public String getAbierto() {
		return abierto;
	}

	public void setAbierto(String abierto) {
		this.abierto = abierto;
	}

	
}
