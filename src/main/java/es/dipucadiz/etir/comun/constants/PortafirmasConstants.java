package es.dipucadiz.etir.comun.constants;



final public class PortafirmasConstants {

	public final static String ESTADO_GENERADO = "GENERADO";
	public final static String ESTADO_ENVIADO = "ENVIADO";

	public final static String ESTADO_FIRMADO = "FIRMADO";
	public final static String ESTADO_DEVUELTO = "DEVUELTO";
	public final static String ESTADO_RECHAZADO = "RECHAZADO";
	public final static String ESTADO_VISTO_BUENO = "VISTOBUENO";
	public static final String ESTADO_ERROR = "ERROR";

	public final static String[] ESTADOS_INVOCAR_TRAMITADOR = new String[] {ESTADO_FIRMADO, ESTADO_VISTO_BUENO, ESTADO_DEVUELTO, ESTADO_RECHAZADO};

}
