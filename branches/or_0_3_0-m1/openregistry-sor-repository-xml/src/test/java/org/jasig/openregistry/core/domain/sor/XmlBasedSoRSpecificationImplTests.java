/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.openregistry.core.domain.sor;

import junit.framework.TestCase;
import org.junit.Test;
import org.openregistry.core.domain.sor.SoRSpecification;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/**
 * Tests for XML-implementation of SoRSpecification.
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 0.1
 */
public final class XmlBasedSoRSpecificationImplTests extends TestCase {

    private JAXBContext jaxbContext;

    private Marshaller marshaller;

    private Unmarshaller unMarshaller;

    protected void setUp() throws Exception {
        this.jaxbContext = JAXBContext.newInstance(XmlBasedSoRSpecificationImpl.class);
        this.marshaller = jaxbContext.createMarshaller();
        this.unMarshaller = jaxbContext.createUnmarshaller();
    }

    @Test
    public void testGetterForSoR() {
        final String SOR = "TEST_SOR";
        final XmlBasedSoRSpecificationImpl test = new XmlBasedSoRSpecificationImpl();
        test.setSor(SOR);

        assertEquals(SOR, test.getSoR());
    }

    @Test
    public void testIsInboundInterfaceAllowed() {
         final XmlBasedSoRSpecificationImpl test = new XmlBasedSoRSpecificationImpl();

        assertFalse(test.isInboundInterfaceAllowed(SoRSpecification.Interfaces.HUMAN));

        final HashSet<SoRSpecification.Interfaces> interfaces = new HashSet<SoRSpecification.Interfaces>();
        interfaces.add(SoRSpecification.Interfaces.HUMAN);
        interfaces.add(SoRSpecification.Interfaces.BATCH);

        test.setInterfaces(interfaces);

        assertTrue(test.isInboundInterfaceAllowed(SoRSpecification.Interfaces.HUMAN));
        assertTrue(test.isInboundInterfaceAllowed(SoRSpecification.Interfaces.BATCH));
        assertFalse(test.isInboundInterfaceAllowed(SoRSpecification.Interfaces.REALTIME));
    }

    @Test
    public void testGetNotificationSchemesByInterface() {
        final XmlBasedSoRSpecificationImpl test = new XmlBasedSoRSpecificationImpl();

        final Map<SoRSpecification.Interfaces, String> interfaceMapping = new HashMap<SoRSpecification.Interfaces, String>();

        interfaceMapping.put(SoRSpecification.Interfaces.HUMAN, "email");
        interfaceMapping.put(SoRSpecification.Interfaces.BATCH, "voice");

        test.setInterfaceToNotificationSchemeMapping(interfaceMapping);

        assertEquals(interfaceMapping, test.getNotificationSchemesByInterface());

        assertEquals("email", test.getNotificationSchemesByInterface().get(SoRSpecification.Interfaces.HUMAN));
        assertEquals("voice", test.getNotificationSchemesByInterface().get(SoRSpecification.Interfaces.BATCH));
        assertNull(test.getNotificationSchemesByInterface().get(SoRSpecification.Interfaces.REALTIME));
    }

    @Test
    public void testIsAllowedValueForProperty() {
        final XmlBasedSoRSpecificationImpl test = new XmlBasedSoRSpecificationImpl();
        final HashSet<XmlBasedAllowValueHelperImpl> values = new HashSet<XmlBasedAllowValueHelperImpl>();

        final XmlBasedAllowValueHelperImpl value = new XmlBasedAllowValueHelperImpl();
        final HashSet<String> propertyValues = new HashSet<String>();
        value.setDomainProperty("domain.property");
        propertyValues.add("HA");
        propertyValues.add("HA2");

        value.setAllowedValues(propertyValues);
        values.add(value);

        test.setAllowedValuesForProperty(values);

        assertTrue(test.isAllowedValueForProperty("domain.property", "HA"));
        assertTrue(test.isAllowedValueForProperty("domain.property", "HA2"));
        assertFalse(test.isAllowedValueForProperty("domain.property", "HA3"));
        assertTrue(test.isAllowedValueForProperty("domain.property1", "HA2"));
    }

    @Test
    public void testIsRequiredProperty() {
        final XmlBasedSoRSpecificationImpl test = new XmlBasedSoRSpecificationImpl();
        final HashSet<String> requiredProperties = new HashSet<String>();

        requiredProperties.add("required.1");
        requiredProperties.add("required.2");

        test.setRequiredProperties(requiredProperties);

        assertTrue(test.isRequiredProperty("required.1"));
        assertTrue(test.isRequiredProperty("required.2"));
        assertFalse(test.isRequiredProperty("required.3"));
    }

    public void testIsDisallowedProperty() {
        final XmlBasedSoRSpecificationImpl test = new XmlBasedSoRSpecificationImpl();
        final HashSet<String> disallowedProperties = new HashSet<String>();

        disallowedProperties.add("required.1");
        disallowedProperties.add("required.2");

        test.setDisallowedProperties(disallowedProperties);

        assertTrue(test.isDisallowedProperty("required.1"));
        assertTrue(test.isDisallowedProperty("required.2"));
        assertFalse(test.isDisallowedProperty("required.3"));
    }

