package org.jasig.openregistry.security;

import org.openregistry.security.ExpressionParser;
import org.openregistry.core.domain.Person;
import org.springframework.stereotype.Component;
import de.odysseus.el.util.SimpleContext;
import de.odysseus.el.ExpressionFactoryImpl;

import javax.el.ValueExpression;
import javax.el.ExpressionFactory;

/**
 * Implementation of the ExpressionParser interface that relies on Unified EL and JUEL.
 *
 * @version $Revision$ $Date$
 * @since 1.0.0
 */
@Component
public final class ElJuelExpressionParser implements ExpressionParser {

    private final ExpressionFactory expressionFactory = new ExpressionFactoryImpl();

    public boolean matches(final Person person, final String expression) {
        final SimpleContext context = new SimpleContext();
        context.setVariable("person", this.expressionFactory.createValueExpression(person, Person.class));

        final ValueExpression valueExpression = this.expressionFactory.createValueExpression(context, expression, boolean.class);
        return (Boolean) valueExpression.getValue(context);
    }

    // TODO implement
    public boolean matches(final String resource, final String resourceExpression) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
