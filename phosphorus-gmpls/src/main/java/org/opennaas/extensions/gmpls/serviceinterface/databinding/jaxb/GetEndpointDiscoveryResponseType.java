
package org.opennaas.extensions.gmpls.serviceinterface.databinding.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getEndpointDiscoveryResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getEndpointDiscoveryResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Endpoint" type="{http://ist_phosphorus.eu/gmpls/webservice}EndpointType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEndpointDiscoveryResponseType", propOrder = {
    "endpoint"
})
public class GetEndpointDiscoveryResponseType {

    @XmlElement(name = "Endpoint")
    protected List<EndpointType> endpoint;

    /**
     * Gets the value of the endpoint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the endpoint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEndpoint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EndpointType }
     * 
     * 
     */
    public List<EndpointType> getEndpoint() {
        if (endpoint == null) {
            endpoint = new ArrayList<EndpointType>();
        }
        return this.endpoint;
    }

}
