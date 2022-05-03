package top.fedoseev.restaurant.voting.web.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import top.fedoseev.restaurant.voting.helper.ClockSetter;
import top.fedoseev.restaurant.voting.helper.DateTimeProvider;
import top.fedoseev.restaurant.voting.mapper.ClockMapper;
import top.fedoseev.restaurant.voting.to.clock.ClockChangingRequest;
import top.fedoseev.restaurant.voting.to.clock.ClockResponse;

import javax.validation.Valid;

@Profile("!prod")
@RestController
@RequestMapping(value = ClockController.REST_URL)
@RequiredArgsConstructor
@Tag(name = ClockController.TAG_NAME, description = "the clock API")
public class ClockController {
    static final String REST_URL = "/api/admin/clock";
    static final String TAG_NAME = "clock";

    private final ClockSetter clockSetter;
    private final DateTimeProvider dateTimeProvider;
    private final ClockMapper clockMapper;

    @Operation(summary = "Get current time")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ClockResponse get() {
        return clockMapper.fromInstant(dateTimeProvider.instant());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Reset time to system")
    public void reset() {
        clockSetter.setDefault();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update time")
    public ClockResponse update(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody ClockChangingRequest request) {
        clockSetter.setClock(request.instant());
        return clockMapper.fromInstant(dateTimeProvider.instant());
    }

}
