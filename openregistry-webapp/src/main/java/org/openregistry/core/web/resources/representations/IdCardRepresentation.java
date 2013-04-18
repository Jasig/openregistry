package org.openregistry.core.web.resources.representations;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * A Jaxb Xml Representation of Idcard
 *
 * @Author Muhammad Siddique
 */
@XmlRootElement(name = "IdCard")
public class IdCardRepresentation {

    @XmlElement
    private String personId;
    @XmlElement
    private String   cardNumber;
    @XmlElement
    private String cardSecurityValue;
    @XmlElement
    private String proximityNumber;
    @XmlElement
    private String barCode;


    public IdCardRepresentation(){

    }
    public IdCardRepresentation(String personId,String cardNumber,String proximityNumber,String barCode,String cardSecurityValue){

        this.personId=personId;
        this.cardNumber=cardNumber;
        this.cardSecurityValue=cardSecurityValue;
        this.proximityNumber=proximityNumber;
        this.barCode=barCode;

    }







}
