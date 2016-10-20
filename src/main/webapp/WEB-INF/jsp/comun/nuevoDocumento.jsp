<%@ include file="../taglibs.jsp"%>

<script src="<s:url value="/js/ext/buscador/utilidades.js" includeParams="none" /><s:property value="#buscadorVersion"/>"></script>
<script src="<s:url value="/js/ext/buscador/buscadorCliente.js" includeParams="none" /><s:property value="#buscadorVersion"/>"></script>

<gadir:pestanas  
		nombre0="pestana.nuevo.documento"
		metodo0="sinMetodo"
	/>

<s:form validate="true" id="NuevoDocumento">

<fieldset class="pestana">

	<s:hidden name="pestana"/>
	<s:hidden name="origen"/>
	<s:hidden name="segundoFormulario" value="%{primerosDatos}" />

	<div class="fila">
	<table width="100%"><tr>
	<td width="30%">
	    <gadir:combo1
	    	styleGroup="width:100%"
	    	styleLabel="width:30%"
	    	styleText="width:68%"
	    	cssStyle="width:80%;"
		   	label="%{getText('municipio')}"
		   	name="municipio"
            keyName="coMunicipio"
            list="listaMunicipios"
            listKey="codigoCompleto"
            listValue="codigoDescripcion"
            required="true"
            emptyOption="false"
            formId="NuevoDocumento"
            searchType="substring"
            resultsLimit="-1"
            forceValidOption="true"
            autoComplete="false"
            labelposition="left"
            valueNotifyTopics="/concepto"
            salida="primerosDatos"
        />
	</td>
	<td width="30%">
 		<s:url var="cargaConceptosAjax" action="nuevoDocumento" method="cargaConceptosAjax"></s:url>
    	<gadir:combo3 
    	  styleGroup="width:100%"
	      styleLabel="width:30%"
	      styleText="width:68%"
	      cssStyle="width:80%;"
    	  label="%{getText('concepto')}"
    	  name="concepto"
          keyName="coConcepto"
          href="%{#cargaConceptosAjax}" 
          indicator="indicator1"
          required="true"
          searchType="substring"
          resultsLimit="-1"
          forceValidOption="true"
          autoComplete="false"
          formId="NuevoDocumento"
          labelposition="left"
          listenTopics="/concepto"
          valueNotifyTopics="/modelo,/version"
          salida="primerosDatos"
 		/>
	</td>
	<td width="38%">
       <s:url var="cargaModelosAjax" action="nuevoDocumento" method="cargaModelosAjax"></s:url>
       <gadir:combo3 
          styleGroup="width:100%"
	      styleLabel="width:30%"
	      styleText="width:68%"
	      cssStyle="width:60%;"
          label="%{getText('modelo')}"
          name="modelo"
          keyName="coModelo"
          href="%{#cargaModelosAjax}" 
          searchType="substring"
          resultsLimit="-1"
          forceValidOption="true"
          autoComplete="false"
          listenTopics="/modelo"
          formId="NuevoDocumento"
          indicator="indicator2"
          labelposition="left"
          valueNotifyTopics="/version"
          conFooter="false"
          salida="primerosDatos"
          required="true"
 		/>
 		<s:url var="cargaVersionesAjax" action="nuevoDocumento" method="cargaVersionesAjax"></s:url>
 		<gadir:combo3 
 		  cssStyle="width:15%;"
          name="version"
          keyName="coVersion"
          href="%{#cargaVersionesAjax}" 
          required="true"
          searchType="substring"
          resultsLimit="-1"
          forceValidOption="true"
          autoComplete="false"
          listenTopics="/version"
          formId="NuevoDocumento"
          indicator="indicator3"
          labelposition="left"
          salida="primerosDatos"
 		/>
 		<gadir:footer/>
	</td>
	</tr></table>
	</div>

	<s:if test="!primerosDatos">
		<div class="fila separador">
			<hr class="linea_separadora"/>
		</div>
		<div class="botoneraIzquierda">
			<s:hidden name="coCliente"/>
			<s:submit value="%{getText('button.aceptar')}" method="botonPrimerosDatos" theme="simple" />
			<s:reset value="%{getText('button.anular')}" theme="simple" />
		</div>
	</s:if>
	<s:else>
		
		<s:hidden name="coModelo"/>
		<s:hidden name="coVersion"/>
		<s:hidden name="coMunicipio"/>
		<s:hidden name="coConcepto"/>
		<s:hidden name="modelo"/>
		<s:hidden name="version"/>
		<s:hidden name="municipio"/>
		<s:hidden name="concepto"/>
	
		<div class="fila separador" style="width:98%;color:gray;border-bottom:1px solid gray">Datos del documento:</div>
		<div class="fila" id="codigoDocOrigen">
		 	<gadir:campoTexto 
		      styleLabel="width:15%"
		      styleText="width:83%"
				name="docOrigenModelo" 
				id="docOrigenModelo" 
				label="%{getText('documento.origen')}" 
		     	labelposition="left" 
		     	maxlength="3" 
		     	size="3"
		     	conFooter="false"
		     	conAyuda="false"
		     	cssClass="autotab"
		     />
		     <gadir:campoTexto 
	    	  	theme="simple"
				name="docOrigenVersion" 
				id="docOrigenVersion" 
		     	labelposition="left" 
		     	maxlength="1" 
		     	size="1"
		     	conAyuda="false"
		     	cssClass="autotab"
		    />
			<gadir:campoTexto 
	    	    theme="simple"
				name="docOrigenDocumento" 
				id="docOrigenDocumento" 
		     	labelposition="left" 
		     	maxlength="9" 
		     	size="9"
		     	conAyuda="false"
		     />
		     <input onclick="$('#buscarDocOrigen').slideToggle(300, alturaExtendibles)" type="button" value="Buscar" title="Buscar documento origen" style="background-image:  url('/etir/img/fdo_otros_botones.gif');border:0px;padding: 0px;height:20px;margin-right:5px;cursor: pointer;border: 1px solid #6ba0b8;" />
		     <gadir:footer/>
		</div>
		<div class="fila" <s:if test="!buscadorDocOriAbierto">style="display:none"</s:if> id="buscarDocOrigen">
			<%@ include file="nuevoDocumentoBuscarDocOrigen.jsp"%>
		</div>
		<div class="fila">
			<gadir:campoTexto 
				styleLabel="width:15%"
		      	styleText="width:83%"
				name="refCat" 
				label="%{getText('ref.obj.trib')}" 
		     	labelposition="left" 
		     	maxlength="20" 
		     />
		</div>
		<div class="fila">
			<script type="text/javascript">
				defectoAsociado = false;
			</script>
			<gadir:campoTextoSelec
				name="cliente"
				value="%{cliente}"
				codigoValue="%{coCliente}"
				nifValue="%{nif}"
				id="texto_cliente"
			    styleLabel="width:15%"
			    styleText="width:83%"
				label="%{getText('cliente')}"
				labelposition="left"
				onclick="mostrarVentanaEfectiva();"
				isCliente="true"
				required="true"
				salida="%{@es.dipucadiz.etir.comun.action.NuevoDocumentoAction@CLIENTES_CENSO.equals(origen) || @es.dipucadiz.etir.comun.action.NuevoDocumentoAction@CLIENTES_LIQUIDACION.equals(origen)}"
			/>
		</div>
		<s:if test="tipo.equals(\"L\") && subtipo.equals(\"N\")" >
		<div class="fila">
			<gadir:campoTexto 
				styleLabel="width:15%"
			    styleText="width:83%"
				name="ejercicioIni" 
				label="%{getText('ejercicio.ini')}" 
		     	labelposition="left" 
		     	maxlength="4" 
		     	size="4"
		     />
		</div>
		<div class="fila">
		     <gadir:campoTexto 
				styleLabel="width:15%"
			    styleText="width:83%"
				name="ejercicioFin" 
				label="%{getText('ejercicio.fin')}" 
		     	labelposition="left" 
		     	maxlength="4" 
		     	size="4"
		     />
		</div>
