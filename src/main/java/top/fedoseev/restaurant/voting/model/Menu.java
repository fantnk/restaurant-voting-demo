package top.fedoseev.restaurant.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"effective_date", "restaurant_id"}, name = "menus_unique_effective_date_restaurant_id_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant", "dishes"})
public class Menu extends BaseEntity {

    @Column(name = "effective_date", nullable = false)
    @NotNull
    private LocalDate effectiveDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @OrderBy("name DESC")
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Dish> dishes;

}
