package es.dipucadiz.etir.comun.taglib.component;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.GenericUIBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.utilidades.Utilidades;

public class Pestanas extends GenericUIBean {

	private static final Log LOG = LogFactory.getLog(Pestanas.class);
	private ResourceBundle resourceBundle;
	
	protected String action;
	protected String subpestana;
	protected String ulStyle;

	protected String action0;
	protected String action1;
	protected String action2;
	protected String action3;
	protected String action4;
	protected String action5;
	protected String action6;
	protected String action7;
	protected String action8;
	protected String action9;
	protected String action10;
	protected String action11;

	protected String action12;
	protected String action13;
	protected String action14;
	
	protected String nombre0;
	protected String metodo0;
	protected String excludeParam0;
	
	protected String nombre1;
	protected String metodo1;
	protected String excludeParam1;
	
	protected String nombre2;
	protected String metodo2;
	protected String excludeParam2;
	
	protected String nombre3;
	protected String metodo3;	
	protected String excludeParam3;
	
	protected String nombre4;
	protected String metodo4;	
	protected String excludeParam4;
	
	protected String nombre5;
	protected String metodo5;
	protected String excludeParam5;
	
	protected String nombre6;
	protected String metodo6;
	protected String excludeParam6;
	
	protected String nombre7;
	protected String metodo7;
	protected String excludeParam7;
	
	protected String nombre8;
	protected String metodo8;
	protected String excludeParam8;
	
	protected String nombre9;
	protected String metodo9;
	protected String excludeParam9;
	
	protected String nombre10;
	protected String metodo10;
	protected String excludeParam10;
	
	protected String nombre11;
	protected String metodo11;
	protected String excludeParam11;
	
	
	protected String nombre12;
	protected String metodo12;
	protected String excludeParam12;
	
	protected String nombre13;
	protected String metodo13;
	protected String excludeParam13;
	
	protected String nombre14;
	protected String metodo14;
	protected String excludeParam14;
	
	protected String marcada0;
	protected String marcada1;
	protected String marcada2;
	protected String marcada3;
	protected String marcada4;
	protected String marcada5;
	protected String marcada6;
	protected String marcada7;
	protected String marcada8;
	protected String marcada9;
	protected String marcada10;
	protected String marcada11;

	protected String marcada12;
	protected String marcada13;
	protected String marcada14;
	
	protected String visible0;
	protected String visible1;
	protected String visible2;
	protected String visible3;
	protected String visible4;
	protected String visible5;
	protected String visible6;
	protected String visible7;
	protected String visible8;
	protected String visible9;
	protected String visible10;
	protected String visible11;

	protected String visible12;
	protected String visible13;
	protected String visible14;
	
