<%@ include file="../taglibs.jsp"%>

<script type="text/javaScript">

	$(document).ready(function(){	
		$('#boton-ejecutar-incidencia').click(function() {
		
			var coModelo = $('#coModeloIncidencia').val();
			var coVersion = $('#coVersionIncidencia').val();
			var coDocumento = $('#coDocumentoIncidencia').val();
			var coIncidencia = $('#coIncidenciaIncidencia').val();
			var coIncidenciaSituacion = $('#coIncidenciaSituacionIncidencia').val();
			 
			pulsaOpcionEspecial(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
		});
		
		$('#boton-cerrar-listaOpciones').click(function() {
			$('#listaOpcionesDiv').toggle(false);
			$('.backgroundLemonChiffon').removeClass("backgroundLemonChiffon");
		});
		
		$('#boton-cerrar-incidencia').click(function() {
			$('#ejecutarIncidenciaDiv').toggle(false);
		});
		
	});

	function pulsaOpcion(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia){
 		if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BAJA%>' || coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BAJA_RECAUDACION%>' || coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_ANULACION_BAJA%>'){
	 		activaMotivo();
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BAJA%>' || coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BAJA_RECAUDACION%>')				
				$('#incidenciaSel').text('Baja documento');
			else
				$('#incidenciaSel').text('Anulación de baja');
			if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BAJA_RECAUDACION%>') { // Solo pedir este dato si baja en recaudación
				$('#economicaIncidencia').val('1');
				$('#bajaConAnulacionCobro').toggle(<%=es.dipucadiz.etir.comun.utilidades.ControlTerritorial.isUsuarioExperto()%>); 
			} else {
				$('#economicaIncidencia').val('0');
				$('#bajaConAnulacionCobro').toggle(false); 
			}
			
	 		$('#ejecutarIncidenciaDiv').toggle(true);
	 		
 		}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_ANULAR_NOTIFICACION_PROVIDENCIA_APREMIO%>'){
 			 
			activaObservaciones();
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			$('#incidenciaSel').text('Anula Notificación Providencia de Apremio');
			$('#ejecutarIncidenciaDiv').toggle(true);
			
		}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CENSO_BAJA%>'){
		 
			activaObservaciones();
			activaFechaBaja();
			//activaEconomica();
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			$('#incidenciaSel').text('Baja en el censo');
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PAGADO_ORGANISMOS%>'){

			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			
			//OBTENER LOS DATOS CON AJAX
	
			$.ajax({
		        type: "POST",
		        url: "G743LiquidacionDocumentos!obtenerImportes.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					
					var arrDatos = datos.split('|');
					$('#impPendCobroIncidencia').val(arrDatos[1]);
					$('#impPendCobro').val(arrDatos[1]);
					$('#impPrincipal').val(arrDatos[0]);

		      	}
			});
			
			activaImportesPago();
			
			$('#incidenciaSel').text('Pago a otros organismos');
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PAGADO_VENTANILLA%>'){
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			
			$.ajax({
		        type: "POST",
		        url: "G743LiquidacionDocumentos!obtenerImportes.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					
					var arrDatos = datos.split('|');
					$('#vntImpPendCobro').val(arrDatos[1]);
					$('#vntImpPrincipal').val(arrDatos[0]);

		      	}
			});
			
			$('#incidenciaSel').text('Pagado en ventanilla');
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth()+1;
			var yyyy = today.getFullYear();
			if(dd<10){dd='0'+dd;}
			if(mm<10){mm='0'+mm;}
			var fx = dd+'/'+mm+'/'+yyyy;
			var fx_struts = dojo.date.strftime(today,"%Y-%m-%dT%H:%M:00%z");
			$('input[name=fechaPagadoIncidencia]').val(fx_struts);
			$('input[name=dojo.fechaPagadoIncidencia]').val(fx);
			$('#fechaPagadoIncidenciaFila').toggle(true);
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PAGO_PARCIAL%>' || coIncidencia == '<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PAGO_COMPENSACION_AYTO%>'){
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			
			$.ajax({
		        type: "POST",
		        url: "G743LiquidacionDocumentos!obtenerImportes.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					
					var arrDatos = datos.split('|');
					$('#impPendCobro').val(arrDatos[1]);
					$('#impPrincipal').val(arrDatos[0]);
					$('#impPendCobroIncidencia').val(arrDatos[2]);
		      	}
			});
			
			$('#incidenciaSel').text('Pago parcial');
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth()+1;
			var yyyy = today.getFullYear();
			if(dd<10){dd='0'+dd;}
			if(mm<10){mm='0'+mm;}
			var fx = dd+'/'+mm+'/'+yyyy;
			var fx_struts = dojo.date.strftime(today,"%Y-%m-%dT%H:%M:00%z");
			$('input[name=fechaPagoParcial]').val(fx_struts);
			$('input[name=dojo.fechaPagoParcial]').val(fx);
			$('#fechaPagoFila').toggle(true);
			activaImportesPago();
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_IMP_DIPTICO_PARCIAL%>'){

			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			
			//OBTENER LOS DATOS CON AJAX
	
			$.ajax({
		        type: "POST",
		        url: "G743LiquidacionDocumentos!obtenerImportesDiptico.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					
					var arrDatos = datos.split('|');
					$('#impPendCobroDipticoIncidencia').val(arrDatos[2]);
					$('#impPendCobroDiptico').val(arrDatos[1]);
					$('#impTotal').val(arrDatos[0]);

		      	}
			});
			
			activaImportesDipticoParcial();
			
			$('#incidenciaSel').text('Impresión díptico parcial');
			$('#ejecutarIncidenciaDiv').toggle(true);
		} else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_ANULAR_PAGO%>'){
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
		
			$('#incidenciaSel').text('Anulación del cobro');
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth()+1;
			var yyyy = today.getFullYear();
			if(dd<10){dd='0'+dd;}
			if(mm<10){mm='0'+mm;}
			var fx = dd+'/'+mm+'/'+yyyy;
			var fx_struts = dojo.date.strftime(today,"%Y-%m-%dT%H:%M:00%z");
			$('input[name=fechaAnularCobroSel]').val(fx_struts);
			$('input[name=dojo.fechaAnularCobroSel]').val(fx);
			$('#fechaAnularCobroSel').toggle(true);
			activaFechaAnularCobro();
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CARGO_MODIFICAR_PERIODO_VOLUNTARIO%>'){

			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			
			//OBTENER LOS DATOS CON AJAX
	
			$.ajax({
		        type: "POST",
		        url: "G743LiquidacionDocumentos!obtenerPeriodoVoluntario.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					
					var arrDatos = datos.split('|');
					$('#wwctrl_fechaInicioVoluntariaIncidencia').html(arrDatos[0]);
					$('#wwctrl_fechaFinVoluntariaIncidencia').html(arrDatos[1]);
					$('input[name="fechaInicioVoluntariaBBDD"]').val(arrDatos[0]);
					$('input[name="fechaFinVoluntariaBBDD"]').val(arrDatos[1]);

		      	}
			});
			
			activaPeriodosVoluntarios();
			activaObservaciones();
			
			$('#incidenciaSel').text('Modificación periodo voluntario');
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_NOTIFICACION%>') {
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			//OBTENER LOS DATOS CON AJAX
			$.ajax({
		        type: "POST",
		        url: "G743DocumentoNotificaciones!obtenerDatosIncidenciaNotificacion.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					var arrDatos = datos.split('|');
					if (arrDatos[1] == 'x') {
						$('#filaFxUltimaNotificacion').hide();
					} else {
						$('#filaFxUltimaNotificacion').show();
						$('#wwctrl_fxUltimaNotificacion div').text(arrDatos[1]);
					}
					if (arrDatos[2] == 'x') {
						$('#filaUltimoResultadoNotificacion').hide();
					} else {
						$('#filaUltimoResultadoNotificacion').show();
						$('#ultimoResultadoNotificacion').text(arrDatos[2]);
					}
					if (arrDatos[3] == 'x') {
						$('#filaPeriodoVoluntarioActual').hide();
					} else {
						$('#filaPeriodoVoluntarioActual').show();
						$('#periodoVoluntarioActual').text(arrDatos[3]);
					}
					var d = new Date();
					var month = d.getMonth()+1;
					var day = d.getDate();
					var output = (day<10 ? '0' : '') + day + '/' + (month<10 ? '0' : '') + month + '/' + d.getFullYear();
					//$('input[name="dojo.fxNotificacion"]').val(output);
					var arrResultados = arrDatos[4].split('##');
					$('#resultadoNotificacion').find('option').remove();
					$.each(arrResultados, function(index, value) {
						if (value != '') {
							var arrOption = value.split('@@');
							$('#resultadoNotificacion').append('<option value="'+arrOption[0]+'">'+arrOption[1]+'</option>');
						}
					});
		      	}
			});
			
			activaNotificacion();
			$('#incidenciaSel').text('Notificación');
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CAMBIO_CARGO%>') {
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			//OBTENER LOS DATOS CON AJAX
			$.ajax({
				type: "POST",
				url: "G771Documentos!obtenerCargosParaCambio.action",
		        data: "coModeloSel=" + coModelo + "&" +
    	          "coVersionSel=" + coVersion + "&" + 
    	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
		        	var cargos = datos.split('#');
		        	$('#cambioDeCargoCombo').find('option').remove().end().append('<option value="" selected="selected">Seleccione cargo</option>');
		        	$.each(cargos, function(i, cargo) {
		        		var partes = cargo.split('|');
		        		var key;
		        		var value;
		        		if (partes[0] == 'N') {
		        			value = 'Nuevo cargo del ' + partes[1];
		        			key = '-|' + partes[1];
		        		} else if (partes[0] == 'E') {
		        			value = 'Cargo existente ' + partes[2] + '/' + partes[1];
		        			key = partes[3] + '|-';
		        			if (partes.length > 4) {
		        				value += ', subcargo ' + partes[4];
		        			}
		        		}
		        		$('#cambioDeCargoCombo').append('<option value="'+key+'">' + value + '</option>');
		        	});
		        	$('#cambioDeCargoFila').show();
		        }
			});
			
			$('#incidenciaSel').text('Cambio de cargo');
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CAMBIO_TITULARIDAD%>'){

			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			
			//OBTENER LOS DATOS CON AJAX
	
			$.ajax({
		        type: "POST",
		        url: "G743CensoDocumentos!obtenerDatosTitularidad.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					
					var arrDatos = datos.split('|');
					$('#clienteTitularId').text(arrDatos[0]);
					$('#coClienteActualTitularId').text(arrDatos[1]);
					$('#domicilioFiscalTitularId').text(arrDatos[2]);
					$('#domicilioTributarioId').text(arrDatos[3]);
					$('#refCatastralId').text(arrDatos[4]);
					$('#refObjTribId').text(arrDatos[5]);
					$('#conceptoId').text(arrDatos[6]);
					$('#ejercicioId').text(arrDatos[7]);							
					$('#periodoId').text(arrDatos[8]);
					$('#municipioId').text(arrDatos[9]);
					$('#refDomiciliacionId').text(arrDatos[10]);
		      	}
			});

			$('#coDocumentoOrigenId').text(coModelo+' '+coVersion+' '+coDocumento);
			
			activaTitularidad();
			
			$('#incidenciaSel').text('Cambio de titularidad');
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PASE_EJECUTIVA%>'){
 
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);

			$.ajax({
		        type: "POST",
		        url: "G743CensoDocumentos!obtenerDatosTitularidad.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					
					var arrDatos = datos.split('|');
					$('#clienteTitularId').text(arrDatos[0]);
					$('#coClienteActualTitularId').text(arrDatos[1]);
					$('#domicilioFiscalTitularId').text(arrDatos[2]);
					$('#domicilioTributarioId').text(arrDatos[3]);
					$('#refCatastralId').text(arrDatos[4]);
					$('#refObjTribId').text(arrDatos[5]);
					$('#conceptoId').text(arrDatos[6]);
					$('#ejercicioId').text(arrDatos[7]);							
					$('#periodoId').text(arrDatos[8]);
					$('#municipioId').text(arrDatos[9]);
					$('#refDomiciliacionId').text(arrDatos[10]);
		      	}
			});

			$('#coDocumentoOrigenId').text(coModelo+' '+coVersion+' '+coDocumento);
			
			activaPaseEjecutiva();
			$('#incidenciaSel').text('Pase individual a ejecutiva');
	 		$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_GENERAR_RECIBO%>'){
			
			//MARTECHER
	 
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);

			$.ajax({
		        type: "POST",
		        url: "G743CensoDocumentos!obtenerDatosTitularidad.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
	  		        success: function(datos){
	  		        	 
						var arrDatos = datos.split('|');
						$('#clienteTitularId2').text(arrDatos[0]);						 
						$('#coClienteActualTitularId2').text(arrDatos[1]);						 
						$('#domicilioFiscalTitularId2').text(arrDatos[2]);
						$('#conceptoId2').text(arrDatos[6]);
						$('#municipioId2').text(arrDatos[9]);
						$('#codDocumento').text(coModelo+' '+coVersion+' '+coDocumento);
						
						$('#coModeloSel').val(coModelo);
						$('#coVersionSel').val(coVersion);
						$('#coDocumentoSel').val(coDocumento);
						
	 			      	}
			});
			
			$('#coDocumentoOrigenId').text(coModelo+' '+coVersion+' '+coDocumento);
				
			activaGenerarRecibo(); 
			$('#incidenciaSel').text('Generar recibo');
	 		$('#ejecutarIncidenciaDiv').toggle(true);
		} else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_ENVIAR%>' || coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_PUBLICAR%>' || coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_NOTIFICAR%>') {
			guardaDatos(coModelo, coVersion, coDocumento, coIncidencia, coIncidencia);
			
			var titulo;
			if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_ENVIAR%>') {
				titulo = 'Envío BOP';
				activaTextoEnvioBOP();
			} else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_PUBLICAR%>') {
				activaIncidenciaFechaBOP();
				activaIncidenciaBOP();
				titulo = 'Fecha de publicación BOP';
			} else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_NOTIFICAR%>') {
				titulo = 'Fecha de notificación BOP';
			} else {
				titulo = 'Fecha de <%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_ENVIAR%>';
			}
			$('#incidenciaSel').text(titulo);
	 		$('#ejecutarIncidenciaDiv').toggle(true);
		} else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BAJA_RECAUDACION%>' && coModelo=='COS') {
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			$.ajax({
		        type: "POST",
		        url: "G728Detalle!ajaxObtenerDatosCostas.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					var arrDatos = datos.split('#|#');
					$('#costasDesc').text(arrDatos[0]);
					$('#costasImp').text(arrDatos[1]);
					$('#costasFx').text(arrDatos[2]);
		      	}
			});
			
			activaBajaCostas();
			$('#incidenciaSel').text('CONFIRMAR BAJA DE COSTAS');
	 		$('#ejecutarIncidenciaDiv').toggle(true);
		} else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_DIPTICO_DATOS_PROTEGIDOS_POR_EMAIL%>'){

			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
		
			$.ajax({
		        type: "POST",
		        url: "G743LiquidacionDocumentos!obtenerImportesDipticoEmail.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					
					var arrDatos = datos.split('|');
					$('#emailSel').val(arrDatos[0]);
		      	}
			});
			
			activaEmailDipticoProtegido();
			
			$('#incidenciaSel').text('Díptico protegido por e-mail');
			$('#ejecutarIncidenciaDiv').toggle(true);
		} else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CUADERNO_REENVIAR%>' && <%=es.dipucadiz.etir.comun.utilidades.ControlTerritorial.isUsuarioExperto()%>){

			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
		
			$.ajax({
		        type: "POST",
		        url: "G7E2Seleccion!obtenerFechaCargo.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					var date = new Date(datos);
					
					var dd = date.getDate();
					var mm = date.getMonth()+1;
					var yyyy = date.getFullYear();
					
					if(dd<10){dd='0'+dd;}
					if(mm<10){mm='0'+mm;}
					var fx = dd+'/'+mm+'/'+yyyy;
					
					var fx_struts = dojo.date.strftime(date,"%Y-%m-%dT%H:%M:00%z");
					
					$('input[name=fxCargoSel]').val(fx_struts);
					$('input[name=dojo.fxCargoSel]').val(fx);		
		      	}
			});						
			$('#fxCargoSel').toggle(true);
			
			activaFxCargo();
			
			$('#incidenciaSel').text('Reenviar Cargo');
			$('#ejecutarIncidenciaDiv').toggle(true);
		} else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_DESASIGNAR%>' && coModelo=='COS') {
			guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia);
			$.ajax({
		        type: "POST",
		        url: "G728Detalle!ajaxObtenerDatosAsignacion.action",
		        data: "coModeloSel=" + coModelo + "&" +
	      	          "coVersionSel=" + coVersion + "&" + 
	      	          "coDocumentoSel=" + coDocumento,
		        success: function(datos){
					var arrDatos = datos.split('#|#');
					$('#costasDoc').text(arrDatos[0]);
					$('#costasMun').text(arrDatos[1]);
					$('#costasCon').text(arrDatos[2]);
					$('#costasSit').text(arrDatos[3]);
					$('#costasPri').text(arrDatos[4]);
					$('#costasRec').text(arrDatos[5]);
					$('#costasInt').text(arrDatos[6]);
					$('#costasCos').text(arrDatos[7]);
					$('#costasTot').text(arrDatos[8]);
		      	}
			});
			
			activaDesasignacionCostas();
			$('#incidenciaSel').text('CONFIRMAR DESASIGNACIÓN DE COSTAS');
	 		$('#ejecutarIncidenciaDiv').toggle(true);
		
		}else{
			$('#coModeloSelId').val(coModelo);
			$('#coVersionSelId').val(coVersion);
			$('#coDocumentoSelId').val(coDocumento);
			$('#opcionId').val(coIncidenciaSituacion);
			//alert('voy ' + coModelo + coVersion + coDocumento + opcion);
			$('#submitBotonOpcion').click();
		}
	}

	function guardaDatos(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia){
		$('#coModeloIncidencia').val(coModelo);
		$('#coVersionIncidencia').val(coVersion);
		$('#coDocumentoIncidencia').val(coDocumento);
		$('#coIncidenciaSituacionIncidencia').val(coIncidenciaSituacion);
		$('#coIncidenciaIncidencia').val(coIncidencia);
	}

	function activaMotivo(){
		$('#motivoIncidenciaFila').toggle(true);
	}
	
	function activaObservaciones(){
		$('#observacionesIncidenciaFila').toggle(true);
	}
	
	function activaFechaBaja(){
		$('#fechaBajaIncidenciaFila').toggle(true);
	}

	function activaImportesPago(){
		$('#importesPagoIncidenciaFila').toggle(true);
	}

	function activaImportesDipticoParcial(){
		$('#importesDipticoParcialIncidenciaFila').toggle(true);
	}

	function activaPeriodosVoluntarios(){
		$('#fechasVoluntariaIncidenciaFila').toggle(true);
	}

	function activaNotificacion() {
		$('#notificacionFila').toggle(true);
	}
	
	function activaTitularidad(){
		$('#cambioTitularidadFila').toggle(true);
	}
	
	function activaPaseEjecutiva(){
		$('#paseEjecutivaFila').toggle(true);
	}

	function activaEmailDipticoProtegido(){
		$('#emailDipticoProtegido').toggle(true);
	}
	
	function activaFxCargo(){
		$('#fxCargo').toggle(true);
	}
	
	function activaFechaAnularCobro(){
		$('#fechaAnularCobro').toggle(true);
	}
	
	function activaGenerarRecibo(){
		$('#generarReciboFila').toggle(true);
	}
	function activaIncidenciaBOP() {
		$('#incidenciaBOP').toggle(true);
	}
	function activaTextoEnvioBOP() {
		$('#filaTextoEnvioBOP').toggle(true);
	}
	function activaIncidenciaFechaBOP() {
		$('#filaFxBOP').toggle(true);
	}
	function activaBajaCostas() {
		$('#bajaCostasFila').toggle(true);
	}
