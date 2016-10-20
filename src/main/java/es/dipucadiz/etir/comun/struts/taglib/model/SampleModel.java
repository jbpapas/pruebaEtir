package es.dipucadiz.etir.comun.struts.taglib.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.freemarker.tags.TagModel;

import com.opensymphony.xwork2.util.ValueStack;

import es.dipucadiz.etir.comun.struts.taglib.component.Sample;

public class SampleModel extends TagModel {

	public SampleModel(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		super(stack, req, res);
	}

	@Override
	protected Component getBean() {
		return new Sample(this.stack, this.req, this.res);
	}

}
