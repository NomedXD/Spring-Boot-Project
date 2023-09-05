package by.teachmeskills.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    // Additional fields while filtering in search page
    private String searchString;
    private Float priceFrom;
    private Float priceTo;
    private String categoryName;
}
