
package com.fanduel.external.client.paddypower.impl.jaxws.generated.gap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CancelTransferRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CancelTransferRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transferRef" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cancelTransferRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelTransferRequestType", propOrder = {
    "transferRef",
    "cancelTransferRef"
})
public class CancelTransferRequestType {

    @XmlElement(required = true)
    protected String transferRef;
    protected String cancelTransferRef;

    /**
     * Gets the value of the transferRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransferRef() {
        return transferRef;
    }

    /**
     * Sets the value of the transferRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransferRef(String value) {
        this.transferRef = value;
    }

    /**
     * Gets the value of the cancelTransferRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancelTransferRef() {
        return cancelTransferRef;
    }

    /**
     * Sets the value of the cancelTransferRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelTransferRef(String value) {
        this.cancelTransferRef = value;
    }

}
