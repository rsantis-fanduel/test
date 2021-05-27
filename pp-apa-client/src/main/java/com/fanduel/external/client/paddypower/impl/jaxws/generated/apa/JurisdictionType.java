
package com.fanduel.external.client.paddypower.impl.jaxws.generated.apa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for JurisdictionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JurisdictionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="jurisdictionData" type="{http://www.betfair.com/servicetypes/v1/PartnerAPI/}JurisdictionDataType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JurisdictionType", propOrder = {

})
public class JurisdictionType {

    @XmlElement(required = true)
    protected String name;
    protected JurisdictionDataType jurisdictionData;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the jurisdictionData property.
     * 
     * @return
     *     possible object is
     *     {@link JurisdictionDataType }
     *     
     */
    public JurisdictionDataType getJurisdictionData() {
        return jurisdictionData;
    }

    /**
     * Sets the value of the jurisdictionData property.
     * 
     * @param value
     *     allowed object is
     *     {@link JurisdictionDataType }
     *     
     */
    public void setJurisdictionData(JurisdictionDataType value) {
        this.jurisdictionData = value;
    }

}
