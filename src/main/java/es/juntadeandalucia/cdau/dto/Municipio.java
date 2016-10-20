/**
 * Municipio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.juntadeandalucia.cdau.dto;

public class Municipio  implements java.io.Serializable {
    private java.lang.String idMunicipio;

    private java.lang.String idProvincia;

    private java.lang.String nombreMunicipio;

    private java.lang.String nombreProvincia;

    public Municipio() {
    }

    public Municipio(
           java.lang.String idMunicipio,
           java.lang.String idProvincia,
           java.lang.String nombreMunicipio,
           java.lang.String nombreProvincia) {
           this.idMunicipio = idMunicipio;
           this.idProvincia = idProvincia;
           this.nombreMunicipio = nombreMunicipio;
           this.nombreProvincia = nombreProvincia;
    }


    /**
     * Gets the idMunicipio value for this Municipio.
     * 
     * @return idMunicipio
     */
    public java.lang.String getIdMunicipio() {
        return idMunicipio;
    }


    /**
     * Sets the idMunicipio value for this Municipio.
     * 
     * @param idMunicipio
     */
    public void setIdMunicipio(java.lang.String idMunicipio) {
        this.idMunicipio = idMunicipio;
    }


    /**
     * Gets the idProvincia value for this Municipio.
     * 
     * @return idProvincia
     */
    public java.lang.String getIdProvincia() {
        return idProvincia;
    }


    /**
     * Sets the idProvincia value for this Municipio.
     * 
     * @param idProvincia
     */
    public void setIdProvincia(java.lang.String idProvincia) {
        this.idProvincia = idProvincia;
    }


    /**
     * Gets the nombreMunicipio value for this Municipio.
     * 
     * @return nombreMunicipio
     */
    public java.lang.String getNombreMunicipio() {
        return nombreMunicipio;
    }


    /**
     * Sets the nombreMunicipio value for this Municipio.
     * 
     * @param nombreMunicipio
     */
    public void setNombreMunicipio(java.lang.String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }


    /**
     * Gets the nombreProvincia value for this Municipio.
     * 
     * @return nombreProvincia
     */
    public java.lang.String getNombreProvincia() {
        return nombreProvincia;
    }


    /**
     * Sets the nombreProvincia value for this Municipio.
     * 
     * @param nombreProvincia
     */
    public void setNombreProvincia(java.lang.String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Municipio)) return false;
        Municipio other = (Municipio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idMunicipio==null && other.getIdMunicipio()==null) || 
             (this.idMunicipio!=null &&
              this.idMunicipio.equals(other.getIdMunicipio()))) &&
            ((this.idProvincia==null && other.getIdProvincia()==null) || 
             (this.idProvincia!=null &&
              this.idProvincia.equals(other.getIdProvincia()))) &&
            ((this.nombreMunicipio==null && other.getNombreMunicipio()==null) || 
             (this.nombreMunicipio!=null &&
              this.nombreMunicipio.equals(other.getNombreMunicipio()))) &&
            ((this.nombreProvincia==null && other.getNombreProvincia()==null) || 
             (this.nombreProvincia!=null &&
              this.nombreProvincia.equals(other.getNombreProvincia())));
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
        if (getIdMunicipio() != null) {
            _hashCode += getIdMunicipio().hashCode();
        }
        if (getIdProvincia() != null) {
            _hashCode += getIdProvincia().hashCode();
        }
        if (getNombreMunicipio() != null) {
            _hashCode += getNombreMunicipio().hashCode();
        }
        if (getNombreProvincia() != null) {
            _hashCode += getNombreProvincia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Municipio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.cdau.juntadeandalucia.es", "Municipio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMunicipio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMunicipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProvincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idProvincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreMunicipio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreMunicipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreProvincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreProvincia"));
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
