<%@ include file="../taglibs.jsp"%>


<script type="text/javaScript">

	function pulsaOpcion(coCargo, coCargoSubcargo, coIncidenciaSituacion, coIncidencia){

		if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CARGO_ACEPTAR%>'){
			/*if (!confirmJs('¿Está seguro de que desea aceptar el cargo?')){
				return;
			}*/

			activaFechasVoluntaria();
			guardaDatos(coCargo, coCargoSubcargo, coIncidenciaSituacion, coIncidencia);

			try{
			$.ajax({
				url: '/etir/${actionName}!ajaxObtienePeriodoVoluntaria.action',
				data: 'coCargo=' + coCargo + '&coCargoSubcargo=' + coCargoSubcargo,
				success: function(data) {

					try{
						var valores = data.split('|');

						if (valores=='liquidacion'){
							$('#coCargoSelId').val(coCargo);
							$('#coCargoSubcargoSelId').val(coCargoSubcargo);
							$('#opcionId').val(coIncidenciaSituacion);
							$('#submitBotonOpcion').click();
						}else{
							//alert(valores[0] + ' ' + valores[1]);
							if (valores[0].length==10 && valores[1].length==10){
							
								$('input[name="fechaInicioVoluntariaIncidenciaCargo"]').val(valores[0]);
								$('input[name="dojo.fechaInicioVoluntariaIncidenciaCargo"]').val(valores[0].substring(8,10) + '/' + valores[0].substring(5,7) + '/' + valores[0].substring(0,4));
								$('input[name="fechaFinVoluntariaIncidenciaCargo"]').val(valores[1]);
								$('input[name="dojo.fechaFinVoluntariaIncidenciaCargo"]').val(valores[1].substring(8,10) + '/' + valores[1].substring(5,7) + '/' + valores[1].substring(0,4));								
							}
							$('#incidenciaSel').text('Aceptar cargo');
							$('#ejecutarIncidenciaCargoDiv').toggle(true);
						}
					}catch(err){
						$('#incidenciaSel').text('Aceptar cargo');
						$('#ejecutarIncidenciaCargoDiv').toggle(true);						
					}

				}
			
			});
			}catch(error){
				$('#incidenciaSel').text('Aceptar cargo');
				$('#ejecutarIncidenciaCargoDiv').toggle(true);				
			}
	 		
		}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CARGO_IMPRIMIR%>'){

			activaOrden();
			guardaDatos(coCargo, coCargoSubcargo, coIncidenciaSituacion, coIncidencia);
			$('#incidenciaSel').text('Imprimir cargo');
			$('#ejecutarIncidenciaCargoDiv').toggle(true);
			
		}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CARGO_MODIFICAR_PERIODO_VOLUNTARIO%>'){
			activaFechasVoluntaria();
			guardaDatos(coCargo, coCargoSubcargo, coIncidenciaSituacion, coIncidencia);
	
			try{
				$.ajax({
					url: '/etir/${actionName}!ajaxObtienePeriodoVoluntariaCargo.action',
					data: 'coCargo=' + coCargo + '&coCargoSubcargo=' + coCargoSubcargo,
					success: function(data) {
		
						try{
							var valores = data.split('|');
		
							if (valores=='liquidacion'){
								alert('La modificación del periodo voluntario de liquidaciones sólo puede realizarse de forma individual.');
								/*$('#coCargoSelId').val(coCargo);
								$('#coCargoSubcargoSelId').val(coCargoSubcargo);
								$('#opcionId').val(coIncidenciaSituacion);
								$('#submitBotonOpcion').click();*/
							}else{
								//alert(valores[0] + ' ' + valores[1]);
								if (valores[0].length==10 && valores[1].length==10){
								
									$('input[name="fechaInicioVoluntariaIncidenciaCargo"]').val(valores[0]);
									$('input[name="dojo.fechaInicioVoluntariaIncidenciaCargo"]').val(valores[0].substring(8,10) + '/' + valores[0].substring(5,7) + '/' + valores[0].substring(0,4));
									$('#fechaInicioVoluntariaBBDD').val(valores[0]);
									$('input[name="fechaFinVoluntariaIncidenciaCargo"]').val(valores[1]);
									$('input[name="dojo.fechaFinVoluntariaIncidenciaCargo"]').val(valores[1].substring(8,10) + '/' + valores[1].substring(5,7) + '/' + valores[1].substring(0,4));
									$('#fechaFinVoluntariaBBDD').val(valores[1]);
								}
								$('#incidenciaSel').text('Modificar periodo voluntario');
								$('#ejecutarIncidenciaCargoDiv').toggle(true);
							}
						}catch(err){
							$('#incidenciaSel').text('Modificar periodo voluntario');
							$('#ejecutarIncidenciaCargoDiv').toggle(true);						
						}
		
					}
				});
			}catch(error){
				$('#incidenciaSel').text('Modificar periodo voluntario');
				$('#ejecutarIncidenciaCargoDiv').toggle(true);				
			}
		}else{
			$('#coCargoSelId').val(coCargo);
			$('#coCargoSubcargoSelId').val(coCargoSubcargo);
			$('#opcionId').val(coIncidenciaSituacion);
			$('#submitBotonOpcion').click();
		}
	}

	function guardaDatos(coCargo, coCargoSubcargo, coIncidenciaSituacion, coIncidencia){
		$('#coCargoIncidenciaCargo').val(coCargo);
		$('#coCargoSubcargoIncidenciaCargo').val(coCargoSubcargo);
		$('#coIncidenciaSituacionIncidenciaCargo').val(coIncidenciaSituacion);
		$('#coIncidenciaIncidenciaCargo').val(coIncidencia);
	}

	function activaFechasVoluntaria(){
		$('#fechasVoluntariaIncidenciaCargoFila').toggle(true);
	}

	function activaOrden(){
		$('#ordenIncidenciaCargoFila').toggle(true);
	}

	function pulsaOpcionEspecial(coCargo, coCargoSubcargo, coIncidenciaSituacion, coIncidencia){

		var error=0;


		var fechaInicioVoluntaria = $('input[name="fechaInicioVoluntariaIncidenciaCargo"]').val();
		var fechaFinVoluntaria = $('input[name="fechaFinVoluntariaIncidenciaCargo"]').val();
		var fechaInicioVoluntariaBBDD = $('#fechaInicioVoluntariaBBDD').val();
		var fechaFinVoluntariaBBDD = $('#fechaFinVoluntariaBBDD').val();

		var orden = $('input[name="coOrdenIncidenciaCargo"]').val();

		
		if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CARGO_ACEPTAR%>' ||
				coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CARGO_MODIFICAR_PERIODO_VOLUNTARIO%>'){
			if (fechaInicioVoluntaria==undefined || fechaInicioVoluntaria==null || fechaInicioVoluntaria==''
				|| fechaFinVoluntaria==undefined || fechaFinVoluntaria==null || fechaFinVoluntaria==''){
				alert('Debe introducir el periodo de voluntaria');
				error=1;
			}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CARGO_MODIFICAR_PERIODO_VOLUNTARIO%>'){
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
			}
				
		}else if (coIncidencia=='<%=es.dipucadiz.etir.comun.utilidades.IncidenciaConstants.CO_INCIDENCIA_CARGO_IMPRIMIR%>'){
			if (orden==undefined || orden==null || orden==''){
				alert('Debe introducir el orden');
				error=1;
			}
		}
		if (error==0){
			$('#coCargoSelId').val(coCargo);
			$('#coCargoSubcargoSelId').val(coCargoSubcargo);
			$('#opcionId').val(coIncidenciaSituacion);
			
			$('#fechaInicioVoluntariaId').val(fechaInicioVoluntaria);
			$('#fechaFinVoluntariaId').val(fechaFinVoluntaria);

			$('#ordenId').val(orden);
			
			$('#submitBotonOpcion').click();
		}
	}

	function muestraOpciones(data){
		 if (data!=''){

			var element = data.split("@@"); 
			var texto='';
			var coCargo='';
			var coCargoSubcargo='';
			texto += '<table style="width:98%">';

			var clickEvents = [];
			if (element.length>0){
				var aux=element[0].split("|");
				coCargo=aux[0];
				coCargoSubcargo=aux[1];

				
	    		if (element.length==1){
	    			texto += '<tr><td>';
		    		texto += '<div style="width:98%;" >';
		    		texto += 'No hay acciones disponibles';
		    		texto += '</div>';
		    		texto += '</td></tr>';
				}else{
					for (var i=0;i<element.length;i++){
			    		
				    	if (i==0){
				    		//coCargoSubcargo=element[0];
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
				    		clickEvents[i] = ['pulsaOpcion'+element2[1], coCargo, coCargoSubcargo, element2[1], element2[2]];
				    	}
			    	}
				}
			}
	    	
	    	texto += '</table>';
	    	
	    	$('#listaOpcionesTxt').html(texto);

	    	// Activar los eventos de las opciones.
			for(var i=1; i<clickEvents.length; i++) {
				$('#'+clickEvents[i][0]).bind('click', {arr: clickEvents[i]}, function(e) {
					pulsaOpcion(e.data.arr[1], e.data.arr[2], e.data.arr[3], e.data.arr[4]);
					return false;
				});
			}
	    }
	}
	
