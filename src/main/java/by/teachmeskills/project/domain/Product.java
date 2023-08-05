package by.teachmeskills.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Size(min = 0, max = 45, message = "Out of validation bounds")
    @Column(name = "name")
    private String name;
    @NotNull(message = "Field is null validation error")
    @Size(min = 0, max = 45, message = "Out of validation bounds")
    @Column(name = "imagepath")
    private String imagepath;
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

    public Product(Integer id, String name, String imagepath, String description, int categoryid, float price) {
        this.id = id;
        this.name = name;
        this.imagepath = imagepath;
        this.description = description;
        this.categoryid = categoryid;
        this.price = price;
    }
}
