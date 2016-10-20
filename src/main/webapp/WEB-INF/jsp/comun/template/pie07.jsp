<%@ include file="../../taglibs.jsp"%>

<%
String ventanaBotonLateral="";
try{
	ventanaBotonLateral=request.getParameter("ventanaBotonLateral");
	if (ventanaBotonLateral==null || ventanaBotonLateral.equals("")){
		ventanaBotonLateral=(String)request.getAttribute("ventanaBotonLateral");
	}
}catch(Exception e){
}
if (ventanaBotonLateral==null)ventanaBotonLateral="false";
%>

<%
if (!ventanaBotonLateral.equals("true")){ 
%>
<script type="text/javaScript">
	$(document).ready(function(){
		$('#boton-notificaciones').click(function() {
			$('#div-notificaciones').toggle('slow');
		});
		$('#boton-cerrar-notificaciones').click(function() {
			$('#div-notificaciones').toggle();
		});
		$('#boton-confirmar-notificaciones').click(function() {
			$('#div-notificaciones').toggle();

			//var n = $("#form-notificaciones input:checked").length;

			var parametros="";
			
			$("#form-notificaciones input:checked").each(function (i) {
				parametros+="confirmadas=" + this.id + "&";
		      });

			parametros+="usuario=<%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getLogin()%>";

			$.ajax({
			        type: "POST",
			        url: "notificaciones!confirmaNotificacionesAjax.action",
			        data: parametros,
			        success: function(datos){
						$('#numero-notificaciones').text(datos);
			      	}
			});

			$("#form-notificaciones input:checked").each(function (i) {
				$("#tr_"+this.id).remove();
		      });
					
			
			
			//$('#span-numero-notificaciones').css('background-color', 'white');
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
						$('#overlaysContainer').empty();
					}	
			});

			$("#form-avisos input:checked").each(function (i) {
				$("#tr_"+this.id).remove();
		    });
    	});
		$('#boton-cerrar-aviso').click(function() {
			$('#overlaysContainer').empty();
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
	});

	
</script>

<s:set name="notificaciones" value="@es.dipucadiz.etir.comun.utilidades.UsuarioNotificacionUtil@getUsuarioNotificacionesTipo1()"/>
<s:set name="notificaciones2" value="@es.dipucadiz.etir.comun.utilidades.UsuarioNotificacionUtil@getUsuarioNotificacionesTipo2()"/>
<%
}
%>

<div id="pie" style="position:absolute;bottom:0;left:0;background-color: white">

	<div id="pie-top"></div>	
	
	<div id="pie-bottom">
	
		<%
		if (!ventanaBotonLateral.equals("true")){ 
		%>
		<div style="
				float:left;
				width:20%;
				
			">
			<s:if test="#notificaciones==null || #notificaciones.size==0">
				No tiene notificaciones.
			</s:if>
			<s:else>
				Notificaciones&nbsp;
				<a id="boton-notificaciones" href="#">
					<span id="span-numero-notificaciones" style="background-color:green; color:white;width:16px;font-weight:bold">
						&nbsp;
						<span id="numero-notificaciones">
							<s:property value="#notificaciones.size"/>
							<s:if test="#notificaciones.size==10">
								+
							</s:if>
						</span>
						&nbsp;
					</span>
				</a>
			</s:else>	
		</div>
		
		
		
		<s:if test="#notificaciones3 != 'bienvenido'">
			<s:set name="notificaciones3" value="@es.dipucadiz.etir.comun.utilidades.UsuarioNotificacionUtil@getUsuarioNotificacionesTipo3T()"/>
		</s:if>
		
	
		<!-- <span id="titulo_pie">
			<s:text name="general.titulo"/>&nbsp;-&nbsp;<s:text name="general.dipu"/>
		</span> -->
		
		<!--
		<img alt="<s:text name="general.ico.xhtml.alt"/>" 
			src="<s:url value="/image/w3c/valid-xhtml10.png"/>"
			title="<s:text name="general.ico.xhtml.alt"/>"/>
	
		<img alt="<s:text name="general.ico.css.alt"/>" 
			src="<s:url value="/image/w3c/valid-css2.png"/>"
			title="<s:text name="general.ico.css.alt"/>"/>
	
		<img alt="<s:text name="general.ico.waiAA.alt"/>" 
			src="<s:url value="/image/w3c/wcag1AA.png"/>"
			title="<s:text name="general.ico.waiAA.alt"/>"/>
		-->
		
		<%
		}
		%>
	</div>
