package es.dipucadiz.etir.comun.taglib.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ListUIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class ComboMultiple extends ListUIBean {

	public static final String TEMPLATE = "gadirComboMultiple";
	final private static String COMPONENT_NAME = ComboMultiple.class.getName();

	protected String styleLabel;
	protected String styleContent;

	public ComboMultiple(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	public String getComponentName() {
		return COMPONENT_NAME;
	}

	public void evaluateExtraParams() {
		super.evaluateExtraParams();

		if (styleLabel != null) addParameter("styleLabel",  findValue(styleLabel, String.class));

		if (styleContent != null) addParameter("styleContent", findValue(styleContent, String.class));

//		addParameter("isUsuarioAccesible", DatosSesion.isUsuarioAccesible());

	}
	
	public String getStyleLabel() {
		return styleLabel;
	}

	public void setStyleLabel(String styleLabel) {
		this.styleLabel = styleLabel;
	}
	
	public String getStyleContent() {
		return styleContent;
	}
	
	public void setStyleContent(String styleContent) {
		this.styleContent = styleContent;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getListKey() {
		return listKey;
	}

	public void setListKey(String listKey) {
		this.listKey = listKey;
	}

	public String getListValue() {
		return listValue;
	}
	
	public void setListValue(String listValue) {
		this.listValue = listValue;
	}
}
