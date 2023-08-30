package by.teachmeskills.project.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class CategoryCsv {

    @CsvBindByName
    private Integer id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private Integer imageId;

    @CsvBindByName
    private String sometext;
}
