package es.dipucadiz.etir.comun.config;

/**
 * Interface que define las key de los parametros de configuracion.
 * 
 * @version 1.0 30/10/2009
 * @author SDS[FJTORRES]
 */
public interface ParametrosConfig {

	/**
	 * Numero de resultados por pagina en los listados.
	 */
	String RESULTADOS_POR_PAGINA = "config.listados.pagesize";

	/**
	 * Numero de casillas que se crean inicialmente.
	 */
	String NUMERO_CASILLAS_INICIALES = "config.numero.casillas.iniciales";

	/**
	 * Tipo de registro por defecto para Estructuras de entrada planas.
	 */
	String TIPO_REGISTRO_DEFECTO = "config.tipoRegistro.defecto";

	/**
	 * Código de la provincia
	 */
	String PROVINCIA = "codigo.provincia.cadiz";
	
	/**
	 * Municipio Genérico
	 */
	String MUNICIPIO_GENERICO = "config.municipio.generico";
	
	/**
	 * Provincia Genérica
	 */
	String PROVINCIA_GENERICA = "config.provincia.generica";
	
	/**
	 * Concepto Genérica
	 */
	String CONCEPTO_GENERICO = "config.concepto.generico";
	
	/**
	 * Concepto Genérica
	 */
	String MODELO_GENERICO = "config.modelo.generico";
	String VERSION_GENERICA = "config.modelo.generico";
	
	
	/**
	 * Código Territorial Genérico
	 */
	String  CODIGO_TERRITORIAL_GENERICO = "config.codigoterritorial.generico";


	/**
	 * Situacion pendiente de un cargo o subcargo
	 */
	String SITUACION_P = "config.situacion.p";	
	
	/**
	 * Situacion generado de un cargo o subcargo
	 */
	String SITUACION_G = "config.situacion.g";
	
	/**
	 * Movimiento para los históricos de cargo o subcargo
	 */
	String MOVIMIENTO_A = "config.movimiento.a";	
	String MOVIMIENTO_M = "config.movimiento.m";
	
	/**
	 * Tipos y subtipos de Modelos
	 */
	String SUBTIPO_R = "config.subtipo.r";	
	String TIPO_L = "config.tipo.l";

	
	/**
	 * Códigos de parámetros Batch
	 */
	String BATCH_GENERAR_CARGO = "config.batch.generarCargo";
	String BATCH_GENERAR_RECIBO = "config.batch.generarRecibo";
	String BATCH_MODIFICAR_DOMICILIO_FISCAL = "config.batch.modificarDomicilioFiscal";
	String BATCH_MODIFICAR_DATOS_PERSONALES = "config.batch.modificarDatosPersonales";
	String BATCH_BORRADO_NIF_FICTICIOS = "config.batch.borradoNIFFicticios";
	String BATCH_NIF_DUPLICADOS = "config.batch.nifDuplicados";
	String BATCH_CANDIDATOS_NIF_FICTICIOS = "config.batch.candidatosNIFFicticios";
	String BATCH_IMPRIMIR_BORRADOR_DOCUMENTOS = "config.batch.imprimir.borrador.documentos";
	String BATCH_IMPRIMIR_BORRADOR = "config.batch.imprimir.borrador";
	String BATCH_IMPRIMIR_DETALLE = "config.batch.imprimir.detalle";
	String BATCH_IMPRIMIR_CARGO = "config.batch.imprimir.cargo";
	String BATCH_IMPRIMIR_NOTIFICACIONES = "config.batch.imprimir.notificaciones";
	String BATCH_CONFIRMAR_CARGO = "config.batch.confirmar.cargo";
	String BATCH_INFORME_ACT_CENSO_IVTM = "config.batch.informe.censo.ivtm";
	String BATCH_INFORME_CENSO_NO_ACTUALIZADO = "config.batch.informe.censo.no.actualizado";
	String BATCH_INFORME_ERRORES_PORCENTAJE = "config.batch.informe.errores.porcentaje";
	String BATCH_INFORME_NOTIFICACIONES_REV_CAT = "config.batch.informe.notificaciones.revision.catastral";
	String BATCH_CLIENTES_ASOCIACION = "config.batch.clientes.asociacion";
	String BATCH_CLIENTES_ASOCIACION_NIFFICTICIOS = "config.batch.clientes.asociacion.nifficticios";
	String BATCH_CARGOS_GENERAR_FICHERO_INTERCAMBIO = "config.batch.cargos.generar.fichero";
	String BATCH_CARGOS_ENVIAR_A_RECAUDACION = "config.batch.cargos.enviar.recaudacion";
	String BATCH_CARGOS_REENVIAR_A_RECAUDACION = "config.batch.cargos.reenviar.recaudacion";
	String BATCH_INFORME_LISTAS_COBRATORIAS = "config.batch.informe.listas.cobratorias";
	String BATCH_CRUCES_LANZAMIENTO_CRUCES = "config.batch.cruces.lanzamiento.cruces";
	String BATCH_TRIBUTOS_ACTIVAR_INCIDENCIA="config.batch.tributos.activar.incidencia";
	String BATCH_TRIBUTOS_NOTIFICACI0NES_CENSOS = "config.batch.tributos.impresion.notificaciones.censos";
	String BATCH_CATASTRAL_MODIFICACION_MASIVA ="config.batch.catastral.modificacion.masiva";
	String BATCH_UURBANA_ASOCIACION = "config.batch.uurbanas.asociacion";
	String BATCH_CATASTRAL_PUBLICACION_BOP = "config.batch.catastral.publicacion.bop";
	String BATCH_INFORME_BONIF_CENSO = "config.batch.informe.bonificaciones.censo";
	String BATCH_INFORME_BONIF_RECIBO = "config.batch.informe.bonificaciones.recibo";

