package es.dipucadiz.etir.comun.taglib.component;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.GenericUIBean;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.dto.CalleDTO;
import es.dipucadiz.etir.comun.dto.CalleUbicacionDTO;
import es.dipucadiz.etir.comun.dto.ClienteDTO;
import es.dipucadiz.etir.comun.dto.DomicilioDTO;
import es.dipucadiz.etir.comun.dto.MunicipioDTO;
import es.dipucadiz.etir.comun.dto.PaisDTO;
import es.dipucadiz.etir.comun.dto.ProvinciaDTO;
import es.dipucadiz.etir.comun.dto.UnidadUrbanaDTO;
import es.dipucadiz.etir.comun.utilidades.DomicilioUtil;
import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class DomicilioTributario extends GenericUIBean {

	private static final Log LOG = LogFactory.getLog(DomicilioTributario.class);

	protected String coDomicilio;
	protected String refCat;
	protected boolean muestraCliente;
	protected String abierto;
	boolean mostrarBotonMaps=false;
	protected String coDomicilioGeo;	
	public DomicilioTributario(ValueStack stack, HttpServletRequest req,
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
		
		
		if (!Utilidades.isEmpty(coDomicilio)) {
			
			try {
				DomicilioDTO domicilioDTO = DomicilioUtil.getDomicilioInicializado(Long.valueOf(coDomicilio), muestraCliente);
				UnidadUrbanaDTO unidadUrbanaDTO = domicilioDTO.getUnidadUrbanaDTO();
				CalleDTO calleDTO = unidadUrbanaDTO.getCalleDTO();
				MunicipioDTO municipioDTO = calleDTO.getMunicipioDTO();
				ProvinciaDTO provinciaDTO = municipioDTO.getProvinciaDTO();
				PaisDTO paisDTO = municipioDTO.getPaisDTO();
				
				String via="";
				String numero="";
				String letra="";
				String planta="";
				String bloque="";
				String km="";
				String escalera="";
				String puerta="";
				
				String ubicacion="";
				String cp="";
				
				String municipio="";
				String provincia="";
				String pais="";
				
				boolean mostrarBotonMaps=false;
				
				via=calleDTO.getSigla() + " " + calleDTO.getNombreCalle();
				if (unidadUrbanaDTO.getNumero()!=null){
					numero=""+unidadUrbanaDTO.getNumero();
				}
				if (unidadUrbanaDTO.getLetra()!=null){
					letra=unidadUrbanaDTO.getLetra();
				}
				if (unidadUrbanaDTO.getPlanta()!=null){
					planta=unidadUrbanaDTO.getPlanta();
				}
				if (unidadUrbanaDTO.getBloque()!=null){
					bloque=unidadUrbanaDTO.getBloque();
				}
				if (unidadUrbanaDTO.getKm()!=null){
					km=""+unidadUrbanaDTO.getKm();
				}
				if (unidadUrbanaDTO.getEscalera()!=null){
					escalera=unidadUrbanaDTO.getEscalera();
				}
				if (unidadUrbanaDTO.getPuerta()!=null){
					puerta=unidadUrbanaDTO.getPuerta();
				}
				if (unidadUrbanaDTO.getCp()!=null && unidadUrbanaDTO.getCp().intValue()!=0){
					cp=""+unidadUrbanaDTO.getCp();
				}
				
				if (unidadUrbanaDTO.getCoordenadaX()!=null && unidadUrbanaDTO.getCoordenadaY()!=null){
					mostrarBotonMaps=true;
					coDomicilioGeo=coDomicilio;
				}
				if (calleDTO.getCalleUbicacionDTO()!=null){
					CalleUbicacionDTO calleUbicacionDTO = calleDTO.getCalleUbicacionDTO();
					ubicacion=calleUbicacionDTO.getUbicacion();
				}
				
				municipio=municipioDTO.getCodigoDescripcion();
				provincia=provinciaDTO.getNombre();
				pais=paisDTO.getNombre();
				
				writer.write("<div class=\"fila\">" + '\n');
				
				writer.write("<script type=\"text/javaScript\">" + '\n');

				writer.write("$(document).ready(function(){" + '\n');	
				writer.write("$('#fieldset-domicilioTributario').click(function(e) {" + '\n');
				writer.write("if ($('#interior-domicilioTributario').css(\"display\") == 'none'){" + '\n');
				writer.write("$('#interior-domicilioTributario').toggle(true);" + '\n');
				writer.write("}else{" + '\n');
				writer.write("$('#interior-domicilioTributario').toggle(false);" + '\n');
				writer.write("}" + '\n');
				writer.write("alturaExtendibles();" + '\n');
				writer.write("});" + '\n');
				writer.write("});" + '\n');
				writer.write("</script>" + '\n');

				writer.write("<fieldset id=\"fieldset-domicilioTributario\" style=\"cursor:pointer; padding: 0pt 0pt 0pt 1.5%\">" + '\n');
				writer.write("<legend id=\"legend-domicilioTributario\">Domicilio tributario</legend>" + '\n');

				writer.write("<div id=\"interior-domicilioTributario\"");
				if (abiertoBoolean != null && !abiertoBoolean) writer.write(" style=\"display: none\"");
				writer.write(">" + '\n');
				
				if (muestraCliente){
					ClienteDTO clienteDTO = domicilioDTO.getClienteDTO();
					writer.write("<div class=\"fila\">" + '\n');
					writer.write("<div style=\"float:left;width:40%\"><label>Nombre:</label>" + clienteDTO.getRazonSocial() + "</div>" + '\n');
					writer.write("<div style=\"float:left;width:25%\"><label>Identificador:</label>" + clienteDTO.getIdentificador() + "</div>" + '\n');
					writer.write("<div style=\"float:left;width:25%\"><label>Código:</label>" + clienteDTO.getCoCliente() + "</div>" + '\n');
					writer.write("</div>" + '\n');
					
				}
				
				writer.write("<div class=\"fila\">" + '\n');
				writer.write("<div style=\"float:left;width:40%\"><label>Vía:</label>" + via + "</div>" + '\n');
				writer.write("<div style=\"float:left;width:8%\"><label>Nº:</label>" + numero + "</div>" + '\n');
				writer.write("<div style=\"float:left;width:8%\"><label>Ltr:</label>" + letra + "</div>" + '\n');
				writer.write("<div style=\"float:left;width:8%\"><label>Blq:</label>" + bloque + "</div>" + '\n');
				writer.write("<div style=\"float:left;width:8%\"><label>Esc:</label>" + escalera + "</div>" + '\n');
				writer.write("<div style=\"float:left;width:8%\"><label>Pla:</label>" + planta + "</div>" + '\n');
				writer.write("<div style=\"float:left;width:8%\"><label>Pta:</label>" + puerta + "</div>" + '\n');
				writer.write("<div style=\"float:left;width:8%\"><label>Km:</label>" + km + "</div>" + '\n');
				writer.write("</div>" + '\n');

				writer.write("<div class=\"fila\">" + '\n');
				writer.write("<div style=\"float:left;width:27%\"><label>Municipio:</label>" + municipio + "</div>" + '\n');
				writer.write("<div style=\"float:left;width:21%\"><label>Provincia:</label>" + provincia + "</div>" + '\n');
				
				if (ubicacion!=null && !ubicacion.equals("")){
					writer.write("<div style=\"float:left;width:21%\"><label>Ubicación:</label>" + ubicacion + "</div>" + '\n');
				} 
				if (cp!=null && !cp.equals("")){
					writer.write("<div style=\"float:left;width:5%\"><label>C.P.:</label>" + cp + "</div>" + '\n');
				}
				
				writer.write("<div style=\"float:left;width:6%\"><label>País:</label>" + pais + "</div>" + '\n');
				if (Utilidades.isEmpty(ubicacion) ){
					writer.write("<div style=\"float:left;width:21%\">&nbsp;</div>" + '\n');
				}
				
				if (mostrarBotonMaps) {
					writer.write("<div style=\"text-align:right;float:left;width:19%;\">");
					writer.write("<script type=\"text/javascript\">");
					writer.write("$(document).ready(function(){");
					writer.write("	downloadJS(document, 'script', '/etir/js/mostrarMapsTrib.js?1.1');");
					writer.write("});");
					writer.write("</script>");
					writer.write("<button id=\"inTagMostrarMapsTrib" + coDomicilioGeo + "\" class=\"inTagMostrarMapsTrib\"><img src=\"/etir/image/iconos/16x16/googleMaps.png\" alt=\"Geolocalización\" style=\"width:16px;height:16px;vertical-align:middle\" /> Geolocalización</button>");
					writer.write("</div>");
				}
				
				writer.write("</div>" + '\n');
				
				if (refCat!=null && !refCat.equals("")){
					writer.write("<div class=\"fila\">" + '\n');
					writer.write("<div style=\"float:left;width:60%\"><label>Ref. catastral:</label>" + refCat + "</div>" + '\n');
					writer.write("</div>" + '\n');
				}

				writer.write("</div>" + '\n');
				writer.write("</fieldset>" + '\n');

				writer.write("</div>" + '\n');
				
			} catch (Exception e) {
				LOG.error("Tag ClienteTag could not print.", e);
			}

		}
			
        return result;
	}

	public String getCoDomicilio() {
		return coDomicilio;
	}

	public void setCoDomicilio(String coDomicilio) {
		this.coDomicilio = coDomicilio;
	}

	public String getRefCat() {
		return refCat;
	}

	public void setRefCat(String refCat) {
		this.refCat = refCat;
	}

	public boolean isMuestraCliente() {
		return muestraCliente;
	}

	public void setMuestraCliente(boolean muestraCliente) {
		this.muestraCliente = muestraCliente;
	}

	public String getAbierto() {
		return abierto;
	}

	public void setAbierto(String abierto) {
		this.abierto = abierto;
	}

	
	
}
