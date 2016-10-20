/**
 * InterfazCDAUWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS;

import es.dipucadiz.etir.comun.config.GadirConfig;

public class InterfazCDAUWSServiceLocator extends org.apache.axis.client.Service implements es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWSService {

    public InterfazCDAUWSServiceLocator() {
    }


    public InterfazCDAUWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public InterfazCDAUWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for InterfazCDAUWS
    private java.lang.String InterfazCDAUWS_address = GadirConfig.leerParametro("interfazCDAUWS_address");

    public java.lang.String getInterfazCDAUWSAddress() {
        return InterfazCDAUWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String InterfazCDAUWSWSDDServiceName = "InterfazCDAUWS";

    public java.lang.String getInterfazCDAUWSWSDDServiceName() {
        return InterfazCDAUWSWSDDServiceName;
    }

    public void setInterfazCDAUWSWSDDServiceName(java.lang.String name) {
        InterfazCDAUWSWSDDServiceName = name;
    }

    public es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWS getInterfazCDAUWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(InterfazCDAUWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getInterfazCDAUWS(endpoint);
    }

    public es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWS getInterfazCDAUWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWSSoapBindingStub _stub = new es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getInterfazCDAUWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setInterfazCDAUWSEndpointAddress(java.lang.String address) {
        InterfazCDAUWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWS.class.isAssignableFrom(serviceEndpointInterface)) {
                es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWSSoapBindingStub _stub = new es.juntadeandalucia.cdau.ws.services.InterfazCDAUWS.InterfazCDAUWSSoapBindingStub(new java.net.URL(InterfazCDAUWS_address), this);
                _stub.setPortName(getInterfazCDAUWSWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("InterfazCDAUWS".equals(inputPortName)) {
            return getInterfazCDAUWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName(GadirConfig.leerParametro("interfazCDAUWS"), "InterfazCDAUWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName(GadirConfig.leerParametro("interfazCDAUWS"), "InterfazCDAUWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("InterfazCDAUWS".equals(portName)) {
            setInterfazCDAUWSEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