	/**
	 * Interface que almacena los valores de parametros de configuracion.
	 * 
	 * @version 1.0 01/12/2009
	 * @author SDS[FJTORRES]
	 */
	public interface ValoresParametrosConfig {

		/**
		 * Valor del parametro {@link ParametrosConfig#RESULTADOS_POR_PAGINA}
		 */
		Integer VALOR_REGISTROS_POR_PAGINA = Integer.valueOf(GadirConfig
		        .leerParametro(RESULTADOS_POR_PAGINA));

		/**
		 * Valor del parametro {@link ParametrosConfig#TIPO_REGISTRO_DEFECTO}
		 */
		String VALOR_TIPO_REGISTRO_DEFECTO = GadirConfig
		        .leerParametro(TIPO_REGISTRO_DEFECTO);

		/**
		 * Valor del parametro
		 * {@link ParametrosConfig#NUMERO_CASILLAS_INICIALES}
		 */
		Integer VALOR_NUMERO_CASILLAS_INICIALES = Integer.valueOf(GadirConfig
		        .leerParametro(NUMERO_CASILLAS_INICIALES));
		        
		 /**
		 * Valor del parametro {@link ParametrosConfig#PROVINCIA}
		 */
		String VALOR_PROVINCIA = GadirConfig.leerParametro(PROVINCIA);
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#MUNICIPIO_GENERICO}
		 */
		String VALOR_MUNICIPIO_GENERICO = GadirConfig.leerParametro(MUNICIPIO_GENERICO);
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#PROVINCIA_GENERICA}
		 */
		String VALOR_PROVINCIA_GENERICA = GadirConfig.leerParametro(PROVINCIA_GENERICA);
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#PROVINCIA_GENERICA}
		 */
		String VALOR_CODIGO_TERRITORIAL_GENERICO = GadirConfig.leerParametro(CODIGO_TERRITORIAL_GENERICO);
	
