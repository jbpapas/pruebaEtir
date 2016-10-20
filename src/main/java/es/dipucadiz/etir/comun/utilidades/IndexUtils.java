package es.dipucadiz.etir.comun.utilidades;

public class IndexUtils {

	public final static String PK_DOCUMENTO_CASLLA_VALOR = "PK_CASVAL";
	public final static String FROM_GA_DOCUMENTO_CASLLA_VALOR = "from GA_DOCUMENTO_CASILLA_VALOR";

	public static String procesar(String sql) {
		if (sql.contains(FROM_GA_DOCUMENTO_CASLLA_VALOR)) {
			sql = sql.replace("select ", "select /*+ INDEX(cas,"+PK_DOCUMENTO_CASLLA_VALOR+") */ ");
		}
		return sql;
	}

}
