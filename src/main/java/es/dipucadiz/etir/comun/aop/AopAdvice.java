package es.dipucadiz.etir.comun.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.AfterAdvice;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import es.dipucadiz.etir.comun.exception.GadirServiceException;

/**
 * AOP-Advice para gestionar el log de la entrada y salida de los metodos.
 * 
 * @author FJTORRES
 * @version 30/03/2009
 */
public class AopAdvice implements AfterAdvice, BeforeAdvice, ThrowsAdvice {
	/**
	 * Atributo que almacena el log de la clase.
	 */
	private static Logger log = Logger.getLogger(AopAdvice.class);

	/**
	 * Método que se encargar de mostrar el log despues de los métodos.
	 * 
	 * @param thisJoinPoint
	 *            Método interceptado.
	 * @throws Throwable
	 *             si ocurre error.
	 */
	public void afterReturning(final JoinPoint thisJoinPoint) throws Throwable {
		final String nombreMetodo = thisJoinPoint.getSignature().getName();
		final Class<?> clase = thisJoinPoint.getTarget().getClass();
		log.debug("Saliendo del método: " + nombreMetodo + " de la clase "
		        + clase.getCanonicalName());
	}

	/**
	 * Método que se encarga de mostrar el log antes de entrar en los métodos.
	 * 
	 * @param thisJoinPoint
	 *            Método interceptado.
	 * @throws Throwable
	 *             si ocurre error.
	 */
	public void before(final JoinPoint thisJoinPoint) throws Throwable {
		final String nombreMetodo = thisJoinPoint.getSignature().getName();
		final Class<?> clase = thisJoinPoint.getTarget().getClass();
		log.debug("Entrando en el método " + nombreMetodo + " de la clase "
		        + clase.getCanonicalName());
	}

	/**
	 * Método que se encarga de controlar las excepciones lanzadas desde la capa
	 * de servicio y las convierte a service exception.
	 * 
	 * @param thisJoinPoint
	 *            Método interceptado.
	 * @param exc
	 *            Excepcion producida.
	 * @throws GadirServiceException
	 *             Si ocurre un error.
	 */
	public void afterThrowing(final JoinPoint thisJoinPoint, final Throwable exc)
	        throws GadirServiceException {
		if (!(exc instanceof GadirServiceException)) {
			final String nombreMetodo = thisJoinPoint.getSignature().getName();
			final Class<?> clase = thisJoinPoint.getTarget().getClass();
			log.error("Ocurrio una excepcion en el método " + nombreMetodo
			        + " de la clase " + clase.getCanonicalName(), exc
			        .getCause());
		}
	}
}