		/**
		 * Valor del parametro {@link ParametrosConfig#CONCEPTO_GENERICO}
		 */
		String VALOR_CONCEPTO_GENERICO = GadirConfig.leerParametro(CONCEPTO_GENERICO);
		
		
		/**
		 * Valor del parametro {@link ParametrosConfig#CONCEPTO_GENERICO}
		 */
		String VALOR_MODELO_GENERICO = GadirConfig.leerParametro(MODELO_GENERICO);
		String VALOR_VERSION_GENERICA = GadirConfig.leerParametro(VERSION_GENERICA);
		
		
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#SITUACION_P}
		 */
		String VALOR_SITUACION_P = GadirConfig.leerParametro(SITUACION_P);
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#SITUACION_G}
		 */
		String VALOR_SITUACION_G = GadirConfig.leerParametro(SITUACION_G);
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#MOVIMIENTO_A}
		 */
		String VALOR_MOVIMIENTO_A = GadirConfig.leerParametro(MOVIMIENTO_A);
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#MOVIMIENTO_M}
		 */
		String VALOR_MOVIMIENTO_M = GadirConfig.leerParametro(MOVIMIENTO_M);
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#SUBTIPO_R}
		 */
		String VALOR_SUBTIPO_R = GadirConfig.leerParametro(SUBTIPO_R);
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#TIPO_L}
		 */
		String VALOR_TIPO_L = GadirConfig.leerParametro(TIPO_L);
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#BATCH_GENERAR_CARGO}
		 */
		String VALOR_BATCH_GENERAR_CARGO = GadirConfig.leerParametro(BATCH_GENERAR_CARGO);
		
