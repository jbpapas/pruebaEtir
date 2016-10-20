<#--
/*
 * $Id: text.ftl 720258 2008-11-24 19:05:16Z musachy $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->


<#-- jbenitac: cambio para controlar atributo salida -->
<#if parameters.salida?default(false)>
	<#if parameters.nameValue??>
		<#if parameters.cssClass?exists && parameters.cssClass.contains('Moneda')>	<#-- Para cualquier clase indicada que contenga la palabra 'Moneda' tiene que existir otra clase con igual nombre terminado en 'Salida' -->
			<div class="${parameters.cssClass+'Salida'}" title="<@s.property value="parameters.nameValue"/>">
				<@s.property value="parameters.nameValue"/>&nbsp;
			</div>			
		<#else>
			<#if parameters.sinCajita?default(false)>
				<@s.property value="parameters.nameValue"/>
			<#else>
				<div class="campoSalida" title="<@s.property value="parameters.nameValue"/>" id="<@s.property value="parameters.id"/>"  >
					<@s.property value="parameters.nameValue"/>&nbsp;
				</div>
			</#if>
		</#if>
	<#else>
		<#if parameters.sinCajita?default(false)>
			
		<#else>
			<div class="campoSalida" id="<@s.property value="parameters.id"/>" >&nbsp;</div>
		</#if>	
	</#if>
<#else>

<input type="text"<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.get("size")??>
 size="${parameters.get("size")?html}"<#rt/>
</#if>
<#if parameters.maxlength??>
 maxlength="${parameters.maxlength?html}"<#rt/>
</#if>
<#if parameters.nameValue??>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex??>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.id??>
 id="${parameters.id?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/css.ftl" />
<#if parameters.title??>
 title="${parameters.title?html}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="errorInput"
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
<#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl" />
/>


<#-- jbenitac: cambio para mostrar ayuda -->
<#if parameters.name?if_exists != "" && parameters.conAyuda?default(false)>

<img id="ayuda_${parameters.name?html}" name="ayuda_${parameters.name?html}" src="/etir/image/iconos/16x16/field_help.png" style="vertical-align:middle;cursor:pointer" />

</#if>

<#-- jbenitac: cambio para mostrar ayuda -->
<#if parameters.name?if_exists != "" && parameters.conAyuda?default(true)>
<script type="text/javascript">
$(document).ready(function() {
	

	$("img[id='ayuda_${parameters.name?html}']").click(function(e) {
		if(
			$('#ayuda_div').css("display") != 'none' 
			&& (parseInt($("img[id='ayuda_${parameters.name?html}']").position().top) != parseInt($('#ayuda_div').position().top)
			|| parseInt($("img[id='ayuda_${parameters.name?html}']").position().left) + 16 != parseInt($('#ayuda_div').position().left))
			){
			
			$('#ayuda_div').toggle();   
			
		}
		if ($('#ayuda_div').css("display") == 'none'){
			$('#ayuda_txt').html('<img id="ayuda_indicator" src="/etir/image/indicator.gif" alt="Cargando..."/>');
		
	      	$('#ayuda_div').css("left", $("img[id='ayuda_${parameters.name?html}']").position().left + 16);
	      	$('#ayuda_div').css("top", $("img[id='ayuda_${parameters.name?html}']").position().top);	     	      	
	      	$.ajax({
	      	  cache: false,
			  url: '/etir/ayudacampo.action',
			  <#if parameters.nombreCampoAyuda?exists>
			  data: "coProceso=${parameters.coProcesoActual?default("")?html}&campo=${parameters.nombreCampoAyuda?html}",
			  <#else>
			  data: "coProceso=${parameters.coProcesoActual?default("")?html}&campo=${parameters.name?html}",
			  </#if>
			  success: function(data) {
			    $('#ayuda_txt').html(data);
			  }
			});      	
		}
		
		$('#ayuda_div').toggle('fade');
	});
});
</script>
</#if>

</#if>
