package by.teachmeskills.project.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Search {
    // Additional fields while filtering in search page
    private String searchString;
    private Float priceFrom;
    private Float priceTo;
    private String categoryName;

    public Search(String searchString) {
        this.searchString = searchString;
    }

}
