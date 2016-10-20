<html>
<head>
	
	<script type="text/javascript">
		
		function actualizaOpener(){
			try{
				
				var test = window.opener.jQuery("input[name=verInforme]");
				test.val('false');
				
			}catch(err){}
		}				
	</script>

</head>
<body style="font-family:Lucida console; font-style:normal; font-weight:normal; font-size:10">
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
1
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
10
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
20
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
30
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
40
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
50
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
60
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
70
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
80
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
90
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
100
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
110
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
120
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
130
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
140
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
150
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
!---+----!----+----!----+----!----+----!----+----!----+----!----+----!----+----!----+----!----+----!----+----!----+----!----+----!----+----!----+----!
<br/>
<#list listaCabeceras as cabecera>
Cabecera: ${cabecera} <br/>
</#list>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
_______________________________________________________________________________________________________________________________________________________
<br/><br/>

<#list listaFilas as fila>
Filas:&nbsp;&nbsp;&nbsp;     ${fila} <br/>
</#list>

<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
...
<br/>
<br/>

<#list listaPies as pie>
Total:&nbsp;&nbsp;&nbsp;    ${pie} <br/>
</#list>
<br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
_______________________________________________________________________________________________________________________________________________________
<br/>
Leyenda: <br/>
<#list listaLeyendas as leyenda>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    ${leyenda} <br/>
</#list>
<br/><br/>
<div style="clear:both;text-align:center">
	<input
		onclick="actualizaOpener();window.close();return false;"
		type="submit"  
		value="Cerrar"
	>
</div>
</body>
</html>
        