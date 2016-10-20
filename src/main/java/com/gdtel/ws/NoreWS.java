/**
 * NoreWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gdtel.ws;

public interface NoreWS extends java.rmi.Remote {
    public java.lang.String crearNotificacion(com.gdtel.goncedc.domain.DatosNotificacion datosNotif, com.gdtel.goncedc.domain.Georef georef, int intentos, com.gdtel.goncedc.domain.Domicilio domAlternativo, java.lang.String emails) throws java.rmi.RemoteException;
    public com.gdtel.goncedc.domain.DatosNotificacion[] obtenerNotificaciones(java.lang.String numExp, java.lang.String dni, java.lang.String nombre, java.lang.String descripcionDoc, java.lang.String codigoBarras, java.lang.String zona, java.lang.String estado, java.lang.String empresaNotif, java.lang.String fecha, java.lang.String municipio, java.lang.String anio) throws java.rmi.RemoteException;
    public com.gdtel.goncedc.domain.DatosNotificacion obtenerNotificacionPorCodigoBarras(java.lang.String codigoBarras) throws java.rmi.RemoteException;
}
