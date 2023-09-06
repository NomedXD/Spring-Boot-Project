package by.teachmeskills.project.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ProductCsv {

    @CsvBindByName
    private Integer id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private Integer imageId;

    @CsvBindByName
    private String description;

    @CsvBindByName
    private Integer categoryId;

    @CsvBindByName
    private float price;
}
