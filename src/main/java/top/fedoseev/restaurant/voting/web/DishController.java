package top.fedoseev.restaurant.voting.web;

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
import top.fedoseev.restaurant.voting.service.DishService;
import top.fedoseev.restaurant.voting.to.dish.DishCreationRequest;
import top.fedoseev.restaurant.voting.to.dish.DishResponse;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = DishController.TAG_NAME, description = "the dish API")
public class DishController {
    static final String REST_URL = "/api/admin/restaurant/{restaurantId}/menu/{menuId}/dish";
    static final String TAG_NAME = "dish";

    private final DishService service;

    @Operation(summary = "Get dish by ID")
    @GetMapping("/{id}")
    public DishResponse get(
            @PathVariable @Parameter(description = "ID of the dish that needs to be fetched", required = true, example = "1") int id,
            @PathVariable @Parameter(description = "ID of the menu", required = true, example = "1") int menuId,
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        return service.getById(id, menuId, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete dish by ID")
    public void delete(
            @PathVariable @Parameter(description = "ID of the dish that needs to be deleted", required = true, example = "1") int id,
            @PathVariable @Parameter(description = "ID of the menu", required = true, example = "1") int menuId,
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        service.delete(id, menuId, restaurantId);
    }

    @GetMapping
    @Operation(summary = "Get all dishes")
    public List<DishResponse> getAll(
            @PathVariable @Parameter(description = "ID of the menu", required = true, example = "1") int menuId,
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        return service.findAll(menuId, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create dish")
    public ResponseEntity<DishResponse> create(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody
                                                       DishCreationRequest request,
                                               @PathVariable @Parameter(description = "ID of the menu", required = true, example = "1") int menuId,
                                               @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1")
                                                       int restaurantId) {
        DishResponse created = service.create(request, menuId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, menuId, created.id())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
