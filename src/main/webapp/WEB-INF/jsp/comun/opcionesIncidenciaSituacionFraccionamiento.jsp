<%@ include file="../taglibs.jsp"%>


<script type="text/javaScript">

	$(document).ready(function(){	
		$('#boton-ejecutar-incidencia').click(function() {
			
			var coFraccionamiento = $('#coFraccionamientoIncidencia').val();
			var coIncidencia = $('#coIncidenciaIncidencia').val();
			var coIncidenciaSituacion = $('#coIncidenciaSituacionIncidencia').val();
			
			pulsaOpcionEspecial(coFraccionamiento, coIncidenciaSituacion, coIncidencia);
		});
		
		$('#boton-cerrar-listaOpciones').click(function() {
			$('#listaOpcionesDiv').toggle(false);
			$('.backgroundLemonChiffon').removeClass("backgroundLemonChiffon");
		});
		
		$('#boton-cerrar-incidencia').click(function() {
			
			$('#ejecutarIncidenciaDiv').toggle(false);
		});
	});


	function pulsaOpcion(coFraccionamiento, coIncidenciaSituacion, coIncidencia){

		if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_FRACCIONAMIENTO_ANULAR%>' 
			|| coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_FRACCIONAMIENTO_DENEGAR%>'){

			guardaDatos(coFraccionamiento, coIncidenciaSituacion, coIncidencia);
			if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_FRACCIONAMIENTO_DENEGAR%>')				
				$('#incidenciaSel').text('Denegar fraccionamiento');
			else
				$('#incidenciaSel').text('Anular fraccionamiento');
	 		$('#ejecutarIncidenciaDiv').toggle(true);

	 		document.onkeypress=function(e){
	 			var esIE=(document.all);
	 			var esNS=(document.layers);
	 			tecla=(esIE) ? event.keyCode : e.which;
	 			if(tecla==13){
	 				$('#boton-ejecutar-incidencia').click();
	 			}
	 		}
	 				 		
		}
		else{

			$('#coFraccionamientoSelId').val(coFraccionamiento);
			$('#opcionId').val(coIncidenciaSituacion);
			$('#submitBotonOpcion').click();
		}
	}

	function guardaDatos(coFraccionamiento, coIncidenciaSituacion, coIncidencia){
		$('#coFraccionamientoIncidencia').val(coFraccionamiento);
		$('#coIncidenciaSituacionIncidencia').val(coIncidenciaSituacion);
		$('#coIncidenciaIncidencia').val(coIncidencia);
	}


	function pulsaOpcionEspecial(coFraccionamiento, coIncidenciaSituacion, coIncidencia){

		var error=0;
		
		var motivoTexto = $('input[name="motivoTextoIncidencia"]').val();
		
		if (motivoTexto==undefined || motivoTexto==null || motivoTexto==''){
			alert('Debe introducir el motivo');
			error=1;
		}
				
		if (error==0){
			$('#coFraccionamientoSelId').val(coFraccionamiento);
			$('#opcionId').val(coIncidenciaSituacion);
			
			$('#motivoIncidenciaId').val(motivoTexto);
			
			$('#submitBotonOpcion').click();
		}
	}
	
	
	function muestraOpciones(data){
		 if (data!=''){

			var element = data.split("@@"); 
			var texto='';
			var coFraccionamiento='';
			texto += '<table style="width:98%">';

			var clickEvents = [];
			if (element.length>0){
				coFraccionamiento=element[0];

	    		if (element.length==1){
	    			texto += '<tr><td>';
		    		texto += '<div style="width:98%;" >';
		    		texto += 'No hay acciones disponibles';
		    		texto += '</div>';
		    		texto += '</td></tr>';
				}else{
					for (var i=0;i<element.length;i++){
			    		
				    	if (i==0){
				    		coFraccionamiento=element[0];
				    	}else{
				    		var element2 = element[i].split("##");
				    		var a = '';
				    		if(i%2==0){
					    		a='2';
				    		}else{
								a='';
					    	}
				    		texto += '<tr class="fila' + a + '"><td>';
				    		texto += '<a href=\"#\" style="width:98%;display:block" id="pulsaOpcion' + element2[1] + '">';
				    		//texto += '<a href=\"#\" style="width:98%;display:block" onclick="pulsaOpcion(\'' + coFraccionamiento + '\', \'' + element2[1] + '\', \'' + element2[2] + '\');return false">';
				    		texto += element2[0];
				    		texto += '</a>';
				    		texto += '</td></tr>';
				    		clickEvents[i] = ['pulsaOpcion'+element2[1], coFraccionamiento, element2[1], element2[2]];
				    	}
			    	}
				}
			}
	    	
	    	texto += '</table>';
	    	
	    	$('#listaOpcionesTxt').html(texto);

	    	// Activar los eventos de las opciones.
			for(var i=1; i<clickEvents.length; i++) {
				$('#'+clickEvents[i][0]).bind('click', {arr: clickEvents[i]}, function(e) {
					pulsaOpcion(e.data.arr[1], e.data.arr[2], e.data.arr[3]);
					return false;
				});
			}
	    }
	}
	
