package top.fedoseev.restaurant.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.model.Menu;
import top.fedoseev.restaurant.voting.util.validation.ValidationUtil;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    Optional<Menu> findByIdAndRestaurantId(int id, int restaurantId);

    List<Menu> findAllByRestaurantId(int restaurantId);

    @Transactional
    int deleteByIdAndRestaurantId(int id, int restaurantId);

    default void deleteExistedByIdAndRestaurantId(int id, int restaurantId) {
        ValidationUtil.checkModification(deleteByIdAndRestaurantId(id, restaurantId), id);
    }
}
