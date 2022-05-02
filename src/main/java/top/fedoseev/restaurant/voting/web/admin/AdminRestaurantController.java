package top.fedoseev.restaurant.voting.web.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import top.fedoseev.restaurant.voting.service.RestaurantService;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantCreationRequest;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantResponse;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL)
@RequiredArgsConstructor
@Tag(name = AdminRestaurantController.TAG_NAME, description = "the restaurant API")
public class AdminRestaurantController {
    static final String REST_URL = "/api/admin/restaurant";
    static final String TAG_NAME = "restaurant";

    private final RestaurantService service;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete restaurant by ID")
    public void delete(
            @PathVariable @Parameter(description = "ID of the restaurant that needs to be deleted", required = true, example = "1") int id) {
        service.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
