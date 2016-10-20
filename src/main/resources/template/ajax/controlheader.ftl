<#--
/*
 * $Id: controlheader.ftl 590812 2007-10-31 20:32:54Z apetrelli $
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
<#if parameters.label?if_exists != "">
	<#-- jbenitac: cambio para que incluya el controlheader de css_chtml -->
	<#include "/${parameters.templateDir}/css_xhtml/controlheader.ftl" />
<#else>
	<#assign hasFieldErrors = parameters.name?exists && fieldErrors?exists && fieldErrors[parameters.name]?exists/>
</#if>
<#if parameters.form?exists && parameters.form.validate?default(false) == true>
	<#-- can't mutate the data model in freemarker -->
    <#if parameters.onblur?exists>
        ${tag.addParameter('onblur', "validate(this);${parameters.onblur}")}
    <#else>
        ${tag.addParameter('onblur', "validate(this);")}
    </#if>
</#if>

<#-- jronnols: si el componente tiene errores, añadir errorInput a la lista de clases a mostrar -->
<#if parameters.cssClass?exists>
	<#if stack.findString(parameters.cssClass)?exists>
		<#assign claseCss = stack.findString(parameters.cssClass)/>
	</#if>
</#if>
<#if hasFieldErrors?if_exists == true>
	<#if claseCss?if_exists != "">
		<#assign claseCss = "${claseCss} errorInput"/>
	<#else>
 		<#assign claseCss = "errorInput"/>
	</#if>
</#if>
<#-- jronnols: fin -->