//
	function activaDesasignacionCostas() {
		$('#desasignacionCostasFila').toggle(true);
	}
	function ocultarDivs(){
		$('#motivoIncidenciaFila').toggle(false);
		$('#observacionesIncidenciaFila').toggle(false);
		$('#fechaBajaIncidenciaFila').toggle(false);
		$('#importesPagoIncidenciaFila').toggle(false);
		$('#fechaPagadoIncidenciaFila').toggle(false);
		$('#fechaPagoFila').toggle(false);
		$('#importesDipticoParcialIncidenciaFila').toggle(false);
		$('#fechasVoluntariaIncidenciaFila').toggle(false);
		$('#notificacionFila').toggle(false);
		$('#cambioDeCargoFila').toggle(false);
		$('#cambioTitularidadFila').toggle(false);
		$('#paseEjecutivaFila').toggle(false);
		$('#generarReciboFila').toggle(false);
		$('#incidenciaBOP').toggle(false);
		$('#filaFxBOP').toggle(false);
		$('#filaTextoEnvioBOP').toggle(false);
		$('#bajaCostasFila').toggle(false);
		$('#emailDipticoProtegido').toggle(false);		
		$('#desasignacionCostasFila').toggle(false);
		$('#fechaAnularCobro').toggle(false);
		$('#fxCargo').toggle(false);
	}
	

	function pulsaOpcionEspecial(coModelo, coVersion, coDocumento, coIncidenciaSituacion, coIncidencia){

		var error=0;
		
		var motivo = $('#motivoIncidencia').val();
		//var motivo = $('input[name="motivoIncidencia"]').val();
		var motivoTexto = $('input[name="motivoTextoIncidencia"]').val();
		var observaciones = $('#observacionesIncidencia').val();
		//var fechaBaja = $('#fechaBajaIncidencia').val();
		var fechaBaja = $('input[name="fechaBajaIncidencia"]').val();
		var economica =$('#economicaIncidencia').val();
		//var economica = $('input[name="coEconomicaIncidencia"]').val();
		var fechaPagado = $('input[name=fechaPagadoIncidencia]').val();
		if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PAGO_PARCIAL%>' || coIncidencia == '<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PAGO_COMPENSACION_AYTO%>'){
			fechaPagado = $('input[name=fechaPagoParcial]').val();
		}

		if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BAJA%>' || coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_ANULACION_BAJA%>' || coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BAJA_RECAUDACION%>'){
			if (motivo==undefined || motivo==null || motivo==''){
				alert('Debe introducir el motivo');
				error=1;
			}else{
				observaciones=motivo + ' | ' + motivoTexto;
				if (observaciones.length > 175) {
					alert('Los textos no puede superar 175 caracteres');
					error=1;
				}
			}
		}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_ANULAR_NOTIFICACION_PROVIDENCIA_APREMIO%>' ){
				if (observaciones==undefined || observaciones==null || observaciones==''){
					alert('Debe introducir las observaciones');
					error=1;
				}else{
					if (observaciones.length > 175) {
						alert('Los textos no puede superar 175 caracteres');
						error=1;
					}
				}
			
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PAGADO_ORGANISMOS%>'){

			var impPago = $('#importePago').val();
			var importeFormateado = ''+ Math.round(impPago.replace(/\./g, '').replace(",", ".")*100)/100;
			
			var impPendCobroIncidencia = $('#impPendCobroIncidencia').val();
			var impPendCobroFormateado = ''+ Math.round(impPendCobroIncidencia.replace(/\./g, '').replace(",", ".")*100)/100;
			
			
			if (impPago==undefined || impPago==null || impPago=='' || impPago=='0' || impPago=='0,00'){
				alert('Debe introducir el importe del pago a realizar');
				error=1;
			}	
			else if(parseFloat(importeFormateado) > parseFloat(impPendCobroFormateado)){
				//Comprobar que es <= que el importe pendiente de cobro
				alert('Debe introducir un importe del pago' + importeFormateado + 'menor o igual al importe pendiente de cobro' + impPendCobroFormateado + '.');
				error=1;
			}else{
				observaciones=impPago; //Empleamos el campo de observaciones ya que recibe el pago por el 2º parámetro
			}
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PAGADO_VENTANILLA%>'){
			var impPendCobro = $('#vntImpPendCobro').val();
			if (fechaPagado==undefined || fechaPagado==null || fechaPagado==''){
				alert('Debe introducir la fecha de pago.');
				error=1;
			} else {
				observaciones=impPendCobro; //Empleamos el campo de observaciones ya que recibe el pago por el 2º parámetro
			}
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PAGO_PARCIAL%>' || coIncidencia == '<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PAGO_COMPENSACION_AYTO%>'){
			var impPago = $('#importePago').val();
			var importeFormateado = ''+ Math.round(impPago.replace(/\./g, '').replace(",", ".")*100)/100;
			var impPendCobroIncidencia = $('#impPendCobroIncidencia').val();
			var impPendCobroFormateado = ''+ Math.round(impPendCobroIncidencia.replace(",", ".")*100)/100;

			if (impPago==undefined || impPago==null || impPago=='' || impPago=='0' || impPago=='0,00'){
				alert('Debe introducir el importe del pago a realizar');
				error=1;
			}
			else if(parseFloat(importeFormateado) > parseFloat(impPendCobroFormateado)){
				//Comprobar que es <= que el importe pendiente de cobro
				alert('Debe introducir un importe del pago menor o igual al importe pendiente de cobro');
				error=1;
			}else if (fechaPagado==undefined || fechaPagado==null || fechaPagado==''){
				alert('Debe introducir la fecha de pago.');
				error=1;
			} else {
				observaciones=impPago; //Empleamos el campo de observaciones ya que recibe el pago por el 2º parámetro
			}
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_IMP_DIPTICO_PARCIAL%>'){

			var impPago = $('#importeDiptico').val();
			var importeFormateado = ''+ Math.round(impPago.replace(/\./g, '').replace(",", ".")*100)/100;
			var impPendCobroIncidencia = $('#impPendCobroDipticoIncidencia').val();
			var impPendCobroFormateado = ''+ Math.round(impPendCobroIncidencia.replace(",", ".")*100)/100;

			if (impPago==undefined || impPago==null || impPago=='' || impPago=='0' || impPago=='0,00'){
				alert('Debe introducir el importe del díptico');
				error=1;
			}
			else if(parseFloat(importeFormateado) > parseFloat(impPendCobroFormateado)){
				//Comprobar que es <= que el importe pendiente de cobro
				alert('Debe introducir un importe del díptico menor o igual al importe pendiente de cobro');
				error=1;
			}else{
				observaciones=impPago; //Empleamos el campo de observaciones ya que recibe el pago por el 2º parámetro
			}
		}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CENSO_BAJA%>'){
			if (observaciones==undefined || observaciones==null || observaciones==''){
				alert('Debe introducir el motivo');
				error=1;
			}else if (fechaBaja==undefined || fechaBaja==null || fechaBaja==''){
				alert('Debe introducir la fecha en formato dd/mm/aaaa');
				error=1;
/*			}else if (economica==undefined || economica==null || economica==''){
				alert('Debe introducir la baja economica/no');
				error=1;*/
			}
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CARGO_MODIFICAR_PERIODO_VOLUNTARIO%>'){

			var fechaInicioVoluntaria = $('input[name="nuevaFechaInicioVoluntariaIncidencia"]').val();
			var fechaFinVoluntaria = $('input[name="nuevaFechaFinVoluntariaIncidencia"]').val();
			var fechaInicioVoluntariaBBDD = $('#wwctrl_fechaInicioVoluntariaIncidencia').html();
			var fechaFinVoluntariaBBDD = $('#wwctrl_fechaFinVoluntariaIncidencia').html();

			if (fechaInicioVoluntaria==undefined || fechaInicioVoluntaria==null || fechaInicioVoluntaria==''
				|| fechaFinVoluntaria==undefined || fechaFinVoluntaria==null || fechaFinVoluntaria==''){
				alert('Debe introducir el nuevo periodo de voluntaria');
				error=1;
			}
			else {
				var fxIniVol = new Date(fechaInicioVoluntaria.substring(0,4) + '/' + fechaInicioVoluntaria.substring(5,7) + '/' + fechaInicioVoluntaria.substring(8,10));	
				var fxFinVol = new Date(fechaFinVoluntaria.substring(0,4) + '/' + fechaFinVoluntaria.substring(5,7) + '/' + fechaFinVoluntaria.substring(8,10));

				if (fxIniVol > fxFinVol){
					alert('Fecha inicio no puede ser posterior a fecha fin');
					error=1;
				}

				if (fechaInicioVoluntaria.substring(0,10) == fechaInicioVoluntariaBBDD.substring(0,10)
					&& fechaFinVoluntaria.substring(0,10) == fechaFinVoluntariaBBDD.substring(0,10)){
					alert('Modifique las fechas');
					error=1;
				}

				if(error == 0){
					var fechaActual = new Date();
					if (false && fxFinVol < fechaActual){
						alert('Fecha fin debe ser posterior al día actual');
						error=1;
					}
					else{
						observaciones = fechaInicioVoluntaria.substring(8,10) + '/' + fechaInicioVoluntaria.substring(5,7) + '/' + fechaInicioVoluntaria.substring(0,4) + '|' + fechaFinVoluntaria.substring(8,10) + '/' + fechaFinVoluntaria.substring(5,7) + '/' + fechaFinVoluntaria.substring(0,4) + '|' + observaciones; 
					}
				}
			}			
		} else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CAMBIO_CARGO%>') {
			var cargoSeleccionado = $('#cambioDeCargoCombo').val();
			if (cargoSeleccionado == '') {
				error=1;
				alert('Debe seleccionar algún cargo destino.');
			} else {
				var params = cargoSeleccionado.split('|');
				if (params[0] != '-') {
					observaciones = params[0];
					economica = '';
				}
				if (params[1] != '-') {
					observaciones = '';
					economica = params[1];
				}
			}
		} else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CAMBIO_TITULARIDAD%>') {
			var coClienteTitular = $('#hidden_coClienteTitularidad').val();
			var coClienteTitularAnterior = $('#coClienteActualTitularId').text();

			if (coClienteTitular == '') {
				error = 1;
				alert('Debe seleccionar el nuevo titular.');
			} else if (coClienteTitular == coClienteTitularAnterior) {
				error = 1;
				alert('El cliente destino no puede ser el mismo que el cliente origen.');
			} else {
				observaciones = coClienteTitular;
			}
		} else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_NOTIFICACION%>') {
			var fxNotif = $('input[name="fxNotificacion"]').val();
			var reNotif = $('#resultadoNotificacion').val();
			var obNotif = $('#observacionesNotificacion').val();
			if (fxNotif == '') {
				error=1;
				alert('Debe introducir fecha notificación.');
			}
			if (reNotif == '') {
				error=1;
				alert('Debe introducir resultado de notificación.');
			}
			
			fechaBaja = fxNotif;
			economica = reNotif;
			observaciones = obNotif;
		} else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PASE_EJECUTIVA%>') {
			observaciones = $('#observacionesPaseEjecutiva').val();
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_GENERAR_RECIBO%>'){
			//martecher
			var txt_ejercicio = $('#txt_ejercicio').val();
	 
			var elm = $('#SeleccionOpcionesIncidencia').find('input[dojoAttachPoint][name=codPeriodo]');
			var periodo = elm.val();
			if (periodo==undefined || periodo==null || periodo == '') {
				error = 1;
				alert('Debe seleccionar un período.');
			}else{
				observaciones = txt_ejercicio+"|"+periodo;
			}
			$('#coModeloSelId').val(coModelo);
			$('#coVersionSelId').val(coVersion);
			$('#coDocumentoSelId').val(coDocumento);
			$('#opcionId').val(coIncidenciaSituacion);
			$('#observacionesId').val(observaciones);
		} else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_ENVIAR%>' || coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_PUBLICAR%>' || coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_NOTIFICAR%>') {
			if (coIncidencia == '<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_BOP_PUBLICAR%>') {
				var fxBOP = $('input[name=fxBOP]').val();
				var nuBOP = $('#nuBopSel').val();
				if (nuBOP == '') {
					error = 1;
					alert('Debe introducir el número del BOP.');
				}
			
				if (fxBOP == '') {
					error = 1;
					alert('Debe introducir fecha.');
				}
				observaciones = nuBOP;
				fechaBaja = fxBOP;
			}
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_DIPTICO_DATOS_PROTEGIDOS_POR_EMAIL%>'){
			var emailSel = $('#emailSel').val();
			if (emailSel == '') {
				error = 1;
				alert('Debe introducir el email.');
			}else{				
				if(!validarEmail(emailSel)){
					error = 1;
					alert('El formato del email es incorrecto.');
				}
			}				
			observaciones = emailSel;		
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_ANULAR_PAGO%>'){
			var fechaSel = $('input[name="fechaAnularCobroSel"]').val();
			var campoObservaciones = $('input[name="observacionesSel"]').val();	

			fechaBaja = fechaSel;
			observaciones = campoObservaciones;
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CUADERNO_REENVIAR%>'){
			var fechaSel = $('input[name="fxCargoSel"]').val();

			if (fechaSel == '') {
				error = 1;
				alert('Debe introducir una fecha.');
			}
			
			fechaBaja = fechaSel;
		}
		
		
		if (error == 0) {
			$('#coModeloSelId').val(coModelo);
			$('#coVersionSelId').val(coVersion);
			$('#coDocumentoSelId').val(coDocumento);
			$('#opcionId').val(coIncidenciaSituacion);
			$('#observacionesId').val(observaciones);
			if (fechaPagado) {
				$('#fechaBajaId').val(fechaPagado);
			} else {
				$('#fechaBajaId').val(fechaBaja);
			}
			$('#economicaId').val(economica);
			$('#submitBotonOpcion').click();
		}
	}

	function muestraOpciones(data) {
		if (data != '') {
			if (data.charAt(0) == '!') {
				$('#listaOpcionesTxt').text(data.substring(1));
			} else {
				var element = data.split("@@");
				var texto = '';
				var coModelo = '';
				var coVersion = '';
				var coDocumento = '';
				texto += '<table style="width:98%">';

				var clickEvents = [];
				if (element.length > 0) {
					var element2 = element[0].split("##");

					coModelo = element2[0];
					coVersion = element2[1];
					coDocumento = element2[2];

					if (element.length == 1) {
						texto += '<tr><td>';
						texto += '<div style="width:98%;" >';
						texto += 'No hay acciones disponibles';
						texto += '</div>';
						texto += '</td></tr>';
					} else {
						for ( var i = 0; i < element.length; i++) {
							var element2 = element[i].split("##");
							if (i == 0) {
								coModelo = element2[0];
								coVersion = element2[1];
								coDocumento = element2[2];
							} else {

								var a = '';
								if (i % 2 == 0) {
									a = '2';
								} else {
									a = '';
								}
								texto += '<tr class="fila' + a + '"><td>';
								texto += '<a href=\"#\" style="width:98%;display:block" id="pulsaOpcion' + element2[1] + '">';
								texto += element2[0];
								texto += '</a>';
								texto += '</td></tr>';
								clickEvents[i] = ['pulsaOpcion'+element2[1], coModelo, coVersion, coDocumento, element2[1], element2[2]];
							}
						}
					}
				}

				texto += '</table>';

				$('#listaOpcionesTxt').html(texto);

				// Activar los eventos de las opciones.
				for(var i=1; i<clickEvents.length; i++) {
					$('#'+clickEvents[i][0]).bind('click', {arr: clickEvents[i]}, function(e) {
						pulsaOpcion(e.data.arr[1], e.data.arr[2], e.data.arr[3], e.data.arr[4], e.data.arr[5]);
						return false;
					});
				}
			}
		}
	}

	dojo.event.topic.subscribe("/actualizar", function(data){
		obtenerDomicilioFiscal(data);	
	});	

	function obtenerDomicilioFiscal(data){
		var coClienteTitular = $('#hidden_coClienteTitularidad').val();
		
		$.ajax({
	        type: "POST",
	        url: "G743CensoDocumentos!cargaDomicilioFiscalTitularAjax.action",
	        data: "coClienteNuevoTitular=" + coClienteTitular,
	        success: function(datos){
				$("#domFiscalTitularidad").text(datos);	
				$("#coClienteTitularidadId").text(coClienteTitular);			
	      	}
		});	
	}
	function validarEmail(emailIntro) { 
		expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!expr.test(emailIntro) )
			return (false);
		else
			return (true);				
	} 
