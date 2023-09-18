package by.teachmeskills.project.utils;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.dto.ProductCsv;
import by.teachmeskills.project.services.CategoryService;
import by.teachmeskills.project.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ProductCsvConverter {
    private final ImageService imageService;
    private final CategoryService categoryService;

    @Autowired
    public ProductCsvConverter(ImageService imageService, CategoryService categoryService) {
        this.imageService = imageService;
        this.categoryService = categoryService;
    }

    public List<ProductCsv> convertInto(List<Product> productList) {
        List<ProductCsv> productCsvList = new ArrayList<>();
        productList.forEach(product -> productCsvList.add(ProductCsv.builder()
                .name(product.getName())
                .imageId(product.getImages().get(0).getId())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .price(product.getPrice()).build()));
        return productCsvList;
    }

    public List<Product> convertFrom(List<ProductCsv> productCsvList) {
        return map(productCsvList);
    }

    private List<Product> map(List<ProductCsv> productCsvList) {
        List<Product> productList = new ArrayList<>();
        productCsvList.forEach(productCsv -> productList.add(Product.builder()
                .id(productCsv.getId())
                .name(productCsv.getName())
                .images(new ArrayList<>(Collections.singletonList(imageService.getImageById(productCsv.getImageId()).orElse(null))))
                .description(productCsv.getDescription())
                .category(categoryService.getCategoryById(productCsv.getCategoryId()).orElse(null))
                .price(productCsv.getPrice())
                .orders(new ArrayList<>()).build()));
        return productList;
    }
}