<%--
		<div class="fila">
		     <gadir:campoTexto 
				styleLabel="width:15%"
			    styleText="width:83%"
				name="porcentajeParticipacion" 
				label="%{getText('porcentaje.participacion')}" 
		     	labelposition="left" 
		     	maxlength="3" 
		     	size="3"
		     />
		</div>
--%>
		</s:if>
		<s:if test="%{tipo.equals(\"L\") && subtipo.equals(\"I\")}" >
		<div class="fila">
			<gadir:fecha 
				styleLabel="width:15%"
			    styleText="width:83%"
				label="%{getText('fecha.ini')}" 
				name="fechaIni" 
				required="false" 
				id="fechaIni" 
				displayFormat="dd/MM/yyyy" 
				labelposition="left" 
				mostrarLeyenda="true"
			/>
		</div>
		<div class="fila">
			<gadir:fecha 
				styleLabel="width:15%"
			    styleText="width:83%"
				label="%{getText('fecha.fin')}" 
				name="fechaFin" 
				required="false" 
				id="fechaFin" 
				displayFormat="dd/MM/yyyy" 
				labelposition="left" 
				mostrarLeyenda="true"
			/>
		</div>
		</s:if>
		<s:if test="pedirImpresion">
		<div class="fila">
			<gadir:combo
				label="Imprimir díptico"
				labelposition="left"
				name="ejecutaIncidencia"
				list="listaImpresiones"
				listKey="key"
				listValue="value"
				styleLabel="width:15%"
			    styleText="width:83%"
				required ="true"
				emptyOption="true"
			/>
		</div>
		</s:if>
		<div class="fila separador">
			<hr class="linea_separadora"/>
		</div>
		<div class="botoneraIzquierda">
			<s:submit value="%{getText('button.aceptar')}" method="botonAceptar" theme="simple" />
			<s:submit value="%{getText('button.anular')}" method="botonAnular" theme="simple" />
		</div>
	</s:else>
	
</fieldset>

<div class="fila">
<div class="separador"></div>
	<div class="botoneraIzquierda">
		<s:if test="!primerosDatos">
			<gadir:botonVolverPila/>
		</s:if>
		<s:else>
			<s:submit value="%{getText('button.volver')}" method="botonVolver" theme="simple" />
		</s:else>
	</div>
</div>

</s:form>
