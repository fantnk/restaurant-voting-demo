package top.fedoseev.restaurant.voting.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.LocalTime;
import java.util.Objects;

@ConfigurationProperties("app")
@ConstructorBinding
public record AppProperties(
        LocalTime votingTillTime
) {
    public AppProperties(LocalTime votingTillTime) {
        this.votingTillTime = Objects.requireNonNull(votingTillTime, "Property app.votingTillTime should be set");
    }
}
