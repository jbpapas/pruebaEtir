
package es.dipucadiz.etir.comun.taglib.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.ComponentTag;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.taglib.component.ComboMultiple;

public class ComboMultipleTag extends ComponentTag {

	private static final long serialVersionUID = -3756861752688123972L;
	protected String classText;
	protected String classLabel;
	protected String styleLabel;
	protected String styleContent;
	protected String styleGroup;
	protected String list;
	protected String listKey;
	protected String listValue;

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new ComboMultiple(stack, req, res);
	}

	protected void populateParams() {
		super.populateParams();

		ComboMultiple comboMultiple = (ComboMultiple) component;

		comboMultiple.setStyleLabel(styleLabel);
		comboMultiple.setStyleContent(styleContent);
		comboMultiple.setList(list);
		comboMultiple.setListKey(listKey);
		comboMultiple.setListValue(listValue);		
		comboMultiple.setLabel(label);
	}

	
	public String getClassText() {
		return classText;
	}

	
	public void setClassText(String classText) {
		this.classText = classText;
	}

	
	public String getClassLabel() {
		return classLabel;
	}

	
	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
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

	
	public String getStyleGroup() {
		return styleGroup;
	}

	
	public void setStyleGroup(String styleGroup) {
		this.styleGroup = styleGroup;
	}

	
	public String getList() {
		return list;
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
