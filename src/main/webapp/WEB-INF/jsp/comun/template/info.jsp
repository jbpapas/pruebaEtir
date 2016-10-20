<%@page import="es.dipucadiz.etir.comun.utilidades.ControlTerritorial"%>
<%@page import="es.dipucadiz.etir.comun.listener.SessionCounterListener"%>
<%@ include file="../../taglibs.jsp"%>

<div id="linea-info" >
	<s:url action="preferencias" id="preferenciasUrl"> </s:url>
	<div id="nombre-linea-info">
	
		<a target="_blank" 
			href="<s:property value="preferenciasUrl"/>"
			title="<s:text name="preferencias.de.usuario"/>"
			onclick="vnt=window.open('<s:property value="preferenciasUrl"/>' , 'ventanaPreferencias' , 'width=700,height=400,scrollbars=NO'); vnt.focus(); return false;" 
		>
			<%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getNombre() %>
			
			&nbsp;
			<span id="numVentana-linea-info" style="font-weight:bold; vertical-align:top"><!-- se inyecta luego --></span>
		</a>
	</div>
	
	<s:if test="%{getText('pagina.titulo')!=null && !getText('pagina.titulo').equals('pagina.titulo')}">
		<div id="titulo-linea-info">&nbsp; <s:property value="paginaTitulo"/></div>
	</s:if>

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

	<div id="botones-linea-info">
		<%
		if (!ventanaBotonLateral.equals("true")){ 
		%>
		<s:url action="bienvenido" namespace="/" id="base_inicio" includeParams="none"/>
		<s:a theme="simple" href="%{#base_inicio}" title="%{getText('general.inicio')}">
			<img src="/etir/image/iconos/16x16/home.png" title="<s:text name="general.inicio"/>" alt="<s:text name="general.inicio"/>"/>
		</s:a>
		<%
		}
		%>
		
		<a href="javascript:informeIconoClick()"><img src="/etir/image/iconos/16x16/print.png" alt="Imprimir" title="Imprimir" id="informeIcono" /></a>
		
		<%
		if (!ventanaBotonLateral.equals("true") && ControlTerritorial.isUsuarioExperto()){
		%>
		<s:url action="usuarios" id="usuariosUrl"></s:url>
		<a target="_blank" 
		   href="<s:property value="usuariosUrl"/>"
		   onclick="vnt=window.open('<s:property value="usuariosUrl"/>' , 'ventanaUsu' , 'width=800,height=600,scrollbars=YES,resizable=YES'); vnt.focus(); return false;" 
		   >
			<img src="/etir/image/iconos/16x16/users.png" alt="Usuarios" id="usuariosIcono" title="Usuarios conectados<%--SessionCounterListener.getTotalActiveSessionString()--%>" />
		</a>
		<%
		}
		%>

		<%
		if (!ventanaBotonLateral.equals("true")){ 
		%>
		
		<s:url action="informacion" id="informacionUrl"></s:url>
		<a target="_blank" 
		   href="<s:property value="informacionUrl"/>"
		   title="<s:text name="informacion"/>"
		   onclick="vnt=window.open('<s:property value="informacionUrl"/>' , 'ventanaInfo' , 'width=500,height=500,scrollbars=NO'); vnt.focus(); return false;" 
		   >
			<img src="/etir/image/iconos/16x16/info2.png" title="<s:text name="informacion"/>" alt="<s:text name="informacion"/>"/>
		</a>
		
		<a href="/etir/logout.action"><img src="/etir/image/iconos/16x16/exit.png" alt="Salir" title="Salir" /></a>
		<%
		}
		%>
	</div>
	
</div>
	


<div id="informeDiv" title="Informe" style="display: none">
	<p id="informeDivMensaje"></p>
	<form id="informeDetalle" action="">
		<table>
			<tr>
				<td>
					<input type="radio" name="informeActuacionFormulario" id="informeAbrir" class="informeRadio" value="2" checked="checked" />
				</td>
				<td>
					<label for="informeAbrir">Abrir</label>
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="informeActuacionFormulario" id="informeGuardar" class="informeRadio" value="1" />
				</td>
				<td>
					<label for="informeGuardar">Guardar en carpeta</label>
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="informeActuacionFormulario" id="informeImprmir" class="informeRadio" value="3" />
				</td>
				<td>
					<label for="informeImprmir">Enviar a impresora</label>
				</td>
			</tr>
			<tr id="informeImprimirTr" style="display: none">
				<td></td>
				<td>
					<select id="informeImprimirSelect">
						<option value="<%=es.dipucadiz.etir.comun.utilidades.TablaGt.getValor("TABIMPR", es.dipucadiz.etir.comun.utilidades.DatosSesion.getImpresora(), "CODIGO")%>" selected="selected"><%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getImpresora()%></option>
					</select>
				</td>
			</tr>
