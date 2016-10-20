<#--
/*
 * $Id: autocompleter.ftl 678836 2008-07-22 18:00:30Z musachy $
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
<#include "/${parameters.templateDir}/ajax/controlheader.ftl" />

<#-- jbenitac: cambio para controlar atributo salida -->
<#if parameters.salida?default(false)>
	<div class="campoSalida" title="<@s.property value="parameters.nameValue"/>">
		<#if parameters.nameValue??>
			<@s.property value="parameters.nameValue"/>&nbsp;
		<#else>
			&nbsp;
		</#if>
	</div>
<#else>

	<@s.if test="%{parameters.list!=null && parameters.list.size()==1}">
		<#-- jbenitac: solo autoselecciono si es required o si autoSeleccionOpcionUnica -->
		<#if (parameters.required?default(false) || parameters.autoSeleccionOpcionUnica?default(false))>
			<#assign unResultado = "true" />
			<@s.iterator value="parameters.list">
			    <#if parameters.listKey?exists>
			    	<#assign unResultadoKey = stack.findString(parameters.listKey) />
			    </#if>
			    <#if parameters.listValue?exists>
			    	<#assign unResultadoValue = stack.findString(parameters.listValue) />
			    </#if>
		    </@s.iterator>
		</#if>
	</@s.if>

	
	<#if parameters.href?exists>
	  <input dojoType="struts:ComboBox"<#rt/>
	  dataUrl="${parameters.href}"<#rt/>
	<#else>
	  <select dojoType="struts:ComboBox"<#rt/>
	</#if>
	<#if parameters.id?if_exists != "">
	 id="${parameters.id?html}"<#rt/>
	</#if>
	<#if claseCss?if_exists != "">
	 class="${claseCss?html}"<#rt/>
	</#if>
	<#if parameters.cssStyle?if_exists != "">
	 style="${parameters.cssStyle?html}"<#rt/>
	</#if>
	<#if parameters.forceValidOption?exists>
	 forceValidOption="${parameters.forceValidOption?string?html}"<#rt/>
	</#if>
	<#if parameters.searchType?if_exists != "">
	 searchType="${parameters.searchType?html}"<#rt/>
	</#if>
	<#if parameters.autoComplete?exists>
	 autoComplete="${parameters.autoComplete?string?html}"<#rt/>
	</#if>
	<#if parameters.delay?exists>
	 searchDelay="${parameters.delay?c}"<#rt/>
	</#if>
	<#if parameters.disabled?default(false)>
	 disabled="disabled"<#rt/>
	</#if>
	<#if parameters.dropdownWidth?exists>
	 dropdownWidth="${parameters.dropdownWidth?c}"<#rt/>
	</#if>
	<#if parameters.dropdownHeight?exists>
	 dropdownHeight="${parameters.dropdownHeight?c}"<#rt/>
	</#if>
	<#if parameters.name?if_exists != "">
	 name="${parameters.name?html}"<#rt/>
	</#if>
	<#if parameters.get("size")?exists>
	 size="${parameters.get("size")?html}"<#rt/>
	</#if>
	<#if parameters.keyName?if_exists != "">
	 keyName="${parameters.keyName?html}"<#rt/>
	</#if>
	<#if parameters.maxlength?exists>
	 maxlength="${parameters.maxlength?string?html}"<#rt/>
	</#if>
	<#if parameters.nameValue?if_exists != "">
	 initialValue="${parameters.nameValue}"<#rt/>
	<#else>
		<#if ( unResultado?if_exists != "" )>
			<#if ( unResultadoValue?if_exists != "" )>
		 		initialValue="${unResultadoValue?html}"<#rt/>
		 	</#if>
		</#if>
	</#if>
	<#if parameters.nameKeyValue?if_exists != "">
	 initialKey="${parameters.nameKeyValue}"<#rt/>
	<#else>
		<#if ( unResultado?if_exists != "" )>
			<#if ( unResultadoKey?if_exists != "" )>
		 		initialKey="${unResultadoKey?html}"<#rt/>
			</#if>
		</#if>
	</#if>
	<#if parameters.readonly?default(false)>
	 readonly="readonly"<#rt/>
	</#if>
	<#if parameters.tabindex?exists>
	 tabindex="${parameters.tabindex?html}"<#rt/>
	</#if>
	<#if parameters.formId?if_exists != "">
	 formId="${parameters.formId?html}"<#rt/>
	</#if>
	<#if parameters.formFilter?if_exists != "">
	 formFilter="${parameters.formFilter?html}"<#rt/>
	</#if>
	<#if parameters.listenTopics?if_exists != "">
	 listenTopics="${parameters.listenTopics?html}"<#rt/>
	</#if>
	<#if parameters.notifyTopics?if_exists != "">
	 notifyTopics="${parameters.notifyTopics?html}"<#rt/>
	</#if>
	<#if parameters.beforeNotifyTopics?if_exists != "">
	  beforeNotifyTopics="${parameters.beforeNotifyTopics?html}"<#rt/>
	</#if>
	<#if parameters.afterNotifyTopics?if_exists != "">
	  afterNotifyTopics="${parameters.afterNotifyTopics?html}"<#rt/>
	</#if>
	<#if parameters.errorNotifyTopics?if_exists != "">
	  errorNotifyTopics="${parameters.errorNotifyTopics?html}"<#rt/>
	</#if>
	<#if parameters.valueNotifyTopics?if_exists != "">
	  valueNotifyTopics="${parameters.valueNotifyTopics?html}"<#rt/>
	</#if>
	<#if parameters.indicator?if_exists != "">
	 indicator="${parameters.indicator?html}"<#rt/>
	</#if>
	<#if parameters.loadOnTextChange?default(false)>
	 loadOnType="${parameters.loadOnTextChange?string?html}"<#rt/>
	</#if>
	<#if parameters.loadMinimumCount?exists>
	 loadMinimum="${parameters.loadMinimumCount?c}"<#rt/>
	</#if>
	<#if parameters.showDownArrow?exists>
	 visibleDownArrow="${parameters.showDownArrow?string?html}"<#rt/>
	</#if>
	<#if parameters.iconPath?if_exists != "">
	 buttonSrc="<@s.url value='${parameters.iconPath}' encode="false" includeParams='none'/>"<#rt/>
	</#if>
	<#if parameters.templateCssPath?if_exists != "">
	 templateCssPath="<@s.url value='${parameters.templateCssPath}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.dataFieldName?if_exists != "">
	 dataFieldName="${parameters.dataFieldName?html}"
	</#if>
	<#if parameters.searchLimit?if_exists != "">
	 searchLimit="${parameters.searchLimit?html}"
	</#if>
	<#if parameters.transport?if_exists != "">
	  transport="${parameters.transport?html}"<#rt/>
	</#if>
	<#if parameters.preload?exists>
	  preload="${parameters.preload?string?html}"<#rt/>
	</#if>
	<#if parameters.required?default(false)>
	  isrequired="true"
	<#else>
	  isrequired="false"
	</#if>
	<#if parameters.isUsuarioAccesible?default(false)>
	  isusuarioaccesible="true"
	<#else>
	  isusuarioaccesible="false"
	</#if>
	<#if parameters.optionsConTitle?default(false)>
	  isoptionscontitle="true"
	<#else>
	  isoptionscontitle="false"
	</#if>
	<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
	<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
	<#if parameters.href?exists>
	 />
	<#else>
	 >
	</#if>
	<#if parameters.list?exists>
		<#if (parameters.headerKey?exists && parameters.headerValue?exists)>
			<option value="${parameters.headerKey?html}">${parameters.headerValue?html}</option>
		</#if>
		<#if parameters.emptyOption?default(false)>
		    <option value=""></option>
		</#if>
	    <@s.iterator value="parameters.list">
	    <#if parameters.listKey?exists>
	    	<#assign tmpListKey = stack.findString(parameters.listKey) />
	    <#else>
	    	<#assign tmpListKey = stack.findString('top') />
	    </#if>
	    <#if parameters.listValue?exists>
	    	<#assign tmpListValue = stack.findString(parameters.listValue) />
	    <#else>
	    	<#assign tmpListValue = stack.findString('top') />
	    </#if>
	    <#-- jbenitac: hago que compare con el nameKeyValue, en lugar del nameValue. Si se ha detectado un solo resultado se selecciona-->
	    <option value="${tmpListKey?html}"<#rt/>    	
	        <#if ( unResultado?if_exists != "" || (parameters.nameKeyValue?exists && parameters.nameKeyValue == tmpListKey))>
	 selected="selected"<#rt/>
	        </#if>
	    ><#t/>
	            ${tmpListValue?html}<#t/>
	    </option><#lt/>
	    </@s.iterator>
	  </select>
	</#if>
	
	<#-- jbenitac: cambio para mostrar ayuda -->
	<#if parameters.name?if_exists != "" && parameters.conAyuda?default(false)>
	
	<img id="ayuda_${parameters.name?html}" name="ayuda_${parameters.name?html}" src="/etir/image/iconos/16x16/field_help.png" style="vertical-align:middle;cursor:pointer" />
	
	</#if>
	
	<#-- jbenitac: cambio para que pinte el indicator -->
	<#if parameters.indicator?if_exists != "">
		<img id="${parameters.indicator?html}" src="/etir/image/indicator.gif" alt="Cargando..." style="display:none;vertical-align:middle;position:absolute"/>
	</#if>


</#if>

<#if parameters.label?if_exists != "">
	<#-- jbenitac: cambio a que incluya el controlfooter de css_xhtm -->
	<#include "/${parameters.templateDir}/css_xhtml/controlfooter.ftl" />
</#if>
	
	
<#-- jbenitac: cambio para mostrar ayuda -->
<#if parameters.name?if_exists != "" && parameters.conAyuda?default(true)>
	<script type="text/javascript">
	$(document).ready(function() {
		
	
		$('#ayuda_${parameters.name?html}').click(function(e) {
		
			if(
				$('#ayuda_div').css("display") != 'none' 
				&& (parseInt($('#ayuda_${parameters.name?html}').position().top) != parseInt($('#ayuda_div').position().top)
				|| parseInt($('#ayuda_${parameters.name?html}').position().left) + 16 != parseInt($('#ayuda_div').position().left))
				){	
				
				$('#ayuda_div').toggle();   
				
			}
			if ($('#ayuda_div').css("display") == 'none'){
				$('#ayuda_txt').html('<img id="ayuda_indicator" src="/etir/image/indicator.gif" alt="Cargando..."/>');
			
		      	$('#ayuda_div').css("left", $('#ayuda_${parameters.name?html}').position().left + 16);
		      	$('#ayuda_div').css("top", $('#ayuda_${parameters.name?html}').position().top);	     	      	
		      	
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

<#if parameters.pushId>
<script language="JavaScript" type="text/javascript">djConfig.searchIds.push("${parameters.id?html}");</script>
</#if>




