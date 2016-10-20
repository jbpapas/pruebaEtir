package es.dipucadiz.etir.comun.utilidades;

final public class BatchConstants {

	// static final public String CO_PROCESO_CALLEJERO_PONER_SINONIMO_PPAL =
	// "G4215_SINP";

	// Poner sinonimo como principal
	static final public String CO_PROCESO_CALLEJERO_MODIFICAR_CASILLAS = "G4215_MOD_CASILLAS";

	// Asociar calles
	static final public String CO_PROCESO_CALLEJERO_ASOCIAR_CALLE = "G4215_ASOCIAR_CALLES";
	static final public String CO_PROCESO_G63_PROCESO_BATCH = "G63_PROCESO_BATCH";
	// Actualizar tramos postales
	static final public String CO_PROCESO_CALLEJERO_ACTUALIZAR_TRAMOS_POSTALES = "G4215_TRPO";
	static final public String CO_PROCESO_RES_CATASTRAL_IMPRIMIR = "G751_IMPR";
	static final public String CO_PROCESO_RES_CATASTRAL_DOC_RECIBIDOS = "G755_DOC_RECIBIDOS";
	static final public String CO_PROCESO_RES_CATASTRAL_DOC_PEND_LIQUIDAR = "G755_DOC_PEND_LIQ";
	static final public String CO_PROCESO_BAJA_RECIBOS_LIQUID = "G743_BAJA_MASIVA";
	static final public String CO_PROCESO_BAJA_RECIBOS_LIQUID_CLIENTE = "G743_BAJA_MAS_CLIENT";
	static final public String CO_PROCESO_IMPRESION_DIPTICOS = "G743_IMPRESIÓN_DIP";

	// Procesos de generación de documentos y calculo
	static final public String CO_PROCESO_GENLIQ = "G762_GENLIQ";
	static final public String CO_PROCESO_GENLIQ_IBI = "G762_GENLIQ_IBI";
	static final public String CO_PROCESO_GENREC = "G763_GENREC";
	static final public String CO_PROCESO_RECALCULO = "G764_RECALCULO";

	// Procesos de clientes
	static final public String CO_PROCESO_ASOCIACION_CLIENTES = "G722A";
	static final public String CO_PROCESO_FICHA_CONTRIBUYENTE = "G728A";

	// Impresion de notificaciones de alta en el censo
	static final public String CO_PROCESO_IMPR_ALTA_CENSO = "G747_IM_ALTA_CENSO-";
	static final public String CO_PROCESO_REIMPR_ALTA_CENSO = "G747_REIM_ALTA_CENSO";

	// Cuaderno 19
	static final public String CO_PROCESO_ENVIO_C19 = "G71E_ENVIO_CARGOS";// "G71E_ENVIO_CARGOS";
	static final public String CO_PROCESO_ENVIO_P19 = "G71E_ENVIO_PPP";// "G71E_ENVIO_PPP";
	static final public String CO_PROCESO_ENVIO_F19 = "G71E_ENVIO_FRACC";// "G71E_APLICAR_FRACCIONAMIENTO";
	static final public String CO_PROCESO_APLICAR_C19 = "G71E_APLICAR_CARGOS";// "G71E_APLICAR_CARGOS";
	static final public String CO_PROCESO_APLICAR_P19 = "G71E_APLICAR_PPP";// "G71E_APLICAR_PPP";
	static final public String CO_PROCESO_APLICAR_F19 = "G71E_APLICAR_FRACC";// "G71E_ENVIO_FRACCIONAMIENTO";
	static final public String CO_PROCESO_ANULAR_C19 = "G7E_ANULAR_ENVIO_C19";
	static final public String CO_PROCESO_ANULAR_P19 = "G7E_ANULAR_ENVIO_P19";
	static final public String CO_PROCESO_ANULAR_F19 = "G7E_ANULAR_ENVIO_F19";
	static final public String CO_PROCESO_CARGA_D19 = "G4G_CARGA_D19";
	static final public String CO_PROCESO_CARGA_D19_NUEVO = "G4G_CARGA_D19_NUEVO";
	static final public String CO_PROCESO_CARGA_C60 = "G4G_CARGA_C60";
	static final public String CO_PROCESO_CARGA_C60_COBROS_DUPLICADOS = "G4G_CARGA_C60_DUPLIC";
	static final public String CO_PROCESO_CARGA_C43 = "G4G_CARGA_C43";
	static final public String CO_PROCESO_CARGA_SEGURIDADSOCIAL = "G4G_CARGA_TGSS";
	static final public String CO_PROCESO_PROCESO_SEGURIDADSOCIAL = "G4G_PROCESO_TGSS";
	static final public String CO_PROCESO_CARGA_C63 = "G4G_CARGA_C63";
	static final public String CO_PROCESO_PROCESO_C63 = "G4G_PROCESO_C63";

