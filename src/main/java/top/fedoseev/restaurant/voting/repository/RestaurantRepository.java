package top.fedoseev.restaurant.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("select r from Restaurant r inner join Menu m on r = m.restaurant and m.effectiveDate = current_date")
    List<Restaurant> findAllByMenusNotEmptyToday();
}