</script>

<div id="ejecutarIncidenciaDiv" style="display: none">
	<div
		style="z-index: 999; background-color: #000000; opacity: 0.4; filter: alpha(opacity =   40); height: 100%; left: 0; position: fixed; top: 0; width: 100%;"></div>
	<div
		style="position: fixed; z-index: 1000; top: 25%; opacity: 1; left: 25%; width: 50%;">
		<div
			style="background-color: #E8E8E6; width: 100%; height: 350; border: 1px solid #E16F26; text-align: left; margin: 6px; padding: 10px;">
			<div style="background-color: white; height: 340px; padding: 5px">
				<div style="float: right; padding: 1px; height: 1px">
					<a id="boton-cerrar-incidencia" onclick="ocultarDivs();" href="#">
						<img src="/etir/image/iconos/16x16/close.png" alt="Cerrar"
						title="Cerrar" />
					</a>
				</div>
				<h1 id="incidenciaSel" style="margin-bottom: 0"></h1>
				<div class="fila separador">
					<hr class="linea_separadora" />
				</div>
				<div>
					<form action="" id="SeleccionOpcionesIncidencia">

						<s:hidden id="coModeloIncidencia" name="coModeloIncidencia" />
						<s:hidden id="coVersionIncidencia" name="coVersionIncidencia" />
						<s:hidden id="coDocumentoIncidencia" name="coDocumentoIncidencia" />
						<s:hidden id="coIncidenciaIncidencia"
							name="coIncidenciaIncidencia" />
						<s:hidden id="coIncidenciaSituacionIncidencia"
							name="coIncidenciaSituacionIncidencia" />

						<s:property value="%{coModeloIncidencia}" />

						<div class="fila" id="motivoIncidenciaFila" style="display: none">
							<div class="fila">
								<!--<gadir:combo1
									id="motivoIncidencia"
									label="%{getText('motivo')}"
									name="motivoIncidencia"
									keyName="coMotivoIncidencia"
									list="@es.dipucadiz.etir.comun.utilidades.TablaGt@getListaCodigoDescripcion('TMOTBAJA')"
									listKey="key"
									listValue="value"
									emptyOption="false"
									searchType="substring"
									resultsLimit="-1"
									forceValidOption="true"
									labelposition="left"
									autoComplete="false"
									styleLabel="width:20%"
									styleText="width:70%"
									cssStyle="width:80%"
									required="true"
								/>-->
								<gadir:areaTexto 
									id="motivoIncidencia"
									label="%{getText('motivo')}" 
									name="motivoIncidencia"
									conAyuda="false" 
									styleText="width:70%" 
									styleLabel="width:20%"
									required="true" 
									labelposition="left" 
									rows="2"
									cssStyle="width:80%" 
									onkeyup="this.value = largoMotivoBaja(this.value)"
								/>
							</div>
							<div class="fila">
								<gadir:campoTexto id="motivoTextoIncidencia"
									name="motivoTextoIncidencia"
									label="%{getText('informacion.adicional')}"
									labelposition="left" conAyuda="false" styleLabel="width:20%"
									styleText="width:70%" cssStyle="width:80%" required="false"
									onkeyup="this.value = largoMotivoBaja(this.value)"
								/>
							</div>
							<div class="fila" id="bajaConAnulacionCobro">
								<span class="wwlbl" style="width:20%">
									<label for="economicaIncidencia">Cobros</label>
								</span>
								<span class="wwctrl" style="width:70%">
									<select id="economicaIncidencia">
										<option value="1">Baja CON anulación de cobros</option>
										<option value="0">Baja SIN anulación de cobros</option>
									</select>
								</span>
							</div>
						</div>