</script>

<div id="ejecutarIncidenciaCargoDiv" style="display:none">				
    <div style="z-index: 999;background-color:#000000;opacity:0.4; filter: alpha(opacity = 40); height:100%; left:0; position:fixed; top:0; width:100%;"></div>			    
    <div style="position: fixed; z-index: 1000; top: 30%; opacity: 1; left: 25%;width: 50%;">			    	
    	<div style="background-color:#E8E8E6;width: 100%; height: 300px; border:1px solid #E16F26; text-align:left; margin:6px; padding:10px;">										
			<div style="background-color:white; height: 290px; padding: 5px">
				<div style="float:right; padding:1px; height:1px">
					<a id="boton-cerrar-incidencia-cargo" href="#">
						<img src="/etir/image/iconos/16x16/close.png" alt="Cerrar" title="Cerrar" />
					</a>
				</div>
				<h1 id="incidenciaSel" style="margin-bottom:0" ></h1>
				<div class="fila separador">
					<hr class="linea_separadora"/>
		        </div>
				<div>
					<form action="">
					
						<s:hidden id="coCargoIncidenciaCargo" name="coCargoIncidenciaCargo" />
						<s:hidden id="coCargoSubcargoIncidenciaCargo" name="coCargoSubcargoIncidenciaCargo" />
						<s:hidden id="coIncidenciaIncidenciaCargo" name="coIncidenciaIncidenciaCargo" />
						<s:hidden id="coIncidenciaSituacionIncidenciaCargo" name="coIncidenciaSituacionIncidenciaCargo" />
						<s:hidden id="fechaInicioVoluntariaBBDD" name="fechaInicioVoluntariaBBDD" />
						<s:hidden id="fechaFinVoluntariaBBDD" name="fechaFinVoluntariaBBDD" />
					
						<div class="fila" id="fechasVoluntariaIncidenciaCargoFila" style="display:none" >
							<div class="fila">
								<gadir:fecha 
									id="fechaInicioVoluntariaIncidenciaCargo" 
									name="fechaInicioVoluntariaIncidenciaCargo" 
									label="%{getText('fecha.inicio.periodo.voluntario')}" 
									labelposition="left" 
									displayFormat="dd/MM/yyyy" 
									styleLabel="width:30%"
									styleText="width:60%"
									required="true"
								/> 
							</div>
							<div class="fila">
								<gadir:fecha 
									id="fechaFinVoluntariaIncidenciaCargo" 
									name="fechaFinVoluntariaIncidenciaCargo" 
									label="%{getText('fecha.fin.periodo.voluntario')}" 
									labelposition="left" 
									displayFormat="dd/MM/yyyy" 
									styleLabel="width:30%"
									styleText="width:60%"
									required="true"
								/> 
							</div>
						</div>
						
						<div class="fila" id="ordenIncidenciaCargoFila" style="display:none" >
							<gadir:combo1
									id="ordenIncidenciaCargo"
									label="%{getText('orden')}"
									name="ordenIncidenciaCargo"
									keyName="coOrdenIncidenciaCargo"
									list="#{'R':'Razon social','D':'Domicilio fiscal','O':'Referencia tributaria'}"
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
								/>
						</div>
						
					</form>
					
					
					<div class="fila separador">  
                        <input id="boton-ejecutar-incidencia-cargo" type="button" value="<s:text name="button.aceptar"/>">
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
