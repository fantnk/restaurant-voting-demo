package top.fedoseev.restaurant.voting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.exception.ErrorMessage;
import top.fedoseev.restaurant.voting.exception.NotFoundException;
import top.fedoseev.restaurant.voting.exception.ValidationException;
import top.fedoseev.restaurant.voting.mapper.MenuMapper;
import top.fedoseev.restaurant.voting.model.Menu;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.repository.MenuRepository;
import top.fedoseev.restaurant.voting.repository.RestaurantRepository;
import top.fedoseev.restaurant.voting.to.menu.MenuCreationRequest;
import top.fedoseev.restaurant.voting.to.menu.MenuResponse;

import java.time.LocalDate;
import java.util.List;

@Service
@CacheConfig(cacheNames = MenuServiceImpl.CACHE_NAME)
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    static final String CACHE_NAME = "menus";

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuMapper menuMapper;

    @Override
    @Cacheable
    public MenuResponse getById(int id, int restaurantId) {
        return menuRepository.findByIdAndRestaurantId(id, restaurantId)
                .map(menuMapper::toMenuResponse)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_BY_ID, "Menu", id));
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public MenuResponse create(MenuCreationRequest request, int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_BY_ID, "Restaurant", restaurantId));
        Menu menu = menuMapper.fromCreationRequest(request, restaurant);
        checkDayHasNotCome(menu.getEffectiveDate());
        Menu savedMenu = menuRepository.save(menu);
        return menuMapper.toMenuResponse(savedMenu);
    }

    private void checkDayHasNotCome(LocalDate date) {
        if (!LocalDate.now().isBefore(date)) {
            throw new ValidationException(ErrorMessage.DAY_HAS_COME, date);
        }
    }

    @Override
    @Cacheable
    public List<MenuResponse> findAll(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId).stream()
                .map(menuMapper::toMenuResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void delete(int id, int restaurantId) {
        menuRepository.deleteExistedByIdAndRestaurantId(id, restaurantId);
    }

    //TODO add update method
}
