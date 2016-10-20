package es.dipucadiz.etir.comun.dao;




public interface QueryName {

	String Carga_findByCoCarga = "Carga.findByCoCarga";
	String Carga_findByCoCargaControlRecepcion = "Carga.findByCoCargaControlRecepcion";
	
	String Validaciones_findByMunicipioAndConceptoAndModeloAndVersion = "Validaciones.findByMunicipioAndConceptoAndModeloAndVersion";
	String Validaciones_findMunicipios = "Validaciones.findMunicipios";
	String Validaciones_findByMunicipioAndConceptoAndModelo = "Validaciones.findByMunicipioAndConceptoAndModelo";
	String Validaciones_findByMunicipioAndConcepto = "Validaciones.findByMunicipioAndConcepto";
	String Validaciones_findByMunicipio = "Validaciones.findByMunicipio";
	String ExtraccionInforme_findExtraccionInformeEstructs = "ExtraccionInforme.findExtraccionInformeEstructs";
	String ExtraccionEstructura_findExtraccionEstructuraCampos = "ExtraccionEstructura.findExtraccionEstructuraCampos";
	String ExtraccionOrden_findExtraccionOrdenCampos = "ExtraccionOrden.findExtraccionOrdenCampos";
	String ExtraccionCriterio_findExtraccionCondiciones = "ExtraccionCriterio.findExtraccionCondiciones";
	String ExtraccionTipoRegistro_findExtraccionCriteriosAndOrdenesAndEstructuras = "ExtraccionTipoRegistro.findExtraccionCriteriosAndOrdenesAndEstructuras";
	String Extraccion_findTipoRegistrosAndInformes = "Extraccion.findTipoRegistrosAndInformes";
	String Carga_findByMunicipioAndConceptoAndModelo = "Carga.findByMunicipioAndConceptoAndModelo";
	String Carga_findByMunicipioAndConcepto = "Carga.findByMunicipioAndConcepto";
	String Carga_findByMunicipio = "Carga.findByMunicipio";
	String Correspondencias_findByMunicipioAndConceptoAndModeloAndVersion = "Correspondencias.findByMunicipioAndConceptoAndModeloAndVersion";
	String Correspondencias_findByMunicipio = "Correspondencias.findByMunicipio";
	String Correspondencias_findByMunicipioAndConceptoAndModelo = "Correspondencias.findByMunicipioAndConceptoAndModelo";
	String Correspondencias_findByMunicipioAndConcepto = "Correspondencias.findByMunicipioAndConcepto";
	String Correspondencias_findMunicipios = "Correspondencias.findMunicipios";
	
	String CargaCriterio_findByCargaAndTipoRegistroAndCriterio = "CargaCriterio.findByCargaAndTipoRegistroAndCriterio";
	String CargaTipoRegistro_findByCargaAndTipoRegistro = "CargaTipoRegistro.findByCargaAndTipoRegistro";
	String Carga_findByMunicipioAndConceptoAndModeloAndVersion = "Carga.findByMunicipioAndConceptoAndModeloAndVersion";
	String Carga_findMunicipios = "Carga.findMunicipios";
	String CasillasRelacionadas_findModeloRelByMuniConceptoModeloVersionMuniRelConceptoRel = "CasillasRelacionadas.findModeloRelByMuniConceptoModeloVersionMuniRelConceptoRel";
	String CasillasRelacionadas_findVersionesRelacionadasByAll = "CasillasRelacionadas.findVersionesRelacionadasByAll";
	
	String CasillasRelacionadas_findModeloRelByMuniConceptoModeloVersionMuniRel = "CasillasRelacionadas.findModeloRelByMuniConceptoModeloVersionMuniRel";
	
	String CASILLAS_RELACIONADAS_FIND_BY_MUNICIPIO_AND_CONCEPTO="CasillasRelacionadas.findByMunicipioAndConcepto";
	String CASILLAS_RELACIONADAS_FIND_BY_MUNICIPIO_AND_CONCEPTO_AND_MODELO="CasillasRelacionadas.findByMunicipioAndConceptoAndModelo";
	String CASILLAS_RELACIONADAS_FIND_MUNICIPIOREL_BY_MUNICIPIO_CONCEPTO_MODELO_VERSION="CasillasRelacionadas.findMunicipioRelByMunicipioConceptoModeloVersion";
	String CASILLAS_RELACIONADAS_FIND_BY_MUNICIPIO="CasillasRelacionadas.findByMunicipio";
	String CASILLAS_RELACIONADAS_FIND_ALL= "CasillasRelacionadas.findByAll";


