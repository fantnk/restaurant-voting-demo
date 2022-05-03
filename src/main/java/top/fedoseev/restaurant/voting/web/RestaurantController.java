package top.fedoseev.restaurant.voting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fedoseev.restaurant.voting.service.RestaurantService;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantModificationRequest;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL)
@RequiredArgsConstructor
@Tag(name = RestaurantController.TAG_NAME, description = "the restaurant API")
public class RestaurantController {
    static final String REST_URL = "/api/restaurant";
    static final String TAG_NAME = "restaurant";

    private final RestaurantService service;

    @Operation(summary = "Get restaurant by ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantResponse get(
            @PathVariable @Parameter(description = "ID of the restaurant that needs to be fetched", required = true, example = "1") int id) {
        return service.getById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all restaurants")
    public List<RestaurantResponse> getAll() {
        return service.findAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modify existed restaurant")
    public RestaurantResponse update(
            @PathVariable @Parameter(description = "ID of the restaurant that needs to be modified", required = true, example = "1") int id,
            @RequestBody @Valid RestaurantModificationRequest request) {
        return service.update(request, id);
    }

}
