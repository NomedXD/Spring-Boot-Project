package by.teachmeskills.project.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class SearchEntity {
    // Additional field while filtering in search page
    private String searchString;

    public SearchEntity(String searchString) {
        this.searchString = searchString;
    }

}
