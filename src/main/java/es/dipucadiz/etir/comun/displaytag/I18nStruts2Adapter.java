package es.dipucadiz.etir.comun.displaytag;

import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;
import org.apache.struts2.views.jsp.TagUtils;
import org.displaytag.Messages;
import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("unchecked")
public class I18nStruts2Adapter implements LocaleResolver, I18nResourceProvider {

	public static final String UNDEFINED_KEY = "???";

	/**
	 * Atributo que almacena el log de la clase.
	 */
	private static Logger log = Logger.getLogger(I18nStruts2Adapter.class);

	/**
	 * {@inheritDoc}
	 */
	public Locale resolveLocale(final HttpServletRequest request) {
		Locale result = null;
		final ValueStack stack = ActionContext.getContext().getValueStack();

		final Iterator iterator = stack.getRoot().iterator();
		while (iterator.hasNext()) {
			final Object o = iterator.next();

			if (o instanceof LocaleProvider) {
				final LocaleProvider lp = (LocaleProvider) o;
				result = lp.getLocale();

				break;
			}
		}
		if (result == null) {
			log.debug("Missing LocalProvider actions, init locale to default");
			result = Locale.getDefault();
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getResource(final String resourceKey,
	        final String defaultValue, final Tag tag,
	        final PageContext pageContext) {
		final String key = resourceKey != null ? resourceKey : defaultValue;

		String message = null;
		final ValueStack stack = TagUtils.getStack(pageContext);
		final Iterator iterator = stack.getRoot().iterator();

		while (iterator.hasNext()) {
			final Object o = iterator.next();

			if (o instanceof TextProvider) {
				final TextProvider tp = (TextProvider) o;
				message = tp.getText(key, defaultValue, Collections.EMPTY_LIST,
				        stack);

				break;
			}
		}

		if (message == null && resourceKey != null) {
			log.debug(Messages.getString("Localization.missingkey ",
			        resourceKey));
			message = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
		}

		return message;
	}

}
