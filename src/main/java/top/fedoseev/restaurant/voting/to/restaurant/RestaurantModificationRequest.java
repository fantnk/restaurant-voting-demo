package top.fedoseev.restaurant.voting.to.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import top.fedoseev.restaurant.voting.util.validation.NoHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema(description = "Restaurant modification request", accessMode = Schema.AccessMode.WRITE_ONLY)
public record RestaurantModificationRequest(
        @NotBlank
        @Size(min = 2, max = 100)
        @NoHtml
        @Schema(description = "Name", example = "Papa Joss")
        String name) {
}
