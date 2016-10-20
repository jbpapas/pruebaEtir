/**
 * InterfazCDAUWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS;

public interface InterfazCDAUWS extends java.rmi.Remote {
    public es.juntadeandalucia.cdau.dto.Version getVersion() throws java.rmi.RemoteException;
    public java.lang.String[] geocoder(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String locality) throws java.rmi.RemoteException;
    public java.lang.String[] geocoderSrs(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String locality, java.lang.String srs) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.GeocoderResult[] geocoderList(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String locality) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.GeocoderResult[] geocoderListSrs(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String locality, java.lang.String srs) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.GeocoderResult[] geocoderMunProv(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String municipio, java.lang.String provincia) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.GeocoderResult[] geocoderMunProvSrs(java.lang.String streetname, java.lang.String streetnumber, java.lang.String streettype, java.lang.String municipio, java.lang.String provincia, java.lang.String srs) throws java.rmi.RemoteException;
    public java.lang.String obtenerCodINE(java.lang.String provincia, java.lang.String municipio) throws java.rmi.RemoteException;
    public java.lang.String[] autocompletarDireccion(java.lang.String input, int limit) throws java.rmi.RemoteException;
    public java.lang.String[] autocompletarDireccionMunicipio(java.lang.String input, int limit, java.lang.String codine) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.TipoVia[] comprobarTipoVia(java.lang.String tipoVia) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.TipoCarretera[] comprobarTipoCarretera(java.lang.String tipoCarretera) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.Address normalizar(java.lang.String cadena) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.Address normalizar(java.lang.String cadena, java.lang.Object[] filters) throws java.rmi.RemoteException;
    public java.lang.Object[] buscarCallejero(java.lang.String query) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.NucleoPoblacion[] localizarNucleos(java.lang.String cadenaBusqueda, int cantidadRegistros, int pagina, int total) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.Municipio[] obtenerMunicipios(java.lang.String codProv) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.Municipio[] comprobarCodIne(java.lang.String codigo) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.NucleoPoblacion[] localizarNucleosSrs(java.lang.String cadenaBusqueda, int cantidadRegistros, int pagina, int total, java.lang.String srs) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.TipoVia[] obtenerTiposVia() throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.Province[] getAllProvinces(boolean orderAsc) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.GeocoderResult geocoderInverso(double x, double y) throws java.rmi.RemoteException;
    public es.juntadeandalucia.cdau.dto.GeocoderResult geocoderInversoSrs(double x, double y, java.lang.String srs) throws java.rmi.RemoteException;
    public java.lang.String[] autocompletarDireccionConNucleoYAgrupacion(java.lang.String input, int limit, java.lang.String codeIne, int nucleo, int agrupacion) throws java.rmi.RemoteException;
    public java.lang.Object[] buscarCallejeroSrsMunicipio(java.lang.String query, java.lang.String codINE, java.lang.String srs) throws java.rmi.RemoteException;
}
