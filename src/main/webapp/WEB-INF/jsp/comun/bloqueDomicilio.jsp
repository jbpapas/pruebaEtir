<%@ include file="../taglibs.jsp"%>


			<!--  -->
			<script src="<s:url value="/js/ext/buscador/utilidades.js" includeParams="none" /><s:property value="#buscadorVersion"/>"></script>
			<script src="<s:url value="/js/ext/buscador/buscadorCalle.js" includeParams="none" /><s:property value="#buscadorVersion"/>"></script>
			<s:if test="!coProvincia.isEmpty() && !coMunicipio.isEmpty()">
				<script type="text/javaScript">
					coProvinciaBC='<s:property value="coProvincia"/>';
					coMunicipioBC='<s:property value="coMunicipio"/>'.substring(2,5);
				</script>
			</s:if>
			<script type="text/javaScript">
			dojo.event.topic.subscribe("/changedProvincia", function(data, request, widget){
			    if (data!=''){
					coProvinciaBC=data.substring(0,2);
			    }
			});
			dojo.event.topic.subscribe("/changedMunicipio", function(data, request, widget){
			    if (data!=''){
			    	coMunicipioBC=data.substring(2,5);
			    	borraValoresCamposCalle();
			    }
			});
			
			function mostrarVentanaSelectBC()
			{		
				try{
					if (coProvinciaBC==''){
						coProvinciaBC=$('input[name="coProvincia"]').val();
					}
				}catch(error){}
			
				try{
					if (coMunicipioBC==''){
						coMunicipioBC=$('input[name="coMunicipio"]').val();
					}
				}catch(error){}
				
				if (coProvinciaBC == '' || coProvinciaBC.length!=2 || coMunicipioBC == '' || coMunicipioBC.length!=3){
					alert("Debe seleccionar provincia y municipio");
				}else{
					mostrarCargandoSelectBC();
					setTimeout("mostrarVentanaEfectivaBC(); dialogoCargarBC.hide();",500);
				}
				
			}
			var dialogoCargarBC;
			
			function mostrarCargandoSelectBC()
			{
				if (!dialogoCargarBC) {
					formCargandoBC = new Ext.FormPanel(
							{
								split :true,
								labelAlign :'left',
								frame :true,
								buttonAlign :'center',
								bodyStyle :'padding:5px 5px 0',
								width :'100%',
								height :'100%',
								items : [{html:'Cargando...Espere, por favor.'}]
							});
					var panelBC = new Ext.Panel( {
						region :'center',
						split :true,
						width :200,
						collapsible :true,
						margins :'3 3 3 3',
						cmargins :'3 3 3 3'
					});
					panelBC.add(formCargandoBC);
					
					dialogoCargarBC = new Ext.Window( {
						title :'',
						closable :false,
						modal :true,
						width :200,
						height :59,
						plain :true,
						layout :'border',
						items : [ panelBC ]
					});
				}
			
				dialogoCargarBC.show();
			}
			
			function actualizaValoresDeCallejero(coSigla,sigla,coCalle,calle,coUbicacion,ubicacion){
			
				//alert(coSigla+sigla+coCalle+calle+coUbicacion+ubicacion);
				$('input[name="coSigla"]').val(coSigla);
				$('input[name="sigla"]').val(sigla);
				$('input[name="coCalle"]').val(coCalle);
				$('input[name="calle"]').val(calle);
				$('input[name="coUbicacion"]').val(coUbicacion);
				$('input[name="ubicacion"]').val(ubicacion);
			
				$(".dojoComboBox:eq(4)").val(sigla); //caja de sigla
				$(".dojoComboBox:eq(6)").val(ubicacion); //caja de ubicacion
				
				//desactivaCamposCalle();
				//desactivaMunicipioYProvincia();
				/*
				document.getElementById("coSiglaBC").value = coSigla;
				document.getElementById("siglaBC").value = sigla;
				document.getElementById("coCalleBC").value = coSigla;
				document.getElementById("calleBC").value = sigla;
				document.getElementById("ubicacionBC").value = sigla;
				document.getElementById("coUbicacionBC").value = sigla;*/
			}
			
			function desactivaMunicipioYProvincia(){
			
				
				$(".dojoComboBox:eq(0)").attr("disabled", true); //caja de provincia
				$(".dojoComboBox:eq(1)").toggle(false); //flecha de provincia
				$(".dojoComboBox:eq(2)").attr("disabled", true); //caja de municipio
				$(".dojoComboBox:eq(3)").toggle(false); //flecha de municipio
				
			}
			
			function desactivaCamposCalle(){
			
				$('#calleId').attr("disabled", true);
				$(".dojoComboBox:eq(4)").attr("disabled", true); //caja de sigla
				$(".dojoComboBox:eq(5)").toggle(false); //flecha de sigla
				$(".dojoComboBox:eq(6)").attr("disabled", true); //caja de ubicacion
			}

			function borraValoresCamposCalle(){
				//try{$('input[name="coSigla"]').val('');}catch(error){}
				//try{$('input[name="sigla"]').val('');}catch(error){}
				try{$('input[name="coCalle"]').val('');}catch(error){}
				//try{$('input[name="calle"]').val('');}catch(error){}
				try{$('input[name="coUbicacion"]').val('');}catch(error){}
				//try{$('input[name="ubicacion"]').val('');}catch(error){}
			}

			function miraCallejeroMunicipal(data){
				$.ajax({
			        type: "POST",
			        url: "G721NuevoCliente_cargar!getCallejeroMunicipalAjax.action",
			        data: 'coMunicipio='+data.substring(0,5),
			        success: function(datos){
						if (datos=='SI'){
							$('#infoCallejeroMunicipal').text('[Callejero municipal]');
		
							desactivaCamposCalle();
							
						}else{
							$('#infoCallejeroMunicipal').text('');
							//$('#calleId').disable();
							//$('#siglaId').disable();
							$('#calleId').removeAttr("disabled");
							//$('#siglaId').removeAttr("disabled");
							$(".dojoComboBox:eq(4)").removeAttr("disabled");
							$(".dojoComboBox:eq(5)").toggle(true);
							$(".dojoComboBox:eq(6)").removeAttr("disabled");
						}
			      	}
				});  
			}	
			
			$(document).ready(function(){
				if ('<s:property value="coMunicipio"/>' != ''){
					miraCallejeroMunicipal('<s:property value="coMunicipio"/>');
				}
				
				dojo.event.topic.subscribe("/changedMunicipio", function(data, request, widget){
				    if (data!=''){
				    	miraCallejeroMunicipal(data);
				    }
				});
			});
			</script>
			<!--  -->
			
			<div class="fila">
				<gadir:combo1 
						label="%{getText('provincia')}"
						name="provincia"
						keyName="coProvincia"
						list="provincias"
						listKey="coProvincia"
						listValue="codigoDescripcion"
						required="true"
						emptyOption="false"
						searchType="substring"
						resultsLimit="-1"
						forceValidOption="false"
						autoComplete="false"            
						labelposition="left"
						formId="Seleccion"
						valueNotifyTopics="/changedProvincia"
						styleGroup="width:35%"
						styleLabel="width:29%"
						styleText="width:70%"
						cssStyle="width: 80%"
             		/>
		 		
		 			<s:url var="cargarMunicipiosAjax" action="G721NuevoCliente_cargar" method="cargarMunicipiosAjax"></s:url>
					<gadir:combo3 
						label="%{getText('municipio')}"
						name="municipio"
						keyName="coMunicipio"
						href="%{#cargarMunicipiosAjax}"
						required="true"
						searchType="substring"
						resultsLimit="-1"
						forceValidOption="false"
						autoComplete="false"         
						listenTopics="/changedProvincia"   
						labelposition="left"
						formId="Seleccion"
						valueNotifyTopics="/changedMunicipio"
						indicator="ind1"
						styleGroup="width:45%"
						styleLabel="width:25%"
						styleText="width:74%"
						cssStyle="width: 80%"
             		/>
             		
             		<gadir:campoTexto id="txt_cp" name="cp" conAyuda="false" required="true"
									label="%{getText('domicilioFiscal.cp')}" labelposition="left"
									maxlength="5" size="3"  
									styleGroup="width:19%" styleLabel="width:29%" styleText="width:70%"
					/>
		        </div>
		        <div class="fila" style="line-height: 0.5"></div>

                <div class="fila">
                	
                	<input title="<s:property value="%{getText('button.seleccionar.de.callejero')}"/>" style="height: 18px;" onclick="mostrarVentanaSelectBC(); return false;" type="button" value="<s:property value="%{getText('button.seleccionar.de.callejero')}"/>"/>
                	
                	<!--<s:submit onclick="mostrarVentanaSelectBC(); return false;" cssStyle="vertical align:bottom;height:18px" theme="simple" value="%{getText('button.seleccionar.de.callejero')}" title="%{getText('button.seleccionar.de.callejero')}" />-->
					<span id="infoCallejeroMunicipal"></span>
                
		 		</div>
		 		<!-- 
		 		<div class="fila" style="line-height: 0.5"></div>
				 -->
				
		 		<div class="fila">
		 			<table style="width:100%">
		 				<tr>
		 					<td>
			 					<gadir:combo1 
			 						id="siglaId"
									label="%{getText('Sigla')}"
									name="sigla"
									keyName="coSigla"
									list="siglas"
									listKey="key"
									listValue="codigoDescripcion"
									required="true"
									emptyOption="false"
									searchType="substring"
									resultsLimit="-1"
									forceValidOption="false"
									autoComplete="false"
									labelposition="top"
									cssStyle="width:121px"
									valueNotifyTopics="/changedSigla"
			             		/>
		 					</td>
		 					<td>
		 						<gadir:campoTexto 
					        		name="calle" id="calleId"
									label="%{getText('domicilioFiscal.nombreVia')}" labelposition="top" maxlength="100"
									required="true" conAyuda="false" cssStyle="width:90%"
								/>
								<s:hidden name="coCalle"/>
		 					</td>
		 					<td>
								<gadir:campoTexto id="txt_numero" name="numero" conAyuda="false"
									label="%{getText('domicilioFiscal.numero')}" labelposition="top"
									maxlength="5" size="2" />
							</td>
							<td>
								<gadir:campoTexto id="txt_letra" name="letra" conAyuda="false"
									label="%{getText('domicilioFiscal.letra')}" labelposition="top"
									maxlength="3" size="2"/>
							</td>
							<td>
								<gadir:campoTexto id="txt_bloque" name="bloque" conAyuda="false"
									label="%{getText('domicilioFiscal.bloque')}" labelposition="top"
									maxlength="4" size="2"/>
					 		</td>
							<td>
								<gadir:campoTexto id="txt_escalera" name="escalera" conAyuda="false"
									label="%{getText('domicilioFiscal.escalera')}" labelposition="top"
									maxlength="2" size="2"/>
							</td>
							<td>
								<gadir:campoTexto id="txt_planta" name="planta" conAyuda="false"
									label="%{getText('domicilioFiscal.planta')}" labelposition="top"
									maxlength="3" size="2"/>
							</td>
							<td>
								<gadir:campoTexto id="txt_puerta" name="puerta" conAyuda="false"
									label="%{getText('domicilioFiscal.puerta')}" labelposition="top"
									maxlength="3" size="2"/>
							</td>
							<td>
								<gadir:campoTexto id="txt_km" name="km" conAyuda="false"
									label="%{getText('domicilioFiscal.km')}" labelposition="top"
									maxlength="11" size="5"/>
							</td>
						</tr>
					</table>
		 		</div>
		 	<div class="fila">    
		 		<s:url var="cargarUbicacionesAjax" action="G721NuevoCliente_cargar" method="cargarUbicacionesAjax"></s:url>
					<gadir:combo3
					   label="%{getText('Ubicacion')}"
                       name="ubicacion"
                       keyName="coUbicacion"
                       href="%{#cargarUbicacionesAjax}" 
                       required="false"
                       searchType="substring"
                       resultsLimit="-1"
                       forceValidOption="true"
                       autoComplete="false"
                       listenTopics="/changedMunicipio"
                       formId="Seleccion"
                       indicator="indicator1"
                       showDownArrow="false"                 
                       labelposition="left"
                       styleGroup="width:45%"
                       styleLabel="width:20%"
                       styleText="width:78%"
                       cssStyle="width: 80%"
                	/>
                <!-- 
				<gadir:campoTexto 
		        		name="cp" disabled="true"
						label="%{getText('domicilioFiscal.codigoPostal')}" labelposition="left" maxlength="5"
						required="false" 
                       styleGroup="width:45%"
                       styleLabel="width:20%"
                       styleText="width:78%"
					/>
				-->
		 	</div>