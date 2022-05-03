package top.fedoseev.restaurant.voting.to.dish;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import top.fedoseev.restaurant.voting.util.validation.NoHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Dish modification request", accessMode = Schema.AccessMode.WRITE_ONLY)
public record DishModificationRequest(
        @NotBlank
        @Size(min = 2, max = 100)
        @NoHtml
        @Schema(description = "Name", example = "Papa Joss")
        String name,

        @NotNull
        @Schema(description = "Price", example = "59.99")
        @JsonFormat(shape=JsonFormat.Shape.STRING)
        BigDecimal price) {
}