<%-- Se elimina la opción por ley de protección de datos.
			<tr style="display:none">
				<td>
					<input type="radio" name="informeActuacionFormulario" id="informeEmail" class="informeRadio" value="4" />
				</td>
				<td>
					<label for="informeEmail">Correo electrónico</label>
				</td>
			</tr>
			<tr id="informeEmailTr" style="display: none">
				<td></td>
				<td>
					<input type="text" value="<%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getEmail()%>" id="informeEmailInput" size="20" maxlength="50" />
				</td>
			</tr>
--%>
		</table>
	</form>
</div>

<div id="sinInformeDiv" title="Informe" style="display: none">
	<p id="sinInformeDivMensaje">No hay informe.</p>
</div>


<script type="text/javascript">
$(document).ready(function() {
	$('#informeDiv').dialog({
		modal: true,
        closeText: 'cerrar',
        autoOpen: false,
        resizable: false,
        buttons: { 
			'Cancelar': function() {
				$(this).dialog('close');
			},
			'Aceptar': function() {
				if (informeValida()) {
					pedirInforme();
					$('#informeDiv :button').attr('disabled', true);
					$(this).dialog('close');
				}
			}
		}
	});
	$('#informeDiv').keypress(function(e) { // Intro para pedir el informe.
	    if (e.keyCode == 13) {
			pedirInforme();
			$('#informeDiv :button').attr('disabled', true);
			$(this).dialog('close');
	    }
	});
	$('#sinInformeDiv').dialog({
		modal: true,
        closeText: 'cerrar',
        autoOpen: false,
        resizable: false,
        minHeight: 70
	});
	
	$('input.informeRadio').click(function() {
		$('#informeImprimirTr').toggle(document.getElementById('informeImprmir').checked);
/*		$('#informeEmailTr').toggle(document.getElementById('informeEmail').checked);*/
	});
});

function informeValida() {
	var mensaje = false;
	var valido = true;
	if (document.getElementById('informeGuardar').checked) {
		mensaje = 'El informe se guarda en la carpeta del usuario.';
	} else if (document.getElementById('informeImprmir').checked) {
		if (document.getElementById('informeImprimirSelect').value == '') {
			mensaje = 'Seleccione impresora.';
			document.getElementById('informeImprimirSelect').focus();
			valido = false;
		} else {
			mensaje = 'El informe se envía a la impresora ' + document.getElementById('informeImprimirSelect').value + '.';
		}
/*	} else if (document.getElementById('informeEmail').checked) {
		if (document.getElementById('informeEmailInput').value == '') {
			mensaje = 'Introduzca correo electrónico.';
			document.getElementById('informeEmailInput').focus();
			valido = false;
		} else { 
			mensaje = 'El informe se envía por correo electrónico a ' + document.getElementById('informeEmailInput').value + '.';
		}*/
	}
	
	if (mensaje) $('#informeDivMensaje').text(mensaje).addClass('ui-state-highlight');
	setTimeout(function() {$('#informeDivMensaje').removeClass('ui-state-highlight', 1500); }, 500);
	return valido;
}

function pedirInforme() {
	if (document.getElementById('informeAbrir').checked) {
		document.getElementById('informeActuacion').value = document.getElementById('informeAbrir').value;
	} else if (document.getElementById('informeGuardar').checked) {
		document.getElementById('informeActuacion').value = document.getElementById('informeGuardar').value;
	} else if (document.getElementById('informeImprmir').checked) {
		document.getElementById('informeActuacion').value = document.getElementById('informeImprmir').value;
		document.getElementById('informeParametro').value = document.getElementById('informeImprimirSelect').value;
/*	} else if (document.getElementById('informeEmail').checked) {
		document.getElementById('informeActuacion').value = document.getElementById('informeEmail').value;
		document.getElementById('informeParametro').value = document.getElementById('informeEmailInput').value;*/
	}
	$('#impresion').submit();
}

//$('#informeIcono').click(function() {
function informeIconoClick() {
	$('#informeDivMensaje').text('Seleccione una opción.');
	if (document.getElementById('impresion')) {
		if (document.getElementById('informeActuacion')) {
			$('#informeDiv').dialog('open');
		} else {
			document.getElementById('impresion').submit();
		}
	} else {
		//window.print();
		$('#sinInformeDiv').dialog('open');
	}
//});
}

</script>