	protected int tabNum;
	protected String cadenaParams[] = new String[13];
	
	
	public Pestanas(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		
		String nombreParam="";
		if (subpestana!=null && subpestana.equals("true")){
			nombreParam = "subpestana";
		}else if(subpestana!=null && subpestana.equals("subsubpestana")){
			nombreParam = "subsubpestana";
		}else{
			nombreParam = "pestana";
		}
		
		
		if (Utilidades.isEmpty(action) && Utilidades.isEmpty(action0)) {
			ActionInvocation ai = (ActionInvocation) this.getStack().getContext().get(ActionContext.ACTION_INVOCATION);
			action = ai.getProxy().getActionName();
		}
		
		DefaultTextProvider defaultTextProvider = new DefaultTextProvider();
		resourceBundle = defaultTextProvider.getTexts("comun/i18n/pestanas");
		
		try {
			if (Utilidades.isNotEmpty(ulStyle)) {
				ulStyle = " style=\"" + ulStyle + "\"";
			} else {
				ulStyle = "";
			}
			
			writer.write("<ul class=\"tabs\"" + ulStyle + ">" + '\n');
			if (mostrarPestana(visible0, "X", "X")) { // "X" para asegurar que la pestana0 sea visible
				writer.write("<li class=\""+ (tabNum==0?"current":"none") +"\">" + '\n');
				if (tabNum!=0){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action0) + "!" + metodo0 + ".action?" + nombreParam + "=0" + cadenaParams[0] + "\" " + estiloLink(marcada0) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada0) + ">");
				}
				writer.write(" " + nombrePestana(nombre0) + " ");
				if (tabNum!=0){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible1, nombre1, metodo1)){
				writer.write("<li class=\""+ (tabNum==1?"current":"none") +"\">" + '\n');
				if (tabNum!=1){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action1) + "!" + metodo1 + ".action?" + nombreParam + "=1" + cadenaParams[1] + "\" " + estiloLink(marcada1) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada1) + ">");
				}
				writer.write(" " + nombrePestana(nombre1) + " ");
				if (tabNum!=1){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible2, nombre2, metodo2)){
				writer.write("<li class=\""+ (tabNum==2?"current":"none") +"\">" + '\n');
				if (tabNum!=2){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action2) + "!" + metodo2 + ".action?" + nombreParam + "=2" + cadenaParams[2] + "\" " + estiloLink(marcada2) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada2) + ">");
				}
				writer.write(" " + nombrePestana(nombre2) + " ");
				if (tabNum!=2){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible3, nombre3, metodo3)){
				writer.write("<li class=\""+ (tabNum==3?"current":"none") +"\">" + '\n');
				if (tabNum!=3){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action3) + "!" + metodo3 + ".action?" + nombreParam + "=3" + cadenaParams[3] + "\" " + estiloLink(marcada3) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada3) + ">");
				}
				writer.write(" " + nombrePestana(nombre3) + " ");
				if (tabNum!=3){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible4, nombre4, metodo4)){
				writer.write("<li class=\""+ (tabNum==4?"current":"none") +"\">" + '\n');
				if (tabNum!=4){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action4) + "!" + metodo4 + ".action?" + nombreParam + "=4" + cadenaParams[4] + "\" " + estiloLink(marcada4) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada4) + ">");
				}
				writer.write(" " + nombrePestana(nombre4) + " ");
				if (tabNum!=4){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible5, nombre5, metodo5)){
				writer.write("<li class=\""+ (tabNum==5?"current":"none") +"\">" + '\n');
				if (tabNum!=5){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action5) + "!" + metodo5 + ".action?" + nombreParam + "=5" + cadenaParams[5] + "\" " + estiloLink(marcada5) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada5) + ">");
				}
				writer.write(" " + nombrePestana(nombre5) + " ");
				if (tabNum!=5){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible6, nombre6, metodo6)){
				writer.write("<li class=\""+ (tabNum==6?"current":"none") +"\">" + '\n');
				if (tabNum!=6){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action6) + "!" + metodo6 + ".action?" + nombreParam + "=6" + cadenaParams[6] + "\" " + estiloLink(marcada6) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada6) + ">");
				}
				writer.write(" " + nombrePestana(nombre6) + " ");
				if (tabNum!=6){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible7, nombre7, metodo7)){
				writer.write("<li class=\""+ (tabNum==7?"current":"none") +"\">" + '\n');
				if (tabNum!=7){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action7) + "!" + metodo7 + ".action?" + nombreParam + "=7" + cadenaParams[7] + "\" " + estiloLink(marcada7) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada7) + ">");
				}
				writer.write(" " + nombrePestana(nombre7) + " ");
				if (tabNum!=7){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible8, nombre8, metodo8)){
				writer.write("<li class=\""+ (tabNum==8?"current":"none") +"\">" + '\n');
				if (tabNum!=8){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action8) + "!" + metodo8 + ".action?" + nombreParam + "=8" + cadenaParams[8] + "\" " + estiloLink(marcada8) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada8) + ">");
				}
				writer.write(" " + nombrePestana(nombre8) + " ");
				if (tabNum!=8){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible9, nombre9, metodo9)){
				writer.write("<li class=\""+ (tabNum==9?"current":"none") +"\">" + '\n');
				if (tabNum!=9){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action9) + "!" + metodo9 + ".action?" + nombreParam + "=9" + cadenaParams[9] + "\" " + estiloLink(marcada9) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada9) + ">");
				}
				writer.write(" " + nombrePestana(nombre9) + " ");
				if (tabNum!=9){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible10, nombre10, metodo10)){
				writer.write("<li class=\""+ (tabNum==10?"current":"none") +"\">" + '\n');
				if (tabNum!=10){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action10) + "!" + metodo10 + ".action?" + nombreParam + "=10" + cadenaParams[10] + "\" " + estiloLink(marcada10) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada10) + ">");
				}
				writer.write(" " + nombrePestana(nombre10) + " ");
				if (tabNum!=10){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible11, nombre11, metodo11)){
				writer.write("<li class=\""+ (tabNum==11?"current":"none") +"\">" + '\n');
				if (tabNum!=11){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action11) + "!" + metodo11 + ".action?" + nombreParam + "=11" + cadenaParams[11] + "\" " + estiloLink(marcada11) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada11) + ">");
				}
				writer.write(" " + nombrePestana(nombre11) + " ");
				if (tabNum!=11){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			
			if(mostrarPestana(visible12, nombre12, metodo12)){
				writer.write("<li class=\""+ (tabNum==12?"current":"none") +"\">" + '\n');
				if (tabNum!=12){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action12) + "!" + metodo12 + ".action?" + nombreParam + "=12" + cadenaParams[12] + "\" " + estiloLink(marcada12) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada12) + ">");
				}
				writer.write(" " + nombrePestana(nombre12) + " ");
				if (tabNum!=12){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible13, nombre13, metodo13)){
				writer.write("<li class=\""+ (tabNum==13?"current":"none") +"\">" + '\n');
				if (tabNum!=13){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action13) + "!" + metodo13 + ".action?" + nombreParam + "=13" + cadenaParams[13] + "\" " + estiloLink(marcada13) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada13) + ">");
				}
				writer.write(" " + nombrePestana(nombre13) + " ");
				if (tabNum!=13){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			if(mostrarPestana(visible14, nombre14, metodo14)){
				writer.write("<li class=\""+ (tabNum==14?"current":"none") +"\">" + '\n');
				if (tabNum!=14){
					writer.write("<a href=\"/etir/" + (!Utilidades.isEmpty(action)?action:action14) + "!" + metodo14 + ".action?" + nombreParam + "=14" + cadenaParams[14] + "\" " + estiloLink(marcada14) + ">");
				}else{
					writer.write("<span" + estiloLink(marcada14) + ">");
				}
				writer.write(" " + nombrePestana(nombre14) + " ");
				if (tabNum!=14){
					writer.write("</a>" + '\n');
				}else{
					writer.write("</span>" + '\n');
				}
				writer.write("</li>" + '\n');
			}
			
			
		} catch (IOException e) {
			LOG.info("Error en pestanas tag", e);
		}
        return result;
	}

	private boolean mostrarPestana(String visible, String nombre, String metodo) {
		return !"false".equals(visible) && !Utilidades.isEmpty(nombre) && !Utilidades.isEmpty(metodo);
	}

	private String estiloLink(String marcada) {
		String result;
		if ("true".equals(marcada)) {
			result = " style=\"color:#ff2400;font-weight:bold\"";
		} else {
			result = "";
		}
		return result;
	}

	@Override
	public boolean end(Writer writer, String body) {
		boolean result = super.end(writer, body);
		try {
			writer.write("</ul>" + '\n');
		} catch (IOException e) {
			LOG.info("Error en pestanas tag", e);
		}
        return result;
	}
	
	private String nombrePestana(String property) {
		Enumeration<String> keysEnum = resourceBundle.getKeys();
		String string = property;
		while (keysEnum.hasMoreElements()) {
			String key = keysEnum.nextElement();
			if (property.equals(key)) {
				string = resourceBundle.getString(property);
				break;
			}
		}
		return string;
	}
	

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction0() {
		return action0;
	}

	public void setAction0(String action0) {
		this.action0 = action0;
	}
	
	public String getAction1() {
		return action1;
	}

	public void setAction1(String action1) {
		this.action1 = action1;
	}
	
	public String getAction2() {
		return action2;
	}

	public void setAction2(String action2) {
		this.action2 = action2;
	}
	
	public String getAction3() {
		return action3;
	}

	public void setAction3(String action3) {
		this.action3 = action3;
	}
	
	public String getAction4() {
		return action4;
	}

	public void setAction4(String action5) {
		this.action5 = action5;
	}
	
	public String getAction5() {
		return action5;
	}

	public void setAction5(String action5) {
		this.action5 = action5;
	}
	
	public String getAction6() {
		return action6;
	}

	public void setAction6(String action6) {
		this.action6 = action6;
	}
	
	public String getAction7() {
		return action7;
	}

	public void setAction7(String action7) {
		this.action7 = action7;
	}
	
	public String getNombre0() {
		return nombre0;
	}

	public void setNombre0(String nombre0) {
		this.nombre0 = nombre0;
	}

	public String getMetodo0() {
		return metodo0;
	}

	public void setMetodo0(String metodo0) {
		this.metodo0 = metodo0;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getMetodo1() {
		return metodo1;
	}

	public void setMetodo1(String metodo1) {
		this.metodo1 = metodo1;
	}

	public int getTabNum() {
		return tabNum;
	}

	public void setTabNum(int tabNum) {
		this.tabNum = tabNum;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public String getMetodo2() {
		return metodo2;
	}

	public void setMetodo2(String metodo2) {
		this.metodo2 = metodo2;
	}

	public String getNombre3() {
		return nombre3;
	}

	public void setNombre3(String nombre3) {
		this.nombre3 = nombre3;
	}

	public String getMetodo3() {
		return metodo3;
	}

	public void setMetodo3(String metodo3) {
		this.metodo3 = metodo3;
	}

	public String getNombre4() {
		return nombre4;
	}

	public void setNombre4(String nombre4) {
		this.nombre4 = nombre4;
	}

	public String getMetodo4() {
		return metodo4;
	}

	public void setMetodo4(String metodo4) {
		this.metodo4 = metodo4;
	}

	public String getNombre5() {
		return nombre5;
	}

	public void setNombre5(String nombre5) {
		this.nombre5 = nombre5;
	}

	public String getMetodo5() {
		return metodo5;
	}

	public void setMetodo5(String metodo5) {
		this.metodo5 = metodo5;
	}

	public String getNombre6() {
		return nombre6;
	}

	public void setNombre6(String nombre6) {
		this.nombre6 = nombre6;
	}

	public String getMetodo6() {
		return metodo6;
	}

	public void setMetodo6(String metodo6) {
		this.metodo6 = metodo6;
	}

	public String getSubpestana() {
		return subpestana;
	}

	public void setSubpestana(String subpestana) {
		this.subpestana = subpestana;
	}

	public String getNombre7() {
		return nombre7;
	}

	public void setNombre7(String nombre7) {
		this.nombre7 = nombre7;
	}

	public String getMetodo7() {
		return metodo7;
	}

	public void setMetodo7(String metodo7) {
		this.metodo7 = metodo7;
	}

	public String getExcludeParam0() {
		return excludeParam0;
	}

	public void setExcludeParam0(String excludeParam0) {
		this.excludeParam0 = excludeParam0;
	}

	public String getExcludeParam1() {
		return excludeParam1;
	}

	public void setExcludeParam1(String excludeParam1) {
		this.excludeParam1 = excludeParam1;
	}

	public String getExcludeParam2() {
		return excludeParam2;
	}

	public void setExcludeParam2(String excludeParam2) {
		this.excludeParam2 = excludeParam2;
	}

	public String getExcludeParam3() {
		return excludeParam3;
	}

	public void setExcludeParam3(String excludeParam3) {
		this.excludeParam3 = excludeParam3;
	}

	public String getExcludeParam4() {
		return excludeParam4;
	}

	public void setExcludeParam4(String excludeParam4) {
		this.excludeParam4 = excludeParam4;
	}

	public String getExcludeParam5() {
		return excludeParam5;
	}

	public void setExcludeParam5(String excludeParam5) {
		this.excludeParam5 = excludeParam5;
	}

	public String getExcludeParam6() {
		return excludeParam6;
	}

	public void setExcludeParam6(String excludeParam6) {
		this.excludeParam6 = excludeParam6;
	}

	public String getExcludeParam7() {
		return excludeParam7;
	}

	public void setExcludeParam7(String excludeParam7) {
		this.excludeParam7 = excludeParam7;
	}

	public String[] getCadenaParams() {
		return cadenaParams;
	}

	public void setCadenaParams(String[] cadenaParams) {
		this.cadenaParams = cadenaParams;
	}

	public String getAction8() {
		return action8;
	}

	public void setAction8(String action8) {
		this.action8 = action8;
	}

	public String getAction9() {
		return action9;
	}

	public void setAction9(String action9) {
		this.action9 = action9;
	}

	public String getAction10() {
		return action10;
	}

	public void setAction10(String action10) {
		this.action10 = action10;
	}

	public String getNombre8() {
		return nombre8;
	}

	public void setNombre8(String nombre8) {
		this.nombre8 = nombre8;
	}

	public String getMetodo8() {
		return metodo8;
	}

	public void setMetodo8(String metodo8) {
		this.metodo8 = metodo8;
	}

	public String getExcludeParam8() {
		return excludeParam8;
	}

	public void setExcludeParam8(String excludeParam8) {
		this.excludeParam8 = excludeParam8;
	}

	public String getNombre9() {
		return nombre9;
	}

	public void setNombre9(String nombre9) {
		this.nombre9 = nombre9;
	}

	public String getMetodo9() {
		return metodo9;
	}

	public void setMetodo9(String metodo9) {
		this.metodo9 = metodo9;
	}

	public String getExcludeParam9() {
		return excludeParam9;
	}

	public void setExcludeParam9(String excludeParam9) {
		this.excludeParam9 = excludeParam9;
	}

	public String getNombre10() {
		return nombre10;
	}

	public void setNombre10(String nombre10) {
		this.nombre10 = nombre10;
	}

	public String getMetodo10() {
		return metodo10;
	}

	public void setMetodo10(String metodo10) {
		this.metodo10 = metodo10;
	}

	public String getExcludeParam10() {
		return excludeParam10;
	}

	public void setExcludeParam10(String excludeParam10) {
		this.excludeParam10 = excludeParam10;
	}

	public String getMarcada0() {
		return marcada0;
	}

	public void setMarcada0(String marcada0) {
		this.marcada0 = marcada0;
	}

	public String getMarcada1() {
		return marcada1;
	}

	public void setMarcada1(String marcada1) {
		this.marcada1 = marcada1;
	}

	public String getMarcada2() {
		return marcada2;
	}

	public void setMarcada2(String marcada2) {
		this.marcada2 = marcada2;
	}

	public String getMarcada3() {
		return marcada3;
	}

	public void setMarcada3(String marcada3) {
		this.marcada3 = marcada3;
	}

	public String getMarcada4() {
		return marcada4;
	}

	public void setMarcada4(String marcada4) {
		this.marcada4 = marcada4;
	}

	public String getMarcada5() {
		return marcada5;
	}

	public void setMarcada5(String marcada5) {
		this.marcada5 = marcada5;
	}

	public String getMarcada6() {
		return marcada6;
	}

	public void setMarcada6(String marcada6) {
		this.marcada6 = marcada6;
	}

	public String getMarcada7() {
		return marcada7;
	}

	public void setMarcada7(String marcada7) {
		this.marcada7 = marcada7;
	}

	public String getMarcada8() {
		return marcada8;
	}

	public void setMarcada8(String marcada8) {
		this.marcada8 = marcada8;
	}

	public String getMarcada9() {
		return marcada9;
	}

	public void setMarcada9(String marcada9) {
		this.marcada9 = marcada9;
	}

	public String getMarcada10() {
		return marcada10;
	}

	public void setMarcada10(String marcada10) {
		this.marcada10 = marcada10;
	}

	public String getUlStyle() {
		return ulStyle;
	}

	public void setUlStyle(String ulStyle) {
		this.ulStyle = ulStyle;
	}

	public String getVisible0() {
		return visible0;
	}

	public void setVisible0(String visible0) {
		this.visible0 = visible0;
	}

	public String getVisible1() {
		return visible1;
	}

	public void setVisible1(String visible1) {
		this.visible1 = visible1;
	}

	public String getVisible2() {
		return visible2;
	}

	public void setVisible2(String visible2) {
		this.visible2 = visible2;
	}

	public String getVisible3() {
		return visible3;
	}

	public void setVisible3(String visible3) {
		this.visible3 = visible3;
	}

	public String getVisible4() {
		return visible4;
	}

	public void setVisible4(String visible4) {
		this.visible4 = visible4;
	}

	public String getVisible5() {
		return visible5;
	}

	public void setVisible5(String visible5) {
		this.visible5 = visible5;
	}

	public String getVisible6() {
		return visible6;
	}

	public void setVisible6(String visible6) {
		this.visible6 = visible6;
	}

	public String getVisible7() {
		return visible7;
	}

	public void setVisible7(String visible7) {
		this.visible7 = visible7;
	}

	public String getVisible8() {
		return visible8;
	}

	public void setVisible8(String visible8) {
		this.visible8 = visible8;
	}

	public String getVisible9() {
		return visible9;
	}

	public void setVisible9(String visible9) {
		this.visible9 = visible9;
	}

	public String getVisible10() {
		return visible10;
	}

	public void setVisible10(String visible10) {
		this.visible10 = visible10;
	}

	public String getAction11() {
		return action11;
	}

	public void setAction11(String action11) {
		this.action11 = action11;
	}

	public String getNombre11() {
		return nombre11;
	}

	public void setNombre11(String nombre11) {
		this.nombre11 = nombre11;
	}

	public String getMetodo11() {
		return metodo11;
	}

	public void setMetodo11(String metodo11) {
		this.metodo11 = metodo11;
	}

	public String getExcludeParam11() {
		return excludeParam11;
	}

	public void setExcludeParam11(String excludeParam11) {
		this.excludeParam11 = excludeParam11;
	}

	public String getMarcada11() {
		return marcada11;
	}

	public void setMarcada11(String marcada11) {
		this.marcada11 = marcada11;
	}

	public String getVisible11() {
		return visible11;
	}

	public void setVisible11(String visible11) {
		this.visible11 = visible11;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public String getAction12() {
		return action12;
	}

	public void setAction12(String action12) {
		this.action12 = action12;
	}

	public String getAction13() {
		return action13;
	}

	public void setAction13(String action13) {
		this.action13 = action13;
	}

	public String getAction14() {
		return action14;
	}

	public void setAction14(String action14) {
		this.action14 = action14;
	}

	public String getNombre12() {
		return nombre12;
	}

	public void setNombre12(String nombre12) {
		this.nombre12 = nombre12;
	}

	public String getMetodo12() {
		return metodo12;
	}

	public void setMetodo12(String metodo12) {
		this.metodo12 = metodo12;
	}

	public String getExcludeParam12() {
		return excludeParam12;
	}

	public void setExcludeParam12(String excludeParam12) {
		this.excludeParam12 = excludeParam12;
	}

	public String getNombre13() {
		return nombre13;
	}

	public void setNombre13(String nombre13) {
		this.nombre13 = nombre13;
	}

	public String getMetodo13() {
		return metodo13;
	}

	public void setMetodo13(String metodo13) {
		this.metodo13 = metodo13;
	}

	public String getExcludeParam13() {
		return excludeParam13;
	}

	public void setExcludeParam13(String excludeParam13) {
		this.excludeParam13 = excludeParam13;
	}

	public String getNombre14() {
		return nombre14;
	}

	public void setNombre14(String nombre14) {
		this.nombre14 = nombre14;
	}

	public String getMetodo14() {
		return metodo14;
	}

	public void setMetodo14(String metodo14) {
		this.metodo14 = metodo14;
	}

	public String getExcludeParam14() {
		return excludeParam14;
	}

	public void setExcludeParam14(String excludeParam14) {
		this.excludeParam14 = excludeParam14;
	}

	public String getMarcada12() {
		return marcada12;
	}

	public void setMarcada12(String marcada12) {
		this.marcada12 = marcada12;
	}

	public String getMarcada13() {
		return marcada13;
	}

	public void setMarcada13(String marcada13) {
		this.marcada13 = marcada13;
	}

	public String getMarcada14() {
		return marcada14;
	}

	public void setMarcada14(String marcada14) {
		this.marcada14 = marcada14;
	}

	public String getVisible12() {
		return visible12;
	}

	public void setVisible12(String visible12) {
		this.visible12 = visible12;
	}

	public String getVisible13() {
		return visible13;
	}

	public void setVisible13(String visible13) {
		this.visible13 = visible13;
	}

	public String getVisible14() {
		return visible14;
	}

	public void setVisible14(String visible14) {
		this.visible14 = visible14;
	}



}