	static final public String VALOR_BATCH_DEUDA_CLIENTES = "G7228_DEUDA_CLIENTE";
	static final public String CO_PROCESO_DEUDA_INEMBARGABLE = "G7229_DEUDA_INEMBARG";
	static final public String CO_PROCESO_INFORME_PRESCRIPCION = "G722C_INF_PRESCRIPC";
	// Cruce
	static final public String CO_PROCESO_CRUCES = "G781A";
	// Corrección de datos
	static final public String CO_PROCESO_ACT_MASIVA_DOCUMENTOS = "G5333_ACT_DOCS";

	// Carga de bancos y sucursales
	static final public String CO_PROCESO_CARGA_BANCOS = "G421N_CARGA";

	// Impresion Expedientes C63
	static final public String CO_PROCESO_IMPRESION_FASE_4_6 = "C63_INFORMES";
	static final public String CO_PROCESO_LEVANTAMIENTO_C63 = "C63_LEVA_EMB_CUENTA";
	static final public String CO_PROCESO_IMPRESION_CARTA_PAGO = "C63_CARTAPAGOPARCIAL";
	static final public String CO_PROCESO_ANULACION_LEVANTAMIENTO = "C63_LEVA_EMB_ANUL";
	static final public String CO_PROCESO_C63_NOTIFICACION = "C63_NOTIEMBARGOCC";

	/**
	 * Constante que almacen el valor que indica el código del proceso de Carga
	 * Dinámica
	 */
	static final public String CO_PROCESO_CARGA_DINAMICA = "G523_CARGA";

	/**
	 * Constante que almacena el valor que indica el código del proceso de
	 * Lanzar Peticion de Extraccion de Informacion
	 */
	static final public String CO_PROCESO_LANZAR_PETICION = "G54_EXTRACCION";

	/**
	 * Constante que almacena el valor que indica el código del proceso de
	 * Lanzar Peticion de Obtencion masiva de candidatos
	 */
	static final public String CO_PROCESO_OBTENCION_MASIVA = "G5334_CANDIDATOS";

	/**
	 * Constante que almacena el valor que indica el código del proceso de Datos
	 * Años Anteriores
	 */
	static final public String CO_PROCESO_DATOS_ANOS_ANTERIORES = "G531_ANNOS";

	static final public String CO_PROCESO_G7B1_GENERACION_PPP = "G7B1_GENERACION_PPP";
	static final public String CO_PROCESO_G7B1_PRORROGAR_PPPS = "G7B1_PRORROGAR_PPPS";
	static final public String CO_PROCESO_G7B1_RECALCULAR_PPP = "G7B1_RECALCULAR_PPP";
	static final public String CO_PROCESO_CONCESION_PPP = "CONCESION_PPP";
	static final public String G7B1_INFORME_PPP_TOTALES_MUNICIPIO = "G7B1_INFORME_TOTALES";
	static final public String G7B1_INFORME_PPP_COMPARATIVA_OTR = "G7B1_INFORME_PPP_OTR";

	static final public String CO_PROCESO_G7B1_SACAR_TRIBUTOS_DEL_PPP = "G7B1_SACAR_TRIB_PPP";
	static final public String CO_PROCESO_G7B1_METER_TRIBUTOS_AL_PPP = "G7B1_METER_TRIB_PPP";

	static final public String CO_PROCESO_G7B6_APLICAR_PPPS = "G7B6_APLICAR_PPPS";

