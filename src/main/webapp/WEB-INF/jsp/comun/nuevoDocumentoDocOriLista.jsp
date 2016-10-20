<%@ include file="../taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script src="<s:url value="/js/ext/utilPaginacion.js" includeParams="none" /><s:property value="#displayTableVersion"/>"></script>

<div id="listado" class="tableContainer">
<div class="scrollContent extendible" style="overflow-x:scroll; overflow-y:auto">
	<display:table
		name="listaDocumentosDocOri"
		uid="row"
		requestURI="${actionNameTriki}.action"
		excludedParams="action:* method:* botonVolverPila"
		cellspacing="0"
		cellpadding="0"
	>
		<display:column
			title="Documento"
			property="codigoConEspacios"
			sortable="true"
			sortProperty="documento"
			decorator="es.dipucadiz.etir.comun.displaytag.CellHeight17Decorator"
		/>
		<s:if test="coClienteDocOri == null || coClienteDocOri.length() == 0">
			<display:column
				title="Contribuyente"
				property="cliente"
				sortable="false"
				decorator="es.dipucadiz.etir.comun.displaytag.CellOverflowHiddenDecorator"					
			/>
		</s:if>					
		<display:column 
			title="Ref. Tributario 1"
			property="refTrib1"
			sortable="true"
			sortProperty="refObjTrib1"
			decorator="es.dipucadiz.etir.comun.displaytag.CellHeight17Decorator"
		/>
		<display:column 
			title="Domicilio"
			property="domicilio"
			sortable="false"
			decorator="es.dipucadiz.etir.comun.displaytag.CellOverflowHiddenDecorator"
		/>
		
		<s:if test="coMunicipioDocOri == null || coMunicipioDocOri.length() == 0">
			<display:column
				title="Municipio"
				property="municipio"
				sortable="true"
				sortProperty="municipio"
				decorator="es.dipucadiz.etir.comun.displaytag.CellOverflowHiddenDecorator"
			/>
		</s:if>
		
		<s:if test="coConceptoDocOri == null || coConceptoDocOri.length() == 0">
			<display:column
				title="Concepto"
				property="concepto"
				sortable="true"
				sortProperty="concepto"
				decorator="es.dipucadiz.etir.comun.displaytag.CellOverflowHiddenDecorator"
			/>
		</s:if>
		
		<display:column 
			title="Situación"
			property="situacion"
			sortable="true"
			sortProperty="situacion"
			decorator="es.dipucadiz.etir.comun.displaytag.CellOverflowHiddenDecorator"
		/>
		
		<display:column
			class="celdaIconos"
			headerClass="celdaIconos"
			decorator="es.dipucadiz.etir.comun.displaytag.CellHeight17Decorator"
		>
			<s:set var="auxCoModelo">${row.coModelo}</s:set>
			<s:set var="auxCoVersion">${row.coVersion}</s:set>
			<s:set var="auxCoDocumento">${row.coDocumento}</s:set>
			<a href="#" class="seleccionDocOri" title="Seleccione documento origen" id="<s:property value="#auxCoModelo"/><s:property value="#auxCoVersion"/><s:property value="#auxCoDocumento"/>"><img src="image/iconos/16x16/select.png" alt="Seleccionar" /></a>
		</display:column>
	</display:table>
</div>
</div>

<script type="text/javaScript">
alturaExtendibles();

$(document).ready(function() {
	$('.seleccionDocOri').click(function() {
		$('#docOrigenModelo').val(this.id.substring(0, 3));
		$('#docOrigenVersion').val(this.id.substring(3, 4));
		$('#docOrigenDocumento').val(this.id.substring(4));
		$('#buscarDocOrigen').slideToggle();
		alturaExtendibles(300, alturaExtendibles);
	});
});
</script>