	String CALLE_UBICACION_FIND_BY_PROVINCIA_AND_MUNICIPIO = "CalleUbicacion.findByProvinciaAndMunicipio";
	String CALLE_FIND_BY_MUNICIPIO_AND_UBICACION= "Calle.findCallesByMunicipioAndUbicacion";
	String CALLE_UBICACION_FIND_BY_PROVINCIA_AND_MUNICIPIO_AND_UBICACION = "CalleUbicacion.findByProvinciaAndMunicipioAndUbicacion";
	String CALLE_SINONIMO_FIND_BY_SINONIMO_AND_SIGLAS = "CalleSinonimo.findBySinonimoAndSiglas";
	String CALLE_FIND_CALLES_BY_MUNICIPIO_AND_NOMBRECALLE_AND_UBICACION = "Calle.findCallesByMunicipioAndNombreCalleAndUbicacion";
	String CALLE_FIND_CALLES_CON_MUNICIPIO_BY_ID="Calle.findCallesConMunicipioByID";
	String CALLE_FIND_CALLES_BY_MUNICIPIO_AND_NOMBRECALLE_SIN_UBICACION= "Calle.findCallesByMunicipioAndNombreCalleSinUbicacion";
	String CALLE_FIND_CALLES_BY_MUNICIPIO_AND_CODIGO_MUNICIPAL="Calle.findCallesByMunicipioAndCodigoMunicipal";
	String MENSAJE_ERROR_FIND_MENSAJES_ERROR_BY_NOMBRE="MensajeError.findMensajesErrorByNombre";
	String MENSAJE_ERROR_FIND_MENSAJES_ERROR_BY_CODIGO_MINIMO="MensajeError.findMensajesErrorByCodigoMinimo";
	String VERSION_FIND_VERSIONES_BY_MODELO2="Version.findVersionesByModelo2";
	String VERSION_FIND_ALL_ORDENADOS="Version.findAllOrdenados";
	String SIGRETASA_VALIDACION_GRABAR_DATOS="SigreTasa.ValidacionGrabarDatos";
	String SIGRETASA_FIND_TASAS_BY_NOMBRE_MUNICIPIO_AND_CODIGO="SigreTasa.findTasasByNombreMunicipioAndCodigo";
	String SIGRETASA_FIND_TASAS_BY_PROVINCIA_AND_MUNICIPIO="SigreTasa.findTasasByProvinciaAndMunicipio";
	String SIGRETASA_FIND_TASAS_BY_PROVINCIA_AND_CONCEPTO="SigreTasa.findTasasByMunicipioAndConcepto";
	String CASILLA_MODELO_FIND_CASILLAS_BY_MODELO_AND_VERSION="CasillaModelo.findCasillasByModeloAndVersion";
	String SIGRE_SUBCONCEPTO_EXISTE_SUBCONCEPTO="SigreSubconcepto.existeSubconcepto";
	String SIGRE_SUBCONCEPTO_FIND_BY_PROVINCIA_AND_MUNICIPIO_AND_TASA="SigreSubconcepto.findSigreSubconceptosByProvinciaAndMunicipioAndTasa";
	String SIGRE_SUBCONCEPTO_FIND_BY_PROVINCIA_AND_MUNICIPIO = "SigreSubconcepto.findSigreSubconceptosByProvinciaAndMunicipio";
	String SIGRE_PERIODO_FIND_PERIODOS_SIGRE_BY_PROVINCIA_MUNICIPIO="SigrePeriodo.findPeriodoSigreByProvinciaMunicipio";
	String SIGRE_PERIODO_FIND_PERIODOS_SIGRE_GADIR_BY_PROVINCIA_MUNICIPIO="SigrePeriodo.findPeriodosSigreGadirByProvinciaMunicipio";
	String ACM_PROCESO_BOTON_FIND_ACM_PROCESO_BOTON_BY_PROCESO="AcmProcesoBoton.findAcmProcesoBotonByProceso";
	String PROCESO_PARAMETRO_FIND_PARAMETROS_BY_PROCESO_ORDENADOS_BY_NUMERO_PARAMETRO="ProcesoParametro.findParametrosByProcesoOrdenadosByNumeroParametro";
	String PROCESO_ACCION_FIND_ACCIONES_BY_PROCESO_ORDENADOS_BY_ACCION="ProcesoAccion.findAccionesByProcesoOrdenadosByAccion";
	String INCIDENCIA_SITUACION_FIND_BY_PROCESO="IncidenciaSituacion.FindByProceso";
	String ACMBOTON_FIND_ACMBOTONS_BY_PROCESO="AcmBoton.findAcmBotonsByProceso";
	String AYUDA_CAMPO_FIND_CAMPOS_BY_PROCESO="AyudaCampo.findCamposByProceso";
	String ACMMENU_FIND_ACMMENUS_BY_PROCESO="AcmMenu.findAcmMenuByProceso";
	String EJECUCION_FIND_BY_PROCESO="Ejecucion.findByProceso";
	String PROCESO_ACCION_FIND_ACCIONES_BY_PROCESO="ProcesoAccion.findAccionesByProceso";
	String ACMPROCESO_BOTON_FIND_ACMPROCESO_BOTON_BY_PROCESO_AND_BOTON ="AcmProcesoBoton.findAcmProcesoBotonByProcesoAndBoton";
	String PROCESO_ACCION_FIND_ACCIONES_BY_PROCESO_AND_CODIGO ="ProcesoAccion.findAccionesByProcesoAndCodigo";
	String FUNCION_ARGUMENTO_FIND_FUNCION_ARGUMENTO_BY_CODIGO_FUNCION="FuncionArgumento.findFuncionArgumentoByCodigoFuncion";
	String FUNCION_ARGUMENTO_FIND_FUNCION_ARGUMENTO_ORDEN_MAXIMO="FuncionArgumento.findFuncionArgumentoOrdenMaximo";
	String VALIDACIONES_FIND_BY_FUNCION="Validaciones.findByFuncion";
	String FORMULA_PASO_FIND_BY_FUNCION="FormulaPaso.findByFuncion";
	String CORRESPONDENCIA_FIND_BY_FUNCION="Correspondencia.findByFuncion";
	String FUNCION_ARGUMENTO_VALIDAR_FUNCION_ARGUMENTO_LOGICO="FuncionArgumento.ValidarFuncionArgumentoLogico";
	String FUNCION_ARGUMENTO_VALIDAR_FUNCION_ARGUMENTO = "FuncionArgumento.ValidarFuncionArgumento";
	String FUNCION_ARGUMENTO_FIND_FUNCION_ARGUMENTO_BY_ORDEN_AND_FUNCION="FuncionArgumento.findFuncionArgumentoByOrdenAndFuncion";
	String FUNCION_ARGUMENTO_FIND_FUNCION_ARGUMENTO_BY_CODIGO_FUNCION_DESCENDENTE="FuncionArgumento.findFuncionArgumentoByCodigoFuncionDescendente";
	String FUNCION_FIND_FUNCIONES_BY_TIPO = "Funcion.findFuncionesByTipo";
	String FUNCION_FIND_FUNCIONES_BY_NOMBRE = "Funcion.findFuncionesByNombre";
	String FUNCION_FIND_FUNCIONES_BY_NOMBRE_AND_TIPO = "Funcion.findFuncionesByNombreAndTipo";
	String FUNCION_FIND_FUNCIONES_BY_CODIGO = "Funcion.findFuncionesByCodigo";
	String FUNCION_FIND_FUNCIONES_BY_CODIGO_AND_TIPO = "Funcion.findFuncionesByCodigoAndTipo";
	String FUNCION_FIND_FUNCIONES_BY_CODIGO_AND_NOMBRE = "Funcion.findFuncionesByCodigoAndNombre";
	String FUNCION_FIND_FUNCIONES_BY_CODIGO_AND_NOMBRE_AND_TIPO = "Funcion.findFuncionesByCodigoAndNombreAndTipo";
	String PROCESO_FIND_BY_TIPO="Proceso.findByTipo";
	String AYUDA_CAMPO_FIND_BY_PROCESO_AND_CAMPO="AyudaCampo.findByProcesoAndCampo";
	String PROCESO_FIND_PROCESO_AYUDA="Proceso.findProcesoAyuda";
	String ACMBOTON_PROCESOS_TIPO_M_Y_P="AcmBoton.ProcesosTipoMyP";
	String PROCESO_FIND_BY_TIPO_MENU="Proceso.findByTipoMenu";
	String ACMMENU_FIND_ACMMENU_PADRE="AcmMenu.findAcmMenuPadre";
	String ACMMENU_FIND_ACMMENU_BY_NOMBRE="AcmMenu.findAcmMenuByNombre";
	String ACMPERFIL_MENU_FIND_ACM_PERFIL_MENU_BY_MENU="AcmPerfilMenu.findAcmPerfilMenuByMenu";
	String ACMPERFIL_MENU_FIND_ACMPEFRIL_MENU_BY_PERFIL_AND_MENU="AcmPerfilMenu.findAcmPerfilMenuByPerfilAndMenu";
	String PROCESO_PARAMETRO_FIND_PARAMETROS_BY_PROCESO_AND_NUMERO="ProcesoParametro.findParametrosByProcesoAndNumero";
	String PLANTILLA_FIND_PLANTILLA_ODT="Plantilla.findPlantillaOdt";
	String HEJECUCION_FIND_BY_CODIGO_ORDENADOS_BY_FECHA="HEjecucion.findByCodigoOrdenadosByFecha";
	String EJECUCION_PARAMETRO_FIND_BY_CODIGO="EjecucionParameto.findByCodigo";
	String EJECUCION_PARAMETRO_FIND_BY_CODIGO_NUMERO="EjecucionParameto.findByCodigoNumero";
	String INFORME_FIND_BY_CODIGO_EJECUCION="Informe.findByCodigoEjecucion";
	String HEJECUCION_FIND_BY_CODIGO_AND_ESTADO="HEjecucion.findByCodigoAndEstado";
	String PROCESO_FIND_BY_TIPO_BATCH ="Proceso.findByTipoBatch";
	String INFORME_FIND_BY_USUARIO_AND_NOMBRE_AND_FECHAS="Informe.findByUsuarioAndNombreAndFechas";
	
String Informe_findByUsuarioAndNombreAndFechaInicio = "Informe.findByUsuarioAndNombreAndFechaInicio";
String Informe_findByUsuarioAndNombreAndFechaFin = "Informe.findByUsuarioAndNombreAndFechaFin";
String Informe_findByUsuarioAndNombre = "Informe.findByUsuarioAndNombre";
String Informe_findByUsuarioAndFechas = "Informe.findByUsuarioAndFechas";
String Informe_findByUsuarioAndFechaInicio = "Informe.findByUsuarioAndFechaInicio";
String Informe_findByUsuarioAndFechaFin = "Informe.findByUsuarioAndFechaFin";
String Informe_findByUsuario = "Informe.findByUsuario";
String Informe_findByNombreAndFechas = "Informe.findByNombreAndFechas";
String Informe_findByNombreAndFechaInicio = "Informe.findByNombreAndFechaInicio";
String Informe_findByNombre = "Informe.findByNombre";
String Informe_findByFechas = "Informe.findByFechas";
String Informe_findByFechaInicio = "Informe.findByFechaInicio";
String Informe_findByFechaFin = "Informe.findByFechaFin";
String Informe_findAllByFecha = "Informe.findAllByFecha";
String Informe_findByUsuarioAndNombreAndFechas = "Informe.findByUsuarioAndNombreAndFechas";
//String Informe_findByUsuarioAndNombreAndFechaInicio = "Informe.findByUsuarioAndNombreAndFechaInicio";
//String Informe_findByUsuarioAndNombreAndFechaFin = "Informe.findByUsuarioAndNombreAndFechaFin";
//String Informe_findByUsuarioAndNombre = "Informe.findByUsuarioAndNombre";
//String Informe_findByUsuarioAndFechas = "Informe.findByUsuarioAndFechas";
//String Informe_findByUsuarioAndFechaInicio = "Informe.findByUsuarioAndFechaInicio";
//String Informe_findByUsuarioAndFechaFin = "Informe.findByUsuarioAndFechaFin";
//String Informe_findByUsuario = "Informe.findByUsuario";;
//String Informe_findByNombreAndFechas = "Informe.findByNombreAndFechas";
//String Informe_findByNombreAndFechaInicio = "Informe.findByNombreAndFechaInicio";
String Informe_findByNombreAndFechaFin = "Informe.findByNombreAndFechaFin";
//String Informe_findByNombre = "Informe.findByNombre";
//String Informe_findByFechas = "Informe.findByFechas";
//String Informe_findByFechaInicio = "Informe.findByFechaInicio";
//String Informe_findByFechaFin = "Informe.findByFechaFin";
//String Informe_findAllByFecha = "Informe.findAllByFecha";
//String Informe_findByUsuarioAndNombreAndFechas = "Informe.findByUsuarioAndNombreAndFechas";
//String Informe_findByUsuarioAndNombreAndFechaInicio = "Informe.findByUsuarioAndNombreAndFechaInicio";
//String Informe_findByUsuarioAndNombreAndFechaFin = "Informe.findByUsuarioAndNombreAndFechaFin";
//String Informe_findByUsuarioAndNombre = "Informe.findByUsuarioAndNombre";
//String Informe_findByUsuarioAndFechas = "Informe.findByUsuarioAndFechas";
//String Informe_findByUsuarioAndFechaInicio = "Informe.findByUsuarioAndFechaInicio";
//String Informe_findByUsuarioAndFechaFin = "Informe.findByUsuarioAndFechaFin";
//String Informe_findByUsuario = "Informe.findByUsuario";
//String Informe_findByNombreAndFechas = "Informe.findByNombreAndFechas";
//String Informe_findByNombreAndFechaInicio = "Informe.findByNombreAndFechaInicio";
//String Informe_findByNombreAndFechaFin = "Informe.findByNombreAndFechaFin";
//String Informe_findByNombre = "Informe.findByNombre";
//String Informe_findByFechas = "Informe.findByFechas";
//String Informe_findByFechaInicio = "Informe.findByFechaInicio";
//String Informe_findByFechaFin = "Informe.findByFechaFin";
	
String CASILLA_MUNICIPIO_FIND_MUNICIPIOS="CasillaMunicipio.findMunicipios";
String CASILLA_MUNICIPIO_FIND_BY_MUNICIPIO_AND_CONCEPTO_AND_MODELO_AND_VERSION="CasillaMunicipio.findByMunicipioAndConceptoAndModeloAndVersion";
String CASILLA_MUNICIPIO_FIND_BY_MUNICIPIO="CasillaMunicipio.findByMunicipio";
String CASILLA_MUNICIPIO_FIND_BY_MUNICIPIO_AND_CONCEPTO = "CasillaMunicipio.findByMunicipioAndConcepto";
String CASILLA_MUNICIPIO_FIND_BY_MUNICIPIO_AND_CONCEPTO_AND_MODELO = "CasillaMunicipio.findByMunicipioAndConceptoAndModelo";
String CASILLAS_RELACIONADAS_FIND_MUNICIPIOS="CasillasRelacionadas.findMunicipios";
	
