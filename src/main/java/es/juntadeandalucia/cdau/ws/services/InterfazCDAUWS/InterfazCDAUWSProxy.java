package es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS;

public class InterfazCDAUWSProxy implements es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWS {
  private String _endpoint = null;
  private es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWS interfazCDAUWS = null;
  
  public InterfazCDAUWSProxy() {
    _initInterfazCDAUWSProxy();
  }
  
  public InterfazCDAUWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initInterfazCDAUWSProxy();
  }
  
  private void _initInterfazCDAUWSProxy() {
    try {
      interfazCDAUWS = (new es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWSServiceLocator()).getInterfazCDAUWS();
      if (interfazCDAUWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)interfazCDAUWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)interfazCDAUWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (interfazCDAUWS != null)
      ((javax.xml.rpc.Stub)interfazCDAUWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWS getInterfazCDAUWS() {
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS;
  }
  
  public es.juntadeandalucia.cdau.dto.Version getVersion() throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.getVersion();
  }
  
  public java.lang.String[] geocoder(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String locality) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.geocoder(streetname, streetnumber, streettype, locality);
  }
  
  public java.lang.String[] geocoderSrs(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String locality, java.lang.String srs) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.geocoderSrs(streetname, streetnumber, streettype, locality, srs);
  }
  
  public es.juntadeandalucia.cdau.dto.GeocoderResult[] geocoderList(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String locality) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.geocoderList(streetname, streetnumber, streettype, locality);
  }
  
  public es.juntadeandalucia.cdau.dto.GeocoderResult[] geocoderListSrs(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String locality, java.lang.String srs) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.geocoderListSrs(streetname, streetnumber, streettype, locality, srs);
  }
  
  public es.juntadeandalucia.cdau.dto.GeocoderResult[] geocoderMunProv(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String municipio, java.lang.String provincia) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.geocoderMunProv(streetname, streetnumber, streettype, municipio, provincia);
  }
  
  public es.juntadeandalucia.cdau.dto.GeocoderResult[] geocoderMunProvSrs(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String municipio, java.lang.String provincia, java.lang.String srs) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.geocoderMunProvSrs(streetname, streetnumber, streettype, municipio, provincia, srs);
  }
  
  public java.lang.String obtenerCodINE(java.lang.String provincia, java.lang.String municipio) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.obtenerCodINE(provincia, municipio);
  }
  
  public java.lang.String[] autocompletarDireccion(java.lang.String input, int limit) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.autocompletarDireccion(input, limit);
  }
  
  public java.lang.String[] autocompletarDireccionMunicipio(java.lang.String input, int limit, java.lang.String codine) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.autocompletarDireccionMunicipio(input, limit, codine);
  }
  
  public es.juntadeandalucia.cdau.dto.TipoVia[] comprobarTipoVia(java.lang.String tipoVia) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.comprobarTipoVia(tipoVia);
  }
  
  public es.juntadeandalucia.cdau.dto.TipoCarretera[] comprobarTipoCarretera(java.lang.String tipoCarretera) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.comprobarTipoCarretera(tipoCarretera);
  }
  
  public es.juntadeandalucia.cdau.dto.Address normalizar(java.lang.String cadena) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.normalizar(cadena);
  }
  
  public es.juntadeandalucia.cdau.dto.Address normalizar(java.lang.String cadena, java.lang.Object[] filters) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.normalizar(cadena, filters);
  }
  
  public java.lang.Object[] buscarCallejero(java.lang.String query) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.buscarCallejero(query);
  }
  
  public es.juntadeandalucia.cdau.dto.NucleoPoblacion[] localizarNucleos(java.lang.String cadenaBusqueda, int cantidadRegistros, int pagina, int total) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.localizarNucleos(cadenaBusqueda, cantidadRegistros, pagina, total);
  }
  
  public es.juntadeandalucia.cdau.dto.Municipio[] obtenerMunicipios(java.lang.String codProv) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.obtenerMunicipios(codProv);
  }
  
  public es.juntadeandalucia.cdau.dto.Municipio[] comprobarCodIne(java.lang.String codigo) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.comprobarCodIne(codigo);
  }
  
  public es.juntadeandalucia.cdau.dto.NucleoPoblacion[] localizarNucleosSrs(java.lang.String cadenaBusqueda, int cantidadRegistros, int pagina, int total, java.lang.String srs) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.localizarNucleosSrs(cadenaBusqueda, cantidadRegistros, pagina, total, srs);
  }
  
  public es.juntadeandalucia.cdau.dto.TipoVia[] obtenerTiposVia() throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.obtenerTiposVia();
  }
  
  public es.juntadeandalucia.cdau.dto.Province[] getAllProvinces(boolean orderAsc) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.getAllProvinces(orderAsc);
  }
  
  public es.juntadeandalucia.cdau.dto.GeocoderResult geocoderInverso(double x, double y) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.geocoderInverso(x, y);
  }
  
  public es.juntadeandalucia.cdau.dto.GeocoderResult geocoderInversoSrs(double x, double y, java.lang.String srs) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.geocoderInversoSrs(x, y, srs);
  }
  
  public java.lang.String[] autocompletarDireccionConNucleoYAgrupacion(java.lang.String input, int limit, java.lang.String codeIne, int nucleo, int agrupacion) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.autocompletarDireccionConNucleoYAgrupacion(input, limit, codeIne, nucleo, agrupacion);
  }
  
  public java.lang.Object[] buscarCallejeroSrsMunicipio(java.lang.String query, java.lang.String codINE, java.lang.String srs) throws java.rmi.RemoteException{
    if (interfazCDAUWS == null)
      _initInterfazCDAUWSProxy();
    return interfazCDAUWS.buscarCallejeroSrsMunicipio(query, codINE, srs);
  }
  
  
}