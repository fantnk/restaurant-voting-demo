package top.fedoseev.restaurant.voting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.exception.ErrorMessage;
import top.fedoseev.restaurant.voting.exception.NotFoundException;
import top.fedoseev.restaurant.voting.mapper.RestaurantMapper;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.repository.RestaurantRepository;
import top.fedoseev.restaurant.voting.repository.VoteRepository;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantCreationRequest;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantModificationRequest;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantResponse;

import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = RestaurantServiceImpl.CACHE_NAME)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    static final String CACHE_NAME = "restaurants";
    private static final int TOTAL_VOTES_TODAY_DEFAULT_VALUE = 0;

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public RestaurantResponse getById(int id) {
        Restaurant restaurant = getRestaurant(id);
        int votes = voteRepository.countTodayVotesByRestaurantId(id);
        return restaurantMapper.toRestaurantResponse(restaurant, votes);
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public RestaurantResponse create(RestaurantCreationRequest request) {
        Restaurant restaurant = restaurantMapper.fromCreationRequest(request);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toRestaurantResponse(savedRestaurant, TOTAL_VOTES_TODAY_DEFAULT_VALUE);
    }

    @Override
    @Cacheable
    public List<RestaurantResponse> findAll(boolean onlyWithMenu) {
        Map<Integer, Integer> votes = voteRepository.countTodayVotes();
        List<Restaurant> restaurants = onlyWithMenu ? restaurantRepository.findAllByMenusNotEmptyToday() : restaurantRepository.findAll();
        return restaurants.stream()
                .map(r -> restaurantMapper.toRestaurantResponse(r, votes.getOrDefault(r.getId(), TOTAL_VOTES_TODAY_DEFAULT_VALUE)))
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void delete(int id) {
        restaurantRepository.deleteExisted(id);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public RestaurantResponse update(RestaurantModificationRequest request, int id) {
        Restaurant restaurant = getRestaurant(id);
        restaurantMapper.updateFromModificationRequest(restaurant, request);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        int votes = voteRepository.countTodayVotesByRestaurantId(id);
        return restaurantMapper.toRestaurantResponse(savedRestaurant, votes);
    }

    private Restaurant getRestaurant(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_BY_ID, "Restaurant", id));
    }

}
