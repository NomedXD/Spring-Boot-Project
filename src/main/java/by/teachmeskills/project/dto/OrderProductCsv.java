package by.teachmeskills.project.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
public class OrderProductCsv {

    @CsvBindByName
    private Integer productId;

    @CsvBindByName
    private String productName;

    @CsvBindByName
    private Integer productImageId;

    @CsvBindByName
    private String productDescription;

    @CsvBindByName
    private Integer categoryId;

    @CsvBindByName
    private Float productPrice;

    @CsvBindByName
    private Integer orderId;

    @CsvBindByName
    private Float totalOrderPrice;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName
    private LocalDate orderDate;

    @CsvBindByName
    private Integer userId;

    @CsvBindByName
    private String creditCardNumber;

    @CsvBindByName
    private String shippingType;

    @CsvBindByName
    private Float shippingCost;

    @CsvBindByName
    private String code;

    @CsvBindByName
    private String address;

    @CsvBindByName
    private String customerNotes;
}
