<%-- 
JSP - Plantilla principal de la aplicación, en ella incluimos las css y definimos la estructura
estandar de Gadir.
--%>
<%@ include file="../../taglibs.jsp"%>
<%@ include file="../../parametrizacion.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<tiles:importAttribute />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es" style="height:100%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<s:text name="general.titulo"/><tiles:getAsString name="titulo"/>
		<s:if test="%{getText('navegador.titulo') != null && getText('navegador.titulo') != 'navegador.titulo'}">
			-&nbsp;<s:text name="navegador.titulo"/>
		</s:if>
	</title>
	
	<sx:head debug="false" extraLocales="es-es"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css07/estructura.css" includeParams="none" />"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/menu.css" includeParams="none" />"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css07/css_xhtml.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css07/tablas/tablas.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css07/tablas/displayTag.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css07/tablas/header-fixed.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css07/textos.css" includeParams="none" />"/>
	<link type="text/css" rel="stylesheet" href="<s:url value="/js/ext/resources/css/ext-all.css" includeParams="none" />" />
	
	<script type="text/javascript" src="<s:url value="/js/lib/jquery.js" includeParams="none" />"></script>
	<script type="text/javascript" src="<s:url value="/js/jstree/jquery.tree.js" includeParams="none" />"></script>
	<script type="text/javascript" src="<s:url value="/js/validaciones_jquery.js" includeParams="none" />"></script>
	<script type="text/javascript" src="<s:url value="/js/validaciones.js" includeParams="none" />"></script>		
	<script type="text/javascript" src="<s:url value="/js/enter_key.js" />"></script>
	<script src="<s:url value="/js/ext/adapter/ext/ext-base.js" includeParams="none" />"></script>
	<script src="<s:url value="/js/ext/ext-all.js" includeParams="none" />"></script>
	<script src="<s:url value="/js/ext/build/locale/ext-lang-es.js" includeParams="none" />"></script>
	<script type="text/javascript" src="<s:url value="/js/lib/jquery-ui-1.7.3.js" includeParams="none" />"></script>

<style type="text/css">
  h1{font-size: 20px;}
  .caja{color: black;}
  .error{color: red;}
  #errores{border: 1px solid; padding: 5px; margin: 10px; display: none;}
</style>

</head>
<body style="height:100%">

	<script type="text/javaScript">
	var documentReady = false;
	$(document).ready(function(){		
		
		$('#contenido-barra-menu').click(function() {
			if ($('#contenido-menu').css("display") == 'none'){
				//$('#contenido-cuerpo').width($('#contenido-cuerpo').width() - ($('#contenido-menu').width() - 10));
				$('#contenido-cuerpo').width('74%');
				$('#contenido-menu').toggle();
			}else{
				$('#contenido-menu').toggle();
				//$('#contenido-cuerpo').width($('#contenido-cuerpo').width() + $('#contenido-menu').width() - 10);
				$('#contenido-cuerpo').width('94%');
			}
		});

		// Para mostrar / ocultar mensajes de error (fieldErrors)
		$("div[id^='wwgrp_']").hover(function () {
			var idErr = 'wwerr' + $(this).attr('id').substring(5);
			var obErr = $('#' + idErr + ' > .errorMessage');
			var pos = $(this).offset();
			obErr.css( { "left": (pos.left) + "px", "top": (pos.top - 18) + "px" } );  
  			obErr.fadeIn('fast');
		}, function () {
			$("div[id^='wwerr_']" + " > .errorMessage").hide();
		});
		$("div[id^='wwerr_'] > .errorMessage").click(function () {
			$(this).fadeOut('fast');
		});

		// Para confirmar borrado
		$('.confirmar').click(function(e) {
			if (!$('#dialog').dialog('isOpen')) {
				var target = $(e.target);
				e.preventDefault();
				confirmDialog('Pulse aceptar para confirmar el borrado.',function() {target.click();});
				return false;
			}
		});
		$('#dialog').dialog({
			modal: true,
	        closeText: 'cerrar',
	        autoOpen: false,
	        resizable: false,
	        minHeight: 100
		});

		alturaExtendibles();
		$(window).resize(function() {
			alturaExtendibles();
		});

		documentReady = true;
	});
	
	// Para confirmar borrado
	function confirmDialog(message, targetClick) {
		$('#dialogMensaje').text(message);
		$('#dialog').dialog('option', 'buttons', { 
			'Cancelar': function() {
				$(this).dialog('close');
			},
			'Aceptar': function() {
				targetClick.apply();
			} 
		});
		$('#dialog').dialog('open');
	}
	function confirmJs() {
		if (documentReady) {
			return true;
		} else {
			return confirm('Pulse aceptar para confirmar el borrado.');
		}
	}

	// Extender altura de divs "extendibles"
	function alturaExtendibles() {
		var contenidoCuerpo = $('#contenido-cuerpo');
		var extendible = $('.extendible');
		var alturaTotal = 0;
		contenidoCuerpo.children(':visible').each(function() {
			alturaTotal += $(this).outerHeight(true);
		});
		var alturaPadre = contenidoCuerpo.height();
		var alturaHueco = alturaPadre - alturaTotal;
		var alturaNueva = extendible.height() + alturaHueco;
		if (alturaNueva < 175) alturaNueva = 175;
		extendible.height(alturaNueva);
		extendible.css('max-height',alturaNueva + 'px');
	}
	
	</script> 

	<div id="wrapper" style="height:100%">
		
		<tiles:insertAttribute name="cabecera" />
		
		<tiles:insertAttribute name="info" />
		
		<div id="contenido">
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
			
			
			<tiles:insertAttribute name="menu" />
			
		
			<!-- jbenitac: ocultacion de menu -->
			<div id="contenido-barra-menu" ></div>
			<!-- jbenitac: hasta aqui -->
			
			<%
			}
			%>
			
			<div id="contenido-cuerpo" <%= ventanaBotonLateral.equals("true")?"style='width:94%'":"" %> >
			<tiles:insertAttribute name="titulopagina" />
				<!-- jsp para mostrar los errores de validación del servidor -->
				<%@ include file="/WEB-INF/jsp/comun/template/mensajes.jsp"%>
				
				<!-- div para mostrar los errores de validación en el cliente -->			
				<div id="errores" class="estiloError">
					<div class="titulo">
						<p><s:text name="error.titulo"/></p>
					</div>
				</div>				
				
				<!-- jbenitac: cambio para mostrar ayuda -->
				<div id="ayuda_div" class="ayudaCampo" style="top: 0px; left: 0px; display: none">
					<fieldset>
					<legend>Ayuda</legend>
						<div id="ayuda_txt">
							<img id="ayuda_indicator" src="/etir/image/indicator.gif" alt="Cargando..."/>
						</div>
					</fieldset>
				</div>
				<!-- jbenitac: fin cambio para mostrar ayuda -->
				
				<tiles:insertAttribute name="cuerpo" />
			</div>
			
			<%
			if (!ventanaBotonLateral.equals("true")){ 
			%>
			
			<tiles:insertAttribute name="botonera-lateral"/>

			<%
			}
			%>
		</div>
		
	<tiles:insertAttribute name="pie" />

	</div>


	<script type="text/javaScript">
	dojo.addOnLoad(function() {
		//Buscar el primer error para enfocarlo.
		$("div[id^='wwerr_']:first").parent().find(":input:visible:first").focus();
	});
	</script>

</body>
</html>