package es.dipucadiz.etir.comun;

/**
 * Clase responsable de definir los tipos de formato que se aceptan en la
 * aplicación.
 * 
 * @version 1.0 11/01/2010
 * @author SDS[FJTORRES]
 */
public class TipoFormato extends TipoRegistrado {

	/**
	 * Atributo que almacena el TIPO_FECHA_CORTA de la clase.
	 */
	public static final TipoFormato TIPO_FECHA_CORTA = new TipoFormato("C",
	        "Fecha Corto");

	/**
	 * Atributo que almacena el TIPO_FECHA_LARGA de la clase.
	 */
	public static final TipoFormato TIPO_FECHA_LARGA = new TipoFormato("F",
	        "Fecha Largo");

	/**
	 * Atributo que almacena el TIPO_HORA_CORTA de la clase.
	 */
	public static final TipoFormato TIPO_HORA_CORTA = new TipoFormato("H",
	        "Hora Corto");

	/**
	 * Atributo que almacena el TIPO_HORA_LARGA de la clase.
	 */
	public static final TipoFormato TIPO_HORA_LARGA = new TipoFormato("T",
	        "Hora Largo");

	/**
	 * Atributo que almacena el TIPO_NUM_EDITADO de la clase.
	 */
	public static final TipoFormato TIPO_NUM_EDITADO = new TipoFormato("I",
	        "Numérico Editado");

	/**
	 * Atributo que almacena el TIPO_NUM_SIN_EDITAR de la clase.
	 */
	public static final TipoFormato TIPO_NUM_SIN_EDITAR = new TipoFormato("S",
	        "Numérico sin editar");

	/**
	 * Atributo que almacena el TIPO_PORCENTAJE de la clase.
	 */
	public static final TipoFormato TIPO_PORCENTAJE = new TipoFormato("P",
	        "Porcentaje");

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 7189774790172671201L;

	/**
	 * Constructor de la clase.
	 * 
	 * @param codigo
	 *            El código del tipo de formato.
	 */
	protected TipoFormato(final String codigo) {
		super(codigo);
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param codigo
	 *            El código del tipo de formato.
	 * @param descripcion
	 *            La descripción del tipo de formato.
	 */
	protected TipoFormato(final String codigo, final String descripcion) {
		super(codigo, descripcion);
	}

	/**
	 * Método que se encarga de obtener el tipo de dato segun el código dado.
	 * 
	 * @param codigo
	 *            Código del tipo de dato que buscamos.
	 * @return Tipo de dato con el código dado.
	 */
	public static TipoFormato get(final String codigo) {
		final TipoFormato tipo = (TipoFormato) getTipoRegistrado(
		        TipoFormato.class, codigo);
		return tipo;
	}

	/**
	 * Método que se encarga de obtener la descripcion del tipo de dato con el
	 * código dado.
	 * 
	 * @param codigo
	 *            Código del tipo de dato que buscamos.
	 * @return Descripción de tipo de dato con el código dado.
	 */
	public static String getDescripcion(final String codigo) {

		final TipoFormato tipo = get(codigo);

		final String descripcion = tipo.getDescripcion() == null ? codigo
		        : tipo.getDescripcion();

		return descripcion;
	}
}
