/**
 * NoreWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gdtel.ws;

import es.dipucadiz.etir.comun.config.GadirConfig;

public class NoreWSServiceLocator extends org.apache.axis.client.Service implements com.gdtel.ws.NoreWSService {

    public NoreWSServiceLocator() {
    }


    public NoreWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NoreWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for NoreWS
    private java.lang.String NoreWS_address = GadirConfig.leerParametro("url.ws.nore");

    public java.lang.String getNoreWSAddress() {
        return NoreWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NoreWSWSDDServiceName = "NoreWS";

    public java.lang.String getNoreWSWSDDServiceName() {
        return NoreWSWSDDServiceName;
    }

    public void setNoreWSWSDDServiceName(java.lang.String name) {
        NoreWSWSDDServiceName = name;
    }

    public com.gdtel.ws.NoreWS getNoreWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NoreWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNoreWS(endpoint);
    }

    public com.gdtel.ws.NoreWS getNoreWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.gdtel.ws.NoreWSSoapBindingStub _stub = new com.gdtel.ws.NoreWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getNoreWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNoreWSEndpointAddress(java.lang.String address) {
        NoreWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.gdtel.ws.NoreWS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.gdtel.ws.NoreWSSoapBindingStub _stub = new com.gdtel.ws.NoreWSSoapBindingStub(new java.net.URL(NoreWS_address), this);
                _stub.setPortName(getNoreWSWSDDServiceName());
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
        if ("NoreWS".equals(inputPortName)) {
            return getNoreWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.gdtel.com", "NoreWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.gdtel.com", "NoreWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("NoreWS".equals(portName)) {
            setNoreWSEndpointAddress(address);
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
