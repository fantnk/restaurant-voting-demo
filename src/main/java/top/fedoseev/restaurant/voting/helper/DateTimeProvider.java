package top.fedoseev.restaurant.voting.helper;

import java.time.Instant;

/**
 * Date and time provider. Used for testing purpose.
 */
public interface DateTimeProvider {

    Instant instant();
}
