package top.fedoseev.restaurant.voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurants_unique_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Schema(description = "Restaurant")
public class Restaurant extends NamedEntity {

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("effectiveDate DESC")
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Menu> menus;

}
