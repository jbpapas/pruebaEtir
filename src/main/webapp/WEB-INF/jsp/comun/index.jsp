<%-- 
JSP - Plantilla principal de la aplicación, en ella incluimos las css y definimos la estructura
estandar de Gadir.
--%>
<%@ include file="../taglibs.jsp"%>
<%@ include file="../parametrizacion.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<tiles:importAttribute />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<s:text name="general.titulo"/><tiles:getAsString name="titulo"/>
		<s:if test="%{getText('navegador.titulo') != null && getText('navegador.titulo') != 'navegador.titulo'}">
			-&nbsp;<s:text name="navegador.titulo"/>
		</s:if>
	</title>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/estructura.css" includeParams="none" />"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/estructura-interior.css" includeParams="none" />"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/menu.css" includeParams="none" />"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/general.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/css_xhtml.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/tablas.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/displayTag.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/header-fixed.css" includeParams="none"/>"/>
</head>
<body>
<div id="wrapper">
<tiles:insertAttribute name="cabecera" />
<tiles:insertAttribute name="info" />
<div id="contenido">
	<div id="contenido-menu">
		<tiles:insertAttribute name="menu" />
	</div>
	<div id="contenido-cuerpo">
		<tiles:insertAttribute name="cuerpo" />
	</div>
</div>
<%--
<tiles:insertAttribute name="pie" />
--%>
</div>
<tiles:insertAttribute name="botonera-lateral"/>
</body>
</html>