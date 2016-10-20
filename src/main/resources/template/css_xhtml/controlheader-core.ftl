<#--
 Se ha modificado la plantilla para que no añada el atributo errorFor, ya que deja de css xhtml valido.
 ¡IMPORTANTE!
 - Se debe modificar esta plantilla para evitar que se muestre el mensaje de validación en el campo.
-->
<#assign hasFieldErrors = parameters.name?exists && fieldErrors?exists && fieldErrors[parameters.name]?exists/> 
<div <#rt/><#if parameters.id?exists>id="wwgrp_${parameters.id}"<#rt/></#if> class="wwgrp" <#if parameters.styleGroup?exists>style="${parameters.styleGroup}"<#rt/></#if> >
	
<#if hasFieldErrors>
<div <#rt/><#if parameters.id?exists>id="wwerr_${parameters.id}"<#rt/></#if> class="wwerr">
<#list fieldErrors[parameters.name] as error>
    <div<#rt/>
    <#-- Comentamos este punto para que sea XHTML valido.
    <#if parameters.id?exists>
     errorFor="${parameters.id}"<#rt/>
    </#if>
    -->
    class="errorMessage">
             ${error?html}
    </div><#t/>
</#list>
</div><#t/>
</#if>

<#if parameters.label?exists>
<#if parameters.labelposition?default("top") == 'top'>
<div <#rt/>
<#else>
<span <#rt/>
</#if>
<#if parameters.id?exists>id="wwlbl_${parameters.id}"<#rt/></#if> class="wwlbl<#if parameters.labelposition?default("top") == 'top'>_top</#if>" <#if parameters.styleLabel?exists>style="${parameters.styleLabel}"<#rt/></#if> >
    <label <#t/>
<#if parameters.id?exists>
        for="${parameters.id?html}" <#t/>
</#if>
<#if hasFieldErrors>
        class="errorLabel"<#t/>
<#else>
        class="label"<#t/>
</#if>
    ><#t/>
<#if parameters.required?default(false)>
        <span class="required">*</span><#t/>
</#if>
        ${parameters.label?html}:
<#include "/${parameters.templateDir}/xhtml/tooltip.ftl" />
	</label><#t/>
<#if parameters.labelposition?default("top") == 'top'>
</div> <br /><#rt/>
<#else>
</span> <#rt/>
</#if>
</#if>
