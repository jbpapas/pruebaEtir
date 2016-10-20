package es.dipucadiz.etir.comun;

import java.io.Serializable;

import es.dipucadiz.etir.comun.utilidades.KeyValue;

/**
 * Clase responsable de proporcionar el comportamiento comun a los tipos de
 * datos que se creen en la aplicación.
 * 
 * @version 1.0 11/01/2010
 * @author SDS[FJTORRES]
 */
public class TipoBase implements Serializable {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = 8808640003732052221L;

	/**
	 * Atributo que almacena el código del tipo.
	 */
	private String codigo;

	/**
	 * Atributo que almacena la descripción del tipo.
	 */
	private String descripcion;

	/**
	 * Constructor de la clase.
	 * 
	 * @param codigo
	 *            El codigo a establecer.
	 */
	protected TipoBase(final String codigo) {
		this.codigo = codigo;
		this.descripcion = codigo;
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param codigo
	 *            Codigo del tipo.
	 * @param descripcion
	 *            Descripción del tipo.
	 */
	protected TipoBase(final String codigo, final String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	/**
	 * Método que se encarga de pasar el tipo a {@link KeyValue}.
	 * 
	 * @return Objeto {@link KeyValue} con los datos del tipo.
	 */
	public KeyValue toKeyValue() {
		final KeyValue lbl = new KeyValue(this.descripcion, this.codigo);
		return lbl;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.codigo + " " + this.descripcion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (codigo == null ? 0 : codigo.hashCode());
		result = prime * result
		        + (descripcion == null ? 0 : descripcion.hashCode());
		return result;
	}

	/**
	 * Se sobrescribe el método equals. Si el objeto de entrada es de tipo
	 * String se compara con el código, si no es de tipo String se realiza el
	 * funcionamiento normal del método equals.
	 * 
	 * @param obj
	 *            El objeto a comparar.
	 * @return boolean Indica si el objeto es igual.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (obj instanceof String) {
			if (this.codigo == null) {
				if (obj != null) {
					return false;
				}
			} else if (!codigo.equalsIgnoreCase((String) obj)) {
				return false;
			}
		} else {

			if (getClass() != obj.getClass()) {
				return false;
			}
			final TipoRegistrado other = (TipoRegistrado) obj;
			if (codigo == null) {
				if (other.getCodigo() != null) {
					return false;
				}
			} else if (!codigo.equalsIgnoreCase(other.getCodigo())) {
				return false;
			}
			if (descripcion == null) {
				if (other.getDescripcion() != null) {
					return false;
				}
			} else if (!descripcion.equalsIgnoreCase(other.getDescripcion())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método que devuelve el atributo codigo.
	 * 
	 * @return codigo.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Método que establece el atributo codigo.
	 * 
	 * @param codigo
	 *            El codigo.
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método que devuelve el atributo descripcion.
	 * 
	 * @return descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método que establece el atributo descripcion.
	 * 
	 * @param descripcion
	 *            El descripcion.
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}
}