<script type="text/javascript">
function largoMotivoBaja(texto) {
	var largoTotal = ($('#motivoIncidencia').val().length + 3 + $('#motivoTextoIncidencia').val().length);
	var largoPermitido = 175;
	if (largoTotal > largoPermitido) {
		var sobreSale = largoTotal - largoPermitido;
		texto = texto.substring(0,texto.length-sobreSale);
	}
	return texto;
}
</script>

						<div class="fila" id="fechasVoluntariaIncidenciaFila"
							style="display: none">
							<s:hidden id="fechaInicioVoluntariaBBDD"
								name="fechaInicioVoluntariaBBDD" />
							<s:hidden id="fechaFinVoluntariaBBDD"
								name="fechaFinVoluntariaBBDD" />

							<div class="fila">
								<h5>Periodo voluntario actual</h5>
							</div>
							<div class="fila">
								<gadir:fecha id="fechaInicioVoluntariaIncidencia"
									name="fechaInicioVoluntariaIncidencia"
									label="%{getText('fecha.inicio.periodo.voluntario')}"
									labelposition="left" displayFormat="dd/MM/yyyy"
									styleLabel="width:30%" styleText="width:60%" salida="true" />
							</div>
							<div class="fila">
								<gadir:fecha id="fechaFinVoluntariaIncidencia"
									name="fechaFinVoluntariaIncidencia"
									label="%{getText('fecha.fin.periodo.voluntario')}"
									labelposition="left" displayFormat="dd/MM/yyyy"
									styleLabel="width:30%" styleText="width:60%" salida="true" />
							</div>

							<div class="separador"></div>

							<div class="fila">
								<h5>Periodo voluntario nuevo</h5>
							</div>

							<div class="fila">
								<gadir:fecha id="nuevaFechaInicioVoluntariaIncidencia"
									name="nuevaFechaInicioVoluntariaIncidencia"
									label="%{getText('fecha.inicio.periodo.voluntario')}"
									labelposition="left" displayFormat="dd/MM/yyyy"
									styleLabel="width:30%" styleText="width:60%" required="true" />
							</div>
							<div class="fila">
								<gadir:fecha id="nuevaFechaFinVoluntariaIncidencia"
									name="nuevaFechaFinVoluntariaIncidencia"
									label="%{getText('fecha.fin.periodo.voluntario')}"
									labelposition="left" displayFormat="dd/MM/yyyy"
									styleLabel="width:30%" styleText="width:60%" required="true" />
							</div>
						</div>

						<div class="fila" id="notificacionFila" style="display: none">
							<div class="fila" style="display: none" id="filaUltimoResultadoNotificacion">
								<gadir:campoTexto id="ultimoResultadoNotificacion"
									name="ultimoResultadoNotificacion"
									label="Resultado última notificación"
									labelposition="left" 
									styleLabel="width:170px" styleText="width:200px" salida="true" />
							</div>
							<div class="fila" style="display: none" id="filaFxUltimaNotificacion">
								<gadir:fecha id="fxUltimaNotificacion"
									name="fxUltimaNotificacion"
									label="Fecha notificado"
									labelposition="left" displayFormat="dd/MM/yyyy"
									styleLabel="width:170px" styleText="width:200px" salida="true" />
							</div>
							<div class="fila" style="display: none" id="filaPeriodoVoluntarioActual">
								<gadir:campoTexto id="periodoVoluntarioActual"
									name="periodoVoluntarioActual"
									label="Periodo voluntario actual"
									labelposition="left" 
									styleLabel="width:170px" styleText="width:200px" salida="true" />
							</div>
							<div class="fila">
								<gadir:fecha id="fxNotificacion"
									name="fxNotificacion"
									label="Fecha de notificación"
									labelposition="left" displayFormat="dd/MM/yyyy"
									styleLabel="width:170px" styleText="width:200px"
									required="true" 
									value="@es.dipucadiz.etir.comun.utilidades.Utilidades@getFechaActual()"
									/>
							</div>
							<div class="fila">
								<gadir:combo id="resultadoNotificacion"
									name="resultadoNotificacion"
									label="Resultado notificación"
									labelposition="left"
									styleLabel="width:170px" styleText="width:200px"
									list="{}"
									required="true"
								/>
							</div>
							<div class="fila">
								<gadir:campoTexto id="observacionesNotificacion"
									name="observacionesNotificacion"
									label="%{getText('observaciones')}" labelposition="left"
									conAyuda="false" styleLabel="width:170px" styleText="width:65%" cssStyle="width:99%"
									required="false" size="100" maxlength="100" />
							</div>
						</div>
						
						<div class="fila" id="observacionesIncidenciaFila"
							style="display: none">
							<gadir:campoTexto id="observacionesIncidencia"
								name="observacionesIncidencia"
								label="%{getText('observaciones')}" labelposition="left"
								conAyuda="false" styleLabel="width:20%" styleText="width:70%"
								cssStyle="width:80%" required="true" />
						</div>

						<div class="fila" id="fechaBajaIncidenciaFila"
							style="display: none">
							<gadir:fecha id="fechaBajaIncidencia" name="fechaBajaIncidencia"
								label="%{getText('fecha.baja')}" labelposition="left"
								displayFormat="dd/MM/yyyy" styleLabel="width:20%"
								styleText="width:70%" required="true" />
						</div>

						<div class="fila" id="fechaPagadoIncidenciaFila"
							style="display: none">
							<gadir:campoTexto label="%{getText('pendiente.cobro')}"
								labelposition="left" cssClass="numericoMonedaEnteraDecimal"
								name="vntImpPendCobro" disabled="true" id="vntImpPendCobro"
								conAyuda="false" size="18" styleGroup="width:90%"
								styleLabel="width:30%" styleText="width:69%" />
							<gadir:campoTexto label="%{getText('principal')}"
								labelposition="left" cssClass="numericoMonedaEnteraDecimal"
								name="vntImpPrincipal" disabled="true" id="vntImpPrincipal"
								conAyuda="false" size="18" styleGroup="width:90%"
								styleLabel="width:30%" styleText="width:69%" />
							<gadir:fecha id="fechaPagadoIncidencia"
								name="fechaPagadoIncidencia" label="Fecha pagado"
								labelposition="left" displayFormat="dd/MM/yyyy"
								styleGroup="width:90%" styleLabel="width:30%"
								styleText="width:69%" required="true" />
						</div>
						<%--						
						<div class="fila" id="economicaIncidenciaFila" style="display:none" >
							<gadir:combo1 
								label="%{getText('economica')}"
								labelposition="left"
								name="economicaIncidencia"
								keyName="coEconomicaIncidencia"
								list="#{'0':'NO','1':'SI'}"  
								searchType="substring"
								resultsLimit="-1"
								forceValidOption="true"
								cssStyle="width:40px"
								emptyOption="false"
								autoComplete="false"
								styleLabel="width:20%"
								styleText="width:70%"
								required="true"
							/>
						</div>
