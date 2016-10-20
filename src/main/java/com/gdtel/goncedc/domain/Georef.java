/**
 * Georef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gdtel.goncedc.domain;

public class Georef  implements java.io.Serializable {
    private java.lang.String altura;

    private java.lang.Integer id;

    private java.lang.String latitud;

    private java.lang.String longitud;

    public Georef() {
    }

    public Georef(
           java.lang.String altura,
           java.lang.Integer id,
           java.lang.String latitud,
           java.lang.String longitud) {
           this.altura = altura;
           this.id = id;
           this.latitud = latitud;
           this.longitud = longitud;
    }


    /**
     * Gets the altura value for this Georef.
     * 
     * @return altura
     */
    public java.lang.String getAltura() {
        return altura;
    }


    /**
     * Sets the altura value for this Georef.
     * 
     * @param altura
     */
    public void setAltura(java.lang.String altura) {
        this.altura = altura;
    }


    /**
     * Gets the id value for this Georef.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this Georef.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the latitud value for this Georef.
     * 
     * @return latitud
     */
    public java.lang.String getLatitud() {
        return latitud;
    }


    /**
     * Sets the latitud value for this Georef.
     * 
     * @param latitud
     */
    public void setLatitud(java.lang.String latitud) {
        this.latitud = latitud;
    }


    /**
     * Gets the longitud value for this Georef.
     * 
     * @return longitud
     */
    public java.lang.String getLongitud() {
        return longitud;
    }


    /**
     * Sets the longitud value for this Georef.
     * 
     * @param longitud
     */
    public void setLongitud(java.lang.String longitud) {
        this.longitud = longitud;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Georef)) return false;
        Georef other = (Georef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.altura==null && other.getAltura()==null) || 
             (this.altura!=null &&
              this.altura.equals(other.getAltura()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.latitud==null && other.getLatitud()==null) || 
             (this.latitud!=null &&
              this.latitud.equals(other.getLatitud()))) &&
            ((this.longitud==null && other.getLongitud()==null) || 
             (this.longitud!=null &&
              this.longitud.equals(other.getLongitud())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAltura() != null) {
            _hashCode += getAltura().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getLatitud() != null) {
            _hashCode += getLatitud().hashCode();
        }
        if (getLongitud() != null) {
            _hashCode += getLongitud().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Georef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "Georef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altura");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "altura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("latitud");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "latitud"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longitud");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "longitud"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
