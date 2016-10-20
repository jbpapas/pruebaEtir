<%@page import="es.dipucadiz.etir.comun.listener.SessionCounterListener"%>
<%@ include file="../taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<tiles:importAttribute />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		
	</title>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/estructura.css" includeParams="none" />"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/estructura-interior.css" includeParams="none" />"/>
	
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/general.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/css_xhtml.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/tablas.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/displayTag.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/header-fixed.css" includeParams="none"/>"/>
	
<style type="text/css">
	body {
		overflow-y: auto !important;
		overflow-x: hidden;
	}
	.fondoEtir {
		background-image:url(/etir/image/fondoetir.png);
		border:0!important;
	}
</style>
</head>
<body>

<s:if test="mensaje != null">
	<div style="background-color:white;border:solid black 1px;margin:10px">
		<p style="margin-left:10px"><s:property value="mensaje" /></p>
	</div>
</s:if>

<div style="margin-top:10px; padding-left:10px">
	<display:table 
		name="activeSessions" 
		cellspacing="0" 
		cellpadding="0" 
		style="background-color:white"
		requestURI="/usuarios.action"
		defaultsort="1"
		defaultorder="ascending"
		uid="row"
		sort="page"
		excludedParams="action:* method:* botonVolverPila"
	>
		<display:column property="user" title="Usuario" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator" sortable="true" />
		<display:column property="nombre" title="Nombre" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator" sortable="true" />
		<display:column property="hora" title="Última actividad" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator" sortable="true" />
		<display:column property="programa" title="Último programa" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator" sortable="true" />
		<display:column property="unidadAdministrativa" title="Unidad administrativa" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator" sortable="true" />
		<display:column property="ipServidor" title="Servidor" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator" sortable="true" />
		<s:if test="mostrarDetalleUsuario">
			<display:column property="host" title="Nombre equipo" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator" sortable="true" />
			<display:column property="ip" title="Dirección IP" decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator" sortable="true" />
		</s:if>
		
		<display:column class="fondoEtir" headerClass="fondoEtir">
			<s:set var="auxSessionId">${row.sessionId}</s:set>
			<s:set var="auxIpServidor">${row.ipServidor}</s:set>
			<s:form theme="simple">
				<s:hidden name="sessionIdSel" value="%{#auxSessionId}" />
				<s:hidden name="ipServidor" value="%{#auxIpServidor}" />
				<s:submit type="image" title="Invalidar sesión" src="image/iconos/16x16/cancel.png" method="botonInvalidarSesion" theme="simple" action="%{actionNameTriki}" onclick="return confirm('Pulse aceptar para invalidar la sesión seleccionada.')" />
			</s:form>
		</display:column>
	</display:table>
	<div class="fila">
		<s:if test="activeSessions.size() == 1">
			<div><s:property value="activeSessions.size()" /> usuario conectado.</div>
		</s:if>
		<s:else>
			<div><s:property value="activeSessions.size()" /> usuarios conectados.</div>
		</s:else>
		<div><%=SessionCounterListener.getTotalActiveSessionString()%> en el nodo actual.</div>
	</div>
</div>

<div style="text-align:center">
	<s:submit theme="simple" onclick="javascript:window.close()" value="Cerrar" style="margin-top:10px"/>
</div>

</body>