	/**
	 * Obtiene los modelos cuyo codigo sea igual o mayor al dado.
	 */
	String MODELOS_BY_CODIGO_DESDE = "Modelo.findModelosByCodigo";

	/**
	 * Obtiene los modelos cuyo concepto.
	 */
	String MODELOS_BY_CONCEPTO = "Modelo.findModelosByConcepto";

	/**
	 * Obtiene los conceptos cuyo codigo sea igual o mayor al dado.
	 */
	String CONCEPTOS_BY_CODIGO_DESDE = "Concepto.findConceptosByCodigo";

	/**
	 * Obtiene los conceptos cuyo municipio sea igual o mayor al dado.
	 */
	String CONCEPTOS_BY_MUNICIPIO_DESDE = "Concepto.findConceptosByMunicipio";

	/**
	 * Obtiene los conceptos cuyo municipio sea igual o mayor al dado.
	 */
	String CONCEPTOS_BY_MUNICIPIO_Y_CODIGO_DESDE = "Concepto.findConceptosByCodigoAndMunicipio";

	/**
	 * Obtiene los municipios cuyo codigo sea igual o mayor al dado.
	 */
	String MUNICIPIOS_BY_CODIGO_DESDE = "Municipio.findMunicipiosByCodigo";

	/**
	 * Obtiene las versiones cuyo modelo sea igual o mayor al dado.
	 */
	String VERSIONES_BY_MODELO = "Version.findVersionesByModelo";

