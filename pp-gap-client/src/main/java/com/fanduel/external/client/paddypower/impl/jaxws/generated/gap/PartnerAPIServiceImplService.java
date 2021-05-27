
package com.fanduel.external.client.paddypower.impl.jaxws.generated.gap;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "PartnerAPIServiceImplService", targetNamespace = "http://endpoint.web.api.games.betfair.com/", wsdlLocation = "file:/Users/denisbarbeito/Development/github/external-clients/paddypower-gap-client/src/main/resources/PP-PartnerApi_Service.wsdl")
public class PartnerAPIServiceImplService
    extends Service
{

    private final static URL PARTNERAPISERVICEIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException PARTNERAPISERVICEIMPLSERVICE_EXCEPTION;
    private final static QName PARTNERAPISERVICEIMPLSERVICE_QNAME = new QName("http://endpoint.web.api.games.betfair.com/", "PartnerAPIServiceImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/Users/denisbarbeito/Development/github/external-clients/paddypower-gap-client/src/main/resources/PP-PartnerApi_Service.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PARTNERAPISERVICEIMPLSERVICE_WSDL_LOCATION = url;
        PARTNERAPISERVICEIMPLSERVICE_EXCEPTION = e;
    }

    public PartnerAPIServiceImplService() {
        super(__getWsdlLocation(), PARTNERAPISERVICEIMPLSERVICE_QNAME);
    }

    public PartnerAPIServiceImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PARTNERAPISERVICEIMPLSERVICE_QNAME, features);
    }

    public PartnerAPIServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, PARTNERAPISERVICEIMPLSERVICE_QNAME);
    }

    public PartnerAPIServiceImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PARTNERAPISERVICEIMPLSERVICE_QNAME, features);
    }

    public PartnerAPIServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PartnerAPIServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns PartnerAPIService
     */
    @WebEndpoint(name = "PartnerAPIServiceImplPort")
    public PartnerAPIService getPartnerAPIServiceImplPort() {
        return super.getPort(new QName("http://endpoint.web.api.games.betfair.com/", "PartnerAPIServiceImplPort"), PartnerAPIService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PartnerAPIService
     */
    @WebEndpoint(name = "PartnerAPIServiceImplPort")
    public PartnerAPIService getPartnerAPIServiceImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://endpoint.web.api.games.betfair.com/", "PartnerAPIServiceImplPort"), PartnerAPIService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PARTNERAPISERVICEIMPLSERVICE_EXCEPTION!= null) {
            throw PARTNERAPISERVICEIMPLSERVICE_EXCEPTION;
        }
        return PARTNERAPISERVICEIMPLSERVICE_WSDL_LOCATION;
    }

}
