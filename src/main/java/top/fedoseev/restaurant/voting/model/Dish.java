package top.fedoseev.restaurant.voting.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "menu_id"}, name = "dishes_unique_name_menu_id_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public class Dish extends NamedEntity {

    @ToString.Include
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    public Dish(Integer id, String name, BigDecimal price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }
}
