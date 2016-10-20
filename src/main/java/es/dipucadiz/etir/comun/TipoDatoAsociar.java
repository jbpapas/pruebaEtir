package es.dipucadiz.etir.comun;

/**
 * Clase responsable de definir los tipos de datos que se permiten asociar.
 * 
 * @version 1.0 22/12/2009
 * @author SDS[FJTORRES]
 */
public class TipoDatoAsociar extends TipoRegistrado {

	/**
	 * Atributo que almacena el TIPO_CASILLA de la clase.
	 */
	public static final TipoDatoAsociar TIPO_CASILLA = new TipoDatoAsociar("S",
	        "Casilla", "4,1");

	/**
	 * Atributo que almacena el TIPO_DATO_CORPORATIVO de la clase.
	 */
	public static final TipoDatoAsociar TIPO_DATO_CORPORATIVO = new TipoDatoAsociar(
	        "D", "Dato corporativo", "5,1");

	/**
	 * Atributo que almacena el TIPO_CONSTANTE de la clase.
	 */
	public static final TipoDatoAsociar TIPO_CONSTANTE = new TipoDatoAsociar(
	        "K", "Constante");

	/**
	 * Atributo que almacena el TIPO_ORDEN_EST_SALIDA de la clase.
	 */
	public static final TipoDatoAsociar TIPO_ORDEN_EST_SALIDA = new TipoDatoAsociar(
	        "E", "Orden estructura salida");

	/**
	 * Atributo que almacena el TIPO_CONCATENACION de la clase.
	 */
	public static final TipoDatoAsociar TIPO_CONCATENACION = new TipoDatoAsociar(
	        "C", "Concatenación");

	/**
	 * Atributo que almacena el TIPO_TABLA de la clase.
	 */
	public static final TipoDatoAsociar TIPO_TABLA = new TipoDatoAsociar("T",
	        "Tabla");

	/**
	 * Atributo que almacena el TIPO_TOTALIZADOR de la clase.
	 */
	public static final TipoDatoAsociar TIPO_TOTALIZADOR = new TipoDatoAsociar(
	        "Z", "Totalizador");
	
	/**
	 * Atributo que almacena el TIPO_OPERACION_MAT de la clase.
	 */
	public static final TipoDatoAsociar TIPO_OPERACION_MAT = new TipoDatoAsociar(
	        "O", "Operacion Mat");
	
	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 9211575654376888744L;

	/**
	 * Atributo que almacena el tipo de longitud que almacena el dato.
	 */
	private String longitud;

	/**
	 * Constructor de la clase.
	 * 
	 * @param codigo
	 *            Nuevo código.
	 * @param descripcion
	 *            Nueva descripción.
	 */
	private TipoDatoAsociar(final String codigo, final String descripcion) {
		super(codigo, descripcion);
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param codigo
	 *            Nuevo código.
	 * @param descripcion
	 *            Nueva descripción.
	 * @param longitud
	 *            Nueva longitud.
	 */
	private TipoDatoAsociar(final String codigo, final String descripcion,
	        final String longitud) {
		super(codigo, descripcion);
		this.longitud = longitud;
	}

	/**
	 * Método que se encarga de obtener el tipo de dato segun el código dado.
	 * 
	 * @param codigo
	 *            Código del tipo de dato que buscamos.
	 * @return Tipo de dato con el código dado.
	 */
	public static TipoDatoAsociar get(final String codigo) {
		final TipoDatoAsociar tipo = (TipoDatoAsociar) getTipoRegistrado(
		        TipoDatoAsociar.class, codigo);
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

		final TipoDatoAsociar tipo = get(codigo);

		final String descripcion = tipo == null
		        || tipo.getDescripcion() == null ? codigo : tipo
		        .getDescripcion();

		return descripcion;
	}

	/**
	 * Método que se encarga de obtener la longitud del tipo.
	 * 
	 * @param entero
	 *            Indica si queremos la longitud de la parte entera (true) o
	 *            total (false).
	 * @return Longitud del tipo.
	 */
	public int obtenerLongitud(final boolean entero) {

		int longitud = 0;
		if (this.longitud != null) {
			final String[] partes = this.longitud.split(",");
			final Integer numero = Integer.valueOf(partes[0]);
			if (entero) {
				longitud = numero;
			} else {
				longitud = numero + 1;
			}
		}

		return longitud;
	}

	/**
	 * Método que devuelve el atributo longitud.
	 * 
	 * @return longitud.
	 */
	public String getLongitud() {
		return longitud;
	}

	/**
	 * Método que establece el atributo longitud.
	 * 
	 * @param longitud
	 *            El longitud.
	 */
	public void setLongitud(final String longitud) {
		this.longitud = longitud;
	}
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}
	public int hashCode() {
		return super.hashCode();
	}
}
