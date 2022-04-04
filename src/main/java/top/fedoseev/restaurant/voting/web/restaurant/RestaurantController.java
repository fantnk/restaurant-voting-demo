package top.fedoseev.restaurant.voting.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import top.fedoseev.restaurant.voting.service.RestaurantService;
import top.fedoseev.restaurant.voting.to.RestaurantCreationRequest;
import top.fedoseev.restaurant.voting.to.RestaurantResponse;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = RestaurantController.TAG_NAME, description = "the restaurant API")
public class RestaurantController {
    static final String REST_URL = "/api/admin/restaurant";
    static final String TAG_NAME = "restaurant";

    private final RestaurantService service;

    @Operation(summary = "Get restaurant by ID")
    @GetMapping("/{id}")
    public RestaurantResponse get(
            @PathVariable @Parameter(description = "ID of the restaurant that needs to be fetched", required = true, example = "1") int id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete restaurant by ID")
    public void delete(
            @PathVariable @Parameter(description = "ID of the restaurant that needs to be deleted", required = true, example = "1") int id) {
        service.delete(id);
    }

    @GetMapping
    @Operation(summary = "Get all restaurants")
    public List<RestaurantResponse> getAll() {
        return service.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create restaurant")
    public ResponseEntity<RestaurantResponse> create(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody
                                                       RestaurantCreationRequest request) {
        RestaurantResponse created = service.create(request);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
