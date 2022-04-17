package top.fedoseev.restaurant.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.model.Dish;
import top.fedoseev.restaurant.voting.util.validation.ValidationUtil;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    Optional<Dish> findByIdAndMenuIdAndMenuRestaurantId(int id, int menuId, int restaurantId);

    List<Dish> findAllByMenuIdAndMenuRestaurantId(int menuId, int restaurantId);

    @Transactional
    int deleteByIdAndMenuIdAndMenuRestaurantId(int id, int menuId, int restaurantId);

    default void deleteExistedByIdAndMenuIdAndMenuRestaurantId(int id, int menuId, int restaurantId) {
        ValidationUtil.checkModification(deleteByIdAndMenuIdAndMenuRestaurantId(id, menuId, restaurantId), id);
    }

}
