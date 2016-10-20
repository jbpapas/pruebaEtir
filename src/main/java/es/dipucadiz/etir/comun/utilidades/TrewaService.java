package es.dipucadiz.etir.comun.utilidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import trewa.bd.sql.ClausulaOrderBy;
import trewa.bd.sql.ClausulaWhere;
import trewa.bd.sql.OperadorLogico;
import trewa.bd.sql.OperadorOrderBy;
import trewa.bd.sql.OperadorWhere;
import trewa.bd.tpo.TpoPK;
import trewa.bd.trapi.trapiui.TrAPIUI;
import trewa.bd.trapi.trapiui.TrAPIUIFactory;
import trewa.bd.trapi.trapiui.tpo.TrEmpleado;
import trewa.bd.trapi.trapiui.tpo.TrOrganismo;
import trewa.exception.TrException;

public class TrewaService {
	
	public static void calculaFirmantesTrewa(){
		String perfil = "java:/comp/env/jdbc/TrewaDS";
		
//		ResourceBundle bun = ResourceBundle.getBundle("constantes");
		String sistema = "SECRETARIA";//bun.getString("sistemaSecretaria");
		
		TrAPIUI apiUI = TrAPIUIFactory.crearAPIUI(perfil, sistema);
		boolean autocommit = apiUI.getAutoCommit();
		apiUI.setAutoCommit(false);

		try {
			
			String area = "4";
			String unidad = "4";
			String codigoUnidadOrganicaRaiz = obtenerOrganismoAutonomo(apiUI, area);
			
			List<TrEmpleado> politicos = obtenerEmpleadosDiputados(apiUI, area);
			List<TrEmpleado> secretarios = obtenerEmpleadosSecretarios(apiUI, codigoUnidadOrganicaRaiz);
			List<TrEmpleado> supervisores = obtenerJefesUnidad(apiUI, unidad);
			
			if(politicos!=null){
				System.out.println("USUARIOS POLITICOS");
				System.out.println(imprimir(politicos));
			}
			
			if(secretarios!=null){
				System.out.println("USUARIOS SECRETARIOS");
				System.out.println(imprimir(secretarios));
			}
			
			if(supervisores!=null){
				System.out.println("USUARIOS SUPERVISORES");
				System.out.println(imprimir(supervisores));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			apiUI.cerrarSesion();
		}

		apiUI.setAutoCommit(autocommit);
	}
	
	/**
	 * Obtiene empleados diputados y presidente. Firmantes POLITICOS.
	 * @param apiUI
	 * @param area
	 * @return
	 * @throws TrException
	 */
	public static List<TrEmpleado> obtenerEmpleadosDiputados(TrAPIUI apiUI, String area)
	throws TrException {

		List<TrEmpleado> resultado = new ArrayList<TrEmpleado>();

		// Obtenemos los diputados del area.
		
		ClausulaOrderBy corder = new ClausulaOrderBy();
		corder.addExpresion(TrEmpleado.CAMPO_APELLIDO1USU,
				OperadorOrderBy.ASCENDENTE);
		ClausulaWhere where = new ClausulaWhere();
		where.addExpresion(TrEmpleado.CAMPO_CODPTOTRAB, OperadorWhere.OP_IGUAL,
				"37");
		where.addExpresion(TrEmpleado.CAMPO_FECHACESE, OperadorWhere.OP_IS_NULL);
		TrEmpleado[] emps = apiUI.obtenerEmpleados(null, new TpoPK(
				new BigDecimal(area)), null, where, corder);
		if (emps != null && emps.length > 0) {
			for (int i = 0; i < emps.length; i++) {
				resultado.add(emps[i]);
			}
		}
		
		// Buscamos ahora al presidente en la raiz.
		String raiz = obtenerOrganismoAutonomo(apiUI, area);
		
		if(raiz != null){
			where = new ClausulaWhere();
			where.addExpresion(TrEmpleado.CAMPO_CODPTOTRAB, OperadorWhere.OP_IGUAL,
					"38");
			where.addExpresion(TrEmpleado.CAMPO_FECHACESE, OperadorWhere.OP_IS_NULL);
			emps = apiUI.obtenerEmpleados(null, new TpoPK(
					new BigDecimal(raiz)), null, where, null);
			if (emps != null && emps.length > 0) {
				for (int i = 0; i < emps.length; i++) {
					resultado.add(emps[i]);
				}
			}
		}
		
		return resultado;
	}
	
	/**
	 * Obtener el mas alto organismo del que depende el servicio indicado por
	 * parametros.
	 * @param apiUI
	 * @param codigoServicio
	 * @return
	 * @throws TrException
	 */
	public static String obtenerOrganismoAutonomo(TrAPIUI apiUI, String codigoServicio) throws TrException {
		return obtenerOrganismoAutonomo(codigoServicio, apiUI);
	}

	/**
	 * Funcion recursiva
	 * 
	 * @param tmpOrganismo
	 * @param apiUI
	 * @return
	 * @throws TrException
	 */
	private static String obtenerOrganismoAutonomo(String tmpOrganismo, TrAPIUI apiUI) throws TrException {
		String res = "";
		try {
			// Buscamos mi organismo padre y miramos si no tiene padre. Sería el
			// organismo raíz.
			ClausulaWhere where = new ClausulaWhere();
			where.addExpresion(TrOrganismo.CAMPO_REFORGPADRE, OperadorWhere.OP_IS_NULL);
			TrOrganismo[] arrayTrOrganismos = apiUI.obtenerOrganismos(new TpoPK(new BigDecimal(tmpOrganismo)), where, null);
			if (arrayTrOrganismos != null) {
				return arrayTrOrganismos[0].getREFORGANISMO().toString();
			} else {
				arrayTrOrganismos = apiUI.obtenerOrganismos(new TpoPK(new BigDecimal(tmpOrganismo)), null, null);
				if (arrayTrOrganismos != null) {
					tmpOrganismo = arrayTrOrganismos[0].getREFORGPADRE().toString();
					return obtenerOrganismoAutonomo(tmpOrganismo, apiUI);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
			throw new TrException(e.getMessage());
		}

		return res;
	}


	/**
	 * Devuelve una lista de empleados, aquellos que sean secretario o
	 * vicesecretario de toda la diputación. Firmantes SECRETARIOS
	 * @param apiUI
	 * @param codigoUnidadOrganicaRaiz
	 * @return
	 * @throws TrException
	 */
	public static List<TrEmpleado> obtenerEmpleadosSecretarios(TrAPIUI apiUI, String codigoUnidadOrganicaRaiz)
			throws TrException {
		List<TrEmpleado> resultado = new ArrayList<TrEmpleado>();

		// Obtenemos los secretarios y vicesecretarios de la diputación.
		ClausulaWhere cwhere = new ClausulaWhere();
		cwhere.setOpLogico(OperadorLogico.OR);
		cwhere.addExpresion(TrEmpleado.CAMPO_CODPTOTRAB,
				OperadorWhere.OP_IGUAL,
				"29013");
		cwhere.addExpresion(TrEmpleado.CAMPO_CODPTOTRAB,
				OperadorWhere.OP_IGUAL, "30003");

		ClausulaOrderBy corder = new ClausulaOrderBy();
		corder.addExpresion(TrEmpleado.CAMPO_APELLIDO1USU,
				OperadorOrderBy.ASCENDENTE);
		ClausulaWhere where = new ClausulaWhere();
		if (codigoUnidadOrganicaRaiz != null)
			where.addExpresion(TrEmpleado.CAMPO_REFORGANISMO,
					OperadorWhere.OP_IGUAL, codigoUnidadOrganicaRaiz);
		where.addExpresion(cwhere);
		where.addExpresion(TrEmpleado.CAMPO_FECHACESE, OperadorWhere.OP_IS_NULL);

		TrEmpleado[] emps = apiUI.obtenerEmpleados(null, null, null, where,
				corder);
		if (emps != null && emps.length > 0) {
			for (int i = 0; i < emps.length; i++) {
				resultado.add(emps[i]);
			}
		}
		return resultado;
	}
	
	/**
	 * Obtenemos los jefes del organismo indicado como parámetro.
	 * 
	 * @param pkOrganismo
	 *            pk del organismo
	 * 
	 * @return Lista de empleados.
	 * 
	 * @throws TrException
	 *             Excepción producida al recuperar los dartos.
	 */
	public static List<TrEmpleado> obtenerJefesUnidad(
			TrAPIUI apiUI, String pkOrganismo) throws TrException {

		List<TrEmpleado> resultado = new ArrayList<TrEmpleado>();
		if (pkOrganismo != null) {
			// Creamos la pk para consultar los empleados
			final TpoPK tpoPk = new TpoPK(pkOrganismo);

			// Obtenemos los empleados asociados al usuario.
			ClausulaOrderBy corder = new ClausulaOrderBy();
			corder.addExpresion(TrEmpleado.CAMPO_APELLIDO1USU,
					OperadorOrderBy.ASCENDENTE);
			ClausulaWhere cw = new ClausulaWhere();
//			cw.addExpresion(TrEmpleado.CAMPO_CODPTOTRAB,
//					OperadorWhere.OP_IGUAL,
//					Constantes.PUESTO_TRABAJO_SUPERVISOR);
			cw.addExpresion(TrEmpleado.CAMPO_FECHACESE, OperadorWhere.OP_IS_NULL);
			final TrEmpleado[] empleados = apiUI.obtenerEmpleados(null, tpoPk,
					"30010", cw, corder);

			if (empleados != null) {
				if (empleados != null && empleados.length > 0) {
					for (int i = 0; i < empleados.length; i++) {
						resultado.add(empleados[i]);
					}
				}
			}
		}
		return resultado;
	}
	
	public static StringBuffer imprimir(List<TrEmpleado> emps){
		StringBuffer tmpBuffer = new StringBuffer();
		for (int i=0; i<emps.size(); i++) {
			
			TrEmpleado tmpEmpleado = emps.get(i);
			
			tmpBuffer.append(tmpEmpleado.getUSUARIO().getAPELLIDO1());
			tmpBuffer.append(" ");
			tmpBuffer.append(tmpEmpleado.getUSUARIO().getAPELLIDO2());
			tmpBuffer.append(", ");
			tmpBuffer.append(tmpEmpleado.getUSUARIO().getNOMBRE());
			tmpBuffer.append(" Pto trabajo: ").append(tmpEmpleado.getPTOTRABAJO().getNOMBRE());
			tmpBuffer.append(" Organismo: ").append(tmpEmpleado.getORGANISMO().getNOMBRE());
			tmpBuffer.append("\n");

		}
		
		return tmpBuffer;
	}
}
