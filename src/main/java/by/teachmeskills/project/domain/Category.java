package by.teachmeskills.project.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "categories")
public class Category extends BaseEntity {

    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    @Size(max = 45, message = "Out of validation bounds")
    @Column(name = "name")
    private String name;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @NotNull(message = "Field is null validation error")
    @Size(max = 45, message = "Out of validation bounds")
    @Column(name = "sometext")
    private String sometext;

    public Category(Integer id, String name, Image image, String sometext) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.sometext = sometext;
    }
}
