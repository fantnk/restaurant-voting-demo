package top.fedoseev.restaurant.voting.to.vote;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

@Schema(description = "New vote creation request", accessMode = Schema.AccessMode.WRITE_ONLY)
public record VoteCreationRequest(
        @NotNull
        @Schema(description = "Restaurant ID", example = "1")
        Integer restaurantId) {
}
