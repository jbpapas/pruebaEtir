<%@ include file="../taglibs.jsp"%>

<script type="text/javaScript" >

	$(document).ready(function(){	

		
		$('.claseBotonCalculaOpciones').click(function(e) {

			var $target = $(e.target);
			//$target.parent().parent().parent().css("background", "LemonChiffon");
			$target.parent().parent().parent().addClass("backgroundLemonChiffon");

			var codigoCompleto=this.id;
			var coModelo = codigoCompleto.substr(0,3);
			var coVersion = codigoCompleto.substr(3,1);
			var coDocumento = codigoCompleto.substr(4);
			
			$('#listaOpcionesDiv').css("left", e.pageX-250);
	      	$('#listaOpcionesDiv').css("top", e.pageY);
	      	$('#listaOpcionesTxt').html('<img style="vertical-align: middle; position: absolute;" alt="Cargando..." src="/etir/image/indicator.gif"><br/>');	  
			$('#listaOpcionesDiv').toggle(true);


			$.ajax({
				url: '/etir/${actionName}!ajaxBotonCalculaOpciones.action',
				data: 'coModeloSel=' + coModelo + '&coVersionSel=' + coVersion + '&coDocumentoSel=' + coDocumento,
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
