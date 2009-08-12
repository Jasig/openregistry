package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Url;
import org.openregistry.core.domain.Type;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @version $Revision$ $Date$
 * @since 0.01
 */
public final class JpaUrlImplTests {


    @Test
    public void checkSettersAndGetters() throws MalformedURLException {
        final Url url = new JpaUrlImpl();

        assertNull(url.getType());
        assertNull(url.getUrl());

        // TODO why is Url.getId() exposed?

        final URL testUrl = new URL("http://www.cnn.com");
        final Type type = new JpaTypeImpl();

        url.setUrl(testUrl);
        assertEquals(testUrl, url.getUrl());
        url.setType(type);
        assertEquals(type, url.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkJpaTypeCheck() {
        final Url url = new JpaUrlImpl();
        url.setType(new Type() {
            public Long getId() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getDataType() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getDescription() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
}
