<%-- 
JSP - Plantilla principal de la aplicación, en ella incluimos las css y definimos la estructura
estandar de Gadir.
--%>
<%@page import="es.dipucadiz.etir.comun.utilidades.ControlTerritorial"%>
<%@ include file="../../taglibs.jsp"%>
<%@ include file="../../parametrizacion.jsp"%>
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
			-&nbsp;<s:property value="%{getText('navegador.titulo')}" />
		</s:if>
	</title>
	
	<s:set name="displayTableVersion" value="'?1.13'"/>
	<s:set name="cssVersion" value="'?1.2'"/>
	<s:set name="buscadorVersion" value="'?1.2'"/>
	
	<link type="text/css" rel="stylesheet" href="<s:url value="/js/ext/resources/css/ext-all.css" includeParams="none" />" />
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/estructura.css" includeParams="none" /><s:property value="#cssVersion"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/estructura-interior.css" includeParams="none" /><s:property value="#cssVersion"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/menu.css" includeParams="none" />"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/general.css" includeParams="none"/>?1.2"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/css_xhtml.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/calendario.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/displayTag.css" includeParams="none"/><s:property value="#displayTableVersion"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/tablas.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/tablas/header-fixed.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" media="all" href="<s:url value="/css/ui-lightness/jquery-ui-1.7.3.custom.css" includeParams="none"/>"/>
	<link type="text/css" rel="stylesheet" href="<s:url value="/struts/xhtml/styles.css" includeParams="none" />"/>
	
	<script type="text/javascript" src="<s:url value="/js/lib/jquery.js" includeParams="none" />"></script>
	<script type="text/javascript" src="<s:url value="/js/lib/jquery-ui-1.7.3.js" includeParams="none" />"></script>
	<script type="text/javascript" src="<s:url value="/js/jstree/jquery.tree.js" includeParams="none" />"></script>
	<script type="text/javascript" src="<s:url value="/js/observaciones.js" includeParams="none" /><s:property value="#displayTableVersion"/>"></script>

<script type="text/javascript">
	// Defered loading of javascript: Add a script element as a child of the body
	var jsDownloads = [];
	function downloadJS(d, t, url) {
		if ($.inArray(url, jsDownloads) == -1) {
			var g = d.createElement(t);
			var s = d.getElementsByTagName(t)[0];
			g.src = url;
			s.parentNode.insertBefore(g, s);
			jsDownloads.push(url);
		}
	}
	function downloadJSAtOnload() {
//		downloadJS(document, 'script', '<s:url value="/js/ext/adapter/ext/ext-base.js" includeParams="none" />', 1);
//		downloadJS(document, 'script', '<s:url value="/js/ext/ext-all.js" includeParams="none" />', 2);
//		downloadJS(document, 'script', '<s:url value="/js/ext/build/locale/ext-lang-es.js" includeParams="none" />', 3);
		downloadJS(document, 'script', '<s:url value="/js/ext/ext-etir-completo.js" includeParams="none" />');
	}
	
	// Check for browser support of event handling capability
	if (window.addEventListener) {
		window.addEventListener("load", downloadJSAtOnload, false);
	} else if (window.attachEvent) {
		window.attachEvent("onload", downloadJSAtOnload);
	} else {
		window.onload = downloadJSAtOnload;
	}
</script>

