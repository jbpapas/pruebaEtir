package es.dipucadiz.etir.comun.struts;

import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import com.opensymphony.xwork2.ActionInvocation;

public class GadirRedirectAction extends ServletActionRedirectResult {

	private static final long serialVersionUID = -917468770981823339L;

	public void execute(ActionInvocation invocation)
	throws Exception
	{
		try{
			String ventanaBotonLateral=((String[])(invocation.getInvocationContext().getParameters().get("ventanaBotonLateral")))[0];
			if (ventanaBotonLateral.equals("true")){
				this.requestParameters.put("ventanaBotonLateral", "true");
			}
		}catch(Exception e){
		}

		super.execute(invocation);
	}

}