	static final public String CO_PROCESO_G771_GENERAR_CARGO = "G771_GENERAR_CARGO";
	static final public String CO_PROCESO_G771_ENV_CARGO_RECAUD = "G771_CARGOS_ENVI_A_RECAUDACION";
	static final public String CO_PROCESO_G771_FICHERO_INTERCAMBIO_GFS = "G771G_FICHINTERSIGRE";
	static final public String CO_PROCESO_G771_FICHERO_INTERCAMBIO_GFI = "G771G";
	static final public String CO_PROCESO_G771_RE_FICHERO_INTERCAMBIO = "G771G_RE_FICINTERSIG";

	static final public String CO_PROCESO_G752_DEVOLUCION_RESOLUCIONES_CATASTRALES = "G752_DEVOL_RES_CAT";

	static final public String CO_PROCESO_G7D1_GENERACION_FRACCIONAMIENTO = "G7D1_GENERACION_FRAC";
	// static final public String CO_PROCESO_G7D1_HOJA_AMORTIZACION =
	// "G7D1_HOJA_AMORTIZACION";
	static final public String CO_PROCESO_G7D1_HOJA_AMORTIZACION = "G7D1_HOJA_AMORTIZACI";

	static final public String CO_PROCESO_G7C2_ACTUALIZACION_CODIGOS_TERRITORIALES = "G7C2_CAMBIO_CONVENIO";

	static final public String G771_CARGOS_CONFIRMAR = "G771C";
	static final public String G771_CARGOS_ENVI_SPRGT = "G771G";
	static final public String G771_CARGOS_ENVI_A_RECAUDACION = "G771E";
	static final public String G771_CARGOS_RECH_ENVI_SPRGT = "G771K";

	/**
	 * Listas cobratorias
	 */
	static final public String CO_LISTAS_COBRATORIAS = "G791A";

	// Actualización de basura
	static final public String CO_PROCESO_ACTUALIZACION_BASURA = "G742A";

	static final public String CO_PROCESO_G773_IMPRESION_DIPTICOS = "G773_IMPR_DIPTICOS";
	static final public String CO_PROCESO_G773_IMPRESION_DIPTICO_UNICO = "G773_IMPR_DIPT_UNICO";
	static final public String CO_PROCESO_G743_IMPRESION_DIPTICO_UNICO = "G743_IMPR_DIP_UNICO";
	static final public String CO_PROCESO_PAGO_COMPENSACION = "G743_PAGO_COMPENSACI";
	static final public String CO_PROCESO_PAGO_TRANSFERENCIA = "G7E4_PAGO_TRANSFEREN";
	static final public String CO_PROCESO_ASIGNAR_DEPOSITO = "G757_ASIGNAR_DEPO";
	static final public String CO_PROCESO_PAGAR_DOCUMENTOS_DEPOSITO_JUNTA = "G758_PAGAR_DEPO_MASI";
	static final public String CO_PROCESO_PAGAR_DOCUMENTOS_DEPOSITOS = "G759_PAGAR_DOCS_DEPO";
	static final public String CO_PROCESO_PAGAR_DOCUMENTOS_DEPOSITOS_FICHERO = "G759_PAGO_DOC_DEPOSI";
	static final public String CO_PROCESO_G7A5_CARGA_DOMS = "G7A5_CARGA_DOMS";
	static final public String CO_PROCESO_G743_BAJA_MASIVA = "G743_BAJA_MASIVA_VAL";
	static final public String CO_PROCESO_G743_SUSPENSION_MASIVA = "G743_SUSPENSION_MASI";

	/*
	 * Envío de SMS
	 */
	static final public String CO_G727_ENVIO_SMS = "G727_ENVIO_SMS";

	static final public String CO_PROCESO_BAJA_RECIBO_FRACCIONAMIENTO = "G7D1_BAJA_RECIBO";

	static final public String CO_PROCESO_G7A1_IMPRIMIR_CONFIRMACION = "G7A1_CONFIRMACION";
	public static final String CO_PROCESO_G7A1_IMPRIMIR_PROVISIONAL = "G7A1_CONF_PROV";
	static final public String CO_PROCESO_G7A1_MODIFICACION = "G7A1_MODIFICACION";
	static final public String CO_PROCESO_G7A1_BAJA = "G7A1_BAJA";

