package top.fedoseev.restaurant.voting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import top.fedoseev.restaurant.voting.config.property.AppProperties;
import top.fedoseev.restaurant.voting.exception.VoteIsTooLateException;
import top.fedoseev.restaurant.voting.helper.DateTimeProvider;
import top.fedoseev.restaurant.voting.mapper.VoteMapper;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.repository.RestaurantRepository;
import top.fedoseev.restaurant.voting.repository.UserRepository;
import top.fedoseev.restaurant.voting.repository.VoteRepository;
import top.fedoseev.restaurant.voting.to.vote.VoteCreationRequest;
import top.fedoseev.restaurant.voting.web.UserTestData;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserVotingServiceImplTest {

    private static final LocalTime TILL_TIME = LocalTime.of(1, 23);
    private static final int RESTAURANT_ID = 1;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoteMapper voteMapper;

    @Mock
    private DateTimeProvider dateTimeProvider;

    private final AppProperties appProperties = new AppProperties(TILL_TIME);

    private UserVotingServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new UserVotingServiceImpl(userRepository, restaurantRepository, voteRepository, voteMapper, dateTimeProvider, appProperties);
    }

    @Test
    void voteShouldBeDeclinedWhenItIsTooLate() {
        when(dateTimeProvider.instant()).thenReturn(TILL_TIME.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
        VoteCreationRequest request = new VoteCreationRequest(RESTAURANT_ID);

        assertThrows(VoteIsTooLateException.class, () -> service.vote(request, UserTestData.USER_ID));
    }

    @Test
    void voteShouldBeSuccess() {
        when(userRepository.getById(UserTestData.USER_ID)).thenReturn(UserTestData.USER);
        when(restaurantRepository.getById(RESTAURANT_ID)).thenReturn(new Restaurant(RESTAURANT_ID, "Restaurant #1"));
        when(dateTimeProvider.instant()).thenReturn(TILL_TIME.atDate(LocalDate.now()).minusSeconds(1).atZone(ZoneId.systemDefault()).toInstant());
        VoteCreationRequest request = new VoteCreationRequest(RESTAURANT_ID);

        assertDoesNotThrow(() -> service.vote(request, UserTestData.USER_ID));
    }
}