	/**
	 * Obtiene las versiones cuyo codigo sea igual o mayor al dado.
	 */
	String VERSIONES_BY_CODIGO_DESDE = "Version.findVersionesByCodigo";

	/**
	 * Obtiene las versiones cuyo modelo y codigo sean igual o mayor a los
	 * dados.
	 */
	String VERSIONES_BY_MODELO_Y_CODIGO_DESDE = "Version.findVersionesPorModeloYCodigo";

	String VERSIONES_BY_MODELO_Y_CODIGO_DESDE_IGUALES = "Version.findVersionesPorModeloYCodigoIguales";

	/**
	 * Obtiene una version segun el codigo de modelo y el de version dado.
	 */
	String VERSION_BY_MODELO_Y_VERSION = "Version.findVersionPorModeloYCodigo";

	String CARGA_CONTROLES_RECEPCION = "Carga.findCarga";

	String CARGA_CONTROLES_RECEPCION_DESDE = "Carga.findCargaDesde";

	String CARGA_CONTROLES_RECEPCION_SIN_FECHA = "Carga.findCargaSinFecha";
	
	String CARGA_CONTROLES_DESDE = "Carga.findDesde";
	
	String CARGA_CONTROLES_DESDE_HASTA = "Carga.findDesdeHasta";
	
	String CARGA_CONTROLES_MUNICIPIO = "Carga.findCargaMunicipio";
	
	String CARGA_CONTROLES_MUNICIPIO_DESDE = "Carga.findCargaMunicipioDesde";
	
	String CARGA_CONTROLES_MUNICIPIO_DESDE_HASTA = "Carga.findCargaMunicipioDesdeHasta";
	
	String CARGA_CONTROLES_CONCEPTO = "Carga.findCargaConcepto";
	
	String CARGA_CONTROLES_CONCEPTO_TODOS_MUNICIPIOS = "Carga.findCargaConceptoTodosMunicipios";
	
	String CARGA_CONTROLES_CONCEPTO_DESDE = "Carga.findCargaConceptoDesde";
	
	String CARGA_CONTROLES_CONCEPTO_DESDE_HASTA = "Carga.findCargaConceptoDesdeHasta";
	
	String CARGA_CONTROLES_MODELO = "Carga.findCargaModelo";
	
	String CARGA_CONTROLES_MODELO_DESDE = "Carga.findCargaModeloDesde";
	
	String CARGA_CONTROLES_MODELO_DESDE_HASTA = "Carga.findCargaModeloDesdeHasta";
	
	

	/**
	 * Obtiene las casillas del modelo y version dados con numero de casilla
	 * distinto al dado.
	 */
	String CASILLAS_MODELO_VERSION_LIGAR = "CasillaModeloDTO.findCasillasALigar";

	/**
	 * Obtiene las casillas del modelo que no está asociada a ninguna Casilla
	 * Municipio para el modelo y version dados.
	 */
	String CASILLAS_MODELO_NOT_IN_CASILLAS_MUNICIPIO = "CasillaModeloDTO.findNotInCasillaMunicipioByModeloVersion";

	
	String CASILLAS_LIGAR_COUNT = "CasillaModeloDTO.findCasillasALigarNumResultados";

	/**
	 * Devuelve las Casillas Modelos con BO_REPETICION.
	 */
	String CASILLA_MODELO_BO_REPETICION = "CasillaModeloDTO.findCasillasModelosBoRepeticion";

	/**
	 * Devuelve una lista que indica si la casilla dada esta dada de alta.
	 */
	String COMPROBAR_EXISTE_CASILLA = "CasillaModeloDTO.findCasillaExisteModeloVersion";

	/**
	 * Devuelve los municipios/conceptos/modelo/versión de las casillas
	 * municipio.
	 */
	String MUNICIPIO_CONCEPTO_MODELO_VERSION = "CasillaMunicipio.findMunicipioConceptoModeloVersion";

	String MODELO_BO = "Modelo.findModelos";

	/**
	 * Devuelve los registros con Hoja1.
	 */
	String HOJA1 = "TipoRegistros.findBoHoja1";

	/**
	 * Devuelve los registros con HojaN.
	 */
	String HOJAN = "TipoRegistros.findBoHojaN";

	/**
	 * Devuelve los criterios de inclusión/exclusión asociados.
	 */
	String CRITERIOS_INC_EXC = "TipoRegistros.findCriterionIncExc";

	/**
	 * Devuelve las posiciones de casillas relacionadas.
	 */
	String POSICIONES_CASILLAS = "TipoRegistros.findPosicionesCasillas";
	
	/**
	 * Devuelve las posiciones de casillas relacionadas.
	 */
	String POSICIONES_CASILLAS_REG = "TipoRegistros.findPosicionesCasillasReg";

	/**
	 * Filtra las casillas municipio por la constraint unique establecida para:
	 * provincia, municipio, concepto, modelo, version y casilla.
	 */
	String CASILLA_MUNICIPIO_BY_UNIQUE_KEY = "CasillaMunicipio.findByUniqueKey";

