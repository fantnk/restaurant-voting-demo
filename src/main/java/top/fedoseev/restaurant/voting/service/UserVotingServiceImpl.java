package top.fedoseev.restaurant.voting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.exception.ErrorMessage;
import top.fedoseev.restaurant.voting.exception.NotFoundException;
import top.fedoseev.restaurant.voting.exception.VoteIsTooLateException;
import top.fedoseev.restaurant.voting.mapper.VoteMapper;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.model.Vote;
import top.fedoseev.restaurant.voting.repository.RestaurantRepository;
import top.fedoseev.restaurant.voting.repository.UserRepository;
import top.fedoseev.restaurant.voting.repository.VoteRepository;
import top.fedoseev.restaurant.voting.to.vote.VoteCreationRequest;
import top.fedoseev.restaurant.voting.to.vote.VoteResponse;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserVotingServiceImpl implements UserVotingService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;

    @Override
    public VoteResponse getById(int id, int userId) {
        return voteRepository.findByIdAndUserId(id, userId)
                .map(voteMapper::toVoteResponse)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VOTE_NOT_FOUND_BY_ID_AND_USER_ID, id, userId));
    }

    @Override
    @Transactional
    public VoteResponse vote(VoteCreationRequest request, int userId) {
        checkVotingIsOpen();

        Restaurant restaurant = restaurantRepository.getById(request.restaurantId());

        Vote vote = voteRepository.findTodayVoteByUserId(userId)
                .orElseGet(() -> new Vote(userRepository.getById(userId), restaurant));
        vote.setRestaurant(restaurant);
        Vote savedVote = voteRepository.save(vote);
        return voteMapper.toVoteResponse(savedVote);
    }

    @Override
    public List<VoteResponse> findAll(int userId) {
        return voteRepository.findAllByUserId(userId).stream()
                .map(voteMapper::toVoteResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(int id, int userId) {
        checkVotingIsOpen();
        voteRepository.deleteExistedByIdSAndUserId(id, userId);
    }

    private static void checkVotingIsOpen() {
        LocalTime tillTime = LocalTime.of(11, 0);
        if (!LocalTime.now().isBefore(tillTime)) {
            throw new VoteIsTooLateException(tillTime);
        }
    }
}
