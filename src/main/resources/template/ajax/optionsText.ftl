<div  style="${parameters.styleLabel?default("padding:0px;margin:0px;border:0px;float: left;width:30%;")?html};padding:0px;margin:0px;border:0px;float: left;"<#rt/>
>
<label for="${parameters.id?default("")?html}"<#rt/>
 id=lbl_"${parameters.name?default("")?html}"<#rt/><#rt/>
>NIF (*) <#rt/>
</label>

<input type="text"
 value="${valorCampo}" maxlength="15" name="${nameCampo}" ${estilos}/>