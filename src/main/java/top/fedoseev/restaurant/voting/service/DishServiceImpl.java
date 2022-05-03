package top.fedoseev.restaurant.voting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.exception.ErrorMessage;
import top.fedoseev.restaurant.voting.exception.NotFoundException;
import top.fedoseev.restaurant.voting.mapper.DishMapper;
import top.fedoseev.restaurant.voting.model.Dish;
import top.fedoseev.restaurant.voting.model.Menu;
import top.fedoseev.restaurant.voting.repository.DishRepository;
import top.fedoseev.restaurant.voting.repository.MenuRepository;
import top.fedoseev.restaurant.voting.to.dish.DishCreationRequest;
import top.fedoseev.restaurant.voting.to.dish.DishModificationRequest;
import top.fedoseev.restaurant.voting.to.dish.DishResponse;
import top.fedoseev.restaurant.voting.util.validation.ValidationUtil;

import java.util.List;

@Service
@CacheConfig(cacheNames = DishServiceImpl.CACHE_NAME)
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    static final String CACHE_NAME = "dishes";

    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;
    private final DishMapper dishMapper;

    @Override
    @Cacheable
    public DishResponse getById(int id, int menuId, int restaurantId) {
        return dishMapper.toDishResponse(getDish(id, menuId, restaurantId));
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public DishResponse create(DishCreationRequest request, int menuId, int restaurantId) {
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.MENU_NOT_FOUND_BY_ID_AND_RESTAURANT_ID, "Menu", menuId, restaurantId));
        ValidationUtil.checkEquals(menu.getRestaurant().getId(), restaurantId, "Restaurant id");
        Dish dish = dishMapper.fromCreationRequest(request, menu);
        Dish savedDish = dishRepository.save(dish);
        return dishMapper.toDishResponse(savedDish);
    }

    @Override
    @Cacheable
    public List<DishResponse> findAll(int menuId, int restaurantId) {
        return dishRepository.findAllByMenuIdAndMenuRestaurantId(menuId, restaurantId).stream()
                .map(dishMapper::toDishResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void delete(int id, int menuId, int restaurantId) {
        dishRepository.deleteExistedByIdAndMenuIdAndMenuRestaurantId(id, menuId, restaurantId);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public DishResponse update(DishModificationRequest request, int id, int menuId, int restaurantId) {
        Dish dish = getDish(id, menuId, restaurantId);
        dishMapper.updateFromModificationRequest(dish, request);
        Dish savedDish = dishRepository.save(dish);
        return dishMapper.toDishResponse(savedDish);
    }

    private Dish getDish(int id, int menuId, int restaurantId) {
        return dishRepository.findByIdAndMenuIdAndMenuRestaurantId(id, menuId, restaurantId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.DISH_NOT_FOUND_BY_ID_AND_MENU_ID_AND_RESTAURANT_ID, id, menuId, restaurantId));
    }
}
