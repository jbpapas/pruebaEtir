package es.dipucadiz.etir.comun.displaytag;

/**
 * 
 * Interface que define las utilidades para la paginacion externa en displayTag.
 * 
 * @version 1.0 29/10/2009
 * @author SDS[FJTORRES]
 */
public interface PaginacionExternaUtil {
	
	/** Constantes con los nombres de los parametros de la request. */
	public interface ParametrosRequest {
		String SORT = "sort";
		String PAGE = "page";
		String ASC = "asc";
		String DESC = "desc";
		String DIRECTION = "dir";
	}

}
