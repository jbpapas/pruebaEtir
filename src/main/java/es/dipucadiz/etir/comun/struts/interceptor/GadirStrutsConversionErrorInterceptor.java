package es.dipucadiz.etir.comun.struts.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.interceptor.StrutsConversionErrorInterceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Se sobrescribe el interceptor de conversión de errores, para extraer el label
 * del campo que dio error con expresiones. Para que esto funciona el label debe
 * corresponder con el nombre del campo.
 * 
 * @version 1.0 23/10/2009
 * @author SDS[FJTORRES]
 */
@SuppressWarnings("unchecked")
public class GadirStrutsConversionErrorInterceptor extends
        StrutsConversionErrorInterceptor {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 4358719496352668175L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		final ActionContext invocationContext = invocation
		        .getInvocationContext();
		final Map conversionErrors = invocationContext.getConversionErrors();
		final ValueStack stack = invocationContext.getValueStack();

		HashMap fakie = null;

		for (final Iterator iterator = conversionErrors.entrySet().iterator(); iterator
		        .hasNext();) {
			final Map.Entry entry = (Map.Entry) iterator.next();
			final String propertyName = (String) entry.getKey();
			final Object value = entry.getValue();

			if (shouldAddError(propertyName, value)) {
				// FJTORRES - FIX para obtener el label del property
				final String labelPropertyName = stack.findString("getText('"
				        + propertyName + "')");
				String message = null;
				if (propertyName.equals(labelPropertyName)) {
					message = XWorkConverter.getConversionErrorMessage(
					        propertyName, stack);
				} else {
					message = XWorkConverter.getConversionErrorMessage(
					        labelPropertyName, stack);
				}

				final Object action = invocation.getAction();
				if (action instanceof ValidationAware) {
					final ValidationAware va = (ValidationAware) action;
					va.addFieldError(propertyName, message);
				}

				if (fakie == null) {
					fakie = new HashMap();
				}

				fakie.put(propertyName, getOverrideExpr(invocation, value));
			}
		}

		if (fakie != null) {
			// if there were some errors, put the original (fake) values in
			// place right before the result
			stack.getContext().put(ORIGINAL_PROPERTY_OVERRIDE, fakie);
			invocation.addPreResultListener(new PreResultListener() {
				public void beforeResult(final ActionInvocation invocation,
				        final String resultCode) {
					final Map fakie = (Map) invocation.getInvocationContext()
					        .get(ORIGINAL_PROPERTY_OVERRIDE);

					if (fakie != null) {
						invocation.getStack().setExprOverrides(fakie);
					}
				}
			});
		}
		return invocation.invoke();
	}
}
