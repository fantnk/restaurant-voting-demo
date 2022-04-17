package top.fedoseev.restaurant.voting.to.common;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Schema(description = "Error response", accessMode = Schema.AccessMode.READ_ONLY)
public record ErrorResponse(

        @Schema(description = "Message", example = "Entity with id 0 was not found")
        @Size(max = 1000)
        String message,

        @Schema(description = "Not valid fields")
        Map<String, List<String>> fields
) {
    public ErrorResponse(String message) {
        this(message, null);
    }
}
