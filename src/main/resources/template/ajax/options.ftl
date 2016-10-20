
<#if autocompleterConCodigo>
[
<#list autocompleterOptions as option>
	["${option.codigoDescripcion}", "${option.key}"],
</#list>
]
<#else>
[
<#list autocompleterOptions as option>
	["${option.value}", "${option.key}"],
</#list>
]
</#if>