package es.dipucadiz.etir.comun.struts.taglib;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.struts.taglib.model.GadirModels;

/**
 * 
 * TagLibrary customizado para la aplicación Gadir.
 * 
 * @version 1.0 21/08/2009
 * @author SDS[FJTORRES]
 */
public class GadirTagLibrary implements org.apache.struts2.views.TagLibrary {

	/**
	 * {@inheritDoc}
	 */
	public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new GadirModels(stack, req, res);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Class> getVelocityDirectiveClasses() {
		return null;
	}

}
