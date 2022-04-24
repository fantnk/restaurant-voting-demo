package top.fedoseev.restaurant.voting.service;

import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.to.vote.VoteCreationRequest;
import top.fedoseev.restaurant.voting.to.vote.VoteResponse;

import java.util.List;

public interface UserVotingService {

    VoteResponse getById(int id, int userId);

    VoteResponse vote(VoteCreationRequest request, int userId);

    List<VoteResponse> findAll(int userId);

    @Transactional
    void delete(int id, int userId);
}
