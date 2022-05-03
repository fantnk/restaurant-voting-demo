package top.fedoseev.restaurant.voting.helper;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Profile("prod")
public class JvmDateTimeProvider implements DateTimeProvider {

    @Override
    public Instant instant() {
        return Instant.now();
    }
}
