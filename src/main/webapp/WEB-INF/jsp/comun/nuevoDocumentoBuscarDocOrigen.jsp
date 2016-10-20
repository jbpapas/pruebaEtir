<%@ include file="../taglibs.jsp"%>

<fieldset>
<legend>Buscar documento origen</legend>
<div class="fila">
<%--
	<gadir:combo1
		styleGroup="width:30%"
		styleLabel="width:30%"
		styleText="width:68%"
		cssStyle="width:80%;"
		label="%{getText('municipio')}"
		name="municipioDocOri"
		keyName="coMunicipioDocOri"
		list="listaMunicipiosDocOri"
		listKey="codigoCompleto"
		listValue="codigoDescripcion"
		required="false"
		emptyOption="false"
		formId="NuevoDocumento"
		searchType="substring"
		resultsLimit="-1"
		forceValidOption="true"
		labelposition="left"
		autoComplete="false"
		salida="true"
	/>
--%>
	<gadir:campoTexto
		styleGroup="width:30%"
		styleLabel="width:30%"
		styleText="width:68%"
		cssStyle="width:80%;"
		label="%{getText('municipio')}"
		value="%{listaMunicipiosDocOri.get(0).codigoDescripcion}"
		salida="true"
		sinCajita="true"
		labelposition="left"
	/>
	<s:hidden name="coMunicipioDocOri" value="%{listaMunicipiosDocOri.get(0).codigoCompleto}" />

	<gadir:combo1 
		styleGroup="width:30%"
		styleLabel="width:30%"
		styleText="width:68%"
		cssStyle="width:80%;"
		label="%{getText('concepto')}"
		name="conceptoDocOri"
		keyName="coConceptoDocOri"
		list="listaConceptosDocOri"
		listKey="coConcepto"
		listValue="codigoDescripcion"
		required="false"
		searchType="substring"
		resultsLimit="-1"
		forceValidOption="true"
		formId="NuevoDocumento"
		labelposition="left"
		valueNotifyTopics="/modelo,/version"
		autoComplete="false"
	/>

	<s:url var="cargaModelosDocOriAjax" action="nuevoDocumento" method="cargaModelosDocOriAjax"></s:url>
	<gadir:combo3 
		styleGroup="width:38%"
		styleLabel="width:20%"
		styleText="width:78%"
		cssStyle="width:60%;"
		label="%{getText('modelo')}"
		name="modeloDocOri"
		keyName="coModeloDocOri"
		href="%{#cargaModelosDocOriAjax}" 
		searchType="substring"
		resultsLimit="-1"
		forceValidOption="true"
		listenTopics="/modelo"
		formId="NuevoDocumento"
		indicator="indicator2DocOri"
		labelposition="left"
		valueNotifyTopics="/version"
		conFooter="false"
		autoComplete="false"
	/>

	<s:url var="cargaVersionesDocOriAjax" action="nuevoDocumento" method="cargaVersionesDocOriAjax"></s:url>
	<gadir:combo3 
		cssStyle="width:15%"
		name="versionDocOri"
		keyName="coVersionDocOri"
		href="%{#cargaVersionesDocOriAjax}" 
		required="true"
		searchType="substring"
		resultsLimit="-1"
		forceValidOption="true"
		listenTopics="/version"
		formId="NuevoDocumento"
		indicator="indicator3DocOri"
		labelposition="left"
		autoComplete="false"
	/>
	<gadir:footer/>
</div>

<div class="fila">
	<gadir:campoTexto 
		styleGroup="width:30%"
		styleLabel="width:30%"
		styleText="width:68%"
		cssStyle="width:85%;"
		name="refObjTrib1DocOri" 
		label="Ref. tributaria" 
		labelposition="left" 
		maxlength="30" 
	/>

	<gadir:campoTextoSelec
		name="clienteDocOri"
		value="%{clienteDocOri}"
		codigoValue="%{coClienteDocOri}"
		nifValue="%{nifDocOri}"
		id="texto_clienteDocOri"
		styleGroup="width:69%"
		styleLabel="width:13%"
		styleText="width:86%"
		label="%{getText('cliente')}"
		labelposition="left"
		onclick="mostrarVentanaEfectiva();"
		isCliente="true"
		required="false"
		sufijo="DocOri"
	/>
</div>

<div class="fila botoneraIzquierda">
	<s:submit value="%{getText('button.aceptar')}" method="botonAceptarDocOri" theme="simple" />
	<s:submit value="%{getText('button.anular')}" method="botonAnularDocOri" theme="simple" title="%{getText('button.anular')}" />
</div>

<s:if test="listaDocumentosDocOri == null">
	<label>Seleccione un filtro y pulse aceptar.</label>
</s:if>
<s:elseif test="listaDocumentosDocOri.fullListSize == 0">
	<label>No existen documentos para el filtro introducido.</label>
</s:elseif>
<s:else>
	<div class="fila">
		<jsp:include page="nuevoDocumentoDocOriLista.jsp" />
	</div>
</s:else>

</fieldset>