	/**
	 * Filtra las casillas municipio por los campos informados de: modelo, version.
	 */
	String CASILLA_MUNICIPIO_BY_MOVER = "CasillaMunicipio.findCasillasMunicipioByModeloVersion";
	
	/**
	 * Filtra las casillas municipio por los campos informados de: modelo, version.
	 */
	String CASILLA_MUNICIPIO_BY_MOVERGEN = "CasillaMunicipio.findCasillasMunicipioByModeloVersionGenerica";
	
	/**
	 * Filtra las casillas relacionadas según el filtro establecido.
	 */
	String CASILLAS_RELACIONADAS_FILTRO = "CasillasRelacionadas.findCasillasRelacionadasFiltro";
	
	/**
	 * Filtra las casillas municipio por los campos informados de: modelo, version, municipio.
	 */
	String CASILLA_MUNICIPIO_BY_MOVERMUN = "CasillaMunicipio.findCasillasMunicipioByModeloVersionMunicipio";
	
	/**
	 * Filtra las casillas municipio por los campos informados de: modelo, version,concepto, municipio.
	 */
	String CASILLA_MUNICIPIO_BY_MOVERCONMUN = "CasillaMunicipio.findCasillasMunicipioByModeloVersionConceptoMunicipio";
	
	/**
	 * Filtra las casillas municipio por los campos informados de: municipiomodelo, version, municipio.
	 */
	String CASILLA_MUNICIPIO_BY_MOVERCON = "CasillaMunicipio.findCasillasMunicipioByModeloVersionConcepto";


	/**
	 * Filtra las condiciones de criterios de carga, devolviendo las de un
	 * criterio dado que no tengan conector.
	 */
	String CONDICIONES_SIN_CONECTOR = "CargaCriterioCondicion.findCondicionesSinConector";
	/**
	 * Filtra las condiciones de criterios de carga, devolviendo las de un
	 * criterio dado que no tengan conector.
	 */
	String CONDICIONES_SIN_CONECTOR_CON_CRITERIO = "CargaCriterioCondicion.findCondicionesSinConectorConFiltroCriterio";

	/**
	 * Filtra las condiciones de criterios de extraccion, devolviendo las de un
	 * criterio dado que no tengan conector.
	 */
	String CONDICIONES_SIN_CONECTOR_EXTRAC = "ExtraccionCriterioCondicion.findCondicionesSinConector";
	
	/**
	 * Filtra las condiciones de criterios de extraccion, devolviendo las de un
	 * criterio dado que no tengan conector.
	 */
	String CONDICIONES_SIN_CONECTOR_EXTRAC_CRITERIO = "ExtraccionCriterioCondicion.findCondicionesSinConectorCriterio";

	/**
	 * Devuelve los criterios asociados a una Estructura de Entrada
	 */
	String CRITERIOS_BY_CARGA = "CargaCriterios.findCriteriosById";

	/**
	 * Devuelve las estructuras de campos para el filtro establecido.
	 */
	String FIND_EXTRACION_CAMPO = "ExtraccionEstructuraCampo.findExtraccionEstructuraCampo";

	/**
	 * Devuelve los documentos con error asociado a la casilla 10 mediante el
	 * municipio y concepto .
	 */
	String FIND_DOCUMENTOS_MUNICIPIO_CONCEPTO = "Documento.findDocumentosByMunicipioConcepto";

	/**
	 * Devuelve los documentos con error asociado a la casilla 10 mediante el
	 * modelo, version y el numero de Documento
	 */
	String FIND_DOCUMENTOS_MODELO_VERSION_NUMDOCUMENTO = "Documento.findDocumentosByModeloVersionNumDocumento";

	String FIND_DOCUMENTOS_MODELO_VERSION_NUMDOCUMENTO_FILTRO = "Documento.findDocumentosByModeloVersionNumDocumentoFiltro";

	String FIND_DOCUMENTOS_CARGAR_CONTROL_RECEPTION= "Documento.findDocumentosByCargaControlRecepcion";

	/**
	 * Devuelve los ordenes asociados a un tipo de registro dado.
	 */
	String FIND_ORDENES_POR_TIPO = "DefInformes.findOrdenesPorTipo";

	/**
	 * Devuelve los valores asociados a un orden de estructura dado.
	 */
	String FIND_VALORES_POR_ORDEN = "DefInformes.findValoresPorOrden";

	/**
	 * Devuelve las estadísticas de los documentos asociados a un municipio.
	 */
	String DOCUMENTOS_OBTENER_ESTADISTICAS = "Documentos.obtenerEstadisticas";

	/**
	 * Devuelve el listado de documentos por estado para mantenimiento de
	 * documentos de entrada.
	 */
	String DOCUMENTOS_OBTENER_LISTADO_COMPLEJO_POR_ESTADO = "Documentos.obtenerListadoDocumentosComplejoPorEstado";

	String DOCUMENTOS_OBTENER_LISTADO_COMPLEJO_POR_ESTADO_SIN_RESTRICCION = "Documentos.obtenerListadoDocumentosComplejoPorEstadoSinRestriccion";

	String DOCUMENTOS_OBTENER_LISTADO_COMPLEJO_POR_MCCV = "Documentos.obtenerListadoDocumentosComplejoPorMCCV";
	
	String DOCUMENTOS_OBTENER_LISTADO_COMPLEJO_POR_MCCV_ESTADO = "Documentos.obtenerListadoDocumentosComplejoPorMCCVConEstado";

	String DOCUMENTOS_OBTENER_LISTADO_COMPLEJO_POR_DOCUMENTO = "Documentos.obtenerListadoDocumentosComplejoPorDocumento";

	/**
	 * Devuelve las estadísticas de todos los documentos.
	 */
	String DOCUMENTOS_OBTENER_TODAS_ESTADISTICAS = "Documentos.obtenerTodasEstadisticas";

	/**
	 * Filtrar las validaciones por el filtro dado y hace un fetch con
	 * casillaModelo.
	 */
	String VALIDACION_OBTENER_LISTADO = "Validacion.findValidaciones";
	
	/**
	 * Filtrar las validaciones por el filtro dado y hace un fetch con
	 * casillaModelo.
	 */
	String VALIDACION_OBTENER_LISTADO_CON_TIPO = "Validacion.findValidacionesConTipo";

	/**
	 * Atributo que almacena el CORRESPONDENCIA_FIND_BY_PARAM de la clase.
	 */
	String CORRESPONDENCIA_FIND_BY_PARAM = "Correspondencia.findCorrespondenciaByParam";

