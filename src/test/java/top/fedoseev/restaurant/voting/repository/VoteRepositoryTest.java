package top.fedoseev.restaurant.voting.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.model.User;
import top.fedoseev.restaurant.voting.model.Vote;
import top.fedoseev.restaurant.voting.web.UserTestData;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void countTodayVotesTest() {
        User user = userRepository.getById(UserTestData.USER_ID);
        Restaurant restaurant = restaurantRepository.getById(1);
        voteRepository.save(new Vote(user, restaurant));

        Map<Integer, Integer> votes = voteRepository.countTodayVotes();

        assertEquals(1, votes.get(restaurant.getId()));
    }
}
