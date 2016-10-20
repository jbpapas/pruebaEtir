/**
 * Address.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.juntadeandalucia.cdau.dto;

public class Address  implements java.io.Serializable {
    private int codigoPostal;

    private java.lang.String complementos;

    private java.lang.String direccionNormalizada;

    private java.lang.String direccionSinNormalizar;

    private java.lang.String municipio;

    private java.lang.String nombreVia;

    private java.lang.String numeroPortal;

    private java.lang.String provincia;

    private java.lang.String tipoVia;

    public Address() {
    }

    public Address(
           int codigoPostal,
           java.lang.String complementos,
           java.lang.String direccionNormalizada,
           java.lang.String direccionSinNormalizar,
           java.lang.String municipio,
           java.lang.String nombreVia,
           java.lang.String numeroPortal,
           java.lang.String provincia,
           java.lang.String tipoVia) {
           this.codigoPostal = codigoPostal;
           this.complementos = complementos;
           this.direccionNormalizada = direccionNormalizada;
           this.direccionSinNormalizar = direccionSinNormalizar;
           this.municipio = municipio;
           this.nombreVia = nombreVia;
           this.numeroPortal = numeroPortal;
           this.provincia = provincia;
           this.tipoVia = tipoVia;
    }


    /**
     * Gets the codigoPostal value for this Address.
     * 
     * @return codigoPostal
     */
    public int getCodigoPostal() {
        return codigoPostal;
    }


    /**
     * Sets the codigoPostal value for this Address.
     * 
     * @param codigoPostal
     */
    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }


    /**
     * Gets the complementos value for this Address.
     * 
     * @return complementos
     */
    public java.lang.String getComplementos() {
        return complementos;
    }


    /**
     * Sets the complementos value for this Address.
     * 
     * @param complementos
     */
    public void setComplementos(java.lang.String complementos) {
        this.complementos = complementos;
    }


    /**
     * Gets the direccionNormalizada value for this Address.
     * 
     * @return direccionNormalizada
     */
    public java.lang.String getDireccionNormalizada() {
        return direccionNormalizada;
    }


    /**
     * Sets the direccionNormalizada value for this Address.
     * 
     * @param direccionNormalizada
     */
    public void setDireccionNormalizada(java.lang.String direccionNormalizada) {
        this.direccionNormalizada = direccionNormalizada;
    }


    /**
     * Gets the direccionSinNormalizar value for this Address.
     * 
     * @return direccionSinNormalizar
     */
    public java.lang.String getDireccionSinNormalizar() {
        return direccionSinNormalizar;
    }


    /**
     * Sets the direccionSinNormalizar value for this Address.
     * 
     * @param direccionSinNormalizar
     */
    public void setDireccionSinNormalizar(java.lang.String direccionSinNormalizar) {
        this.direccionSinNormalizar = direccionSinNormalizar;
    }


    /**
     * Gets the municipio value for this Address.
     * 
     * @return municipio
     */
    public java.lang.String getMunicipio() {
        return municipio;
    }


    /**
     * Sets the municipio value for this Address.
     * 
     * @param municipio
     */
    public void setMunicipio(java.lang.String municipio) {
        this.municipio = municipio;
    }


    /**
     * Gets the nombreVia value for this Address.
     * 
     * @return nombreVia
     */
    public java.lang.String getNombreVia() {
        return nombreVia;
    }


    /**
     * Sets the nombreVia value for this Address.
     * 
     * @param nombreVia
     */
    public void setNombreVia(java.lang.String nombreVia) {
        this.nombreVia = nombreVia;
    }


    /**
     * Gets the numeroPortal value for this Address.
     * 
     * @return numeroPortal
     */
    public java.lang.String getNumeroPortal() {
        return numeroPortal;
    }


    /**
     * Sets the numeroPortal value for this Address.
     * 
     * @param numeroPortal
     */
    public void setNumeroPortal(java.lang.String numeroPortal) {
        this.numeroPortal = numeroPortal;
    }


    /**
     * Gets the provincia value for this Address.
     * 
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this Address.
     * 
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the tipoVia value for this Address.
     * 
     * @return tipoVia
     */
    public java.lang.String getTipoVia() {
        return tipoVia;
    }


    /**
     * Sets the tipoVia value for this Address.
     * 
     * @param tipoVia
     */
    public void setTipoVia(java.lang.String tipoVia) {
        this.tipoVia = tipoVia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Address)) return false;
        Address other = (Address) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigoPostal == other.getCodigoPostal() &&
            ((this.complementos==null && other.getComplementos()==null) || 
             (this.complementos!=null &&
              this.complementos.equals(other.getComplementos()))) &&
            ((this.direccionNormalizada==null && other.getDireccionNormalizada()==null) || 
             (this.direccionNormalizada!=null &&
              this.direccionNormalizada.equals(other.getDireccionNormalizada()))) &&
            ((this.direccionSinNormalizar==null && other.getDireccionSinNormalizar()==null) || 
             (this.direccionSinNormalizar!=null &&
              this.direccionSinNormalizar.equals(other.getDireccionSinNormalizar()))) &&
            ((this.municipio==null && other.getMunicipio()==null) || 
             (this.municipio!=null &&
              this.municipio.equals(other.getMunicipio()))) &&
            ((this.nombreVia==null && other.getNombreVia()==null) || 
             (this.nombreVia!=null &&
              this.nombreVia.equals(other.getNombreVia()))) &&
            ((this.numeroPortal==null && other.getNumeroPortal()==null) || 
             (this.numeroPortal!=null &&
              this.numeroPortal.equals(other.getNumeroPortal()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia()))) &&
            ((this.tipoVia==null && other.getTipoVia()==null) || 
             (this.tipoVia!=null &&
              this.tipoVia.equals(other.getTipoVia())));
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
        _hashCode += getCodigoPostal();
        if (getComplementos() != null) {
            _hashCode += getComplementos().hashCode();
        }
        if (getDireccionNormalizada() != null) {
            _hashCode += getDireccionNormalizada().hashCode();
        }
        if (getDireccionSinNormalizar() != null) {
            _hashCode += getDireccionSinNormalizar().hashCode();
        }
        if (getMunicipio() != null) {
            _hashCode += getMunicipio().hashCode();
        }
        if (getNombreVia() != null) {
            _hashCode += getNombreVia().hashCode();
        }
        if (getNumeroPortal() != null) {
            _hashCode += getNumeroPortal().hashCode();
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        if (getTipoVia() != null) {
            _hashCode += getTipoVia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Address.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.cdau.juntadeandalucia.es", "Address"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPostal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoPostal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complementos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "complementos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccionNormalizada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direccionNormalizada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direccionSinNormalizar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "direccionSinNormalizar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "municipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreVia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroPortal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroPortal"));
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
        elemField.setFieldName("tipoVia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoVia"));
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
