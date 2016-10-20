<%@ include file="../taglibs.jsp"%>


<script type="text/javaScript">

	$(document).ready(function(){	
		$('#boton-ejecutar-incidencia').click(function() {
			
			var coPpp = $('#coPppIncidencia').val();
			var coIncidencia = $('#coIncidenciaIncidencia').val();
			var coIncidenciaSituacion = $('#coIncidenciaSituacionIncidencia').val();
			
			pulsaOpcionEspecial(coPpp, coIncidenciaSituacion, coIncidencia);
		});
		
		$('#boton-cerrar-listaOpciones').click(function() {
			$('#listaOpcionesDiv').toggle(false);
			$('.backgroundLemonChiffon').removeClass("backgroundLemonChiffon");
		});
		
		$('#boton-cerrar-incidencia').click(function() {
			
			$('#ejecutarIncidenciaDiv').toggle(false);
		});
	});


	function pulsaOpcion(coPpp, coIncidenciaSituacion, coIncidencia){

		if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PPP_ANULAR%>'){
			guardaDatos(coPpp, coIncidenciaSituacion, coIncidencia);
			$('#incidenciaSel').text('Anular PPP');
			activarAnularPPP();
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if(coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PPP_MODIFICAR_PLAZO%>'){
			guardaDatos(coPpp, coIncidenciaSituacion, coIncidencia);
			// Obtención de datos.
			$.ajax({
		        type: "POST",
		        url: "G7B1Seleccion!obtenerDatos.action",
		        data: "coPpp=" + coPpp,
		        success: function(datos){
					var arrDatos = datos.split('|');
					$('#wwctrl_numeroPlazosActuales').text(arrDatos[0]);
					var arrResultados = arrDatos[1].split('##');
					$('#plazosPosibles').find('option').remove();
					var opcionBlanco = "";
					$('#plazosPosibles').append('<option value="'+opcionBlanco+'">'+opcionBlanco+'</option>');
					$.each(arrResultados, function(index, value) {
						if (value != '') {
							$('#plazosPosibles').append('<option value="'+value+'">'+value+'</option>');
						}
					});
		      	}
			});
			
			$('#incidenciaSel').text('Modificar nº plazos PPP');
			activarModificarPlazos();
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PPP_DEPOSITO%>'){
			guardaDatos(coPpp, coIncidenciaSituacion, coIncidencia);

			// Obtención de datos.
			$.ajax({
		        type: "POST",
		        url: "G7B1Alta!obtenerDatosDeposito.action",
		        data: "coPpp=" + coPpp,
		        success: function(datos){
					var arrDatos = datos.split('|');
					$('#wwctrl_imMaximo > div.numericoMonedaEnteraDecimalSalida').text(arrDatos[0]);
					$('#depTitular').val(arrDatos[1]);
					$('#labelDepTitular').text(arrDatos[2]);
					if (arrDatos[3]) {
						$('#depConyuge').val(arrDatos[3]);
						$('#labelDepConyuge').text(arrDatos[4]);
						$('#clienteDepositoConyuge').toggle(true);
					} else {
						$('#clienteDepositoConyuge').toggle(false);
					}
		      	}
			});

			$('#incidenciaSel').text('Crear depósito');
			activarCrearDeposito();
			$('#ejecutarIncidenciaDiv').toggle(true);
		}else{
			$('#coPppSelId').val(coPpp);
			$('#opcionId').val(coIncidenciaSituacion);
			$('#submitBotonOpcion').click();
		}
	}

	function guardaDatos(coPpp, coIncidenciaSituacion, coIncidencia){
		$('#coPppIncidencia').val(coPpp);
		$('#coIncidenciaSituacionIncidencia').val(coIncidenciaSituacion);
		$('#coIncidenciaIncidencia').val(coIncidencia);
	}

	function activarAnularPPP(){
		$('#anularPppFila').toggle(true);
	}
	function activarCrearDeposito(){
		$('#crearDepositoFila').toggle(true);
	}
	function activarModificarPlazos(){
		$('#modificarPlazosFila').toggle(true);
	}
	function ocultarDivs(){
		$('#anularPppFila').toggle(false);
		$('#crearDepositoFila').toggle(false);
		$('#modificarPlazosFila').toggle(false);
	}

	function pulsaOpcionEspecial(coPpp, coIncidenciaSituacion, coIncidencia){

		var error=0;
		
		if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PPP_ANULAR%>'){
			var motivoTexto = $('input[name="motivoTextoIncidencia"]').val();
			if (motivoTexto==undefined || motivoTexto==null || motivoTexto==''){
				alert('Debe introducir el motivo');
				error=1;
			}
		}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PPP_MODIFICAR_PLAZO%>'){
			var plazoActual = $('#wwctrl_numeroPlazosActuales').text();
			var plazoPosible = $('#plazosPosibles').val();
			if (plazoPosible == '') {
				error=1;
				alert('Debe seleccionar un plazo.');
			}else{
				if(plazoActual == plazoPosible){
					error=1;
					alert('El número de plazos no puede ser igual al actual');
				}
			}
			motivoTexto = plazoPosible;
		}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_PPP_DEPOSITO%>'){
			var imMax = $('#wwctrl_imMaximo > div.numericoMonedaEnteraDecimalSalida').text().replace('\.','').replace(',','\.');
			var imDep = $('#imDeposito').val().replace('\.','').replace(',','\.');
			var cliente = $('input:radio[name=clienteDeposito]:checked').val();
			
			if (imMax==undefined || imMax==null || imMax=='' || imMax==0){
				alert('No existe saldo sobrante del ejercicio anterior');
				error=1;
			}else if (imDep==undefined || imDep==null || imDep=='' || eval(imDep)==0){
				alert('Debe introducir el importe del depósito');
				error=1;
			}else if (eval(imDep)>eval(imMax)){
				alert('El importe del depósito supera el importe máximo permitido');
				error=1;
			}else if (cliente==undefined || cliente==null || cliente==''){
				alert('Debe seleccionar contribuyente del depósito');
				error=1;
			}
			motivoTexto = imMax + '|' + imDep + '|' + cliente;
		}

		if (error==0){
			$('#coPppSelId').val(coPpp);
			$('#opcionId').val(coIncidenciaSituacion);
			
			$('#motivoIncidenciaId').val(motivoTexto);
			
			$('#submitBotonOpcion').click();
		}
	}
	
	
	function muestraOpciones(data){
		 if (data!=''){

			var element = data.split("@@"); 
			var texto='';
			var coPpp='';
			texto += '<table style="width:98%">';

			var clickEvents = [];
			if (element.length>0){
				coPpp=element[0];

	    		if (element.length==1){
	    			texto += '<tr><td>';
		    		texto += '<div style="width:98%;" >';
		    		texto += 'No hay acciones disponibles';
		    		texto += '</div>';
		    		texto += '</td></tr>';
				}else{
					for (var i=0;i<element.length;i++){
			    		
				    	if (i==0){
				    		coPpp=element[0];
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
				    		texto += element2[0];
				    		texto += '</a>';
				    		texto += '</td></tr>';
				    		clickEvents[i] = ['pulsaOpcion'+element2[1], coPpp, element2[1], element2[2]];
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
    	<div style="background-color:#E8E8E6;width: 100%; height: 210px; border:1px solid #E16F26; text-align:left; margin:6px; padding:10px;">										
			<div style="background-color:white; height: 200px; padding: 5px">
				<div style="float:right; padding:1px; height:1px">
					<a id="boton-cerrar-incidencia" onclick="ocultarDivs();" href="#">
						<img src="/etir/image/iconos/16x16/close.png" alt="Cerrar" title="Cerrar" />
					</a>
				</div>
				<h1 id="incidenciaSel" style="margin-bottom:0" ></h1>
				<div class="fila separador">
					<hr class="linea_separadora"/>
	           	</div>
				<div>
					<form action="" id = "SeleccionOpcionesIncidenciaPPP">
					
						<s:hidden id="coPppIncidencia" name="coPppIncidencia" />
						<s:hidden id="coIncidenciaIncidencia" name="coIncidenciaIncidencia" />
						<s:hidden id="coIncidenciaSituacionIncidencia" name="coIncidenciaSituacionIncidencia" />
			
						<div class="fila" id="anularPppFila" style="display:none">
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
						<div class="fila" id="modificarPlazosFila" style="display:none">
							<div class="fila">
								<gadir:campoTexto 
									id="numeroPlazosActuales" 
									name="numeroPlazosActuales" 
									label="Nº Plazos actuales" 
									labelposition="left" 
									salida="true"
									sinCajita="true"
									conAyuda = "false"
									styleLabel="width:20%"
									styleText="width:70%"
									cssStyle="width:80%"
									required="false"
								/> 
							</div>
							<div class="fila">
								<gadir:combo id="plazosPosibles"
									name="plazosPosibles"
									label="Plazos posibles"
									labelposition="left"
									styleLabel="width:170px" styleText="width:200px"
									list="{}"
									required="true"
								/>
							</div>
						</div>
						<div class="fila" id="crearDepositoFila" style="display:none">
							<div class="fila">
								<gadir:campoTexto
									styleGroup="width:400px"
									styleLabel="width:100px"
									styleText="width:100px"
									name="imMaximo"
									id="imMaximo"
									label="Importe máximo" 
									labelposition="left" 
									cssClass="numericoMonedaEnteraDecimal"
									conAyuda="false"
									salida="true"
									sinCajita="true"
								/>
							</div>
							<div class="fila">
								<gadir:campoTexto 
									styleGroup="width:400px"
									styleLabel="width:100px"
									styleText="width:100px"
									name="imDeposito" 
									id="imDeposito"
									label="Importe depósito" 
									labelposition="left" 
									cssClass="numericoMonedaEnteraDecimal"
									conAyuda="false"
									cssStyle="width:87%"
							     />
							</div>
							<div class="fila">
								<label>Contribuyente:</label>
								<input name="clienteDeposito" type="radio" value="" id="depTitular" checked="checked" /> <label for="depTitular" id="labelDepTitular">NIF NOMBRE Titular</label>
								<span id="clienteDepositoConyuge">
									<input name="clienteDeposito" type="radio" value="" id="depConyuge" /> <label for="depConyuge" id="labelDepConyuge">NIF NOMBRE Conyuge</label>
								</span>
							</div>
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
