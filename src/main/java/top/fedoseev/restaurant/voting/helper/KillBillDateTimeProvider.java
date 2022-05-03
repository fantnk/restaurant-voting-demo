package top.fedoseev.restaurant.voting.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Component
@Profile("!prod")
@RequiredArgsConstructor
@Slf4j
public class KillBillDateTimeProvider implements DateTimeProvider, ClockSetter {

    private Clock clock;

    @PostConstruct
    void init() {
        clock = Clock.systemDefaultZone();
        log.info("Test time provider inited");
    }

    @Override
    public Instant instant() {
        return clock.instant();
    }

    @Override
    public void setClock(Instant instant) {
        this.clock = Clock.fixed(instant, ZoneId.systemDefault());
    }

    @Override
    public void setDefault() {
        clock = Clock.systemDefaultZone();
    }
}
