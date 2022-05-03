package top.fedoseev.restaurant.voting.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import top.fedoseev.restaurant.voting.model.Menu;
import top.fedoseev.restaurant.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void findAllByMenusNotEmptyTodayTest() {
        Restaurant restaurant = restaurantRepository.save(new Restaurant(null, "Restaurant #1"));
        List<Restaurant> restaurants = restaurantRepository.findAllByMenusNotEmptyToday();
        assertThat(restaurants).hasSize(2).doesNotContain(restaurant);

        menuRepository.save(new Menu(LocalDate.now(), restaurant, List.of()));

        restaurants = restaurantRepository.findAllByMenusNotEmptyToday();
        assertThat(restaurants).hasSize(3).contains(restaurant);
    }
}
