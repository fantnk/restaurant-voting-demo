package top.fedoseev.restaurant.voting.to.user;

import io.swagger.v3.oas.annotations.media.Schema;
import top.fedoseev.restaurant.voting.model.Role;
import top.fedoseev.restaurant.voting.util.validation.NoHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Schema(description = "New user creation request by admin")
public record UserCreationByAdminRequest(
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
        String email,

        @NotBlank
        @Size(min = 5, max = 32)
        @Schema(description = "Password", example = "123456")
        String password,

        @NotBlank
        @Size(min = 1)
        @Schema(description = "Collection of roles", example = "USER")
        Collection<@NotNull Role> roles
) {
}
