package es.dipucadiz.etir.comun.config;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

public class GadirConfig implements ApplicationContextAware {

	/**
	 * Numero de minutos hasta refrescarse las propiedades y mensajes -1 no se
	 * refrescan nunca.
	 */
	public static long minutosRefresco = -1;

	/**
	 * Ultima actualizacion de las propiedades y mensajes
	 */
	private static long ultimaActualizacion = System.currentTimeMillis();

	/**
	 * Atributo que almacena el contexto de spring.
	 */
	private static AbstractApplicationContext context;

	/**
	 * Propiedades de configuración.
	 */
	private static Properties config;

	/**
	 * Atributo que almacena el logger de la clase.
	 */
	private static final Logger log = Logger.getLogger(GadirConfig.class);

	/**
	 * Invocado por el BeanFactory despues de que todas las propiedades de los
	 * beans de propiedades han sido inicializados.
	 * 
	 * @throws Exception
	 *             Cuando se ha producido un fallo al leer los properties de la
	 *             aplicacion
	 * @semantics Se lee el fichero de configuración de los posibles filtros de
	 *            consultas.
	 */
	public void init() {
		// INIT METHOD
	}

	/**
	 * Metodo para refrescar la configuracion de spring cuando cumple un timeout
	 * Asi se pueden cambiar los ficheros properties en caliente sin necesidad
	 * de tirar el servidor.
	 */
	public static synchronized void refreshSpringConfiguration() {
		final long hora = System.currentTimeMillis();
		final long intervalo = minutosRefresco * 60 * 1000;

		if (minutosRefresco != -1 && hora - ultimaActualizacion > intervalo) {
			ultimaActualizacion = hora;
			context.refresh();
		}
	}

	/**
	 * Proporciona el valor de un parametro
	 * 
	 * @param strNombre
	 *            - nombre del parametro a leer
	 * @return Valor del parametro leido
	 */
	public static String leerParametro(final String strNombre) {
		String strValor = null;
		try {
			refreshSpringConfiguration();
			strValor = config.get(strNombre).toString();
		} catch (final Exception e) {
			log.error("Error al leer un parametro de configuración.", e);
		}
		return strValor;
	}

	/**
	 * Método que se encarga de obtener de la configuración un bean de spring.
	 * 
	 * @param beanName
	 *            Nombre del bean a obtener.
	 * @return Bean con el nombre dado.
	 */
	public static Object getBean(final String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setApplicationContext(final ApplicationContext _context)
	        throws BeansException {
		context = (AbstractApplicationContext) _context;
	}

	/**
	 * Método que devuelve el atributo config.
	 * 
	 * @return config.
	 */
	public Properties getConfig() {
		return GadirConfig.config;
	}

	/**
	 * Método que establece el atributo config.
	 * 
	 * @param config
	 *            El config.
	 */
	public void setConfig(final Properties config) {
		GadirConfig.config = config;
	}
}