    public void testIsWithinRequiredSize() {
        final XmlBasedSoRSpecificationImpl test = new XmlBasedSoRSpecificationImpl();
        final HashSet<XmlBasedPropertySizeHelperImpl> properties = new HashSet<XmlBasedPropertySizeHelperImpl>();

        final XmlBasedPropertySizeHelperImpl helper = new XmlBasedPropertySizeHelperImpl();
        helper.setProperty("property");
        helper.setMin(1);
        helper.setMax(3);

        properties.add(helper);

        test.setMinMaxPropertySizes(properties);

        final Collection<String> emptyCollection = new ArrayList<String>();
        final Collection<String> minSize = new ArrayList<String>();
        minSize.add("1");

        final Collection<String> maxSize = new ArrayList<String>();
        maxSize.add("1");
        maxSize.add("2");
        maxSize.add("3");

        final Collection<String> overSized = new ArrayList<String>();
        overSized.add("1");
        overSized.add("2");
        overSized.add("3");
        overSized.add("4");

        final Collection<String> inRange = new ArrayList<String>();
        inRange.add("1");
        inRange.add("2");

        assertFalse(test.isWithinRequiredSize("property",emptyCollection));
        assertTrue(test.isWithinRequiredSize("property",minSize));
        assertTrue(test.isWithinRequiredSize("property",maxSize));
        assertTrue(test.isWithinRequiredSize("property",inRange));
        assertFalse(test.isWithinRequiredSize("property",overSized));
        assertTrue(test.isWithinRequiredSize("property1",overSized));
        assertTrue(test.isWithinRequiredSize("property1",emptyCollection));
    }

    @Test
    public void testSuccessfulMarshall() throws Exception {
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
        final HashSet<XmlBasedAllowValueHelperImpl> allowedValuesForProperty = new HashSet<XmlBasedAllowValueHelperImpl>();

        final XmlBasedAllowValueHelperImpl helper1 = new XmlBasedAllowValueHelperImpl();
        final HashSet<String> values = new HashSet<String>();
        values.add("FOO");
        values.add("BAR");
        helper1.setDomainProperty("test.property");
        helper1.setAllowedValues(values);

        allowedValuesForProperty.add(helper1);

        spec.setAllowedValuesForProperty(allowedValuesForProperty);

        // ******** DISALLOWED PROPERTIES **********/
        final HashSet<String> disallowedProperties = new HashSet<String>();
        disallowedProperties.add("foo.bar");
        disallowedProperties.add("my.bar");

        spec.setDisallowedProperties(disallowedProperties);

        // ******** INTERFACES **********/
        final HashSet<SoRSpecification.Interfaces> interfaces = new HashSet<SoRSpecification.Interfaces>();
        interfaces.add(SoRSpecification.Interfaces.BATCH);
        interfaces.add(SoRSpecification.Interfaces.HUMAN);

        spec.setInterfaces(interfaces);

        final Map<SoRSpecification.Interfaces,String> notification = new HashMap<SoRSpecification.Interfaces, String>();
        notification.put(SoRSpecification.Interfaces.HUMAN, "email");
        notification.put(SoRSpecification.Interfaces.BATCH, "letter");

        spec.setInterfaceToNotificationSchemeMapping(notification);
        // **** MAX VALUES FOR COLLECTIONS

        final HashSet<XmlBasedPropertySizeHelperImpl> propertySizeHelpers = new HashSet<XmlBasedPropertySizeHelperImpl>();

        final XmlBasedPropertySizeHelperImpl helper = new XmlBasedPropertySizeHelperImpl();
        helper.setProperty("collection.1");
        helper.setMin(5);
        helper.setMax(20);

        propertySizeHelpers.add(helper);

        spec.setMinMaxPropertySizes(propertySizeHelpers);

        // ***** OUTPUT ********** /
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        this.marshaller.marshal(spec, byteArrayOutputStream);

       // System.out.println(byteArrayOutputStream.toString().replaceAll("><", ">\n<"));
    }

    @Test
    public void testSuccessfulUnMarshallWithMinimalSet() throws Exception {
        assertEquals("foo", getSoRSpecificationFromString("<specification><id>foo</id><name>TEST</name><description>This is a test sentence.</description></specification>").getSoR());
    }

    @Test(expected = JAXBException.class)
    public void testUnSuccessfulUnMarshallWithNoData() throws Exception {
        getSoRSpecificationFromString("<specification></specification>");
    }

    @Test
    public void testMarshallWithRequiredProperties() throws Exception {
        final SoRSpecification soRSpecification = getSoRSpecificationFromString("<specification><id>TEST</id><name>TEST</name><description>This is a test sentence.</description><requiredProperties><property>t1</property><property>t2</property></requiredProperties></specification>");

        assertEquals("TEST", soRSpecification.getSoR());
        assertTrue(soRSpecification.isRequiredProperty("t1"));
        assertTrue(soRSpecification.isRequiredProperty("t2"));
        assertFalse(soRSpecification.isRequiredProperty("t3"));
    }

    @Test
    public void testMarshallWithRequiredAndDisallowedProperties() throws Exception {
        final SoRSpecification soRSpecification = getSoRSpecificationFromString("<specification><id>TEST</id><name>TEST</name><description>This is a test sentence.</description><requiredProperties><property>t1</property><property>t2</property></requiredProperties><disallowedProperties><property>d1</property><property>d2</property></disallowedProperties></specification>");

        assertEquals("TEST", soRSpecification.getSoR());
        assertTrue(soRSpecification.isRequiredProperty("t1"));
        assertTrue(soRSpecification.isRequiredProperty("t2"));
        assertFalse(soRSpecification.isRequiredProperty("t3"));

        assertTrue(soRSpecification.isDisallowedProperty("d1"));
        assertTrue(soRSpecification.isDisallowedProperty("d2"));
        assertFalse(soRSpecification.isDisallowedProperty("t1"));
    }

    protected SoRSpecification getSoRSpecificationFromString(final String spec) throws JAXBException {
        final Reader reader = new StringReader(spec);
        return (SoRSpecification) this.unMarshaller.unmarshal(reader);
    }
}
