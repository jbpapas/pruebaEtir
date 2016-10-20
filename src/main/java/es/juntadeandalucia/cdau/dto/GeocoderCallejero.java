/**
 * GeocoderCallejero.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.juntadeandalucia.cdau.dto;

public class GeocoderCallejero  extends es.juntadeandalucia.cdau.dto.GeocoderResult  implements java.io.Serializable {
    private java.lang.String cityName;

    private java.lang.String localityName;

    public GeocoderCallejero() {
    }

    public GeocoderCallejero(
           double coordinateX,
           double coordinateY,
           java.lang.String letra,
           java.lang.String locality,
           java.lang.String matchLevel,
           java.lang.String noMatchInfo,
           java.lang.String resultType,
           java.lang.String rotulo,
           double similarity,
           java.lang.String streetName,
           int streetNumber,
           java.lang.String streetType,
           java.lang.String cityName,
           java.lang.String localityName) {
        super(
            coordinateX,
            coordinateY,
            letra,
            locality,
            matchLevel,
            noMatchInfo,
            resultType,
            rotulo,
            similarity,
            streetName,
            streetNumber,
            streetType);
        this.cityName = cityName;
        this.localityName = localityName;
    }


    /**
     * Gets the cityName value for this GeocoderCallejero.
     * 
     * @return cityName
     */
    public java.lang.String getCityName() {
        return cityName;
    }


    /**
     * Sets the cityName value for this GeocoderCallejero.
     * 
     * @param cityName
     */
    public void setCityName(java.lang.String cityName) {
        this.cityName = cityName;
    }


    /**
     * Gets the localityName value for this GeocoderCallejero.
     * 
     * @return localityName
     */
    public java.lang.String getLocalityName() {
        return localityName;
    }


    /**
     * Sets the localityName value for this GeocoderCallejero.
     * 
     * @param localityName
     */
    public void setLocalityName(java.lang.String localityName) {
        this.localityName = localityName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GeocoderCallejero)) return false;
        GeocoderCallejero other = (GeocoderCallejero) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cityName==null && other.getCityName()==null) || 
             (this.cityName!=null &&
              this.cityName.equals(other.getCityName()))) &&
            ((this.localityName==null && other.getLocalityName()==null) || 
             (this.localityName!=null &&
              this.localityName.equals(other.getLocalityName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCityName() != null) {
            _hashCode += getCityName().hashCode();
        }
        if (getLocalityName() != null) {
            _hashCode += getLocalityName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GeocoderCallejero.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.cdau.juntadeandalucia.es", "GeocoderCallejero"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cityName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cityName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localityName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "localityName"));
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
