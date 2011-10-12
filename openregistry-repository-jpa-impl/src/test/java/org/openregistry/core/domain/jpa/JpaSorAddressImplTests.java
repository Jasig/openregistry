package org.openregistry.core.domain.jpa;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.jpa.sor.JpaSorAddressImpl;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:base-integration-tests.xml"})

public class JpaSorAddressImplTests {

	@Test 
	public void testSetValidRegion () {
		Address sorAddress = new JpaSorAddressImpl();
		Region validRegion = new JpaRegionImpl();
		
		assertNull("Region must be null before setting", sorAddress.getRegion());
		sorAddress.setRegion(validRegion);
		assertNotNull("Region must not be null after setting", sorAddress.getRegion());
	}
	
	@Test
	public void testSetNullRegion () {
		Address sorAddress = new JpaSorAddressImpl();
		Region validRegion = new JpaRegionImpl();
		sorAddress.setRegion(validRegion);
		
		assertNotNull("Region must not be null before setting it to null", sorAddress.getRegion());
		sorAddress.setRegion(null);
		assertNull("Region must be null after setting it to null", sorAddress.getRegion());
	}
	
    @Test(expected = IllegalArgumentException.class)
    public void testSetIncorrectRegionType() {
    	
		Address sorAddress = new JpaSorAddressImpl();
		
    	// This implements Region but is not instance of JpaRegionImpl
    	Region invalidRegion = new Region() {
			public String getName() {
				return null;
			}
			public String getCode() {
				return null;
			}
			public Country getCountry() {
				return null;
			}    		
    	};
    	sorAddress.setRegion(invalidRegion);
    }
}
