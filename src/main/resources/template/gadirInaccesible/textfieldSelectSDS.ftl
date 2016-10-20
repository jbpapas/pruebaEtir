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
 <#else>
  style="width:80%"<#rt/>
</#if>
<#if parameters.title?exists>
 title="${parameters.title?html}"<#rt/>
 <#else>
  title="${parameters.label?html}"<#rt/>
</#if>

<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
 onkeypress="return false"
/><#rt/>



<input type="button" style="background-image:  url('/etir/img/fdo_otros_botones.gif');<#rt/>
			border:0px;<#rt/>
	      	width: 16px;<#rt/>
	      	padding: 0px;<#rt/>
	      	height:20px;
	      	border: 1px solid #6ba0b8;"<#rt/>
	      	<#if parameters.disabled?default(false)>
			 disabled="disabled"<#rt/>
			</#if>
	      	
	      	value="..."<#rt/>
	      	
<#if parameters.onclick?exists>
 onclick="mostrarVentanaSelectSDS()"<#rt/>
</#if>	
><#rt/>
<script>
function mostrarVentanaSelectSDS()
{
	mostrarCargandoSelectSDS();
	
	setTimeout("${parameters.onclick?html}; dialogoCargar.hide();",500);
	
}
var dialogoCargar;
function mostrarCargandoSelectSDS()
{
	if (!dialogoCargar) {
		formCargando = new Ext.FormPanel(
				{
					split :true,
					labelAlign :'left',
					frame :true,
					buttonAlign :'center',
					bodyStyle :'padding:5px 5px 0',
					width :'100%',
					height :'100%',
					items : [{html:'Cargando...Espere, por favor.'}]
				});
		
		var panel = new Ext.Panel( {
			region :'center',
			split :true,
			width :200,
			collapsible :true,
			margins :'3 3 3 3',
			cmargins :'3 3 3 3'
		});
		panel.add(formCargando);
		
		dialogoCargar = new Ext.Window( {
			title :'',
			closable :false,
			modal :true,
			width :200,
			height :59,
			plain :true,
			layout :'border',
			items : [ panel ]
		});
	}

	dialogoCargar.show();
}
</script>


</div>



