package org.openregistry.core.web.resources.config;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import org.openregistry.core.web.resources.PersonIdentifierRepresentation;
import org.openregistry.core.web.resources.PersonResponseRepresentation;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

/**
 * Jersey ContextResolver to enable marshalling JAXB-mapped beans into <i>natural</i> JSON notaion.
 * This allows for a very lightweight programming model with a unified Java model for both XML and JSON representations.
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@Provider
@Component
@Scope(value = "singleton")
public final class JaxbContextResolver implements ContextResolver<JAXBContext> {

    private final JAXBContext context;

    private final Set<Class> types;

    private final Class[] cTypes = {PersonIdentifierRepresentation.class, PersonResponseRepresentation.class};

    public JaxbContextResolver() throws Exception {
        this.types = new HashSet(Arrays.asList(cTypes));
        this.context = new JSONJAXBContext(JSONConfiguration.natural().build(), cTypes);
    }

    public JAXBContext getContext(Class<?> objectType) {
        return (types.contains(objectType)) ? context : null;
    }
}