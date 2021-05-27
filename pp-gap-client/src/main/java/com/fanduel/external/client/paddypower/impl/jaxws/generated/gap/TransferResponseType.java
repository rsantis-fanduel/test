
package com.fanduel.external.client.paddypower.impl.jaxws.generated.gap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransferResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransferResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="response" type="{http://www.betfair.com/servicetypes/v1/PartnerAPI/}ComplexTransferResponseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransferResponseType", propOrder = {
    "response"
})
public class TransferResponseType {

    @XmlElement(required = true)
    protected ComplexTransferResponseType response;

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexTransferResponseType }
     *     
     */
    public ComplexTransferResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexTransferResponseType }
     *     
     */
    public void setResponse(ComplexTransferResponseType value) {
        this.response = value;
    }

}
