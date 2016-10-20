<%@ include file="../../taglibs.jsp"%>

<div style="z-index: 900000;background-color:#000000;opacity:0.4; filter: alpha(opacity = 40); height:100%; left:0; position:fixed; top:0; width:100%;"></div>
<div style="position: fixed; z-index: 900002; top: 20%; opacity: 1; left: 30%;width: 40%;">
   	<div style="background-color:#E8E8E6; border:1px solid #E16F26; text-align:left; margin:6px; padding:10px;">
		<div style="background-color:white; padding: 5px">
			<h1 style="margin-bottom:0">Aviso</h1>
			<div class="fila separador">
				<hr class="linea_separadora"/>
           	</div>

			<div style="padding-top:10px">
				<span style="font-size:1.4em; color:black">
					Al fin de mejorar el servicio de eTIR, le rogamos que cumplimente este breve y simple cuestionario que es totalmente anónimo.<br/>
					Muchas gracias por su colaboración.
				</span>
			</div>
			<div style="text-align: center; margin-top:10px; padding-top: 20px">
				<s:form target="_blank">
					<s:submit value="Ir a encuesta" theme="simple" method="botonIrEncuesta" onclick="encuestaClick();" />
				</s:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
function encuestaClick() {
	setTimeout(function(){
		location.reload();
	}, 1000);
}
</script>