package top.fedoseev.restaurant.voting.mapper;

import org.mapstruct.Mapper;
import top.fedoseev.restaurant.voting.config.MapStructConfig;
import top.fedoseev.restaurant.voting.to.clock.ClockResponse;

import java.time.Instant;

@Mapper(config = MapStructConfig.class)
public interface ClockMapper {

    ClockResponse fromInstant(Instant instant);

}