</div>

<%
if (!ventanaBotonLateral.equals("true")){ 
%>
<div id="div-notificaciones" style="
		background-color:#FFFFFF;
		border-color:#7B9FB7 #7B9FB7 #7B9FB7 #7B9FB7;
		border-width:1px 1px 1px 1px;
		display:none;
		left:-1px;
		overflow:hidden;
		position:absolute;
		bottom:28px;
		left:1em;
		width:330px;
		z-index:2;
		padding-left:4px;
		padding-bottom:5px;
		
		border-style:dashed dashed none dashed;
		font-size:70%;
		color:#333333;
		direction:ltr;">
		
	<div style="font-weight:bold;float:left; font-size:110%">
		Notificaciones
	</div>
	<div style="float:right; padding:2px; height:16px">
		<a id="boton-cerrar-notificaciones" href="#">
			<img src="/etir/image/iconos/16x16/close.png" alt="Cerrar" title="Cerrar" />
		</a>
	</div>

	<div>
		<div class="tableContainer">
			<form id="form-notificaciones" action="">
				<table class="display-table" style="width:95%" cellpadding="0" cellspacing="0">
					<tbody>
						<s:iterator status="state" value="#notificaciones" >					
							<s:set name="css" value="%{'even'}"/>
							<s:if test="%{#state.index % 2 == 0}">
								<s:set name="css" value="%{'odd'}"/>
							</s:if>
							<tr id="tr_<s:property value="coAcmUsuarioNotificacion"/>" class="<s:property value="%{#css}"/>">
								<td>
									<s:property value="fhActualizacion"/>						
								</td>
								<td>
									<s:property value="mensaje"/>
									<s:if test="ejecucionDTO.coEjecucion!=null">
										
										<a 
											target="_blank" 
											href="notificaciones!verEjecucion.action?ventanaBotonLateral=true&coEjecucion=<s:property value="ejecucionDTO.coEjecucion"/>" 
											title="Ver"
											onclick="vnt=window.open('notificaciones!verEjecucion.action?ventanaBotonLateral=true&coEjecucion=<s:property value="ejecucionDTO.coEjecucion"/>' , 'ventanaBotonLateral' , 'width=1024,height=768,scrollbars=YES'); vnt.focus(); return false;" 
											>
											<img src="/etir/image/iconos/16x16/view.png" alt="Detalle" title="Detalle" />
										</a>
									</s:if>						
								</td>
								<td style="padding: 0.4em 0.4em 0.4em 0.4em">
									<input id="<s:property value="coAcmUsuarioNotificacion"/>" type="checkbox" checked="true" />								
								</td>
							</tr>			
						</s:iterator>
					</tbody>
				</table>
				
			</form>
		</div>
		
		<div class="fila separador">
		</div>

		<div class="botoneraIzquierda">	
			<input id="boton-confirmar-notificaciones" style="background-image:url('../image/fondo_boton.png');background-repeat:repeat-x;font-size:1em;border:1px solid #6AA0B8;color:#087EAE;text-align:center;padding-right:0.2em;padding-left:0.2em;height:22px;font-weight:bold;" type="button" value="<s:text name="button.confirmar"/>">
		</div>
	</div>
</div>

<s:if test="#notificaciones3 != null && #notificaciones3.size != 0">	
	<div id="overlaysContainer">				
	    <div style="z-index: 900000;background-color:#000000;opacity:0.4; filter: alpha(opacity = 40); height:100%; left:0; position:fixed; top:0; width:100%;"></div>			    
	    <div style="position: fixed; z-index: 900001; top: 30%; opacity: 1; left: 30%;">			    	
	    	<div style="background-color:#E8E8E6;width: 60%; height: 300px; border:1px solid #E16F26; text-align:left; margin:6px; padding:10px;">										
				<div style="background-color:white; height: 290px; padding: 5px">
					<div style="float:right; padding:1px; height:1px">
						<a id="boton-cerrar-aviso" href="#">
							<img src="/etir/image/iconos/16x16/close.png" alt="Cerrar" title="Cerrar" />
						</a>
					</div>
					<h1 style="margin-bottom:0" >Aviso:</h1>
					<div class="fila separador">
						<hr class="linea_separadora"/>
		           	</div>
					
					<div>
						<div class="tableContainer">
							<form id="form-avisos" action="">
								<table class="display-table" style="width:95%" cellpadding="0" cellspacing="0">
									<tbody>
										<s:iterator status="state" value="#notificaciones3">					
											<s:set name="css" value="%{'even'}"/>
											<s:if test="%{#state.index % 2 == 0}">
												<s:set name="css" value="%{'odd'}"/>
											</s:if>
											<tr id="tr_<s:property value="coAcmUsuarioNotificacion"/>" class="<s:property value="%{#css}"/>">
												<td>
													<s:property value="fhActualizacion"/>						
												</td>
												<td>
													<s:property value="mensaje"/>								
												</td>
												<td style="padding: 0.4em 0.4em 0.4em 0.4em">
													<input id="<s:property value="coAcmUsuarioNotificacion"/>" type="checkbox" checked="true" />								
												</td>
											</tr>			
										</s:iterator>
									</tbody>
								</table>
							</form>
						</div>
						<div style="text-align: center; margin-top:10px">    
	                        <input id="boton-confirmar-aviso" type="button" value="<s:text name="button.confirmar"/>">
						</div>					
					</div>
				</div>
			</div>
	     </div>
	</div>
</s:if>

<s:if test="#notificaciones2!=null && #notificaciones2.size>0">	
	<div id="barraInferior" style="position:absolute;bottom:8px;right:0;background-color: white; width:80%;">
		<div style="
			background-color:#FFFFFF;
			border-color:#7B9FB7 #7B9FB7 #7B9FB7 #7B9FB7;
			border-width:1px 1px 1px 1px;
			float:left;
			width:80%;">			
			
			<form id="form-notificaciones2" action="">
				<s:set name="encontrado" value="false"/>
				<MARQUEE scrolldelay="100" >	
					<table>
						<tbody>	
							<tr>
								<td>
									<s:iterator status="state" value="#notificaciones2" >
										<span id="filaMensaje_<s:property value="#state.index"/>">	
											<span id="numMensaje_<s:property value="#state.index - 1"/>">
												<s:if test="#state.index > 0">
													-&nbsp;&nbsp;&nbsp;&nbsp;
												</s:if>
											</span>
											<span id="mensaje_<s:property value="coAcmUsuarioNotificacion"/>">											
												<s:property value="mensaje"/>
												<s:if test="%{tipo.equals(\"Q\")}">
													<s:set name="encontrado" value="true"/>
													<input style="visibility: hidden" id="<s:property value="coAcmUsuarioNotificacion"/>" value="<s:property value="#state.index"/>" type="checkbox" checked="true" />
												</s:if>
												<s:else>
													<input style="visibility: hidden" id="<s:property value="coAcmUsuarioNotificacion"/>" value="<s:property value="#state.index"/>" type="checkbox" />
												</s:else>
											</span>
										</span>																	
									</s:iterator>
								</td>
							</tr>			
						</tbody>
					</table>
				</MARQUEE>
			</form>
		</div>			
		
		<s:if test="encontrado">
		<div style="float:right; padding:1px; height:1px">
			<a id="boton-confirmar-notificaciones2" href="#">
				<img src="/etir/image/iconos/16x16/close.png" alt="Cerrar" title="Cerrar" />
			</a>
		</div>		
		</s:if>
	</div>
</s:if>	
<%
}
%>
