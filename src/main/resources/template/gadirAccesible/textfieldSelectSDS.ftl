
<div class="${parameters.classLabel?default("")?html}"<#rt/>
 style="${parameters.styleLabel?default("")?html}"<#rt/>
>
<label for="${parameters.id?default("")?html}"<#rt/>
 id=lbl_"${parameters.name?default("")?html}"<#rt/>
	style="width:95%"<#rt/>
>${parameters.label?default("")?html} <#rt/>
</label>

</div>
<div class="${parameters.classText?default("")?html}"<#rt/>
style="${parameters.styleText?default("")?html}"<#rt/>
>
<input type="text"<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.get("size")?exists>
 size="${parameters.get("size")?html}"<#rt/>
</#if>
<#if parameters.maxlength?exists>
 maxlength="${parameters.maxlength?html}"<#rt/>
</#if>
<#if parameters.nameValue?exists>
 value="<@s.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?default(false)>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex?exists>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.id?exists>
 id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.title?exists>
 title="${parameters.title?html}"<#rt/>
 <#else>
  title="${parameters.label?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />

/>
</div>

