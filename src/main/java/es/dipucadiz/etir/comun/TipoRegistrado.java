package es.dipucadiz.etir.comun;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Clase responsable de almacenar los tipos registrados en la aplicación y
 * proporcionar métodos para acceder a ellos.
 * 
 * @version 1.0 22/12/2009
 * @author SDS[FJTORRES]
 */
public class TipoRegistrado extends TipoBase {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -55657045402211290L;

	/**
	 * Atributo que almacena el LOG de la clase.
	 */
	private static final Logger LOG = Logger.getLogger(TipoRegistrado.class);

	/**
	 * Atributo que almacena el el mapa con las clases de Tipos.
	 */
	private static Map<Class<?>, Map<String, TipoRegistrado>> clasesRegistradas = null;

	/**
	 * Constructor de la clase.
	 * 
	 * @param codigo
	 *            Código del tipo registrado.
	 */
	protected TipoRegistrado(final String codigo) {
		super(codigo);
		registraTipo(this);
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param codigo
	 *            Código del tipo registrado.
	 * @param descripcion
	 *            Descripción del tipo registrado.
	 */
	protected TipoRegistrado(final String codigo, final String descripcion) {
		super(codigo, descripcion);
		registraTipo(this);
	}

	/**
	 * Método que se encarga de devolver los tipos registrados, si la instancia
	 * es null la crea.
	 * 
	 * @return Instancia con los registros registrados.
	 */
	private static Map<Class<?>, Map<String, TipoRegistrado>> getClasesRegistradas() {
		if (clasesRegistradas == null) {
			clasesRegistradas = new HashMap<Class<?>, Map<String, TipoRegistrado>>();
		}
		return clasesRegistradas;
	}

	/**
	 * Método que se encarga de registrar el TipoRegistrado dado.
	 * 
	 * @param tsr
	 *            TipoRegistrado a registrar en la cache.
	 */
	protected void registraTipo(final TipoRegistrado tsr) {
		LOG.info("Registrando el tipo: " + tsr);
		Class<?> clase = tsr.getClass();

		while (clase != TipoRegistrado.class) {
			Map<String, TipoRegistrado> ht = getClasesRegistradas().get(clase);
			if (ht == null) {
				ht = new HashMap<String, TipoRegistrado>();
				getClasesRegistradas().put(clase, ht);
			}

			ht.put(tsr.getCodigo(), tsr);

			// Siguiente superclase
			clase = clase.getSuperclass();
		}
		LOG.info("Tipo registrado correctamente.");
	}

	/**
	 * Método que obtiene la instancia (única) del derivado de TipoRegistrado
	 * identificado por la <code>clase</code> cuyo valor String coincide con el
	 * solicitado.
	 * 
	 * @param clase
	 *            Clase del TipoRegistrado.
	 * @param codigo
	 *            Código del tipo buscado.
	 * @return null si no se encuentra el tipo o el valor entre los registrados.
	 */
	public static TipoRegistrado getTipoRegistrado(final Class<?> clase,
	        final String codigo) {
		TipoRegistrado tsr = null;
		final Map<String, TipoRegistrado> ht = getClasesRegistradas()
		        .get(clase);
		if (ht != null) {
			tsr = ht.get(codigo);
		}
		return tsr;
	}
}
