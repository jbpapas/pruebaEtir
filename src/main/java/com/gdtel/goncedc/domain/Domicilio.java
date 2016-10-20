/**
 * Domicilio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gdtel.goncedc.domain;

public class Domicilio  implements java.io.Serializable {
    private java.lang.String bloque;

    private java.lang.String codPostal;

    private java.lang.String escalera;

    private java.lang.Integer id;

    private java.lang.String letra;

    private java.lang.String municipio;

    private java.lang.String numero;

    private java.lang.String planta;

    private java.lang.String provincia;

    private java.lang.String puerta;

    private java.lang.String siglas;

    private java.lang.String ubicacion;

    private java.lang.String via;

    public Domicilio() {
    }

    public Domicilio(
           java.lang.String bloque,
           java.lang.String codPostal,
           java.lang.String escalera,
           java.lang.Integer id,
           java.lang.String letra,
           java.lang.String municipio,
           java.lang.String numero,
           java.lang.String planta,
           java.lang.String provincia,
           java.lang.String puerta,
           java.lang.String siglas,
           java.lang.String ubicacion,
           java.lang.String via) {
           this.bloque = bloque;
           this.codPostal = codPostal;
           this.escalera = escalera;
           this.id = id;
           this.letra = letra;
           this.municipio = municipio;
           this.numero = numero;
           this.planta = planta;
           this.provincia = provincia;
           this.puerta = puerta;
           this.siglas = siglas;
           this.ubicacion = ubicacion;
           this.via = via;
    }


    /**
     * Gets the bloque value for this Domicilio.
     * 
     * @return bloque
     */
    public java.lang.String getBloque() {
        return bloque;
    }


    /**
     * Sets the bloque value for this Domicilio.
     * 
     * @param bloque
     */
    public void setBloque(java.lang.String bloque) {
        this.bloque = bloque;
    }


    /**
     * Gets the codPostal value for this Domicilio.
     * 
     * @return codPostal
     */
    public java.lang.String getCodPostal() {
        return codPostal;
    }


    /**
     * Sets the codPostal value for this Domicilio.
     * 
     * @param codPostal
     */
    public void setCodPostal(java.lang.String codPostal) {
        this.codPostal = codPostal;
    }


    /**
     * Gets the escalera value for this Domicilio.
     * 
     * @return escalera
     */
    public java.lang.String getEscalera() {
        return escalera;
    }


    /**
     * Sets the escalera value for this Domicilio.
     * 
     * @param escalera
     */
    public void setEscalera(java.lang.String escalera) {
        this.escalera = escalera;
    }


    /**
     * Gets the id value for this Domicilio.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this Domicilio.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the letra value for this Domicilio.
     * 
     * @return letra
     */
    public java.lang.String getLetra() {
        return letra;
    }


    /**
     * Sets the letra value for this Domicilio.
     * 
     * @param letra
     */
    public void setLetra(java.lang.String letra) {
        this.letra = letra;
    }


    /**
     * Gets the municipio value for this Domicilio.
     * 
     * @return municipio
     */
    public java.lang.String getMunicipio() {
        return municipio;
    }


    /**
     * Sets the municipio value for this Domicilio.
     * 
     * @param municipio
     */
    public void setMunicipio(java.lang.String municipio) {
        this.municipio = municipio;
    }


    /**
     * Gets the numero value for this Domicilio.
     * 
     * @return numero
     */
    public java.lang.String getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this Domicilio.
     * 
     * @param numero
     */
    public void setNumero(java.lang.String numero) {
        this.numero = numero;
    }


    /**
     * Gets the planta value for this Domicilio.
     * 
     * @return planta
     */
    public java.lang.String getPlanta() {
        return planta;
    }


    /**
     * Sets the planta value for this Domicilio.
     * 
     * @param planta
     */
    public void setPlanta(java.lang.String planta) {
        this.planta = planta;
    }


    /**
     * Gets the provincia value for this Domicilio.
     * 
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this Domicilio.
     * 
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the puerta value for this Domicilio.
     * 
     * @return puerta
     */
    public java.lang.String getPuerta() {
        return puerta;
    }


    /**
     * Sets the puerta value for this Domicilio.
     * 
     * @param puerta
     */
    public void setPuerta(java.lang.String puerta) {
        this.puerta = puerta;
    }


    /**
     * Gets the siglas value for this Domicilio.
     * 
     * @return siglas
     */
    public java.lang.String getSiglas() {
        return siglas;
    }


    /**
     * Sets the siglas value for this Domicilio.
     * 
     * @param siglas
     */
    public void setSiglas(java.lang.String siglas) {
        this.siglas = siglas;
    }


    /**
     * Gets the ubicacion value for this Domicilio.
     * 
     * @return ubicacion
     */
    public java.lang.String getUbicacion() {
        return ubicacion;
    }


    /**
     * Sets the ubicacion value for this Domicilio.
     * 
     * @param ubicacion
     */
    public void setUbicacion(java.lang.String ubicacion) {
        this.ubicacion = ubicacion;
    }


    /**
     * Gets the via value for this Domicilio.
     * 
     * @return via
     */
    public java.lang.String getVia() {
        return via;
    }


    /**
     * Sets the via value for this Domicilio.
     * 
     * @param via
     */
    public void setVia(java.lang.String via) {
        this.via = via;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Domicilio)) return false;
        Domicilio other = (Domicilio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bloque==null && other.getBloque()==null) || 
             (this.bloque!=null &&
              this.bloque.equals(other.getBloque()))) &&
            ((this.codPostal==null && other.getCodPostal()==null) || 
             (this.codPostal!=null &&
              this.codPostal.equals(other.getCodPostal()))) &&
            ((this.escalera==null && other.getEscalera()==null) || 
             (this.escalera!=null &&
              this.escalera.equals(other.getEscalera()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.letra==null && other.getLetra()==null) || 
             (this.letra!=null &&
              this.letra.equals(other.getLetra()))) &&
            ((this.municipio==null && other.getMunicipio()==null) || 
             (this.municipio!=null &&
              this.municipio.equals(other.getMunicipio()))) &&
            ((this.numero==null && other.getNumero()==null) || 
             (this.numero!=null &&
              this.numero.equals(other.getNumero()))) &&
            ((this.planta==null && other.getPlanta()==null) || 
             (this.planta!=null &&
              this.planta.equals(other.getPlanta()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia()))) &&
            ((this.puerta==null && other.getPuerta()==null) || 
             (this.puerta!=null &&
              this.puerta.equals(other.getPuerta()))) &&
            ((this.siglas==null && other.getSiglas()==null) || 
             (this.siglas!=null &&
              this.siglas.equals(other.getSiglas()))) &&
            ((this.ubicacion==null && other.getUbicacion()==null) || 
             (this.ubicacion!=null &&
              this.ubicacion.equals(other.getUbicacion()))) &&
            ((this.via==null && other.getVia()==null) || 
             (this.via!=null &&
              this.via.equals(other.getVia())));
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
        if (getBloque() != null) {
            _hashCode += getBloque().hashCode();
        }
        if (getCodPostal() != null) {
            _hashCode += getCodPostal().hashCode();
        }
        if (getEscalera() != null) {
            _hashCode += getEscalera().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getLetra() != null) {
            _hashCode += getLetra().hashCode();
        }
        if (getMunicipio() != null) {
            _hashCode += getMunicipio().hashCode();
        }
        if (getNumero() != null) {
            _hashCode += getNumero().hashCode();
        }
        if (getPlanta() != null) {
            _hashCode += getPlanta().hashCode();
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        if (getPuerta() != null) {
            _hashCode += getPuerta().hashCode();
        }
        if (getSiglas() != null) {
            _hashCode += getSiglas().hashCode();
        }
        if (getUbicacion() != null) {
            _hashCode += getUbicacion().hashCode();
        }
        if (getVia() != null) {
            _hashCode += getVia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Domicilio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "Domicilio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bloque");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "bloque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codPostal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "codPostal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("escalera");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "escalera"));
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
        elemField.setFieldName("letra");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "letra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "municipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "numero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("planta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "planta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "provincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puerta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "puerta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siglas");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "siglas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ubicacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "ubicacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("via");
        elemField.setXmlName(new javax.xml.namespace.QName("http://domain.goncedc.gdtel.com", "via"));
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
