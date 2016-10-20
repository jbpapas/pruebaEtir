/**
 * GeocoderResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.juntadeandalucia.cdau.dto;

public class GeocoderResult  implements java.io.Serializable {
    private double coordinateX;

    private double coordinateY;

    private java.lang.String letra;

    private java.lang.String locality;

    private java.lang.String matchLevel;

    private java.lang.String noMatchInfo;

    private java.lang.String resultType;

    private java.lang.String rotulo;

    private double similarity;

    private java.lang.String streetName;

    private int streetNumber;

    private java.lang.String streetType;

    public GeocoderResult() {
    }

    public GeocoderResult(
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
           java.lang.String streetType) {
           this.coordinateX = coordinateX;
           this.coordinateY = coordinateY;
           this.letra = letra;
           this.locality = locality;
           this.matchLevel = matchLevel;
           this.noMatchInfo = noMatchInfo;
           this.resultType = resultType;
           this.rotulo = rotulo;
           this.similarity = similarity;
           this.streetName = streetName;
           this.streetNumber = streetNumber;
           this.streetType = streetType;
    }


    /**
     * Gets the coordinateX value for this GeocoderResult.
     * 
     * @return coordinateX
     */
    public double getCoordinateX() {
        return coordinateX;
    }


    /**
     * Sets the coordinateX value for this GeocoderResult.
     * 
     * @param coordinateX
     */
    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }


    /**
     * Gets the coordinateY value for this GeocoderResult.
     * 
     * @return coordinateY
     */
    public double getCoordinateY() {
        return coordinateY;
    }


    /**
     * Sets the coordinateY value for this GeocoderResult.
     * 
     * @param coordinateY
     */
    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }


    /**
     * Gets the letra value for this GeocoderResult.
     * 
     * @return letra
     */
    public java.lang.String getLetra() {
        return letra;
    }


    /**
     * Sets the letra value for this GeocoderResult.
     * 
     * @param letra
     */
    public void setLetra(java.lang.String letra) {
        this.letra = letra;
    }


    /**
     * Gets the locality value for this GeocoderResult.
     * 
     * @return locality
     */
    public java.lang.String getLocality() {
        return locality;
    }


    /**
     * Sets the locality value for this GeocoderResult.
     * 
     * @param locality
     */
    public void setLocality(java.lang.String locality) {
        this.locality = locality;
    }


    /**
     * Gets the matchLevel value for this GeocoderResult.
     * 
     * @return matchLevel
     */
    public java.lang.String getMatchLevel() {
        return matchLevel;
    }


    /**
     * Sets the matchLevel value for this GeocoderResult.
     * 
     * @param matchLevel
     */
    public void setMatchLevel(java.lang.String matchLevel) {
        this.matchLevel = matchLevel;
    }


    /**
     * Gets the noMatchInfo value for this GeocoderResult.
     * 
     * @return noMatchInfo
     */
    public java.lang.String getNoMatchInfo() {
        return noMatchInfo;
    }


    /**
     * Sets the noMatchInfo value for this GeocoderResult.
     * 
     * @param noMatchInfo
     */
    public void setNoMatchInfo(java.lang.String noMatchInfo) {
        this.noMatchInfo = noMatchInfo;
    }


    /**
     * Gets the resultType value for this GeocoderResult.
     * 
     * @return resultType
     */
    public java.lang.String getResultType() {
        return resultType;
    }


    /**
     * Sets the resultType value for this GeocoderResult.
     * 
     * @param resultType
     */
    public void setResultType(java.lang.String resultType) {
        this.resultType = resultType;
    }


    /**
     * Gets the rotulo value for this GeocoderResult.
     * 
     * @return rotulo
     */
    public java.lang.String getRotulo() {
        return rotulo;
    }


    /**
     * Sets the rotulo value for this GeocoderResult.
     * 
     * @param rotulo
     */
    public void setRotulo(java.lang.String rotulo) {
        this.rotulo = rotulo;
    }


    /**
     * Gets the similarity value for this GeocoderResult.
     * 
     * @return similarity
     */
    public double getSimilarity() {
        return similarity;
    }


    /**
     * Sets the similarity value for this GeocoderResult.
     * 
     * @param similarity
     */
    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }


    /**
     * Gets the streetName value for this GeocoderResult.
     * 
     * @return streetName
     */
    public java.lang.String getStreetName() {
        return streetName;
    }


    /**
     * Sets the streetName value for this GeocoderResult.
     * 
     * @param streetName
     */
    public void setStreetName(java.lang.String streetName) {
        this.streetName = streetName;
    }


    /**
     * Gets the streetNumber value for this GeocoderResult.
     * 
     * @return streetNumber
     */
    public int getStreetNumber() {
        return streetNumber;
    }


    /**
     * Sets the streetNumber value for this GeocoderResult.
     * 
     * @param streetNumber
     */
    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }


    /**
     * Gets the streetType value for this GeocoderResult.
     * 
     * @return streetType
     */
    public java.lang.String getStreetType() {
        return streetType;
    }


    /**
     * Sets the streetType value for this GeocoderResult.
     * 
     * @param streetType
     */
    public void setStreetType(java.lang.String streetType) {
        this.streetType = streetType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GeocoderResult)) return false;
        GeocoderResult other = (GeocoderResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.coordinateX == other.getCoordinateX() &&
            this.coordinateY == other.getCoordinateY() &&
            ((this.letra==null && other.getLetra()==null) || 
             (this.letra!=null &&
              this.letra.equals(other.getLetra()))) &&
            ((this.locality==null && other.getLocality()==null) || 
             (this.locality!=null &&
              this.locality.equals(other.getLocality()))) &&
            ((this.matchLevel==null && other.getMatchLevel()==null) || 
             (this.matchLevel!=null &&
              this.matchLevel.equals(other.getMatchLevel()))) &&
            ((this.noMatchInfo==null && other.getNoMatchInfo()==null) || 
             (this.noMatchInfo!=null &&
              this.noMatchInfo.equals(other.getNoMatchInfo()))) &&
            ((this.resultType==null && other.getResultType()==null) || 
             (this.resultType!=null &&
              this.resultType.equals(other.getResultType()))) &&
            ((this.rotulo==null && other.getRotulo()==null) || 
             (this.rotulo!=null &&
              this.rotulo.equals(other.getRotulo()))) &&
            this.similarity == other.getSimilarity() &&
            ((this.streetName==null && other.getStreetName()==null) || 
             (this.streetName!=null &&
              this.streetName.equals(other.getStreetName()))) &&
            this.streetNumber == other.getStreetNumber() &&
            ((this.streetType==null && other.getStreetType()==null) || 
             (this.streetType!=null &&
              this.streetType.equals(other.getStreetType())));
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
        _hashCode += new Double(getCoordinateX()).hashCode();
        _hashCode += new Double(getCoordinateY()).hashCode();
        if (getLetra() != null) {
            _hashCode += getLetra().hashCode();
        }
        if (getLocality() != null) {
            _hashCode += getLocality().hashCode();
        }
        if (getMatchLevel() != null) {
            _hashCode += getMatchLevel().hashCode();
        }
        if (getNoMatchInfo() != null) {
            _hashCode += getNoMatchInfo().hashCode();
        }
        if (getResultType() != null) {
            _hashCode += getResultType().hashCode();
        }
        if (getRotulo() != null) {
            _hashCode += getRotulo().hashCode();
        }
        _hashCode += new Double(getSimilarity()).hashCode();
        if (getStreetName() != null) {
            _hashCode += getStreetName().hashCode();
        }
        _hashCode += getStreetNumber();
        if (getStreetType() != null) {
            _hashCode += getStreetType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GeocoderResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.cdau.juntadeandalucia.es", "GeocoderResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordinateX");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coordinateX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordinateY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coordinateY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("letra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "letra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locality");
        elemField.setXmlName(new javax.xml.namespace.QName("", "locality"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matchLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matchLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noMatchInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "noMatchInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rotulo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rotulo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("similarity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "similarity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetType"));
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
