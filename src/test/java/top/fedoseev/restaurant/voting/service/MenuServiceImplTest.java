package top.fedoseev.restaurant.voting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import top.fedoseev.restaurant.voting.exception.ValidationException;
import top.fedoseev.restaurant.voting.mapper.MenuMapper;
import top.fedoseev.restaurant.voting.mapper.MenuMapperImpl;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.repository.MenuRepository;
import top.fedoseev.restaurant.voting.repository.RestaurantRepository;
import top.fedoseev.restaurant.voting.to.menu.MenuCreationRequest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    public static final int RESTAURANT_ID = 1;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private MenuServiceImpl service;

    @BeforeEach
    void setUp() {
        Restaurant restaurant = new Restaurant(RESTAURANT_ID, "Restaurant №1");
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));

        MenuMapper menuMapper = new MenuMapperImpl();
        service = new MenuServiceImpl(menuRepository, restaurantRepository, menuMapper);
    }

    @Test
    void createShouldReturnErrorWhenEffectiveDateHasCome() {
        MenuCreationRequest request = new MenuCreationRequest(LocalDate.now());
        assertThrows(ValidationException.class, () -> service.create(request, RESTAURANT_ID));
    }
}
