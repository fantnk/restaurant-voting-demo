package top.fedoseev.restaurant.voting.to;

import io.swagger.v3.oas.annotations.media.Schema;
import top.fedoseev.restaurant.voting.util.validation.NoHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Response of the restaurant")
public record RestaurantResponse(
        @NotNull
        @Schema(description = "ID", example = "1")
        Integer id,

        @NotBlank
        @Size(min = 2, max = 100)
        @NoHtml
        @Schema(description = "Name", example = "Papa Joss")
        String name
) {
}
