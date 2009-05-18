package org.openregistry.core.service.identifier;

import org.openregistry.core.service.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Temp Class until we get real implementations
 */
@Component
public final class NoOpIdentifierGenerator implements IdentifierGenerator {

    private AtomicLong incrementalValue = new AtomicLong(0);

    public long generateNextLong() {
        return incrementalValue.incrementAndGet();
    }

    public String generateNextString() {
        return String.valueOf(incrementalValue.incrementAndGet());
    }

    public int generateNextInt() {
        return Integer.parseInt(String.valueOf(incrementalValue.incrementAndGet()));
    }
}
