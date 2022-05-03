package top.fedoseev.restaurant.voting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import top.fedoseev.restaurant.voting.service.UserVotingService;
import top.fedoseev.restaurant.voting.to.vote.VoteCreationRequest;
import top.fedoseev.restaurant.voting.to.vote.VoteResponse;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL)
@RequiredArgsConstructor
@Tag(name = VoteController.TAG_NAME, description = "the vote API")
public class VoteController {
    static final String REST_URL = "/api/vote";
    static final String TAG_NAME = "vote";

    private final UserVotingService service;

    @Operation(summary = "Get vote by ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteResponse get(
            @PathVariable @Parameter(description = "ID of the vote that needs to be fetched", required = true, example = "1") int id,
            @AuthenticationPrincipal AuthUser authUser) {
        return service.getById(id, authUser.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete vote by ID")
    public void delete(
            @PathVariable @Parameter(description = "ID of the vote that needs to be deleted", required = true, example = "1") int id,
            @AuthenticationPrincipal AuthUser authUser) {
        service.delete(id, authUser.id());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all votes")
    public List<VoteResponse> getAll(@AuthenticationPrincipal AuthUser authUser) {
        return service.findAll(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Vote", responses = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "201")})
    public ResponseEntity<VoteResponse> vote(@Valid @RequestBody VoteCreationRequest request, @AuthenticationPrincipal AuthUser authUser) {
        VoteResponse vote = service.vote(request, authUser.id());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(vote.id())
                .toUri();
        return ResponseEntity.status(vote.created() ? HttpStatus.CREATED : HttpStatus.OK).location(uriOfNewResource).body(vote);
    }

}
