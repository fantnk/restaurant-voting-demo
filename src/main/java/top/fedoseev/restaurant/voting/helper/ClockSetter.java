package top.fedoseev.restaurant.voting.helper;

import java.time.Instant;

public interface ClockSetter {

    void setClock(Instant instant);

    void setDefault();
}
