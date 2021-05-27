
package com.fanduel.external.client.paddypower.impl.jaxws.generated.gap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fanduel.external.client.paddypower.impl.jaxws.generated package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TransferRequest_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "TransferRequest");
    private final static QName _RetrieveBalanceResponse_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "RetrieveBalanceResponse");
    private final static QName _CancelTransferRequest_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "CancelTransferRequest");
    private final static QName _KeepAliveRequest_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "KeepAliveRequest");
    private final static QName _TransferResponse_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "TransferResponse");
    private final static QName _PartnerAPIException_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "PartnerAPIException");
    private final static QName _RetrieveBalanceRequest_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "RetrieveBalanceRequest");
    private final static QName _RetrieveUserSessionDetailsRequest_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "RetrieveUserSessionDetailsRequest");
    private final static QName _KeepAliveResponse_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "KeepAliveResponse");
    private final static QName _RetrieveUserSessionDetailsResponse_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "RetrieveUserSessionDetailsResponse");
    private final static QName _CancelTransferResponse_QNAME = new QName("http://www.betfair.com/servicetypes/v1/PartnerAPI/", "CancelTransferResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fanduel.external.client.paddypower.impl.jaxws.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link KeepAliveResponseType }
     * 
     */
    public KeepAliveResponseType createKeepAliveResponseType() {
        return new KeepAliveResponseType();
    }

    /**
     * Create an instance of {@link CancelTransferResponseType }
     * 
     */
    public CancelTransferResponseType createCancelTransferResponseType() {
        return new CancelTransferResponseType();
    }

    /**
     * Create an instance of {@link RetrieveUserSessionDetailsResponseType }
     * 
     */
    public RetrieveUserSessionDetailsResponseType createRetrieveUserSessionDetailsResponseType() {
        return new RetrieveUserSessionDetailsResponseType();
    }

    /**
     * Create an instance of {@link PartnerAPIExceptionType }
     * 
     */
    public PartnerAPIExceptionType createPartnerAPIExceptionType() {
        return new PartnerAPIExceptionType();
    }

    /**
     * Create an instance of {@link RetrieveBalanceRequestType }
     * 
     */
    public RetrieveBalanceRequestType createRetrieveBalanceRequestType() {
        return new RetrieveBalanceRequestType();
    }

    /**
     * Create an instance of {@link TransferResponseType }
     * 
     */
    public TransferResponseType createTransferResponseType() {
        return new TransferResponseType();
    }

    /**
     * Create an instance of {@link RetrieveUserSessionDetailsRequestType }
     * 
     */
    public RetrieveUserSessionDetailsRequestType createRetrieveUserSessionDetailsRequestType() {
        return new RetrieveUserSessionDetailsRequestType();
    }

    /**
     * Create an instance of {@link CancelTransferRequestType }
     * 
     */
    public CancelTransferRequestType createCancelTransferRequestType() {
        return new CancelTransferRequestType();
    }

    /**
     * Create an instance of {@link KeepAliveRequestType }
     * 
     */
    public KeepAliveRequestType createKeepAliveRequestType() {
        return new KeepAliveRequestType();
    }

    /**
     * Create an instance of {@link RetrieveBalanceResponseType }
     * 
     */
    public RetrieveBalanceResponseType createRetrieveBalanceResponseType() {
        return new RetrieveBalanceResponseType();
    }

    /**
     * Create an instance of {@link TransferRequestType }
     * 
     */
    public TransferRequestType createTransferRequestType() {
        return new TransferRequestType();
    }

    /**
     * Create an instance of {@link ProfileType }
     * 
     */
    public ProfileType createProfileType() {
        return new ProfileType();
    }

    /**
     * Create an instance of {@link ComplexTransferResponseType }
     * 
     */
    public ComplexTransferResponseType createComplexTransferResponseType() {
        return new ComplexTransferResponseType();
    }

    /**
     * Create an instance of {@link JurisdictionDataType }
     * 
     */
    public JurisdictionDataType createJurisdictionDataType() {
        return new JurisdictionDataType();
    }

    /**
     * Create an instance of {@link JurisdictionType }
     * 
     */
    public JurisdictionType createJurisdictionType() {
        return new JurisdictionType();
    }

    /**
     * Create an instance of {@link StringResponseType }
     * 
     */
    public StringResponseType createStringResponseType() {
        return new StringResponseType();
    }

    /**
     * Create an instance of {@link UserSessionDetailsResponseType }
     * 
     */
    public UserSessionDetailsResponseType createUserSessionDetailsResponseType() {
        return new UserSessionDetailsResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "TransferRequest")
    public JAXBElement<TransferRequestType> createTransferRequest(TransferRequestType value) {
        return new JAXBElement<TransferRequestType>(_TransferRequest_QNAME, TransferRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveBalanceResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "RetrieveBalanceResponse")
    public JAXBElement<RetrieveBalanceResponseType> createRetrieveBalanceResponse(RetrieveBalanceResponseType value) {
        return new JAXBElement<RetrieveBalanceResponseType>(_RetrieveBalanceResponse_QNAME, RetrieveBalanceResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelTransferRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "CancelTransferRequest")
    public JAXBElement<CancelTransferRequestType> createCancelTransferRequest(CancelTransferRequestType value) {
        return new JAXBElement<CancelTransferRequestType>(_CancelTransferRequest_QNAME, CancelTransferRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KeepAliveRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "KeepAliveRequest")
    public JAXBElement<KeepAliveRequestType> createKeepAliveRequest(KeepAliveRequestType value) {
        return new JAXBElement<KeepAliveRequestType>(_KeepAliveRequest_QNAME, KeepAliveRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "TransferResponse")
    public JAXBElement<TransferResponseType> createTransferResponse(TransferResponseType value) {
        return new JAXBElement<TransferResponseType>(_TransferResponse_QNAME, TransferResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartnerAPIExceptionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "PartnerAPIException")
    public JAXBElement<PartnerAPIExceptionType> createPartnerAPIException(PartnerAPIExceptionType value) {
        return new JAXBElement<PartnerAPIExceptionType>(_PartnerAPIException_QNAME, PartnerAPIExceptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveBalanceRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "RetrieveBalanceRequest")
    public JAXBElement<RetrieveBalanceRequestType> createRetrieveBalanceRequest(RetrieveBalanceRequestType value) {
        return new JAXBElement<RetrieveBalanceRequestType>(_RetrieveBalanceRequest_QNAME, RetrieveBalanceRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveUserSessionDetailsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "RetrieveUserSessionDetailsRequest")
    public JAXBElement<RetrieveUserSessionDetailsRequestType> createRetrieveUserSessionDetailsRequest(RetrieveUserSessionDetailsRequestType value) {
        return new JAXBElement<RetrieveUserSessionDetailsRequestType>(_RetrieveUserSessionDetailsRequest_QNAME, RetrieveUserSessionDetailsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KeepAliveResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "KeepAliveResponse")
    public JAXBElement<KeepAliveResponseType> createKeepAliveResponse(KeepAliveResponseType value) {
        return new JAXBElement<KeepAliveResponseType>(_KeepAliveResponse_QNAME, KeepAliveResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveUserSessionDetailsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "RetrieveUserSessionDetailsResponse")
    public JAXBElement<RetrieveUserSessionDetailsResponseType> createRetrieveUserSessionDetailsResponse(RetrieveUserSessionDetailsResponseType value) {
        return new JAXBElement<RetrieveUserSessionDetailsResponseType>(_RetrieveUserSessionDetailsResponse_QNAME, RetrieveUserSessionDetailsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelTransferResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.betfair.com/servicetypes/v1/PartnerAPI/", name = "CancelTransferResponse")
    public JAXBElement<CancelTransferResponseType> createCancelTransferResponse(CancelTransferResponseType value) {
        return new JAXBElement<CancelTransferResponseType>(_CancelTransferResponse_QNAME, CancelTransferResponseType.class, null, value);
    }

}
