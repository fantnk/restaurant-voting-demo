package top.fedoseev.restaurant.voting.to.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import top.fedoseev.restaurant.voting.util.validation.NoHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Response of the dish", accessMode = Schema.AccessMode.READ_ONLY)
public record DishResponse(
        @NotNull
        @Schema(description = "ID", example = "1")
        Integer id,

        @NotBlank
        @Size(min = 2, max = 100)
        @NoHtml
        @Schema(description = "Name", example = "Papa Joss")
        String name,

        @NotNull
        @Schema(description = "Price", example = "59.99")
        BigDecimal price) {
}