	/**
	 * Atributo que almacena el CORRESPONDENCIA_FIND_FUNCION de la clase.
	 */
	String CORRESPONDENCIA_FIND_FUNCION = "Correspondencia.findFuncionCorrespondencias";

	/**
	 * Filtrar las validaciones por el filtro dado y hace un fetch con
	 * casillaModelo.
	 */
	String VALIDACION_FUNCION_OBTENER_LISTADO = "Validacion.findValidacionesFunciones";

	/**
	 * Obtiene las funciones que contengan parametros de salida.
	 */
	String FUNCION_PARAM_SALIDA = "Funciones.obtenerFuncionesConParametrosSalida";
	/**
	 * Obtiene las funciones que contengan parametros de salida.
	 */
	String FUNCION_PARAM_SALIDA_CON_TIPO = "Funciones.obtenerFuncionesConParametrosSalidaConTipo";
	
	

	/**
	 * Obtiene la lista de casillas asociadas a un documento.
	 */
	String DOCUMENTO_CASILLAS = "DocumentosCasillas.obtenerCasillas";

	/**
	 * Devuelve las tablasGT.
	 */
	String TABLAS_GT = "TablaGT.obtenerTablaGT";

	/**
	 * Devuelve los ValidacionArgumento asociados a una validacion seleccionada.
	 */
	String ARGUMENTO_VALIDACION_COVALIDACION = "Validacion.findArgumentosValidaciones";
	
	/**
	 * Devuelve los ValidacionArgumento asociados a una validacion seleccionada.
	 */
	String ARGUMENTO_VALIDACION_BY_CASILLA = "Validacion.findArgumentosByCasillas";

	/**
	 * Devuelve los CorrespondenciaArgumento asociados a una correspondencia
	 * seleccionada.
	 */
	String ARGUMENTO_COCORRESPONDENCIA = "Correspondencia.findArgumentosCorrespondencias";
	/**
	 * Devuelve una Validacion segun el id recibido con los objetos de casillas
	 * y funcion poblados.
	 */
	String VALIDACION_WITH_CASILLAS = "Validacion.findByIdWithCasillas";

	/**
	 * Devuelve un ValidacionArgumento segun el id recibido con el objeto
	 * funcionArgumento poblado.
	 */
	String VALIDACIONARG_WITH_FUNCARG = "ValidacionArgumento.findByIdWithFuncionArgumento";

	/**
	 * Devuelve un ValidacionArgumento segun el id recibido con el objeto
	 * funcionArgumento poblado.
	 */
	String CORRESPONDENCIAARG_WITH_FUNCARG = "CorrespondenciaArgumento.findByIdWithFuncionArgumento";

	String PLANTILLAS_OBTENER_LISTADO_POR_PROCESO = "Plantillas.findPlantillasByProceso";

	String PLANTILLAS_OBTENER_LISTADO = "Plantillas.findPlantillas";

	/**
	 * Devuelve los datos de la plantilla pasandole el id seleccionado en la
	 * pantalla de plantillas.
	 */
	String PLANTILLA_OBTENER_DATOS = "Plantillas.findPlantillaByCodigo";

	String PLANTILLA_OBTENER_DATOS_POR_ID_MAYO = "Plantillas.findPlantillaByCodigoMayor";

	String PLANTILLA_FIRMA_POR_UNIDADADMINISTRATIVA = "Plantillas.findFirmaByUnidadAdministrativa";

	/**
	 * Devuelve la lista de Casillas asociadas a una Plantilla.
	 */
	String PLANTILLA_CASILLAS = "Plantillas.obtenerCasillas";

	/**
	 * Devuelve la lista de etiqueta campo.
	 */
	String PLANTILLA_ETIQ_CAMPO = "Plantillas.findPlantillaEtiquetaCampo";
	
	/**
	 * Devuelve la lista de etiqueta campo por codigo de etiqueta y de
	 * plantilla.
	 */
	String PLANTILLA_ETIQ_CAMPO_COD_PLANTILLA_COD_ETIQUETA = "Plantillas.findByCoPlantillaAndCoEtiqueta";
	
	/**
	 * Devuelve la lista de etiqueta campo.
	 */
	String PLANTILLA_ETIQUETAS_TABLAS = "Plantillas.findPlantillaEtiquetaTabla";
	
	/**
	 * Devuelve la lista de plantillas.
	 */
	String PLANTILLA_ODT_NOMBRE = "Plantillas.findPlantillaNombre";

	/**
	 * Atributo que almacena el nombre de la query que devuelve la lista de
	 * codigos de tabla asociados a una plantilla.
	 */
	String PLANTILLA_TABLA_CODIGOS_BY_PLANTILLA = "PlantillaTabla.findCodigosByPlantilla";
	
	/**
	 * Atributo que almacena el nombre de la query que devuelve la lista de
	 * codigos de tabla asociados a una plantilla.
	 */
	String PLANTILLA_IMAGEN_FIND_BY_ID_LAZY = "PlantillaImagen.findByIdLazy";

	/**
	 * Devuelve la lista de casillas que se pueden ligar a una dada.
	 */
	String CASILLA_MODELO_CASILLAS_A_LIGAR = "CasillaModelo.findCasillasALigar";

	/**
	 * Atributo que almacena el FIND_PLANTILLA_CAMPO de la clase.
	 */
	String FIND_PLANTILLA_CAMPO = "PlantillaEtiquetaCampo.findPlantillaEtiquetaCampo";

	/**
	 * Devuelve los datos de la unidad urbana pasandole el id devuelto por la
	 * funcion pl FU_GA_BUSQUEDA_CANDIDATOS.
	 */
	String UNIDAD_URBANA_OBTENER_DATOS = "UnidadUrbana.findUnidadUrbanaByCodigo";

	
	String UNIDAD_URBANA_BUSCA_IDENTICA = "UUrbana.buscaUnidadIdentica";
	
	String DOCUMENTOS_ESPECIFICOS_UNIDAD_URBANA = "UnidadUrbana.documentosEspecificos";
	String DOCUMENTOS_UNIDAD_URBANA_SITUACION_DISTINTA = "UnidadUrbana.documentosSituacionDistinta";

	
	/**
	 * Devuelve la lista de casillas Municipios.
	 */
	String CASILLAS_MUNICIPIO_DOCUMENTO = "CasillaMunicipio.obtenerCasillasDocumentos";

	/**
	 * Devuelve la lista de casillas Municipios Operacion filtrado por el codigo de la Funcion.
	 */
	String CASILLA_MUNICIPIO_OPERACION_BY_COFUNCION = "CasillaMunicipioOperacion.findByCoCasillaMunicipioAndCoOperacion";
	
