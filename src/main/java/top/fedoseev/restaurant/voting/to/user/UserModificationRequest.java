package top.fedoseev.restaurant.voting.to.user;

import io.swagger.v3.oas.annotations.media.Schema;
import top.fedoseev.restaurant.voting.util.validation.NoHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema(description = "User modification request", accessMode = Schema.AccessMode.WRITE_ONLY)
public record UserModificationRequest(
        @NotBlank
        @Size(min = 2, max = 100)
        @NoHtml
        @Schema(description = "Name", example = "Michel")
        String name,

        @Email
        @NotBlank
        @Size(max = 100)
        @NoHtml
        @Schema(description = "Email", example = "michel@example.com")
        String email
) {
}