		 /**
		 * Valor del parametro {@link ParametrosConfig#BATCH_GENERAR_RECIBO}
		 */
		String VALOR_BATCH_GENERAR_RECIBO = GadirConfig.leerParametro(BATCH_GENERAR_RECIBO);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_MODIFICAR_DOMICILIO_FISCAL}
		 */
		String VALOR_BATCH_MODIFICAR_DOMICILIO_FISCAL = GadirConfig.leerParametro(BATCH_MODIFICAR_DOMICILIO_FISCAL);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_MODIFICAR_DATOS_PERSONALES}
		 */
		String VALOR_BATCH_MODIFICAR_DATOS_PERSONALES = GadirConfig.leerParametro(BATCH_MODIFICAR_DATOS_PERSONALES);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_BORRADO_NIF_FICTICIOS}
		 */
		String VALOR_BATCH_BORRADO_NIF_FICTICIOS = GadirConfig.leerParametro(BATCH_BORRADO_NIF_FICTICIOS);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_NIF_DUPLICADOS}
		 */
		String VALOR_BATCH_NIF_DUPLICADOS = GadirConfig.leerParametro(BATCH_NIF_DUPLICADOS);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_CANDIDATOS_NIF_FICTICIOS}
		 */
		String VALOR_BATCH_CANDIDATOS_NIF_FICTICIOS = GadirConfig.leerParametro(BATCH_CANDIDATOS_NIF_FICTICIOS);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_IMPRIMIR_BORRADOR_DOCUMENTOS}
		 */
		String VALOR_BATCH_IMPRIMIR_BORRADOR_DOCUMENTOS = GadirConfig.leerParametro(BATCH_IMPRIMIR_BORRADOR_DOCUMENTOS);
	
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_IMPRIMIR_BORRADOR}
		 */
		String VALOR_BATCH_IMPRIMIR_BORRADOR = GadirConfig.leerParametro(BATCH_IMPRIMIR_BORRADOR);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_CONFIRMAR_CARGO}
		 */
		String VALOR_BATCH_CONFIRMAR_CARGO = GadirConfig.leerParametro(BATCH_CONFIRMAR_CARGO);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_IMPRIMIR_DETALLE}
		 */
		String VALOR_BATCH_IMPRIMIR_DETALLE = GadirConfig.leerParametro(BATCH_IMPRIMIR_DETALLE);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_IMPRIMIR_CARGO}
		 */
		String VALOR_BATCH_IMPRIMIR_CARGO = GadirConfig.leerParametro(BATCH_IMPRIMIR_CARGO);
	
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_IMPRIMIR_NOTIFICACIONES}
		 */
		String VALOR_BATCH_IMPRIMIR_NOTIFICACIONES = GadirConfig.leerParametro(BATCH_IMPRIMIR_NOTIFICACIONES);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_INFORME_ACT_CENSO_IVTM}
		 */
		String VALOR_BATCH_INFORME_ACT_CENSO_IVTM = GadirConfig.leerParametro(BATCH_INFORME_ACT_CENSO_IVTM);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_INFORME_CENSO_NO_ACTUALIZADO}
		 */
		String VALOR_BATCH_INFORME_CENSO_NO_ACTUALIZADO = GadirConfig.leerParametro(BATCH_INFORME_CENSO_NO_ACTUALIZADO);
	
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_INFORME_ERRORES_PORCENTAJE}
		 */
		String VALOR_BATCH_INFORME_ERRORES_PORCENTAJE = GadirConfig.leerParametro(BATCH_INFORME_ERRORES_PORCENTAJE);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_INFORME_NOTIFICACIONES_REV_CAT}
		 */
		String VALOR_BATCH_INFORME_NOTIFICACIONES_REV_CAT = GadirConfig.leerParametro(BATCH_INFORME_NOTIFICACIONES_REV_CAT);
		
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_CLIENTES_ASOCIACION}
		 */
		String VALOR_BATCH_CLIENTES_ASOCIACION = GadirConfig.leerParametro(BATCH_CLIENTES_ASOCIACION);
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_CLIENTES_ASOCIACION_NIFFICTICIOS}
		 */
		String VALOR_BATCH_CLIENTES_ASOCIACION_NIFFICTICIOS = GadirConfig.leerParametro(BATCH_CLIENTES_ASOCIACION_NIFFICTICIOS);
	
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_CARGOS_GENERAR_FICHERO_INTERCAMBIO}
		 */
		String VALOR_BATCH_CARGOS_GENERAR_FICHERO_INTERCAMBIO = GadirConfig.leerParametro(BATCH_CARGOS_GENERAR_FICHERO_INTERCAMBIO);
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_CARGOS_ENVIAR_A_RECAUDACION}
		 */
		String VALOR_BATCH_CARGOS_ENVIAR_A_RECAUDACION = GadirConfig.leerParametro(BATCH_CARGOS_ENVIAR_A_RECAUDACION);
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_CLIENTES_ASOCIACION_NIFFICTICIOS}
		 */
		String VALOR_BATCH_CARGOS_REENVIAR_A_RECAUDACION = GadirConfig.leerParametro(BATCH_CARGOS_REENVIAR_A_RECAUDACION);
			
		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_INFORME_LISTAS_COBRATORIAS}
		 */
		String VALOR_BATCH_INFORME_LISTAS_COBRATORIAS = GadirConfig.leerParametro(BATCH_INFORME_LISTAS_COBRATORIAS);

		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_CRUCES_LANZAMIENTO_CRUCES}
		 */
		String VALOR_BATCH_CRUCES_LANZAMIENTO_CRUCES = GadirConfig.leerParametro(BATCH_CRUCES_LANZAMIENTO_CRUCES);
		
		String VALOR_BATCH_TRIBUTOS_ACTIVAR_INCIDENCIA=GadirConfig.leerParametro(BATCH_TRIBUTOS_ACTIVAR_INCIDENCIA);
		
		String VALOR_BATCH_TRIBUTOS_NOTIFICACI0NES_CENSOS = GadirConfig.leerParametro(BATCH_TRIBUTOS_NOTIFICACI0NES_CENSOS);
		
		String VALOR_BATCH_CATASTRAL_MODIFICACION_MASIVA = GadirConfig.leerParametro(BATCH_CATASTRAL_MODIFICACION_MASIVA);
		
		String VALOR_BATCH_UURBANA_ASOCIACION = GadirConfig.leerParametro(BATCH_UURBANA_ASOCIACION);
		
		String VALOR_BATCH_CATASTRAL_PUBLICACION_BOP = GadirConfig.leerParametro(BATCH_CATASTRAL_PUBLICACION_BOP);

		/**
		 * Valor del parametro {@link ParametrosConfig#BATCH_INFORME_BONIFICACIONES}
		 */
		String VALOR_BATCH_INFORME_BONIF_CENSO = GadirConfig.leerParametro(BATCH_INFORME_BONIF_CENSO);
		String VALOR_BATCH_INFORME_BONIF_RECIBO = GadirConfig.leerParametro(BATCH_INFORME_BONIF_RECIBO);
		

		
	
		
		
	}
}
