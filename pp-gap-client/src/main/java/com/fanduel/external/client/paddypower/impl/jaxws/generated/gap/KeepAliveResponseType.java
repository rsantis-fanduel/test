
package com.fanduel.external.client.paddypower.impl.jaxws.generated.gap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KeepAliveResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KeepAliveResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="response" type="{http://www.betfair.com/servicetypes/v1/PartnerAPI/}StringResponseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeepAliveResponseType", propOrder = {
    "response"
})
public class KeepAliveResponseType {

    @XmlElement(required = true)
    protected StringResponseType response;

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link StringResponseType }
     *     
     */
    public StringResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringResponseType }
     *     
     */
    public void setResponse(StringResponseType value) {
        this.response = value;
    }

}
