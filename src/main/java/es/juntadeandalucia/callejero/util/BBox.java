/**
 * BBox.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.juntadeandalucia.callejero.util;

public class BBox  implements java.io.Serializable {
    private double XMax;

    private double XMin;

    private double YMax;

    private double YMin;

    public BBox() {
    }

    public BBox(
           double XMax,
           double XMin,
           double YMax,
           double YMin) {
           this.XMax = XMax;
           this.XMin = XMin;
           this.YMax = YMax;
           this.YMin = YMin;
    }


    /**
     * Gets the XMax value for this BBox.
     * 
     * @return XMax
     */
    public double getXMax() {
        return XMax;
    }


    /**
     * Sets the XMax value for this BBox.
     * 
     * @param XMax
     */
    public void setXMax(double XMax) {
        this.XMax = XMax;
    }


    /**
     * Gets the XMin value for this BBox.
     * 
     * @return XMin
     */
    public double getXMin() {
        return XMin;
    }


    /**
     * Sets the XMin value for this BBox.
     * 
     * @param XMin
     */
    public void setXMin(double XMin) {
        this.XMin = XMin;
    }


    /**
     * Gets the YMax value for this BBox.
     * 
     * @return YMax
     */
    public double getYMax() {
        return YMax;
    }


    /**
     * Sets the YMax value for this BBox.
     * 
     * @param YMax
     */
    public void setYMax(double YMax) {
        this.YMax = YMax;
    }


    /**
     * Gets the YMin value for this BBox.
     * 
     * @return YMin
     */
    public double getYMin() {
        return YMin;
    }


    /**
     * Sets the YMin value for this BBox.
     * 
     * @param YMin
     */
    public void setYMin(double YMin) {
        this.YMin = YMin;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BBox)) return false;
        BBox other = (BBox) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.XMax == other.getXMax() &&
            this.XMin == other.getXMin() &&
            this.YMax == other.getYMax() &&
            this.YMin == other.getYMin();
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
        _hashCode += new Double(getXMax()).hashCode();
        _hashCode += new Double(getXMin()).hashCode();
        _hashCode += new Double(getYMax()).hashCode();
        _hashCode += new Double(getYMin()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BBox.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://util.callejero.juntadeandalucia.es", "BBox"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "XMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "XMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("YMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "YMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("YMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "YMin"));
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
