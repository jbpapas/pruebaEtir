/**
 * NucleoPoblacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.juntadeandalucia.cdau.dto;

public class NucleoPoblacion  implements java.io.Serializable {
    private es.juntadeandalucia.callejero.util.BBox extent;

    private int idNucleo;

    private java.lang.String municipio;

    private java.lang.String nombre;

    private java.lang.String tipo;

    public NucleoPoblacion() {
    }

    public NucleoPoblacion(
           es.juntadeandalucia.callejero.util.BBox extent,
           int idNucleo,
           java.lang.String municipio,
           java.lang.String nombre,
           java.lang.String tipo) {
           this.extent = extent;
           this.idNucleo = idNucleo;
           this.municipio = municipio;
           this.nombre = nombre;
           this.tipo = tipo;
    }


    /**
     * Gets the extent value for this NucleoPoblacion.
     * 
     * @return extent
     */
    public es.juntadeandalucia.callejero.util.BBox getExtent() {
        return extent;
    }


    /**
     * Sets the extent value for this NucleoPoblacion.
     * 
     * @param extent
     */
    public void setExtent(es.juntadeandalucia.callejero.util.BBox extent) {
        this.extent = extent;
    }


    /**
     * Gets the idNucleo value for this NucleoPoblacion.
     * 
     * @return idNucleo
     */
    public int getIdNucleo() {
        return idNucleo;
    }


    /**
     * Sets the idNucleo value for this NucleoPoblacion.
     * 
     * @param idNucleo
     */
    public void setIdNucleo(int idNucleo) {
        this.idNucleo = idNucleo;
    }


    /**
     * Gets the municipio value for this NucleoPoblacion.
     * 
     * @return municipio
     */
    public java.lang.String getMunicipio() {
        return municipio;
    }


    /**
     * Sets the municipio value for this NucleoPoblacion.
     * 
     * @param municipio
     */
    public void setMunicipio(java.lang.String municipio) {
        this.municipio = municipio;
    }


    /**
     * Gets the nombre value for this NucleoPoblacion.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this NucleoPoblacion.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the tipo value for this NucleoPoblacion.
     * 
     * @return tipo
     */
    public java.lang.String getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this NucleoPoblacion.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.String tipo) {
        this.tipo = tipo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NucleoPoblacion)) return false;
        NucleoPoblacion other = (NucleoPoblacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.extent==null && other.getExtent()==null) || 
             (this.extent!=null &&
              this.extent.equals(other.getExtent()))) &&
            this.idNucleo == other.getIdNucleo() &&
            ((this.municipio==null && other.getMunicipio()==null) || 
             (this.municipio!=null &&
              this.municipio.equals(other.getMunicipio()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo())));
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
        if (getExtent() != null) {
            _hashCode += getExtent().hashCode();
        }
        _hashCode += getIdNucleo();
        if (getMunicipio() != null) {
            _hashCode += getMunicipio().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NucleoPoblacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.cdau.juntadeandalucia.es", "NucleoPoblacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://util.callejero.juntadeandalucia.es", "BBox"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idNucleo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idNucleo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "municipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipo"));
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
