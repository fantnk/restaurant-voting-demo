package top.fedoseev.restaurant.voting.config;

import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true),
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public class MapStructConfig {
}
