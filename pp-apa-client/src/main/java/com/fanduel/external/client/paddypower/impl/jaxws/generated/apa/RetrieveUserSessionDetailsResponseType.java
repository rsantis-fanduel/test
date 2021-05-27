
package com.fanduel.external.client.paddypower.impl.jaxws.generated.apa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetrieveUserSessionDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetrieveUserSessionDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="response" type="{http://www.betfair.com/servicetypes/v1/PartnerAPI/}UserSessionDetailsResponseType"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrieveUserSessionDetailsResponseType", propOrder = {

})
public class RetrieveUserSessionDetailsResponseType {

    @XmlElement(required = true)
    protected UserSessionDetailsResponseType response;

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link UserSessionDetailsResponseType }
     *     
     */
    public UserSessionDetailsResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserSessionDetailsResponseType }
     *     
     */
    public void setResponse(UserSessionDetailsResponseType value) {
        this.response = value;
    }

}
