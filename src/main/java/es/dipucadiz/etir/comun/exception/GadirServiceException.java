package es.dipucadiz.etir.comun.exception;

/**
 * Clase responsable de encapsular las excepciones de la capa de servicio.
 * 
 * @version 1.0 06/08/2009
 * @author SDS[FJTORRES]
 */
public class GadirServiceException extends Exception {

	/**
	 * Atributo que almacena el serialVersionUID de la clase.
	 */
	private static final long serialVersionUID = -7908447690253025342L;

	/**
	 * Atributo que almacena el mensaje explicativo de la excepcion.
	 */
	private String mensaje;

	/**
	 * Constructor de la clase.
	 */
	public GadirServiceException() {
		super();
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param message
	 *            Mensaje de la excepción.
	 */
	public GadirServiceException(final String message) {
		super(message);
		this.mensaje = message;
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param throwable
	 *            Excepción ocurrida.
	 */
	public GadirServiceException(final Throwable throwable) {
		super(throwable);
		this.mensaje = throwable.getLocalizedMessage();
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param message
	 *            Mensaje de la excepción.
	 * @param throwable
	 *            Excepción ocurrida.
	 */
	public GadirServiceException(final String message, final Throwable throwable) {
		super(message, throwable);
		this.mensaje = message;
	}

	/**
	 * Método que devuelve el atributo mensaje.
	 * 
	 * @return mensaje.
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Método que establece el atributo mensaje.
	 * 
	 * @param mensaje
	 *            El mensaje.
	 */
	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}
}