<style type="text/css">
  h1{font-size: 20px;}
  .caja{color: black;}
  .error{color: red;}
  #errores{border: 1px solid; padding: 5px; margin: 10px; display: none;}
  <s:if test="!@es.dipucadiz.etir.comun.utilidades.DatosSesion@isConAccesibilidad()">
  .wwctrl{display: none;}
  </s:if>
  
  @media print {
  	#contenido-menu {display:none!important;}
  	#contenido-barra-menu {display:none!important;}
  	#botonera-lateral {display:none!important;}
  	#contenido-cuerpo {height:auto!important;width:98%!important;overflow:visible!important;}
  	#botones-linea-info {display:none!important;}
  	#pie {display:none!important;}
  	#copyright {top:0px;bottom:auto!important;border: 1px dashed #e8e8e6;}
  	.botoneraIzquierda {display:none!important;}
  	/*.celdaIconos {display:none!important;}*/
  	.toggleador {display:none!important;}
  	.pestana {border-top:1px solid #7B9FB7!important}
  	body {height:auto!important;overflow:visible!important;}
  	#wrapper {height:auto!important;}
  	#contenido {height:auto!important;}
  	html {height:auto!important;}
  	.scrollContent {overflow:visible!important;}
  	.tabs .current span {font-size:1.5em;line-height:1.5}
  }
</style>	

	<sx:head debug="false" extraLocales="es-es"/>
</head>
<body style="height:100%">

<script type="text/javascript">
<!--

	var documentReady = false;
	var borradoTitulo = "Confirmar borrado";
	var borradoTexto = "Pulse aceptar para confirmar el borrado.";
	var infoTitulo = "eTIR";
	var infoTexto = "";
	
 	
 
	function autotabExt(){
		$('.autotab').keyup(function(event) {
			if (event.keyCode != '9' && event.keyCode != '16') { // Excluir tabulador / mayusculas.
				var $this = $(this);
				if ($this.val().length == $this.attr('maxlength')) {
					var inputs = $this.closest('form').find(':input');
					inputs.eq(inputs.index(this) + 1).focus();
				}
			}
		});
	}
	function formatearMonedas(){	
		$('.numerico').keydown(function(e) {
			return inhibeTeclasParaFormatearNumero(e.keyCode,false);
		});
		/*$('.numerico').keyup(function() {
			var $this = $(this);
			$this.val(formateaNumerico($this.val()));
		});*/		
		$('.numerico').change(function() {
			var $this = $(this); 
			$this.val(formateaNumerico($this.val()));
		});		
		$('.numerico').blur(function() {
			var $this = $(this);
			$this.val(limpiaNumerico($this.val()));
		});		
		$('.numericoMonedaEntera').keydown(function(e) {
			return inhibeTeclasParaFormatearNumeroConPuntos(e.keyCode,false);
		});
		/*$('.numericoMonedaEntera').keyup(function() {
			var $this = $(this);
			$this.val(formateaNumericoConPuntos($this.val()));
		});*/		
		$('.numericoMonedaEntera').change(function() {
			var $this = $(this); 
			$this.val(formateaNumericoConPuntos($this.val()));
		});		
		$('.numericoMonedaEntera').blur(function() {
			var $this = $(this);
			$this.val(limpiaNumericoConPuntos($this.val()));
		});		
		$('.numericoMonedaEnteraDecimal').keydown(function(e) {
			var $this = $(this);	
			return inhibeTeclasParaFormatearNumeroConPuntosYDecimales(e.keyCode,false,$this.val());
		});
		/*$('.numericoMonedaEnteraDecimal').keyup(function() {
			var $this = $(this);
			pos = doGetCaretPosition(document.getElementById($this.attr('id')));	
			$this.val(formateaNumericoConPuntosYDecimales($this.val()));
			doSetCaretPosition(document.getElementById($this.attr('id')), pos);
		});*/		
		$('.numericoMonedaEnteraDecimal').change(function() {
			var $this = $(this);
			$this.val(formateaNumericoConPuntosYDecimales($this.val()));
		});		
		$('.numericoMonedaEnteraDecimal').blur(function() {
			var $this = $(this);
			$this.val(fuerzaDecimales(limpiaNumericoConPuntosYDecimales($this.val()),2));
		});				
 
	}
	
	
	$(document).ready(function(){
		<% if (!ControlTerritorial.isUsuarioExperto()) { %>
		if (window.name != 'ventanagadir' && window.name.substring(0, 14) != 'ventanaLateral' && window.location.href.indexOf('historicoGenerico.action') === -1) {
			window.location = '/etir/';
		}
		<% } %>
		
		$('#boton-cerrar-ayuda').click(function() {
			$('#ayuda_div').toggle();
		});
		
		$('#contenido-barra-menu').click(function() {
			if ($('#contenido-menu').css("display") == 'none'){
				//$('#contenido-cuerpo').width($('#contenido-cuerpo').width() - ($('#contenido-menu').width() - 10));
				$('#contenido-cuerpo').width('72%');
				$('#contenido-menu').toggle();
			}else{
				$('#contenido-menu').toggle();
				//$('#contenido-cuerpo').width($('#contenido-cuerpo').width() + $('#contenido-menu').width() - 10);
				$('#contenido-cuerpo').width('93%');
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

		engancharDialogosConfirmarBorrado();
		$('#dialog').dialog({
			modal: true,
	        closeText: 'cerrar',
	        autoOpen: false,
	        resizable: false,
	        minHeight: 100
		});

		engancharDialogosInformar();
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
			posicionarToggleadores();
		});

		$('#contenido-cuerpo').scroll(function() {
			posicionarToggleadores();
			var lineaInfo = $('#linea-info');
			var umbral = lineaInfo.offset().top + lineaInfo.height();
			$('.toggleador').each(function () {
				var toggleador = $(this);
				var toggleadorImg = toggleador.children('a:first').children('img:first');
				if (toggleador.offset().top <= umbral && !toggleadorImg.attr('src').match(/transparente.png$/)) {
					toggleadorImg.attr('src', '/etir/image/iconos/16x16/transparente.png');
				} else if (toggleador.offset().top > umbral && toggleadorImg.attr('src').match(/transparente.png$/)) {
					var icono = '';
					if ($('#'+this.id.substring(0, this.id.length-6)).is(':visible')) {
						icono='toggle_minimize.png';
					} else {
						icono='toggle-expand.png';
					}
					toggleadorImg.attr('src', '/etir/image/iconos/16x16/' + icono);
				}
			});
		});

		$('.autotab').keyup(function(event) {
			if (event.keyCode != '9' && event.keyCode != '16') { // Excluir tabulador / mayusculas.
				var $this = $(this);
				if ($this.val().length == $this.attr('maxlength')) {
					var inputs = $this.closest('form').find(':input');
					inputs.eq(inputs.index(this) + 1).focus();
				}
			}
		});

		/* Formateo de campos con hora:minutos:segundos */
		/* Formateo de campos numéricos, con decimales y monedas */
		$('.horaMinutosSegundos').keydown(function(e) {
			return inhibeTeclasParaFormatearHHMMSS(e.keyCode,false);
		});
		$('.horaMinutosSegundos').blur(function() {
			var $this = $(this);
			$this.val(limpiaHHMMSS($this.val()));
		});		
		/* Formateo de campos numéricos, con decimales y monedas */
		$('.numerico').keydown(function(e) {
			return inhibeTeclasParaFormatearNumero(e.keyCode,false);
		});
		/*$('.numerico').keyup(function() {
			var $this = $(this);
			$this.val(formateaNumerico($this.val()));
		});*/		
		$('.numerico').change(function() {
			var $this = $(this); 
			$this.val(formateaNumerico($this.val()));
		});		
		$('.numerico').blur(function() {
			var $this = $(this);
			$this.val(limpiaNumerico($this.val()));
		});		
		$('.numericoEjercicio').keydown(function(e) {
			return inhibeTeclasParaFormatearNumero(e.keyCode,false);
		});
		/*$('.numericoEjercicio').keyup(function() {
			var $this = $(this);
			$this.val(formateaNumerico($this.val()));
		});*/		
		$('.numericoEjercicio').change(function() {
			var $this = $(this); 
			$this.val(formateaNumerico($this.val()));
		});		
		$('.numericoEjercicio').blur(function() {
			var $this = $(this);
			$this.val(limpiaNumerico($this.val()));
		});		

		
		$('.numericoConDOSDecimales').keydown(function(e) {
			return inhibeTeclasParaFormatearNumeroConDecimales(e.keyCode,false);
		});
		/*$('.numericoConDOSDecimales').keyup(function() {
			var $this = $(this);
			$this.val(formateaNumericoConPuntos($this.val()));
		});*/		
		$('.numericoConDOSDecimales').change(function() {
			var $this = $(this); 
			$this.val(formateaNumericoConDecimales($this.val()));
		});		
		$('.numericoConDOSDecimales').blur(function() {
			var $this = $(this);
			$this.val(fuerzaDecimales(limpiaNumericoConDecimales($this.val()),2));
		});		
		
		$('.numericoMonedaEntera').keydown(function(e) {
			return inhibeTeclasParaFormatearNumeroConPuntos(e.keyCode,false);
		});
		/*$('.numericoMonedaEntera').keyup(function() {
			var $this = $(this);
			$this.val(formateaNumericoConPuntos($this.val()));
		});*/		
		$('.numericoMonedaEntera').change(function() {
			var $this = $(this); 
			$this.val(formateaNumericoConPuntos($this.val()));
		});		
		$('.numericoMonedaEntera').blur(function() {
			var $this = $(this);
			$this.val(limpiaNumericoConPuntos($this.val()));
		});		
		$('.numericoMonedaEnteraDecimal').keydown(function(e) {
			var $this = $(this);	
			return inhibeTeclasParaFormatearNumeroConPuntosYDecimales(e.keyCode,false,$this.val());
		});
		/*$('.numericoMonedaEnteraDecimal').keyup(function() {
			var $this = $(this);
			pos = doGetCaretPosition(document.getElementById($this.attr('id')));	
			$this.val(formateaNumericoConPuntosYDecimales($this.val()));
			doSetCaretPosition(document.getElementById($this.attr('id')), pos);
		});*/		
		$('.numericoMonedaEnteraDecimal').change(function() {
			var $this = $(this);
			$this.val(formateaNumericoConPuntosYDecimales($this.val()));
		});		
		$('.numericoMonedaEnteraDecimal').blur(function() {
			var $this = $(this);
			$this.val(fuerzaDecimales(limpiaNumericoConPuntosYDecimales($this.val()),2));
		});				
		$('.numericoConPuntosYDecimales').keydown(function(e) {
			var $this = $(this);			
			return inhibeTeclasParaFormatearNumeroConPuntosYDecimales(e.keyCode,false,$this.val());
		});
		/*$('.numericoConPuntosYDecimales').keyup(function() {
			var $this = $(this);
			$this.val(formateaNumericoConPuntosYDecimales($this.val()));
		});*/		
		$('.numericoConPuntosYDecimales').change(function() {
			var $this = $(this);
			$this.val(formateaNumericoConPuntosYDecimales($this.val()));
		});		
		$('.numericoConPuntosYDecimales').blur(function() {
			var $this = $(this);
			$this.val(limpiaNumericoConPuntosYDecimales($this.val()));
		});						
		
		$('.numericoConPuntos').keydown(function(e) {
			return inhibeTeclasParaFormatearNumeroConPuntos(e.keyCode,false);
		});
		/*$('.numericoConPuntos').keyup(function() {
			var $this = $(this);
			$this.val(formateaNumericoConPuntos($this.val()));
		});*/		
		$('.numericoConPuntos').change(function() {
			var $this = $(this);
			$this.val(formateaNumericoConPuntos($this.val()));
		});		
		$('.numericoConPuntos').blur(function() {
			var $this = $(this);
			$this.val(limpiaNumericoConPuntos($this.val()));
		});						

		// Ocultar pestañas y habilitar ocultación de pestañas ocultables.
		$('.ocultable').each(function () {
			var icono;
			var thisId = this.id;
			if (getCookie(thisId + 'Toggleado')=='0') {
				if ($('#'+thisId).is(':visible')) {
					togglearPestana(thisId);
				}
				icono='toggle-expand.png';
			} else {
				icono='toggle_minimize.png';
			}
			$('body').prepend('<div style="position:absolute" id="'+thisId+'Toggle" class="toggleador"><a href="#" id="toggleadorPestana'+thisId+'"><img src="/etir/image/iconos/16x16/'+icono+'" alt="Ocultar/mostrar" title="Ocultar/mostrar" /></a></div>');
			$('#toggleadorPestana'+thisId).click(function() {
				togglearPestana(thisId);
				return false;
			});
		});
		posicionarToggleadores();

		//diferenciacion de ventanas
		var windowName = window.name;
		if (windowName == ''){
			window.name ='tab'+(new Date()).getTime();
		}
		$.ajax({
	        type: "POST",
	        url: "menu!getNumeracionVentanaAjax.action?tabName="+window.name,
	        success: function(datos){
	        	$('#numVentana-linea-info').html('[' + datos + ']');
	      	}
		});
		
		$(window).focus(function() {setTabIdEnCookie(window.name)});
		$(window).mouseover(function() {setTabIdEnCookie(window.name)});
		
		documentReady = true;
	});
	
	function setTabIdEnCookie(tabName){
		setCookie('tabName', tabName);
	}

	function togglearPestana(id) {
		$('#'+id).toggle(0, alturaExtendibles);
		if ($('#'+id).is(':visible')) {
			icono='toggle_minimize.png';
			setCookie(id + 'Toggleado', '1', 4);
		} else {
			icono='toggle-expand.png';
			setCookie(id + 'Toggleado', '0', 4);
		}
		$('#'+id+'Toggle img').attr('src', '/etir/image/iconos/16x16/'+icono);
		posicionarToggleadores();
		return false;
	}

	function posicionarToggleadores() {
		$('.toggleador').each(function () {
			var pestana = $('#'+this.id.substring(0, this.id.length - 6)).prev('ul.tabs');
			var toggleador = $(this);
			toggleador.css('left', pestana.offset().left + pestana.width() - toggleador.width());
			toggleador.css('top',  pestana.offset().top + pestana.height() - toggleador.height()/2 - 2);
		});
	}

	function getCookie(c_name) {
		var i,x,y,ARRcookies=document.cookie.split(";");
		for (i=0;i<ARRcookies.length;i++) {
			x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
			y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
			x=x.replace(/^\s+|\s+$/g,"");
			if (x==c_name) {
		    	return unescape(y);
			}
		}
	}
	function setCookie(c_name, value, exdays) {
		var exdate=new Date();
		exdate.setDate(exdate.getDate() + exdays);
		var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
		document.cookie=c_name + "=" + c_value;
	}

	function quitaCerosNoSignificativos(str) {
		if (str == "" || str == null){
			return str;
		}else	
			if(str.replace(/0/g,"")=="")
				return "0";
			else 
				return str;
	}
	
	function getTag(e) {
		if (!e) var obj = window.event.srcElement;
		else var obj = e.target;
		while (obj.nodeType != 1) {
			obj = obj.parentNode;
		}
		obj = obj.parentNode;
		return obj;
	}
	
	// Para confirmar borrado
	function engancharDialogosConfirmarBorrado() {
		$('.confirmar').click(function(e) {
			if (!$('#dialog').dialog('isOpen')) {
				var tag = getTag(e);
				e.preventDefault();
				if (tag.tagName == 'A') {
					confirmDialog(borradoTitulo, borradoTexto, function() {window.location = tag.href;});
				} else {
					var target = $(e.target);
					confirmDialog(borradoTitulo, borradoTexto, function() {target.click();});
				}
				return false;
			}
		});
	}
	// Para confirmar borrado
	function confirmDialog(titulo, message, targetClick) {
		$('#ui-dialog-title-dialog').text(titulo);
		$('#dialogMensaje').text(message);
		$('#dialog').dialog('option', 'buttons', { 
			'Cancelar': function() {
				$(this).dialog('close');
			},
			'Aceptar': function() {
				$('.ui-dialog-buttonpane').html('<p style="text-align:center"><img src="/etir/image/indicator.gif" alt="Cargando" style="vertical-align:middle" /> Espere por favor</p>');
				$('.ui-dialog-titlebar-close').hide();
				targetClick.apply();
			} 
		});
		$('#dialog').dialog('open');
	}
	function confirmJs() {
		if (arguments.length >= 2) {
			borradoTitulo = arguments[1];
		}
		if (arguments.length >= 1) {
			borradoTexto = arguments[0];
		}
		if (documentReady) {
			return true;
		} else {
			return confirm('Pulse aceptar para confirmar el borrado.');
		}
	}

	
	// Para diálogo informar
	function engancharDialogosInformar() {
		$('.informacion').click(function(e) {
			if (!$('#dialog').dialog('isOpen')) {
				var tag = getTag(e);
				e.preventDefault();
				if (tag.tagName == 'A') {
					infoDialog(infoTitulo, infoTexto);
				} else {
					var target = $(e.target);
					infoDialog(infoTitulo, infoTexto);
				}
				return false;
			}
		});
	}
	// Para diálogo informar
	function infoDialog(titulo, message, action, params) {
		$('#ui-dialog-title-dialog').text(titulo);
		$('#dialogMensaje').text(message);
		$('#dialog').dialog('option', 'buttons', { 
			'Aceptar': function() {
				$(this).dialog('close');
			}
		});
		$('#dialog').dialog('option',"closeOnEscape", false);
		if (action) { // Callback a un action al cerrar/aceptar la ventana
			$('#dialog').bind('dialogclose', function(event) {
		      	$.ajax({
					  url: '/etir/' + action + '.action',
					  data: params
				});
			});
		}
		$('#dialog').dialog('open');
	}
	function infoJs() {
		if (arguments.length >= 2) {
			infoTitulo = arguments[1];
		}
		if (arguments.length >= 1) {
			infoTexto = arguments[0];
		}
		if (documentReady) {
			return false;
		} else {
			return alert('');
		}
	}
	
	// Extender altura de divs "extendibles"
	function alturaExtendibles() {
		var contenidoAltura = $(window).height() - $('#cabecera').height() - $('#linea-info').height() - $('#copyright').height();
		$('#contenido-cuerpo').height(contenidoAltura);
		$('#contenido-menu').height(contenidoAltura);
		$('#contenido-barra-menu').height(contenidoAltura);

		var contenidoCuerpo = $('#contenido-cuerpo');
		var extendible = $('.extendible');
		var alturaTotal = 0;
		contenidoCuerpo.children(':visible').each(function() {
			if ($(this).get(0).tagName != 'SCRIPT' && $(this).attr('class') != 'limpiar') {
				alturaTotal += $(this).outerHeight(true);
			}
		});
		var alturaPadre = contenidoCuerpo.height();
		var alturaHueco = alturaPadre - alturaTotal;
		var alturaNueva = extendible.height() + alturaHueco;
		if (alturaNueva < 135) alturaNueva = 135;
		extendible.height(alturaNueva-8);
		extendible.css('max-height',alturaNueva-8 + 'px');
	}

	function abreHistorico(historicoNombre, historicoCriterios, historicoBusqueda){

 
		
		if(historicoNombre != "cliente.cambios.fiscales"){
			vnt=window.open(
					'/etir/historicoGenerico.action?ventanaBotonLateral=true&historicoNombre=' + escape(historicoNombre) + '&historicoCriterios=' + escape(historicoCriterios) + '&historicoBusqueda=' + escape(historicoBusqueda) , 
					'ventanaBotonLateral' , 'width=1024,height=768,scrollbars=YES, resizable=YES');
		} 
		
	
		
		else{
			vnt=window.open(
					'/etir/historicoGenericoFiscal.action?ventanaBotonLateral=true&historicoNombre=' + escape(historicoNombre) + '&historicoCriterios=' + escape(historicoCriterios) + '&historicoBusqueda=' + escape(historicoBusqueda) , 
					'ventanaBotonLateral' , 'width=1024,height=768,scrollbars=YES');
		}
		vnt.focus(); 
	}

	/* Formateo de campos numéricos */
	keycero = 48;
	keynueve = 57;
	keypunto = 190;
	keycoma = 188; 
	keycoma2 = 44;

	keycero2 = 96;
	keynueve2 = 105;
	
	keyleft = 37;
	keyright = 40;
	keyescape = 27;
	keybackspace = 8;
	keysupr = 46;
	keyctrl = 17;
	keyalt = 18;
	keywindows = 91;
	keywopcion = 93;	
	keytab = 9;
	keymay = 16;
	keybloqmay	= 20;
	keydespl = 145;
	keypausa =	19;
	keyinsert = 45;
	keyinicio = 36;
	keyfin = 35;
	keypagmas = 33;
	keypagmenos = 34;
	keybloqnum = 144;
	keyintro = 13;

	dospuntos = 190;

	function fuerzaDecimales(cad,cuantos) {
		mat = cad.split(",");
		entero = "";
		decimales = "";

		if(cad != "") {
			if(mat.length > 0)
				entero = mat[0]; 
			else
				entero = "0";

			if(mat.length > 1) {
				if(mat[1].length <= cuantos) {
					decimales = mat[1] + repite("0", cuantos - mat[1].length);
				} else
					decimales = mat[1].substring(0, cuantos);
			} else 
				decimales = repite("0", cuantos);
	
			return entero + "," + decimales;

		} else
			return cad;
	
	}
	function repite(cad, veces) {
		result = "";
		for(var i=0; i < veces; i++) {
			result += cad;
		}
		return result;
	}
	function esTeclaEstandarWindows(tecla) {
		//window.status=tecla;
		return ((tecla >= keyleft && tecla <= keyright) || tecla == keyescape || tecla == keybackspace || tecla == keysupr || 
				tecla == keyctrl || tecla == keyalt || tecla == keywindows || tecla == keywopcion || tecla == keytab ||
				tecla == keymay || tecla == keybloqmay || tecla == keydespl || tecla == keypausa || tecla == keyinsert ||
				tecla == keyinicio || tecla == keyfin || tecla == keypagmas || tecla == keypagmenos || tecla == keybloqnum || tecla == keyintro
				);		
	}

	/* Formateo de campos numéricos sin decimales ni delimitadores de miles */
	function inhibeTeclasParaFormatearNumero(tecla,silent) {
		if(esTeclaEstandarWindows(tecla))
			return true;
		else if(!(tecla >= keycero && tecla <= keynueve) && !(tecla >= keycero2 && tecla <= keynueve2)) {
			//if(!silent) dummy=confirmJs("Solo se permiten números sin decimales");
			return false;
		} else 
			return true;
	}
	function limpiaNumerico(input) { 
		result = "";
		if(input != null) {
			for (var i=0; i < input.length; i++) {
				letra = input.charCodeAt(i);
				if(inhibeTeclasParaFormatearNumero(letra,true))
					result += String.fromCharCode(letra);
			};
		}
		return result;
	}
	function inhibeTeclasParaFormatearHHMMSS(tecla,silent) {
		if(esTeclaEstandarWindows(tecla))
			return true;
		else if(!(tecla >= keycero && tecla <= keynueve) && !(tecla >= keycero2 && tecla <= keynueve2) && !(tecla == dospuntos)) {
			//if(!silent) dummy=confirmJs("Solo se permiten números sin decimales");
			return false;
		} else 
			return true;
	}
	function limpiaHHMMSS(input) { 
		result = "";
		if(input != null && input.length==8) {
			hora = input.charAt(0) + input.charAt(1);
			sep1 = input.charAt(2);
			minutos = input.charAt(3) + input.charAt(4); 
			sep2 = input.charAt(5);
			segundos = input.charAt(6) + input.charAt(7);  
			result = (hora >= "00" && hora <= "23" && sep1 == ":" & minutos >= "00" && minutos <= "59" && sep2 == ":" && segundos >= "00" && segundos <= "59" ? input : "");
		}
		return result;
	}
	function formateaNumerico(input) {
		return quitaCerosNoSignificativos(input);
	}	
	/* Formateo de campos numéricos con decimales SIN delimitadores de miles */
	function inhibeTeclasParaFormatearNumeroConDecimales(tecla,silent,input) {
		//	window.status=tecla; 
		if(esTeclaEstandarWindows(tecla))
			return true;
		else if(!(tecla >= keycero && tecla <= keynueve) && !(tecla >= keycero2 && tecla <= keynueve2) && (tecla != keycoma) && (tecla != keycoma2)) {
			//if(!silent) dummy=confirmJs("Solo se permiten números con decimales");
			return false;
		} else {
			if((input != undefined) && (tecla == keycoma || tecla == keycoma2) && (input.indexOf(String.fromCharCode(keycoma)) != -1 || input.indexOf(String.fromCharCode(keycoma2)) != -1))	// Si ya contiene coma no se debe permitir introducir otra
				return false;
			else
				return true;
		};
	}
	function limpiaNumericoConDecimales(input) { 
		result = "";
		if(input != null) {
			for (var i=0; i < input.length; i++) {
				letra = input.charCodeAt(i);
				if(inhibeTeclasParaFormatearNumeroConDecimales(letra,true))
					result += String.fromCharCode(letra);
			};
		}
		return formateaNumericoConDecimales(result);
	}
	function formateaNumericoConDecimales(input) {
		var numcompleto = input.split(",");
		numcompleto[0] = quitaCerosNoSignificativos(numcompleto[0]);		
		var num = numcompleto[0].replace(/\./g,'');
		if(numcompleto[1] != null)
			input = input + "," + numcompleto[1];
		return input;	
	}	
	/* Formateo de campos numéricos con decimales y delimitadores de miles */
	function inhibeTeclasParaFormatearNumeroConPuntosYDecimales(tecla,silent,input) {
		if(esTeclaEstandarWindows(tecla))
			return true;
		else if(!(tecla >= keycero && tecla <= keynueve) && !(tecla >= keycero2 && tecla <= keynueve2) && (tecla != keycoma) && (tecla != keycoma2)) {
			//if(!silent) dummy=confirmJs("Solo se permiten números con decimales");
			return false;
		} else {
			if((input != undefined) && (tecla == keycoma || tecla == keycoma2) && (input.indexOf(String.fromCharCode(keycoma)) != -1 || input.indexOf(String.fromCharCode(keycoma2)) != -1))	// Si ya contiene coma no se debe permitir introducir otra
				return false;
			else
				return true;
		};
	}
	function limpiaNumericoConPuntosYDecimales(input) { 
		result = "";
		if(input != null) {
			for (var i=0; i < input.length; i++) {
				letra = input.charCodeAt(i);
				if(inhibeTeclasParaFormatearNumeroConPuntosYDecimales(letra,true))
					result += String.fromCharCode(letra);
			};
		}
		return formateaNumericoConPuntosYDecimales(result);
	}
	function formateaNumericoConPuntosYDecimales(input) {
		var numcompleto = input.split(",");
		numcompleto[0] = quitaCerosNoSignificativos(numcompleto[0]);
		var num = numcompleto[0].replace(/\./g,'');
		if(!isNaN(num)) {
			num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g,'$1.');
			num = num.split('').reverse().join('').replace(/^[\.]/,'');
			input = num;
		}
		else{
			input = input.replace(/[^\d\.\,]*/g,'');
			//dummy=confirmJs('Solo se permiten números con decimales');
		}
		if(numcompleto[1] != null)
			input = input + "," + numcompleto[1];
		return input;	
	}
	function formateaNumericoConPuntosYDecimalesConPunto(input) {
		return formateaNumericoConPuntosYDecimales(input.replace('\.',','));
	}
	/* Formateo de campos numéricos sin decimales y delimitadores de miles */
	function inhibeTeclasParaFormatearNumeroConPuntos(tecla,silent) {
		if(esTeclaEstandarWindows(tecla))
			return true;
		if(!(tecla >= keycero && tecla <= keynueve) && !(tecla >= keycero2 && tecla <= keynueve2)) {
			//if(!silent) dummy=confirmJs('Solo se permiten números sin decimales');
			return false;
		} else
			return true;
	}
	function limpiaNumericoConPuntos(input) {
		result = "";
		if(input != null) {
			for (var i=0; i < input.length; i++) {
				letra = input.charCodeAt(i);
				if(inhibeTeclasParaFormatearNumeroConPuntos(letra,true))
					result += String.fromCharCode(letra);
			};
		}
		return formateaNumericoConPuntos(result);
	}	
	function formateaNumericoConPuntos(input) {
		var num = input.replace(/\./g,'');
		num[0] = quitaCerosNoSignificativos(num[0]);
		if(!isNaN(num)) {
			num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g,'$1.');
			num = num.split('').reverse().join('').replace(/^[\.]/,'');
			input = num;
		}
		else{
			input = input.replace(/[^\d\.]*/g,'');
			//dummy=confirmJs('Solo se permiten números sin decimales');
		}
		return input;
	}

	function doGetCaretPosition (oField) {
	  var iCaretPos = 0;
	
	  // IE 
	  if (document.selection) { 
	    oField.focus ();
	    var oSel = document.selection.createRange ();
	    oSel.moveStart ('character', -oField.value.length);
	    iCaretPos = oSel.text.length;
	  }
	  // Firefox 
	  else if (oField.selectionStart || oField.selectionStart == '0')
	    iCaretPos = oField.selectionStart;
	
	  return (iCaretPos);
	}
	
	function doSetCaretPosition (oField, iCaretPos) {
	
	  // IE
	  if (document.selection) { 
	    oField.focus ();
	    var oSel = document.selection.createRange ();
	    oSel.moveStart ('character', -oField.value.length);
	    oSel.moveStart ('character', iCaretPos);
	    oSel.moveEnd ('character', iCaretPos + 1);
	    oSel.select ();
	  }
	  // Firefox
	  else if (oField.selectionStart || oField.selectionStart == '0') {
	    oField.selectionStart = iCaretPos;
	    oField.selectionEnd = iCaretPos;
	    oField.focus ();
	  }
	}

	function ocultaTotalizadorDisplayTableDeCapa(pardiv) {
		$(document).ready(function(){	
			$('#'+pardiv).find('.paginacionDisplay').css('display', 'none');
			$('#'+pardiv).css('display', 'inline');
		});					
	}

	function sleep(milliSeconds) { 
		var startTime = new Date().getTime(); 
		while (new Date().getTime() < startTime + milliSeconds); 
	} 
	
	function opcionesComboAccesible(dojoCombo) {
		var formId = dojoCombo.formId;
		var opciones = dojoCombo.optionsListNode;
		var valueName = dojoCombo.name;
		var keyName = dojoCombo.keyName;
		var notifyTopics = dojoCombo.valueNotifyTopics;
		if (opciones.innerHTML == '') {
			return false;
		}
		$('#opcionesAccesiblesFormId').val(formId);
		$('#opcionesAccesiblesKeyName').val(keyName);
		$('#opcionesAccesiblesValueName').val(valueName);
		$('#opcionesAccesiblesNotifyTopics').val(notifyTopics);
		
		var options = $('#opcionesAccesiblesValores');
		options.empty();
		var dataDom = $(opciones.innerHTML);
		for (var i=0; i<dataDom.size(); i++) {
			options.append($("<option />").val(dataDom[i].attributes.resultValue.value).text(dataDom[i].attributes.resultName.value));
		}
		options.append($("<option />").val('').text(''));
		$('#opcionesAccesiblesDialog').show();
		options.focus();
		return true;
	}
	function identificaDojoElm(name, formId) {
		var elm = $('input[dojoAttachPoint][name='+name+']');
		if (elm.length != 1 && formId) {
			var form = $('#'+formId);
			if (form.length == 1) {
				elm = $('#'+formId).find('input[dojoAttachPoint][name='+name+']');
			}
		}
		if (elm.length != 1) {
			alert('El elemento ' + formId + ' ' + name + ' no es accesible, comprueba que tenga los atributos name y formId.');
			return false;
		}
		return elm;
	}
	function opcionesAccesiblesSeleccionar() {
		var selectedKey = $('#opcionesAccesiblesValores').val();
		var selectedValue = $('#opcionesAccesiblesValores option:selected"').text();
		var formId = $('#opcionesAccesiblesFormId').val();
		var keyName = $('#opcionesAccesiblesKeyName').val();
		var valueName = $('#opcionesAccesiblesValueName').val();
		var notifyTopics = $('#opcionesAccesiblesNotifyTopics').val();
		
		var keyNameElm = identificaDojoElm(keyName, formId);
		if (!keyNameElm) return false;
		var valueNameElm = identificaDojoElm(valueName, formId);
		if (!valueNameElm) return false;
		
		keyNameElm.val(selectedKey);
		valueNameElm.val(selectedValue);
		keyNameElm.next('input').val(selectedValue);
		opcionesAccesiblesSalir();
		keyNameElm.next('input').focus();
		if (notifyTopics) {
			var arrTopics = notifyTopics.split(',');
			for (topicIndex in arrTopics) {
				dojo.event.topic.publish(arrTopics[topicIndex]);
			}
		}
	}
	function opcionesAccesiblesSalir() {
		$('#opcionesAccesiblesDialog').hide();
	}
	$(document).ready(function() {
		$('#opcionesAccesiblesValores').keyup(function(e) {
			if (e.keyCode == 13) { opcionesAccesiblesSeleccionar(); } // enter
			if (e.keyCode == 27) { opcionesAccesiblesSalir(); }       // esc
		});
	});
	
	// Ventana "Espere por favor".
	function ventanaEspera(t) {
		if (t.is(":visible")) {
			var text = t.val();
			t.after('<input type="button" value="'+text+'" disabled="disabled" />');
			t.hide();
		}
		
		setTimeout(function() {
			$('html').css('overflow', 'hidden');
			var ph = 60;
			var pw = 300;
			var h = $(window).height()/2 - ph/2;
			var w = $(window).width()/2 - pw/2;
			$('body').append(
				'<div id="ventanaEsperePorFavor" style="display:none;position:absolute;top:0;left:0;width:5000px;height:5000px;background-image:url(/etir/image/whitesemitrans.png);background-size:100% 100%;z-index:99999">'+
				'<table style="height:'+ph+'px;width:'+pw+'px;margin-top:'+h+'px;margin-left:'+w+'px">'+
				'<tr>'+
				'<td style="color:#333333; border:1px solid #7B9FB7; text-align:center; background-color:white">'+
				'<img src="/etir/image/indicator.gif" alt="Cargando" style="vertical-align:middle" /> '+
				'<span style="font-size:16px">Espere mientras cargando...</span>'+
				'</td>'+
				'</tr>'+
				'</table>'+
				'</div>'
			);
			$('#ventanaEsperePorFavor').fadeIn('slow');
			$('input[type=submit]').attr('disabled', 'disabled');
		}, 1000);
	}
	
	$(document).ready(function() {
		$('.botonEspera').click(function(e) {
			ventanaEspera($(this));
			return true;
		});
	});