</script>


<div id="ejecutarIncidenciaDiv" style="display:none">				
    <div style="z-index: 999;background-color:#000000;opacity:0.4; filter: alpha(opacity = 40); height:100%; left:0; position:fixed; top:0; width:100%;"></div>			    
    <div style="position: fixed; z-index: 1000; top: 30%; opacity: 1; left: 25%;width: 50%;">			    	
    	<div style="background-color:#E8E8E6;width: 100%; height: 150px; border:1px solid #E16F26; text-align:left; margin:6px; padding:10px;">										
			<div style="background-color:white; height: 140px; padding: 5px">
				<div style="float:right; padding:1px; height:1px">
					<a id="boton-cerrar-incidencia" href="#">
						<img src="/etir/image/iconos/16x16/close.png" alt="Cerrar" title="Cerrar" />
					</a>
				</div>
				<h1 id="incidenciaSel" style="margin-bottom:0" ></h1>
				<div class="fila separador">
					<hr class="linea_separadora"/>
	           	</div>
				<div>
					<form action="">
					
						<s:hidden id="coFraccionamientoIncidencia" name="coFraccionamientoIncidencia" />
						<s:hidden id="coIncidenciaIncidencia" name="coIncidenciaIncidencia" />
						<s:hidden id="coIncidenciaSituacionIncidencia" name="coIncidenciaSituacionIncidencia" />
						
						<div class="fila">
							<gadir:campoTexto 
								id="motivoTextoIncidencia" 
								name="motivoTextoIncidencia" 
								label="Motivo" 
								labelposition="left" 
								conAyuda = "false"
								styleLabel="width:20%"
								styleText="width:70%"
								cssStyle="width:80%"
								required="false"
								maxlength="200"
							/> 
						</div>
		
					</form>
					
					
					<div class="fila separador">  
                        <input id="boton-ejecutar-incidencia" type="button" value="<s:text name="button.aceptar"/>">
					</div>				
				</div>
			</div>
		</div>
     </div>
</div>



<div id="listaOpcionesDiv" style="
		background-color:#FFFFFF;
		border:1px solid #7B9FB7;
		display:none;
		left:-1px;
		overflow:hidden;
		position:absolute;
		top:300px;
		left:300px;
		width:200px;
		z-index:2;
		padding:5px;
		color:#333333;
		direction:ltr;">
		

	<div id="listaOpcionesTxt" style="clear:both">
		<img style="vertical-align: middle; position: absolute;" alt="Cargando..." src="/etir/image/indicator.gif">
	</div>
	
	<div style="padding-top:2px; text-align: center; border-top: 1px solid gray;">
		<a id="boton-cerrar-listaOpciones" href="#" >
			Cerrar
		</a>
	</div>
</div>