	static final public String CO_PROCESO_G7A2_BATCH = "G7A2_BATCH";

	// Imprimir fórmulas
	static final public String CO_PROCESO_G765_IMPRIMIR_FORMULA = "G765_PRINT_FORMULA";

	// Informes gestión cartera
	static final public String CO_PROCESO_PARTE_CAJA = "G7F1_PARTE_CAJA";
	static final public String CO_PROCESO_CUADRE_CAJA = "G7F1_CUADRE_CAJA";
	static final public String CO_PROCESO_PARTE_CAJA_AY = "G7F1_PARTE_CAJA_AY";
	static final public String CO_PROCESO_CUADRE_CAJA_AY = "G7F1_CUADRE_CAJA_AY";
	static final public String CO_PROCESO_CUENTA_GESTION_RECAUDATORIA = "G7F2_CUENTA_GEST";
	static final public String CO_PROCESO_RESUMEN_ESTADO_COBRO = "G7F2_RESUMEN_ESTCO";
	static final public String CO_PROCESO_INFORME_DETALLE = "G7F2_INFORME_DETALLE";
	static final public String CO_PROCESO_CUENTA_GESTION_REC = "G7F8_CUENTA_GEST";
	static final public String CO_PROCESO_CUENTA_GESTION_REC_MES = "G7F2_GESTION_REC_MES";
	// Informe de liquidaciones
	static final public String CO_PROCESO_INFORME_LIQUIDACIONES = "G7F5_INFORME_LIQ";
	// Informes listado censo
	static final public String CO_PROCESO_LISTADO_CENSO = "G797_LISTADO_CENSO";

	// Procesos BOP
	static final public String CO_PROCESO_G7E7_PETICION_BOP = "G7E7A";
	static final public String CO_PROCESO_G7E7_NOTIFICACION_BOP = "G7E7B";

	// Expedientes
	static final public String CO_PROCESO_IMPRIMIR_EXPEDIENTES = "EXP_IMPRIMIR";
	static final public String CO_PROCESO_TRAMITADOR_AUTOMATICO = "EXP_TRAMITADOR_AUTO";
	static final public String CO_PROCESO_CADUCIDAD_EXPEDIENTES = "EXP_CADUCIDAD";
	static final public String CO_PROCESO_BORRADO_EXPEDIENTES = "G62_BORRAR_EXP";
	static final public String CO_PROCESO_FINAL_PORTAFIRMA = "EXP_FINAL_PORTAFIRMA";
	static final public String CO_PROCESO_ABRIR_PROVIAPREM = "EXP_ABRIR_PROVIAPREM";

	// Ejecutiva
	public static final String CO_PROCESO_PASE_EJECUTIVA_INDIVIDUAL = "G7G1_PASE_EJECUT_IND";
	public static final String CO_PROCESO_PASE_EJECUTIVA_MASIVO = "G7G1_PASE_EJECUT_MAS";
	public static final String CO_PROCESO_DEVOLUCION_VOLUNTARIA = "G7G5_VUELTA_A_VOL";

	// Utilidades
	public static final String CO_PROCESO_RETROCEDER_CARGO = "G4K1_RETRO_CARGO";

	// Carga de la Junta de Andalucía
	public static final String CO_PROCESO_CARGA_JUNTA = "G525_BATCH";

	public static final String CO_PROCESO_ERRORES_R = "CO_ERRORES_RGC";
	public static final String CO_PROCESO_EXLCUIR_ERRORES_RGC = "G527EXCL_BATCH";

	public static final String CO_ANULAR_PAGO = "G743_ANULAR_PAGO";

	public static final String CALCULO_RECARGO_20 = "CALCULO_RECARGO_20";
	public static final String FIRMA_PROV_SIN_NOTIF = "FIRMA_PROV_SIN_NOTIF";

	public static final String ALTA_MANUAL_DOCUMENTO = "ALTA_MANUAL_DOC";
	public static final String CALCULO_MANUAL_DOCUMENTO = "CALCULO_MANUAL_DOC";
	public static final String CREAR_HOJAS_DOCUMENTO = "CREAR_HOJAS_DOC";
	public static final String GUARDAR_CASILLAS_DOCUMENTO = "GUARDAR_CASILLAS_DOC";
	public static final String ACTUALIZAR_CON_DOCUMENTO_ORIGEN = "ACT_CON_DOC_ORIGEN";

