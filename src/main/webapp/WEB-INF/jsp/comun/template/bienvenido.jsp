<%@ include file="../../taglibs.jsp"%>
<%@ include file="../../parametrizacion.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javaScript">
	$(document).ready(function(){	
		preparaEventos(true);
		//programacionActualizacionNumNotificaciones(true); Se deshabilita la actualización de notificaciones por rendimiento

		$('#contenido-barra-menu').click();
		var windowName = window.name;
		if (windowName == ''){
			window.name ='tab'+(new Date()).getTime();
		}
		
	});
</script>

<div id="bienvenido-globo">
	<div style="padding-top:34px;padding-left:37px">
		<img src="image/textobienvenido.png" alt="Bienvenidos a eTir. Todos los tributos, todos los ingresos y toda la recaudación en una sola herramienta." />
	</div>
</div>

<s:set name="notificaciones3L" value="@es.dipucadiz.etir.comun.utilidades.UsuarioNotificacionUtil@getUsuarioNotificacionesTipo3L()"/>
<s:set name="notificaciones3T" value="@es.dipucadiz.etir.comun.utilidades.UsuarioNotificacionUtil@getUsuarioNotificacionesTipo3T()"/>
<s:set name="notificaciones3" value="'bienvenido'" />

<%--
<s:set name="urlEncuestaAux" value="urlEncuesta" />
<s:if test="!\"YA\".equals(#urlEncuestaAux)">
	<%@ include file="encuesta.jsp" %>
</s:if>
<s:else>
--%>
<s:if test="(#notificaciones3L!=null && #notificaciones3L.size>0) || (#notificaciones3T!=null && #notificaciones3T.size>0)">
	<div id="overlaysContainer">				
		<%@ include file="notificaciones3L.jsp" %>
	</div>
</s:if>
<%--
</s:else>
--%>
