<%@ include file="../taglibs.jsp"%>
<%@ include file="../parametrizacion.jsp"%>

<script src="<s:url value="/js/ext/utilPaginacion.js" includeParams="none" /><s:property value="#displayTableVersion"/>"></script>	

<gadir:pestanas  
	nombre0="pestana.criterios.seleccion"
	metodo0="sinMetodo"
/>

<fieldset class="pestana">
	<s:iterator value="listaCriterios">
		<div class="fila">
			<gadir:campoTexto
				salida="true"
				labelposition="left"
				label="%{key}"
				value="%{value}"
			/>
		</div>
	</s:iterator>

<s:form validate="true" id="Filtrar" name="formFiltrar">
	<s:if test="experto">
		<div class="fila">
			<gadir:combo1 
				label="%{getText('usuario')}"
				name="usuario"
				keyName="usuarioRowid"
				list="listaUsuarios"
				listKey="rowid"
				listValue="coAcmUsuario"
				value="%{usuario}"
				required="false"
				emptyOption="false"
				cssStyle="width: 200px"
				searchType="substring"
				resultsLimit="-1"
				forceValidOption="true"
				autoComplete="false"
				labelposition="left"
			/>
		</div>
	</s:if>
	
	<div class="fila">
		<gadir:fecha label="%{getText('fecha_desde')}" name="fechaDesde" required="false" id="fechaDesde" displayFormat="dd/MM/yyyy" labelposition="left" />
	</div>
	
	<div class="fila">
		<gadir:fecha label="%{getText('fecha_hasta')}" name="fechaHasta" required="false" id ="fechaHasta" displayFormat="dd/MM/yyyy" labelposition="left" />
	</div>
	
	<div class="fila">
				
		<gadir:combo1 
			label="%{getText('movimiento')}"
			labelposition="left"
			name="movimiento"
			keyName="movimientoKey"
			list="listaMovimientos"
			listKey="key"
			listValue="codigoDescripcion"
			required="false"
			emptyOption="false"
			cssStyle="width: 350px"
			searchType="substring"
			resultsLimit="-1"
			forceValidOption="true"
			salida="listaMovimientos.size() == 0"			
			autoComplete="false"
		/>			
	</div>
	
	<s:hidden name="historicoNombre"/>
	<s:hidden name="historicoCriterios"/>
	<s:hidden name="historicoBusqueda"/>
	
	<div class="fila separador">
		<hr class="linea_separadora"/>
	</div>
	
	<div class="botoneraIzquierda">
		<s:submit value="%{getText('button.aplicar')}" method="botonFiltrar" theme="simple" />
		<s:submit value="%{getText('button.anular')}" method="botonAnular" theme="simple" />
	</div>
	
</s:form>
</fieldset>

<div class="separador"></div>

<gadir:pestanas
	subpestana="true"
	nombre0="pestana.movimientos"
	metodo0="sinMetodo"
/>

<fieldset class="pestana">
	<s:if test="historicoTabla.size() == 0">
			<tr>
				<td><label class="label"><s:text name="sin.movimientos" /></label></td>
			</tr>
	</s:if>
	<s:else>
		<div class="scrollContent extendible">
			<s:set name="rowCont" value="%{0}"/>
			<display:table
				name="listadoHistorico"
				uid="row"
				requestURI="${actionName}.action"
				excludedParams="action:* method:* botonVolverPila"
				cellspacing="0"
				cellpadding="0"
			>
			
			<display:column
				titleKey="fecha"
				property="fecha"
				decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"
			/>
			
			<display:column
				titleKey="hora"
				property="hora"
				decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"
			/>
			
			<s:if test="experto">
			
				<display:column
					titleKey="usuario"
					property="usuario"
					decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"
				/>
			
			</s:if>
			
			<s:if test ="%{mostrarMovimiento}">
			<display:column
				titleKey="movimiento"
				property="movimiento"
				decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"
			/>
			</s:if>

			
			<s:iterator value="titulos" status="estado" var="titulo">
				
				<s:set name="valor" value="%{obtenerValorColumna(#rowCont, #estado.index-1)}" />
				
				<s:if test="%{alinearDerecha}">
					<s:set name="estilo">text-align: right</s:set>
				</s:if>
				<s:else>
					<s:set name="estilo">text-align: left</s:set>
				</s:else>
				
				<display:column title="${titulo}" style="${estilo}"					
					decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator">	
						<s:property value="%{#valor}"/>
				</display:column>
				
			</s:iterator>
			
			<s:set name="ejec">${row.ejecucion}</s:set>
		
			<s:if test="%{!#ejec.isEmpty()}">
			
			
				 <display:column
					class="celdaIconos"
					headerClass="celdaIconos"
					decorator="es.dipucadiz.etir.comun.displaytag.EmptyCellsDecorator"
				>
					<a target="_blank" 
						href="/etir/G46Seleccion.action" 
						title="Consulta de proceso masivo"
						onclick="vnt=window.open('/etir/G46Detalle.action?ventanaBotonLateral=true&procesoMasivoRowid=<s:property value="%{#ejec}" />' , 'ventanaEjecucion' , 'width=1024,height=768,scrollbars=YES'); vnt.focus(); return false;"
					>
						<img src="/etir/image/iconos/16x16/execution.png" title="Consulta de proceso masivo" alt="Consulta de proceso masivo" style="vertical-align: bottom" />
					</a>
					
				</display:column>
			
			</s:if>
			
			<s:set name="rowCont" value="%{#rowCont + 1}"/>
			</display:table>
		
		</div>
	</s:else>	
</fieldset>



<gadir:informe metodo="botonImprimirHistorico">
	<s:set name="fechaD" value="%{fechaDesdeString}" />
	<s:set name="fechaH" value="%{fechaHastaString}" />
	<gadir:parametro nombre="historicoNombre" valor="${historicoNombre}" />
	<gadir:parametro nombre="historicoBusqueda" valor="${historicoBusqueda}" />
	<gadir:parametro nombre="historicoCriterios" valor="${historicoCriterios}" />	
	<gadir:parametro nombre="usuarioRowid" valor="${usuarioRowid}" />
	<gadir:parametro nombre="usuario" valor="${usuario}" />
	<gadir:parametro nombre="movimientoKey" valor="${movimientoKey}" />
	<gadir:parametro nombre="movimiento" valor="${movimiento}" />
	<gadir:parametro nombre="fechaD" valor="${fechaD}" />
	<gadir:parametro nombre="fechaH" valor="${fechaH}" />
</gadir:informe>