-->
</script> 

	<%@ include file="gestionNotificaciones.jsp"%>

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
			
			<div id="contenido-cuerpo" <%= ventanaBotonLateral.equals("true")?"style='width:99%'":"" %> >
				<tiles:insertAttribute name="titulopagina" />
				<!-- jsp para mostrar los errores de validación del servidor -->
				<%@ include file="/WEB-INF/jsp/comun/template/mensajes.jsp"%>
				
				<!-- div para mostrar los errores de validación en el cliente -->			
				<div id="errores" class="errores2">
					<div class="titulo">
						<p><s:text name="error.titulo"/></p>
					</div>
				</div>				
				
				<tiles:insertAttribute name="cuerpo" />
				<tiles:insertAttribute name="cuerpo2" />
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

	<!-- jbenitac: cambio para mostrar ayuda -->
	<div id="ayuda_div" class="ayudaCampo" style="top: 0px; left: 0px; display: none">
		<fieldset>
		<legend>Ayuda</legend>
		
			<div style="float:right; padding-right:2px; height:16px">
				<a id="boton-cerrar-ayuda" href="#">
					<img src="/etir/image/iconos/16x16/close.png" alt="Cerrar" title="Cerrar" />
				</a>
			</div>
			
			<div id="ayuda_txt" style="padding-top:10px; padding-left:2px">
				<img id="ayuda_indicator" src="/etir/image/indicator.gif" alt="Cargando..."/>
			</div>
		</fieldset>
	</div>
	<!-- jbenitac: fin cambio para mostrar ayuda -->

	<!-- jronnols: confirmar borrado -->
	<div id="dialog" title="Confirmar borrado">
		<p id="dialogMensaje"></p>
	</div>
	<!-- jronnols: fin confirmar borrado -->

	<!-- jronnols: consultar observaciones -->
	<div id="observacionesDiv" title="Observaciones" style="display: none">
		<div id="listaObservaciones" style="width:100%;overflow:scroll">Observaciones...</div>
		<div id="nuevaObservaciones" style="width:100%">
			<p><label for="observacionesText">Observaciones:</label></p>
			<p>
				<textarea id="observacionesText" style="width:450px; height:70px"></textarea>
				<input type="button" value="Guardar" id="guardarObservaciones" style="width:90px" />
				<input type="hidden" id="observacionesTabla" />
				<input type="hidden" id="observacionesCampos" />
				<input type="hidden" id="observacionesId" />
				<input type="hidden" id="observacionesGrupo" />
				<input type="hidden" id="observacionesIconoId" />
			</p>
		</div>
	</div>
	<!-- jronnols: fin consultar observaciones -->


	<div id="opcionesAccesiblesDialog" style="display:none;position:absolute;background-color:white;padding:10px;border:1px solid black;top:2%;left:2%;height:90%;width:90%">
		<h2>Combo accesible</h2>
		<div>
			<select id="opcionesAccesiblesValores"><option>&nbsp;</option></select>
		</div>
		<div>
			<input type="hidden" id="opcionesAccesiblesFormId" />
			<input type="hidden" id="opcionesAccesiblesKeyName" />
			<input type="hidden" id="opcionesAccesiblesValueName" />
			<input type="hidden" id="opcionesAccesiblesNotifyTopics" />
			<input id="opcionesAccesiblesButtonSeleccionar" type="button" value="Seleccionar valor" onclick="opcionesAccesiblesSeleccionar()" />
			<input id="opcionesAccesiblesButtonSalir" type="button" value="Salir" onclick="opcionesAccesiblesSalir()" />
		</div>
	</div>

	<script type="text/javaScript">
	var datePickerActivo;
	dojo.addOnLoad(function() {
		//Buscar el primer error para enfocarlo.
		$("div[id^='wwerr_']:first").parent().find(":input:visible:first").focus();

		// Volvemos a mostrar los autocompleters.
		$('.wwctrl').show();

		// Posicionamos correctamente los toggleadores.
		posicionarToggleadores();

		// Pasar foco al campo de fecha después de seleccionar en calendario.
		$('.currentMonth').click(function() {
			$('input[name=dojo.'+datePickerActivo+']').focus();
		});

	});
	
	function calendarioClick(calendarioActivo) {
		datePickerActivo = calendarioActivo;
	}
	</script>

</body>
</html>