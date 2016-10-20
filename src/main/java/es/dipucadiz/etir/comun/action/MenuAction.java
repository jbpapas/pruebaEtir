package es.dipucadiz.etir.comun.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.dipucadiz.etir.comun.exception.GadirServiceException;
import es.dipucadiz.etir.comun.utilidades.DatosSesion;
import es.dipucadiz.etir.comun.vo.GestorTareasSeguimientoHGestorTareasVO;

final public class MenuAction extends AbstractGadirBaseAction {

	private static final long serialVersionUID = -785972905522959721L;

	private String action;

	private static final Log LOG = LogFactory.getLog(MenuAction.class);

	public String execute() throws GadirServiceException {

		
		
		try{
			HttpServletResponse res = getServletResponse();
			
			if (action!= null && !action.equals("")){
				res.sendRedirect(res.encodeRedirectURL("/etir/"+action));
			}else{
				res.setStatus(204);
			}
		}catch(Exception e){
			LOG.error("Error con la redireccion en el menu accesible", e);
		}
		

		return INPUT;
	}

	public String getMenuAjax(){
		ajaxData=es.dipucadiz.etir.comun.utilidades.DatosSesion.getMenuHtml();
		return AJAX_DATA;
	}

	public String getNumeracionVentanaAjax(){
		
		HttpServletRequest req = getRequest();
		String tabName = req.getParameter("tabName");
		Integer numVentana = null;
		if (StringUtils.isNotEmpty(tabName)){
			numVentana = DatosSesion.getNumeracionVentana(tabName);
			if (numVentana==null){
				numVentana = DatosSesion.actualizaNumeracionVentanas(tabName);
			}
		}
		ajaxData = numVentana==null?"":numVentana.toString();
		return AJAX_DATA;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
}