package top.fedoseev.restaurant.voting.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.fedoseev.restaurant.voting.util.validation.NoHtml;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class NamedEntity extends BaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    @NoHtml
    private String name;

    NamedEntity(Integer id, String name) {
        super();
        setId(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + name + ']';
    }
}
