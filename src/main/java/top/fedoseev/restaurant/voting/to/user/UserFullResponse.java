package top.fedoseev.restaurant.voting.to.user;

import io.swagger.v3.oas.annotations.media.Schema;
import top.fedoseev.restaurant.voting.util.validation.NoHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Response of the user with all fields")
public record UserFullResponse(
        @NotNull
        @Schema(description = "ID", example = "1")
        Integer id,

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
