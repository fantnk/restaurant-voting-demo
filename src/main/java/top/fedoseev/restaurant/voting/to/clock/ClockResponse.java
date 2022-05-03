package top.fedoseev.restaurant.voting.to.clock;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Schema(description = "Response of the clock", accessMode = Schema.AccessMode.READ_ONLY)
public record ClockResponse(
        @NotNull
        @Schema(description = "Timestamp", example = "2022-05-12T20:30:00Z")
        Instant instant) {
}
