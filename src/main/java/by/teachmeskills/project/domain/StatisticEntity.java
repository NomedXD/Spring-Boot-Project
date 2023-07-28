package by.teachmeskills.project.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class StatisticEntity extends BaseEntity{
    @NotNull
    @PositiveOrZero
    private Integer id;
    @NotNull
    @Size(min = 0, max = 45)
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
