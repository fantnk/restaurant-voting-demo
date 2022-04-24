package top.fedoseev.restaurant.voting.to.vote;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "Response of the vote", accessMode = Schema.AccessMode.READ_ONLY)
public record VoteResponse(
        @NotNull
        @Schema(description = "ID", example = "1")
        Integer id,

        @NotNull
        @Schema(description = "Restaurant ID", example = "1")
        Integer restaurantId,

        @NotNull
        @Schema(description = "Date", example = "2021-12-31")
        LocalDate date) {
}
