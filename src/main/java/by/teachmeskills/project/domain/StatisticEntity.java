package by.teachmeskills.project.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class StatisticEntity extends BaseEntity{
    @NotNull(message = "Field is null validation error")
    @PositiveOrZero(message = "Field must be positive or zero")
    private Integer id;
    @NotNull(message = "Field is null validation error")
    @Size(min = 0, max = 45, message = "Out of validation bounds")
    private String description;

    public StatisticEntity(){

    }

    public StatisticEntity(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
