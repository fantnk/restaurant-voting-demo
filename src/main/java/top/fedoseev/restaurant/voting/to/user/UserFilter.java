package top.fedoseev.restaurant.voting.to.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserFilter(
        @Schema(description = "Name", example = "Us*")
        String name,

        @Schema(description = "Email", example = "user@*")
        String email) {
}
