package by.teachmeskills.project.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    @Size(max = 45, message = "Out of validation bounds")
    @Column(name = "name")
    private String name;

    // Может быть OneToMany в будущем, если будет карусель картинок(тогда еще будет поле boolean primeImage)
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @NotNull(message = "Field is null validation error")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Field is null validation error")
    @PositiveOrZero(message = "Field must be positive or zero")
    @Column(name = "categoryid")
    private int categoryid;

    @NotNull(message = "Field is null validation error")
    @Positive(message = "Field must be positive")
    @Column(name = "price")
    private float price;

    public Product(Integer id, String name, Image image, String description, int categoryid, float price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.categoryid = categoryid;
        this.price = price;
    }
}
