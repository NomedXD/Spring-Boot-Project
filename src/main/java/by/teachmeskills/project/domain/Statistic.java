package by.teachmeskills.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "time_statistic")
public class Statistic extends BaseEntity{

    @NotNull(message = "Field is null validation error")
    @Size(max = 45, message = "Out of validation bounds")
    @Column(name = "description")
    private String description;

    public Statistic(String description) {
        this.description = description;
    }
}
