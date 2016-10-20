<script type="text/javaScript">
<!--
	var dateLoad = new Date();
	var elapsedTime = 0;
	var ttimer = 60000; // Valor del timer (milisegundos) 
	var tpaso  = 180000; // Incremento del timer cada llamada
	var tmax   = 432000000;// Valor m·ximo del timer	432000000 = 5 dÌas
	
	function programacionActualizacionNumNotificaciones(bienvenido) {
		var dateNow = new Date();
		elapsedTime = dateNow.getTime() - dateLoad.getTime();
		actualizaNumeroNotificaciones(bienvenido);
		setTimeout('programacionActualizacionNumNotificaciones('+bienvenido+')', ttimer);	
		if(ttimer >= tmax)
			ttimer = tmax;
		else
			ttimer += tpaso;
	}
	
	function ocultaPie() {
		return;
/*
		$('#textoNotificaciones').text('No tiene notificaciones.');
		$('#boton-notificaciones').css('visibility', 'hidden');
		$('#span-numero-notificaciones').css('visibility', 'hidden');*/
	}
	
	function actualizaPie(datos) {
		return;
/*		$('#numero-notificaciones').text(datos);
		if(datos!="0" && !isNaN(datos)) {
			$('#textoNotificaciones').text('Notificaciones: ');
			$('#boton-notificaciones').css('visibility', 'visible');
			$('#span-numero-notificaciones').css('visibility', 'visible');
		} else {
			ocultaPie();
		}*/	
	}
	
	function eventos1() {
		$('#boton-cerrar-notificaciones').click(function() {
			$('#div-notificaciones').slideToggle();
			return false;
		});
		
		$('#boton-confirmar-notificaciones').click(function() {
			$('#div-notificaciones').slideToggle();

			var parametros="";
			$("#form-notificaciones input:checked").each(function (i) {
				parametros+="confirmadas=" + this.id + "&";
		      });
			parametros += "usuario=<%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getLogin()%>";
			$.ajax({
		        type: "POST",
		        url: "notificaciones!confirmaNotificacionesAjax.action",
		        data: parametros,
		        success: function(datos){
					if(!isNaN(datos))
						actualizaPie(datos);
					else {
						conexionNoDisponible();
					}
				},
				error: function(datos){
					conexionNoDisponible();
				}
			});		
			
			$("#form-notificaciones input:checked").each(function (i) {
				$("#tr_"+this.id).remove();
		      });
	
		});
		
		$('#boton-confirmar-todo').click(function() {
			$('#div-notificaciones').slideToggle();

			var parametros = "usuario=<%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getLogin()%>";
			$.ajax({
		        type: "POST",
		        url: "notificaciones!confirmaTodasNotificacionesAjax.action",
		        data: parametros,
		        success: function(datos){
					if(!isNaN(datos))
						actualizaPie(datos);
					else {
						conexionNoDisponible();
					}
				},
				error: function(datos){
					conexionNoDisponible();
				}
			});		
		});
		
		
	}
	
	function eventos3() {
		$('#boton-cerrar-aviso').click(function() {
			$('#overlaysContainer').toggle(false);
		});
	
		$('#boton-confirmar-aviso').click(function() { 
			var parametros="";
			$("#form-avisos input:checked").each(function (i) {
				parametros+="confirmadas=" + this.id + "&";
		    });
			parametros+="usuario=<%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getLogin()%>";
			$.ajax({
			        type: "POST",
			        url: "notificaciones!confirmaNotificacionesAjax.action",
			        data: parametros,
			        success: function(datos){
						$('#overlaysContainer').toggle(false);
					}	
			});
	
			$("#form-avisos input:checked").each(function (i) {
				$("#tr_"+this.id).remove();
		    });
		});
	}
	
	function eventos2() {
		$('#boton-notificaciones').click(function() {
			muestraNotificacionesActualizadas(this.id, ($('#div-notificaciones').css("display") == 'none'));
			return false;
		});
		
		$('#boton-confirmar-notificaciones2').click(function() { 			  
			var parametros="";	
			$("#form-notificaciones2 input:checked").each(function (i) {
				parametros+="confirmadas=" + this.id + "&";
		    });
		
			parametros+="usuario=<%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getLogin()%>";
		
			$.ajax({
			        type: "POST",
			        url: "notificaciones!confirmaNotificacionesAjax.action",
			        data: parametros,
			        success: function(datos){
						$('#boton-confirmar-notificaciones2').toggle(false);
					}	
			});	
			$("#form-notificaciones2 input:checked").each(function (i) {
				$("#numMensaje_" + this.value).remove();
				$("#filaMensaje_" + this.value).remove();				
				$("#mensaje_"+this.id).remove();								
		    });
		
		});	
	}
	
	function preparaEventos(todos) {
		// Notificaci√≥n 1
		eventos1();
		
		if(!todos) return;
	
		// Notificaci√≥n 2
		eventos2();
	}	
	
	var actualizando = false;
	function actualizaNumeroNotificaciones(bienvenido) {
		if(actualizando) return;
		actualizando = true;
		var parametros = "usuario=<%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getLogin()%>&timer="+elapsedTime;
		$.ajax({
	        type: "POST",
	        url: "notificaciones!totalNotificacionesAjax.action",
	        data: parametros,
	        success: function(datos){
				if(!isNaN(datos) && datos >= 0) {
					actualizaPie(datos);

					// Notificaciones 3
					muestraNotificaciones3Actualizadas(bienvenido);
				} else if (datos == -1) {
					window.location.href = '/etir/login.action?fin_sesion=1';
				} else {
					conexionNoDisponible();
				}
	
				actualizando = false;
	      	},
			error: function(datos){
				conexionNoDisponible();

				actualizando = false;
			}      	
		});		
	}	

	function conexionNoDisponible() {
		ocultaPie();
		$('#textoNotificaciones').text('');

		$('#div-notificaciones').toggle(false);
		
		infoDialog("eTIR", "No se puede acceder al Servidor de eTIR");
	}
	
	var dentroMuestra = false;
	function muestraNotificacionesActualizadas(idElemento, mostrar) {
		if(dentroMuestra)
			return;
		dentroMuestra = true;
		actualizaNumeroNotificaciones();
		$.ajax({
			url: '/etir/notificacionesUsuario!muestraNotificaciones.action',
			data: '',		
			cache: false,
			success: function(data) {
				// Notificaciones 1
				$('#div-notificaciones').html(data);
				$('#div-notificaciones').slideToggle();
	
				preparaEventos(false);
	
				dentroMuestra = false;
			}
		});
	}
	var dentroMuestra3 = false;
	function muestraNotificaciones3Actualizadas(bienvenido) {
		if(dentroMuestra3)
			return;
		dentroMuestra3 = true;
		$.ajax({
			url: (bienvenido ? '/etir/notificaciones3LUsuario!muestraNotificaciones.action' : '/etir/notificaciones3TUsuario!muestraNotificaciones.action'),
			data: '',		
			cache: false,
			success: function(data) {
				// Notificaciones 3
				$('#overlaysContainer').html(data);			
	
				dentroMuestra3 = false;
			}
		});
	}
-->
</script>
