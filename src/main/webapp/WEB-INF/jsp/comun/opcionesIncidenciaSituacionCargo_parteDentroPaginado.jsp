<%@ include file="../taglibs.jsp"%>

<script type="text/javaScript">

	$(document).ready(function(){	

		$('#boton-ejecutar-incidencia-cargo').click(function() {

			var coCargo = $('#coCargoIncidenciaCargo').val();
			var coCargoSubcargo = $('#coCargoSubcargoIncidenciaCargo').val();
			var coIncidencia = $('#coIncidenciaIncidenciaCargo').val();
			var coIncidenciaSituacion = $('#coIncidenciaSituacionIncidenciaCargo').val();
			
			pulsaOpcionEspecial(coCargo, coCargoSubcargo, coIncidenciaSituacion, coIncidencia);
		});
		
		$('#boton-cerrar-listaOpciones').click(function() {
			$('#listaOpcionesDiv').toggle(false);
			$('.backgroundLemonChiffon').removeClass("backgroundLemonChiffon");
		});

		$('#boton-cerrar-incidencia-cargo').click(function() {
			
			$('#ejecutarIncidenciaCargoDiv').toggle(false);
		});
		
		$('.claseBotonCalculaOpciones').click(function(e) {

			var $target = $(e.target);
			//$target.parent().parent().parent().css("background", "LemonChiffon");
			$target.parent().parent().parent().addClass("backgroundLemonChiffon");

			var codigoCompleto=this.id;
			var aux = codigoCompleto.split("|");
			var coCargo=aux[0];
			var coCargoSubcargo=aux[1];
			
			$('#listaOpcionesDiv').css("left", e.pageX-200);
	      	$('#listaOpcionesDiv').css("top", e.pageY);
	      	$('#listaOpcionesTxt').html('<img style="vertical-align: middle; position: absolute;" alt="Cargando..." src="/etir/image/indicator.gif"><br/>');	  
			$('#listaOpcionesDiv').toggle(true);

			$.ajax({
				url: '/etir/${actionName}!ajaxBotonCalculaOpciones.action',
				data: 'coCargo=' + coCargo + '&coCargoSubcargo=' + coCargoSubcargo,
				success: function(data) {
					muestraOpciones(data);
					
					var posYMax = $(window).height() - $('#listaOpcionesDiv').height() - 12;
					if (e.pageY > posYMax) {
						$('#listaOpcionesDiv').css("top", posYMax);
					}
				}
			});
			
			return (false);
		});
		
		$('.claseBotonCalculaOpciones').toggle(true);

	});
	
</script>