--%>
						<div class="fila" id="fechaPagoFila"
							style="display: none; margin-left: 5%; width: 85%">
							<gadir:fecha id="fechaPagoParcial" name="fechaPagoParcial"
								label="Fecha pago" labelposition="left"
								displayFormat="dd/MM/yyyy" styleGroup="width:90%"
								styleLabel="width:30%" styleText="width:69%" required="true" />
						</div>
						<div class="fila" id="importesPagoIncidenciaFila"
							style="display: none; margin-left: 5%; width: 85%">

							<s:hidden id="impPendCobroIncidencia"
								name="impPendCobroIncidencia" />

							<gadir:campoTexto label="%{getText('principal')}"
								labelposition="left" cssClass="numericoMonedaEnteraDecimal"
								name="impPrincipal" readonly="true" id="impPrincipal"
								conAyuda="false" size="18" styleGroup="width:90%"
								styleLabel="width:30%" styleText="width:69%" />
							<br />
							<gadir:campoTexto label="%{getText('pendiente.cobro')}"
								labelposition="left" cssClass="numericoMonedaEnteraDecimal"
								name="impPendCobro" readonly="true" id="impPendCobro"
								conAyuda="false" size="18" styleGroup="width:90%"
								styleLabel="width:30%" styleText="width:69%" />
							<div class="fila separador"></div>
							<gadir:campoTexto label="%{getText('imp.pago')}"
								labelposition="left" cssClass="numericoMonedaEnteraDecimal"
								name="importePago" required="true" id="importePago"
								conAyuda="false" size="18" maxLength="18" styleGroup="width:90%"
								styleLabel="width:30%" styleText="width:69%" />
							
							<div class="fila separador">
								<s:text
									name="imp.pago.menor" />
							</div>
							
						</div>

						<div class="fila" id="importesDipticoParcialIncidenciaFila"
							style="display: none; margin-left: 5%; width: 85%">

							<s:hidden id="impPendCobroDipticoIncidencia"
								name="impPendCobroDipticoIncidencia" />

							<gadir:campoTexto label="%{getText('total')}"
								labelposition="left" cssClass="numericoMonedaEnteraDecimal"
								name="impTotal" readonly="true" id="impTotal" conAyuda="false"
								size="18" styleGroup="width:90%" styleLabel="width:30%"
								styleText="width:69%" />
							<br />
							<gadir:campoTexto label="%{getText('pendiente.cobro')}"
								labelposition="left" cssClass="numericoMonedaEnteraDecimal"
								name="impPendCobro" readonly="true" id="impPendCobroDiptico"
								conAyuda="false" size="18" styleGroup="width:90%"
								styleLabel="width:30%" styleText="width:69%" />
							<div class="fila separador"></div>
							<gadir:campoTexto label="%{getText('imp.diptico')}"
								labelposition="left" cssClass="numericoMonedaEnteraDecimal"
								name="importeDiptico" required="true" id="importeDiptico"
								conAyuda="false" size="18" maxLength="18" styleGroup="width:90%"
								styleLabel="width:30%" styleText="width:69%" />

							<div class="fila separador">
								 El importe del díptico debe ser menor o igual al importe pendiente de cobro


							</div>
						</div>

						<div id="cambioDeCargoFila" style="display: none">
							Seleccione cargo destino: <select id="cambioDeCargoCombo">
								<option></option>
							</select>
						</div>

						<div class="fila" id="cambioTitularidadFila" style="display: none; margin-left: 1%; width: 98.5%">
							<div class="fila separador"	style="width: 98.5%; color: gray; border-bottom: 1px solid gray">
								Datos del documento:
							</div>

							<div class="fila">
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 29%">
									<label class="label">Documento:</label>
									<span id="coDocumentoOrigenId"></span>
								</div>	
								<div class="wwlbl" style="float: left; white-space: nowrap; width: 29%">
									<label class="label">Municipio</label>
									<span id="municipioId"></span>
								</div>	
								<div class="wwlbl" style="float: left; white-space: nowrap; width: 41%">
									<label class="label">Concepto</label>
									<span id="conceptoId"></span>
								</div>						
							</div>
							<div class="fila">
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 99%">
									<label class="label">Dom. Tributario</label>
									<span id="domicilioTributarioId"></span>
								</div>								
							</div>

							<div class="fila">
								<div class="wwlbl" style="float: left; white-space: nowrap; width: 29%">
									<label class="label">Ejercicio</label>
									<span id="ejercicioId"></span>
								</div>

								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 29%">
									<label class="label">Periodo</label>
									<span id="periodoId"></span>
								</div>
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 41%">
									<label class="label">Ref. Obj. Trib.</label>
									<span id="refObjTribId"></span>
								</div>
							</div>

							<div class="fila">
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 58%">
									<label class="label">Ref. Domiciliación</label>
									<span id="refDomiciliacionId"></span>
								</div>
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 41%">
									<label class="label">Ref. Catastral</label>
									<span id="refCatastralId"></span>
								</div>
							</div>

							<div class="fila separador" style="width: 98%; color: gray; border-bottom: 1px solid gray">
								Titular	actual:
							</div>
							<div class="fila">
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 79%">
									<label class="label">Contribuyente</label>
									<span id="clienteTitularId"></span>
								</div>
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 20%">
									<label class="label">Código</label>
									<span id="coClienteActualTitularId"></span>
								</div>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label">Domicilio fiscal</label>
								<span id="domicilioFiscalTitularId"></span>
							</div>
							
							<div class="fila separador"	style="width: 98%; color: gray; border-bottom: 1px solid gray">
								Seleccione el nuevo titular:
							</div>

							<div class="fila">
								<div style="float: left; white-space: nowrap; overflow: hidden; width: 79%">
									<gadir:campoTextoSelec 
										name="clienteTitularidad"
										value="%{clienteTitularidad}"
										codigoValue="%{coClienteTitularidad}"
										nifValue="%{nifTitularidad}" 
										label="%{getText('cliente')}"
										labelposition="left" 
										id="texto_clienteTitularidad"
										onclick="mostrarVentanaEfectiva();" 
										required="false"
										isCliente="true" 
										styleLabel="width:23%" 
										styleText="width:76%"
										sufijo="Titularidad" 									
									/>
								</div>
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 20%">
									<label class="label">Códgio</label>
									<span id="coClienteTitularidadId"></span>
								</div>									
							</div>
							<div class="fila wwlbl">
								<label class="label">Domicilio fiscal</label>
								<span id="domFiscalTitularidad"></span>
							</div>
						</div> 

						<div class="fila" id="paseEjecutivaFila" style="display: none">
							<div class="fila">
								<div class="wwgrp">
									<span class="wwlbl" style="width:12%"><label class="label">Documento:</label></span>
									<span class="wwctrl" id="documentoPaseEjecutiva"></span>
								</div>
							</div>
							<div class="fila">
								<div class="wwgrp">
									<span class="wwlbl" style="width:12%"><label class="label">Inicio voluntaria:</label></span>
									<span class="wwctrl" id="fxIniPaseEjecutiva"></span>
								</div>
							</div>
							<div class="fila">
								<div class="wwgrp">
									<span class="wwlbl" style="width:12%"><label class="label">Fin voluntaria:</label></span>
									<span class="wwctrl" id="fxFinPaseEjecutiva"></span>
								</div>
							</div>
							<div class="fila">
								<gadir:campoTexto id="observacionesPaseEjecutiva"
									name="observacionesIncidencia"
									label="%{getText('observaciones')}" labelposition="left"
									conAyuda="false" styleLabel="width:12%" styleText="width:70%"
									cssStyle="width:85%" required="false" maxlength="200" />
							</div>
						</div>
						
						
						
						<!-- martecher -->
						<div class="fila" id="generarReciboFila" style="display: none">
							<s:hidden id="coModeloSel" name="coModeloSel" />
							<s:hidden id="coVersionSel" name="coVersionSel" />
							<s:hidden id="coDocumentoSel" name="coDocumentoSel" />
							
							<s:hidden id="codPeriodo" name="codPeriodo" />							
							 
						 
								<div class="fila separador"	style="width: 98.5%; color: gray; border-bottom: 1px solid gray">
								Datos del documento:
							</div>

							<div class="fila">
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 29%">
									<label class="label">Documento:</label>
									<span id="codDocumento"></span>
								</div>	
								<div class="wwlbl" style="float: left; white-space: nowrap; width: 29%">
									<label class="label">Municipio</label>
									<span id="municipioId2"></span>
								</div>	
								<div class="wwlbl" style="float: left; white-space: nowrap; width: 41%">
									<label class="label">Concepto</label>
									<span id="conceptoId2"></span>
								</div>						
							</div>
	 

							<div class="fila separador" style="width: 98%; color: gray; border-bottom: 1px solid gray">
								Titular	actual:
							</div>
							<div class="fila">
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 79%">
									<label class="label">Contribuyente</label>
									<span id="clienteTitularId2"></span>
								</div>
								<div class="wwlbl" style="float: left; white-space: nowrap; overflow: hidden; width: 20%">
									<label class="label">Código</label>
									<span id="coClienteActualTitularId2"></span>
								</div>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label">Domicilio fiscal</label>
								<span id="domicilioFiscalTitularId2"></span>
							</div>
							
							<div class="fila separador"	style="width: 98%; color: gray; border-bottom: 1px solid gray">
								Ejercicio:
							</div>

							<div class="fila">
								<s:if test="%{!accesible}">
				             		<div class="fila">
				             		
				             			<script type="text/javascript">
				             				var ejercicioAnterior;
				             				function ejercicioChanged(e) {
				             					var ejercicioActual = e.value;
				             					 
				             					if (ejercicioActual.length == 4 && ejercicioAnterior != ejercicioActual) {
				             						dojo.event.topic.publish('/cargaPeriodos');
				             						ejercicioAnterior = ejercicioActual;
				             					}
				             				}
				             			</script>
				             		
										<gadir:campoTexto id="txt_ejercicio" name="ejercicio" label="Ejercicio"  
				                       		labelposition="left" title="Ejercicio"
				                            maxlength="4" required="true" cssStyle="width:50px;"
				                            onkeyup="ejercicioChanged(this)" onchange="ejercicioChanged(this)" onblur="ejercicioChanged(this)" conAyuda="false"
										/>    
									 <!-- Combo Periodos -->
			
										<s:url action="G743CensoDocumentos" var="cargaPeriodosAjax" method="cargaPeriodosAjax"></s:url>
										<gadir:combo3
											id="codPeriodoKey"
											label="Período"
										   	name="codPeriodoKey"
								            keyName="codPeriodo"
											href="%{#cargaPeriodosAjax}" 
											searchType="substring"
											resultsLimit="-1"
											forceValidOption="true"
											autoComplete="false"
											formId="SeleccionOpcionesIncidencia"
											indicator="indicatorPeriodo"
											showDownArrow="true"
											cssStyle="width:230px"
											labelposition="left"
											listenTopics="/cargaPeriodos"
											required="true"
											
											
										/>
				                        <!-- Fin Combo Periodos -->
									</div>	
								</s:if>
							
								<s:else>
							 		<div class="fila">
							 			<gadir2:campoTexto 	id="txt_ejercicio" 
							 								name="ejercicio" 
															label="Ejercicio" 
									 						tabindex="%{tabIndex}" 
									 						maxlength="4" 
									 						cssStyle="margin-left: 37%; width:20%;" 
									 						styleText="width: 20%;"
									 	/>
							 			<s:submit	cssClass="botonNormal" 
							 						id="btn_cargarPeriodos" 
							 						theme="simple" 
							 						value="Cargar Períodos" 
					                           		title="Cargar Períodos" 
					                           		tabindex="%{tabIndex}" 
					                           		cssStyle="margin-left:7.9%;  width:15%;" 
					                           		action="G743CensoDocumentos"/>		 			
							 			<!-- Combo Periodos -->
							 			<s:label 	cssClass="sinancho" 
							 						cssStyle="margin-left:0.5%;" 
							 						for="txt_periodo" 
							 						id="lbl_periodo" 
							 						theme="simple"
					                        		value="Período"/>
							 			<s:select 	id="txt_periodo" 
							 						list="periodos" 
							 						headerKey="" 
							 						headerValue="Seleccione" 
							 						listValue="codigoDescripcion" 
							 						listKey="key" 
							 						name="codPeriodo"  
					                             	labelposition="left" 
					                             	tabindex="%{tabIndex}" 
					                             	title="Período" 
					                             	theme="simple" 
					                             	cssClass="tipo1" 
					                             	cssStyle="width:15%;margin-left:3%;" />
					                    <!-- Fin Combo Periodos -->
							 		</div>
							 	</s:else>
							</div>
						</div>
						<!-- martecher -->
						
						<div class="fila" id="incidenciaBOP" style="display: none">
							<gadir:campoTexto
								id="nuBopSel"
								name="nuBopSel"
								label="Número BOP"
								labelposition="left"
								conAyuda="false"
								styleLabel="width:170px"
								styleText="width:200px"
								size="10"
								maxlength="10"
								required="true"
							/>
						</div> 
						
						<div class="fila" id="filaTextoEnvioBOP" style="display: none">
							<s:label value="Pulse aceptar para proceder al envío del BOP" />
						</div>
						
						<div class="fila" id="filaFxBOP" style="display: none">
							<gadir:fecha
								id="fxBOP"
								name="fxBOP"
								label="Introduzca fecha"
								labelposition="left"
								displayFormat="dd/MM/yyyy"
								styleLabel="width:170px"
								styleText="width:200px"
								required="true" 
							/>
						</div>
						
						<div class="fila" id="bajaCostasFila" style="display: none">
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Fecha de alta:</label>
								<span id="costasFx"></span>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Descripción:</label>
								<span id="costasDesc"></span>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Importe:</label>
								<span id="costasImp"></span>
							</div>
						</div>
						
						<div class="fila" id="desasignacionCostasFila" style="display: none">
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Documento:</label>
								<span id="costasDoc"></span>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Municipio:</label>
								<span id="costasMun"></span>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Concepto:</label>
								<span id="costasCon"></span>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Situación:</label>
								<span id="costasSit"></span>
							</div>
							<h5>Importe pendiente</h5>
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Principal:</label>
								<span id="costasPri"></span>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Recargo:</label>
								<span id="costasRec"></span>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Intereses:</label>
								<span id="costasInt"></span>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Costas:</label>
								<span id="costasCos"></span>
							</div>
							<div class="fila">
								<label class="wwlbl" class="label" style="width:80px">Total:</label>
								<span id="costasTot"></span>
							</div>
						</div>
						
						<div class="fila" id="emailDipticoProtegido" style="display: none">
							<div class="fila">
								<gadir:campoTexto
									id="emailSel"
									name="emailSel"
									label="E-mail"
									labelposition="left"
									conAyuda="false"
									styleLabel="width:60px"
									styleText="width:400px"
									required="true"
									size="60"
								/>
							</div>
						</div>		
						<div class="fila" id="fxCargo" style="display: none">
							<div class="fila">
								<gadir:fecha
									id="fxCargoSel"
									name="fxCargoSel"
									label="Fecha del cargo"
									labelposition="left"
									conAyuda="false"
									styleLabel="width:90px"
									styleText="width:400px"
								/>
							</div>
						</div>
						<div class="fila" id="fechaAnularCobro" style="display: none">
							<div class="fila">
								<gadir:fecha
									id="fechaAnularCobroSel"
									name="fechaAnularCobroSel"
									label="Fecha"
									labelposition="left"
									conAyuda="false"
									styleLabel="width:90px"
									styleText="width:400px"
								/>
							</div>
							<div class="fila">
								<gadir:campoTexto
									id="observacionesSel"
									name="observacionesSel"
									label="Observaciones"
									labelposition="left"
									conAyuda="false"
									styleLabel="width:90px"
									styleText="width:400px"
									size="50"
									maxlength="100"
								/>
							</div>
						</div>		
										
					</form>

					<div class="fila separador">
						<input id="boton-ejecutar-incidencia" type="button"
							value="<s:text name="button.aceptar"/>">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>






<div id="listaOpcionesDiv"
	style="background-color: #FFFFFF; border: 1px solid #7B9FB7; display: none; left: -1px; overflow: hidden; position: absolute; top: 300px; left: 300px; width: 250px; z-index: 2; padding: 5px; color: #333333; direction: ltr;">


	<div id="listaOpcionesTxt" style="clear: both">
		<img style="vertical-align: middle; position: absolute;"
			alt="Cargando..." src="/etir/image/indicator.gif">
	</div>

	<div
		style="padding-top: 2px; text-align: center; border-top: 1px solid gray;">
		<a id="boton-cerrar-listaOpciones" href="#"> Cerrar </a>
	</div>
</div>