	/**
	 * Obtiene la lista de municipios con callejero.
	 */
	String FIND_MUNICIPIOS_CALLEJERO = "Municipio.findMunicipioCallejero";
	
	String FIND_DOCUMENTOS_MUNICIPIO_CONCEPTO_MODELO_VERSION = "Documento.findDocumentosByMunicipioConceptoModeloVersion";
	
	String FIND_DOCUMENTOS_MUNICIPIO_CONCEPTO_MODELO_VERSION_ESTADO = "Documento.findDocumentosByMunicipioConceptoModeloVersionEstado";
	
	/**
	 * Almacena los nombres de query asociados a CargaDTO.
	 */
	public interface QueryCarga {

		/**
		 * Busca la CargaDTO segun su id y hace fetch con municipio, concepto y
		 * modelo-version.
		 */
		String FIND_BY_ID = "CargaDTO.findById";
		
		/**
		 * Busca la CargaDTO segun el id de municipio cuando solo existe un registro para ese municipio en GA_CARGA
		 * y hace fetch con municipio, concepto y modelo-version.
		 */
		String FIND_BY_MUNICIPIO_ID = "CargaDTO.findByMunicipioId";
		
		/**
		 * Busca la CargaDTO segun egún el número de registros en GA_CARGA ,
	 	 * se obtiene Municipio, concepto y modelo-version si solo hay un registro para ese municipio.
		 */
		String FIND_NUMERO_ESTRUCTURAS = "CargaDTO.numeroEstructuras";

		/**
		 * Atributo que almacena el ESTRUCTURA_FIND_BY_PARAM de la clase.
		 */
		String ESTRUCTURA_FIND_BY_PARAM = "CargaDTO.findEstructuraByParam";
		
		/**
		 * Atributo que almacena el MUNICIPIOS_FIND_BY_PARAM de la clase.
		 */
		String MUNICIPIOS_FIND_BY_PARAM = "CargaDTO.findMunicipiosByParam";
		
		/**
		 * Devuelve LA LISTA MUNICIPIOPS POR CALLE
		 */
		String MUNICIPIO_BUSQUEDABYCALLE = "municipio.busquedaMunicipioVOByCalle";
	
	}

	/**
	 * Almacen los nombres de query asociados a ExtraccionDTO.
	 */
	public interface QueryExtraccion {

		/**
		 * Busca la ExtraccionDTO segun su id y hace fetch con municipio, modelo
		 * y version.
		 */
		String EXT_BY_ID = "ExtraccionDTO.findByIdExtraccion";

		/**
		 * Obtiene la longitud de los campos de una estructura que sean
		 * distintos al dado.
		 */
		String EXT_EST_CAMPO_LONGITUD = "ExtraccionEstructuraCampoDTO.longitudCampos";

		/**
		 * Devuelve las estructuras de Salida para el filtro establecido.
		 */
		String FIND_ESTRUCTURAS_SALIDA = "ExtraccionDTO.findEstructurasSalidaByParam";

		/**
		 * Atributo que almacena el FIND_CONDICIONES_DETALLE de la clase.
		 */
		String FIND_CONDICIONES_DETALLE = "ExtraccionCriterioCondicion.findCondicionesDetalle";

		/**
		 * Devuelve las casillas destino de la estructura de salida con modelo
		 * de salida.
		 */
		String FIND_CASMOD_DESTINO = "CasillaModeloDTO.finCasillasDestino";

		/**
		 * Devuelve el campo con ExtraccionEstructuraDTO rellenado.
		 */
		String FIND_CAMPO_LAZY = "ExtraccionEstructuraCampoDTO.findByIdLazy";

	}

	/**
	 * Devuelve los Modelos existentes en GA_DOCUMENTOS para un estado.
	 */
	String DOCUMENTOS_OBTENER_TODOS_LOS_MODELOS = "ModeloDTO.findAllModelos";

	/**
	 * Devuelve las versiones existentes para un Modelo seleccionado de
	 * GA_DOCUMENTOS.
	 */
	String DOCUMENTOS_OBTENER_VERSIONES_DEL_MODELO = "ModeloVersionDTO.findAllVersionByModelo";

	/**
	 * Devuelve las correspondencias activas existentes para un Documento.
	 */
	String CORRESPONDENCIAS_ACTIVAS_CO_DOCUMENTO = "CorrespondenciaDTO.findBoActivaCoDocumento";
	
	/**
	 * Devuelve todos los modelos ordenados por coModelo
	 */
	String MODELOS_ORDENADOS = "Modelo.findAllOrdenados";
	
	/**
	 * Querys para el subsistema 7
	 */
	
	/**
	 * Devuelve UNA LISTA DE CLIENTES
	 */
	String CLIENTE_BUSCACLIENTEVO = "Cliente.buscaClienteVO";
	
	/**
	 * Devuelve un domicilio dado su id.
	 */
	String DOMICILIO_FINDBYIDFETCH = "Domicilio.findByIdFetch";
	
	/**
	 * Devuelve un domicilio dado su id.
	 */
	String DOMICILIONOTIFICACION_FINDBYIDFETCH = "DomicilioNotificacion.findByIdFetch";
	
	/**
	 * Devuelve un domicilio notificacion por cliente y domicilio.
	 */
	String DOMICILIONOTIFICACION_FINDBYCLIENTEANDDOMICILIO = "DomicilioNotificacion.findByClienteAndDomicilio";
	
	/**
	 * Devuelve una calle dado su id.
	 */
	String CALLE_FINDBYIDFETCH = "Calle.findByIdFetch";
	
	/**
	 * Devuelve la lista de clientes con nif ficticio filtrados por la razón
	 * social recibida.
	 */
	String CLIENTE_BUSCAFICTICIOSBYRAZONSOCIAL = "Cliente.busquedaNifFicticiosByRazonSocial";
	
	String CLIENTE_BUSCACLIENTEVO_POR_ID = "Cliente.buscaClienteVOPorId";
	
	String CLIENTE_TIENE_DOC_CENSOS_ACTIVOS = "Cliente.tieneDocumentosCensoActivos";
	
	String CLIENTE_TIENE_DOC_DISTINTOS_B = "Cliente.tieneDocumentosDistintosB";
	/**
	 * Devuelve LA LISTA DE CLIENTES POR CALLE Y KM PARA LA SELECCION DE CLIENTES
	 */
	String CLIENTE_BUSCASELECCIONCLIENTEVOBYCALLEANDKM = "Cliente.busquedaSeleccionClienteVOByCalleAndKm";
	
