package top.fedoseev.restaurant.voting.to.clock;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Schema(description = "Change clock request", accessMode = Schema.AccessMode.WRITE_ONLY)
public record ClockChangingRequest(
        @NotNull
        @Schema(description = "Timestamp", example = "2022-05-12T20:30:00Z")
        Instant instant) {
}