	// Actualizar censo alcantarillado
	public static final String ACTUALIZAR_CENSO_ALCANTARILLADO = "G74B_ALCANTARILLADO";

	// Generación de RGC
	public static final String GENERACION_RGC = "G527_GENERACIONRGC";

	// Envio de información de Ayuntamientos
	public static final String ENVIO_INFORMACION_AYUNTAMIENTOS = "G7F3_ENVINFAYTO";
	public static final String ENVIO_INFORMACION_PUERTO_SANTAMARIA = "G7F4_DATA_PUERTO";

	// Envio de información Comunidad Regantes Costa Noroeste
	public static final String ENVIO_INFORMACION_COMUNIDAD_REGANTES = "G7F7_ENVINF_COMREGAN";

	// Envio de información genérico
	public static final String ENVIO_INFORMACION_GENERICO = "G7F4_ENVINFAYTOGEN";
	// Solicitar informe depósito
	public static final String SOLICITUD_INFORME_DEPOSITO = "G757_INFORME";

	// Movimientos contables
	public static final String SOLICITUD_INFORME_TRANSFERENCIA = "G7E5_INFORME";
	public static final String CO_PROCESO_ASIGNAR = "G7E5_TRANSFERENCIAS";

	// Informes C60
	public static final String SOLICITUD_INFORME_EMISORA = "G7E3_INFORME";
	public static final String SOLICITUD_INFORME_RESUMEN_EMISORAS = "G7E3_INFORME_EMISORA";

	// Duplicar bonificaciones
	public static final String CO_PROCESO_DUPLICAR_BONIFI = "G717_DUPLICAR_BONIF";

	// Informe Cuotas anuales, cuotas de Diputación
	public static final String SOLICITUD_INFORME_CUOTAS_DIPUTACION = "G7F6_INFORME";

	// Informe Incidencia Junta entre fechas

	public static final String INCIDENCIA_JUNTA_FECHAS = "G7H4_INCIDENC_FX";
	public static final String DOCUMENTOS_VARIAS_INCIDENCIA_JUNTA = "G7H4_DOC_VAR_INC";
	public static final String DOCUMENTOS_INCIDENCIA_JUNTA_PENDT = "G7H4_DOC_INC_PEN";

	// Réplicas
	public static final String GENERAR_REPLICAS = "G7F4_REPLI_GENERAR";
	public static final String ENVIAR_REPLICAS = "G7F4_REPLI_ENVIAR";
	public static final String RECHAZAR_REPLICAS = "G7F4_REPLI_RECHAZAR";
	public static final String ACEPTAR_REPLICAS = "G7F4_REPLI_ACEPTAR";

	// Integración IDECadiz
	public static final String FICHERO_PETICION_XY = "G7H5IDECADIZPETICION";
	public static final String FICHERO_ACTUALIZACION_XY = "G7H5IDECADIZACTUALIZ";

	// Remesas
	public static final String CO_PROCESO_GENERAR_REMESA = "G7J1_GENERAR_REMESA";
	public static final String CO_PROCESO_RECHAZAR_REMESA = "G7J1_RECHAZAR_REMESA";
	public static final String CO_PROCESO_HABILITAR_REMESA = "G7J1_ACTIVAR_REMESA";
	public static final String CO_PROCESO_INFORME_DOCS_NOTIF = "G7J1_INFORME_DOCS";
	public static final String CO_PROCESO_PUBLICA_NOTSEDE_REMESA = "G7J1_PUBLICA_NOTSEDE";

	// Ficheros embargo AEAT
	public static final String CO_PROCESO_EMBARGO_AEAT_COBROS = "G7G9_AEAT_COBROS";
	public static final String CO_PROCESO_EMBARGO_AEAT_DEPOSITOS = "G7G9_AEAT_DEPOSITOS";

	// Cálculo de intereses masivo.
	public static final String CO_PROCESO_G7EB_CALCULO_INTERESES_MASIVO = "G7EB_INTERES_MASIVO";

}
