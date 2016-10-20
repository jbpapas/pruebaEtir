<%-- 
JSP con la parametrización de las paginas, 
estas seran obtenidas de parametros configurables.
--%>
<%@ include file="taglibs.jsp"%>
<s:set name="pagesize" scope="request" value="%{getParametro('config.listados.pagesize')}"/>
<s:set name="pagesizeLargo" scope="request" value="%{getParametro('config.listados.pagesizeLargo')}"/>
<s:set name="pagesizeLargo2" scope="request" value="%{getParametro('config.listados.pagesizeLargo2')}"/>
<s:set name="pagesize20" scope="request" value="%{getParametro('config.listados.pagesize20')}"/>
