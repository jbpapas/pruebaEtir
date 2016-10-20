<%@ include file="../taglibs.jsp"%>


<script type="text/javaScript">

	$(document).ready(function(){	

		$('#boton-cerrar-listaOpciones').click(function() {
			$('#listaOpcionesDiv').toggle(false);
			$('.backgroundLemonChiffon').removeClass("backgroundLemonChiffon");
		});
		
		$('.claseBotonCalculaOpciones').click(function(e) {

			var $target = $(e.target);
			//$target.parent().parent().parent().css("background", "LemonChiffon");
			$target.parent().parent().parent().addClass("backgroundLemonChiffon");

			var coFraccionamiento=this.id;

			$('#listaOpcionesDiv').css("left", e.pageX-200);
	      	$('#listaOpcionesDiv').css("top", e.pageY);
	      	$('#listaOpcionesTxt').html('<img style="vertical-align: middle; position: absolute;" alt="Cargando..." src="/etir/image/indicator.gif"><br/>');	  
			$('#listaOpcionesDiv').toggle(true);
			
			$.ajax({
				url: '/etir/${actionName}!ajaxBotonCalculaOpciones.action',
				data: 'coFraccionamiento=' + coFraccionamiento,
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

