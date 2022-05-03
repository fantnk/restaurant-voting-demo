package top.fedoseev.restaurant.voting.to.menu;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "Menu modification request", accessMode = Schema.AccessMode.WRITE_ONLY)
public record MenuModificationRequest(
        @NotNull
        @Schema(description = "The date on which the menu is effective", example = "2022-01-01")
        LocalDate effectiveDate) {
}
