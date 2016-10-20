package es.dipucadiz.etir.comun.taglib.component;

import java.io.Writer;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.GenericUIBean;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.action.AbstractGadirBaseAction;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.utilidades.ItemPilaVolver;


public class BotonVolverPila extends GenericUIBean {

	private static final Log LOG = LogFactory.getLog(BotonVolverPila.class);
	
	public BotonVolverPila(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		
		//<input type="submit" value="Imprimir" name="method:botonImprimir" id="Detalle4__botonImprimir">
		try {
//			String action=DatosSesion.topPilaVolver().getActionVolvible();
//			Map parametros=DatosSesion.topPilaVolver().getParametros();
//			writer.write("<a class=\"botonVolverPila\" href=\"/etir/" + action + ".action");
//			if (!parametros.isEmpty()){
//				writer.write("?");
//			}
//			Iterator it=parametros.keySet().iterator();
//			while(it.hasNext()){
//				String nombreParam=(String)it.next();
//				if(!nombreParam.equals("botonVolverPila") && !nombreParam.startsWith("method:")){
//					try{
//						String valorParam=(String)parametros.get(nombreParam);
//						writer.write(nombreParam + "=" + valorParam + "&");
//					}catch(Exception e){
//						try{
//							String valorParam=((String[])parametros.get(nombreParam))[0];
//							writer.write(nombreParam + "=" + valorParam + "&");
//						}catch(Exception e2){
//						}
//					}
//				}
//			}
//			
//			writer.write("botonVolverPila=true");
//			
//			writer.write("\" value=\"VOLVER PILA\" >");
//			
//			writer.write("VOLVER PILA");
//			
//			writer.write("</a>" + '\n');
			String tabName=getTabName();
			if (StringUtils.isNotEmpty(tabName)){
				if (DatosSesion.topPilaVolver(tabName)!=null){
					writer.write("<input type=\"button\" onclick=\"location.href='");
					writer.write(BotonVolverPila.getURL(DatosSesion.topPilaVolver(tabName)));
					writer.write("'\" value=\"Volver PILA\" >");
	
					writer.write("</input>" + '\n');
				}
			}
		} catch (Exception e) {
			LOG.info("Tag BotonVolverPilaTag could not print.");
		}
        return result;
	}
	
	public static String getURL(ItemPilaVolver itemPilaVolver) {
		String result = "";
		if(itemPilaVolver != null) {
			String action=itemPilaVolver.getActionVolvible();
			Map<String, Object> parametros=itemPilaVolver.getParametros();
			result = "/etir/" + action + ".action";
	
			result += "?";
	
			Iterator<String> it=parametros.keySet().iterator();
			while(it.hasNext()){
				String nombreParam=(String)it.next();
				if(!nombreParam.equals("botonVolverPila") && !nombreParam.startsWith("method:")){
					try{
						String valorParam=(String)parametros.get(nombreParam);
						result += nombreParam + "=" + URLEncoder.encode(valorParam, "ISO-8859-1") + "&";
					}catch(Exception e){
						try{
							String valorParam=((String[])parametros.get(nombreParam))[0];
							result += nombreParam + "=" + URLEncoder.encode(valorParam, "ISO-8859-1") + "&";
						}catch(Exception e2){
						}
					}
				}
			}
		}
		result += "botonVolverPila=true";
		
		return result;
	}

	private String getTabName(){
		String tabName="";
		Cookie[] cookies = request.getCookies();
		if (cookies!=null && cookies.length>0){
			for (int i=0;i<cookies.length;i++){
				if (AbstractGadirBaseAction.COOKIE_TAB_NAME.equals(cookies[i].getName())){
					tabName=cookies[i].getValue();
				}
			}
		}
		return tabName;
	}
	
//	@SuppressWarnings("unchecked")
//	private String renderFormUrl() {
//		ActionInvocation ai = (ActionInvocation) this.getStack().getContext().get(ActionContext.ACTION_INVOCATION);
//		String action = ai.getProxy().getActionName();
//		String namespace = ai.getProxy().getNamespace();
//
//        ActionMapping nameMapping = actionMapper.getMappingFromActionName(action);
//        String actionName = nameMapping.getName();
//        String actionMethod = nameMapping.getMethod();
//
//		ActionMapping mapping = new ActionMapping(actionName, namespace, actionMethod, this.parameters);
//		return UrlHelper.buildUrl(this.actionMapper.getUriFromActionMapping(mapping), this.request, this.response, null);
//	}
	
	

}
