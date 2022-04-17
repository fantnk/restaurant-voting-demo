package top.fedoseev.restaurant.voting.to.menu;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "Response of the menu", accessMode = Schema.AccessMode.READ_ONLY)
public record MenuResponse(
        @NotNull
        @Schema(description = "ID", example = "1")
        Integer id,

        @NotNull
        @Schema(description = "The date on which the menu is effective", example = "2022-01-01")
        LocalDate effectiveDate) {
}
