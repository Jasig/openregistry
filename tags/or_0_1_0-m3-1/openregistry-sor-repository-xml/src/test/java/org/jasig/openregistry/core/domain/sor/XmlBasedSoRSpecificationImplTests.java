package org.jasig.openregistry.core.domain.sor;

import junit.framework.TestCase;
import org.openregistry.core.domain.sor.SystemOfRecord;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: battags
 * Date: Dec 15, 2009
 * Time: 3:21:41 PM
 * To change this template use File | Settings | File Templates.
 */
public final class XmlBasedSoRSpecificationImplTests extends TestCase {

    private JAXBContext jaxbContext;

    private Marshaller marshaller;

    protected void setUp() throws Exception {
        this.jaxbContext = JAXBContext.newInstance(XmlBasedSoRSpecificationImpl.class);
        this.marshaller = jaxbContext.createMarshaller();
    }

    public void testMarshall() throws Exception {
        final XmlBasedSoRSpecificationImpl spec = new XmlBasedSoRSpecificationImpl();

        // ******** SOR **********/
        spec.setSor("fooBar");

        // ******** VALUES FOR REQUIRED PROPERTIES **********/
        final HashSet<String> requiredProperties = new HashSet<String>();
        requiredProperties.add("required.property.1");
        requiredProperties.add("required.property.2");
        requiredProperties.add("required.property.3");
        spec.setRequiredProperties(requiredProperties);

        // ******** VALUES FOR PROPERTIES **********/
        final Map<String, HashSet<String>> allowedValuesForProperty = new HashMap<String, HashSet<String>>();
        final HashSet<String> values = new HashSet<String>();
        values.add("FOO");
        values.add("BAR");
        allowedValuesForProperty.put("test.property", values);
        allowedValuesForProperty.put("test.property2", values);

        spec.setAllowedValuesForProperty(allowedValuesForProperty);

        // ******** DISALLOWED PROPERTIES **********/
        final HashSet<String> disallowedProperties = new HashSet<String>();
        disallowedProperties.add("foo.bar");
        disallowedProperties.add("my.bar");

        spec.setDisallowedProperties(disallowedProperties);

        // ******** INTERFACES **********/
        final HashSet<SystemOfRecord.Interfaces> interfaces = new HashSet<SystemOfRecord.Interfaces>();
        interfaces.add(SystemOfRecord.Interfaces.BATCH);
        interfaces.add(SystemOfRecord.Interfaces.HUMAN);

        spec.setInterfaces(interfaces);

        final Map<SystemOfRecord.Interfaces,String> notification = new HashMap<SystemOfRecord.Interfaces, String>();
        notification.put(SystemOfRecord.Interfaces.HUMAN, "email");
        notification.put(SystemOfRecord.Interfaces.BATCH, "letter");

        spec.setInterfaceToNotificationSchemeMapping(notification);

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        this.marshaller.marshal(spec, byteArrayOutputStream);

        System.out.println(byteArrayOutputStream.toString().replaceAll("><", ">\n<"));
    }
    

}
