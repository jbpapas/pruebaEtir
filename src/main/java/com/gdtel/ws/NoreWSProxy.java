package com.gdtel.ws;

public class NoreWSProxy implements com.gdtel.ws.NoreWS {
  private String _endpoint = null;
  private com.gdtel.ws.NoreWS noreWS = null;
  
  public NoreWSProxy() {
    _initNoreWSProxy();
  }
  
  public NoreWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initNoreWSProxy();
  }
  
  private void _initNoreWSProxy() {
    try {
      noreWS = (new com.gdtel.ws.NoreWSServiceLocator()).getNoreWS();
      if (noreWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)noreWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)noreWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (noreWS != null)
      ((javax.xml.rpc.Stub)noreWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.gdtel.ws.NoreWS getNoreWS() {
    if (noreWS == null)
      _initNoreWSProxy();
    return noreWS;
  }
  
  public java.lang.String crearNotificacion(com.gdtel.goncedc.domain.DatosNotificacion datosNotif, com.gdtel.goncedc.domain.Georef georef, int intentos, com.gdtel.goncedc.domain.Domicilio domAlternativo, java.lang.String emails) throws java.rmi.RemoteException{
    if (noreWS == null)
      _initNoreWSProxy();
    return noreWS.crearNotificacion(datosNotif, georef, intentos, domAlternativo, emails);
  }
  
  public com.gdtel.goncedc.domain.DatosNotificacion[] obtenerNotificaciones(java.lang.String numExp, java.lang.String dni, java.lang.String nombre, java.lang.String descripcionDoc, java.lang.String codigoBarras, java.lang.String zona, java.lang.String estado, java.lang.String empresaNotif, java.lang.String fecha, java.lang.String municipio, java.lang.String anio) throws java.rmi.RemoteException{
    if (noreWS == null)
      _initNoreWSProxy();
    return noreWS.obtenerNotificaciones(numExp, dni, nombre, descripcionDoc, codigoBarras, zona, estado, empresaNotif, fecha, municipio, anio);
  }
  
  public com.gdtel.goncedc.domain.DatosNotificacion obtenerNotificacionPorCodigoBarras(java.lang.String codigoBarras) throws java.rmi.RemoteException{
    if (noreWS == null)
      _initNoreWSProxy();
    return noreWS.obtenerNotificacionPorCodigoBarras(codigoBarras);
  }
  
  
}