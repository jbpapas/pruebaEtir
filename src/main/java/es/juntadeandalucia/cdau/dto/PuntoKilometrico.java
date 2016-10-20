/**
 * PuntoKilometrico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.juntadeandalucia.cdau.dto;

public class PuntoKilometrico  implements java.io.Serializable {
    private java.lang.String carretera;

    private java.lang.String pk;

    private java.lang.String provincia;

    private double x;

    private double y;

    public PuntoKilometrico() {
    }

    public PuntoKilometrico(
           java.lang.String carretera,
           java.lang.String pk,
           java.lang.String provincia,
           double x,
           double y) {
           this.carretera = carretera;
           this.pk = pk;
           this.provincia = provincia;
           this.x = x;
           this.y = y;
    }


    /**
     * Gets the carretera value for this PuntoKilometrico.
     * 
     * @return carretera
     */
    public java.lang.String getCarretera() {
        return carretera;
    }


    /**
     * Sets the carretera value for this PuntoKilometrico.
     * 
     * @param carretera
     */
    public void setCarretera(java.lang.String carretera) {
        this.carretera = carretera;
    }


    /**
     * Gets the pk value for this PuntoKilometrico.
     * 
     * @return pk
     */
    public java.lang.String getPk() {
        return pk;
    }


    /**
     * Sets the pk value for this PuntoKilometrico.
     * 
     * @param pk
     */
    public void setPk(java.lang.String pk) {
        this.pk = pk;
    }


    /**
     * Gets the provincia value for this PuntoKilometrico.
     * 
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this PuntoKilometrico.
     * 
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the x value for this PuntoKilometrico.
     * 
     * @return x
     */
    public double getX() {
        return x;
    }


    /**
     * Sets the x value for this PuntoKilometrico.
     * 
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }


    /**
     * Gets the y value for this PuntoKilometrico.
     * 
     * @return y
     */
    public double getY() {
        return y;
    }


    /**
     * Sets the y value for this PuntoKilometrico.
     * 
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PuntoKilometrico)) return false;
        PuntoKilometrico other = (PuntoKilometrico) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.carretera==null && other.getCarretera()==null) || 
             (this.carretera!=null &&
              this.carretera.equals(other.getCarretera()))) &&
            ((this.pk==null && other.getPk()==null) || 
             (this.pk!=null &&
              this.pk.equals(other.getPk()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia()))) &&
            this.x == other.getX() &&
            this.y == other.getY();
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
        if (getCarretera() != null) {
            _hashCode += getCarretera().hashCode();
        }
        if (getPk() != null) {
            _hashCode += getPk().hashCode();
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        _hashCode += new Double(getX()).hashCode();
        _hashCode += new Double(getY()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PuntoKilometrico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.cdau.juntadeandalucia.es", "PuntoKilometrico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carretera");
        elemField.setXmlName(new javax.xml.namespace.QName("", "carretera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pk");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pk"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("x");
        elemField.setXmlName(new javax.xml.namespace.QName("", "x"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("y");
        elemField.setXmlName(new javax.xml.namespace.QName("", "y"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
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
