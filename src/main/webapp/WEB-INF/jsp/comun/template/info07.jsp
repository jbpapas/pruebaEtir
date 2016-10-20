<%@ include file="../../taglibs.jsp"%>
<div id="linea-info">
	<s:url action="preferencias" id="preferenciasUrl"> </s:url>
	<div id="nombre-linea-info">
	
		<a target="_blank" 
		   href="<s:property value="preferenciasUrl"/>"
		   title="<s:text name="preferencias de usuario"/>"
		   onclick="vnt=window.open('<s:property value="preferenciasUrl"/>' , 'ventanaPreferencias' , 'width=700,height=400,scrollbars=NO'); vnt.focus(); return false;" 
		   >
			<%=es.dipucadiz.etir.comun.utilidades.DatosSesion.getNombre()%>
		</a>
	</div>
	
	<s:if test="%{getText('pagina.titulo')!=null && !getText('pagina.titulo').equals('pagina.titulo')}">
		<div id="titulo-linea-info">&nbsp; <s:text name="pagina.titulo"/></div>
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
			<img src="/etir/image/iconos/16x16/home2.png" title="<s:text name="general.inicio"/>" alt="<s:text name="general.inicio"/>"/>
		</s:a>
		<s:a theme="simple" href="%{#base_inicio}" title="%{getText('general.inicio')}">
			<img src="/etir/image/iconos/16x16/home.png" title="<s:text name="general.inicio"/>" alt="<s:text name="general.inicio"/>"/>
		</s:a>
		<s:a theme="simple" href="%{#base_inicio}" title="%{getText('general.inicio')}">
			<img src="/etir/image/iconos/16x16/home3.png" title="<s:text name="general.inicio"/>" alt="<s:text name="general.inicio"/>"/>
		</s:a>
		
		<img src="/etir/image/iconos/16x16/blank.png" height="16" width="16" alt="Blanco" />
		<%
			}
		%>
		<a href="#" onclick="return false"><img src="/etir/image/iconos/16x16/print.png" alt="Imprimir" id="informeIcono" /></a>
		
		<s:url action="informacion" id="informacionUrl">
			<%--<s:param name="coAcmMenu" value="%{coAcmMenu}" />--%>
			<s:param name="coProceso" value="%{coProcesoActual}" />
		</s:url>

		<a target="_blank" 
		   href="<s:property value="informacionUrl"/>"
		   title="<s:text name="informacion"/>"
		   onclick="vnt=window.open('<s:property value="informacionUrl"/>' , 'ventanaInfo' , 'width=500,height=400,scrollbars=NO'); vnt.focus(); return false;" 
		   >
			<img src="/etir/image/iconos/16x16/info2.png" title="<s:text name="informacion"/>" alt="<s:text name="informacion"/>"/>
		</a>
		
		<%
					if (!ventanaBotonLateral.equals("true")){
				%>
		<a href="/etir/j_acegi_logout"><img src="/etir/image/iconos/16x16/exit.png" alt="Salir" /></a>
		<%
			}
		%>
	</div>
</div>




<div id="informeDiv" style="position: absolute; top: 95px; left: 80%; width: 200px; display: none">
	<fieldset style="background-color: white">
	<legend>Informe</legend>
	<form name="informeDetalle" onsubmit="return pedirInforme()">
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
						<option value="LOp1255" selected="selected">LOp1255</option>
						<option value="iSyR02">iSyR02</option>
						<option value="LexmarkT420">LexmarkT420</option>
						<option value="LexmarkT610">LexmarkT610</option>
						<option value="LexmarkT630">LexmarkT630</option>
					</select>
				</td>
			</tr>
			<tr>
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
			<tr>
				<td></td>
				<td><input type="submit" value="Ok" id="informeSubmit" /></td>
			</tr>
		</table>
	</form>
	</fieldset>
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
	$('#sinInformeDiv').dialog({
		modal: true,
        closeText: 'cerrar',
        autoOpen: false,
        resizable: false,
        minHeight: 70
	});
	$('#informeIcono').click(function() {
		if (document.getElementById('impresion')) {
			if (document.getElementById('informeActuacion')) {
				$('#informeDiv').toggle('fade');
			} else {
				document.getElementById('impresion').submit();
			}
		} else {
			alert('No hay informe.');
		}
	});
	$('input.informeRadio').click(function() {
		$('#informeImprimirTr').toggle(document.getElementById('informeImprmir').checked);
		$('#informeEmailTr').toggle(document.getElementById('informeEmail').checked);
	});
	$('#informeSubmit').click(function() {
		var mensaje = false;
		if (document.getElementById('informeGuardar').checked) {
			if (!confirm('El informe se guarda en la carpeta del usuario.')) return false;
		} else if (document.getElementById('informeImprmir').checked) {
			if (document.getElementById('informeImprimirSelect').value == '') {
				alert('Seleccione impresora.');
				document.getElementById('informeImprimirSelect').focus();
				return false;
			} else {
				if (!confirm('El informe se envía a la impresora ' + document.getElementById('informeImprimirSelect').value + '.')) return false;
			}
		} else if (document.getElementById('informeEmail').checked) {
			if (document.getElementById('informeEmailInput').value == '') {
				alert('Introduzca correo electrónico.');
				document.getElementById('informeEmailInput').focus();
				return false;
			} else { 
				if (!confirm('El informe se envía por correo electrónico a ' + document.getElementById('informeEmailInput').value + '.')) return false;
			}
		}
		if (mensaje) alert(mensaje);
		$('#informeDiv').toggle('fade');
		return true;
	});
});

function pedirInforme() {
	if (document.getElementById('informeAbrir').checked) {
		document.getElementById('informeActuacion').value = document.getElementById('informeAbrir').value;
	} else if (document.getElementById('informeGuardar').checked) {
		document.getElementById('informeActuacion').value = document.getElementById('informeGuardar').value;
	} else if (document.getElementById('informeImprmir').checked) {
		document.getElementById('informeActuacion').value = document.getElementById('informeImprmir').value;
		document.getElementById('informeParametro').value = document.getElementById('informeImprimirSelect').value;
	} else if (document.getElementById('informeEmail').checked) {
		document.getElementById('informeActuacion').value = document.getElementById('informeEmail').value;
		document.getElementById('informeParametro').value = document.getElementById('informeEmailInput').value;
	}
	$('#impresion').submit(); 
	return false;
}
</script>