	/**
	 * Devuelve LA LISTA DEl HISTORICO PARA UNA UNIDAD URBANA DADA
	 */
	String H_UNIDAD_URBANA_OBTENER_HISTORIAL = "HUUrbana.busquedaHistoricoBycodigo";
	/**
	 * Devuelve LA LISTA DEl HISTORICO PARA UNA UNIDAD URBANA A partir de una fecha
	 */
	String H_UNIDAD_URBANA_OBTENER_HISTORIAL_FFECHA = "HUUrbana.busquedaHistoricoBycodigoYfecha";
	
	/**
	 * Devuelve La prvincia a partir de un municipio
	 */
	String PROVINCIA_BY_MUNICIPIO = "provincia.busquedaByMunicipio";
	
	/**
	 * Devuelve La ubicacion a partir de una calle
	 */
	String UBICACION_BY_CALLE = "ubicacion.busquedaByCalle";
	
	/**
	 * Devuelve LA LISTA DEl HISTORICO PARA UNA UNIDAD URBANA A partir de una fecha
	 */
	String H_UNIDAD_URBANA_OBTENER_HISTORIAL_VIA = "HGUUrbana.busquedaHistoricoBycodigoYVia";
	
	/**
	 * Devuelve LA LISTA DEl HISTORICO PARA UNA UNIDAD URBANA A partir de una fecha
	 */
	String H_UNIDAD_URBANA_OBTENER_HISTORIAL_FFECHA_VIA = "HGUUrbana.busquedaHistoricoBycodigoFechaYVia";
	
	String UNIDAD_URBANA_TIENE_DOMICILIOS = "UUrbana.tieneDomicilos";
	
	String UNIDAD_URBANA_TIENE_ASOCIADA = "UUrbana.tieneAsociada";
	
	String UNIDAD_URBANA_ES_UNIDAD_ADMINISTRATIVA = "UUrbana.esUnidadAdmistrativa";
	

	
	/**
	 * Devuelve los ejercicios para el municipio seleccionado.
	 */
	String MODELOS_BY__MUNICIPIO = "Modelo.findModelosByMunicipio";		
	
	/**
	 * Devuelve los ejercicios para el municipio y modelo seleccionado.
	 */
	String FIND_EJERCICIO_BY_MUNICIPIO_MODELO = "Cargo.findEjercicioByMunicipioModelo";
	
	/**
	 * Obtiene los modelos a partir de un municipio y documento dado.
	 */
	String FIND_PERIODO_BY_EJERCICIO_MUNICIPIO_MODELO = "Cargo.findPeriodoByMunicipioModeloEjercicio";	

	String CARGO_OBTENER = "Cargo.busquedaCargoBycodigo";
	
	/**
	 * Obtiene el campo "Subtipo" del modelo relacionado con un subcargo.
	 */
	String FIND_SUBTIPO = "Modelo.findSubtipo";	
	
	/**
	 * Devuelve La Unidad Administrativa a partir de un cargo
	 */
	String H_CARGO_OBTENER_UADM = "HCargo.busquedaUAdminByCargo";
	
	/**
	 * Devuelve LA LISTA DEl HISTORICO PARA UNA UNIDAD URBANA 
	 */
	String H_HCARGO_OBTENER = "HCargo.busquedaHistorialCargoBycodigo";
	
	/**
	 * Devuelve LA LISTA DEl HISTORICO PARA UNA UNIDAD URBANA A partir de una fecha
	 */
	String H_HCARGO_OBTENERBYFECHA = "HCargo.busquedaHistorialCargoBycodigoYFecha";
	
	/**
	 * Devuelve el subCargo a partir del id
	 */
	String SUBCARGO_OBTENER = "SubCargo.obtenerPorId";
	
	/**
	 * Devuelve el número de subcargos asociados a un cargo
	 */
	String FIND_NUMERO_SUBCARGOS = "SubCargo.numeroSubcargos";
	
	/**
	 * Devuelve la lista de cargos anteriores
	 */
	String OBTENER_CARGOS_ANTERIORES = "Cargo.obtenerCargosAnosAnteriores";	

	String ENV_GEN_OBTENER_ESTRUCTURA = "EnvRecGenFich.obtenerEstructuras";

	String CARGO_PARAMETROS = "SeleccionCargo.busquedaParametros";
	
	String CARGO_ULTIMO_HISTORICO= "SeleccionCargo.historico.ultimo";
	String SUB_CARGO_ULTIMO_HISTORICO= "SeleccionSubCargo.historico.ultimo";
	
	/**
	 * Obtiene los documentos para las listas Cobratorias.
	 */
	String FIND_DOCUMENTOS_LISTAS_COBRATORIAS = "ListasCobratorias.findDocumentos";
	
	/**
	 * Obtiene los documentos para las listas Cobratorias.
	 */
	String FIND_CONCEPTOS_LISTAS_COBRATORIAS = "ListaCobratorias.findConceptos";
	
	String FIND_CASILLAS_BY_MUN_CON_MOD_VER = "CasillaMunicipio.findCasillasMunicipioByModeloVersionConceptoMunicipioProvincia";
	
	/**
	 * Devuelve el número de documentos catastrales a partir de unos valores
	 */
	String FIND_DOCUMENTO_CATASTRAL = "Documento.findDocumentoCatastral";
	String FIND_NOTIFIACION_BY_FECH = "NotificacionDgc.findBycoNotificacionDgc";
	
	String DOCUMENTOS_CARGOS = "Cargos.findDocumentosAsociados";
	
	
	String MODELOS_DE_DOCUMENTO = "NuevaResolucionCatastral.findModelosDeDocumentos";
	
	String VERSIONES_DE_DOCUMENTO_BY_MODELO = "NuevaResolucionCatastral.findVersionesDeDocumentos";
	
	String DOCUMENTO_BY_MODELO_VERSION = "NuevaResolucionCatastral.findDocumentosPorModeloVersion";
	String DOCUMENTO_BY_REF_CATASTRAL = "NuevaResolucionCatastral.findDocumentosPorRefCatastral";
	String CONCEPTOS_BY_MODELO = "NuevaResolucionCatastral.conceptosbymodelo";
	String EXISTE_RELACION = "NuevaResolucionCatastral.existeRelacion";


	//Modos de cobro
	
	String MODOSCOBRO_BY_ESTADO_ORIGEN = "ModoCobro.findByEstadoOrigen";
	String MODOSCOBRO_BY_ESTADO_DESTINO = "ModoCobro.findByEstadoDestino";
	
	
	
}
